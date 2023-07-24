package com.ztesoft.mobile.service.handler;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.etl.util.DataBaseTime;

import com.ztesoft.mobile.message.dao.MobileMessageDAO;
import com.ztesoft.mobile.message.dao.MobileMessageDAOImpl;
import com.ztesoft.mobile.service.handler.AbstractHandler;

public class PublicMessageHandler extends AbstractHandler  {

	public static String TAG = PublicMessageHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(PublicMessageHandler.class);
	
	 
	@Override
	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		logger.debug("============handler传入参数: " + jsonPara);
		
		String method = null;
		List retList = null;
		Map retMap = null;
		JSONArray retArr = null;
		JSONObject retObj = null;
		String resultCode = null;
		
		//处理数据
		try{
			System.out.println("publicMessageHandler---------jsonPara="+jsonPara);
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
            Map _map = new HashMap();
            _map.put("jobId", jsonObject.optString("jobId"));
            _map.put("staffId", jsonObject.optString("staffId"));
            _map.put("orgId", jsonObject.optString("orgId"));
            _map.put("publicType", jsonObject.optString("publicType", ""));
            
            _map.put("pageIndex", 0);
            _map.put("pageSize", 20);
            
            System.out.println("publicMessageHandler---------_map="+_map);
			Map  returndata = getMobileMessageDAO().selByConn(_map);
			List resultList =  (List) MapUtils.getObject(returndata,"resultList");

			System.out.println("publicMessageHandler---------resultList="+resultList);
			retArr = toArrFormCable(resultList);
							
		
		}catch (JSONException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		//根据resultCode,组装数据,返回数据
		try{
			String newstr = "";

			JSONObject jo = new JSONObject();
			jo.put("result", "000");					
			jo.put("listdata", retArr);										
			newstr = jo.toString();

			System.out.println("publicMessageHandler---------newstr="+newstr);
			newstr = ZipUtil.compress(newstr);
			paramMap.put("response", newstr);
		}catch (JSONException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	}
	
	private MobileMessageDAO getMobileMessageDAO() {
		String daoName = MobileMessageDAOImpl.class.getName();
		return (MobileMessageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	public JSONArray toArrFormCable(List dataList){
		JSONArray retArr = new JSONArray();
		if( dataList==null || dataList.isEmpty() ) return retArr;
		try{
			Map t_m = null;
			for(int i=0; i<dataList.size(); i++){
				t_m = (Map) dataList.get(i);				
				JSONObject t_jo = new JSONObject();
				
				t_jo.put("title", MapUtils.getString(t_m, "title", " "));
				t_jo.put("content", MapUtils.getString(t_m, "content", " "));
				t_jo.put("createStaffName", MapUtils.getString(t_m, "createStaffName", " "));
				t_jo.put("publicType", MapUtils.getString(t_m, "publicType", " "));
				t_jo.put("createTime", MapUtils.getString(t_m, "createTime", " "));
				t_jo.put("publicObjectName", MapUtils.getString(t_m, "publicObjectName", " "));
				
				retArr.add(t_jo);
			}			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retArr;
	}
	
}
