/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.entity;

import java.util.Date;

import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.Excel;


/**
 * 借阅管理Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblBorrowExport extends DataEntity<TblBorrowArchives> {
	
	private static final long serialVersionUID = 1L;
	
	@Excel(name = "序号")
	private String xh;
	@Excel(name = "借阅日期")
	private Date borrowDate;		// 借阅日期
	@Excel(name = "借阅单位")
	private String consultUnitName;
	@Excel(name = "姓名")
	private String name;
	@Excel(name = "单位")
	private String unitName;
	@Excel(name = "职务")
	private String duty;
	@Excel(name = "政治面貌")
	private String politicalStatus;
	@Excel(name = "被查阅人员数量")
	private String borrowTarNum; //被查阅人员数量
	@Excel(name = "查阅人")
	private String perStr;
	@Excel(name = "查阅人员数量")
	private String borrowPerNum; //查阅人员数量
	
	private String unit;
	private String mainId;
	
	public TblBorrowExport() {
		super();
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBorrowTarNum() {
		return borrowTarNum;
	}

	public void setBorrowTarNum(String borrowTarNum) {
		this.borrowTarNum = borrowTarNum;
	}

	public String getPerStr() {
		return perStr;
	}

	public void setPerStr(String perStr) {
		this.perStr = perStr;
	}

	public String getBorrowPerNum() {
		return borrowPerNum;
	}

	public void setBorrowPerNum(String borrowPerNum) {
		this.borrowPerNum = borrowPerNum;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	
}