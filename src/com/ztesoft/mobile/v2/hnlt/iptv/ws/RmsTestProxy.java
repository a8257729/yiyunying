package com.ztesoft.mobile.v2.hnlt.iptv.ws;

import java.io.PrintStream;
import java.util.Map;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class RmsTestProxy
{
  public String RmsServiceTest(Map<String, Object> map)
  {
    String result = "没有查到数据";
    try
    {
      String endpoint = "http://gkweb1:9020/FLOWBUS_INTERFACE/services/RMSInterface?wsdl";
      
      Service service = new Service();
      Call call = (Call)service.createCall();
      call.setTargetEndpointAddress(endpoint);
      
      call.setOperationName("queryDiagnose");
      
      call.addParameter("ukType", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("ukValue", XMLType.XSD_STRING, ParameterMode.IN);
      call.addParameter("flag", XMLType.XSD_STRING, ParameterMode.IN);
      
      call.setReturnType(XMLType.XSD_STRING);
      
      Object[] paramValues = { map.get("ukType"), map.get("ukValue"), map.get("flag") };
      
      result = (String)call.invoke(paramValues);
      
      System.out.println("result is " + result);
      return result;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
