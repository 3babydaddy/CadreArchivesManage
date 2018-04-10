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

import com.tfkj.business.rollout.entity.TblRollOutBack;
import com.tfkj.business.rollout.service.TblRollOutBackService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;


/**
 * 转出管理回执Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/rollout/tblRollOutBack")
public class TblRollOutBackController extends BaseController {

	@Autowired
	private TblRollOutBackService tblRollOutBackService;
	
	@ModelAttribute
	public TblRollOutBack get(@RequestParam(required=false) String id) {
		TblRollOutBack entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblRollOutBackService.get(id);
		}
		if (entity == null){
			entity = new TblRollOutBack();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblRollOutBack tblRollOutBack, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollOutBack> page = tblRollOutBackService.findPage(new Page<TblRollOutBack>(request, response), tblRollOutBack); 
		model.addAttribute("page", page);
		return "business/rollout/tblRollOutBackList";
	}

	@RequestMapping(value = "form")
	public String form(TblRollOutBack tblRollOutBack, Model model) {
		model.addAttribute("tblRollOutBack", tblRollOutBack);
		return "business/rollout/tblRollOutBackForm";
	}

	@RequestMapping(value = "save")
	public String save(TblRollOutBack tblRollOutBack, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblRollOutBack)){
			return form(tblRollOutBack, model);
		}
		tblRollOutBackService.save(tblRollOutBack);
		addMessage(redirectAttributes, "保存转出管理回执成功");
		return "redirect:"+Global.getAdminPath()+"/rollout/tblRollOutBack/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblRollOutBack tblRollOutBack, RedirectAttributes redirectAttributes) {
		tblRollOutBackService.delete(tblRollOutBack);
		addMessage(redirectAttributes, "删除转出管理回执成功");
		return "redirect:"+Global.getAdminPath()+"/rollout/tblRollOutBack/?repage";
	}
	
	@RequestMapping(value = "receiptSave")
	public String receiptSave(String rollApproveAttachment, String rollOutId, RedirectAttributes redirectAttributes) {
		
		addMessage(redirectAttributes, "回执上传成功");
		return "redirect:"+Global.getAdminPath()+"/rollout/tblRollOut/?repage";
	}

}