package com.ztesoft.sa.inf.mobileinterface.client;



import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceServiceLocator;
import com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterfaceSoapBindingStub;
import com.ztesoft.sa.inf.mobileinterface.ZteSoftMobileInterface_PortType;


public class MsgParser {
	public static List findListInMap(Map paramMap,String target){   
		Iterator it=paramMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry=(Map.Entry)it.next();
			String key=(String)entry.getKey();
			Object value=entry.getValue();
			if(key!=null&&key.equals(target)){
				if(value instanceof List)return (List)value;
			}else{
				if(value instanceof Map){
					List ro=findListInMap((Map)value,target);
					if(ro!=null)return ro;
				}else if(value instanceof List){
					List list=(List)value;
					for(int i=0;i<list.size();i++){
						Object obj=list.get(i);
						if(obj instanceof Map){
							List ro=findListInMap((Map)obj,target);
							if(ro!=null)return ro;
						}else if(obj instanceof String){
							
						}
					}
				}
			}
		}
		return null;
	}
	public static Map findMapInMap(Map paramMap,String target){   
		Iterator it=paramMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry=(Map.Entry)it.next();
			String key=(String)entry.getKey();
			Object value=entry.getValue();
			if(key!=null&&key.equals(target)){
				if(value instanceof Map)return (Map)value;
			}else{
				if(value instanceof Map){
					Map ro=findMapInMap((Map)value,target);
					if(ro!=null)return ro;
				}else if(value instanceof List){
					List list=(List)value;
					for(int i=0;i<list.size();i++){
						Object obj=list.get(i);
						if(obj instanceof Map){
							Map ro=findMapInMap((Map)obj,target);
							if(ro!=null)return ro;
						}else if(obj instanceof String){
							
						}
					}
				}
			}
		}
		return null;
	}
	public static Object findInMap(Map paramMap,String target){   
		Iterator it=paramMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry=(Map.Entry)it.next();
			String key=(String)entry.getKey();
			Object value=entry.getValue();
			if(key!=null&&key.equals(target)){
				return value;
			}else{
				if(value instanceof Map){
					Object ro=findInMap((Map)value,target);
					if(ro!=null)return ro;
				}else if(value instanceof List){
					List list=(List)value;
					for(int i=0;i<list.size();i++){
						Object obj=list.get(i);
						if(obj instanceof Map){
							Object ro=findInMap((Map)obj,target);
							if(ro!=null)return ro;
						}else if(obj instanceof String){
							return list;
						}
					}
				}
			}
		}
		return null;
	}
	public static String findStringInMap(Map paramMap,String target){   
		Iterator it=paramMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry=(Map.Entry)it.next();
			String key=(String)entry.getKey();
			Object value=entry.getValue();
			if(key!=null&&key.equals(target)){
				if(value instanceof String){
					return (String)value;
				}else{
					return null;
				}
			}else{
				if(value instanceof Map){
					String ro=findStringInMap((Map)value,target);
					if(ro!=null)return ro;
				}else if(value instanceof List){
					List list=(List)value;
					for(int i=0;i<list.size();i++){
						Object obj=list.get(i);
						if(obj instanceof Map){
							String ro=findStringInMap((Map)obj,target);
							if(ro!=null)return ro;
						}else if(obj instanceof String){
							
						}
					}
				}
			}
		}
		return null;
	}
	public static Map Xml2Map(String xml) throws DocumentException {   
	    SAXReader reader = new SAXReader();   
	    StringReader sr = new StringReader(xml);   
	    try{
	    Document document = reader.read(sr);   
	    OutputFormat format = OutputFormat.createPrettyPrint();   
	    format.setEncoding("UTF-8");   
	    Element root = document.getRootElement();   
	    
	    Map map=Dom2Map(root);
	    return map;
	    }catch(Exception e){
	    	e.printStackTrace();
	    	throw new RuntimeException(e.getMessage());
	    }finally{
	    	sr.close();
	    }
	}  
	//根据参数map构建xml,key作为节点名,value作为节点内容
	public static String map2Xml(Map paramMap){
		
		Document doc=DocumentHelper.createDocument();  
		
		Iterator it=paramMap.keySet().iterator();
		while(it.hasNext()){
			String k=(String)it.next();
			Object obj=MapUtils.getObject(paramMap,k);
			Element ele=doc.addElement(k);
			if(obj instanceof Map){
				addMap((Map)obj,ele);
			}else if(obj instanceof List){
				addList((List)obj,ele,null,k);
			}else if(obj instanceof String){
				ele.setText((String)obj);
			}else if(obj==null){
				ele.setText("");
			}else{
				ele.setText((String)obj);
			}
		}
		String result=doc.asXML();
		result=result.replaceAll("&lt;", "<");
		result=result.replaceAll("&gt;", ">");
		return result;
	}
	
	public static String map2XmlParse(Map paramMap){
		
		
		String xmlParser="";
		Iterator it=paramMap.keySet().iterator();
		while(it.hasNext()){
			String k=(String)it.next();
			Object obj=MapUtils.getObject(paramMap,k);
			if(obj instanceof Map){
				xmlParser+="<"+k+">"+map2XmlParse((Map)obj)+"</"+k+">";
			}else if(obj instanceof List){
				throw new RuntimeException("not valide");
			}else if(obj instanceof String){
				xmlParser+="<"+k+">"+(String)obj+"</"+k+">";
			}else if(obj==null){
				xmlParser+="<"+k+"></"+k+">";
			}
			else {
				xmlParser+="<"+k+">"+MapUtils.getString(paramMap,k)+"</"+k+">";
			}
		}
		return xmlParser;
	}
	
	public static void addMap(Map map,Element ele){
		Iterator it=map.keySet().iterator();
		while(it.hasNext()){
			String k=(String)it.next();
			Object _obj=MapUtils.getObject(map,k);
			Element ne=ele.addElement(k);
			if(_obj instanceof Map){
				 addMap((Map)_obj,ne);
			}else if(_obj instanceof List){
				 addList((List)_obj,ne,ele,k);
			}else if(_obj instanceof String){
				ne.setText((String)_obj);
			}else if(_obj==null){
				ne.setText("");
			}else {
				ne.setText(MapUtils.getString(map,k));
			}
		}
	}
	
	public static  void addList(List list,Element ele,Element parentEle,String k){
		for(int i=0;i<list.size();i++){
			if(i>0){
				ele=parentEle.addElement(k);
			}
			Object _obj=list.get(i);
			if(_obj instanceof Map){
				 addMap((Map)_obj,ele);
			}else if(_obj instanceof List){
				throw new RuntimeException("not valide");
			}else if(_obj instanceof String){
				ele.setText((String)_obj);
			}else if(_obj==null){
				ele.setText("");
			}else {
				ele.setText((String)_obj);
			}
		}
	}
	private static Map Dom2Map(Element e) {   
	    Map map = new HashMap();    
	    List list = e.elements();   
	    if (list.size() > 0) {   
	        for (int i = 0; i < list.size(); i++) {   
	            Element iter = (Element) list.get(i);   
	            List mapList = new ArrayList();   
	  
	            if (iter.elements().size() > 0) {   
	                Map m = Dom2Map(iter);   
	                if (map.get(iter.getName()) != null) {   
	                    Object obj = map.get(iter.getName());   
	                    if (!obj.getClass().getName().equals(   
	                            "java.util.ArrayList")) {   
	                        mapList = new ArrayList();   
	                        mapList.add(obj);   
	                        mapList.add(m);   
	                    }   
	                    if (obj.getClass().getName().equals(   
	                            "java.util.ArrayList")) {   
	                        mapList = (List) obj;   
	                        mapList.add(m);   
	                    }   
	                    map.put(iter.getName(), mapList);   
	                } else  
	                    map.put(iter.getName(), m);   
	            } else {   
	                if (map.get(iter.getName()) != null) {   
	                    Object obj = map.get(iter.getName());   
	                    if (!obj.getClass().getName().equals(   
	                            "java.util.ArrayList")) {   
	                        mapList = new ArrayList();   
	                        mapList.add(obj);   
	                        mapList.add(iter.getText());   
	                    }   
	                    if (obj.getClass().getName().equals(   
	                            "java.util.ArrayList")) {   
	                        mapList = (List) obj;   
	                        mapList.add(iter.getText());   
	                    }   
	                    map.put(iter.getName(), mapList);   
	                } else  
	                    map.put(iter.getName(), iter.getText());   
	            }   
	        }   
	    } else  
	        map.put(e.getName(), e.getText());   
	    return map;   
	}  
	
	/*public static MobileInterface_PortType getIomService() throws ServiceException{
		MobileInterfaceServiceLocator service = new MobileInterfaceServiceLocator();
		MobileInterface_PortType client =  service.getMobileInterface();
		return client;
	}
	public static ZteSoftMobileInterface_PortType getIomService() throws ServiceException{
		ZteSoftMobileInterfaceServiceLocator service = new ZteSoftMobileInterfaceServiceLocator();
		ZteSoftMobileInterface_PortType client =  service.getZteSoftMobileInterface();
		return client;
	}
	*/
    //创建连接
	public static ZteSoftMobileInterfaceSoapBindingStub getService() throws MalformedURLException,ServiceException {
    	ZteSoftMobileInterfaceServiceLocator service = new ZteSoftMobileInterfaceServiceLocator();
    	
        //ResourceBundle res = ResourceBundle.getBundle("com.ztesoft.sa.inf.infConfig");
        String serviceAddress ="http://10.19.100.172:8080/IOMPROJ/services/ZteSoftMobileInterface";// res.getString("hardlinktest_service_address");
        String timeOut = null;//res.getString("timeOut");
        ZteSoftMobileInterfaceSoapBindingStub binding = null;
        if(serviceAddress==null || serviceAddress.equals("")){
        	System.out.println("固网测试系统的接口服务地址没有配置!");
        	throw new ServiceException("服务地址没有配置");
        }
        if(timeOut == null || timeOut.equals("")){
        	timeOut = "3000" ;
        }
        System.out.println("固网测试系统的接口服务地址为:" + serviceAddress +",超时时间为:" + timeOut);

        URL endpoint = new URL(serviceAddress);
        try {
        	binding = (ZteSoftMobileInterfaceSoapBindingStub)service.getZteSoftMobileInterface(endpoint);
        	binding.setTimeout(new Integer(timeOut).intValue());
            
        } catch (Exception ex) {
            System.out.println("获取服务报错!" + ex.getMessage());
            throw new ServiceException();
        }
        return binding;
    }
    

}
