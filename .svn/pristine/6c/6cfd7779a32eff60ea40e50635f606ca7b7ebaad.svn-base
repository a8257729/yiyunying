package com.ztesoft.mobile.common.db;



public class DbOperSupportForSYBASE extends DbOperSupportForMSSQL{

    public DbOperSupportForSYBASE(){


            /*
                * 适用于Sybase 产品,查询库表字段详细信息 columnName:字段名; columnType:字段类型;
                * isComputed:是否计算值--1 true,0 false; length:长度; prec:精度; scale:范围;
                * nullable:可否为空-- 1 true,0 false;
                */
               // 根据表ID查询

            /* query table info: name,objId,type, */
      qryTableInfo =

                  "select name as tableName,id as tableObjId,type as tableType from dbo.sysobjects  where  type = 'U'";



    qryclumnInfoByTbObjId =

                  "  select a.name as columnName,b.name as columnType,1 as isComputed,"
                  + " convert(int, a.length) as length,"
                  + "  a.prec,a.scale,case a.status when 0 then 0 else 1 end as nullable,1 as isIdentity"
                  +
                  "  from dbo.syscolumns a inner join dbo.systypes b on a.usertype=b.usertype"
                  + " where id =?  order by a.colid";

          // 根据表名查询
    qryclumnInfoByTbName =

                  "  select a.name as columnName,b.name as columnType,null as isComputed,"
                  + " convert(int, a.length) as length,"
                  + "  a.prec,a.scale,case a.status when 0 then 0 else 1 end as nullable,null as isIdentity"
                  +
                  "  from dbo.syscolumns a inner join dbo.systypes b on a.usertype=b.usertype"
                  + "  where a.id in(select id from dbo.sysobjects where name =? and type in('U','V')) "
                  + " order by a.colid";


    }

}
