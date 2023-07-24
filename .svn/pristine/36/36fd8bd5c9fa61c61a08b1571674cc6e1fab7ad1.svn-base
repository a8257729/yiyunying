package com.ztesoft.android.client.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.client.dao.CommonDAO;
import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;

public class CommonAction implements AndrBaseAction{
	

	public Object doAction(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String code = jsonObject.get("code")==null?"-1":jsonObject.getString("code");   //这个为当前要请法语的环节
		String type = jsonObject.get("type")==null?"-1":jsonObject.getString("type");
		String obstructProdType = jsonObject.get("obstructProdType")==null?"-1":jsonObject.getString("obstructProdType");
		String jsondata="";
		try {
			if(type.equals("0")){
			  jsondata = getServiceDAO().getReasonType(obstructProdType);
			}else if(type.equals("1")) {
				jsondata = getServiceDAO().getReason(code);
			}else if(type.equals("2")){
				jsondata = getServiceDAO().getReasonReaulstType(obstructProdType);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		JSONObject resultObject = new JSONObject();
		JSONObject listObject = new JSONObject();
		listObject.put("listdata", jsondata);
		resultObject.put("result", "000"); 
		resultObject.put("body", listObject);
		
		System.out.println("原字符串="+resultObject.toString());   

	    String newstr = ZipUtil.compress(resultObject.toString());   
	    System.out.println("压缩后的长="+newstr.length());  
	      
	    response.setContentType("text/plain;charset=ISO-8859-1");
	    response.getWriter().write(newstr);
	    return null;
	}

	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private CommonDAO getCommonDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (CommonDAO) BaseDAOFactory.getImplDAO(daoName);
	}


}
