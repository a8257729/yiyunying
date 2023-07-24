package com.ztesoft.mobile.v2.controller.manager;

import com.ztesoft.android.util.SecurityUtil;
import com.ztesoft.eoms.exintf.util.dao.UosConfigDAO;
import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workfloworder.hunan.WorkFlowDao;
import com.ztesoft.mobile.v2.dao.workfloworder.hunan.WorkFlowDaoImpl;
import com.ztesoft.mobile.v2.entity.common.AuthRefresh;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("xjUserManagerController")
public class UserManagerController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(UserManagerController.class);

	private  WorkFlowDao wfdao = new WorkFlowDaoImpl();

	

	/**
	 * 改用户密码--web端
	 * @param Map
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/web/user/changepassword" })
	public @ResponseBody
	Result changeUserPassWord(@RequestBody Map<String,Object> data) {
		Result result = null;
		try{
		if (logger.isDebugEnabled()) { 
			
			
			logger.debug(" Call WorkOrderDetail method ");
		}
		
//		System.out.println("the workOrderID is:"+workOderVO.toString());
//		System.out.println("the orderID is:"+orderID.toString());
		JSONObject js = new JSONObject();
		String userName = (String)data.get("username");
		String oldpassword = (String)data.get("oldpassword");
		String newpassword = (String)data.get("newpassword");
		
		Map map = wfdao.getPasswordByUserName(userName);
		String password = map==null?"":map.get("password")+"";
		String staffId = map==null?"-1":map.get("staffId").toString();
		String paramValue = UosConfigDAO.getInstance().getValue("PASSWORD_ENCODE");
		if("BASE64Encoder".equals(paramValue)){
			oldpassword = SecurityUtil.encrypt(staffId + oldpassword);
			newpassword = SecurityUtil.encrypt(staffId + newpassword);
		}else if(password.startsWith("{SHA}")){
			oldpassword = "{SHA}" + new String(Base64It.encode(oldpassword.getBytes()));
			newpassword = "{SHA}" + new String(Base64It.encode(newpassword.getBytes()));
		}
		if(!(oldpassword.equals(password)||password.equals((String)data.get("oldpassword"))))
			{
			String msg = "请输入正确的原密码";
				result = Result.buildInterInfoError(msg);
			}
			else
			{
				wfdao.updateUserPasswordByName(userName, newpassword);
				
				result = Result.buildSuccess();
			}
		 return result;
		}catch(Exception e)
		{
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildDataAccessError();
		}
			
		 return result;
	}
	/**
	 * 获取反馈描述原因信息--web端
	 * @param Map
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/web/fault/callbackreason" })
	public @ResponseBody
	Result getFCallBackReason(@RequestBody Map<String,Object> data) {
		Result result = new Result();
		try
		{
			String flag = (String)data.get("key");
			String sql = "";
			if(flag.equals("kt"))
			{
				sql = "select t.ID,t.return_name,t.parent_id,t.level_id from OM_ORDERING_TYPE t";
			}
			else
			{
				sql = "select t.ID,t.return_name,t.parent_id,t.level_id from  OM_SA_ORDERING_TYPE t";
			}
			
			List reasonList = wfdao.getFCallBackReason(sql);
			//List returnDataList = buildReturnData(reasonList);
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put(AuthRefresh.ReturnData, reasonList);
			result = Result.buildSuccess(resultData);
			System.out.println(resultData);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			result.setResultCode(-1);
			return result;
		}
		return result;
	}
	
	/**
	 * 获取反馈描述原因信息--web端
	 * @param Map
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/web/fault/insert/callbackreason" })
	public @ResponseBody
	Result insertFCallBackReason(@RequestBody Map<String,Object> data) {
		Result result = new Result();
		try{
			
			wfdao.insertFCallBackReason(data);
			//List returnDataList = buildReturnData(reasonList);
			Map<Object, Object> resultData = new HashMap<Object, Object>();
		
			result = Result.buildSuccess();
			System.out.println(resultData);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			result.setResultCode(-1);
			return result;
		}
		return result;
	}
	
}


