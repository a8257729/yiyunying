package com.ztesoft.mobile.systemMobMgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBean;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBeanNoCheck;
import com.ztesoft.mobile.systemMobMgr.util.BuildTreeUtil;

public class MobMunePrDAOImpl extends BaseDAOImpl implements MobMunePrDAO{
	private static final String TABLE_NAME = "MOBILE_MUNE";
	private static final String TABLE_NAME2 = "MOBILE_PRIV";
	public void insert(Map paramMap) throws DataAccessException {
		//System.out.println("====[debug]=insert传入的map值=======: "+paramMap.toString());
		String patternStr = "MUNE_ID:muneId,PARENT_ID:parentId,MUNE_NAME:muneName,PATH_NAME:pathName,PATH_CODE:pathCode,ISBG:isbg,DISPLAY_INEDX:displayInedx,DISPLAY_TYPE:displayType,ICON_ADR:iconAdr,OTHER_SYS_CODE:otherSysCode,TO_PAGE:toPage,GET_METHOD:getMethod,STATE:state,IS_LEAF:isLeaf,PRIV_CODE:privCode,INTEFACE_URL:intefaceUrl,AXIS_TYPE:axisType,MUNE_TYPE:menuType,IS_PASS:isPass,APK_ID:apkId,OS_TYPE:osType";
		String patternStrpriv = "PRIV_CODE:privCode,PRIV_NAME:privName,PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType,COMMENTS:comments,STATE:state";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		String pathCode = paramMap.get("pathCode").equals("null")?nextId+"":paramMap.get("pathCode")+"/"+nextId;

		paramMap.put("muneId", nextId);
		paramMap.put("privCode", "MUNE_"+nextId);//权限代码自动生成	modify by guo.jinjun at 2012-05-10 10:08:03
		paramMap.put("pathCode", pathCode.replace("null/", ""));
		paramMap.put("pathName", MapUtils.getString(paramMap, "pathName").replace("null/", ""));

		Map priv = new HashMap();
		priv.put("privCode", MapUtils.getString(paramMap, "privCode"));
		priv.put("privName", MapUtils.getString(paramMap, "muneName"));
		priv.put("privClassId", nextId);
		priv.put("privType", "1");
		priv.put("comments", MapUtils.getString(paramMap, "muneName"));
		priv.put("state", "10A");
	
		dynamicInsertByMap(priv, TABLE_NAME2, patternStrpriv);  //权限
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);       //菜单  
		
	}
	public void update(Map paramMap) throws DataAccessException {

		String updatePatternStr = "MUNE_ID:muneId,MUNE_NAME:muneName,ISBG:isbg,DISPLAY_INEDX:displayInedx,DISPLAY_TYPE:displayType,ICON_ADR:iconAdr,OTHER_SYS_CODE:otherSysCode,TO_PAGE:toPage,GET_METHOD:getMethod,INTEFACE_URL:intefaceUrl,AXIS_TYPE:axisType,MUNE_TYPE:menuType,OS_TYPE:osType";
		String wherePatternStr = "MUNE_ID:muneId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "MUNE_ID:muneId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selByDisplayInedx(Map paramMap)throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT COUNT(*) AS icount  FROM MOBILE_MUNE WHERE PARENT_ID = 0  ");

		if (MapUtils.getString(paramMap, "displayInedx") != null && !MapUtils.getString(paramMap, "displayInedx").equals("")) {
			sqlBuf.append(" AND DISPLAY_INEDX ="+MapUtils.getLong(paramMap, "displayInedx"));			
		}
		if (MapUtils.getString(paramMap, "osType") != null && !MapUtils.getString(paramMap, "osType").equals("")) {
			sqlBuf.append(" AND OS_TYPE ='"+MapUtils.getString(paramMap, "osType")+"'");			
		}
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);
		
	}
	public Map selById(Map paramMap) throws DataAccessException {
		
		final String sqlStr ="SELECT   MUNE_ID AS muneId,  PARENT_ID AS parentId,  MUNE_NAME AS muneName,PATH_NAME:pathName,PATH_CODE:pathCode,  ISBG AS isbg,  DISPLAY_TYPE AS displayType,  ICON_ADR AS iconAdr,  OTHER_SYS_CODE AS otherSysCode,  TO_PAGE AS toPage,  GET_METHOD AS getMethod,  STATE AS state,PRIV_CODE as privCode,INTEFACE_URL AS intefaceUrl,AXIS_TYPE AS axisType,MUNE_TYPE AS menuType,OS_TYPE AS osType FROM MOBILE_MUNE WHERE MUNE_ID=?";
		String wherePatternStr = "MUNE_ID:muneId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   MUNE_ID AS muneId,  PARENT_ID AS parentId,  MUNE_NAME AS muneName,PATH_NAME:pathName,PATH_CODE:pathCode,  ISBG AS isbg,  DISPLAY_TYPE AS displayType,  ICON_ADR AS iconAdr,  OTHER_SYS_CODE AS otherSysCode,  TO_PAGE AS toPage,  GET_METHOD AS getMethod,  STATE AS state,PRIV_CODE as privCode,INTEFACE_URL AS intefaceUrl,AXIS_TYPE AS axisType,MUNE_TYPE AS menuType, OS_TYPE AS osType FROM MOBILE_MUNE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   Mune_Id AS id,  Mune_Name AS name, " +
		" PARENT_ID AS parentId,  PATH_NAME AS pathName,  PATH_CODE AS pathCode,  STATE AS state,  DISPLAY_INEDX AS displayInedx, OS_TYPE AS osType," +
		" IS_LEAF AS isLeaf FROM MOBILE_MUNE " +
		"WHERE PARENT_ID="+MapUtils.getString(paramMap, "parentId")+" and Mune_Name='"+MapUtils.getString(paramMap, "name")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
//	模型目录	
	public Map getSubMenusById(int moduleId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName, P.OS_TYPE AS osType,");
		sqlBuf.append(" P.ISBG AS isbg,P.DISPLAY_TYPE AS displayType,P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,");
		sqlBuf.append(" P.OTHER_SYS_CODE as otherSysCode,P.TO_PAGE as toPage,P.GET_METHOD as getMethod, ");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode,INTEFACE_URL AS intefaceUrl,AXIS_TYPE AS axisType,MUNE_TYPE AS menuType  ");
		sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A' AND P.OS_TYPE = 0 ");
		if(moduleId == 0){
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		}else{
			sqlBuf.append(" AND P.PARENT_ID = ").append(moduleId);
		}
		sqlBuf.append(" ORDER BY P.DISPLAY_INEDX");

		System.out.println(sqlBuf.toString());

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		ArrayList resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				Map result = new HashMap();
				result.put("id", rs.getInt("id"));
				result.put("text",rs.getString("name"));
				result.put("pathCode", rs.getString("pathCode"));
				result.put("pathName",rs.getString("pathName"));
				result.put("leaf", rs.getInt("leaf")==1?true:false);
				result.put("parentId", rs.getInt("parentId"));
				result.put("displayIndex",rs.getString("displayIndex"));
				result.put("isLeaf",rs.getString("isLeaf"));

				result.put("isbg",rs.getString("isbg"));
				result.put("displayType",rs.getString("displayType"));
				result.put("iconAdr",rs.getString("iconAdr"));
				
				result.put("otherSysCode",rs.getString("otherSysCode"));
				result.put("toPage",rs.getString("toPage"));
				result.put("getMethod",rs.getString("getMethod"));
				
				result.put("privCode",rs.getString("privCode"));
				result.put("intefaceUrl",rs.getString("intefaceUrl"));
				result.put("axisType",rs.getString("axisType"));
				result.put("menuType", rs.getString("menuType"));
				resultList.add(result);
			}
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	public Map getSubIndexById(int moduleId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" P.ISBG AS isbg,P.DISPLAY_TYPE AS displayType,P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,");
		sqlBuf.append(" P.OTHER_SYS_CODE as otherSysCode,P.TO_PAGE as toPage,P.GET_METHOD as getMethod, P.OS_TYPE AS osType,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode,INTEFACE_URL AS intefaceUrl,AXIS_TYPE AS axisType,MUNE_TYPE AS menuType  ");
		sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A'");
		if(moduleId == 0){
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		}else{
			sqlBuf.append(" AND P.PARENT_ID = ").append(moduleId);
		}

		System.out.println(sqlBuf.toString());

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		ArrayList resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				Map result = new HashMap();
				result.put("id", rs.getInt("id"));
				result.put("text",rs.getString("name"));
				result.put("pathCode", rs.getString("pathCode"));
				result.put("pathName",rs.getString("pathName"));
				result.put("leaf", rs.getInt("leaf")==1?true:false);
				result.put("parentId", rs.getInt("parentId"));
				result.put("displayIndex",rs.getString("displayIndex"));
				result.put("isLeaf",rs.getString("isLeaf"));
				
				result.put("isbg",rs.getString("isbg"));
				result.put("displayType",rs.getString("displayType"));
				result.put("iconAdr",rs.getString("iconAdr"));
				
				result.put("otherSysCode",rs.getString("otherSysCode"));
				result.put("toPage",rs.getString("toPage"));
				result.put("getMethod",rs.getString("getMethod"));
				
				result.put("privCode",rs.getString("privCode"));
				result.put("intefaceUrl",rs.getString("intefaceUrl"));
				result.put("axisType",rs.getString("axisType"));
				result.put("menuType", rs.getString("menuType"));
				resultList.add(result);
			}
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	//权限选择
	public Map getAllParentPrivData(int moduleId,int jobId, int defaultJobId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" P.ISBG AS isbg,P.DISPLAY_TYPE AS displayType,P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,");
		sqlBuf.append(" P.OTHER_SYS_CODE as otherSysCode,P.TO_PAGE as toPage,P.GET_METHOD as getMethod, ");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode,INTEFACE_URL AS intefaceUrl,AXIS_TYPE AS axisType,MUNE_TYPE AS menuType  ");
		sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A'");
		if(moduleId == 0){
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		}else{
			sqlBuf.append(" AND P.PARENT_ID = ").append(moduleId);
		}
		sqlBuf.append(" AND PRIV_CODE not in (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+")) ");

		System.out.println(sqlBuf.toString());

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		ArrayList resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				Map result = new HashMap();
				result.put("id", rs.getInt("id"));
				result.put("text",rs.getString("name"));
				result.put("pathCode", rs.getString("pathCode"));
				result.put("pathName",rs.getString("pathName"));
				result.put("leaf", rs.getInt("leaf")==1?true:false);
				result.put("parentId", rs.getInt("parentId"));
				result.put("displayIndex",rs.getString("displayIndex"));
				result.put("privCode",rs.getString("privCode"));
				result.put("intefaceUrl",rs.getString("intefaceUrl"));
				resultList.add(result);
			}
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	//职位权限选择
	public Map getAllSubPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if(staffId.equals("0")){          //超级管理员权限
			sqlBuf.append("SELECT P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A'");

			sqlBuf.append(" AND PRIV_CODE NOT IN (select mm.PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MUNE mm where mm.priv_code=msjr.priv_code and mm.is_leaf=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
		}else {              //人员可以分配给其它人的权限
			sqlBuf.append("SELECT distinct P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MUNE P ");
			sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MUNE a, MOBILE_STAFF_JOB_RIGHT b where a.priv_code = b.priv_code and (b.JOB_ID = "+jobId+" or b.JOB_ID = "+specialJobId+")) V ");
			sqlBuf.append(" WHERE P.STATE = '10A'");
			
			//sqlBuf.append(" AND (P.Mune_Id != 0)");
			sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.Mune_Id = V.parent_id)  ");
			//sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
			sqlBuf.append(" AND PRIV_CODE NOT IN (select mm.PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MUNE mm where mm.priv_code=msjr.priv_code and mm.is_leaf=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				//bean.setChecked(false);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");

			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBeanNoCheck nebean = (TreeBeanNoCheck)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			
			
			System.out.println("resultList--->  "+newresultlist.toString());
			map.put("resultList", newresultlist);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	//已有职位权限选择
	public Map getAllHasPrivData(int jobId, int defaultJobId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		/*sqlBuf.append("SELECT distinct P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MUNE P ");
		sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MUNE a, MOBILE_STAFF_JOB_RIGHT b where a.priv_code = b.priv_code and (b.JOB_ID = "+jobId+" or b.JOB_ID = "+defaultJobId+")) V ");
		sqlBuf.append(" WHERE P.STATE = '10A'");
		
		sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.Mune_Id = V.parent_id)  ");*/
		sqlBuf.append("SELECT distinct P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,p.PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MUNE P ,MOBILE_STAFF_JOB_RIGHT b ");
		sqlBuf.append(" WHERE P.STATE = '10A'");
		sqlBuf.append(" and p.priv_code = b.priv_code and (b.JOB_ID = "+jobId+" or b.JOB_ID = "+defaultJobId+") ");

		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				//bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	
	//角色权限选择
	public Map getAllRolePrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if(staffId.equals("0")){          //超级管理员权限
			sqlBuf.append("SELECT P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A'");

			sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
		}else {              //人员可以分配给其它人的权限
			sqlBuf.append("SELECT distinct P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MUNE P ");
			sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MUNE a, MOBILE_ROLE_PRIV b where a.priv_code = b.priv_code and (b.JOB_ID = "+jobId+" or b.JOB_ID = "+specialJobId+")) V ");
			sqlBuf.append(" WHERE P.STATE = '10A'");
			
			//sqlBuf.append(" AND (P.Mune_Id != 0)");
			sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.Mune_Id = V.parent_id)  ");
			sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(false);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");

			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBean nebean = (TreeBean)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			
			
			System.out.println("resultList--->  "+newresultlist.toString());
			map.put("resultList", newresultlist);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	
	//已有角色权限选择
	public Map getAllHasRolePrivData(int roleId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT distinct P.Mune_Id AS id, P.Mune_Name AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.Mune_Id FROM MOBILE_MUNE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.Mune_Id) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INEDX AS displayIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MUNE P ");
		sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MUNE a, MOBILE_ROLE_PRIV b where a.priv_code = b.priv_code and (b.ROLE_ID = "+roleId+")) V ");
		sqlBuf.append(" WHERE P.STATE = '10A'");
		
		//sqlBuf.append(" AND (P.Mune_Id != 0)");
		sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.Mune_Id = V.parent_id)  ");

		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	
	
	//可选择操作按钮权限选择
	public Map getAllButPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if(staffId.equals("0")){          //超级管理员权限
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
			sqlBuf.append(" ) where 1=1 ");
		}else {
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+specialJobId+")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+specialJobId+")) ");
			sqlBuf.append(" ) where 1=1 ");
			sqlBuf.append(" AND privCode NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				//bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
	
			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBeanNoCheck nebean = (TreeBeanNoCheck)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			System.out.println("alllist---->> "+alllist.toString());
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	//可选择操作按钮权限选择
	public Map getHasButPrivData(int jobId, int defaultJobId)throws Exception {
		//sqlBuf.append(" SELECT P.Mune_Id AS id, P.MUNE_NAME AS name, P.PARENT_ID AS parentId, P.PRIV_CODE as privCode,0 as leaf FROM MOBILE_MUNE P WHERE P.STATE = '10A' ");
		
        //sqlBuf.append(" union ");
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

		sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+")) ");
		sqlBuf.append(" union ");
		sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+defaultJobId+")) ");
		sqlBuf.append(" ) where 1=1 ");
      
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				//bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
	
			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBeanNoCheck nebean = (TreeBeanNoCheck)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			System.out.println("alllist---->> "+alllist.toString());
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	
	
	//可选择角色操作按钮权限选择
	public Map getAllRoleButPrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if(staffId.equals("0")){          //超级管理员权限
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
			sqlBuf.append(" ) where 1=1 ");
		}else {
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+specialJobId+")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+jobId+" or msjr.JOB_ID = "+specialJobId+")) ");
			sqlBuf.append(" ) where 1=1 ");
			sqlBuf.append(" AND privCode NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				//bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
	
			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBeanNoCheck nebean = (TreeBeanNoCheck)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			System.out.println("alllist---->> "+alllist.toString());
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	//可选择角色操作按钮权限选择
	public Map getHasRoleButPrivData(int roleId)throws Exception {
		//sqlBuf.append(" SELECT P.Mune_Id AS id, P.MUNE_NAME AS name, P.PARENT_ID AS parentId, P.PRIV_CODE as privCode,0 as leaf FROM MOBILE_MUNE P WHERE P.STATE = '10A' ");
		
        //sqlBuf.append(" union ");
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

		sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MUNE_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MUNE P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.Mune_Id=P3.Mune_Id AND P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
		sqlBuf.append(" union ");
		sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "+roleId+")) ");
		sqlBuf.append(" ) where 1=1 ");
      
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(true);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");
	
			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBean nebean = (TreeBean)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			System.out.println("alllist---->> "+alllist.toString());
			System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	public Map getMenuRank() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT MUNE_NAME AS name,MUNE_ID AS ID,COUNT_NUM AS downloadCt,ICON_ADR AS icon from ( ")
		.append(" SELECT MM.MUNE_NAME,MM.MUNE_ID,RANK.COUNT_NUM,MM.ICON_ADR FROM MOBILE_MUNE MM,( ")
		.append(" SELECT A.PRIV_CODE, COUNT(1) AS COUNT_NUM ")
		.append("   FROM MOBILE_MUNE A, MOBILE_STAFF_JOB_RIGHT B ")
		.append("  WHERE A.PRIV_CODE = B.PRIV_CODE ")
		.append("  GROUP BY A.PRIV_CODE) RANK ")
		.append("  WHERE MM.PRIV_CODE=RANK.PRIV_CODE ")
		.append("  AND MM.PARENT_ID!=0 ")
		.append("  ORDER BY RANK.COUNT_NUM DESC) ")
		.append(" where rownum <=10 ");
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		ArrayList resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				Map result = new HashMap();
				result.put("id", rs.getInt("id"));
				result.put("name",rs.getString("name"));
				result.put("downloadCt", rs.getString("downloadCt"));
				result.put("icon",rs.getString("icon"));
				resultList.add(result);
			}
			map.put("resultList", resultList);
			return map;
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
}
