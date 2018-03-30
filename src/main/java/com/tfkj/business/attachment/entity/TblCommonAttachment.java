/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.attachment.entity;

import org.hibernate.validator.constraints.Length;
import com.tfkj.framework.core.persistence.DataEntity;

/**
 * 附件Entity
 * @author tianzhen
 * @version 2018-03-28
 */
public class TblCommonAttachment extends DataEntity<TblCommonAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String saveFilename;		// 存储文件名
	private String filePath;		// 文件路径
	private Long fileSize;		// 文件大小
	private String fileType;		// 文件类型
	
	public TblCommonAttachment() {
		super();
	}

	public TblCommonAttachment(String id){
		super(id);
	}

	@Length(min=0, max=128, message="文件名称长度必须介于 0 和 128 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=128, message="存储文件名长度必须介于 0 和 128 之间")
	public String getSaveFilename() {
		return saveFilename;
	}

	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}
	
	@Length(min=0, max=200, message="文件路径长度必须介于 0 和 200 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Length(min=0, max=1, message="文件类型长度必须介于 0 和 1 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}