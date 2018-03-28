package com.tfkj.business.ywgl.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.business.web.constants.YwglConstantsUtil;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.system.utils.DictUtils;

public class Instructions extends DataEntity<Instructions> {

	private static final long serialVersionUID = 1L;

	/**
	 * 任务年度
	 */
	private String taskYear;

	/**
	 * 排序序号
	 */
	private String sort;

	/**
	 * 子级数据--单位
	 */
	List<InstructionsElement> elementList;

	/**
	 * 子级数据--单位
	 */
	List<InstructionsElement> officeElementList;

	/**
	 * 子级数据--领导
	 */
	List<InstructionsElement> leaderElementList;

	private String officeIds;

	private String leaderIds;

	/**
	 * 批示类型
	 */
	private String type;

	/**
	 * 文件名
	 */
	private String in0001;

	/**
	 * 文件号
	 */
	private String in0002;

	/**
	 * 批示内容
	 */
	private String in0003;

	/**
	 * 批示日期
	 */
	private Date in0004;

	private Date in0004Start;

	private Date in0004End;

	/**
	 * 收件日期
	 */
	private Date in0005;

	private Date in0005Start;

	private Date in0005End;

	/**
	 * 按轻重分类
	 */
	private String in0006;

	/**
	 * 按要求分类
	 */
	private String in0007;

	/**
	 * 按内容分类
	 */
	private String in0008;

	/**
	 * 责任领导IDS
	 */
	private String in0009;

	/**
	 * 承办单位IDS
	 */
	private String in0010;

	/**
	 * 催办日期
	 */
	private Date in0011;

	private Date in0011Start;

	private Date in0011End;

	/**
	 * 是否超期
	 */
	private String in0012;

	/**
	 * 办理情况
	 */
	private String in0013;

	/**
	 * 落实及上报情况
	 */
	private String in0014;

	private Date in0015;

	/**
	 * 文件类型
	 */
	private String in0016;

	/**
	 * 来源
	 */
	private String in0017;

	/**
	 * 内容下备注
	 */
	private String in0018;

	/**
	 * 标记
	 */
	private String in0019;

	/**
	 * 关联专报ID
	 */
	private String in0020;

	private String in0020Text;

	/**
	 * 批示文件-附件
	 */
	private String in0021;

	private String in0022;

	private String in0023;

	private String in0024;

	private String in0025;

	private String in0026;

	private String in0027;

	private String in0028;

	private String in0029;

	private String in0030;

	private String in0031;

	private String in0032;

	private String in0033;

	private String in0034;

	private String in0035;

	private String in0036;


	private String relationType;

	private String unqId ;

	private String remindersDate;

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getUnqId() {
		return unqId;
	}

	public void setUnqId(String unqId) {
		this.unqId = unqId;
	}

	public String getRemindersDate() {
		return remindersDate;
	}

	public void setRemindersDate(String remindersDate) {
		this.remindersDate = remindersDate;
	}

	public Date getIn0004Start() {

		return in0004Start;
	}

	public void setIn0004Start(Date in0004Start) {

		this.in0004Start = in0004Start;
	}

	public Date getIn0004End() {

		return in0004End;
	}

	public void setIn0004End(Date in0004End) {

		this.in0004End = in0004End;
	}

	public void setIn0015(Date in0015) {

		this.in0015 = in0015;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0005Start() {

		return in0005Start;
	}

	public void setIn0005Start(Date in0005Start) {

		this.in0005Start = in0005Start;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0005End() {

		return in0005End;
	}

	public void setIn0005End(Date in0005End) {

		this.in0005End = in0005End;
	}

	public Date getIn0011Start() {

		return in0011Start;
	}

	public void setIn0011Start(Date in0011Start) {

		this.in0011Start = in0011Start;
	}

	public Date getIn0011End() {

		return in0011End;
	}

	public void setIn0011End(Date in0011End) {

		this.in0011End = in0011End;
	}

	public String getOfficeNames() {

		return YwglConstantsUtil.getOfficeNames(in0010);
	}

	public void setOfficeNames(String officeNames) {

	}

	public String getLeaderNames() {

		return YwglConstantsUtil.getLeaderNames(in0009);
	}

	public void setLeaderNames(String leaderNames) {

	}

	public List<InstructionsElement> getOfficeElementList() {

		return officeElementList;
	}

	public void setOfficeElementList(List<InstructionsElement> officeElementList) {

		this.officeElementList = officeElementList;
	}

	public List<InstructionsElement> getLeaderElementList() {

		return leaderElementList;
	}

	public void setLeaderElementList(List<InstructionsElement> leaderElementList) {

		this.leaderElementList = leaderElementList;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
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

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0004() {

		return in0004;
	}

	public void setIn0004(Date in0004) {

		this.in0004 = in0004;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0005() {

		return in0005;
	}

	public void setIn0005(Date in0005) {

		this.in0005 = in0005;
	}

	public String getIn0006() {

		return in0006;
	}

	public void setIn0006(String in0006) {

		this.in0006 = in0006;
	}

	public String getIn0006Text() {

		return DictUtils.getDictValue(in0006, "YW_PS_FL_QZ", "");
	}

	public String getIn0012Text() {

		return DictUtils.getDictValue(in0012, "YW_SF", "");
	}

	public void setIn0012Text(String in0012Text) {

	}

	public void setIn0006Text(String in0006Text) {

	}

	public String getIn0007() {

		return in0007;
	}

	public void setIn0007(String in0007) {

		this.in0007 = in0007;
	}

	public String getIn0007Text() {

		return DictUtils.getDictValue(in0007, "YW_PS_FL_YQ", "");
	}

	public void setIn0007Text(String in0007Text) {

	}

	public String getIn0008() {

		return in0008;
	}

	public void setIn0008(String in0008) {

		this.in0008 = in0008;
	}

	public String getIn0008Text() {

		return DictUtils.getDictValue(in0008, "YW_PS_FL_NR", "");
	}

	public void setIn0008Text(String in0008Text) {

	}

	public String getIn0009() {

		return in0009;
	}

	public void setIn0009(String in0009) {

		this.in0009 = in0009;
	}

	public String getIn0009Text() {

		return YwglConstantsUtil.getLeaderNames(in0009);
	}

	public void setIn0009Text(String in0009Text) {

	}

	public String getIn0010() {

		return in0010;
	}

	public void setIn0010(String in0010) {

		this.in0010 = in0010;
	}

	public String getIn0010Text() {

		return YwglConstantsUtil.getOfficeNames(in0010);
	}

	public void setIn0010Text(String in0010Text) {

	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0011() {

		return in0011;
	}

	public void setIn0011(Date in0011) {

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

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getIn0015() {

		return in0015;
	}

	public String getIn0016() {

		return in0016;
	}

	public void setIn0016(String in0016) {

		this.in0016 = in0016;
	}

	public String getIn0017() {

		return in0017;
	}

	public void setIn0017(String in0017) {

		this.in0017 = in0017;
	}

	public String getIn0018() {

		return in0018;
	}

	public void setIn0018(String in0018) {

		this.in0018 = in0018;
	}

	public String getIn0019() {

		return in0019;
	}

	public void setIn0019(String in0019) {

		this.in0019 = in0019;
	}

	public String getIn0020() {

		return in0020;
	}

	public void setIn0020(String in0020) {

		this.in0020 = in0020;
	}

	public String getIn0021() {

		return in0021;
	}

	public void setIn0021(String in0021) {

		this.in0021 = in0021;
	}

	public String getIn0022() {

		return in0022;
	}

	public void setIn0022(String in0022) {

		this.in0022 = in0022;
	}

	public String getIn0023() {

		return in0023;
	}

	public void setIn0023(String in0023) {

		this.in0023 = in0023;
	}

	public String getIn0024() {

		return in0024;
	}

	public void setIn0024(String in0024) {

		this.in0024 = in0024;
	}

	public String getIn0025() {

		return in0025;
	}

	public void setIn0025(String in0025) {

		this.in0025 = in0025;
	}

	public String getIn0026() {

		return in0026;
	}

	public void setIn0026(String in0026) {

		this.in0026 = in0026;
	}

	public String getIn0027() {

		return in0027;
	}

	public void setIn0027(String in0027) {

		this.in0027 = in0027;
	}

	public String getIn0028() {

		return in0028;
	}

	public void setIn0028(String in0028) {

		this.in0028 = in0028;
	}

	public String getIn0029() {

		return in0029;
	}

	public void setIn0029(String in0029) {

		this.in0029 = in0029;
	}

	public String getIn0030() {

		return in0030;
	}

	public void setIn0030(String in0030) {

		this.in0030 = in0030;
	}

	public String getIn0031() {

		return in0031;
	}

	public void setIn0031(String in0031) {

		this.in0031 = in0031;
	}

	public String getIn0032() {

		return in0032;
	}

	public void setIn0032(String in0032) {

		this.in0032 = in0032;
	}

	public String getIn0033() {

		return in0033;
	}

	public void setIn0033(String in0033) {

		this.in0033 = in0033;
	}

	public String getIn0034() {

		return in0034;
	}

	public void setIn0034(String in0034) {

		this.in0034 = in0034;
	}

	public String getIn0035() {

		return in0035;
	}

	public void setIn0035(String in0035) {

		this.in0035 = in0035;
	}

	public String getIn0036() {

		return in0036;
	}

	public void setIn0036(String in0036) {

		this.in0036 = in0036;
	}

	public String getSort() {

		return sort;
	}

	public void setSort(String sort) {

		this.sort = sort;
	}

	public String getIn0020Text() {

		return in0020Text;
	}

	public void setIn0020Text(String in0020Text) {

		this.in0020Text = in0020Text;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public String getTaskYear() {

		return taskYear;
	}

	public List<InstructionsElement> getElementList() {

		return elementList;
	}

	public void setElementList(List<InstructionsElement> elementList) {

		this.elementList = elementList;
	}

	public String getOfficeIds() {

		return officeIds;
	}

	public void setOfficeIds(String officeIds) {

		this.officeIds = officeIds;
	}

	public String getLeaderIds() {

		return leaderIds;
	}

	public void setLeaderIds(String leaderIds) {

		this.leaderIds = leaderIds;
	}

	public void setTaskYear(String taskYear) {

		this.taskYear = taskYear;
	}

	public List<String> getIn0019List() {

		List<String> in0019List = new ArrayList<String>();
		if (StringUtils.isNotBlank(in0019)) {
			String[] idsArray = in0019.split(",");
			in0019List = Arrays.asList(idsArray);
		}
		return in0019List;
	}

	public void setIn0019List(List<String> in0019List) {

		String str = "";
		if (in0019List != null && in0019List.size() > 0) {
			for (int i = 0; i < in0019List.size(); i++) {
				if (i == in0019List.size() - 1) {
					str += in0019List.get(i);
				} else {
					str += in0019List.get(i) + ",";
				}
			}
		}
		this.in0019 = str;
	}
}
