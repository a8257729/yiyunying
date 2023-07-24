package com.ztesoft.mobile.v2.service.app;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAOImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("mobileFrameAppService")
public class MobileFrameAppServiceImpl implements MobileFrameAppService {
	
	private MobileFrameAppDAO getMobileFrameAppDAO() {
    	String daoName = MobileFrameAppDAOImpl.class.getName();
        return (MobileFrameAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }

	public Map versionNameExists(String versionName) throws Exception {
		boolean flag = true;
        List list = getMobileFrameAppDAO().selVersionIsExist(versionName);
        if(list == null || 0 == list.size()) {
        	flag = false;
        }
        Map resultMap = new HashMap();
        resultMap.put("flag", flag);
        return resultMap;
	}

}
