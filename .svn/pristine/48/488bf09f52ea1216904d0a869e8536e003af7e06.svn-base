package com.ztesoft.eoms.common.idproduct.stragety;

import java.io.IOException;
import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.idproduct.InitIdDto;

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
public interface IdGetStragety {
    public void refresh() throws UOSException;

    public long nextId(String key, int addNum) throws
            UOSException;

    public InitIdDto[] qryInitIdDto() throws UOSException;
}
