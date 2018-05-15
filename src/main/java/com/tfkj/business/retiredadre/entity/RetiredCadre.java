/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.retiredadre.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;

/**
 * 退休干部管理模块Entity
 * @author tianzhen
 * @version 2018-03-26
 */
public class RetiredCadre extends DataEntity<RetiredCadre> {
	
	private static final long serialVersionUID = 1L;
	private String sort;		// 排序
	private String noteNo;     //本号
	private String name;		// 姓名
	private String sex;		// 性别
	private String archivesNo;		// 档案号
	private String unitsDuties;		// 单位及职务
	private Date birthday;		// 出生日期
	private String education;		// 学历
	private String referenceNo;		// 任免文号
	private Date archivesCreatetime;		// 建档时间
	private String status;		// 状态 1-离退2-死亡3-已转档案局
	private String statusTem;	//保留更改前的状态
	private Date diedTime;		// 转死亡时间
	private Date recordOfficeTime;		// 转档案时间
	private String workUnit;		// 退休工作单位
	private String recordOfficeAddress;		// 档案局地址
	private String certificateNo;   //证件号
	//private String remarks;       //备注
	
	private Date startBir;
	private Date endBir;
	private String workUnitName;
	private String xh;
	
	public RetiredCadre() {
		super();
	}

	public RetiredCadre(String id){
		super(id);
	}
	@ExcelField(title = "序号", align = 2, sort = 8)
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	@Length(min=1, max=11, message="编号长度必须介于 1 和 11 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=16, message="本号长度必须介于 1 和 11 之间")
	@ExcelField(title = "本号", align = 2, sort = 9)
	public String getNoteNo() {
		return noteNo;
	}

	public void setNoteNo(String noteNo) {
		this.noteNo = noteNo;
	}

	@Length(min=1, max=64, message="姓名长度必须介于 1 和 64 之间")
	@ExcelField(title = "姓名", align = 2, sort = 10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=1, max=16, message="档案号长度必须介于 1 和 16 之间")
	@ExcelField(title = "档号", align = 2, sort = 20)
	public String getArchivesNo() {
		return archivesNo;
	}

	public void setArchivesNo(String archivesNo) {
		this.archivesNo = archivesNo;
	}
	
	@Length(min=1, max=255, message="单位及职务长度必须介于 1 和 255 之间")
	@ExcelField(title = "工作单位及职务", align = 2, sort = 30)
	public String getUnitsDuties() {
		return unitsDuties;
	}

	public void setUnitsDuties(String unitsDuties) {
		this.unitsDuties = unitsDuties;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "出生日期", align = 2, sort = 50)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=4, message="学历长度必须介于 0 和 4 之间")
	@ExcelField(title = "学历", align = 2, sort = 60, dictType="education")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=0, max=16, message="任免文号长度必须介于 0 和 16 之间")
	@ExcelField(title = "任免文号", align = 2, sort = 70)
	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "建档时间", align = 2, sort = 80)
	public Date getArchivesCreatetime() {
		return archivesCreatetime;
	}

	public void setArchivesCreatetime(Date archivesCreatetime) {
		this.archivesCreatetime = archivesCreatetime;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusTem() {
		return statusTem;
	}

	public void setStatusTem(String statusTem) {
		this.statusTem = statusTem;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDiedTime() {
		return diedTime;
	}

	public void setDiedTime(Date diedTime) {
		this.diedTime = diedTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRecordOfficeTime() {
		return recordOfficeTime;
	}

	public void setRecordOfficeTime(Date recordOfficeTime) {
		this.recordOfficeTime = recordOfficeTime;
	}
	
	@Length(min=0, max=64, message="退休工作单位长度必须介于 0 和 120 之间")
	@ExcelField(title = "何时调往何处工作", align = 2, sort = 90)
	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	
	@Length(min=0, max=64, message="档案局地址长度必须介于 0 和 120 之间")
	@ExcelField(title = "档案何时转往何处", align = 2, sort = 100)
	public String getRecordOfficeAddress() {
		return recordOfficeAddress;
	}

	public void setRecordOfficeAddress(String recordOfficeAddress) {
		this.recordOfficeAddress = recordOfficeAddress;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	@ExcelField(title = "备注", align = 2, sort = 40)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getStartBir() {
		return startBir;
	}

	public void setStartBir(Date startBir) {
		this.startBir = startBir;
	}

	public Date getEndBir() {
		return endBir;
	}

	public void setEndBir(Date endBir) {
		this.endBir = endBir;
	}

	public String getWorkUnitName() {
		return workUnitName;
	}

	public void setWorkUnitName(String workUnitName) {
		this.workUnitName = workUnitName;
	}
	
}