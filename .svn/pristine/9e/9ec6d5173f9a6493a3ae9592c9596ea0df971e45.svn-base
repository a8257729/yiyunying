package com.ztesoft.mobile.pn.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAO;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAOImpl;
import com.ztesoft.mobile.pn.service.MobilePnUserService;

public class MobilePnUserServiceImpl implements MobilePnUserService {

	public Map getPnUserById(Map paramMap) throws Exception {
		return this.getMobilePnUserDAO().selById(paramMap);
	}

	public List selAll(Map paramMap) throws Exception {
		return this.getMobilePnUserDAO().selAll(paramMap);
	}

	public void savePnUser(Map paramMap) throws Exception {
		paramMap.put("pnCreateDate", new Date());
		paramMap.put("pnUpdateDate", new Date());
		this.getMobilePnUserDAO().insert(paramMap);
	}
	
	public void updatePnUser(Map paramMap) throws Exception {
		paramMap.put("pnUpdateDate", new Date());
		this.getMobilePnUserDAO().update(paramMap);
	}

	public void deleteUser(Map paramMap) throws Exception {
		this.getMobilePnUserDAO().delete(paramMap);
	}
	
	public Map getOneByCons(Map paramMap) throws Exception {
		return this.getMobilePnUserDAO().getOneByCons(paramMap);
	}
	
	private MobilePnUserDAO getMobilePnUserDAO() {
		String daoName = MobilePnUserDAOImpl.class.getName();
		return (MobilePnUserDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
