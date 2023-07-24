package com.ztesoft.eoms.oaas.menu.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.oaas.menu.dao.MenuDAO;

/**
 * 菜单炒作impl类
 * @author FL
 *
 */
public class MenuDAOImpl extends BaseDAOImpl implements MenuDAO {
	
	public Map getSubMenusById(int moduleId)throws Exception {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT P.ID AS id, P.NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.ID FROM PB_MODULE P1 WHERE P1.STATE = '10A' AND P1.PARENT_ID = P.ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.DISPLAY_INDEX AS displayIndex,P.ICON_FILE_NAME AS iconFileName,P.COMMENTS AS comments, p.priv_class_id AS privClassId");
		sqlBuf.append(" FROM PB_MODULE P WHERE P.STATE = '10A'");
		if(moduleId == 0){
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		}else{
			sqlBuf.append(" AND P.PARENT_ID = ").append(moduleId);
		}

		//logger.debug(sqlBuf.toString());

		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("id"));
                result.put("text",rs.getString("name"));
                result.put("pathCode", rs.getString("pathCode"));
                result.put("pathName",rs.getString("pathName"));
                result.put("leaf", rs.getInt("leaf"));
                result.put("parentId", rs.getInt("parentId"));
                result.put("displayIndex",rs.getString("displayIndex"));
                result.put("iconFileName",rs.getString("iconFileName"));
                result.put("comments",rs.getString("comments"));
                result.put("privClassId",rs.getString("privClassId"));
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
	
	/**
	 * 根据模块查询菜单列表
	 */
	public Map getMenuListByModule(int moduleId) throws DataAccessException {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ID AS id, A.NAME AS name, ME.URL_STRING AS urlString, A.ICON_FILE_NAME AS iconFileName, ");
		sqlBuf.append(" A.PRIV_CODE AS privCode, ME.DISPLAY_INDEX AS displayIndex,ME.SHOW_MODEL AS showModel,A.COMMENTS AS comments");
		sqlBuf.append(" FROM PB_ACTION A, PB_MENU ME WHERE A.ID = ME.ID AND A.STATE = '10A'");
		sqlBuf.append(" AND A.MODULE_ID = ");
		sqlBuf.append(moduleId);

		//logger.debug("getMenuListByModule sql"+sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	public int updateMenuModule(int moduleId,int menuId)throws Exception{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("UPDATE PB_ACTION P SET P.MODULE_ID = ? WHERE P.ID = ?");
		
		int flag = 0;

		Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setInt(1, moduleId);
            psmt.setInt(2, menuId);
            
            flag = psmt.executeUpdate();
        }catch(Exception e) {
			//logger.debug(e.getMessage());
		}finally{
            cleanUp(conn, null, psmt, null);
            return flag ;
        }
	}
	
	public int isExitPrivByCode(String privCode) throws Exception {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT COUNT(P.PRIV_CODE) AS C_PRIV_CODE FROM UOS_PRIV P WHERE P.PRIV_CODE = ? ");

		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int count = 0 ;
        
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setString(1, privCode);
            rs = psmt.executeQuery();
            if(rs.next()){
            	count = rs.getInt("C_PRIV_CODE") ;
            }
        }catch(Exception e) {
			//logger.debug(e.getMessage());
		}finally{
            cleanUp(conn, null, psmt, rs);
            return count ;
        }
	}
	
	public int isExitActionByModule(int moduleId,String actionName) throws Exception {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT COUNT(A.ID) AS C_ACTION FROM PB_ACTION A WHERE A.STATE = '10A'");
		sqlBuf.append(" AND A.MODULE_ID = ? AND A.NAME = ? ");

		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int count = 0 ;
        
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setInt(1, moduleId);
            psmt.setString(2, actionName);
            rs = psmt.executeQuery();
            if(rs.next()){
            	count = rs.getInt("C_ACTION") ;
            }
        }catch(Exception e) {
			//logger.debug(e.getMessage());
		}finally{
            cleanUp(conn, null, psmt, rs);
            return count ;
        }
	}
	
	public static void main(String[] args) {
		MenuDAOImpl m = new MenuDAOImpl();
		try {
			//logger.debug(m.isExitActionByModule(207,"待审核文档"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
