package com.ztesoft.eoms.mobileoffice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String Html2Text(String inputString) {
		   //����html��ǩ
		   String htmlStr = inputString; // ��html��ǩ���ַ���
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
		    String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // ����script���������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
		    // }
		    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // ����style���������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
		    // }
		    String regEx_html = "<[^>]+>"; // ����HTML��ǩ���������ʽ

		    String regEx_cont1 = "[\\s*`~!%\\(\\)\\+=|{}';'\\[\\].<>?~#��%��������+|��������]"; // ����HTML��ǩ���������ʽ

		    String regEx_cont2 = "[\\w[^\\W]*]"; // ����HTML��ǩ���������ʽ[a-zA-Z]

		    p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		    m_script = p_script.matcher(htmlStr);
		    htmlStr = m_script.replaceAll(""); // ����script��ǩ

		    p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		    m_style = p_style.matcher(htmlStr);
		    htmlStr = m_style.replaceAll(""); // ����style��ǩ

		    p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		    m_html = p_html.matcher(htmlStr);
		    htmlStr = m_html.replaceAll(""); // ����html��ǩ

		    p_cont1 = Pattern.compile(regEx_cont1, Pattern.CASE_INSENSITIVE);
		    m_cont1 = p_cont1.matcher(htmlStr);
		    htmlStr = m_cont1.replaceAll(""); // ����������ǩ

//		    p_cont2 = Pattern.compile(regEx_cont2, Pattern.CASE_INSENSITIVE);
//		    m_cont2 = p_cont2.matcher(htmlStr);
//		    htmlStr = m_cont2.replaceAll(""); // ����html��ǩ

		    textStr = htmlStr.replaceAll("&nbsp", "");

		   } catch (Exception e) {
		    System.err.println("Html2Text: " + e.getMessage());
		   }
		   return textStr;// �����ı��ַ���
		}
	
	/** 
	 * �õ���ҳ��ͼƬ�ĵ�ַ 
	  */  
	public static List getImgStr(String htmlStr){     
	     String img="";     
	     Pattern p_image;     
	     Matcher m_image;     
	     List pics = new ArrayList();  
	  
	     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //ͼƬ���ӵ�ַ     
	     p_image = Pattern.compile   
	             (regEx_img,Pattern.CASE_INSENSITIVE);     
	    m_image = p_image.matcher(htmlStr);   
	    while(m_image.find()){     
	         img = img + "," + m_image.group();     
	         Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //ƥ��src  
	         while(m.find()){  
	            pics.add(m.group(1));  
	         }  
	     }     
	        return pics;     
	 }    
}