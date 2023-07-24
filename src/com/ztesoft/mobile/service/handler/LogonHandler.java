package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.dao.SystemInfoDAO;
import com.ztesoft.android.dao.SystemInfoDAOImpl;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class LogonHandler extends AbstractHandler {

    public static final Map<String ,Integer> osMapping = new HashMap<String, Integer>();
    static {
        osMapping.put("android", new Integer(0));
        osMapping.put("ios", new Integer(1));
        osMapping.put("wp", new Integer(2));
    }
	
	private static String LOGON_SUCCESS = "000";// �û���???����У���??
	private static String USERNAME_NOT_EXIST = "005";// �û���������
	private static String PASSWORD_ERROR = "006";// �������
	private static String USERNAME_NULL = "007";// �û���Ϊ??
	private static String PASSWORD_NULL = "008";// ����Ϊ��
	private static String JSON_PARA_ERROR = "009";// �����JSON���ݸ�ʽ����??

	private static String CONNECTION_ERROR = "010";//���ݿ�������??
	private static String PNONENUMBER_ERROR = "012";//�ֻ��������
	private static String AUTOLOGIN_ERROR = "2003";//�Զ���¼״̬��������״̬��һ��,�����˺ŵ�¼
	private static String IMSI_ERROR = "2004";//IMSI�����,�����°�
	
	JSONObject device = new JSONObject();
	
	  protected void processHandle(Map paramMap) throws Exception {
		//Map tmpMap = MapUtils.getMap(paramMap, "ext");R
		String params = MapUtils.getString(paramMap, "params");
		String os = MapUtils.getString(paramMap, "os", null);
		//
		System.out.println("�ͻ���OS��" + os);
		String jsonPara = URLDecoder.decode(params, "UTF-8");
		System.out.println("�������??" + jsonPara);
		String resultCode = null;
		String para_username = null; 
		String para_password = null;
		String phoneNumber = null;
		boolean isPhoneAutoLogin = false;
		String jsonjob = null;      //ְλ��Ϣ
		String versionjson = null;  //������apk��Ϣ
		String staffMapping = null; //��Աӳ����Ϣ
		String othApkInfo = null;  //ҵ��ϵͳ��apk��Ϣ
		String othApkMethod = null;  //ҵ��ϵͳ��apk������Ϣ
		String muneTabs = null; //��ҳ�˵�tabҳ
		String staffId=null;
		boolean isAuto = false;
		try
		{
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
			para_username = jsonObject.optString("username", "");
			para_password = jsonObject.optString("password", "");
			//ע: iOSƽ̨�ϻ�ȡIMIS�ŵķ�����˽�еģ�������ʹ��
			phoneNumber =  jsonObject.optString("serialImis","");
			isPhoneAutoLogin = jsonObject.optBoolean("isPhoneAutoLogin", false);
			device = jsonObject.optJSONObject("device");
			//
			//System.out.println("para_userId "+device.toString());
		} catch (JSONException e)
		{
			resultCode = JSON_PARA_ERROR;
			e.printStackTrace();
		}
		if(isPhoneAutoLogin){
		
			Map map = null;
			try {
				if(StringUtils.isBlank(phoneNumber)){
					resultCode=PNONENUMBER_ERROR;
				}else {
					map = getServiceDAO().getPasswordByPhoneNO(phoneNumber);  //�ֻ����Զ���¼					
					if(map==null){
						resultCode=IMSI_ERROR;
					}else {
						staffId = map.get("staffId").toString();
						int agent = map.get("agent")==null?0:1;
						isAuto = agent==0?false:true;
						if(isPhoneAutoLogin == isAuto){  //����ͻ��˵��Զ���¼״̬������˲�һ�£��������Զ���¼		
							device.put("staffId", staffId);
							resultCode = LOGON_SUCCESS;// ��¼�ɹ�
							jsonjob = getServiceDAO().getJobData(staffId);
							staffMapping = getServiceDAO().getMappingSession(staffId);
							versionjson = getServiceDAO().getVersionNo();
							othApkInfo = getServiceDAO().getOtherApkInfo();
							othApkMethod = getServiceDAO().getOtherApkMethodInfo();
							muneTabs = getServiceDAO().getMuneTabs(staffId, osMapping.get(os));
						}else {
						  resultCode=AUTOLOGIN_ERROR;
						}
					}
				}
			} catch (DataAccessException e1) {
				resultCode=CONNECTION_ERROR;
				e1.printStackTrace();
			}
		}else {
			if (StringUtils.isBlank(para_username))
			{// �û���Ϊ??
				resultCode = USERNAME_NULL;
			} else
			{// �û�����Ϊ��
				if (StringUtils.isBlank(para_password)) {// ����Ϊ��
					resultCode = PASSWORD_NULL;
				} else {// ���벻Ϊ??
					String password = null;
					Map map = null;
					try {
						map = getServiceDAO().getPasswordByUserName(para_username);
						password = MapUtils.getString(map, "password");

					} catch (DataAccessException e1) {
						resultCode=CONNECTION_ERROR;
						password = null;
						e1.printStackTrace();
					}
					System.out.println("password  "+password);
					if (StringUtils.isBlank(password)) {// �����ڸ��û�
						resultCode = USERNAME_NOT_EXIST;
					} else { // ���ڸ��û���Ȼ��У������
						byte[] bytes = para_password.getBytes(); 
						String oldBPassWord = "{SHA}"+new String(new Base64It().encode(bytes));
						if (password.equals(para_password)||password.equals(oldBPassWord)) {
							staffId = map.get("staffId")+"";
							if(!"ios".equalsIgnoreCase(os)) {
								device.put("staffId", staffId);
							}
							resultCode = LOGON_SUCCESS;
							// ��¼�ɹ�
							try {
								jsonjob = getServiceDAO().getJobData(staffId);
								staffMapping = getServiceDAO().getMappingSession(staffId);
								versionjson = getServiceDAO().getVersionNo();
								othApkInfo = getServiceDAO().getOtherApkInfo();
								othApkMethod = getServiceDAO().getOtherApkMethodInfo();
								muneTabs = getServiceDAO().getMuneTabs(staffId, osMapping.get(os));
							} catch (DataAccessException e) {
								resultCode=CONNECTION_ERROR;
								e.printStackTrace();
							}
						} else
						{
							resultCode = PASSWORD_ERROR;// �������
						}
					}
				}
			}
		}

		JSONObject resultObject = new JSONObject();
		JSONObject bodyObject = new JSONObject();
		bodyObject.put("jobList", jsonjob);
		
		if(!"ios".equalsIgnoreCase(os) ){		//android
			if(versionjson !=null){
				   bodyObject.put("new_version", versionjson);  
				}
				if(staffMapping !=null){
					bodyObject.put("staffMapping", staffMapping);			
				}
				if(othApkInfo !=null){
					bodyObject.put("othApkInfo", othApkInfo);			
				}
				if(othApkMethod !=null){
					bodyObject.put("othApkMethod", othApkMethod);			
				}

		} else  {	//ios
			//TODO ��ȡ����������
		}
		
		if(muneTabs !=null){
			bodyObject.put("muneTabs", muneTabs);			
		}	

		//else{
		//	resultCode="1004";
		//}
		
		resultObject.put("result", resultCode); 
		resultObject.put("body", bodyObject);
		
		System.out.println("ԭ�ַ���="+resultObject.toString());   
	    String newstr = ZipUtil.compress(resultObject.toString());   
	    paramMap.put("response", newstr);
	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private SystemInfoDAO getSystemInfoDAO() {
		String daoName = SystemInfoDAOImpl.class.getName();
		return (SystemInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
