/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.entity;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;


public class TblConsultExport extends DataEntity<TblConsultArchives> {
	
	private static final long serialVersionUID = 1L;
	private Date borrowDate;		// 借阅日期
	private String consultUnit;		// 借阅单位
	private String mainId;
	
	private Date startBorrowDate;
	private Date endBorrowDate;
	private String tarStr;
	private String perStr;
	private String consultUnitName;
	private String consultTarNum; //被查阅人员数量
	private String consultPerNum; //查阅人员数量
	private String xh;
	
	private String name;
	private String unitName;
	private String unit;
	private String duty;
	private String politicalStatus;
	
	public TblConsultExport() {
		super();
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "查阅日期", align = 2, sort = 20)
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getConsultUnit() {
		return consultUnit;
	}

	public void setConsultUnit(String consultUnit) {
		this.consultUnit = consultUnit;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartBorrowDate() {
		return startBorrowDate;
	}

	public void setStartBorrowDate(Date startBorrowDate) {
		this.startBorrowDate = startBorrowDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndBorrowDate() {
		return endBorrowDate;
	}

	public void setEndBorrowDate(Date endBorrowDate) {
		this.endBorrowDate = endBorrowDate;
	}

	public String getTarStr() {
		return tarStr;
	}

	public void setTarStr(String tarStr) {
		this.tarStr = tarStr;
	}
	@ExcelField(title = "查阅人", align = 2, sort = 90)
	public String getPerStr() {
		return perStr;
	}

	public void setPerStr(String perStr) {
		this.perStr = perStr;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@ExcelField(title = "查阅单位", align = 2, sort = 30)
	public String getConsultUnitName() {
		return consultUnitName;
	}

	public void setConsultUnitName(String consultUnitName) {
		this.consultUnitName = consultUnitName;
	}
	@ExcelField(title = "被查阅人数", align = 2, sort = 80)
	public String getConsultTarNum() {
		return consultTarNum;
	}

	public void setConsultTarNum(String consultTarNum) {
		this.consultTarNum = consultTarNum;
	}
	@ExcelField(title = "查阅人数", align = 2, sort = 100)
	public String getConsultPerNum() {
		return consultPerNum;
	}

	public void setConsultPerNum(String consultPerNum) {
		this.consultPerNum = consultPerNum;
	}
	@ExcelField(title = "序号", align = 2, sort = 10)
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}
	@ExcelField(title = "姓名", align = 2, sort = 40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title = "单位", align = 2, sort = 50)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@ExcelField(title = "职务", align = 2, sort = 60)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	@ExcelField(title = "政治面貌", align = 2, sort = 70)
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

}