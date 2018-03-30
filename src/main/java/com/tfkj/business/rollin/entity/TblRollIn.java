/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollin.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 转入管理人员Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollIn extends DataEntity<TblRollIn> {
	
	private static final long serialVersionUID = 1L;
	private Long character;		// 字
	private Long number;		// 号
	private Date rollInTime;		// 转入时间
	private String operator;		// 经办人
	private String recipient;		// 接收人
	private String beforeUnit;		// 原存档单位
	private String beforeUnitTel;		// 原存档单位电话
	private String saveUnit;		// 现存档单位
	private String rollApproveAttachment;		// 转递单附件
	private List<TblRollInPersons> tblRollInPersonsList = Lists.newArrayList();		// 子表列表
	
	private Date startRollInTime;
	private Date endRollInTime;
	
	public TblRollIn() {
		super();
	}

	public TblRollIn(String id){
		super(id);
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRollInTime() {
		return rollInTime;
	}

	public void setRollInTime(Date rollInTime) {
		this.rollInTime = rollInTime;
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
	
	@Length(min=0, max=32, message="原存档单位电话长度必须介于 0 和 32 之间")
	public String getBeforeUnitTel() {
		return beforeUnitTel;
	}

	public void setBeforeUnitTel(String beforeUnitTel) {
		this.beforeUnitTel = beforeUnitTel;
	}
	
	@Length(min=0, max=64, message="现存档单位长度必须介于 0 和 64 之间")
	public String getSaveUnit() {
		return saveUnit;
	}

	public void setSaveUnit(String saveUnit) {
		this.saveUnit = saveUnit;
	}
	
	@Length(min=0, max=64, message="转递单附件长度必须介于 0 和 64 之间")
	public String getRollApproveAttachment() {
		return rollApproveAttachment;
	}

	public void setRollApproveAttachment(String rollApproveAttachment) {
		this.rollApproveAttachment = rollApproveAttachment;
	}
	
	public List<TblRollInPersons> getTblRollInPersonsList() {
		return tblRollInPersonsList;
	}

	public void setTblRollInPersonsList(List<TblRollInPersons> tblRollInPersonsList) {
		this.tblRollInPersonsList = tblRollInPersonsList;
	}

	public Date getStartRollInTime() {
		return startRollInTime;
	}

	public void setStartRollInTime(Date startRollInTime) {
		this.startRollInTime = startRollInTime;
	}

	public Date getEndRollInTime() {
		return endRollInTime;
	}

	public void setEndRollInTime(Date endRollInTime) {
		this.endRollInTime = endRollInTime;
	}
}