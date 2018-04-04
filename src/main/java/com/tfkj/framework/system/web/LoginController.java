/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.utils.CacheUtils;
import com.tfkj.framework.core.utils.CookieUtils;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;
import com.tfkj.framework.system.entity.User;
import com.tfkj.framework.system.security.FormAuthenticationFilter;
import com.tfkj.framework.system.utils.LoginModelUtil;
import com.tfkj.framework.system.utils.UserUtils;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

	/*
	 * @Autowired private SessionDAO sessionDAO;
	 */

	@RequestMapping(value = "${adminPath}/login/help", method = RequestMethod.GET)
	public String help() {

		return "business/common/productIntroduction";
	}

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		/*
		 * Principal principal = UserUtils.getPrincipal(); if
		 * (logger.isDebugEnabled()) {
		 * logger.debug("login, active session size: {}",
		 * sessionDAO.getActiveSessions(false).size()); }
		 */
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		// 如果已经登录，则跳转到管理首页
		Subject subject = UserUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			return "redirect:" + adminPath;
		}
		return "login";
	}

	/**
	 * 管理退出
	 */
	@RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Subject subject = UserUtils.getSubject();
		subject.logout();
		return "login";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		/*
		 * Principal principal = UserUtils.getPrincipal(); // 如果已经登录，则跳转到管理首页 if
		 * (principal != null) { return "redirect:" + adminPath; }
		 */

		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		String error = null;

		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(
				exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (exceptionClassName != null) {
			error = "其他错误：" + exceptionClassName;
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM,
				error);
		if (!StringUtils.isEmpty(error)) {
			return "login";
		}
		return "redirect:" + adminPath;
		/*
		 * String username = WebUtils.getCleanParam(request,
		 * FormAuthenticationFilter.DEFAULT_USERNAME_PARAM); boolean rememberMe
		 * = WebUtils.isTrue(request,
		 * FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM); boolean mobile =
		 * WebUtils.isTrue(request,
		 * FormAuthenticationFilter.DEFAULT_MOBILE_PARAM); String exception =
		 * (String) request.getAttribute(FormAuthenticationFilter.
		 * DEFAULT_ERROR_KEY_ATTRIBUTE_NAME); String message = (String)
		 * request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		 * if (StringUtils.isBlank(message) || StringUtils.equals(message,
		 * "null")) { message = "用户或密码错误, 请重试."; }
		 * model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
		 * username);
		 * model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM
		 * , rememberMe);
		 * model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM,
		 * mobile); model.addAttribute(FormAuthenticationFilter.
		 * DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		 * model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM,
		 * message); if (logger.isDebugEnabled()) { logger.debug(
		 * "login fail, active session size: {}, message: {}, exception: {}",
		 * sessionDAO.getActiveSessions(false).size(), message, exception); }
		 * return "login";
		 */
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {

		// Principal principal = UserUtils.getPrincipal();
		// // 登录成功后，验证码计算器清零
		// isValidateCodeLogin(principal.getLoginName(), false, true);
		// if (logger.isDebugEnabled()) {
		// /*logger.debug("show index, active session size: {}",
		// sessionDAO.getActiveSessions(false).size());*/
		// }
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)) {
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")) {
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		Subject subject = UserUtils.getSubject();
		if(subject.hasRole("admin")){
			return "index";
		}else{
			return "index-user";
		}
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme,
			HttpServletRequest request, HttpServletResponse response) {

		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:" + request.getParameter("url");
	}

	/**
	 * 是否是验证码登录
	 * 
	 * @param useruame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail,
			boolean clean) {

		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils
				.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}

	/**
	 * @Title: isValidateLoginKey
	 * @Description: 验证加密锁
	 * @param @param loginKey
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isValidateLoginKey(User user, String loginKey,
			String randomString) {

		// 验证-登陆-加密锁
		String clientRandomString = randomString;
		String ciphertext = loginKey;
		LoginModelUtil lm = new LoginModelUtil();
		if (user == null) {
			return false;
		}
		// -登陆-加密规则--改
		lm.setPublicKey(user.getLoginName() + user.getOffice().getId());
		// -登陆-加密规则--改
		lm.setRandomString(clientRandomString);
		lm.setCiphertext(ciphertext);
		if (lm.CheckAuthorityWithEPass()) {
			return true;
		} else {
			return false;
		}
	}
}
