package com.ztesoft.mobile.systemMobMgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class MobButtonRightDAOImpl extends BaseDAOImpl implements MobButtonRightDAO {
private static final String TABLE_NAME = "MOBILE_BUTTON_RIGHT";
private static final String TABLE_NAME2 = "MOBILE_PRIV";
	public void insert(Map paramMap) throws DataAccessException {
		System.out.println("====[debug]=insert传入的map值=======: "+paramMap.toString());
		String patternStr = "BUTTON_ID:buttonId,MUNE_ID:muneId,FORM_ID:formId,BUTTON_NAME:buttonName,NEXT_FORM_ID:nextFormId,GET_METHOD:getMethod,PRIV_CODE:privCode,BUTTON_SEQU:buttonSequ,IS_SHOW:isShow,IS_BOTTOM:isBottom,ORIENTATION:orientation,BUTTON_TYPE:buttonType,ERROR_INFO:errorInfo";
		String patternStrpriv = "PRIV_CODE:privCode,PRIV_NAME:privName,PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType,COMMENTS:comments,STATE:state";

		Long nextId = getPKFromMem("MOBILE_MUNE", 9);  //这个必须跟菜单的ID序列生成一致,因为这两个表都有权限，后面要根据ID来查询权限代码
		paramMap.put("buttonId", nextId);
		paramMap.put("privCode", "BUTTON_"+nextId);//自动生成	modify by guo.jinjun at 2012-05-10 10:56:01
		//paramMap.put("buttonSequ", getMaxSchedId());  //获取最大顺序
		
		Map priv = new HashMap();
		priv.put("privCode", MapUtils.getString(paramMap, "privCode"));
		priv.put("privName", MapUtils.getString(paramMap, "buttonName"));
		priv.put("privClassId", nextId);
		priv.put("privType", "2");
		priv.put("comments", MapUtils.getString(paramMap, "buttonName"));
		priv.put("state", "10A");
		
		dynamicInsertByMap(priv, TABLE_NAME2, patternStrpriv);  //权限
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);   //按钮
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "BUTTON_ID:buttonId,BUTTON_NAME:buttonName,NEXT_FORM_ID:nextFormId,GET_METHOD:getMethod,BUTTON_SEQU:buttonSequ,IS_SHOW:isShow,IS_BOTTOM:isBottom,ORIENTATION:orientation,BUTTON_TYPE:buttonType,ERROR_INFO:errorInfo";
		String wherePatternStr = "BUTTON_ID:buttonId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "BUTTON_ID:buttonId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void delete2(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FORM_ID:formId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   mbr.BUTTON_ID AS buttonId,mbr.BUTTON_NAME AS buttonName, mbr.NEXT_FORM_ID AS nextFormId,  mbr.GET_METHOD AS getMethod,mbr.PRIV_CODE as privCode,mbr.BUTTON_SEQU AS buttonSequ,mbr.IS_SHOW as isShow,mbr.IS_BOTTOM as isBottom,mbr.ORIENTATION as orientation,mbr.BUTTON_TYPE as buttonType,mjc.teach_name as toPage,mjc.form_name as formName,ERROR_INFO as errorInfo FROM MOBILE_BUTTON_RIGHT mbr,MOBILE_JSON_CREATE mjc where mbr.next_form_id = mjc.form_id ");
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" AND mbr.FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}
		sqlStr.append(" order by mbr.BUTTON_SEQU asc ");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   BUTTON_ID AS buttonId,  MUNE_ID AS muneId,  FORM_ID AS formId,  BUTTON_NAME AS buttonName, NEXT_FORM_ID AS nextFormId,  GET_METHOD AS getMethod,PRIV_CODE as privCode,BUTTON_SEQU AS buttonSequ,mbr.IS_SHOW as isShow,mbr.IS_BOTTOM as isBottom,mbr.ORIENTATION as orientation,mbr.BUTTON_TYPE as buttonType,ERROR_INFO as errorInfo FROM MOBILE_BUTTON_RIGHT mbr ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT BUTTON_ID AS buttonId from MOBILE_BUTTON_RIGHT " +
							"WHERE BUTTON_NAME='"+MapUtils.getString(paramMap, "buttonName")+"' AND FORM_ID = '"+MapUtils.getString(paramMap, "formId")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	/**
	 * 取最大执行顺序
	 * @param scheduleId
	 * @return
	 * @throws SQLException
	 */
//	public long getMaxSchedId() {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select case when max(b.BUTTON_SEQU) is null then 0 else max(b.BUTTON_SEQU) end + 1 as butSequId from MOBILE_BUTTON_RIGHT b ");
//		Connection conn = null;
//		PreparedStatement psmt = null;
//		ResultSet rs = null;
//		long butSequId = 0;
//		try {
//			conn = getConnection();
//			psmt = conn.prepareStatement(sql.toString());
//			rs = psmt.executeQuery();
//			while (rs.next()) {
//				butSequId = rs.getLong("butSequId");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			cleanUp(conn, null, psmt, rs);
//		}
//		return butSequId;
//	}
}


