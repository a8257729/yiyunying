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
				throw new IOException("cfg/monitor.properties"+"�����ļ�δ�ҵ�");
			} else {
				prop.load(in);
			}
		} catch(IOException e) {
			System.out.println("cfg/monitor.properties"+"�����ļ�δ�ҵ�...");
			e.printStackTrace();
		}
		return prop.getProperty("UPLOAD_PHOTO_PATH");
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
	
	public static Map savePhoto(String username, String photoStr) throws IOException{
		byte[] photoBytes = convertHexStringToByte(photoStr);
		
		Date now = new Date();
		SimpleDateFormat photoName = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat pathName = new SimpleDateFormat("yyyy-MM-dd");
		String uploadPath = getPath() + File.separator + pathName.format(now) + File.separator + username + "_" + photoName.format(now) + ".jpg";
		File tempPhotoFile = new File(uploadPath); // �����Ƭ���ļ�����	
		System.out.println("========PhotoUploadHandler======"+uploadPath);
		
		if (!tempPhotoFile.getParentFile().exists()){
			tempPhotoFile.getParentFile().mkdirs();//��Ŀ¼������,�򴴽�
		}
		if (tempPhotoFile.exists())
		{
			tempPhotoFile.delete(); // ���ļ�������ɾ��
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
		int result = -1;//0�ɹ�,-1δ֪,11ͼƬ����ʧ��,12���ݿ����ʧ��
		String jsonPara = MapUtils.getString(paramMap, "params");
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		
		String QueryMethod = jsonObject.getString("QueryMethod");
		String username = jsonObject.getString("username");
		String photo = jsonObject.getString("photo");
		
		Map map = null;
		try {
			map = savePhoto(username, photo);//����ͼƬ,����ͼƬ����
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
		//ȥ����������,ʡ����
		map.remove("photoPath");
		map.remove("photoId");
		
		String jsonData = JsonUtil.getJsonDataByMap(map);
		JSONObject returnJson = new JSONObject();
		returnJson.put("result", result);
		returnJson.put("jsonData", jsonData);
		String newstr = returnJson.toString();
		System.out.println("======[info]=====���ص�����: "+newstr);
		newstr = ZipUtil.compress(newstr);
		paramMap.put("response", newstr);
	}

}
