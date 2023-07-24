package com.ztesoft.mobile.service.handler;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import net.sf.json.JSONObject;

import com.ztesoft.android.client.dao.CommonDAO;
import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;

public class ReturnWorkOrderHandler extends AbstractHandler {
	
	 //private static String iomServiceUrl = "http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort?wsdl";
	 private static String iomServiceOperName = "pfServicesForEBiz";

	 protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		System.out.println("----------------------------------进入退单方法");
		String outXml = "";
		Date now = new Date();
		DateFormat df = DateFormat.getDateTimeInstance();
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String teachName = jsonObject.get("topage")==null?"-1":jsonObject.getString("topage");   //这个为当前要请法语的环节
		
		String workOrderID = jsonObject.get("workOrderId")==null?"":jsonObject.getString("workOrderId");  //2170327
		String queryMethod = "returnWorkOrderForEBiz";
		String userName = "";
		if(!"".equals(jsonObject.get("userName")) && jsonObject.get("userName") != null ){
			userName = jsonObject.getString("userName");
		}else{
			userName = "admin";
		}
		String loginPhoneNo = jsonObject.get("loginPhoneNo")==null?"":jsonObject.getString("loginPhoneNo"); 
		String errReason = jsonObject.get("errReason")==null?"":jsonObject.getString("errReason"); 
		String comments = "".equals(jsonObject.get("comments"))?"退单操作":jsonObject.getString("comments"); 
		String finishTime = df.format(now);
		System.out.println("finishTime----------------------------> "+finishTime );
		System.out.println("errReason----------------------------> "+errReason );
		System.out.println("userName----------------------------> "+userName );
		System.out.println("comments----------------------------> "+comments );
		
		String inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><QueryMethod>" 
			+ queryMethod + "</QueryMethod><Params><UseName>" + userName + "</UseName><WorkOrderID>" + workOrderID + "</WorkOrderID>" +
			"<LoginPhoneNo>" +loginPhoneNo+"</LoginPhoneNo><ErroReason>" + errReason + "</ErroReason><Comments>" + comments + "</Comments><FinishTime>" + finishTime + "</FinishTime></Params></Data>";
		System.out.println("inXml----------------------------> "+inXml );
		
		paramMap.put("requestXml", inXml);
		this.beforeCallWS(paramMap);		//调用接口之前
		

		try {
		    if ("returnWorkOrderForEBiz".equals(queryMethod)) {
		    	outXml = returnWorkOrder(inXml);
		     }
		} catch (Exception e) {
//			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    
		
		paramMap.put("responseXml", outXml);
		this.afterCallWS(paramMap);		//调用接口之后

		/*outXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><WorkOrderID>" + workOrderID + "</WorkOrderID>" +
		"<Return><Result>000</Result><ErrorDesc>失败信息描述</ErrorDesc></Return>" + 
		 "</Data>";*/

		String jsondata = JsonUtils.getJsonStrFromXML(outXml);
		System.out.println("原字符串="+jsondata);   
	    System.out.println("原长="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
	    //ResponseObject respObj = new ResponseObject();
	    //respObj.setResponse(newstr);
	    //return respObj;
	    paramMap.put("response", newstr);
	}

	  public String returnWorkOrder(String inXml) {
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    try {
		    System.out.println("移动平台发送退单的请求报文：" + inXml);
		    String[] paraName = { "a", "b" };
		    String[] paraValues = { inXml, "returnWorkOrderForEBiz" };
		    
			retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
		    System.out.println("开通返回的报文：" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }


}
