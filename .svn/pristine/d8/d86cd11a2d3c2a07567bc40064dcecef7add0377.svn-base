package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MobileAppFuncDAO extends BaseDAO {

    public void insert(Map paramMap) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;
  //分页查询
    public Map pageSel(Map paramMap) throws DataAccessException;
    
    //通过app查询func的类路径
    public List selFunClass(Map paramMap) throws DataAccessException;
    
    //分页查询功能列表
    public Map selFunPage(Map paramMap) throws DataAccessException;
    //通过应用分类查询应用类型
    public List selClassForType(Map paramMap) throws DataAccessException;
    // 删除应用的时候，级联删除appFun 和 MOBILE_FUNC_MENU_RELA 还有MOBILE_MENU_CONFIG
    public void deleteAppFuncRelaConfig(Map paraMap) throws DataAccessException;
}
