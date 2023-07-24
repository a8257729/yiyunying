package com.ztesoft.mobile.ws.dao;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WsXmlManageDAOImpl extends BaseDAOImpl implements WsXmlManageDAO {
	private static final String TABLE_NAME = "WS_XML_MANAGE";

	public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "WS_XML_MANAGE_ID:@@SEQ,MEMO:memo,UPLOAD_PATH:uploadPath,UPLOAD_TIME:uploadTime,UPLOAD_FILE_NAME:uploadFileName,STATE:state,STATE_DATE:stateDate,MAPPING_ID:mappingId,UPLOAD_STAFF_ID:uploadStaffId,UPLOAD_STAFF_NAME:uploadStaffName,WS_XML_TYPE:wsXmlType";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "WS_XML_MANAGE_ID:wsXmlManageId,MEMO:memo,UPLOAD_PATH:uploadPath,UPLOAD_TIME:uploadTime,UPLOAD_FILE_NAME:uploadFileName,STATE:state,STATE_DATE:stateDate,MAPPING_ID:mappingId,UPLOAD_STAFF_ID:uploadStaffId,UPLOAD_STAFF_NAME:uploadStaffName,WS_XML_TYPE:wsXmlType";
		String wherePatternStr = "WS_XML_MANAGE_ID:wsXmlManageId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "WS_XML_MANAGE_ID:wsXmlManageId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = this.getSqlStr(new HashMap());
        sqlStr.append(" AND WS_XML_MANAGE_ID = ? ");
		String wherePatternStr = "WS_XML_MANAGE_ID:wsXmlManageId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

    private StringBuffer getSqlStr(Map paramMap) {
        StringBuffer sqlBuf = new StringBuffer(" SELECT T.WS_XML_MANAGE_ID as wsXmlManageId, ");
        sqlBuf.append(" T.MEMO as memo, ");
        sqlBuf.append(" T.UPLOAD_PATH as uploadPath, ");
        sqlBuf.append(" T.UPLOAD_TIME as uploadTime, ");
        sqlBuf.append(" T.UPLOAD_FILE_NAME as uploadFileName, ");
        sqlBuf.append(" T.MAPPING_ID as mappingId, ");
        sqlBuf.append(" T.UPLOAD_STAFF_ID AS uploadStaffId, ");
        sqlBuf.append(" T.UPLOAD_STAFF_NAME AS uploadStaffName,");
        sqlBuf.append(" T.WS_XML_TYPE AS wsXmlType ");
        sqlBuf.append(" FROM WS_XML_MANAGE T WHERE STATE=1 ");

        Long mappingId = MapUtils.getLong(paramMap, "mappingId");
        if(null != mappingId) {
            sqlBuf.append(" AND T.MAPPING_ID = " + mappingId);
        }

        Integer wsXmlType = MapUtils.getInteger(paramMap, "wsXmlType");
        if(null != wsXmlType) {
            sqlBuf.append(" AND T.WS_XML_TYPE = " + wsXmlType);
        }

        //TODO 补充条件
        System.out.println(TABLE_NAME + "打印的SQL是：" + sqlBuf.toString());

        return sqlBuf;
    }
}
