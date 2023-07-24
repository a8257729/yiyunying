package com.ztesoft.mobile.service.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.client.dao.MobileResPhotoUploadDAO;
import com.ztesoft.android.client.dao.MobileResPhotoUploadDAOImpl;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class PhotoUploadHandler extends AbstractHandler {

	private static MobileResPhotoUploadDAO getMobileResPhotoUploadDAO()
	{
		String daoName = MobileResPhotoUploadDAOImpl.class.getName();
		return (MobileResPhotoUploadDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	public static String getPath() {
		Properties prop = new Properties();
		try{
			InputStream in = AbstractHandler.class.getClassLoader().getResourceAsStream("cfg/monitor.properties");
			if(null == in) {
				throw new IOException("cfg/monitor.properties"+"配置文件未找到");
			} else {
				prop.load(in);
			}
		} catch(IOException e) {
			System.out.println("cfg/monitor.properties"+"配置文件未找到...");
			e.printStackTrace();
		}
		return prop.getProperty("UPLOAD_PHOTO_PATH");
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
	
	public static Map savePhoto(String username, String photoStr) throws IOException{
		byte[] photoBytes = convertHexStringToByte(photoStr);
		
		Date now = new Date();
		SimpleDateFormat photoName = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat pathName = new SimpleDateFormat("yyyy-MM-dd");
		String uploadPath = getPath() + File.separator + pathName.format(now) + File.separator + username + "_" + photoName.format(now) + ".jpg";
		File tempPhotoFile = new File(uploadPath); // 获得照片的文件对象	
		System.out.println("========PhotoUploadHandler======"+uploadPath);
		
		if (!tempPhotoFile.getParentFile().exists()){
			tempPhotoFile.getParentFile().mkdirs();//若目录不存在,则创建
		}
		if (tempPhotoFile.exists())
		{
			tempPhotoFile.delete(); // 若文件存在则删除
		}
		tempPhotoFile.createNewFile(); // need add permission to		
		FileOutputStream fos = new FileOutputStream(tempPhotoFile);
		fos.write(photoBytes);
		fos.close();
		
		Map map = new HashMap();
		map.put("photoPath", uploadPath);
		map.put("photoName", tempPhotoFile.getName());
		map.put("username", username);
		return map;
	}

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		int result = -1;//0成功,-1未知,11图片保存失败,12数据库插入失败
		String jsonPara = MapUtils.getString(paramMap, "params");
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		
		String QueryMethod = jsonObject.getString("QueryMethod");
		String username = jsonObject.getString("username");
		String photo = jsonObject.getString("photo");
		
		Map map = null;
		try {
			map = savePhoto(username, photo);//保存图片,返回图片名称
			result = 0;
		} catch (IOException e) {
			e.printStackTrace();
			result = 11;
		}				
//		map.put("username", jsonObject.getString("username"));		
		try {
			getMobileResPhotoUploadDAO().insert(map);
			result = 0;
		} catch (DataAccessException e) {
			e.printStackTrace();
			result = 12;
		}
		//去掉无用数据,省流量
		map.remove("photoPath");
		map.remove("photoId");
		
		String jsonData = JsonUtil.getJsonDataByMap(map);
		JSONObject returnJson = new JSONObject();
		returnJson.put("result", result);
		returnJson.put("jsonData", jsonData);
		String newstr = returnJson.toString();
		System.out.println("======[info]=====返回的数据: "+newstr);
		newstr = ZipUtil.compress(newstr);
		paramMap.put("response", newstr);
	}

}
