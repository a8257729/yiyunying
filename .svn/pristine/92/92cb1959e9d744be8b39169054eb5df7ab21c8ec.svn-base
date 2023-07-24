package com.ztesoft.mobile.v2.service.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.common.MobileStaffSigninDAO;
import com.ztesoft.mobile.v2.dao.common.MobileStaffSigninDAOImpl;
import com.ztesoft.mobile.v2.entity.common.MobileStaffSignin;

@Service("mobileStaffSigninService")
public class MobileStaffSigninServiceImpl implements MobileStaffSigninService {

	/** * 签到 */
	private static final String SIGNINFOREBIZ = "signInForEBiz";
	
	private MobileStaffSigninDAO getMobileStaffSigninDAO() {
		String daoName = MobileStaffSigninDAOImpl.class.getName();
		return (MobileStaffSigninDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	public List getMobileStaffSigninService(Map param) {
		List list = null;
		try {
			list = getMobileStaffSigninDAO().selAll(param);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Transactional(rollbackFor={Throwable.class})
	public Result addStaffSignin(MobileStaffSignin staffSigninInfo) throws Exception {

		Date now = new Date();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobileStaffSignin.STAFF_ID_NODE,
				staffSigninInfo.getStaffId());
		paramMap.put(MobileStaffSignin.CROODS_TYPE_NODE,
				staffSigninInfo.getCroodsType());
		paramMap.put(MobileStaffSignin.LATITUDE_NODE,
				staffSigninInfo.getLatitude());
		paramMap.put(MobileStaffSignin.LONGITUDE_NODE,
				staffSigninInfo.getLongitude());
		paramMap.put(MobileStaffSignin.SIGNIN_ADDR_NODE,
				staffSigninInfo.getSigninAddr());
		paramMap.put(MobileStaffSignin.SIGNIN_STATUS_NODE,
				staffSigninInfo.getSigninStatus());
		paramMap.put(MobileStaffSignin.SIGNIN_TIME_NODE,
				now);	//只能取服务器时间
		paramMap.put(MobileStaffSignin.SIGNIN_TYPE_NODE,
				staffSigninInfo.getSigninType());
		paramMap.put(MobileStaffSignin.STAFF_NAME_NODE,
				staffSigninInfo.getStaffName());
		paramMap.put(MobileStaffSignin.USERNAME_NODE,
				staffSigninInfo.getUsername());

		Long staffSigninId = getMobileStaffSigninDAO().insert(paramMap);
		Result result = Result.buildSuccess();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put(MobileStaffSignin.STAFF_SIGNIN_ID_NODE, staffSigninId);
		resultData.put(MobileStaffSignin.SIGNIN_TIME_NODE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));	//转为字符串
		result.setResultData(resultData);
		
		return result;
	}

	@Transactional(rollbackFor={Throwable.class})
	public Result delStaffSignin(Long staffSigninId)  throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobileStaffSignin.STAFF_SIGNIN_ID_NODE, staffSigninId);
		
		getMobileStaffSigninDAO().delete(paramMap);
		Result result = Result.buildSuccess();
		
		return result;
	}

	@Transactional(rollbackFor={Throwable.class})
	public Result updateStaffSignin(MobileStaffSignin staffSigninInfo) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobileStaffSignin.STAFF_SIGNIN_ID_NODE,
				staffSigninInfo.getStaffSigninId());
		paramMap.put(MobileStaffSignin.STAFF_ID_NODE,
				staffSigninInfo.getStaffId());
		paramMap.put(MobileStaffSignin.CROODS_TYPE_NODE,
				staffSigninInfo.getCroodsType());
		paramMap.put(MobileStaffSignin.LATITUDE_NODE,
				staffSigninInfo.getLatitude());
		paramMap.put(MobileStaffSignin.LONGITUDE_NODE,
				staffSigninInfo.getLongitude());
		paramMap.put(MobileStaffSignin.SIGNIN_ADDR_NODE,
				staffSigninInfo.getSigninAddr());
		paramMap.put(MobileStaffSignin.SIGNIN_STATUS_NODE,
				staffSigninInfo.getSigninStatus());
		paramMap.put(MobileStaffSignin.SIGNIN_TIME_NODE,
				staffSigninInfo.getSigninTime());
		paramMap.put(MobileStaffSignin.SIGNIN_TYPE_NODE,
				staffSigninInfo.getSigninType());
		paramMap.put(MobileStaffSignin.STAFF_NAME_NODE,
				staffSigninInfo.getStaffName());
		paramMap.put(MobileStaffSignin.USERNAME_NODE,
				staffSigninInfo.getUsername());
		
		getMobileStaffSigninDAO().update(paramMap);
		Result result = Result.buildSuccess();

		return result;
	}

}
