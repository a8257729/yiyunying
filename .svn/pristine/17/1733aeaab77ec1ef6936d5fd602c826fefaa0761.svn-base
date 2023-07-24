package com.ztesoft.eoms.common.idproduct;

import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.helper.DAOHelper;
import com.ztesoft.eoms.common.idproduct.stragety.IdGetStragetyFactory;

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
public class TestIdProduct {
    public void testFunc() throws Exception {
        Long id = DAOHelper.getPKFromMem("VIP_GROUP", 1);
        throw new Exception("TestIdProduct Exception...");
    }

    public static InitIdDto[] qryInitIdDto() throws UOSException {
        return IdGetStragetyFactory.getIdGetStragety(null).qryInitIdDto();

    }
}
