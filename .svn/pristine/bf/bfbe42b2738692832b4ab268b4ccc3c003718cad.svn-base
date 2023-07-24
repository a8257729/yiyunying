package com.ztesoft.mobile.v2.service.workform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	public static  String url = "http://10.45.47.172:18042/MOBILE/xml/";

	public static String getXmlData(String fileName) {

		Document nmdk2iomDoc = null;
	    File file = new File("D:/xml/"+fileName+".xml");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
			SAXReader saxReader = new SAXReader();
			nmdk2iomDoc = saxReader.read(fileInputStream);
			fileInputStream.close();
			String retXml = nmdk2iomDoc.asXML();
			return retXml;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
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
			System.out.println("url==="+url+fileName+".xml,content===="+sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
       return sb.toString();
	}
}
