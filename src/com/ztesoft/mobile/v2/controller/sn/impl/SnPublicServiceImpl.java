package com.ztesoft.mobile.v2.controller.sn.impl;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.controller.sn.SnPublicService;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class SnPublicServiceImpl extends BaseDAOImpl implements SnPublicService {

    public Map<String,Object> selBySystem(String staffId) throws SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
        String acronym = getAcronym(staffId);
        String sqlStr=(" select CODEA from hnlt_gk.os_public where STYPE = 'APP_REPLAY_TERMINAL' and CODEC like ? ");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,"%"+acronym+"%");
            rs = ps.executeQuery();
            resultMap.put("flag","true");
            if (rs.next()) {

                String codea = rs.getString("CODEA");

                    resultMap.put("flag",codea);


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    private String getAcronym(String staffId) throws SQLException {
       String result = "";
        String sqlStr=(" SELECT ua.acronym as acronym FROM uos_job_staff T\n" +
                "left join uos_job uj on uj.job_id = t.job_id\n" +
                "left join uos_org uo on uo.org_id = uj.org_id\n" +
                "left join uos_area ua on ua.area_id =  uo.area_id\n" +
                "where t.state = '1'\n" +
                "and t.is_normal = '1'\n" +
                "and uj.state = '1'\n" +
                "and uo.state = '1'\n" +
                "and t.staff_id =" +staffId+
                " and rownum = 1 ");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("acronym"));
                result = rs.getString("acronym")==null?"":rs.getString("acronym");

            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return result;
    }

    public Map<String, Object> selBySystem1() throws DataAccessException, SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
        String sqlStr=(" SELECT hnlt_gk.om_order_seq.nextval FROM dual ");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("NEXTVAL"));
                resultMap.put("flag",rs.getString("NEXTVAL"));
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public Map<String,Object> selBySnCode1(String sncode) throws DataAccessException, SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
        String sqlStr=(" select count(*)\n" +
                "        from  hnlt_gk.tif_router_model \n" +
                "        where\n" +
                "        SN=? ");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,sncode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer snc = rs.getInt("count(*)");
                if(snc==0){
                    resultMap.put("flag","false");
                }
                else {
                    resultMap.put("flag","true");
                }
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public Map<String,Object> selBySnCode(String sncode,String orderId,String staffId,String staff_name,String workOrderID) throws DataAccessException, SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
//        String fttr = getFttrOrder(orderId);
//        if("true".equals(fttr))
//        {
//            resultMap.put("flag","true");
//            resultMap.put("desc","");
//            return resultMap;
//        }
        String isWareHouseStaff = getWareHouseStaff(staffId,orderId);
        if("true".equals(isWareHouseStaff))
        {
            //校验终端管理
       Map<String,String>   map =   queryTerminalRouterSn(staffId,workOrderID,sncode,staff_name);

       String out_flag = map.get("out_flag")==null?"0":map.get("out_flag");
       String desc = map.get("desc")==null?"":map.get("desc");

            resultMap.put("flag","0".equals(out_flag)?"false":"true");
            resultMap.put("desc",desc);

        }
        else
        {
            String sqlStr=(" select count(*) as num  \n" +
                    "        from  hnlt_gk.tif_router_model \n" +
                    "        where\n" +
                    "        SN=? ");
            System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = getConnection();
                ps = conn.prepareStatement(sqlStr);
                ps.setString(1,sncode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Integer snc = rs.getInt("num");
                    if(snc==0){
                        resultMap.put("flag","false");
                        resultMap.put("desc","");
                    }
                    else {
                        resultMap.put("flag","true");
                        resultMap.put("desc","");
                    }
                }
            } finally {
                cleanUp(conn, null, ps, rs);
            }
        }

        return resultMap;
    }


    private Map<String,String> queryTerminalRouterSn(String staffId,String orderId,String sn,String staff_name) throws DataAccessException, SQLException {
        String result =  "false";
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call hnlt_gk.F_COMMIT_TERMINAL_ROUTER_SN(?,?,?,?,?,?) }");
            cs.setString(1, orderId);
            cs.setString(2, sn );
            cs.setString(3, staffId);
            cs.setString(4,  staff_name);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.registerOutParameter(6, Types.VARCHAR);

            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();
            String  flag = cs.getString(5);//0：未成功 1：成功
            String  desc = cs.getString(6);
            resultMap.put("desc",desc);
            resultMap.put("out_flag",flag);
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }
    private String getWareHouseStaff(String staffId,String orderId) throws DataAccessException, SQLException {
        String result =  "false";
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            cs = conn.prepareCall("{?= call hnlt_gk.GET_IF_WAREHOUSE_STAFF(?,?) }");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, Integer.parseInt(orderId));
            cs.setInt(3,  Integer.parseInt(staffId));


            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();
            int  num = cs.getInt(1);//1是智家，0是走老逻辑
            if(num==1)
            {
                result="true";
            }

        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return result;
    }


    private String getFttrOrder(String orderId) throws DataAccessException, SQLException {
        String result =  "false";
        String sqlStr=(" SELECT COUNT(1) as num FROM OM_ORDER T WHERE T.ID = ? AND T.SERVICE_ID = '600909' AND hnlt_gk.GET_IF_IPTV( T.ID )= '（家庭组网业务）'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(orderId));
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer snc = rs.getInt("num");
                if(snc==1){
                   return "false";
                }
                else {
                    return "true";
                }
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return result;
    }

    public Map<String, Object> selByWkOrderId(String wk_order_id) throws DataAccessException, SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
        String sqlStr=(" SELECT t.new_rms_state,t.new_rms_rult_id " +
                " FROM hnlt_gk.UOS_CHGHGU T" +
                " where t.chgu_id = ? ");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setLong(1,Long.parseLong(wk_order_id));
            rs = ps.executeQuery();
            if (rs.next()) {
                resultMap.put("rmsState",rs.getString("NEW_RMS_STATE"));
                resultMap.put("rmsRultId",rs.getString("NEW_RMS_RULT_ID"));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cleanUp(conn, null, ps, rs);
        }

        return resultMap;
    }

}
