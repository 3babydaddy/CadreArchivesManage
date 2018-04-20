/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.web;

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

import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblBorrowExport;
import com.tfkj.business.borrow.service.TblBorrowArchivesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExcelUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
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
		addMessage(redirectAttributes, "保存借阅记录成功");
		return "redirect:"+Global.getAdminPath()+"/terminal/borrowArchives";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblBorrowArchives tblBorrowArchives, String idStr, RedirectAttributes redirectAttributes) {
		tblBorrowArchivesService.delete(tblBorrowArchives, idStr);
		addMessage(redirectAttributes, "删除借阅记录成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblBorrowArchives/?repage";
	}
	
	@RequestMapping(value = "look")
	public String look(TblBorrowArchives tblBorrowArchives, Model model) {
		model.addAttribute("tblBorrowArchives", tblBorrowArchives);
		return "business/borrow/tblBorrowArchivesLook";
	}
	
	/**
	 * 送审，更改借阅数据的状态
	 * @param tblBorrowArchives
	 * @param idStr
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "censorship")
	@ResponseBody
	public Map<String, Object> censorship(TblBorrowArchives tblBorrowArchives, String idStr, RedirectAttributes redirectAttributes) {
		Map<String, Object> resultMap = new HashMap<>();
		try{
			tblBorrowArchivesService.censorship(tblBorrowArchives, idStr);
			resultMap.put("flag", "success");
			resultMap.put("msg", "送审借阅记录成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("flag", "fail");
			resultMap.put("msg", "送审借阅记录失败");
		}
		return resultMap;
	}
	/**
	 * 审核借阅数据
	 * @param tblBorrowArchives
	 * @param idStr
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "auditData")
	public String auditData(TblBorrowArchives tblBorrowArchives, String idStr, String status, RedirectAttributes redirectAttributes) {
		tblBorrowArchivesService.auditData(tblBorrowArchives, idStr, status);
		addMessage(redirectAttributes, "审核借阅记录成功");
		return "redirect:"+Global.getAdminPath()+"/borrow/tblBorrowArchives/?repage";
	}
	/**
	 * 借阅人员查询统计数据列表
	 * @param tblBorrowArchives
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"querycountlist"})
	public String queryCountPage(TblBorrowArchives tblBorrowArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblBorrowArchives> page = tblBorrowArchivesService.queryCountPage(new Page<TblBorrowArchives>(request, response), tblBorrowArchives); 
		model.addAttribute("page", page);
		return "business/borrow/tblBorrowCountList";
	}
	
	/**
	 * 借阅人员查询统计数据导出
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    @ResponseBody
    public Map<String, Object> exportData(TblBorrowArchives tblBorrowArchives, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		Map<String, Object> resultMap = new HashMap<>();
    	try {
    		String fileName = "borrowArchives.xlsx";
    		ExportExcel export = new ExportExcel();
    		String realPath = export.createFilePath(fileName);
    		
    		List<TblBorrowExport> list = tblBorrowArchivesService.queryCountList(tblBorrowArchives);
			ExcelUtils<TblBorrowExport> excelUtils = new ExcelUtils<TblBorrowExport>(TblBorrowExport.class);
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
   	 * 借阅人员查询统计数据导出
   	 * @param response
   	 * @param redirectAttributes
   	 * @return
   	 */
      @RequestMapping(value = "doDown")
      public void doDown(String filePath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
   	   try{
   			filePath = java.net.URLDecoder.decode(filePath,"UTF-8");
   			ExportExcel export = new ExportExcel();
   			export.doDown(filePath, "借阅人员数据信息统计.xlsx", request, response);
   		}catch(Exception e){
   			e.printStackTrace();
   		}
      }
}