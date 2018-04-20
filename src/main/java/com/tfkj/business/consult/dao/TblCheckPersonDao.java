/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tfkj.business.consult.entity.TblCheckPerson;
import com.tfkj.business.consult.entity.TblConsultExport;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 查阅档案DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblCheckPersonDao extends CrudDao<TblCheckPerson> {
	
	List<TblCheckPerson> queryPerInfoByTar(@Param("export") TblConsultExport tblConsultExport);

	int querySum();
}