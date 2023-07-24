package com.ztesoft.eoms.common.idproduct.stragety;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.CommonError;
import com.ztesoft.eoms.common.db.ConnectionAdapters;
import com.ztesoft.eoms.common.db.DbOper;
import com.ztesoft.eoms.common.helper.StringHelper;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.common.idproduct.InitIdDto;
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
public class UseNotNeedDBTableStragety extends AbstractIdGetStragety {


    UseNotNeedDBTableStragety(String confFilePath) throws UOSException {
        super(confFilePath);
    }

    private static Logger logger = Logger.getLogger(UseNotNeedDBTableStragety.class);


    /**
     * 获取IDDTO
     * @return InitIdDto[]
     * @throws IOException
     * @throws SQLException
     */

    protected InitIdDto[] initInitIdDto() throws UOSException {
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

        } catch (SQLException s) {
            s.printStackTrace();
            throw new UOSException(new CommonError("No access Datebase =" +
                    s.getMessage()));
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
        if (ValidateHelper.validateNotEmpty(retArr)) {

            initSequeMap(retArr);

        }

        return retArr;

    }

    /**
     * 从属性中得到InitDto
     * @param prop Properties
     * @return InitIdDto[]
     * @throws SQLException
     */

    private InitIdDto[] prop2InitIdDto(Properties prop) throws SQLException {

        List list = null;
        Connection conn = null;

        try {
            if (prop != null && !prop.isEmpty()) {
                conn =  new ConnectionAdapters().newConnection();
                list = new ArrayList();

                for (Enumeration en = prop.propertyNames(); en.hasMoreElements(); ) {

                    String tableName = ((String) en.nextElement()).trim();
                    String key = prop.getProperty(tableName, "").trim();
                    InitIdDto idDto = new InitIdDto();
                    idDto.setTableName(tableName);
                    idDto.setKey(tableName);
                    idDto.setColumnName(key);
                    Long initizeMax = getInitizeMax(conn, tableName, key);
                    if (logger.isDebugEnabled()) {
                        logger.debug("tableName=" + tableName + " columnName=" +
                                     key + " initizeMax=" + initizeMax);
                    }
                    /*内存中保留值和数据库初始值同步*/
                    idDto.setSeqValue(!ValidateHelper.validateNotNull(
                            initizeMax) ?
                                      (0) :
                                      (initizeMax.longValue()));
                    list.add(idDto);
                }
            }
        } finally {
            DbOper.cleanUp(null, null, null, conn);
            prop = null;
        }

        return (ValidateHelper.validateNotEmpty(list)) ?
                (InitIdDto[]) list.toArray(new InitIdDto[list.size()]) : null;

    }

    /**
     * 获取最大值
     * @param conn Connection
     * @param tableName String
     * @param columnName String
     * @return Long
     * @throws SQLException
     */
    private Long getInitizeMax(Connection conn, String tableName,
                               String columnName) {
        String sql = "select MAX(" + columnName.toUpperCase() + ") from " +
                     tableName.toUpperCase() +
                     " ";
        Long retVal = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Object tmp = rs.getObject(1);
                //System.out.println("内存加载Sequences初始化加载sql=" + sql + "取得的值" +
                //                   StringHelper.toString(tmp));
                if (ValidateHelper.validateNotNull(tmp)) {
                    try {
                        retVal = new Long(tmp.toString());
                    } catch (NumberFormatException e) {
                        //System.out.println("sequnece初始化错误=" + sql + "取得的值" +
                        //                   StringHelper.toString(tmp));
                        e.printStackTrace(System.out);
                        e.printStackTrace(System.err);
                    }
                }
            }

        } catch (SQLException e) {
            //如果发生sql异常。则忽视
        } finally {
            DbOper.cleanUp(rs, null, ps, null);
        }
        return retVal;

    }

    public long nextId(String key, int addNum) throws
            UOSException {

        key = key.toUpperCase();
        Object lock = getLock(key);
        synchronized (lock) {

            long retVal = -1;
            if (sequeMap.containsKey(key)) {
                InitIdDto idDto = (InitIdDto) sequeMap.get(key);
                long tmp = idDto.getSeqValue();
                /*进行加操作*/
                tmp += addNum;
                idDto.setSeqValue(tmp);
                retVal = idDto.getSeqValue();

                sequeMap.put(key, idDto);
            } else {
                throw new UOSException(new CommonError(
                        "not exist in corresponding KEY , configure please!"));
            }

            return retVal;

        }
    }

    /**
     * 初始化SequeMap
     * @param arr InitIdDto[]
     */

    private void initSequeMap(InitIdDto[] arr) {

        if (ValidateHelper.validateNotNull(sequeMap) && sequeMap.isEmpty()) {
            for (int i = 0; i < arr.length; i++) {
                sequeMap.put(arr[i].getKey().toUpperCase(),
                             arr[i]);
            }
        }
    }

    protected synchronized InitIdDto getInitIdDto(String tableName) throws
            UOSException {
        if (sequeMap.containsKey(tableName)) {
            return (InitIdDto) sequeMap.get(tableName);
        } else {
            throw new UOSException(new CommonError("Function getInitIdDto Error Cause: not exist in corresponding KEY , configure please!"));
        }
    }
}
