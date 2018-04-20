/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.business.rollin.entity.TblRollInPersonsExport;
import com.tfkj.business.rollin.service.TblRollInService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExcelUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
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
		return "redirect:"+Global.getAdminPath()+"/terminal/rollInArchives";
	}
	
	
	@RequestMapping(value = "saveTerminal")
	@ResponseBody
	public boolean saveTerminal(TblRollIn tblRollIn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblRollIn)){
			return false;
		}
		tblRollInService.save(tblRollIn);
		addMessage(redirectAttributes, "保存转入管理人员成功");
		return true;
	}
	@RequestMapping(value = "delete")
	public String delete(TblRollIn tblRollIn, String idStr, RedirectAttributes redirectAttributes) {
		tblRollInService.delete(tblRollIn, idStr);
		addMessage(redirectAttributes, "删除转入管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/rollin/tblRollIn/?repage";
	}
	
	@RequestMapping(value = "look")
	public String look(TblRollIn tblRollIn, Model model) {
		model.addAttribute("tblRollIn", tblRollIn);
		return "business/rollin/tblRollInLook";
	}
	
	/**
	 * 送审，更改转入数据的状态
	 * @param tblRollIn
	 * @param idStr
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "censorship")
	@ResponseBody
	public Map<String, Object> censorship(TblRollIn tblRollIn, String idStr, RedirectAttributes redirectAttributes) {
		Map<String, Object> resultMap = new HashMap<>();
		try{
			tblRollInService.censorship(tblRollIn, idStr);
			resultMap.put("flag", "success");
			resultMap.put("msg", "送审转入记录成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("flag", "fail");
			resultMap.put("msg", "送审转入记录失败");
		}
		return resultMap;
	}
	/**
	 * 审核转入数据
	 * @param tblRollIn
	 * @param idStr
	 * @param status
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "auditData")
	public String auditData(TblRollIn tblRollIn, String idStr, String status, RedirectAttributes redirectAttributes) {
		tblRollInService.auditData(tblRollIn, idStr, status);
		addMessage(redirectAttributes, "审核转入记录成功");
		return "redirect:"+Global.getAdminPath()+"/rollin/tblRollIn/?repage";
	}
	
	/**
	 * 一个批次的转入的人员列表
	 * @param per
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"personlist"})
	public String personlist(TblRollInPersons per, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollInPersons> page = tblRollInService.findPersonPage(new Page<TblRollInPersons>(request, response), per); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", per.getMainId());
		model.addAttribute("batchNum", per.getBatchNum().replace("zi", "字").replace("hao", "号"));
		return "business/rollin/tblRollInPersonsList";
	}
	/**
	 * 转入管理的查询统计列表
	 * @param tblRollInPersons
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"querycountlist"})
	public String queryCountPage(TblRollInPersons tblRollInPersons, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollInPersons> page = tblRollInService.queryCountPage(new Page<TblRollInPersons>(request, response), tblRollInPersons); 
		model.addAttribute("page", page);
		return "business/rollin/tblRollInCountList";
	}
	
	/**
	 * 转入查询统计数据导出
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    @ResponseBody
    public Map<String, Object> exportData(TblRollInPersons tblRollInPersons, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		Map<String, Object> resultMap = new HashMap<>();
    	try {
    		String fileName = "borrowArchives.xlsx";
    		ExportExcel export = new ExportExcel();
    		String realPath = export.createFilePath(fileName);
    		
    		List<TblRollInPersonsExport> list = tblRollInService.queryCountList(tblRollInPersons); 
			ExcelUtils<TblRollInPersonsExport> excelUtils = new ExcelUtils<TblRollInPersonsExport>(TblRollInPersonsExport.class);
			excelUtils.writeToFile(list, realPath);
			resultMap.put("flag", "success");
			resultMap.put("filePath", realPath);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出借阅管理-数据统计失败！失败信息："+e.getMessage());
			resultMap.put("flag", "fail");
		}
    	return resultMap;
    }
    
    
    /**
   	 * 转入人员查询统计数据导出
   	 * @param response
   	 * @param redirectAttributes
   	 * @return
   	 */
      @RequestMapping(value = "doDown")
      public void doDown(String filePath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
   	   try{
   			filePath = java.net.URLDecoder.decode(filePath,"UTF-8");
   			ExportExcel export = new ExportExcel();
   			export.doDown(filePath, "转入人员数据信息统计.xlsx", request, response);
   		}catch(Exception e){
   			e.printStackTrace();
   		}
      }
}