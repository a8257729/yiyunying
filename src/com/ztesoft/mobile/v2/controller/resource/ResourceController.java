package com.ztesoft.mobile.v2.controller.resource;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.v2.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.resource.ResourceService;
import com.ztesoft.mobile.v2.service.resource.ResourceServiceImpl;

@Controller("ResourceController")
public class ResourceController {
	private static final Logger logger = Logger.getLogger(ResourceController.class);
	private ResourceService resourceService;

	public ResourceService getResourceService() {
		return resourceService;
	}

	@Autowired(required = false)
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * obd二维码查询资源信息
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/resource/resourceInfo/queryResourceInfo" })
	public @ResponseBody Result queryResourceInfo(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Call queryResourceInfo method");

		String qrCode = data.get("qrCode") == null ? "" : (String) data.get("qrCode");
		Result result = this.getResourceService().getResourceInfoByQrcode(qrCode);
		return result;
	}

	//资源清查  分光器查询----------------
	@RequestMapping(value = {"/client/resource/resources_clean/Spectroscope/search"})
	public @ResponseBody String selSpectroscope(@RequestBody Map<String,Object> map) throws Exception {
		String orgId = (String)map.get("orgId");
		String direName = (String)map.get("direName");
		String condition = (String)map.get("condition");
		String equipmentName = (String)map.get("equipmentName");
		Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
		Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
		String staffId = (String) map.get("staffId");
		String result = this.getResourceService().getResourceInfoBySpectroscope(orgId, direName, equipmentName, pageSize, pageIndex,staffId,condition);
		return result;
	}

	//资源清查  分光器查询----------------
	@RequestMapping(value = {"/client/resource/resources_clean/resOrder/search"})
	public @ResponseBody String selResOrder(@RequestBody Map<String,Object> map) throws Exception {
		String direName = (String)map.get("direName");
		String condition = (String)map.get("condition");
		String timer1 =  (String)map.get("timer1");
		String timer2 =  (String)map.get("timer2");
		Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
		Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
		String staffId = (String) map.get("staffId");
		String result = this.getResourceService().getResourceOrderBySpectroscope(direName,condition,timer1,timer2,pageIndex,pageSize,staffId);
		return result;
	}

	@RequestMapping(value = {"/client/resource/resources_clean/obdOrder/search"})
	public @ResponseBody String selObdOrder(@RequestBody Map<String,Object> map) throws Exception {
		String id = (String)map.get("id");
		String result = this.getResourceService().getObdOrderById(id);
		return result;
	}

	//修改清查标识
	@RequestMapping(value = {"/client/resource/resources_clean/obdOrder/updateState"})
	public @ResponseBody void updateOrderState(@RequestBody Map<String,Object> map) throws Exception {
		String id = (String)map.get("id");
		this.getResourceService().updateResCleanOrderState(id);
	}

	//资源清查  挂测查询----------------
	@RequestMapping(value = {"/client/resource/resources_clean/tblprojectobddevice/search"})
	public @ResponseBody String seltblprojectobddevice(@RequestBody Map<String,Object> map) throws Exception {
		String orgId = (String)map.get("orgId");
		String equipmentName = (String)map.get("equipmentName");
		logger.info("Search tblprojectobddevice equipmentName:"+equipmentName);
		Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
		Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
		String staffId = (String) map.get("staffId");
		String result = this.getResourceService().getProjectobddevice(orgId, equipmentName, pageSize, pageIndex,staffId);
		return result;
	}

	//资源清查  分光器详情
	@RequestMapping(value = {"/client/resource/resources_clean/SpectroscopeInfo/search"})
	public @ResponseBody Result selSpectroscopeInfo(@RequestBody Map<String,Object> map) throws Exception {
		String eqpId = map.get("eqpId") == null ? "" : (String)map.get("eqpId");
		logger.info("Search SpectroscopeInfo eqpId:"+eqpId);
		Result result = this.getResourceService().getResourceInfoBySpectroscopeInfo(eqpId);
		return result;
	}

	//分光器详情  端口查询
	@RequestMapping(value = {"/client/resource/resources_clean/SpectroscopePort/search"})
	public @ResponseBody String selSpectroscopePort(@RequestBody Map<String,Object> map) throws Exception {
		String eqpId = map.get("eqpId") == null ? "" : (String)map.get("eqpId");
		logger.info("Search SpectroscopePort eqpId:"+eqpId);
		String result = this.getResourceService().getResourceInfoBySpectroscopePort(eqpId);
		return result;
	}

	//用户信息查询
	@RequestMapping(value = {"/client/resource/resources_clean/userLoid/search"})
	public @ResponseBody String selUserInfo(@RequestBody Map<String,Object> map) throws Exception {
		String loid = map.get("loid") == null ? "" : (String)map.get("loid");
		logger.info("Search UserInfo loid:"+loid);
		String result = this.getResourceService().getUserInfoByLoid(loid);
		return result;
	}

	//覆盖地址  端口查询
	@RequestMapping(value = {"/client/resource/resources_clean/CoverAddress/search"})
	public @ResponseBody String selCoverAddress(@RequestBody Map<String,Object> map) throws Exception {
		String eqpId = map.get("eqpId") == null ? "" : (String)map.get("eqpId");
		logger.info("Search SpectroscopeCoverAddress eqpId:"+eqpId);
		String result = this.getResourceService().getResourceInfoByCoverAddress(eqpId);
		return result;
	}

	//覆盖地址查询
	@RequestMapping(value = {"/client/resource/resources_clean/AllCoverAddress/search"})
	public @ResponseBody String selAllCoverAddress(@RequestBody Map<String,Object> map) throws Exception {
		String address = map.get("address") == null ? "" : (String)map.get("address");
		logger.info("Search AllCoverAddress eqpId:"+address);
		Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
		Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
		String result = this.getResourceService().getResourceInfoByAllCoverAddress(address,pageSize,pageIndex);
		return result;
	}

	//扫码查询obd
	@RequestMapping(value = {"/client/resource/resources_clean/sn/searchObd"})
	public @ResponseBody Result searchOBDBySN(@RequestBody Map<String,Object> map) throws Exception {
		String digCode = map.get("digCode") == null ? "" : (String)map.get("digCode");
		logger.info("Search OBDBySN:"+digCode);
		Result result = this.getResourceService().getResourceInfoBySn(digCode);
		return result;
	}

	//OLT查询
	@RequestMapping(value = {"/client/resource/resources_clean/oltInf/search"})
	public @ResponseBody String selAllOltInf(@RequestBody Map<String,Object> map) throws Exception {
		String eqpName = map.get("eqpName") == null ? "" : (String)map.get("eqpName");
		logger.info("Search oltInf eqpName:"+eqpName);
		String result = this.getResourceService().getOltInfoByEqpName(eqpName);
		return result;
	}


	//olt解绑 管理员审批
	@RequestMapping(value = {"/client/resource/resources_clean/oltInf/opeateByManage"})
	public @ResponseBody String opeateByManage(@RequestBody Map<String,Object> map) throws Exception {
		String staffId = (String)map.get("staffId");
		String choose = (String)map.get("choose");
		String ids = (String)map.get("ids");
		String opinion = (String)map.get("opinion");
		String result = this.getResourceService().updateOltAuditingState(staffId,choose,ids,opinion);
		return result;
	}

	//OLT解绑预约
	@RequestMapping(value = {"/client/resource/resources_clean/oltInf/auditingOltUnbind"})
	public @ResponseBody String auditingOltUnbind(@RequestBody Map<String,Object> map) throws Exception {
		String brasIp = map.get("brasIp") == null ? "" : (String)map.get("brasIp");
		String oltIp = map.get("oltIp") == null ? "" : (String)map.get("oltIp");
		String unbindtime = map.get("unbindtime") == null ? "" : (String)map.get("unbindtime");
		String staffId = map.get("staffId") == null ? "" : (String)map.get("staffId");
		String result = this.getResourceService().insertAuditingInfo(brasIp,oltIp,staffId,unbindtime);
		return result;
	}



	//OLT查询
	@RequestMapping(value = {"/client/resource/OltUnbind/auditingOltUnbind/search"})
	public @ResponseBody String selAllauditingOltUnbindInf(@RequestBody Map<String,Object> map) throws Exception {
		String staffId = (String)map.get("staffId")==null?"":(String)map.get("staffId");
		String checkState =  (String)map.get("checkState")==null?"":(String)map.get("checkState");
		String selInfo =  (String)map.get("selInfo")==null?"":(String)map.get("selInfo");
		String timer1 =  (String)map.get("timer1");
		String timer2 =  (String)map.get("timer2");
		String state =  (String)map.get("state")==null?"":(String)map.get("state");
		int page =  (Integer)map.get("page");
		int pageSize =  (Integer)map.get("pageSize");
		String result = this.getResourceService().getOltAuditingInfoByStaffId(staffId, checkState, selInfo, timer1, timer2, state,page, pageSize);
		return result;
	}

	//故障单查询是否政企SVIP
	@RequestMapping(value = {"/client/faultorder/private/workOrderDetail/ZQSVIP"})
	public @ResponseBody String queryWorkOrderZQSVIP(@RequestBody Map<String,Object> map) throws Exception {
		String workOrderID = (String)map.get("WorkOrderID");
		String result = this.getResourceService().getWorkOrderZQSVIP(workOrderID);
		return result;
	}
	//查询提交渠道和受理渠道
	@RequestMapping(value = {"/client/faultorder/private/workOrderDetail/acceptChannel"})
	public @ResponseBody String queryOrderChannel(@RequestBody Map<String,Object> map) throws Exception {
		String workOrderID = (String)map.get("WorkOrderID");
		String result = this.getResourceService().getWorkOrderChannel(workOrderID);
		return result;
	}


	//解绑
	@RequestMapping(value = {"/client/resource/OltUnbind/operate"},produces = "application/json;charset=UTF-8")
	public @ResponseBody String oltUnbind(@RequestBody Map<String,Object> map) throws Exception {
		JSONObject res = new JSONObject();
		//判断是否在解绑时间内
		String validDate = (String) map.get("validDate");
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date dateObj = calendar.getTime();
		String formattedDate = dtf.format(dateObj);
		Boolean flag = false;
		if(formattedDate.equals(validDate)){
			flag = isBelong();
		}
		if(flag==false){
			//1 "不在申请的解绑时间内！"
			res.put("error","不在申请的解绑时间内！");
			return res.toString();
		}

		String ids =  (String) map.get("ids");
		String staffId = (String) map.get("staffId");


		JSONObject js = new JSONObject();
		js.put("brasIp",(String)map.get("brasIp"));
		js.put("oltIp",(String)map.get("oltIp"));
		String result = null;
		try {
			result = HttpUtil.sendPostBoc("http://192.168.101.175:8082/soaptrans/unbind/UnbindSubscriberService", js.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return "网络请求失败，请联系管理员";
		}
		JSONObject resultJs = JSONObject.fromObject(result);

		String msg = null;
		String resCode = resultJs.getString("ResultCode") == null ? "" :  resultJs.getString("ResultCode");
		if(resCode.equals("406500975")){
			msg = "不存在这个bras节点";
		}else {
			msg = resultJs.getString("ResultDesc") == null ? "未获取到返回结果，请联系管理员" : resultJs.getString("ResultDesc");
		}
		try {
			this.getResourceService().writeOltUnbindLog(map, msg);
			this.getResourceService().updateOltUnbindInfo(ids,staffId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		res.put("msg",msg);
		return res.toString();
	}

	public Boolean isBelong(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Date now =null;
		Date beginTime = null;
		Date endTime = null;
		try {
			now = df.parse(df.format(new Date()));
			beginTime = df.parse("00:00");
			endTime = df.parse("06:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean flag = belongCalendar(now, beginTime, endTime);
		return flag;
	}

	//修补OLT解绑预约单状态
	@RequestMapping(value = {"/client/resource/OltUnbindState/update"})
	public void updateOltUnbindState(@RequestBody Map<String,Object> map) throws Exception {
		String ids =  (String) map.get("ids");

		this.getResourceService().updateOltUnbindStateById(ids);
	}



	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}


	//绑定地址坐标
	@RequestMapping(value = {"/client/resource/resources_clean/bind/addressCoordinates"})
	public @ResponseBody String bindAddressCoordinates(@RequestBody Map<String,Object> map) throws Exception {
		String eqpId = map.get("eqpId") == null ? "" : (String)map.get("eqpId");
		String x = map.get("lon") == null ? "" : (String)map.get("lon");
		String y = map.get("lat") == null ? "" : (String)map.get("lat");
		String staffId = map.get("staffId") == null ? "" : (String)map.get("staffId");
		logger.info("bind addressCoordinates by eqpId:"+eqpId);
		String result = this.getResourceService().bindAddressCoordinates(x,y,eqpId,staffId);
		return result;
	}

	//替换的OLT日志写入
	@RequestMapping(value = {"/client/resource/resources_clean/writeReplaceOlt"})
	public void writeReplaceOlt(@RequestBody Map<String,Object> map) throws Exception {
		String eqpId = map.get("eqpId") == null ? "" : (String)map.get("eqpId");
		String portId = map.get("portId") == null ? "" : (String)map.get("portId");
		String ponId = map.get("ponId") == null ? "" : (String)map.get("ponId");
		String staffId = map.get("staffId") == null ? "" : (String)map.get("staffId");
		this.getResourceService().writeReplaceOltInfo(portId,ponId,eqpId,staffId);
	}

	//判断是否是楼长，并查询周月的数量
	@RequestMapping(value = {"/client/buildingManage/seachResourcesNum"})
	public @ResponseBody String seachBuildingResourcesNum(@RequestBody Map<String,Object> map) throws Exception {
		String staffId = map.get("staffId") == null ? "" : (String)map.get("staffId");
		String buildingNum = this.getResourceService(). getBuildingNum(staffId);
		return buildingNum;
	}

	//根据STAFFID获取地市，根据地市返回级联
	@RequestMapping(value = {"/client/buildingManage/searchAddressList"})
	public @ResponseBody String searchBuildingAddressList(@RequestBody Map<String,Object> map) throws Exception {
		String staffId = map.get("staffId") == null ? "" : (String)map.get("staffId");
		String buildingArea = this.getResourceService().getBuildingAddressList(staffId);
		return buildingArea;
	}

	/**
	 * 根据端口二维码或者portId查询用户端口使用详情
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/resource/resourceInfo/queryUserInfo" })
	public @ResponseBody Result queryUserInfo(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Call queryResourceInfo method");

		String qrCode = data.get("qrCode") == null ? "" : (String) data.get("qrCode");
		String portId = data.get("portId") == null ? "" : (String) data.get("portId");
		logger.info("<<<queryUserInfo>>>,qrCode:" + qrCode + ",portId:" + portId);
		Result result = this.getResourceService().queryUserInfo(portId,qrCode);
		return result;
	}
	
	public static void testObjectBysql() {
		StringBuffer sbf = new StringBuffer();
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
		sbf.append("  SF_GET_DESC_CHINA(c.OPR_STATE_ID) as portState       \n");
		sbf.append("   from pub_two_dimension_code a,                      \n");
		sbf.append("        rme_eqp                b,                      \n");
		sbf.append("        rme_port               c,                      \n");
		sbf.append("        pub_two_dimension_code d,                      \n");
		sbf.append("        ZYQC_ftth_user_2019    e                       \n");
		sbf.append("  where                                                \n");
		sbf.append("  d.digcode = ?                                        \n");
		sbf.append("  and a.delete_state = '0'                             \n");
		sbf.append("  and a.res_id = b.eqp_id                              \n");
		sbf.append("  and c.delete_state = '0'                             \n");
		sbf.append("  and c.port_id = d.res_id                             \n");
		sbf.append("  and d.res_type_id = '6024'                           \n");
		sbf.append("  and c.super_res_id = b.eqp_id                        \n");
		sbf.append("  and b.res_type_id = ?                                \n");
		sbf.append("  and c.port_id = e.assign_port_id                     \n");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("qrCode", "ATC0000170");
		paramMap.put("resTypeId", "2530");
		String wherePatternStr = "digcode:qrCode,res_type_id:resTypeId";
		Result result = new ResourceServiceImpl().commonQueryObjectBySql(sbf.toString(), paramMap, wherePatternStr);
	    System.out.println(result.getResultData().toString()); 
	}
	
	public static void testListBySql(){
		StringBuffer sbf = new StringBuffer();
		sbf.append(" select c.port_id as portId,                                                  \n");
		sbf.append("        d.loid as serviceNum,                                                     \n");
		sbf.append("        d.cust_name as custName,                                                \n");
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
		sbf.append("  where a.digcode = ?                                               \n");
		sbf.append("    and a.delete_state = ?                                        \n");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("qrCode", "AAA0000888");
		paramMap.put("deleteState", "0");
		String wherePatternStr = "digcode:qrCode,delete_state:deleteState";
		Result result = new ResourceServiceImpl().commonQueryListBySql(sbf.toString(), paramMap, wherePatternStr);
	    System.out.println(result.getResultData().toString()); 
	}
	
	public static void testObjectByProcedure(){
		List<Object> list = new ArrayList<Object>();
		list.add("AAA0000888");
		list.add("");list.add("");
		list.add("");list.add("");
		list.add("");list.add("");
		list.add("");list.add("");
		list.add("");
		list.add(new BigDecimal(0));
		list.add(new BigDecimal(0));
		String[] outParams = {"splitterName","eqpId","workMode","protectStyle","oltIP","oltName","pon","ponId","qrCode","posX","posY"};
		Result result = new ResourceServiceImpl().commonQueryObjectByProcedure("Sf_Get_eqpdetail_by_code(?,?,?,?,?,?,?,?,?,?,?,?)", list, 1,outParams);
	    System.out.println(result.getResultData().toString()); 
	}

}
