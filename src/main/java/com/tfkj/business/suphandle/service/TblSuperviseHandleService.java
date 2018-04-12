/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.suphandle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.suphandle.dao.TblSuperviseHandleDao;
import com.tfkj.business.suphandle.entity.TblSuperviseHandle;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 督查督办Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblSuperviseHandleService extends CrudService<TblSuperviseHandleDao, TblSuperviseHandle> {

	public TblSuperviseHandle get(String id) {
		return super.get(id);
	}
	
	public List<TblSuperviseHandle> findList(TblSuperviseHandle tblSuperviseHandle) {
		return super.findList(tblSuperviseHandle);
	}
	
	public Page<TblSuperviseHandle> findPage(Page<TblSuperviseHandle> page, TblSuperviseHandle tblSuperviseHandle) {
		return super.findPage(page, tblSuperviseHandle);
	}
	
	@Transactional(readOnly = false)
	public void save(TblSuperviseHandle tblSuperviseHandle) {
		super.save(tblSuperviseHandle);
	}
	
	@Transactional(readOnly = false)
	public void delete(TblSuperviseHandle tblSuperviseHandle, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				TblSuperviseHandle info = new TblSuperviseHandle();
				info.setId(idArray[i]);
				super.delete(info);
			}
		}
	}
}