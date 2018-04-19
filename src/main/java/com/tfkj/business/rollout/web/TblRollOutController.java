/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.web;

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

import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.business.rollout.entity.TblRollOutPersonsExport;
import com.tfkj.business.rollout.service.TblRollOutService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExcelUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
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
	/**
	 * 一个批次转出的人员列表
	 * @param tblRollOutPersons
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"personlist"})
	public String personlist(TblRollOutPersons tblRollOutPersons, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollOutPersons> page = tblRollOutService.findPersonPage(new Page<TblRollOutPersons>(request, response), tblRollOutPersons); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", tblRollOutPersons.getMainId());
		model.addAttribute("batchNum", tblRollOutPersons.getBatchNum().replace("zi", "字").replace("hao", "号"));
		return "business/rollout/tblRollOutPersonsList";
	}
	/**
	 * 转出管理的查询统计的列表
	 * @param tblRollOutPersons
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"querycountlist"})
	public String queryCountPage(TblRollOutPersons tblRollOutPersons, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblRollOutPersons> page = tblRollOutService.queryCountPage(new Page<TblRollOutPersons>(request, response), tblRollOutPersons); 
		model.addAttribute("page", page);
		return "business/rollout/tblRollOutCountList";
	}
	/**
	 * 转出管理的查询统计导出
	 * @param tblRollOutPersons
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"export"})
	@ResponseBody
	public Map<String, Object> queryCountList(TblRollOutPersons tblRollOutPersons, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		Map<String, Object> resultMap = new HashMap<>();
    	try {
    		String fileName = "consultArchives.xlsx";
    		ExportExcel export = new ExportExcel();
    		String realPath = export.createFilePath(fileName);
    		
    		List<TblRollOutPersonsExport> list = tblRollOutService.queryCountList(tblRollOutPersons);  
			ExcelUtils<TblRollOutPersonsExport> excelUtils = new ExcelUtils<TblRollOutPersonsExport>(TblRollOutPersonsExport.class);
			excelUtils.writeToFile(list, realPath);
			resultMap.put("flag", "success");
			resultMap.put("filePath", realPath);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出查阅管理-数据统计失败！失败信息："+e.getMessage());
			resultMap.put("flag", "fail");
		}
    	return resultMap;
	}
	
	
	 /**
		 * 转出人员查询统计数据导出
		 * @param response
		 * @param redirectAttributes
		 * @return
		 */
	   @RequestMapping(value = "doDown")
	   public void doDown(String filePath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		   try{
				filePath = java.net.URLDecoder.decode(filePath,"UTF-8");
				ExportExcel export = new ExportExcel();
				export.doDown(filePath, "转出人员数据信息统计.xlsx", request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
	   }
	
}