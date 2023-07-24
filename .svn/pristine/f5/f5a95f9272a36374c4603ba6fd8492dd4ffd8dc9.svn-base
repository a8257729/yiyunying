package com.ztesoft.eoms.common.idproduct;

import java.io.Serializable;

/**
 *
 * <p>Title: 电子运维项目</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: ZTESoft</p>
 *
 * @author dawn
 * @version 1.0
 */
public final class InitIdDto implements Serializable {


    /** 标识. */
    private int initId;

    /** 表名. */
    private String tableName;

    /** 列名. */
    private String columnName;

    /**取值的key名*/
    private String key;

    private long seqValue=-1;

    private long setpValue = -1;


    /**
     * 构造器
     */
    public InitIdDto() {
    }

    /**
     * 设置标识
     * @param initId int
     */
    public void setInitId(int initId) {
        this.initId = initId;
    }

    /**
     * 设置表名
     * @param tableName String
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 设置列名
     * @param columnName String
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setSeqValue(long seqValue) {
        this.seqValue = seqValue;
    }

    public void increaseSeqValue(long num) {
        this.seqValue += num;
    }

    public void setSetpValue(long setpValue) {
        this.setpValue = setpValue;
    }

    public void decreaseSetpValue(long num) {
        this.setpValue -= num;
    }

    public void increaseSetpValue(long num) {
        this.setpValue += num;
    }

    /**
     * 得到标识
     * @return int
     */
    public int getInitId() {
        return initId;
    }

    /**
     * 得到表名
     * @return String
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 得到列名
     * @return String
     */
    public String getColumnName() {
        return columnName;
    }


    public String getKey() {
        return key;
    }

    public long getSeqValue() {
        return seqValue;
    }

    public long getSetpValue() {
        return setpValue;
    }

    /**
     * 重载父类方法
     * @return int
     */
    public int hasCode() {
        return 37 + 17 * this.initId;
    }

}
