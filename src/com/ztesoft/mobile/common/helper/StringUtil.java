package com.ztesoft.mobile.common.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String Html2Text(String inputString) {
		   //过滤html标签
		   String htmlStr = inputString; // 含html标签的字符串
		   String textStr = "";
		   java.util.regex.Pattern p_script;
		   java.util.regex.Matcher m_script;
		   java.util.regex.Pattern p_style;
		   java.util.regex.Matcher m_style;
		   java.util.regex.Pattern p_html;
		   java.util.regex.Matcher m_html;
		   java.util.regex.Pattern p_cont1;
		   java.util.regex.Matcher m_cont1;
		   java.util.regex.Pattern p_cont2;
		   java.util.regex.Matcher m_cont2;

		   try {
		    String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
		    // }
		    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
		    // }
		    String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		    String regEx_cont1 = "[\\s*`~!%\\(\\)\\+=|{}';'\\[\\].<>?~#￥%……（）+|‘”“’]"; // 定义HTML标签的正则表达式

		    String regEx_cont2 = "[\\w[^\\W]*]"; // 定义HTML标签的正则表达式[a-zA-Z]

		    p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		    m_script = p_script.matcher(htmlStr);
		    htmlStr = m_script.replaceAll(""); // 过滤script标签

		    p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		    m_style = p_style.matcher(htmlStr);
		    htmlStr = m_style.replaceAll(""); // 过滤style标签

		    p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		    m_html = p_html.matcher(htmlStr);
		    htmlStr = m_html.replaceAll(""); // 过滤html标签

		    p_cont1 = Pattern.compile(regEx_cont1, Pattern.CASE_INSENSITIVE);
		    m_cont1 = p_cont1.matcher(htmlStr);
		    htmlStr = m_cont1.replaceAll(""); // 过滤其它标签

//		    p_cont2 = Pattern.compile(regEx_cont2, Pattern.CASE_INSENSITIVE);
//		    m_cont2 = p_cont2.matcher(htmlStr);
//		    htmlStr = m_cont2.replaceAll(""); // 过滤html标签

		    textStr = htmlStr.replaceAll("&nbsp", "");

		   } catch (Exception e) {
		    System.err.println("Html2Text: " + e.getMessage());
		   }
		   return textStr;// 返回文本字符串
		}
	
	/** 
	 * 得到网页中图片的地址 
	  */  
	public static List getImgStr(String htmlStr){     
	     String img="";     
	     Pattern p_image;     
	     Matcher m_image;     
	     List pics = new ArrayList();  
	  
	     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址     
	     p_image = Pattern.compile   
	             (regEx_img,Pattern.CASE_INSENSITIVE);     
	    m_image = p_image.matcher(htmlStr);   
	    while(m_image.find()){     
	         img = img + "," + m_image.group();     
	         Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src  
	         while(m.find()){  
	            pics.add(m.group(1));  
	         }  
	     }     
	        return pics;     
	 }    
	/** 
	 * 得到网页中图片和文件的地址 并把ip地址去掉，这里不能直接替换，需找到图片
	  */  
	public static String getStrAndRep(String htmlStr){     
		String textStr = "";
		String img="";  
		String files="";
		Pattern p_image;     
		Matcher m_image;     
		Pattern p_file;
		Matcher m_file;
		String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址     
		String regEx_file = "<a.*href=(.*?)[^>]*?>";//上传文件路址
		p_image = Pattern.compile(regEx_img,Pattern.CASE_INSENSITIVE);     
		m_image = p_image.matcher(htmlStr);  
		p_file = Pattern.compile(regEx_file,Pattern.CASE_INSENSITIVE);     
		m_file = p_file.matcher(htmlStr);   
		
		String regex = "http://(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[:][\\d][\\d][\\d][\\d]";
		while(m_image.find()){     
			img = img + "," + m_image.group();     
			
			Matcher m  = Pattern.compile(regex).matcher(img); //匹配src  
			while(m.find()){  
				String imgstr = m.group();
				htmlStr = htmlStr.replaceAll(imgstr, "");
			}  
		}  
		while(m_file.find()){     
			files = files + "," + m_file.group();     
			Matcher m  = Pattern.compile(regex).matcher(files); //匹配src  
			while(m.find()){  
				String filestr = m.group();
				htmlStr = htmlStr.replaceAll(filestr, "");
			}  
		}     
		return htmlStr;     
	}    
	//按日期创建目录
	public static String createPath(String pathString){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String path2="";
		try {
			Date date = new Date();//df.parse(dateString);
			String path=pathString+"/"+new SimpleDateFormat("yyyy/MM/dd").format(date);
			//System.out.println(DateFormat.getDateInstance().format(date));
			StringTokenizer st=new StringTokenizer(path,"/");
			String path1=st.nextToken()+"/";
			path2 =path1;
			while(st.hasMoreTokens())
			{
				path1=st.nextToken()+"/";
				path2+=path1;
				File inbox = new File(path2);
				if(!inbox.exists())
					inbox.mkdir();
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(path2);
		return path2;
	}	
	
	/**
	 * 判断输入值是否为空或者空字符串
	 * @return  空：true <br> 非空：false
	 */
	public static boolean isNull(String str){
		if(str == null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断输入值是否为空或者空字符串
	 * @return  空：true <br> 非空：false
	 */
	public static boolean isNull(Long l){
		return l == null ? true : false;
	}
	
	/**
	 * @param str
	 * @return
	 */
	public final static String leftTrim(String str){
		if(str == null)
			return "";
		while ((str.indexOf(" ")==0) && (str.length()>1)){
			str=str.substring(1,str.length());   //去除前面空格
		}
		
		return str;

	}
	
	/**
	 * 去掉字符串左则的空则
	 * @param str
	 * @return
	 */
	public final static String null2String(String str){
		return isNull(str) ? "" :str;
	}

	/**
	 * 将日期转换成指定的格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date,String format){
		String result = "";
		try{
			DateFormat df = new SimpleDateFormat(format);
			if(date != null){
				result = df.format(date);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 将日期型转换为短日期格式(如2011-01-01)
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
		return date2String(date,"yyyy-MM-dd");
	}
	
	/**
	 * 将日期型转换为短日期格式(如2011-01-01)
	 * @param date
	 * @return
	 */
	public static String date2LongString(Date date){
		return date2String(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	public static String decode(String str){
		return decode(str,"UTF-8");
	}
	
	public static String decode(String str,String enc){
		try {
			return isNull(str) ? "" : java.net.URLDecoder.decode(str,enc);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static void main(String[] args) {

		String cont = "<img src=\"http://120.20.39.54/IOMPROJ/de.jpg\">3234234234234324<img src=\"http://sina.com/IOMPOJ/d.jpg\"><A href=\"http://10.40.199.185:9082/IOMPROJ/infomanager/IOMPROJ/download/news/2010/10/27/20101027113600-65231-error.txt\" target=_blank>error.txt</A>&nbsp;&nbsp;3333";
		
		System.out.println(getStrAndRep(cont));
	
	}
	
}
