/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.DateUtils;
import com.tfkj.framework.system.dao.LogDao;
import com.tfkj.framework.system.entity.Log;

/**
 * 日志Service
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
	
	public Log get(String id) {
		return super.get(id);
	}
	
	public List<Log> findList(Log sysLog) {
		return super.findList(sysLog);
	}
	
	public Page<Log> findPage(Page<Log> page, Log sysLog) {
		return super.findPage(page, sysLog);
	}
	
	@Transactional(readOnly = false)
	public void save(Log sysLog) {
		super.save(sysLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(Log sysLog) {
		super.delete(sysLog);
	}
	
	/**
	 * 搜索框日志的起止时间
	 * 
	 * @param log
	 * @param pageParam
	 * @return
	 */
	public Log logDate(Log log) {
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return log;

	}

}
