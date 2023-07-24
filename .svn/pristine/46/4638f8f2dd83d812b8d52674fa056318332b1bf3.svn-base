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
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;
import org.apache.log4j.Logger;

public class GetReturnReasonAction implements AndrBaseAction{
	
	  private static Logger logger = Logger.getLogger(GetReturnReasonAction.class);
	  //private static String iomServiceUrl = "http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort?wsdl";
	  private static String iomServiceOperName = "pfServicesForEBiz";

	public Object doAction(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		System.out.println("----------------------------------进入获取异常原因方法");
		String outXml = "";
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String teachName = jsonObject.get("topage")==null?"-1":jsonObject.getString("topage");   //这个为当前要请法语的环节
		System.out.println("name--> "+teachName );
		
		String workOrderID = jsonObject.get("workOrderId")==null?"2170327":jsonObject.getString("workOrderId");  //2170327
		String queryMethod = "returnReasonListForEBiz";
		String inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><QueryMethod>" 
			+ queryMethod + "</QueryMethod><Params><WorkOrderID>" + workOrderID + "</WorkOrderID></Params></Data>";
		System.out.println("inXml----------------------------> "+inXml );
			
		try {
		    if ("returnReasonListForEBiz".equals(queryMethod)) {
		    	outXml = queryReturnReason(inXml);
		     }
		} catch (Exception e) {
//			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    
		
		/*outXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><WorkOrderID>11111</WorkOrderID>" +
		"<Return><Result>000</Result><ErrorDesc>失败信息描述</ErrorDesc></Return>" + 
		 "<ReasonList><Reason><ErrorCode>E9999</ErrorCode><ErrorName>用户不装</ErrorName><TargetTache>开始节点</TargetTache></Reason>" +
		 "<Reason><ErrorCode>E8888</ErrorCode><ErrorName>接入方式错</ErrorName><TargetTache>配线节点</TargetTache></Reason></ReasonList></Data>";*/

		/*outXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><WorkOrderID>11111</WorkOrderID><ReasonList/>" +
		"<Return><Result>000</Result><ErrorDesc>失败信息描述</ErrorDesc></Return>" + 
		 "</Data>";*/
		
		String jsondata = JsonUtils.getJsonStrFromXML(outXml);
		//jsondata = "{\"result\": \"000\"}";
		System.out.println("原字符串="+jsondata);   
	    System.out.println("原长="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
	    response.setContentType("text/plain;charset=ISO-8859-1");
	    response.getWriter().write(newstr);
	    return null;
	}

	  public String queryReturnReason(String inXml) {
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    try {
		    System.out.println("移动平台发送异常原因查询的请求报文：" + inXml);
		    String[] paraName = { "a", "b" };
		    String[] paraValues = { inXml, "returnReasonListForEBiz" };
			retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
		    System.out.println("开通返回的报文：" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }


}
