/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.BaseController;
import com.tfkj.framework.system.entity.DictType;
import com.tfkj.framework.system.service.DictTypeService;

/**
 * 字典分类/类型Controller
 * 
 * @author sunxuelong
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dictType")
public class DictTypeController extends BaseController {

	@Autowired
	private DictTypeService dictTypeService;

	@ModelAttribute
	public DictType get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return dictTypeService.get(id);
		} else {
			return new DictType();
		}
	}

	/**
	 * 列表页
	 * 
	 * @param dictType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "list" })
	public String list(DictType dictType, Model model) {
		dictType.setParentIds("0");
		model.addAttribute("list", dictTypeService.findList(dictType));
		return "system/dictType/dictTypeList";
	}

	/**
	 * form页
	 * 
	 * @param dict
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(DictType dictType, Model model) {
		model.addAttribute("DictType", dictType);
		return "system/dictType/dictTypeForm";
	}

	/**
	 * 保存到数据库
	 * 
	 * @param dict
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(DictType dictType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dictType)) {
			return form(dictType, model);
		}
		dictTypeService.save(dictType);
		addMessage(redirectAttributes, "保存'" + dictType.getChname() + "'成功");
		return "redirect:" + adminPath + "/sys/dictType/list?id=" + dictType.getParentId();
	}

	/**
	 * 删除
	 * 
	 * @param dictType
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String delete(DictType dictType, RedirectAttributes redirectAttributes) {
		dictTypeService.delete(dictType);
		addMessage(redirectAttributes, "删除" + dictType.getChname() + "成功");
		return "redirect:" + adminPath + "/sys/dictType/list?id=" + dictType.getParentId() + "&parentIds=" + dictType.getParentIds();
	}

	/**
	 * 树形菜单
	 * 
	 * @param type
	 * @param response
	 * @return
	 */
	//@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<DictType> list = dictTypeService.findAllList();
		for (int i = 0; i < list.size(); i++) {
			DictType e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("pIds", e.getParentIds());
			map.put("name", e.getChname());
			map.put("enname", e.getEnname());
			map.put("type", e.getType());
			if (e.getParentId() != null && "0".equals(e.getType())) {
				map.put("isParent", true);
			}
			mapList.add(map);
		}
		return mapList;
	}
}
