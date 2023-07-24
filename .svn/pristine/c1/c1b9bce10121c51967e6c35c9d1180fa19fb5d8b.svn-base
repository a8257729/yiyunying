package com.ztesoft.eoms.web.dao.attachment;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import com.zterc.uos.UOSException;
import com.ztesoft.eoms.common.dao.BaseDAO;

public interface AttachmentDAO extends BaseDAO{
	public String insertFileInfo(List fileInfoList) throws SQLException,UOSException;
	public String insertFileInfo(Map fileInfo) throws SQLException,UOSException;
	public Map insertFileInfoNoTran(Map fileInfo) throws SQLException,UOSException;
	public int updateDocSize(Map fileInfo) throws SQLException,UOSException;
	public Map selByDocKey(String docKey)throws SQLException;
        public Map selByDocId(Long docId)throws SQLException;
}
