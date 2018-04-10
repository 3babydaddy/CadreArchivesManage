/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.xml.sax.SAXException;

import com.tfkj.framework.core.mapper.JsonMapper;
import com.tfkj.framework.core.servlet.UploadFileService;

/**
 * @Description: 文件上传
 * @author gaowei
 * @date 2017年8月25日
 */
@Controller
@RequestMapping(value = "${adminPath}/file")
public class UploadFileController extends BaseController {

	@Autowired
	private UploadFileService fileService;

	/**
	 * 照片上传
	 *
	 * @param apkFile
	 * @param request
	 * @param id
	 * @return Json
	 */
	@RequestMapping(value = "/upPhoto")
	public void upPhoto(MultipartRequest request, HttpServletRequest hRequest, HttpServletResponse response, String id) throws IOException, ParserConfigurationException, SAXException {

		Map<String, Object> map = new HashMap<String, Object>();
		hRequest.getSession().getServletContext().getRealPath("");
		// 得到上传的文件
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (String key : fileMap.keySet()) {
			MultipartFile mFile = fileMap.get(key);
			mFile.getOriginalFilename();
			mFile.getContentType();
			map = fileService.upPhoto(mFile, hRequest, response, id);
		}
		String str = JsonMapper.toJsonString(map);
		renderString(response, str, "text/html");
	}

	/*	*//**
	       * @Title: upFile
	       * @Description: 文件上传
	       * @param @param fileName
	       * @param @param request
	       * @param @param response
	       * @param @param id 参数
	       * @return void 返回类型
	       * @throws
	       *//*
	         * @RequestMapping(value = "/upFile")
	         * public void upFile(@RequestParam("fileName") MultipartFile fileName, HttpServletRequest request, HttpServletResponse response, String id) {
	         * Map<String, Object> map = new HashMap<String, Object>();
	         * map = fileService.upFile(fileName, request, response, id);
	         * String str = JsonMapper.toJsonString(map);
	         * renderString(response, str, "text/html");
	         * }
	         */
	@RequestMapping(value = "/upFile", method = RequestMethod.POST)
	public void importRyxxZip(MultipartRequest request, HttpServletRequest hRequest, HttpServletResponse response, String id) throws IOException, ParserConfigurationException, SAXException {

		Map<String, Object> params = new HashMap<String, Object>();
		hRequest.getSession().getServletContext().getRealPath("");
		// 得到上传的文件
		Map<String, MultipartFile> fileMap = request.getFileMap();
		for (String key : fileMap.keySet()) {
			MultipartFile mFile = fileMap.get(key);
			mFile.getOriginalFilename();
			mFile.getContentType();
			params = fileService.upFile(mFile, hRequest, response, id);
		}
		String str = JsonMapper.toJsonString(params);
		renderString(response, str, "text/html");
	}
}
