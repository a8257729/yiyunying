package com.ztesoft.eoms.oaas.post.dao;

import java.sql.SQLException;
import java.util.Map;
import com.ztesoft.eoms.common.dao.BaseDAO;

public interface PostDAO extends BaseDAO {
	public Map findByOrgTmp(int orgTmpId) throws SQLException ;
}
