package com.ztesoft.mobile.v2.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class GZipUtils {
	
	public static final String DEFAULT_ENCODING = "ISO-8859-1";
	
	//压缩
    public static String compress(String str) throws IOException {   
      if (str == null || str.length() == 0) {   
        return str;   
      }   
      ByteArrayOutputStream out = new ByteArrayOutputStream();   
      GZIPOutputStream gzip = new GZIPOutputStream(out);   
      gzip.write(str.getBytes());   
      gzip.close();   
      return out.toString(DEFAULT_ENCODING);   
    }  
    
    //解压
    public static String uncompress(String str) throws IOException {   
      if (str == null || str.length() == 0) {   
        return str;   
      }   
      ByteArrayOutputStream out = new ByteArrayOutputStream();   
      ByteArrayInputStream in = new ByteArrayInputStream(str   
          .getBytes(DEFAULT_ENCODING));   
      GZIPInputStream gunzip = new GZIPInputStream(in);   
      byte[] buffer = new byte[256];   
      int n;   
      while ((n = gunzip.read(buffer)) >= 0) {   
        out.write(buffer, 0, n);   
      }   
      return out.toString();
    }  
    
    public static void main(String args[]) throws Exception {
    	String heison="我是正文";
    	String ext = "";
    	System.out.println(ext=GZipUtils.compress(heison));
    	System.out.println(GZipUtils.uncompress(ext));
    }
    
}
