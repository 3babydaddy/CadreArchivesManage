/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 转出管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollOut extends DataEntity<TblRollOut> {
	
	private static final long serialVersionUID = 1L;
	private Date rollOutTime;		// 转出时间
	private String operator;		// 经办人
	private String recipient;		// 接收人
	private String beforeUnit;		// 原存档单位
	private String saveUnit;		// 现存档单位
	private String receiveUnitTel;		// 接收单位电话
	private String isReturn;		// 是否回执
	private String rollApproveAttachment;		// 转递单附件
	private Long character;		// 字
	private Long number;		// 号
	private List<TblRollOutPersons> tblRollOutPersonsList = Lists.newArrayList();		// 子表列表
	
	private Date startRollOutTime;
	private Date endRollOutTime;
	private String beforeUnitName;
	private String saveUnitName;
	
	public TblRollOut() {
		super();
	}

	public TblRollOut(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRollOutTime() {
		return rollOutTime;
	}

	public void setRollOutTime(Date rollOutTime) {
		this.rollOutTime = rollOutTime;
	}
	
	@Length(min=0, max=64, message="经办人长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="原存档单位长度必须介于 0 和 64 之间")
	public String getBeforeUnit() {
		return beforeUnit;
	}

	public void setBeforeUnit(String beforeUnit) {
		this.beforeUnit = beforeUnit;
	}
	
	@Length(min=0, max=64, message="现存档单位长度必须介于 0 和 64 之间")
	public String getSaveUnit() {
		return saveUnit;
	}

	public void setSaveUnit(String saveUnit) {
		this.saveUnit = saveUnit;
	}
	
	@Length(min=0, max=32, message="接收单位电话长度必须介于 0 和 32 之间")
	public String getReceiveUnitTel() {
		return receiveUnitTel;
	}

	public void setReceiveUnitTel(String receiveUnitTel) {
		this.receiveUnitTel = receiveUnitTel;
	}
	
	@Length(min=0, max=2, message="是否回执长度必须介于 0 和 2 之间")
	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	
	@Length(min=0, max=64, message="转递单附件长度必须介于 0 和 64 之间")
	public String getRollApproveAttachment() {
		return rollApproveAttachment;
	}

	public void setRollApproveAttachment(String rollApproveAttachment) {
		this.rollApproveAttachment = rollApproveAttachment;
	}
	
	public Long getCharacter() {
		return character;
	}

	public void setCharacter(Long character) {
		this.character = character;
	}
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	public List<TblRollOutPersons> getTblRollOutPersonsList() {
		return tblRollOutPersonsList;
	}

	public void setTblRollOutPersonsList(List<TblRollOutPersons> tblRollOutPersonsList) {
		this.tblRollOutPersonsList = tblRollOutPersonsList;
	}

	public Date getStartRollOutTime() {
		return startRollOutTime;
	}

	public void setStartRollOutTime(Date startRollOutTime) {
		this.startRollOutTime = startRollOutTime;
	}

	public Date getEndRollOutTime() {
		return endRollOutTime;
	}

	public void setEndRollOutTime(Date endRollOutTime) {
		this.endRollOutTime = endRollOutTime;
	}

	public String getBeforeUnitName() {
		return beforeUnitName;
	}

	public void setBeforeUnitName(String beforeUnitName) {
		this.beforeUnitName = beforeUnitName;
	}

	public String getSaveUnitName() {
		return saveUnitName;
	}

	public void setSaveUnitName(String saveUnitName) {
		this.saveUnitName = saveUnitName;
	}
}