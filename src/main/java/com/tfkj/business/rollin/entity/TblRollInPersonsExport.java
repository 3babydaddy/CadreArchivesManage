/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.entity;

import java.util.Date;

import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.Excel;


/**
 * 转入管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollInPersonsExport extends DataEntity<TblRollInPersons> {
	
	private static final long serialVersionUID = 1L;
	@Excel(name = "序号")
	private String xh;
	@Excel(name = "转入时间")
	private Date rollInTime;		// 转入时间
	@Excel(name = "姓名")
	private String name;		// 姓名
	@Excel(name = "单位及职务")
	private String duty;		// 单位及职务
	private String beforeUnit;		// 原存档单位
	@Excel(name = "原存档单位")
	private String beforeUnitName;
	@Excel(name = "接收人")
	private String recipient;		// 接收人
	@Excel(name = "档案材料（份）")
	private Long filesNo;		// 档案材料（份）
	
	public TblRollInPersonsExport() {
		super();
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public Long getFilesNo() {
		return filesNo;
	}

	public void setFilesNo(Long filesNo) {
		this.filesNo = filesNo;
	}

	public String getBeforeUnit() {
		return beforeUnit;
	}

	public void setBeforeUnit(String beforeUnit) {
		this.beforeUnit = beforeUnit;
	}

	
}