package com.ztesoft.eoms.common.idproduct.stragety;

import java.util.Map;

import com.ztesoft.eoms.common.idproduct.FieldConstants;
import com.zterc.uos.UOSException;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public abstract class IdGetStragetyFactory {
    private static UseNeedDBTableStragety _useNeedDbTableStragety = null;
    private static UseNotNeedDBTableStragety _useNotNeedDbTableStragety = null;

    private IdGetStragetyFactory() {
    }

    private static IdGetStragety getUseNeedDBTableStragety(String confFile) throws UOSException{
        if (_useNeedDbTableStragety == null) {
            _useNeedDbTableStragety = new UseNeedDBTableStragety(confFile);
        }
        return _useNeedDbTableStragety;
    }

    private static IdGetStragety getUseNotNeedDBTableStragety(String confFile)  throws UOSException{

        if (_useNotNeedDbTableStragety == null) {
            _useNotNeedDbTableStragety = new UseNotNeedDBTableStragety(confFile);
        }
        return _useNotNeedDbTableStragety;

    }

    public static IdGetStragety getIdGetStragety(String confFile) throws UOSException {
        switch (FieldConstants.getSeqGetStrategy()) {

        case FieldConstants.NEED_DB_TABLE:
            return getUseNeedDBTableStragety(confFile);

        case FieldConstants.NOT_NEED_DB_TABLE:
            return getUseNotNeedDBTableStragety(confFile);
        default:
            throw new IllegalArgumentException(
                    "the  strategy should not be supported !");

        }

    }


}
