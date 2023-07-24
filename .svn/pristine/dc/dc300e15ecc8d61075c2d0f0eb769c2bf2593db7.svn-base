package com.ztesoft.mobile.v2.dao.weixin;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface WeChatDAO extends BaseDAO {
	public Map queryUserPhoneInf(String staff_id) throws Exception;
	public Map queryUserHouseInfoByCode(String staff_id) throws Exception;
	
	public int insertMap(String log_id,String service_num,String qrcode,String custname) throws Exception;
	public String queryWorkOrderInfoSA(String service_num) throws Exception;
	public String queryWorkOrderInfo(String service_num) throws Exception;

	public String queryorderId(String serviceNum)throws Exception;
	//查装机时间
	public String createDate(String ooid)throws Exception;
	//查光衰
	public String guangshuai(String ooid)throws Exception;
	//测试速率
	public String maxnetspeed(String ooid, String serviceNum)throws Exception;
}
