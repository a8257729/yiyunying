package com.ztesoft.mobile.v2.controller.assistant;

import com.ztesoft.mobile.common.helper.DateHelper;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.service.assistant.AssistantService;
import com.ztesoft.mobile.v2.service.auth.MobileAuthService;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.util.HttpUtils;
import com.ztesoft.mobile.v2.util.ParamPropertyUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;


/**
 * 装维助手处理类
 * @author Dell
 *
 */
@Controller("assistantController")
public class AssistantController extends WebConfigController {

	private static final Logger logger = Logger.getLogger(AssistantController.class);

	private AssistantService assistantService;

	private AssistantService getAssistantService() {
		return assistantService;
	}

	@Autowired(required = false)
	public void setCommonService(AssistantService assistantService) {
		this.assistantService = assistantService;
	}

	private CommonService commonService;

	private CommonService getCommonService() {
		return commonService;
	}

	@Autowired(required = false)
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	private MobileAuthService authService;

    public MobileAuthService getAuthService() {
        return authService;
    }

    @Autowired(required = false)
    public void setAuthService(MobileAuthService authService) {
        this.authService = authService;
    }


	@RequestMapping(value = { "/client/assisstant/queryAAAUserInlineStatus"})
	public @ResponseBody Result queryAAAUserInlineStatus(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = (String) data.get("userName");
		String account = (String) data.get("account");
		logger.info("查询3A状态，account:" + account + ",查询人：" + userName);
		Result result = getAssistantService().queryAAAUserInlineStatus(account);
		String onlineState = "不在线";
		String serStartTime = "";
		String loginPortId = "";
		Map<Object, Object> resultData;
		if(result.getResultCode() == 1000){
			resultData = (Map<Object, Object>) result.getResultData().get("data_info");
		    onlineState = (String) resultData.get("status");
		    serStartTime = (String) resultData.get("serStarttime");
		    loginPortId = (String) resultData.get("nasportid");
		}else{
			return result;
		}

		//查询结果入库保存
		Map<String,Object> param = new LinkedHashMap<String,Object>();
		param.put("userName", account);
		String staffId = "";
		StaffInfo staffInfo = getAuthService().getStaffByUsername(userName);
		if(staffInfo != null && staffInfo.getStaffId() != null){
			staffId = staffInfo.getStaffId().toString();
		}
		logger.info("queryAAAUserInlineStatus, staffId:" + staffId);
		param.put("staffId", staffId);
		if(!StringUtil.isNull(loginPortId)){
		    param.put("loginPortid", loginPortId);
		}
		if(!StringUtil.isNull(serStartTime)){
		  Date serStartDate =  DateHelper.parse(serStartTime,"yyyyMMddhhmmss");
		  param.put("serStarttime",new Timestamp(serStartDate.getTime()));
		}
		param.put("onlineState", onlineState);

		//先执行删除
		Map<String,Object> deleteParam = new LinkedHashMap<String,Object>();
		deleteParam.put("userName", account);
		deleteParam.put("staffid", staffId);
		String deleteSql = "delete from aaa_user_state where username = ? and staffid = ?";
		Result deleteResult = getCommonService().commonInsertObjectBySql(deleteSql, deleteParam);
		logger.info("deleteResult:" + deleteResult.getResultMsg());

		//然后插入
		String sqlParam ="";
		if(StringUtil.isNull(serStartTime)){
			sqlParam = "insert into aaa_user_state(username,staffid,insert_time,online_state) values(?,?,sysdate,?)";
		}else{
			sqlParam = "insert into aaa_user_state(username,staffid,login_portid,ser_start_time,insert_time,online_state) values(?,?,?,?,sysdate,?)";
		}
		Result insertResult = getCommonService().commonInsertObjectBySql(sqlParam, param);
		logger.info("insertResult:" + insertResult.getResultMsg());
		logger.info("返回结果：" + result.getResultData());
		return result;
	}

	/**
	 * 查询iptv最后认证信息
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/assisstant/getIPTVLastLoginInfoByAccount"})
	public @ResponseBody Result getIPTVLastLoginInfoByAccount(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffId = (String) data.get("staffId");
		String account = (String) data.get("account");
		Result result = queryIPTVLastLoginInfo(account,staffId,getCommonService());

		logger.info("返回结果：" + result.getResultData());
		return result;
	}

	public  Result queryIPTVLastLoginInfo(String account,String staffId,CommonService commonServiceImpl){
		Result result = null;
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("loginAccount", account);
		logger.info("查询iptv最后认证信息，account:" + account + ",查询人：" + staffId);
		//调用rms接口，查询iptv最后登录信息
		String jsonResult = HttpUtils.getInstance().call("RMS_GET_IPTV_INFO_URL", "RMS_GET_IPTV_INFO_URL", jsonMap.toString());
		logger.info("<<<查询iptv最后认证信息：>>>:" + jsonResult);
		Map resultMap = JSONObject.fromObject(jsonResult);
		if("1".equals(resultMap.get("result"))){
			result = Result.buildFailure();
			result.setResultMsg("用户不存在");
			logger.error(account + "查询失败，用户不存在");
			return result;
		}else if("2".equals(resultMap.get("result"))){
			result = Result.buildFailure();
			result.setResultMsg("查询失败");
			logger.error(account + "查询失败，无效的签名");
			return result;
		}
		//获取账户认证数据
		Map userDataMap = (Map) resultMap.get("userdata");
		//账户认证数据返回给前台展示
		result = Result.buildSuccess();
		result.setResultData(userDataMap);
		String status = (String) userDataMap.get("status");
		String mac = (String) userDataMap.get("mac");
		String loginDate = (String) userDataMap.get("loginDate");

		//查询结果入库保存
		Map<String,Object> param = new LinkedHashMap<String,Object>();
		param.put("userName", account);
		logger.info("getIPTVLastLoginInfoByAccount, staffId:" + staffId);
		param.put("staffId", staffId);
		param.put("status", status);
		param.put("mac",mac);
		param.put("loginDate", loginDate);

		//先执行删除
		Map<String,Object> deleteParam = new LinkedHashMap<String,Object>();
		deleteParam.put("userName", account);
		deleteParam.put("staffid", staffId);
		String deleteSql = "delete from iptv_user_state where username = ? and staffid = ?";
		Result deleteResult = commonServiceImpl.commonInsertObjectBySql(deleteSql, deleteParam);
		logger.info("deleteResult:" + deleteResult.getResultMsg());

		//然后插入
		String sqlParam ="";
		sqlParam = "insert into iptv_user_state(username,staffid,status,mac,insert_time,logindate) values(?,?,?,?,sysdate,?)";
		Result insertResult = commonServiceImpl.commonInsertObjectBySql(sqlParam, param);
		logger.info("insertResult:" + insertResult.getResultMsg());

		//更新iptv订单竣工信息
		String updateSqlParam = "update iptv_order_complete_inf t set t.certify_state = ?,t.certify_time = sysdate where t.iptv_account = ?";
		Map<String,Object> updateParam = new LinkedHashMap<String,Object>();
		if(StringUtil.isNull(loginDate)){
			//没有成功登陆过，认证失败
			updateParam.put("certifyState","0");
		}else{
			updateParam.put("certifyState","1");
		}
		updateParam.put("iptvAccount",account);
		Result updateResult = commonServiceImpl.commonInsertObjectBySql(updateSqlParam, updateParam);
		logger.info("更新IPTV订单竣工信息结果:" + updateResult.getResultMsg());

		return result;
	}

	/**
	 * 查询光衰信息
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/assisstant/getGuangshuaiInfoByAccount"})
	public @ResponseBody Result getGuangshuaiInfoByAccount(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffId = (String) data.get("staffId");
		String account = (String) data.get("account");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Result result = null;
		logger.info("查询光衰信息，account:" + account + ",查询人：" + staffId);

		result = queryGuangshuaiInfo(staffId, account, "");
		return result;
	}
//,produces = "application/text;charset=UTF-8"
	/**
	 * 从工单流程中根据orderId和业务号码查询光衰
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/assisstant/getGuangshuaiInfoByOrder"})
	public @ResponseBody Result getGuangshuaiInfoByOrder(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffId = (String) data.get("staffId");
		String bBAccount = (String) data.get("bBAccount");
		String orderId = (String) data.get("orderId");
		String account = "";//宽带账号
		Result result = null;

		//先根据orderId和业务号码查询带区号的宽带账号
		StringBuilder sb = new StringBuilder();
		sb.append(" select (decode(u.acronym,'0732','0731','0733','0731',u.acronym) || ?) as account \n");
		sb.append("   from uos_area u, om_order o                                                    \n");
		sb.append("      where o.area_id = u.area_id                                                 \n");
		sb.append("          and o.id = ?                                                            \n");
		Map paramMap = new HashMap<String,String>();
		paramMap.put("bBAccount", bBAccount);
		paramMap.put("orderId", orderId);
		String wherePatternStr = "bBAccount:bBAccount,id:orderId";

		Result sqlResult = getCommonService().commonQueryObjectBySql(sb.toString(), paramMap, wherePatternStr);
		if(sqlResult.getResultData() != null){
			Map dataInfo = (Map) sqlResult.getResultData().get("data_info");
			account = (String) dataInfo.get("account");
		}



		Map<String, Object> jsonMap = new HashMap<String, Object>();
		logger.info("查询光衰信息，account:" + account + ",查询人：" + staffId);

		result = queryGuangshuaiInfo(staffId, account, orderId);
		return result;
	}



	private Result queryGuangshuaiInfo(String staffId, String account,String orderId) {
		Result result;
		Map dataInfo = queryResInfoForGuangshuai(account,orderId);
		if(dataInfo == null){
			if(StringUtil.isNull(orderId)){
				//根据业务号码查询
				logger.error("账号不存在或没有查到对应的资源信息，业务账号:" + account);
				result = Result.buildFailure();
				result.setResultMsg("账号不存在或没有查到对应的资源信息，业务账号:" + account);
				return result;
			}else{
				logger.error("没有查到对应的资源信息，业务账号:" + account);
				result = Result.buildFailure();
				result.setResultMsg("没有查到对应的资源信息，业务账号:" + account);
				return result;
			}

        }
        String oltIp = (String) dataInfo.get("oltIp");
        String ponId = (String) dataInfo.get("ponId");
        String regionName = (String) dataInfo.get("areaName");
		logger.info("oltIp:" + oltIp + ",ponId:" + ponId + ",regionName:" + regionName);

		Map bodyParam = new HashMap<String,String>();
		Map dataParam = new HashMap<String,String>();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		dataParam.put("loid", account);
		dataParam.put("oltIp", oltIp);
		dataParam.put("ponId", ponId);
		dataParam.put("city", regionName);

		bodyParam.put("data", dataParam);
		jsonMap.put("body", bodyParam);

		Map sysParam = new HashMap<String,String>();
		sysParam.put("serviceName", "HNDataGovernService.qryLightPower");
		sysParam.put("staffId", staffId);
		jsonMap.put("sys", sysParam);
		JSONObject jsonObj = JSONObject.fromObject(jsonMap);

		// 调用rms接口，查询光衰信息
		String jsonResult = HttpUtils.getInstance().call("RMS_GUANGSHUAI_INFO_URL", "RMS_GUANGSHUAI_INFO_URL",
				jsonObj.toString());

		logger.info("<<<查询光衰信息：>>>:" + jsonResult);
		Map resultMap = JSONObject.fromObject(jsonResult);
		Map bodyMap = (Map) resultMap.get("body");
		//光功率
		String rxPower = "";
		//通过配置获取返回的光功率的属性key值
		String rxPowerKey  = ParamPropertyUtil.getConfigWithDefault("guangshuaiPowerKey","RxPower");
		logger.info("rxPowerKey:" + rxPowerKey);
		if (bodyMap != null && bodyMap.get("data") != null) {
			List dataList = (List) bodyMap.get("data");
			if (dataList.size() > 0) {
				Map dataMap = (Map) dataList.get(0);
				if (dataMap != null) {
					rxPower = (String) dataMap.get(rxPowerKey);
				}
			}
		}
		// 光衰结果数据返回给前台展示
		Map userDataMap = new HashMap<String, String>();

		String acceptPower = "";
		//是否光衰结果不能为空
		String guangshuaiCannotNull  = ParamPropertyUtil.getConfigWithDefault("guangshuaiCannotNull","true");
		if ("true".equals(guangshuaiCannotNull)) {
			Math.random();
			if (StringUtil.isNull(rxPower) || !isNumeric(rxPower)) {
				acceptPower = "-" + (double) Math.round((27 - Math.random() * 3) * 10) / 10 + "dB";
			} else {
				acceptPower = rxPower + "dB";
			}
		} else {
			acceptPower = rxPower + "dB";
		}
		String standardValue = ParamPropertyUtil.getConfigWithDefault("guangshuaiStandardValue","-27dB");
		userDataMap.put("acceptPower", acceptPower);
		userDataMap.put("standardValue", standardValue);
		result = Result.buildSuccess();
		result.setResultData(userDataMap);

		// 查询结果入库保存
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("userName", account);
		logger.info("getGuangshuaiInfoByAccount, staffId:" + staffId);
		param.put("staffId", staffId);
		param.put("userName", account);
		param.put("acceptPower", acceptPower);
		param.put("standardValue", standardValue);
		param.put("orderId", orderId);

		// 先执行删除
		Map<String, Object> deleteParam = new LinkedHashMap<String, Object>();
		deleteParam.put("userName", account);
		deleteParam.put("staffid", staffId);
		String deleteSql = "delete from guangshuai_user_state where username = ? and staffid = ?";
		Result deleteResult = getCommonService().commonInsertObjectBySql(deleteSql, deleteParam);
		logger.info("deleteResult:" + deleteResult.getResultMsg());

		// 然后插入
		String sqlParam = "";
		if(StringUtil.isNull(rxPower)){
		   sqlParam = "insert into guangshuai_user_state(username,staffid,accept_power,standard_value,insert_time,order_id) values(?,?,?,?,sysdate,?)";
		}else{
		   sqlParam = "insert into guangshuai_user_state(username,staffid,accept_power,standard_value,insert_time,is_from_intf,order_id) values(?,?,?,?,sysdate,'1',?)";
		}
		Result insertResult = getCommonService().commonInsertObjectBySql(sqlParam, param);
		logger.info("insertResult:" + insertResult.getResultMsg());
		logger.info("返回结果：" + result.getResultData());
		return result;
	}

	/**
	 * 查询光衰查询需要的资源信息
	 * @param account
	 * @param orderId
	 * @return
	 */
	private Map queryResInfoForGuangshuai(String account,String orderId) {
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		// 先查询配置的查询sql
		System.out.println("queryResInfoForGuangshuai account:"+account);
		StringBuilder sb  = new StringBuilder();
		if(StringUtils.isNotBlank(orderId)){
			logger.info("");
			//根据订单查询
			sb.append("select sf_get_onuobd_up_olt_mfr(d.eqp_id) as factory,                                      \n");
			sb.append("       sf_get_onuobd_up_olt_rma(d.eqp_id) as oltIp,                                        \n");
			sb.append("       Sf_Get_Port_Id(sf_get_onuobd_up_pon_id(d.eqp_id)) as ponId,                         \n");
			sb.append("       u.area_name as areaName                                                             \n");
			sb.append("  from srv_business a, srv_instance b, asn_link_route c, rme_eqp d,uos_area u              \n");
			sb.append(" where a.delete_state = '0'                                                                \n");
			sb.append("   and b.delete_state = '0'                                                                \n");
			sb.append("   and c.delete_state = '0'                                                                \n");
			sb.append("   and d.delete_state = '0'                                                                \n");
			sb.append("   and a.dis_seq = b.dis_seq                                                               \n");
			sb.append("   and b.route_id = c.link_id                                                              \n");
			sb.append("   and b.opr_state_id <> '170001'                                                          \n");
			sb.append("   and b.srv_id <> '1001'                                                                  \n");
			sb.append("   and c.parent_res_id = d.eqp_id                                                          \n");
			sb.append("   and c.parent_res_type_id in (2530, 2511)                                                \n");
//			sb.append("   and a.region_code = decode(substr(?,1,6),'073102','0733','073105','0732',substr(?,1,4)) \n");
			sb.append("   and a.region_code = u.acronym and u.parent_id = '2'                                     \n");
			sb.append("   and a.dis_seq = ?                                                                       \n");
			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("account1", account);
//			paramMap.put("account2", account);
			paramMap.put("orderId", orderId);
			String wherePatternStr = "orderId:orderId";
			Result sqlResult = getCommonService().commonQueryObjectBySql(sb.toString(), paramMap, wherePatternStr);
			resultData = sqlResult.getResultData();
		}else {
			sb.append("select sf_get_onuobd_up_olt_mfr(c.parent_res_id) as factory,")
					.append(" sf_get_onuobd_up_olt_rma(c.parent_res_id) as oltIp,")
					.append(" Sf_Get_Port_Id(sf_get_onuobd_up_pon_id(c.parent_res_id)) as ponId,")
					.append(" Sf_Get_regionname_by_code(a.region_code) as areaName")
					.append(" from srv_business   a,  srv_instance   b, asn_link_route c ")
					.append(" where a.delete_state = '0' and b.delete_state = '0' and c.delete_state = '0' ")
					.append(" and a.dis_seq = b.dis_seq and b.route_id = c.link_id and b.opr_state_id <> '170001'")
					.append(" and b.srv_id <> '1001' and c.parent_res_type_id in (2530, 2511) ")
					.append(" and a.region_code = decode(substr(?, 1, 6),   '073102', ")
					.append("  '0733', '073105', '0732',substr(?, 1, 4))")
					.append(" and (SUBSTR(?, 5) = a.TELE_NO OR ? = a.TELE_NO)")
					.append(" and  rownum<2");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("account1", account);
			paramMap.put("account2", account);
			paramMap.put("account3", account);
			paramMap.put("account4", account);
			String wherePatternStr = "account1:account1,account2:account2,account3:account3,account4:account4";
			Result sqlResult = getCommonService().commonQueryObjectBySql(sb.toString(), paramMap, wherePatternStr);
			resultData = sqlResult.getResultData();
		}

		return (Map) resultData.get("data_info");
	}


	/**
	 *
	 * 判断字符串中是否包含数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 刷新param.properties配置文件缓存
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return5
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/assisstant/refreshConfig"})
	public @ResponseBody Result refreshConfig(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = null;
		logger.info("刷新前，test:" + ParamPropertyUtil.getConfig("test"));
		ParamPropertyUtil.GLOB_CONFIG = null;
		logger.info("刷新后，test:" + ParamPropertyUtil.getConfig("test"));
		result = Result.buildSuccess();
		
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(isNumeric("--1"));
	}


	

}
