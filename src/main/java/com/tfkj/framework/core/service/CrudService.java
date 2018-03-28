/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tfkj.framework.core.exception.ServiceCheckedException;
import com.tfkj.framework.core.exception.ServiceRuntimeException;
import com.tfkj.framework.core.persistence.BaseEntity;
import com.tfkj.framework.core.persistence.CrudDao;
import com.tfkj.framework.core.persistence.DataEntity;
import com.tfkj.framework.core.persistence.Page;
import com.tfkj.framework.core.utils.StringUtils;
import com.tfkj.framework.core.web.page.PageParam;
import com.tfkj.framework.core.web.page.PageResult;

/**
 * Service基类
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     * @param entity
     * @param pageParam
     * @return
     */
    public PageResult<T> findPage(T entity, PageParam pageParam) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        entity.getSqlMap().put("dsf", dataScopeFilter(entity.getCurrentUser(), "o", "u"));
        PageHelper.offsetPage(pageParam.getOffset(), pageParam.getLimit());
        if (!StringUtils.isBlank(pageParam.getOrderBy())) {
            PageHelper.orderBy(pageParam.getOrderBy());
        }
        PageInfo<T> pageInfo = new PageInfo<T>(dao.findList(entity));
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(pageInfo.getList());
        return pageResult;
    }

    /**
     * 不分页查询数据
     * @param entity
     * @param pageParam
     * @return
     */
    public PageResult<T> findNoPage(T entity, PageParam pageParam) {
        if (!StringUtils.isBlank(pageParam.getOrderBy())) {
            PageHelper.orderBy(pageParam.getOrderBy());
        }
        PageInfo<T> pageInfo = new PageInfo<T>(dao.findList(entity));
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(pageInfo.getList());
        return pageResult;
    }

    /**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}
    
    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param params
     * @return
     * @throws ServiceRuntimeException
     */
    @Transactional(readOnly = false)
    public String deleteAll(String[] idsArray) throws ServiceCheckedException {
        return deleteAll(idsArray, null);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param params
     * @return
     * @throws ServiceRuntimeException
     */
    @Transactional(readOnly = false)
    public String deleteAll(String[] idsArray, Map<String, Object> params) throws ServiceCheckedException {
        params = params == null ? params = new HashMap<String, Object>() : params;
        params.put("ids", StringUtils.arrayToSql(idsArray));
        params.put("delFlag", BaseEntity.DEL_FLAG_DELETE);
        int res = dao.updateByIds(params);
        if (res == 0 || res != idsArray.length) {
            throw new ServiceCheckedException("存在不能删除的记录！");
        }
        return SERVICE_SUCCESS;
    }

}
