/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.service;

import java.util.ArrayList;
import java.util.Collections;
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
	private TblBorrowArchivesDao tblBorrowArchivesDao;
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
	
	public Page<TblBorrowArchives> queryCountList(Page<TblBorrowArchives> page, TblBorrowArchives tblBorrowArchives) {
		tblBorrowArchives.setPage(page);
		List<TblBorrowArchives> archList  = tblBorrowArchivesDao.queryCountList(tblBorrowArchives);
		//总被查阅人数
		int tarNum = 0;
		//总查阅人数
		int perNum = 0;
		for(int i = 0; i < archList.size(); i++){
			//设置序号
			archList.get(i).setXh((archList.size()-i)+"");
			//组织查阅对象数据
			String perStr = "";
			TblBorrowTarget tar = new TblBorrowTarget();
			tar.setMainId(archList.get(i).getId());
			List<TblBorrowTarget> tarList = tblBorrowTargetDao.findList(tar);
			//如果为空时，手动添加一个，为页面展示的rowspan用
			if(tarList.size() == 0){
				tarList.add(new TblBorrowTarget());
			}
			archList.get(i).setTblBorrowTargetList(tarList);
			archList.get(i).setBorrowTarNum(tarList.size()+"");
			tarNum += tarList.size();
			//组织查阅人数据
			TblBorrowPerson per = new TblBorrowPerson();
			per.setMainId(archList.get(i).getId());
			List<TblBorrowPerson> perList = tblBorrowPersonDao.findList(per);
			for(TblBorrowPerson cper : perList){
				if(perStr == ""){
					perStr = cper.getName();
				}else{
					perStr += "," + cper.getName();
				}
			}
			archList.get(i).setPerStr(perStr);
			archList.get(i).setBorrowPerNum(perList.size()+"");
			perNum += perList.size();
		}
		TblBorrowArchives info = new TblBorrowArchives();
		info.setXh("总数");
		info.setBorrowTarNum(tarNum+"");
		info.setBorrowPerNum(perNum+"");
		//手动添加一个，为页面展示的rowspan用
		List<TblBorrowTarget> tarTemList = new ArrayList<>();
		tarTemList.add(new TblBorrowTarget());
		info.setTblBorrowTargetList(tarTemList);
		archList.add(info);
		Collections.reverse(archList);
		page.setList(archList);
		return page;
	}
}