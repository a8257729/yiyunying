package com.ztesoft.mobile.outsystem.dao;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileXmlDocumentDAOImpl extends BaseDAOImpl implements
		MobileXmlDocumentDAO {
	
	private static final String TABLE_NAME = "MOBILE_XML_DOCUMENT";
	private static final String SEQUENCE = "SEQ_MOBILE_XML_DOCUMENT";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "XML_DOCUMENT_ID:xmlDocumentId,XML_DOCUMENT_NAME:xmlDocumentName,XML_DOCUMENT_VERSION:xmlDocumentVersion,XML_DOCUMENT_ENCODING:xmlDocumentEncoding,XML_DOCUMENT_NAMESPACE:xmlDocumentNamespace,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "XML_DOCUMENT_ID:xmlDocumentId,XML_DOCUMENT_NAME:xmlDocumentName,XML_DOCUMENT_VERSION:xmlDocumentVersion,XML_DOCUMENT_ENCODING:xmlDocumentEncoding,XML_DOCUMENT_NAMESPACE:xmlDocumentNamespace,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "XML_DOCUMENT_ID:xmlDocumentId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "XML_DOCUMENT_ID:xmlDocumentId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   XML_DOCUMENT_ID AS xmlDocumentId,  XML_DOCUMENT_NAME AS xmlDocumentName,  XML_DOCUMENT_VERSION AS xmlDocumentVersion,  XML_DOCUMENT_ENCODING AS xmlDocumentEncoding,  XML_DOCUMENT_NAMESPACE AS xmlDocumentNamespace,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_XML_DOCUMENT WHERE XML_DOCUMENT_ID=?";
		String wherePatternStr = "XML_DOCUMENT_ID:xmlDocumentId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   XML_DOCUMENT_ID AS xmlDocumentId,  XML_DOCUMENT_NAME AS xmlDocumentName,  XML_DOCUMENT_VERSION AS xmlDocumentVersion,  XML_DOCUMENT_ENCODING AS xmlDocumentEncoding,  XML_DOCUMENT_NAMESPACE AS xmlDocumentNamespace,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_XML_DOCUMENT";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
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
}