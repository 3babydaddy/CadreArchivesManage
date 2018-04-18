/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.business.rollin.service.TblRollInService;
import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.business.rollout.service.TblRollOutService;
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

	@Autowired
	private TblRollOutService tblRollOutService;
	@Autowired
	private TblRollInService tblRollInService;
	
	/**
	 * 生成审批单
	 * @param tblRollOut
	 * @param num
	 * @param model
	 * @param redirectAttributes
	 * @param response
	 * @return
	 */
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
	
	/**
	 * 生产转递单
	 * @param rollOutId
	 * @param model
	 * @param redirectAttributes
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "createRelayBill")
	public String createRelayBill(String rollOutId, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		//得到当前的转出数据
		TblRollOut tblRollOut = tblRollOutService.get(rollOutId);
		
		Configuration configuration = new Configuration();  
		configuration.setDefaultEncoding("utf-8");
        Template freemarkerTemplate  = null;  
        PrintWriter wt = null;  
        try {   
            Map<String,Object> dataMap=new HashMap<String,Object>();  
            /** 放置数据 **/  
            this.makeRelayOrderData(tblRollOut, dataMap);   
            //FTL文件所存在的位置  
            configuration.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/templet").getPath()));  
            freemarkerTemplate  = configuration.getTemplate("RelayBill.ftl"); ; //文件名    
            //配置 Response 参数  
            response.setContentType("application/msword; charset=UTF-8");  
            response.setHeader("Content-Disposition",  "Attachment;filename= " 
            			+ new String("干部人事档案材料转递单.doc".getBytes("gb2312"), "ISO8859_1"));  
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
	
	/**
	 * 生产回执单
	 * @param rollInId
	 * @param model
	 * @param redirectAttributes
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "createReceiptBill")
	public String createReceiptBill(String rollInId, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		//得到当前的转出数据
		TblRollIn tblRollIn = tblRollInService.get(rollInId);
		
		Configuration configuration = new Configuration();  
		configuration.setDefaultEncoding("utf-8");
        Template freemarkerTemplate  = null;  
        PrintWriter wt = null;  
        try {   
            Map<String,Object> dataMap=new HashMap<String,Object>();  
            /** 放置数据 **/  
            this.makeReceiptOrderData(tblRollIn, dataMap);   
            //FTL文件所存在的位置  
            configuration.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/templet").getPath()));  
            freemarkerTemplate  = configuration.getTemplate("ReceiptBill.ftl"); ; //文件名    
            //配置 Response 参数  
            response.setContentType("application/msword; charset=UTF-8");  
            response.setHeader("Content-Disposition",  "Attachment;filename= " 
            			+ new String("干部人事档案材料回执单.doc".getBytes("gb2312"), "ISO8859_1"));  
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
        	 //材料数
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
	 
	 public void makeRelayOrderData(TblRollOut tblRollOut, Map<String, Object> dataMap) {  
		 
		 if(tblRollOut.getRollOutTime() != null){
        	 Calendar now = Calendar.getInstance();
        	 now.setTime(tblRollOut.getRollOutTime());
        	 dataMap.put("year", now.get(Calendar.YEAR)+"");
        	 dataMap.put("month", (now.get(Calendar.MONTH) + 1) + "");
        	 dataMap.put("day", now.get(Calendar.DAY_OF_MONTH)+"");
         }
		 List<TblRollOutPersons> perList = tblRollOut.getTblRollOutPersonsList();
		 if(perList != null && perList.size() > 0){
        	 dataMap.put("perName", perList.get(0).getName());
        	 dataMap.put("perNum", perList.size());
        	 //正本卷数
        	 long originalNo = 0;
        	 //副本卷数
        	 long viceNo = 0;
        	 //材料卷数
        	 long filesNo = 0;
        	 for(TblRollOutPersons info : perList){
        		 originalNo += info.getOriginalNo();
        		 viceNo += info.getViceNo();
        		 filesNo += info.getFilesNo();
        	 }
        	 dataMap.put("originalNo", originalNo);
        	 dataMap.put("viceNo", viceNo);
        	 dataMap.put("filesNo", filesNo);
        	 if(perList.size() < 3){
        		 while(perList.size() < 3){
        			 perList.add(new TblRollOutPersons());
        		 }
        	 }
        	 dataMap.put("perList", perList);
         }
		 if(tblRollOut.getBeforeUnitName() != null){
			 dataMap.put("beforeUnitName", tblRollOut.getBeforeUnitName());
		 }
		 if(tblRollOut.getSaveUnitName() != null){
			 dataMap.put("saveUnitName", tblRollOut.getSaveUnitName());
		 }
		 if(tblRollOut.getRecipient() != null){
			 dataMap.put("recipient", "尊敬的"+tblRollOut.getRecipient());
		 }
		 if(tblRollOut.getCharacter() != null){
			 dataMap.put("character", tblRollOut.getCharacter()+"");
		 }
		 if(tblRollOut.getNumber() != null){
			 dataMap.put("number", tblRollOut.getNumber()+"");
		 }
	 }
	 
 public void makeReceiptOrderData(TblRollIn tblRollIn, Map<String, Object> dataMap) {  
		 
		 if(tblRollIn.getRollInTime() != null){
        	 Calendar now = Calendar.getInstance();
        	 now.setTime(tblRollIn.getRollInTime());
        	 dataMap.put("year", now.get(Calendar.YEAR)+"");
        	 dataMap.put("month", (now.get(Calendar.MONTH) + 1) + "");
        	 dataMap.put("day", now.get(Calendar.DAY_OF_MONTH)+"");
         }
		 List<TblRollInPersons> perList = tblRollIn.getTblRollInPersonsList();
		 if(perList != null && perList.size() > 0){
        	 dataMap.put("perName", perList.get(0).getName());
        	 dataMap.put("perNum", perList.size());
        	 //正本卷数
        	 long originalNo = 0;
        	 //副本卷数
        	 long viceNo = 0;
        	 //材料卷数
        	 long filesNo = 0;
        	 for(TblRollInPersons info : perList){
        		 originalNo += info.getOriginalNo();
        		 viceNo += info.getViceNo();
        		 filesNo += info.getFilesNo();
        	 }
        	 dataMap.put("originalNo", originalNo);
        	 dataMap.put("viceNo", viceNo);
        	 dataMap.put("filesNo", filesNo);
         }
		 
		 if(tblRollIn.getRecipient() != null){
			 dataMap.put("operator", "尊敬的"+tblRollIn.getRecipient());
		 }
		 if(tblRollIn.getCharacter() != null){
			 dataMap.put("character", tblRollIn.getCharacter()+"");
		 }
		 if(tblRollIn.getNumber() != null){
			 dataMap.put("number", tblRollIn.getNumber()+"");
		 }
	 }
}
