package com.ztesoft.eoms.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.common.configure.ConfigMgr;
import com.ztesoft.eoms.common.helper.DAOHelper;
import com.ztesoft.eoms.common.helper.DateHelper;
import com.ztesoft.eoms.common.helper.NumberHelper;
import com.ztesoft.eoms.common.helper.StringHelper;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.helper.SQLHelper;

public class DbOperSupportForMSSQL implements DbOperSupport {

    public DbOperSupportForMSSQL() {

        /*
         * 适用于微软MSSQL 产品,查询库表字段详细信息 columnName:字段名; columnType:字段类型;
         * isComputed:是否计算值--1 true,0 false; length:长度; prec:精度; scale:范围;
         * nullable:可否为空-- 1 true,0 false;
         */

        qryTableInfo =
                " select name as tableName,id as tableObjId,xtype as tableType "
                + " from dbo.sysobjects where xtype='U'"; // in('U','V')";

        qryclumnInfoByTbObjId =
                "select name as columnName,type_name(xusertype) as columnType,"
                + " case when iscomputed = 0 then 0 else 1 end as isComputed,"
                + " convert(int, length) as length,"
                + " case when charindex(type_name(xtype),'tinyint,smallint,decimal,int,real,money,float,numeric,smallmoney') > 0"
                + " then convert(char(5),ColumnProperty(id, name, 'precision'))else ' ' end as prec,"
                + " case when charindex(type_name(xtype),'tinyint,smallint,decimal,int,real,money,float,numeric,smallmoney') > 0"
                + " then OdbcScale(xtype,xscale) else 0 end as scale,"
                + " case when isnullable = 0 then 0 else 1 end as nullable,"
                + " case when autoval is null then 0 else 1 end as isIdentity"
                +
                " from dbo.syscolumns where id =? and number = 0 order by colid";

        qryclumnInfoByTbName =

                "select name as columnName,type_name(xusertype) as columnType,"
                + " case when iscomputed = 0 then 0 else 1 end as isComputed,"
                + " convert(int, length) as length,"
                + " case when charindex(type_name(xtype), 'tinyint,smallint,decimal,int,real,money,float,numeric,smallmoney') > 0 "
                + " then convert(char(5),ColumnProperty(id, name, 'precision'))else ' ' end as prec,"
                + " case when charindex(type_name(xtype), 'tinyint,smallint,decimal,int,real,money,float,numeric,smallmoney') > 0"
                + " then OdbcScale(xtype,xscale) else 0 end as scale,"
                + " case when isnullable = 0 then 0 else 1 end as nullable,"
                + " case when autoval is null then 0 else 1 end as isIdentity"
                + " from dbo.syscolumns where id in(select id from dbo.sysobjects where name =? and xtype in('U','V')) "
                + " and number = 0 order by colid";

    }

    /* query table info: name,objId,type, */
    public String qryTableInfo = null;

    // 根据表ID查询
    public String qryclumnInfoByTbObjId = null;

    // 根据表名查询
    public String qryclumnInfoByTbName = null;

    /**
     * 查询库表里面全部talbe信息
     *
     * @return
     * @throws Exception
     */
    public DbTableInfo[] qryAllTableInfo() throws DataAccessException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List resultList = new ArrayList();

        try {

            conn = getConnection();

            ps = conn.prepareStatement(qryTableInfo);

            rs = ps.executeQuery();

            while (rs.next()) {
                DbTableInfo tableInfo = new DbTableInfo();
                tableInfo.setTableName(rs.getString("tableName"));
                tableInfo.setTableObjId(new Long(rs.getLong("tableObjId")));
                tableInfo.setTableType(rs.getString("tableType"));
                resultList.add(qryTableColumnInfo(tableInfo));
            }

            return (DbTableInfo[]) resultList.toArray(new DbTableInfo[] {});
        } catch (SQLException se) {
            throw new DataAccessException(se.getMessage(), se);
        } finally {
            DbOper.cleanUp(rs, null, ps, conn);
        }
    }

    public DbTableInfo qryTableColumnInfo(DbTableInfo tableInfo,
                                          Connection conn) throws
            DataAccessException {
        ValidateHelper.notNull(tableInfo);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List resultList = new ArrayList();
        try {
            if (ValidateHelper.validateNotNull(tableInfo.getTableObjId())
                && tableInfo.getTableObjId().longValue() > 0) {

                ps = conn.prepareStatement(qryclumnInfoByTbObjId);
                ps.setLong(1, tableInfo.getTableObjId().longValue());
            } else if (ValidateHelper
                       .validateNotEmpty(tableInfo.getTableName())) {
                ps = conn.prepareStatement(qryclumnInfoByTbName);
                ps.setString(1, tableInfo.getTableName());
            } else {
                return tableInfo;
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                DbColumnInfo columnInfo = new DbColumnInfo();
                columnInfo.setColumnName(rs.getString("columnName"));
                columnInfo.setColumnType(rs.getString("columnType"));
                columnInfo.setIsComputed(NumberHelper.integerValueOf(rs
                        .getObject("isComputed")));
                columnInfo.setLength(NumberHelper.longValueOf(rs
                        .getObject("Length")));
                columnInfo.setIsIdentity(NumberHelper.integerValueOf(rs
                        .getObject("isIdentity")));
                columnInfo.setNullAble(NumberHelper.integerValueOf(rs
                        .getObject("nullable")));
                columnInfo.setScale(NumberHelper.integerValueOf(rs
                        .getObject("scale")));
                columnInfo.setPrec(rs.getString("prec"));

                resultList.add(columnInfo);
            }
            tableInfo.setColumnInfo((DbColumnInfo[]) resultList
                                    .toArray(new DbColumnInfo[0]));
            return tableInfo;
        } catch (SQLException se) {
            throw new DataAccessException(se.getMessage(), se);

        } finally {
            DbOper.cleanUp(rs, null, ps, conn);
        }

    }

    /**
     * 根据table基本信息,查询此table的字段信息
     *
     * @param tableInfo
     * @return
     * @throws Exception
     */
    public DbTableInfo qryTableColumnInfo(DbTableInfo tableInfo) throws
            DataAccessException {
        ValidateHelper.notNull(tableInfo);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List resultList = new ArrayList();
        try {
            conn = getConnection();
            if (ValidateHelper.validateNotNull(tableInfo.getTableObjId())
                && tableInfo.getTableObjId().longValue() > 0) {

                ps = conn.prepareStatement(qryclumnInfoByTbObjId);
                ps.setLong(1, tableInfo.getTableObjId().longValue());
            } else if (ValidateHelper
                       .validateNotEmpty(tableInfo.getTableName())) {
                ps = conn.prepareStatement(qryclumnInfoByTbName);
                ps.setString(1, tableInfo.getTableName());
            } else {
                return tableInfo;
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                DbColumnInfo columnInfo = new DbColumnInfo();
                columnInfo.setColumnName(rs.getString("columnName"));
                columnInfo.setColumnType(rs.getString("columnType"));
                columnInfo.setIsComputed(NumberHelper.integerValueOf(rs
                        .getObject("isComputed")));
                columnInfo.setLength(NumberHelper.longValueOf(rs
                        .getObject("Length")));
                columnInfo.setIsIdentity(NumberHelper.integerValueOf(rs
                        .getObject("isIdentity")));
                columnInfo.setNullAble(NumberHelper.integerValueOf(rs
                        .getObject("nullable")));
                columnInfo.setScale(NumberHelper.integerValueOf(rs
                        .getObject("scale")));
                columnInfo.setPrec(rs.getString("prec"));

                resultList.add(columnInfo);
            }
            tableInfo.setColumnInfo((DbColumnInfo[]) resultList
                                    .toArray(new DbColumnInfo[0]));
            return tableInfo;
        } catch (SQLException se) {
            throw new DataAccessException(se.getMessage(), se);

        } finally {
            DbOper.cleanUp(rs, null, ps, conn);
        }
    }

    /**
     * 根据表名确定表信息: 包括:表的基本信息(表的objId,表的类型,表名);表的字段信息ColumnInfo[]
     *
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public DbTableInfo qryTableInfo(String tableName) throws
            DataAccessException {
        ValidateHelper.notNull(tableName);
        DbTableInfo dbTableInfo = new DbTableInfo();
        dbTableInfo.tableName = tableName;
        dbTableInfo.tableObjId = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer advQry = new StringBuffer(200);
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            advQry.append(qryTableInfo).append(" and TNAME=?");
        } else {
            advQry.append(qryTableInfo).append(" and name=?");
        }

        DbTableInfo tableInfo = new DbTableInfo();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(advQry.toString());
            ps.setString(1, tableName);
            rs = ps.executeQuery();
            while (rs.next()) {
                tableInfo.setTableName(rs.getString("tableName"));
                tableInfo.setTableObjId(new Long(rs.getLong("tableObjId")));
                tableInfo.setTableType(rs.getString("tableType"));
            }
            tableInfo = qryTableColumnInfo(tableInfo, conn);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new DataAccessException(se.getMessage(), se);
        } finally {
            DbOper.cleanUp(rs, null, ps, conn);
        }

        return tableInfo;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    protected Connection getConnection() throws SQLException {
        Connection conn = new ConnectionAdapters().newConnection();

        return conn;
    }

    protected DbColumnInfo getColumnInfo(String patternStr,
                                         DbTableInfo tableInfo) {
        DbColumnInfo[] columnInfo = tableInfo.columnInfo;
        String columnName = patternStr
                            .substring(0, patternStr.lastIndexOf(":"));
        if (columnName.indexOf(":") != -1) {
            columnName = columnName.substring(columnName.indexOf(":") + 1);
        }
        for (int i = 0; i < columnInfo.length; i++) {
            if (columnName.equalsIgnoreCase(columnInfo[i].getColumnName())) {
                return columnInfo[i];
            }
        }
        return null;

    }

    /**
     * test
     *
     * @param arg
     */
    public static void main(String[] arg) {
        DbOperSupport getter = DbOperSupportFactory.getFactory()
                               .getDbOperSupport();
        String tableName = "PLAN_TASKORDER";
        try {
            DbTableInfo tableInfo = getter.qryTableInfo(tableName);
            DbTableInfo[] infoArr = getter.qryAllTableInfo();
            for (int i = 0; i < infoArr.length; i++) {
                //System.out.println(infoArr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map tableInfoMap = new HashMap(300);

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#setParValue(java.lang.String[],
     *      java.sql.PreparedStatement, java.util.Map,
     *      com.ztesoft.eoms.common.db.DbColumnInfo[])
     */
    public void setPrepValue(String[] patternStrArr, PreparedStatement ps,
                             Map param, String tableName) throws
            DataAccessException {
        ValidateHelper.notNull(tableName);
        int k = -1;
        String errorKey = "";

        DbTableInfo dbTableInfo = null;

        if (tableInfoMap.containsKey(tableName.trim())) {
            dbTableInfo = (DbTableInfo) tableInfoMap.get(tableName.trim());

        }
        if (!ValidateHelper.validateNotNull(dbTableInfo)) {
            dbTableInfo = DbOperSupportFactory.getFactory().getDbOperSupport()
                          .qryTableInfo(tableName);
            tableInfoMap.put(tableName, dbTableInfo);
        }

        ValidateHelper.notNull(dbTableInfo);

        try {
            int loop = 1;
            for (int j = 0; j < patternStrArr.length; j++) {
                k = 0;

                String mapKey = patternStrArr[j].substring(patternStrArr[j]
                        .lastIndexOf(":") + 1);
                Object mapValue = param.get(mapKey);

                DbColumnInfo _info = getColumnInfo(patternStrArr[j],
                        dbTableInfo);

                // 再取一次
                if (!ValidateHelper.validateNotNull(_info)) {

                    dbTableInfo = DbOperSupportFactory.getFactory()
                                  .getDbOperSupport().qryTableInfo(tableName);

                    tableInfoMap.put(tableName, dbTableInfo);

                    _info = getColumnInfo(patternStrArr[j], dbTableInfo);

                }

                // 如果仍然为空，则抛出应用异常
                if (!ValidateHelper.validateNotNull(_info)) {
                    String columnName = patternStrArr[j].substring(0,
                            patternStrArr[j].lastIndexOf(":"));
                    throw new java.lang.IllegalArgumentException(
                            "COLUMN NAME='" + columnName
                            + "' NOT EXISTS IN TABLE");
                }

                if ("numeric".equalsIgnoreCase(_info.getColumnType()) ||
                    ("NUMBER".equalsIgnoreCase(_info.getColumnType()) &&
                     ValidateHelper.validateNotEmpty(_info.getPrec()))) {
                    if (ValidateHelper.validateNotNull(_info.getScale())
                        && _info.getScale().intValue() > 0) {
                        k = 1;
                        errorKey = mapKey;
                        DbOper.setPrepStatementParam(loop++, ps, NumberHelper
                                .doubleValueOf(mapValue));

                    } else if (_info.getScale().intValue() == 0) {
                        k = 2;
                        errorKey = mapKey;
                        DbOper.setPrepStatementParam(loop++, ps, NumberHelper
                                .longValueOf(mapValue));
                    }
                }

                if ("varchar".equalsIgnoreCase(_info.getColumnType())
                    || "char".equalsIgnoreCase(_info.getColumnType())
                    || "VARCHAR2".equalsIgnoreCase(_info.getColumnType())) {
                    k = 3;
                    errorKey = mapKey;
                    DbOper.setPrepStatementParam(loop++, ps,
                                                 StringHelper.
                                                 stringValueOf(mapValue));
                }
                if ("smalldatetime".equalsIgnoreCase(_info.getColumnType())
                    || "datetime".equalsIgnoreCase(_info.getColumnType())
                    || "DATE".equalsIgnoreCase(_info.getColumnType())) {
                    k = 4;
                    errorKey = mapKey;
                    DbOper.setPrepStatementParam(loop++, ps, DateHelper
                                                 .dateValueOf(mapValue));
                }
                if ("int".equalsIgnoreCase(_info.getColumnType()) ||
                    ("NUMBER".equalsIgnoreCase(_info.getColumnType()) &&
                     !ValidateHelper.validateNotEmpty(_info.getPrec()))) {
                    k = 5;
                    errorKey = mapKey;
                    DbOper.setPrepStatementParam(loop++, ps, NumberHelper
                                                 .integerValueOf(mapValue));
                }
            }
        } catch (Throwable se) {
            String errorMsg = "";
            switch (k) {
            case 1:
                errorMsg =
                        "Parameter type is not correct. The right type is double! key name = "
                        + errorKey;
                break;
            case 2:
                errorMsg =
                        "Parameter type is not correct. The right type is  long! key name = "
                        + errorKey;
                break;
            case 3:
                errorMsg =
                        "Parameter type is not correct. The right type is String! key name = "
                        + errorKey;
                break;
            case 4:
                errorMsg =
                        "Parameter type is not correct.  The right type is date! key name = "
                        + errorKey;
                break;
            case 5:
                errorMsg =
                        "Parameter type is not correct.The right type is integer! key name = "
                        + errorKey;
                break;

            default:
                errorMsg = "Unknown error ";
                break;
            }

            throw new DataAccessException(
                    "Assignment of prep failure, cause :  \n" + errorMsg, se);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#generateSeq(java.lang.String)
     */
    public Long sequence(String tableName, Connection conn) throws
            DataAccessException {
        // TODO Auto-generated method stub
        String func = ConfigMgr.getInstance().getPropertyAsString("common",
                "com.ztesoft.eoms.database.identity.function");
        StringBuffer sequenceSql = new StringBuffer(18);
        if (!ValidateHelper.validateNotEmpty(func)) {
            func = "IDENT_CURRENT";
        }
        func.toUpperCase();
        if (func.trim().equals("IDENT_CURRENT")) {
            sequenceSql.append("select IDENT_CURRENT('").append(tableName).
                    append("')");
        } else if (func.trim().equals("SCOPE_IDENTITY")) {
            sequenceSql.append("select SCOPE_IDENTITY()");
        } else if (func.trim().equals("IDENTITY")) {
            sequenceSql.append("select @@IDENTITY");
        } else {
            throw new DataAccessException(
                    "Unable to understand the definition '"
                    + func.trim().toUpperCase()
                    + "' func identifier \n "
                    + DAOHelper.getDatabaseType()
                    +
                    " {SCOPE_IDENTITY，IDENT_CURRENT ，IDENTITY} to  be supported");
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sequenceSql.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return NumberHelper.longValueOf(rs.getObject(1));

            } else {
                return null;
            }

        } catch (SQLException se) {
            se.printStackTrace();
            throw new DataAccessException("Get Sequence Failed.... ", se);
        } finally {

            DbOper.cleanUp(rs, null, ps, null);
        }
    }

    /*
     * (non-Javadoc)
     * 对于自增长列，获取下一个Sequence
     * @see com.ztesoft.eoms.common.db.DbOperSupport#generateSeq(java.lang.String)
     */
    public Long getNextSequence(String tableName, Connection conn) throws
            DataAccessException {
        String sqlStr = null;
        //Oracle的Squence格式约定为："SEQ_"+表名
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            sqlStr = "SELECT SEQ_" + tableName +
                     ".NEXTVAL AS squenceValue FROM DUAL";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sqlStr.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return NumberHelper.longValueOf(rs.getObject("squenceValue"));
            } else {
                return null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new DataAccessException("Get Sequence Failed.... ", se);
        } finally {
            DbOper.cleanUp(rs, null, ps, null);
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#replaceSequenceDefinition(java.lang.String)
     */
    public String[] replaceSequenceDefinition(String[] patternStrArr) throws
            DataAccessException {
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            return patternStrArr;
        }

        // TODO Auto-generated method stub
        List list = new ArrayList();
        for (int i = 0; i < patternStrArr.length; i++) {
            if (patternStrArr[i].indexOf(":@@SEQ") == -1) {
                list.add(patternStrArr[i]);
            }
        }
        return (String[]) list.toArray(new String[0]);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#lastInsert(java.util.Map,
     *      java.lang.String, java.lang.String, java.sql.Connection)
     */
    public void lastedInsert(Map param, String tableName,
                             String keyName, DbOper dbOper) throws
            DataAccessException {
        // TODO Auto-generated method stub
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            param.put(keyName, param.get("@@SEQ"));
        } else {
            Long sequenceValue = sequence(tableName, dbOper.getDbConnection());
            param.put(keyName, sequenceValue);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#lastedInsetBatch(java.util.List,
     *      java.lang.String, java.lang.String,
     *      com.ztesoft.eoms.common.db.DbOper)
     */
    public void lastedInsetBatch(List param, String tableName, String keyName,
                                 DbOper dbOper) throws DataAccessException {
        // TODO Auto-generated method stub
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            for (int i = 0; i < param.size(); i++) {
                Map _map = (Map) param.get(i);
                _map.put(keyName, _map.get("@@SEQ"));
            }
        } else {
            Long sequenceValue = sequence(tableName, dbOper.getDbConnection());
            int size = param.size();
            long startIndex = -1;
            if (sequenceValue != null) {
                startIndex = sequenceValue.longValue() - size;
            }
            for (int i = 0; i < param.size(); i++) {
                Map _map = (Map) param.get(i);
                _map.put(keyName, new Long(++startIndex));
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#preparedInsert(java.util.Map,
     *      java.lang.String, java.lang.String, java.sql.Connection)
     */
    public void preparedInsert(Map paramMap, String tableName, String keyName,
                               DbOper dbOper) throws DataAccessException {
        // TODO Auto-generated method stub
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            Long sequenceValue = getNextSequence(tableName,
                                                 dbOper.getDbConnection());
            paramMap.put("@@SEQ", sequenceValue);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ztesoft.eoms.common.db.DbOperSupport#preparedInsertBatch(java.util.List,
     *      java.lang.String, java.lang.String,
     *      com.ztesoft.eoms.common.db.DbOper)
     */
    public void preparedInsertBatch(List paramList, String tableName,
                                    String keyName, DbOper dbOper) throws
            DataAccessException {
        // TODO Auto-generated method stub
        if (SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.ORACLE_TAG) {
            Map _map = null;
            for (int i = 0; i < paramList.size(); i++) {
                _map = (Map) paramList.get(i);
                preparedInsert(_map, tableName, keyName, dbOper);
            }
        }
    }
}
