package com.ztesoft.mobile.v2.service.common;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobileFeedback;
import com.ztesoft.mobile.v2.entity.common.MobilePhotoRecord;
import com.ztesoft.mobile.v2.entity.common.MobileStaffShortcut;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;

/**
 * 公共模块服务
 */
public interface CommonService {

	/**
	 * 普通的sql查询单个对象
	 * 
	 * @param sql
	 * @param paramMap
	 *            参数map，如：map.put("orderId","1125555");
	 *            key与wherePatternStr中冒号后的名字对应
	 * @param wherePatternStr
	 *            sql字段和入参对应键值对，如："order_id:orderId,staff_id:staffId"
	 * @return
	 */
	public Result commonQueryObjectBySql(String sqlStr, Map paramMap, String wherePatternStr);

	/**
	 * 普通的sql查询数据列表
	 * 
	 * @param sql
	 * @param paramMap
	 *            参数map，如：map.put("orderId","1125555");
	 *            key与wherePatternStr中冒号后的名字对应
	 * @param wherePatternStr
	 *            sql字段和入参对应键值对，如："order_id:orderId,staff_id:staffId"
	 * @return
	 */
	public Result commonQueryListBySql(String sql, Map<String, Object> paramMap, String wherePatternStr);

	/**
	 * 调用存储过程查询map
	 * 
	 * @param procedureName
	 *            存储过程名
	 *            ,如：Sf_Get_eqpdetail_by_code(?,?,?,?,?,?,?,?,?,?,?,?)。需要入参全部在前面
	 *            ，出参全部在后面
	 * @param paramList
	 *            参数列表，出参为一个包含数据类型的初始化对象，如 new String(""),new BigDecimal("0")
	 * @param intParamLength
	 *            存储过程入参个数
	 * @param outParam
	 *            存储过程出参返回给map的key属性数组，如["orderNo","splitterName"]。要求返回的为varchar或者Number类型字段
	 * @return
	 */
	public Result commonQueryObjectByProcedure(String procedureName, List paramList, int inParamLength, String[] outParam);

	
	public Result commonInsertObjectBySql(String sqlStr,Map paramMap);

	public int commonQueryTotalBySql(String sqlStr,Map paramMap,String wherePatternStr);
}
