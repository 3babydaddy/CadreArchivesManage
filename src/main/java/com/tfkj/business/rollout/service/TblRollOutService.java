/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.rollout.dao.TblRollOutDao;
import com.tfkj.business.rollout.dao.TblRollOutPersonsDao;
import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;


/**
 * 转出管理人员Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblRollOutService extends CrudService<TblRollOutDao, TblRollOut> {

	@Autowired
	private TblRollOutPersonsDao tblRollOutPersonsDao;
	
	public TblRollOut get(String id) {
		TblRollOut tblRollOut = super.get(id);
		
		TblRollOutPersons per = new TblRollOutPersons();
		per.setMainId(id);
		tblRollOut.setTblRollOutPersonsList(tblRollOutPersonsDao.findList(per));
		return tblRollOut;
	}
	
	public List<TblRollOut> findList(TblRollOut tblRollOut) {
		return super.findList(tblRollOut);
	}
	
	public Page<TblRollOut> findPage(Page<TblRollOut> page, TblRollOut tblRollOut) {
		return super.findPage(page, tblRollOut);
	}
	
	@Transactional(readOnly = false)
	public void save(TblRollOut tblRollOut) {
		super.save(tblRollOut);
		for (TblRollOutPersons tblRollOutPersons : tblRollOut.getTblRollOutPersonsList()){
			if (tblRollOutPersons.getId() == null){
				continue;
			}
			if (TblRollOutPersons.DEL_FLAG_NORMAL.equals(tblRollOutPersons.getDelFlag())){
				if (StringUtils.isBlank(tblRollOutPersons.getId())){
					tblRollOutPersons.setMainId(tblRollOut.getId());
					tblRollOutPersons.preInsert();
					tblRollOutPersonsDao.insert(tblRollOutPersons);
				}else{
					tblRollOutPersons.preUpdate();
					tblRollOutPersonsDao.update(tblRollOutPersons);
				}
			}else{
				tblRollOutPersonsDao.delete(tblRollOutPersons);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblRollOut tblRollOut, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblRollOut.setId(idArray[i]);
				super.delete(tblRollOut);
				
				TblRollOutPersons per = new TblRollOutPersons();
				per.setMainId(idArray[i]);
				tblRollOutPersonsDao.delete(per);
			}
		}
	}
	
	public Page<TblRollOutPersons> findPersonPage(Page<TblRollOutPersons> page, TblRollOutPersons tblRollOutPersons) {
		tblRollOutPersons.setPage(page);
		page.setList(tblRollOutPersonsDao.findList(tblRollOutPersons));
		return page;
	}
	
	public Page<TblRollOutPersons> queryCountPage(Page<TblRollOutPersons> page, TblRollOutPersons tblRollOutPersons) {
		tblRollOutPersons.setPage(page);
		List<TblRollOutPersons> perList = tblRollOutPersonsDao.queryCountList(tblRollOutPersons);
		long filesNum = 0;
		for(int i = 0; i < perList.size(); i++){
			perList.get(i).setXh((perList.size()-i)+"");
			filesNum += (perList.get(i).getFilesNo() == null ? 0 : perList.get(i).getFilesNo());
		}
		TblRollOutPersons info = new TblRollOutPersons();
		info.setXh("总数");
		info.setFilesNo(filesNum);
		perList.add(info);
		Collections.reverse(perList);  
		page.setList(perList);
		return page;
	}
	
	public List<TblRollOutPersons> queryCountList(TblRollOutPersons tblRollOutPersons) {
		List<TblRollOutPersons> perList = tblRollOutPersonsDao.queryCountList(tblRollOutPersons);
		long filesNum = 0;
		for(int i = 0; i < perList.size(); i++){
			perList.get(i).setXh((perList.size()-i)+"");
			filesNum += (perList.get(i).getFilesNo() == null ? 0 : perList.get(i).getFilesNo());
		}
		TblRollOutPersons info = new TblRollOutPersons();
		info.setXh("总数");
		info.setFilesNo(filesNum);
		perList.add(info);
		Collections.reverse(perList);  
		return perList;
	}
}