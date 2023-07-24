package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;
import oracle.jdbc.internal.OracleTypes;
import net.sf.json.JSONObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileDataInstallDAOImpl extends BaseDAOImpl implements MobileDataInstallDAO {

    public Map<String,String> queryDataInstallByOrg(String org_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("SELECT count(1) as flag FROM uos_scan2aaabind_org@dblink_to_iom T where t.org_id ='"+org_id+ "'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {

                resultMap.put("flag",String.valueOf(rs.getInt("flag")));
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public Map<String, String> querySelOrgByAccount(String account) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("select a.eqp_name as EQP_NAME, a.bss_pro_name as BSS_PRO_NAME\n" +
                "  from srv_business s, srv_instance t, asn_link_route r, rme_eqp a\n" +
                " where s.region_code = decode(substr('"+ account +"', 1, 6),\n" +
                "                              '073105',\n" +
                "                              '0732',\n" +
                "                              '073102',\n" +
                "                              '0733',\n" +
                "                              substr('"+ account +"', 1, 4))\n" +
                "   and s.delete_state = '0'\n" +
                "   and s.lan_id is null\n" +
                "   and (s.tele_no = '"+ account +"' or s.tele_no = substr('"+ account +"', 5))\n" +
                "   and t.dis_seq = s.dis_seq\n" +
                "   and t.delete_state = '0'\n" +
                "   and t.srv_id <> '1001'\n" +
                "   and t.opr_state_id <> '170001'\n" +
                "   and r.delete_state = '0'\n" +
                "   and t.route_id = r.link_id\n" +
                "   and r.parent_res_id = a.eqp_id\n" +
                "   and a.delete_state = '0'\n" +
                "   and a.res_type_id = '2530'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {
                resultMap.put("flag1",rs.getString(1));
                resultMap.put("flag2",rs.getString(2));
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }


    public Map<String, String> queryPixianCode(String wk_order_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("SELECT aa.digcode as digCode \n" +
                "FROM \n" +
                "(Select p.digcode as digCode ,t.create_date\n" +
                " From Uos_Datamake t left join pub_two_dimension_code p on t.res_port_id = p.res_id where t.wk_order_id = "+wk_order_id+"and t.oper_type =2 and p.res_type_id = '6024' \n" +
                " union\n" +
                "Select p.digcode as digCode ,t.create_date\n" +
                " From UOS_SCAN2AAABAND t left join pub_two_dimension_code p on t.new_obd_port_id = p.res_id where t.wk_order_id = "+wk_order_id+"  and p.res_type_id = '6024' \n" +
                "order by create_date desc) aa");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {
                resultMap.put("digCode",rs.getString(1));

            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public Map<String,String> queryBSSProject(String work_order_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String projectName ="";
        String flag ="";
        String msg ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call PKG_SCAN2AAABAND.sab_QueryOrderInfo(?,?,?,?,?) }");
            cs.setString(1, work_order_id);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs.registerOutParameter(4, java.sql.Types.VARCHAR);
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();
            projectName = cs.getString(3);
            flag = cs.getString(4);
            msg = cs.getString(5);
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        resultMap.put("project_name",projectName);
        resultMap.put("ret_code",flag);
        resultMap.put("ret_msg",msg);
        return resultMap;
    }

    public Map<String, String> queryPowerModel1(String order_id, String hgu_sn, String staffId, String staffName) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String flag ="";
        String msg ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call hnlt_gk.f_commit_terminal_transfer(?,?,?,?,?,?) }");
            cs.setString(1, order_id);
            cs.setString(2, hgu_sn);
            cs.setString(3, staffId);
            cs.setString(4, staffName);
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);

            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();

            flag = cs.getString(5);
            msg = cs.getString(6);
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        resultMap.put("ret_code",flag);
        resultMap.put("ret_msg",msg);
        return resultMap;
    }

    public Map<String,String> queryPowerModel(String base_order_id, String hgu_sn, String staffId, String staffName,String res) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String projectName ="";
        String flag ="";
        String msg ="";
        try {
            conn = getConnection();
            if("1".equals(res)){
                cs = conn.prepareCall("{ call F_COMMIT_TERMINAL_HGU_OWN(?,?,?,?,?,?) }");
            }else {
                cs = conn.prepareCall("{ call f_commit_terminal_hgu_sn(?,?,?,?,?,?) }");
            }

            cs.setString(1, base_order_id);
            cs.setString(2, hgu_sn);
            cs.setString(3, staffId);
            cs.setString(4, staffName);
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);

            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();

            flag = cs.getString(5);
            msg = cs.getString(6);
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        resultMap.put("ret_code",flag);
        resultMap.put("ret_msg",msg);
        return resultMap;
    }

    /**
     * 数据下发
     * @param paramMap
     * @return
     * @throws
     */
    public Map<Object,Object> processDataInstall( Map<String,Object> paramMap) throws SQLException {
        Map<Object, Object> resultMap =  new HashMap<Object,Object>();
        String hgu_sn = paramMap.get("hgu_sn").toString();
        String stb_mac = paramMap.get("stb_mac").toString();
        String eqp_id = paramMap.get("eqp_id").toString();
        String port_id = paramMap.get("port_id").toString();
        String wk_order_id = paramMap.get("wk_order_id").toString();
        String staff_id = paramMap.get("staff_id").toString();
        String staff_name = paramMap.get("staff_name").toString();
        String inf_type = paramMap.get("inf_type").toString();
        String hgu_pon = paramMap.get("hgu_pon")==null?"":paramMap.get("hgu_pon").toString();
        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        String flag ="";
        String msg ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call hnlt_gk.PKG_SCAN2AAABAND.F_SCAN2AAABAND(?,?,?,?,?,?,?,?,?,?,?) }");
            cs.setString(1, wk_order_id);
            cs.setString(2, hgu_sn);
            cs.setString(3, stb_mac);
            cs.setString(4, eqp_id);
            cs.setString(5, port_id);
            cs.setString(6, staff_id);
            cs.setString(7, staff_name);
            cs.setString(8, inf_type);
            cs.setString(9, hgu_pon);
            cs.registerOutParameter(10, java.sql.Types.VARCHAR);
            cs.registerOutParameter(11, java.sql.Types.VARCHAR);

            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();

            flag = cs.getString(10);
            msg = cs.getString(11);
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        resultMap.put("ret_code",flag);
        resultMap.put("ret_msg",msg);
        return resultMap;
    }

    public Map<Object, Object> processDataInstall1(Map<String, Object> paramMap) throws SQLException {
        Map<Object, Object> resultMap =  new HashMap<Object,Object>();
        String wk_order_id = paramMap.get("wk_order_id").toString();
        String hgu_sn = paramMap.get("hgu_sn").toString();
        String staff_id = paramMap.get("staff_id").toString();
        String loid = paramMap.get("loid").toString();
        String new_pwd = paramMap.get("new_pwd").toString();
        String aaa_portid = paramMap.get("aaa_portid").toString();
        String aaa_olt_ip = paramMap.get("aaa_olt_ip").toString();
        String aaa_pon_no = paramMap.get("aaa_pon_no").toString();
        String aaa_hgu_sn = paramMap.get("aaa_hgu_sn").toString();
        String aaa_bindtype = paramMap.get("aaa_bindtype").toString();
        String band3a_rult_id = paramMap.get("band3a_rult_id").toString();
        String username = paramMap.get("username").toString();

        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        String flag ="";
        String desc ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call hnlt_gk.F_UOS_CHGHGU_TRANSFER(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
            cs.setString(1, wk_order_id);
            cs.setString(2, hgu_sn);
            cs.setString(3, staff_id);
            cs.setString(4, loid);
            cs.setString(5, new_pwd);
            cs.setString(6, aaa_portid);
            cs.setString(7, aaa_olt_ip);
            cs.setString(8, aaa_pon_no);
            cs.setString(9, aaa_hgu_sn);
            cs.setString(10, aaa_bindtype);
            cs.setString(11, band3a_rult_id);
            cs.setString(12, username);
            cs.registerOutParameter(13, java.sql.Types.VARCHAR);
            cs.registerOutParameter(14, java.sql.Types.VARCHAR);

            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();

            flag = cs.getString(13);
            desc = cs.getString(14);
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        resultMap.put("ret_code",flag);
        resultMap.put("ret_desc",desc);
        return resultMap;
    }

//    public Map<String,String> queryOBDInfo(String qrCode) throws SQLException {
//        Map<String, String> resultMap =  new HashMap<String,String>();
//        String sqlStr=("");
//        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
//        Connection conn = null;
//        PreparedStatement ps = null;
//        CallableStatement cs = null;
//        ResultSet rs = null;
//
//        try {
//            conn = getConnection();
//            cs = conn.prepareCall("{ call sab_QueryOBDInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
//            cs.setString(1, qrCode);
//            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(4, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
//
//            cs.registerOutParameter(6, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(7, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(8, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(9, java.sql.Types.VARCHAR);
//
//            cs.registerOutParameter(10, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(11, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(12, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(13, java.sql.Types.VARCHAR);
//            cs.registerOutParameter(14, java.sql.Types.VARCHAR);
//            logger.info("----准备执行P_CREATE_ORDER-----");
//            cs.execute();
//            resultMap.put("obd_id",cs.getString(2)==null?"":cs.getString(2));
//            resultMap.put("obd_name",cs.getString(3)==null?"":cs.getString(3));
//            resultMap.put("obd_remark",cs.getString(4)==null?"":cs.getString(4));
//            resultMap.put("obd_prjName",cs.getString(5)==null?"":cs.getString(5));
//            resultMap.put("obd_proNo",cs.getString(6)==null?"":cs.getString(6));
//
//            resultMap.put("dire_id",cs.getString(7)==null?"":cs.getString(7));
//            resultMap.put("dire_name",cs.getString(8)==null?"":cs.getString(8));
//            resultMap.put("olt_id",cs.getString(9)==null?"":cs.getString(9));
//
//            resultMap.put("olt_ip",cs.getString(10)==null?"":cs.getString(10));
//            resultMap.put("pon_id",cs.getString(11)==null?"":cs.getString(11));
//            resultMap.put("pon_code",cs.getString(12)==null?"":cs.getString(12));
//
//        } finally {
//            cleanUp(conn, null, ps, rs);
//        }
//
//
//        return resultMap;
//    }

    public Map<Object, Object> queryOBDInfo(String qrCode, String wkOrderId) {
        // qrCode = "6901285991219";
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = getConnection();

            logger.info("<<<qrCode>>>:" + qrCode);
            logger.info("<<<wkOrderId>>>:" + wkOrderId);
            // if (StringUtil.isNull(qrCode)) {
            String resultQrCode = "";

            StringBuffer queryQrCodeSql = new StringBuffer();
            queryQrCodeSql.append(" Select t.HGU_PON as hgu_pon,t.HGU_HG as hgu_hg,t.HGU_SN as hgu_sn,t.NEW_OBD_CODE as qrCode,t.NEW_OBD_PORT_ID as portId,p.digcode as digCode \n");
            queryQrCodeSql.append(" From UOS_SCAN2AAABAND@DBLINK_TO_IOM t left join pub_two_dimension_code p on t.NEW_OBD_PORT_ID = p.res_id where t.wk_order_id = '" + wkOrderId + "'\n");
            //  queryQrCodeSql.append(" order by t.create_date desc");
            Map<String, Object> map = null;
            try {
                map = dynamicQueryObjectByMap(queryQrCodeSql.toString(), null, null);
            } catch (DataAccessException e) {
                logger.error("wkOrderId:" + wkOrderId + "UOS_SCAN2AAABAND " + e.getMessage());
            }
            String portId = "";
            String digCode = "";
            String hgu_sn = "";
            String hgu_hg="";
            String hgu_pon="";
            if (map != null && map.size() > 0) {
                resultQrCode = (String) map.get("qrCode");
                portId = (String) map.get("portId");
                digCode = (String) map.get("digCode");
                hgu_sn = (String) map.get("hgu_sn");

                hgu_hg = (String) map.get("hgu_hg");
                hgu_pon = (String) map.get("hgu_pon");

            }
            // }

            logger.info("<<<resultQrCode>>>:" + resultQrCode);

            callStmt = conn.prepareCall("{ call PKG_SCAN2AAABAND.sab_QueryOBDInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?)  }");
            if(StringUtil.isNull(qrCode)){

                callStmt.setString(1, resultQrCode);
            }else{

                callStmt.setString(1, qrCode);
            }

            callStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

            callStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(8, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(9, java.sql.Types.VARCHAR);

            callStmt.registerOutParameter(10, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(12, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(13, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
            callStmt.execute();


//            resultMap.put("oltName", oltName);

//            resultMap.put("address",address);
//            resultMap.put("standName",standName);


            //obd_id
            resultMap.put("eqpId",callStmt.getString(2)==null?"":callStmt.getString(2));
            //obd_name
            resultMap.put("splitterName",callStmt.getString(3)==null?"":callStmt.getString(3));
            //obd_remark
            resultMap.put("remark",callStmt.getString(4)==null?"":callStmt.getString(4));
            resultMap.put("obd_prjName",callStmt.getString(5)==null?"":callStmt.getString(5));
            resultMap.put("obd_proNo",callStmt.getString(6)==null?"":callStmt.getString(6));

            resultMap.put("qrCode", resultQrCode);
            resultMap.put("portId", portId);
            resultMap.put("digCode", digCode);
            resultMap.put("hgu_sn", hgu_sn);
            resultMap.put("hgu_hg", hgu_hg);
            resultMap.put("hgu_pon", hgu_pon);

            resultMap.put("dire_id",callStmt.getString(7)==null?"":callStmt.getString(7));
            resultMap.put("dire_name",callStmt.getString(8)==null?"":callStmt.getString(8));
            //olt_id
            resultMap.put("resId",callStmt.getString(9)==null?"":callStmt.getString(9));


            resultMap.put("oltIP",callStmt.getString(10)==null?"":callStmt.getString(10));
            resultMap.put("ponId",callStmt.getString(11)==null?"":callStmt.getString(11));
            //pon_code
            resultMap.put("pon",callStmt.getString(12)==null?"":callStmt.getString(12));

            StringBuffer sbf = new StringBuffer();
            sbf.append(
                    " select c.position as portName , SF_GET_DESC_CHINA(c.OPR_STATE_ID) as state ,port_id as portId\n");
            sbf.append(" from rme_eqp a, pub_two_dimension_code b, rme_port c           \n");
            sbf.append(" where a.delete_state = '0'                                     \n");
            sbf.append("  and a.res_type_id = '2530'                                    \n");
            sbf.append("  and a.eqp_id = b.res_id                                       \n");
            sbf.append("  and b.delete_state = '0'                                      \n");
            sbf.append("  and b.res_type_id  in( '2530','160001')                       \n");
            sbf.append("  and c.delete_state = '0'                                      \n");
            sbf.append("  and c.super_res_id = a.eqp_id                                 \n");
            if(StringUtil.isNull(qrCode)){
                sbf.append("  and b.digcode ='" + resultQrCode + "' ");
            }else{
                sbf.append("  and b.digcode ='" + qrCode + "' ");
            }
            sbf.append(" order by portName");

            List ponPortList = null;
            try {
                ponPortList = dynamicQueryListByMap(sbf.toString(), null, null);
            } catch (DataAccessException e) {
                logger.error("根据qrCode查询PON List" + qrCode + "PON List:" + e.getMessage());
            }

            //query resId
            String yzPortId = "";
            StringBuilder sb = new StringBuilder();
            sb.append(" select r.res_id as resId                                   \n");
            sb.append("    from Asn_Link_Route r, srv_instance i,wo_work_order a   \n");
            sb.append("   where r.Delete_State = '0'                               \n");
            sb.append("     And r.Link_Id = i.Route_Id                             \n");
            sb.append("     and i.Dis_Seq = to_char(a.base_order_id)               \n");
            sb.append("     And i.Srv_Id <> ('1001')                               \n");
            sb.append("     And i.Delete_State = '0'                               \n");
            sb.append("     and a.id= '" + wkOrderId + "'                           ");
            try {
                map = dynamicQueryObjectByMap(sb.toString(), null, null);
            } catch (DataAccessException e) {
                logger.error("wkOrderId:" + wkOrderId + ",  query resId" + e.getMessage());
            }
            if (null != map) {
                yzPortId = (String) map.get("resId");
                logger.info("wkOrderId:" + wkOrderId + ",Asn_Link_Route" + yzPortId);

                if (ponPortList != null && ponPortList.size() > 0 && !StringUtil.isNull(yzPortId)) {
                    for (Object portInfo : ponPortList) {
                        if (((Map) portInfo).get("portId").equals(yzPortId)) {
                            ((Map) portInfo).put("state", "占用");
                        }
                    }
                }
            }
            resultMap.put("ponPortList", ponPortList);
        } catch (SQLException e) {
            resultMap.put("flag_desc", "qrCode:" + qrCode + "不存在!");
            e.printStackTrace();
        } finally {
            cleanUp(conn, null, callStmt, null);
        }
        return resultMap;
    }

    public String queryOrderInfo(String staffId1) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        String flag ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call HNLT_GK.INF_APP_HPAGE_QUERY(?,?) }");
            cs.setString(1, staffId1);
            cs.registerOutParameter(2, OracleTypes.VARCHAR);
            cs.execute();
            flag = cs.getString(2);
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        logger.info("result---:"+flag);
        return flag;
    }

    public Map<String,String> querymakeProcessData(String wk_order_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("SELECT t.NEW_WG_STATE_IPTV as wg_iptv_state,t.NEW_WG_RULT_ID_IPTV as wg_ret_id_iptv,t.NEW_WG_RULT_DESC_IPTV as wg_iptv_desc,t.AAA_RULT_ID as aaa_ret_id,t.AAA_RULT_DESC as aaa_ret_desc,t.NEW_WG_RULT_ID as wg_ret_id,t.NEW_WG_RULT_DESC as wg_ret_desc,t.NEW_BAND3A_WG_RULT_ID as band3a_wg_ret_id,t.NEW_BAND3A_WG_RULT_DESC as band3a_wg_ret_desc,NVL(t.NEW_WG_STATE,-1) as wg_state,NVL(t.AAA_STATE,-1) as  aaa_state,NVL(t.NEW_BAND3A_STATE,-1) as band3A_state FROM hnlt_gk.UOS_SCAN2AAABAND T where t.WK_ORDER_ID ='"+wk_order_id+ "'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {

                resultMap.put("aaa_ret_id",rs.getString("aaa_ret_id")==null?"":rs.getString("aaa_ret_id"));
                resultMap.put("aaa_ret_desc",rs.getString("aaa_ret_desc")==null?"":rs.getString("aaa_ret_desc"));
                resultMap.put("wg_ret_id",rs.getString("wg_ret_id")==null?"":rs.getString("wg_ret_id"));
                resultMap.put("wg_ret_desc",rs.getString("wg_ret_desc")==null?"":rs.getString("wg_ret_desc"));
                resultMap.put("band3a_wg_ret_id",rs.getString("band3a_wg_ret_id")==null?"":rs.getString("band3a_wg_ret_id"));
                resultMap.put("band3a_wg_ret_desc",rs.getString("band3a_wg_ret_desc")==null?"":rs.getString("band3a_wg_ret_desc"));

                resultMap.put("wg_ret_id_iptv",rs.getString("wg_ret_id_iptv")==null?"":rs.getString("wg_ret_id_iptv"));
                resultMap.put("wg_iptv_desc",rs.getString("wg_iptv_desc")==null?"":rs.getString("wg_iptv_desc"));
                String wg_state = String.valueOf(rs.getInt("wg_state"));
                String aaa_state = String.valueOf(rs.getInt("aaa_state"));
                String band3A_state = String.valueOf(rs.getInt("band3A_state"));
                System.out.println("band3A_state:"+rs.getInt("band3A_state"));
                String wg_iptv_state = String.valueOf(rs.getInt("wg_iptv_state"));

                resultMap.put("wg_iptv_state",wg_iptv_state);

                resultMap.put("wg_state",wg_state);
                resultMap.put("wg_state_name",getStateName(wg_state));

                resultMap.put("aaa_state",aaa_state);
                resultMap.put("aaa_state_name",getStateName(aaa_state));

                resultMap.put("band3a_state",band3A_state);
                resultMap.put("band3a_state_name",getStateName(band3A_state));
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    private String getStateName(String key)
    {
        String result = "";
        if("0".equals(key))
        {
            result="初始状态";
        }
        if("1".equals(key))
        {
            result="已扫描";
        }
        if("3".equals(key))
        {
            result="失败";
        }

        return result;
    }


    public void insertYHCount(String qrCode,String type) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="insert into YH_CLICK_COUNT(QRCODE,OPERATION_TYPE) VALUES(?,?)";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,qrCode);
            ps.setString(2,type);
            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public Map<String,String> queryOrderTypeById(String orderId) throws SQLException {
        String orderType= "";
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("SELECT (case when t.service_id = '600909' then '6' else decode(ooki.evt_with_prod_id ,'9','1','71','2','72','2','32','4','7')  end ) as order_type ,\n" +
                " ra.num as  cityCode,\n" +
                " ua.city_code as  districtCode ,decode(ua.acronym,'0732','0731','0733','0731',ua.acronym)||ooki.acc_nbr  as  acc_nbr \n" +
                "FROM om_order T\n" +
                "left join hnlt_gk.om_order_key_info ooki on ooki.id = t.id\n" +
                "left join hnlt_gk.uos_area_city_map ua on ua.area_id = t.area_id\n" +
                "left join spc_region_china@to_ibss ra on ra.region_code = ua.acronym\n" +
                " where t.id = ? and  rownum=1 ");
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
                orderType = rs.getString("order_type");
                String cityCode = rs.getString("cityCode");
                String districtCode = rs.getString("districtCode");
                String accNbrCode = rs.getString("acc_nbr");
                resultMap.put("orderType",orderType);
                resultMap.put("cityCode",cityCode);
                resultMap.put("districtCode",districtCode);
                resultMap.put("accNbrCode",accNbrCode);

            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public String queryStaffTel(String staffId) throws SQLException {
        String phone= "";
        String sqlStr=("select mobile_tel  as phone from uos_staff where staff_id= ?");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(staffId));
            rs = ps.executeQuery();
            while (rs.next()) {

                phone = rs.getString("phone");
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return phone;
    }

    public Map<String,Object> queryZWTSpeedInfo(String accNbr) throws SQLException
    {
        Map<String, Object> resultMap =  new HashMap<String,Object>();
        String sqlStr=(" select  order_id as orderId  from  hnlt_gk.TEST_REPORT_SPEED_NEW where rownum=1 and net_code=?   order by create_date desc");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,accNbr);
            rs = ps.executeQuery();
            while (rs.next()) {
                resultMap.put("orderId",rs.getObject("orderId"));
//                phone = rs.getString("phone");
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        return resultMap;
    }

    public List<Map> querySpotSpeedInfo(String orderId) throws SQLException
    {
        List<Map> list = new ArrayList<Map>();
        String sqlStr=("  select test_area as spot,max_speed,average_speed,less_speed from  hnlt_gk.TEST_REPORT_SPEED_NEW_spot where ORDER_ID=?  and rownum<6");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> resultMap =  new HashMap<String,Object>();
                resultMap.put("spot",rs.getObject("spot")==null?"":rs.getObject("spot"));
                resultMap.put("max_speed",rs.getObject("max_speed")==null?"":rs.getObject("max_speed"));
                resultMap.put("average_speed",rs.getObject("average_speed")==null?"":rs.getObject("average_speed"));
                resultMap.put("less_speed",rs.getObject("less_speed")==null?"":rs.getObject("less_speed"));
                list.add(resultMap);
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        return list;
    }
    public Map<String, Object> queryLightState(String orderId) throws SQLException {
        Map<String, Object> resultMap =  new HashMap<String,Object>();

        String sqlStr=("");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        String flag ="";
        String desc ="";
        try {
            conn = getConnection();
            cs = conn.prepareCall("{ call hnlt_gk.P_APP_KT_PROCESS_CHECK(?,?) }");
            cs.setString(1, orderId);

            cs.registerOutParameter(2, java.sql.Types.VARCHAR);


            logger.info("----准备执行P_CREATE_ORDER-----");
            cs.execute();

            desc = cs.getString(2);
            JSONObject js  = JSONObject.fromObject(desc);
            resultMap.put("nFlagArrivalSign",js.getString("nFlagArrivalSign"));
            resultMap.put("nFlagSm",js.getString("nFlagSm"));
            resultMap.put("nFlagYh",js.getString("nFlagYh"));
            resultMap.put("nFlagSpeed",js.getString("nFlagSpeed"));
            resultMap.put("nFlagPower",js.getString("nFlagPower"));
            resultMap.put("nFlagZWT",js.getString("nFlagZWT"));
            System.out.println("queryLightState :"+desc);
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        //resultMap.put("ret_code",flag);
        resultMap.put("ret_desc",desc);
        return resultMap;
    }


    public Map<String, String> submitPonChangeMachine(Map<String, Object> param) {
        // qrCode = "6901285991219";
        Map<String, String> resultMap = new HashMap<String, String>();
        Connection conn = null;
        CallableStatement callStmt = null;
        try {
            conn = getConnection();

            callStmt = conn.prepareCall("{ call hnlt_gk.F_UOS_CHGPON_TRANSFER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  }");

            callStmt.setString(1, param.get("wk_order_id")==null?"": (String)param.get("wk_order_id"));
            callStmt.setString(2, param.get("staff_id")==null?"": (String)param.get("staff_id"));
            callStmt.setString(3, param.get("accnbr")==null?"": (String)param.get("accnbr"));
            callStmt.setString(4, param.get("aaa_nas_port_id")==null?"": (String)param.get("aaa_nas_port_id"));
            callStmt.setString(5, param.get("aaa_olt_info")==null?"": (String)param.get("aaa_olt_info"));
            callStmt.setString(6, param.get("aaa_pon_port")==null?"": (String)param.get("aaa_pon_port"));
            callStmt.setString(7, param.get("aaa_onu_info")==null?"": (String)param.get("aaa_onu_info"));
            callStmt.setString(8, param.get("bind_type")==null?"": (String)param.get("bind_type"));
            callStmt.setString(9, "");
            callStmt.setString(10, param.get("eqp_id")==null?"": (String)param.get("eqp_id"));
            callStmt.setString(11, param.get("port_id")==null?"": (String)param.get("port_id"));
            callStmt.setString(12, param.get("olt_ip")==null?"": (String)param.get("olt_ip"));
            callStmt.setString(13, param.get("pon")==null?"": (String)param.get("pon"));
            callStmt.setString(14, param.get("qr_code")==null?"": (String)param.get("qr_code"));

            callStmt.registerOutParameter(15, java.sql.Types.VARCHAR);
            callStmt.registerOutParameter(16, java.sql.Types.VARCHAR);
            callStmt.execute();

            //obd_id
            resultMap.put("code",callStmt.getString(15)==null?"":callStmt.getString(15));
            //obd_name
            resultMap.put("desc",callStmt.getString(16)==null?"":callStmt.getString(16));
            resultMap.put("wk_order_id",(String)param.get("wk_order_id"));
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            cleanUp(conn, null, callStmt, null);
        }
        return resultMap;
    }

    public Map<String,String> queryPonChangeMachineData(String wk_order_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("select a.CUT_WG_RULT_ID as cut_wg_id,a.NEW_WG_RULT_ID as new_wg_id,a.NEW_WG_STATE as new_wg_state,a.CUT_WG_STATE as cut_wg_state,a.CUT_WG_RULT_DESC as cut_wg_desc,a.NEW_WG_RULT_DESC as new_wg_desc  from hnlt_gk.UOS_CHGPON a where a.WK_ORDER_ID ='"+wk_order_id+ "'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {

                resultMap.put("cut_wg_desc",rs.getString("cut_wg_desc")==null?"":rs.getString("cut_wg_desc"));
                resultMap.put("new_wg_desc",rs.getString("new_wg_desc")==null?"":rs.getString("new_wg_desc"));
                resultMap.put("cut_wg_state",rs.getString("cut_wg_state")==null?"":rs.getString("cut_wg_state"));
                resultMap.put("new_wg_state",rs.getString("new_wg_state")==null?"":rs.getString("new_wg_state"));

                resultMap.put("cut_wg_id",rs.getString("cut_wg_id")==null?"":rs.getString("cut_wg_id"));
                resultMap.put("new_wg_id",rs.getString("new_wg_id")==null?"":rs.getString("new_wg_id"));

            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public  Map<String, String> queryNetOrderInfo(String orderId)
    {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("    SELECT T.USER_NAME , T.CONTACT_TEL , T.ACC_NBR ,T.PROJECT_NAME FROM OM_FAULT_ORDER T WHERE T.FAULT_ORDER_ID = (SELECT T.BASE_ORDER_ID FROM WO_WORK_ORDER T WHERE T.ID = "+orderId+")");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {

                resultMap.put("userName",rs.getString("USER_NAME")==null?"":rs.getString("USER_NAME"));
                resultMap.put("contactPhone",rs.getString("CONTACT_TEL")==null?"":rs.getString("CONTACT_TEL"));
                resultMap.put("broadbandNumber",rs.getString("ACC_NBR")==null?"":rs.getString("ACC_NBR"));
                resultMap.put("broadPro",rs.getString("PROJECT_NAME")==null?"":rs.getString("PROJECT_NAME"));


            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return resultMap;
        }finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }

    public List<Map> queryTerminalStoreByStaffId(String staffId,String oper_type)
    {
        List<Map> list = new ArrayList<Map>();
        String sqlStr="";


        //全部记录
        if("0".equals(oper_type))
        {
            sqlStr=(" select B.STAFF_ID,B.HGU_STATUS,B.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1) is null THEN B.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,B.HGU_MAC,B.HGU_SN,B.HGU_SYSTEM,B.HGU_LAN,B.HGU_EDITION,B.GMPONTYPE,B.GM32CODE\n" +
                    ",B.HGU_RESOURCE,to_char(B.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,B.OPER_STAFF ,B.MODIFY_STAFF,to_char(B.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,B.HGU_TYPE,B.RETURN_TYPE from hnlt_gk.uos_warehouse B where B.STAFF_ID=? and B.HGU_STATUS='1'\n" +
                    "union\n" +
                    "SELECT A.STAFF_ID,A.HGU_STATUS,A.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) is null THEN A.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,A.HGU_MAC,A.HGU_SN,A.HGU_SYSTEM,A.HGU_LAN,A.HGU_EDITION,A.GMPONTYPE,A.GM32CODE\n" +
                    ",A.HGU_RESOURCE,to_char(A.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,A.OPER_STAFF ,A.MODIFY_STAFF,to_char(A.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,A.HGU_TYPE,A.RETURN_TYPE from hnlt_gk.uos_warehouse A where A.accept_staff=? and A.HGU_STATUS='2'\n")+
                    "union\n" +
                    "SELECT A.STAFF_ID,A.HGU_STATUS,A.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) is null THEN A.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,A.HGU_MAC,A.HGU_SN,A.HGU_SYSTEM,A.HGU_LAN,A.HGU_EDITION,A.GMPONTYPE,A.GM32CODE\n" +
                    ",A.HGU_RESOURCE,to_char(A.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,A.OPER_STAFF ,A.MODIFY_STAFF,to_char(A.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,A.HGU_TYPE,A.RETURN_TYPE from hnlt_gk.uos_warehouse A where A.MODIFY_STAFF=? and A.HGU_STATUS='2' and  STAFF_ID=0 \n"+
                    "union\n" +
                    "SELECT A.STAFF_ID,A.HGU_STATUS,A.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) is null THEN A.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,A.HGU_MAC,A.HGU_SN,A.HGU_SYSTEM,A.HGU_LAN,A.HGU_EDITION,A.GMPONTYPE,A.GM32CODE\n" +
                    ",A.HGU_RESOURCE,to_char(A.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,A.OPER_STAFF ,A.MODIFY_STAFF,to_char(A.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,A.HGU_TYPE,A.RETURN_TYPE from hnlt_gk.uos_warehouse A where A.STAFF_ID=? and A.HGU_STATUS='3.5'  \n";
        }
        //领用记录
        if("1".equals(oper_type))
        {
            sqlStr=" select B.STAFF_ID,B.HGU_STATUS,B.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1) is null THEN B.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,B.HGU_MAC,B.HGU_SN,B.HGU_SYSTEM,B.HGU_LAN,B.HGU_EDITION,B.GMPONTYPE,B.GM32CODE\n" +
                    ",B.HGU_RESOURCE,to_char(B.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,B.OPER_STAFF ,B.MODIFY_STAFF,to_char(B.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,B.HGU_TYPE,B.RETURN_TYPE from hnlt_gk.uos_warehouse B where B.STAFF_ID=? and B.HGU_STATUS='1'\n" ;

        }
        //转派待收记录
        if("2".equals(oper_type))
        {
            sqlStr="SELECT A.STAFF_ID,A.HGU_STATUS,A.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) is null THEN A.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,A.HGU_MAC,A.HGU_SN,A.HGU_SYSTEM,A.HGU_LAN,A.HGU_EDITION,A.GMPONTYPE,A.GM32CODE\n" +
                    ",A.HGU_RESOURCE,to_char(A.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,A.OPER_STAFF ,A.MODIFY_STAFF,to_char(A.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,A.HGU_TYPE,A.RETURN_TYPE from hnlt_gk.uos_warehouse A where A.accept_staff=? and A.HGU_STATUS='2'\n";
        }
        //转派待收记录
        if("3".equals(oper_type))
        {
            sqlStr="SELECT A.STAFF_ID,A.HGU_STATUS,A.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) is null THEN A.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=A.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,A.HGU_MAC,A.HGU_SN,A.HGU_SYSTEM,A.HGU_LAN,A.HGU_EDITION,A.GMPONTYPE,A.GM32CODE\n" +
                    ",A.HGU_RESOURCE,to_char(A.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,A.OPER_STAFF ,A.MODIFY_STAFF,to_char(A.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,A.HGU_TYPE,A.RETURN_TYPE from hnlt_gk.uos_warehouse A where A.MODIFY_STAFF=? and A.HGU_STATUS='2' and  STAFF_ID=0 \n";
        }

        //01可用退回中  07待检退回中
        if("01".equals(oper_type) || "07".equals(oper_type))
        {
            sqlStr=" select B.STAFF_ID,B.HGU_STATUS,B.HGU_BRAND,(CASE WHEN(SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1) is null THEN B.HGU_MODEL ELSE (SELECT T.TERMINAL_DESC FROM hnlt_gk.UOS_WAREHOUSE_TERMINAL_TYPE T where HGU_MODEL=B.HGU_MODEL and rownum=1 ) END)AS HGU_MODEL,B.HGU_MAC,B.HGU_SN,B.HGU_SYSTEM,B.HGU_LAN,B.HGU_EDITION,B.GMPONTYPE,B.GM32CODE\n" +
                    ",B.HGU_RESOURCE,to_char(B.CREATE_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  CREATE_DATE ,B.OPER_STAFF ,B.MODIFY_STAFF,to_char(B.MODIFY_DATE, 'yyyy-mm-dd-hh24:mi:ss') as  MODIFY_DATE ,B.HGU_TYPE,B.RETURN_TYPE from hnlt_gk.uos_warehouse B where B.STAFF_ID=? and B.HGU_STATUS='3.5' and B.RETURN_TYPE=? \n" ;
        }


        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            if("0".equals(oper_type))
            {
                ps.setInt(1,Integer.parseInt(staffId));
                ps.setString(2,staffId);
                ps.setInt(3,Integer.parseInt(staffId));
                ps.setInt(4,Integer.parseInt(staffId));

            }
            else if("1".equals(oper_type))
            {
                ps.setInt(1,Integer.parseInt(staffId));
            }
            else if("2".equals(oper_type))
            {
                ps.setString(1,staffId);
            } else if("3".equals(oper_type))
            {
                ps.setInt(1,Integer.parseInt(staffId));
            }
            else if("01".equals(oper_type) || "07".equals(oper_type))
            {
                ps.setInt(1,Integer.parseInt(staffId));
                ps.setString(2,oper_type);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> resultMap =  new HashMap<String,String>();
                resultMap.put("STAFF_ID",String.valueOf(rs.getInt("STAFF_ID")));
                resultMap.put("HGU_STATUS",rs.getString("HGU_STATUS")==null?"":rs.getString("HGU_STATUS"));
                resultMap.put("HGU_BRAND",rs.getString("HGU_BRAND")==null?"":rs.getString("HGU_BRAND"));
                resultMap.put("HGU_MODEL",rs.getString("HGU_MODEL")==null?"":rs.getString("HGU_MODEL"));

                resultMap.put("HGU_MAC",rs.getString("HGU_MODEL")==null?"":rs.getString("HGU_MAC"));
                resultMap.put("HGU_SN",rs.getString("HGU_MODEL")==null?"":rs.getString("HGU_SN"));
                resultMap.put("HGU_SYSTEM",rs.getString("HGU_SYSTEM")==null?"":rs.getString("HGU_SYSTEM"));
                resultMap.put("HGU_LAN",rs.getString("HGU_LAN")==null?"":rs.getString("HGU_LAN"));
                resultMap.put("HGU_EDITION",rs.getString("HGU_EDITION")==null?"":rs.getString("HGU_EDITION"));
                resultMap.put("GMPONTYPE",rs.getString("GMPONTYPE")==null?"":rs.getString("GMPONTYPE"));
                resultMap.put("GM32CODE",rs.getString("GM32CODE")==null?"":rs.getString("GM32CODE"));
                resultMap.put("HGU_RESOURCE",rs.getString("HGU_RESOURCE")==null?"":rs.getString("HGU_RESOURCE"));
                resultMap.put("CREATE_DATE",rs.getString("CREATE_DATE")==null?"":rs.getString("CREATE_DATE"));
                resultMap.put("OPER_STAFF",String.valueOf(rs.getInt("OPER_STAFF")));
                resultMap.put("MODIFY_STAFF",String.valueOf(rs.getInt("MODIFY_STAFF")));
                resultMap.put("MODIFY_DATE",rs.getString("MODIFY_DATE")==null?"":rs.getString("MODIFY_DATE"));
                resultMap.put("HGU_TYPE",rs.getString("HGU_TYPE")==null?"":rs.getString("HGU_TYPE"));
                resultMap.put("RETURN_TYPE",rs.getString("RETURN_TYPE")==null?"":rs.getString("RETURN_TYPE"));
                list.add(resultMap);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return list;
        }finally {
            cleanUp(conn, null, ps, rs);
        }
        return list;
    }


    public void insertTerminalStore(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="insert into uos_warehouse(STAFF_ID,HGU_STATUS,HGU_BRAND,HGU_MODEL,HGU_MAC,HGU_SN,HGU_SYSTEM,HGU_LAN,HGU_EDITION,GMPONTYPE,GM32CODE,HGU_RESOURCE,CREATE_DATE,OPER_STAFF ,MODIFY_STAFF,MODIFY_DATE,HGU_TYPE)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,sysdate,?)";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(map.get("STAFF_ID")));
            //终端状态
            ps.setString(2,map.get("HGU_STATUS"));
            //终端品牌
            ps.setString(3,map.get("HGU_BRAND"));
            //终端类型
            ps.setString(4,map.get("HGU_MODEL"));
            //终端mac
            ps.setString(5,map.get("HGU_MAC").trim());
            //终端sn
            ps.setString(6,map.get("HGU_SN").trim());
            //终端系统
            ps.setString(7,map.get("HGU_SYSTEM"));
            //终端LAN口规格
            ps.setString(8,map.get("HGU_LAN"));
            //终端版本
            ps.setString(9,map.get("HGU_EDITION"));
            //终端光制式
            ps.setString(10,map.get("GMPONTYPE"));
            //终端二维码
            ps.setString(11,map.get("GM32CODE"));
            //来源
            ps.setString(12,map.get("HGU_RESOURCE"));

            ps.setInt(13,Integer.parseInt(map.get("OPER_STAFF")));
            ps.setInt(14,Integer.parseInt(map.get("MODIFY_STAFF")));
            ps.setString(15,map.get("HGU_TYPE"));


            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }


    public void insertSendLog(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="insert into UOS_WAREHOUSE_SEND_LOG(send_staff_id,accept_staff_id,oper_type)" +
                " VALUES(?,?,?)";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);

            ps.setString(1,map.get("staffId"));

            ps.setString(2,map.get("accept_staff"));

            ps.setString(3,map.get("oper_type"));

            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }


    public void updateSendTerimalData(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="update UOS_WAREHOUSE set STAFF_ID=?,ACCEPT_STAFF=?,MODIFY_STAFF=?,MODIFY_DATE=sysdate,HGU_STATUS=? where HGU_SN=? and HGU_STATUS=?";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,0);
            ps.setString(2,map.get("accept_staff"));

            ps.setInt(3,Integer.parseInt(map.get("staffId")));
            ps.setString(4,map.get("terminal_state"));
            ps.setString(5,map.get("terimal_sn"));

            ps.setString(6,"1");
            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public void updateBackTerimalData(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="update UOS_WAREHOUSE set STAFF_ID=?,ACCEPT_STAFF=?,MODIFY_STAFF=?,MODIFY_DATE=sysdate,HGU_STATUS=? where HGU_SN=? ";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(map.get("modify_staff")));
            ps.setString(2,"");

            ps.setInt(3,Integer.parseInt(map.get("staffId")));
            ps.setString(4,map.get("terminal_state"));
            ps.setString(5,map.get("terimal_sn"));

            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public void updateAcceptTerimalData(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="update UOS_WAREHOUSE set STAFF_ID=?,ACCEPT_STAFF=?,MODIFY_STAFF=?,MODIFY_DATE=sysdate,HGU_STATUS=? where HGU_SN=?  and  HGU_STATUS=? ";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(map.get("staffId")));
            ps.setString(2,"");

            ps.setInt(3,Integer.parseInt(map.get("staffId")));
            ps.setString(4,map.get("terminal_state"));
            ps.setString(5,map.get("terimal_sn"));
            ps.setString(6,map.get("ware_house_status"));

            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public void updateRetTerimalData(Map<String,String> map) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr="update UOS_WAREHOUSE set STAFF_ID=?,ACCEPT_STAFF=?,MODIFY_STAFF=?,MODIFY_DATE=sysdate,HGU_STATUS=?,return_type=? where HGU_SN=?  and  HGU_STATUS=? ";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        System.out.println("staffId："+map.get("staffId"));
        System.out.println("terminal_state："+map.get("terminal_state"));
        System.out.println("terimal_sn："+map.get("terimal_sn"));
        System.out.println("terminalState："+map.get("terminalState"));
        System.out.println("ware_house_status："+map.get("ware_house_status"));
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(map.get("staffId")));
            ps.setString(2,"");

            ps.setInt(3,Integer.parseInt(map.get("staffId")));
            ps.setString(4,map.get("terminal_state"));
            ps.setString(5,map.get("terminalState"));
            ps.setString(6,map.get("terimal_sn"));
            ps.setString(7,map.get("ware_house_status"));

            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public  Map<String, String> queryStaff(String phoneNumber)
    {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=(" select staff_id as staff_id ,staff_name as staff_name from uos_staff where USERNAME= ? and state='1'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,phoneNumber);
            rs = ps.executeQuery();
            while (rs.next()) {

                int staff_id = rs.getInt("staff_id");
                String staff_name = rs.getString("staff_name");
                resultMap.put("staff_id",String.valueOf(staff_id));
                resultMap.put("staff_name",staff_name);

            }
        }catch(Exception e)
        {

            e.printStackTrace();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        return resultMap;
    }

    public List<Map> queryTerminalOperByStaffId(String staffId,String oper_type)
    {
        List<Map> list = new ArrayList<Map>();

        String sqlStr=("select to_char(OPER_DATE,'yyyy-mm-dd-hh24:mi:ss') as OPER_DATE ,(case when oper_type='01' then '出库' when oper_type='02' then '换机' when oper_type='03' then '退机' when oper_type='04' then '领用' when oper_type='05' then '退回' when oper_type='06' then '调拨' else oper_type end )as OPER_TYPE from uos_warehouse_log \n" +
                " where STAFF_ID=? ");

        if(!"0".equals(oper_type))
        {
            sqlStr = sqlStr+" and oper_type=? ";
        }
        sqlStr = sqlStr+" and rownum<101 order by oper_date desc";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(staffId));
            if(!"0".equals(oper_type))
            {
                ps.setString(2,oper_type);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> resultMap =  new HashMap<String,String>();

                 resultMap.put("OPER_DATE",rs.getString("OPER_DATE")==null?"":rs.getString("OPER_DATE"));
                resultMap.put("OPER_TYPE",rs.getString("OPER_TYPE")==null?"":rs.getString("OPER_TYPE"));

                list.add(resultMap);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return list;
        }finally {
            cleanUp(conn, null, ps, rs);
        }
        return list;
    }

    public  Map<String, String> queryStaffMobile(String staffId)
    {
        Map<String, String> result = new HashMap<String,String>();
        String sqlStr=("select mobile_tel from uos_staff where staff_id=? ");


        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setInt(1,Integer.parseInt(staffId));


            rs = ps.executeQuery();
            while (rs.next()) {


                result.put("mobile_tel",rs.getString("mobile_tel")==null?"":rs.getString("mobile_tel"));




            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return result;
        }finally {
            cleanUp(conn, null, ps, rs);
        }

        return result;
    }

    public String getWareHouseStaff(String staffId,String orderId) throws DataAccessException, SQLException {
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


    public Map<String,String> queryIsSap(String wk_order_id) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("select count(*) as sap_flag from uos_warehouse t where HGU_SN=? and HGU_STATUS <>'3'");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            rs = ps.executeQuery();
            if (rs.next()) {
             int  sap_flag =  rs.getInt("aaa_ret_id");
             //1是sap  0不是sap
                resultMap.put("sap_flag",String.valueOf(sap_flag));
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }



    public   Map<String, String> queryChangeMachineOrder(String staffId)
    {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sql = "select t1.work_order_id,\n" +
                "       t1.hgu_sn,\n" +
                "       t1.werks,\n" +
                "       t1.staff_id,\n" +
                "       t1.create_date,\n" +
                "       t1.loid,\n" +
                "       t1.new_pwd,\n" +
                "       t1.aaa_portid,\n" +
                "       t1.aaa_olt_ip,\n" +
                "       t1.aaa_pon_no,\n" +
                "       t1.aaa_hgu_sn,\n" +
                "       t1.aaa_bindtype,\n" +
                "       t1.band3a_rult_id,\n" +
                "       t1.username,\n" +
                "       t1.aaa_flag,\n" +
                "       t1.sn_old,\n" +
                "       t1.sap_flag,\n" +
                "       t1.channelName,\n" +
                "       t1.zwNumber,\n" +
                "       t1.zwName,\n" +
                "       t2.new_rms_rult_id\n" +
                "  from UOS_CHANGE_MACHINE_ORDER t1\n" +
                "  left join UOS_CHGHGU t2\n" +
                "    on t1.work_order_id = t2.chgu_id\n" +
                " where t1.staff_id = ?";
        String sqlStr=(sql);
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int orderNum =0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                resultMap.put("work_order_id",rs.getString("work_order_id")) ;
                resultMap.put("hgu_sn",rs.getString("hgu_sn")) ;
                resultMap.put("werks",rs.getString("werks")) ;
                resultMap.put("staff_id",rs.getString("staff_id")) ;
                resultMap.put("loid",rs.getString("loid")) ;

                resultMap.put("new_pwd",rs.getString("new_pwd")) ;
                resultMap.put("aaa_portid",rs.getString("aaa_portid")) ;
                resultMap.put("aaa_olt_ip",rs.getString("aaa_olt_ip")) ;
                resultMap.put("aaa_pon_no",rs.getString("aaa_pon_no")) ;
                resultMap.put("aaa_hgu_sn",rs.getString("aaa_hgu_sn")) ;

                resultMap.put("aaa_bindtype",rs.getString("aaa_bindtype")) ;
                resultMap.put("band3a_rult_id",rs.getString("band3a_rult_id")) ;
                resultMap.put("username",rs.getString("username")) ;
                resultMap.put("aaa_flag",rs.getString("aaa_flag")) ;
                resultMap.put("sn_old",rs.getString("sn_old")) ;

                resultMap.put("sap_flag",rs.getString("sap_flag")) ;
                resultMap.put("channelName",rs.getString("channelName")) ;
                resultMap.put("zwNumber",rs.getString("zwNumber")) ;
                resultMap.put("zwName",rs.getString("zwName")) ;
                resultMap.put("new_rms_rult_id",rs.getString("new_rms_rult_id")) ;



            }
        }catch(Exception e)
        {

            e.printStackTrace();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

        return resultMap;
    }

    public void insertChangeMachineOrder(Map<String,Object> paramMap) throws SQLException {
        String wk_order_id = paramMap.get("wk_order_id").toString();
        String hgu_sn = paramMap.get("hgu_sn").toString();
        String staff_id = paramMap.get("staff_id").toString();
        String loid = paramMap.get("loid").toString();
        String new_pwd = paramMap.get("new_pwd").toString();
        String aaa_portid = paramMap.get("aaa_portid").toString();
        String aaa_olt_ip = paramMap.get("aaa_olt_ip").toString();
        String aaa_pon_no = paramMap.get("aaa_pon_no").toString();
        String aaa_hgu_sn = paramMap.get("aaa_hgu_sn").toString();
        String aaa_bindtype = paramMap.get("aaa_bindtype").toString();
        String band3a_rult_id = paramMap.get("band3a_rult_id").toString();
        String username = paramMap.get("username").toString();

        String aaa_flag = paramMap.get("aaa_flag").toString();
        String sn_old = paramMap.get("sn_old").toString();
        String sap_flag = paramMap.get("sap_flag").toString();
        String zwNumber = paramMap.get("zwNumber").toString();
        String channelName = paramMap.get("channelName").toString();
        String werks = paramMap.get("werks").toString();
        String zwName = paramMap.get("zwName").toString();




        String sqlStr="insert into UOS_CHANGE_MACHINE_ORDER(work_order_id,hgu_sn,werks,staff_id,create_date,loid,new_pwd,aaa_portid,aaa_olt_ip,aaa_pon_no,aaa_hgu_sn,aaa_bindtype,band3a_rult_id," +
                "username,aaa_flag,sn_old,sap_flag,channelName,zwNumber,zwName) VALUES(?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,wk_order_id);
            ps.setString(2,hgu_sn);
            ps.setString(3,werks);
            ps.setString(4,staff_id);
            ps.setString(5,loid);
            ps.setString(6,new_pwd);
            ps.setString(7,aaa_portid);
            ps.setString(8,aaa_olt_ip);
            ps.setString(9,aaa_pon_no);
            ps.setString(10,aaa_hgu_sn);

            ps.setString(11,aaa_bindtype);
            ps.setString(12,band3a_rult_id);
            ps.setString(13,username);
            ps.setString(14,aaa_flag);
            ps.setString(15,sn_old);
            ps.setString(16,sap_flag);
            ps.setString(17,channelName);
            ps.setString(18,zwNumber);
            ps.setString(19,zwName);

            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }


    public void delChangeMachineOrder(String staffId) throws SQLException {




        String sqlStr="delete  from  UOS_CHANGE_MACHINE_ORDER where staff_id=?";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,staffId);
            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public void insertChangeMachineOldSn(Map<String,Object> paramMap) throws SQLException {
        //领用SN
        String newTerminalId =(String) paramMap.get("newTerminalId");
        //staffID
        String staffId =(String) paramMap.get("staffId");

        String oldTerminalId = (String)paramMap.get("oldTerminalId");




        String sqlStr="insert into UOS_CHANGE_MACHINE_OLD_SN(new_hgu_sn,old_hgu_sn,staff_id) VALUES(?,?,?,sysdate)";
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,newTerminalId);
            ps.setString(2,oldTerminalId);
            ps.setString(3,staffId);
            ps.execute();
        } finally {
            cleanUp(conn, null, ps, rs);
        }

    }

    public Map<String,String> queryTerimalBySn(String sn) throws SQLException {
        Map<String, String> resultMap =  new HashMap<String,String>();
        String sqlStr=("select  t.hgu_sn,(case when t.hgu_status='1' then '领用' when t.hgu_status='2' then '转派中' when t.hgu_status='3.5' then '回退中' when t.hgu_status='4' then '出库' else '扫码装机' end  )as hgu_status \n" +
                ",(select z.terminal_desc from hnlt_gk.uos_warehouse_terminal_type z where z.hgu_model=t.hgu_model and rownum=1 ) as hgu_model,t.staff_id,(select tf.staff_name from hnlt_gk.uos_staff tf where tf.staff_id=t.accept_staff) as accept_staff,(select tf.staff_name from hnlt_gk.uos_staff tf where tf.staff_id=t.modify_staff) as modify_staff,\n" +
                " to_char(t.create_date,'yyyy-mm-dd hh24:mi:ss')as create_date ,(select o.broadbanduserid from hnlt_gk.UOS_WAREHOUSE_OUT o where o.newterminalid=t.hgu_sn and rownum=1) as broadbanduserid,\n" +
                "(select o.fixednumuserid from hnlt_gk.UOS_WAREHOUSE_OUT o where o.newterminalid=t.hgu_sn and rownum=1) as fixednumuserid,\n" +
                "(select o.iptvuserid from hnlt_gk.UOS_WAREHOUSE_OUT o where o.newterminalid=t.hgu_sn and rownum=1) as iptvuserid,\n" +
                "to_char((select o.state_date from hnlt_gk.UOS_WAREHOUSE_OUT o where o.newterminalid=t.hgu_sn and rownum=1),'yyyy-mm-dd hh24:mi:ss') as out_date\n" +
                " from hnlt_gk.uos_warehouse t  where  t.hgu_sn=? and t.hgu_status <>'3' and t.hgu_resource is null");
        System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlStr);
            ps.setString(1,sn);
            rs = ps.executeQuery();
            if (rs.next()) {
                String  hgu_sn =  rs.getString("hgu_sn");
                String  hgu_status =  rs.getString("hgu_status");
                String  hgu_model =  rs.getString("hgu_model");
                String  staff_id =  String.valueOf(rs.getInt("staff_id"));
                String accept_staff = rs.getString("accept_staff")==null?"":rs.getString("accept_staff");
                String modify_staff =  rs.getString("modify_staff");

                String broadbanduserid =  rs.getString("broadbanduserid")==null?"":rs.getString("broadbanduserid");
                String fixednumuserid =  rs.getString("fixednumuserid")==null?"":rs.getString("fixednumuserid");
                String iptvuserid =  rs.getString("iptvuserid")==null?"":rs.getString("iptvuserid");
                String out_date =  rs.getString("out_date")==null?"":rs.getString("out_date");
                String create_date = rs.getString("create_date");

                resultMap.put("hgu_sn",hgu_sn);
                resultMap.put("hgu_status",hgu_status);
                resultMap.put("hgu_model",hgu_model);
                resultMap.put("staff_id",staff_id);
                resultMap.put("accept_staff",accept_staff);

                resultMap.put("modify_staff",modify_staff);
                resultMap.put("broadbanduserid",broadbanduserid);
                resultMap.put("fixednumuserid",fixednumuserid);
                resultMap.put("iptvuserid",iptvuserid);
                resultMap.put("out_date",out_date);
                resultMap.put("create_date",create_date);
            }
        } finally {
            cleanUp(conn, null, ps, rs);
        }
        return resultMap;
    }


}


