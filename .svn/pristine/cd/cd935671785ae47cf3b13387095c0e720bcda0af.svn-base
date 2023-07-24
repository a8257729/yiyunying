package com.ztesoft.mobile.v2.inf;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.encoding.ser.BaseFactory;
import org.dom4j.DocumentException;

import com.zterc.uos.UOSException;
import com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb;
import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.oaas.staff.dao.StaffPermissionsDAO;
import com.ztesoft.eoms.oaas.staff.dao.StaffSelDAO;
import com.ztesoft.eoms.oaas.staff.impl.*;
import com.ztesoft.mobile.v2.inf.MsgParser;

public class StaffSyncInterface {

	public String qryStaffInfo(String msg) throws DocumentException {
		Map rootMap = MsgParser.Xml2Map(msg);
		String str ="";
		return str;
	}
	
	public String addStaff(String msg) throws DocumentException, UOSException, SQLException{
		String str ="";
		Map rootMap = MsgParser.Xml2Map(msg);
		Map returnMap=new HashMap();
		Map root=new HashMap();
		
		Staff staff = new Staff();

		Map<String, Object> schMap = new HashMap<String, Object>();
		String username = cvtStr(rootMap.get("USERNAME"));
		schMap.put("userName", username);
		schMap.put("jobId", cvtStr(rootMap.get("JOB_ID")));
		schMap.put("orgId", cvtStr(rootMap.get("ORGID")));
		
		Map user = getStaffSelDAO().selByUserName(schMap);

		if (user != null && user.get("staffId") != null && !user.get("staffId").equals("")){
			int staffId = Integer.parseInt(cvtStr(user.get("staffId")));
			if (staffId >0){
				root.put("RESULT", "1");
				root.put("RESULTINFO", "用户名已经存在");
				returnMap.put("root", root);
				str =MsgParser.map2Xml(returnMap);			    
				return str;
			}
		}
		Map orgmap = getStaffSelDAO().selByOrgId(schMap);
		if (orgmap == null) {
			throw new SQLException("找不到组织="+cvtStr(rootMap.get("ORGID")));
		}
		int icount = Integer.parseInt(cvtStr(orgmap.get("orgcount")));
		if (icount ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "组织不存在");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);				    
			return str;
		}
		 
		Map jobmap = getStaffSelDAO().selByJobId(schMap);
		if (jobmap == null) {
			throw new SQLException("找不到职位"+cvtStr(rootMap.get("JOB_ID")));
		}
		int ijob = Integer.parseInt(cvtStr(jobmap.get("icount")));
		if (ijob ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "职位不存在");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);		    
			return str;
		}
		
		staff.setValidCommMode("1");
		staff.setEffectDate(new Date());
		staff.setLogonNumber(3);
		staff.setNationId(1L);
		staff.setUserName(cvtStr(MsgParser.findStringInMap(rootMap,"USERNAME")));
		staff.setPassword(cvtStr(rootMap.get("PASSWORD")));
		staff.setStaffName(cvtStr(rootMap.get("STAFF_NAME")));
		staff.setMobileTel(cvtStr(rootMap.get("MOBILE_TEL")));
		staff.setHomeTel(cvtStr(rootMap.get("HOME_TEL")));
		staff.setAddress1(cvtStr(rootMap.get("ADDRESS1")));
		staff.setAddress2(cvtStr(rootMap.get("ADDRESS2")));
		staff.setEmail(cvtStr(rootMap.get("EMAIL")));
		staff.setOfficeTel(cvtStr(rootMap.get("OFFICE_TEL")));
		staff.setComments(cvtStr(rootMap.get("COMMENTS")));

		Object orgIdStr = cvtStr(rootMap.get("ORGID"));
		int orgId = orgIdStr == null ? 0 : Integer.parseInt(cvtStr(rootMap.get("ORGID")));
		staff.setOrgId(orgId);
		
		StaffManagerWeb staffService = new StaffManagerWeb();
		// TODO 是否默认职位
		String isBasic = "1";
		// 系统管理员

		int jobId =Integer.parseInt(cvtStr(MsgParser.findStringInMap(rootMap,"JOB_ID")));

		// 登陸用戶信息，系統自創，暫時為空
		String logonStaff = cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFNAME"));
		Long logonStaffId = Long.parseLong(cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFID")));
		// staff = staffService.findByKey(38381);
		// arg0 ->Staff 对象，人员的VO对象
		// arg1 -> orgId 登录人员的组织ID
		// arg2 -> jobId 登录人员的职位Id
		// arg3 -> isBasic 是否默认职位
		// arg4 -> staffId 登录人员的id
		// arg5 -> staffName 登录人员姓名
		staffService.createWithLog(staff, orgId, jobId, isBasic, logonStaffId,
				logonStaff);
		
		Map userMap = new HashMap();
		userMap.put("userName", staff.getUserName());
	//	System.out.println("CHENLIN:------->11111111111");
		getStaffPermissionsDAO().setPermissions(userMap);
	//	System.out.println("CHENLIN:------->22222222222");
		root.put("RESULT", "0");
		root.put("RESULTINFO", "新增成功");
		returnMap.put("root", root);
		str =MsgParser.map2Xml(returnMap);	
		
		return str;
	}
	
	public String ModStaff(String msg) throws DocumentException, SQLException, UOSException{
		String str ="";
		Map rootMap = MsgParser.Xml2Map(msg);
		Map returnMap=new HashMap();
		Map root=new HashMap();
		
		String username = cvtStr(rootMap.get("USERNAME"));
		Map<String, Object> schMap = new HashMap<String, Object>();

		schMap.put("userName", username);
		Map user = getStaffSelDAO().selByUserName(schMap);

		if (user == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "用户名不存在");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);		    
		   
			return str;
		}
		int staffId =0 ;
		schMap.remove("userName");
		if (user != null && user.get("staffId") != null && !user.get("staffId").equals("")){
		    staffId = Integer.parseInt(cvtStr(user.get("staffId")));
		    if (staffId == 0){
		    	root.put("RESULT", "1");
				root.put("RESULTINFO", "用户不存在");
				returnMap.put("root", root);
				str =MsgParser.map2Xml(returnMap);		    
			   
				return str;
		    }
		}
		Map test = new HashMap();
		test.put("jobId", cvtStr(rootMap.get("JOB_ID")));
		test.put("orgId", cvtStr(rootMap.get("ORGID")));
		Map orgmap = getStaffSelDAO().selByOrgId(test);
		if (orgmap == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "找不到组织");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		   
			return str;
		}
		int icount = Integer.parseInt(cvtStr(orgmap.get("orgcount")));
		if (icount ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "组织不存在");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		   
			return str;
		}
		
		Map jobmap = getStaffSelDAO().selByJobId(test);
		if (jobmap == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "找不到职位");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}
		int ijob = Integer.parseInt(cvtStr(jobmap.get("icount")));
		if (ijob ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "职位不存在");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}

		StaffManagerWeb staffService = new StaffManagerWeb();
		Staff staff = staffService.findByKey(staffId);
		if (staff == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "找不到人员");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}

		staff.setPassword(cvtStr(rootMap.get("PASSWORD")));
		staff.setStaffName(cvtStr(rootMap.get("STAFF_NAME")));
		staff.setMobileTel(cvtStr(rootMap.get("MOBILE_TEL")));
		staff.setHomeTel(cvtStr(rootMap.get("HOME_TEL")));
		staff.setAddress1(cvtStr(rootMap.get("ADDRESS1")));
		staff.setAddress2(cvtStr(rootMap.get("ADDRESS2")));
		staff.setEmail(cvtStr(rootMap.get("EMAIL")));
		staff.setOfficeTel(cvtStr(rootMap.get("OFFICE_TEL")));
		staff.setComments(cvtStr(rootMap.get("COMMENTS")));
		staff.setIsModifyPwd(cvtStr(rootMap.get("ISMODIFYPWD")));
		Object orgIdStr = cvtStr(rootMap.get("ORGID"));
		int orgId = orgIdStr == null ? 0 : Integer.parseInt(cvtStr(rootMap.get("ORGID")));
		staff.setOrgId(orgId);
    
		Object jobIdStr = cvtStr(rootMap.get("JOB_ID"));
		int jobId = jobIdStr == null ? 0: Integer.parseInt(cvtStr(rootMap.get("JOB_ID")));
		
		System.out.println("staff------------------="+staff.getStaffName()+staff.getAddress1());
		String isBasic = staff.getIsBasic();

		String logonStaff = cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFNAME"));
		Long logonStaffId = Long.parseLong(cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFID")));

		// arg1 ->Staff 对象，人员的VO对象
		// arg2 -> jobId 登录人员的职位Id
		// arg3 -> isBasic 是否默认职位
		// arg4 -> staffId 登录人员的id
		// arg5 -> staffName 登录人员姓名

		staffService.updateWithLog(staff, jobId, isBasic, logonStaffId,
				logonStaff);
		root.put("RESULT", "0");
		root.put("RESULTINFO", "修改成功");
		returnMap.put("root", root);
		str =MsgParser.map2Xml(returnMap);	
		
		return str;
	}
	
	public String DelStaff(String msg) throws DocumentException, SQLException, UOSException {
		Map rootMap = MsgParser.Xml2Map(msg);
		Map returnMap=new HashMap();
		Map root=new HashMap();
		String username = cvtStr(rootMap.get("USERNAME"));
		String str ="";
		Map<String, Object> schMap = new HashMap<String, Object>();

		schMap.put("userName", username);

		Map user = getStaffSelDAO().selByUserName(schMap);

		if (user == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "找不到用户");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}
		int staffId = Integer.parseInt(cvtStr(user.get("staffId")));
        if (staffId >0){
			StaffManagerWeb staffService = new StaffManagerWeb();
	
			// 登陸用戶信息，系統自創，暫時為空
			String logonStaff = cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFNAME"));
			Long logonStaffId = Long.parseLong(cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFID")));
			staffService.deleteWithLog(staffId, logonStaffId, logonStaff);
        }else {
        	root.put("RESULT", "1");
    		root.put("RESULTINFO", "找不到用户名");
    		returnMap.put("root", root);
    		str =MsgParser.map2Xml(returnMap);	        	
			return str;
        }
        root.put("RESULT", "0");
		root.put("RESULTINFO", "删除成功");
		returnMap.put("root", root);
		str =MsgParser.map2Xml(returnMap);	
        
		return str;
	}
	private String cvtStr(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}
		
	private StaffSelDAO getStaffSelDAO() {
        String daoName = StaffSelDAOImpl.class.getName();
        return (StaffSelDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private StaffPermissionsDAO getStaffPermissionsDAO(){
		String daoName = StaffPermissionsDAOImpl.class.getName();		
		return (StaffPermissionsDAO) BaseDAOFactory.getImplDAO(daoName);
		
	}
	
}
