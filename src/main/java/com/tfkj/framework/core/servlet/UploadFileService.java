package com.tfkj.framework.core.servlet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tfkj.business.attachment.entity.TblCommonAttachment;
import com.tfkj.business.attachment.service.TblCommonAttachmentService;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.utils.FileUtils;
import com.tfkj.framework.core.utils.IdGen;
import com.tfkj.framework.core.web.Servlets;

/**
 * @Description: 附件上传service
 * @author gaowei
 * @date 2017年8月25日
 */
@Service
@Transactional(readOnly = true)
public class UploadFileService {

	@Autowired
	private TblCommonAttachmentService tblCommonAttachmentService;
	
	@Transactional(readOnly = false)
	public Map<String, Object> upPhoto(MultipartFile file, HttpServletRequest request, HttpServletResponse response, String id) {
		TblCommonAttachment attach = new TblCommonAttachment();
		/* Json json=new Json(); */
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
			// 文件保存目录路径
			String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_COPY_URL + "/" + "image";
			// 判断文件夹是否存在
			File inbox = new File(realPath);
			if (!inbox.exists()) {
				FileUtils.createDirectory(FileUtils.path(realPath));
			}
			// 最大文件大小
			long maxSize = Long.parseLong(Global.getConfig("photoSize"));
			if (!ServletFileUpload.isMultipartContent(request)) {
				map.put("message", "请选择文件");
				map.put("success", false);
				return map;
			}
			// 检查文件大小
			if (file.getSize() > maxSize) {
				map.put("message", Global.getConfig("photoSizeMessage"));
				map.put("success", false);
				return map;
			}
			String originFileName = file.getOriginalFilename();
			String suffix = originFileName.indexOf(".") != -1 ? originFileName.substring(originFileName.lastIndexOf("."), originFileName.length()) : null;
			String str = IdGen.uuid() + "";
			String newFileName = str + suffix;
			/*
			 * String dirName = request.getParameter("dir");
			 * if (dirName == null) {
			 * dirName = "image";
			 * }
			 */
			// 检查扩展名
			String photoType = Global.getConfig("photoType");
			String[] types = photoType.split(",");
			if (!Arrays.<String> asList(types).contains(suffix)) {
				map.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + photoType + "格式。");
				map.put("success", false);
				return map;
			}
			if (suffix != null) {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFileName));
				String returnPath = Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_COPY_URL + "/" + "image" + "/" + newFileName;
				map.put("success", true);
				map.put("returnPath", returnPath);
				//插入附件信息
				attach.setFileName(originFileName);
				attach.setSaveFilename(newFileName);
				attach.setFilePath(realPath);
				attach.setFileSize(file.getSize());
				attach.setFileType("0");
				tblCommonAttachmentService.save(attach);
				
				return map;
			} else {
				map.put("message", "不能识别该文件 或文件已损坏");
				map.put("success", false);
				return map;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			return map;
		}
	}

	@Transactional(readOnly = false)
	public Map<String, Object> upFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response, String id) {
		TblCommonAttachment attach = new TblCommonAttachment();
		/* Json json=new Json(); */
		Map<String, Object> map = new HashMap<String, Object>(16);
		try {
			String str = IdGen.uuid() + "";
			// 文件保存目录路径
			String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_COPY_URL + "/" + "file"+"/"+str;
			// 判断文件夹是否存在
			File inbox = new File(realPath);
			if (!inbox.exists()) {
				FileUtils.createDirectory(FileUtils.path(realPath));
			}
			// 最大文件大小
			long maxSize = Long.parseLong(Global.getConfig("fileSize"));
			if (!ServletFileUpload.isMultipartContent(request)) {
				map.put("message", "请选择文件");
				map.put("success", false);
				return map;
			}
			// 检查文件大小
			if (file.getSize() > maxSize) {
				map.put("message", Global.getConfig("fileSizeMessage"));
				map.put("success", false);
				return map;
			}
			String originFileName = file.getOriginalFilename();
			String suffix = originFileName.indexOf(".") != -1 ? originFileName.substring(originFileName.lastIndexOf("."), originFileName.length()) : null;

			String showFileName =originFileName.indexOf("/") !=-1?
	                   originFileName.substring(originFileName.lastIndexOf("/"), originFileName.length()):originFileName;
			/*
			 * String dirName = request.getParameter("dir");
			 * if (dirName == null) {
			 * dirName = "image";
			 * }
			 */
			// 检查扩展名
			String fileType = Global.getConfig("fileType");
			String[] types = fileType.split(",");
			if (!Arrays.<String> asList(types).contains(suffix)) {
				map.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + fileType + "格式。");
				map.put("success", false);
				return map;
			}
			if (suffix != null) {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, showFileName));
				String returnPath = Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_COPY_URL + "/" + "file" + "/" +str+ "/"+showFileName;
				Date day=new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String newTime=df.format(day).toString();
               
                //插入附件信息
				attach.setFileName(originFileName);
				attach.setSaveFilename(showFileName);
				attach.setFilePath(realPath);
				attach.setFileSize(file.getSize());
				attach.setFileType("1");
				tblCommonAttachmentService.save(attach);
                
                map.put("newTime", newTime);
				map.put("success", true);
				map.put("returnPath", returnPath);
				map.put("showName", showFileName);
				map.put("fileId", attach.getId());
				return map;
			} else {
				map.put("message", "不能识别该文件 或文件已损坏");
				map.put("success", false);
				return map;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			return map;
		}
	}
}
