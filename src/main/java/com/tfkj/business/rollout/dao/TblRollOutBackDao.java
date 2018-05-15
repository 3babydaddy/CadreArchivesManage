/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.dao;

import org.apache.ibatis.annotations.Param;

import com.tfkj.business.rollout.entity.TblRollOutBack;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 转出管理回执DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblRollOutBackDao extends CrudDao<TblRollOutBack> {
	
	TblRollOutBack getBackInfoByMainId(@Param("rollOutId") String rollOutId);
}