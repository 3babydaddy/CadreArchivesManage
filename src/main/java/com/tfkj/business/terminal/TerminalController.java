package com.tfkj.business.terminal;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tfkj.business.consult.entity.TblConsultArchives;

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
	 * 根据传入图片base64 生成图片
	 * 
	 * @param imgBase64Str
	 * @return
	 */
	@RequestMapping("createImg")
	@ResponseBody
	public boolean createImg(String imgBase64Str) {
		try {
			logger.info("createImg str --> [{}]", imgBase64Str);
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
			
			String imgFilePath = "C:\\new.jpg";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
