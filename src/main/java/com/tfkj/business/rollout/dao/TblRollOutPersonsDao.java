/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.dao;

import java.util.List;

import com.tfkj.business.rollout.entity.TblRollOutPersons;
import com.tfkj.business.rollout.entity.TblRollOutPersonsExport;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 转出管理人员DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblRollOutPersonsDao extends CrudDao<TblRollOutPersons> {
	
	List<TblRollOutPersons> queryCountPage(TblRollOutPersons tblRollOutPersons);
	
	List<TblRollOutPersonsExport> queryCountList(TblRollOutPersons tblRollOutPersons);
	
	String querySum(TblRollOutPersons tblRollOutPersons);
}