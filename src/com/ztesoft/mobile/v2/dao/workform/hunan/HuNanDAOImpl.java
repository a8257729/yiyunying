package com.ztesoft.mobile.v2.dao.workform.hunan;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;

import net.sf.json.JSONObject;

import oracle.jdbc.internal.OracleTypes;
import org.apache.commons.collections.ListUtils;

public class HuNanDAOImpl extends BaseDAOImpl implements HuNanDAO {

	/**
	 * 铏忚寘鑴╃倝鑴宠劉鑴簱鑴檱鑴滆劏
	 * */
	public Map queryResourcesList(String staff_id ,String search_address,int pageIndex,int pageSize) throws DataAccessException {
		StringBuffer sq = new StringBuffer();


		sq.append(" select stand_name AS STAND_NAME,access_type AS ACCESS_TYPE from addr_access_full where super_region_id =  ");		
		sq.append(" (select f_get_region_by_staff_id('"+staff_id+"') from  dual)  ");

		if(StringUtil.isNull(search_address) == false){
			sq.append(" and STAND_NAME like '%"+search_address+"%'  ");
		}

		//        int maxNum = pageSize*pageIndex;
		//        int minNum = pageSize*(pageIndex -1);
		//        
		//        StringBuffer sq2 = new StringBuffer();
		//        sq2.append(" ( ");
		//        sq2.append(sq.toString());
		//        sq2.append(" ) ");


		//        resultList.put("DeviceList", deviceList);
		//        resultList.put("totalCount", totalCount);
		//        resultList.put("TotalPage", totalPage.toString());



		System.out.println("HuNanDAOImpl.queryResourcesList --> "+sq.toString());
		return populateQueryByMap(sq,pageIndex,pageSize);
	}


	public List queryStaffGirdList(String staff_id) throws DataAccessException {
		StringBuffer sb = new StringBuffer();

		sb.append("select a.org_id as ORG_ID,a.org_name as ORG_NAME from app_staff_to_org a where a.staff_id = '"+staff_id+"' order by a.org_name ");

		System.out.println(sb.toString());
		return dynamicQueryListByMap(sb.toString(), null, null);

	}

	public List queryDeviceTypeList(String staff_id) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.no as NO,a.type_name as TYPE_NAME, a.type_id as TYPE_ID from app_eqp_type a order by a.no asc");
		System.out.println(sb.toString());
		return dynamicQueryListByMap(sb.toString(), null, null);
	}

	public Map<Object, Object>  queryDeviceListByPage(String org_id,String res_type_id,String eqp_name,Integer pageSize, Integer pageIndex)  throws DataAccessException{
		Map<Object, Object>  resultList = new HashMap<Object,Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select B.EQP_ID as EQP_ID,B.EQP_NAME as EQP_NAME,B.EQP_NO as EQP_NO,B.DIRECTION as DIRECTION,B.PORT_INFO as PORT_INFO, ");
		sb.append(" B.ADDR as ADDR,B.EQP_TYPE as EQP_TYPE,B.RES_TYPE_ID as RES_TYPE_ID,B.INSTALL_ADDR as INSTALL_ADDR,");
		sb.append(" B.RANGE_ADDR as RANGE_ADDR,B.ORG_ID as ORG_ID,B.PON_ID AS PON_ID,B.PON_NAME as PON_NAME ");
		sb.append(" FROM (SELECT A.*, ROWNUM RN FROM (");
		sb.append(" SELECT * FROM v_app_eqp where 1=1 ");
		if(StringUtil.isNull(org_id) == false &&  "null".equals(org_id)== false){
			sb.append(" and org_id = " + org_id);
		}
		if(StringUtil.isNull(res_type_id) == false &&  "null".equals(res_type_id)== false ){
			sb.append(" and res_type_id = " + res_type_id);
		}
		if(StringUtil.isNull(eqp_name) == false){
			sb.append(" and eqp_name Like '%" +eqp_name+"%'");
		}

		int maxNum = pageSize*pageIndex;
		int minNum = pageSize*(pageIndex -1);
		sb.append(" ) A WHERE ROWNUM <= "+maxNum+")B WHERE RN > " + minNum);
		System.out.println(sb.toString());
		List deviceList =  dynamicQueryListByMap(sb.toString(), null, null);

		sb = new StringBuffer();
		sb.append("SELECT count(1) as count_num FROM v_app_eqp where 1=1");
		if(StringUtil.isNull(org_id) == false &&  "null".equals(org_id)== false){
			sb.append(" and org_id = " + org_id);
		}
		if(StringUtil.isNull(res_type_id) == false && "null".equals(res_type_id)== false ){
			sb.append(" and res_type_id = " + res_type_id );
		}
		if(StringUtil.isNull(eqp_name) == false){
			sb.append(" and eqp_name Like '%" +eqp_name+"%'");
		}
		List countNumList =  dynamicQueryListByMap(sb.toString(), null, null);
		Map countNumMap = (Map) countNumList.get(0);
		BigDecimal d = (BigDecimal) countNumMap.get("count_num");
		String totalCount  = d.toString();

		int totalPage = Integer.parseInt(totalCount)%pageSize;
        if(totalPage==0){
        	totalPage = Integer.parseInt(totalCount)/pageSize;
        }else{
        	totalPage=Integer.parseInt(totalCount)/pageSize+1;
        }
		if("0".equals(totalCount)||StringUtil.isNull(totalCount)){
			totalPage = 0;
		}
		resultList.put("DeviceList", deviceList);
		resultList.put("totalCount", totalCount);
		resultList.put("TotalPage", totalPage);
		return resultList;
	}

	public Map<Object, Object>  queryDevicePortListByPage(String eqp_id,String port_name,Integer pageSize, Integer pageIndex)  throws DataAccessException{
		Map<Object, Object>  resultList = new HashMap<Object,Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select B.PORT_ID as PORT_ID,B.EQP_ID as EQP_ID,B.PORT_NAME as PORT_NAME,B.STATE_NAME as STATE_NAME,");
		sb.append(" B.OPR_STATE_ID as OPR_STATE_ID,B.TELE_NO as TELE_NO,B.INSTALL_ADDR as INSTALL_ADDR,B.USERNAME as USERNAME ");
		sb.append(" FROM (SELECT A.*, ROWNUM RN FROM (");
		sb.append(" SELECT * FROM v_app_port where 1=1 ");
		if(StringUtil.isNull(eqp_id) == false &&  "null".equals(eqp_id)== false){
			sb.append(" and eqp_id = '" + eqp_id + "'");
		}
		if(StringUtil.isNull(port_name) == false){
			sb.append(" and port_name Like '%" +port_name+"%'");
		}

		int maxNum = pageSize*pageIndex;
		int minNum = pageSize*(pageIndex -1);
		sb.append(" ) A WHERE ROWNUM <= "+maxNum+")B WHERE RN > " + minNum);
		System.out.println(sb.toString());
		List devicePortList =  dynamicQueryListByMap(sb.toString(), null, null);

		sb = new StringBuffer();
		sb.append("SELECT count(1) as count_num FROM v_app_port where 1=1");
		if(StringUtil.isNull(eqp_id) == false &&  "null".equals(eqp_id)== false){
			sb.append(" and eqp_id = '" + eqp_id + "'");
		}
		if(StringUtil.isNull(port_name) == false){
			sb.append(" and port_name Like '%" +port_name+"%'");
		}
		List countNumList =  dynamicQueryListByMap(sb.toString(), null, null);
		Map countNumMap = (Map) countNumList.get(0);
		BigDecimal d = (BigDecimal) countNumMap.get("count_num");
		String totalCount  = d.toString();

		int totalPage = Integer.parseInt(totalCount)%pageSize;
        if(totalPage==0){
        	totalPage = Integer.parseInt(totalCount)/pageSize;
        }else{
        	totalPage=Integer.parseInt(totalCount)/pageSize+1;
        }
		if("0".equals(totalCount)||StringUtil.isNull(totalCount)){
			totalPage = 0;
		}
		resultList.put("DevicePortList", devicePortList);
		resultList.put("totalCount", totalCount);
		resultList.put("TotalPage", totalPage);
		return resultList;
	}

	public Map queryMaxUnBildTime(String accNbr) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT to_char(MAX(T.CREATE_DATE),'yyyy-MM-dd hh24:mi:ss') as unbind_time FROM inf_unbind_log T   ");
		sb.append(" where T.ACC_NBR = ? ");


		System.out.println("queryDeviceOperationList.sb -->>"+sb.toString() );
		Map<String,String> resultMap = new HashMap<String,String>();
		Connection conn = null;
		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ps.setString(1,accNbr);
		ResultSet rs = ps.executeQuery();
		String date = "";
		while(rs.next())
		{
			date = rs.getString("unbind_time");
			resultMap.put("unbind_time",date);
		}
		ps.close();
		conn.close();

	//	List OperationList =  dynamicQueryListByMap(sb.toString(), null, null);

		return resultMap;
	}

	public Map queryBtnYy(String staffId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT count(1) as count1 FROM vw_staff_full_priv vw   ");
		sb.append(" where vw.staff_id = '"+staffId +"' and vw.priv_code = 'APP_WFM'");


		System.out.println("queryBtnYy.sb -->>"+sb.toString() );
		Map<String,String> resultMap = new HashMap<String,String>();
		Connection conn = null;
		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		int data = 0;
		while(rs.next())
		{
			data = rs.getInt("count1");
			resultMap.put("count1",String.valueOf(data) );
		}
		ps.close();
		conn.close();

		//	List OperationList =  dynamicQueryListByMap(sb.toString(), null, null);

		return resultMap;
	}

	public Map queryBtnYySA(String staffId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT count(1) as count1 FROM vw_staff_full_priv vw   ");
		sb.append(" where vw.staff_id = '"+staffId +"' and vw.priv_code = 'APP_SA_WFM'");


		System.out.println("queryBtnYy.sb -->>"+sb.toString() );
		Map<String,String> resultMap = new HashMap<String,String>();
		Connection conn = null;
		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		int data = 0;
		while(rs.next())
		{
			data = rs.getInt("count1");
			resultMap.put("count1",String.valueOf(data) );
		}
		ps.close();
		conn.close();

		//	List OperationList =  dynamicQueryListByMap(sb.toString(), null, null);

		return resultMap;
	}

	public List queryDeviceOperationList(String state_id) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("     Select state_id as STATE_ID,oper_name as OPER_NAME,oper_id as OPER_ID  From APP_STATE_CONFIG  ");
		sb.append(" where STATE_ID = '"+state_id +"' ");
		sb.append(" Order By ORDER_NO ");

		System.out.println("queryDeviceOperationList.sb -->>"+sb.toString() );

		List OperationList =  dynamicQueryListByMap(sb.toString(), null, null);

		return OperationList;
	}

	public Map<Object, Object> executeDeviceOperation(String staff_id,String port_id,String oper_id) throws Exception{
//		create or replace function sf_app_change_port_state(staff_id in varchar2,port_id In Varchar2,state in varchar2,flag_desc out varchar2) return number 

		Map<Object, Object> resultMap =  new HashMap<Object,Object>();
		Connection conn = null;  
	    CallableStatement callStmt = null;  
		try {
			callStmt = null; 
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call sf_app_change_port_state(?,?,?,?,?,?,?,?,?)}");
			callStmt.setString(2, staff_id);
			callStmt.setString(3, port_id);
			callStmt.setString(4, oper_id);
			//			callStmt.registerOutParameter(4, Types.VARCHAR);  
			callStmt.registerOutParameter(1, Types.INTEGER);  
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR); 
			callStmt.registerOutParameter(6, OracleTypes.INTEGER);  
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);  
			
			callStmt.execute();  
			int returnNum = callStmt.getInt(1);
			String flag_desc = callStmt.getString(5);
			int new_state_id = callStmt.getInt(6);
			String new_state_name = callStmt.getString(7);
			String tele_no = callStmt.getString(8);
			String install_addr = callStmt.getString(9);
			String userName = callStmt.getString(10);

			resultMap.put("flag_desc",  flag_desc);
			resultMap.put("returnNum",  returnNum+"");
			
			resultMap.put("new_state_id",  new_state_id+"");
			resultMap.put("new_state_name",  new_state_name);
			resultMap.put("tele_no",  tele_no);
			resultMap.put("install_addr",  install_addr);
			resultMap.put("userName",  userName);
			
			return resultMap;
		} catch (Exception e) {  
			e.printStackTrace(System.out);
			resultMap.put("flag_desc",  "閹笛嗩攽鐎涙ê鍋嶆潻鍥┾柤婢惰精瑙�!");
			resultMap.put("returnNum",  "-1");
			return resultMap;
		} finally {
			if (null != callStmt) {  
				callStmt.close();  
			}  
			if (null != conn) {  
				conn.close();  
			}  
		}  

	}

	
	public Map<Object, Object>  queryTeleRollInListByPage(String org_id,String tele_nbr,Integer pageSize, Integer pageIndex)  throws DataAccessException{
		Map<Object, Object>  resultList = new HashMap<Object,Object>();
		StringBuffer sb = new StringBuffer();
        sb.append("select B.PORT_ID as PORT_ID,B.EQP_ID as EQP_ID,B.ORG_ID as ORG_ID, B.PORT_NAME as PORT_NAME,B.STATE_NAME as STATE_NAME,");
        sb.append(" B.OPR_STATE_ID as OPR_STATE_ID,B.TELE_NO as TELE_NO,B.INSTALL_ADDR as INSTALL_ADDR,B.USERNAME as USERNAME,B.DISSEQ as DISSEQ ");
        sb.append(" FROM (SELECT A.*, ROWNUM RN FROM (");
        sb.append(" SELECT * FROM v_app_eqp_num where 1=1 ");
        if(StringUtil.isNull(org_id) == false &&  "null".equals(org_id)== false){
        	sb.append(" and org_id = '" + org_id + "'");
        }
        if(StringUtil.isNull(tele_nbr) == false){
        	sb.append(" and tele_no Like '%" +tele_nbr+"%'");
        }
        
        int maxNum = pageSize*pageIndex;
        int minNum = pageSize*(pageIndex -1);
        sb.append(" ) A WHERE ROWNUM <= "+maxNum+")B WHERE RN > " + minNum);
        System.out.println(sb.toString());
        List devicePortList =  dynamicQueryListByMap(sb.toString(), null, null);
   
        sb = new StringBuffer();
        sb.append("SELECT count(1) as count_num FROM v_app_eqp_num where 1=1");
        if(StringUtil.isNull(org_id) == false &&  "null".equals(org_id)== false){
        	sb.append(" and org_id = '" + org_id + "'");
        }
        if(StringUtil.isNull(tele_nbr) == false){
        	sb.append(" and tele_no Like '%" +tele_nbr+"%'");
        }
        List countNumList =  dynamicQueryListByMap(sb.toString(), null, null);
        Map countNumMap = (Map) countNumList.get(0);
        BigDecimal d = (BigDecimal) countNumMap.get("count_num");
        String totalCount  = d.toString();
        
        Integer totalPage = Integer.valueOf(totalCount) / pageSize + 1;
        if("0".equals(totalCount)||StringUtil.isNull(totalCount)){
        	totalPage = 0;
        }
        resultList.put("TeleNoList", devicePortList);
        resultList.put("totalCount", totalCount);
        resultList.put("TotalPage", totalPage.toString());
        return resultList;
	}
	
	public Map<Object, Object> executeDeviceRollInOperation(String staff_id,String port_id,String disseq) throws Exception{
		Map<Object, Object> resultMap =  new HashMap<Object,Object>();
		Connection conn = null;  
	    CallableStatement callStmt = null;  
		try {
			callStmt = null; 
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call sf_app_port_installl_num(?,?,?,?,?,?,?,?,?)}");
			callStmt.setString(2, staff_id);
			callStmt.setString(3, port_id);
			callStmt.setString(4, disseq);
			callStmt.registerOutParameter(1, Types.INTEGER);  
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(6, OracleTypes.INTEGER);  
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);  
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);  
			
			callStmt.execute();  

			int returnNum = callStmt.getInt(1);
			String flag_desc = callStmt.getString(5);
			int new_state_id = callStmt.getInt(6);
			String new_state_name = callStmt.getString(7);
			String tele_no = callStmt.getString(8);
			String install_addr = callStmt.getString(9);
			String userName = callStmt.getString(10);

			resultMap.put("flag_desc",  flag_desc);
			resultMap.put("returnNum",  returnNum+"");
			
			resultMap.put("new_state_id",  new_state_id+"");
			resultMap.put("new_state_name",  new_state_name);
			resultMap.put("tele_no",  tele_no);
			resultMap.put("install_addr",  install_addr);
			resultMap.put("userName",  userName);
			
			return resultMap;
		} catch (Exception e) {  
			e.printStackTrace(System.out);
			resultMap.put("flag_desc",  "閹笛嗩攽鐎涙ê鍋嶆潻鍥┾柤婢惰精瑙�!");
			resultMap.put("returnNum",  "-1");
			return resultMap;
		} finally {
			if (null != callStmt) {  
				callStmt.close();  
			}  
			if (null != conn) {  
				conn.close();  
			}  
		}  
	}


	public Map<Object, Object> selPublicWorkOrderForRobByPage(String staffId,
			String username, Long jobId, Integer pageSize, Integer pageIndex)
			throws DataAccessException {
		Map<Object, Object>  resultList = new HashMap<Object,Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.workorderId as WorkOrderID,b.id as OrderID,b.order_code as OrderCode,b.service_name as ServiceName,b.acc_nbr as AccNbr, b.cust_name as CustName,");
		sb.append(" b.contact_nbr as ContactPhone,to_char( b.accept_date,'yyyy-mm-dd hh24:mi:ss') as AcceptDate,to_char( b.limit_date,'yyyy-mm-dd hh24:mi:ss') as LimitDate,b.addr_name as Address,");
		sb.append(" b.work_order_type as WorkOrderType,to_char( b.be_bok_date,'yyyy-mm-dd hh24:mi:ss') as BeBokDate,b.ext_state as ExtState,b.accept_staff_name as AcceptStaffName,");
		sb.append(" b.bok_state as BokState,to_char( b.CreateDate,'yyyy-mm-dd hh24:mi:ss') as CreateDate,b.party_id as PartId,b.rownum_ as rownum_ ");
		sb.append(" FROM (SELECT A.*, ROWNUM RN FROM (");
		sb.append(" SELECT * FROM v_app_work_pool where 1=1 ");
		if(StringUtil.isNull(staffId) == false &&  "null".equals(staffId)== false){
			sb.append(" and p_view_staffid.set_staffid("+staffId+")=" + staffId);
		}

		int maxNum = pageSize*pageIndex;
		int minNum = pageSize*(pageIndex -1);
		sb.append(" ) A WHERE ROWNUM <= "+maxNum+")B WHERE RN > " + minNum);
		System.out.println(sb.toString());
		List workOrderList =  dynamicQueryListByMap(sb.toString(), null, null);

		sb = new StringBuffer();
		sb.append("SELECT count(1) as count_num FROM v_app_work_pool where 1=1");
		if(StringUtil.isNull(staffId) == false &&  "null".equals(staffId)== false){
			sb.append(" and p_view_staffid.set_staffid("+staffId+")=" + staffId);
		}

		List countNumList =  dynamicQueryListByMap(sb.toString(), null, null);
		Map countNumMap = (Map) countNumList.get(0);
		BigDecimal d = (BigDecimal) countNumMap.get("count_num");
		String totalCount  = d.toString();

		Integer totalPage = Integer.valueOf(totalCount) / pageSize + 1;
		if("0".equals(totalCount)||StringUtil.isNull(totalCount)){
			totalPage = 0;
		}
		resultList.put("WorkOrderList", workOrderList);
		resultList.put("totalCount", totalCount);
		resultList.put("TotalPage", totalPage.toString());
		return resultList;
	}
	
	public Map<Object, Object> executeRodOrderOperation(String workOrderID,String staffId) throws Exception{
		Map<Object, Object> resultMap =  new HashMap<Object,Object>();
		Connection conn = null;  
	    CallableStatement callStmt = null;  
		try {
			callStmt = null; 
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call f_grab_order_app(?,?,?)}");
			callStmt.setString(2, staffId);
			callStmt.setString(3, workOrderID);
			callStmt.registerOutParameter(1, Types.INTEGER);  
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);  
			
			callStmt.execute();  

			int returnNum = callStmt.getInt(1);
			String flag_desc = callStmt.getString(4);
			resultMap.put("flag_desc",  flag_desc);
			resultMap.put("returnNum",  returnNum+"");
			
			return resultMap;
		} catch (Exception e) {  
			e.printStackTrace(System.out);
			resultMap.put("flag_desc",  "閸氬骸褰存径鍕倞闁挎瑨顕ら敍灞惧閸楁洖銇戠拹锟�!");
			resultMap.put("returnNum",  "-1");
			return resultMap;
		} finally {
			if (null != callStmt) {  
				callStmt.close();  
			}  
			if (null != conn) {  
				conn.close();  
			}  
		} 
	}
	
	public Map<Object, Object> executeSaveUserInfoForNotifyOrder(JSONObject json) throws Exception{
		Map<Object, Object>  resultMap = new HashMap<Object,Object>();
		if(null == json){
			resultMap.put("msg",  "閸欏倹鏆熸稉铏光敄!");
			resultMap.put("resultCode",  "0");
			return resultMap;
		}
		String username = ""+json.optString("username");
		String errorCode = ""+json.optString("errorCode");
		String appid = ""+json.optString("appid");
		String userId = ""+json.optString("userId");
		String channelId = ""+json.optString("channelId");
		String requestId = ""+json.optString("requestId");
		String os_type = ""+json.optString("os_type");

		if(StringUtil.isNull(username) == true||StringUtil.isNull(errorCode) == true
			||StringUtil.isNull(channelId) == true||StringUtil.isNull(os_type) == true){
			resultMap.put("msg",  "閸欏倹鏆熸稉铏光敄!");
			resultMap.put("resultCode",  "0");
			return resultMap;
		}

        String sql=" update staff_baiduyun_token s set s.state='0',s.state_date=sysdate where s.username = '" + username + "' and s.state='1' ";
        dynamicUpdateBySql(sql);

        sql=" insert into staff_baiduyun_token (id, username, errorCode, appid, userId, channelId, requestId, state_date, state, os_type) "
		+"values (staff_baiduyun_token_seq.nextval, '"+username+"', "+errorCode+", "+appid+", "+userId+", "+channelId+", "+requestId+", sysdate, '1', "+os_type+")";
        dynamicUpdateBySql(sql);


		resultMap.put("msg",  "閹垮秳缍旂�瑰本鍨�!");
		resultMap.put("resultCode",  "1");
		return resultMap;
	}
	
	
	public Map<Object, Object> executeSaveLoginlogOperation(JSONObject json) throws Exception{
		Map<Object, Object>  resultMap = new HashMap<Object,Object>();
		if(null == json){
			resultMap.put("msg",  "閸欏倹鏆熸稉铏光敄!");
			resultMap.put("resultCode",  "0");
			return resultMap;
		}
		String staff_id = ""+json.optString("staffId");
		String staff_name = ""+json.optString("staffName");
		String org_id = ""+json.optString("orgId");
		String org_name = ""+json.optString("orgName");

		if(StringUtil.isNull(staff_id) == true||StringUtil.isNull(staff_name) == true
			||StringUtil.isNull(org_id) == true||StringUtil.isNull(org_name) == true){
			resultMap.put("msg",  "閸欏倹鏆熸稉铏光敄!");
			resultMap.put("resultCode",  "0");
			return resultMap;
		}


        String sql=" insert into login_log (log_id, staff_id, staff_name, org_id, org_name, login_date, spare1, spare2, spare3) "
		+"values (SEQ_LOGIN_LOG.nextval, '"+staff_id+"', '"+staff_name+"', '"+org_id+"', '"+org_name+"',sysdate,'', '','')";
        
        dynamicUpdateBySql(sql);
		System.out.println("executeSaveLoginlogOperation -->>"+sql);

		resultMap.put("msg",  "閹垮秳缍旂�瑰本鍨�!");
		resultMap.put("resultCode",  "1000");
		return resultMap;
	}
	
	public Map selParams(String staffId) throws Exception {
		StringBuffer sb = new StringBuffer();
		String wherePatternStr = null;
		Map paramMap = null;
		sb.append("select STAFF_NAME,USERNAME,JOB_ID,ORG_ID,ORG_NAME from VM_STAFF_ORG  where 1=1");
		if(StringUtil.isNull(staffId) == false &&  "null".equals(staffId)== false){
		//	sb.append(" and STAFF_ID = " + staffId);zz
			sb.append(" and STAFF_ID = ?");
			wherePatternStr = "STAFF_ID:staffId";
			paramMap = new HashMap();
			paramMap.put("staffId",staffId);
		}
		System.out.println(sb.toString());
		return dynamicQueryObjectByMap(sb.toString(), paramMap, wherePatternStr);
	}

	public List<Map<String,String>> selAllParams(String staffId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select ORG_ID from VM_STAFF_ORG where STAFF_ID = ?");
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String, String>>();
		try {
			connection = getConnection();
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1,staffId);
			rs = ps.executeQuery();
			while(rs.next()){
				Map map = new HashMap();
				map.put("ORG_ID",rs.getObject("ORG_ID")==null?"":rs.getObject("ORG_ID"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询失败");
		}finally {
			cleanUp(connection,null,ps,rs);
		}
		return list;
	}
	
	public int insertMap(String staffId,String orgId,String smx,String smy,String state) throws Exception {
		int record;
		String selSql = "select count(*) from hnlt_gk.RES_ADDR_POSIXY where ORG_ID="+orgId;
		String insetSql = "INSERT INTO hnlt_gk.RES_ADDR_POSIXY(ORG_ID,STAFF_ID,SMX,SMY,STATE) VALUES("+orgId+','+staffId+','+smx+','+smy+','+"'"+state+"'"+")";
		String updateSql = "UPDATE hnlt_gk.RES_ADDR_POSIXY set ORG_ID="+orgId+','+"STAFF_ID="+staffId+','+"SMX="+smx+','+"SMY="+smy+','+"STATE="+"'"+state+"'"+" WHERE ORG_ID="+orgId;
		List num = dynamicQueryListByMap(selSql);
		 Map map = (Map)num.get(0);
		String isExit =  (String)map.get("count(*)");
		if("0".equals(isExit)){
			record = dynamicUpdateBySql(insetSql);
		}else{
			record = dynamicUpdateBySql(updateSql); 
		}
		
		/*if(StringUtil.isNull(staffId) == false &&  "null".equals(staffId)== false){
			sql+=" and STAFF_ID = " + staffId;
		}*/
		
		return record;
				
	}
	
	
	
	
}
