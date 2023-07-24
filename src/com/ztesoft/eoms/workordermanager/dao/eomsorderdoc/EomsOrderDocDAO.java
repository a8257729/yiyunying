package com.ztesoft.eoms.workordermanager.dao.eomsorderdoc;

import java.sql.*;

import com.zterc.uos.exception.*;
import com.ztesoft.eoms.workordermanager.dto.*;
import java.io.IOException;
import com.zterc.uos.UOSException;

public interface EomsOrderDocDAO {

    public EomsOrderDocDto insertEomsOrderDoc(EomsOrderDocDto inputTypeDto) throws
        SQLException, RequiredException, TooLongException,UOSException;
    
    public EomsOrderDocDto insertEomsOrderDocNoTran(EomsOrderDocDto inputTypeDto) throws
    SQLException, RequiredException, TooLongException,UOSException;
    public int updateEomsOrderDoc(EomsOrderDocDto inputTypeDto) throws SQLException,
        RequiredException, TooLongException;

    public int deleteEomsOrderDoc(long id) throws SQLException, RequiredException;

    public EomsOrderDocDto selectEomsOrderDocById(long id) throws IOException,SQLException;
    
    public void setDocumentContent(long fileId,byte[] fileBytes) throws SQLException;
    public void setDocumentContentNoTran(long fileId,byte[] fileBytes) throws SQLException;

}
