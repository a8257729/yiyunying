package com.ztesoft.mobile.v2.dao.interf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleTypes;
import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.DateHelper;

public class MobileFaultReportDAOImpl  extends BaseDAOImpl implements MobileFaultReportDAO {

	public Map selReportInfo(Map paramMap) throws DataAccessException    {
        String sqlStr = " SELECT  A.ID  AS id , A.REPORT_NAME  AS reportName, A.REPORT_HEAD AS reportHead ,A.REPORT_CODE   AS reportCode, A.REPORT_SQL  AS reportSql ,A.CREATE_DATE AS createDate, A.REPORT_COMMENT AS reportComment, A.SHOW_FORMULA AS showFormula  FROM iomdev.FAULT_REPORT@mobiletoiomdev.regress.rdbms.dev.us.oracle.com  A WHERE A.STATE =1 AND A.REPORT_CODE = '"
                        + MapUtils.getString(paramMap,"reportCode")+"'";
        System.out.println("new chenlin selReportInfo..SQL..:  " + sqlStr);
        return dynamicQueryObjectByMap(sqlStr, paramMap, null);      
       
    }

    public List selReportTitle(Map paramMap) throws DataAccessException{
        String sql = " SELECT A.REPORT_HEAD as reportHead , A.REPORT_SQL AS reportSql , B.TITLE_NAME as titleName , "
                     +" B.REPORT_ROW as reportRow, B.REPORT_COLUMN as reportColumn, B.ROW_NUM as rowsNum, "
                     +" B.COLUMN_NUM as columnNum, B.REPORT_WIDTH  as reportWidth, B.TITLE_TYPE as titleType,"
                     +" B.ID as id, B.PROPERTY_NAME as propertyName"
                     +" FROM iomdev.FAULT_REPORT@mobiletoiomdev.regress.rdbms.dev.us.oracle.com  A "
                     +" JOIN iomdev.FAULT_REPORT_TITLE@mobiletoiomdev.regress.rdbms.dev.us.oracle.com B ON A.ID = B.REPORT_ID"
                     +" WHERE A.STATE =1 AND B.STATE =1  AND A.REPORT_CODE = '"
                     + MapUtils.getString(paramMap,"reportCode")+"'"
                     +" ORDER BY  B.REPORT_ROW , B.REPORT_COLUMN ";

        System.out.println("new chenlin selReportTitle..SQL..:  " + sql);

        return dynamicQueryListByMap(sql, paramMap, null);
    }

     public List selReportParam(Map paramMap)throws DataAccessException{
         String sql = " SELECT B.PARAMETER AS parameter , B.PARAM_TYPE  AS paramType,PARAM_INDEX AS paramIndex "
                      +" FROM iomdev.FAULT_REPORT@mobiletoiomdev.regress.rdbms.dev.us.oracle.com A "
                      +" JOIN iomdev.FAULT_REPORT_PARAMETER@mobiletoiomdev.regress.rdbms.dev.us.oracle.com B ON  A.ID = B.REPORT_ID"
                      +" WHERE B.STATE =  1 AND A.STATE =1  AND A.REPORT_CODE = '"
                      + MapUtils.getString(paramMap,"reportCode")+"'"
                      +" ORDER BY  B.PARAM_TYPE  ";
         System.out.println("new chenlin selReportParam..SQL..:  " + sql);
         return dynamicQueryListByMap(sql, paramMap, null);
     }

     public List selReportField(Map paramMap)throws DataAccessException{
	    String sql = " SELECT B.FIELD_CODE AS fieldCode , B.FIELD_NAME  AS fieldName,B.FIELD_INDEX AS fieldIndex ,B.FUNCTION_LINK AS functionLink ,B.LINK_FIELD AS linkField,B.SPACE_FLAG AS spaceFlag"
	                 +" FROM iomdev.FAULT_REPORT@mobiletoiomdev.regress.rdbms.dev.us.oracle.com A "
	                 +" JOIN iomdev.FAULT_REPORT_FIELD@mobiletoiomdev.regress.rdbms.dev.us.oracle.com B ON  A.ID = B.REPORT_ID"
	                 +" WHERE B.STATE =  1 AND A.STATE =1  AND A.REPORT_CODE = '"
	                 + MapUtils.getString(paramMap,"reportCode")+"'"
	                 +" ORDER BY  B.FIELD_INDEX  ";
	    System.out.println("new chenlin selReportField..SQL..:  " + sql);
	    return dynamicQueryListByMap(sql, paramMap, null);
     }

     public Map selReportDimensionInfo(Map paramMap) throws DataAccessException {
    	 String sqlStr = "   select  BEGIN_COLUMN   as beginColumn,  COLUMN_NUM     as columnNum,  FIRST_TITLE    as firstTile,  ROW_STYLE      as rowStyle,  COL_STYLE      as colStyle  "
    		 +" from  iomdev.fault_report_dimension@mobiletoiomdev.regress.rdbms.dev.us.oracle.com a "
    		 +" join iomdev.fault_report@mobiletoiomdev.regress.rdbms.dev.us.oracle.com b on a.report_id = b.id "
    		 +" where a.state = 1 and b.state =1  AND B.REPORT_CODE = '"
             + MapUtils.getString(paramMap,"reportCode")+"'";
    	 return dynamicQueryObjectByMap(sqlStr, paramMap, null);
     }
     
	public List selProduct() throws DataAccessException {
	    String sql = "SELECT  ID AS id , NAME  AS name FROM iomdev.pm_product@mobiletoiomdev.regress.rdbms.dev.us.oracle.com    WHERE  state='10A' AND  type_id='001' AND ID <>'2120864'";
	    return dynamicQueryListByMap(sql, null, null);
	}
    
	
	
	
    public List selReportData(Map paramMap)throws DataAccessException{
        List paramList = (List)MapUtils.getObject(paramMap,"paramList");
        List fieldList = (List)MapUtils.getObject(paramMap,"fieldList");
        String reportSql = MapUtils.getString(paramMap,"reportSql");
        List dataList = new ArrayList();
        int totalRecords = 0; // 所有页记录总数
        Map retMap =  new HashMap();
        String callStr = "{call " + reportSql + "(";
         for   (int i=0; i<paramList.size()+1;i++)     {
                        callStr+=" ?,";
         }
         callStr = callStr.substring(0,callStr.length() -1);
         callStr+=")}"  ;
         System.out.println("callStr...." +callStr);
         Connection conn = null;
         CallableStatement proc = null;
         ResultSet rs = null;
         try {
            conn = getConnection();
            proc = conn.prepareCall(callStr);
            int i =0;
            for( i=0; i<paramList.size();i++){
                Map para = (Map) paramList.get(i);
                String paramType = MapUtils.getString(para,"paramType");//参数类型
                Integer paramIndex = MapUtils.getInteger(para,"paramIndex");//序列
                String parameter = MapUtils.getString(para,"parameter");//参数名
                System.out.println(parameter+":"+MapUtils.getString(paramMap,parameter)+",");
                if(paramType.equals("1")){
                    proc.setString(paramIndex, MapUtils.getString(paramMap,parameter));
                }else  if(paramType.equals("2")){
                    if(MapUtils.getObject(paramMap,parameter)!=null&&!MapUtils.getString(paramMap,parameter).equals("")
                            &&!MapUtils.getString(paramMap,parameter).equals("null")){
                        proc.setLong(paramIndex,
                                     MapUtils.getLong(paramMap, parameter));
                    }else
                       proc.setLong(paramIndex.intValue(),new Long(-1));
                }else  if(paramType.equals("3")){
                    Object  param = MapUtils.getObject(paramMap,parameter);
                    if (param != null){
                        Date paramDate =null;
                        if(param instanceof String){
                            paramDate = DateHelper.parse((String)param);
                        }else{
                            paramDate = (Date) param;
                        }
                        proc.setTimestamp(paramIndex.intValue(),new java.sql.Timestamp(paramDate.getTime()));

                    }else{
                        proc.setDate(paramIndex.intValue(),null);
                    }
                }
            }           
            proc.registerOutParameter(++i, OracleTypes.CURSOR);
            proc.execute();           
            rs = (ResultSet) proc.getObject(i);               
            dataList = getFaultReportList(rs,reportSql);
            System.out.println("new chenlin dataList is " +  dataList);
        } catch (Exception ex) {
            ex.printStackTrace();
            /** @todo Handle this exception */
        } finally {
            cleanUp(conn, proc, null, rs);
        }
        return dataList;
    }       
	public List selReportFormula(Map paramMap ) throws DataAccessException {
		String sql = " SELECT B.TITLE_ID AS titleID , B.CONTENT AS content,A.ID AS reportId,"
				+ " B.TITLE_GROUP_IDS AS titleGroupIds FROM iomdev.FAULT_REPORT@mobiletoiomdev.regress.rdbms.dev.us.oracle.com A "
				+ " JOIN iomdev.FAULT_REPORT_FORMULA@mobiletoiomdev.regress.rdbms.dev.us.oracle.com B ON  A.ID = B.REPORT_ID"
				+ " JOIN iomdev.FAULT_REPORT_TITLE@mobiletoiomdev.regress.rdbms.dev.us.oracle.com C ON A.ID = C.REPORT_ID AND B.TITLE_ID=C.ID"
				+ " WHERE B.STATE =  1 AND A.STATE =1  AND A.REPORT_CODE = '"
				+ MapUtils.getString(paramMap, "reportCode")
				+ "'"
				+ " ORDER BY C.REPORT_ROW,C.REPORT_COLUMN ";
		System.out.println("selReportFormula..SQL..:  " + sql);
		return dynamicQueryListByMap(sql, paramMap, null);
	}

    private static Object nvl(Object obj){
    	if(obj==null) 	obj=" ";
    	return obj;
    }
    
    private List getFaultReportList(ResultSet rs, String reportSql) throws SQLException {
    	 List dataList = new ArrayList();
    	 if (rs != null ){
    		 while (rs.next()){
    			 Map paramMap = new HashMap();
    			 if (reportSql.equals("PROC_ORDER_TIMELY_RATE_LIST")){
    				 //装机单及时率
    				 paramMap.put("AREA_NAME", rs.getString("AREA_NAME"));
        			 paramMap.put("SUM",rs.getString("SUM"));
        			 paramMap.put("TIME", rs.getString("TIME"));
    			 }
    			 if (reportSql.equals("PROC_FAULTPROMPT_RATE")){
    				 //故障单及时率
    				 paramMap.put("FAULTAREANAME", rs.getString("FAULTAREANAME"));
        			 paramMap.put("ORDERAMOUNT",rs.getString("ORDERAMOUNT"));
        			 paramMap.put("ALARMNUM", rs.getString("ALARMNUM"));
        			 paramMap.put("ALARMRATE", rs.getString("ALARMRATE"));
    			 }
    			 if (reportSql.equals("PROC_FAULTBOK_REPORT")){
    				 //履约，预约率
    				 paramMap.put("AREANAME", rs.getString("AREANAME"));
    				 paramMap.put("ORDERAMOUNT", rs.getString("ORDERAMOUNT"));
    				 paramMap.put("BOKNUM", rs.getString("BOKNUM"));
    				 paramMap.put("ONTIMENUM", rs.getString("ONTIMENUM"));
    			}
    			
    			 dataList.add(paramMap);
    		 }
    	 }
    	return dataList;
    }
    

}
