package com.ztesoft.mobile.etl.util;

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

	public static Date strToDate(String strDate) {    
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    
		ParsePosition pos = new ParsePosition(0);    
		Date strtodate = formatter.parse(strDate, pos);    
		return (Date) strtodate;     
	}  
	public static String dateAddOrDele(String time,int i){
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date dt;
		Calendar cal = Calendar.getInstance();
		try {
			dt = formatter.parse(time);
			cal.setTime(dt);
			cal.add(Calendar.HOUR,i);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatter.format(cal.getTime());
	}
	public static String getNowTime(){
		Date nowDate = new Date(); 
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = formatter.format(now.getTime());
		return str;
	}
	public static void main(String args[]){
		
		try {
			System.out.println(TimeUtil.parses("2011-01-23 10:32:30","yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
