/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.business.scattereds.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfkj.business.scattereds.dao.TblHandOverFilesDao;
import com.tfkj.business.scattereds.dao.TblScatteredFilesDao;
import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.service.CrudService;
import com.tfkj.framework.core.utils.StringUtils;

/**
 * 零散材料移交人员Service
 * @author tianzhen
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class TblScatteredFilesService extends CrudService<TblScatteredFilesDao, TblScatteredFiles> {

	@Autowired
	private TblHandOverFilesDao tblHandOverFilesDao;
	@Autowired
	private TblScatteredFilesDao tblScatteredFilesDao;
	
	
	public TblScatteredFiles get(String id) {
		TblScatteredFiles tblScatteredFiles = super.get(id);
		
		TblHandOverFiles overfile = new TblHandOverFiles();
		overfile.setMainId(id);
		tblScatteredFiles.setTblHandOverFilesList(tblHandOverFilesDao.findList(overfile));
		return tblScatteredFiles;
	}
	
	public List<TblScatteredFiles> findList(TblScatteredFiles tblScatteredFiles) {
		return super.findList(tblScatteredFiles);
	}
	
	public Page<TblScatteredFiles> findPage(Page<TblScatteredFiles> page, TblScatteredFiles tblScatteredFiles) {
		return super.findPage(page, tblScatteredFiles);
	}
	
	@Transactional(readOnly = false)
	public void save(TblScatteredFiles tblScatteredFiles) {
		super.save(tblScatteredFiles);
		for (TblHandOverFiles tblHandOverFiles : tblScatteredFiles.getTblHandOverFilesList()){
			if (tblHandOverFiles.getId() == null){
				continue;
			}
			if (TblHandOverFiles.DEL_FLAG_NORMAL.equals(tblHandOverFiles.getDelFlag())){
				if (StringUtils.isBlank(tblHandOverFiles.getId())){
					tblHandOverFiles.setMainId(tblScatteredFiles.getId());
					tblHandOverFiles.preInsert();
					tblHandOverFilesDao.insert(tblHandOverFiles);
				}else{
					tblHandOverFiles.preUpdate();
					tblHandOverFilesDao.update(tblHandOverFiles);
				}
			}else{
				tblHandOverFilesDao.delete(tblHandOverFiles);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TblScatteredFiles tblScatteredFiles, String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			String[] idArray = idStr.split(",");
			for(int i = 0; i < idArray.length; i++){
				tblScatteredFiles.setId(idArray[i]);
				super.delete(tblScatteredFiles);
				
				TblHandOverFiles overfile = new TblHandOverFiles();
				overfile.setMainId(idArray[i]);
				tblHandOverFilesDao.delete(overfile);	
			}
		}
	}
	
	public Page<TblHandOverFiles> findPersonPage(Page<TblHandOverFiles> page, TblHandOverFiles tblHandOverFiles) {
		
		tblHandOverFiles.setPage(page);
		page.setList(tblHandOverFilesDao.findList(tblHandOverFiles));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void saveScatteredInfo(TblScatteredFiles tblScatteredFiles) {
		super.save(tblScatteredFiles);
		for (TblHandOverFiles tblHandOverFiles : tblScatteredFiles.getTblHandOverFilesList()){
			tblHandOverFiles.setMainId(tblScatteredFiles.getId());
			tblHandOverFiles.preInsert();
			tblHandOverFilesDao.insert(tblHandOverFiles);
		}
	}
	
	public Page<TblScatteredFiles> findCountPage(Page<TblScatteredFiles> page, TblScatteredFiles tblScatteredFiles) {
		
		TblHandOverFiles handOver = new TblHandOverFiles();
		if(StringUtils.isNotBlank(tblScatteredFiles.getHandOverStr())){
			handOver.setName(tblScatteredFiles.getHandOverStr());
		}
		List<TblHandOverFiles> handOverList = tblHandOverFilesDao.queryHandOverList(handOver);
		tblScatteredFiles.setTblHandOverFilesList(handOverList);
		tblScatteredFiles.setPage(page);
		List<TblScatteredFiles> scatteredList = tblScatteredFilesDao.findCountList(tblScatteredFiles);
		//移交总人数
		int handOversNum = 0;
		//移交总材料数
		long filesNum = 0;
		for(int j = 0; j < scatteredList.size(); j++){
			TblScatteredFiles info = scatteredList.get(j);
			info.setXh((scatteredList.size()-j)+"");
			//移交人员字符串
			String handOverStr = "";
			//移交人数
			int handOverNum = 0;
			//移交材料数
			long fileNum = 0;
			for(int i = 0; i < handOverList.size(); i++){
				if((info.getId()).equals(handOverList.get(i).getMainId())){
					if(handOverStr == ""){
						handOverStr = handOverList.get(i).getName();
						fileNum = (handOverList.get(i).getOriginalNo() == null ? 0 : handOverList.get(i).getOriginalNo());
					}else{
						handOverStr += "," + handOverList.get(i).getName();
						fileNum += (handOverList.get(i).getOriginalNo() == null ? 0 : handOverList.get(i).getOriginalNo());
					}
					handOverNum++;
				}
			}
			handOversNum += handOverNum;
			filesNum += fileNum;
			info.setHandOverStr(handOverStr);
			info.setHandOverNum(handOverNum+"");
			info.setFileNum(fileNum+"");
		}
		TblScatteredFiles scatteredFiles = new TblScatteredFiles();
		scatteredFiles.setXh("合计");
		scatteredFiles.setHandOverNum(handOversNum+"");
		scatteredFiles.setFileNum(filesNum+"");
		scatteredList.add(scatteredFiles);
		Collections.reverse(scatteredList);  
		page.setList(scatteredList);
		return page;
	}
}