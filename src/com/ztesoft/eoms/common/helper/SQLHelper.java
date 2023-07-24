package com.ztesoft.eoms.common.helper;

import java.util.Date;

import com.zterc.uos.util.DateUtilities;
import com.ztesoft.eoms.exception.RequiredException;
import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * Title: EomsProj
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
public final class SQLHelper {
    private SQLHelper() {
    }

    /**
     * 分页方法（旧）
     *
     * @param sqlStr
     *            String
     * @param startIndex
     *            int
     * @param currentSize
     *            int
     * @param dbTag
     *            int
     * @return String
     * @throws RequiredException
     */
    public static String populateQuerySQL(String sqlStr, int notInCount,
                                          int currentSize, int dbTag) throws
            RequiredException {
        return populateQuerySQLWithKey(sqlStr, notInCount, currentSize, dbTag,
                                       null);
    }

    private static final Logger logger = Logger.getLogger(SQLHelper.class);

    /**
     * long数组转换成适合用in的sql字段
     * @param conditions   *            Integer[]
     * @return String
     */
    public static String array2SqlStr(Integer[] conditions) {
        if (!ValidateHelper.validateNotEmpty(conditions)) {
            return null;
        }
        String[] conditionStrs = new String[conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            conditionStrs[i] = StringHelper.toString(conditions[i]);
        }

        return array2SqlStr(conditionStrs, false);
    }

    /**
     * long数组转换成适合用in的sql字段
     * @param conditions  Long[]
     * @return String
     */
    public static String array2SqlStr(Long[] conditions) {
        if (!ValidateHelper.validateNotEmpty(conditions)) {
            return null;
        }
        String[] conditionStrs = new String[conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            conditionStrs[i] = StringHelper.toString(conditions[i]);
        }

        return array2SqlStr(conditionStrs, false);
    }

    /**
     * string数组转换成适合用in的sql字段
     * @param conditions String[]
     * @param isStr boolean
     * @return String
     */
    public static String array2SqlStr(String[] conditions, boolean isStr) {
        if (!ValidateHelper.validateNotEmpty(conditions)) {
            return null;
        }
        StringBuffer cond = new StringBuffer("(");
        for (int i = 0; i < conditions.length; i++) {
            if (isStr) {
                cond.append("'").append(conditions[i]).append("'");
            } else {
                cond.append(conditions[i]);
            }
            cond.append(",");
        }
        cond.replace(cond.length() - 1, cond.length(), ")");

        return cond.toString();
    }

    public static final int ORACLE_TAG = 0;

    public static final int SQLSERVER_TAG = 1;

    public static final int SYBASE_TAG = 2;

    public static int CURRENT_DATABASE_TAG = -1;

    static {
        if (DAOHelper.getDatabaseType().toUpperCase().equals("ORACLE")) {
            CURRENT_DATABASE_TAG = SQLHelper.ORACLE_TAG;
        } else if (DAOHelper.getDatabaseType().toUpperCase().equals("SYBASE")) {
            CURRENT_DATABASE_TAG = SQLHelper.SYBASE_TAG;
        } else if (DAOHelper.getDatabaseType().toUpperCase().equals("MSSQL")) {
            CURRENT_DATABASE_TAG = SQLHelper.SQLSERVER_TAG;
        }

    }

    /**
     * 获得替换date数据类型的字符串
     * @param date Date
     * @param dbTag int
     * @return String
     */
    public static String toSqlStr(Date date, int dbTag) {
        return toDateSqlStr(date, dbTag);
    }

    /**
     * 获得替换date数据类型的字符串
     * @param date Date
     * @param dbTag int
     * @return String
     */
    public static String toDateSqlStr(Date date, int dbTag) {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "to_date('" + DateUtilities.getInstance().parse(date)
                    + "','yyyy-MM-dd HH24:mi:ss')";
        case SQLSERVER_TAG:
            return "convert(datetime,'" + DateHelper.parse(date) + "',102)";
        case SYBASE_TAG:
            return "convert(datetime,'" + DateHelper.parse(date) + "',102)";
        default:
            return "";
        }
    }

    public static String toNullSqlStr(String columnName, Object replaceValue) {
        char strSeparator = (replaceValue instanceof String) ? '\'' : ' ';
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "nvl(" + columnName + "," + strSeparator + replaceValue
                    + strSeparator + ")";
        case SQLSERVER_TAG:
            return "isNull(" + columnName + "," + strSeparator + replaceValue
                    + strSeparator + ")";
        case SYBASE_TAG:
            return "";

        default:
            return "";
        }
    }

    public static String toCharIndexSeqStr(int charIndex) {

        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "chr(" + charIndex + ")";
        case SQLSERVER_TAG:
            return "char(" + charIndex + ")";
        case SYBASE_TAG:
            return "";
        default:
            return "";
        }
    }

    public static String toConcatSqlStr() {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "||";
        case SQLSERVER_TAG:
            return "+";
        case SYBASE_TAG:
            return "";
        default:
            return "";
        }
    }

    public static String toSubStrSqlStr(String columnName, int startIndex,
                                        int endIndex) {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "SUBSTR(" + columnName + "," + startIndex + "," + endIndex
                    + ")";
        case SQLSERVER_TAG:
            return "SUBSTRING(" + columnName + "," + startIndex + ","
                    + endIndex + ")";
        case SYBASE_TAG:
            return "";

        default:
            return "";
        }
    }

    /**
     * 日期转为
     * @param columnName String
     * @return String
     */
    public static String dateToString(String columnName) {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "TO_CHAR(" + columnName + ",'yyyy-MM-dd HH24:mi:ss')";
        case SQLSERVER_TAG:
            return "CONVERT(VARCHAR(19)," + columnName + ",120)";
        case SYBASE_TAG:
            return "CONVERT(VARCHAR(10)," + columnName + ",111)" + "+' '+" +
                    "CONVERT(VARCHAR(8)," + columnName + ",108)";
        default:
            return "";
        }
    }
    /**
 * 日期转为
 * @param columnName String
 * @return String
 */
public static String dateToString_queryFault(String columnName) {
    switch (CURRENT_DATABASE_TAG) {
    case ORACLE_TAG:
        return "TO_CHAR(" + columnName + ",'yyyy-MM-dd HH24:mi:ss')";
    case SQLSERVER_TAG:
        return "CONVERT(VARCHAR(19)," + columnName + ",120)";
    case SYBASE_TAG:
        return //"CONVERT(VARCHAR(10)," + columnName + ",111)" + "+' '+" +
                // "CONVERT(VARCHAR(8)," + columnName + ",108)";
                "CONVERT(VARCHAR(4),DATEPART(YEAR," + columnName + "))+'-'+" +
                "CASE WHEN DATEPART(MM," + columnName + ")<10 THEN '0' END + CONVERT(VARCHAR(2),DATEPART(MM," + columnName + "))+'-'+" +
                "CASE WHEN DATEPART(DD," + columnName + ")<10 THEN '0' END + CONVERT(VARCHAR(2),DATEPART(DD," + columnName + "))+' '+" +
                "CONVERT(VARCHAR(12)," + columnName + ",108)" ;
    default:
        return "";
    }
}


    public static String stringToNumeric(String columnName, int pValue) {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "TO_NUMBER(" + columnName + ")";
        case SQLSERVER_TAG:
            return "CONVERT(NUMERIC(" + pValue + ")," + columnName + ")";
        case SYBASE_TAG:
            return "CONVERT(NUMERIC(" + pValue + ")," + columnName + ")";
        default:
            return "";
        }
    }

    public static String numericToVarchar(String columnName, int charLength) {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "TO_CHAR(" + columnName + ")";
        case SQLSERVER_TAG:
            return "CONVERT(VARCHAR(" + charLength + ")," + columnName + ")";
        case SYBASE_TAG:
            return "CONVERT(VARCHAR(" + charLength + ")," + columnName + ")";
        default:
            return "";
        }
    }

    public static String getNoLockSql() {
        switch (CURRENT_DATABASE_TAG) {
        case ORACLE_TAG:
            return "";
        case SQLSERVER_TAG:
            return "WITH(NOLOCK)";
        case SYBASE_TAG:
            return "";
        default:
            return "";
        }
    }


    /**
     * 分页方法（新） key是分页字段，应当具有唯一性，一般用ID(主键) 例如key是"ID"(别名) 或
     * "A.ID"(字段名),则ID必须出现在SELECT出的字段中
     *
     * @param sqlStr
     *            String
     * @param startIndex
     *            int
     * @param currentSize
     *            int
     * @param dbTag
     *            int
     * @param key
     *            String
     * @return String
     * @throws RequiredException
     */
    public static String populateQuerySQLWithKey(String sqlStr, int notInCount,
                                                 int currentSize, int dbTagKey,
                                                 String key) throws
            RequiredException {

        switch (CURRENT_DATABASE_TAG) {
        case SQLSERVER_TAG:
            return populateSQLServerQuerySQLWithKey(sqlStr, notInCount,
                    currentSize, key);
        case ORACLE_TAG:
            return populateOracleQuerySQLWithKey(sqlStr, notInCount,
                                                 currentSize, key);
        default:
            return "";
        }
    }

    /**
     * 重新整合SQL语句，仅仅支持Oracle数据库的重整。 需要采用三层子查询，因为在Oracle中是先定位再排序的。
     * 已知的限制：不能处理表名为ROW_或是列名为ROWNUM_的查询。
     *
     * @param sqlStr
     * @param notInCount
     * @param currentSize
     * @return
     */
    private static String populateOracleQuerySQLWithKey(String sqlStr,
            int notInCount, int currentSize, String key) {
        // 构造合法SQL语句的数据缓冲区
        StringBuffer mainBuffer = new StringBuffer(300);
        if (notInCount == 0 && currentSize <= 0) {
            mainBuffer.append(sqlStr);
        } else {
            // 查询语句
            mainBuffer.append(" SELECT ROW_.*, ROWNUM ROWNUM_ FROM ( ");
            // 给定的查询作为内嵌子查询
            mainBuffer.append(sqlStr);
            // 给内嵌子查询命名
            mainBuffer.append(" ) ROW_ ");
            if (notInCount == 0) {
                mainBuffer.append(" WHERE ROWNUM <= ");
                // 最大索引
                mainBuffer.append(notInCount + currentSize);
            } else if (currentSize <= 0) {
                mainBuffer.append(" WHERE ROWNUM > ");
                // 起始索引
                mainBuffer.append(notInCount);
            } else {
                //
                mainBuffer.insert(0, "SELECT * FROM ( ");
                // 第二层限定结束记录数
                mainBuffer.append(" WHERE ROWNUM <= ");
                // 最大索引
                mainBuffer.append(notInCount + currentSize);
                // 第一层限定开始记录数
                mainBuffer.append(") WHERE ROWNUM_ > ");
                // 最小索引
                mainBuffer.append(notInCount);
            }
        }
        // 调试语句
        if (logger.isDebugEnabled()) {
            logger.debug("populateOracleQuerySQL整合后的语句:"
                         + mainBuffer.toString());
        }
        return mainBuffer.toString();
    }

    /**
     * 为SQL Server 组装分页提供机制
     *
     * @param sqlStr
     *            一般查询语句
     * @param notInCount
     *            int
     * @param currentSize
     *            当前索引
     * @param key
     *            String 分页字段，例如"ID"(别名) 或"A.ID"(字段名)
     * @return 返回SQL语句
     * @throws RequiredException
     *             如果没有Order by 子句将抛出错误
     */
    private static String populateSQLServerQuerySQLWithKey(String sqlStr,
            int notInCount, int currentSize, String key) throws
            RequiredException {
        // 构造合法SQL语句的数据缓冲区
        sqlStr = sqlStr.trim();
        StringBuffer mainBuffer = new StringBuffer(300);
        String sqlTemp = sqlStr.toUpperCase();
        if (notInCount == 0 && currentSize <= 0) {
            mainBuffer.append(sqlStr);
        } else {
            // 先分析order by子句
            int orderbyIndex = sqlTemp.lastIndexOf(" ORDER BY ");
            int fromIndex = sqlTemp.indexOf(" FROM ");
            boolean isDistict = sqlTemp.indexOf(" DISTINCT ") != -1;
            // 加入key支持
            boolean isUseKey = ValidateHelper.validateNotEmpty(key);
            String keyTableAlias = "";
            String keyField = "";
            if (isUseKey) {
                String keyTemp = key.toUpperCase();
                if (keyTemp.indexOf(".") != -1) {
                    keyTableAlias = keyTemp.substring(0, keyTemp.indexOf("."));
                    keyField = keyTemp.substring(keyTemp.indexOf(".") + 1);
                } else {
                    keyField = keyTemp;
                }
            }
            if (orderbyIndex > 0) {
                mainBuffer.append("SELECT * FROM ( SELECT ");
                if (isDistict) {
                    mainBuffer.append(" DISTINCT ");
                }
                mainBuffer.append(" TOP ");
                mainBuffer.append((currentSize + notInCount)).append(" ");
                if (isDistict) {
                    mainBuffer.append(StringHelper.substr(sqlStr.toString(),
                            sqlTemp.indexOf(" DISTINCT ")
                            + " DISTINCT ".length(),
                            orderbyIndex + 1));

                } else {
                    mainBuffer.append(StringHelper.substr(sqlStr.toString(),
                            sqlTemp.indexOf("SELECT ") +
                            "SELECT ".length(),
                            orderbyIndex + 1));

                }

                String orderbyStr = sqlStr.substring(orderbyIndex
                        + " ORDER BY ".length());
                String selectStr = null;
                if (isDistict) {
                    selectStr = StringHelper.substr(sqlStr.toString(), sqlTemp
                            .indexOf(" DISTINCT ")
                            + " DISTINCT ".length(),
                            sqlTemp.indexOf(" FROM "));

                } else {
                    selectStr = StringHelper.substr(sqlStr.toString(), sqlTemp
                            .indexOf("SELECT ")
                            + "SELECT ".length(),
                            sqlTemp.indexOf(" FROM "));

                }

                String[] selectFields = StringHelper.split(selectStr, ",");
                String[] orderFields = StringHelper.split(orderbyStr, ",");
                if (orderFields.length < 1) {
                    throw new RequiredException("'Order by' SQL ");
                }
                // 分析排序字段
                String[] tableAlaises = new String[orderFields.length];
                String[] sortFields = new String[orderFields.length];
                String[] sortFieldsAlias = new String[orderFields.length];
                String[] sortFlags = new String[orderFields.length];

                for (int i = 0; i < orderFields.length; i++) {
                    String fieldTemp = orderFields[i].trim();
                    String tableAlais = "";
                    String sortField = "";
                    String sortFieldAlias = "";
                    String sortFlag = "";

                    // 从后面开始是避免多个别名连接，比如:eoms.a.某某字段
                    int dotIndex = fieldTemp.lastIndexOf(".");
                    int spaceIndex = fieldTemp.indexOf(" ");
                    int firstIndex = 0;
                    int endIndex = fieldTemp.length();
                    if (dotIndex > 0) {
                        // 加一表示把'.'字符长度加入
                        firstIndex = dotIndex + 1;
                        tableAlais = fieldTemp.substring(0, dotIndex);
                    }
                    if (spaceIndex > 0) {
                        endIndex = spaceIndex;
                        sortFlag = fieldTemp.substring(spaceIndex);
                    } else {
                        spaceIndex = endIndex;
                    }
                    sortField = fieldTemp.substring(firstIndex, endIndex);

                    // 把 ORDER BY 的字段的别名找出来
                    sortFieldAlias = sortField; // 别名默认就是字段名
                    for (int j = 0; j < selectFields.length; j++) {
                        String tempSelectField = selectFields[j].trim()
                                                 .toUpperCase();
                        int asIndex = tempSelectField.indexOf(fieldTemp
                                .substring(0, spaceIndex).
                                toUpperCase()
                                + " AS ");
                        if (asIndex != -1) {
                            sortFieldAlias = tempSelectField
                                             .substring(tempSelectField.indexOf(
                                    " AS ")
                                    + " AS ".length());
                            break;
                        }
                    }

                    tableAlaises[i] = tableAlais;
                    sortFields[i] = sortField;
                    sortFieldsAlias[i] = sortFieldAlias;
                    sortFlags[i] = sortFlag;
                }

                // 开始拼装分页的ORDER BY
                String orderByKeyStr = "";
                if (isUseKey) {
                    // 1.key是表的字段名--key是"tableAlias.columnName"的形式
                    if (ValidateHelper.validateNotEmpty(keyTableAlias)) {
                        orderByKeyStr = keyTableAlias + "." + keyField;
                    } else {
                        boolean isFinded = false;

                        // 2.key是表的字段名--key是"columnName"的形式
                        for (int i = 0; i < selectFields.length; i++) {
                            if (selectFields[i].trim().equals(key)) {
                                orderByKeyStr = key.toUpperCase();
                                isFinded = true;
                                break;
                            }

                        }
                        if (!isFinded) {
                            // 3.key是字段别名--key是"columnAlias"的形式
                            for (int i = 0; i < selectFields.length; i++) {
                                int asIndex = selectFields[i].trim()
                                              .toUpperCase().indexOf(
                                        " AS " + key.toUpperCase());
                                if (asIndex != -1) {
                                    orderByKeyStr = selectFields[i].substring(
                                            0, asIndex);
                                    break;
                                }

                                if (i == selectFields.length - 1) {
                                    throw new RequiredException(
                                            "key在SELECT语句中不存在");
                                }
                            }
                        }
                    }
                    orderByKeyStr += " DESC ";
                } else {
                    if (tableAlaises[0].length() > 0) {
                        orderByKeyStr = tableAlaises[0] + ".";
                    }
                    orderByKeyStr += sortFields[0] + " " + sortFlags[0];
                } // end of 拼装分页的ORDER BY

                // ORDER BY #分页字段(key)#
                mainBuffer.append(" ORDER BY ").append(orderByKeyStr).append(
                        " ) ROW_ WHERE ROW_.");

                // 开始拼装分页的字段别名
                if (ValidateHelper.validateNotEmpty(key)) {
                    // 1.key是表的字段名--key是"tableAlias.columnName"的形式
                    if (key.indexOf(".") != -1) {
                        for (int i = 0; i < selectFields.length; i++) {
                            int asIndex = selectFields[i].trim().toUpperCase()
                                          .indexOf(key.toUpperCase() + " AS ");
                            // 表示key有别名，取别名
                            if (asIndex != -1) {
                                mainBuffer.append(selectFields[i]
                                                  .substring(selectFields[i]
                                        .indexOf(" AS ")
                                        + " AS ".length()));
                                break;
                            }
                            // 表示key没有别名，取keyField
                            if (i == selectFields.length - 1) {
                                mainBuffer.append(keyField);
                            }
                        }
                    } else { // 3.key是字段别名--key是"columnAlias"的形式
                        mainBuffer.append(key.toUpperCase());
                    }
                } else {
                    mainBuffer.append(sortFieldsAlias[0]); // 这里必须用别名sortFieldAlias
                }

                mainBuffer.append(" NOT IN (  ");
                // 加入distinct支持
                if (isDistict) {
                    mainBuffer.append("SELECT DISTINCT TOP ");
                } else {
                    mainBuffer.append("SELECT TOP ");
                }
                mainBuffer.append(notInCount);
                mainBuffer.append(" ");
                if (ValidateHelper.validateNotEmpty(key)) {
                    // 1.key是表的字段名--key是"tableAlias.columnName"的形式
                    if (key.indexOf(".") != -1) {
                        mainBuffer.append(key.toUpperCase());
                    } else {
                        boolean isFinded = false;
                        // 2.key是表的字段名--key是"columnName"的形式
                        for (int i = 0; i < selectFields.length; i++) {
                            if (selectFields[i].trim().equals(key)) {
                                mainBuffer.append(key.toUpperCase());
                                isFinded = true;
                                break;
                            }
                        }
                        if (!isFinded) {
                            // 3.key是字段别名--key是"columnAlias"的形式
                            for (int i = 0; i < selectFields.length; i++) {
                                int asIndex = selectFields[i].trim()
                                              .toUpperCase().indexOf(
                                        " AS " + key.toUpperCase());
                                if (asIndex != -1) {
                                    mainBuffer.append(selectFields[i]);
                                    break;
                                }
                                if (i == selectFields.length - 1) {
                                    throw new RequiredException(
                                            "key在SELECT语句中不存在");
                                }
                            }
                        }
                    }
                } else {
                    if (tableAlaises[0].length() > 0) {
                        mainBuffer.append(tableAlaises[0]);
                        mainBuffer.append(".");
                        mainBuffer.append(sortFields[0]);
                    } else {
                        boolean isFinded = false;
                        for (int i = 0; i < selectFields.length; i++) {
                            if (selectFields[i].trim().equals(sortFields[0])) {
                                mainBuffer.append(sortFields[0]);
                                isFinded = true;
                                break;
                            }
                        }
                        if (!isFinded) {
                            for (int i = 0; i < selectFields.length; i++) {
                                int asIndex = selectFields[i].trim()
                                              .toUpperCase().indexOf(
                                        " AS "
                                        + sortFields[0]
                                        .toUpperCase());
                                if (asIndex != -1) {
                                    mainBuffer.append(selectFields[i]);
                                    break;
                                }
                                if (i == selectFields.length - 1) {
                                    throw new RequiredException("AliasName:"
                                            + sortFields[0] + "在条件语句中不存在");
                                }
                            }
                        }
                    }
                }
                mainBuffer.append(" ");
                mainBuffer
                        .append(sqlStr.substring(fromIndex, orderbyIndex + 1));
                // ORDER BY #分页字段(key)#
                mainBuffer.append(" ORDER BY ").append(orderByKeyStr).append(
                        " ) ");
                // ORDER BY #排序字段#
                mainBuffer.append(" ORDER BY ");
                for (int i = 0; i < orderFields.length; i++) {
                    mainBuffer.append("ROW_.");
                    mainBuffer.append(sortFieldsAlias[i]); // 这里必须用别名
                    mainBuffer.append(sortFlags[i]);
                    if (i < (orderFields.length - 1)) {
                        mainBuffer.append(",");
                    }
                }
            } else {
                throw new RequiredException("'Order by' SQL ");
            }
        }
        return mainBuffer.toString();
    }

    /**
     * 组装查询记录总数的SQL语句
     *
     * @param sqlStr
     *            查询记录的SQL语句
     * @return 统计查询总数的SQL语句
     */
    public static String preparedCalculateSQL(String sqlStr) {
        StringBuffer sqlBuffer = new StringBuffer("SELECT COUNT(*) FROM (");
        // 去掉Order By语句，加快统计总数的速度
        int orderbyIndex = sqlStr.toUpperCase().lastIndexOf("ORDER BY");
        if (orderbyIndex > 0) {
            sqlBuffer.append(sqlStr.substring(0, orderbyIndex));
        } else {
            sqlBuffer.append(sqlStr);
        }
        sqlBuffer.append(") alias_ ");
        return sqlBuffer.toString();
    }

    /**
     * 获取当前使用的数据库的TAG
     *
     * @return int
     */
    public static int getDatabaseTypeTag() {
        return CURRENT_DATABASE_TAG;
    }

    /**
     * 获取指定SQL语句的记录数
     *
     * @param sql
     *            String
     * @return int
     * @throws SQLException
     */
    public static int getCount(String sql, Connection conn) throws SQLException {
        if (!ValidateHelper.validateNotEmpty(sql)
            || !ValidateHelper.validateNotNull(conn)) {
            return 0;
        }

        String sqlStrCount = SQLHelper.preparedCalculateSQL(sql);
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sqlStrCount.toString());
            rs = ps.executeQuery();
            rs.next();
            int totalRecords = rs.getInt(1);
            return totalRecords;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

}
