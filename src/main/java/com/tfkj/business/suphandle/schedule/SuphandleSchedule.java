package com.tfkj.business.suphandle.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tfkj.business.suphandle.dao.TblSuperviseHandleDao;
import com.tfkj.business.suphandle.entity.TblSuperviseHandle;

/**
 * 督查督办定时器
 * 进行倒计时的天数的更改
 * @author tianzhen
 * @version 2018-03-28
 */

@Component
@Lazy(false)  
public class SuphandleSchedule {

	@Autowired
	private TblSuperviseHandleDao tblSuperviseHandleDao;
	
	@Scheduled(cron = "0 0 0 0/1 * ?")
	public void execute() {
		long nd = 1000 * 24 * 60 * 60;
		List<TblSuperviseHandle> superList = tblSuperviseHandleDao.querySupHandleData();
		for(TblSuperviseHandle info : superList){
			long num = (info.getRaisedTime().getTime() - (new Date()).getTime())/nd;
			if(num < 0){
				info.setCountDown((long)0);
				info.setWaringStatus("R");
			}else if(num >= 0){
				info.setCountDown(num);
			}
			tblSuperviseHandleDao.update(info);
		}
	}
}
