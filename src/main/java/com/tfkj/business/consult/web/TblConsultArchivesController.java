/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.consult.entity.TblConsultArchives;
import com.tfkj.business.consult.service.TblConsultArchivesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;

/**
 * 查阅档案Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/consult/tblConsultArchives")
public class TblConsultArchivesController extends BaseController {

	@Autowired
	private TblConsultArchivesService tblConsultArchivesService;
	
	@ModelAttribute
	public TblConsultArchives get(@RequestParam(required=false) String id) {
		TblConsultArchives entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblConsultArchivesService.get(id);
		}
		if (entity == null){
			entity = new TblConsultArchives();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblConsultArchives tblConsultArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblConsultArchives> page = tblConsultArchivesService.findPage(new Page<TblConsultArchives>(request, response), tblConsultArchives); 
		model.addAttribute("page", page);
		return "business/consult/tblConsultArchivesList";
	}

	@RequestMapping(value = "form")
	public String form(TblConsultArchives tblConsultArchives, Model model) {
		model.addAttribute("tblConsultArchives", tblConsultArchives);
		return "business/consult/tblConsultArchivesForm";
	}

	@RequestMapping(value = "save")
	public String save(TblConsultArchives tblConsultArchives, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblConsultArchives)){
			return form(tblConsultArchives, model);
		}
		tblConsultArchivesService.save(tblConsultArchives);
		addMessage(redirectAttributes, "保存查阅档案成功");
		return "redirect:"+Global.getAdminPath()+"/consult/tblConsultArchives/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblConsultArchives tblConsultArchives, String idStr, RedirectAttributes redirectAttributes) {
		tblConsultArchivesService.delete(tblConsultArchives, idStr);
		addMessage(redirectAttributes, "删除查阅档案成功");
		return "redirect:"+Global.getAdminPath()+"/consult/tblConsultArchives/?repage";
	}

}