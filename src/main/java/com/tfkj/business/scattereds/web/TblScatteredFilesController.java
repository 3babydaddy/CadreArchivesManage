/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.business.scattereds.service.TblScatteredFilesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;

/**
 * 零散材料移交人员Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/scattereds/tblScatteredFiles")
public class TblScatteredFilesController extends BaseController {

	@Autowired
	private TblScatteredFilesService tblScatteredFilesService;
	
	@ModelAttribute
	public TblScatteredFiles get(@RequestParam(required=false) String id) {
		TblScatteredFiles entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblScatteredFilesService.get(id);
		}
		if (entity == null){
			entity = new TblScatteredFiles();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblScatteredFiles tblScatteredFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblScatteredFiles> page = tblScatteredFilesService.findPage(new Page<TblScatteredFiles>(request, response), tblScatteredFiles); 
		model.addAttribute("page", page);
		return "business/scattereds/tblScatteredFilesList";
	}

	@RequestMapping(value = "form")
	public String form(TblScatteredFiles tblScatteredFiles, Model model) {
		model.addAttribute("tblScatteredFiles", tblScatteredFiles);
		return "business/scattereds/tblScatteredFilesForm";
	}

	@RequestMapping(value = "save")
	public String save(TblScatteredFiles tblScatteredFiles, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblScatteredFiles)){
			return form(tblScatteredFiles, model);
		}
		tblScatteredFilesService.save(tblScatteredFiles);
		addMessage(redirectAttributes, "保存零散材料移交人员成功");
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblScatteredFiles tblScatteredFiles, String idStr, RedirectAttributes redirectAttributes) {
		tblScatteredFilesService.delete(tblScatteredFiles, idStr);
		addMessage(redirectAttributes, "删除零散材料移交人员成功");
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
	}
	
	@RequestMapping(value = {"personlist"})
	public String personlist(TblHandOverFiles tblHandOverFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblHandOverFiles> page = tblScatteredFilesService.findPersonPage(new Page<TblHandOverFiles>(request, response), tblHandOverFiles); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", tblHandOverFiles.getMainId());
		return "business/scattereds/tblHandOverFilesList";
	}

}