/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.consult.dao.TblCheckPersonDao;
import com.tfkj.business.consult.dao.TblCheckedTargetDao;
import com.tfkj.business.consult.dao.TblConsultArchivesDao;
import com.tfkj.business.consult.entity.TblCheckPerson;
import com.tfkj.business.consult.entity.TblCheckedTarget;
import com.tfkj.business.consult.entity.TblConsultArchives;
import com.tfkj.business.consult.entity.TblConsultExport;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 查阅档案Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblConsultArchivesService extends CrudService<TblConsultArchivesDao, TblConsultArchives> {

	@Autowired
	private TblConsultArchivesDao tblConsultArchivesDao;
	@Autowired
	private TblCheckedTargetDao tblCheckedTargetDao;
	@Autowired
	private TblCheckPersonDao tblCheckPersonDao;
	
	public TblConsultArchives get(String id) {
		TblConsultArchives tblConsultArchives = super.get(id);
		TblCheckedTarget tar = new TblCheckedTarget();
		tar.setMainId(id);
		tblConsultArchives.setTblCheckedTargetList(tblCheckedTargetDao.findList(tar));
		TblCheckPerson per = new TblCheckPerson();
		per.setMainId(id);
		tblConsultArchives.setTblCheckPersonList(tblCheckPersonDao.findList(per));
		return tblConsultArchives;
	}
	
	public List<TblConsultArchives> findList(TblConsultArchives tblConsultArchives) {
		return super.findList(tblConsultArchives);
	}
	
	public Page<TblConsultArchives> findPage(Page<TblConsultArchives> page, TblConsultArchives tblConsultArchives) {
		tblConsultArchives.setPage(page);
		List<TblConsultArchives> archList  = dao.findList(tblConsultArchives);
		for(TblConsultArchives info : archList){
			//被查阅人的名称
			String tarStr = "";
			//查阅人的名称
			String perStr = "";
			
			TblCheckedTarget tar = new TblCheckedTarget();
			tar.setMainId(info.getId());
			List<TblCheckedTarget> tarList = tblCheckedTargetDao.findList(tar);
			for(TblCheckedTarget ctar : tarList){
				if(tarStr == ""){
					tarStr = ctar.getName();
				}else{
					tarStr += "," + ctar.getName();
				}
			}
			info.setTarStr(tarStr);
			
			TblCheckPerson per = new TblCheckPerson();
			per.setMainId(info.getId());
			List<TblCheckPerson> perList = tblCheckPersonDao.findList(per);
			for(TblCheckPerson cper : perList){
				if(perStr == ""){
					perStr = cper.getName();
				}else{
					perStr += "," + cper.getName();
				}
			}
			info.setPerStr(perStr);
			
		}
		page.setList(archList);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TblConsultArchives tblConsultArchives) {
		super.save(tblConsultArchives);
		//新增、更改或者删除被查借阅的数据
		for (TblCheckedTarget tblCheckedTarget : tblConsultArchives.getTblCheckedTargetList()){
			if (tblCheckedTarget.getId() == null){
				continue;
			}
			if (TblCheckedTarget.DEL_FLAG_NORMAL.equals(tblCheckedTarget.getDelFlag())){
				if (StringUtils.isBlank(tblCheckedTarget.getId())){
					tblCheckedTarget.setMainId(tblConsultArchives.getId());
					tblCheckedTarget.preInsert();
					tblCheckedTargetDao.insert(tblCheckedTarget);
				}else{
					tblCheckedTarget.preUpdate();
					tblCheckedTargetDao.update(tblCheckedTarget);
				}
			}else{
				tblCheckedTargetDao.delete(tblCheckedTarget);
			}
		}
		//新增、更改或者删除查阅人的数据
		for (TblCheckPerson tblCheckPerson : tblConsultArchives.getTblCheckPersonList()){
			if (tblCheckPerson.getId() == null){
				continue;
			}
			if (TblCheckPerson.DEL_FLAG_NORMAL.equals(tblCheckPerson.getDelFlag())){
				if (StringUtils.isBlank(tblCheckPerson.getId())){
					tblCheckPerson.setMainId(tblConsultArchives.getId());
					tblCheckPerson.preInsert();
					tblCheckPersonDao.insert(tblCheckPerson);
				}else{
					tblCheckPerson.preUpdate();
					tblCheckPersonDao.update(tblCheckPerson);
				}
			}else{
				tblCheckPersonDao.delete(tblCheckPerson);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblConsultArchives tblConsultArchives, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			
			for(int i = 0; i < idArray.length; i++){
				tblConsultArchives.setId(idArray[i]);
				super.delete(tblConsultArchives);
				
				TblCheckedTarget tar = new TblCheckedTarget();
				tar.setMainId(idArray[i]);
				tblCheckedTargetDao.delete(tar);
				
				TblCheckPerson per = new TblCheckPerson();
				per.setMainId(idArray[i]);
				tblCheckPersonDao.delete(per);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void censorship(TblConsultArchives tblConsultArchives, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblConsultArchives = super.get(idArray[i]);
				tblConsultArchives.setStatus("2");
				super.save(tblConsultArchives);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void auditData(TblConsultArchives tblConsultArchives, String idStr, String status) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblConsultArchives = super.get(idArray[i]);
				tblConsultArchives.setStatus(status);
				super.save(tblConsultArchives);
			}
		}
	}
	
	public Page<TblConsultArchives> queryCountPage(Page<TblConsultArchives> page, TblConsultArchives tblConsultArchives) {
		
		//查询一页的数据
		tblConsultArchives.setPage(page);
		List<TblConsultArchives> archList  = tblConsultArchivesDao.queryCountList(tblConsultArchives);
		for(int i = 0; i < archList.size(); i++){
			//设置序号
			archList.get(i).setXh((page.getPageNo()-1)*30 + (archList.size()-i)+"");
			//组织查阅对象数据
			String perStr = "";
			TblCheckedTarget tar = new TblCheckedTarget();
			tar.setMainId(archList.get(i).getId());
			List<TblCheckedTarget> tarList = tblCheckedTargetDao.findList(tar);
			archList.get(i).setConsultTarNum(tarList.size()+"");
			//如果为空时，手动添加一个，为页面展示的rowspan用
			if(tarList.size() == 0){
				tarList.add(new TblCheckedTarget());
			}
			archList.get(i).setTblCheckedTargetList(tarList);
			//组织查阅人数据
			TblCheckPerson per = new TblCheckPerson();
			per.setMainId(archList.get(i).getId());
			List<TblCheckPerson> perList = tblCheckPersonDao.findList(per);
			for(TblCheckPerson cper : perList){
				if(perStr == ""){
					perStr = cper.getName();
				}else{
					perStr += "," + cper.getName();
				}
			}
			archList.get(i).setPerStr(perStr);
			archList.get(i).setConsultPerNum(perList.size()+"");
		}
		//总被查阅人数
		int tarNum = tblCheckedTargetDao.querySum();
		//总查阅人数
		int perNum = tblCheckPersonDao.querySum();
		//第一行数据
		TblConsultArchives info = new TblConsultArchives();
		info.setXh("合计");
		info.setConsultTarNum(tarNum+"");
		info.setConsultPerNum(perNum+"");
		//手动添加一个，为页面展示的rowspan用
		List<TblCheckedTarget> tarTemList = new ArrayList<>();
		tarTemList.add(new TblCheckedTarget());
		info.setTblCheckedTargetList(tarTemList);
		archList.add(info);
		
		Collections.reverse(archList);
		page.setList(archList);
		return page;
	}
	
	public List<TblConsultExport> queryCountList(TblConsultArchives tblConsultArchives) {
		
		List<TblConsultExport> conExportList  = tblConsultArchivesDao.queryTarCountList(tblConsultArchives);
		for(int i = 0; i < conExportList.size(); i++){
			conExportList.get(i).setXh((conExportList.size()-i)+"");
			if(StringUtils.isNotBlank(conExportList.get(i).getName())){
				conExportList.get(i).setConsultTarNum("1");
			}else{
				conExportList.get(i).setConsultTarNum("0");
			}
			//查阅人的名称
			String perStr = "";
			List<TblCheckPerson> perList = tblCheckPersonDao.queryPerInfoByTar(conExportList.get(i));
			for(TblCheckPerson per : perList){
				if(perStr == ""){
					perStr = per.getName();
				}else{
					perStr += "," + per.getName();
				}
			}
			conExportList.get(i).setConsultPerNum(perList.size()+"");
			conExportList.get(i).setPerStr(perStr);
		}
		//总被查阅人数
		int tarNum = tblCheckedTargetDao.querySum();
		//总查阅人数
		int perNum = tblCheckPersonDao.querySum();
		//第一行数据
		TblConsultExport info = new TblConsultExport();
		info.setXh("合计");
		info.setConsultTarNum(tarNum+"");
		info.setConsultPerNum(perNum+"");
		conExportList.add(info);
		
		Collections.reverse(conExportList);
		return conExportList;
	}
	
}