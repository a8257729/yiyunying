package com.ztesoft.mobile.v2.controller.building;

import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.controller.common.Hdf;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAOImpl;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.util.HttpUtil;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @Description: TODO APP楼宇管理
 * @author: lck
 * @date: 2021年03月01日 15:07
 */


@Controller("builDingController")
@RequestMapping("/builDing")
public class BuilDingController extends WebConfigController {

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }




    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }




    /**
     * 楼宇管理信息
     *
     * @param data     楼长id
     * @param request
     * @param response
     * @return 楼宇信息
     * @throws Exception
     */
    @RequestMapping(value = "/client/builDingInfo")
    @ResponseBody
    public Object builDingInfo(@RequestBody Map<String, Object> data,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String search = (String) data.get("search");

        String buildingId =(String) data.get("staffId");

        String markets =(String) data.get("market")==null?"":(String) data.get("market");
        String regions =(String) data.get("region")==null?"":(String) data.get("region");
        String grades =(String) data.get("grade")==null?"":(String) data.get("grade");


        String sql1 = "SELECT DISTINCT ua.area_id as area_id,decode(ua.area_name,'湘西','吉首','永州','永州市',ua.area_name) as area_name,ua.grade as grade FROM uos_staff t  " +
                "left join uos_job_staff ujs on ujs.staff_id = t.staff_id " +
                "left join uos_job uj on uj.job_id = ujs.job_id " +
                "left join uos_org uo on uo.org_id = uj.org_id " +
                "left join uos_area ua on ua.area_id = uo.area_id " +
                "where t.state = '1' " +
                "and ujs.state = '1' " +
                "and uj.state = '1' " +
                "and uo.state = '1' " +
                "and ua.state = '10A' " +

                "and ua.grade in('C2','C3') " +
                "and ujs.is_normal='1' "+
                "and t.staff_id = ? ";

        Map paramMap1 = new HashMap();



        paramMap1.put("staffId", buildingId);
        String	wherePatternStr12 = "staff_id:staffId";
        Result dataList12 = getCommonService().commonQueryListBySql(sql1, paramMap1, wherePatternStr12);
        List data12 = (List)((Map)dataList12.getResultData().get("data_info")).get("dataList");

        PageResult pageResult = new PageResult();
        Pager pager = new Pager();
        int page = pager.getPage();
        Map pageParams = data;

        int recPerPage = pager.getRecPerPage();
        if(pageParams.get("page")!=null){
            page = (Integer) pageParams.get("page");
        }
        if(pageParams.get("recPerPage")!=null){
            recPerPage = (Integer)pageParams.get("recPerPage");
        }
        int startIndex =(page-1)*recPerPage+1; //开始下标
        int endIndex = page*recPerPage; //结束下标

        if(data12.size()>0){
            String cType = "";
            String cArea = "";
            for(int i = 0;i<data12.size();i++){
                String grade = (String)((Map)(data12.get(i))).get("grade");
                cType += grade+",";
                cArea +=   (String)((Map)(data12.get(i))).get("area_name")+",";

            }
            if(cType.length()>0){
                cType = cType.substring(0,cType.length()-1);

            }

            if(cArea.length()>0){
                cArea = cArea.substring(0,cArea.length()-1);

            }


            if(cType.contains("C2")){
                cType = "C2";
            }else if(cType.contains("C3")){
                cType = "C3";
            }
            else if(cType.contains("C4")){
                cType = "C4";
            }

            if(cType.equals("C2")){
                String sql12Count = "select count(1) as total from SPC_BUILDING a  ";
                if(StringUtils.isNotBlank(search)){
                    sql12Count+=(" where build_name like '%"+search+"%'");
                }
                if(StringUtils.isNotBlank(markets)){
                    if(StringUtils.isNotBlank(search)) {
                        sql12Count += ("and ADDR2='" + markets + "' and ADDR3='" + regions + "' and grade='" + grades + "'");
                    }
                    else {
                        sql12Count += ("where ADDR2='" + markets + "' and ADDR3='" + regions + "' and grade='" + grades + "'");
                    }
                }

                StringBuilder sb = new StringBuilder();

                sb.append("select build_id,build_code,build_name,region,ADDR6,rownm,sss_region_name,region_name " );
                sb.append("from (select a.build_id,a.build_code,a.build_name,a.region,a.ADDR6 as ADDR6,rownum as rownm,a.ADDR2 as sss_region_name,a.ADDR3 as region_name,a.grade as grade" );
                sb.append(" from SPC_BUILDING a ");
                if(StringUtils.isNotBlank(search)){
                    sb.append("where build_name like '%"+search+"%'");
                }
                if(StringUtils.isNotBlank(markets)){
                    if(StringUtils.isNotBlank(search)){
                    sb.append("and ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");}
                    else {
                        sb.append("where ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");}
                }
                sb.append(") t ");

                sb.append("where  rownm>=? and rownm<=?");
                Map paramMap = new HashMap();
                paramMap.put("startIndex", startIndex);
                paramMap.put("endIndex", endIndex);
                String	wherePatternStr = "startIndex:startIndex,endIndex:endIndex";
                Result dataList = getCommonService().commonQueryListBySql(sb.toString(), paramMap, wherePatternStr);

                List data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

                //总记录数
                Map<String, Object> paramMap_obd = new LinkedHashMap<String, Object>();
                String wherePatternStr_obd = "";

                int total = getCommonService().commonQueryTotalBySql( sql12Count,paramMap_obd, wherePatternStr_obd);
                pager.setPage(page);
                pager.setRecPerPage(recPerPage);
                pager.setRecTotal(total);
                pageResult.setPager(pager);
                pageResult.setData(data1);
                String jsonStr = JSONObject.fromObject(pageResult).toString();
                return jsonStr;
            }

            if(cType.equals("C3")){
                String replaceAll=null;
                if(cArea.contains(",")) {
                    replaceAll = cArea.replaceAll(",", "','");
                }
                else {
                    replaceAll = cArea;
                }
                String sql13Count = "select count(1) as total from SPC_BUILDING a where  region in ('"+replaceAll+"')";
                if(StringUtils.isNotBlank(search)){
                    sql13Count+=(" and build_name like '%"+search+"%'");
                }
                if(StringUtils.isNotBlank(markets)){
                    sql13Count+=("and ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");
                }

                StringBuilder sb = new StringBuilder();
                sb.append("select build_id,build_code,build_name,region,ADDR6,rownm,sss_region_name,region_name " );
                sb.append("from (select a.build_id,a.build_code,a.build_name,a.region,a.ADDR6 as ADDR6,rownum as rownm,a.ADDR2 as sss_region_name,a.ADDR3 as region_name ,a.grade as grade" );
                sb.append(" from SPC_BUILDING a ");
                sb.append(" where  region in ('"+replaceAll+"') " );
                if(StringUtils.isNotBlank(search)){
                    sb.append("and build_name like '%"+search+"%'");
                }
                if(StringUtils.isNotBlank(markets)){
                        sb.append("and ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");
                }
                sb.append(") t ");
                sb.append("where  rownm>=? and rownm<=?");
                Map paramMap = new HashMap();
                paramMap.put("startIndex", startIndex);
                paramMap.put("endIndex", endIndex);
                String	wherePatternStr = "startIndex:startIndex,endIndex:endIndex";
                Result dataList = getCommonService().commonQueryListBySql(sb.toString(), paramMap, wherePatternStr);

                List data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

                //总记录数
                Map<String, Object> paramMap_obd = new LinkedHashMap<String, Object>();
                String wherePatternStr_obd = "";

                int total = getCommonService().commonQueryTotalBySql( sql13Count,paramMap_obd, wherePatternStr_obd);
                pager.setPage(page);
                pager.setRecPerPage(recPerPage);
                pager.setRecTotal(total);
                pageResult.setPager(pager);
                pageResult.setData(data1);
                String jsonStr = JSONObject.fromObject(pageResult).toString();
                return jsonStr;
            }
        }




        List data1 = null;
        StringBuilder sb_total = new StringBuilder();
        sb_total.append("select count(1) as total from SPC_BUILDING a  where staff_id = ? ");
        if(StringUtils.isNotBlank(search)){
            sb_total.append("and build_name like '%"+search+"%'");
        }
        if(StringUtils.isNotBlank(markets)){
            sb_total.append("and ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select build_id,build_code,build_name,region,ADDR6,rownm,sss_region_name,region_name " );
        sb.append("from (select a.build_id,a.build_code,a.build_name,a.region,a.ADDR6 as ADDR6,rownum as rownm,a.ADDR2 as sss_region_name,a.ADDR3 as region_name,a.grade as grade " );
        sb.append(" from SPC_BUILDING a ");
        sb.append(" where a.staff_id = ?   " );
        if(StringUtils.isNotBlank(search)){
            sb.append("and build_name like '%"+search+"%'");
        }
        if(StringUtils.isNotBlank(markets)){
            sb.append("and ADDR2='"+markets+"' and ADDR3='"+regions+"' and grade='"+grades+"'");
        }
        sb.append(") t ");
        sb.append("where  rownm>=? and rownm<=?");

        String sql = sb.toString();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();


        paramMap.put("staffId", buildingId);
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        String	wherePatternStr = "staff_id:staffId,startIndex:startIndex,endIndex:endIndex";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);

        data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        //总记录数
        Map<String, Object> paramMap_obd = new LinkedHashMap<String, Object>();
        paramMap_obd.put("staffId", buildingId);
        String wherePatternStr_obd = "staff_id:staffId";

        int total = getCommonService().commonQueryTotalBySql( sb_total.toString(),paramMap_obd, wherePatternStr_obd);
        pager.setPage(page);
        pager.setRecPerPage(recPerPage);
        pager.setRecTotal(total);
        pageResult.setPager(pager);
        pageResult.setData(data1);
        String jsonStr = JSONObject.fromObject(pageResult).toString();
        return jsonStr;
    }



    @RequestMapping(value = "/client/orderInfoInit")
    @ResponseBody
    public Object orderInit(@RequestBody String data) throws Exception {
//        JSONObject jsonObject = JSONObject.fromObject(data);
//        final String staffId1 = (String) jsonObject.get("StaffId");
//        Hdf hdf = new Hdf();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(hdf.getDataSource());
//        String param2Value = (String) jdbcTemplate.execute(
//                new CallableStatementCreator() {
//                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
//                        String storedProc = "{call HNLT_GK.INF_APP_HPAGE_QUERY(?,?)}";// 调用的sql
//                        CallableStatement cs = con.prepareCall(storedProc);
//                        cs.setString(1,staffId1);// 设置输入参数的值
//                        cs.registerOutParameter(2, OracleTypes.VARCHAR);// 注册输出参数的类型
//                        System.out.println(cs);
//                        return cs;
//                    }
//                },new CallableStatementCallback() {
//                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
//                        cs.execute();
//                        System.out.println(cs.getString(2));
//                        return cs.getString(2);// 获取输出参数的值
//                    }
//
//                });
//        return param2Value;
        JSONObject jsonObject = JSONObject.fromObject(data);
        String staffId1 = (String) jsonObject.get("StaffId");
        MobileDataInstallDAOImpl i = new MobileDataInstallDAOImpl();
        String result = i.queryOrderInfo(staffId1);
        return result;
    }
    @RequestMapping(value = "/client/builDingGponDetail")
    @ResponseBody
    public Object gponDetail(@RequestBody Map<String, Object> data,
                             HttpServletRequest request, HttpServletResponse response){
        String type = (String) data.get("type");
        String code = (String) data.get("code");
        StringBuffer sb = new StringBuffer();

        sb.append("select b.OBD设备总数 as obdEqpNum,b.OBD端口数 as obdPortNumber,b.OBD端口占用数 as obdPortOccupyNumber,b.OBD端口利用率 as obdPortUtilizationRate");
        sb.append(", b.ONU设备总数 as onuEqpNum,b.ONU端口数 as onuPortNumber,b.ONU端口占用数 as onuPortOccupyNumber,b.ONU端口利用率 as onuPortUtilizationRate");
        sb.append(", b.公众宽带数 as kdBusinessNumber ,b.公众IPTV数 as iptvBusinessNumber,b.公众语音数 as voiceBusinessNumber");
        sb.append(", b.DIA业务数 as diaBusinessNumber , b.商务快线业务数 as swkx,b.双创云快线业务数 as scykx from SPC_BUILDING_TJREPORT b");
        sb.append(" where IF_10GPON =  ? and BUILD_CODE = ? ");


        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("code", code);
        String	wherePatternStr = "IF_10GPON:type,BUILD_CODE:code";
        Result dataList = getCommonService().commonQueryObjectBySql(sb.toString(), paramMap, wherePatternStr);
        return dataList;
    }



    /**
     * 资源详情
     *
     * @param data     楼宇id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/client/builDingRes")
    @ResponseBody
    public Object builDingRes(@RequestBody Map<String, Object> data,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = (String)data.get("type");

        if("onu".equals(type.toLowerCase())){
            return getOnu(data);
        }else if("obd".equals(type.toLowerCase())){
            return getObd(data);
        }
        return null;
    }

    public int queryTotal(Map paramMap,String sqlStr,String wherePatternStr){
        return getCommonService().commonQueryTotalBySql(sqlStr, paramMap,wherePatternStr);
    }


    public Object getObd(Map<String, Object> data) throws Exception{
        String buildNo = (String) data.get("code");
        String search =  (String) data.get("search");
        String type =  (String) data.get("type1");

        Map pageParams = data;
        PageResult pageResult = new PageResult();
        List data1 = null;
        StringBuilder sb_total = new StringBuilder();
        sb_total.append("select  count(*) total FROM SPC_BUILDING  a,                                    \n");
        sb_total.append("        spc_building_eqp_rela r  ,                                                  \n");
        sb_total.append("        rme_eqp               d                                                 \n");
        sb_total.append("  where  a.build_id=r.build_id                                                   \n");
        sb_total.append("   and r.delete_state='0'                                                \n");
        sb_total.append("     and r.eqp_id=d.eqp_id                                                     \n");
        sb_total.append("    and d.delete_state = '0'                                                    \n");
        sb_total.append("     and d.res_type_id = '2530'                                                    \n");
        sb_total.append("     and r.if_10gpon= ?");
        sb_total.append("    and a.build_code= ?   				   	     								 \n");
        if(StringUtils.isNotBlank(search)){
            sb_total.append(" and d.eqp_name like '%"+search+"%'");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select portInfo,rownm,eqpId,eqpName,portNum,usePortNum,installAddr,standAddr,splitRate FROM     											                   \n");
        sb.append("(select rownum as rownm,d.eqp_id as eqpId,d.eqp_name as eqpName,                \n");
        sb.append("        (SELECT COUNT(DISTINCT p.PORT_ID)                                       \n");
        sb.append("           FROM RME_PORT p                                                      \n");
        sb.append("          WHERE p.DELETE_STATE = '0'                                            \n");
        sb.append("            AND p.SUPER_RES_ID = d.EQP_ID                                       \n");
        sb.append("            AND p.POSITION <> GET_INVAILDPOSITION(d.EQP_ID,                     \n");
        sb.append("                                                  d.RES_TYPE_ID,                \n");
        sb.append("                                                  d.PROTECT_STYLE_ID,           \n");
        sb.append("                                                  d.REGION_ID)) as portNum,     \n");
        sb.append("        (SELECT COUNT(DISTINCT p.PORT_ID)                                       \n");
        sb.append("           FROM RME_PORT p                                                      \n");
        sb.append("          WHERE p.DELETE_STATE = '0'                                            \n");
        sb.append("            AND p.SUPER_RES_ID = d.EQP_ID                                       \n");
        sb.append("            AND SF_GET_TELENO_BY_RES_ID(p.PORT_ID) IS NOT NULL                  \n");
        sb.append("            AND p.POSITION <> GET_INVAILDPOSITION(d.EQP_ID,                     \n");
        sb.append("                                                  d.RES_TYPE_ID,                \n");
        sb.append("                                                  d.PROTECT_STYLE_ID,           \n");
        sb.append("                                                  d.REGION_ID)) as usePortNum,  \n");
        sb.append("                                                                                \n");
        sb.append("        d.ADDRESS as installAddr,                                               \n");
        sb.append("        sf_get_eqp_port_info1(d.eqp_id) as portInfo,                            \n");
        sb.append("        sf_get_stand_name_by_eqp_id(d.eqp_id) as standAddr,                     \n");
        sb.append("        SF_GET_DESC_CHINA(d.WORK_MODE) as splitRate                             \n");
        sb.append("   FROM SPC_BUILDING          a,                                                \n");
        sb.append("         spc_building_eqp_rela r  ,                                               \n");
        sb.append("        rme_eqp               d                                                \n");
        sb.append("  where a.build_id=r.build_id                                                    \n");
        sb.append("     and r.delete_state='0'                                                   \n");
        sb.append("     and d.delete_state = '0'                                                        \n");

        sb.append("    and r.eqp_id=d.eqp_id                                                     \n");
        sb.append("    and d.res_type_id = '2530'                                                  \n");
        sb.append("    and r.if_10gpon= ?");

        if(StringUtils.isNotBlank(search)){
            sb.append(" and d.eqp_name like '%"+search+"%'");
        }
        sb.append("    and a.build_code= ?) t  where rownm>=? and rownm<=? 				   	       \n");

        String sql = sb.toString();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();

        Pager pager = new Pager();
        int page = pager.getPage();
        int recPerPage = pager.getRecPerPage();
        if(pageParams.get("page")!=null){
            page = (Integer) pageParams.get("page");
        }
        if(pageParams.get("recPerPage")!=null){
            recPerPage = (Integer)pageParams.get("recPerPage");
        }
        int startIndex =(page-1)*recPerPage+1; //开始下标
        int endIndex = page*recPerPage; //结束下标

        paramMap.put("if10gpon", type);
        paramMap.put("buildNo", buildNo);
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        String	wherePatternStr = "if_10gpon:if10gpon,build_code:buildNo,startIndex:startIndex,endIndex:endIndex";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
        data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        //总记录数
        Map<String, Object> paramMap_obd = new LinkedHashMap<String, Object>();
        paramMap_obd.put("if10gpon", type);
        paramMap_obd.put("buildNo", buildNo);
        String wherePatternStr_obd = "if_10gpon:if10gpon,build_code:buildNo";
        int total = queryTotal(paramMap_obd, sb_total.toString(), wherePatternStr_obd);
        pager.setPage(page);
        pager.setRecPerPage(recPerPage);
        pager.setRecTotal(total);
        pageResult.setPager(pager);
        pageResult.setData(data1);

        String jsonStr = JSONObject.fromObject(pageResult).toString();
        return jsonStr;
    }

    public Object getOnu(Map<String, Object> data) throws Exception{
        String buildNo = (String) data.get("code");

        String search = (String) data.get("search");
        Map pageParams = data;  //前台分页对象
        PageResult pageResult = new PageResult();
        List data1 = null;

        StringBuilder sb_total = new StringBuilder();
        sb_total.append("select  count(*) total FROM SPC_BUILDING  a,                              \n");
        sb_total.append("        exc_station_direction b,                                          \n");
        sb_total.append("        spc_exc_eqp_rela      c,                                          \n");
        sb_total.append("        rme_eqp               d                                           \n");
        sb_total.append("  where a.build_name = b.dire_name                                        \n");
        sb_total.append("    and b.delete_state = '0'                                              \n");
        sb_total.append("    and c.delete_state = '0'                                              \n");
        sb_total.append("    and d.delete_state = '0'                                              \n");
        sb_total.append("    and b.dire_id=c.dire_id                                               \n");
        sb_total.append("    and c.eqp_id=d.eqp_id                                                 \n");
        sb_total.append("    and d.res_type_id = '2511'                                            \n");
        sb_total.append("    and a.build_code= ?   				   	     						   \n");
        if(StringUtils.isNotBlank(search)){
            sb_total.append(" and d.eqp_name like '%"+search+"%'");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" select direction,portInfo,rownm,eqpId,eqpName,portNum,usePortNum,oltCode,ponCode,installAddr,standAddr,manageRange,voipType,resType,factory,ipAddr,voipAddr, macAddr,eqpSeq FROM           												    \n");
        sb.append(" (select rownum as rownm,d.eqp_id as eqpId, d.eqp_name as eqpName,           \n");
        sb.append("        (SELECT COUNT(DISTINCT p.PORT_ID)                                    \n");
        sb.append("           FROM RME_PORT p                                                   \n");
        sb.append("          WHERE p.DELETE_STATE = '0'                                         \n");
        sb.append("            AND p.SUPER_RES_ID = d.EQP_ID                                    \n");
        sb.append("            AND p.POSITION <> GET_INVAILDPOSITION(d.EQP_ID,                  \n");
        sb.append("                                                  d.RES_TYPE_ID,             \n");
        sb.append("                                                  d.PROTECT_STYLE_ID,        \n");
        sb.append("                                                  d.REGION_ID)) as portNum,  \n");
        sb.append("        (SELECT COUNT(DISTINCT p.PORT_ID)                                    \n");
        sb.append("           FROM RME_PORT p                                                   \n");
        sb.append("          WHERE p.DELETE_STATE = '0'                                         \n");
        sb.append("            AND p.SUPER_RES_ID = d.EQP_ID                                    \n");
        sb.append("            AND SF_GET_TELENO_BY_RES_ID(p.PORT_ID) IS NOT NULL               \n");
        sb.append("                                                                             \n");
        sb.append("            AND p.POSITION <> GET_INVAILDPOSITION(d.EQP_ID,                  \n");
        sb.append("                                                 d.RES_TYPE_ID,              \n");
        sb.append("                                                 d.PROTECT_STYLE_ID,         \n");
        sb.append("                                                 d.REGION_ID)) as usePortNum,\n");
        sb.append("        Sf_Get_ONUOBD_Up_OLT_No(d.eqp_id) as oltCode,                        \n");
        sb.append("        Sf_Get_ONUOBD_Up_Pon_No(d.eqp_id) as ponCode,                        \n");
        sb.append("        d.ADDRESS AS installAddr,                                            \n");
        sb.append("        b.dire_name as direction,                                            \n");
        sb.append("        sf_get_eqp_port_info1(d.eqp_id) as portInfo,                         \n");
        sb.append("        sf_get_stand_name_by_eqp_id(d.eqp_id) as standAddr,                  \n");
        sb.append("        d.MANAGE_RANGE as manageRange,                                       \n");
        sb.append("        SF_GET_DESC_CHINA(d.Voip_Type) as voipType,                          \n");
        sb.append("        sf_get_res_type(d.res_type_id) as resType,                           \n");
        sb.append("        (select m.mfr                                                        \n");
        sb.append("           FROM pub_mfr m, pub_mfr_res mr                                    \n");
        sb.append("          where m.delete_state = '0'                                         \n");
        sb.append("            and mr.delete_state = '0'                                        \n");
        sb.append("            and mr.res_id = d.eqp_id                                         \n");
        sb.append("            and mr.mfr_id = m.mfr_id                                         \n");
        sb.append("            and rownum = 1) as factory,                                      \n");
        sb.append("        (select o.ip_address                                                 \n");
        sb.append("           FROM rme_out_eqp o                                                \n");
        sb.append("          where o.delete_state = '0'                                         \n");
        sb.append("            and o.eqp_id = d.eqp_id) as ipAddr,                              \n");
        sb.append("        d.voip_addr as voipAddr,                                             \n");
        sb.append("        d.manage_ipaddress as macAddr,                                       \n");
        sb.append("        d.eqp_sequence as eqpSeq                                             \n");
        sb.append("   FROM SPC_BUILDING          a,                                             \n");
        sb.append("        exc_station_direction b,                                             \n");
        sb.append("        spc_exc_eqp_rela      c,                                             \n");
        sb.append("        rme_eqp               d                                              \n");
        sb.append("  where a.build_name = b.dire_name                                           \n");
        sb.append("    and b.delete_state = '0'                                                 \n");
        sb.append("    and c.delete_state = '0'                                                 \n");
        sb.append("    and d.delete_state = '0'                                                 \n");
        sb.append("    and d.res_type_id = '2511'                                               \n");
        sb.append("     and b.dire_id=c.dire_id                                                 \n");
        sb.append("    and c.eqp_id=d.eqp_id                                                    \n");
        if(StringUtils.isNotBlank(search)){
            sb.append(" and d.eqp_name like '%"+search+"%'");
        }
        sb.append("    and a.build_code= ?) t where rownm>=? and rownm<=?                       \n");
        String sql = sb.toString();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();

        Pager pager = new Pager();
        int page = pager.getPage();
        int recPerPage = pager.getRecPerPage();
        if(pageParams.get("page")!=null){
            page = (Integer) pageParams.get("page");
        }
        if(pageParams.get("recPerPage")!=null){
            recPerPage = (Integer)pageParams.get("recPerPage");
        }
        int startIndex =(page-1)*recPerPage+1; //开始下标
        int endIndex = page*recPerPage; //结束下标

        paramMap.put("buildNo", buildNo);
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        String	wherePatternStr = "build_code:buildNo,startIndex:startIndex,endIndex:endIndex";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
        data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        //总记录数
        Map<String, Object> paramMap_onu = new LinkedHashMap<String, Object>();
        paramMap_onu.put("buildNo", buildNo);
        String wherePatternStr_onu = "build_code:buildNo";
        int total = queryTotal(paramMap_onu, sb_total.toString(), wherePatternStr_onu);

        pager.setPage(page);
        pager.setRecPerPage(recPerPage);
        pager.setRecTotal(total);
        pageResult.setPager(pager);
        pageResult.setData(data1);

        String jsonStr = JSONObject.fromObject(pageResult).toString();
        return jsonStr;
    }

    /**
     * 业务详情
     *
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/client/builDingBusiness")
    @ResponseBody
    public Object builDingBusiness(@RequestBody Map<String, Object> data,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        String buildNo = (String) data.get("code");
        String type = (String) data.get("type");
        Map pageParams = data;
        PageResult pageResult = new PageResult();
        List data1 = null;
        StringBuilder sb_service = new StringBuilder();
        sb_service.append("select count(*) as total from SPC_BUILDING_BUSINESS where build_code = ? and IF_10GPON = ?");

        Map<String, Object> paramMap_server = new LinkedHashMap<String, Object>();

        StringBuilder sb = new StringBuilder();
        sb.append("select userContact,dk,swzxUpDownRate,ywkdTime,isKd,prodName,buildCode,customName,tel,address,oltIp,oltName,pon,eqpName,rownm,portState,portPos from \n" );
        sb.append("(select build_code as buildCode,custom_name as customName,tele_no as tel,address,olt_ip as oltIp,olt_name as oltName,pon,eqp_name as eqpName,rownum as rownm ,port_pos as portPos,port_state as portState,prod_name as prodName,是否商务宽带 as isKd \n");
        sb.append(" , 商务专线上下行速率 as swzxUpDownRate , 带宽 as dk,客户联系方式 as userContact,业务开通日期 as  ywkdTime \n");
        sb.append("from SPC_BUILDING_BUSINESS where build_code = ? and IF_10GPON = ? ) t where rownm>=? and rownm<=? ");
        String sql = sb.toString();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();

        Pager pager = new Pager();
        int page = pager.getPage();
        int recPerPage = pager.getRecPerPage();
        if(pageParams.get("page")!=null){
            page = (Integer) pageParams.get("page");
        }
        if(pageParams.get("recPerPage")!=null){
            recPerPage = (Integer)pageParams.get("recPerPage");
        }
        int startIndex =(page-1)*recPerPage+1; //开始下标
        int endIndex = page*recPerPage; //结束下标

        paramMap.put("buildNo", buildNo);
        paramMap.put("type", type);
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        String	wherePatternStr = "build_code:buildNo,IF_10GPON:type,startIndex:startIndex,endIndex:endIndex";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
        data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        String wherePatternStr_server = "build_code:buildNo,IF_10GPON:type";
        paramMap_server.put("buildNo", buildNo);
        paramMap_server.put("type", type);
        int total = queryTotal(paramMap_server, sb_service.toString(), wherePatternStr_server);

        pager.setPage(page);
        pager.setRecPerPage(recPerPage);
        pager.setRecTotal(total);
        pageResult.setPager(pager);
        pageResult.setData(data1);

        String jsonStr = JSONObject.fromObject(pageResult).toString();
        return jsonStr;
    }



    @RequestMapping(value = "/client/builDingObdDetail")
    @ResponseBody
    public Object builDingObdDetail(@RequestBody Map<String, Object> data,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String eqp_id = (String) data.get("eqpId");
        Map pageParams = data;
        PageResult pageResult = new PageResult();
        List data1 = null;
        StringBuilder sb_service = new StringBuilder();
        sb_service.append("SELECT count(1) as total FROM v_app_port where 1=1");
        sb_service.append(" and eqp_id = ? ");
        Map<String, Object> paramMap_server = new LinkedHashMap<String, Object>();

        StringBuilder sb = new StringBuilder();
        sb.append("select B.PORT_ID as PORT_ID,B.EQP_ID as EQP_ID,B.PORT_NAME as PORT_NAME,B.STATE_NAME as STATE_NAME,");
        sb.append(" B.OPR_STATE_ID as OPR_STATE_ID,B.TELE_NO as TELE_NO,B.INSTALL_ADDR as INSTALL_ADDR,B.USERNAME as USERNAME ,rownm");
        sb.append(" FROM (SELECT A.*, ROWNUM as rownm FROM (");
        sb.append(" SELECT * FROM v_app_port where 1=1 ");
        sb.append(" and eqp_id = ? ) A ) B where rownm>=? and rownm<=?");

        String sql = sb.toString();
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();

        Pager pager = new Pager();
        int page = pager.getPage();
        int recPerPage = pager.getRecPerPage();
        if(pageParams.get("page")!=null){
            page = (Integer) pageParams.get("page");
        }
        if(pageParams.get("recPerPage")!=null){
            recPerPage = (Integer)pageParams.get("recPerPage");
        }
        int startIndex =(page-1)*recPerPage+1; //开始下标
        int endIndex = page*recPerPage; //结束下标

        paramMap.put("eqpId", eqp_id);
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        String	wherePatternStr = "eqp_id:eqpId,startIndex:startIndex,endIndex:endIndex";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
        data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        String wherePatternStr_server = "eqp_id:eqpId";
        paramMap_server.put("eqpId", eqp_id);
        int total = queryTotal(paramMap_server, sb_service.toString(), wherePatternStr_server);

        pager.setPage(page);
        pager.setRecPerPage(recPerPage);
        pager.setRecTotal(total);
        pageResult.setPager(pager);
        pageResult.setData(data1);

        String jsonStr = JSONObject.fromObject(pageResult).toString();
        return jsonStr;
    }


    @RequestMapping(value = "/client/building/report")
    @ResponseBody
    public Object builDingBusinessReport(@RequestBody Map<String, Object> data,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception{

        String buildNo = (String) data.get("code");
        String type = (String) data.get("type");
        String sql = "select  sum(case " +
                "                              when b.prod_name = ('DIA业务PON') then " +
                "                               1 " +
                "                              else " +
                "                               0 " +
                "                            end) as DIA业务数, " +
                "                        sum(case " +
                "                              when b.prod_type <> '110001' and b.lan_id is null and " +
                "                                   b.prod_name not in " +
                "                                   ('双创云快线', 'DIA业务PON', '商务快线') then " +
                "                               1 " +
                "                              else " +
                "                               0 " +
                "                            end) as 公众宽带数, " +
                "                        sum(case " +
                "                              when b.lan_id is not null then " +
                "                               1 " +
                "                              else " +
                "                               0 " +
                "                            end) as 公众IPTV数, " +
                "                        sum(case " +
                "                              when b.prod_type = '110001' then " +
                "                               1 " +
                "                              else  " +
                "                               0 " +
                "                            end) as 公众语音数" +
                "  ,sum(case " +
                "                              when b.是否商务宽带= ('是') then " +
                "                               1 " +
                "                              else " +
                "                               0 " +
                "                            end) as 商务专线业务数 FROM spc_building_business b where IF_10GPON = ? and build_code = ? ";


        String str1 = " and 业务开通日期 BETWEEN trunc(sysdate,'D')+1  and  trunc(sysdate,'D')+7 ";
        String str2 = " and 业务开通日期 BETWEEN trunc(sysdate,'mm')  and  last_day(sysdate) ";



        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("buildCode", buildNo);
        List data1 = null;
        List data2 = null;
        List list = new ArrayList();
        String	wherePatternStr = "IF_10GPON:type,build_code:buildCode";
        Result dataList = getCommonService().commonQueryObjectBySql(sql+str1, paramMap, wherePatternStr);
        Result dataList1= getCommonService().commonQueryObjectBySql(sql+str2, paramMap, wherePatternStr);

        //data1 = (List)((Map)dataList.getResultData().get("data_info")).get("dataList");

        //data2 = (List)((Map)dataList1.getResultData().get("data_info")).get("dataList");
        list.add(dataList);
        list.add(dataList1);
        return list;
    }

    /**
     * 用户详情
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/client/staffIdRole")
    @ResponseBody
    public Object builDingUser(@RequestBody Map<String, Object> data,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String buildNo = (String) data.get("code");
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("staffId", buildNo);
        StringBuilder sb = new StringBuilder();
        sb.append("select priv_gk as gk,priv_bi as bi from MOBILE_STAFF_RIGHT where staff_id=?");
        String sql = sb.toString();
        String	wherePatternStr = "staff_id:staffId";
        Result dataList = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);
        return dataList;
    }



    /**
     * 用户详情
     * @param data
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/client/staffIdOltRole")
    @ResponseBody
    public Object builDingOLTUser(@RequestBody Map<String, Object> data,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String buildNo = (String) data.get("code");
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("staffId", buildNo);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT T.priv_code as pc  FROM VW_STAFF_PRIV T \n" +
                "WHERE t.staff_id = ?\n" +
                "and T.priv_code in ( 'APP_OLT_APPLY','APP_OLT_APPROVAL' )");
        String sql = sb.toString();
        String	wherePatternStr = "staff_id:staffId";
        Result dataList = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
        return dataList;
    }




    @RequestMapping(value = "/client/dacpUserClmfpmtQuery")
    @ResponseBody
    public Object dacpUserClmfpmtQuery(@RequestBody Map<String, Object> data,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        String msisdn = (String) data.get("msisdn" );
        String orderId = (String) data.get("orderId" );
        String staffId = (String) data.get("staffId" );
        String workId = (String) data.get("workId");
        if(orderId==null||orderId.equals("null")){
            String sql  = "SELECT base_order_id as ORDER_ID FROM wo_work_order where id = ?";
            String where = "id:id";
            Map map = new HashMap();

            map.put("id",workId);
            Result result = getCommonService().commonQueryObjectBySql(sql, map, where);
            Map map1=  (Map)result.getResultData().get("data_info");
            orderId = ((BigDecimal)map1.get("ORDER_ID")).toString();
        }

        RestTemplate rest = new RestTemplate();
//      String url = "http://192.168.101.175:11010/yiyiyun/dacp/user/clmfpmt/query?msisdn="+msisdn;
//      String list = rest.postForObject(url, null, String.class);
        JSONObject  param = new JSONObject();
        param.put("number",msisdn);
        String url = "http://192.168.101.175:8001/policy/queryPolicy";
      //  String url = "http://127.0.0.1:8101/policy/queryPolicy";
        String list =  HttpUtil.sendPostBoc(url, param.toString());

        String sql = "insert into APP_SDCL_LOG(STAFF_ID,ORDER_ID,RESULT,ACCOUNT,ID,DATE_TIME) values(?,?,?,?,APP_SDCL_LOG_SEQ.nextval,sysdate)";
        Map map = new LinkedHashMap();
        map.put("staffId",staffId);
        map.put("orderId",orderId);
        map.put("result",list);
        map.put("account",msisdn);

        Result result = getCommonService().commonInsertObjectBySql(sql, map);


        return list;
    }





}
