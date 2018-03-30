/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.rollout.dao.TblRollOutBackDao;
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
	
}