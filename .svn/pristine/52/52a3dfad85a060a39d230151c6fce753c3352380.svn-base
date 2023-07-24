package com.ztesoft.mobile.v2.dao.interf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileStaffInfoDAOImpl extends BaseDAOImpl implements
		MobileStaffInfoDAO {
	private static final Logger logger = Logger.getLogger(MobileStaffInfoDAOImpl.class);
	private static final String TABLE_NAME = "MOBILE_STAFF_LOGIN_TRACK";
	public void insert(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String patternStr = "LOGIN_TRACK_ID:ID,STAFF_ID:staffId,ORG_ID:orgId";
		logger.info("CHNELIN INSERT LOGIN MSG33333333333333");
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("ID", nextId);
		logger.info("CHNELIN INSERT LOGIN MSG44444444444444");
		System.out.println("CHENLIN paramMap = "+ paramMap);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		
		
		/*
		final String sqlStr =
	            "insert into mobile_staff_login_track(LOGIN_TRACK_ID,STAFF_ID,ORG_ID,STATUS_DATE) " +
	            "values("+ nextId + ","+ MapUtils.getString(paramMap, "staffId")+","+ MapUtils.getString(paramMap, "orgId")+",sysdate)";
	        Connection conn = null;
	        PreparedStatement ps = null;
	        
	        logger.info("CHNELIN INSERT LOGIN MSG5______________" +sqlStr);
	       // int dbloop = 1;
	        try {
	            conn = getConnection();

	            conn.setAutoCommit(false);
	            ps = conn.prepareStatement(sqlStr);

	            ps.executeUpdate();	      

	            conn.commit();
	        }catch (Exception e) {
	        	e.printStackTrace();
	        	try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	        finally {
	            cleanUp(conn, null, ps, null);
	        }*/
	        logger.info("CHNELIN INSERT LOGIN MSG5555555555555555");
	}

	public Map selByStaffId(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String sqlStr = "select STAFF_ID as staffId, ORG_ID as orgId from VW_STAFF_QUERY where username = '" + MapUtils.getString(paramMap, "userName") + "'";
		System.out.println("CHENLIN select sql is " + sqlStr);
		return dynamicQueryObjectByMap(sqlStr, paramMap, null);  
	}
	

}
