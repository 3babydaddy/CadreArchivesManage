/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.retiredadre.web;

import java.io.File;
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

import com.tfkj.business.retiredadre.entity.RetiredCadre;
import com.tfkj.business.retiredadre.service.RetiredCadreService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
import com.tfkj.framework.core.utils.excel.ImportExcel;
import com.tfkj.framework.core.web.BaseController;
import com.tfkj.framework.system.utils.FileUtils;


/**
 * 退休干部管理模块Controller
 * @author tianzhen
 * @version 2018-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/retiredadre/retiredCadre")
public class RetiredCadreController extends BaseController {

	@Autowired
	private RetiredCadreService retiredCadreService;
	
	@ModelAttribute
	public RetiredCadre get(@RequestParam(required=false) String id) {
		RetiredCadre entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = retiredCadreService.get(id);
		}
		if (entity == null){
			entity = new RetiredCadre();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(RetiredCadre retiredCadre, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RetiredCadre> page = retiredCadreService.findPage(new Page<RetiredCadre>(request, response), retiredCadre); 
		model.addAttribute("page", page);
		return "business/retiredadre/retiredCadreList";
	}

	@RequestMapping(value = "form")
	public String form(RetiredCadre retiredCadre, Model model) {
		model.addAttribute("retiredCadre", retiredCadre);
		return "business/retiredadre/retiredCadreForm";
	}

	@RequestMapping(value = "look")
	public String look(RetiredCadre retiredCadre, Model model) {
		model.addAttribute("retiredCadre", retiredCadre);
		return "business/retiredadre/retiredCadreLook";
	}
	
	@RequestMapping(value = "save")
	public String save(RetiredCadre retiredCadre, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, retiredCadre)){
			return form(retiredCadre, model);
		}
		retiredCadre.setStatusTem(retiredCadre.getStatus());
		retiredCadreService.save(retiredCadre);
		addMessage(redirectAttributes, "保存退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(RetiredCadre retiredCadre, String idStr, RedirectAttributes redirectAttributes) {
		retiredCadreService.delete(retiredCadre, idStr);
		addMessage(redirectAttributes, "删除退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}
	/**
	 * 转死亡-更改状态
	 * @param retiredCadre
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "updateStatus")
	public String updateStatus(RetiredCadre retiredCadre, RedirectAttributes redirectAttributes) {
		retiredCadre.setStatus("2");
		retiredCadreService.save(retiredCadre);
		addMessage(redirectAttributes, "更改退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}
	/**
	 * 撤销人员信息状态，回退到更改前的状态
	 * @param retiredCadre
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "revokeStatus")
	public String revokeStatus(RetiredCadre retiredCadre, RedirectAttributes redirectAttributes) {
		retiredCadre.setStatus(retiredCadre.getStatusTem());
		retiredCadreService.save(retiredCadre);
		addMessage(redirectAttributes, "更改退休干部信息操作成功");
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
	}
	/**
	 * 下载转档案局数据
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "exportArchivesInfo")
    public String importFileTemplate(RetiredCadre retiredCadre, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "退休、死亡干部信息.xlsx";
    		List<RetiredCadre> list = retiredCadreService.findList(retiredCadre); 
    		for(int i = 0; i < list.size(); i++){
    			list.get(i).setXh((i+1)+"");
    		}
    		list.add(new RetiredCadre());
    		new ExportExcel("退休、死亡干部-转档案人员数据", RetiredCadre.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/list?repage";
    }
    
    /**
   	 * 干部管理模板下载
   	 * @param response
   	 * @param redirectAttributes
   	 * @return
   	 */
      @RequestMapping(value = "moduleDown")
      public void moduleDown(HttpServletResponse response) {
   	   try{
   		    File file = new File(this.getClass().getResource("/templet/retiredCadreModel.xls").getPath());
   			FileUtils.download("干部信息模板.xls", file, response);
   		}catch(Exception e){
   			e.printStackTrace();
   		}
      }
    
    /**
	 * 导入离退休干部人员数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RetiredCadre retiredCadre, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/?repage";
		}
		try {
			String originalFilename = file.getOriginalFilename();
			if(StringUtils.isBlank(originalFilename)){
				throw new Exception("导入文档为空！");
			}
			if(!originalFilename.contains(".xlsx") && !originalFilename.contains(".xls")){
				throw new Exception("请根据下载的模板进行数据的录入并上传!");
			}
			
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			
			List<RetiredCadre> list = ei.getDataList(RetiredCadre.class);
			try{
				if(list.size() > 0){
					for(RetiredCadre info : list){
						if(StringUtils.isBlank(info.getNoteNo())){
							//addMessage(redirectAttributes, "本号为必填数据；不能为空 ");
							continue;
						}
						if(StringUtils.isBlank(info.getName())){
							//addMessage(redirectAttributes, "姓名为必填数据；不能为空");
							continue;
						}
						if(StringUtils.isBlank(info.getArchivesNo())){
							//addMessage(redirectAttributes, "档案号为必填数据；不能为空");
							continue;
						}
						if(StringUtils.isBlank(info.getUnitsDuties())){
							//addMessage(redirectAttributes, "工作单位及职务为必填数据；不能为空");
							continue;
						}
						retiredCadreService.save(info);
					}
				}
			}catch (Exception ex) {
				failureMsg.append("导入失败："+ex.getMessage());
			}
			addMessage(redirectAttributes, "已成功导入 ");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/retiredadre/retiredCadre/list?repage";
    }
   
}