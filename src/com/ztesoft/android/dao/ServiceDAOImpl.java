package com.ztesoft.android.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil; 


public class ServiceDAOImpl extends BaseDAOImpl implements ServiceDAO
{

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws DataAccessException
	 */
	public String getVersionNo() throws DataAccessException
	{

		String sql = "SELECT e.VERSION_NO as engine_version,e.VERSION_URL as url,e.FORCE_UPDATE as force_update,e.COMMENTS as comments,e.PIC_VERSION_NO as pic_version,e.PIC_VERSION_URL as pic_version_url " +
				     "FROM MOBILE_PHONE_VERSION e where e.public_date in(select Max(d.public_date) from MOBILE_PHONE_VERSION d) ";

		Map m = dynamicQueryObjectByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonDataByMap(m);	
		System.out.println("jsondata "+resultjson);
		return resultjson;
	}
	/**
	 * 根据用户的手机号码获取用户的密码
	 * 
	 * @param userId
	 *            用户的手机号??
	 * @return
	 * @throws DataAccessException 
	 */
	public Map getPasswordByUserName(String userName) throws DataAccessException
	{
		
		String sql = "SELECT e.password as password,e.staff_id as staffId,e.agent as agent FROM UOS_STAFF e WHERE  e.state='1' and e.username = '"+userName+"'";
		Map map = dynamicQueryObjectByMap(sql, null, null);
	
		return map;
	}
	public Map getPasswordByPhoneNO(String userName) throws DataAccessException
	{
		
		String sql = "SELECT e.password as password,e.staff_id as staffId,e.agent as agent FROM UOS_STAFF e WHERE e.ADDRESS2='"+userName+"'";
		Map map = dynamicQueryObjectByMap(sql, null, null);
		System.out.println("map----->>> "+map);
		return map;
	}
	//只查有apk集成的人员映射信息



	public String getMappingSession(String staffId) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select smb.staff_id as mapStaffId,smb.username as mapUserName ");
		sql.append(" ,smb.role_id as mapRoleId,smb.org_id as mapOrgId,smb.sys_code as sysCode ");
		sql.append(" from OSSMH_MAPPING om,STAFF_MAP_BASE smb where om.STAFF_MAP_BASE_ID = smb.STAFF_MAP_BASE_ID ");
		sql.append(" and om.staff_id = "+staffId );
		System.out.println("人员映射信息" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		return resultjson;
	}
	//取得人员映射信息
	public String getStaffMappingInfo(String staffId,String sysCode) throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select smb.staff_id as StaffId,smb.username as UseName ");
		sql.append(" ,smb.role_id as JobId,smb.org_id as OrgId ");
		sql.append(" from OSSMH_MAPPING om,STAFF_MAP_BASE smb where om.STAFF_MAP_BASE_ID = smb.STAFF_MAP_BASE_ID ");
		sql.append(" and smb.sys_code ='"+sysCode+"' and om.staff_id = "+staffId );
		System.out.println("人员映射信息" + sql);
		String resultjson = JsonUtil.getJsonDataByMap(dynamicQueryObjectByMap(sql.toString(), null, null));
		return resultjson;
	}
	//业务系统apk信息查询
	public String getOtherApkInfo() throws DataAccessException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select mmsm.systemCode as sysCode,mmsm.methodAddress as mAdress,mmsm.forceUpdate as forceUpdate, ");
		sql.append(" mmsm.apkVersionNo as version,mmsm.apkPackage as aPackage,mmsm.apkUrl as url,mmsm.comments as comments ");
		sql.append(" from VM_MOBILE_MAPPING_SYS_MAN mmsm where 1=1 ");
		System.out.println("业务系统apk信息查询" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		return resultjson;
	}
	//业务APK系统所有映射方法



	public String getOtherApkMethodInfo() throws DataAccessException{
		StringBuffer sql = new StringBuffer();
//		sql.append(" select mim.mapping_code as mappingCode,mim.inteface_method as intefaceMethod from MOBILE_INTEFACE_MANAGER mim ");
//		sql.append(" join MOBILE_OTHERE_SYS_MANAGER mosm on mim.sys_address_id = mosm.sys_address_id and mosm.method_type = 1");
		sql.append("  select maf.fun_code as mappingCode,maf.fun_class as intefaceMethod,oam.apk_package as apkPkg from MOBILE_APK_FUNCTION maf, " +
		"OTHER_APK_MANAGE oam , vm_mobile_mune mm where maf.apk_code = oam.apk_code and maf.fun_code = mm.getmethod");
		System.out.println("业务APK系统所有映射方法" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		return resultjson;
	}
	
	public String getJobData(String staffId)throws DataAccessException{
		/*		StringBuffer sql = new StringBuffer();
		sql.append(" select b.job_id as jobId,b.isbasic as isbasic,b.staff_id as staffId,a.JOB_NAME as jobName,a.ORG_ID as orgId ");
		sql.append(" from UOS_JOB_STAFF b,vm_staff_org a where b.staff_id = a.staff_id and b.state = 1 and a.staff_id = "+staffId );

		System.out.println("查询部门职位信息" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		System.out.println("查询部门职位信息" + sql); */
		
		StringBuffer sql = new StringBuffer();
		sql.append("select d.staff_id as staffId, ");
		sql.append("d.staff_name as staffName,");
		sql.append("d.username as username,");
		sql.append("b.job_id as jobId, ");
		sql.append("c.org_id as orgId, ");
		sql.append("c.area_id as areaId, ");
		sql.append("a.is_normal as isbasic, ");
		sql.append("D.MOBILE_TEL as mobileTel, ");
		sql.append("b.job_name as jobName ");
		sql.append("from UOS_JOB_STAFF a join UOS_JOB b  on a.JOB_ID = b.job_id ");
		sql.append("join UOS_ORG c on b.org_id = c.org_id join UOS_STAFF d on a.staff_id = d.staff_id ");
		sql.append("where a.state = 1 and b.state = 1 and c.state = 1 and d.state = 1 and a.staff_id = "+staffId );
		//return dynamicQueryListByMap(sql.toString(),null, null, dataBaseName);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		System.out.println("查询部门职位信息" + sql);
		return resultjson;
	} 
	
	/**
	 * 
	 * 查询当前选择职位，和特有职位的菜单权限信息



	 * @throws SQLException 
	 */
	public String getMuneData(String jobId,String defaultJobId)throws SQLException{
//		Connection con = null;
//		PreparedStatement ps = null;
//		ResultSet result = null;
		StringBuffer sql = new StringBuffer();
		/*sql.append("select distinct mm.muneid as muneid,mm.title as title,mm.type as type, mm.getmethod AS getmethod,");
		sql.append(" mm.leaf AS leaf,mm.icon as icon,mm.topage as topage,mm.syscode as syscode, MEM.DISPLAY_INEDX  as muneType,mm.displayindex as displayindex ");//修改成显示顺序

		sql.append(" from VM_MOBILE_MUNE mm,MOBILE_ENUM_MANGER MEM,(select a.parentId, a.pathCode,a.jobId FROM VM_MOBILE_MUNE_PRIV a ");
		sql.append(" where (a.jobId = "+jobId+" or a.jobId = "+defaultJobId+")) vv  ");
		sql.append(" where mm.state = '10A' AND mm.parentid = 0 AND MEM.ENUM_ID=MM.muneType AND (mm.pathcode like '' || vv.pathCode || '%' or mm.muneid = vv.parentId) ");
		sql.append(" ORDER BY mm.displayindex asc ");*/
		sql.append("SELECT distinct mm.muneid as muneid,mm.title as title,mm.type as type,mm.getmethod AS getmethod,")
		.append(" mm.leaf AS leaf,mm.icon as icon,mm.topage as topage,mm.syscode as syscode,MEM.DISPLAY_INEDX as muneType,")
		.append(" mm.displayindex as displayindex,mm.keyName as keyname ")
		.append("  FROM VM_MOBILE_MUNE mm, MOBILE_ENUM_MANGER MEM, VM_MOBILE_MUNE_PRIV B")
		.append(" WHERE (b.jobId = "+jobId+" or b.jobId = "+defaultJobId+")")
		.append("   AND b.parentId = mm.parentid")
		.append("   AND MEM.ENUM_ID = MM.muneType")
		.append("   and mm.parentid IN (SELECT distinct VMM.muneid")
		.append("         FROM VM_MOBILE_MUNE VMM, VM_MOBILE_MUNE_PRIV VMMP")
		.append("        WHERE VMM.parentid = 0")
		.append("          AND (VMMP.jobId = "+jobId+" or VMMP.jobId = "+defaultJobId+")")
		.append("          AND VMMP.parentId = VMM.parentid) ")
		.append(" ORDER BY mm.displayindex asc "); 
        System.out.println("查询父菜单信息" + sql);
        String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(),null,null));
		return resultjson;
	} 
	/**
	 * 跟根一级菜单ID，找二级菜单,只支持最多二级菜单


	 * @param parentId
	 * @param jobId
	 * @param defaultJobId
	 * @return
	 * @throws SQLException 
	 */
	public List getSubMuneData(String parentId,String jobId,String defaultJobId, Integer osType) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		StringBuffer sql = new StringBuffer();
		
		/*
		sql.append(" select distinct mm.mune_id as muneid,mm.MUNE_NAME as title,mm.isbg as isbg,mm.DISPLAY_TYPE as type,mm.get_method AS getmethod, ");
		sql.append(" mm.is_leaf AS leaf,mm.icon_adr as icon,mm.to_page as topage ,mm.OTHER_SYS_CODE as syscode,");
		sql.append(" MMP.display_inedx AS muneType,mm.display_inedx as displayindex ");	
		sql.append(" from  MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MUNE mm left join MOBILE_MUNE MMP on mm.parent_id = MMP.MUNE_ID  ");
		sql.append(" WHERE mm.state = '10A' and (mm.priv_code = msjr.priv_code or msjr.priv_code = '-1') ");
		sql.append(" and mm.parent_id in("+parentId+") and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+") ");
		sql.append(" ORDER BY mm.display_inedx asc ");
		*/
		
		sql.append("SELECT distinct mm.muneid as muneid,mm.title as title,mm.type as type,mm.getmethod AS getmethod,");
		sql.append(" mm.leaf AS leaf,mm.icon as icon,mm.topage as topage,mm.syscode as syscode,MMP.display_inedx AS muneType,");
		sql.append(" mm.displayindex as displayindex,mm.keyName as keyname ");
		sql.append(" from  MOBILE_STAFF_JOB_RIGHT msjr,VM_MOBILE_MUNE mm left join MOBILE_MUNE MMP on mm.parentid = MMP.MUNE_ID  ");
		sql.append(" WHERE mm.state = '10A' and (mm.privcode = msjr.priv_code or msjr.priv_code = '-1') ");
		sql.append(" and mm.parentid in("+parentId+") and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+") ");
        //根据客户端系统去取菜单
        if(null != osType) {
            sql.append(" AND MM.OSTYPE = " + osType);
        }
		sql.append(" ORDER BY mm.displayindex asc ");
		
        System.out.println("334查询二级菜单信息" + sql);
   
        //String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(),null,null));
		return dynamicQueryListByMap(sql.toString(),null,null);
	} 
	public String getStrSubMuneData(String parentId,String jobId,String defaultJobId) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct mm.mune_id as muneid,mm.MUNE_NAME as title,mm.isbg as isbg,mm.DISPLAY_TYPE as type,mm.get_method AS getmethod, ");
		sql.append(" mm.is_leaf AS leaf,mm.icon_adr as icon,mm.to_page as topage ,mm.OTHER_SYS_CODE as syscode, MMP.display_inedx AS muneType,mm.display_inedx as displayindex ");	
		sql.append(" from  MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MUNE mm left join MOBILE_MUNE MMP on mm.parent_id = MMP.MUNE_ID  ");
		sql.append(" WHERE mm.state = '10A' and (mm.priv_code = msjr.priv_code or msjr.priv_code = '-1') ");
		sql.append(" and mm.parent_id in("+parentId+") and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+") ");
		sql.append(" ORDER BY mm.display_inedx asc ");
		
        System.out.println("查询二级菜单信息" + sql);
   
        String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(),null,null));
		return resultjson;//dynamicQueryListByMap(sql.toString(),null,null);
	} 
	public String getListFormData(String staffId, String dataBaseName)throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select keyId,name,label,title,sndtitle,datetime from MoblieListForm ");
		
         System.out.println("查询部门职位信息" + sql);
		
		JSONArray jr = new JSONArray();
	
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			result = ps.executeQuery();
			while (result.next())
			{
				JSONObject j = new JSONObject();
				
				j.put("keyId", result.getString("keyId"));
				j.put("name", result.getString("name"));
				j.put("label", result.getString("label"));
				j.put("title", result.getString("title"));
				j.put("sndtitle", result.getString("sndtitle"));
				j.put("datetime", result.getString("datetime"));
				jr.add(j);
			}
			cleanUp(con, null, ps, result);
		} catch (SQLException e)
		{
			cleanUp(con, null, ps, result);
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			cleanUp(con, null, ps, result);
		}

		String resultjson = jr.toString();
		
		//String newjson = "{\"jobList\":"+resultjson+"}";
		//String newjson = "{'result': \"000\", \"body\": {\"jobList\": [{\"jobPath\": \"省局长\", \"jobId\": \"82\"}]}}";
		System.out.println("resultjson----->>  "+resultjson);
		
		return resultjson;
	} 
	
	/**
	 * 根据请求环节查接口映射地址
	 * @param teachName
	 * @return
	 * @throws DataAccessException
	 */
	public Map getIntefaceInfoByTeach(String teachName)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" select mjc.form_id as formId,mjc.form_name as formName,mjc.teach_name as teachName,mjc.display_type as displayType,mjc.inteface_type as intefaceType,mjc.inteface_url as intefaceUrl,mm.inteface_url as intefaceUrl2, ");
		sql.append(" mjc.inteface_name as intefaceName,mjc.key_name as keyName,mjc.inteface_url as sysCode from MOBILE_JSON_CREATE mjc,MOBILE_MUNE mm where mjc.MUNE_ID = mm.MUNE_ID and mjc.teach_name = '"+teachName+"' ");
		System.out.println("查询接口信息" + sql);
		Map m = dynamicQueryObjectByMap(sql.toString(), null, null);
		
		System.out.println("map----->>>> "+m.toString());
		return m;

	} 
	public Map getIntefaceInfoByName(String teachName)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
//		sql.append(" select mjc.form_id as formId,mjc.form_name as formName,mjc.teach_name as teachName,mjc.display_type as displayType,mjc.inteface_type as intefaceType,mjc.inteface_url as intefaceUrl,mm.inteface_url as intefaceUrl2, ");
//		sql.append(" mjc.inteface_name as intefaceName,mjc.key_name as keyName from MOBILE_JSON_CREATE mjc,MOBILE_MUNE mm where mjc.MUNE_ID = mm.MUNE_ID and mjc.teach_name = '"+teachName+"' ");
		sql.append("select mjc.form_id as formId,mjc.form_name as formName,mjc.teach_name as teachName,mjc.display_type as displayType, ");
		sql.append("mjc.inteface_type as intefaceType,mjc.inteface_name as intefaceName,mjc.key_name as keyName,");
		sql.append("mosm.method_address as methodAddress,mosm.interface_namespace as interfaceNamespace,mosm.Data_Type as dataType,mosm.Interface_Method as interfaceMethod, ");
		sql.append("mim.inteface_method as intefaceMethodParam,mim.mapping_name as interfMethodType,mim.mapping_code as mappingCode,");
		sql.append("mim.mapping_method as interfParamsName,mim.inteface_params as intefaceParams,");
		sql.append("msfm.system_fileds as systemFileds,msfm.mapping_fileds as mappingFileds,msfm.system_fileds2 as systemFileds2,");
		sql.append("msfm.mapping_fileds2 as mappingFileds2,msfm.system_fileds3 as systemFileds3,msfm.mapping_fileds3 as mappingFileds3,");
		sql.append("msfm.system_fileds4 as systemFileds4,msfm.mapping_fileds4 as mappingFileds4,msfm.system_fileds5 as systemFileds5,");
		sql.append("msfm.mapping_fileds5 as mappingFileds5,msfm.system_fileds6 as systemFileds6,msfm.mapping_fileds6 as mappingFileds6, ");
		sql.append("msfm.system_fileds7 as systemFileds7,msfm.mapping_fileds7 as mappingFileds7,msfm.system_fileds8 as systemFileds8,msfm.mapping_fileds8 as mappingFileds8 ");
		sql.append("from MOBILE_SYS_FILED_MAPPING msfm right join MOBILE_JSON_CREATE mjc on mjc.inteface_name = msfm.mapping_code ,MOBILE_INTEFACE_MANAGER mim,MOBILE_OTHERE_SYS_MANAGER mosm ");
		sql.append("where mjc.inteface_name =mim.mapping_code and mim.sys_address_id = mosm.sys_address_id  ");
		sql.append("and mjc.teach_name = '"+teachName+"' ");
		System.out.println("查询接口信息" + sql);
		Map m = dynamicQueryObjectByMap(sql.toString(), null, null);
		
		System.out.println("map----->>>> "+m.toString());
		return m;

	} 
	/**
	 * 根据环节ID查需要显示的字段
	 * @param formId
	 * @return
	 * @throws DataAccessException
	 */
	public List getFiledByFormId(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct mfi.filed_name as filedName,mfi.filed_lable as filedLable,");
		sql.append(" mfi.filed_id as filedId,mfi.FILED_TYPE as filedType,mfi.checked_data as checkedData,");
		sql.append(" mfi.select_data as selectData, mfi.is_display as isDisplay,mfi.is_pass_value as isPassValue,");
		sql.append(" mfi.IS_REQUIRED AS isRequired,mfi.index_id as indexId,mfi.seq_id as seqId,mfi.node_name as nodeName, ");
		sql.append(" mfi.attr_code as attrCode,mfi.is_show_label as showLable,mfi.is_search_field as isSearchField,");
		sql.append(" mfi.position as position,mfi.default_value as defaultValue,mfi.action_code as actionCode,");
		sql.append(" mfi.action_event as actionEvent,mfi.data_form as dateForm ,mjc.display_type as displayType");
		sql.append(" from MOBILE_FIELD_INFO mfi left join MOBILE_JSON_CREATE mjc on mfi.checked_data= mjc.teach_name ");
		sql.append(" where mfi.form_id = "+formId+" order by mfi.seq_id asc ");
		System.out.println("根据环节ID查需要显示的字段:" + sql);
		return dynamicQueryListByMap(sql.toString(), null, null);

	} 
	
	/**
	 * 根据环节ID查需要显示的字段节点
	 * @param formId
	 * @return
	 * @throws DataAccessException
	 */
	public List getFiledNodeByFormId(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct mfn.node_name as nodeName,mfn.node_label as nodeLabel,mfn.is_leaf as isLeaf,mfn.SEQ_ID as seqId FROM MOBILE_JSON_CREATE mjc, MOBILE_FILED_NODE mfn where mjc.form_id = mfn.form_id ");
		sql.append(" and mjc.form_id = "+formId+" order by mfn.SEQ_ID asc ");
		System.out.println("根据环节ID查需要显示的字段节点:" + sql);
		return dynamicQueryListByMap(sql.toString(), null, null);

	} 
	public String getFiledNodeStrByFormId(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct mfn.node_name as nodeName,mfn.node_label as nodeLabel,mfn.is_leaf as isLeaf,mfn.SEQ_ID as seqId FROM MOBILE_JSON_CREATE mjc, MOBILE_FILED_NODE mfn where mjc.form_id = mfn.form_id ");
		sql.append(" and mjc.form_id = "+formId+" order by mfn.SEQ_ID asc ");
		
		System.out.println("根据环节ID查需要显示的字段节点:" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;
	} 
	/**
	 * 根据环节ID查可操作权限
	 * @param formId
	 * @return
	 * @throws DataAccessException
	 */
	
	public String getPriButByFormId(String formId,String jobId,String defaultJobId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct mbr.button_id as buttonId,mbr.button_name as buttonName,");
		sql.append(" mjc.teach_name as toPage,mjc.display_type as displayType,mjc.INTEFACE_NAME as intefaceName,mjc.inteface_url as systemCode,mbr.get_method as getMethod, ");
		sql.append(" mbr.IS_SHOW as isShow,mbr.IS_BOTTOM as isBottom,mbr.ORIENTATION as orientation,mbr.button_sequ as buttonSequ,mbr.BUTTON_TYPE as buttonType,mbr.ERROR_INFO as errorInfo ");
		sql.append(" from MOBILE_BUTTON_RIGHT mbr, MOBILE_JSON_CREATE mjc,MOBILE_STAFF_JOB_RIGHT msjr ");
		sql.append(" where mbr.next_form_id = mjc.form_id and (mbr.priv_code = msjr.priv_code or '-1' = msjr.priv_code) and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+") and mbr.form_id = "+formId);
		sql.append(" order by mbr.button_sequ asc ");
		System.out.println("根据环节ID查有权限显示的操作按钮:" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	} 
	
	//按钮不设置权限

	public String getPriButByFormId(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct mbr.button_id as buttonId,mbr.button_name as buttonName,");
		sql.append(" mjc.teach_name as toPage,mjc.display_type as displayType,mjc.INTEFACE_NAME as intefaceName,mjc.inteface_url as systemCode,mbr.get_method as getMethod, ");
		sql.append(" mbr.IS_SHOW as isShow,mbr.IS_BOTTOM as isBottom,mbr.ORIENTATION as orientation,mbr.button_sequ as buttonSequ,mbr.BUTTON_TYPE as buttonType,mbr.ERROR_INFO as errorInfo ");
		sql.append(" from MOBILE_BUTTON_RIGHT mbr, MOBILE_JSON_CREATE mjc ");
		sql.append(" where mbr.next_form_id = mjc.form_id and mbr.form_id = "+formId);
		sql.append(" order by mbr.button_sequ asc ");
		System.out.println("根据环节ID查有权限显示的操作按钮:" + sql);
		String resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	} 
	/**
	 * 统计信息查询
	 */
	
	public String getStatFiledByFormId(String formId)throws DataAccessException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select mfi.form_id as L3,mfi.filed_name as L0,mfi.filed_lable as L1,(select mst.operate_name from MOBILE_STAT_TRANSFER mst where mst.filed_id = mfi.filed_id and mst.operate_type =2) as L2 from MOBILE_FIELD_INFO mfi  ");
		sql.append(" where  mfi.form_id = "+formId+" order by mfi.index_id asc,mfi.seq_id asc ");
		System.out.println("根据环节ID查需要显示的字段:" + sql);
		
		JSONArray jr = new JSONArray();
		
		try
		{
			con = getConnection();
			ps = con.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			result = ps.executeQuery();
			while (result.next())
			{
				JSONObject j = new JSONObject();
				
				j.put("L0", result.getString("L0"));
				j.put("L1", result.getString("L1"));
				j.put("L2", result.getString("L2")==null?"-1":result.getString("L2"));
				j.put("L3", result.getString("L3"));
				jr.add(j);
			}
			cleanUp(con, null, ps, result);
		} catch (SQLException e)
		{
			cleanUp(con, null, ps, result);
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally
		{
			cleanUp(con, null, ps, result);
		}

		String resultjson = jr.toString();
		return resultjson;
	}  
	/**
	 * 查统计转取配置文件


	 */
	public String getStatTransferByFormId(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append(" select mst.operate_type as operateType,mst.operate_name as operateName, ");
		sql.append(" mjc.teach_name as teachName,mjc2.teach_name as nextTeachName,mfi.filed_name as fileName ");
		sql.append(" from MOBILE_STAT_TRANSFER mst , mobile_json_create mjc,mobile_json_create mjc2,mobile_field_info mfi ");
		sql.append(" where mst.form_id = mjc.form_id and mst.next_form_id = mjc2.form_id and mst.filed_id = mfi.filed_id ");
		sql.append(" and mst.form_id ="+formId);
		System.out.println("根据环节ID查有权限显示的操作按钮:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	} 
	
	/**
	 * 查询通用配置页面
	 */
	public String getCommonLayout(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append("select mcl.orientation as orientation,mcl.position as position,mcl.display_count as displayCount ");
		sql.append(" from MOBILE_COMMON_LAYOUT mcl ");
		//sql.append(" where mst.form_id ="+formId);
		System.out.println("根据环节ID查询通用配置页面:" + sql);
		Map returndata = dynamicQueryObjectByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonDataByMap(returndata);
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	} 
	
	/**
	 * 查询字段配置
	 */
	public String getSearchFiled(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append("select distinct msf.is_display as isDisplay,mfi.filed_name as filedName,mfi.filed_lable as filedLable,");
		sql.append(" mfi.filed_type as filedType,mfi.index_id as indexId,mfi.seq_id as seqId FROM MOBILE_SEARCH_FILED msf,MOBILE_FIELD_INFO mfi  ");
		sql.append(" where msf.form_id = mfi.form_id and msf.filed_id = mfi.filed_id order by mfi.seq_id asc ");
		//sql.append(" and msf.form_id ="+formId);
		System.out.println("根据环节ID查询字段配置:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		System.out.println("resultjson----->>  "+resultjson); 
		return resultjson;

	} 
	
	/**
	 * 查询TAB页配置


	 */ 
	public String getSearchTab(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		sql.append("select mst.tab_name as tabName,mst.seq_id as seqId,mst.position as position,");
		sql.append(" mjc.teach_name as teachName,mjc.display_type as displayType from MOBILE_SEARCH_TAB mst,MOBILE_JSON_CREATE mjc ");
		sql.append(" where mst.next_form_id = mjc.form_id and mst.is_show = 1 ");		
		sql.append(" and mst.form_id = "+formId+" order by mst.seq_id asc ");

		System.out.println("根据环节ID查询TAB页配置:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	}
	/**
	 * 查询TAB页配置


	 */ 
	public String getSearchTab2(String formId)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
	
		sql.append("select mst.seq_id as seqId,mst.position as position,mst.next_form_id as formId,mst.show_height as height,mst.is_bg as isbg, ");
		sql.append(" mjc.teach_name as teachName,mjc.display_type as displayType from MOBILE_SEARCH_TAB mst,MOBILE_JSON_CREATE mjc ");
		sql.append(" where mst.next_form_id = mjc.form_id and mst.is_show = 1 ");		
		sql.append(" and mst.form_id = "+formId+" order by mst.seq_id asc ");

		System.out.println("根据环节ID查询TAB页配置:" + sql);
		List<Map> returndata = dynamicQueryListByMap(sql.toString(), null, null);
//		int k=2;
//		for(Map m: returndata){			
//			List filedData = getFiledByFormId(m.get("formId").toString());
//			m.put("listdata"+k, filedData); 
//			k++;
//		}
		String resultjson = JsonUtil.getJsonByList(returndata);
		System.out.println("resultjson----->>  "+resultjson);
		return resultjson;

	}
	public String getMuneTabs(String staffId, Integer osType) throws DataAccessException {
		StringBuffer sql = new StringBuffer();
		//sql.append(" SELECT ENUM_NAME AS enumName,ENUM_ID as enumId,DISPLAY_INEDX as displayIndex FROM MOBILE_ENUM_MANGER where ENUM_TYPE=1 and IS_SHOW = 1 order by DISPLAY_INEDX ");
		sql.append("select distinct mm.mune_name AS enumName,mm.mune_id as enumId,DISPLAY_INEDX as displayIndex,mm.icon_adr as icon " +
				" from mobile_staff_job_right b, uos_job_staff a,mobile_mune mm " + 
				" where a.job_id = b.job_id and mm.priv_code=b.priv_code ");
        sql.append(" and mm.parent_id=0 and a.staff_id ="+staffId);
        if(null != osType) {
              sql.append(" and mm.os_type = " + osType);
        }
        sql.append(	" order by DISPLAY_INEDX");
		System.out.println("查询muneTab页" + sql);
		String resultjson=null;
		List reslutLit=dynamicQueryListByMap(sql.toString(), null, null);
		/*Map topHome=new HashMap();
		topHome.put("enumName", "首页");
		topHome.put("displayIndex", "0");
		topHome.put("enumId", "0");
		topHome.put("icon", "top_home");
		reslutLit.add(topHome);*/
		if(reslutLit.size()>0){
			resultjson = JsonUtil.getJsonByList(reslutLit);
		}
		/*if(resultjson==null||"".equals(resultjson)){
			resultjson="[{\"displayIndex\":0,\"enumId\":0,\"icon\":\"top_home\",\"enumName\":\"首页\"}]";
		}*/
		System.out.println(resultjson);
		return resultjson;
	} 
	
	public Map getIntefaceInfoByMappingCode(String mappingCode)throws DataAccessException {

		StringBuffer sql = new StringBuffer();
		/*
		sql.append("select mim.mapping_code as mappingCode, msfm.system_fileds as systemFileds1,msfm.mapping_fileds as mappingFileds1,msfm.system_fileds2 as systemFileds2,");
		sql.append("msfm.mapping_fileds2 as mappingFileds2,msfm.system_fileds3 as systemFileds3,msfm.mapping_fileds3 as mappingFileds3,");
		sql.append("msfm.system_fileds4 as systemFileds4,msfm.mapping_fileds4 as mappingFileds4,msfm.system_fileds5 as systemFileds5,");
		sql.append("msfm.mapping_fileds5 as mappingFileds5 ");
		sql.append("from MOBILE_INTEFACE_MANAGER mim,MOBILE_OTHERE_SYS_MANAGER mosm,MOBILE_SYS_FILED_MAPPING msfm ");
		sql.append("where mim.sys_address_id = mosm.sys_address_id and mim.mapping_code = msfm.mapping_code ");
		sql.append("and mim.mapping_code = '"+mappingCode+"' ");
		*/
		sql.append("select "); 
		sql.append("mim.inteface_method as intefaceMethodParam,mim.mapping_name as interfMethodType,mim.mapping_code as mappingCode,");
		sql.append("mim.mapping_method as interfParamsName,mim.inteface_params as intefaceParams,mosm.Data_Type as dataType,mosm.Interface_Method as interfaceMethod,");
		sql.append("msfm.system_fileds as systemFileds,msfm.mapping_fileds as mappingFileds,msfm.system_fileds2 as systemFileds2,");
		sql.append("msfm.mapping_fileds2 as mappingFileds2,msfm.system_fileds3 as systemFileds3,msfm.mapping_fileds3 as mappingFileds3,");
		sql.append("msfm.system_fileds4 as systemFileds4,msfm.mapping_fileds4 as mappingFileds4,msfm.system_fileds5 as systemFileds5,");
		sql.append("msfm.mapping_fileds5 as mappingFileds5,msfm.system_fileds6 as systemFileds6,msfm.mapping_fileds6 as mappingFileds6, ");
		sql.append("msfm.system_fileds7 as systemFileds7,msfm.mapping_fileds7 as mappingFileds7,msfm.system_fileds8 as systemFileds8,msfm.mapping_fileds8 as mappingFileds8 ");
		sql.append("from MOBILE_SYS_FILED_MAPPING msfm right join MOBILE_INTEFACE_MANAGER mim on msfm.mapping_code =mim.mapping_code,MOBILE_OTHERE_SYS_MANAGER mosm  ");
		sql.append("where mim.sys_address_id = mosm.sys_address_id    ");
		sql.append("and mim.mapping_code = '"+mappingCode+"' ");
		System.out.println("查询接口信息" + sql);
		Map m = dynamicQueryObjectByMap(sql.toString(), null, null);
		
		//System.out.println("map----->>>> "+m.toString());
		return m;
	} 
	
	public String getReasonType(String obstructProdType)throws DataAccessException{

		StringBuffer sql = new StringBuffer();
		//	sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
		//	"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=0 AND PNAME='"+obstructProdType+"' order by codeA ");	
			sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
					"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=0 order by codeA ");	
		
		System.out.println("查故障原因大类:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
	public String getReason(String code)throws DataAccessException{

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
				"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=1 AND PNAME='"+code+"' order by codeA ");	

		System.out.println("查故障原因:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
	public String getReasonReaulstType(String obstructProdType)throws DataAccessException{

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT MRC.ID as id,MRC.CODEA AS codeA,MRC.Codeb as codeB,MRC.Pkey as pKey," +
				"MRC.Pname as pName FROM MOBILE_REASON_CLASS MRC WHERE MRC.NODE_TYPE=3 AND PNAME='"+obstructProdType+"' order by codeA ");	

		System.out.println("查故障结果:" + sql);
		List returndata = dynamicQueryListByMap(sql.toString(), null, null);
		String resultjson = JsonUtil.getJsonByList(returndata);
		return resultjson;
	}
	public void updateUserPasswordByName(String username, String newPassword)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String sql = "UPDATE UOS_STAFF SET PASSWORD=? WHERE USERNAME=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setString(2, username);
			ps.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			cleanUp(conn, null, ps,null);	
		}
		
	}
	
	public Map getTestIntefaceData(String mappingCode) throws DataAccessException
	{

		String sql = "select mmtd.json_data as jsonData,mmtd.xml_data as xmlData " +
				"from MOBILE_MAPPING_TEST_DATA mmtd where mmtd.inteface_mapping_code ='"+mappingCode+"'";

		Map m = dynamicQueryObjectByMap(sql.toString(), null, null);
		return m;
	}

}
