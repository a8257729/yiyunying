package com.ztesoft.mobile.pn.service;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.ztesoft.mobile.pn.service.impl.MobilePnUserServiceImpl;

/**
 * This is a helper class to look up service objects.
 *
 */
public class ServiceLocator {

	private Log logger = LogFactory.getLog(this.getClass());

	private static ServiceLocator instance;

	private ServiceLocator() {

	}

    public static ServiceLocator getInstance() {
        // return instance;
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public Object getService(Class clazz){
    	try {
			return  Class.forName(clazz.getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    /**
     * Obtains the user service.
     *
     * @return the user service
     */
    public MobilePnUserService getMobilePnUserService() {
        return (MobilePnUserService) getInstance().getService(MobilePnUserServiceImpl.class);
    }

}
