/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.borrow.dao.TblBorrowArchivesDao;
import com.tfkj.business.borrow.dao.TblGiveBackDao;
import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblGiveBack;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 借阅归还Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblGiveBackService extends CrudService<TblGiveBackDao, TblGiveBack> {

	@Autowired
	private TblBorrowArchivesDao tblBorrowArchivesDao;
	
	public TblGiveBack get(String id) {
		return super.get(id);
	}
	
	public List<TblGiveBack> findList(TblGiveBack tblGiveBack) {
		return super.findList(tblGiveBack);
	}
	
	public Page<TblGiveBack> findPage(Page<TblGiveBack> page, TblGiveBack tblGiveBack) {
		return super.findPage(page, tblGiveBack);
	}
	
	@Transactional(readOnly = false)
	public void save(TblGiveBack tblGiveBack) {
		tblGiveBack.setStatus("2");
		super.save(tblGiveBack);
		if(StringUtils.isNotBlank(tblGiveBack.getMainId())){
			TblBorrowArchives tblBorrowArchives = tblBorrowArchivesDao.get(tblGiveBack.getMainId());
			tblBorrowArchives.setOperator(tblGiveBack.getOperator());
			tblBorrowArchivesDao.update(tblBorrowArchives);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblGiveBack tblGiveBack) {
		super.delete(tblGiveBack);
	}
	
}