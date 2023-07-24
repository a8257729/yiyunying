package com.ztesoft.eoms.web.dao.attachment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.ztesoft.eoms.common.db.DbOper;
import java.sql.PreparedStatement;
import com.ztesoft.eoms.common.helper.DAOHelper;
import java.sql.Connection;
import java.util.Date;
import com.zterc.uos.UOSException;
import java.util.ArrayList;
import java.sql.ResultSet;

import org.apache.commons.collections.MapUtils;

public class AttachmentDAOImpl
    extends AbstractDAOImpl implements AttachmentDAO {

  public String insertFileInfo(List fileInfoList) throws SQLException,
      UOSException {
    String docIds = "";
    if (fileInfoList.isEmpty()) {
      return null;
    }
    final String sql =
        "INSERT INTO DOC(DOC_ID,DOC_NAME,DOC_CONTENT_TYPE,DOC_SIZE,DOC_POINT,DOC_KEY,DOC_PATH_KEY,CREATE_DATE) VALUES(?,?,?,?,?,?,NULL,?)";
    Connection conn = null;
    PreparedStatement ps = null;
    try {

      conn = getConnection();
      conn.setAutoCommit(false);
      ps = conn.prepareStatement(sql);
      int dbloop = 0;
      Long docId = null;
      Map fileMap = null;

      for (Iterator iter = fileInfoList.iterator(); iter.hasNext(); ) {
        fileMap = (Map) iter.next();
        docId = DAOHelper.getPKFromMem("DOC", 9);
        docIds += docId + ",";

        dbloop = 1;
        DbOper.setPrepStatementParam(dbloop++, ps, docId);
        DbOper.setPrepStatementParam(dbloop++, ps,
                                     (String) fileMap.get("fileName"));
        DbOper.setPrepStatementParam(dbloop++, ps,
                                     (String) fileMap.get("contentType"));
        DbOper.setPrepStatementParam(dbloop++, ps,
                                     (Long) fileMap.get("size"));
        DbOper.setPrepStatementParam(dbloop++, ps,
                                     (String) fileMap.get(
                                         "tempFileName"));
        DbOper.setPrepStatementParam(dbloop++, ps,
                                     (String) fileMap.get("fileKey"));
        DbOper.setPrepStatementParam(dbloop++, ps, new Date());
        ps.addBatch();
      }
      ps.executeBatch();
      conn.commit();
    }catch (Exception e) {
    	conn.rollback();
    	e.printStackTrace();
    }
    finally {
      cleanUp(conn, null, ps, null);
    }
    if (docIds != null && docIds != "") {
      docIds = docIds.substring(0, docIds.length() - 1);
    }
    return docIds;
  }
  
  public int updateDocSize(Map fileInfo) throws SQLException,UOSException{
      String sqlStr =
    	  "UPDATE DOC SET DOC_SIZE=? WHERE DOC_ID=?";
    Connection conn = null;
    PreparedStatement ps = null;
    int i = -1 ;
    int dbloop = 1;
    try {
        conn = getConnection();
        conn.setAutoCommit(false);
        ps = conn.prepareStatement(sqlStr);

        DbOper.setPrepStatementParam(dbloop++, ps,MapUtils.getInteger(fileInfo,"docSize"));
        DbOper.setPrepStatementParam(dbloop++, ps, MapUtils.getLong(fileInfo,"docId"));
        i = ps.executeUpdate();
        conn.commit();
    }
    finally {
    	conn.rollback();
        cleanUp(conn, null, ps, null);
    }
    
    return i;
  }

  public String insertFileInfo(Map fileInfo) throws SQLException, UOSException {
    List fileInfoList = new ArrayList();
    fileInfoList.add(fileInfo);
    return insertFileInfo(fileInfoList);
  }
  public Map insertFileInfoNoTran(Map fileInfo) throws SQLException,UOSException{
	   final String sql =
	        "INSERT INTO DOC(DOC_ID,DOC_NAME,DOC_CONTENT_TYPE,DOC_SIZE,DOC_POINT,DOC_KEY,DOC_PATH_KEY,CREATE_DATE) VALUES(?,?,?,?,?,?,NULL,?)";
	    Connection conn = null;
	    PreparedStatement ps = null;
	    try {

		conn = getConnection();
		ps = conn.prepareStatement(sql);
		int dbloop = 0;
		Long docId = null;
		docId = DAOHelper.getPKFromMem("DOC", 9);
		fileInfo.put("docId", docId);
		dbloop = 1;
		DbOper.setPrepStatementParam(dbloop++, ps, docId);

		DbOper.setPrepStatementParam(dbloop++, ps,
		                             (String) fileInfo.get("fileName"));

		DbOper.setPrepStatementParam(dbloop++, ps,
		                             (String) fileInfo.get("contentType"));

		DbOper.setPrepStatementParam(dbloop++, ps,
		                             (Long) fileInfo.get("size"));

		DbOper.setPrepStatementParam(dbloop++, ps,
		                             (String) fileInfo.get(
		                                 "tempFileName"));

		DbOper.setPrepStatementParam(dbloop++, ps,
		                             (String) fileInfo.get("fileKey"));

		DbOper.setPrepStatementParam(dbloop++, ps, new Date());   
		
		ps.execute();
		
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
	    finally {
	      cleanUp(conn, null, ps, null);
	    }	      
	  return fileInfo;
  }
  
  public Map selByDocKey(String docKey) throws SQLException {

    final String sqlStr = "SELECT DOC_ID,DOC_NAME,DOC_CONTENT_TYPE,DOC_SIZE,DOC_POINT,DOC_PATH_KEY,CREATE_DATE" +
        " FROM DOC WHERE DOC_KEY=?";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Map inParam = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sqlStr);
      DbOper.setPrepStatementParam(1, ps, docKey);
      rs = ps.executeQuery();
      if (rs.next()) {
        inParam = new HashMap();
        inParam.put("key", docKey);

        inParam.put("docId", rs.getObject("DOC_ID"));
        inParam.put("fileName", rs.getString("DOC_NAME"));
        inParam.put("contentType", rs.getString("DOC_CONTENT_TYPE"));

        inParam.put("size", rs.getObject("DOC_SIZE"));
        inParam.put("point", rs.getString("DOC_POINT"));
        inParam.put("pathKey", rs.getString("DOC_PATH_KEY"));
        inParam.put("createDate", rs.getDate("CREATE_DATE"));

      }
    }
    finally {
      cleanUp(conn, null, ps, rs);
    }
    return inParam;
  }

  public Map selByDocId(Long docId) throws SQLException {
    final String sqlStr = "SELECT DOC_ID,DOC_NAME,DOC_CONTENT_TYPE,DOC_SIZE,DOC_POINT,DOC_PATH_KEY,CREATE_DATE" +
        " FROM DOC WHERE DOC_ID=?";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Map inParam = null;

    try {
      conn = getConnection();
      ps = conn.prepareStatement(sqlStr);
      DbOper.setPrepStatementParam(1, ps, docId);
      rs = ps.executeQuery();
      if (rs.next()) {
        inParam = new HashMap();
        inParam.put("key", docId);

        inParam.put("docId", rs.getObject("DOC_ID"));
        inParam.put("fileName", rs.getString("DOC_NAME"));
        inParam.put("contentType", rs.getString("DOC_CONTENT_TYPE"));

        inParam.put("size", rs.getObject("DOC_SIZE"));
        inParam.put("point", rs.getString("DOC_POINT"));
        inParam.put("pathKey", rs.getString("DOC_PATH_KEY"));
        inParam.put("createDate", rs.getDate("CREATE_DATE"));

      }
    }
    finally {
      cleanUp(conn, null, ps, rs);
    }
    return inParam;

  }
}
