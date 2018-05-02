/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblHandOverFilesExport;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 零散材料移交人员DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblHandOverFilesDao extends CrudDao<TblHandOverFiles> {
	
	List<TblHandOverFiles> queryHandOverList(TblHandOverFiles handOver);
	
	List<TblHandOverFilesExport> getHandOverList(@Param("scatteredList") List<TblScatteredFiles> scatteredList);
	
	List<TblHandOverFiles> querySum(@Param("scattered") TblScatteredFiles scattered);
	
}