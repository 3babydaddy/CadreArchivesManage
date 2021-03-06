package com.tfkj.business.consult.web;

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

import com.tfkj.business.consult.entity.TblConsultArchives;
import com.tfkj.business.consult.entity.TblConsultExport;
import com.tfkj.business.consult.service.TblConsultArchivesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExcelUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
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
	
	@RequestMapping(value = "saveTerminal")
	@ResponseBody
	public boolean saveTerminal(TblConsultArchives tblConsultArchives, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblConsultArchives)){
			return false;
		}
		tblConsultArchivesService.save(tblConsultArchives);
		addMessage(redirectAttributes, "保存查阅档案成功");
		return true;
	}
	@RequestMapping(value = "delete")
	public String delete(TblConsultArchives tblConsultArchives, String idStr, RedirectAttributes redirectAttributes) {
		tblConsultArchivesService.delete(tblConsultArchives, idStr);
		addMessage(redirectAttributes, "删除查阅档案成功");
		return "redirect:"+Global.getAdminPath()+"/consult/tblConsultArchives/?repage";
	}
	
	@RequestMapping(value = "look")
	public String look(TblConsultArchives tblConsultArchives, Model model) {
		model.addAttribute("tblConsultArchives", tblConsultArchives);
		return "business/consult/tblConsultArchivesLook";
	}
	
	/**
	 * 签名功能
	 * @param siginId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "drowSigin")
	public String drowSigin(String siginId, Model model) {
		
		model.addAttribute("siginId", siginId);
		return "business/consult/tblDrowSigin";
	}
	
	/**
	 * 签名功能
	 * @param siginId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "drowSiginByUser")
	public String drowSiginByUser(String siginId, Model model) {
		
		model.addAttribute("siginId", siginId);
		return "business/terminal/drowSigin";
	}
	
	/**
	 * 送审，更改借阅数据的状态
	 * @param tblConsultArchives
	 * @param idStr
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "censorship")
	@ResponseBody
	public Map<String, Object> censorship(TblConsultArchives tblConsultArchives, String idStr, RedirectAttributes redirectAttributes) {
		Map<String, Object> resultMap = new HashMap<>();
		try{
			tblConsultArchivesService.censorship(tblConsultArchives, idStr);
			resultMap.put("flag", "success");
			resultMap.put("msg", "送审查阅记录成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("flag", "fail");
			resultMap.put("msg", "送审查阅记录失败");
		}
		return resultMap;
	}
	/**
	 * 审核借阅数据
	 * @param tblConsultArchives
	 * @param idStr
	 * @param status
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "auditData")
	public String auditData(TblConsultArchives tblConsultArchives, String idStr, String status, RedirectAttributes redirectAttributes) {
		tblConsultArchivesService.auditData(tblConsultArchives, idStr, status);
		addMessage(redirectAttributes, "审核查阅记录成功");
		return "redirect:"+Global.getAdminPath()+"/consult/tblConsultArchives/?repage";
	}
	/**
	 * 查阅人员查询统计数据列表
	 * @param tblConsultArchives
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"querycountlist"})
	public String queryCountPage(TblConsultArchives tblConsultArchives, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblConsultArchives> page = tblConsultArchivesService.queryCountPage(new Page<TblConsultArchives>(request, response), tblConsultArchives); 
		model.addAttribute("page", page);
		return "business/consult/tblConsultCountList";
	}
	
	 /**
	 * 查阅人员查询统计数据导出
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    @ResponseBody
    public Map<String, Object> exportData(TblConsultArchives tblConsultArchives, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Map<String, Object> resultMap = new HashMap<>();
    	try {
    		String fileName = "consultArchives.xlsx";
    		ExportExcel export = new ExportExcel();
    		String realPath = export.createFilePath(fileName);
    		
    		List<TblConsultExport> list = tblConsultArchivesService.queryCountList(tblConsultArchives); 
			ExcelUtils<TblConsultExport> excelUtils = new ExcelUtils<TblConsultExport>(TblConsultExport.class);
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
	 * 查阅人员查询统计数据导出
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
   @RequestMapping(value = "doDown")
   public void doDown(String filePath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
	   try{
			filePath = java.net.URLDecoder.decode(filePath,"UTF-8");
			ExportExcel export = new ExportExcel();
			export.doDown(filePath, "查阅人员数据信息统计.xlsx", request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
   }
		
   
}