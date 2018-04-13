/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.dao;

import java.util.List;

import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.borrow.entity.TblBorrowExport;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.annotation.MyBatisDao;

/**
 * 借阅管理DAO接口
 * @author tianzhen
 * @version 2018-03-28
 */
@MyBatisDao
public interface TblBorrowArchivesDao extends CrudDao<TblBorrowArchives> {
	
	List<TblBorrowArchives> queryCountList(TblBorrowArchives tblBorrowArchives);
	
	List<TblBorrowExport> queryTarCountList(TblBorrowArchives tblBorrowArchives);
	
}