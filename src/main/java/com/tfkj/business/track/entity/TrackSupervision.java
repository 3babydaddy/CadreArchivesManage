package com.tfkj.business.track.entity;

import com.github.pagehelper.StringUtil;
import com.tfkj.business.ywgl.entity.TableOfficeElement;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.system.utils.DictUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

public class TrackSupervision extends DataEntity<TrackSupervision> {

  private static final long serialVersionUID = 1L;

  // 是否后续跟踪
  private String isTrack;
  // 跟踪任务
  private String trackTask;
  // 跟踪情况
  private String trackSituation;
  // 状态  （未办结，已办结）
  private String status;
  // 销号人
  private String salesPerson;
  // 是否日常管理
  private String isDayManager;
  // 销号时间
  private String salesNumberDate;
  // 年度
  private String yearNumber;
  // 任务类型
  private String taskType;
  private String taskTypeText;
  // 督查时间
  private String inspectionDate;

  // 督查任务
  private String inspectionTask;
  // 参加单位
  private String joinCompany;
  private String joinCompanyText;

  private List<TableOfficeElement> joinCompanyList;
//表扬单位
  private List<TableOfficeElement> praiseCompany;
//批评单位
  private List<TableOfficeElement> criticismCompany;
  // 带队领导
  private String leadLeader;
  private String leadLeaderText;
  // 督查范围
  private String inspectionRange;
  // 督查范围list
  private  List<TableOfficeElement> inspectionRangeList;
  // 督查范围首个单位名称
  private String inspectionRangeName;
  // 工作情况
  private String workSituation;
  // 督查方案
  private String inspectionProgramme;
  // 督查通知
  private String inspectionNotice;
  // 督查专报
  private String inspectionSpecialReport;
  // 督查批示
  private String inspectionInstructions;
  // 其他上传资料
  private String otherData;
  // 是否关联
  private String isRelation;
  // 关联任务
  private String relationTask;
  private String relationTaskName;
  /* new word*/
  //落实时间
  private String luoshiDate;
  private String luoshiEndDate;
  //下传通报路径
  private String xctbUploadUrl;
  //下传通报文件名称
  private String xctbUploadName;
  //上传领导批示
  private String ldpsUploadUrl;
  private String ldpsUploadName;
  //上传清单
  private String scqdUploadUrl;
  private String scqdUploadName;
  //上传报告
  private String bgxsUploadUrl;
  private String bgxsUploadName;
  //报告形式
  private String bgxs;
  //督查结束时间
  private String inspectionEndDate;
  //l来源
  private String source;

  /**
   * 填报单位ID
   */
  private TableOfficeElement office;

  public TableOfficeElement getOffice() {
    return office;
  }

  public void setOffice(TableOfficeElement office) {
    this.office = office;
  }

  public List<TableOfficeElement> getInspectionRangeList() {
    return inspectionRangeList;
  }

  public void setInspectionRangeList(List<TableOfficeElement> inspectionRangeList) {
    this.inspectionRangeList = inspectionRangeList;
  }

  public String getInspectionRangeName() {
    return inspectionRangeName;
  }

  public void setInspectionRangeName(String inspectionRangeName) {
    this.inspectionRangeName = inspectionRangeName;
  }

  public String getLuoshiEndDate() {
    return luoshiEndDate;
  }

  public void setLuoshiEndDate(String luoshiEndDate) {
    this.luoshiEndDate = luoshiEndDate;
  }
  @ExcelField(align = 2, type = 0, sort = 3000, title = "关联任务")
  public String getRelationTaskName() {
    return StringEscapeUtils.unescapeHtml4(relationTaskName);
  }

  public void setRelationTaskName(String relationTaskName) {
    this.relationTaskName = relationTaskName;
  }

  public String getTaskTypeText() {
    return DictUtils.getDictLabel(this.taskType,"RWLX","");
  }

  public void setTaskTypeText(String taskTypeText) {
    this.taskTypeText = taskTypeText;
  }

  public String getXctbUploadUrl() {
    return xctbUploadUrl;
  }

  public void setXctbUploadUrl(String xctbUploadUrl) {
    this.xctbUploadUrl = xctbUploadUrl;
  }

  public String getLdpsUploadUrl() {
    return ldpsUploadUrl;
  }

  public void setLdpsUploadUrl(String ldpsUploadUrl) {
    this.ldpsUploadUrl = ldpsUploadUrl;
  }

  public String getScqdUploadUrl() {
    return scqdUploadUrl;
  }

  public void setScqdUploadUrl(String scqdUploadUrl) {
    this.scqdUploadUrl = scqdUploadUrl;
  }

  public String getBgxsUploadUrl() {
    return bgxsUploadUrl;
  }

  public void setBgxsUploadUrl(String bgxsUploadUrl) {
    this.bgxsUploadUrl = bgxsUploadUrl;
  }

  public String getJoinCompanyText() {
    return DictUtils.getOfficeName(this.joinCompany);
  }

  public void setJoinCompanyText(String joinCompanyText) {
    this.joinCompanyText = joinCompanyText;
  }

  public String getLeadLeaderText() {
    return DictUtils.getLeadName(this.leadLeader);
  }
  public void setLeadLeaderText(String leadLeaderText) {
    this.leadLeaderText = leadLeaderText;
  }
  @ExcelField(align = 2, type = 0, sort = 8000, title = "落实时间")
  public String getLuoshiDate() {
    return luoshiDate;
  }

  public void setLuoshiDate(String luoshiDate) {
    this.luoshiDate = luoshiDate;
  }


  public String getXctbUploadName() {
    String url = this.xctbUploadUrl;
    if (StringUtils.isNotBlank(url)){
      String[] array = url.split("/");
      this.xctbUploadName = array[array.length-1];
    }
    return this.xctbUploadName;
  }

  public void setXctbUploadName(String xctbUploadName) {
    this.xctbUploadName = xctbUploadName;
  }



  public String getLdpsUploadName() {
    String url = this.ldpsUploadUrl;
    if (StringUtils.isNotBlank(url)){
      String[] array = url.split("/");
      this.ldpsUploadName = array[array.length-1];
    }
    return this.ldpsUploadName;
  }

  public void setLdpsUploadName(String ldpsUploadName) {
    this.ldpsUploadName = ldpsUploadName;
  }


  public String getScqdUploadName() {
    String url = this.scqdUploadUrl;
    if (StringUtils.isNotBlank(url)){
      String[] array = url.split("/");
      this.scqdUploadName = array[array.length-1];
    }
    return this.scqdUploadName;
  }

  public void setScqdUploadName(String scqdUploadName) {
    this.scqdUploadName = scqdUploadName;
  }


  public String getBgxsUploadName() {
    String url = this.bgxsUploadUrl;
    if (StringUtils.isNotBlank(url)){
      String[] array = url.split("/");
      this.bgxsUploadName = array[array.length-1];
    }
    return this.bgxsUploadName;
  }

  public void setBgxsUploadName(String bgxsUploadName) {
    this.bgxsUploadName = bgxsUploadName;
  }
  @ExcelField(align = 2, type = 0, sort = 6000, title = "报告形式")
  public String getBgxs() {
    return bgxs;
  }

  public void setBgxs(String bgxs) {
    this.bgxs = bgxs;
  }

  public String getInspectionEndDate() {
    return inspectionEndDate;
  }

  public void setInspectionEndDate(String inspectionEndDate) {
    this.inspectionEndDate = inspectionEndDate;
  }

  @ExcelField(align = 2, type = 0, sort = 1000, title = "关联来源")
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getIsRelation() {
    return isRelation;
  }

  public void setIsRelation(String isRelation) {
    this.isRelation = isRelation;
  }

  public String getRelationTask() {
    return relationTask;
  }

  public void setRelationTask(String relationTask) {
    this.relationTask = relationTask;
  }

  public List<TableOfficeElement> getJoinCompanyList() {
    return joinCompanyList;
  }

  public void setJoinCompanyList(List<TableOfficeElement> joinCompanyList) {
    this.joinCompanyList = joinCompanyList;
  }

  public List<TableOfficeElement> getPraiseCompany() {
    return praiseCompany;
  }

  public void setPraiseCompany(List<TableOfficeElement> praiseCompany) {
    this.praiseCompany = praiseCompany;
  }

  public List<TableOfficeElement> getCriticismCompany() {
    return criticismCompany;
  }

  public void setCriticismCompany(List<TableOfficeElement> criticismCompany) {
    this.criticismCompany = criticismCompany;
  }

  public String getIsTrack() {
    return isTrack;
  }

  public void setIsTrack(String isTrack) {
    this.isTrack = isTrack;
  }

  public String getTrackTask() {
    return trackTask;
  }

  public void setTrackTask(String trackTask) {
    this.trackTask = trackTask;
  }

  public String getTrackSituation() {
    return trackSituation;
  }

  public void setTrackSituation(String trackSituation) {
    this.trackSituation = trackSituation;
  }

  public String getStatus() {
    return status;
  }

  @ExcelField(align = 2, type = 0, sort = 7000, title = "状态")
  public String getStatusName() {
    String status = this.status;
    if (StringUtils.isNotBlank(status)){
      if ("01".equals(status)){
        status = "已落实";
      }else if("02".equals(status)){
        status = "未落实";
      }
    }else {
      status = "";
    }
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public String getSalesPerson() {
    return salesPerson;
  }

  public void setSalesPerson(String salesPerson) {
    this.salesPerson = salesPerson;
  }

  public String getIsDayManager() {
    return isDayManager;
  }

  public void setIsDayManager(String isDayManager) {
    this.isDayManager = isDayManager;
  }

  public String getYearNumber() {
    return yearNumber;
  }

  public void setYearNumber(String yearNumber) {
    this.yearNumber = yearNumber;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public String getSalesNumberDate() {
    return salesNumberDate;
  }

  public void setSalesNumberDate(String salesNumberDate) {
    this.salesNumberDate = salesNumberDate;
  }
  @ExcelField(align = 2, type = 0, sort = 4000, title = "督查时间")
  public String getAllInspectionDate() {
    String date = "";
    if (StringUtils.isNotBlank(inspectionDate)){
      date += inspectionDate;
    }
    if (StringUtils.isNotBlank(inspectionDate)&&StringUtils.isNotBlank(inspectionEndDate)){
      date += "至";
    }
    if (StringUtils.isNotBlank(inspectionEndDate)){
      date += inspectionEndDate;
    }
    return date;
  }
  public String getInspectionDate() {
    return inspectionDate;
  }

  public void setInspectionDate(String inspectionDate) {
    this.inspectionDate = inspectionDate;
  }
  @ExcelField(align = 2, type = 0, sort = 2000, title = "督查事项")
  public String getInspectionTask() {
    return  StringEscapeUtils.unescapeHtml4(inspectionTask);
  }

  public void setInspectionTask(String inspectionTask) {
    this.inspectionTask = inspectionTask;
  }

  public String getJoinCompany() {
    return joinCompany;
  }

  public void setJoinCompany(String joinCompany) {
    this.joinCompany = joinCompany;
  }

  public String getLeadLeader() {
    return leadLeader;
  }

  public void setLeadLeader(String leadLeader) {
    this.leadLeader = leadLeader;
  }
  @ExcelField(align = 2, type = 0, sort = 5000, title = "被督查单位")
  public String getInspectionRange() {
    String manager = "";
    if (StringUtil.isNotEmpty(inspectionRange)){
      String[] array = inspectionRange.split(",|，|、");
      if (array.length==1){
        manager = array[0];
      }else {
        manager = array[0] + "等"+array.length+"单位";
      }
    }
    return manager;
  }

  public void setInspectionRange(String inspectionRange) {
    this.inspectionRange = inspectionRange;
  }

  public String getWorkSituation() {
    return workSituation;
  }

  public void setWorkSituation(String workSituation) {
    this.workSituation = workSituation;
  }

  public String getInspectionProgramme() {
    return inspectionProgramme;
  }

  public void setInspectionProgramme(String inspectionProgramme) {
    this.inspectionProgramme = inspectionProgramme;
  }

  public String getInspectionNotice() {
    return inspectionNotice;
  }

  public void setInspectionNotice(String inspectionNotice) {
    this.inspectionNotice = inspectionNotice;
  }

  public String getInspectionSpecialReport() {
    return inspectionSpecialReport;
  }

  public void setInspectionSpecialReport(String inspectionSpecialReport) {
    this.inspectionSpecialReport = inspectionSpecialReport;
  }

  public String getInspectionInstructions() {
    return inspectionInstructions;
  }

  public void setInspectionInstructions(String inspectionInstructions) {
    this.inspectionInstructions = inspectionInstructions;
  }

  public String getOtherData() {
    return otherData;
  }

  public void setOtherData(String otherData) {
    this.otherData = otherData;
  }
}
