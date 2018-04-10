/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.attachment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.attachment.dao.TblCommonAttachmentDao;
import com.tfkj.business.attachment.entity.TblCommonAttachment;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;

/**
 * 借阅归还Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblCommonAttachmentService extends CrudService<TblCommonAttachmentDao, TblCommonAttachment> {

	public TblCommonAttachment get(String id) {
		return super.get(id);
	}
	
	public List<TblCommonAttachment> findList(TblCommonAttachment tblCommonAttachment) {
		return super.findList(tblCommonAttachment);
	}
	
	public Page<TblCommonAttachment> findPage(Page<TblCommonAttachment> page, TblCommonAttachment tblCommonAttachment) {
		return super.findPage(page, tblCommonAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(TblCommonAttachment tblCommonAttachment) {
		super.save(tblCommonAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(TblCommonAttachment tblCommonAttachment) {
		super.delete(tblCommonAttachment);
	}
	
}