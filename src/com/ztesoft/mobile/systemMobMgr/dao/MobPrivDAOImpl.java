package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.bean.Privilege;
public class MobPrivDAOImpl extends BaseDAOImpl implements MobPrivDAO {
private static final String TABLE_NAME = "MOBILE_PRIV";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PRIV_CODE:privCode,PRIV_NAME:privName,PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType,COMMENTS:comments,STATE:state";
//		Long nextId = getPKFromMem(TABLE_NAME, 9);
//		paramMap.put("privCode", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PRIV_CODE:privCode,PRIV_NAME:privName,PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType,COMMENTS:comments,STATE:state";
		String wherePatternStr = "PRIV_CODE:privCode";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PRIV_CODE:privCode";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void delete2(Map paramMap) throws DataAccessException {
		//String wherePatternStr = "PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType";
		String sql = "delete from MOBILE_PRIV where PRIV_CLASS_ID in ("+MapUtils.getString(paramMap, "privClassId")+") and PRIV_TYPE = '"+MapUtils.getString(paramMap, "privType")+"'";
		dynamicUpdateBySql(sql);
		//dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PRIV_CODE AS privCode,  PRIV_NAME AS privName,  PRIV_CLASS_ID AS privClassId,  PRIV_TYPE AS privType,  COMMENTS AS comments,  STATE AS state FROM MOBILE_PRIV WHERE PRIV_CODE=?";
		String wherePatternStr = "PRIV_CODE:privCode";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PRIV_CODE AS privCode,  PRIV_NAME AS privName,  PRIV_CLASS_ID AS privClassId,  PRIV_TYPE AS privType,  COMMENTS AS comments,  STATE AS state FROM MOBILE_PRIV";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Privilege[] selAllPriv(int jobId, int defaultJobId) throws DataAccessException {
//		final String sqlStr ="SELECT   a.PRIV_CODE AS privCode,  a.PRIV_NAME AS privName,  " +
//							" a.PRIV_CLASS_ID AS privClassId,  a.PRIV_TYPE AS privType, " +
//							" a.COMMENTS AS comments,  a.STATE AS state FROM MOBILE_PRIV a, MOBILE_STAFF_JOB_RIGHT b " +
//							" where a.priv_code = b.priv_code and (b.job_id = "+jobId+" or b.job_id = "+defaultJobId+") ";
		final String sqlStr ="SELECT   a.PRIV_CODE AS privCode,  a.PRIV_NAME AS privName,  " +
		" a.PRIV_CLASS_ID AS privClassId,  a.PRIV_TYPE AS privType, " +
		" a.COMMENTS AS comments,  a.STATE AS state FROM MOBILE_PRIV a ";
		System.out.println("sqlStr--------->>  "+sqlStr);
		String wherePatternStr = null;
		return Privilege.convertMapListToDtoArray(dynamicQueryListByMap(sqlStr, null, wherePatternStr));
	}
	
	
	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT PRIV_CODE AS privCode from MOBILE_PRIV " +
							"WHERE PRIV_CODE='"+MapUtils.getString(paramMap, "privCode")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
}
