package com.ztesoft.android.client.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.BaseDataDAO;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class ReasonDAOImpl extends BaseDAOImpl implements BaseDataDAO{

	public List getDataByList(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getDataByMap(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDataByStr(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDataByStr(String teachName, String jsonParams, Map mapParams) {
		JSONObject jsonObject = new JSONObject().fromObject(jsonParams);
		System.out.println("params-----> "+jsonParams);
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
			jsondata = getReasonType("0");
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
			System.out.println("code--555---> "+code);
			try {
				jsondata = getReason(code);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("code--666---> "+code);
		return jsondata;
	}
	
	public String getReasonType(String obstructProdType)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		//	sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
		//	"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=0 AND PNAME='"+obstructProdType+"' order by codeA ");	
			sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
					"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=0 order by codeA ");	
		
		System.out.println("查故障原因大类:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
	public String getReason(String code)throws DataAccessException{

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
				"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=1 AND PNAME='"+code+"' order by codeA ");	

		System.out.println("查故障原因:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
	public String getReasonReaulstType(String obstructProdType)throws DataAccessException{

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
				"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=3 AND PNAME='"+obstructProdType+"' order by codeA ");	

		System.out.println("查故障结果:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
}
