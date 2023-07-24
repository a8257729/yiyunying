package com.ztesoft.mobile.v2.dao.broadband;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.sql.SQLException;
import java.util.Map;

public interface BroadbandDao extends BaseDAO {
    Map<Object, Object> queryBigTitle() throws DataAccessException;

    Map<Object, Object> queryQuestionByBigTitle(String paramString) throws DataAccessException;

    Map<Object, Object> queryAnswerBySmallTitle(String paramString) throws SQLException;

    Map<Object, Object> queryHotQustion() throws DataAccessException;

    Map<Object, Object> blurQuery(String paramString) throws SQLException;

    Map<Object, Object> updateAccess(String paramString1, String paramString2) throws DataAccessException;
}
