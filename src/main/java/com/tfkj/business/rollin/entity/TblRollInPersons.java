/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 转入管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollInPersons extends DataEntity<TblRollInPersons> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编码 父类
	private String name;		// 姓名
	private String duty;		// 单位及职务
	private String inType;		// 转入形式
	private String reasonContent;		// 转入原因
	private Long originalNo;		// 正本（卷）
	private Long viceNo;		// 副本（卷）
	private Long filesNo;		// 档案材料（份）
	private String certificateNo;		// 证件号
	private String archivesNo;		// 档案号
	
	private Date startCreateDate;
	private Date endCreateDate;
	
	public TblRollInPersons() {
		super();
	}

	public TblRollInPersons(String id){
		super(id);
	}

	@Length(min=1, max=64, message="主表编码长度必须介于 1 和 64 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="单位及职务长度必须介于 0 和 200 之间")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@Length(min=0, max=1, message="转入形式长度必须介于 0 和 1 之间")
	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}
	
	@Length(min=0, max=2000, message="转入原因长度必须介于 0 和 2000 之间")
	public String getReasonContent() {
		return reasonContent;
	}

	public void setReasonContent(String reasonContent) {
		this.reasonContent = reasonContent;
	}
	
	public Long getOriginalNo() {
		return originalNo;
	}

	public void setOriginalNo(Long originalNo) {
		this.originalNo = originalNo;
	}
	
	public Long getViceNo() {
		return viceNo;
	}

	public void setViceNo(Long viceNo) {
		this.viceNo = viceNo;
	}
	
	public Long getFilesNo() {
		return filesNo;
	}

	public void setFilesNo(Long filesNo) {
		this.filesNo = filesNo;
	}
	
	@Length(min=0, max=30, message="证件号长度必须介于 0 和 30 之间")
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	@Length(min=0, max=16, message="档案号长度必须介于 0 和 16 之间")
	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	
}