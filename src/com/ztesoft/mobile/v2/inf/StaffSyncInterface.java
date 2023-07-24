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
				root.put("RESULTINFO", "�û����Ѿ�����");
				returnMap.put("root", root);
				str =MsgParser.map2Xml(returnMap);			    
				return str;
			}
		}
		Map orgmap = getStaffSelDAO().selByOrgId(schMap);
		if (orgmap == null) {
			throw new SQLException("�Ҳ�����֯="+cvtStr(rootMap.get("ORGID")));
		}
		int icount = Integer.parseInt(cvtStr(orgmap.get("orgcount")));
		if (icount ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "��֯������");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);				    
			return str;
		}
		 
		Map jobmap = getStaffSelDAO().selByJobId(schMap);
		if (jobmap == null) {
			throw new SQLException("�Ҳ���ְλ"+cvtStr(rootMap.get("JOB_ID")));
		}
		int ijob = Integer.parseInt(cvtStr(jobmap.get("icount")));
		if (ijob ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "ְλ������");
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
		// TODO �Ƿ�Ĭ��ְλ
		String isBasic = "1";
		// ϵͳ����Ա

		int jobId =Integer.parseInt(cvtStr(MsgParser.findStringInMap(rootMap,"JOB_ID")));

		// ����Ñ���Ϣ��ϵ�y�Ԅ������r���
		String logonStaff = cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFNAME"));
		Long logonStaffId = Long.parseLong(cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFID")));
		// staff = staffService.findByKey(38381);
		// arg0 ->Staff ������Ա��VO����
		// arg1 -> orgId ��¼��Ա����֯ID
		// arg2 -> jobId ��¼��Ա��ְλId
		// arg3 -> isBasic �Ƿ�Ĭ��ְλ
		// arg4 -> staffId ��¼��Ա��id
		// arg5 -> staffName ��¼��Ա����
		staffService.createWithLog(staff, orgId, jobId, isBasic, logonStaffId,
				logonStaff);
		
		Map userMap = new HashMap();
		userMap.put("userName", staff.getUserName());
	//	System.out.println("CHENLIN:------->11111111111");
		getStaffPermissionsDAO().setPermissions(userMap);
	//	System.out.println("CHENLIN:------->22222222222");
		root.put("RESULT", "0");
		root.put("RESULTINFO", "�����ɹ�");
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
			root.put("RESULTINFO", "�û���������");
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
				root.put("RESULTINFO", "�û�������");
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
			root.put("RESULTINFO", "�Ҳ�����֯");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		   
			return str;
		}
		int icount = Integer.parseInt(cvtStr(orgmap.get("orgcount")));
		if (icount ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "��֯������");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		   
			return str;
		}
		
		Map jobmap = getStaffSelDAO().selByJobId(test);
		if (jobmap == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "�Ҳ���ְλ");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}
		int ijob = Integer.parseInt(cvtStr(jobmap.get("icount")));
		if (ijob ==0){
			root.put("RESULT", "1");
			root.put("RESULTINFO", "ְλ������");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}

		StaffManagerWeb staffService = new StaffManagerWeb();
		Staff staff = staffService.findByKey(staffId);
		if (staff == null) {
			root.put("RESULT", "1");
			root.put("RESULTINFO", "�Ҳ�����Ա");
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

		// arg1 ->Staff ������Ա��VO����
		// arg2 -> jobId ��¼��Ա��ְλId
		// arg3 -> isBasic �Ƿ�Ĭ��ְλ
		// arg4 -> staffId ��¼��Ա��id
		// arg5 -> staffName ��¼��Ա����

		staffService.updateWithLog(staff, jobId, isBasic, logonStaffId,
				logonStaff);
		root.put("RESULT", "0");
		root.put("RESULTINFO", "�޸ĳɹ�");
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
			root.put("RESULTINFO", "�Ҳ����û�");
			returnMap.put("root", root);
			str =MsgParser.map2Xml(returnMap);	
		    return str;
		}
		int staffId = Integer.parseInt(cvtStr(user.get("staffId")));
        if (staffId >0){
			StaffManagerWeb staffService = new StaffManagerWeb();
	
			// ����Ñ���Ϣ��ϵ�y�Ԅ������r���
			String logonStaff = cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFNAME"));
			Long logonStaffId = Long.parseLong(cvtStr(MsgParser.findStringInMap(rootMap,"LOGONSTAFFID")));
			staffService.deleteWithLog(staffId, logonStaffId, logonStaff);
        }else {
        	root.put("RESULT", "1");
    		root.put("RESULTINFO", "�Ҳ����û���");
    		returnMap.put("root", root);
    		str =MsgParser.map2Xml(returnMap);	        	
			return str;
        }
        root.put("RESULT", "0");
		root.put("RESULTINFO", "ɾ���ɹ�");
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
