/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.suphandle.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.suphandle.entity.TblSuperviseHandle;
import com.tfkj.business.suphandle.service.TblSuperviseHandleService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;



/**
 * 督查督办Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/suphandle/tblSuperviseHandle")
public class TblSuperviseHandleController extends BaseController {

	@Autowired
	private TblSuperviseHandleService tblSuperviseHandleService;
	
	@ModelAttribute
	public TblSuperviseHandle get(@RequestParam(required=false) String id) {
		TblSuperviseHandle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblSuperviseHandleService.get(id);
		}
		if (entity == null){
			entity = new TblSuperviseHandle();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblSuperviseHandle tblSuperviseHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblSuperviseHandle> page = tblSuperviseHandleService.findPage(new Page<TblSuperviseHandle>(request, response), tblSuperviseHandle); 
		model.addAttribute("page", page);
		return "business/suphandle/tblSuperviseHandleList";
	}

	@RequestMapping(value = "form")
	public String form(TblSuperviseHandle tblSuperviseHandle, Model model) {
		model.addAttribute("tblSuperviseHandle", tblSuperviseHandle);
		return "business/suphandle/tblSuperviseHandleForm";
	}

	@RequestMapping(value = "save")
	public String save(TblSuperviseHandle tblSuperviseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblSuperviseHandle)){
			return form(tblSuperviseHandle, model);
		}
		tblSuperviseHandleService.save(tblSuperviseHandle);
		addMessage(redirectAttributes, "保存督查督办成功");
		return "redirect:"+Global.getAdminPath()+"/suphandle/tblSuperviseHandle/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblSuperviseHandle tblSuperviseHandle, RedirectAttributes redirectAttributes) {
		tblSuperviseHandleService.delete(tblSuperviseHandle);
		addMessage(redirectAttributes, "删除督查督办成功");
		return "redirect:"+Global.getAdminPath()+"/suphandle/tblSuperviseHandle/?repage";
	}

}