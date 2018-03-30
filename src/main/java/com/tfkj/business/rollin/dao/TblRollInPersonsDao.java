/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.dao;

import com.tfkj.business.rollin.entity.TblRollInPersons;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 转入管理人员DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblRollInPersonsDao extends CrudDao<TblRollInPersons> {
	
}