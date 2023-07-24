package com.ztesoft.eoms.common.idproduct.stragety;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.CommonError;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.common.idproduct.FieldConstants;
import com.ztesoft.eoms.common.idproduct.InitIdDto;
import com.ztesoft.eoms.common.idproduct.dao.UosMemoryDAO;
import com.ztesoft.eoms.common.transaction.JtaTransactionManagerFactory;
import org.apache.log4j.Logger;

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
public class UseNeedDBTableStragety extends AbstractIdGetStragety {
    private static Logger logger = Logger.getLogger(UseNeedDBTableStragety.class);
    UseNeedDBTableStragety(String confFilePath) throws UOSException {
        super(confFilePath);
    }


    /**
     * 获取IDDTO
     * @return InitIdDto[]
     * @throws IOException
     * @throws SQLException
     */

    protected InitIdDto[] initInitIdDto() throws UOSException {
        //Ignore;
        Properties tableProp = null;
        FileInputStream stream = null;
        InitIdDto[] retArr = null;
        try {
            tableProp = new Properties();

            stream = new FileInputStream(confFilePath);

            tableProp.load(stream);

            retArr = prop2InitIdDto(tableProp);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new UOSException(new CommonError("IO EXCEPTION=" +
                    ie.getMessage()));

        } finally {

            if (ValidateHelper.validateNotNull(tableProp)) {
                tableProp.clear();
                tableProp = null;
            }
            if (ValidateHelper.validateNotNull(stream)) {
                try {
                    stream.close();
                } catch (IOException i) {

                } finally {
                    stream = null;
                }

            }

        }
        if (retArr != null && retArr.length != 0) {
            for (int i = 0; i < retArr.length; i++) {
                InitIdDto idDto = (InitIdDto) retArr[i];

                keyMap.put(idDto.getKey(), idDto);
            }
        }
        return retArr;

    }


    /**
     * 从属性中得到InitDto
     * @param prop Properties
     * @return InitIdDto[]
     * @throws SQLException
     */

    private InitIdDto[] prop2InitIdDto(Properties prop) {

        List list = null;

        try {
            if (prop != null && !prop.isEmpty()) {

                list = new ArrayList();

                for (Enumeration en = prop.propertyNames(); en.hasMoreElements(); ) {

                    String tableName = ((String) en.nextElement()).trim();
                    String key = prop.getProperty(tableName, "").trim();
                    InitIdDto idDto = new InitIdDto();
                    idDto.setTableName(tableName);
                    idDto.setKey(tableName);
                    idDto.setColumnName(key);

                    list.add(idDto);
                }
            }
        } finally {
            prop = null;
        }

        return (ValidateHelper.validateNotEmpty(list)) ?
                (InitIdDto[]) list.toArray(new InitIdDto[list.size()]) : null;

    }


    public long nextId(String key, int addNum) throws
            UOSException {

        key = key.toUpperCase();

        Object lock = getLock(key);

        synchronized (lock) {

            long retVal = -1;
            InitIdDto idDto = new InitIdDto();
            if (sequeMap.containsKey(key)) {
                //System.out.println("sequeMap getKey");
                idDto = (InitIdDto) sequeMap.get(key);

            } else {
                idDto.setTableName(key);
                idDto.setKey(key);
                //System.out.println(
                //        "sequeMap not getKey need Syncronized location 1 ");
                try {
                    syncInitIdDtoFromDb(idDto);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new UOSException(new CommonError("SQL EXCEPTION=" +
                            e.getMessage()));
                }

            }

            long _addNum = addNum;

            while (true) {

                if (idDto.getSetpValue() <= 0) {
                    //System.out.println(
                    //        "sequeMap not getKey need Syncronized location 2 ");
                    try {
                        idDto = syncInitIdDtoFromDb(idDto);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new UOSException(new CommonError(e.getMessage()));
                    }

                }

                if (_addNum == 0) {
                    break;
                } else {
                    if (_addNum > idDto.getSetpValue()) {
                        long _setpValue = idDto.getSetpValue();
                        idDto.decreaseSetpValue(idDto.getSetpValue());
                        idDto.increaseSeqValue(idDto.getSetpValue());
                        _addNum -= _setpValue;
                    } else {
                        idDto.decreaseSetpValue(_addNum);
                        idDto.increaseSeqValue(_addNum);
                        _addNum = 0;
                    }

                }

            }
            retVal = idDto.getSeqValue();

            sequeMap.put(key, idDto);

            return retVal;

        }

    }


    private InitIdDto syncInitIdDtoFromDb(InitIdDto idDto) throws SQLException,
            UOSException {
        InitIdDto ret = null;
        TransactionManager transactionManager = JtaTransactionManagerFactory.
                                                instance().
                                                getTransactionManager();
        Transaction transaction = null;

        if (transactionManager == null) {
            logger.debug(
                    "Can n't Find TranactionManager . Non Transaction Suspend!");

        } else {
            logger.debug(
                    " Find TransactionManager .  Transaction will be Suspend and Resume!");
        }
        //成功运行标志
        boolean isSuccess = true;

        try {
            logger.debug(" transactionManager.getStatus()" +
                               transactionManager.getStatus());
        } catch (SystemException ex1) {
        }
        //挂起父事务

        try {
            transaction = transactionSuspend(transactionManager);
        } catch (Exception ex) {
            isSuccess = false;
            throw new UOSException(new CommonError(ex.getMessage()));
        }

        try {
            transactionBegin(transactionManager);
            String columnName = getInitIdDto(idDto.getTableName()).
                                getColumnName();
            idDto.setColumnName(columnName);
            UosMemoryDAO dao = (UosMemoryDAO) BaseDAOFactory.getDAO(
                    FieldConstants.
                    getUosMemoryDAOPath());
            ret = dao.increase(idDto);
            transactionCommit(transactionManager);
        } catch (Exception uose) {
            uose.printStackTrace();
            isSuccess = false;
            try {
                transactionRollback(transactionManager);
            } catch (Exception e) {

            }
        }
        //父事务恢复
        try {
            transactionResume(transactionManager, transaction);
        } catch (Exception e) {
            isSuccess = false;

            throw new UOSException(new CommonError(e.getMessage()));
        } finally {
            transactionManager = null;
            transaction = null;
        }
        //如果有异常，抛出异常
        if (!isSuccess) {
            throw new UOSException(new CommonError(
                    "Transaction Suspend and Resume Error in Function syncInitIdDtoFromDb"));
        }
        return ret;
    }

    protected synchronized InitIdDto getInitIdDto(String tableName) throws
            UOSException {
        if (keyMap.containsKey(tableName)) {
            return (InitIdDto) keyMap.get(tableName);
        } else {
            throw new UOSException(new CommonError("Function getInitIdDto Error Cause: not exist in corresponding KEY , configure please!"));
        }
    }


    private void transactionBegin(TransactionManager transactionManager) throws
            SystemException, NotSupportedException {
        if (transactionManager != null) {
            //System.out.println("transactionManger.begin");
            transactionManager.begin();
        }

    }

    private void transactionCommit(TransactionManager transactionManager) throws
            SystemException, IllegalStateException, SecurityException,
            HeuristicRollbackException, HeuristicMixedException,
            RollbackException {
        if (transactionManager != null) {
            //System.out.println("transactionManger.commit");
            transactionManager.commit();
        }

    }

    private void transactionRollback(TransactionManager transactionManager) throws
            SystemException, SecurityException, IllegalStateException {
        if (transactionManager != null) {
            //System.out.println("transactionManger.rollback");
            transactionManager.rollback();

        }

    }

    private void transactionResume(TransactionManager transactionManager,
                                   Transaction transaction) throws
            SystemException, IllegalStateException, InvalidTransactionException {
        if (transactionManager != null && transaction != null) {
            //System.out.println("transactionManager.resume!");
            transactionManager.resume(transaction);
        }

    }

    private Transaction transactionSuspend(TransactionManager
                                           transactionManager) throws
            SystemException {
        if (transactionManager.getStatus() != Status.STATUS_NO_TRANSACTION) {
            //System.out.println("transactionManager.suspend!");
            return transactionManager.suspend();
        } else {
            return null;
        }
    }
}
