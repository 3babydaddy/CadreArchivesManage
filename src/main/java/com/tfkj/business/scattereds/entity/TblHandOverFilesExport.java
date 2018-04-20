/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.entity;


import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.Excel;


/**
 * 零散材料移交人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblHandOverFilesExport extends DataEntity<TblHandOverFiles> {
	
	private static final long serialVersionUID = 123L;
	@Excel(name = "序号")
	private String xh;
	@Excel(name = "姓名")
	private String name;		// 姓名
	@Excel(name = "职务")
	private String duty;		// 职务
	@Excel(name = "材料份数")
	private Long originalNo;		// 正本（卷）
	
	private String mainId;
	
	public TblHandOverFilesExport() {
		super();
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	
}