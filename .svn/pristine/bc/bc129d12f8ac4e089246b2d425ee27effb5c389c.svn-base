package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface MobMunePrDAO extends BaseDAO{

	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public Map selByDisplayInedx(Map paramMap)throws DataAccessException;
	public List selByName(Map paramMap) throws DataAccessException;
	public Map getSubMenusById(int moduleId)throws Exception;  
	public Map getSubIndexById(int moduleId)throws Exception; 
	public Map getAllParentPrivData(int moduleId,int jobId, int defaultJobId)throws Exception;
	public Map getAllHasPrivData(int jobId, int defaultJobId)throws Exception;
	public Map getAllSubPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception;
	public Map getAllRolePrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception; //角色权限可选择
	public Map getAllHasRolePrivData(int roleId)throws Exception;                     //已有角色
	public Map getAllButPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception; //可配置操作按钮权限
	public Map getHasButPrivData(int jobId, int defaultJobId)throws Exception;  //职位可配置操作权限
	
	public Map getAllRoleButPrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception;  //角色可配置操作权限
	public Map getHasRoleButPrivData(int roleId)throws Exception;//角色已有操作权限
	
	public Map getMenuRank()throws Exception;//获取被分配权限的菜单的排行榜
}
