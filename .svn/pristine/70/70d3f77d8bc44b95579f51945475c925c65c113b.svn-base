package com.ztesoft.android.util;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.EBizToOtherWebservice;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class TestDataUtil {

	public  String getData(Map param,int type,String jsondata){ 
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
			String initxml="<Data><Params><DefaultJobId>15821</DefaultJobId><JobId>4213</JobId><OrgId>1888</OrgId><StaffId>27250</StaffId><UseName>zhangkai</UseName><name1>22222</name1></Params><QueryMethod>x</QueryMethod></Data>";
			intefaceParams = intefaceParams==null?initxml:intefaceParams;
			intefaceParams = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+intefaceParams;
		}
	
		System.out.println("请求intefaceParams----> "+intefaceParams);
		//System.out.println("interfMethodType "+interfMethodType+"interfParamsName "+interfParamsName+" intefaceParams "+intefaceParams);
		String[]paraName={interfMethodType, interfParamsName};
		String[]paraValues={intefaceMethodParam, intefaceParams};
		String returnstr=toXml(mappingCode,dataType);
		String json = "";
		
		String ndatas = returnstr.replaceAll(systemFileds, mappingFileds).replaceAll(systemFileds2, mappingFileds2)
		.replaceAll(systemFileds3, mappingFileds3).replaceAll(systemFileds4, mappingFileds4)
		.replaceAll(systemFileds5, mappingFileds5).replaceAll(systemFileds6, mappingFileds6)
		.replaceAll(systemFileds7, mappingFileds7).replaceAll(systemFileds8, mappingFileds8);
		if(dataType.equals("1")){  //判断是否是json数据格式
			json = returnstr;
		}else {			
			System.out.println("替换后的xml --> "+ndatas);	
			json = jsonUtil.GetJsonStrFromXML1(ndatas); 
		}
		return json;
	}
	
	public  String toXml(String mappingCode,String dataType){
	
		System.out.println("mappingCode "+mappingCode +" dataType "+dataType);
		Map jsonMap = null;
		try {
			jsonMap = getServiceDAO().getTestIntefaceData(mappingCode);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String datas = "";
		if(dataType.equals("1")){
			datas = jsonMap.get("jsonData")+"";
		}else {
			datas = jsonMap.get("xmlData")+"";
		}
		System.out.println("测试数据------------>> "+datas);
		return datas;
	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	public static void main(String[] args){
		XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
		String testdatas = "{\"Params\":{\"OrgId\":\"1888\",\"JobId\":\"4213\",\"name2\":\"网元\",\"StaffId\":\"27250\",\"DefaultJobId\":\"15821\",\"name3\":\"未完成\",\"name1\":\"232323\",\"UseName\":\"zhangkai\"},\"QueryMethod\":\"test\"}";
	
		String xmldata = jsonUtil.GetXMLFromJson1(testdatas);
		System.out.println("xmldata--> "+xmldata);
	}
}
