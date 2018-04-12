/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.suphandle.web;

import java.util.Calendar;
import java.util.Date;
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
import com.tfkj.business.suphandle.entity.TblSuperviseHandle;
import com.tfkj.business.suphandle.service.TblSuperviseHandleService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.ExportExcel;
import com.tfkj.framework.core.utils.excel.ImportExcel;
import com.tfkj.framework.core.web.BaseController;





/**
 * 督查督办Controller
 * @author tianzhen
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/suphandle/tblSuperviseHandle")
public class TblSuperviseHandleController extends BaseController {

	@Autowired
	private TblSuperviseHandleService tblSuperviseHandleService;
	
	@ModelAttribute
	public TblSuperviseHandle get(@RequestParam(required=false) String id) {
		TblSuperviseHandle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tblSuperviseHandleService.get(id);
		}
		if (entity == null){
			entity = new TblSuperviseHandle();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TblSuperviseHandle tblSuperviseHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TblSuperviseHandle> page = tblSuperviseHandleService.findPage(new Page<TblSuperviseHandle>(request, response), tblSuperviseHandle); 
		model.addAttribute("page", page);
		return "business/suphandle/tblSuperviseHandleList";
	}

	@RequestMapping(value = "form")
	public String form(TblSuperviseHandle tblSuperviseHandle, Model model) {
		model.addAttribute("tblSuperviseHandle", tblSuperviseHandle);
		return "business/suphandle/tblSuperviseHandleForm";
	}

	@RequestMapping(value = "save")
	public String save(TblSuperviseHandle tblSuperviseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tblSuperviseHandle)){
			return form(tblSuperviseHandle, model);
		}
		tblSuperviseHandleService.save(tblSuperviseHandle);
		addMessage(redirectAttributes, "保存督查督办成功");
		return "redirect:"+Global.getAdminPath()+"/suphandle/tblSuperviseHandle/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(TblSuperviseHandle tblSuperviseHandle, String idStr, RedirectAttributes redirectAttributes) {
		tblSuperviseHandleService.delete(tblSuperviseHandle, idStr);
		addMessage(redirectAttributes, "删除督查督办成功");
		return "redirect:"+Global.getAdminPath()+"/suphandle/tblSuperviseHandle/?repage";
	}

	/**
	 * 导入督查督办数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/suphandle/tblSuperviseHandle/list?repage";
		}
		try {
			Calendar calendar = Calendar.getInstance();
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 2, 0);
			
			List<TblSuperviseHandle> list = ei.getDataList(TblSuperviseHandle.class);
			for (TblSuperviseHandle info : list){
				try{
					if(StringUtils.isBlank(info.getName())){
						//addMessage(redirectAttributes, "名字为必填数据；不能为空");
						continue;
					}
					
					info.setStatus("1");
					info.setCountDown((long)90);
					info.setWaringStatus("G");
					calendar.setTime(info.getPresentDutyTime());
					calendar.add(calendar.MONTH, 3); 
					Date time = calendar.getTime(); 
					info.setRaisedTime(time);
					tblSuperviseHandleService.save(info);
					successNum++;
				}catch (Exception ex) {
					failureMsg.append("<br/>姓名 "+info.getName()+" 导入失败："+ex.getMessage());
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/suphandle/tblSuperviseHandle/list?repage";
    }
	
	/**
	 * 下载督查督办数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "提拔干部数据导入模板.xlsx";
    		List<TblSuperviseHandle> list = Lists.newArrayList(); 
    		list.add(new TblSuperviseHandle());
    		new ExportExcel("提拔干部数据", TblSuperviseHandle.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/suphandle/tblSuperviseHandle/list?repage";
    }
    
    @RequestMapping(value = "updateStatus")
	public String updateStatus(TblSuperviseHandle tblSuperviseHandle, RedirectAttributes redirectAttributes) {
    	tblSuperviseHandle.setStatus("2");
    	tblSuperviseHandleService.save(tblSuperviseHandle);
		addMessage(redirectAttributes, "督查督办上交成功");
		return "redirect:"+Global.getAdminPath()+"/suphandle/tblSuperviseHandle/?repage";
	}
}