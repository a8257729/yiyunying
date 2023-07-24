package com.ztesoft.mobile.v2.dao.resource;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lowagie.text.J;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;

import oracle.jdbc.internal.OracleTypes;
import org.apache.shiro.util.StringUtils;

public class ResourceDAOImpl extends BaseDAOImpl implements ResourceDAO {

	private static final Logger logger = Logger.getLogger(ResourceDAOImpl.class);

	public Map<String, Object> getResourceInfoByQrcode(String qrCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			logger.info("<<<qrCode>>>:" + qrCode);

			callStmt = conn.prepareCall("{call Sf_Get_eqpdetail_by_code2(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.setString(1, qrCode);

			callStmt.registerOutParameter(2, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(11, OracleTypes.NUMBER);
			callStmt.registerOutParameter(12, OracleTypes.NUMBER);
			callStmt.registerOutParameter(13, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(14, OracleTypes.VARCHAR);

			callStmt.execute();
			String splitterName = callStmt.getString(2);// 分光器名称
			String eqpId = callStmt.getString(3);
			String workMode = callStmt.getString(4);// 分光比
			String protectStyle = callStmt.getString(5);// 分光级别
			String oltIP = callStmt.getString(6);// oltIp
			String oltName = callStmt.getString(7);// olt名称
			String pon = callStmt.getString(8);// pon口
			String ponId = callStmt.getString(9);// pon口id
			String posX = "";
			String posY = "";
			if (callStmt.getBigDecimal(11) != null) {
				posX = callStmt.getBigDecimal(11).toString();// 经度
			}
			if (callStmt.getBigDecimal(12) != null) {
				posY = callStmt.getBigDecimal(12).toString();// 维度
			}

			String outAddress = callStmt.getString(13);// 安放地址
			String outStandName = callStmt.getString(14);//标准地址
			
			resultMap.put("splitterName", splitterName);
			resultMap.put("oltIP", oltIP);
			resultMap.put("oltName", oltName);
			resultMap.put("pon", pon);
			resultMap.put("eqpId", eqpId);
			resultMap.put("ponId", ponId);
			resultMap.put("qrCode", qrCode);
			resultMap.put("workMode", workMode);
			resultMap.put("protectStyle", protectStyle);
			resultMap.put("posX", posX);
			resultMap.put("posY", posY);
			resultMap.put("outAddress", outAddress);
			resultMap.put("outStandName", outStandName);

			// 根据二维码查询分光器端口占用列表
			StringBuffer sbf = new StringBuffer();
			sbf.append(" select c.port_id as portId,                                        \n");
			sbf.append("        c.position as position,                                     \n");
			sbf.append("        d.loid as serviceNum,                                       \n");
			sbf.append("        d.cust_name as custName,                                   \n");
			sbf.append("        SF_GET_ADDRESS_BY_RES_ID(C.PORT_ID) as custAddr,           \n");
			sbf.append("        SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState             \n");
			sbf.append("   from pub_two_dimension_code a                                    \n");
			sbf.append("   left join rme_eqp b on a.res_id = b.eqp_id                       \n");
			sbf.append("                      and b.res_type_id = '2530'                    \n");
			sbf.append("                      and b.delete_state = '0'                      \n");
			sbf.append("   left join rme_port c on c.delete_state = '0'                     \n");
			sbf.append("                       and b.eqp_id = c.super_res_id                \n");
			sbf.append("   left join ZYQC_ftth_user_2019 d on c.port_id = d.assign_port_id  \n");
			sbf.append("                                                                    \n");
			sbf.append("  where a.digcode = '" + qrCode + "'                                 \n");
			sbf.append("    and a.delete_state = '0'  order by position                  \n");

			List ponPortList = null;
			try {
				logger.info("查询分光器端口信息: \n" + sbf.toString());
				ponPortList = dynamicQueryListByMap(sbf.toString(), null, null);
			} catch (DataAccessException e) {
				logger.error("查询分光器端口信息失败，qrCode：" + qrCode + "。" + e.getMessage());
			}
			resultMap.put("ponPortList", ponPortList);
		} catch (SQLException e) {
			resultMap.put("flag_desc", "根据qrCode:" + qrCode + "查询资源信息失败!");
			logger.error("根据qrCode:" + qrCode + "查询资源信息失败!");
			logger.error(e.getMessage());
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

//	public Map<String, Object> queryUserInfo(String portId, String qrCode) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Map<String, Object> map = null;
//		logger.info("<<<portId>>>:" + portId);
//		logger.info("<<<qrCode>>>:" + qrCode);
//		StringBuffer sbf = new StringBuffer();
//		if (!StringUtil.isNull(portId)) {
//			sbf.append(" select                                                  \n");
//			sbf.append("     c.port_id as portId,                                \n");
//			sbf.append("     e.cust_name as custName,                            \n");
//			sbf.append("     SF_GET_ADDRESS_BY_RES_ID(C.PORT_ID) as custAddr,    \n");
//			sbf.append("     e.tele_no as teleNo,                                \n");
//			sbf.append("     e.loid as serviceNum,                               \n");
//			sbf.append("     b.eqp_no as eqpNo,                                  \n");
//			sbf.append("     c.position as position,                             \n");
//			sbf.append("     d.digcode as qrCode,                                \n");
//			sbf.append("     SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState,     \n");
//			sbf.append("     sf_get_onuobd_up_olt_rma(b.eqp_id) as oltIP,        \n");
//			sbf.append("     sf_get_onuobd_up_olt_name(b.eqp_id) as oltName,    \n");
//			sbf.append("     Sf_Get_Port_Id(b.node_id) as pon   \n");
//			sbf.append("      from pub_two_dimension_code a,                     \n");
//			sbf.append("           rme_eqp                b,                     \n");
//			sbf.append("           rme_port               c,                     \n");
//			sbf.append("           pub_two_dimension_code d,                     \n");
//			sbf.append("           ZYQC_ftth_user_2019    e                      \n");
//			sbf.append("     where                                               \n");
//			sbf.append("     c.port_id = '" + portId + "'\n");
//			sbf.append("     and a.delete_state = '0'                            \n");
//			sbf.append("     and a.res_id = b.eqp_id                             \n");
//			sbf.append("     and c.delete_state = '0'                            \n");
//			sbf.append("     and c.port_id = d.res_id                            \n");
//			sbf.append("     and d.res_type_id = '6024'                          \n");
//			sbf.append("     and c.super_res_id = b.eqp_id                       \n");
//			sbf.append("     and b.res_type_id = '2530'                          \n");
//			sbf.append("     and c.port_id = e.assign_port_id                    \n");
//		} else if (!StringUtil.isNull(qrCode)) {
//			sbf.append("  select                                               \n");
//			sbf.append("  c.port_id as portId,                                 \n");
//			sbf.append("  e.cust_name as custName,                             \n");
//			sbf.append("  SF_GET_ADDRESS_BY_RES_ID(C.PORT_ID) as custAddr,     \n");
//			sbf.append("  e.tele_no as teleNo,                                 \n");
//			sbf.append("  e.loid as serviceNum,                                \n");
//			sbf.append("  b.eqp_no as eqpNo,                                   \n");
//			sbf.append("  c.position as position,                              \n");
//			sbf.append("  d.digcode as qrCode,                                 \n");
//			sbf.append("  sf_get_onuobd_up_olt_rma(b.eqp_id) as oltIP,         \n");
//			sbf.append("  sf_get_onuobd_up_olt_name(b.eqp_id) as oltName,      \n");
//			sbf.append("  Sf_Get_Port_Id(b.node_id) as pon,                    \n");
//			sbf.append("  SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState      \n");
//			sbf.append("   from pub_two_dimension_code a,                      \n");
//			sbf.append("        rme_eqp                b,                      \n");
//			sbf.append("        rme_port               c,                      \n");
//			sbf.append("        pub_two_dimension_code d,                      \n");
//			sbf.append("        ZYQC_ftth_user_2019    e                       \n");
//			sbf.append("  where                                                \n");
//			sbf.append("  d.digcode = '" + qrCode + "'                         \n");
//			sbf.append("  and a.delete_state = '0'                             \n");
//			sbf.append("  and a.res_id = b.eqp_id                              \n");
//			sbf.append("  and c.delete_state = '0'                             \n");
//			sbf.append("  and c.port_id = d.res_id                             \n");
//			sbf.append("  and d.res_type_id = '6024'                           \n");
//			sbf.append("  and c.super_res_id = b.eqp_id                        \n");
//			sbf.append("  and b.res_type_id = '2530'                           \n");
//			sbf.append("  and c.port_id = e.assign_port_id                     \n");
//		}
//
//		try {
//			logger.info("查询端口用戶信息：\n" + sbf.toString());
//			map = dynamicQueryObjectByMap(sbf.toString(), null, null);
//		} catch (DataAccessException e) {
//			logger.error("查询端口用戶信息失败，portId：" + portId + ",qrCode:" + qrCode + "。" + e.getMessage());
//		}
//
//		return map;
//
//	}

	public Map<String, Object> queryUserInfo(String portId, String qrCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("<<<portId>>>:" + portId);
		logger.info("<<<qrCode>>>:" + qrCode);
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (!StringUtil.isNull(portId)) {
			sbf.append(" select                                                  \n");
			sbf.append("     c.port_id as portId,                                \n");
			sbf.append("     e.cust_name as custName,                            \n");
			sbf.append("     SF_GET_ADDRESS_BY_RES_ID(C.PORT_ID) as custAddr,    \n");
			sbf.append("     e.tele_no as teleNo,                                \n");
			sbf.append("     e.loid as serviceNum,                               \n");
			sbf.append("     b.eqp_no as eqpNo,                                  \n");
			sbf.append("     c.position as position,                             \n");
			sbf.append("     d.digcode as qrCode,                                \n");
			sbf.append("     SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState,     \n");
			sbf.append("     sf_get_onuobd_up_olt_rma(b.eqp_id) as oltIP,        \n");
			sbf.append("     sf_get_onuobd_up_olt_name(b.eqp_id) as oltName,    \n");
			sbf.append("     Sf_Get_Port_Id(b.node_id) as pon   \n");
			sbf.append("      from pub_two_dimension_code a,                     \n");
			sbf.append("           rme_eqp                b,                     \n");
			sbf.append("           rme_port               c,                     \n");
			sbf.append("           pub_two_dimension_code d,                     \n");
			sbf.append("           ZYQC_ftth_user_2019    e                      \n");
			sbf.append("     where                                               \n");
			sbf.append("     c.port_id =? \n");
			sbf.append("     and a.delete_state = '0'                            \n");
			sbf.append("     and a.res_id = b.eqp_id                             \n");
			sbf.append("     and c.delete_state = '0'                            \n");
			sbf.append("     and c.port_id = d.res_id                            \n");
			sbf.append("     and d.res_type_id = '6024'                          \n");
			sbf.append("     and c.super_res_id = b.eqp_id                       \n");
			sbf.append("     and b.res_type_id = '2530'                          \n");
			sbf.append("     and c.port_id = e.assign_port_id                    \n");
		} else if (!StringUtil.isNull(qrCode)) {
			sbf.append("  select                                               \n");
			sbf.append("  c.port_id as portId,                                 \n");
			sbf.append("  e.cust_name as custName,                             \n");
			sbf.append("  SF_GET_ADDRESS_BY_RES_ID(C.PORT_ID) as custAddr,     \n");
			sbf.append("  e.tele_no as teleNo,                                 \n");
			sbf.append("  e.loid as serviceNum,                                \n");
			sbf.append("  b.eqp_no as eqpNo,                                   \n");
			sbf.append("  c.position as position,                              \n");
			sbf.append("  d.digcode as qrCode,                                 \n");
			sbf.append("  sf_get_onuobd_up_olt_rma(b.eqp_id) as oltIP,         \n");
			sbf.append("  sf_get_onuobd_up_olt_name(b.eqp_id) as oltName,      \n");
			sbf.append("  Sf_Get_Port_Id(b.node_id) as pon,                    \n");
			sbf.append("  SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState      \n");
			sbf.append("   from pub_two_dimension_code a,                      \n");
			sbf.append("        rme_eqp                b,                      \n");
			sbf.append("        rme_port               c,                      \n");
			sbf.append("        pub_two_dimension_code d,                      \n");
			sbf.append("        ZYQC_ftth_user_2019    e                       \n");
			sbf.append("  where                                                \n");
			sbf.append("  d.digcode = ?                         \n");
			sbf.append("  and a.delete_state = '0'                             \n");
			sbf.append("  and a.res_id = b.eqp_id                              \n");
			sbf.append("  and c.delete_state = '0'                             \n");
			sbf.append("  and c.port_id = d.res_id                             \n");
			sbf.append("  and d.res_type_id = '6024'                           \n");
			sbf.append("  and c.super_res_id = b.eqp_id                        \n");
			sbf.append("  and b.res_type_id = '2530'                           \n");
			sbf.append("  and c.port_id = e.assign_port_id                     \n");
		}

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			if (!StringUtil.isNull(portId)) {
				ps.setString(1,portId);
			} else if (!StringUtil.isNull(qrCode)) {
				ps.setString(1,qrCode);
			}
			rs = ps.executeQuery();
			if (rs.next()) {

				map.put("portId",rs.getObject("portId"));
				map.put("custName",rs.getObject("custName"));
				map.put("custAddr",rs.getObject("custAddr"));
				map.put("teleNo",rs.getObject("teleNo"));
				map.put("serviceNum",rs.getObject("serviceNum"));
				map.put("eqpNo",rs.getObject("eqpNo"));

				map.put("position",rs.getObject("position"));
				map.put("qrCode",rs.getObject("qrCode"));
				map.put("portState",rs.getObject("portState"));
				map.put("oltIP",rs.getObject("oltIP"));
				map.put("oltName",rs.getObject("oltName"));
				map.put("pon",rs.getObject("pon"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询端口用戶信息失败，portId：" + portId + ",qrCode:" + qrCode + "。" + e.getMessage());
		} finally {
			cleanUp(conn, null, ps, rs);
		}

		return map;

	}

	public Map<String, Object> commonQueryObjectBySql(String sqlStr, Map paramMap, String wherePatternStr) {
		Map<String, Object> map = null;
		try {
			logger.info("查询sql：\n" + sqlStr);
			map = dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
		} catch (DataAccessException e) {
			logger.error("查询信息失败，paramMap：" + paramMap.toString() + "\n" + e.getMessage());
		}
		return map;
	}

	public Map<String, Object> commonQueryListBySql(String sqlStr, Map paramMap, String wherePatternStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		List dataList = null;
		try {
			logger.info("查询sql：\n" + sqlStr);
			dataList = dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
		} catch (DataAccessException e) {
			logger.error("查询信息失败，paramMap：" + paramMap.toString() + "\n" + e.getMessage());
		}
		map.put("dataList", dataList);
		return map;
	}

	/**
	 * 根据存储过程查询信息
	 * 
	 * @param procedureName
	 *            存储过程名字，带参数，如：Sf_Get_eqpdetail_by_code(?,?,?,?,?,?,?,?,?,?,?,?)
	 * @param paramList
	 * @param intParamLength 
	 * @return
	 */
	public Map<String, Object> commonQueryObjectByProcedure(String procedureName, List paramList, int intParamLength,String[] outParam) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			logger.info("<<call procedure>>:" + procedureName);
			logger.info("<<paramList>>:" + paramList.toString());

			callStmt = conn.prepareCall("{call "+procedureName + "}");
			if(paramList == null || paramList.size() == 0){
				return resultMap;
			}
			for (int i = 0; i < paramList.size(); i++) {
				if (i < intParamLength) {
					Class<? extends Object> paramClass = paramList.get(i).getClass();
					if (paramClass.isInstance(new String(""))) {
						callStmt.setString(i + 1, (String) paramList.get(i));
					} else if (paramClass.isInstance(new Integer(0))) {
						callStmt.setInt(i + 1, (Integer) paramList.get(i));
					} else if (paramClass.isInstance(new Double(0.0))) {
						callStmt.setDouble(i + 1, (Double) paramList.get(i));
					}else if(paramClass.isInstance(new BigDecimal(0))){
						callStmt.setBigDecimal(i+1, (BigDecimal) paramList.get(i));
					}
				} else {
					Class<? extends Object> paramClass = paramList.get(i).getClass();
					if (paramClass.isInstance(new String(""))) {
						callStmt.registerOutParameter(i + 1, OracleTypes.VARCHAR);
					}else if(paramClass.isInstance(new BigDecimal(1))){
						callStmt.registerOutParameter(i + 1, OracleTypes.NUMBER);
					}
				}
			}

			callStmt.execute();
            
			for (int j = 0; j < paramList.size() - intParamLength; j++) {
				Class<? extends Object> paramClass = paramList.get(j + intParamLength).getClass();
				if (paramClass.isInstance(new String(""))) {
					resultMap.put(outParam[j], callStmt.getString(j + 1 + intParamLength) == null ? ""
							: callStmt.getString(j + 1 + intParamLength));
				} else if (paramClass.isInstance(new BigDecimal(0))) {
					resultMap.put(outParam[j], callStmt.getBigDecimal(j + 1 + intParamLength) == null ? ""
							: callStmt.getBigDecimal(j + 1 + intParamLength));
				}
			}

		} catch (SQLException e) {
			logger.error("执行存储过程失败!" + procedureName);
			logger.error(e.getMessage());
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public void writePathToOrderEqp(String path, String orderId, String eqpId) {
		String sql = "update hnlt_gk.res_dispatch_order_eqp set photo_url = ? where order_id = ? and eqp_id= ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, path);
			ps.setString(2, orderId);
			ps.setString(3, eqpId);
			ps.executeUpdate();
			String sql2 = "select hnlt_gk.SF_GET_QC_flag(?)  from dual";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, eqpId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("入库失败!");
		} finally {
			cleanUp(conn, null, ps, rs);
		}



	}

	public String getResouceObdOrderList(String id) throws Exception {
		StringBuffer sb = new StringBuffer();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sb.append("select eqp_name,eqp_id from hnlt_gk.res_dispatch_order_eqp where order_id = ? and photo_url is null");
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1,id);
			rs = ps.executeQuery();
			while(rs.next()){
				JSONObject result = new JSONObject();
				result.put("eqpName",rs.getObject("eqp_name")==null?"":rs.getObject("eqp_name"));
				result.put("eqpId",rs.getObject("eqp_id")==null?"":rs.getObject("eqp_id"));
				jsonArray.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			jsonObject.put("result",jsonArray);
			cleanUp(connection,null,ps,rs);
		}
		return jsonObject.toString();
	}

	public String getResouceOrderList(String direName, String condition, String timer1, String timer2, Integer pageIndex, Integer pageSize, String staffId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		//如果查的为第一页，需要返回数量
		if(pageIndex==1) {
			StringBuffer sbf = new StringBuffer();
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			sbf.append("select count(*) from hnlt_gk.res_dispatch_order where staff_id = ?\n");
			if(StringUtils.hasText(direName)){
				sbf.append("and dire_name like ?");
			}
			sbf.append("and create_date BETWEEN to_date(?,'yyyy/mm/dd')  AND to_date(?,'yyyy/mm/dd')");
			if(StringUtils.hasText(condition)){
				sbf.append("and order_state = ? ");
			}
			sbf.append("and delete_state='0'");
			try {
				connection = getConnection();
				ps = connection.prepareStatement(sbf.toString());
				ps.setString(1,staffId);
				int i = 2;
				if(StringUtils.hasText(direName)){
					ps.setString(2,"%"+direName+"%");
					i = 3;
				}
				ps.setString(i,timer1);
				ps.setString(i+1,timer2);
				if(StringUtils.hasText(condition)){
					ps.setString(i+2,condition);
				}

				rs = ps.executeQuery();
				//获取总页数量
				if(rs.next()){
					if(Integer.parseInt(rs.getString("count(*)"))<=pageSize){
						jsonObject.put("totalpage","1");
					}else {
						if(Integer.parseInt(rs.getString("count(*)"))  % pageSize == 0){
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize;
							jsonObject.put("totalpage",String.valueOf(j));
						}else {
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize +1;
							jsonObject.put("totalpage",String.valueOf(j));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("获取数量失败");
			}finally {
				cleanUp(connection,null,ps,rs);
			}
		}
		//不是第一页的话
		StringBuffer sb = new StringBuffer();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sb.append("SELECT * FROM (SELECT temp.*,ROWNUM RN FROM (select id,city,dire_name,order_state,create_date from  hnlt_gk.res_dispatch_order where staff_id = ?");
		if(StringUtils.hasText(direName)){
			sb.append("and dire_name like ?");
		}
		sb.append("and create_date BETWEEN to_date(?,'yyyy/mm/dd')  AND to_date(?,'yyyy/mm/dd')");
		if(StringUtils.hasText(condition)){
			sb.append("and order_state = ?");
		}
		sb.append("and delete_state='0' ORDER BY create_date DESC) temp  \n" +
				"WHERE ROWNUM <= ?  \n" +
				")  \n" +
				"WHERE RN > ?");
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1,staffId);
			int i = 2;
			if(StringUtils.hasText(direName)){
				ps.setString(2,"%"+direName+"%");
				i++;
			}
			ps.setString(i,timer1);
			i++;
			ps.setString(i,timer2);
			i++;
			if(StringUtils.hasText(condition)){
				ps.setString(i,condition);
				i++;
			}
			ps.setInt(i,pageIndex*pageSize);
			i++;
			ps.setInt(i,(pageIndex-1)*pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				JSONObject result = new JSONObject();
				result.put("id",rs.getObject("id")==null?"":rs.getObject("id"));
				result.put("city",rs.getObject("city")==null?"":rs.getObject("city"));
				result.put("direName",rs.getObject("dire_name")==null?"":rs.getObject("dire_name"));
				result.put("orderState",rs.getObject("order_state")==null?"":rs.getObject("order_state"));
				result.put("createDate",String.valueOf(rs.getDate("create_date")==null?"":rs.getDate("create_date")));
				jsonArray.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			jsonObject.put("result",jsonArray);
			cleanUp(connection,null,ps,rs);
		}
		return jsonObject.toString();
		}

	public String getResouceInfoList(String orgId, String direName, String equipmentName, Integer pageSize, Integer pageIndex, String staffId, String condition) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		//正则匹配所有orgid
		String regName = ":(.*?)\\}";
		Pattern pattern = Pattern.compile(regName);
		Matcher matcher = pattern.matcher(orgId);
		int is = 0;
		String orgList = "";
		while ( matcher.find() ){
			orgList=orgList+matcher.group(1)+",";
		}
		String Allorg1 = orgList.substring(0, orgList.length() - 1);
		String Allorg3 = "";
		if(Allorg1.contains(",")){
			Allorg3 = Allorg1.replaceAll(",", "','");
		}
		else {
			Allorg3 = Allorg1;
		}
		//如果查的为第一页，需要返回数量
		if(pageIndex==1){
			StringBuffer sbf = new StringBuffer();
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			sbf.append("   SELECT count(*)\n" +
					"  FROM rme_eqp a ,spc_exc_eqp_rela re,exc_station_direction d \n" +
					" WHERE a.delete_state = '0'\n" +
					"   and a.res_type_id = '2530'\n" +
					"   and a.eqp_name  not  like '%光交%' \n" +
					"   and NVL(a.Opr_State_Id,'0') <>'170038'\n" +
					"   and a.eqp_name is not null" +
					"   and re.delete_state='0'\n" +
					"   and d.delete_state='0'\n" +
					"   and a.eqp_id=re.eqp_id\n" +
					"   and re.dire_id=d.dire_id\n");
			sbf.append("   and d.dire_name like ? \n");
			sbf.append("   and a.eqp_name like ? \n");
			if("2".equals(condition)){
				sbf.append("and a.check_state='0' \n");
			}
			if("3".equals(condition)){
				sbf.append("and a.check_state='1' \n");
			}
			if(!"248701".equals(staffId)){
				sbf.append("   and a.org_id in ('"+Allorg3+"')  ");
			}

			try {
				connection = getConnection();
				ps = connection.prepareStatement(sbf.toString());
				ps.setString(1,"%"+direName+"%");
				ps.setString(2,"%"+equipmentName+"%");
				rs = ps.executeQuery();
				//获取总页数量
				if(rs.next()){
					if(Integer.parseInt(rs.getString("count(*)"))<=pageSize){
						jsonObject.put("totalpage","1");
					}else {
						if(Integer.parseInt(rs.getString("count(*)"))  % pageSize == 0){
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize;
							jsonObject.put("totalpage",String.valueOf(j));
						}else {
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize +1;
							jsonObject.put("totalpage",String.valueOf(j));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询数量失败");
			}finally {
				cleanUp(connection,null,ps,rs);
			}
		}
		//不是第一页的话
		StringBuffer sb = new StringBuffer();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sb.append("SELECT \"ID\", \"NO\", \"NAME\", \"TYPE\", \"RESID\", \"OBD_GRADE\", \"RESTYPEID\", \"MODIRY_DATE\", \"CREATE_DATE\", \"DELETE_STATE\", \"DELETE_TIME\", \"SUPERREGIONID\", \"ORG_ID\", \"CHECK_STATE\" ,\"DIRE_NAME\" FROM (SELECT \"T1\".*, ROWNUM \"X_SEQUEL_ROW_NUMBER_X\" FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT a.eqp_id as id,\n" +
				"       a.eqp_no as no,\n" +
				"       a.eqp_name as name,\n" +
				"       'obd' type,\n" +
				"       a.eqp_id as resid,\n" +
				"       sf_get_desc_china(a.protect_style_id) as obd_grade,\n" +
				"       '2530' as restypeid,\n" +
				"       a.modiry_date,\n" +
				"       a.create_date,\n" +
				"       a.delete_state,\n" +
				"       a.delete_time,\n" +
				"       a.org_id as superregionid,\n" +
				"       a.org_id ,\n" +
				"       a.check_state as check_state,\n" +
				"       d.dire_id,\n" +
				"       d.dire_name\n" +
				"  FROM rme_eqp a,spc_exc_eqp_rela re,exc_station_direction d\n" +
				" WHERE a.delete_state = '0'\n" +
				"   and a.res_type_id = '2530'\n" +
				"   and a.eqp_name  not  like '%光交%' \n" +
				"   and NVL(a.Opr_State_Id,'0') <>'170038'\n" +
				"   and a.eqp_name is not null\n" +
				"   and re.delete_state='0'\n" +
				"   and d.delete_state='0'\n" +
				"   and a.eqp_id=re.eqp_id\n" +
				"   and re.dire_id=d.dire_id\n");
		sb.append("and d.dire_name like ? \n");
		sb.append("and a.eqp_name like ? \n");
		if("2".equals(condition)){
			sb.append("and a.check_state='0' \n");
		}
		if("3".equals(condition)){
			sb.append("and a.check_state='1' \n");
		}
		if(!"248701".equals(staffId)){
			sb.append("and a.org_id in ('"+Allorg3+"') ");
		}
		sb.append(") \"T2\") \"T2\" WHERE (ROWNUM <= 1000) ) \"T1\") \"T1\") \"T1\" " +
				"WHERE ((\"X_SEQUEL_ROW_NUMBER_X\" > ?) AND " +
				"(\"X_SEQUEL_ROW_NUMBER_X\" <= (? + ?)))");
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1,"%"+direName+"%");
			ps.setString(2,"%"+equipmentName+"%");
			ps.setInt(3,(pageIndex-1)*pageSize);
			ps.setInt(4,(pageIndex-1)*pageSize);
			ps.setInt(5,pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				JSONObject result = new JSONObject();
				result.put("id",rs.getObject("id")==null?"":rs.getObject("id"));
				result.put("no",rs.getObject("no")==null?"":rs.getObject("no"));
				result.put("name",rs.getObject("name")==null?"":rs.getObject("name"));
				result.put("type",rs.getObject("type")==null?"":rs.getObject("type"));
				result.put("resid",rs.getObject("resid")==null?"":rs.getObject("resid"));
				result.put("obd_grade",rs.getObject("obd_grade")==null?"":rs.getObject("obd_grade"));
				result.put("restypeid",rs.getObject("restypeid")==null?"":rs.getObject("restypeid"));
				result.put("modiry_date",String.valueOf(rs.getDate("modiry_date")==null?"":rs.getDate("modiry_date")));
				result.put("create_date",String.valueOf(rs.getDate("create_date")==null?"":rs.getDate("create_date")));
				result.put("delete_state",rs.getObject("delete_state")==null?"":rs.getObject("delete_state"));
				result.put("delete_time",String.valueOf(rs.getDate("delete_time")==null?"":rs.getDate("delete_time")));
				result.put("superregionid",rs.getObject("superregionid")==null?"":rs.getObject("superregionid"));
				result.put("org_id",rs.getObject("org_id")==null?"":rs.getObject("org_id"));
				result.put("check_state",rs.getObject("check_state")==null?"":rs.getObject("check_state"));
				result.put("dire_name",rs.getObject("dire_name")==null?"":rs.getObject("dire_name"));
				jsonArray.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			jsonObject.put("result",jsonArray);
			cleanUp(connection,null,ps,rs);
		}
		return jsonObject.toString();
	}

	public String getgcInfoList(String orgId, String equipmentName, Integer pageSize, Integer pageIndex,String staffId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		//正则匹配所有orgid
		String regName = ":(.*?)\\}";
		Pattern pattern = Pattern.compile(regName);
		Matcher matcher = pattern.matcher(orgId);
		int is = 0;
		String orgList = "";
		while ( matcher.find() ){
			orgList=orgList+matcher.group(1)+",";
		}
		String Allorg1 = orgList.substring(0, orgList.length() - 1);
		String Allorg3 = "";
		if(Allorg1.contains(",")){
			Allorg3 = Allorg1.replaceAll(",", "','");
		}
		else {
			Allorg3 = Allorg1;
		}
		//如果查的为第一页，需要返回数量
		if(pageIndex==1){
			StringBuffer sbf = new StringBuffer();
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			sbf.append("SELECT count(*)\n" +
					"FROM inf_gc_to_qc_order a,\n" +
					"tblprojectobddevice t,\n" +
					"rme_project_belong b\n" +
					"WHERE a.STATUS = '10I'\n" +
					"and a.delete_state = '0'\n" +
					"AND t.obd_id_hx = a.obd_id\n" +
					"and b.project_code = t.project_code\n");
			sbf.append("and t.optical_name like ? \n");
			if(!"248701".equals(staffId)){
				sbf.append("and b.team_id in ('"+Allorg3+"') ");
			}
			try {
				connection = getConnection();
				ps = connection.prepareStatement(sbf.toString());
				ps.setString(1,"%"+equipmentName+"%");
				rs = ps.executeQuery();
				//获取总页数量
				if(rs.next()){
					if(Integer.parseInt(rs.getString("count(*)"))<=pageSize){
						jsonObject.put("totalpage","1");
					}else {
						if(Integer.parseInt(rs.getString("count(*)"))  % pageSize == 0){
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize;
							jsonObject.put("totalpage",String.valueOf(j));
						}else {
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize +1;
							jsonObject.put("totalpage",String.valueOf(j));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询数量失败");
			}finally {
				cleanUp(connection,null,ps,rs);
			}
		}
		//不是第一页的话
		StringBuffer sb = new StringBuffer();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sb.append("SELECT ID, NO, NAME, TYPE, RESID, RESTYPEID, MODIRY_DATE, CREATE_DATE, DELETE_STATE, DELETE_TIME, SUPERREGIONID FROM (SELECT T1.*, ROWNUM X_SEQUEL_ROW_NUMBER_X FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT distinct a.obd_id AS ID, /* 设备id */\n" +
				"                        t.optical_code AS NO, /*  设备编码*/\n" +
				"                        t.optical_name AS NAME,\n" +
				"                        'BELONG' TYPE,\n" +
				"                        a.obd_id AS RESID,\n" +
				"                        '160001' AS RESTYPEID,\n" +
				"                        a.MODIRY_DATE AS modiry_date,\n" +
				"                        a.CREATE_DATE as create_date,\n" +
				"                        a.DELETE_STATE AS delete_state,\n" +
				"                        a.DELETE_TIME AS delete_time,\n" +
				"                        B.TEAM_ID AS SUPERREGIONID\n" +
				"          FROM inf_gc_to_qc_order      a,\n" +
				"               tblprojectobddevice t,\n" +
				"               rme_project_belong      b\n" +
				"         WHERE a.STATUS = '10I'\n" +
				"           and a.delete_state = '0'\n" +
				"           AND t.obd_id_hx = a.obd_id\n" +
				"           and b.project_code = t.project_code \n");
		sb.append("and t.optical_name like ? \n");
		if(!"248701".equals(staffId)){
			sb.append("and b.team_id in ('"+ Allorg3 +"') ");
		}
		sb.append(")T2) T2 WHERE (ROWNUM <= 500) ) T1)T1)T1 WHERE ((X_SEQUEL_ROW_NUMBER_X > ?) AND (X_SEQUEL_ROW_NUMBER_X <= (? + ?)))");
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1,"%"+equipmentName+"%");
			ps.setInt(2,(pageIndex-1)*pageSize);
			ps.setInt(3,(pageIndex-1)*pageSize);
			ps.setInt(4,pageSize);
			rs = ps.executeQuery();
			while(rs.next()){
				JSONObject result = new JSONObject();
				result.put("id",rs.getObject("ID")==null?"":rs.getObject("ID"));
				result.put("no",rs.getObject("NO")==null?"":rs.getObject("NO"));
				result.put("name",rs.getObject("NAME")==null?"":rs.getObject("NAME"));
				result.put("type",rs.getObject("TYPE")==null?"":rs.getObject("TYPE"));
				result.put("resid",rs.getObject("RESID")==null?"":rs.getObject("RESID"));
				result.put("restypeid",rs.getObject("RESTYPEID")==null?"":rs.getObject("RESTYPEID"));
				result.put("modiry_date",String.valueOf(rs.getDate("modiry_date")==null?"":rs.getDate("modiry_date")));
				result.put("create_date",String.valueOf(rs.getDate("create_date")==null?"":rs.getDate("create_date")));
				result.put("delete_state",rs.getObject("delete_state")==null?"":rs.getObject("delete_state"));
				result.put("delete_time",String.valueOf(rs.getDate("delete_time")==null?"":rs.getDate("delete_time")));
				result.put("superregionid",rs.getObject("SUPERREGIONID")==null?"":rs.getObject("SUPERREGIONID"));
				jsonArray.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			jsonObject.put("result",jsonArray);
			cleanUp(connection,null,ps,rs);
		}
		return jsonObject.toString();
	}

	public  Map<String, Object> getResouceInfo(String eqpId) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("<<<eqpId>>>:" + eqpId);
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("   SELECT re.eqp_id AS eqpId,re.bss_pro_name AS bssName,\n" +
				"          re.eqp_name AS eqpName,\n" +
				"          re.eqp_no AS eqpNo,\n" +
				"          decode(re.check_state, '1', '未清查', '0', '已清查', '未清查') AS isCheck,\n" +
				"          re.posit_type_id AS positTypeId,\n" +
				"          sf_get_desc_china(re.posit_type_id) AS positTypeName,\n" +
				"          re.POSIT_ID AS positId,\n" +
				"          (select p.posit_name\n" +
				"             from VW_POS_POSIT@to_res p\n" +
				"            where p.posit_id = re.posit_id) as positName,\n" +
				"          '' AS location,\n" +
				"          (SELECT prs.desc_china\n" +
				"             FROM pub_restriction@to_res prs\n" +
				"            WHERE prs.serial_no = re.ASSET_INFO) AS netLevel,\n" +
				"          re.ASSET_INFO as assetInfo,\n" +
				"          super_eqp.eqp_id AS superEqpId,\n" +
				"          sf_get_port_id(sf_get_onuobd_up_pon_id(re.eqp_id) ) as  PON_no,\n" +
				"          super_eqp.eqp_name AS superEqpName,\n" +
				"          super_eqp.rma AS ipAddress,\n" +
				"          pon_port.port_no AS ponNo,\n" +
				"          pon_port.port_id AS ponId,\n" +
				"          parent_eqp.eqp_id AS parentEqpId,\n" +
				"          parent_eqp.eqp_name AS parentEqpName,\n" +
				"          re.ACCESS_TYPE AS useType,\n" +
				"          decode(cos.object_state, '0', '否', '1', '是', '2', '待', '否') AS isInvestigation,\n" +
				"          nvl(cos.object_state, '0') AS isInvestigationId,\n" +
				"          decode(poncos.object_state, '0', '否', '1', '是', '否') AS ponIsInvestigation,\n" +
				"          nvl(poncos.object_state, '0') AS ponIsInvestigationId,\n" +
				"          SF_GET_DESC_CHINA(re.PROTECT_STYLE_ID) as grade,\n" +
				"          re.eqp_from as coverArea,\n" +
				"          re.product_no as productNo,\n" +
				"          pub.digcode as digcode,\n" +
				"          re.check_state checkState,\n" +
				"          sf_get_desc_china(re.POWER_INFO) as powerInfoValue,\n" +
				"          re.POWER_INFO as powerInfo,\n" +
				"          sf_get_eqp_model(re.EQP_MODEL_ID) as eqpModel,\n" +
				"          re.EQP_MODEL_ID as eqpModelId,\n" +
				"          re.CONTRACTOR as contractor,\n" +
				"          re.USE_TIME as useTime,\n" +
				"          re.ADDRESS as address,\n" +
				"          'FTTH' as accessType,\n" +
				"          re.HDWE_VER as hdweVer,\n" +
				"          re.SW_VER as swVer,\n" +
				"          re.pos_x as xTude,\n" +
				"          re.pos_y as yTude,\n" +
				"          b.port_id AS upPortId,\n" +
				"          re.MIN_CIC as remark,\n" +
				"          SF_GET_DESC_CHINA(re.WORK_MODE) as workMode\n" +
				"     FROM rme_eqp re\n" +
				"     LEFT JOIN rme_eqp parent_eqp\n" +
				"       ON re.SUPER_EQP_ID = parent_eqp.eqp_id\n" +
				"      AND parent_eqp.delete_state = '0'\n" +
				"     LEFT JOIN rme_port pon_port\n" +
				"       ON re.node_id = pon_port.port_id\n" +
				"      AND pon_port.delete_state = '0'\n" +
				"     LEFT JOIN rme_eqp super_eqp\n" +
				"       ON super_eqp.eqp_id = re.parent_id\n" +
				"      AND super_eqp.DELETE_STATE = '0'\n" +
				"     LEFT JOIN saat_object_state@to_res cos\n" +
				"       ON re.eqp_id = cos.res_id\n" +
				"     LEFT JOIN saat_object_state@to_res poncos\n" +
				"       ON pon_port.port_id = poncos.res_id\n" +
				"      and ((poncos.obdid is not null and re.eqp_id = poncos.obdid) or\n" +
				"          poncos.obdid is null)\n" +
				"     left join PUB_TWO_DIMENSION_CODE pub\n" +
				"       on pub.res_id = re.eqp_id\n" +
				"      and pub.delete_state = '0'\n" +
				"     LEFT JOIN RME_PORT b\n" +
				"       ON re.eqp_id = b.super_res_id\n" +
				"      and b.position = '0'\n" +
				"    WHERE re.res_type_id = '2530'\n" +
				"      AND re.delete_state = '0'\n ");
		sbf.append("AND re.eqp_id = ? \n");
		sbf.append("AND rownum = 1 ");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,eqpId);
			rs = ps.executeQuery();
			if (rs.next()) {
				map.put("eqpId",rs.getObject("eqpId")==null?"":rs.getObject("eqpId"));
				map.put("bssName",rs.getObject("bssName")==null?"":rs.getObject("bssName"));
				map.put("eqpName",rs.getObject("eqpName")==null?"":rs.getObject("eqpName"));
				map.put("eqpNo",rs.getObject("eqpNo")==null?"":rs.getObject("eqpNo"));
				map.put("isCheck",rs.getObject("isCheck")==null?"":rs.getObject("isCheck"));
				map.put("positTypeId",rs.getObject("positTypeId")==null?"":rs.getObject("positTypeId"));
				map.put("positTypeName",rs.getObject("positTypeName")==null?"":rs.getObject("positTypeName"));
				map.put("positId",rs.getObject("positId")==null?"":rs.getObject("positId"));
				map.put("positName",rs.getObject("positName")==null?"":rs.getObject("positName"));
				map.put("location",rs.getObject("location")==null?"":rs.getObject("location"));
				map.put("netLevel",rs.getObject("netLevel")==null?"":rs.getObject("netLevel"));
				map.put("assetInfo",rs.getObject("assetInfo")==null?"":rs.getObject("assetInfo"));
				map.put("superEqpId",rs.getObject("superEqpId")==null?"":rs.getObject("superEqpId"));
				map.put("PON_no",rs.getObject("PON_no")==null?"":rs.getObject("PON_no"));
				map.put("superEqpName",rs.getObject("superEqpName")==null?"":rs.getObject("superEqpName"));
				map.put("ipAddress",rs.getObject("ipAddress")==null?"":rs.getObject("ipAddress"));
				map.put("ponNo",rs.getObject("ponNo")==null?"":rs.getObject("ponNo"));
				map.put("ponId",rs.getObject("ponId")==null?"":rs.getObject("ponId"));
				map.put("parentEqpId",rs.getObject("parentEqpId")==null?"":rs.getObject("parentEqpId"));
				map.put("parentEqpName",rs.getObject("parentEqpName")==null?"":rs.getObject("parentEqpName"));
				map.put("useType",rs.getObject("useType")==null?"":rs.getObject("useType"));
				map.put("isInvestigation",rs.getObject("isInvestigation")==null?"":rs.getObject("isInvestigation"));
				map.put("isInvestigationId",rs.getObject("isInvestigationId")==null?"":rs.getObject("isInvestigationId"));
				map.put("ponIsInvestigation",rs.getObject("ponIsInvestigation")==null?"":rs.getObject("ponIsInvestigation"));
				map.put("ponIsInvestigationId",rs.getObject("ponIsInvestigationId")==null?"":rs.getObject("ponIsInvestigationId"));
				map.put("grade",rs.getObject("grade")==null?"":rs.getObject("grade"));
				map.put("coverArea",rs.getObject("coverArea")==null?"":rs.getObject("coverArea"));
				map.put("productNo",rs.getObject("productNo")==null?"":rs.getObject("productNo"));
				map.put("digcode",rs.getObject("digcode")==null?"":rs.getObject("digcode"));
				map.put("checkState",rs.getObject("checkState")==null?"":rs.getObject("checkState"));
				map.put("powerInfoValue",rs.getObject("powerInfoValue")==null?"":rs.getObject("powerInfoValue"));
				map.put("powerInfo",rs.getObject("powerInfo")==null?"":rs.getObject("powerInfo"));
				map.put("eqpModel",rs.getObject("eqpModel")==null?"":rs.getObject("eqpModel"));
				map.put("eqpModelId",rs.getObject("eqpModelId")==null?"":rs.getObject("eqpModelId"));
				map.put("contractor",rs.getObject("contractor")==null?"":rs.getObject("contractor"));
				map.put("useTime",rs.getObject("useTime")==null?"":rs.getObject("useTime"));
				map.put("address",rs.getObject("address")==null?"":rs.getObject("address"));
				map.put("accessType",rs.getObject("accessType")==null?"":rs.getObject("accessType"));
				map.put("hdweVer",rs.getObject("hdweVer")==null?"":rs.getObject("hdweVer"));
				map.put("swVer",rs.getObject("swVer")==null?"":rs.getObject("swVer"));
				map.put("xTude",rs.getObject("xTude")==null?"":rs.getObject("xTude"));
				map.put("yTude",rs.getObject("yTude")==null?"":rs.getObject("yTude"));
				map.put("upPortId",rs.getObject("upPortId")==null?"":rs.getObject("upPortId"));
				map.put("remark",rs.getObject("remark")==null?"":rs.getObject("remark"));
				map.put("workMode",rs.getObject("workMode")==null?"":rs.getObject("workMode"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询分光器信息失败!");
		} finally {
			cleanUp(conn, null, ps, rs);
		}

		return map;

	}

	public String getResoucePortInfoList(String eqpId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		StringBuffer sbf = new StringBuffer();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("select distinct eqp.eqp_id,\n" +
				"                 eqp.eqp_name,\n" +
				"                 eqp.eqp_no,\n" +
				"                 port.port_id,\n" +
				"                 port.port_no,\n" +
				"                 port.position,\n" +
				"                 port.res_type_id,\n" +
				"                 port.opr_state_id,\n" +
				"                 sf_get_desc_china(port.opr_state_id) AS oprState,\n" +
				"                 decode(comb.object_state, 1, '已清查', '未清查') AS isCheck,\n" +
				"                 (case\n" +
				"                   WHEN comb.object_state is not null then\n" +
				"                    comb.OBJECT_STATE\n" +
				"                   else\n" +
				"                    0\n" +
				"                 end) as comb_state_id,\n" +
				"                 v.order_id as \"order_id\",\n" +
				"                 v.dis_seq,\n" +
				"                 (case\n" +
				"                   when SF_GET_TELENO_BY_RES_ID(port.PORT_ID) IS NOT NULL then\n" +
				"                    v.cust_name\n" +
				"                   else\n" +
				"                    s.auth_info\n" +
				"                 end) as \"cust_name\",\n" +
				"                /* v.assign_port_id*/decode((case\n" +
				"                   when SF_GET_TELENO_BY_RES_ID(port.PORT_ID) IS NOT NULL then\n" +
				"                    v.tele_no\n" +
				"                   else\n" +
				"                    s.auth_info\n" +
				"                 end),'','',port.port_id) as \"account_id\",\n" +
				"                 '6024' as \"account_type_id\",\n" +
				"                 (case\n" +
				"                   when SF_GET_TELENO_BY_RES_ID(port.PORT_ID) IS NOT NULL then\n" +
				"                    v.tele_no\n" +
				"                   else\n" +
				"                    s.auth_info\n" +
				"                 end) as \"tele_no\",\n" +
				"                 v.addr_id as \"addr_id\",\n" +
				"                 SF_GET_address_BY_RES_ID(v.assign_port_id) as \"address\",\n" +
				"                 nvl(v.address, '未找到地址') as \"stand_name\",\n" +
				"                 (case\n" +
				"                   when SF_GET_TELENO_BY_RES_ID(port.PORT_ID) IS NOT NULL then\n" +
				"                    v.loid\n" +
				"                   else\n" +
				"                    s.auth_info\n" +
				"                 end) as \"loid\",\n" +
				"                 eqp.eqp_no as \"ont_id\",\n" +
				"                 v.assign_port_id,\n" +
				"                 code.digcode as \"digcode\",\n" +
				"                 v.assign_port_id AS assign_port_id,\n" +
				"                 v.assign_eqp_id AS assign_eqp_id,\n" +
				"                 v.assign_port_id AS terminal_port_id,\n" +
				"                 v.assign_eqp_id AS terminal_eqp_id,\n" +
				"                 '' as is_need_line,\n" +
				"                 s.flag\n" +
				"   from rme_eqp eqp\n" +
				"   left join rme_port port on eqp.eqp_id = port.super_res_id\n" +
				"                          and port.delete_state = '0'\n" +
				"   left join saat_object_state@to_res comb on comb.res_id = port.port_id\n" +
				"   left join pub_two_dimension_code code on port.port_id = code.res_id\n" +
				"                                        and code.res_type_id = '6024'\n" +
				"                                        and code.delete_state = '0'\n" +
				"   left join ZYQC_ftth_user_2019 v on port.port_id = v.assign_port_id\n" +
				"   left join ZYQC_SN_without_user@to_res s on port.port_id = s.port_id\n" +
				"  where eqp.delete_state = '0'\n" +
				"    and port.res_type_id = 2532\n");
		sbf.append("    and eqp.eqp_id = ? \n");
		sbf.append("  order by port.position asc");
		int useroccpy = 0;
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sbf.toString());
			ps.setString(1,eqpId);
			rs = ps.executeQuery();
			String name = "";
			String telno = "";
			while(rs.next()){
				JSONObject result = new JSONObject();
				result.put("eqp_id",rs.getObject("eqp_id")==null?"":rs.getObject("eqp_id"));
				result.put("eqp_name",rs.getObject("eqp_name")==null?"":rs.getObject("eqp_name"));
				result.put("eqp_no",rs.getObject("eqp_no")==null?"":rs.getObject("eqp_no"));
				result.put("port_id",rs.getObject("port_id")==null?"":rs.getObject("port_id"));
				result.put("port_no",rs.getObject("port_no")==null?"":rs.getObject("port_no"));
				result.put("position",rs.getObject("position")==null?"":rs.getObject("position"));
				result.put("res_type_id",rs.getObject("res_type_id")==null?"":rs.getObject("res_type_id"));
				result.put("opr_state_id",rs.getObject("opr_state_id")==null?"":rs.getObject("opr_state_id"));
				result.put("oprState",rs.getObject("oprState")==null?"":rs.getObject("oprState"));
				if("占用".equals(rs.getObject("oprState"))){
					useroccpy++;
				}
				result.put("isCheck",rs.getObject("isCheck"));
				result.put("comb_state_id",rs.getObject("comb_state_id"));
				result.put("dis_seq",rs.getObject("dis_seq"));
				try {
					if(rs.getString("cust_name")==null){
						result.put("cust_name","");
					}else {
						//脱敏
						if (rs.getString("cust_name").length() > 2) {
							name = rs.getString("cust_name").substring(0,1) + "**";
						} else {
							name = rs.getString("cust_name").substring(0,1) + "*";
						}
						result.put("cust_name", name);
					}
					if(rs.getString("tele_no")==null){
						result.put("tele_no",rs.getObject(""));
					}else {
						telno =  rs.getString("tele_no").substring(0,3)+"****"+rs.getString("tele_no").substring(rs.getString("tele_no").length()-1,rs.getString("tele_no").length());
						result.put("tele_no",telno);
					}
				} catch (SQLException e) {
					result.put("cust_name",rs.getObject("cust_name")==null?"":rs.getObject("cust_name"));
					result.put("tele_no",rs.getObject("tele_no")==null?"":rs.getObject("tele_no"));
				}
				result.put("account_id",rs.getObject("account_id")==null?"":rs.getObject("account_id"));
				result.put("account_type_id",rs.getObject("account_type_id")==null?"":rs.getObject("account_type_id"));
				result.put("addr_id",rs.getObject("addr_id")==null?"":rs.getObject("addr_id"));
				result.put("address",rs.getObject("address")==null?"":rs.getObject("address"));
				result.put("stand_name",rs.getObject("stand_name")==null?"":rs.getObject("stand_name"));
				result.put("loid",rs.getObject("loid")==null?"":rs.getObject("loid"));
				result.put("ont_id",rs.getObject("ont_id")==null?"":rs.getObject("ont_id"));
				result.put("assign_port_id",rs.getObject("assign_port_id")==null?"":rs.getObject("assign_port_id"));
				result.put("digcode",rs.getObject("digcode")==null?"":rs.getObject("digcode"));
				result.put("assign_eqp_id",rs.getObject("assign_eqp_id")==null?"":rs.getObject("assign_eqp_id"));
				result.put("terminal_port_id",rs.getObject("terminal_port_id")==null?"":rs.getObject("terminal_port_id"));
				result.put("terminal_eqp_id",rs.getObject("terminal_eqp_id")==null?"":rs.getObject("terminal_eqp_id"));
				result.put("is_need_line",rs.getObject("is_need_line")==null?"":rs.getObject("is_need_line"));
				result.put("flag",rs.getObject("flag")==null?"":rs.getObject("flag"));
				jsonArray.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			jsonObject.put("num",useroccpy);
			jsonObject.put("result",jsonArray);
			cleanUp(connection,null,ps,rs);
		}
		return jsonObject.toString();
	}

	public String getResouceCoverAddressInfoList(String eqpId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("SELECT DISTINCT G.ADDR_SEGM_ID AS ADDRSEGMID,\n" +
				"        G.RES_ID       AS RESID,\n" +
				"        G.SEGM_EQP_ID  AS SEGMEQPID,\n" +
				"        D.STAND_NAME   AS STANDNAME\n" +
				"        FROM ADDR_SEGM_EQP@to_res G, ADDR_SEGM@to_res D,RME_EQP@to_res P\n" +
				"        WHERE G.ADDR_SEGM_ID = D.SEGM_ID\n" +
				"        AND G.DELETE_STATE = '0'\n" +
				"        AND D.DELETE_STATE = '0'\n" +
				"        AND P.DELETE_STATE='0'\n" +
				"        and g.res_id=p.eqp_id\n");
		sbf.append("AND G.RES_ID = ? ");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,eqpId);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject result = new JSONObject();
				result.put("addrsegmid",rs.getString("ADDRSEGMID")==null?"":rs.getString("ADDRSEGMID"));
				result.put("resid",rs.getString("RESID")==null?"":rs.getString("RESID"));
				result.put("segmeqpid",rs.getString("SEGMEQPID")==null?"":rs.getString("SEGMEQPID"));
				result.put("standname",rs.getString("STANDNAME")==null?"":rs.getString("STANDNAME"));
				jsonArray.add(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询分光器信息失败!");
		} finally {
			jsonObject.put("num",jsonArray.size());
			jsonObject.put("result",jsonArray);
			cleanUp(conn, null, ps, rs);
		}
		return jsonObject.toString();
	}

	public String getResouceAllCoverAddressInfoList(String address,Integer pageSize,Integer pageIndex) throws Exception {
		logger.info("<<<CoverAddress search by address>>>:" + address);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		//如果查的为第一页，需要返回数量
		if(pageIndex==1){
			StringBuffer stringBuffer = new StringBuffer();
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			stringBuffer.append("select  count(*) from zyqc_addr_segm@to_res a where 1=1 and name like ? ");
			try {
				connection = getConnection();
				ps = connection.prepareStatement(stringBuffer.toString());
				ps.setString(1,"%"+address+"%");
				rs = ps.executeQuery();
				//获取总页数量
				if(rs.next()){
					if(Integer.parseInt(rs.getString("count(*)"))<=pageSize){
						jsonObject.put("totalpage","1");
					}else {
						if(Integer.parseInt(rs.getString("count(*)"))  % pageSize == 0){
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize;
							jsonObject.put("totalpage",String.valueOf(j));
						}else {
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize +1;
							jsonObject.put("totalpage",String.valueOf(j));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询数量失败");
			}finally {
				cleanUp(connection,null,ps,rs);
			}
		}
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("SELECT ID, NAME, SUPERREGIONID FROM (SELECT T1.*, ROWNUM X_SEQUEL_ROW_NUMBER_X FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (select  a.id, a.name,a.superregionid,'180009' segmTypeId FROM zyqc_addr_segm@to_res a where 1=1\n");
		sbf.append("and name like ? ");
		sbf.append(") T2) T2 WHERE (ROWNUM <= 250) ) T1) T1) T1 \n" +
				"WHERE ((X_SEQUEL_ROW_NUMBER_X > ?) AND \n" +
				"(X_SEQUEL_ROW_NUMBER_X <= (? + ?)))");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,"%"+address+"%");
			ps.setInt(2,(pageIndex-1)*pageSize);
			ps.setInt(3,(pageIndex-1)*pageSize);
			ps.setInt(4,pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject result = new JSONObject();
				result.put("id",rs.getObject("ID")==null?"":rs.getObject("ID"));
				result.put("name",rs.getObject("NAME")==null?"":rs.getObject("NAME"));
				result.put("superregionid",rs.getObject("SUPERREGIONID")==null?"":rs.getObject("SUPERREGIONID"));
				jsonArray.add(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询覆盖地址信息失败!");
		} finally {
			jsonObject.put("result",jsonArray);
			cleanUp(conn, null, ps, rs);
		}
		return jsonObject.toString();
	}

	public void updateResOrderState(String id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "update hnlt_gk.res_dispatch_order set order_state = '1',finish_date = sysdate where id = ? ";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.executeQuery();

		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			cleanUp(conn, null, ps, rs);
		}

	}

	public String getOltInfoList(String eqpName) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("select t.* from (\n" +
				"select a.eqp_id as id,   /* 设备id */\n" +
				"       a.eqp_name as name,  /* 设备名  */\n" +
				"       a.eqp_id as resId,   /*  设备id*/\n" +
				"       /*SF_GET_DESC_CHINA(A.PROTECT_STYLE_ID) as obd_grade,*/  /* 分光器级别 */      \n" +
				"        '2510' as resTypeId,/*资源类型ID*/\n" +
				"        a.region_id as superRegionId\n" +
				"  from rme_eqp a\n" +
				" where a.delete_state = '0'\n" +
				"   and a.res_type_id = '2510'\n" +
				"   and a.eqp_name like ? ) t where  rownum < 51");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,"%"+eqpName+"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject result = new JSONObject();
				result.put("id",rs.getObject("id")==null?"":rs.getObject("id"));
				result.put("name",rs.getObject("name")==null?"":rs.getObject("name"));
				result.put("resId",rs.getObject("resId")==null?"":rs.getObject("resId"));
				result.put("superRegionId",rs.getObject("superRegionId")==null?"":rs.getObject("superRegionId"));
				jsonArray.add(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询OLT失败!");
		} finally {
			jsonObject.put("result",jsonArray);
			cleanUp(conn, null, ps, rs);
		}
		return jsonObject.toString();
	}

	public String bindObdCoordinate(String x, String y, String eqpId) {
		JSONObject jsonObject = new JSONObject();
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		sbf.append("update  rme_eqp rl\n" +
				"set rl.pos_x = ?,rl.pos_y= ?\n" +
				"where rl.eqp_id= ?");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,x);
			ps.setString(2,y);
			ps.setString(3,eqpId);
			int i = ps.executeUpdate();
			if(i>0){
				jsonObject.put("result","000");
				jsonObject.put("msg","成功");
			}
			else {
				jsonObject.put("result","001");
				jsonObject.put("msg","失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分光器绑定经纬度失败!");
		} finally {
			cleanUp(conn, null, ps, null);
		}
		return jsonObject.toString();
	}

	public void writeCoordinateLogToDb(String res_type_id,String res_id,String res_name,String wo_id,String new_info,String old_info,String type,String update_op){
		StringBuffer sbf = new StringBuffer();
		sbf.append("INSERT INTO SAAT_object_notes@To_Res\n" +
				"    (notes_id, res_type_id, res_id,res_name, wo_id, new_info, old_info, type,update_op, create_time)\n" +
				"    VALUES \n" );
		sbf.append("(seq_SAAT_object_notes.nextval@To_Res,?,?,?,?,?,?,?,?,sysdate)");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,res_type_id);
			ps.setString(2,res_id);
			ps.setString(3,res_name);
			ps.setString(4,wo_id);
			ps.setString(5,new_info);
			ps.setString(6,old_info);
			ps.setString(7,type);
			ps.setString(8,update_op);
			int i = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}

	public String getBuildingAccount(String staffId) {
		JSONObject jsonObject = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		List<Map<String,String>> list = new ArrayList();
		ResultSet rs = null;
		String sql = "select * from uos_staff a where exists(select 1 from spc_building b \n" +
				"where a.staff_id=b.staff_id and a.staff_id=? \n" +
				"and b.staff_id  is not null)  \n" +
				"and a.state='1'";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1,staffId);
			rs = ps.executeQuery();
			if (rs.next()) {
			result = 1;
			}
			else {
				result = 0;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		String newSql1 = "select a.m_num, b.d_num\n" +
				"  from (select '1' as tj, count(*) as m_num\n" +
				"          from spc_building_business a\n" +
				"        \n" +
				"         where to_char(a.业务开通日期, 'yyyymm') = to_char(sysdate, 'yyyymm')\n" +
				"           and a.build_code in\n" +
				"               (select b.build_code\n" +
				"                  from spc_building b\n" +
				"                 where b.staff_id = ?)) a\n" +
				"\n" +
				"  left join (SELECT '1' as tj, count(*) as d_num\n" +
				"               FROM spc_building_business a\n" +
				"              WHERE a.业务开通日期 >= TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 1)\n" +
				"                AND a.业务开通日期 < TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 7) + 1\n" +
				"                and a.build_code in\n" +
				"                    (select b.build_code\n" +
				"                       from spc_building b\n" +
				"                      where b.staff_id = ?)) b on a.tj = b.tj";
		if(result==1){
			try {
				conn = getConnection();
				ps = conn.prepareStatement(newSql1);
				ps.setString(1,staffId);
				ps.setString(2,staffId);
				rs = ps.executeQuery();
				if (rs.next()) {
					jsonObject.put("m_num",rs.getString("m_num"));
					jsonObject.put("d_num",rs.getString("d_num"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, ps, rs);
			}
		}
		if(result == 0) {
			int checkarea = 0;
			String searchArea = "SELECT DISTINCT\n" +
					"                 decode(ua.area_name,\n" +
					"                        '湘西',\n" +
					"                        '吉首',\n" +
					"                        '永州',\n" +
					"                        '永州市',\n" +
					"                        ua.area_name) as area_name\n" +
					"   FROM uos_staff t, uos_job_staff ujs, uos_job uj, uos_org uo, uos_area ua\n" +
					"  where ujs.staff_id = t.staff_id\n" +
					"    and uj.job_id = ujs.job_id\n" +
					"    and uo.org_id = uj.org_id\n" +
					"    and ua.area_id = uo.area_id\n" +
					"    and t.state = '1'\n" +
					"    and ujs.state = '1'\n" +
					"    and uj.state = '1'\n" +
					"    and uo.state = '1'\n" +
					"    and ua.state = '10A'\n" +
					"    and ua.grade in ('C2', 'C3')\n" +
					"    and ujs.is_normal = '1'\n" +
					"    and uj.job_name like ('%楼宇管理%')\n" +
					"    and t.staff_id = ?";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(searchArea);
				ps.setString(1,staffId);
				rs = ps.executeQuery();
				while (rs.next()) {
					Map<String,String> map = new HashMap<String, String>();
					map.put("area",rs.getString("area_name"));
					list.add(map);
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, ps, rs);
			}
			checkarea = list.toString().indexOf("湖南省");

			//不存在湖南省 查询地市
			if(checkarea==-1){
				//正则匹配所有orgid
				String regName = "=(.*?)\\}";
				Pattern pattern = Pattern.compile(regName);
				Matcher matcher = pattern.matcher(list.toString());
				int is = 0;
				String orgList = "";
				while ( matcher.find() ){
					orgList=orgList+matcher.group(1)+",";
				}
				String Allorg1 = orgList.substring(0, orgList.length() - 1);
				String Allorg3 = "";
				if(Allorg1.contains(",")){
					Allorg3 = Allorg1.replaceAll(",", "','");
				}
				else {
					Allorg3 = Allorg1;
				}
				String areaSql = "select a.d_num,b.m_num  from ( SELECT  '1' as tj,count(*) as d_num\n" +
						"     FROM spc_building_business a \n" +
						"    WHERE a.业务开通日期 >= TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 1)\n" +
						"      AND a.业务开通日期 < TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 7) + 1\n" +
						"       and a.build_code in\n" +
						"       (select b.build_code from spc_building b where\n" +
						"        b.region in  ('"+Allorg3+"')  )) a \n" +
						"        \n" +
						"        left join \n" +
						"        ( select '1' as tj ,count(*) as m_num\n" +
						"  from spc_building_business a\n" +
						" where to_char(a.业务开通日期, 'yyyymm') = to_char(sysdate, 'yyyymm')\n" +
						"   and a.build_code in\n" +
						"       (select b.build_code from spc_building b where\n" +
						"        b.region in ('"+Allorg3+"')  ) )b on a.tj=b.tj";
				try {
					conn = getConnection();
					ps = conn.prepareStatement(areaSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						jsonObject.put("m_num",rs.getString("m_num"));
						jsonObject.put("d_num",rs.getString("d_num"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					cleanUp(conn, null, ps, rs);
				}
			}
			else {
				String arSql = "select a.d_num,b.m_num  from ( SELECT  '1' as tj,count(*) as d_num\n" +
						"     FROM spc_building_business a \n" +
						"    WHERE a.业务开通日期 >= TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 1)\n" +
						"      AND a.业务开通日期 < TRUNC(NEXT_DAY(SYSDATE - 8, 1) + 7) + 1\n" +
						"       and a.build_code in\n" +
						"       (select b.build_code from spc_building b )) a \n" +
						"        \n" +
						"        left join \n" +
						"        ( select '1' as tj ,count(*) as m_num\n" +
						"  from spc_building_business a\n" +
						" where to_char(a.业务开通日期, 'yyyymm') = to_char(sysdate, 'yyyymm')\n" +
						"   and a.build_code in\n" +
						"       (select b.build_code from spc_building b  ) ) b on a.tj=b.tj";
				try {
					conn = getConnection();
					ps = conn.prepareStatement(arSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						jsonObject.put("m_num",rs.getString("m_num"));
						jsonObject.put("d_num",rs.getString("d_num"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					cleanUp(conn, null, ps, rs);
				}
			}
		}
		return jsonObject.toString();
	}


	public String getUserInfo(String loid) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("select g.loid||'_'||g.dis_seq as id, /* 设备id */\n" +
				"       g.loid as no, /*  设备编码*/\n" +
				"       g.cust_name as name, /* 设备名  */\n" +
				"       g.loid as searchSimpleSpell, /* 简拼查询  */\n" +
				"      g.address as address\n" +
				"  from ZYQC_ftth_user_2019 g\n" +
				" where 1 = 1 and  g.delete_state='0' \n");
		sbf.append(" and g.loid = ?");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,loid);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject result = new JSONObject();
				result.put("no",rs.getObject("no")==null?"":rs.getObject("no"));
				result.put("name",rs.getObject("name")==null?"":rs.getObject("name"));
				result.put("address",rs.getObject("address")==null?"":rs.getObject("address"));
				jsonArray.add(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询用户失败!");
		} finally {
			jsonObject.put("result",jsonArray);
			cleanUp(conn, null, ps, rs);
		}
		return jsonObject.toString();
	}


	public Map<String, Object> getResouceInfoByCode(String digCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("<<<digCode>>>:" + digCode);
		StringBuffer sbf = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sbf.append("select a.eqp_id from  rme_eqp a, pub_two_dimension_code b where a.eqp_id=b.res_id and b.res_type_id='2530' and a.delete_state='0' and b.delete_state='0' and b.digCode=?");
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());
			ps.setString(1,digCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				map.put("eqp_id",rs.getObject("eqp_id"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("扫码查询OBD失败");
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return map;

	}

	public String getAreaList(String staffId) {
		JSONObject results = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int checkarea = 0;
		List<Map<String,String>> list = new ArrayList();
		String searchArea = "SELECT DISTINCT\n" +
				"                 decode(ua.area_name,\n" +
				"                        '湘西',\n" +
				"                        '吉首',\n" +
				"                        '永州',\n" +
				"                        '永州市',\n" +
				"                        ua.area_name) as area_name\n" +
				"   FROM uos_staff t, uos_job_staff ujs, uos_job uj, uos_org uo, uos_area ua\n" +
				"  where ujs.staff_id = t.staff_id\n" +
				"    and uj.job_id = ujs.job_id\n" +
				"    and uo.org_id = uj.org_id\n" +
				"    and ua.area_id = uo.area_id\n" +
				"    and t.state = '1'\n" +
				"    and ujs.state = '1'\n" +
				"    and uj.state = '1'\n" +
				"    and uo.state = '1'\n" +
				"    and ua.state = '10A'\n" +
				"    and ua.grade in ('C2', 'C3')\n" +
				"    and ujs.is_normal = '1'\n" +
				"    and uj.job_name like ('%楼宇管理%')\n" +
				"    and t.staff_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(searchArea);
			ps.setString(1,staffId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("area",rs.getString("area_name"));
				list.add(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		checkarea = list.toString().indexOf("湖南省");
		//不存在湖南省 查询地市
		if(checkarea==-1){
			//正则匹配所有orgid
			String regName = "=(.*?)\\}";
			Pattern pattern = Pattern.compile(regName);
			Matcher matcher = pattern.matcher(list.toString());
			int is = 0;
			String orgList = "";
			while ( matcher.find() ){
				orgList=orgList+matcher.group(1)+",";
			}
			String Allorg1 = orgList.substring(0, orgList.length() - 1);
			String Allorg3 = "";
			if(Allorg1.contains(",")){
				Allorg3 = Allorg1.replaceAll(",", "','");
			}
			else {
				Allorg3 = Allorg1;
			}
			String areaSql = "select distinct t.region,t.addr2 from spc_building t where t.region in ('"+Allorg3+"')";
			JSONArray markets = new JSONArray();
			try {
				conn = getConnection();
				ps = conn.prepareStatement(areaSql);
				rs = ps.executeQuery();
				while (rs.next()) {
					//市
					JSONObject market = new JSONObject();
					market.put("code",rs.getString("region")==null?"":rs.getString("region"));
					market.put("name",rs.getString("addr2")==null?"":rs.getString("addr2"));
					markets.add(market);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				results.put("markets",markets);
				cleanUp(conn, null, ps, rs);
			}
			areaSql = "select distinct t.region,t.addr3 from spc_building t where t.region in ('"+Allorg3+"')";
			JSONArray regions = new JSONArray();
			try {
				conn = getConnection();
				ps = conn.prepareStatement(areaSql);
				rs = ps.executeQuery();
				while (rs.next()) {
					//区
					JSONObject region = new JSONObject();
					region.put("parentCode",rs.getString("region")==null?"":rs.getString("region"));
					region.put("name",rs.getString("addr3")==null?"":rs.getString("addr3"));
					regions.add(region);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				results.put("regions",regions);
				cleanUp(conn, null, ps, rs);
			}
		}
		else {
			String areaSql = "select distinct t.region,t.addr2 from spc_building t ";
			JSONArray markets = new JSONArray();
			try {
				conn = getConnection();
				ps = conn.prepareStatement(areaSql);
				rs = ps.executeQuery();
				while (rs.next()) {
					//市
					JSONObject market = new JSONObject();
					market.put("code",rs.getString("region")==null?"":rs.getString("region"));
					market.put("name",rs.getString("addr2")==null?"":rs.getString("addr2"));
					markets.add(market);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				results.put("markets",markets);
				cleanUp(conn, null, ps, rs);
			}
			areaSql = "select distinct t.region,t.addr3 from spc_building t ";
			JSONArray regions = new JSONArray();
			try {
				conn = getConnection();
				ps = conn.prepareStatement(areaSql);
				rs = ps.executeQuery();
				while (rs.next()) {
					//区
					JSONObject region = new JSONObject();
					region.put("parentCode",rs.getString("region")==null?"":rs.getString("region"));
					region.put("name",rs.getString("addr3")==null?"":rs.getString("addr3"));
					regions.add(region);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				results.put("regions",regions);
				cleanUp(conn, null, ps, rs);
			}
		}
		return  results.toString();
	}

	public Integer insertUnbindAuditing(String brasIp,String oltIp,String staffId,String unbindtime) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		int checkBrasIp = 0;
		String checkSql =  "select count(*) from AAA_OLT_UNBIND where bras_ip = ? and olt_ip = ? and EXEC_NUM = 0 and state='0'";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(checkSql);
			ps.setString(1,brasIp);
			ps.setString(2,oltIp);
			rs = ps.executeQuery();
			if (rs.next()) {
				//区
				 checkBrasIp = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			cleanUp(conn, null, ps, rs);
		}
		if(checkBrasIp>0){
			return 0;
		}
		else {
			String sql = "INSERT INTO AAA_OLT_UNBIND (BRAS_IP,OLT_IP,APPLY_STAFF_ID,APPLY_STAFF_NAME,APPLY_TIME,REGION_NAME,EXEC_NUM,AUDIT_RESULT,STATE,VALID_DATE) values (?,?,?,(select staff_name from UOS_STAFF where staff_id = ?),sysdate,(SELECT U.AREA_NAME     \n" +
					"FROM VW_STAFF_QUERY T \n" +
					"LEFT JOIN UOS_ORG UO ON UO.ORG_ID = T.org_id \n" +
					"LEFT JOIN UOS_AREA UA ON UA.AREA_ID = UO.AREA_ID\n" +
					"LEFT JOIN (SELECT U.* FROM UOS_AREA U where U.GRADE = 'C3') U ON U.ACRONYM = UA.ACRONYM\n" +
					"WHERE T.staff_id = ? and rownum=1),0,'2','0',to_date(?,'yyyy/mm/dd'))";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, brasIp);
				ps.setString(2, oltIp);
				ps.setString(3, staffId);
				ps.setString(4, staffId);
				ps.setString(5, staffId);
				ps.setString(6, unbindtime);
				count = ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("入库失败!");
			} finally {
				cleanUp(conn, null, ps, rs);
			}
			if (count > 0) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	public Integer updateUnbindAuditing(String staffId,String choose,String ids,String opinion) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "update AAA_OLT_UNBIND t\n" +
				"       set t.AUDIT_TIME = sysdate, t.AUDIT_STAFF_ID = ? ,t.AUDIT_STAFF = (select staff_name from UOS_STAFF where staff_id = ?) ,t.AUDIT_RESULT = ?,t.AUDIT_OPINION = ? \n" +
				"where t.id = ? ";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, staffId);
				ps.setString(2, staffId);
				ps.setString(3, choose);
				ps.setString(4, opinion);
				ps.setString(5, ids);

				count = ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, ps, rs);
			}
			if (count > 0) {
				return 1;
			} else {
				return 2;
			}
	}

	public JSONArray getOltUnbindInf(String staffId,String checkState,String selInfo,String timer1,String timer2,String state,int page,int pageSize) {
		JSONArray jsonArray = new JSONArray();
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		sb.append("SELECT * FROM   \n" +
				"(  \n" +
				"SELECT temp.*, ROWNUM RN   \n" +
				"FROM (select * from AAA_OLT_UNBIND t where APPLY_STAFF_ID ");
		if(StringUtils.hasText(staffId)) {
			sb.append("= ? \n");
		}
		else {
			sb.append(" is not null \n");
		}
		if (StringUtils.hasText(checkState)) {
			sb.append("and AUDIT_RESULT = ?");
		}
		if (StringUtils.hasText(selInfo)) {
			sb.append("and BRAS_IP like ?");
		}
		if (timer1 != null && timer2 != null) {
			sb.append("and APPLY_TIME BETWEEN to_date(?,'yyyy/mm/dd')  AND to_date(?,'yyyy/mm/dd')");
		}
		if (StringUtils.hasText(state)) {
			sb.append("and STATE = ? ");
		}
		sb.append(" ORDER BY APPLY_TIME DESC) temp  \n" +
				"WHERE ROWNUM <= ?  \n" +
				")  \n" +
				"WHERE RN > ?");

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sb.toString());
			int i = 1;
			if(StringUtils.hasText(staffId)){
			ps.setString(1, staffId);}
			else {
				i=0;
			}
			if (StringUtils.hasText(checkState)) {
				i++;
				ps.setString(i, checkState);
			}
			if (StringUtils.hasText(selInfo)) {
				i++;
				ps.setString(i, "%" + selInfo + "%");
			}
			if (timer1 != null && timer2 != null) {
				i++;
				ps.setString(i, timer1);
				i++;
				ps.setString(i, timer2);
			}
			if (StringUtils.hasText(state)) {
				i++;
				ps.setString(i, state);
			}
			i++;
			ps.setInt(i,page*pageSize);
			i++;
			ps.setInt(i,(page-1)*pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject result = new JSONObject();
				result.put("ID",rs.getObject("ID")==null?"":rs.getObject("ID"));
				result.put("BRAS_IP",rs.getObject("BRAS_IP")==null?"":rs.getObject("BRAS_IP"));
				result.put("OLT_IP",rs.getObject("OLT_IP")==null?"":rs.getObject("OLT_IP"));
				result.put("APPLY_STAFF_NAME",rs.getObject("APPLY_STAFF_NAME")==null?"":rs.getObject("APPLY_STAFF_NAME"));
				result.put("APPLY_TIME",String.valueOf(rs.getDate("APPLY_TIME")==null?"":rs.getDate("APPLY_TIME")));
				result.put("AUDIT_STAFF",rs.getObject("AUDIT_STAFF")==null?"":rs.getObject("AUDIT_STAFF"));
				result.put("AUDIT_TIME",String.valueOf(rs.getDate("AUDIT_TIME")==null?"":rs.getDate("AUDIT_TIME")));
				result.put("AUDIT_RESULT",rs.getObject("AUDIT_RESULT")==null?"":rs.getObject("AUDIT_RESULT"));
				result.put("AUDIT_OPINION",rs.getObject("AUDIT_OPINION")==null?"":rs.getObject("AUDIT_OPINION"));
				result.put("STATE",rs.getObject("STATE")==null?"":rs.getObject("STATE"));
				result.put("REGION_NAME",rs.getObject("REGION_NAME")==null?"":rs.getObject("REGION_NAME"));

				result.put("VALID_DATE",String.valueOf(rs.getDate("VALID_DATE")==null?"":rs.getDate("VALID_DATE")));
				jsonArray.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return  jsonArray;
	}


	public String getOltAuditingInfoListById(String staffId,String checkState,String selInfo,String timer1,String timer2,String state,int page,int pageSize) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		if(page == 1) {
			StringBuffer sb = new StringBuffer();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			sb.append("select count(*) from AAA_OLT_UNBIND t where APPLY_STAFF_ID ");
			if(StringUtils.hasText(staffId)) {
				sb.append("= ? \n");
			}
			else {
				sb.append(" is not null \n");
			}
			//是否被审批
			if (StringUtils.hasText(checkState)) {
				sb.append("and AUDIT_RESULT = ?");
			}
			if (StringUtils.hasText(selInfo)) {
				sb.append("and BRAS_IP like ?");
			}
			if (StringUtils.hasText(state)) {
				sb.append("and STATE = ? ");
			}
			if (timer1 != null && timer2 != null) {
				sb.append("and APPLY_TIME BETWEEN to_date(?,'yyyy/mm/dd')  AND to_date(?,'yyyy/mm/dd')");
			}

			try {
				conn = getConnection();
				ps = conn.prepareStatement(sb.toString());
				int i = 1;
				if(StringUtils.hasText(staffId)){
					ps.setString(1, staffId);}
				else {
					i = 0;
				}
				if (StringUtils.hasText(checkState)) {
					i++;
					ps.setString(i, checkState);
				}
				if (StringUtils.hasText(selInfo)) {
					i++;
					ps.setString(i, "%" + selInfo + "%");
				}
				if (StringUtils.hasText(state)) {
					i++;
					ps.setString(i, state);
				}
				if (timer1 != null && timer2 != null) {
					i++;
					ps.setString(i, timer1);
					ps.setString(i + 1, timer2);
				}

				rs = ps.executeQuery();
				if (rs.next()) {
					if(Integer.parseInt(rs.getString("count(*)"))<=pageSize){
						jsonObject.put("totalpage","1");
					}else {
						if(Integer.parseInt(rs.getString("count(*)"))  % pageSize == 0){
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize;
							jsonObject.put("totalpage",String.valueOf(j));
						}else {
							int j = Integer.parseInt(rs.getString("count(*)"))  / pageSize +1;
							jsonObject.put("totalpage",String.valueOf(j));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, ps, rs);
			}
			jsonObject.put("result",getOltUnbindInf(staffId, checkState, selInfo, timer1, timer2,state, page, pageSize));
			return jsonObject.toString();
		}
		else {
			jsonObject.put("result",getOltUnbindInf(staffId, checkState, selInfo, timer1, timer2,state, page, pageSize));
			return jsonObject.toString();
		}
	}

	public void writeOltUnbindLogToDb(Map<String, Object> map, String msg) throws SQLException {

			String sql = "INSERT INTO AAA_UNBIND_LOG" +
					"(STAFF_ID,CREATE_DATE,BRAS_IP,OLT_IP,RET_MSG)" +
					" VALUES(?,SYSDATE,?,?,?)";
			Connection conn = null;
			PreparedStatement ps = null;

			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setObject(1, map.get("staffId"));
				ps.setObject(2, map.get("brasIp"));
				ps.setObject(3, map.get("oltIp"));
				ps.setObject(4, msg);
				ps.execute();
			} finally {
				cleanUp(conn, null, ps, null);
			}
	}

	public void updateOltUnbindAuditing(String id,String staffId) throws SQLException {

		String sql = "update AAA_OLT_UNBIND t\n" +
				"   set t.EXEC_DATE = sysdate, t.EXEC_STAFF_ID = ? ,t.EXEC_STAFF_NAME = (select staff_name from UOS_STAFF where staff_id = ?) ,t.EXEC_NUM = t.EXEC_NUM + 1 \n" +
				" where t.id = ? ";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setObject(1, staffId);
			ps.setObject(2, staffId);
			ps.setObject(3, id);
			ps.execute();
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}

	public void updateOltUnbindAuditingState(String id) throws SQLException {

		String sql = "update AAA_OLT_UNBIND t\n" +
				"   set t.STATE = '1' \n" +
				" where t.id = ? ";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setObject(1, id);
			ps.execute();
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}

	public String selWorkOrderZQSVIP(String workOrderID) {
		JSONObject jsonObject = new JSONObject();
		JSONObject res = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT FA1.PARAM_NAME 故障类型, \n" +
				"FA2.PARAM_NAME 客户类型,\n" +
				"FA3.PARAM_NAME 工单类型 ,\n" +
				"FA4.PARAM_NAME 客户特殊身份 ,\n" +
				"T.ZQSVIP  isGeVIP,\n" +
				"T.SHOWINFOREQV2  diaDetails ,\n" +
				"T.BROADBANDDIAGNOSISSEQV2 diaInfo\n" +
				"FROM ACCEPT_FAULT_ORDER_INFO T \n" +
				"LEFT JOIN fault_order_info_config FA1 ON (FA1.STYPE = 'COMPAINT_PHENOMENON' AND FA1.PARAM_CODE = T.COMPAINT_PHENOMENON)\n" +
				"LEFT JOIN fault_order_info_config FA2 ON (FA2.STYPE = 'IMPORT_TANCE' AND FA2.PARAM_CODE = T.IMPORT_TANCE)\n" +
				"LEFT JOIN fault_order_info_config FA3 ON (FA3.STYPE = 'NET_WORK_FIELD' AND FA3.PARAM_CODE = T.NET_WORK_FIELD)\n" +
				"LEFT JOIN fault_order_info_config FA4 ON (FA4.STYPE = 'CONTACT_LEVE' AND FA4.PARAM_CODE = T.Contact_Leve)\n" +
				"WHERE T.CS_SHEET_ID = (SELECT OO.ORDER_CODE FROM WO_WORK_ORDER W \n" +
				"LEFT JOIN OM_ORDER OO ON OO.ID = W.BASE_ORDER_ID\n" +
				"WHERE W.ID = ?)\n" +
				"　and rownum = 1";
			try {
				conn = getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, workOrderID);
				rs = ps.executeQuery();
				if (rs.next()) {

					res.put("isGeVIP",rs.getObject("isGeVIP")==null?"":rs.getObject("isGeVIP"));
					res.put("diaDetails",rs.getObject("diaDetails")==null?"":rs.getObject("diaDetails"));
					res.put("diaInfo",rs.getObject("diaInfo")==null?"":rs.getObject("diaInfo"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, ps, rs);
			}
			jsonObject.put("result",res);
			return jsonObject.toString();
	}
	public String selWorkOrderChannel(String workOrderID) {
		JSONObject jsonObject = new JSONObject();
		JSONObject res = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT (SELECT T.PNAME FROM  hnlt_gk.OS_PUBLIC T  WHERE T.STYPE = 'ACCEPT_CHANNEL' AND T.PKEY = A.ACCEPT_CHANNEL ) as acceptCancel, --受理渠道\n" +
				" (SELECT T.PNAME FROM  hnlt_gk.OS_PUBLIC T  WHERE T.STYPE = 'COMMIT_CHANNEL' AND T.PKEY = A.COMMIT_CHANNEL ) as  subCancel--提交渠道\n" +
				" FROM hnlt_gk.OM_ORDER OO \n" +
				" LEFT JOIN hnlt_gk.ACCEPT_FAULT_ORDER_INFO A ON A.CS_SHEET_ID = OO.ORDER_CODE\n" +
				" LEFT JOIN hnlt_gk.WO_WORK_ORDER WWO ON WWO.BASE_ORDER_ID = OO.ID\n" +
				" WHERE WWO.ID = ?\n" +
				" AND ROWNUM = 1";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, workOrderID);
			rs = ps.executeQuery();
			if (rs.next()) {
				res.put("acceptCancel",rs.getObject("acceptCancel")==null?"":rs.getObject("acceptCancel"));
				res.put("subCancel",rs.getObject("subCancel")==null?"":rs.getObject("subCancel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		jsonObject.put("result",res);
		return jsonObject.toString();
	}
}
//	public JdbcTemplate jdbcTemplate1 = new JdbcTemplate(
//			new DriverDataSource("jdbc:oracle:thin:@192.168.101.190:1521/gkdb1",
//					"oracle.jdbc.driver.OracleDriver",
//					System.getProperties(),
//					"reshnlt",
//					"reshnlt"));