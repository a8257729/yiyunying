package com.ztesoft.eoms.common.dao.ibatis;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.*;
import com.zterc.uos.*;
import com.zterc.uos.exception.*;
import com.ztesoft.eoms.common.db.*;
import com.ztesoft.eoms.common.helper.*;
import com.ztesoft.eoms.exception.*;
import org.apache.log4j.*;
import com.ztesoft.eoms.common.configure.ConfigMgr;

/**
 * <p>
 * Title: 电子运维项目
 * </p>
 *
 * <p>
 * Description: 适用于ibatis的统一dao实现继承类
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 *
 * <p>
 * Company: ZTESoft
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
public abstract class BaseIbatisDAOImpl {

    private static final Logger logger = Logger.getLogger(BaseIbatisDAOImpl.class);

    /* sqlMap实例 */
    private static SqlMapClient sqlMap = null;

    protected BaseIbatisDAOImpl() {

    }


    /**
     *
     * insert方法 供继承类调用
     *
     * @param str
     *            String
     * @param obj
     *            Object
     * @return Object
     * @throws SQLException
     */
    protected Object insertForIbatis(String str, Object obj) throws
            SQLException {
        SqlMapClient client = getSqlMapInstance();
        Connection conn = getConnection();
        try {
            SqlMapSession session = client.openSession(conn);
            Object ret = session.insert(str, obj);
            session.close();
            session = null;
            return ret;
        } finally {
            DbOper.cleanUp(null, null, null, conn);
        }
    }

    protected int updateForIbatis(String str, Object obj) throws SQLException {
        SqlMapClient client = getSqlMapInstance();
        Connection conn = getConnection();
        try {
            SqlMapSession session = client.openSession(conn);
            int ret = session.update(str, obj);
            session.close();
            session = null;
            return ret;
        } finally {
            DbOper.cleanUp(null, null, null, conn);
        }

    }

    protected int insertForIbatis(String str) throws SQLException {
        SqlMapClient client = getSqlMapInstance();
        Connection conn = getConnection();
        try {
            SqlMapSession session = client.openSession(conn);
           int result =session.update(str,null);
            session.close();
            session = null;
            return result;
        } finally {
            DbOper.cleanUp(null, null, null, conn);
        }

    }
    
    /**
     *
     * delete方法,供继承类调用
     *
     * @param str
     *            String
     * @param obj
     *            Object
     * @return int
     * @throws SQLException
     */
    protected int deleteForIbatis(String str, Object obj) throws SQLException {
        SqlMapClient client = getSqlMapInstance();
        Connection conn = getConnection();
        try {
            SqlMapSession session = client.openSession(conn);
            int ret = session.delete(str, obj);
            session.close();
            session = null;
            return ret;
        } finally {
            DbOper.cleanUp(null, null, null, conn);

        }
    }


    /**
     * 查询方法,供继承类调用,返回<code>java.util.List</code>
     *
     * @param str
     *            String
     * @param obj
     *            Object
     * @return List
     * @throws SQLException
     */
    protected List queryListForIbatis(String str, Object obj) throws
            DataAccessException {

        Connection conn = null;
        try {
            conn = getConnection();
            SqlMapSession session = getSqlMapInstance().openSession(conn);
            List list = session.queryForList(str, obj);

            session.close();
            session = null;
            return list;
        } catch (Exception e) {
            throw new DataAccessException(
                    "Exception:BaseDAOImpl <List queryForList(String str, Object obj)>."
                    + e.getMessage());
        } finally {
            DbOper.cleanUp(null, null, null, conn);
        }
    }

    /**
     * 查询方法,供继承类调用,返回<code>java.lang.Object</code>
     *
     * @param str
     *            String
     * @param obj
     *            Object
     * @return Object
     * @throws SQLException
     */

    protected Object queryObjectForIbatis(String str, Object obj) throws
            DataAccessException {

        Connection conn = null;
        try {
              SqlMapClient client = getSqlMapInstance();
              conn = getConnection();
            SqlMapSession session = client.openSession(conn);
            Object obj1 = session.queryForObject(str, obj);
            session.close();
            session = null;
            return obj1;
        }catch(Exception se){
            throw new DataAccessException(
                        "Exception:BaseDAOImpl <Object queryForObject(String str, Object obj)>."
                        + se.getMessage());

        } finally {
            DbOper.cleanUp(null, null, null, conn);
        }
    }


    /* ibatis文件名称 */
    private static final String SQL_MAP_CONF_XML_FILE =
            "cfg/sql_map_config_"+DAOHelper.getDatabaseType().toLowerCase()+".xml";

    /**
     * 获取sqlMap实例
     *
     * @return SqlMapClient
     * @throws SQLException
     */
    protected synchronized SqlMapClient getSqlMapInstance() throws SQLException {

        String ibatisDebug = ConfigMgr.getInstance().getPropertyAsString(
                "common", "com.ztesoft.eoms.ibatis.debug");

        if (!ValidateHelper.validateNotNull(sqlMap)
            || (ValidateHelper.validateNotEmpty(ibatisDebug) && ibatisDebug
                .equals("TRUE"))) {
            synchronized (BaseIbatisDAOImpl.class) {
                Reader reader = null;
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("SqlMap重新加载!");
                    }

                    reader = Resources
                             .getResourceAsReader(SQL_MAP_CONF_XML_FILE);
                    sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

                } catch (Exception e) {
                    throw new SQLException(e.getMessage());
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            reader = null;
                        }

                    }
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("SqlMap使用缓存!");
            }
        }
        return sqlMap;
    }


    /* 从UOS_UOSSEQUENCE获取主键标志 */
    protected static final int PK_FORM_DB = 1;

    /* 从MEMORY获取主键标志 */
    protected static final int PK_FORM_MEM = 2;

    /**
     * 获取主键方法
     *
     * @param tableName
     *            String 表名
     * @param num
     *            int 长度
     * @param typeFlag
     *            int 1:2 从UOS_UOSSEQUENCE获取:从内存中获取
     * @return Long
     * @throws UOSException
     */
    protected Long getPK(String tableName, int num, int typeFlag) throws
            UOSException,SQLException {
        switch (typeFlag) {
        case PK_FORM_DB:
            return DAOHelper.getPKFromDB(tableName, num);
        case PK_FORM_MEM:
            return DAOHelper.getPKFromMem(tableName, num);
        default:
            throw new UOSException(new CommonError("不支持该生成主键类型"));
        }
    }

    /* 两层或者三层调用标记 */
    private static boolean isServer = true;

    /**
     * 获取数据库连接，传递两层或者三层调用 。如果两层则isServer为false,三层为true
     *
     * @param isServer
     *            boolean
     * @return Connection
     * @throws SQLException
     */
    protected Connection getConnection(boolean isServer) throws SQLException {
        ConnectionAdapters adapters = new ConnectionAdapters();

        Connection conn = adapters.newConnection();
        adapters = null;
        return conn;
    }

    /**
     * 默认获取数据库连接方法。后需要和DAOHelper.isDebug方法进行关联，以支持两层调试
     *
     * @return Connection
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        return getConnection(true);
    }


    /**
     * count一条 sql语句结果得数量
     *
     * @param sql
     *            String
     * @return int
     * @throws SQLException
     *
     */

    protected int calculateSQLCount(String sql) throws SQLException {
        String sqlStrCount = SQLHelper.preparedCalculateSQL(sql);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStrCount.toString());
            rs = ps.executeQuery();
            rs.next();
            int totalRecords = rs.getInt(1);
            return totalRecords;
        } finally {
            DbOper.cleanUp(rs, null, ps, conn);
        }

    }

    protected boolean isIsServer() {
        return isServer;
    }

    protected SqlMapClient getSqlMap() {
        return sqlMap;
    }


    /* 获取table的ID值 */
    /**
     * 从库表当中获取sequence值
     *
     * @param int
     *            num:数据库中该值的长度 tableName:要获取ID值的表。
     */
    public static Long getPKFromDB(String tableName, int num) throws
            DataAccessException {
        try {
            long retId = com.zterc.uos.client.SequenceGenerator
                         .getSequenceValue(tableName, num);
            return (!EqualsHelper.equals(retId, -1)) ? new Long(retId) : null;
        } catch (UOSException e) {
            throw new DataAccessException(
                    "Exception: get sequence from DB for " + tableName);
        }

    }

    /**
     * 从内存中获得primaryKey,增加值指定
     *
     * @param tablename
     *            String
     * @param num
     *            int 该ID字段的长度(一般为9)
     * @param addNum
     *            int 要获取的ID值个数
     * @return Long
     * @throws UOSException
     */
    public static Long getPKFromMem(String tableName, int num, int addNum) throws
            DataAccessException {
        try {
            return DAOHelper.getPKFromMem(tableName, addNum);
        } catch (Exception e) {
            throw new DataAccessException(
                    "Exception: get sequence from Mem for " + tableName);
        }
    }

    /**
     * 从内存中获得primaryKey,增加值指定
     *
     * @param tableName
     *            String
     * @param num
     *            int
     * @return Long
     * @throws DataAccessException
     */
    public static Long getPKFromMem(String tableName, int num) throws
            DataAccessException {
        return getPKFromMem(tableName, num, 1);
    }


}