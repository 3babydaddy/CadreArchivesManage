/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;


/**
 * 转出管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollOutPersons extends DataEntity<TblRollOutPersons> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编码 父类
	private String name;		// 姓名
	private String duty;		// 单位及职务
	private String outType;		// 转出形式
	private String reason;		// 转出事由
	private String reasonContent;		// 事由具体内容
	private String relatedAttachment; //相关附件
	private Long originalNo;		// 正本（卷）
	private Long viceNo;		// 副本（卷）
	private Long filesNo;		// 档案材料（份）
	private String certificateNo;		// 证件号
	private String archivesNo;		// 档案号
	private String approveAttachment;		// 审批附件
	
	private Date startCreateDate;
	private Date endCreateDate;
	private String batchNum; //批次号
	
	private Date rollOutTime;		// 转出时间
	private String recipient;		// 接收人
	private String saveUnit;		// 现存档单位
	private String saveUnitName;		
	private String xh;		
	
	public TblRollOutPersons() {
		super();
	}

	public TblRollOutPersons(String id){
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
	@ExcelField(title = "姓名", align = 2, sort = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="单位及职务长度必须介于 0 和 200 之间")
	@ExcelField(title = "单位及职务", align = 2, sort = 40)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@Length(min=0, max=1, message="转出形式长度必须介于 0 和 1 之间")
	@ExcelField(title = "转出形式", align = 2, sort = 50)
	public String getOutType() {
		return outType;
	}

	public void setOutType(String outType) {
		this.outType = outType;
	}
	
	@Length(min=0, max=1, message="转出事由长度必须介于 0 和 1 之间")
	@ExcelField(title = "转出事由", align = 2, sort = 60)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=2000, message="事由具体内容长度必须介于 0 和 2000 之间")
	public String getReasonContent() {
		return reasonContent;
	}

	public void setReasonContent(String reasonContent) {
		this.reasonContent = reasonContent;
	}
	
	public String getRelatedAttachment() {
		return relatedAttachment;
	}

	public void setRelatedAttachment(String relatedAttachment) {
		this.relatedAttachment = relatedAttachment;
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
	@ExcelField(title = "材料份数", align = 2, sort = 90)
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
	
	@Length(min=0, max=100, message="审批附件长度必须介于 0 和 100之间")
	public String getApproveAttachment() {
		return approveAttachment;
	}

	public void setApproveAttachment(String approveAttachment) {
		this.approveAttachment = approveAttachment;
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

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	@ExcelField(title = "转出日期", align = 2, sort = 20)
	public Date getRollOutTime() {
		return rollOutTime;
	}

	public void setRollOutTime(Date rollOutTime) {
		this.rollOutTime = rollOutTime;
	}
	@ExcelField(title = "接收人", align = 2, sort = 80)
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSaveUnit() {
		return saveUnit;
	}

	public void setSaveUnit(String saveUnit) {
		this.saveUnit = saveUnit;
	}
	@ExcelField(title = "接收单位", align = 2, sort = 70)
	public String getSaveUnitName() {
		return saveUnitName;
	}

	public void setSaveUnitName(String saveUnitName) {
		this.saveUnitName = saveUnitName;
	}
	@ExcelField(title = "序号", align = 2, sort = 10)
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}
	
}