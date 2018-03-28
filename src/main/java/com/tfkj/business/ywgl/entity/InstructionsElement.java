package com.tfkj.business.ywgl.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.DateUtils;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.system.utils.DictUtils;

public class InstructionsElement extends DataEntity<InstructionsElement> {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序序号
	 */
	private String sort;

	/**
	 * 数据ID
	 */
	private String dataId;

	/**
	 * 单位IDS
	 */
	private String officeId;

	/**
	 * 领导IDS
	 */
	private String leaderId;

	/**
	 * 单位IDS
	 */
	private String officeNames;

	/**
	 * 领导IDS
	 */
	private String leaderNames;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 相关附件
	 */
	private String filePath;

	/**
	 * 催报日期
	 */
	private String in0001;

	/**
	 * 办理情况
	 */
	private String in0002;

	/**
	 * 是否超期
	 */
	private String in0003;

	/**
	 * 落实及上报情况
	 */
	private String in0004;

	/**
	 * 上传日期
	 */
	private String in0005;

	private String in0006;

	private String in0007;

	private String in0008;

	private String in0009;

	private String in0010;

	private String in0011;

	private String in0011Start;

	private String in0011End;

	private String in0012;

	private String in0013;

	private String in0014;

	private String in0015;

	private String remindersId;

	private String relationId;

	public String getRemindersId() {

		return remindersId;
	}

	public void setRemindersId(String remindersId) {
		this.remindersId = remindersId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getIn0011Start() {
		return in0011Start;
	}

	public void setIn0011Start(String in0011Start) {
		this.in0011Start = in0011Start;
	}

	public String getIn0011End() {
		return in0011End;
	}

	public void setIn0011End(String in0011End) {
		this.in0011End = in0011End;
	}


	/*
	 * 用于前台展现
	 */
	/**
	 * 文件名
	 */
	private String parentIn0001;

	/**
	 * 文件号
	 */
	private String parentIn0002;

	/**
	 * 批示内容
	 */
	private String parentIn0003;

	/**
	 * 批示日期
	 */
	private Date parentIn0004;

	private Date parentIn0004Start;

	private Date parentIn0004End;

	/**
	 * 收件日期
	 */
	private Date parentIn0005;

	private Date parentIn0005Start;

	private Date parentIn0005End;

	/**
	 * 按轻重分类
	 */
	private String parentIn0006;

	/**
	 * 按要求分类
	 */
	private String parentIn0007;

	/**
	 * 按内容分类
	 */
	private String parentIn0008;

	/**
	 * 责任领导IDS
	 */
	private String parentIn0009;

	/**
	 * 承办单位IDS
	 */
	private String parentIn0010;

	/**
	 * 责任领导Names
	 */
	private String parentIn0009Text;

	/**
	 * 承办单位Names
	 */
	private String parentIn0010Text;

	/**
	 * 催办日期
	 */
	private String parentIn0011;

	private Date parentIn0011Start;

	private Date parentIn0011End;

	/**
	 * 是否超期
	 */
	private String parentIn0012;

	private String leaderIds;

	private String officeIds;

	/**
	 * 办理情况
	 */
	private String parentIn0013;

	/**
	 * 落实及上报情况
	 */
	private String parentIn0014;

	private String relationType;

	/**
	 * 按内容分类--下备注
	 */
	private String parentIn0018;

	/**
	 * 标记
	 */
	private String parentIn0019;

	/**
	 * 文件类型
	 */
	private String parentIn0016;

	/**
	 * 来源
	 */
	private String parentIn0017;

	/*
	 * 专报期数
	 */
	private String parentIn0020Text;

	/*
	 * 专报-id
	 */
	private String parentIn0020Id;

	private String in0004Start;

	private String taskYear;

	private String in0004End;

	public String getParentIn0004Text() {

		if (this.parentIn0004 == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.format(this.parentIn0004);
		return format.format(this.parentIn0004);
	}

	public void setParentIn0004Text(String parentIn0004Text) {

	}

	public String getLeaderIds() {

		return leaderIds;
	}

	public void setLeaderIds(String leaderIds) {

		this.leaderIds = leaderIds;
	}

	public String getOfficeIds() {

		return officeIds;
	}

	public void setOfficeIds(String officeIds) {

		this.officeIds = officeIds;
	}

	public String getTaskYear() {

		return taskYear;
	}

	public void setTaskYear(String taskYear) {

		this.taskYear = taskYear;
	}

	public String getIn0004End() {

		return in0004End;
	}

	public void setIn0004End(String in0004End) {

		this.in0004End = in0004End;
	}

	public String getIn0004Start() {

		return in0004Start;
	}

	public void setIn0004Start(String in0004Start) {

		this.in0004Start = in0004Start;
	}

	public String getRelationType() {

		return relationType;
	}

	public void setRelationType(String relationType) {

		this.relationType = relationType;
	}

	private String unqId;

	private String remindersDate;

	private String delId;

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public String getUnqId() {

		return unqId;
	}

	public void setUnqId(String unqId) {

		this.unqId = unqId;
	}



	public void setRemindersDate(String remindersDate) {

		this.remindersDate = remindersDate;
	}

	public String getParentIn0020Id() {

		return parentIn0020Id;
	}

	public void setParentIn0020Id(String parentIn0020Id) {

		this.parentIn0020Id = parentIn0020Id;
	}



	public void setParentIn0020Text(String parentIn0020Text) {

		this.parentIn0020Text = parentIn0020Text;
	}



	public void setParentIn0018(String parentIn0018) {

		this.parentIn0018 = parentIn0018;
	}

	public String getParentIn0019Text() {

		return DictUtils.getDictValue(parentIn0019, "YW_PS_BJ", "");
	}

	public String getParentIn0019() {

		return parentIn0019;
	}

	public void setParentIn0019(String parentIn0019) {

		this.parentIn0019 = parentIn0019;
	}

	public String getParentIn0016() {
		return parentIn0016;
	}

	public void setParentIn0016(String parentIn0016) {

		this.parentIn0016 = parentIn0016;
	}

	public String getParentIn0017() {

		return parentIn0017;
	}

	public void setParentIn0017(String parentIn0017) {

		this.parentIn0017 = parentIn0017;
	}

	public String getDataId() {

		return dataId;
	}

	public void setDataId(String dataId) {

		this.dataId = dataId;
	}

	public String getOfficeId() {

		return officeId;
	}

	public void setOfficeId(String officeId) {

		this.officeId = officeId;
	}

	public String getLeaderId() {

		return leaderId;
	}

	public void setLeaderId(String leaderId) {

		this.leaderId = leaderId;
	}

	public String getOfficeNames() {

		return officeNames;
	}

	public void setOfficeNames(String officeNames) {

		this.officeNames = officeNames;
	}

	public String getLeaderNames() {

		return leaderNames;
	}

	public void setLeaderNames(String leaderNames) {

		this.leaderNames = leaderNames;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getFilePath() {

		return filePath;
	}

	public void setFilePath(String filePath) {

		this.filePath = filePath;
	}

	public String getIn0001() {

		return in0001;
	}

	public void setIn0001(String in0001) {

		this.in0001 = in0001;
	}

	public String getIn0002() {

		return in0002;
	}

	public void setIn0002(String in0002) {

		this.in0002 = in0002;
	}

	public String getIn0003() {

		return in0003;
	}

	public void setIn0003(String in0003) {

		this.in0003 = in0003;
	}

	public String getIn0004() {

		return in0004;
	}

	public void setIn0004(String in0004) {

		this.in0004 = in0004;
	}

	public String getIn0005() {

		return in0005;
	}

	public void setIn0005(String in0005) {

		this.in0005 = in0005;
	}

	public String getIn0006() {

		return in0006;
	}

	public void setIn0006(String in0006) {

		this.in0006 = in0006;
	}

	public String getIn0007() {

		return in0007;
	}

	public void setIn0007(String in0007) {

		this.in0007 = in0007;
	}

	public String getIn0008() {

		return in0008;
	}

	public void setIn0008(String in0008) {

		this.in0008 = in0008;
	}

	public String getIn0009() {

		return in0009;
	}

	public void setIn0009(String in0009) {

		this.in0009 = in0009;
	}

	public String getIn0010() {

		return in0010;
	}

	public void setIn0010(String in0010) {

		this.in0010 = in0010;
	}

	public String getIn0011() {

		return in0011;
	}

	public void setIn0011(String in0011) {

		this.in0011 = in0011;
	}

	public String getIn0012() {

		return in0012;
	}

	public void setIn0012(String in0012) {

		this.in0012 = in0012;
	}

	public String getIn0013() {

		return in0013;
	}

	public void setIn0013(String in0013) {

		this.in0013 = in0013;
	}

	public String getIn0014() {

		return in0014;
	}

	public void setIn0014(String in0014) {

		this.in0014 = in0014;
	}

	public String getIn0015() {

		return in0015;
	}

	public void setIn0015(String in0015) {

		this.in0015 = in0015;
	}

	public String getSort() {

		return sort;
	}

	public void setSort(String sort) {

		this.sort = sort;
	}

	public void setParentIn0002(String parentIn0002) {

		this.parentIn0002 = parentIn0002;
	}

	public String getParentIn0003() {

		return parentIn0003;
	}

	public void setParentIn0003(String parentIn0003) {

		this.parentIn0003 = parentIn0003;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0004() {

		return parentIn0004;
	}

	public void setParentIn0004(Date parentIn0004) {

		this.parentIn0004 = parentIn0004;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0004Start() {

		return parentIn0004Start;
	}

	public void setParentIn0004Start(Date parentIn0004Start) {

		this.parentIn0004Start = parentIn0004Start;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0004End() {

		return parentIn0004End;
	}

	public void setParentIn0004End(Date parentIn0004End) {

		this.parentIn0004End = parentIn0004End;
	}


	public void setParentIn0005(Date parentIn0005) {

		this.parentIn0005 = parentIn0005;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0005Start() {

		return parentIn0005Start;
	}

	public void setParentIn0005Start(Date parentIn0005Start) {

		this.parentIn0005Start = parentIn0005Start;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0005End() {

		return parentIn0005End;
	}

	public void setParentIn0005End(Date parentIn0005End) {

		this.parentIn0005End = parentIn0005End;
	}



	public void setParentIn0006(String parentIn0006) {

		this.parentIn0006 = parentIn0006;
	}



	public void setParentIn0007(String parentIn0007) {

		this.parentIn0007 = parentIn0007;
	}



	public String getParentIn0006() {

		return parentIn0006;
	}

	public String getParentIn0007() {

		return parentIn0007;
	}

	public String getParentIn0008() {

		return parentIn0008;
	}

	public void setParentIn0008(String parentIn0008) {

		this.parentIn0008 = parentIn0008;
	}

	public String getParentIn0009() {

		return parentIn0009;
	}

	public void setParentIn0009(String parentIn0009) {

		this.parentIn0009 = parentIn0009;
	}

	public String getParentIn0010() {

		return parentIn0010;
	}

	public void setParentIn0010(String parentIn0010) {

		this.parentIn0010 = parentIn0010;
	}



	public void setParentIn0011(String parentIn0011) {

		this.parentIn0011 = parentIn0011;
	}

	public Date getParentIn0011Start() {

		return parentIn0011Start;
	}

	public void setParentIn0011Start(Date parentIn0011Start) {

		this.parentIn0011Start = parentIn0011Start;
	}

	public Date getParentIn0011End() {

		return parentIn0011End;
	}

	public void setParentIn0011End(Date parentIn0011End) {

		this.parentIn0011End = parentIn0011End;
	}

	/*
	 * public String getParentIn0012Text() {
	 * return DictUtils.getDictChname(parentIn0012, "YW_SF", "");
	 * }
	 * public String getParentIn0013Text() {
	 * return DictUtils.getDictChname(parentIn0013, "YW_PS_BLQK", "");
	 * }
	 */
	public String getParentIn0012() {

		return parentIn0012;
	}

	public void setParentIn0012(String parentIn0012) {

		this.parentIn0012 = parentIn0012;
	}



	public void setParentIn0014(String parentIn0014) {

		this.parentIn0014 = parentIn0014;
	}



	public void setParentIn0009Text(String parentIn0009Text) {

		this.parentIn0009Text = parentIn0009Text;
	}


	@ExcelField(align = 2,  sort = 100, type=0, title = "责任领导" , exportListType = "1")
	public String getParentIn0010ShowText() {
		return parentIn0010Text;
	}
	@ExcelField(align = 2,  sort = 200, type=0, title = "承办单位" , exportListType = "2")
	public String getParentIn0009ShowText() {
		return parentIn0009Text;
	}
	@ExcelField(align = 2,  sort = 300, type=0, title = "催办时间" , exportListType = "1,2")
	public String getRemindersDate() {

		return remindersDate;
	}



	@ExcelField(align = 2,  sort = 1000, type=0, title = "文件类型" , exportListType = "1,2,3")
	public String getParentIn0016Text() {

		return DictUtils.getDictValue(parentIn0016, "YW_PS_WJLX", "");
	}

	@ExcelField(align = 2,  sort = 1100, type=0, title = "批示领导" , exportListType = "1,2")
	public String getParentIn0017ShowText() {
		return DictUtils.getDictValue(parentIn0017, "YW_PS_LY", "");
	}

	@ExcelField(align = 2,  sort = 2000, type=0, title = "来源" , exportListType = "3")
	public String getParentIn0017Text() {

		return DictUtils.getDictValue(parentIn0017, "YW_PS_LY", "");
	}

	@ExcelField(align = 2,  sort = 3000, type=0, title = "文件名", exportListType = "1,2,3")
	public String getParentIn0001() {
		return parentIn0001;
	}

	public void setParentIn0001(String parentIn0001) {
		this.parentIn0001 = parentIn0001;
	}

	@ExcelField(align = 2,  sort = 4000, type=0, title = "文件号" , exportListType = "1,2,3")
	public String getParentIn0002() {
		return parentIn0002;
	}

	@ExcelField(align = 2,  sort = 5000, type=0, title = "批示内容及日期" , exportListType = "1,2,3")
	public String getParentIn0003Text() {
		String show = "";
		if (StringUtils.isNotBlank(parentIn0003)) {
			show += this.parentIn0003;
		}
		if (parentIn0004 != null) {
			show += DateUtils.formatDate(this.parentIn0004);
		}
		return show;
	}

	@ExcelField(align = 2,   sort = 6000, type=0, title = "收件日期" ,exportListType = "3")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getParentIn0005() {
		return parentIn0005;
	}

	@ExcelField(align = 2,  sort = 7000, type=0, title = "按轻重分类" ,exportListType = "3")
	public String getParentIn0006Text() {
		return DictUtils.getDictValue(parentIn0006, "YW_PS_FL_QZ", "");
	}

	@ExcelField(align = 2,  sort = 8000, type=0, title = "按要求分类" ,exportListType = "3")
	public String getParentIn0007Text() {
		return DictUtils.getDictValue(parentIn0007, "YW_PS_FL_YQ", "");
	}

	@ExcelField(align = 2,  sort = 9000, type=0, title = "按内容分类" ,exportListType = "3")
	public String getParentIn0008Text() {
		return DictUtils.getDictValue(parentIn0008, "YW_PS_FL_NR", "");
	}

	@ExcelField(align = 2,  sort = 10000, type=0, title = "备注" ,exportListType = "3")
	public String getParentIn0018() {
		return parentIn0018;
	}

	@ExcelField(align = 2,  sort = 11000, type=0, title = "责任领导" , exportListType = "3")
	public String getParentIn0010Text() {
		return parentIn0010Text;
	}

	@ExcelField(align = 2,  sort = 12000, type=0, title = "承办单位" ,exportListType = "3")
	public String getParentIn0009Text() {
		return parentIn0009Text;
	}

	@ExcelField(align = 2,  sort = 13000, type=0, title = "催办日期" ,exportListType = "3")
	public String getParentIn0011() {
		return parentIn0011;
	}

	@ExcelField(align = 2,  sort = 14000, type=0, title = "是否超期", exportListType = "1,2,3")
	public String getParentIn0013Text() {
		return DictUtils.getDictValue(parentIn0013, "YW_SF", "");
	}

	@ExcelField(align = 2,  sort = 15000, type=0, title = "办理情况", exportListType = "1,2,3")
	public String getParentIn0012Text() {
		return DictUtils.getDictValue(parentIn0012, "YW_PS_BLQK", "");
	}

	@ExcelField(align = 2,  sort = 16000, type=0, title = "落实及上报情况",exportListType = "1,2,3")
	public String getParentIn0014() {
		return parentIn0014;
	}

	@ExcelField(align = 2,  sort = 17000, type=0, title = "专报", exportListType = "3")
	public String getParentIn0020Text() {
		return parentIn0020Text;
	}

	public void setParentIn0003Text(String parentIn0003Text) {

	}

	public String getParentIn0013() {

		return parentIn0013;
	}

	public void setParentIn0013(String parentIn0013) {

		this.parentIn0013 = parentIn0013;
	}





	public static long getSerialversionuid() {

		return serialVersionUID;
	}
}
