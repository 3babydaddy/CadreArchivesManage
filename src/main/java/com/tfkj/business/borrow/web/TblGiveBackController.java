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

import com.tfkj.business.borrow.entity.TblGiveBack;
import com.tfkj.business.borrow.service.TblGiveBackService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;

/**
 * 借阅归还Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/tblGiveBack")
public class TblGiveBackController extends BaseController {

	@Autowired
	private TblGiveBackService tblGiveBackService;
	
	@ModelAttribute
	public TblGiveBack get(@RequestParam(required=false) String id) {
		TblGiveBack entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblGiveBackService.get(id);
		}
		if (entity == null){
			entity = new TblGiveBack();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblGiveBack tblGiveBack, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblGiveBack> page = tblGiveBackService.findPage(new Page<TblGiveBack>(request, response), tblGiveBack); 
		model.addAttribute("page", page);
		return "business/borrow/tblGiveBackList";
	}

	@RequestMapping(value = "form")
	public String form(TblGiveBack tblGiveBack, Model model) {
		model.addAttribute("tblGiveBack", tblGiveBack);
		return "business/borrow/tblGiveBackForm";
	}

	@RequestMapping(value = "save")
	public String save(TblGiveBack tblGiveBack, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblGiveBack)){
			return form(tblGiveBack, model);
		}
		tblGiveBackService.save(tblGiveBack);
		addMessage(redirectAttributes, "保存借阅归还成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblGiveBack/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblGiveBack tblGiveBack, RedirectAttributes redirectAttributes) {
		tblGiveBackService.delete(tblGiveBack);
		addMessage(redirectAttributes, "删除借阅归还成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblGiveBack/?repage";
	}

}