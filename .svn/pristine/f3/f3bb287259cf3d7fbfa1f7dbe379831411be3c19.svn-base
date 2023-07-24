package com.ztesoft.android.service;

import com.ztesoft.android.dao.BaseDataDAO;

public class BaseNativService {

	private final static String packages = "com.ztesoft.android.client.dao.";
	public BaseDataDAO getDao(String daoName){
		
		BaseDataDAO dataDao = null;
		try {

			dataDao = (BaseDataDAO) (Class.forName(packages+daoName).newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dataDao;
	}
}
