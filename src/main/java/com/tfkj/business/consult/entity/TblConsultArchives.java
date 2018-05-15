/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.consult.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 查阅档案Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblConsultArchives extends DataEntity<TblConsultArchives> {
	
	private static final long serialVersionUID = 1L;
	private Date borrowDate;		// 查阅日期
	private String consultUnit;		// 查阅单位
	private String reason;		// 查阅事由
	private String content;		// 查档内容
	private String borrowUnitOpinion;		// 查阅单位意见
	private Date borrowApproveTime;		// 查阅单位批示时间
	private String municipalOpinion;		// 市委领导意见
	private Date municipalApproveTime;		// 市委批示时间
	private String approveAttachment;		// 查阅审批附件
	private String operator;		// 经办人
	private String status;		// 业务状态
	private List<TblCheckedTarget> tblCheckedTargetList = Lists.newArrayList();		// 子表列表
	private List<TblCheckPerson> tblCheckPersonList = Lists.newArrayList();		// 子表列表
	
	private Date startBorrowDate;
	private Date endBorrowDate;
	private String tarStr;
	private String perStr;
	private String consultUnitName;
	private String consultTarNum; //被查阅人员数量
	private String consultPerNum; //查阅人员数量
	private String xh;
	
	public TblConsultArchives() {
		super();
	}

	public TblConsultArchives(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	
	@Length(min=0, max=255, message="查阅单位长度必须介于 0 和 255 之间")
	public String getConsultUnit() {
		return consultUnit;
	}

	public void setConsultUnit(String consultUnit) {
		this.consultUnit = consultUnit;
	}
	
	@Length(min=0, max=500, message="查档事由长度必须介于 0 和 500 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=2000, message="查档内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=500, message="查阅单位意见长度必须介于 0 和 500 之间")
	public String getBorrowUnitOpinion() {
		return borrowUnitOpinion;
	}

	public void setBorrowUnitOpinion(String borrowUnitOpinion) {
		this.borrowUnitOpinion = borrowUnitOpinion;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBorrowApproveTime() {
		return borrowApproveTime;
	}

	public void setBorrowApproveTime(Date borrowApproveTime) {
		this.borrowApproveTime = borrowApproveTime;
	}
	
	@Length(min=0, max=500, message="市委领导意见长度必须介于 0 和 500 之间")
	public String getMunicipalOpinion() {
		return municipalOpinion;
	}

	public void setMunicipalOpinion(String municipalOpinion) {
		this.municipalOpinion = municipalOpinion;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMunicipalApproveTime() {
		return municipalApproveTime;
	}

	public void setMunicipalApproveTime(Date municipalApproveTime) {
		this.municipalApproveTime = municipalApproveTime;
	}
	
	@Length(min=0, max=1000, message="查阅审批附件长度必须介于 0 和 1000 之间")
	public String getApproveAttachment() {
		return approveAttachment;
	}

	public void setApproveAttachment(String approveAttachment) {
		this.approveAttachment = approveAttachment;
	}
	
	@Length(min=0, max=64, message="经办人长度必须介于 0 和 64 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=1, message="业务状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<TblCheckedTarget> getTblCheckedTargetList() {
		return tblCheckedTargetList;
	}

	public void setTblCheckedTargetList(List<TblCheckedTarget> tblCheckedTargetList) {
		this.tblCheckedTargetList = tblCheckedTargetList;
	}
	public List<TblCheckPerson> getTblCheckPersonList() {
		return tblCheckPersonList;
	}

	public void setTblCheckPersonList(List<TblCheckPerson> tblCheckPersonList) {
		this.tblCheckPersonList = tblCheckPersonList;
	}

	public Date getStartBorrowDate() {
		return startBorrowDate;
	}

	public void setStartBorrowDate(Date startBorrowDate) {
		this.startBorrowDate = startBorrowDate;
	}

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

	public String getPerStr() {
		return perStr;
	}

	public void setPerStr(String perStr) {
		this.perStr = perStr;
	}

	public String getConsultUnitName() {
		return consultUnitName;
	}

	public void setConsultUnitName(String consultUnitName) {
		this.consultUnitName = consultUnitName;
	}

	public String getConsultTarNum() {
		return consultTarNum;
	}

	public void setConsultTarNum(String consultTarNum) {
		this.consultTarNum = consultTarNum;
	}

	public String getConsultPerNum() {
		return consultPerNum;
	}

	public void setConsultPerNum(String consultPerNum) {
		this.consultPerNum = consultPerNum;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

}