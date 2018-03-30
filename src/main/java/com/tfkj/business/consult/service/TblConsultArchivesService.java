/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.service;

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
			String tarStr = "";
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
	
}