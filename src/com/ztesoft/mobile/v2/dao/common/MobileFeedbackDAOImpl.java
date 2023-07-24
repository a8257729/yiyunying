package com.ztesoft.mobile.v2.dao.common;

/**
 * User: heisonyee
 * Date: 13-3-13
 * Time: ÉÏÎç10:36
 */


import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileFeedbackDAOImpl extends BaseDAOImpl implements MobileFeedbackDAO {
    private static final String TABLE_NAME = "MOBILE_FEEDBACK";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "FEEDBACK_ID:@@SEQ,TITLE:title,CONTENT:content,CONTACT_TYPE:contactType,CONTACT:contact,FEEDBACK_TIME:feedbackTime";
        //Long nextId = getPKFromMem(TABLE_NAME, 9);
        //paramMap.put("feedbackId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

/*    public void insertBatch(List paramMapList) throws DataAccessException {
        String patternStr = "FEEDBACK_ID:feedbackId,TITLE:title,CONTENT:content,CONTACT_TYPE:contactType,CONTACT:contact,FEEDBACK_TIME:feedbackTime";
        long nextId = getPKFromMem(TABLE_NAME, 9, paramMapList.size()).longValue();
        for (int i = 0; i < paramMapList.size(); i++) {
            ((Map) paramMapList.get(i)).put("feedbackId", new Long(nextId));
            nextId--;
        }
        dynamicInsertBatchByMap(paramMapList, TABLE_NAME, patternStr);
    }*/

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "FEEDBACK_ID:feedbackId,TITLE:title,CONTENT:content,CONTACT_TYPE:contactType,CONTACT:contact,FEEDBACK_TIME:feedbackTime";
        String wherePatternStr = "FEEDBACK_ID:feedbackId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

/*    public void updateBatch(List paramMapList) throws DataAccessException {
        String updatePatternStr = "FEEDBACK_ID:feedbackId,TITLE:title,CONTENT:content,CONTACT_TYPE:contactType,CONTACT:contact,FEEDBACK_TIME:feedbackTime";
        String wherePatternStr = "FEEDBACK_ID:feedbackId";
        dynamicUpdateBatchByMap(paramMapList, TABLE_NAME, updatePatternStr, wherePatternStr);
    }*/

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "FEEDBACK_ID:feedbackId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   FEEDBACK_ID AS feedbackId,  TITLE AS title,  CONTENT AS content,  CONTACT_TYPE AS contactType,  CONTACT AS contact,  FEEDBACK_TIME AS feedbackTime FROM MOBILE_FEEDBACK WHERE FEEDBACK_ID=?";
        String wherePatternStr = "FEEDBACK_ID:feedbackId";
        return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   FEEDBACK_ID AS feedbackId,  TITLE AS title,  CONTENT AS content,  CONTACT_TYPE AS contactType,  CONTACT AS contact,  FEEDBACK_TIME AS feedbackTime FROM MOBILE_FEEDBACK";
        String wherePatternStr = null;
        return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}