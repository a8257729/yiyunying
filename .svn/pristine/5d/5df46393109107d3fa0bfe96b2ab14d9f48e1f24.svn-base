package com.ztesoft.mobile.v2.service.broadband;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.broadband.BroadbandDao;
import com.ztesoft.mobile.v2.dao.broadband.BroadbandDaoImpl;

import java.sql.SQLException;
import java.util.Map;

public class BroadbandServiceImpl implements BroadbandService {
    private BroadbandDao broadbandDao = (BroadbandDao)new BroadbandDaoImpl();

    public Result queryBigTitle() throws DataAccessException {
        Map<Object, Object> resultData = this.broadbandDao.queryBigTitle();
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("类问题查询成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }

    public Result queryQuestionByBigTitle(String bigTitle) throws DataAccessException {
        Map<Object, Object> resultData = this.broadbandDao.queryQuestionByBigTitle(bigTitle);
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("子问题查询成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }

    public Result queryAnswerBySmallTitle(String smallTitle) throws SQLException {
        Map<Object, Object> resultData = this.broadbandDao.queryAnswerBySmallTitle(smallTitle);
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("子问题答案查询成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }

    public Result queryHotQustion() throws DataAccessException {
        Map<Object, Object> resultData = this.broadbandDao.queryHotQustion();
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("热门问题答案查询成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }

    public Result blurQuery(String search) throws SQLException {
        Map<Object, Object> resultData = this.broadbandDao.blurQuery(search);
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("模糊查询子问题成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }

    public Result updateAccess(String smallTitle, String id) throws DataAccessException {
        Map<Object, Object> resultData = this.broadbandDao.updateAccess(smallTitle, id);
        Result result = new Result();
        if (resultData.size() > 0) {
            result.setResultData(resultData);
            result.setResultMsg("评论次数个更新成功");
                    result.setResultCode(Integer.valueOf(1000));
        } else {
            result.setResultData(null);
            result.setResultMsg("没有查到相关数据");
                    result.setResultCode(Integer.valueOf(-1000));
        }
        return result;
    }
}
