package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class JsonCreateDAOImpl extends BaseDAOImpl implements JsonCreateDAO {
private static final String TABLE_NAME = "MOBILE_JSON_CREATE";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "FORM_ID:formId,MUNE_ID:muneId,FORM_NAME:formName,TEACH_NAME:teachName,DISPLAY_TYPE:displayType,INTEFACE_TYPE:intefaceType,INTEFACE_URL:intefaceUrl,INTEFACE_NAME:intefaceName,KEY_NAME:keyName,FRIST_PAGE:fristPage,OS_TYPE:osType";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("formId", nextId);
		paramMap.put("teachName", "PAGE_"+nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "FORM_ID:formId,FORM_NAME:formName,DISPLAY_TYPE:displayType,INTEFACE_TYPE:intefaceType,INTEFACE_URL:intefaceUrl,INTEFACE_NAME:intefaceName,KEY_NAME:keyName,FRIST_PAGE:fristPage";
		String wherePatternStr = "FORM_ID:formId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FORM_ID:formId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		System.out.println("paramMap---->>> "+paramMap.toString());
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   FORM_ID AS formId,  MUNE_ID AS muneId,  FORM_NAME AS formName,TEACH_NAME AS teachName, DISPLAY_TYPE AS displayType,  INTEFACE_TYPE AS intefaceType,  INTEFACE_URL AS intefaceUrl,INTEFACE_NAME AS intefaceName,KEY_NAME AS keyName, FRIST_PAGE AS fristPage FROM MOBILE_JSON_CREATE where 1=1  ");

		if (!MapUtils.getString(paramMap, "moduleId").equals("0")) {
			sqlStr.append(" AND MUNE_ID ="+MapUtils.getString(paramMap, "moduleId"));
		}
		sqlStr.append(" order by FRIST_PAGE desc,FORM_ID asc ");
		System.out.println("sqlStr---->>> "+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   FORM_ID AS formId,  MUNE_ID AS muneId,  FORM_NAME AS formName,TEACH_NAME AS teachName,   DISPLAY_TYPE AS displayType,  INTEFACE_TYPE AS intefaceType,  INTEFACE_URL AS intefaceUrl,NTEFACE_NAME AS intefaceName,KEY_NAME AS keyName, FRIST_PAGE AS fristPage FROM MOBILE_JSON_CREATE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	
	public List selByName(Map paramMap) throws DataAccessException {
		String sqlStr ="SELECT FORM_ID AS formId from MOBILE_JSON_CREATE ";
		if(MapUtils.getString(paramMap, "fristPage")!=null&&!MapUtils.getString(paramMap, "fristPage").equals("0")){
			sqlStr += "WHERE FRIST_PAGE="+MapUtils.getString(paramMap, "fristPage")+" AND MUNE_ID="+MapUtils.getString(paramMap, "muneId");
		}else if(MapUtils.getString(paramMap, "teachName")!=null&&!MapUtils.getString(paramMap, "teachName").equals("0")){
			sqlStr += "WHERE TEACH_NAME='"+MapUtils.getString(paramMap, "teachName")+"'";
		}	
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	
	public List selName(String sqlStr) throws DataAccessException {
		return dynamicQueryListByMap(sqlStr, null, null);
	}
}

