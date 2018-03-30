/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.borrow.dao.TblBorrowArchivesDao;
import com.tfkj.business.borrow.dao.TblBorrowPersonDao;
import com.tfkj.business.borrow.dao.TblBorrowTargetDao;
import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblBorrowPerson;
import com.tfkj.business.borrow.entity.TblBorrowTarget;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 借阅管理Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblBorrowArchivesService extends CrudService<TblBorrowArchivesDao, TblBorrowArchives> {

	@Autowired
	private TblBorrowPersonDao tblBorrowPersonDao;
	@Autowired
	private TblBorrowTargetDao tblBorrowTargetDao;
	
	public TblBorrowArchives get(String id) {
		TblBorrowArchives tblBorrowArchives = super.get(id);
		
		TblBorrowTarget tar = new TblBorrowTarget();
		tar.setMainId(id);
		tblBorrowArchives.setTblBorrowTargetList(tblBorrowTargetDao.findList(tar));
		
		TblBorrowPerson per = new TblBorrowPerson();
		per.setMainId(id);
		tblBorrowArchives.setTblBorrowPersonList(tblBorrowPersonDao.findList(per));
		
		return tblBorrowArchives;
	}
	
	public List<TblBorrowArchives> findList(TblBorrowArchives tblBorrowArchives) {
		return super.findList(tblBorrowArchives);
	}
	
	public Page<TblBorrowArchives> findPage(Page<TblBorrowArchives> page, TblBorrowArchives tblBorrowArchives) {
		tblBorrowArchives.setPage(page);
		List<TblBorrowArchives> archList = dao.findList(tblBorrowArchives);
		
		for(TblBorrowArchives info : archList){
			String tarStr = "";
			String perStr = "";
			
			TblBorrowTarget tar = new TblBorrowTarget();
			tar.setMainId(info.getId());
			List<TblBorrowTarget> tarList = tblBorrowTargetDao.findList(tar);
			for(TblBorrowTarget btar : tarList){
				if(tarStr == ""){
					tarStr = btar.getName();
				}else{
					tarStr += "," + btar.getName();
				}
			}
			info.setTarStr(tarStr);
			
			TblBorrowPerson per = new TblBorrowPerson();
			per.setMainId(info.getId());
			List<TblBorrowPerson> perList = tblBorrowPersonDao.findList(per);
			for(TblBorrowPerson bper : perList){
				if(perStr == ""){
					perStr = bper.getName();
				}else{
					perStr += "," + bper.getName();
				}
			}
			info.setPerStr(perStr);
		}
		
		page.setList(archList);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TblBorrowArchives tblBorrowArchives) {
		super.save(tblBorrowArchives);
		for (TblBorrowPerson tblBorrowPerson : tblBorrowArchives.getTblBorrowPersonList()){
			if (tblBorrowPerson.getId() == null){
				continue;
			}
			if (TblBorrowPerson.DEL_FLAG_NORMAL.equals(tblBorrowPerson.getDelFlag())){
				if (StringUtils.isBlank(tblBorrowPerson.getId())){
					tblBorrowPerson.setMainId(tblBorrowArchives.getId());
					tblBorrowPerson.preInsert();
					tblBorrowPersonDao.insert(tblBorrowPerson);
				}else{
					tblBorrowPerson.preUpdate();
					tblBorrowPersonDao.update(tblBorrowPerson);
				}
			}else{
				tblBorrowPersonDao.delete(tblBorrowPerson);
			}
		}
		for (TblBorrowTarget tblBorrowTarget : tblBorrowArchives.getTblBorrowTargetList()){
			if (tblBorrowTarget.getId() == null){
				continue;
			}
			if (TblBorrowTarget.DEL_FLAG_NORMAL.equals(tblBorrowTarget.getDelFlag())){
				if (StringUtils.isBlank(tblBorrowTarget.getId())){
					tblBorrowTarget.setMainId(tblBorrowArchives.getId());
					tblBorrowTarget.preInsert();
					tblBorrowTargetDao.insert(tblBorrowTarget);
				}else{
					tblBorrowTarget.preUpdate();
					tblBorrowTargetDao.update(tblBorrowTarget);
				}
			}else{
				tblBorrowTargetDao.delete(tblBorrowTarget);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblBorrowArchives tblBorrowArchives, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblBorrowArchives.setId(idArray[i]);
				super.delete(tblBorrowArchives);
				
				TblBorrowTarget tar = new TblBorrowTarget();
				tar.setMainId(idArray[i]);
				tblBorrowTargetDao.delete(tar);
				
				TblBorrowPerson per = new TblBorrowPerson();
				per.setMainId(idArray[i]);
				tblBorrowPersonDao.delete(per);
			}
		}
	}
	
}