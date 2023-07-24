package com.ztesoft.mobile.v2.dao.weixin;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;

import net.sf.json.JSONObject;

import oracle.jdbc.internal.OracleTypes;

public class WeChatDAOImpl extends BaseDAOImpl implements WeChatDAO {

	public Map queryUserPhoneInf (String qrcode) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select cust_phone from house_code_inf where qrcode = ? or qry_uid = ? ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, qrcode);
			ps.setString(2, qrcode);
			rs = ps.executeQuery();
			if (rs.next()) {
				Map resultMap = new HashMap();
				String cust_phone = rs.getString("cust_phone");
				String substring = cust_phone.substring(0,cust_phone.length() - 4);
				resultMap.put("cust_phone",substring);
				return resultMap;
			}
			return null;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}
	public Map queryUserHouseInfoByCode(String qrcode) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select service_num,qrcode,staff_id,staff_name,staff_phone,cust_name,cust_phone,order_time,install_addr,insert_time,update_time,area_id from house_code_inf where qrcode = ? or qry_uid = ? ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, qrcode);
			ps.setString(2, qrcode);
			rs = ps.executeQuery();
			if (rs.next()) {
				Map resultMap = new HashMap();
				SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date date;
				java.util.Date date1;
				java.util.Date date2;
				if(rs.getString("order_time")==null){
					resultMap.put("order_time","");
				}else {
					date = simpleDateFormat.parse(String.valueOf(rs.getString("order_time")));
					resultMap.put("order_time",date);
				}
				if(rs.getString("insert_time")==null){
					resultMap.put("insert_time","");
				}else {
					date1 = simpleDateFormat.parse(String.valueOf(rs.getString("insert_time")));
					resultMap.put("insert_time",date1);
				}
				if(rs.getString("update_time")==null){
					resultMap.put("insert_time","");
				}else {
					date2 = simpleDateFormat.parse(String.valueOf(rs.getString("update_time")));
					resultMap.put("update_time",date2);
				}
				resultMap.put("service_num",rs.getObject("service_num"));
				resultMap.put("qrcode",rs.getObject("qrcode")==null?"":rs.getObject("qrcode"));
				resultMap.put("staff_id",rs.getObject("staff_id")==null?"":rs.getObject("staff_id"));
				resultMap.put("staff_name",rs.getObject("staff_name")==null?"":rs.getObject("staff_name"));
				resultMap.put("staff_phone",rs.getObject("staff_phone")==null?"":rs.getObject("staff_phone"));
				resultMap.put("cust_name",rs.getObject("cust_name")==null?"":rs.getObject("cust_name"));
				resultMap.put("cust_phone",rs.getObject("cust_phone")==null?"":rs.getObject("cust_phone"));
				resultMap.put("install_addr",rs.getObject("install_addr")==null?"":rs.getObject("install_addr"));
				resultMap.put("area_id",rs.getString("area_id")==null?"":rs.getObject("area_id"));
				return resultMap;
			}
			return null;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
}


	public int insertMap(String log_id,String service_num,String qrcode,String custname) throws Exception {
		int record;
		String insetSql = "INSERT INTO user_house_log(LOG_ID,SERVICE_NUM,QRCODE,CUST_NAME,CREATE_TIME) VALUES(?,?,?,?,sysdate)";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(insetSql);
			ps.setString(1, log_id);
			ps.setString(2, service_num);
			ps.setString(3, qrcode);
			ps.setString(4, custname);
			record = ps.executeUpdate();
			return record;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}
	//查询orderID
	public String queryorderId(String serviceNum) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT aa.id FROM \n" +
				"(SELECT ooki.id,\n" +
				"row_number() over (partition by ooki.acc_nbr  order by  ooki.create_time  desc) row_n\n" +
				"FROM om_order_key_info ooki \n" +
				"left join uos_area ua on ua.area_id = ooki.area_id\n" +
				"where ooki.acc_nbr =  substr(?,5)\n" +
				"and ua.acronym =   substr(?,1,4)\n" +
				"and ooki.evt_with_prod_id = '9')aa \n" +
				"where aa.row_n = 1");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println(sb.toString());
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, serviceNum);
			ps.setString(2, serviceNum);
			rs = ps.executeQuery();
			if (rs.next()) {
				int dbloop = 1;
				String ooid = String.valueOf(rs.getString("ID"));
				return ooid;
			}
			return null;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}



	public String createDate(String ooid) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT oo.create_date FROM om_order oo where oo.id = ?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, ooid);
			rs = ps.executeQuery();
			if (rs.next()) {
				int dbloop = 1;
				String createDate = String.valueOf(rs.getString("CREATE_DATE"));
				return createDate;
			}
			return null;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}

	public String guangshuai(String ooid) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT h.accept_power FROM guangshuai_user_state h where h.order_id=?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, ooid);
			rs = ps.executeQuery();
			if (rs.next()) {
				int dbloop = 1;
				String acceptPower = String.valueOf(rs.getString("ACCEPT_POWER"));
				return acceptPower;
			}
			return null;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}

	public String maxnetspeed(String ooid, String serviceNum) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.maxnetspeed FROM usernet_cesu u \n" +
				"left join om_order oo on oo.id = ?\n" +
				"where u.createdate > oo.create_date\n" +
				"and u.username = ?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		conn = getConnection();
		ps = conn.prepareStatement(sb.toString());
		ps.setString(1, ooid);
		ps.setString(2, serviceNum);
		rs = ps.executeQuery();
		if (rs.next()) {
		int dbloop = 1;
		String maxnetspeed = String.valueOf(rs.getString("MAXNETSPEED"));
		return maxnetspeed;
		}
		return null;
		} finally {
		cleanUp(conn, null, ps, rs);
		}
	}
	//查故障单
	public String queryWorkOrderInfoSA(String service_num) throws Exception
	{
		String result =  "";
		Connection conn = null;
		CallableStatement callStmt = null;
		CallableStatement callStmt1 = null;
		try {
			callStmt = null;
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call hnlt_gk.F_orderQueryPlan_for_zwt(?,?)}");
			callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			callStmt.setString(2, service_num);
			callStmt.setString(3, "1SA");
			callStmt.execute();
			result=callStmt.getString(1);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			if (null != callStmt) {
				callStmt.close();
			}
			if (null != conn) {
				conn.close();
			}
		}
	}
	//查开通单
	public String queryWorkOrderInfo(String service_num) throws Exception
	{
		String result =  "";
		Connection conn = null;
	    CallableStatement callStmt = null;
		CallableStatement callStmt1 = null;
		try {
			callStmt = null;
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call hnlt_gk.F_orderQueryPlan_for_zwt(?,?)}");
			callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			callStmt.setString(2, service_num);
			callStmt.setString(3, "10S");
			callStmt.execute();
			result=callStmt.getString(1);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			if (null != callStmt) {  
				callStmt.close();  
			}  
			if (null != conn) {  
				conn.close();  
			}  
		} 
	}

	
	
	
	
	
}
