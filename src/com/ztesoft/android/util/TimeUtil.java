package com.ztesoft.android.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		}
		catch (Exception e) {
		}
		return result;
	}

	public static Date parses(String strDate, String pattern)   throws   ParseException   {   
		return new SimpleDateFormat(pattern).parse(strDate);   
	} 

	public static java.sql.Date strToDate(String strDate) {    
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    
		ParsePosition pos = new ParsePosition(0);    
		Date strtodate = formatter.parse(strDate, pos);    
		return (java.sql.Date) strtodate;     
	}  
	public static String getNowTime(){
		Date nowDate = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = formatter.format(now.getTime());
		return str;
	}
	public static String strObj(Object obj){	
		return obj==null?"-1":obj.toString();
	}

	public static String strParse(String strdata,String mappdata){
		
		String strsplit[] = strdata.split(":");
		String newstrData = "";
		if(strsplit.length>1){
			for(String s : strsplit){
				newstrData +="<"+s+">"+"|</"+s+">|"+"<"+s+"/>|";
			}
			return "(?:"+newstrData.substring(0, newstrData.length()-1)+")";
		}else {
			if(mappdata.equals("")){
				return "<"+strdata+">|</"+strdata+">";
			}else {
			  return strdata;
			}
		}

	}
	 public static void main(String[] args) {  
		 String dstrdata="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
			"<Data>" +
			"<QueryMethod>queryWorkOrderForEBiz</QueryMethod>" + 
			"<Return>" + 
			"<Result>000</Result>" + 
			"<ErrorDesc>失败信息描述</ErrorDesc>" +
			"<ContentList>" + 
			"<Content>" + 
			"<Public>" + 
			"<WorkOrderID>123</WorkOrderID>" + 
			"<OrderCode>定单编码</OrderCode>" + 
			"<ServiceName>业务名称</ServiceName>" + 
			"<AccNbr>业务号码</AccNbr>" + 
			"<CustName>客户名称</CustName>" + 
			"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
			"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
			"<TacheName>环节名称</TacheName>" + 
			"<TacheCode>环节编码</TacheCode>" + 
			"<Address>装机地址</Address>" + 
			"<SlaTime>预约时间</SlaTime>" + 
			"<WorkOrderType>工单类型</WorkOrderType>" + 
			"<CreateDate>工单派发时间</CreateDate>" + 
			"</Public>" + 
			"</Content>" +
			"<Content>" + 
			"<Public>" + 
			"<WorkOrderID>123</WorkOrderID>" + 
			"<OrderCode>定单编码</OrderCode>" + 
			"<ServiceName>业务名称</ServiceName>" + 
			"<AccNbr>业务号码</AccNbr>" + 
			"<CustName>客户名称</CustName>" + 
			"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
			"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
			"<TacheName>环节名称</TacheName>" + 
			"<TacheCode>环节编码</TacheCode>" + 
			"<Address>装机地址</Address>" + 
			"<SlaTime>预约时间</SlaTime>" + 
			"<WorkOrderType>工单类型</WorkOrderType>" + 
			"<CreateDate>工单派发时间</CreateDate>" + 
			"</Public>" + 
			"</Content>" +
			"</ContentList>" + 
			"</Return>" + 
			"</Data>";
		 String ndatas = TimeUtil.strParse("Return:Content","");
		 System.out.println(ndatas);
		 String n =  dstrdata.replaceAll(ndatas, "");
		 System.out.println(n);
		 XmlToJsonUtils jsonUtil = new XmlToJsonUtils();
	    	String json = jsonUtil.GetJsonStrFromXML1(n); 
		 System.out.println(json);
	 }
}
