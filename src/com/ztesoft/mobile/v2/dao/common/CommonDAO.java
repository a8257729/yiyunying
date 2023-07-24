package com.ztesoft.mobile.v2.dao.common;

import com.ztesoft.mobile.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface CommonDAO extends BaseDAO {


	/**
	 * 普通的sql查询单个对象
	 * @param sql
	 * @param paramMap 参数map，如：map.put("orderId","1125555");key与wherePatternStr中冒号后的名字对应
	 * @param wherePatternStr sql字段和入参对应键值对，如："order_id:orderId,staff_id:staffId"
	 * @return
	 */
	Map<String, Object> commonQueryObjectBySql(String sqlStr, Map paramMap, String wherePatternStr);

	/**
	 * 普通的sql查询数据列表
	 * @param sql
	 * @param paramMap 参数map，如：map.put("orderId","1125555");key与wherePatternStr中冒号后的名字对应
	 * @param wherePatternStr sql字段和入参对应键值对，如："order_id:orderId,staff_id:staffId"
	 * @return
	 */
	Map<String, Object> commonQueryListBySql(String sql, Map<String, Object> paramMap, String wherePatternStr);

	/**
	 * 调用存储过程查询map
	 * @param procedureName 存储过程名
	 * @param paramList 参数列表，出参为一个包含数据类型的初始化对象，如 new String(""),new BigDecimal(0)
	 * @param intParamLength 存储过程入参个数
	 * @param outParam 存储过程出参返回给map的key属性数组，如["orderNo","splitterName"]
	 * @return
	 */
	public Map<String, Object> commonQueryObjectByProcedure(String procedureName, List paramList, int intParamLength,String[] outParam) ;
	
	public boolean commonInsertObjectBySql(String sqlStr, Map paramMap);

	public int commonQueryTotalBySql(String sqlStr,Map paramMap,String wherePatternStr);
}
  