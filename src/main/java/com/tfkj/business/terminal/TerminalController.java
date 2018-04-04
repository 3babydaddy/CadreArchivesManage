package com.tfkj.business.terminal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
