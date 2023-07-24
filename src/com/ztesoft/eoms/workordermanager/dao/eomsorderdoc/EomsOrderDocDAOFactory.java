package com.ztesoft.eoms.workordermanager.dao.eomsorderdoc;

/**
 * <p>Title: 电子运维项目</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ZTESoft</p>
 * @author not attributable
 * @version 1.0
 */

import com.zterc.uos.helpers.*;
import com.ztesoft.eoms.common.helper.DAOHelper;


public class EomsOrderDocDAOFactory {

    private static final String className =
        "com.ztesoft.eoms.workordermanager.dao.eomsorderdoc.EomsOrderDocDAO" +
        DAOHelper.getDatabaseType();

    private EomsOrderDocDAOFactory() {
    }

    public static EomsOrderDocDAO getDAO() throws DAOSysException {
        EomsOrderDocDAO dao = null;
        try {
         //System.out.println("className="+className);
                dao = (EomsOrderDocDAO) Class.forName(className).newInstance();

        }
        catch (Exception ex) {
            throw new DAOSysException(
                "EomsOrderDocDAOFactory.getDAO: "
                + "Exception while getting DAO type : \n"
                + ex.getMessage());
        }
        return dao;
    }

}
