/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.dao;

import java.util.List;


import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.business.rollin.entity.TblRollInPersonsExport;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 转入管理人员DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblRollInPersonsDao extends CrudDao<TblRollInPersons> {
	
	List<TblRollInPersons> queryCountPage(TblRollInPersons tblRollInPersons);
	
	List<TblRollInPersonsExport> queryCountList(TblRollInPersons tblRollInPersons);
	
	int querySum(TblRollInPersons tblRollInPersons);
}