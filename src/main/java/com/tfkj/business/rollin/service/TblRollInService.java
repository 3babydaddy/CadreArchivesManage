/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.rollin.dao.TblRollInDao;
import com.tfkj.business.rollin.dao.TblRollInPersonsDao;
import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.business.rollin.entity.TblRollInPersonsExport;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 转入管理人员Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblRollInService extends CrudService<TblRollInDao, TblRollIn> {

	@Autowired
	private TblRollInPersonsDao tblRollInPersonsDao;
	
	public TblRollIn get(String id) {
		TblRollIn tblRollIn = super.get(id);
		
		TblRollInPersons per = new TblRollInPersons();
		per.setMainId(id);
		tblRollIn.setTblRollInPersonsList(tblRollInPersonsDao.findList(per));
		return tblRollIn;
	}
	
	public List<TblRollIn> findList(TblRollIn tblRollIn) {
		return super.findList(tblRollIn);
	}
	
	public Page<TblRollIn> findPage(Page<TblRollIn> page, TblRollIn tblRollIn) {
		return super.findPage(page, tblRollIn);
	}
	
	@Transactional(readOnly = false)
	public void save(TblRollIn tblRollIn) {
		super.save(tblRollIn);
		for (TblRollInPersons tblRollInPersons : tblRollIn.getTblRollInPersonsList()){
			if (tblRollInPersons.getId() == null){
				continue;
			}
			if (TblRollInPersons.DEL_FLAG_NORMAL.equals(tblRollInPersons.getDelFlag())){
				if (StringUtils.isBlank(tblRollInPersons.getId())){
					tblRollInPersons.setMainId(tblRollIn.getId());
					tblRollInPersons.preInsert();
					tblRollInPersonsDao.insert(tblRollInPersons);
				}else{
					tblRollInPersons.preUpdate();
					tblRollInPersonsDao.update(tblRollInPersons);
				}
			}else{
				tblRollInPersonsDao.delete(tblRollInPersons);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblRollIn tblRollIn, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblRollIn.setId(idArray[i]);
				super.delete(tblRollIn);
				
				TblRollInPersons per = new TblRollInPersons();
				per.setMainId(idArray[i]);
				tblRollInPersonsDao.delete(per);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void censorship(TblRollIn tblRollIn, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblRollIn = super.get(idArray[i]);
				tblRollIn.setStatus("2");
				super.save(tblRollIn);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void auditData(TblRollIn tblRollIn, String idStr, String status) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblRollIn = super.get(idArray[i]);
				tblRollIn.setStatus(status);
				super.save(tblRollIn);
			}
		}
	}
	
	public Page<TblRollInPersons> findPersonPage(Page<TblRollInPersons> page, TblRollInPersons tblRollInPersons) {
		tblRollInPersons.setPage(page);
		page.setList(tblRollInPersonsDao.findList(tblRollInPersons));
		return page;
	}
	
	public Page<TblRollInPersons> queryCountPage(Page<TblRollInPersons> page, TblRollInPersons tblRollInPersons) {
		tblRollInPersons.setPage(page);
		List<TblRollInPersons> perList = tblRollInPersonsDao.queryCountPage(tblRollInPersons);
		for(int i = 0; i < perList.size(); i++){
			perList.get(i).setXh((page.getPageNo()-1)*30+(perList.size()-i)+"");
		}
		//材料总数
		int filesNo = tblRollInPersonsDao.querySum();
		//第一行数据
		TblRollInPersons info = new TblRollInPersons();
		info.setXh("合计");
		info.setFilesNo((long)filesNo);
		perList.add(info);
		
		Collections.reverse(perList);
		page.setList(perList);
		return page;
	}
	
	public List<TblRollInPersonsExport> queryCountList(TblRollInPersons tblRollInPersons) {
		List<TblRollInPersonsExport> perList = tblRollInPersonsDao.queryCountList(tblRollInPersons);
		//材料总数
		long filesNo = 0;
		for(int i = 0; i < perList.size(); i++){
			perList.get(i).setXh((perList.size()-i)+"");
			filesNo += (perList.get(i).getFilesNo() == null ? 0 : perList.get(i).getFilesNo());
		}
		//第一行数据
		TblRollInPersonsExport info = new TblRollInPersonsExport();
		info.setXh("合计");
		info.setFilesNo(filesNo);
		perList.add(info);
		
		Collections.reverse(perList);
		return perList;
	}
}