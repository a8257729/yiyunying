package com.ztesoft.mobile.common.dao;

import java.util.HashMap;

import com.ztesoft.mobile.common.helper.DAOHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;
import com.ztesoft.mobile.common.exception.DAOException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: ȡ��BaseDAOͳһ����</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0 
 */
public abstract class BaseDAOFactory {
    private BaseDAOFactory() {
    }

    public static void cleanMap() {
        if (daoMap != null) {
            daoMap.clear();
            daoMap = null;
        }
    }

    private static HashMap daoMap = null;

    /**
     * ��ȡDAO�������ݿ��׺
     * @param daoPathName String
     * @return BaseDAO
     */
    public static final BaseDAO getDAO(String daoPathName) {
      return getDAO(daoPathName, true);
    }

    /**
     * ��ȡDAO��û�����ݿ��׺
     * @param daoPathName String
     * @return BaseDAO
     */
    public static final BaseDAO getImplDAO(String daoPathName) {
      return getDAO(daoPathName, false);
    }

    /**
     * ȡ��BaseDAOͳһ���� ����Ҫ��DAOHelper.isDebug�������й�������֧���������
     * @param daoPathName String daoָ���class·��
     * @return BaseDAO
     */
    private static final BaseDAO getDAO(String daoPathName, boolean needDBTag) {

        Object dao = null;
        daoPathName = daoPathName.trim();
        if(needDBTag){
          daoPathName = daoPathName + DAOHelper.getDatabaseType();
        }

        if (ValidateHelper.validateNotEmpty(daoPathName)) {
            if (!ValidateHelper.validateNotNull(daoMap)) {
                daoMap = new HashMap();
            }
            try {
                if (daoMap.containsKey(daoPathName)) {

                    dao = daoMap.get(daoPathName);
                } else {

                    dao = Class.forName(daoPathName).newInstance();
                    daoMap.put(daoPathName, dao);
                }

            } catch (Exception ex) {
                throw new DAOException(
                        daoPathName
                        + "Exception while getting DAO type : \n"
                        + ex.getMessage());
            }

            if (ValidateHelper.validateNotNull(dao)) {
                if (dao instanceof BaseDAO) {
                    return (BaseDAO) dao;
                } else {
                    throw new DAOException(daoPathName +
                                              "Exception while getting DAO type: DAO not implements BASEDAO");
                }

            } else {
                throw new DAOException(daoPathName +
                                          "Exception while getting DAO type:DAO is Null");
            }
        } else {
            throw new DAOException(daoPathName
                                      +
                                      "Exception while getting DAO type :DAOName is Null! \n");

        }

    }

}
