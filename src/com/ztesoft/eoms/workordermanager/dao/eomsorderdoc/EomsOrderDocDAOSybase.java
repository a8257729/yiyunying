package com.ztesoft.eoms.workordermanager.dao.eomsorderdoc;

import java.sql.*;

import com.zterc.uos.exception.*;
import com.zterc.uos.helpers.*;
import com.ztesoft.eoms.workordermanager.dto.*;
import com.ztesoft.eoms.common.helper.DAOHelper;
import java.io.IOException;
import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.helper.ValidateHelper;

public class EomsOrderDocDAOSybase
    extends EomsOrderDocDAOMSSQL{

    /**
     * 空构造方法
     */
    public EomsOrderDocDAOSybase() {
    }

    /**
     * 构造方法
     * @param dsName String 数据源的名字
     * @param debug boolean 是否测试用
     */
    public EomsOrderDocDAOSybase(String dsName, boolean debug) {
        super(dsName, debug);
    }

    public EomsOrderDocDto insertEomsOrderDoc(EomsOrderDocDto eomsOrderDocDto) throws
        SQLException, RequiredException, TooLongException, UOSException {
        if (!ValidateHelper.validateNotNull(eomsOrderDocDto.getId())) {
            eomsOrderDocDto.setId(DAOHelper.getPKFromMem("eoms_order_doc",
                9));
        }

        validate(eomsOrderDocDto);
        String sqlStr = "insert into EOMS_ORDER_DOC (ID,FILE_NAME,DOCUMENT_CONTENT)         values(?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        int dbloop = 1;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setLong(dbloop++, eomsOrderDocDto.getId().longValue());
            ps.setString(dbloop++, eomsOrderDocDto.getFileName());
            DAOUtils.setImage(ps,dbloop++, eomsOrderDocDto.getDocumentContent());
            //ps.setBytes(dbloop++, eomsOrderDocDto.getDocumentContent());
            ps.executeUpdate();
        }
        finally {
            cleanUp(conn, null, ps, null);
        }
        return eomsOrderDocDto;
    }

    public int updateEomsOrderDoc(EomsOrderDocDto eomsOrderDocDto) throws
        SQLException,
        RequiredException, TooLongException {
        validate(eomsOrderDocDto);
        String sqlStr =
            "update EOMS_ORDER_DOC set FILE_NAME=?,DOCUMENT_CONTENT=? where ID=?";
        Connection conn = null;
        PreparedStatement ps = null;
        int dbloop = 1;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);

            ps.setString(dbloop++, eomsOrderDocDto.getFileName());
            DAOUtils.setImage(ps,dbloop++, eomsOrderDocDto.getDocumentContent());
            //ps.setBytes(dbloop++, eomsOrderDocDto.getDocumentContent());
            ps.setLong(dbloop++, eomsOrderDocDto.getId().longValue());

            return ps.executeUpdate();
        }
        finally {
            cleanUp(conn, null, ps, null);
        }
    }

    public int deleteEomsOrderDoc(long id) throws SQLException,
        RequiredException {
        String sqlStr = "delete from EOMS_ORDER_DOC where ID=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
        finally {
            cleanUp(conn, null, ps, null);
        }
    }

    public EomsOrderDocDto selectEomsOrderDocById(long EomsOrderDocId) throws
         IOException,SQLException {
        EomsOrderDocDto dto = null;
        String sqlStr = "select ID,FILE_NAME,DOCUMENT_CONTENT from EOMS_ORDER_DOC where ID=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setLong(1, EomsOrderDocId);
            rs = ps.executeQuery();
            while (rs.next()) {
                dto = new EomsOrderDocDto();
                dto.setId(new Long(rs.getLong("ID")));
                dto.setFileName(rs.getString("FILE_NAME"));
                dto.setDocumentContent(DAOUtils.getImage(rs,3));
                //dto.setDocumentContent(rs.getBytes("DOCUMENT_CONTENT"));
            }
        }
        finally {
            cleanUp(conn, null, ps, rs);
        }
        return dto;
    }

    private void validate(EomsOrderDocDto eomsOrderDocDto) throws
        RequiredException,
        TooLongException {
        if (!ValidateHelper.validateNotNull(eomsOrderDocDto )) {
            throw new RequiredException("EOMS_ORDER_DOC");
        }

    }
}
