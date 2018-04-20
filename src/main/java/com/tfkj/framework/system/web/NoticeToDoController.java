/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.service.TblBorrowArchivesService;
import com.tfkj.business.consult.entity.TblConsultArchives;
import com.tfkj.business.consult.service.TblConsultArchivesService;
import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.rollin.service.TblRollInService;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.business.scattereds.service.TblScatteredFilesService;
import com.tfkj.framework.core.web.BaseController;

/**
 * 字典分类/类型Controller
 * 
 * @author sunxuelong
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/noticeToDo")
public class NoticeToDoController extends BaseController {

	@Autowired
	private TblConsultArchivesService tblConsultArchivesService;
	@Autowired
	private TblBorrowArchivesService tblBorrowArchivesService;
	@Autowired
	private TblRollInService tblRollInService;
	@Autowired
	private TblScatteredFilesService tblScatteredFilesService;
	
	/**
	 * 查询待办的数据
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryNoticeData")
	@ResponseBody
	public List<Map<String, Object>> queryNoticeData(HttpServletRequest request, HttpServletResponse response) {
		
		List<Map<String, Object>> mapList = new ArrayList<>();
		//查阅数据
		TblConsultArchives tblConsultArchives = new TblConsultArchives();
		tblConsultArchives.setStatus("1");
		List<TblConsultArchives> conList = tblConsultArchivesService.findList(tblConsultArchives);
		if(conList.size() > 0){
			Map<String, Object> map1 = new HashMap<>();
			map1.put("flag", "consultArchives");
			map1.put("url", "/consult/tblConsultArchives/list");
			map1.put("msg", "查阅管理有待审核的数据,请尽快处理");
			mapList.add(map1);
		}
		//借阅数据
		TblBorrowArchives tblBorrowArchives = new TblBorrowArchives();
		tblBorrowArchives.setStatus("1");
		List<TblBorrowArchives> borList = tblBorrowArchivesService.findList(tblBorrowArchives);
		if(borList.size() > 0){
			Map<String, Object> map2 = new HashMap<>();
			map2.put("flag", "borrowArchives");
			map2.put("url", "/borrow/tblBorrowArchives/list");
			map2.put("msg", "借阅管理有待审核的数据,请尽快处理");
			mapList.add(map2);
		}
		//转入数据
		TblRollIn tblRollIn = new TblRollIn();
		tblRollIn.setStatus("1");
		List<TblRollIn> rollInList = tblRollInService.findList(tblRollIn);
		if(rollInList.size() > 0){
			Map<String, Object> map3 = new HashMap<>();
			map3.put("flag", "rollIn");
			map3.put("url", "/rollin/tblRollIn/list");
			map3.put("msg", "转入管理有待审核的数据,请尽快处理");
			mapList.add(map3);
		}
		//零散材料数据
		TblScatteredFiles tblScatteredFiles = new TblScatteredFiles();
		tblScatteredFiles.setStatus("1");
		List<TblScatteredFiles> scaList = tblScatteredFilesService.findList(tblScatteredFiles);
		if(scaList.size() > 0){
			Map<String, Object> map4 = new HashMap<>();
			map4.put("flag", "scatteredFiles");
			map4.put("url", "/scattereds/tblScatteredFiles/list");
			map4.put("msg", "零散材料管理有待审核的数据,请尽快处理");
			mapList.add(map4);
		}
		
		return mapList;
	}
}
