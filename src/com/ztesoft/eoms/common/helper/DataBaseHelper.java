package com.ztesoft.eoms.common.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ztesoft.eoms.common.dao.BaseDAOImpl;

public class DataBaseHelper extends BaseDAOImpl{
    private static Logger logger = Logger.getLogger(DataBaseHelper.class.getName());

    public DataBaseHelper() {
    }
    
    /**
     * 获取数据库服务器时间
     * @return Date
     * @throws SQLException 
     */
	public Date getDataBaseDate() throws SQLException
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
			//获取数据库时间失败，返回应用服务器的时间，不抛出异常
			e.printStackTrace();
			logger.error(e.getMessage());
			return new Date();
		}
        finally 
        {
            cleanUp(conn, null, ps, rs);
        }
	}
	
	/**
     * 获取数据库服务器时间的当前月份
     * @return int
     * @throws SQLException 
     */
	public int getDataBaseCurrentMonth() throws SQLException
	{
		int currentMonth = 0;
		Date date = getDataBaseDate();
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentMonth = Integer.valueOf(tf.format(date).substring(5,7));
		return currentMonth;
	}
	
	/**
     * 获取数据库服务器时间的当前年份
     * @return int
     * @throws SQLException 
     */
	public int getDataBaseCurrentYear() throws SQLException
	{
		int currentYear = 0;
		Date date = getDataBaseDate();
		SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentYear = Integer.valueOf(tf.format(date).substring(0,4));
		return currentYear;
	}
	
	/*public static void main(String[] args) throws SQLException{
		DataBaseHelper test = new DataBaseHelper();
		
		System.out.println("获取当前年份："+test.getDataBaseCurrentYear());
		System.out.println("获取当前月份："+test.getDataBaseCurrentMonth());
	}*/
}