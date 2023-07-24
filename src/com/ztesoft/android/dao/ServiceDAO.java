package com.ztesoft.android.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface ServiceDAO extends BaseDAO{
	public String getVersionNo() throws DataAccessException; //��ѯ�汾��
	public Map getPasswordByUserName(String userName) throws DataAccessException;
	public String getJobData(String staffId)throws DataAccessException;
	public String getMappingSession(String staffId) throws DataAccessException;
	
	public String getStaffMappingInfo(String staffId,String sysCode) throws DataAccessException;
	public String getOtherApkInfo() throws DataAccessException;//ҵ��ϵͳapk��Ϣ��ѯ
	public String getOtherApkMethodInfo() throws DataAccessException;//ҵ��ϵͳ��Ӧ�ķ���
	public String getMuneTabs(String staffId, Integer osType)throws DataAccessException;
	
	public String getMuneData(String jobId,String defaultJobId)throws SQLException;  //һ���˵�
	public List getSubMuneData(String parentId,String jobId,String defaultJobId, Integer osType)throws SQLException; //�����˵�
	public String getStrSubMuneData(String parentId,String jobId,String defaultJobId) throws SQLException;
	public Map getIntefaceInfoByTeach(String teachName)throws DataAccessException;
	public List getFiledByFormId(String formId)throws DataAccessException;
	public String getPriButByFormId(String formId,String jobId,String defaultJobId)throws DataAccessException;//��Ȩ��
	public String getPriButByFormId(String formId)throws DataAccessException;//��Ȩ��
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
	 * ���ݽӿ�ӳ�����ȥ��ȡӳ���ֶ���Ϣ
	 * @param mappingCode
	 * @return
	 * @throws DataAccessException
	 */
	public Map getIntefaceInfoByMappingCode(String mappingCode)throws DataAccessException;
	
	public String getReasonType(String code)throws DataAccessException;
	public String getReason(String code)throws DataAccessException;
	public String getReasonReaulstType(String code)throws DataAccessException;
	public void updateUserPasswordByName(String username, String newPassword)throws DataAccessException;
	
	//��������
	public Map getTestIntefaceData(String mappingCode) throws DataAccessException;
}
  