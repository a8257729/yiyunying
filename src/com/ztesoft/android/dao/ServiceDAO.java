package com.ztesoft.android.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface ServiceDAO extends BaseDAO{
	public String getVersionNo() throws DataAccessException; //查询版本号
	public Map getPasswordByUserName(String userName) throws DataAccessException;
	public String getJobData(String staffId)throws DataAccessException;
	public String getMappingSession(String staffId) throws DataAccessException;
	
	public String getStaffMappingInfo(String staffId,String sysCode) throws DataAccessException;
	public String getOtherApkInfo() throws DataAccessException;//业务系统apk信息查询
	public String getOtherApkMethodInfo() throws DataAccessException;//业务系统对应的方法
	public String getMuneTabs(String staffId, Integer osType)throws DataAccessException;
	
	public String getMuneData(String jobId,String defaultJobId)throws SQLException;  //一级菜单
	public List getSubMuneData(String parentId,String jobId,String defaultJobId, Integer osType)throws SQLException; //二级菜单
	public String getStrSubMuneData(String parentId,String jobId,String defaultJobId) throws SQLException;
	public Map getIntefaceInfoByTeach(String teachName)throws DataAccessException;
	public List getFiledByFormId(String formId)throws DataAccessException;
	public String getPriButByFormId(String formId,String jobId,String defaultJobId)throws DataAccessException;//有权限
	public String getPriButByFormId(String formId)throws DataAccessException;//无权限
	public Map getPasswordByPhoneNO(String userName) throws DataAccessException;
	public List getFiledNodeByFormId(String formId)throws DataAccessException;
	public String getFiledNodeStrByFormId(String formId)throws DataAccessException;
	
	public String getStatFiledByFormId(String formId)throws DataAccessException;
	public String getStatTransferByFormId(String formId)throws DataAccessException;
	
	public String getCommonLayout(String formId)throws DataAccessException; 
	public String getSearchFiled(String formId)throws DataAccessException;
	public String getSearchTab(String formId)throws DataAccessException;
	public String getSearchTab2(String formId)throws DataAccessException;
	public Map getIntefaceInfoByName(String teachName)throws DataAccessException;
	/**
	 * 根据接口映射编码去获取映射字段信息
	 * @param mappingCode
	 * @return
	 * @throws DataAccessException
	 */
	public Map getIntefaceInfoByMappingCode(String mappingCode)throws DataAccessException;
	
	public String getReasonType(String code)throws DataAccessException;
	public String getReason(String code)throws DataAccessException;
	public String getReasonReaulstType(String code)throws DataAccessException;
	public void updateUserPasswordByName(String username, String newPassword)throws DataAccessException;
	
	//测试数据
	public Map getTestIntefaceData(String mappingCode) throws DataAccessException;
}
  