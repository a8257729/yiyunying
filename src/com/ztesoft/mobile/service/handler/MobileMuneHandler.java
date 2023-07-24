package com.ztesoft.mobile.service.handler;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class MobileMuneHandler extends AbstractHandler {
      //
      public static final Map<String ,Integer> osMapping = new HashMap<String, Integer>();
      static {
          osMapping.put("android", new Integer(0));
          osMapping.put("ios", new Integer(1));
          osMapping.put("wp", new Integer(2));
      }
	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
        String os = MapUtils.getString(paramMap, "os");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("�������??" + jsonPara);
		String resultCode = null;

		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String StaffId = TimeUtil.strObj(jsonObject.get("StaffId"));               //����ID
        String returnData = TimeUtil.strObj(jsonObject.get("returnData"));
        String orgId = TimeUtil.strObj(jsonObject.get("OrgId"));             //����ID
        String jobId = TimeUtil.strObj(jsonObject.get("JobId"));             //��ǰְλ
        String defaultJobId = TimeUtil.strObj(jsonObject.get("DefaultJobId")); //Ĭ��ְλ
        String userName = TimeUtil.strObj(jsonObject.get("UseName"));   //��¼�˺�
        
		String leaf = jsonObject.optString("leaf", "-1");
		String muneId = jsonObject.optString("muneId", "-1");
		resultCode = "000";
		String jsonmune = null;
		try {
			if(leaf.equals("0")){    // ��������ӽڵ㣬�������˵�
				//jsonmune = getServiceDAO().getSubMuneData(muneId,jobId,defaultJobId);
				jsonmune= JsonUtil.getJsonByList(getServiceDAO().getSubMuneData(muneId,jobId,defaultJobId, osMapping.get(os)));
			}else if(leaf.equals("-1")){
				jsonmune=this.getInitMuneData(jobId,defaultJobId, osMapping.get(os));
			}else{
				jsonmune = getServiceDAO().getMuneData(jobId,defaultJobId);
			}
		} catch (Exception e) {
			resultCode = "001";         //��������ѯ�쳣
			e.printStackTrace();
		}

		String resultValue = "{\"result\": \""+resultCode+"\", \"body\": {\"muneList\": "+jsonmune+"}}";
		//System.out.println("ԭ�ַ���="+jsondata);   
	    //System.out.println("ԭ��="+jsondata.length());   
		String newstr = ZipUtil.compress(resultValue);   
	    //System.out.println("ѹ������ַ���="+newstr);   
	    //System.out.println("ѹ����ĳ�="+newstr.length());  
		//ResponseObject resObj = new ResponseObject();
		//resObj.setResponse(newstr);
		
		//return resObj;
		paramMap.put("response", newstr);

	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	public String getInitMuneData(String jobId,String defaultJobId, Integer osType){
		String jsonmune = null;
		try {
			List parentList=getServiceDAO().getSubMuneData("0",jobId,defaultJobId, osType);
			String praentIds="";
			for(int i=0;i<parentList.size();i++){
				HashMap parentMap=(HashMap)parentList.get(i);
				if(i==0){
					praentIds=parentMap.get("muneid").toString();
				}else{
					praentIds=praentIds+","+parentMap.get("muneid").toString();
				}
			}
			List subMuneList=getServiceDAO().getSubMuneData(praentIds,jobId,defaultJobId, osType);
			jsonmune= JsonUtil.getJsonByList(subMuneList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonmune;
	}
}