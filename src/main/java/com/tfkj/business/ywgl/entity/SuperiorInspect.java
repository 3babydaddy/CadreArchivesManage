package com.tfkj.business.ywgl.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.business.web.constants.YwglConstantsUtil;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.DateUtils;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.system.utils.DictUtils;

/**
 * @Description: 上级督查
 * @author gaowei
 * @date 2018年1月24日
 */
public class SuperiorInspect extends DataEntity<SuperiorInspect> {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序序号
	 */
	private String sort;

	/**
	 * 填报单位ID
	 */
	private TableOfficeElement office;

	/**
	 * 上级单位IDs
	 */
	private String parentOfficeIds;

	/**
	 * 分管区单位IDs
	 */
	private String chargeOfficeIds;

	/**
	 * 对接协调单位IDs
	 */
	private String cohereOfficeIds;

	/**
	 * 承办单位IDs
	 */
	private String dutyOfficeIds;

	/**
	 * 上级单位Names
	 */
	private String parentOfficeNames;

	/**
	 * 分管区单位Names
	 */
	private String chargeOfficeNames;

	/**
	 * 上级单位ChargePersons
	 */
	private String parentOfficeChargePersons;

	/**
	 * 对接协调单位ChargePersons
	 */
	private String cohereOfficeChargePersons;

	/**
	 * 承办单位ChargePersons
	 */
	private String dutyOfficeChargePersons;

	/**
	 * 上级单位
	 */
	private List<TableOfficeElement> parentOfficeList;

	/**
	 * 分管区单位
	 */
	private List<TableOfficeElement> chargeOfficeList;

	/**
	 * 对接协调单位
	 */
	private List<TableOfficeElement> cohereOfficeList;

	/**
	 * 承办单位
	 */
	private List<TableOfficeElement> dutyOfficeList;

	/**
	 * 督查事项
	 */
	private String inspectMatter;

	/**
	 * 督查起止时间
	 */
	private String inspectTime;

	/**
	 * 督查开始时间
	 */
	private Date inspectTimeStart;

	/**
	 * 督查结束时间
	 */
	private Date inspectTimeEnd;

	/**
	 * 督查事项
	 */
	private String inspectContent;

	/**
	 * 督查附件
	 */
	private String inspectFile;

	/**
	 * 督查类型
	 */
	private String inspectType;

	/**
	 * 督查要求简述
	 */
	private String checkContent;

	/**
	 * 督查完成情况
	 */
	private String completeContent;

	/**
	 * 事项完成情况
	 */
	private String dataState;

	/**
	 * 跟踪事项
	 */
	private String trackMatter;

	/**
	 * 任务完成情况
	 */
	private String taskState;

	/**
	 * 任务年度
	 */
	private String taskYear;

	/**
	 * 上传方案-附件
	 */
	private String planOpinionFile;

	/**
	 * 上传汇报材料-附件
	 */
	private String tripPlanFile;

	/**
	 * 上传点位介绍-附件
	 */
	private String receiveSchemeFile;

	/**
	 * 上传自查报告时间
	 */
	private Date selfReportTime;

	/**
	 * 完成时限
	 */
	private Date finishTimeLimit;

	/**
	 * 专函附件
	 */
	private String specialLetterFile;

	/**
	 * 自查报告附件
	 */
	private String selfCheckReportFile;

	/**
	 * 上传督查要求
	 */
	private String checkContentFile;

	/**
	 * 落实情况附件
	 */
	private String resultSituationFile;

	/**
	 * 落实情况
	 */
	private String resultSituation;

	/**
	 * 上传通知
	 */
	private String s0001;

	/**
	 * 落实时间
	 */
	private Date s0002;

	private Date s0002Start;

	private Date s0002End;

	/**
	 * 分管区领导
	 */
	private String s0003;

	/**
	 * 对接协调单位名称
	 */
	private String s0004;

	/**
	 * 承办单位名称
	 */
	private String s0005;

	private String s0006;

	private String s0007;

	private String s0008;

	private String s0009;


	public void setS0004(String s0004) {

		this.s0004 = s0004;
	}



	public void setS0005(String s0005) {

		this.s0005 = s0005;
	}

	public String getS0006() {

		return s0006;
	}

	public void setS0006(String s0006) {

		this.s0006 = s0006;
	}

	public String getS0007() {

		return s0007;
	}

	public void setS0007(String s0007) {

		this.s0007 = s0007;
	}

	public String getS0008() {

		return s0008;
	}

	public void setS0008(String s0008) {

		this.s0008 = s0008;
	}

	public String getS0009() {

		return s0009;
	}

	public void setS0009(String s0009) {

		this.s0009 = s0009;
	}

	public String getS0001() {

		return s0001;
	}

	public void setS0001(String s0001) {

		this.s0001 = s0001;
	}



	public void setS0002(Date s0002) {

		this.s0002 = s0002;
	}

	public Date getS0002Start() {

		return s0002Start;
	}

	public void setS0002Start(Date s0002Start) {

		this.s0002Start = s0002Start;
	}

	public Date getS0002End() {

		return s0002End;
	}

	public void setS0002End(Date s0002End) {

		this.s0002End = s0002End;
	}



	public void setS0003(String s0003) {

		this.s0003 = s0003;
	}

	public String getSort() {

		return sort;
	}

	public void setSort(String sort) {

		this.sort = sort;
	}

	public TableOfficeElement getOffice() {

		return office;
	}

	public void setOffice(TableOfficeElement office) {

		this.office = office;
	}

	public String getParentOfficeIds() {

		return parentOfficeIds;
	}

	public String getChargeOfficeIds() {

		return chargeOfficeIds;
	}

	public String getCohereOfficeIds() {

		return cohereOfficeIds;
	}

	public String getDutyOfficeIds() {

		return dutyOfficeIds;
	}

	public String getParentOfficeNames() {

		return parentOfficeNames;
	}
	@ExcelField(align = 2, type = 0, sort = 1000, title = "上级单位")
	public String getParentOfficeNamesText() {
		return DictUtils.getDictValue(this.parentOfficeIds, "YW_SJDW", "");
	}

	@ExcelField(align = 2, type = 0, sort = 2000, title = "督查事项")
	public String getInspectMatter() {
		return inspectMatter;
	}

	@ExcelField(align = 2, type = 0, sort = 3000, title = "督查时间")
	public String getInspectTime() {

		String startTime = "";
		if (inspectTimeStart != null) {
			startTime = DateUtils.formatDate(inspectTimeStart);
		}
		String endTime = "";
		if (inspectTimeEnd != null) {
			endTime = DateUtils.formatDate(inspectTimeEnd);
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			inspectTime = startTime + " 至 " + endTime;
		}
		if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
			inspectTime = startTime;
		}
		if (StringUtils.isNotBlank(endTime) && StringUtils.isBlank(startTime)) {
			inspectTime = endTime;
		}
		return inspectTime;
	}

	@ExcelField(align = 2, type = 0, sort = 4000, title = "分管区领导")
	public String getS0003Show() {
		String manager="";
		if(StringUtils.isNotBlank(s0003)){
			String[] array = s0003.split(",|，|、");
			Integer length = array.length;
			if (array.length >= 2) {
				manager = array[0] + "等"
						+ length + "位领导";
			} else {
				manager = array[0];
			}
		}
		return manager;
	}

	public String getS0003() {
		return s0003;
	}

	@ExcelField(align = 2, type = 0, sort = 5000, title = "对接协调单位")
	public String getS0004Show() {
		String manager = "";
		if (StringUtils.isNotBlank(s0004)) {
			String[] array = s0004.split(",|，|、");
			int length = array.length;
			if (array.length >= 2) {
				manager = array[0] + "等"
						+ length + "家单位";
			} else {
				manager = array[0];
			}
		}
		return manager;
	}

	public String getS0004() {

		return s0004;
	}

	@ExcelField(align = 2, type = 0, sort = 6000, title = "承办单位")
	public String getS0005Show() {
		String manager = "";
		if (StringUtils.isNotBlank(s0005)) {
			String[] array = s0005.split(",|，|、");
			int length = array.length;
			if (array.length >= 2) {
				manager = array[0] + "等"
						+ length + "家单位";
			} else {
				manager = array[0];
			}
		}
		return manager;
	}

	public String getS0005() {

		return s0005;
	}

	@ExcelField(align = 2, type = 0, sort = 7000, title = "督查要求")
	public String getCheckContent() {

		return checkContent;
	}

	@ExcelField(align = 2, type = 0, sort = 8000, title = "落实情况")
	public String getResultSituation() {

		return resultSituation;
	}


	@ExcelField(align = 2, type = 0, sort = 9000, title = "状态")
	public String getTaskStateText() {

		return DictUtils.getDictValue(this.taskState, "YW_DCLSQK", "");
	}

	@ExcelField(align = 2, type = 0, sort = 10000, title = "落实时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getS0002() {

		return s0002;
	}
	public String getChargeOfficeNames() {

		return chargeOfficeNames;
	}

	public String getParentOfficeChargePersons() {

		return parentOfficeChargePersons;
	}

	public String getChargeOfficeChargePersonsText() {

		return YwglConstantsUtil.getLeaderNames(chargeOfficeIds);
	}

	public String getCohereOfficeChargePersons() {

		return cohereOfficeChargePersons;
	}

	public String getDutyOfficeChargePersons() {

		return dutyOfficeChargePersons;
	}

	public void setParentOfficeChargePersons(String parentOfficeChargePersons) {

		this.parentOfficeChargePersons = parentOfficeChargePersons;
	}

	public void setCohereOfficeChargePersons(String cohereOfficeChargePersons) {

		this.cohereOfficeChargePersons = cohereOfficeChargePersons;
	}

	public void setDutyOfficeChargePersons(String dutyOfficeChargePersons) {

		this.dutyOfficeChargePersons = dutyOfficeChargePersons;
	}

	public void setParentOfficeNames(String parentOfficeNames) {

		this.parentOfficeNames = parentOfficeNames;
	}

	public void setChargeOfficeNames(String chargeOfficeNames) {

		this.chargeOfficeNames = chargeOfficeNames;
	}

	public void setParentOfficeIds(String parentOfficeIds) {

		this.parentOfficeIds = parentOfficeIds;
	}

	public void setChargeOfficeIds(String chargeOfficeIds) {

		this.chargeOfficeIds = chargeOfficeIds;
	}

	public void setCohereOfficeIds(String cohereOfficeIds) {

		this.cohereOfficeIds = cohereOfficeIds;
	}

	public void setDutyOfficeIds(String dutyOfficeIds) {

		this.dutyOfficeIds = dutyOfficeIds;
	}

	public List<TableOfficeElement> getParentOfficeList() {

		return parentOfficeList;
	}

	public void setParentOfficeList(List<TableOfficeElement> parentOfficeList) {

		this.parentOfficeList = parentOfficeList;
	}

	public List<TableOfficeElement> getChargeOfficeList() {

		return chargeOfficeList;
	}

	public void setChargeOfficeList(List<TableOfficeElement> chargeOfficeList) {

		this.chargeOfficeList = chargeOfficeList;
	}

	public List<TableOfficeElement> getCohereOfficeList() {

		return cohereOfficeList;
	}

	public void setCohereOfficeList(List<TableOfficeElement> cohereOfficeList) {

		this.cohereOfficeList = cohereOfficeList;
	}

	public List<TableOfficeElement> getDutyOfficeList() {

		return dutyOfficeList;
	}

	public void setDutyOfficeList(List<TableOfficeElement> dutyOfficeList) {

		this.dutyOfficeList = dutyOfficeList;
	}

	public void setInspectMatter(String inspectMatter) {

		this.inspectMatter = inspectMatter;
	}

	public String getResultSituationFile() {
		return resultSituationFile;
	}

	public void setInspectTime(String inspectTime) {

		this.inspectTime = inspectTime;
	}

	public Date getInspectTimeStart() {

		return inspectTimeStart;
	}

	public void setInspectTimeStart(Date inspectTimeStart) {

		this.inspectTimeStart = inspectTimeStart;
	}

	public Date getInspectTimeEnd() {

		return inspectTimeEnd;
	}

	public void setInspectTimeEnd(Date inspectTimeEnd) {

		this.inspectTimeEnd = inspectTimeEnd;
	}

	public String getInspectContent() {

		return inspectContent;
	}

	public void setInspectContent(String inspectContent) {

		this.inspectContent = inspectContent;
	}

	public String getInspectFile() {

		return inspectFile;
	}

	public void setInspectFile(String inspectFile) {

		this.inspectFile = inspectFile;
	}

	public String getInspectType() {

		return inspectType;
	}

	public void setInspectType(String inspectType) {

		this.inspectType = inspectType;
	}



	public void setCheckContent(String checkContent) {

		this.checkContent = checkContent;
	}

	public String getCompleteContent() {

		return completeContent;
	}

	public void setCompleteContent(String completeContent) {

		this.completeContent = completeContent;
	}

	public String getDataState() {

		return dataState;
	}

	public void setDataState(String dataState) {

		this.dataState = dataState;
	}

	public String getTrackMatter() {

		return trackMatter;
	}

	public void setTrackMatter(String trackMatter) {

		this.trackMatter = trackMatter;
	}

	public String getTaskState() {

		return taskState;
	}



	public void setTaskState(String taskState) {

		this.taskState = taskState;
	}

	public String getTaskYear() {

		return taskYear;
	}

	public void setTaskYear(String taskYear) {

		this.taskYear = taskYear;
	}

	public String getPlanOpinionFile() {

		return planOpinionFile;
	}

	public void setPlanOpinionFile(String planOpinionFile) {

		this.planOpinionFile = planOpinionFile;
	}

	public String getTripPlanFile() {

		return tripPlanFile;
	}

	public void setTripPlanFile(String tripPlanFile) {

		this.tripPlanFile = tripPlanFile;
	}

	public String getReceiveSchemeFile() {

		return receiveSchemeFile;
	}

	public void setReceiveSchemeFile(String receiveSchemeFile) {

		this.receiveSchemeFile = receiveSchemeFile;
	}

	public Date getSelfReportTime() {

		return selfReportTime;
	}

	public void setSelfReportTime(Date selfReportTime) {

		this.selfReportTime = selfReportTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getFinishTimeLimit() {

		return finishTimeLimit;
	}

	public void setFinishTimeLimit(Date finishTimeLimit) {

		this.finishTimeLimit = finishTimeLimit;
	}

	public String getSpecialLetterFile() {

		return specialLetterFile;
	}

	public void setSpecialLetterFile(String specialLetterFile) {

		this.specialLetterFile = specialLetterFile;
	}

	public String getSelfCheckReportFile() {

		return selfCheckReportFile;
	}

	public void setSelfCheckReportFile(String selfCheckReportFile) {

		this.selfCheckReportFile = selfCheckReportFile;
	}

	public String getCheckContentFile() {

		return checkContentFile;
	}

	public void setCheckContentFile(String checkContentFile) {

		this.checkContentFile = checkContentFile;
	}



	public void setResultSituationFile(String resultSituationFile) {

		this.resultSituationFile = resultSituationFile;
	}



	public void setResultSituation(String resultSituation) {

		this.resultSituation = resultSituation;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}
}
