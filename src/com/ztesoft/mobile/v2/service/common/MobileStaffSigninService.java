package com.ztesoft.mobile.v2.service.common;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobileStaffSignin;

public interface MobileStaffSigninService {
	
	public Result addStaffSignin(MobileStaffSignin staffSigninInfo) throws Exception;
	
	public Result delStaffSignin(Long staffSigninId) throws Exception;
	
	public Result updateStaffSignin(MobileStaffSignin staffSigninInfo) throws Exception;
	//«©µΩ≤È—Ø
	public List getMobileStaffSigninService(Map param);
}
