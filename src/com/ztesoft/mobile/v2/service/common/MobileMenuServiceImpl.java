package com.ztesoft.mobile.v2.service.common;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;
import com.ztesoft.mobile.v2.entity.menu.MobileMenu;
import com.ztesoft.mobile.v2.entity.menu.MobileMenuCatalog;
import com.ztesoft.mobile.v2.entity.menu.MobileMenuConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-3-12
 * Time: 上午11:23
 */
@Service("mobileMenuService")
public class MobileMenuServiceImpl implements MobileMenuService {

    private MobileMenuDAO getMobileMenuDAO() {
        String daoName = MobileMenuDAOImpl.class.getName();
        return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    private MobileMenuConfigDAO getMobileMenuConfigDAO() {
        String daoName = MobileMenuConfigDAOImpl.class.getName();
        return (MobileMenuConfigDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    public Result queryMenuCatalog(Long staffId, Long jobId, Long defaultJobId, Integer osType) {
        if(null == staffId || null == jobId || null == osType)
            return Result.buildParameterError();

        Result result = null;
        try {

            List<Map> list = getMobileMenuDAO().queryMenuCatalog(staffId, jobId, defaultJobId, osType);

            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileMenuCatalog.MENU_CATALOG_NODE, list);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (DataAccessException e) {
            result = Result.buildServerError();
            //Print stack
            e.printStackTrace();
        }
        return result;
    }

    public Result querySubMenu(Long staffId, Long jobId, Long defaultJobId, Integer osType) {
        if(null == staffId || null == jobId || null == osType)
            return Result.buildParameterError();
        Result result = null;
        try {
            
            List<Map> list = getMobileMenuDAO().queryMenuCatalog(staffId, jobId, defaultJobId, osType);

            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileMenuCatalog.MENU_CATALOG_NODE, list);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (DataAccessException e) {
            result = Result.buildServerError();
            //Print stack
            e.printStackTrace();
        }
        return result;
    }

    /** 查询菜单信息，含菜单分类 */
    public Result queryMenu(Long staffId, Long jobId, Long defaultJobId, Integer osType) {
        if(null == staffId || null == jobId || null == osType)
            return Result.buildParameterError();
        Result result = null;
        try {
            List<Map> menuCagalogList = getMobileMenuDAO().queryMenuCatalog(staffId, jobId, defaultJobId, osType);
            List<Map> menuList = getMobileMenuDAO().querySubMenu(staffId, jobId, defaultJobId, osType);
            List<Map> menuConfigList = getMobileMenuConfigDAO().selMenuConfigByCons(staffId, jobId, defaultJobId, osType);

            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileMenuCatalog.MENU_CATALOG_NODE, menuCagalogList);
            resultData.put(MobileMenu.MENU_NODE, menuList);
            resultData.put(MobileMenuConfig.MENU_CONFIG_NODE, menuConfigList);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
            
        } catch (DataAccessException e) {
            result = Result.buildServerError();
            //Print stack
            e.printStackTrace();
        }
        return result;
    }

}
