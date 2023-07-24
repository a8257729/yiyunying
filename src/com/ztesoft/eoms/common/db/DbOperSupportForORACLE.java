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
         * ������Oracle ��Ʒ,��ѯ����ֶ���ϸ��Ϣ columnName:�ֶ���; columnType:�ֶ�����;
         * isComputed:�Ƿ����ֵ--1 true,0 false; length:����; prec:����; scale:��Χ;
         * nullable:�ɷ�Ϊ��-- 1 true,0 false;
         */
        // ���ݱ�ID��ѯ

        /* query table info: name,objId,type, */
        qryTableInfo = "select TNAME as tableName,NULL as tableObjId,TABTYPE as tableType from tab where TABTYPE='TABLE'";

        qryclumnInfoByTbObjId = null;

        // ���ݱ�����ѯ
        qryclumnInfoByTbName =
                "select a.CNAME as columnName, COLTYPE as columnType, 1 as isComputed," +
                "WIDTH as length, PRECISION as prec, SCALE as scale," +
                "case when NULLS='NULL' then 1 else 0 end as nullable," +
                "1 as isIdentity" +
                " from col a" +
                " where a.TNAME=?";
    }
}
