package com.ztesoft.mobile.etl.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zterc.uos.exception.RequiredException;
import com.ztesoft.mobile.common.exception.ParamException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.etl.vo.DSDataColumnDto;
import com.ztesoft.mobile.etl.vo.DSDataDto;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetParamDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetTypeDto;
import com.ztesoft.mobile.etl.vo.EtlRuleDto;

public class DataSourceHelper {

	private final static Logger logger = Logger.getLogger(DataSourceHelper.class);
	
	/**
	 * 通过数据源配置中的值获取数据库链接
	 * 
	 * @param dsDto
	 * @return
	 */
	public final static Connection getConne(DataSourceDto dsDto) {
		// TODO 这里应该实现成连接池的形式,目前为了实现功能,暂时采用JDBC的方式
		Connection conn = null;
		try {
			logger.debug("数据库驱动类: " + dsDto.getDbDriver());
			Driver driver = (Driver) (Class.forName(dsDto.getDbDriver())
					.newInstance());
			
			DriverManager.registerDriver(driver); // 注册 JDBC 驱动程序
			conn = DriverManager.getConnection(dsDto.getDbUrl(),dsDto.getUserName(), dsDto.getPassword());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("通过配置获取："+dsDto.getDsName()+"数据源连接失败！详细信息:" + ex.getMessage());
		}

		return conn;
	}

	/**
	 * 通过数据源和数据集获取数据
	 * @param dsDto
	 * @param paramList
	 * @param sql
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	public final static DSDataDto findData(DataSourceDto dsDto,EtlRuleDto etlRuleDto,EtlDatasetParamDto[] paramArr,int rowNum){
		DSDataDto dsDataDto = new DSDataDto();
		//SQL语句
		if(EtlDatasetTypeDto.DSTYPE_SQL.equals(etlRuleDto.getDatasetType())){
			dsDataDto = findData(dsDto,etlRuleDto.getSourceDataset(),paramArr,rowNum);
		//存储过程
		}else {
			dsDataDto.setErrorMessage("暂时只支持SQL类型的数据集....");
		}
		return dsDataDto;
	}

	/**
	 * 通过SQL获取数据
	 * @param dsDto
	 * @param exeSql
	 * @param paramArr
	 * @param rowNum
	 * @return
	 * @throws RequiredException
	 * @throws SQLException
	 */
	public final static DSDataDto findData(DataSourceDto dsDto,String exeSql,EtlDatasetParamDto[] paramArr,int rowNum){
		DSDataDto dsDataDto = new DSDataDto();
		if(!StringUtil.isNull(exeSql)){
			Connection conn = getConne(dsDto);
			Statement st = null;
			ResultSet rs = null;
			DSDataColumnDto[] columns = null;
			if(conn != null){
				StringBuffer dataXml = new StringBuffer("<items>");
				try{
					//组合SQL语句
					String sql = getQuerySql(exeSql,getParamValue(paramArr),rowNum);
					logger.debug("sql===========>: " + sql);
					st = conn.createStatement();
					rs = st.executeQuery(sql);
					columns = getColumnNameAndType(rs);
					if(columns != null && columns.length > 0){
						while(rs.next()){
							dataXml.append("<item ");
							for(int i=0;i<columns.length;i++){
								DSDataColumnDto columnDto = columns[i];
								String columnValue = getColumnValue(rs,columnDto);
								logger.debug("columnValue====>" + columnValue);
								dataXml.append( columnDto.getColumnName() + "=\"" + columnValue +"\" ");
							}
							dataXml.append("></item>");
						}						
					}
					dataXml.append("</items>");
					dsDataDto.setDataXml(dataXml.toString());
					dsDataDto.setFieldName(getFieldNameFromColumn(columns));
					logger.debug("dataXml："+dataXml.toString());
				}catch(ParamException pe){
					pe.printStackTrace();
					dsDataDto.setErrorMessage(pe.getMessage());
				} catch (SQLException e) {
					e.printStackTrace();
					dsDataDto.setErrorMessage("查询数据时出错,请确认数据集SQL语句是否正确!");
				}finally{
					try{
						if(rs != null )	rs.close();
						
						if(st != null )	st.close();
						
						if(conn != null )conn.close();
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
			}else{
				dsDataDto.setErrorMessage("获取数据源链接时出错,请确认数据源配置是否正确!");
			}
		}
		
		return dsDataDto;
		
	}
	
	private final static DSDataColumnDto[] getColumnNameAndType(ResultSet rs ) throws SQLException{
		DSDataColumnDto[] columns = null;
		if(rs != null){
			ResultSetMetaData md = rs.getMetaData();
			columns = new DSDataColumnDto[md.getColumnCount()];
			
			for(int i=1,j=0;i<=md.getColumnCount();i++,j++){
				DSDataColumnDto dto = new DSDataColumnDto();
				dto.setColumnName(md.getColumnName(i));
				dto.setColumnType(md.getColumnType(i));
				//logger.debug(md.getColumnName(i) + "的类型为======: " + md.getColumnType(i));
				columns[j] = dto;
			}
		}
		return columns;
	}
	
	private final static String[] getFieldNameFromColumn(DSDataColumnDto[] columns){
		String[] fieldNames = null;
		if(columns != null && columns.length > 0){
			fieldNames = new String[columns.length];
			for(int i=0;i<columns.length;i++){
				DSDataColumnDto columnDto = columns[i];
				fieldNames[i] = columnDto.getColumnName();
			}
		}
		
		return fieldNames;
	}
	
	private final static String getColumnValue(ResultSet rs,DSDataColumnDto columnDto) throws SQLException{
		String columnValue = "";
		switch(columnDto.getColumnType()){
			case Types.NULL:
				columnValue ="";
				break;
			case Types.BIT:
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.BIGINT:
				columnValue = String.valueOf(rs.getInt(columnDto.getColumnName()));
				break;		
			case Types.FLOAT:
				columnValue = String.valueOf(rs.getFloat(columnDto.getColumnName()));
				break;
			case Types.NUMERIC:
				long l = rs.getLong(columnDto.getColumnName());
				logger.debug(columnDto.getColumnName() + " LONG VALUE == " + l);
				columnValue = String.valueOf(rs.getLong((columnDto.getColumnName())));
				break;				
			case Types.DOUBLE:
			case Types.DECIMAL:
				columnValue = String.valueOf(rs.getDouble((columnDto.getColumnName())));
				break;
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
			case Types.BLOB:
				columnValue = StringUtil.null2String(rs.getString(columnDto.getColumnName()));
				break;
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				columnValue = StringUtil.date2LongString(rs.getDate(columnDto.getColumnName()));
				break;				
			default :
				columnValue = rs.getObject(columnDto.getColumnName()).toString();
		}
		
		
		return columnValue;
	}
	
	private final static String getQuerySql(String quyerSql,Map paramMap,int rowNum)throws ParamException{
		String seleSql = "";
		String whereSql = "";
		String orderSql = "";
		if(quyerSql != null && !"".equals(quyerSql.trim())){
			quyerSql = quyerSql.toUpperCase();		//转换成大写
			//包含WHERE 语句
			if(quyerSql.indexOf("WHERE") > 0){
				seleSql = quyerSql.substring(0,quyerSql.indexOf("WHERE"));
				whereSql = quyerSql.substring(quyerSql.indexOf("WHERE"));
			}else{
				seleSql = quyerSql;
			}

			if(quyerSql.indexOf("ORDER BY") > 0){
				if(!"".equals(whereSql)){
					whereSql = quyerSql.substring(quyerSql.indexOf("WHERE"),quyerSql.indexOf("ORDER"));
				}else{
					//没有WHERE条件
					seleSql = quyerSql.substring(0,quyerSql.indexOf("ORDER"));
				}
				orderSql = quyerSql.substring(quyerSql.indexOf("ORDER"));
			}
			
			//包含ORDER BY 语句
			if(!"".equals(whereSql)){
				 Map paramNameMap = getAllParam(whereSql);
				 if(paramNameMap != null && !paramNameMap.isEmpty()){
					 Iterator iter = paramNameMap.keySet().iterator();
					 while(iter.hasNext()){
						 String paramName = (String)iter.next();
						 logger.debug("PARAM : " +(String)paramMap.get(paramName));
						 if(paramMap.containsKey(paramName)){
							 whereSql = whereSql.replaceAll(paramName, "("+(String)paramMap.get(paramName)+")");
						 }else{
							 throw new ParamException("参数列表中未包含【" + paramName +"】的参数");
						 }
					 }
				 }
				 
				 whereSql += " AND ROWNUM <=" + rowNum;
			}else{
				whereSql = " WHERE ROWNUM <=" + rowNum;
			}
			
		   return seleSql + " " + whereSql + " " + orderSql;
		}
		
		return null;
	}
	
	private final static Map getParamValue(EtlDatasetParamDto[] paramArr){
		Map paramMap = new HashMap();
		if(paramArr != null && paramArr.length > 0){
			for(int i =0;i<paramArr.length;i++){
				EtlDatasetParamDto dto = (EtlDatasetParamDto)paramArr[i];
				//logger.debug( "QuerySql========> :" + dto.getQuerySql());
				paramMap.put(":"+dto.getParamCode(),StringUtil.decode(dto.getQuerySql()));
				logger.debug( "==== :" + dto.getParamCode() + "  :  " +  StringUtil.decode(dto.getQuerySql()));
			}
		}
		return paramMap;
	}
	
	private final static Map getAllParam(String sql){
		String[] paramStr = sql.split("=");
		Map parNameMap = new HashMap();
		//因为第一个是where前面的语句,没有参数
		for(int i=1;i<paramStr.length;i++){
			String param = StringUtil.leftTrim(paramStr[i]);
			int begin = param.indexOf(":");
			int end = param.indexOf(" ");
			if(begin > -1){
				String paramName;
				if(end < 0){
					paramName = param.substring(begin);
				}else{
					paramName = param.substring(begin,end);
				}
				logger.debug("paramName: " + paramName);
				parNameMap.put(paramName,null);
			}

		}
		
		return parNameMap;
	}
	

	
	public static void main(String[] args){
		String sql = "SELECT * FROM sq_data_source WHERE DS_TYPE =:DS_TYPE";//"select * from a where id = :id and ee = :ee and cc =:cc order by ee";
		Map paramMap = new HashMap();
		paramMap.put("DS_TYPE", "SELECT '000' FROM DUAL");
		//paramMap.put("EE", "AAAAA");
		//paramMap.put("CC", "EEEEE");
		try {
			String sss = getQuerySql(sql,paramMap,10);
			System.out.println(sss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(leftTrim("   ad adf ad "));
	}

}
