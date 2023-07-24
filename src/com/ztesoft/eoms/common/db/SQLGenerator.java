/**
 *
 */
package com.ztesoft.eoms.common.db;

import com.ztesoft.eoms.common.db.sql.DeleteSqlBuilder;
import com.ztesoft.eoms.common.db.sql.ISqlBuilder;
import com.ztesoft.eoms.common.db.sql.InsertSqlBuilder;
import com.ztesoft.eoms.common.db.sql.UpdateSqlBuilder;
import com.ztesoft.eoms.common.helper.ValidateHelper;

/**
 * @author dawn
 *
 */
public class SQLGenerator {

    static final int INSERT_TAG = 1;

    static final int UPDATE_TAG = 2;

    static final int DEL_TAG = 3;

    //static final int SEL_TAG = 4;

    private static final InsertSqlBuilder iBuilder = new InsertSqlBuilder();

    private static final UpdateSqlBuilder uBuilder = new UpdateSqlBuilder();

    private static final DeleteSqlBuilder dBuilder = new DeleteSqlBuilder();



    public static String generateSql(String[] columnPatternStrArr,
                                     String[] whereColumnPatternStrArr,
                                     String tableName, int tag) {
        ISqlBuilder builder = null;
        switch (tag) {

        case INSERT_TAG: {
            ValidateHelper.notNull(columnPatternStrArr);
            builder = iBuilder;

            break;
        }
        case UPDATE_TAG: {
            ValidateHelper.notNull(columnPatternStrArr);
            builder = uBuilder;
            break;
        }
        case DEL_TAG: {
            builder = dBuilder;
            break;
        }
        default:
            throw new java.lang.UnsupportedOperationException(
                    "This Operation UnSupport .. By generateSql");
        }

        return generateIUDSql(columnPatternStrArr, whereColumnPatternStrArr,
                         tableName, builder);
    }

    /**
     * 得到一条修改语句
     *
     * @param updateColumnName
     * @param whereColumnName
     * @param tableName
     * @return
     */
    private static String generateIUDSql(String[] columnPatternStrArr,
                                    String[] wherePatternStrArr,
                                    String tableName, ISqlBuilder builder) {

        StringBuffer sql = new StringBuffer(100);

        sql.append(builder.buildHeaderSql(tableName));
        sql.append(builder.buildColumnSql(columnPatternStrArr));
        sql.append(builder.buildWhereColumnSql(wherePatternStrArr));
        return sql.toString();
    }

}
