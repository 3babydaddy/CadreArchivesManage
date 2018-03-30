/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.borrow.dao.TblGiveBackDao;
import com.tfkj.business.borrow.entity.TblGiveBack;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;

/**
 * 借阅归还Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblGiveBackService extends CrudService<TblGiveBackDao, TblGiveBack> {

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
		super.save(tblGiveBack);
	}
	
	@Transactional(readOnly = false)
	public void delete(TblGiveBack tblGiveBack) {
		super.delete(tblGiveBack);
	}
	
}