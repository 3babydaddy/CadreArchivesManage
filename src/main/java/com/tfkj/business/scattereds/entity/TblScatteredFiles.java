/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 零散材料移交人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblScatteredFiles extends DataEntity<TblScatteredFiles> {
	
	private static final long serialVersionUID = 1L;
	private String handOverUnit;		// 移交单位
	private Date handOverDate;		// 移交时间
	private String operator;		// 经手人
	private String recipient;		// 接收人
	private String status;		// 业务状态
	private List<TblHandOverFiles> tblHandOverFilesList = Lists.newArrayList();		// 子表列表
	
	private Date startHandOverDate;
	private Date endHandOverDate;
	private String handOverUnitName;
	private String handOverStr;	//移交人
	private String handOverNum;	//移交人数
	private String fileNum;	//材料数
	private String xh;			//序号
	
	public TblScatteredFiles() {
		super();
	}

	public TblScatteredFiles(String id){
		super(id);
	}

	@Length(min=0, max=64, message="移交单位长度必须介于 0 和 64 之间")
	public String getHandOverUnit() {
		return handOverUnit;
	}

	public void setHandOverUnit(String handOverUnit) {
		this.handOverUnit = handOverUnit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandOverDate() {
		return handOverDate;
	}

	public void setHandOverDate(Date handOverDate) {
		this.handOverDate = handOverDate;
	}
	
	@Length(min=0, max=64, message="经手人长度必须介于 0 和 64 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=64, message="接收人长度必须介于 0 和 64 之间")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TblHandOverFiles> getTblHandOverFilesList() {
		return tblHandOverFilesList;
	}

	public void setTblHandOverFilesList(List<TblHandOverFiles> tblHandOverFilesList) {
		this.tblHandOverFilesList = tblHandOverFilesList;
	}

	public Date getStartHandOverDate() {
		return startHandOverDate;
	}

	public void setStartHandOverDate(Date startHandOverDate) {
		this.startHandOverDate = startHandOverDate;
	}

	public Date getEndHandOverDate() {
		return endHandOverDate;
	}

	public void setEndHandOverDate(Date endHandOverDate) {
		this.endHandOverDate = endHandOverDate;
	}

	public String getHandOverUnitName() {
		return handOverUnitName;
	}

	public void setHandOverUnitName(String handOverUnitName) {
		this.handOverUnitName = handOverUnitName;
	}

	public String getHandOverStr() {
		return handOverStr;
	}

	public void setHandOverStr(String handOverStr) {
		this.handOverStr = handOverStr;
	}

	public String getHandOverNum() {
		return handOverNum;
	}

	public void setHandOverNum(String handOverNum) {
		this.handOverNum = handOverNum;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}
}