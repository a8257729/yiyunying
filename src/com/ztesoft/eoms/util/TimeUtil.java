package com.ztesoft.eoms.util;

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
//	当前时间加1个月
	public static String getNowTimeAddMonth(){        
		Calendar   c   =   Calendar.getInstance();
		//c.add(c.DATE,-15); 
		c.add(c.MONTH,+1);
		String time = ""+c.get(c.YEAR)+"-"+(c.get(c.MONTH)+1)+"-"+c.get(c.DATE)+" "+c.get(c.HOUR_OF_DAY)+":"+c.get(c.MINUTE)+":"+c.get(c.SECOND); 
		String returnstr = "";
		try{
			Date d = parses(time,"yyyy-MM-dd HH:mm:ss");
			//System.out.println(SimpleDateFormat(d,"yyyy-MM-dd HH:mm:ss"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			returnstr = formatter.format(d);
		}catch(Exception e){

		}
		return returnstr;

	}	
	public static String getNowTime(){
		Date nowDate = new Date(); 
		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = formatter.format(now.getTime());
		return str;
	}
}
