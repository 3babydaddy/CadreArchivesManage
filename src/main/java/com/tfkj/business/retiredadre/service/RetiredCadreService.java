/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.retiredadre.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.retiredadre.dao.RetiredCadreDao;
import com.tfkj.business.retiredadre.entity.RetiredCadre;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;



/**
 * 退休干部管理模块Service
 * @author tianzhen
 * @version 2018-03-26
 */
@Service
@Transactional(readOnly = true)
public class RetiredCadreService extends CrudService<RetiredCadreDao, RetiredCadre> {

	public RetiredCadre get(String id) {
		return super.get(id);
	}
	
	public List<RetiredCadre> findList(RetiredCadre retiredCadre) {
		return super.findList(retiredCadre);
	}
	
	public Page<RetiredCadre> findPage(Page<RetiredCadre> page, RetiredCadre retiredCadre) {
		return super.findPage(page, retiredCadre);
	}
	
	@Transactional(readOnly = false)
	public void save(RetiredCadre retiredCadre) {
		super.save(retiredCadre);
	}
	
	@Transactional(readOnly = false)
	public void delete(RetiredCadre retiredCadre, String idStr) {
		String[] idArray = idStr.split(",");
		for(int i = 0; i < idArray.length; i++){
			super.delete(super.get(idArray[i]));
		}
	}
	
}