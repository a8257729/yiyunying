package com.ztesoft.android.client.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.client.dao.MobileResPhotoUploadDAO;
import com.ztesoft.android.client.dao.MobileResPhotoUploadDAOImpl;
import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class ResourcePhotoUploadAction implements AndrBaseAction {//2012-04-10 by guo.jinjun

	public Object doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		JSONObject body = (JSONObject) (jsonObject.get("body")==null?(new JSONObject()):jsonObject.get("body"));
		String photoStr = body.getString("photo")==null?"-1":body.getString("photo");
		String resCode = body.getString("resCode")==null?"-1":body.getString("resCode");//设备编号
		String comments = body.getString("comments")==null?"-1":body.getString("comments");
		String latitude = body.getString("latitude")==null?"-1":body.getString("latitude");//纬度
		String longitude = body.getString("photo")==null?"-1":body.getString("longitude");//经度
		
		String photoName = savePhoto("", photoStr, request);//保存图片,返回图片名称
		
		Map param = new HashMap();
		param.put("photoName", photoName);
		param.put("resCode", resCode);
		param.put("comments", comments);
		param.put("latitude", latitude);
		param.put("longitude", longitude);
		try {
			getMobileResPhotoUploadDAO().insert(param);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		String jsondata = "{\"result\": \"000\"}";
		System.out.println("原字符串="+jsondata);   
	    System.out.println("原长="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
	    response.setContentType("text/plain;charset=ISO-8859-1");
	    response.getWriter().write(newstr);
		
		return null;
	}
	
	private static MobileResPhotoUploadDAO getMobileResPhotoUploadDAO()
	{
		String daoName = MobileResPhotoUploadDAOImpl.class.getName();
		return (MobileResPhotoUploadDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	public static String uploadPic(JSONObject jsonObj, HttpServletRequest request){
//		System.out.println("上传服务端保存的数据" + photoStr);
		String photoStr = jsonObj.getString("photo");
		String workOrderId = jsonObj.getString("WorkOrderID");
		String resCode = jsonObj.getString("resCode");
		
		if(photoStr == "" || photoStr.length() < 3)return "";
		String resultCode = null;
		
		String photoName = "";
		try {
			photoName = savePhoto(workOrderId, photoStr, request);//保存图片,返回图片名称
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Map param = new HashMap();
		param.put("photoName", photoName);
		param.put("workOrderId", workOrderId);
		param.put("resCode", resCode);
		try {
			getMobileResPhotoUploadDAO().insert(param);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return photoName;
	}
	
	/**
	 * 将十六进制字符串转为字节数组
	 * 
	 * @param pic
	 * @return
	 */
	public static byte[] convertHexStringToByte(String pic)
	{
		byte[] result = new byte[pic.length() / 2]; // 数组中每两位存放一个十六进制码
		for (int i = 0; i < pic.length();)
		{
			int temp = Integer.valueOf(pic.substring(i, i + 2), 16);
			if (temp > 127)
				temp -= 256;
			result[i / 2] = (byte) temp;
			i += 2;
		}
		return result;
	}
	
	public static String savePhoto(String workOrderID, String photoStr, HttpServletRequest request) throws IOException{
		byte[] photoBytes = convertHexStringToByte(photoStr);
		Date now = new Date();
		String uploadPath = request.getSession().getServletContext().getRealPath("/") + "/images/resourcephoto/" 
			+ "workOrderID_"+workOrderID+"_"+now.getYear()+"-"+now.getMonth()+"-"+now.getDate()+"_"+now.getHours()+".jpg";
		File tempPhotoFile = new File(uploadPath); // 获得照片的文件对象	
		System.out.println("========ResourcePhotoUploadAction======"+uploadPath);
		boolean b;
		if (tempPhotoFile.exists())
		{
			b = tempPhotoFile.delete(); // 若文件存在则删除
		}
		tempPhotoFile.createNewFile(); // need add permission to		
		FileOutputStream fos = new FileOutputStream(tempPhotoFile);
		fos.write(photoBytes);
		fos.close();
		return tempPhotoFile.getName();
	}

}
