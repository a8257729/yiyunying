package com.ztesoft.android.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.DateHelper;

public class SystemInfoDAOImpl extends BaseDAOImpl implements SystemInfoDAO{
	private static final String TABLE_NAME = "MOBILE_PHONE_INFO";
	private static final String STAFF_TABLE_NAME = "MOBILE_LOGIN_INFO";
	public void insert(JSONObject device) {
		try {
			Map paramMap = new HashMap();
			paramMap.put("phoneModel", device.get("model"));
			paramMap.put("phoneScreen", device.get("screen"));
			List exitList = selExistByName(paramMap);
			//ÅÐ¶ÏÊÇ·ñ´æÔÚ
			if(exitList.size()==0){
				String patternStr = "PHONE_ID:phoneId,PHONE_SCREEN:phoneScreen,PHONE_MODEL:phoneModel";
				Long nextId = getPKFromMem(TABLE_NAME, 9);
				paramMap.put("phoneId", nextId);		
				dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
			}
			System.out.println("paramMap---> "+paramMap.toString());
			System.out.println("device---> "+device.toString());
			insertStaffInfo(device);
		} catch (DataAccessException e) {
		
		}
	}
	
	
	public void insertStaffInfo(JSONObject jsonData) {
		Map paramMap = new HashMap();
		paramMap.put("phoneModel", jsonData.get("model"));
		paramMap.put("staffId", Integer.parseInt(jsonData.get("staffId")+""));
		paramMap.put("systemVersion", jsonData.get("version"));
//		String dateTime = TimeUtil.format(new Date(),"yyyy-MM-dd hh:mm:ss");  
//		paramMap.put("startDate", DateHelper.parse(dateTime));
//		paramMap.put("endDate", DateHelper.parse(dateTime));
		String patternStr = "ID:id,PHONE_MODEL:phoneModel,STAFF_ID:staffId,SYSTEM_VERSION:systemVersion";
		try {
			Long nextId = getPKFromMem(STAFF_TABLE_NAME, 9);
			paramMap.put("id", nextId);
			dynamicInsertByMap(paramMap, STAFF_TABLE_NAME, patternStr);
		} catch (DataAccessException e) {
		}
		
	}

	
	public void updateStaffInfo(String jsonPara) throws DataAccessException{
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String updateType = jsonObject.get("updateType")==null?"-1":jsonObject.getString("updateType");
		String staffId = jsonObject.get("staffId")==null?"-1":jsonObject.getString("staffId");
		String version = jsonObject.get("version")==null?"-1":jsonObject.getString("version");
		String sysCodeMap = jsonObject.get("sysCodeMap")==null?"-1":jsonObject.getString("sysCodeMap");
		String sql1="update MOBILE_LOGIN_INFO set end_date = sysdate,SYSTEM_CODE='"+sysCodeMap+"',SYSTEM_VERSION='"+version+"' where staff_id = "+staffId+" and start_date in (SELECT Max(mli2.start_date) FROM  MOBILE_LOGIN_INFO mli2 where mli2.staff_id = "+staffId+")";
		dynamicUpdateBySql(sql1);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PHONE_ID AS phoneId,  PHONE_SCREEN AS phoneScreen,  PHONE_MODEL AS phoneModel FROM MOBILE_PHONE_INFO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public List selExistByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT PHONE_ID AS phoneId from MOBILE_PHONE_INFO " +
							"WHERE PHONE_MODEL='"+MapUtils.getString(paramMap, "phoneModel")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	
	public void updateStaffImsi(String staffId,String imsi) {
	
		try {
			Map m = selExistAddress2(staffId);
			System.out.println("m.get--->>> "+m.get("address"));
			if(m.get("address")==null){
				String sql1="update UOS_STAFF set ADDRESS2='"+imsi+"' where staff_id = "+staffId+"";
				dynamicUpdateBySql(sql1);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		
	}
	public Map selExistAddress2(String staffId) throws DataAccessException {
		final String sqlStr ="SELECT ADDRESS2 as address FROM  UOS_STAFF mli2 where mli2.staff_id = "+staffId+"";
		return dynamicQueryObjectByMap(sqlStr, null, null);
	}
}

