package com.ztesoft.eoms.common.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zterc.uos.i18nproduct.I18NClient;
import com.zterc.uos.runtime.ExecutionDelegate;

public class I18NResourceNew {
	private static Logger logger = Logger.getLogger(I18NResourceNew.class);
    private static Map langMap = new HashMap();
    public static String RUNTIME_TIER = "com.zterc.uos.RuntimeTier";

    public I18NResourceNew() {
    }

    public static String getResource(String key, String langCode) {
        String value = "no defined";
        try {
            if (langMap.size() < 1 || langMap.get(langCode) == null) {
                getResMapFromXML(langCode);
            }
            //.......取map中的值
            Map temp = (Map) langMap.get(langCode);
            if (temp.get(key) != null) {
                value = (String) temp.get(key);

            }
        }
        catch (DocumentException ex) {
            logger.error("run getResource exception，DocumentException：" + ex);
        }
        catch (MalformedURLException ex) {
            logger.error("run getResource exception，MalformedURLException：" +
                         ex);
        }
        catch (FileNotFoundException ex) {
            logger.error("run getResource exception，FileNotFoundException：" +
                         ex);
        }
        return value;
    }

    public static void getResMapFromXML(String langCode)
        throws MalformedURLException, DocumentException, FileNotFoundException {

        if (langCode==null || langCode=="") {
            langCode = "zh_cn";

        }
        String xmlPath = "/resource/" + langCode;
        Map map = getResMap(xmlPath);

        langMap.put(langCode, map);
    }

    private static Map getResMap(String xmlPath)
        throws MalformedURLException, DocumentException, FileNotFoundException {
        Map map = new HashMap();
        SAXReader reader = new SAXReader();
        String runtimeTier = System.getProperty(RUNTIME_TIER);
        I18NClient tm = new I18NClient();
        URL url = null;
        String path = null;

        url = I18NResourceNew.class.getResource(xmlPath);
        logger.info("new url--" + url);
        if (url == null) {
            //logger.info("run method getResMapFromXML() exception ：I18n resource file name or path is wrong！ xmlPath＝" + url);
            path = ExecutionDelegate.singleton().getBuizClass_Path() + xmlPath;
        }
        else {
            path = url.getPath();
        }
        logger.info(path);
        Document d = null;

        File f = new File(path);
        if(f==null) {
            logger.info("run method getResMapFromXML() exception ：I18n resource file name or path is wrong！ xmlPath＝" + path);
            return null;
        }
        File[] file = f.listFiles();
        if (file == null && f != null) {
            file = new File[1];
            file[0] = f;
        }
        Element root = null;
        Element foo = null;
        String key = "";
        String value = "";
        String fileName = "";
        int temp = 0;
        String postfix = "";
        for (int i = 0; i < file.length; i++) {

            fileName = file[i].getName();
            //只选择xml类型的文件
            temp = fileName.lastIndexOf(".");
            if (temp > 0) {
                postfix = fileName.substring(temp + 1);
            }
            if (postfix == null || postfix == "" || !postfix.equals("xml") ||
                !file[i].canRead()) {
                logger.error("The file isn't xml file，cannot loaded：" +
                             fileName);
                continue;
            }
            logger.info("Loading resource file：" + fileName);
            try {
                FileInputStream fis = new FileInputStream(file[i]);
                d = reader.read(fis, "UTF-8");
                root = d.getRootElement();
            }
            catch (Exception ex) {
                logger.error("Loading failure：" + ex);
                continue;
            }
            for (Iterator it = root.elementIterator("Label"); it.hasNext(); ) {
                foo = (Element) it.next();
                key = foo.attribute("key").getValue();
                value = foo.getTextTrim();
                map.put(key, value);
                //logger.error("key:" + key + " value:" + value);
            }
        }
        return map;
    }
    public static void main(String[] args) {
    	String key = "oaas.staff";
    	Object lan = null;
    	if (lan == null) {
    		lan = "zh_cn";
    	}
    	
    	System.out.println("key" + key);
    	String value =  com.ztesoft.eoms.common.helper.I18NResourceNew.getResource(key, (String)lan).replaceAll("\"", "\\\\x22").replaceAll("\'", "\\\\x27");
    	System.out.println("return" + value);
	}
}
