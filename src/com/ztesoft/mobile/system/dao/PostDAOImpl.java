package com.ztesoft.mobile.system.dao;

import java.sql.SQLException;
import java.util.Map;
import com.zterc.uos.oaas.exception.OAASException;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;


/**
 * ְλģ�����Impl��
 * @author fl
 */
public class PostDAOImpl extends BaseDAOImpl implements PostDAO {
	
	/**
     * ��ѯĳ��֯ģ�������ְλģ��
     * @param orgTmpId int ��֯ģ����
     * @throws OAASException
     * @return Position[] ְλģ���������
     */
    public Map findByOrgTmp(int orgTmpId) throws SQLException {
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("SELECT A.POST_ID AS postId,");
        sqlBuf.append(" A.ORG_TMP_ID AS orgTmpId,");
        sqlBuf.append(" A.POST_LEVEL_ID AS postLevelId,");
        sqlBuf.append(" B.POST_LEVEL_NAME AS postLevelName,");
        sqlBuf.append(" A.POST_NAME AS postName,");
        sqlBuf.append(" A.MAX_STAFFS AS maxStaffs,");
        sqlBuf.append(" A.COMMENTS AS comments");
        sqlBuf.append(" FROM UOS_POST A, UOS_POST_LEVEL B");
        sqlBuf.append(" WHERE A.POST_LEVEL_ID = B.POST_LEVEL_ID");
        sqlBuf.append(" AND A.STATE = '1'");
        sqlBuf.append(" AND A.ORG_TMP_ID = ");
        sqlBuf.append(orgTmpId);
        
        return (Map) populateQueryByMap(sqlBuf, 0, 0);
    }
}
