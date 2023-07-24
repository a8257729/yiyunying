package com.ztesoft.mobile.v2.service.broadband;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;

import java.sql.SQLException;

public interface BroadbandService {
    Result queryBigTitle() throws DataAccessException;

    Result queryQuestionByBigTitle(String paramString) throws DataAccessException;

    Result queryAnswerBySmallTitle(String paramString) throws SQLException;

    Result queryHotQustion() throws DataAccessException;

    Result blurQuery(String paramString) throws SQLException;

    Result updateAccess(String paramString1, String paramString2) throws DataAccessException;
}
