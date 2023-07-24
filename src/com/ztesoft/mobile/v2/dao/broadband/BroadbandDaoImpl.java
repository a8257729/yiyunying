package com.ztesoft.mobile.v2.dao.broadband;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadbandDaoImpl extends BaseDAOImpl implements BroadbandDao {
    public Map<Object, Object> queryBigTitle() throws DataAccessException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "SELECT distinct CLASS_NAME FROM mobile.KD_QUESTION";
        List questionList = dynamicQueryListByMap(sqlStr, null, null);
        resultDate.put("questionList", questionList);
        return resultDate;
    }

    public Map<Object, Object> queryQuestionByBigTitle(String bigTitle) throws DataAccessException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "SELECT QUESTION_TITLE FROM mobile.KD_QUESTION WHERE CLASS_NAME='" + bigTitle + "'";
        List sonQuestionList = dynamicQueryListByMap(sqlStr, null, null);
        resultDate.put("sonQuestionList", sonQuestionList);
        return resultDate;
    }

    public Map<Object, Object> queryAnswerBySmallTitle(String smallTitle) throws SQLException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "SELECT QUESTION_TITLE,QUESTION_CONTENT,HELPFUL_CNT,UNHELPFUL_CNT FROM mobile.KD_QUESTION WHERE QUESTION_TITLE = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,smallTitle);
            rs = ps.executeQuery();
            Map<String,Object> map = new HashMap<String, Object>();
            while(rs.next()){
                map = new HashMap<String, Object>();
                map.put("QUESTION_TITLE",rs.getString("QUESTION_TITLE"));
                map.put("QUESTION_CONTENT",rs.getString("QUESTION_CONTENT"));
                map.put("HELPFUL_CNT",rs.getString("HELPFUL_CNT"));
                map.put("UNHELPFUL_CNT",rs.getString("UNHELPFUL_CNT"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

//        List sonQuestionAnswer = dynamicQueryListByMap(sqlStr, null, null);
        resultDate.put("sonQuestionAnswer", list);
        return resultDate;
    }

    public Map<Object, Object> queryHotQustion() throws DataAccessException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "SELECT  CLASS_NAME,QUESTION_TITLE,QUESTION_CONTENT  FROM  mobile.KD_QUESTION  where rownum<11  ORDER BY  USE_CNT DESC ";
        List hotQustion = dynamicQueryListByMap(sqlStr, null, null);
        resultDate.put("hotQustion", hotQustion);
        return resultDate;
    }

    public Map<Object, Object> blurQuery(String search) throws SQLException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "SELECT QUESTION_TITLE,QUESTION_CONTENT FROM  mobile.KD_QUESTION WHERE QUESTION_TITLE LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,"%" + search + "%");
            rs = ps.executeQuery();
            Map<String,Object> map = new HashMap<String, Object>();
            while(rs.next()){
                map = new HashMap<String, Object>();
                map.put("QUESTION_TITLE",rs.getString("QUESTION_TITLE"));
                map.put("QUESTION_CONTENT",rs.getString("QUESTION_CONTENT"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        resultDate.put("blur", list);
        return resultDate;
    }

    public Map<Object, Object> updateAccess(String smallTitle, String id) throws DataAccessException {
        Map<Object, Object> resultDate = new HashMap<Object, Object>();
        String sqlStr = "";
        ResultSet rs = null;
        if ("1".equals(id)) {
            sqlStr = "UPDATE  mobile.KD_QUESTION SET HELPFUL_CNT=HELPFUL_CNT+1  WHERE QUESTION_TITLE=?";
        }
        if ("0".equals(id)) {
            sqlStr = "UPDATE mobile.KD_QUESTION SET UNHELPFUL_CNT=UNHELPFUL_CNT+1  WHERE QUESTION_TITLE=?";
        }

        Connection conn = null;
        PreparedStatement ps = null;
        int access = 0;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1, smallTitle);
            access = ps.executeUpdate();
            resultDate.put("access", Integer.valueOf(access));
            return resultDate;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        resultDate.put("access", Integer.valueOf(access));
        return resultDate;
    }
}
