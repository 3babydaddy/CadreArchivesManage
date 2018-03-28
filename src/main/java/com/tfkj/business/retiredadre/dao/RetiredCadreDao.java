/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.retiredadre.dao;

import com.tfkj.business.retiredadre.entity.RetiredCadre;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 退休干部管理模块DAO接口
 * @author tianzhen
 * @version 2018-03-26
 */
@MyBatisDao
public interface RetiredCadreDao extends CrudDao<RetiredCadre> {
	
}