package com.ztesoft.mobile.systemMonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.collections.MapUtils;

public class MobileFuncCallRecordDAOImpl extends BaseDAOImpl implements MobileFuncCallRecordDAO {
	
	private static final String TABLE_NAME = "MOBILE_FUNC_CALL_RECORD";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ID:id,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STAFF_ID:staffId,STAFF_NAME:staffName,NRECEIVE_TIME:nreceiveTime,SSEND_TIME:ssendTime,SRECEIVE_TIME:sreceiveTime,NBACK_TIME:nbackTime,CREATE_TIME:createTime,OUT_MESSAGE:outMessage,IN_MESSAGE:inMessage,IN_MESSAGE_SIZE:inMessageSize,OUT_MESSAGE_SIZE:outMessageSize,COMMENTS:comments,STATE:state,IS_SUCCESS:isSuccess,USERNAME:username";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("id", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ID:id,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STAFF_ID:staffId,STAFF_NAME:staffName,NRECEIVE_TIME:nreceiveTime,SSEND_TIME:ssendTime,SRECEIVE_TIME:sreceiveTime,NBACK_TIME:nbackTime,CREATE_TIME:createTime,OUT_MESSAGE:outMessage,IN_MESSAGE:inMessage,IN_MESSAGE_SIZE:inMessageSize,OUT_MESSAGE_SIZE:outMessageSize,COMMENTS:comments,STATE:state,IS_SUCCESS:isSuccess,USERNAME:username";
		String wherePatternStr = "ID:id";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ID:id";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  NRECEIVE_TIME AS nreceiveTime,  SSEND_TIME AS ssendTime,  SRECEIVE_TIME AS sreceiveTime,  NBACK_TIME AS nbackTime,  CREATE_TIME AS createTime,  OUT_MESSAGE AS outMessage,  IN_MESSAGE AS inMessage,  IN_MESSAGE_SIZE AS inMessageSize,  OUT_MESSAGE_SIZE AS outMessageSize,  COMMENTS AS comments,  STATE AS state,IS_SUCCESS AS isSuccess,USERNAME AS username FROM MOBILE_FUNC_CALL_RECORD WHERE ID=?";
		String wherePatternStr = "ID:id";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  NRECEIVE_TIME AS nreceiveTime,  SSEND_TIME AS ssendTime,  SRECEIVE_TIME AS sreceiveTime,  NBACK_TIME AS nbackTime,  CREATE_TIME AS createTime,  OUT_MESSAGE AS outMessage,  IN_MESSAGE AS inMessage,  IN_MESSAGE_SIZE AS inMessageSize,  OUT_MESSAGE_SIZE AS outMessageSize,  COMMENTS AS comments,  STATE AS state,IS_SUCCESS AS isSuccess,USERNAME AS username FROM MOBILE_FUNC_CALL_RECORD";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selByConn(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT  REST_SERVICE_ID AS serviceId,  SERV_NAME AS serviceName ");
		sqlStr.append(" FROM  MOBILE_REST_SERVICE WHERE STATE = 1  ");
		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND REST_SERVICE_ID =").append(MapUtils.getLong(paramMap, "serviceId"));
		}
		
		sqlStr.append(" order by REST_SERVICE_ID asc ");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public Map qryTimeConsuming(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();

		sqlStr.append("  SELECT SERVICE_ID as serviceId ,SERVICE_NAME as serviceName,USERNAME as username,SERVICE_ID as staffId,id as id,CREATE_TIME as createTime,TO_CHAR(NRECEIVE_TIME,'YYYY-MM-DD HH24:MI:SS:FF3') as nreceiveTime,TO_CHAR(SSEND_TIME,'YYYY-MM-DD HH24:MI:SS:FF3') as ssendTime,TO_CHAR(SRECEIVE_TIME,'YYYY-MM-DD HH24:MI:SS:FF3') as sreceiveTime,TO_CHAR(NBACK_TIME,'YYYY-MM-DD HH24:MI:SS:FF3') as nbackTime, ");  
		sqlStr.append("  (TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(NRECEIVE_TIME,'YYYYMMDDHH24MISSFF3')) AS firstTimeconsuming,  ");       
		sqlStr.append("  (TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3')) AS secondTimeconsuming,  ");  
		sqlStr.append("  (TO_CHAR(NBACK_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3')) AS thirdTimeconsuming  ");               
		sqlStr.append(" FROM MOBILE_FUNC_CALL_RECORD    "); 		
		sqlStr.append("	WHERE STATE = 1  "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" ORDER BY CREATE_TIME DESC  "); 

		//System.out.println("qryTimeConsuming="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	
       
	}
	public Map qryAvgTimeConsuming(Map paramMap) throws SQLException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT SERVICE_ID as serviceId,SERVICE_NAME as serviceName,AVGFIRSTTIMECONSUMING as avgServerAskTime,AVGSECONDTIMECONSUMING as avgBusiAnswerTime,AVGTHIRDTIMECONSUMING as avgServerAnswerTime FROM ");  
		sqlStr.append(" (SELECT SERVICE_ID,SERVICE_NAME,AVG(FIRSTTIMECONSUMING) AS  AVGFIRSTTIMECONSUMING, ");  
		sqlStr.append("   AVG(SECONDTIMECONSUMING) AS AVGSECONDTIMECONSUMING,AVG(THIRDTIMECONSUMING) AS AVGTHIRDTIMECONSUMING  ");  
		sqlStr.append("   FROM  (SELECT SERVICE_ID,SERVICE_NAME,  ");  
		sqlStr.append("  (TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(NRECEIVE_TIME,'YYYYMMDDHH24MISSFF3'))  FIRSTTIMECONSUMING,  ");       
		sqlStr.append("  (TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3')) SECONDTIMECONSUMING,  ");  
		sqlStr.append("  (TO_CHAR(NBACK_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3')) THIRDTIMECONSUMING  ");               
		sqlStr.append("	FROM  MOBILE_FUNC_CALL_RECORD    "); 		
		sqlStr.append("	WHERE STATE = 1  "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY SERVICE_ID,SERVICE_NAME,NRECEIVE_TIME,SSEND_TIME,SRECEIVE_TIME,NBACK_TIME  "); 
		sqlStr.append(" ) WHERE FIRSTTIMECONSUMING  !=0 and SECONDTIMECONSUMING !=0 and THIRDTIMECONSUMING !=0  GROUP BY SERVICE_ID,  SERVICE_NAME) "); 

		//System.out.println("qryAvgTimeConsuming="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	    	
       
	}
	
	public Map qryserverAskRank10(Map paramMap) throws SQLException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("  SELECT FIRSTTIMECONSUMING FROM ");  
		sqlStr.append("  (SELECT  (TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3') -TO_CHAR(NRECEIVE_TIME,'YYYYMMDDHH24MISSFF3')) AS FIRSTTIMECONSUMING ");  
		sqlStr.append("  FROM MOBILE_FUNC_CALL_RECORD  ");  
		sqlStr.append("  WHERE STATE = 1  ");       

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY SSEND_TIME ,NRECEIVE_TIME ORDER BY FIRSTTIMECONSUMING DESC ) WHERE FIRSTTIMECONSUMING IS NOT NULL AND ROWNUM <= 10   "); 
		
		//System.out.println("qryserverAskRank10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();
            Map result = new HashMap();
            int num = 1;
            while (rs.next()) {
            	
                if(num == 1){
                	result.put("ServerAsk1",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 2){
                	result.put("ServerAsk2",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 3){
                	result.put("ServerAsk3",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 4){
                	result.put("ServerAsk4",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 5){
                	result.put("ServerAsk5",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 6){
                	result.put("ServerAsk6",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 7){
                	result.put("ServerAsk7",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 8){
                	result.put("ServerAsk8",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 9){
                	result.put("ServerAsk9",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }else if(num == 10){
                	result.put("ServerAsk10",rs.getString("FIRSTTIMECONSUMING")==null?"":rs.getString("FIRSTTIMECONSUMING"));
                }
                num++;
                
            }
            resultList.add(result);
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
	}
	
	public Map qryInterAskRank10(Map paramMap) throws SQLException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("  SELECT SECONDTIMECONSUMING FROM ");  
		sqlStr.append("  (SELECT  (TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SSEND_TIME,'YYYYMMDDHH24MISSFF3')) SECONDTIMECONSUMING ");  
		sqlStr.append("  FROM MOBILE_FUNC_CALL_RECORD  ");  
		sqlStr.append("  WHERE STATE = 1  ");       

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY SRECEIVE_TIME ,SSEND_TIME ORDER BY SECONDTIMECONSUMING DESC ) WHERE SECONDTIMECONSUMING IS NOT NULL AND ROWNUM <= 10   "); 
		
		//System.out.println("qryInterAskRank10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();
            Map result = new HashMap();
            int num = 1;
            while (rs.next()) {
            	
                if(num == 1){
                	result.put("interAsk1",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 2){
                	result.put("interAsk2",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 3){
                	result.put("interAsk3",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 4){
                	result.put("interAsk4",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 5){
                	result.put("interAsk5",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 6){
                	result.put("interAsk6",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 7){
                	result.put("interAsk7",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 8){
                	result.put("interAsk8",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 9){
                	result.put("interAsk9",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }else if(num == 10){
                	result.put("interAsk10",rs.getString("SECONDTIMECONSUMING")==null?"":rs.getString("SECONDTIMECONSUMING"));
                }
                num++;
                
            }
            resultList.add(result);
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
	}
	
	public Map qryServerRespondRank10(Map paramMap) throws SQLException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("  SELECT THIRDTIMECONSUMING FROM ");  
		sqlStr.append("  (SELECT (TO_CHAR(NBACK_TIME,'YYYYMMDDHH24MISSFF3') - TO_CHAR(SRECEIVE_TIME,'YYYYMMDDHH24MISSFF3')) THIRDTIMECONSUMING ");  
		sqlStr.append("  FROM MOBILE_FUNC_CALL_RECORD  ");  
		sqlStr.append("  WHERE STATE = 1  ");       

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY NBACK_TIME ,SRECEIVE_TIME ORDER BY THIRDTIMECONSUMING DESC ) WHERE THIRDTIMECONSUMING IS NOT NULL AND  ROWNUM <= 10   "); 
		
		//System.out.println("qryServerRespondRank10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();

            int num = 1;
            Map result = new HashMap();
            while (rs.next()) {
            	
                if(num == 1){
                	result.put("serverRespond1",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 2){
                	result.put("serverRespond2",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 3){
                	result.put("serverRespond3",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 4){
                	result.put("serverRespond4",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 5){
                	result.put("serverRespond5",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 6){
                	result.put("serverRespond6",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 7){
                	result.put("serverRespond7",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 8){
                	result.put("serverRespond8",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 9){
                	result.put("serverRespond9",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }else if(num == 10){
                	result.put("serverRespond10",rs.getString("THIRDTIMECONSUMING")==null?"":rs.getString("THIRDTIMECONSUMING"));
                }
                num++;
                
            }
            resultList.add(result);
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
	}
	public Map qrySuccessRate(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT SERVICE_ID as serviceId  ,SERVICE_NAME as serviceName, SUCCESSRATE as successRate,SUCCESSTIMES as successTimes,FAULTTIMES as faultTimes FROM ");  
		sqlStr.append(" (SELECT SERVICE_ID,SERVICE_NAME,  SUM(DECODE(IS_SUCCESS,'1',1,0)) as SUCCESSTIMES ,SUM(DECODE(IS_SUCCESS,'1',0,1)) as FAULTTIMES ,");  
		sqlStr.append("  TRIM(TO_CHAR( (SUM(DECODE(IS_SUCCESS,'1',1,0)))/((SUM(DECODE(IS_SUCCESS,'1',1,0)))+(SUM(DECODE(IS_SUCCESS,'1',0,1)))),'0.9999'))*100 as SUCCESSRATE ");  
		sqlStr.append("  FROM  MOBILE_FUNC_CALL_RECORD");       	
		sqlStr.append("	 WHERE STATE = 1  "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
//		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
//			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
//		}
//		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
//			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
//		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY  SERVICE_ID,SERVICE_NAME ) "); 


		System.out.println("qrySuccessRate="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	    	
	}
	
	public Map qrySuccessRateCount10(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT SERVICE_ID  ,SERVICE_NAME, SUCCESSRATE FROM ");  
		sqlStr.append(" (SELECT SERVICE_ID,SERVICE_NAME,  ");  
		sqlStr.append("  TRIM(TO_CHAR( (SUM(DECODE(IS_SUCCESS,'1',1,0)))/((SUM(DECODE(IS_SUCCESS,'1',1,0)))+(SUM(DECODE(IS_SUCCESS,'1',0,1)))),'0.9999'))*100 SUCCESSRATE ");  
		sqlStr.append("  FROM  MOBILE_FUNC_CALL_RECORD      ");       	
		sqlStr.append("	 WHERE STATE = 1  "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
//		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
//			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
//		}
//		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
//			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
//		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		sqlStr.append(" GROUP BY  SERVICE_ID,SERVICE_NAME ORDER BY SUCCESSRATE DESC)  WHERE ROWNUM <= 10  "); 


		//System.out.println("qrySuccessRateCount10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();
            int num = 1;
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("successRank", num);
                result.put("serviceId", rs.getString("SERVICE_ID"));
                result.put("serviceName",rs.getString("SERVICE_NAME"));
                result.put("successRate", rs.getString("SUCCESSRATE"));
                num++; 
                resultList.add(result);
                
            }
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
		
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}

	public Map qryFlow(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select mrsl.REST_SERV_LOG_ID AS restServLogId, "); 
		sqlStr.append("        mrsl.REST_SERVICE_ID  AS restServiceId, "); 
		sqlStr.append("        mrsl.SERV_LOG_TYPE    AS servLogType, "); 
		sqlStr.append("        mrsl.IN_TIMESTAMP     AS inTimestamp, "); 
		sqlStr.append("        mrsl.OUT_TIMESTAMP    AS outTimestamp, "); 
//		sqlStr.append("        mrsl.IN_CONTENT       AS inContent, "); 
//		sqlStr.append("        mrsl.OUT_CONTENT      AS outContent, "); 
		sqlStr.append("        mrsl.IN_SIZE          AS inSize, "); 
		sqlStr.append("        mrsl.OUT_SIZE         AS outSize, "); 
		sqlStr.append("        mrsl.STAFF_ID         AS staffId, "); 
		sqlStr.append("        us.staff_name         AS staffName, "); 
		sqlStr.append("        us.username           AS username, "); 
		sqlStr.append("        mrs.serv_name         AS servName, "); 
		sqlStr.append("        mrs.serv_addr         AS servAddr "); 
		sqlStr.append("   from mobile_rest_serv_log mrsl, uos_staff us, mobile_rest_service mrs "); 
		sqlStr.append("  where mrsl.staff_id = us.staff_id "); 
		sqlStr.append("    and mrsl.rest_service_id = mrs.rest_service_id "); 
		sqlStr.append("    and mrs.state = 1 "); 
		
//		sqlStr.append(" SELECT SERVICE_ID as serviceId,SERVICE_NAME as serviceName,STAFF_NAME as staffName,USERNAME as username,IN_MESSAGE_SIZE as inMessageSize,OUT_MESSAGE_SIZE as outMessageSize,CREATE_TIME as createTime FROM ");  		
//		sqlStr.append(" MOBILE_FUNC_CALL_RECORD    "); 		
//		sqlStr.append("	WHERE STATE = 1  AND  IN_MESSAGE_SIZE IS NOT NULL AND OUT_MESSAGE_SIZE IS NOT NULL "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND mrsl.REST_SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND mrsl.IN_TIMESTAMP >= ").append(MapUtils.getString(paramMap, "beginDate")).append(" ");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND mrsl.OUT_TIMESTAMP <= ").append(MapUtils.getString(paramMap, "endDate")).append(" ");
        }
		
		sqlStr.append(" ORDER BY mrsl.OUT_TIMESTAMP DESC  "); 
		
//		logger.debug("=======sqlStr: "+sqlStr.toString());
		System.out.println("qryFlow="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	    	
	}
	public Map qryAvgFlow(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select restServiceId, avgInSize, avgOutSize, staffId, username, servName from "); 
		sqlStr.append(" ( select  mrsl.REST_SERVICE_ID  AS restServiceId, "); 
		sqlStr.append("        TRIM(to_char(AVG (mrsl.IN_SIZE),'99999999999999.99'))       AS avgInSize, "); 
		sqlStr.append("        TRIM(to_char(AVG (mrsl.OUT_SIZE),'99999999999999.99'))       AS avgOutSize,  "); 
		sqlStr.append("        mrsl.STAFF_ID         AS staffId, "); 
		sqlStr.append("        us.username           AS username, "); 
		sqlStr.append("        mrs.serv_name         AS servName "); 
		sqlStr.append("   from mobile_rest_serv_log mrsl, uos_staff us, mobile_rest_service mrs "); 
		sqlStr.append("  where mrsl.staff_id = us.staff_id "); 
		sqlStr.append("    and mrsl.rest_service_id = mrs.rest_service_id "); 
		sqlStr.append("    and mrs.state = 1 "); 
//		sqlStr.append(" SELECT serviceId, serviceName, staffName,username,avgInMessageSize,avgOutMessageSize  FROM  ");
//		sqlStr.append(" (SELECT SERVICE_ID as serviceId,SERVICE_NAME as serviceName,STAFF_NAME as staffName,USERNAME as username,TRIM(to_char(AVG (IN_MESSAGE_SIZE),'99999999999999.99')) as avgInMessageSize,TRIM(to_char(AVG (OUT_MESSAGE_SIZE),'99999999999999.99')) as avgOutMessageSize");  		
//		sqlStr.append(" FROM MOBILE_FUNC_CALL_RECORD    "); 		
//		sqlStr.append("	WHERE STATE = 1 AND  IN_MESSAGE_SIZE IS NOT NULL AND OUT_MESSAGE_SIZE IS NOT NULL "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND mrsl.REST_SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND mrsl.IN_TIMESTAMP >= ").append(MapUtils.getString(paramMap, "beginDate")).append(" ");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND mrsl.OUT_TIMESTAMP <= ").append(MapUtils.getString(paramMap, "endDate")).append(" ");
        }
		
		sqlStr.append(" GROUP BY mrsl.REST_SERVICE_ID, mrsl.STAFF_ID, us.username, mrs.serv_name ) "); 

//		logger.debug("=======sqlStr: "+sqlStr.toString());
		System.out.println("qryAvgFlow="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	    	
	}
	
	public Map qryAskFlow10(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("  SELECT IN_MESSAGE_SIZE FROM ");  
		sqlStr.append("  (SELECT IN_SIZE AS IN_MESSAGE_SIZE ");  
		sqlStr.append("  FROM MOBILE_REST_SERV_LOG mrsl ");  
		sqlStr.append("  WHERE 1 = 1  ");       

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND mrsl.REST_SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
//		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
//			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
//		}
//		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
//			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
//		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND mrsl.IN_TIMESTAMP >= ").append(MapUtils.getString(paramMap, "beginDate")).append(" ");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND mrsl.OUT_TIMESTAMP <= ").append(MapUtils.getString(paramMap, "endDate")).append(" ");
        }
		
		sqlStr.append(" ORDER BY IN_SIZE DESC ) WHERE IN_MESSAGE_SIZE IS NOT NULL AND  ROWNUM <= 10   "); 
		
//		logger.debug("=============qryAskFlow10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();

            int num = 1;
            Map result = new HashMap();
            while (rs.next()) {
            	
                if(num == 1){
                	result.put("ServerAsk1",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 2){
                	result.put("ServerAsk2",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 3){
                	result.put("ServerAsk3",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 4){
                	result.put("ServerAsk4",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 5){
                	result.put("ServerAsk5",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 6){
                	result.put("ServerAsk6",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 7){
                	result.put("ServerAsk7",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 8){
                	result.put("ServerAsk8",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 9){
                	result.put("ServerAsk9",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }else if(num == 10){
                	result.put("ServerAsk10",rs.getString("IN_MESSAGE_SIZE")==null?"":rs.getString("IN_MESSAGE_SIZE"));
                }
                num++;
                
            }
            resultList.add(result);
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
	}
	public Map qryRespondFlow10(Map paramMap) throws SQLException{
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("  SELECT OUT_MESSAGE_SIZE FROM ");  
		sqlStr.append("  (SELECT OUT_SIZE AS OUT_MESSAGE_SIZE ");  
		sqlStr.append("  FROM MOBILE_REST_SERV_LOG mrsl ");  
		sqlStr.append("  WHERE 1 = 1  ");       

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND mrsl.REST_SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
//		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
//			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
//		}
//		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
//			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
//		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND mrsl.IN_TIMESTAMP >= ").append(MapUtils.getString(paramMap, "beginDate")).append(" ");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND mrsl.OUT_TIMESTAMP <= ").append(MapUtils.getString(paramMap, "endDate")).append(" ");
        }
		
		sqlStr.append(" ORDER BY OUT_SIZE DESC ) WHERE OUT_MESSAGE_SIZE IS NOT NULL AND ROWNUM <= 10   "); 
		
//		logger.debug("=======qryRespondFlow10="+sqlStr.toString());
		    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultMap = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();

            psmt = conn.prepareStatement(sqlStr.toString());

            rs = psmt.executeQuery();

            int num = 1;
            Map result = new HashMap();
            while (rs.next()) {
            	
                if(num == 1){
                	result.put("serverRespond1",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 2){
                	result.put("serverRespond2",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 3){
                	result.put("serverRespond3",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 4){
                	result.put("serverRespond4",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 5){
                	result.put("serverRespond5",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 6){
                	result.put("serverRespond6",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 7){
                	result.put("serverRespond7",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 8){
                	result.put("serverRespond8",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 9){
                	result.put("serverRespond9",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }else if(num == 10){
                	result.put("serverRespond10",rs.getString("OUT_MESSAGE_SIZE")==null?"":rs.getString("OUT_MESSAGE_SIZE"));
                }
                num++;
                
            }
            resultList.add(result);
            resultMap.put("resultList",resultList);
            return resultMap ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
	}
	
	public Map qryTotalFlow(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT AA.SERVICE_ID AS serviceId,AA.USERNAME AS username,BB.SERVICE_NAME as serviceName , AA.TOTAL_IN_MESSAGE_SIZE as totalInMessageSize , ");
		sqlStr.append(" AA.TOTAL_OUT_MESSAGE_SIZE as totalOutMessageSize,BB.IN_MESSAGE_SIZE as inMessageSize,BB.OUT_MESSAGE_SIZE as outMessageSize FROM ");  		
		sqlStr.append(" (SELECT SERVICE_ID ,USERNAME, MAX(ID) AS ID, SUM(IN_MESSAGE_SIZE) AS TOTAL_IN_MESSAGE_SIZE,SUM (OUT_MESSAGE_SIZE) AS TOTAL_OUT_MESSAGE_SIZE "); 		
		sqlStr.append(" FROM  MOBILE_FUNC_CALL_RECORD WHERE STATE = 1 AND  IN_MESSAGE_SIZE IS NOT NULL AND OUT_MESSAGE_SIZE IS NOT NULL "); 


		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND mrsl.REST_SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND mrsl.IN_TIMESTAMP >= ").append(MapUtils.getString(paramMap, "beginDate")).append(" ");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND mrsl.OUT_TIMESTAMP <= ").append(MapUtils.getString(paramMap, "endDate")).append(" ");
        }
		
//		if (MapUtils.getString(paramMap, "beginMonth") != null && !MapUtils.getString(paramMap, "beginMonth").equals("") ) {
//            sqlStr.append("  AND cast(CREATE_TIME as date) = TO_DATE('").append(MapUtils.getString(paramMap, "beginMonth")).append("','YYYY-MM')");
//        }

		
		sqlStr.append(" GROUP BY SERVICE_ID,USERNAME) AA ,MOBILE_FUNC_CALL_RECORD BB WHERE  AA.ID = BB.ID  ORDER BY AA.SERVICE_ID "); 
		
//		logger.debug("==========qryTotalFlow: "+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	    	    	
		
	}
}

