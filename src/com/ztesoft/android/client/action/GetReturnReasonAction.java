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
		System.out.println("�������??" + jsonPara);
		System.out.println("----------------------------------�����ȡ�쳣ԭ�򷽷�");
		String outXml = "";
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String teachName = jsonObject.get("topage")==null?"-1":jsonObject.getString("topage");   //���Ϊ��ǰҪ�뷨��Ļ���
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
		"<Return><Result>000</Result><ErrorDesc>ʧ����Ϣ����</ErrorDesc></Return>" + 
		 "<ReasonList><Reason><ErrorCode>E9999</ErrorCode><ErrorName>�û���װ</ErrorName><TargetTache>��ʼ�ڵ�</TargetTache></Reason>" +
		 "<Reason><ErrorCode>E8888</ErrorCode><ErrorName>���뷽ʽ��</ErrorName><TargetTache>���߽ڵ�</TargetTache></Reason></ReasonList></Data>";*/

		/*outXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><WorkOrderID>11111</WorkOrderID><ReasonList/>" +
		"<Return><Result>000</Result><ErrorDesc>ʧ����Ϣ����</ErrorDesc></Return>" + 
		 "</Data>";*/
		
		String jsondata = JsonUtils.getJsonStrFromXML(outXml);
		//jsondata = "{\"result\": \"000\"}";
		System.out.println("ԭ�ַ���="+jsondata);   
	    System.out.println("ԭ��="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("ѹ������ַ���="+newstr);   
	    System.out.println("ѹ����ĳ�="+newstr.length());  
	      
	    response.setContentType("text/plain;charset=ISO-8859-1");
	    response.getWriter().write(newstr);
	    return null;
	}

	  public String queryReturnReason(String inXml) {
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    try {
		    System.out.println("�ƶ�ƽ̨�����쳣ԭ���ѯ�������ģ�" + inXml);
		    String[] paraName = { "a", "b" };
		    String[] paraValues = { inXml, "returnReasonListForEBiz" };
			retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
		    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }


}
