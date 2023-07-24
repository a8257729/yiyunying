package com.ztesoft.mobile.v2.service.resource;

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
 * 资源预判模块服务
 */
public interface ResourceService {

	Result getResourceInfoByQrcode(String qrCode);

	Result queryUserInfo(String portId, String qrCode);
	
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

	/**
	 * 资源清查分光器
	 * @param orgId
	 * @param direName
	 * @param equipmentName
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	String getResourceInfoBySpectroscope(String orgId, String direName, String equipmentName, Integer pageSize, Integer pageIndex,String staffId,String condition);
	//查询资源整治订单
	String getResourceOrderBySpectroscope(String direName,String condition,String timer1,String timer2,Integer pageIndex,Integer pageSize,String staffId);
	//查订单的obd
	String getObdOrderById(String id);
	//修改资源清查订单的状态
	void updateResCleanOrderState(String id);
	//挂测查询
	String getProjectobddevice(String orgId,String equipmentName,Integer pageSize, Integer pageIndex,String staffId);
	//资源分光器详情查询
	Result getResourceInfoBySpectroscopeInfo(String eqpId);
	//资源分光器端口详情查询
	String getResourceInfoBySpectroscopePort(String eqpId);
	//覆盖地址
	String getResourceInfoByCoverAddress(String eqpId);
	//所有覆盖地址
	String getResourceInfoByAllCoverAddress(String eqpId,Integer pageSize,Integer pageIndex);
	//查询olt
	String getOltInfoByEqpName(String eqpName);
	//绑定地址坐标Address coordinates
	String bindAddressCoordinates(String x,String y,String eqpId,String staffId);
	//写入替换OLT的日志
	void writeReplaceOltInfo(String portId,String ponId,String eqpId,String staffId);
	//用户信息查询
	String getUserInfoByLoid(String loid);
	//扫码查询obd
	Result getResourceInfoBySn(String digCode);
	//楼宇查询数量
	String getBuildingNum(String staffId);
	//返回地市级联
	String getBuildingAddressList(String staffId);
	//插入OLT解绑申请信息
	String insertAuditingInfo(String brasIp,String oltIp,String staffId,String unbindtime);
	//查询OLT解绑申请信息(个人）
	String getOltAuditingInfoByStaffId(String staffId,String checkState,String selInfo,String timer1,String timer2,String state,int page,int pageSize);
	//写入日志
	void writeOltUnbindLog(Map<String,Object> map,String msg);
	//修改信息
	void updateOltUnbindInfo(String id,String staffId);
	//修改OLT解绑状态
	void updateOltUnbindStateById(String id);
	//审批OLT解绑申请信息
	String updateOltAuditingState(String staffId,String choose,String ids,String opinion);
	//故障单查询是否政企SVIP
	String getWorkOrderZQSVIP(String workOrderID);
	//故障单的受理渠道和提交渠道
	String getWorkOrderChannel(String workOrderID);

}
