package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class FiledInfoDAOImpl extends BaseDAOImpl implements FiledInfoDAO {
private static final String TABLE_NAME = "MOBILE_FIELD_INFO";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "FILED_ID:filedId,FORM_ID:formId,FILED_NAME:filedName,FILED_LABLE:filedLable,FILED_TYPE:filedType,CHECKED_DATA:checkedData,SELECT_DATA:selectData,IS_DISPLAY:isDisplay,IS_PASS_VALUE:isPassValue,IS_REQUIRED:isRequired,SEQ_ID:seqId,INDEX_ID:indexId,IS_KEYID:isKeyid,IS_SEARCH_FIELD:isSearchField,POSITION:position,ATTR_CODE:attrCode,ACTION_CODE:actionCode,IS_SHOW_LABEL:isShowLabel,DEFAULT_VALUE:defaultValue,NODE_NAME:nodeName,DATA_FORM:dataForm,ACTION_TYPE:actionType,ACTION_EVENT:actionEvent";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("filedId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "FILED_ID:filedId,FORM_ID:formId,FILED_NAME:filedName,FILED_LABLE:filedLable,FILED_TYPE:filedType,CHECKED_DATA:checkedData,SELECT_DATA:selectData,IS_DISPLAY:isDisplay,IS_PASS_VALUE:isPassValue,IS_REQUIRED:isRequired,SEQ_ID:seqId,INDEX_ID:indexId,IS_KEYID:isKeyid,IS_SEARCH_FIELD:isSearchField,POSITION:position,ATTR_CODE:attrCode,ACTION_CODE:actionCode,IS_SHOW_LABEL:isShowLabel,DEFAULT_VALUE:defaultValue,NODE_NAME:nodeName,DATA_FORM:dataForm,ACTION_TYPE:actionType,ACTION_EVENT:actionEvent";
		String wherePatternStr = "FILED_ID:filedId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FILED_ID:filedId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void delete2(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FORM_ID:formId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   a.FILED_ID AS filedId,  a.FORM_ID AS formId,  a.FILED_NAME AS filedName,  " +
				"a.FILED_LABLE AS filedLable,  a.FILED_TYPE AS filedType,  a.CHECKED_DATA AS checkedData, " +
				" a.SELECT_DATA AS selectData,  a.IS_DISPLAY AS isDisplay,  a.IS_PASS_VALUE AS isPassValue,b.TYPE_LABLE as typeName,IS_REQUIRED as isRequired " +
				",SEQ_ID as seqId,INDEX_ID as indexId, IS_KEYID AS isKeyid,  IS_SEARCH_FIELD AS isSearchField,  POSITION AS position,  ATTR_CODE AS attrCode,  ACTION_CODE AS actionCode, " +
				"  IS_SHOW_LABEL AS isShowLabel,  DEFAULT_VALUE AS defaultValue,  NODE_NAME AS nodeName,  DATA_FORM AS dataForm,  ACTION_TYPE AS actionType,  ACTION_EVENT AS actionEvent " +
				" FROM MOBILE_FIELD_INFO a,MOBILE_FILED_TYPE b WHERE a.FILED_TYPE = b.TYPE_NAME  ");
	
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" AND FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}
		if (MapUtils.getString(paramMap, "nodeName")!=null&&!MapUtils.getString(paramMap, "nodeName").equals("0")) {
			sqlStr.append(" AND NODE_NAME ='"+MapUtils.getString(paramMap, "nodeName")+"'");
		}
		sqlStr.append(" order by SEQ_ID asc ");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   FILED_ID AS filedId,  FORM_ID AS formId,  FILED_NAME AS filedName,  FILED_LABLE AS filedLable,  FILED_TYPE AS filedType,  CHECKED_DATA AS checkedData,  SELECT_DATA AS selectData,  IS_DISPLAY AS isDisplay,  IS_PASS_VALUE AS isPassValue,IS_REQUIRED as isRequired,SEQ_ID as seqId,INDEX_ID as indexId, IS_KEYID AS isKeyid,  IS_SEARCH_FIELD AS isSearchField,  POSITION AS position,  ATTR_CODE AS attrCode,  ACTION_CODE AS actionCode,  IS_SHOW_LABEL AS isShowLabel,  DEFAULT_VALUE AS defaultValue,  NODE_NAME AS nodeName, DATA_FORM AS dataForm,  ACTION_TYPE AS actionType,  ACTION_EVENT AS actionEvent   FROM MOBILE_FIELD_INFO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT FILED_ID AS filedId from MOBILE_FIELD_INFO " +
							"WHERE FILED_NAME='"+MapUtils.getString(paramMap, "filedName")+"' AND FORM_ID = '"+MapUtils.getString(paramMap, "formId")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
}

