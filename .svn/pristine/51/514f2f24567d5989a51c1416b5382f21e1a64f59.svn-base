package com.ztesoft.eoms.common.idproduct.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zterc.uos.helpers.AbstractDAOImpl;
import com.ztesoft.eoms.common.idproduct.FieldConstants;
import com.ztesoft.eoms.common.idproduct.InitIdDto;
import com.zterc.uos.helpers.DAOSysException;
import java.sql.ResultSet;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.common.dao.jdbc.BaseJDBCDAOImpl;
import com.ztesoft.eoms.common.helper.StringHelper;

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
public class UosMemoryDAOSybase extends BaseJDBCDAOImpl implements UosMemoryDAO {
    public UosMemoryDAOSybase() {
        super();
    }

    private static final String INSERT_SQL =
            "INSERT INTO " + FieldConstants.getSeqDBTableName() + "(" +
            FieldConstants.getSeqIdDBFieldName() + "," +
            FieldConstants.getSeqNameDBFieldName()
            + ")VALUES(?,?)";

    private static final String INCREASE_SQL =
            "UPDATE " + FieldConstants.getSeqDBTableName() + " SET " +
            FieldConstants.getSeqIdDBFieldName() + "="
            + FieldConstants.getSeqIdDBFieldName() + "+" +
            FieldConstants.getSeqIncreaseDBNum() + " WHERE " +
            FieldConstants.getSeqNameDBFieldName() + " =?";

    private static final String DEL_SQL = " DELETE FROM" +
                                          FieldConstants.getSeqDBTableName() +
                                          " WHERE " +
                                          FieldConstants.getSeqNameDBFieldName() +
                                          "=?";

    private static final String SEL_SEQNAME_SQL
            = " SELECT " + FieldConstants.getSeqIdDBFieldName() +
              " FROM " + FieldConstants.getSeqDBTableName() +
              " WHERE " + FieldConstants.getSeqNameDBFieldName() + "=?";

    private void insert(Connection conn, InitIdDto dto) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String tableName = dto.getTableName();
        String columnName = dto.getColumnName();
        String sql = "select MAX(" + columnName.toUpperCase() + ") from " +
                     tableName.toUpperCase() +
                     " ";
        Long _val = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Object tmp = rs.getObject(1);
                if (ValidateHelper.validateNotNull(tmp)) {
                    try {
                        _val = new Long(tmp.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace(System.out);
                        e.printStackTrace(System.err);
                    }
                }
            }
            if (_val != null) {
                dto.setSeqValue(_val.longValue());
            } else {
                dto.setSeqValue(0);
            }
            cleanUp(null, null, ps, rs);

            ps = conn.prepareStatement(INSERT_SQL);

            ps.setLong(1, dto.getSeqValue());
            ps.setString(2, dto.getKey());
            ps.executeUpdate();

        } finally {
            cleanUp(null, null, ps, rs);
        }
    }

    private InitIdDto selBySeqName(Connection conn, String seqName) throws
            SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        InitIdDto dto = null;
        try {
            ps = conn.prepareStatement(SEL_SEQNAME_SQL);
            ps.setString(1, seqName);
            rs = ps.executeQuery();
            if (rs.next()) {
                dto = new InitIdDto();
                dto.setTableName(seqName);
                dto.setSeqValue(rs.getLong(1));
            }
        } finally {
            cleanUp(null, null, ps, rs);
        }
        return dto;
    }

    public InitIdDto increase(InitIdDto dto) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            if (ValidateHelper.validateNotEmpty(dto.getTableName())) {
                InitIdDto _qryObject = selBySeqName(conn, dto.getTableName());
                if (_qryObject != null) {
                    dto.setSeqValue(_qryObject.getSeqValue());
                    updateInitDto(conn, dto);
                    dto.increaseSetpValue(
                            new Long(FieldConstants.getSeqIncreaseDBNum()).
                            longValue());
                } else {
                    insert(conn, dto);
                }

            } else {

            }
        } finally {
            cleanUp(conn, null, null, null);
        }
        return dto;
    }

    private void updateInitDto(Connection conn, InitIdDto dto) throws
            SQLException {
        PreparedStatement ps = null;
        try {
         
            ps = conn.prepareStatement(INCREASE_SQL);
            ps.setString(1, dto.getKey());
            ps.executeUpdate();

        } finally {
            cleanUp(null, null, ps, null);
        }

    }


    public void del(InitIdDto dto) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(DEL_SQL);
            ps.setString(1, dto.getKey());
        } finally {
            cleanUp(conn, null, ps, null);
        }
    }
}
