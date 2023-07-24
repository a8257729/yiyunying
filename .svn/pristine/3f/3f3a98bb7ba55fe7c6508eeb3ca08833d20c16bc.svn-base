package com.ztesoft.android.service;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

/**
 * @class-name：Axis2Clent
 * @class-function：
 * @creator：zhuang.zhao
 * @create-time：2011-10-18 下午05:31:30
 * @revision：$Id: 1.0
 */
public class Axis2Clent {

	public String  edb(String params,String wdlUrl,String spaceName,String method) throws Exception {

		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(wdlUrl);		
		options.setTo(targetEPR);
		// // 下面是调用getPrice方法的代码，这些代码与调用getGreeting方法的代码类似
		Class[] classes = new Class[] { String.class };
		// http://webservice.bss 发布成功的命名空间 就是那个包名路径 ，这儿不注意容易犯错
		QName opAddEntry = new QName(spaceName,method);
		// 指定getGreeting方法的参数值
		Object[] opAddEntryArgs = new Object[] { params };
		// 指定getGreeting方法返回值的数据类型的Class对象
		classes = new Class[] { String.class };
		// serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
		// 调用getGreeting方法并输出该方法的返回值
		String returndata = "";
		try {	
			returndata = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0].toString();			
		} catch (Exception e) {
		}
		return returndata;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		try {
			Axis2Clent c = new Axis2Clent();

			long startTime = System.currentTimeMillis();
			for (int i = 0; i < 1; i++) {
				
				String prarms = "{\"content\":{\"UseName\":\"zhangkai\",\"body\":{\"type\":\"1\",\"code\":\"BBJ.NJJ00/GJ010\"},\"user_id\":\"27250\",\"areaId\":\"-1\",\"OrgId\":\"1888\",\"JobId\":\"4213\"},\"method\":\"gJQueryService_getDeviceInfo\"}";
				String wdlUrl = "http://10.45.28.24:9080/iresource/service/Iresource?wsdl";
				String spaceName = "http://webservice.sys.resmaster.ztesoft.com/";
				String methodName = "processBuiz";
				Object d = c.edb(prarms,wdlUrl,spaceName,methodName);
			
				System.out.println("d--> "+d);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("数据测试缓存耗时:" + (endTime - startTime) / 1000);
			// c.edb();
		} catch (Exception e) {
			System.out.print("异常信息" + e.getMessage());
		}
	}
}
