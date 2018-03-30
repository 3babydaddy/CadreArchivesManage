/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.rollout.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 转出管理回执Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblRollOutBack extends DataEntity<TblRollOutBack> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编号
	private String recipient;		// 收件人
	private Date returnTime;		// 回执时间
	private String returnAttmentId;		// 回执附件
	
	public TblRollOutBack() {
		super();
	}

	public TblRollOutBack(String id){
		super(id);
	}

	@Length(min=1, max=64, message="主表编号长度必须介于 1 和 64 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
	@Length(min=0, max=64, message="收件人长度必须介于 0 和 64 之间")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=64, message="回执附件长度必须介于 0 和 64 之间")
	public String getReturnAttmentId() {
		return returnAttmentId;
	}

	public void setReturnAttmentId(String returnAttmentId) {
		this.returnAttmentId = returnAttmentId;
	}
	
}