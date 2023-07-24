package com.ztesoft.mobile.v2.dao.workform.hunan;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface HuNanDAO extends BaseDAO {
	public Map queryResourcesList(String staff_id ,String search_address,int pageIndex,int pageSize) throws DataAccessException;
	
	public List queryStaffGirdList(String staff_id) throws DataAccessException;
	
	public List queryDeviceTypeList(String staff_id) throws DataAccessException;
	
	public Map<Object, Object>  queryDeviceListByPage(String org_id,String res_type_id,String eqp_name,Integer pageSize, Integer pageIndex)  throws DataAccessException;
	
	public Map<Object, Object>  queryDevicePortListByPage(String eqp_id,String port_name,Integer pageSize, Integer pageIndex)  throws DataAccessException;

	public List queryDeviceOperationList(String state_id) throws DataAccessException;
	
	public Map<Object, Object> executeDeviceOperation(String staff_id,String port_id,String oper_id) throws Exception;
	
	public Map<Object, Object>  queryTeleRollInListByPage(String eqp_id,String tele_nbr,Integer pageSize, Integer pageIndex)  throws DataAccessException;
	
	public Map<Object, Object> executeDeviceRollInOperation(String staff_id,String port_id,String disseq) throws Exception;
	
	public Map<Object, Object> selPublicWorkOrderForRobByPage(String staffId, String username, Long jobId, Integer pageSize, Integer pageIndex)  throws DataAccessException;
	
	public Map<Object, Object> executeRodOrderOperation(String workOrderID,String staffId) throws Exception;
	
	public Map<Object, Object> executeSaveUserInfoForNotifyOrder(JSONObject json) throws Exception;
	
	public Map<Object, Object> executeSaveLoginlogOperation(JSONObject json) throws Exception;

	public Map queryMaxUnBildTime(String accNbr) throws Exception;

	public Map queryBtnYy(String staffId) throws Exception;
	public Map queryBtnYySA(String staffId) throws Exception;
	//���������ѯ
	public Map selParams(String staffId) throws Exception;

	public List<Map<String,String>> selAllParams(String staffId) throws Exception;

	//��ͼ
	public int insertMap(String staffId,String orgId,String smx,String smy,String state) throws Exception;
	
}
