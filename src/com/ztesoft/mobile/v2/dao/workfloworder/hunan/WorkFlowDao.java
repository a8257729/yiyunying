package com.ztesoft.mobile.v2.dao.workfloworder.hunan;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.exception.DataAccessException;

public interface WorkFlowDao
{
	/**
	 * 锟斤拷签锟斤拷锟斤拷锟紸RRIVE_SIGN锟斤拷荼锟斤拷锟�?	 * @param data
	 * @throws DataAccessException
	 */
	public void insertArriveSign(Map<String,Object> data) throws DataAccessException;
	
	/**
	 * 实时锟斤拷锟斤拷锟疥到锟斤拷MOBILE_STAFF_POSITION
	 * @param data
	 * @throws DataAccessException
	 */
    public void insertMobileStaffPosition(Map<String,Object> data) throws DataAccessException;
    
    /**
	 * 锟斤拷锟斤拷没锟斤拷锟斤拷只锟斤拷锟斤拷锟饺★拷没锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param userId
	 *            锟矫伙拷锟斤拷锟街伙拷锟�?
	 * @return
	 * @throws DataAccessException 
	 */
	public Map getPasswordByUserName(String userName) throws DataAccessException;
	
	/**
	 * 锟睫革拷锟矫伙拷锟斤拷锟斤拷
	 * @param username
	 * @param newPassword
	 * @throws DataAccessException
	 */
	public void updateUserPasswordByName(String username, String newPassword)
			throws DataAccessException;
	
	
	/**
	 * 
	 * 鑾峰彇閫�崟鍘熷�?
	 * 
	 *           
	 * @return
	 * @throws DataAccessException 
	 */
	public List getCallBackReason() throws DataAccessException;
	
	/**
	 * 
	 * 工单详情查询资源信息
	 * 
	 *           
	 * @return
	 * @throws DataAccessException 
	 */
	public List getResourceInfoList(String workOrderId) throws Exception;
	
	public String getSAInfoList(String username, Long jobId,
			Integer pageSize, Integer pageIndex) throws Exception;

	public String getZdInfoList(String username, Long jobId,
								Integer pageSize, Integer pageIndex) throws Exception;


	public String getZDInfoList(String username, Long jobId,
								Integer pageSize, Integer pageIndex) throws Exception;


	/**
	 * 
	 * 工单反�F描述原因信息
	 * 
	 *           
	 * @return
	 * @throws DataAccessException 
	 */
	public List getFCallBackReason(String sql) throws Exception;
	
	/**
	 * 入库反�F在�?原因
	 * @param data
	 * @throws DataAccessException
	 */
    public void insertFCallBackReason(Map<String,Object> data) throws DataAccessException;

}
