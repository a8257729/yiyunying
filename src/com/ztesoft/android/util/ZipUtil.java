package com.ztesoft.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {


	// 压缩
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}
	// 解压缩
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}
	

    // 测试方法   
    public static void main(String[] args) throws IOException {   
      String temp = "" +
      "{\"result\": \"000\", \"body\": {\"muneList\": [{\"displayindex\":2,\"type\""+
      ":\"2\",\"title\":\"服务开通\",\"syscode\":\"002\",\"getmethod\":null,\"icon\":\"wycs.png\",\"topa"+
      "ge\":\"page2\",\"leaf\":0,\"isbg\":\"true\",\"muneid\":71},{\"displayindex\":4,\"type\":\"6\",\"ti"+
      "tle\":\"GIS定位\",\"syscode\":\"004\",\"getmethod\":\"GISMethod\",\"icon\":\"zxdw.png\",\"topage"+
      "\":\"zxdwpage\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":182},{\"displayindex\":4,\"type\":\"7\",\""+
      "title\":\"电路查询\",\"syscode\":\"002\",\"getmethod\":null,\"icon\":\"button.png\",\"topage\":"+
      "\"querygldl\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":202},{\"displayindex\":4,\"type\":\"1\",\"t"+
      "itle\":\"调用统计\",\"syscode\":\"003\",\"getmethod\":null,\"icon\":\"stat.png\",\"topage\":\"ot"+
      "herStatList\",\"leaf\":0,\"isbg\":\"true\",\"muneid\":342},{\"displayindex\":4,\"type\":\"11\","+
      "\"title\":\"光路查询(RM)\",\"syscode\":\"RM\",\"getmethod\":\"com.ztesoft.android.manager.u"+
      "i.MobileReport\",\"icon\":\"zxdw.png\",\"topage\":\"com.ztesoft.android.LoginService\",\"l"+
      "eaf\":1,\"isbg\":\"true\",\"muneid\":86},{\"displayindex\":4,\"type\":\"6\",\"title\":\"扫描\",\"s"+
      "yscode\":\"003\",\"getmethod\":\"BARCODESCAN\",\"icon\":\"smcx.png\",\"topage\":\"BARCODESCAN\""+
      ",\"leaf\":1,\"isbg\":\"true\",\"muneid\":181},{\"displayindex\":4,\"type\":\"2\",\"title\":\"资源"+
      "管理\",\"syscode\":\"002\",\"getmethod\":null,\"icon\":\"info.png\",\"topage\":\"page3\",\"leaf\""+
      ":0,\"isbg\":\"true\",\"muneid\":73},{\"displayindex\":8,\"type\":\"11\",\"title\":\"工单查询(AT"+
      "OM)\",\"syscode\":\"ATOM\",\"getmethod\":\"com.xumin.zxing.OrderInfoActivity\",\"icon\":\"in"+
      "fo.png\",\"topage\":\"com.common.LoginService\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":244},"+
      "{\"displayindex\":9,\"type\":\"11\",\"title\":\"改接入方式(RM)\",\"syscode\":\"RM\",\"getmethod"+
      "\":\"com.ztesoft.android.manager.accesskind.ChangeAccessKind\",\"icon\":\"zsk.png\",\"to"+
      "page\":\"com.ztesoft.android.LoginService\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":245},{\""+
      "displayindex\":9,\"type\":\"9\",\"title\":\"流程查询\",\"syscode\":\"004\",\"getmethod\":null,\""+
      "icon\":\"tache.gif\",\"topage\":\"flowdispriv\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":262},{\""+
      "displayindex\":9,\"type\":\"11\",\"title\":\"资源指标(ATOM)\",\"syscode\":\"ATOM\",\"getmethod"+
      "\":\"com.xumin.zxing.ReportRmActivity\",\"icon\":\"task.png\",\"topage\":\"com.common.Logi"+
      "nService\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":246},{\"displayindex\":13,\"type\":\"12\",\"t"+
      "itle\":\"统计报表\",\"syscode\":\"003\",\"getmethod\":\"stat\",\"icon\":\"stat.png\",\"topage\":\""+
      "statpage\",\"leaf\":1,\"isbg\":\"true\",\"muneid\":282}]}}"+
      		" ";   
      System.out.println("原字符串="+temp);   
      System.out.println("原长="+temp.length());   
      String temp1 = ZipUtil.compress(temp);   
      System.out.println("压缩后的字符串="+temp1);   
      System.out.println("压缩后的长="+temp1.length());   
      System.out.println("解压后的字符串="+ZipUtil.uncompress(temp1));   
		
		
	}

}
