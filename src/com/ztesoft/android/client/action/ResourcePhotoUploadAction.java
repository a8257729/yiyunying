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
		System.out.println("�������??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		JSONObject body = (JSONObject) (jsonObject.get("body")==null?(new JSONObject()):jsonObject.get("body"));
		String photoStr = body.getString("photo")==null?"-1":body.getString("photo");
		String resCode = body.getString("resCode")==null?"-1":body.getString("resCode");//�豸���
		String comments = body.getString("comments")==null?"-1":body.getString("comments");
		String latitude = body.getString("latitude")==null?"-1":body.getString("latitude");//γ��
		String longitude = body.getString("photo")==null?"-1":body.getString("longitude");//����
		
		String photoName = savePhoto("", photoStr, request);//����ͼƬ,����ͼƬ����
		
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
		System.out.println("ԭ�ַ���="+jsondata);   
	    System.out.println("ԭ��="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("ѹ������ַ���="+newstr);   
	    System.out.println("ѹ����ĳ�="+newstr.length());  
	      
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
//		System.out.println("�ϴ�����˱��������" + photoStr);
		String photoStr = jsonObj.getString("photo");
		String workOrderId = jsonObj.getString("WorkOrderID");
		String resCode = jsonObj.getString("resCode");
		
		if(photoStr == "" || photoStr.length() < 3)return "";
		String resultCode = null;
		
		String photoName = "";
		try {
			photoName = savePhoto(workOrderId, photoStr, request);//����ͼƬ,����ͼƬ����
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
	 * ��ʮ�������ַ���תΪ�ֽ�����
	 * 
	 * @param pic
	 * @return
	 */
	public static byte[] convertHexStringToByte(String pic)
	{
		byte[] result = new byte[pic.length() / 2]; // ������ÿ��λ���һ��ʮ��������
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
		File tempPhotoFile = new File(uploadPath); // �����Ƭ���ļ�����	
		System.out.println("========ResourcePhotoUploadAction======"+uploadPath);
		boolean b;
		if (tempPhotoFile.exists())
		{
			b = tempPhotoFile.delete(); // ���ļ�������ɾ��
		}
		tempPhotoFile.createNewFile(); // need add permission to		
		FileOutputStream fos = new FileOutputStream(tempPhotoFile);
		fos.write(photoBytes);
		fos.close();
		return tempPhotoFile.getName();
	}

}
