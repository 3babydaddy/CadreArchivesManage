/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.business.rollin.service.TblRollInService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;

/**
 * 转入管理人员Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/rollin/tblRollIn")
public class TblRollInController extends BaseController {

	@Autowired
	private TblRollInService tblRollInService;
	
	@ModelAttribute
	public TblRollIn get(@RequestParam(required=false) String id) {
		TblRollIn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblRollInService.get(id);
		}
		if (entity == null){
			entity = new TblRollIn();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblRollIn tblRollIn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollIn> page = tblRollInService.findPage(new Page<TblRollIn>(request, response), tblRollIn); 
		model.addAttribute("page", page);
		return "business/rollin/tblRollInList";
	}

	@RequestMapping(value = "form")
	public String form(TblRollIn tblRollIn, Model model) {
		model.addAttribute("tblRollIn", tblRollIn);
		return "business/rollin/tblRollInForm";
	}

	@RequestMapping(value = "save")
	public String save(TblRollIn tblRollIn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblRollIn)){
			return form(tblRollIn, model);
		}
		tblRollInService.save(tblRollIn);
		addMessage(redirectAttributes, "保存转入管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/rollin/tblRollIn/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblRollIn tblRollIn, String idStr, RedirectAttributes redirectAttributes) {
		tblRollInService.delete(tblRollIn, idStr);
		addMessage(redirectAttributes, "删除转入管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/rollin/tblRollIn/?repage";
	}

	@RequestMapping(value = {"personlist"})
	public String personlist(TblRollInPersons per, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollInPersons> page = tblRollInService.findPersonPage(new Page<TblRollInPersons>(request, response), per); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", per.getMainId());
		model.addAttribute("batchNum", per.getBatchNum().replace("zi", "字").replace("hao", "号"));
		return "business/rollin/tblRollInPersonsList";
	}
}