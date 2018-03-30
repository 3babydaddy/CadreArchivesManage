/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.business.rollout.service.TblRollOutService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;


/**
 * 转出管理人员Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/rollout/tblRollOut")
public class TblRollOutController extends BaseController {

	@Autowired
	private TblRollOutService tblRollOutService;
	
	@ModelAttribute
	public TblRollOut get(@RequestParam(required=false) String id) {
		TblRollOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblRollOutService.get(id);
		}
		if (entity == null){
			entity = new TblRollOut();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblRollOut tblRollOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollOut> page = tblRollOutService.findPage(new Page<TblRollOut>(request, response), tblRollOut); 
		model.addAttribute("page", page);
		return "business/rollout/tblRollOutList";
	}

	@RequestMapping(value = "form")
	public String form(TblRollOut tblRollOut, Model model) {
		model.addAttribute("tblRollOut", tblRollOut);
		return "business/rollout/tblRollOutForm";
	}

	@RequestMapping(value = "save")
	public String save(TblRollOut tblRollOut, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblRollOut)){
			return form(tblRollOut, model);
		}
		tblRollOutService.save(tblRollOut);
		addMessage(redirectAttributes, "保存转出管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/rollout/tblRollOut/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblRollOut tblRollOut, String idStr, RedirectAttributes redirectAttributes) {
		tblRollOutService.delete(tblRollOut, idStr);
		addMessage(redirectAttributes, "删除转出管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/rollout/tblRollOut/?repage";
	}

	@RequestMapping(value = {"personlist"})
	public String personlist(TblRollOutPersons tblRollOutPersons, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollOutPersons> page = tblRollOutService.findPersonPage(new Page<TblRollOutPersons>(request, response), tblRollOutPersons); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", tblRollOutPersons.getMainId());
		return "business/rollout/tblRollOutPersonsList";
	}
}