package com.tfkj.business.terminal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tfkj.business.attachment.entity.TblCommonAttachment;
import com.tfkj.business.attachment.service.TblCommonAttachmentService;
import com.tfkj.business.borrow.entity.TblBorrowArchives;
import com.tfkj.business.consult.entity.TblConsultArchives;
import com.tfkj.business.rollin.entity.TblRollIn;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.framework.core.config.Global;
import com.tfkj.framework.core.utils.FileUtils;
import com.tfkj.framework.core.utils.IdGen;
import com.tfkj.framework.core.web.Servlets;

/**
 * TerminalController 终端控制器
 * 
 * @author haiwang.wang
 * @version 2018-04-04
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/terminal/")
public class TerminalController {

	@Autowired
	private TblCommonAttachmentService tblCommonAttachmentService;
	
	private static final Logger logger = LoggerFactory.getLogger(TerminalController.class);

	/**
	 * 查阅档案录入终端界面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("consult")
	public String consult(TblConsultArchives tblConsultArchives, Model model) {
		model.addAttribute("tblConsultArchives", tblConsultArchives);
		return "business/terminal/consult";
	}
	/**
	 * 借阅档案录入终端界面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("borrowArchives")
	public String borrowArchives(TblBorrowArchives tblBorrowArchives, Model model) {
		
		model.addAttribute("tblBorrowArchives", tblBorrowArchives);
		return "business/terminal/borrowArchives";
	}
	/**
	 * 转入档案录入终端界面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("rollInArchives")
	public String rollInArchives(TblRollIn tblRollIn, Model model) {
		
		model.addAttribute("tblRollIn", tblRollIn);
		return "business/terminal/rollInArchives";
	}
	/**
	 * 零散材料录入终端界面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("scatteredFiles")
	public String scatteredFiles(TblScatteredFiles tblScatteredFiles, Model model) {
		
		model.addAttribute("tblScatteredFiles", tblScatteredFiles);
		return "business/terminal/scatteredFiles";
	}
	
	@RequestMapping("success")
	public String success(Model model) {
		
		return "business/terminal/success";
	}
	/**
	 * 根据传入图片base64 生成图片
	 * 
	 * @param imgBase64Str
	 * @return
	 */
	@RequestMapping("createImg")
	@ResponseBody
	public Map<String, Object> createImg(@RequestParam("imgBase64Str") String imgBase64Str) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("createImg str --> [{}]", imgBase64Str);
			TblCommonAttachment attach = new TblCommonAttachment();
			// 对字节数组字符串进行Base64解码并生成图片
			// Base64解码
			String[] split = imgBase64Str.split(",");
			byte[] b = Base64.decodeBase64(split != null ? split[1] : "");
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			// TODO: 图片生成后上传到指定位置，并保存附件
			
			// 文件保存目录路径
			String newFileName = IdGen.uuid() + ".jpg";
			String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_COPY_URL + "/" + "image";
			// 判断文件夹是否存在
			File inbox = new File(realPath);
			if (!inbox.exists()) {
				FileUtils.createDirectory(FileUtils.path(realPath));
			}
			
			OutputStream out = new FileOutputStream(realPath+"/"+newFileName);
			out.write(b);
			out.flush();
			out.close();
			
			//插入附件信息
			attach.setFileName(newFileName);
			attach.setSaveFilename(newFileName);
			attach.setFilePath(realPath);
			attach.setFileSize((long)b.length);
			tblCommonAttachmentService.save(attach);
			String returnPath = Servlets.getRequest().getContextPath() + Global.USERFILES_BASE_COPY_URL + "/" + "image" + "/" + newFileName;
			map.put("result", "true");
			map.put("path", returnPath);
			return map;
		} catch (Exception e) {
			map.put("result", "false");
			return map;
		}
	}
	
	/**
	 * 示例页面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("demo")
	public String demo(Model model) {
		return "business/terminal/demo";
	}
	
	/**
	 * 拍照页面
	 * 
	 * @return 页面资源路径
	 */
	@RequestMapping("camera")
	public String camera(Model model) {
		return "business/terminal/camera";
	}

}
