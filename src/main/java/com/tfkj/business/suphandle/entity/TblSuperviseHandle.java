/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.suphandle.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;


/**
 * 督查督办Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblSuperviseHandle extends DataEntity<TblSuperviseHandle> {
	
	private static final long serialVersionUID = 1L;
	private String archivesNo;		// 档案号
	private String name;		// 姓名
	private String sex;		// 性别
	private Date birthday;		// 出生日期
	private String beforeUnitDuty; //原单位及职务
	private String unitDuty;		// 单位及职务
	private String status;		// 业务状态
	private Date presentDutyTime;		// 拟任现职时间
	private Date raisedTime;		// 应提交档案时间
	private Long countDown;		// 倒计时(天)
	private String waringStatus;		// 预警灯状态
	private String certificateNo;		// 证件号
	
	private Date startBirthday;
	private Date endBirthday;
	private Date startRaisedTime;
	private Date endRaisedTime;
	private String xh;
	
	public TblSuperviseHandle() {
		super();
	}

	public TblSuperviseHandle(String id){
		super(id);
	}

	@Length(min=0, max=16, message="档案号长度必须介于 0 和 16 之间")
	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}
	
	@Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	@ExcelField(title = "姓名", align = 2, sort = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=2, message="性别长度必须介于 0 和 2 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "出生日期", align = 2, sort = 30)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=128, message="原任职单位及职务长度必须介于 0 和 128 之间")
	@ExcelField(title = "原任职单位及职务", align = 2, sort = 40)
	public String getBeforeUnitDuty() {
		return beforeUnitDuty;
	}

	public void setBeforeUnitDuty(String beforeUnitDuty) {
		this.beforeUnitDuty = beforeUnitDuty;
	}

	@Length(min=0, max=128, message="单位及职务长度必须介于 0 和 128 之间")
	@ExcelField(title = "现任职单位及职务", align = 2, sort = 60)
	public String getUnitDuty() {
		return unitDuty;
	}

	public void setUnitDuty(String unitDuty) {
		this.unitDuty = unitDuty;
	}
	
	@Length(min=0, max=1, message="业务状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "任现职时间", align = 2, sort = 50)
	public Date getPresentDutyTime() {
		return presentDutyTime;
	}

	public void setPresentDutyTime(Date presentDutyTime) {
		this.presentDutyTime = presentDutyTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	public Date getRaisedTime() {
		return raisedTime;
	}

	public void setRaisedTime(Date raisedTime) {
		this.raisedTime = raisedTime;
	}
	
	public Long getCountDown() {
		return countDown;
	}

	public void setCountDown(Long countDown) {
		this.countDown = countDown;
	}
	
	@Length(min=0, max=1, message="预警灯状态长度必须介于 0 和 1 之间")
	public String getWaringStatus() {
		return waringStatus;
	}

	public void setWaringStatus(String waringStatus) {
		this.waringStatus = waringStatus;
	}
	
	@Length(min=0, max=30, message="证件号长度必须介于 0 和 30 之间")
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Date getStartBirthday() {
		return startBirthday;
	}

	public void setStartBirthday(Date startBirthday) {
		this.startBirthday = startBirthday;
	}

	public Date getEndBirthday() {
		return endBirthday;
	}

	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}

	public Date getStartRaisedTime() {
		return startRaisedTime;
	}

	public void setStartRaisedTime(Date startRaisedTime) {
		this.startRaisedTime = startRaisedTime;
	}

	public Date getEndRaisedTime() {
		return endRaisedTime;
	}

	public void setEndRaisedTime(Date endRaisedTime) {
		this.endRaisedTime = endRaisedTime;
	}
	@ExcelField(title = "序号", align = 2, sort = 10)
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}
	
}