/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.service.TblBorrowArchivesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;

/**
 * 借阅管理Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/tblBorrowArchives")
public class TblBorrowArchivesController extends BaseController {

	@Autowired
	private TblBorrowArchivesService tblBorrowArchivesService;
	
	@ModelAttribute
	public TblBorrowArchives get(@RequestParam(required=false) String id) {
		TblBorrowArchives entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblBorrowArchivesService.get(id);
		}
		if (entity == null){
			entity = new TblBorrowArchives();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblBorrowArchives tblBorrowArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblBorrowArchives> page = tblBorrowArchivesService.findPage(new Page<TblBorrowArchives>(request, response), tblBorrowArchives); 
		model.addAttribute("page", page);
		return "business/borrow/tblBorrowArchivesList";
	}

	@RequestMapping(value = "form")
	public String form(TblBorrowArchives tblBorrowArchives, Model model) {
		model.addAttribute("tblBorrowArchives", tblBorrowArchives);
		return "business/borrow/tblBorrowArchivesForm";
	}

	@RequestMapping(value = "save")
	public String save(TblBorrowArchives tblBorrowArchives, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblBorrowArchives)){
			return form(tblBorrowArchives, model);
		}
		tblBorrowArchivesService.save(tblBorrowArchives);
		addMessage(redirectAttributes, "保存借阅管理成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblBorrowArchives/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblBorrowArchives tblBorrowArchives, String idStr, RedirectAttributes redirectAttributes) {
		tblBorrowArchivesService.delete(tblBorrowArchives, idStr);
		addMessage(redirectAttributes, "删除借阅管理成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblBorrowArchives/?repage";
	}

}