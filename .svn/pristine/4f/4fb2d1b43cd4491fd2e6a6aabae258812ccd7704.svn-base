package com.ztesoft.mobile.outsystem.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileXmlElementDAOImpl extends BaseDAOImpl implements
		MobileXmlElementDAO {
	private static final String TABLE_NAME = "MOBILE_XML_ELEMENT";
	private static final String SEQUENCE = "SEQ_MOBILE_XML_ELEMENT";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "XML_ELEMENT_ID:xmlElementId,ELEMENT_NAME:elementName,ELEMENT_DATATYPE:elementDatatype,ELEMENT_DESC:elementDesc,ELEMENT_TYPE:elementType,ELEMENT_LENGTH:elementLength,ELEMENT_ISNULL:elementIsnull,PARENT_ID:parentId,PATH_CODE:pathCode,PATH_NAME:pathName,XML_DOCUMENT_ID:xmlDocumentId,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "XML_ELEMENT_ID:xmlElementId,ELEMENT_NAME:elementName,ELEMENT_DATATYPE:elementDatatype,ELEMENT_DESC:elementDesc,ELEMENT_TYPE:elementType,ELEMENT_LENGTH:elementLength,ELEMENT_ISNULL:elementIsnull,PARENT_ID:parentId,PATH_CODE:pathCode,PATH_NAME:pathName,XML_DOCUMENT_ID:xmlDocumentId,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "XML_ELEMENT_ID:xmlElementId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "XML_ELEMENT_ID:xmlElementId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   XML_ELEMENT_ID AS xmlElementId,  ELEMENT_NAME AS elementName,  ELEMENT_DATATYPE AS elementDatatype,  ELEMENT_DESC AS elementDesc,  ELEMENT_TYPE AS elementType,  ELEMENT_LENGTH AS elementLength,  ELEMENT_ISNULL AS elementIsnull,  PARENT_ID AS parentId,  PATH_CODE AS pathCode,  PATH_NAME AS pathName,  XML_DOCUMENT_ID AS xmlDocumentId, STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_XML_ELEMENT WHERE XML_ELEMENT_ID=?";
		String wherePatternStr = "XML_ELEMENT_ID:xmlElementId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		String sqlStr = "SELECT   XML_ELEMENT_ID AS xmlElementId,  ELEMENT_NAME AS elementName,  ELEMENT_DATATYPE AS elementDatatype,  ELEMENT_DESC AS elementDesc,  ELEMENT_TYPE AS elementType,  ELEMENT_LENGTH AS elementLength,  ELEMENT_ISNULL AS elementIsnull,  PARENT_ID AS parentId,  PATH_CODE AS pathCode,  PATH_NAME AS pathName,  XML_DOCUMENT_ID AS xmlDocumentId, STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_XML_ELEMENT";
		//String wherePatternStr = null;
		Long xmlDocumentId = MapUtils.getLong(paramMap, "xmlDocumentId", null);
		if(null != xmlDocumentId) {
			sqlStr += "AND XML_DOCUMENT_ID = " + xmlDocumentId;
		}
		
		sqlStr += " order by parent_id asc ";
		
		return dynamicQueryListByMap(sqlStr, null, null);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Long generatePK() throws DataAccessException {
		int i = -1;
		try {
			i = getSeqId(SEQUENCE).intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i = -1;
		}
		return new Long(i);
	}

	public static void main(String args[]) throws Exception {
		MobileXmlElementDAOImpl im = new MobileXmlElementDAOImpl();
		Long l = im.generatePK();
		System.out.println(l);
	}

}