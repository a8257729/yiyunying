package com.ztesoft.mobile.v2.dao.common;

import com.ztesoft.eoms.common.db.DbOper;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import oracle.jdbc.internal.OracleTypes;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class CommonDAOImpl extends BaseDAOImpl implements CommonDAO {

	private static final Logger logger = Logger.getLogger(CommonDAOImpl.class);


	public Map<String, Object> commonQueryObjectBySql(String sqlStr, Map paramMap, String wherePatternStr) {
		Map<String, Object> map = null;
		try {
			//logger.info("查询sql：\n" + sqlStr);
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
			//logger.info("查询sql：\n" + sqlStr);
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
			//logger.info("<<call procedure>>:" + procedureName);
			//logger.info("<<paramList>>:" + paramList.toString());

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
					}else if(paramClass.isInstance(new Long(0))){
						callStmt.setLong(i+1, (Long) paramList.get(i));
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
	
	public boolean commonInsertObjectBySql(String sqlStr, Map paramMap) {
		Connection conn = null;
		PreparedStatement callStmt = null;
		List<Object> paramList = new ArrayList<Object>();
		Set<String> keySet = paramMap.keySet();
		// 入参值
		for (String key : keySet) {
			paramList.add(paramMap.get(key));
		}
		//logger.info("执行sql:" + sqlStr);
		try {
			conn = getConnection();
			callStmt = conn.prepareStatement(sqlStr);
			for (int i = 0; i < paramList.size(); i++) {
				Class<? extends Object> paramClass = paramList.get(i).getClass();
				System.out.println(paramClass.getName());
				if (paramClass.isInstance(new String(""))) {
					callStmt.setString(i + 1, (String) paramList.get(i));
				} else if (paramClass.isInstance(new Integer(0))) {
					callStmt.setInt(i + 1, (Integer) paramList.get(i));
				} else if (paramClass.isInstance(new Double(0.0))) {
					callStmt.setDouble(i + 1, (Double) paramList.get(i));
				} else if (paramClass.isInstance(new BigDecimal(0))) {
					callStmt.setBigDecimal(i + 1, (BigDecimal) paramList.get(i));
				}else if(paramClass.isInstance(new Timestamp(0))){
					callStmt.setTimestamp(i + 1, (Timestamp) paramList.get(i));
				}else if(paramClass.isInstance(new Long(0))){
					callStmt.setLong(i+1, (Long) paramList.get(i));
				}
			}
			callStmt.execute();

		} catch (SQLException e) {
			logger.error("执行sql错误，" + e.getMessage());
			return false;
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return true;
	}

	public int commonQueryTotalBySql(String sqlStr, Map paramMap,String wherePatternStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		try {
			logger.info("查询sql：\n" + sqlStr);
			count = dynamicQueryTotalByMap(sqlStr, paramMap);
		} catch (com.ztesoft.eoms.exception.DataAccessException e) {
			logger.error("查询信息失败，paramMap：" + paramMap.toString() + "\n" + e.getMessage());
		}
		return count;
	}

	protected int dynamicQueryTotalByMap(String sqlStr,Map<String,Object> paramMap) throws com.ztesoft.eoms.exception.DataAccessException {
		Connection conn = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		int count = 0;
		Object fValue = null;
		Class fType = null;
		try {
			conn = getConnection();
			DbOper dbOper = new DbOper(conn);
			ps = dbOper.prepareStatement(sqlStr);
			int i = 0;
			for(String key:paramMap.keySet()){
				++i;
				fValue = MapUtils.getObject(paramMap, key);
				fType = fValue.getClass();
				if(fType.isInstance(new Integer(0))){
					ps.setInt(i, (Integer)fValue);
				}
				if(fType.isInstance(new String(""))){
					ps.setString(i, (String)fValue);;
				}

			}
			rs = ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					count = rs.getInt("total");
				}
			}
			return count;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new com.ztesoft.eoms.exception.DataAccessException(ex.getMessage(), ex);
		} finally {
			DbOper.cleanUp(rs, null, ps, conn);;
		}

	}
}
