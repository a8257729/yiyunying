package com.ztesoft.eoms.common.idproduct.dao;

import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.common.idproduct.InitIdDto;
import java.sql.SQLException;



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
public interface UosMemoryDAO extends BaseDAO{

    public InitIdDto increase(InitIdDto dto)throws SQLException;

}
