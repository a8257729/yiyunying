package com.ztesoft.android.service;

import java.util.Map;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;

import net.sf.json.JSONObject;

public class NativeService {

	public String commonGetData(String toPage,String params){
		System.out.println("params11111111-----> "+params);
		JSONObject jsonObject = new JSONObject().fromObject(params);
		System.out.println("params-----> "+params);
		StringBuffer returnstr = new StringBuffer();
		String jsondata = getReason(jsonObject);

		JSONObject resultObject = new JSONObject();
		JSONObject listObject = new JSONObject();
		listObject.put("listdata", jsondata);
		resultObject.put("result", "000"); 
		resultObject.put("body", listObject);
		return resultObject.toString();

	}

	public String getReasonType(JSONObject jsonObject){

		//String type = jsonObject.getString("name1");
		String jsondata="";
		try {
			jsondata = getServiceDAO().getReasonType("0");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsondata;
	}

	public String getReason(JSONObject jsonObject){
		String jsondata="";
		String code = jsonObject.has("reasontype")?jsonObject.getString("reasontype"):"";
		System.out.println("code-----> "+code);
		if(code.equals("")||code.equals("-1")){
			jsondata = getReasonType(jsonObject);
		}else {
			System.out.println("code--222222---> "+code);
			try {
				jsondata = getServiceDAO().getReason(code);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("code--333333333---> "+code);
		return jsondata;
	}
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
