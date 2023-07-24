package com.ztesoft.eoms.common.db;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.eoms.common.helper.StringHelper;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author  dawn
 * @version 1.0
 */
public class SQLCache {

    private SQLCache() {
    }

    private static final Map INSERT_SQL_CACHE = new HashMap(300);

    private static final Map UPDATE_SQL_CACHE = new HashMap(300);

    private static final Map DELETE_SQL_CACHE = new HashMap(300);


    public static String putInsertSQL(String tableName, String patternStr,
                                      String value) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(StringHelper.
                replaceNull(patternStr)).append("}");
        INSERT_SQL_CACHE.put(_buffer.toString(), value);
        return _buffer.toString();
    }


    public static String getInsertSQL(String tableName, String patternStr) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(StringHelper.
                replaceNull(patternStr)).append("}");

        return StringHelper.replaceNull((String) INSERT_SQL_CACHE.get(_buffer.
                toString()));
    }


    public static String putUpdateSQL(String tableName, String patternStr,
                                      String whereStr, String value) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(
                StringHelper.replaceNull(patternStr)).append("$").append(
                        StringHelper.replaceNull(whereStr)).append("}");
        UPDATE_SQL_CACHE.put(_buffer.toString(), value);

        return _buffer.toString();

    }

    public static String getUpdateSQL(String tableName, String patternStr,
                                      String wherePatternStr) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(
                StringHelper.replaceNull(patternStr)).append("$").append(
                        StringHelper.replaceNull(wherePatternStr)).append("}");

        return StringHelper.replaceNull((String) UPDATE_SQL_CACHE.get(_buffer.
                toString()));

    }

    public static String putDeleteSQL(String tableName, String whereStr,
                                      String value) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(StringHelper.
                replaceNull(whereStr)).append("}");
        DELETE_SQL_CACHE.put(_buffer.toString(), value);
        return _buffer.toString();

    }

    public static String getDeleteSQL(String tableName, String whereStr) {
        StringBuffer _buffer = new StringBuffer(50);
        _buffer.append("&").append(tableName).append("{").append(StringHelper.
                replaceNull(whereStr)).append("}");

        return StringHelper.replaceNull((String) DELETE_SQL_CACHE.get(_buffer.
                toString()));
    }


}
