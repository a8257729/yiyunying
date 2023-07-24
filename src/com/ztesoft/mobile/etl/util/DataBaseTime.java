package com.ztesoft.mobile.etl.util;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;

public class DataBaseTime extends BaseDAOImpl {
	 /**
     * 获取数据库服务器时间
     * @return Date
     * @throws SQLException 
     */
	public  Date getDataBaseDate() throws SQLException
	{
    	String sql = "SELECT SYSDATE FROM DUAL";
		
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try 
        {
            conn = getConnection();
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            if (rs.next()) 
            	return tf.parse(rs.getString(1));
            else
            	return null;
        } 
        catch (ParseException e)
		{
			e.printStackTrace();
			return new Date();
		}
        finally 
        {
            cleanUp(conn, null, ps, rs);
        }
	}
}
