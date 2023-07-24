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
			"<ErrorDesc>ʧ����Ϣ����</ErrorDesc>" +
			"<ContentList>" + 
			"<Content>" + 
			"<Public>" + 
			"<WorkOrderID>123</WorkOrderID>" + 
			"<OrderCode>��������</OrderCode>" + 
			"<ServiceName>ҵ������</ServiceName>" + 
			"<AccNbr>ҵ�����</AccNbr>" + 
			"<CustName>�ͻ�����</CustName>" + 
			"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
			"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
			"<TacheName>��������</TacheName>" + 
			"<TacheCode>���ڱ���</TacheCode>" + 
			"<Address>װ����ַ</Address>" + 
			"<SlaTime>ԤԼʱ��</SlaTime>" + 
			"<WorkOrderType>��������</WorkOrderType>" + 
			"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
			"</Public>" + 
			"</Content>" +
			"<Content>" + 
			"<Public>" + 
			"<WorkOrderID>123</WorkOrderID>" + 
			"<OrderCode>��������</OrderCode>" + 
			"<ServiceName>ҵ������</ServiceName>" + 
			"<AccNbr>ҵ�����</AccNbr>" + 
			"<CustName>�ͻ�����</CustName>" + 
			"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
			"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
			"<TacheName>��������</TacheName>" + 
			"<TacheCode>���ڱ���</TacheCode>" + 
			"<Address>װ����ַ</Address>" + 
			"<SlaTime>ԤԼʱ��</SlaTime>" + 
			"<WorkOrderType>��������</WorkOrderType>" + 
			"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
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
