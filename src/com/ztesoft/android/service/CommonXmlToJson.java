package com.ztesoft.android.service;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.util.TestDataUtil;
import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.android.util.XmlToJsonUtils;

public class CommonXmlToJson {

	public static String[] XmlToJson(Map param){
		
		StringBuffer returnstr = new StringBuffer();
		String json = getData(param,returnstr,0,null);
		JSONObject resultobj = new JSONObject();
		resultobj.put("body", json);
		resultobj.put("result", "000");
		String[] returnStrArr = new String[2];
		returnStrArr[0] = resultobj.toString();
		returnStrArr[1] = returnstr.toString();
		return returnStrArr;

	}
	
	public static String xmlToJsonData(Map param,String jsonPara){
		StringBuffer returnstr = new StringBuffer();
		String json = getData(param,returnstr,1,jsonPara);
		JSONObject resultobj = new JSONObject();
		resultobj.put("body", json);
		resultobj.put("result", "000");
		System.out.println("转换后的json --> "+resultobj);	
		return resultobj.toString();
	}
	
	/**
	 * 把下面的逻辑拆分成三部分：json->接口请求报文xml， 调用接口，接口返回报文xml->json
	 * @param param
	 * @param returnxmlstr
	 * @param type
	 * @param jsondata
	 * @return
	 */
	@Deprecated
	public static String getData(Map param,StringBuffer returnxmlstr,int type,String jsondata){
		XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
		//System.out.println("paramparam--> "+param.toString());
	    String intefaceType = MapUtils.getObject(param, "intefaceType")==null?"":MapUtils.getString(param, "intefaceType");
		String mappingFileds = MapUtils.getObject(param, "mappingFileds")==null?"":MapUtils.getString(param, "mappingFileds");
		String systemFileds = MapUtils.getObject(param, "systemFileds")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds"),mappingFileds);
		String mappingFileds2 = MapUtils.getObject(param, "mappingFileds2")==null?"":MapUtils.getString(param, "mappingFileds2");
		String systemFileds2 = MapUtils.getObject(param, "systemFileds2")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds2"),mappingFileds2);
		String mappingFileds3 = MapUtils.getObject(param, "mappingFileds3")==null?"":MapUtils.getString(param, "mappingFileds3");
		String systemFileds3 = MapUtils.getObject(param, "systemFileds3")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds3"),mappingFileds3);
		String mappingFileds4 = MapUtils.getObject(param, "mappingFileds4")==null?"":MapUtils.getString(param, "mappingFileds4");
		String systemFileds4 = MapUtils.getObject(param, "systemFileds4")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds4"),mappingFileds4);
		String mappingFileds5 = MapUtils.getObject(param, "mappingFileds5")==null?"":MapUtils.getString(param, "mappingFileds5");
		String systemFileds5 = MapUtils.getObject(param, "systemFileds5")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds5"),mappingFileds5);
		String mappingFileds6 = MapUtils.getObject(param, "mappingFileds6")==null?"":MapUtils.getString(param, "mappingFileds6");
		String systemFileds6 = MapUtils.getObject(param, "systemFileds6")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds6"),mappingFileds6);
		String mappingFileds7 = MapUtils.getObject(param, "mappingFileds7")==null?"":MapUtils.getString(param, "mappingFileds7");
		String systemFileds7 = MapUtils.getObject(param, "systemFileds7")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds7"),mappingFileds7);
		String mappingFileds8 = MapUtils.getObject(param, "mappingFileds8")==null?"":MapUtils.getString(param, "mappingFileds8");
		String systemFileds8 = MapUtils.getObject(param, "systemFileds8")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds8"),mappingFileds8);

		EBizToOtherWebservice eBizToIomWebservice=new EBizToOtherWebservice();
		String url = MapUtils.getString(param, "methodAddress");					//接口  方法地址
		String interfaceNamespace = MapUtils.getString(param, "interfaceNamespace");//空间名称
		String intefaceParams = MapUtils.getString(param, "intefaceParams");		//接口参数
		String interfMethodType = MapUtils.getString(param, "interfMethodType");	//接口方法类型
		String interfParamsName = MapUtils.getString(param, "interfParamsName");	//接口参数名
		String intefaceMethodParam = MapUtils.getString(param, "intefaceMethodParam");		//接口方法变量参数
		String interfaceMethod = MapUtils.getString(param, "interfaceMethod");   //接口方法名
		String dataType = MapUtils.getString(param, "dataType");  //接口数据类型
		String interfaceVersion = MapUtils.getString(param, "interfaceVersion");    //调用方式，即是标准备服务还是不标准服务
		String mappingCode = MapUtils.getString(param, "mappingCode");
		System.out.println("请求接口url----> "+url);
		if(type==1 && jsondata !=null){
			
			JSONObject jsonObject = new JSONObject();               //把从手机转过来的数据加上接口名，再把json转成xml，传入到业务系统中
			jsonObject.put("Params", jsondata);
			jsonObject.put("QueryMethod", intefaceMethodParam);
			intefaceParams = jsonUtil.GetXMLFromJson1(jsonObject.toString());
		}else {
			intefaceParams = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+intefaceParams;
		}
	
		//System.out.println("请求intefaceParams----> "+intefaceParams);
		//System.out.println("interfMethodType "+interfMethodType+"interfParamsName "+interfParamsName+" intefaceParams "+intefaceParams);
		String[]paraName={interfMethodType, interfParamsName};
		String[]paraValues={intefaceMethodParam, intefaceParams};
		String returnstr="";
		try {
			if(intefaceType.equals("4")){   //判断是否测试数据，如果是测试数据，则直接取库中的数据
			    returnstr = new TestDataUtil().toXml(mappingCode,dataType);
			}else {
				returnstr = eBizToIomWebservice.callWebService(url, interfaceNamespace, interfaceMethod, paraName, paraValues);
			}
			returnxmlstr.append(returnstr);
			System.out.println("业务系统返回xml:"+returnstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ndatas = returnstr.replaceAll(systemFileds, mappingFileds).replaceAll(systemFileds2, mappingFileds2)
		.replaceAll(systemFileds3, mappingFileds3).replaceAll(systemFileds4, mappingFileds4)
		.replaceAll(systemFileds5, mappingFileds5).replaceAll(systemFileds6, mappingFileds6)
		.replaceAll(systemFileds7, mappingFileds7).replaceAll(systemFileds8, mappingFileds8);
		//System.out.println("替换后的xml --> "+ndatas);	
		String json = "";
		if(dataType.equals("1")){   //判断是否格式，如果数据格式为json，则不需要把json转xml
			json = ndatas;
		}else {
		   json = jsonUtil.GetJsonStrFromXML1(ndatas); 
		}
		return json;
	}
	
	/**
	 * 读取配置文件，将客户端的json转换成接口所要用到的XML格式的请求报文
	 * @param param
	 * @return
	 */
	public static String json2IntefXml(Map param, int type,String jsondata) {
		XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
		
		String mappingFileds = MapUtils.getObject(param, "mappingFileds")==null?"":MapUtils.getString(param, "mappingFileds");
		String systemFileds = MapUtils.getObject(param, "systemFileds")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds"),mappingFileds);
		String mappingFileds2 = MapUtils.getObject(param, "mappingFileds2")==null?"":MapUtils.getString(param, "mappingFileds2");
		String systemFileds2 = MapUtils.getObject(param, "systemFileds2")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds2"),mappingFileds2);
		String mappingFileds3 = MapUtils.getObject(param, "mappingFileds3")==null?"":MapUtils.getString(param, "mappingFileds3");
		String systemFileds3 = MapUtils.getObject(param, "systemFileds3")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds3"),mappingFileds3);
		String mappingFileds4 = MapUtils.getObject(param, "mappingFileds4")==null?"":MapUtils.getString(param, "mappingFileds4");
		String systemFileds4 = MapUtils.getObject(param, "systemFileds4")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds4"),mappingFileds4);
		String mappingFileds5 = MapUtils.getObject(param, "mappingFileds5")==null?"":MapUtils.getString(param, "mappingFileds5");
		String systemFileds5 = MapUtils.getObject(param, "systemFileds5")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds5"),mappingFileds5);
		String mappingFileds6 = MapUtils.getObject(param, "mappingFileds6")==null?"":MapUtils.getString(param, "mappingFileds6");
		String systemFileds6 = MapUtils.getObject(param, "systemFileds6")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds6"),mappingFileds6);
		String mappingFileds7 = MapUtils.getObject(param, "mappingFileds7")==null?"":MapUtils.getString(param, "mappingFileds7");
		String systemFileds7 = MapUtils.getObject(param, "systemFileds7")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds7"),mappingFileds7);
		String mappingFileds8 = MapUtils.getObject(param, "mappingFileds8")==null?"":MapUtils.getString(param, "mappingFileds8");
		String systemFileds8 = MapUtils.getObject(param, "systemFileds8")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds8"),mappingFileds8);

		String url = MapUtils.getString(param, "methodAddress");					//接口  方法地址
		String interfaceNamespace = MapUtils.getString(param, "interfaceNamespace");//空间名称
		String intefaceParams = MapUtils.getString(param, "intefaceParams");		//接口参数
		String interfMethodType = MapUtils.getString(param, "interfMethodType");	//接口方法类型
		String interfParamsName = MapUtils.getString(param, "interfParamsName");	//接口参数名
		String intefaceMethodParam = MapUtils.getString(param, "intefaceMethodParam");		//接口方法变量参数
		String interfaceMethod = MapUtils.getString(param, "interfaceMethod");   //接口方法名
		String dataType = MapUtils.getString(param, "dataType");  //接口数据类型
		String interfaceVersion = MapUtils.getString(param, "interfaceVersion");    //调用方式，即是标准备服务还是不标准服务
		System.out.println("url----> "+url);
		if(type==1 && jsondata !=null){
			
			JSONObject jsonObject = new JSONObject();               //把从手机转过来的数据加上接口名，再把json转成xml，传入到业务系统中
			jsonObject.put("Params", jsondata);
			jsonObject.put("QueryMethod", intefaceMethodParam);
			intefaceParams = jsonUtil.GetXMLFromJson1(jsonObject.toString());
		}else {
			intefaceParams = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+intefaceParams;
		}
		//url="http://10.45.47.154:18049/IOMPROJ/services/PFServicesForEBizPort?wsdl";
		//intefaceParams = "<?xml version=\"1.0\" encoding=\"GBK\"?><Data><Params><JobId>4213</JobId><PageNum>1</PageNum><PageSize>2</PageSize><ProductNbr/><SysParam/><UseName>zhangkai</UseName></Params><QueryMethod>queryWorkOrderForEBiz</QueryMethod></Data>";
		
		System.out.println("intefaceParams----> "+intefaceParams);
		
		return intefaceParams;
	}
	
	/**
	 * 调用接口，并返回接口返回的XML报文
	 * @param map
	 * @param requestXmlStr
	 * @return
	 */
	public static String callWebService(Map param, String jsondata) throws Exception  {

		StringBuffer responseXmlStr = new StringBuffer("");
		
		EBizToOtherWebservice eBizToIomWebservice=new EBizToOtherWebservice();
		String url = MapUtils.getString(param, "methodAddress");					//接口  方法地址
		String interfaceNamespace = MapUtils.getString(param, "interfaceNamespace");//空间名称
		String intefaceParams = MapUtils.getString(param, "interfaceParams");		//接口参数
		String interfMethodType = MapUtils.getString(param, "interfMethodType");	//接口方法类型
		String interfParamsName = MapUtils.getString(param, "interfParamsName");	//接口参数名
		String intefaceMethodParam = MapUtils.getString(param, "intefaceMethodParam");		//接口方法变量参数
		String interfaceMethod = MapUtils.getString(param, "interfaceMethod");   //接口方法名
		String dataType = MapUtils.getString(param, "dataType");  //接口数据类型
		String interfaceVersion = MapUtils.getString(param, "interfaceVersion");    //调用方式，即是标准备服务还是不标准服务
		
		System.out.println("url----> "+url);
		System.out.println("interfaceNamespace----> "+interfaceNamespace);
		System.out.println("intefaceParams----> "+intefaceParams);
		System.out.println("requestXmlStr----> "+jsondata);
		System.out.println("intefaceParams----> "+intefaceParams);
		System.out.println("interfaceMethod----> "+interfaceMethod);
		System.out.println("intefaceMethodParam----> "+intefaceMethodParam);
		
		String returnstr="";
		JSONObject rootObj = new JSONObject();
		rootObj.put("method", intefaceMethodParam);
		rootObj.put("content", jsondata);
		try {
			if(interfaceVersion.equals("1")){  //不准服务
				XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
				//根据规则，在原有的json字符串外面再嵌套一层 
				String[]paraName={interfMethodType, interfParamsName};
				String[]paraValues={intefaceMethodParam, rootObj.toString()};
				returnstr = eBizToIomWebservice.callWebService(url, interfaceNamespace, interfaceMethod, paraName, paraValues);
			}else if(interfaceVersion.equals("2")) {  //标准服务
				//returnstr = DoServiceDaoImpl.CommonDoService(url, intefaceMethod, rootObj.toString());
				System.out.println("rootObj.toString()---> "+rootObj.toString());
				Axis2Clent axisclent = new Axis2Clent();
				//returnstr = "";
				returnstr= axisclent.edb(rootObj.toString(),url,interfaceNamespace,interfaceMethod);
			}
			responseXmlStr.append(returnstr);
			System.out.println("业务系统返回xml:"+returnstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseXmlStr.toString();
	}
	
	/**
	 * 接口返回的XML报文转换成客户端所用的json
	 * @param param
	 * @param responseXmlStr
	 * @return
	 */
	public static String InterfXml2Json(Map param, String responseXmlStr) {
		XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
		
		String mappingFileds = MapUtils.getObject(param, "mappingFileds")==null?"":MapUtils.getString(param, "mappingFileds");
		String systemFileds = MapUtils.getObject(param, "systemFileds")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds"),mappingFileds);
		String mappingFileds2 = MapUtils.getObject(param, "mappingFileds2")==null?"":MapUtils.getString(param, "mappingFileds2");
		String systemFileds2 = MapUtils.getObject(param, "systemFileds2")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds2"),mappingFileds2);
		String mappingFileds3 = MapUtils.getObject(param, "mappingFileds3")==null?"":MapUtils.getString(param, "mappingFileds3");
		String systemFileds3 = MapUtils.getObject(param, "systemFileds3")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds3"),mappingFileds3);
		String mappingFileds4 = MapUtils.getObject(param, "mappingFileds4")==null?"":MapUtils.getString(param, "mappingFileds4");
		String systemFileds4 = MapUtils.getObject(param, "systemFileds4")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds4"),mappingFileds4);
		String mappingFileds5 = MapUtils.getObject(param, "mappingFileds5")==null?"":MapUtils.getString(param, "mappingFileds5");
		String systemFileds5 = MapUtils.getObject(param, "systemFileds5")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds5"),mappingFileds5);
		String mappingFileds6 = MapUtils.getObject(param, "mappingFileds6")==null?"":MapUtils.getString(param, "mappingFileds6");
		String systemFileds6 = MapUtils.getObject(param, "systemFileds6")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds6"),mappingFileds6);
		String mappingFileds7 = MapUtils.getObject(param, "mappingFileds7")==null?"":MapUtils.getString(param, "mappingFileds7");
		String systemFileds7 = MapUtils.getObject(param, "systemFileds7")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds7"),mappingFileds7);
		String mappingFileds8 = MapUtils.getObject(param, "mappingFileds8")==null?"":MapUtils.getString(param, "mappingFileds8");
		String systemFileds8 = MapUtils.getObject(param, "systemFileds8")==null?"":TimeUtil.strParse(MapUtils.getString(param, "systemFileds8"),mappingFileds8);
			
		String ndatas = responseXmlStr.replaceAll(systemFileds, mappingFileds).replaceAll(systemFileds2, mappingFileds2)
		.replaceAll(systemFileds3, mappingFileds3).replaceAll(systemFileds4, mappingFileds4)
		.replaceAll(systemFileds5, mappingFileds5).replaceAll(systemFileds6, mappingFileds6)
		.replaceAll(systemFileds7, mappingFileds7).replaceAll(systemFileds8, mappingFileds8);
		//
		System.out.println("替换后的xml --> "+ndatas);	
		
		String json = jsonUtil.GetJsonStrFromXML1(ndatas); 
		
		return json;
	}
}
