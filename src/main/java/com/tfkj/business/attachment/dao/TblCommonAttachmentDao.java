/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.attachment.dao;

import com.tfkj.business.attachment.entity.TblCommonAttachment;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 附件DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblCommonAttachmentDao extends CrudDao<TblCommonAttachment> {
	
}