/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.entity;

import org.hibernate.validator.constraints.Length;

import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;


/**
 * 零散材料移交人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblHandOverFiles extends DataEntity<TblHandOverFiles> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编码 父类
	private String name;		// 姓名
	private String duty;		// 职务
	private String filesNames;		// 材料名称
	private Long originalNo;		// 正本（卷）
	private String archivesNo;		// 档案号
	private String certificateNo;		// 证件号
	
	private String xh;
	
	public TblHandOverFiles() {
		super();
	}

	public TblHandOverFiles(String id){
		super(id);
	}
	@ExcelField(title = "序号", align = 2, sort = 9)
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	@Length(min=1, max=64, message="主表编码长度必须介于 1 和 64 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	@ExcelField(title = "姓名", align = 2, sort = 10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="职务长度必须介于 0 和 32 之间")
	@ExcelField(title = "职务", align = 2, sort = 20)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@Length(min=0, max=2000, message="材料名称长度必须介于 0 和 2000 之间")
	@ExcelField(title = "材料名称", align = 2, sort = 30)
	public String getFilesNames() {
		return filesNames;
	}

	public void setFilesNames(String filesNames) {
		this.filesNames = filesNames;
	}
	
	@ExcelField(title = "份数", align = 2, sort = 40)
	public Long getOriginalNo() {
		return originalNo;
	}

	public void setOriginalNo(Long originalNo) {
		this.originalNo = originalNo;
	}
	
	@Length(min=0, max=16, message="档案号长度必须介于 0 和 16 之间")
	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}
	
	@Length(min=0, max=30, message="证件号长度必须介于 0 和 30 之间")
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
}