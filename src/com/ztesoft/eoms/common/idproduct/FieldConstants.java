package com.ztesoft.eoms.common.idproduct;

import java.util.ResourceBundle;

import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.common.idproduct.dao.UosMemoryDAO;
import com.ztesoft.eoms.common.configure.ConfigMgr;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class FieldConstants {
    private FieldConstants() {
    }

    private static final String UOS_MEMORY_DAO_PATH = UosMemoryDAO.class.
            getName();

    private static String SEQ_DB_TABLE_NAME;
    private static String SEQ_ID_DB_FIELD_NAME;
    private static String SEQ_NAME_DB_FIELD_NAME;
    private static String SEQ_INCREASE_DB_NUM;
    private static int SEQ_GET_STRATEGY;
    public static final int NOT_NEED_DB_TABLE = 1;
    public static final int NEED_DB_TABLE = 2;


    static {
        initialize();
    }

    private static void initialize() {

     SEQ_DB_TABLE_NAME=   setField("com.ztesoft.eoms.memoryseq.tableName",
                 "UOS_MEMORY_SEQUENCE");

    SEQ_ID_DB_FIELD_NAME=    setField("com.ztesoft.eoms.memoryseq.field.seqId",
                 "SEQ_ID");

    SEQ_NAME_DB_FIELD_NAME=    setField(
                 "com.ztesoft.eoms.memoryseq.field.seqName", "SEQ_NAME");

  SEQ_INCREASE_DB_NUM=      setField(
                 "com.ztesoft.eoms.memoryseq.field.increaseNum", "1000");

        setSeqGetStrategy("com.ztesoft.eoms.memoryseq.seqGetStrategy",
                          NOT_NEED_DB_TABLE);

    }

    public static void setSeqGetStrategy(String key, int _default) {

        if (ValidateHelper.validateNotEmpty(key)) {
            String value = ConfigMgr.getInstance().getPropertyAsString("common",
                    key).trim();

            if (value.equalsIgnoreCase("NOT_NEED_DB_TABLE")) {
                SEQ_GET_STRATEGY = NOT_NEED_DB_TABLE;
            } else if (value.equalsIgnoreCase("NEED_DB_TABLE")) {
                SEQ_GET_STRATEGY = NEED_DB_TABLE;
            }

        } else {

            SEQ_GET_STRATEGY = _default;
        }

    }


    public static String setField( String key, String _default) {
       String _field = "";
        if (!ValidateHelper.validateNotEmpty(_field)) {
            String value = ConfigMgr.getInstance().getPropertyAsString("common",
                    key).trim();
            _field = ValidateHelper.
                     validateNotEmpty(value) ?

                     value : _default;

        }
            return _field;
    }


    public static String getSeqIdDBFieldName() {

        return SEQ_ID_DB_FIELD_NAME;

    }

    public static String getSeqNameDBFieldName() {

        return SEQ_NAME_DB_FIELD_NAME;

    }

    public static String getSeqIncreaseDBNum() {

        return SEQ_INCREASE_DB_NUM;

    }

    public static String getSeqDBTableName() {

        return SEQ_DB_TABLE_NAME;

    }

    public static int getSeqGetStrategy() {

        return SEQ_GET_STRATEGY;

    }

    public static String getUosMemoryDAOPath() {
        return UOS_MEMORY_DAO_PATH;
    }

    public static void main(String[] args){
     System.out.println(SEQ_DB_TABLE_NAME);
     System.out.println(SEQ_ID_DB_FIELD_NAME);
     System.out.println(SEQ_NAME_DB_FIELD_NAME);
     System.out.println(SEQ_INCREASE_DB_NUM);
    }
}
