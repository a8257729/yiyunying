package com.ztesoft.mobile.common.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public abstract class AbstractDAOContext {

    private final Map singleDAOCache = new HashMap();

    protected Object getDAO(String daoName) {
        daoName = daoName.trim();

        if (singleDAOCache.containsKey(daoName)) {
            return singleDAOCache.get(daoName);
        } else {
            return null;
        }
    }

    protected Object putDAO(String daoName, Object obj) {
        singleDAOCache.put(daoName, obj);
        return obj;

    }
}
