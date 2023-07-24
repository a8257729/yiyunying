package com.ztesoft.eoms.im.action;

import java.util.HashMap;
import java.util.Map;

import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.helper.StringHelper;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

/**
 * 判断门户小助手中的设定的IM默认联系人是否在线,如果在线则isOnLine=Y, 不在线则isOnLine=N
 * @author Xsh
 *
 */
public class ImServerIsOnLineAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		Map retMap = new HashMap();
		try{
			//获取后台设置的默认联系人
			String serStaffId = com.ztesoft.eoms.common.helper.UosConfigHelper.getValueByKey("HELP_IM_SERVER");
			System.out.println("ImServerIsOnLineAction serStaffId=====> " + serStaffId);
			if(!StringHelper.isNull(serStaffId)){
				Staff staff = getOnLineSerStaff(serStaffId);
				System.out.println("ImServerIsOnLineAction serStaff=====> " + staff);
				if(staff != null){
					System.out.println("ImServerIsOnLineAction isOnLine=====> Y" );
					retMap.put("isOnLine","Y");
					retMap.put("serStaffId", staff.getStaffId());
					retMap.put("serStaffName", staff.getStaffName());
				}else{
					System.out.println("ImServerIsOnLineAction isOnLine=====> N" );
					retMap.put("isOnLine","N");
				}				
			}else{
				retMap.put("error","未配置IM默认联系人,请联系管理员配置!");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		DedicatedActionContext.setResult(retMap);		
		
		return null;
	}
	
	private Staff getOnLineSerStaff(String serStaffId){
		Session [] sessions = SessionManager.getInstance().getSessions();
		System.out.println("getOnLineSerStaff sessions=====> " +  sessions.length);
		
		for(int i =0;i<sessions.length;i++){
			Staff staff = (Staff)sessions[i].getHttpSession().getAttribute("staff");
			
			System.out.println("getOnLineSerStaff serStaffId=====> " +  staff.getStaffId());
			System.out.println("getOnLineSerStaff serStaffName=====> " + staff.getStaffName());
			
			if(serStaffId.equals(StringHelper.toString(staff.getStaffId()))){
				return staff;
			}
		}
		
		
		return null;
	}

}
