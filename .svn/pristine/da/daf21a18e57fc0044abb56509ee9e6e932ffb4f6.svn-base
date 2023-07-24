package com.ztesoft.eoms.common.db;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DbOperSupportForORACLE extends DbOperSupportForMSSQL {
    public DbOperSupportForORACLE() {
        /*
         * 适用于Oracle 产品,查询库表字段详细信息 columnName:字段名; columnType:字段类型;
         * isComputed:是否计算值--1 true,0 false; length:长度; prec:精度; scale:范围;
         * nullable:可否为空-- 1 true,0 false;
         */
        // 根据表ID查询

        /* query table info: name,objId,type, */
        qryTableInfo = "select TNAME as tableName,NULL as tableObjId,TABTYPE as tableType from tab where TABTYPE='TABLE'";

        qryclumnInfoByTbObjId = null;

        // 根据表名查询
        qryclumnInfoByTbName =
                "select a.CNAME as columnName, COLTYPE as columnType, 1 as isComputed," +
                "WIDTH as length, PRECISION as prec, SCALE as scale," +
                "case when NULLS='NULL' then 1 else 0 end as nullable," +
                "1 as isIdentity" +
                " from col a" +
                " where a.TNAME=?";
    }
}
