/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.business.scattereds.service.TblScatteredFilesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
import com.tfkj.framework.core.utils.excel.ScatteredFileImportUtil;
import com.tfkj.framework.core.web.BaseController;
import com.tfkj.framework.system.dao.DictDao;
import com.tfkj.framework.system.entity.Dict;

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
	@Autowired
	private DictDao dictDao;
	
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

	/**
	 * 导入人员数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, TblHandOverFiles tblHandOverFiles, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/scattereds/tblScatteredFiles/list?repage";
		}
		try {
			String originalFilename = file.getOriginalFilename();
			ScatteredFileImportUtil util = new ScatteredFileImportUtil();
			TblScatteredFiles tblScatteredFiles = util.getExcelInfo(originalFilename, file);
			try{
				Dict dict = dictDao.getDictInfoByLabel(tblScatteredFiles.getHandOverUnitName());
				tblScatteredFiles.setHandOverUnit(dict.getValue());
				tblScatteredFilesService.saveScatteredInfo(tblScatteredFiles);
			}catch (Exception ex) {
				addMessage(redirectAttributes, "导入失败 ");
			}
			addMessage(redirectAttributes, "已成功导入 ");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
    }
	
	/**
	 * 下载人员模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "零散材料人员数据导入模板.xlsx";
    		List<TblHandOverFiles> list = Lists.newArrayList(); 
    		list.add(new TblHandOverFiles());
    		new ExportExcel("零散材料人员数据", TblHandOverFiles.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
    }
   
    
}