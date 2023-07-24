package com.ztesoft.mobile.service.handler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.system.dao.StaffSelDAO;
import com.ztesoft.mobile.system.dao.StaffSelDAOImpl;

public class ModPersonalInformationHandler extends AbstractHandler {

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		System.out.println("======[info]=====(ModPersonalInformationHandler)=jsonPara: "+jsonPara);
		
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		
		String QueryMethod = jsonObject.getString("QueryMethod");
		Map map = new HashMap();
		map.put("username", jsonObject.getString("username"));
		map.put("address2", jsonObject.getString("imsi"));
//		map.put("staffId", jsonObject.getString("staffId"));
//		map.put("jobId", jsonObject.getString("jobId"));
		
		Staff staff = null;
		int resultCode = -1;//默认是-1,成功是0,没有此用户名是1,更新失败是2,此imsi码已被其他用户占用是3
		if("query".equals(QueryMethod)){	
			resultCode = 1;						
			
			staff = getStaffSelDAO().selStaff(map);
			if(staff != null){
				resultCode = 0;
				String password = staff.getPassword();		
				if(password.indexOf("{SHA}")>-1){
					byte[] data = Base64It.decode(staff.getPassword().substring(5).getBytes());
					staff.setPassword(new String(data));
				}
			}
		} else if ("updata".equals(QueryMethod)){	
			if(getStaffSelDAO().selImsi(map) > 0){
				resultCode = 3;
			}else{
				map.put("staffName", jsonObject.getString("staffName"));
				map.put("mobileTel", jsonObject.getString("mobileTel"));
				map.put("email", jsonObject.getString("email"));
				map.put("address1", jsonObject.getString("address1"));
	//			map.put("address2", jsonObject.getString("imsi"));
				map.put("agent", jsonObject.getString("isAutoLogin"));
				
				resultCode = getStaffSelDAO().updataStaff(map)>0 ? 0 : 2;
			}
			
		}
		
		String newstr = "";
		if(resultCode == 0){
			if("query".equals(QueryMethod)){
				JSONObject jo = new JSONObject();
				jo.put("username", staff.getUserName());
				jo.put("staffName", staff.getStaffName());
				jo.put("mobileTel", staff.getMobileTel()==null? "" : staff.getMobileTel());
				jo.put("email", staff.getEmail()==null? "" : staff.getEmail());
				jo.put("address1", staff.getAddress1()==null? "" : staff.getAddress1());
				jo.put("imsi", staff.getAddress2()==null? "" : staff.getAddress2());
				jo.put("isAutoLogin", staff.getAgent());
				jo.put("password", staff.getPassword()==null? "" : staff.getPassword());
				jo.put("result", "000");
				
				newstr = jo.toString();
			} else if ("updata".equals(QueryMethod)){
				newstr = "{\"result\": \"000\"}";
			}
		}else {
			newstr = "{\"result\": \""+resultCode+"\"}";
		}
		System.out.println("======[info]=====返回的数据: "+newstr);
		newstr = ZipUtil.compress(newstr);
		paramMap.put("response", newstr);
	}
	
	private StaffSelDAO getStaffSelDAO(){
		String daoName = StaffSelDAOImpl.class.getName();
		return (StaffSelDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
