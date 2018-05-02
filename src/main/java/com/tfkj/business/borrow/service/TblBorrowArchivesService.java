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
import com.tfkj.business.borrow.dao.TblGiveBackDao;
import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblBorrowExport;
import com.tfkj.business.borrow.entity.TblBorrowPerson;
import com.tfkj.business.borrow.entity.TblBorrowTarget;
import com.tfkj.business.borrow.entity.TblGiveBack;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.system.dao.DictDao;

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
	@Autowired
	private TblGiveBackDao tblGiveBackDao;
	@Autowired
	private DictDao dictDao;
	
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
			//被借阅人的名称
			String tarStr = "";
			//借阅人的名称
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
		//新增、更改或者删除被借阅人的数据
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
		//新增、更改或者删除借阅人的数据
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
		
		//新增归还记录
		TblGiveBack tblGiveBack = new TblGiveBack();
		tblGiveBack.setMainId(tblBorrowArchives.getId());
		List<TblGiveBack> giveBackList = tblGiveBackDao.findList(tblGiveBack);
		if(giveBackList.size() == 0){
			//tblGiveBack.setMainId(tblBorrowArchives.getId());
			tblGiveBack.setStatus("1");
			tblGiveBack.preInsert();
			tblGiveBackDao.insert(tblGiveBack);
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
	
	@Transactional(readOnly = false)
	public void censorship(TblBorrowArchives tblBorrowArchives, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblBorrowArchives = super.get(idArray[i]);
				tblBorrowArchives.setStatus("2");
				super.save(tblBorrowArchives);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void auditData(TblBorrowArchives tblBorrowArchives, String idStr, String status) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblBorrowArchives = super.get(idArray[i]);
				tblBorrowArchives.setStatus(status);
				super.save(tblBorrowArchives);
			}
		}
	}
	
	public Page<TblBorrowArchives> queryCountPage(Page<TblBorrowArchives> page, TblBorrowArchives tblBorrowArchives) {
		
		//查询一页的数据
		tblBorrowArchives.setPage(page);
		List<TblBorrowArchives> archList  = tblBorrowArchivesDao.queryCountList(tblBorrowArchives);
		for(int i = 0; i < archList.size(); i++){
			//设置序号
			archList.get(i).setXh((page.getPageNo()-1)*30+(archList.size()-i)+"");
			//组织查阅对象数据
			String perStr = "";
			TblBorrowTarget tar = new TblBorrowTarget();
			tar.setMainId(archList.get(i).getId());
			List<TblBorrowTarget> tarList = tblBorrowTargetDao.findList(tar);
			archList.get(i).setBorrowTarNum(tarList.size()+"");
			//如果为空时，手动添加一个，为页面展示的rowspan用
			if(tarList.size() == 0){
				tarList.add(new TblBorrowTarget());
			}
			archList.get(i).setTblBorrowTargetList(tarList);
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
		}
		//总被查阅人数
		int tarNum = tblBorrowTargetDao.querySum(tblBorrowArchives);
		//总查阅人数
		int perNum = tblBorrowPersonDao.querySum(tblBorrowArchives);
		//第一行数据
		TblBorrowArchives info = new TblBorrowArchives();
		info.setXh("合计");
		info.setBorrowTarNum(String.valueOf(tarNum));
		info.setBorrowPerNum(String.valueOf(perNum));
		//手动添加一个，为页面展示的rowspan用
		List<TblBorrowTarget> tarTemList = new ArrayList<>();
		tarTemList.add(new TblBorrowTarget());
		info.setTblBorrowTargetList(tarTemList);
		archList.add(info);
		
		Collections.reverse(archList);
		page.setList(archList);
		return page;
	}
	
	public List<TblBorrowExport> queryCountList(TblBorrowArchives tblBorrowArchives) {
		
		List<TblBorrowExport> bExportList = tblBorrowArchivesDao.queryTarCountList(tblBorrowArchives);
		for(int i = 0; i < bExportList.size(); i++){
			bExportList.get(i).setXh((bExportList.size()-i)+"");
			if(StringUtils.isNotBlank(bExportList.get(i).getName())){
				bExportList.get(i).setBorrowTarNum("1");
			}else{
				bExportList.get(i).setBorrowTarNum("0");
			}
			//查阅人
			String perStr = "";
			List<TblBorrowPerson> perList = tblBorrowPersonDao.queryPerInfoByTar(bExportList.get(i));
			for(TblBorrowPerson per : perList){
				if(perStr == ""){
					perStr = per.getName();
				}else{
					perStr += "," + per.getName();
				}
			}
			bExportList.get(i).setStatus(dictDao.getLabelByValue(bExportList.get(i).getStatus(), "audit_status"));
			bExportList.get(i).setBorrowPerNum(perList.size()+"");
			bExportList.get(i).setPerStr(perStr);
		}
		//总被查阅人数
		int tarNum = tblBorrowTargetDao.querySum(tblBorrowArchives);
		//总查阅人数
		int perNum = tblBorrowPersonDao.querySum(tblBorrowArchives);
		//第一行数据
		TblBorrowExport info = new TblBorrowExport();
		info.setXh("合计");
		info.setBorrowTarNum(String.valueOf(tarNum));
		info.setBorrowPerNum(String.valueOf(perNum));
		bExportList.add(info);
		
		Collections.reverse(bExportList);
		return bExportList;
	}
}