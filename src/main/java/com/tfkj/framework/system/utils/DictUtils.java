/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tfkj.business.instructions.dao.InstructionsSupervisionDao;
import com.tfkj.business.ywgl.dao.InstructionsDao;
import com.tfkj.business.ywgl.entity.Instructions;
import com.tfkj.framework.core.mapper.JsonMapper;
import com.tfkj.framework.core.utils.CacheUtils;
import com.tfkj.framework.core.utils.SpringContextHolder;
import com.tfkj.framework.system.dao.DictDao;
import com.tfkj.framework.system.dao.LeaderDao;
import com.tfkj.framework.system.dao.OfficeDao;
import com.tfkj.framework.system.entity.Dict;
import com.tfkj.framework.system.entity.Leader;
import com.tfkj.framework.system.entity.Office;

/**
 * 字典工具类
 *
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {

	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	private static LeaderDao leaderDao = SpringContextHolder.getBean(LeaderDao.class);

	private static InstructionsDao instructionsDao = SpringContextHolder.getBean(InstructionsDao.class);

	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	private static InstructionsSupervisionDao supervisionDao = SpringContextHolder.getBean(InstructionsSupervisionDao.class);
	
	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static String getOfficeName(String code) {
		String name = "";
		if (StringUtils.isNotBlank(code)){
			String[] list = code.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i <list.length ; i++) {
				String item = list[i];
				Office office= officeDao.get(item);
				if (office!=null){
					sb.append(office.getName());
					if (i!=list.length-1){
						sb.append(",");
					}
				}
			}
			name = sb.toString();
		}
		return name;
	}
	public static Map<String,String> getRelationName(String code) {
		String name = "";
		String ids = "";
		int num = 0;
;		if (StringUtils.isNotBlank(code)){
			String[] list = code.split(",");
			for (int i = 0; i < list.length ; i++) {
				String item = list[i];
				Instructions instructions= instructionsDao.get(item);
				if (instructions!=null){
					if (StringUtils.isNotBlank(ids)){
						ids+=",";
					}
					ids += item;
					num ++;
					if (StringUtils.isBlank(name)){
						name+=instructions.getIn0001();
					}
				}
			}
			if (num>1){
				name+="等"+num+"个批示件";
			}

		}
		Map<String,String> map = new HashMap<>();
		map.put("name",name);
		map.put("ids",ids);
		return map;
	}
	public static String getDictName(String code,String type) {
		String name = "";
		if (StringUtils.isNotBlank(code)){
			String[] list = code.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i <list.length ; i++) {
				String item = list[i];
				Dict dict = new Dict();
				dict.setValue(item);
				dict.setType(type);
				List<Dict> dictList= dictDao.findList(dict);
				if (dictList!=null&&dictList.size()>0){
					sb.append(dictList.get(0).getLabel());
					if (i!=list.length-1){
						sb.append(",");
					}
				}
			}
			name = sb.toString();
		}
		return name;
	}

	public static String getLeadName(String code) {
		String name = "";
		if (StringUtils.isNotBlank(code)){
			String[] list = code.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i <list.length ; i++) {
				String item = list[i];
				Leader leaderParam=new Leader();
				leaderParam.setId(item);
				Leader leader= leaderDao.get(leaderParam);
				if (leader!=null){
					sb.append(leader.getL001());
					if (i!=list.length-1){
						sb.append(",");
					}
				}
			}
			name = sb.toString();
		}
		return name;
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
}
