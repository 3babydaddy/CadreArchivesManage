/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.rollout.dao.TblRollOutBackDao;
import com.tfkj.business.rollout.entity.TblRollOut;
import com.tfkj.business.rollout.entity.TblRollOutBack;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;



/**
 * 转出管理回执Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblRollOutBackService extends CrudService<TblRollOutBackDao, TblRollOutBack> {

	@Autowired
	private TblRollOutService tblRollOutService;
	@Autowired
	private TblRollOutBackDao tblRollOutBackDao;
	
	public TblRollOutBack get(String id) {
		return super.get(id);
	}
	
	public List<TblRollOutBack> findList(TblRollOutBack tblRollOutBack) {
		return super.findList(tblRollOutBack);
	}
	
	public Page<TblRollOutBack> findPage(Page<TblRollOutBack> page, TblRollOutBack tblRollOutBack) {
		return super.findPage(page, tblRollOutBack);
	}
	
	@Transactional(readOnly = false)
	public void save(TblRollOutBack tblRollOutBack) {
		super.save(tblRollOutBack);
	}
	
	@Transactional(readOnly = false)
	public void delete(TblRollOutBack tblRollOutBack) {
		super.delete(tblRollOutBack);
	}
	
	@Transactional(readOnly = false)
	public void receiptSave(String rollApproveAttachment, String rollOutId)throws Exception{
		
		TblRollOut tblRollOut = tblRollOutService.get(rollOutId);
		tblRollOut.setIsReturn("1");
		tblRollOutService.save(tblRollOut);
		
		TblRollOutBack tblRollOutBack = tblRollOutBackDao.getBackInfoByMainId(rollOutId);
		if(tblRollOutBack == null){
			TblRollOutBack rollOutBack = new TblRollOutBack();
			rollOutBack.setMainId(rollOutId);
			rollOutBack.setRecipient(tblRollOut.getOperator());
			rollOutBack.setReturnTime(new Date());
			rollOutBack.setReturnAttmentId(rollApproveAttachment);
			super.save(rollOutBack);
		}else{
			tblRollOutBack.setReturnTime(new Date());
			tblRollOutBack.setReturnAttmentId(rollApproveAttachment);
			super.save(tblRollOutBack);
		}
	}
	
}