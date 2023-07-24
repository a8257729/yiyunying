package com.ztesoft.eoms.workordermanager.dao.eomsorderdoc;

import java.io.*;
import java.sql.*;

import com.zterc.uos.exception.*;
import com.zterc.uos.helpers.*;
import com.ztesoft.eoms.workordermanager.dto.*;
import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.helper.DAOHelper;

public class EomsOrderDocDAOOracle
    extends EomsOrderDocDAOMSSQL {
    /**
     * 空构造方法
     */
    public EomsOrderDocDAOOracle() {
        super();
    }

    /**
     * 构造方法
     * @param dsName String 数据源的名字
     * @param debug boolean 是否测试用
     */
    public EomsOrderDocDAOOracle(String dsName, boolean debug) {
        super(dsName, debug);
    }

    public EomsOrderDocDto insertEomsOrderDoc(EomsOrderDocDto eomsOrderDocDto) throws
        SQLException, RequiredException, TooLongException, UOSException {
        if (eomsOrderDocDto.getId() == null) {
            Long id = DAOHelper.getPKFromMem("eoms_order_doc", 9);
            eomsOrderDocDto.setId(id);
        }

        final String sqlStr =
            "insert into eoms_order_doc (id,file_name,document_content)         values(?,?,EMPTY_BLOB())";
        Connection conn = null;
        PreparedStatement ps = null;
        int dbloop = 1;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sqlStr);
            ps.setLong(dbloop++, eomsOrderDocDto.getId().longValue());
            ps.setString(dbloop++, eomsOrderDocDto.getFileName());
            ps.executeUpdate();
            conn.commit();
        }catch (Exception e) {
        	conn.rollback();
        	e.printStackTrace();
        }
        finally {
            cleanUp(conn, null, ps, null);
        }
        //插入文件内容
        setDocumentContent(eomsOrderDocDto.getId().longValue(),
                           eomsOrderDocDto.getDocumentContent());
        return eomsOrderDocDto;

    }

    public EomsOrderDocDto insertEomsOrderDocNoTran(EomsOrderDocDto eomsOrderDocDto) throws
    SQLException, RequiredException, TooLongException, UOSException {
	    if (eomsOrderDocDto.getId() == null) {
	        Long id = DAOHelper.getPKFromDB("eoms_order_doc", 9);
	        eomsOrderDocDto.setId(id);
	    }
	
	    final String sqlStr =
	        "insert into eoms_order_doc (id,file_name,document_content)         values(?,?,EMPTY_BLOB())";
	    Connection conn = null;
	    PreparedStatement ps = null;
	    int dbloop = 1;
	    try {
	        conn = getConnection();
	        ps = conn.prepareStatement(sqlStr);
	        ps.setLong(dbloop++, eomsOrderDocDto.getId().longValue());
	        ps.setString(dbloop++, eomsOrderDocDto.getFileName());
	        ps.executeUpdate();
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	        cleanUp(conn, null, ps, null);
	    }
	    //插入文件内容
	    setDocumentContentNoTran(eomsOrderDocDto.getId().longValue(),
	                       eomsOrderDocDto.getDocumentContent());
	    return eomsOrderDocDto;
	
	}    
    public void setDocumentContent(long fileId,
                                   byte[] fileBytes) throws SQLException {

        //update data
        String sqlStr =
            "SELECT document_content FROM eoms_order_doc WHERE ID=? for update ";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);//启用全局事务后该语句不需要
            ps = conn.prepareStatement(sqlStr,
                                       ResultSet.TYPE_FORWARD_ONLY,
                                       ResultSet.CONCUR_UPDATABLE);
            ps.setLong(1, fileId);
            rs = ps.executeQuery();

            if (rs.next()) {
                DAOUtils.setBlob(rs, 1, fileBytes);
                conn.commit();
            }
            else {
                throw new SQLException("NOT FOUND BULLITE RECORD");
            }

        }catch (Exception e) {
        	conn.rollback();
        	e.printStackTrace();
        }
        finally {
            
            cleanUp(conn, null, ps, rs);
        }
    }

    public void setDocumentContentNoTran(long fileId,
            byte[] fileBytes) throws SQLException {
	
		//update data
		String sqlStr =
		"SELECT document_content FROM eoms_order_doc WHERE ID=? for update ";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		conn = getConnection();
		ps = conn.prepareStatement(sqlStr,
		                ResultSet.TYPE_FORWARD_ONLY,
		                ResultSet.CONCUR_UPDATABLE);
		ps.setLong(1, fileId);
		rs = ps.executeQuery();
		
		if (rs.next()) {
		DAOUtils.setBlob(rs, 1, fileBytes);
		}
		else {
			throw new SQLException("NOT FOUND BULLITE RECORD");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		
			cleanUp(conn, null, ps, rs);
		}
	} 
    
    public EomsOrderDocDto selectEomsOrderDocById(long eomsOrderDocId) throws
        SQLException {
        EomsOrderDocDto eomsOrderDocDto = null;
        final String sqlStr =
            "select id,file_name,document_content from eoms_order_doc where id=?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlStr);
            ps.setLong(1, eomsOrderDocId);
            rs = ps.executeQuery();
            if (rs.next()) {
                eomsOrderDocDto = new EomsOrderDocDto();
                eomsOrderDocDto.setId(new Long(rs.getLong(1)));
                eomsOrderDocDto.setFileName(rs.getString(2));
                Blob blob = rs.getBlob(3);
                if (blob != null) {
                    eomsOrderDocDto.setDocumentContent(blob.getBytes(1, (int) blob.length()));
                }
                //取出blob字段。便于前台调用
//                  oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(3);
//                  if (blob != null) {
//                      try{
//                          InputStream in = blob.getBinaryStream();
//                          int blobSize=(int)blob.length();
//                          byte[] inbyte = new byte[blobSize];
//                          int bytesRead=0;
//                          while((bytesRead=in.read(inbyte))!=-1)
//                         // in.read(inbyte);
//                          in.close();
//                          eomsOrderDocDto.setDocumentContentStream(in);
//                          eomsOrderDocDto.setDocumentContent(inbyte);
//                      }catch(IOException ie){
//                       ie.printStackTrace();
//                      }
//                  }
            }
        }
        finally {
            cleanUp(connection, null, ps, rs);
        }

        return eomsOrderDocDto;
    }

}
