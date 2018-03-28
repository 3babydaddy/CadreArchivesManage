/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.retiredadre.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.retiredadre.entity.RetiredCadre;
import com.tfkj.business.retiredadre.service.RetiredCadreService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;



/**
 * 退休干部管理模块Controller
 * @author tianzhen
 * @version 2018-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/retiredadre/retiredCadre")
public class RetiredCadreController extends BaseController {

	@Autowired
	private RetiredCadreService retiredCadreService;
	
	@ModelAttribute
	public RetiredCadre get(@RequestParam(required=false) String id) {
		RetiredCadre entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = retiredCadreService.get(id);
		}
		if (entity == null){
			entity = new RetiredCadre();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(RetiredCadre retiredCadre, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RetiredCadre> page = retiredCadreService.findPage(new Page<RetiredCadre>(request, response), retiredCadre); 
		model.addAttribute("page", page);
		return "business/retiredadre/retiredCadreList";
	}

	@RequestMapping(value = "form")
	public String form(RetiredCadre retiredCadre, Model model) {
		model.addAttribute("retiredCadre", retiredCadre);
		return "business/retiredadre/retiredCadreForm";
	}

	@RequestMapping(value = "save")
	public String save(RetiredCadre retiredCadre, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, retiredCadre)){
			return form(retiredCadre, model);
		}
		retiredCadreService.save(retiredCadre);
		addMessage(redirectAttributes, "保存退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(RetiredCadre retiredCadre, String idStr, RedirectAttributes redirectAttributes) {
		retiredCadreService.delete(retiredCadre, idStr);
		addMessage(redirectAttributes, "删除退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}

}