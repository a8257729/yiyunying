package com.ztesoft.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
public class HttpClientUtile {
	public static String SendHttpClient(String url,String params) {
		String strResponse = "";
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		System.out.println("params  "+params);
		String nparams = null;
		try {
			nparams = new String(params.getBytes("utf-8"),"GBK");
		} catch (UnsupportedEncodingException e1) { 
			e1.printStackTrace();
		}
		
		NameValuePair[] data = { new NameValuePair("params", nparams)};
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		try {
			httpClient.getParams().setContentCharset("gbk");
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			System.out.println("url--> "+url+" d "+ statusCode  +"statusCode");
			if (statusCode == 200) {
				//strResponse = postMethod.getResponseBodyAsString();
				
				 BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(),"utf-8"));  
		            String tempBf = null;  
		            while((tempBf=reader.readLine()) != null){  
		                strResponse = tempBf;
		            }  
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return strResponse;
	}



	public static void main(String[] args) {
		HttpClientUtile testSendData = new HttpClientUtile();
		try {
//			JSONObject jsonData = new JSONObject();
//
//			jsonData.put("userId", "中文");
//			jsonData.put("password", "3");
			String sle = "{\"user_id\":\"10\",\"orgId\":\"1888\",\"staffId\":\"27270\",\"flag\":\"8\",\"access_num\":\"5441450\",\"topage\":\"querygldl\",\"jobId\":\"4213\",\"session_id\":\"f5b1450f0450470d9f27fb6d53d56e52\",\"defaultJobId\":\"15841\"}";
			//String sle = "{\"access_num\":\"52412450\",\"user_id\":\"10\",\"session_id\":\"181590546f7d4aa68fb519ad79736567\"}";
			//String sle = "{\"body\": {\"user_name\": \"zhangyanan\", \"pwd\": \"zhangyanan\" },\"engine_version\": \"1.0\",\"device_info\": {\"screen\": \"480*800\", \"model\": \"xt800\"}}";
			//String responseBody = testSendData.SendHttpClient("http://202.102.116.111:8080/irec/get-route!getRoute?",sle);  
			String responseBody = testSendData.SendHttpClient("http://192.168.43.22:7001/MOBILE/ceshiDataServlet",sle);  
			
			System.out.println("sendMail:"+responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}