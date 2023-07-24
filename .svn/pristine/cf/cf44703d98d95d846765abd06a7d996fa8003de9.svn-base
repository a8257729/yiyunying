package com.ztesoft.mobile.v2.dao.menu;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MobileMenuDAO extends BaseDAO {

    public Map insert(Map paramMap) throws DataAccessException;

    //public void insertBatch(List paramMapList) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    //public void updateBatch(List paramMapList) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;

    /** 获取菜单分类 */
    public List queryMenuCatalog(Long staffId, Long jobId, Long defaultJobId, Integer osType) throws DataAccessException;

    public List querySubMenu(Long staffId, Long jobId, Long defaultJobId, Integer osType) throws DataAccessException;
    
	public List querySubMenu(Long menuCataLogId, Long staffId, Long jobId, Long defaultJobId,
			Integer osType) throws DataAccessException;
    
    public Map selClassByOsType(Map paramMap) throws DataAccessException;
    /**
     * mobile_app,mobile_app_func,mobile_menu,mobile_menu_config,mobile_func_menu_rela 联合查询
     * @param param
     * @return
     * @throws DataAccessException
     */
    public List getMenuAppClass(Map param)throws DataAccessException;

    
    public Map selByDisplayInedx(Map paramMap)throws DataAccessException;
    
    public List selByName(Map paramMap) throws DataAccessException ;
		//查询某个节点下最大的排序值
    public Map selByMenuIndex(Map paramMap) throws DataAccessException;
}