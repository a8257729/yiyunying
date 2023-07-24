package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;

public class GetReturnReasonHandler extends AbstractHandler{
	
	  private static Logger logger = Logger.getLogger(GetReturnReasonHandler.class);
	  //private static String iomServiceUrl = "http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort?wsdl";
	  private static String iomServiceOperName = "pfServicesForEBiz";
	  
	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
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

		paramMap.put("requestXml", inXml);
		this.beforeCallWS(paramMap)	;	//���ýӿ�֮��
		
		try {
		    if ("returnReasonListForEBiz".equals(queryMethod)) {
		    	outXml = queryReturnReason(inXml);
		     }
		} catch (Exception e) {
//			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    
		
		paramMap.put("responseXml", outXml);
		this.afterCallWS(paramMap);		//���ýӿ�֮��
		
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
	      
	    //ResponseObject respObj = new ResponseObject();
	    //respObj.setResponse(newstr);
	    //return respObj;
	    paramMap.put("response", newstr);
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
