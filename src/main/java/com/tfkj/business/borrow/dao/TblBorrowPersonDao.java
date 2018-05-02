/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblBorrowExport;
import com.tfkj.business.borrow.entity.TblBorrowPerson;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 借阅管理DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblBorrowPersonDao extends CrudDao<TblBorrowPerson> {
	
	List<TblBorrowPerson> queryPerInfoByTar(@Param("export") TblBorrowExport TblBorrowExport);
	
	int querySum(@Param("borrow") TblBorrowArchives tblBorrowArchives);
}