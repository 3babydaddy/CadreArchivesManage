/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.borrow.entity;

import org.hibernate.validator.constraints.Length;

import com.tfkj.framework.core.persistence.DataEntity;


/**
 * 借阅管理Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblBorrowPerson extends DataEntity<TblBorrowPerson> {
	
	private static final long serialVersionUID = 1L;
	private String mainId;		// 主表编码 父类
	private String name;		// 姓名
	private String photo;		// 照片
	private String unit;		// 单位
	private String duty;		// 职务
	private String politicalStatus;		// 政治面貌
	private String telphone;		// 联系方式
	private String siginName;		// 签名
	
	public TblBorrowPerson() {
		super();
	}

	public TblBorrowPerson(String id){
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
	
	@Length(min=0, max=64, message="照片长度必须介于 0 和 64 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=255, message="单位长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=32, message="职务长度必须介于 0 和 32 之间")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@Length(min=0, max=32, message="政治面貌长度必须介于 0 和 32 之间")
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	
	@Length(min=0, max=32, message="联系方式长度必须介于 0 和 32 之间")
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	@Length(min=0, max=64, message="签名长度必须介于 0 和 64 之间")
	public String getSiginName() {
		return siginName;
	}

	public void setSiginName(String siginName) {
		this.siginName = siginName;
	}
	
}