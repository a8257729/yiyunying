package com.ztesoft.mobile.v2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class XmlDataUtil {
	public static  String url = "http://10.45.47.142:8001/MOBILE/simulatedXml/";

	public static String getXmlData(String fileName) {

	    File file = new File("E:/所内VSS/服务端/WEB-ROOT/xml/"+fileName+".xml");
		FileInputStream fileInputStream;
		StringBuffer sb = new StringBuffer();
		try {
			fileInputStream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRemotelXmlData(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			URL theUrl = new URL(url+fileName+".xml");
			HttpURLConnection conn = (HttpURLConnection) theUrl.openConnection();
			conn.connect();
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
       return sb.toString();
	}
}
