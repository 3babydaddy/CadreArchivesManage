package com.tfkj.business.scattereds.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblHandOverFilesExport;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.business.scattereds.service.TblScatteredFilesService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExcelUtils;
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
		return "redirect:"+Global.getAdminPath()+"/terminal/scatteredFiles";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblScatteredFiles tblScatteredFiles, String idStr, RedirectAttributes redirectAttributes) {
		tblScatteredFilesService.delete(tblScatteredFiles, idStr);
		addMessage(redirectAttributes, "删除零散材料移交人员成功");
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
	}
	
	/**
	 * 送审，更改零散材料数据的状态
	 * @param tblScatteredFiles
	 * @param idStr
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "censorship")
	@ResponseBody
	public Map<String, Object> censorship(TblScatteredFiles tblScatteredFiles, String idStr, RedirectAttributes redirectAttributes) {
		Map<String, Object> resultMap = new HashMap<>();
		try{
			tblScatteredFilesService.censorship(tblScatteredFiles, idStr);
			resultMap.put("flag", "success");
			resultMap.put("msg", "送审零散材料记录成功");
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("flag", "fail");
			resultMap.put("msg", "送审零散材料记录失败");
		}
		return resultMap;
	}
	/**
	 * 审核零散材料数据
	 * @param tblScatteredFiles
	 * @param idStr
	 * @param status
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "auditData")
	public String auditData(TblScatteredFiles tblScatteredFiles, String idStr, String status, RedirectAttributes redirectAttributes) {
		tblScatteredFilesService.auditData(tblScatteredFiles, idStr, status);
		addMessage(redirectAttributes, "审核借阅记录成功");
		return "redirect:"+Global.getAdminPath()+"/scattereds/tblScatteredFiles/?repage";
	}
	
	/**
	 * 某个零散材料的移交人员列表
	 * @param tblHandOverFiles
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"personlist"})
	public String personlist(TblHandOverFiles tblHandOverFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblHandOverFiles> page = tblScatteredFilesService.findPersonPage(new Page<TblHandOverFiles>(request, response), tblHandOverFiles); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", tblHandOverFiles.getMainId());
		return "business/scattereds/tblHandOverFilesList";
	}

	/**
	 * 导入移交人员数据
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
				Dict dict = dictDao.getDictInfoByLabel(tblScatteredFiles.getHandOverUnitName(), "unit_list");
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
     * 零散材料移交人员查询统计数据的列表
     * @param tblScatteredFiles
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"querycountlist"})
	public String findCountPage(TblScatteredFiles tblScatteredFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
    	Page<TblScatteredFiles> page = tblScatteredFilesService.findCountPage(new Page<TblScatteredFiles>(request, response), tblScatteredFiles); 
		model.addAttribute("page", page);
		return "business/scattereds/tblScatteredFilesCountList";
	}
    
    /**
	 * 零散材料移交人员查询统计数据导出
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    @ResponseBody
    public Map<String, Object> exportData(TblScatteredFiles tblScatteredFiles, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		Map<String, Object> resultMap = new HashMap<>();
    	try {
    		String fileName = "consultArchives.xlsx";
    		ExportExcel export = new ExportExcel();
    		String realPath = export.createFilePath(fileName);
    		
    		List<TblHandOverFilesExport> list = tblScatteredFilesService.findCountList(tblScatteredFiles);  
			ExcelUtils<TblHandOverFilesExport> excelUtils = new ExcelUtils<TblHandOverFilesExport>(TblHandOverFilesExport.class);
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
   	 * 移交人员查询统计数据导出
   	 * @param response
   	 * @param redirectAttributes
   	 * @return
   	 */
      @RequestMapping(value = "doDown")
      public void doDown(String filePath, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
   	   try{
   			filePath = java.net.URLDecoder.decode(filePath,"UTF-8");
   			ExportExcel export = new ExportExcel();
   			export.doDown(filePath, "移交人员数据信息统计.xlsx", request, response);
   		}catch(Exception e){
   			e.printStackTrace();
   		}
      }
>>>>>>> 36b84bc6a08a00472c6a7d7239a946109eb329ee
}