package com.ztesoft.mobile.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;





/**
 * 权限操作Impl类
 * @author fl
 */
public class PrivDAOImpl extends BaseDAOImpl implements PrivDAO {
	
	/**
     * 查询2个职位不具有的权限，权限范围是全体权限
     */
    public List findPrivsJobNotHold(int jobId, int defaultJobId,int parentId) throws SQLException {
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT PRIV_CODE, PRIV_NAME, A.PRIV_CLASS_ID, B.PARENT_ID, B.PATH_CODE,");
        sb.append(" (CASE WHEN EXISTS (SELECT B1.PRIV_CLASS_ID FROM UOS_PRIV_CLASS B1 WHERE B1.PARENT_ID = B.PRIV_CLASS_ID) THEN 0 ELSE 1 END) AS LEAF");
        sb.append(" FROM UOS_PRIV A, UOS_PRIV_CLASS B");
        sb.append(" WHERE A.PRIV_CLASS_ID = B.PRIV_CLASS_ID ");
        sb.append(" AND NOT EXISTS (SELECT * FROM VW_JOB_PRIV ");
        sb.append(" WHERE (JOB_ID =? OR JOB_ID =?) AND PRIV_CODE=A.PRIV_CODE) ");
        sb.append(" AND B.PARENT_ID = ?");
        sb.append(" AND A.STATE='10A' ORDER BY PRIV_NAME");
        
        System.out.println("findPrivsJobNotHold sb:"+sb.toString());

    	System.out.println("jobId:"+jobId);
    	System.out.println("defaultJobId:"+defaultJobId);
    	System.out.println("parentId:"+parentId);

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            psmt.setInt(1, jobId);
            psmt.setInt(2, defaultJobId);
            psmt.setInt(3, parentId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	
            	Map result = new HashMap();
                result.put("id", rs.getString("PRIV_CODE"));
                result.put("text",rs.getString("PRIV_NAME"));
                result.put("leaf", rs.getInt("LEAF"));	
                result.put("checked",false);
                result.put("pathCode", rs.getString("PATH_CODE"));
                result.put("PRIV_CLASS_ID", rs.getString("PRIV_CLASS_ID"));
                resultList.add(result);
                System.out.println("******************"+rs.getString("PRIV_CODE"));
            }
            return resultList;
            
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 返回某职位拥有，而另外2个职位没有的权限
     * @param jobId1 int 职位编号1
     * @param jobIds int 职位编号2
     * @param defaultJobId int 职位编号3
     */
    public List findPrivsJobNotHoldnew(int jobId1, int jobId2, int defaultJobId,int parentId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT PRIV_CODE,CAN_GRANT,PRIV_NAME,PRIV_CLASS_ID,LEAF,PATH_CODE FROM VW_JOB_PRIV ");
        sb.append(" WHERE JOB_ID=? ");
        sb.append(" AND CAN_GRANT='1' ");
        sb.append(" AND PRIV_CODE NOT IN (SELECT PRIV_CODE ");
        sb.append(" FROM VW_JOB_PRIV WHERE JOB_ID=? OR JOB_ID=? ) AND PARENT_ID = ? ORDER BY PRIV_NAME ");

        String sql = sb.toString();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, jobId1);
            psmt.setInt(2, jobId2);
            psmt.setInt(3, defaultJobId);
            psmt.setInt(4, parentId);

            rs = psmt.executeQuery();
            while (rs.next()) {
            	boolean checked ;
            	if(1 == rs.getInt("CAN_GRANT")){
            		checked = true ;
            	}else {
            		checked = false ;
            	}
            	Map result = new HashMap();
                result.put("id", rs.getString("PRIV_CODE"));
                result.put("text",rs.getString("PRIV_NAME"));
                result.put("leaf", rs.getInt("LEAF"));
                result.put("checked",checked);
                result.put("pathCode", "PATH_CODE");
                resultList.add(result);
            }
            
            return resultList;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    
    /**
     * 查询某两个职位具有而2个职位不具有的权限
     * @param jobId1 int 职位1
     * @param jobId2 int 职位2
     * @param jobId3 int 职位3
     * @param isAll boolean 是全部权限还是能授出的权限
     */
    public List findPrivsJobNotHold(int jobId1, int jobId2, int jobId3, int defaultJobId,int parentId) throws SQLException {

    	StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT PRIV_CODE,CAN_GRANT,PRIV_NAME,PRIV_CLASS_ID,LEAF,PATH_CODE ");
        sb.append(" FROM VW_JOB_PRIV WHERE (JOB_ID=? OR JOB_ID=?) ");
        sb.append(" AND CAN_GRANT='1' ");
        sb.append(" AND PRIV_CODE NOT IN ( SELECT PRIV_CODE FROM VW_JOB_PRIV ");
        sb.append(" WHERE JOB_ID=? OR JOB_ID=? ) AND PARENT_ID = ? ORDER BY PRIV_NAME ");

        String sql = sb.toString();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, jobId1);
            psmt.setInt(2, jobId2);
            psmt.setInt(3, jobId3);
            psmt.setInt(4, defaultJobId);
            psmt.setInt(5, parentId);

            rs = psmt.executeQuery();
            while (rs.next()) {
            	boolean checked ;
            	if(1 == rs.getInt("CAN_GRANT")){
            		checked = true ;
            	}else {
            		checked = false ;
            	}
            	Map result = new HashMap();
                result.put("id", rs.getString("PRIV_CODE"));
                result.put("text",rs.getString("PRIV_NAME"));
                result.put("leaf", rs.getInt("LEAF"));
                result.put("checked",checked);
                result.put("pathCode", "PATH_CODE");
                resultList.add(result);
            }
            return resultList;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    
    /**
     * 查询一个职位的所有权限
     * @param jobId int 职位编号
     */
    public Map findPrivsByJob(int jobId,int parentId) throws SQLException {

    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT A.PRIV_CODE, B.PRIV_NAME, A.CAN_GRANT, B.PRIV_CLASS_ID, A.JOB_ID,C.PATH_CODE,");
    	sb.append(" (CASE WHEN EXISTS (SELECT B1.PRIV_CLASS_ID FROM UOS_PRIV_CLASS B1 WHERE B1.PARENT_ID = B.PRIV_CLASS_ID) THEN 0 ELSE 1 END) AS LEAF");
    	sb.append(" FROM UOS_JOB_PRIV A, UOS_PRIV B,UOS_PRIV_CLASS C");
    	sb.append(" WHERE A.PRIV_CODE = B.PRIV_CODE");
    	sb.append(" AND C.PRIV_CLASS_ID = B.PRIV_CLASS_ID");
    	sb.append(" AND A.JOB_ID = ?");
    	sb.append(" AND C.PARENT_ID = ?");
    	sb.append(" AND B.STATE = '10A' ORDER BY B.PRIV_NAME");
    	
    	System.out.println("findPrivsByJob sb:"+sb.toString());
    	System.out.println("jobId:"+jobId);
    	System.out.println("parentId:"+parentId);
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultList = new HashMap();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            psmt.setInt(1, jobId);
            psmt.setInt(2, parentId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	boolean checked ;
            	if(1 == rs.getInt("CAN_GRANT")){
            		checked = true ;
            	}else {
            		checked = false ;
            	}
            	Map result = new HashMap();
                result.put("id", rs.getString("PRIV_CODE"));
                result.put("text",rs.getString("PRIV_NAME"));
                result.put("leaf", rs.getInt("LEAF"));
                result.put("checked",checked);
                result.put("pathCode", "PATH_CODE");
                resultList.put(rs.getString("PRIV_CODE"),result);
            }
            return resultList ;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 查询顶级的权限类别
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public Map findTopPrivClassesMap() throws SQLException {
        String sql = "SELECT PRIV_CLASS_ID,PRIV_CLASS_NAME,PARENT_ID,PATH_CODE,COMMENTS FROM UOS_PRIV_CLASS C WHERE C.PARENT_ID IS NULL";
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map resultList = new HashMap();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id",rs.getInt("PRIV_CLASS_ID"));
                result.put("PARENT_ID",rs.getInt("PARENT_ID"));
                result.put("text",rs.getString("PRIV_CLASS_NAME"));
                result.put("leaf",false);
                result.put("checked",false);
                result.put("pathCode", rs.getString("PATH_CODE"));
                resultList.put(rs.getInt("PRIV_CLASS_ID"),result);
            }
            return resultList;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 查询下属的权限类别
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public List findSubPrivClassesMap(int parentId) throws SQLException {
        String sql = "SELECT PRIV_CLASS_ID,PRIV_CLASS_NAME,PARENT_ID,PATH_CODE,COMMENTS FROM UOS_PRIV_CLASS C WHERE C.PARENT_ID = ?";
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, parentId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id",rs.getInt("PRIV_CLASS_ID"));
                result.put("text",rs.getString("PRIV_CLASS_NAME"));
                result.put("leaf",false);
                result.put("checked",false);
                result.put("pathCode", rs.getString("PATH_CODE"));
                resultList.add(result);
            }
            return resultList;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 查询所有的权限类别
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public List findPrivClassesList(int parentId) throws SQLException {
    	StringBuffer sb = new StringBuffer();
        sb.append("SELECT A.PRIV_CLASS_ID AS privClassId,");
        sb.append(" A.PRIV_CLASS_NAME AS privClassName,");
        sb.append(" A.PARENT_ID AS parentId,");
        sb.append(" A.PATH_CODE AS pathCode,");
        sb.append(" A.COMMENTS AS comments,");
        sb.append(" (CASE WHEN EXISTS (SELECT B1.PRIV_CLASS_ID FROM UOS_PRIV_CLASS B1 WHERE B1.PARENT_ID = A.PRIV_CLASS_ID) THEN 0 ELSE 1 END) AS leaf,");
        sb.append(" B.PRIV_CLASS_ID source");
        sb.append(" FROM UOS_PRIV_CLASS A");
        sb.append(" LEFT JOIN PB_MODULE B ON A.PRIV_CLASS_ID = B.PRIV_CLASS_ID");
        if(parentId == 0){
        	sb.append(" WHERE (A.PARENT_ID IS NULL OR A.PARENT_ID = 0)");
        }else{
        	sb.append(" WHERE A.PARENT_ID = ");
        	sb.append(parentId);
        }
        sb.append(" ORDER BY A.PATH_CODE ");
        
        System.out.println("findPrivClassesList sql:"+sb.toString());
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("id",rs.getInt("privClassId"));
                result.put("privClassId",rs.getInt("privClassId"));
                result.put("privClassName",rs.getString("privClassName"));
                result.put("parentId",rs.getInt("parentId"));
                result.put("pathCode", rs.getString("pathCode"));
                result.put("comments", rs.getString("comments"));
                result.put("leaf",rs.getInt("leaf"));
                
                if(rs.getInt("source") == 0){
                	result.put("source","权限管理");
                	result.put("sourceCode","privMng");
                }else{
                	result.put("source","模块");
                	result.put("sourceCode","model");
                }
                resultList.add(result);
            }
            System.out.println("findPrivClassesList resultList"+resultList);
            return resultList;
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null ; 
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 根据权限类别查询权限
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public List findPrivByClass(int privClassId) throws SQLException {
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT A.PRIV_CODE AS privCode,A.PRIV_NAME AS privName,A.COMMENTS AS comments,B.PRIV_CODE AS actionPriv,A.STATE AS state");
    	sb.append(" FROM UOS_PRIV A LEFT JOIN PB_ACTION B ON B.PRIV_CODE = A.PRIV_CODE");
    	sb.append(" WHERE A.PRIV_CLASS_ID = ");
    	sb.append(privClassId);
    	sb.append(" ORDER BY A.PRIV_NAME");
    	
    	System.out.println("findPrivByClass sql:"+sb.toString());
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("privCode",rs.getString("privCode"));
                result.put("privName",rs.getString("privName"));
                result.put("comments",rs.getString("comments"));
                
                if(rs.getString("actionPriv") != null && "".equals(rs.getString("actionPriv"))){
                	result.put("actionPriv","功能");
                }else{
                	result.put("actionPriv","权限管理");
                }
                
                result.put("state", rs.getString("state"));
                
                resultList.add(result);
            }
            System.out.println("findPrivByClass resultList"+resultList);
            return resultList;
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null ; 
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    /**
     * 查询特有的权限类别（包括权限类别）
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public List findJobPrivTreeList(int parentId,int jobId) throws SQLException {
    	StringBuffer sb = new StringBuffer();
        sb.append("SELECT to_char(PRIV_CLASS_ID) AS id,PRIV_CLASS_NAME AS name,'2' AS canGrant,0 AS leaf FROM UOS_PRIV_CLASS C");
        sb.append(" WHERE C.PRIV_CLASS_ID IN ");
        sb.append(" (SELECT B.PRIV_CLASS_ID FROM UOS_JOB_PRIV A, UOS_PRIV B WHERE A.JOB_ID = ");
        sb.append(jobId);
        sb.append(" AND A.PRIV_CODE = B.PRIV_CODE AND B.STATE = '10A')");
        if(parentId == 0){
        	sb.append(" AND (C.PARENT_ID = 0 OR C.PARENT_ID IS NULL)");
        }else{
        	sb.append(" AND C.PARENT_ID = ").append(parentId);
        }
        sb.append(" UNION");
        sb.append(" SELECT A.PRIV_CODE AS id, B.PRIV_NAME AS name, A.CAN_GRANT AS canGrant,1 AS leaf");
        sb.append(" FROM UOS_JOB_PRIV A, UOS_PRIV B");
        sb.append(" WHERE A.PRIV_CODE = B.PRIV_CODE AND B.STATE = '10A'");
        sb.append(" AND B.PRIV_CLASS_ID = ");
        sb.append(parentId);
        sb.append(" AND A.JOB_ID = ");
        sb.append(jobId);
        
        System.out.println("findPrivClassesList sql:"+sb.toString());
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("id",rs.getString("id"));
                result.put("name",rs.getString("name"));
                
                if("1".equals(rs.getString("canGrant"))){
                	result.put("canGrant","是");
                }else if("".equals(rs.getString("canGrant")) || rs.getString("canGrant") == null){
                	result.put("canGrant","否");
                }
                
                result.put("leaf",rs.getInt("leaf"));
                
                resultList.add(result);
            }
            System.out.println("findPrivClassesList resultList"+resultList);
            return resultList;
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null ; 
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    } 
    
    
    /**
     * 查询所有的权限类别（包括权限类别）
     * @throws SQLException
     * @return Map 类别编号和类别对象的值对
     */
    public List findAllJobPrivTreeList(int parentId,int jobId) throws SQLException {
    	StringBuffer sb = new StringBuffer();
        sb.append("SELECT TO_CHAR(C.PRIV_CLASS_ID) AS privCode, C.PRIV_CLASS_NAME AS privName, '2' AS canGrant, 0 AS leaf FROM UOS_PRIV_CLASS C");
        sb.append(" WHERE C.PRIV_CLASS_ID IN (SELECT V.PRIV_CLASS_ID FROM VW_JOB_PRIV V WHERE JOB_ID = ");
        sb.append(jobId).append(") ");
        if(parentId == 0){
        	sb.append(" AND (C.PARENT_ID = 0 OR C.PARENT_ID IS NULL)");
        }else{
        	sb.append(" AND C.PARENT_ID = ").append(parentId);
        }
        sb.append(" UNION");
        sb.append(" SELECT V.PRIV_CODE AS privCode, V.PRIV_NAME AS privName, V.CAN_GRANT AS canGrant, 1 AS leaf FROM VW_JOB_PRIV V");
        sb.append(" WHERE V.PRIV_CLASS_ID = ");
        sb.append(parentId);
        sb.append(" AND  JOB_ID = ");
        sb.append(jobId);
        
        System.out.println("findAllJobPrivTreeList sql:"+sb.toString());
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sb.toString());
            
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("id",rs.getString("privCode"));
                result.put("name",rs.getString("privName"));
                if("1".equals(rs.getString("canGrant"))){
                	result.put("canGrant","是");
                }else if("".equals(rs.getString("canGrant")) || rs.getString("canGrant") == null){
                	result.put("canGrant","否");
                }
                result.put("leaf",rs.getInt("leaf"));
                
                resultList.add(result);
            }
            System.out.println("findAllJobPrivTreeList resultList"+resultList);
            return resultList;
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null ; 
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    } 
}
