package com.ztesoft.mobile.v2.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.log4j.Logger;

/**   
 * @ClassName:  HttpUtils   
 * @Description: HTTP请求入口
 * @author: kai.li  
 * @date:   2017年9月8日 上午10:04:07      
 */ 
public class HttpUtils {
	
	private static final Logger logger = Logger.getLogger(HttpUtils.class);
	public static final String getMethod = "GET";
	public static final String postMethod = "POST";
	public static final String putMethod = "PUT";
	private static final String charset = "UTF-8";
	private static final String contentType = "application/json; charset=UTF-8";
	
	private static HttpUtils instance = new HttpUtils();

	private HttpUtils(){
	}
	
	public synchronized static HttpUtils getInstance() {
		if (instance == null) {
			instance = new HttpUtils();
		}
		return instance;
	}
	
	/**
	 * @Title: call 
	 * @Description: 发起http调用 
	 * @param: URL 请求的url 
	 * @param: modelName 请求模块 
	 * @param: param 请求的json参数 
	 * @param: @return 
	 * @return: String 
	 * @throws
	 */
	public String call( String URL, String modelName, String param,String method) {
//		String xtranId = "GK"+PrimaryKey.getUuid();
		URL = this.getServiceUrl(URL);
		logger.info(modelName);
		OutputStream os = null;
		OutputStreamWriter osw = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String state = "";
		try {
			if(getMethod.equals(method)){
				URL = URL+ "/"+URLEncoder.encode( param, "UTF-8" );;
			}
			HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(URL).openConnection());
			// 设置建立连接的超时时间（单位：毫秒）
			httpURLConnection.setConnectTimeout(10000);
			// 设置传递数据的超时时间（单位：毫秒）
			httpURLConnection.setReadTimeout(90000);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod(method);
			//httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", contentType);
			//httpURLConnection.setRequestProperty("Accept", "application/json, text/javascript, */*");
			//httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
//			httpURLConnection.setRequestProperty("X-channel-id", "JKYPT_NX");
//			httpURLConnection.setRequestProperty("X-trans-id", xtranId);
			//httpURLConnection.setRequestProperty("Content-Length", param.getBytes("UTF-8").length + "");
			String line = null;
			logger.info(URL+"请求方式:" + method);
			logger.info("请求参数:" + param);
			
			if(!getMethod.equals(method)){
				os = httpURLConnection.getOutputStream();
				osw = new OutputStreamWriter(os, "UTF-8");
				osw.write(param);
				osw.flush();
			}
			
			state = httpURLConnection.getResponseCode()+"";
			logger.info("响应代码:" + state);
			is = httpURLConnection.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(isr);
			while ((line = reader.readLine()) != null) {
				resultBuffer.append(line);
			}

		} catch (MalformedURLException e) {
			logger.error(param + " MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			logger.error(param + " IOException :" + e.getMessage());
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return resultBuffer.toString();
	}
	
	
	/**
	 * @Title: call 
	 * @Description: 发起http调用 
	 * @param: URL 请求的url 
	 * @param: modelName 请求模块 
	 * @param: param 请求的json参数 
	 * @param: @return 
	 * @return: String 
	 * @throws
	 */
	public String call(String URL, String modelName, String param) {
//		String xtranId = "GK"+PrimaryKey.getUuid();
		//根据url名去资源文件读取配置的url
		URL = this.getServiceUrl(URL);
		logger.info("http请求：" + URL + "," + modelName);
		OutputStream os = null;
		OutputStreamWriter osw = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String state = "";
		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(URL).openConnection());
			// 设置建立连接的超时时间（单位：毫秒）
			httpURLConnection.setConnectTimeout(10000);
			// 设置传递数据的超时时间（单位：毫秒）
			httpURLConnection.setReadTimeout(90000);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", contentType);
			httpURLConnection.setRequestProperty("Accept", "application/json, text/javascript, */*");
			httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
//			httpURLConnection.setRequestProperty("X-channel-id", "JKYPT_NX");
//			httpURLConnection.setRequestProperty("X-trans-id", xtranId);
			httpURLConnection.setRequestProperty("Content-Length", param.getBytes("UTF-8").length + "");
			String line = null;

			os = httpURLConnection.getOutputStream();
			osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(param);
			osw.flush();
			state = httpURLConnection.getResponseCode()+"";
			logger.info("响应代码:" + state);
			logger.info("请求参数:" + param);
			is = httpURLConnection.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(isr);
			while ((line = reader.readLine()) != null) {
				resultBuffer.append(line);
			}

		} catch (MalformedURLException e) {
			logger.error(param + " MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			logger.error(param + " IOException :" + e.getMessage());
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return resultBuffer.toString();
	}
	
	public String getServiceUrl(String urlString){
		String url="";
		String filePath = "/extservice/infConfig.properties";
		  String path = getClass().getProtectionDomain().getCodeSource()    
          .getLocation().getPath();    
		  if (path.indexOf("WEB-INF") > 0) {    
			  path = path.substring(0, path.indexOf("WEB-INF") + 7);    
		  } 
		try {
			System.out.println(path+filePath);
			InputStream in = new FileInputStream(path+filePath);
			Properties config = new Properties();
			config.load(in);
			System.out.println(config.getProperty(urlString).toString()+" invoke..");
			url=config.getProperty(urlString).toString();
		}  catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public static void main(String[] args) {
		String a = HttpUtils.getInstance().call("http://10.223.207.4:20111/ngcrmpfcore_nx/ws/business/broadbandUserAccountStatus", "xxxxxxxxxx", 
"{\"params\":{\"serialNo\":\"\",\"account\":\"\"}}", "GET");
		System.out.println(a);
	}
}
