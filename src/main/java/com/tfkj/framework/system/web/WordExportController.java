/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.framework.core.web.BaseController;

import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * word生成Controller
 * 
 * @author tianzhen
 */
@Controller
@RequestMapping(value = "${adminPath}/order")
public class WordExportController extends BaseController {

	
	@RequestMapping(value = "createAuditBill")
	public String createAuditBill(TblRollOut tblRollOut, String num, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		
		Configuration configuration = new Configuration();  
		configuration.setDefaultEncoding("utf-8");
        Template freemarkerTemplate  = null;  
        PrintWriter wt = null;  
        try {   
            Map<String,Object> dataMap=new HashMap<String,Object>();  
            /** 放置数据 **/  
            this.makeAuditOrderData(tblRollOut, num, dataMap);   
            //FTL文件所存在的位置  
            configuration.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/templet").getPath()));  
            freemarkerTemplate  = configuration.getTemplate("AuditBill.ftl"); ; //文件名    
            //配置 Response 参数  
            response.setContentType("application/msword; charset=UTF-8");  
            response.setHeader("Content-Disposition",  "Attachment;filename= " 
            			+ new String("市管干部档案转出审批单.doc".getBytes("gb2312"), "ISO8859_1"));  
            wt = response.getWriter();  
            freemarkerTemplate .process(dataMap, wt);  
        } catch (IOException e) {    
            e.printStackTrace();    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {  
            if(wt!=null){  
                wt.flush();  
                wt.close();   
            }  
        }  
        return null;  
	}
	
	 public void makeAuditOrderData(TblRollOut tblRollOut, String num, Map<String, Object> dataMap) {  
		 TblRollOutPersons per = tblRollOut.getTblRollOutPersonsList().get(Integer.parseInt(num));  
		 if(per == null){  
             return;  
         }  
         if(per.getName() != null){  
              dataMap.put("name", per.getName());
         }
         if(per.getFilesNo() != null){  
             dataMap.put("filesNo", per.getFilesNo());
         }
         if(per.getDuty() != null){  
             dataMap.put("duty", per.getDuty());
         }
         if(per.getReasonContent() != null){  
             dataMap.put("reasonContent", per.getReasonContent());
         }else{
        	 dataMap.put("reasonContent", "无");
         }
         if(per.getRelatedAttachment() != null){  
             dataMap.put("relatedAttachment", per.getRelatedAttachment());
         }else{
        	 dataMap.put("relatedAttachment", "无");
         }
         if(tblRollOut.getRollOutTime() != null){
        	 Calendar now = Calendar.getInstance();
        	 now.setTime(tblRollOut.getRollOutTime());
        	 dataMap.put("year", now.get(Calendar.YEAR)+"");
        	 dataMap.put("month", (now.get(Calendar.MONTH) + 1) + "");
        	 dataMap.put("day", now.get(Calendar.DAY_OF_MONTH)+"");
         }
         if(tblRollOut.getRecipient() != null){  
             dataMap.put("recipient", tblRollOut.getRecipient());
         }
         if(tblRollOut.getSaveUnit() != null){  
             dataMap.put("saveUnit", tblRollOut.getSaveUnitName());
         }
         if(per.getRemarks() != null){  
             dataMap.put("remarks", per.getRemarks());
         }else{
        	 dataMap.put("remarks", "无");
         }
	 }  
}
