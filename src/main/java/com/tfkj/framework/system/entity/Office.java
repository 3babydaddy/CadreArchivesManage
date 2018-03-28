/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.persistence.TreeEntity;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.core.utils.excel.annotation.ExcelFields;
import com.tfkj.framework.system.utils.DictUtils;

/**
 * 单位Entity
 *
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;

	/** 导出excel列表类型 */
	private String exportListType;

	// 单位名称
	private String name;

	// 组织单位代码
	private String zzjgdm;

	// 单位性质
	private String jgxz;

	// 规范简称
	private String gfjc;

	// 组织人事部门
	private String zzrsbm;

	// 使用版本
	private String version;

	// 单位状态
	private String stateFlag;

	// 查询标记
	private String searchFlag;

	// 导出的操作
	private String caozuo;

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public String getCaozuo() {

		return caozuo;
	}

	public void setCaozuo(String caozuo) {

		this.caozuo = caozuo;
	}

	private List<String> childDeptList;// 快速添加子部门

	public Office() {
		super();
	}

	public Office(String id) {
		super(id);
	}

	@Override
	@ExcelField(align = 2, sort = -2, value = "sysVersion", title = "系统版本", exportListType = "14,15,16")
	public String getSysVersion() {

		return Global.getConfig("version");
	}

	@Length(min = 1, max = 100)
	@ExcelFields({ @ExcelField(align = 2, sort = -1, value = "id", title = "填报单位唯一标识", exportListType = "14"), @ExcelField(align = 2, sort = -1, value = "id", title = "单位唯一标识", exportListType = "16"),
	        @ExcelField(align = 2, sort = 0, value = "parent.id", title = "上级单位ID", exportListType = "14,16"),
	        @ExcelField(align = 2, sort = 1, value = "parentIds", title = "所有上级单位ID", exportListType = "14,16"),
	        @ExcelField(align = 2, sort = 2, value = "name", title = "填报单位", exportListType = "14"), @ExcelField(align = 2, sort = 2, value = "name", title = "单位名称", exportListType = "16") })
	public String getName() {

		return name;
	}

	@ExcelField(align = 2, sort = 1, title = "单位名称", exportListType = "15")
	public String getNameString() {

		return name;
	}

	@ExcelField(align = 2, sort = 2, title = "规范简称", exportListType = "14,15,16")
	public String getGfjc() {

		return gfjc;
	}

	@ExcelField(align = 2, sort = 3, title = "组织人事部门", exportListType = "14,15,16")
	public String getZzrsbm() {

		return zzrsbm;
	}

	@ExcelField(align = 2, sort = 4, title = "单位性质", dictType = "YW_JGXZ", exportListType = "14,16")
	public String getJgxz() {

		return jgxz;
	}

	@ExcelField(align = 2, sort = 4, title = "单位性质", exportListType = "15")
	public String getJgxzTextString() {

		return DictUtils.getDictValue(this.jgxz, "YW_JGXZ", "");
	}

	@ExcelFields({ @ExcelField(align = 2, sort = 5, title = "使用版本", dictType = "DWYH_VERSION", exportListType = "16"),
	        @ExcelField(align = 2, sort = 5, title = "使用版本", dictType = "DWYH_VERSION", exportListType = "14") })
	public String getVersion() {

		return version;
	}

	@ExcelField(align = 2, sort = 5, title = "使用版本", exportListType = "15")
	public String getVersionString() {

		if ("0".equals(version)) {
			return "网络版";
		} else {
			return "单机版";
		}
	}

	@ExcelFields({ @ExcelField(align = 2, sort = 6, title = "是否可用", dictType = "yes_no", exportListType = "16"),
	        @ExcelField(align = 2, sort = 6, title = "是否可用", dictType = "yes_no", exportListType = "14") })
	public String getStateFlag() {

		return stateFlag;
	}

	@ExcelField(align = 2, sort = 6, title = "是否可用", exportListType = "15")
	public String getStateFlagString() {

		if ("1".equals(stateFlag)) {
			return "是";
		} else {
			return "否";
		}
	}

	@Override
	@ExcelField(align = 2, sort = 7, title = "排序", exportListType = "14,15,16")
	public Integer getSort() {

		return sort;
	}

	// 单位性质
	public String getJgxzText() {

		return DictUtils.getDictValue(this.jgxz, "YW_JGXZ", "");
	}

	public void setJgxz(String jgxz) {

		this.jgxz = jgxz;
	}

	@Length(min = 0, max = 100)
	public String getZzjgdm() {

		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {

		this.zzjgdm = zzjgdm;
	}

	public List<String> getChildDeptList() {

		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {

		this.childDeptList = childDeptList;
	}

	@Override
	public Office getParent() {

		return parent;
	}

	@Override
	public void setParent(Office parent) {

		this.parent = parent;
	}

	@Override
	public String toString() {

		return name;
	}

	public void setGfjc(String gfjc) {

		this.gfjc = gfjc;
	}

	public void setZzrsbm(String zzrsbm) {

		this.zzrsbm = zzrsbm;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public void setStateFlag(String stateFlag) {

		this.stateFlag = stateFlag;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getSearchFlag() {

		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {

		this.searchFlag = searchFlag;
	}

	@Override
	@ExcelField(align = 2, sort = 10000, title = "备注", exportListType = "14")
	public String getRemarks() {

		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {

		this.remarks = remarks;
	}

	/*@Override
	@ExcelField(align = 2, sort = 10100, value = "createBy.id", title = "创建者唯一标识", exportListType = "14")
	public User getCreateBy() {

		return createBy;
	}

	@Override
	public void setCreateBy(User createBy) {

		this.createBy = createBy;
	}*/

	@Override
	@ExcelField(align = 2, sort = 10200, title = "创建时间", exportListType = "14")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getCreateDate() {

		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
	}

	/*@Override
	@ExcelField(align = 2, sort = 10300, value = "updateBy.id", title = "更新者唯一标识", exportListType = "14")
	public User getUpdateBy() {

		return updateBy;
	}*/

	/*@Override
	public void setUpdateBy(User updateBy) {

		this.updateBy = updateBy;
	}

	@Override
	@ExcelField(align = 2, sort = 10400, title = "更新时间", exportListType = "14")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getUpdateDate() {

		return updateDate;
	}*/

	@Override
	public void setUpdateDate(Date updateDate) {

		this.updateDate = updateDate;
	}

	@Override
	@ExcelField(align = 2, sort = 10500, title = "删除标记", exportListType = "14")
	public String getDelFlag() {

		return delFlag;
	}

	@Override
	public void setDelFlag(String delFlag) {

		this.delFlag = delFlag;
	}

	public String getExportListType() {

		return exportListType;
	}

	public void setExportListType(String exportListType) {

		this.exportListType = exportListType;
	}
}