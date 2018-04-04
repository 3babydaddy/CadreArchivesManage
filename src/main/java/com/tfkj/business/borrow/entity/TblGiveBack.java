/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 借阅归还Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblGiveBack extends DataEntity<TblGiveBack> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编码
	private Date returnTime;		// 归还时间
	private String returnPerson;		// 归还人
	private String photo;		// 归还人照片
	private String status;		// 归还状态
	
	private String operator; //经办人
	private String returnTimeTxt;
	
	public TblGiveBack() {
		super();
	}

	public TblGiveBack(String id){
		super(id);
	}

	@Length(min=1, max=64, message="主表编码长度必须介于 1 和 64 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=64, message="归还人长度必须介于 0 和 64 之间")
	public String getReturnPerson() {
		return returnPerson;
	}

	public void setReturnPerson(String returnPerson) {
		this.returnPerson = returnPerson;
	}
	
	@Length(min=0, max=100, message="归还人照片长度必须介于 0 和 100 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=1, message="归还状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnTimeTxt() {
		return returnTimeTxt;
	}

	public void setReturnTimeTxt(String returnTimeTxt) {
		this.returnTimeTxt = returnTimeTxt;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}