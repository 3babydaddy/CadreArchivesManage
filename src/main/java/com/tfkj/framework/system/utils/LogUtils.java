/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tf.permission.client.entity.User;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.utils.CacheUtils;
import com.tfkj.framework.core.utils.Exceptions;
import com.tfkj.framework.core.utils.SpringContextHolder;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.system.dao.DictDao;
import com.tfkj.framework.system.dao.LogDao;
import com.tfkj.framework.system.dao.MenuDao;
import com.tfkj.framework.system.entity.Log;
import com.tfkj.framework.system.entity.Menu;

/**
 * 字典工具类
 * 
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {

	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title) {
		saveLog(request, null, null, title);
	}

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
		User user = UserUtils.getUser();
		if (user != null && user.getId() != null) {
			Log log = new Log();
			log.setTitle(title);
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setIp(StringUtils.getRemoteAddr(request));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setCreateDate(new Date());
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		}
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread {

		private Log log;
		private Object handler;
		private Exception ex;

		public SaveLogThread(Log log, Object handler, Exception ex) {
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}

		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())) {
				String permission = "";
				if (handler instanceof HandlerMethod) {
					Method m = ((HandlerMethod) handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
				}
				//log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
				log.setTitle(getMenuNamePathSelf(log.getRequestUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())) {
				return;
			}
			// 保存日志信息
			log.preInsert();
			logDao.insert(log);
		}
	}

	/**
	 * 获取菜单名称路径（如：系统设置-单位用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(String requestUri, String permission) {
		String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
		@SuppressWarnings("unchecked")
		Map<String, String> menuMap = (Map<String, String>) CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
		if (menuMap == null) {
			menuMap = Maps.newHashMap();
			List<Menu> menuList = menuDao.findAllList(new Menu());
			for (Menu menu : menuList) {
				// 获取菜单名称路径（如：系统设置-单位用户-用户管理-编辑）
				String namePath = "";
				if (menu.getParentIds() != null) {
					List<String> namePathList = Lists.newArrayList();
					for (String id : StringUtils.split(menu.getParentIds(), ",")) {
						if (Menu.getRootId().equals(id)) {
							continue; // 过滤跟节点
						}
						for (Menu m : menuList) {
							if (m.getId().equals(id)) {
								namePathList.add(m.getName());
								break;
							}
						}
					}
					namePathList.add(menu.getName());
					namePath = StringUtils.join(namePathList, "-");
				}
				// 设置菜单名称路径
				if (StringUtils.isNotBlank(menu.getHref())) {
					menuMap.put(menu.getHref(), namePath);
				}
				if (StringUtils.isNotBlank(menu.getPermission())) {
					for (String p : StringUtils.split(menu.getPermission())) {
						menuMap.put(p, namePath);
					}
				}

			}
			CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
		}
		String menuNamePath = menuMap.get(href);
		if (menuNamePath == null) {
			for (String p : StringUtils.split(permission, ",")) {
				menuNamePath = menuMap.get(p);
				if (StringUtils.isNotBlank(menuNamePath)) {
					break;
				}
			}
			if (menuNamePath == null) {
				return "";
			}
		}
		return menuNamePath;
	}
	
	/**
	 * 获取菜单名称路径
	 */
	public static String getMenuNamePathSelf(String requestUri, String permission) {
		String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
		String menuNamePath = "";
		if(StringUtils.isBlank(href)){
			return menuNamePath;
		}else{
			menuNamePath = dictDao.getLabelByValue(href, "sysLogTitle");
		}
		return menuNamePath;
	}

}
