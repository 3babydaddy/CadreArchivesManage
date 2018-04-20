/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.entity;

import java.util.Date;


import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.Excel;


/**
 * 转出管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollOutPersonsExport extends DataEntity<TblRollOutPersons> {
	
	private static final long serialVersionUID = 1L;
	
	@Excel(name = "序号")
	private String xh;
	@Excel(name = "转出时间")
	private Date rollOutTime;		// 转出时间
	@Excel(name = "姓名")
	private String name;		// 姓名
	@Excel(name = "单位及职务")
	private String duty;		// 单位及职务
	@Excel(name = "转出形式")
	private String outType;		// 转出形式
	@Excel(name = "转出事由")
	private String reason;		// 转出事由
	private String saveUnit;		// 现存档单位
	@Excel(name = "先存档单位")
	private String saveUnitName;	
	@Excel(name = "接收人")
	private String recipient;		// 接收人
	@Excel(name = "档案材料（份）")
	private Long filesNo;		// 档案材料（份）
	
	private String mainId;
	
	public TblRollOutPersonsExport(){
		super();
	}
	
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getFilesNo() {
		return filesNo;
	}
	public void setFilesNo(Long filesNo) {
		this.filesNo = filesNo;
	}

	public String getSaveUnit() {
		return saveUnit;
	}

	public void setSaveUnit(String saveUnit) {
		this.saveUnit = saveUnit;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
}