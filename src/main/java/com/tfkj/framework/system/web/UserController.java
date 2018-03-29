/**
 * i * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tf.permission.client.entity.User;
import com.tf.permission.client.service.PermissionClientService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;
import com.tfkj.framework.system.utils.UserUtils;

/**
 * 用户Controller
 *
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {
	@Autowired
	private PermissionClientService permissionClient;

	/*@Autowired
	private SystemService systemService;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {

		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	*//**
	 * 跳转到list页
	 *
	 * @return
	 *//*
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "index", "" })
	public String index() {

		return "system/user/userList";
	}

	*//**
	 * json格式列表
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "list" })
	@ResponseBody
	public PageResult<User> list(User user, HttpServletRequest request, HttpServletResponse response) {

		PageResult<User> pageResult = userService.findPage(user, new PageParam(request));
		return new PageResult<User>();
	}

	*//**
	 * 获取查询条件
	 *
	 * @param user
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {

		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "system/user/userForm";
	}

	*//**
	 * 用户添加中的保存
	 *
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(user.getNewPassword());
		}
		if (!beanValidator(model, user)) {
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user";
	}

	*//**
	 * 删除
	 *
	 * @param user
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {

		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (user.isSuperAdmin()) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user";
	}

	*//**
	 * 导出用户数据
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("用户数据", User.class).setDataList(systemService.findUser(user)).write(response, fileName).dispose();
			return null;
		} catch ( Exception e ) {
			addMessage(redirectAttributes, "导出用户失败！系统出现错误,请稍后重试!");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	*//**
	 * 导入用户数据
	 *
	 * @param file
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPassword(Constants.SYSTEM_DEFAULT_LOGIN_PASSWORD);
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					} else {
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
						failureNum++;
					}
				} catch ( ConstraintViolationException ex ) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch ( Exception ex ) {
					failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch ( Exception e ) {
			addMessage(redirectAttributes, "导入用户失败！系统出现错误,请稍后重试!");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	*//**
	 * 下载导入用户数据模板
	 *
	 * @param response
	 * @param redirectAttributes
	 * @return
	 *//*
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {

		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch ( Exception e ) {
			addMessage(redirectAttributes, "导入模板下载失败！系统出现错误,请稍后重试!");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	*//**
	 * 验证登录名是否有效
	 *
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 *//*
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {

		return userService.checkLoginName(oldLoginName, loginName);
	}

	*//**
	 * 用户信息显示及保存
	 *
	 * @param user
	 * @param model
	 * @return
	 *//*
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {

		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "system/user/userInfo";
			}
			currentUser.setRemarks(user.getRemarks());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "system/user/userInfo";
	}

	*//**
	 * 返回用户信息
	 *
	 * @return
	 *//*
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {

		return UserUtils.getUser();
	}*/

	/**
	 * 修改个人用户密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	/*@RequiresPermissions("user")*/
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {

		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "system/user/userModifyPwd";
			}
			/*if (SystemService.validatePassword(oldPassword, user.getPassword())) {*/
				int i = permissionClient.modifyUserPassword(user.getUsername(), oldPassword, newPassword);
				/*systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);*/
				if(i != -1){
					model.addAttribute("message", "修改密码成功");
				}else{
					model.addAttribute("message", "修改密码失败，旧密码错误");
				}
			/*} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}*/
		}
		model.addAttribute("user", user);
		return "system/user/userModifyPwd";
	}

	/*@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {

		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}*/
}
