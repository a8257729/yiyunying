package com.ztesoft.mobile.etl.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.client.SequenceGenerator;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.zterc.uos.helpers.DAOSysException;
import com.zterc.uos.helpers.DAOUtils;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.DataSourcePageDto;

public class DataSourceDAOImpl extends AbstractDAOImpl implements DataSourceDAO {
	private final Logger logger = Logger.getLogger(DataSourceDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.ztesoft.sqm.etl.dao.DataSourceDAO#countDsByName(java.lang.String)
	 */
	public int countDsByName(String dsName) throws DAOSysException,
			SQLException {
		String sql = "select count(*) from SQ_DATA_SOURCE d where d.state = ? and upper(d.ds_name) = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loop = 1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(loop++, "10A");
			ps.setString(loop++, dsName.toUpperCase());

			rs = ps.executeQuery();
			rs.next();
			int totalRecords = rs.getInt(1);
			rs.close();
			return totalRecords;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}
	
	public DataSourcePageDto queryDataSourceList(DataSourceDto dataSourceDto,
			int startIndex, int stepSize) throws RequiredException,
			SystemException, SQLException {
		// TODO Auto-generated method stub
		StringBuffer sbf = new StringBuffer("select * from SQ_DATA_SOURCE ");
		
		if (dataSourceDto.getDsName() != null
				&& !"".equals(dataSourceDto.getDsName())) {
			sbf.append(" WHERE DS_NAME LIKE ?");
		}else{
			if (dataSourceDto.getState() != null
					&& !"".equals(dataSourceDto.getState())) {
				sbf.append(" WHERE State = '"+dataSourceDto.getState()+"' ");
			}			
		}
		//add by chen.yuesheng
		if (dataSourceDto.getDsId() != null) {
			if(sbf.toString().indexOf("WHERE")!=-1){
				sbf.append(" and DS_ID=?");
			}else{
				sbf.append(" where DS_ID=?");
			}
		}
		sbf.append(" order by state ,STATE_DATE desc ");
		return queryDataSourceEX_(sbf, dataSourceDto, startIndex, stepSize);
	}

	public DataSourcePageDto queryDataSourceEX_(StringBuffer sbf,
			DataSourceDto dataSourceDto, int startIndex, int stepSize)
			throws RequiredException, SystemException, SQLException {

		String sqlStr = sbf.toString();
        System.out.println(sqlStr);
		DataSourcePageDto dataSourcePageDto = new DataSourcePageDto();
		int totalRecords = this.getDataSourceListCount(sqlStr, dataSourceDto);
		if (stepSize < 0) {
			stepSize = totalRecords - startIndex;
		}
		// ÒòÂÒÂëÒÆ³ý¸Ã×¢ÊÍ
		if (totalRecords == 0) {
			dataSourcePageDto.setTotalSize(0);
			return dataSourcePageDto;
		} else {
			dataSourcePageDto.setTotalSize((int) Math
					.ceil((double) totalRecords / stepSize));
		}
		// ÒòÂÒÂëÒÆ³ý¸Ã×¢ÊÍ
		int currentStepSize = 0;

		if (startIndex >= dataSourcePageDto.getTotalSize()) {
			startIndex = dataSourcePageDto.getTotalSize();
			currentStepSize = totalRecords - (startIndex - 1) * stepSize;
		} else {
			currentStepSize = stepSize;
		}

		dataSourcePageDto.setPageIndex(startIndex); // ÒòÂÒÂëÒÆ³ý¸Ã×¢ÊÍ
		dataSourcePageDto.setPageSize(stepSize); // ÒòÂÒÂëÒÆ³ý¸Ã×¢ÊÍ
		dataSourcePageDto.setTotalRecords(totalRecords); // ÒòÂÒÂëÒÆ³ý¸Ã×¢ÊÍ

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loop = 1;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(DAOUtils.populateQuerySQL(sqlStr,
					(startIndex - 1) * stepSize, currentStepSize, conn));
			if (!"".equals(dataSourceDto.getDsName())
					&& dataSourceDto.getDsName() != null) {
				ps.setString(loop, "%"+ dataSourceDto.getDsName() + "%");
			}
			//add by chen.yuesheng
			if (dataSourceDto.getDsId() != null) {
				ps.setLong(loop, dataSourceDto.getDsId().longValue());
			}
			rs = ps.executeQuery();
			Collection col = getDataSourcesFromRs(rs);

			dataSourcePageDto.setDataSourceDto((DataSourceDto[]) col
					.toArray(new DataSourceDto[] {}));
			com.zterc.uos.helpers.DAOUtils.dropDataPageTempTable(conn);
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return dataSourcePageDto;
	}

	public int getDataSourceListCount(String sql, DataSourceDto dataSourceDto)
			throws DAOSysException, SQLException {
		String sqlStrCount = com.zterc.uos.helpers.DAOUtils
				.preparedCalculateSQL(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loop = 1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sqlStrCount);
			if (!"".equals(dataSourceDto.getDsName())
					&& dataSourceDto.getDsName() != null) {
				ps.setString(loop, "%"+ dataSourceDto.getDsName() + "%");
			}
			//add by chen.yuesheng
			if (dataSourceDto.getDsId() != null) {
				ps.setLong(loop, dataSourceDto.getDsId().longValue());
			}
			rs = ps.executeQuery();
			rs.next();
			int totalRecords = rs.getInt(1);
			rs.close();
			return totalRecords;
		} finally {
			cleanUp(conn, null, ps, rs);
		}
	}

	private Collection getDataSourcesFromRs(ResultSet rs) throws SQLException {
		Collection col = new ArrayList();
		DataSourceDto dataSourceDto = null;
		while (rs.next()) {
			dataSourceDto = new DataSourceDto();
			
			dataSourceDto.setDbDriver(rs.getString("Db_Driver"));
			dataSourceDto.setDbUrl(rs.getString("Db_Url"));
			dataSourceDto.setDsId(new Long(rs.getLong("Ds_Id")));
			dataSourceDto.setDsName(rs.getString("Ds_Name"));
			dataSourceDto.setDsType(rs.getString("Ds_Type"));
			dataSourceDto.setState(rs.getString("State"));
			dataSourceDto.setDbDriver(rs.getString("Db_Driver"));
			dataSourceDto.setDbUrl(rs.getString("Db_Url"));
			dataSourceDto.setUserName(rs.getString("User_Name"));
			dataSourceDto.setPassword(rs.getString("Password"));
			dataSourceDto.setInitNum(new Long(rs.getLong("Init_Num")));
			dataSourceDto.setIncNum(new Long(rs.getLong("Inc_Num")));
			dataSourceDto.setMaxNum(new Long(rs.getLong("Max_Num")));
			dataSourceDto.setFtpIp(rs.getString("Ftp_Ip"));
			dataSourceDto.setSerCop(rs.getString("SER_COP"));
			dataSourceDto.setCreateDate(rs.getTimestamp("Create_Date"));
			dataSourceDto.setStateDate(rs.getTimestamp("STATE_DATE"));
			dataSourceDto.setMemo(rs.getString("MEMO"));
			dataSourceDto.setOperManId(new Long(rs.getLong("OPER_MAN_ID")));
			dataSourceDto.setOperManName(rs.getString("OPER_MAN_NAME"));
			col.add(dataSourceDto);
		}
		return col;
	}

	public Long addDataSource(DataSourceDto dataSourceDto) throws SQLException,
			UOSException {
		logger.info("executing addDataSource for etl");

		String sqlStr = "INSERT INTO  SQ_DATA_SOURCE(DS_ID,DS_NAME,DS_TYPE,STATE,DB_DRIVER ,DB_URL,USER_NAME,PASSWORD, INIT_NUM"
				+ ",INC_NUM,MAX_NUM, FTP_IP, SER_COP, CREATE_DATE ,STATE_DATE ,MEMO ,OPER_MAN_ID,OPER_MAN_NAME,DB_TYPE) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?,?,?)";

		Connection conn = null;
		PreparedStatement ps = null;
		Long objId = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;
			objId = new Long(SequenceGenerator.getSequenceValue(
					"SQ_DATA_SOURCE", 9));
			ps.setLong(dbloop++, objId.longValue());
			ps.setString(dbloop++, dataSourceDto.getDsName());
			ps.setString(dbloop++, dataSourceDto.getDsType());
		
			ps.setString(dbloop++, dataSourceDto.getState());
			ps.setString(dbloop++, dataSourceDto.getDbDriver());
			ps.setString(dbloop++, dataSourceDto.getDbUrl());
			
			ps.setString(dbloop++, dataSourceDto.getUserName());
			ps.setString(dbloop++, dataSourceDto.getPassword());
			ps.setLong(dbloop++, dataSourceDto.getInitNum().longValue());
			ps.setLong(dbloop++, dataSourceDto.getIncNum().longValue());
			ps.setLong(dbloop++, dataSourceDto.getMaxNum().longValue());
			ps.setString(dbloop++, dataSourceDto.getFtpIp());
			ps.setString(dbloop++, dataSourceDto.getSerCop());			
			
			ps.setString(dbloop++, dataSourceDto.getMemo());
			ps.setLong(dbloop++, dataSourceDto.getOperManId().longValue());
			ps.setString(dbloop++, dataSourceDto.getOperManName());
			ps.setString(dbloop++, dataSourceDto.getDbType());
			

			
           
			ps.executeUpdate();
			
			//add by feng.yang at ningbo 2011.7.4
			String dbType = dataSourceDto.getDbType();
			if ("000".equals(dbType)) {
				createDblink(conn, objId);
			}
			
			return objId;
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}
	
	// add by feng.yang at ningbo 2011.7.4
	public void createDblink(Connection conn, long dataSourceId)
			throws SQLException {
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall("{ ? = call common.CREATE_DBLINK (?) }");
			int loop = 1;

			cs.registerOutParameter(loop++, Types.VARCHAR);

			cs.setLong(loop++, dataSourceId);

			cs.execute();
			
			System.out.println(cs.getInt(1));
		} finally {
			cleanUp(null, cs, null, null);
		}
	}
	
	public void deleteDblink(Connection conn, long dataSourceId)
			throws SQLException {
		CallableStatement cs = null;
		try {
			cs = conn.prepareCall("{ ? = call common.DEL_DBLINK (?) }");
			int loop = 1;

			cs.registerOutParameter(loop++, Types.VARCHAR);

			cs.setLong(loop++, dataSourceId);

			cs.execute();
			
			System.out.println(cs.getInt(1));
		} finally {
			cleanUp(null, cs, null, null);
		}
	}

	public void deleteDataSource(Long objId,Long manId,String manName) throws SQLException, UOSException {
		logger.info("executing deleteDataSource for etl");

        String sqlStr = "update SQ_DATA_SOURCE  set state='10P',STATE_DATE=sysdate,OPER_MAN_ID=?,OPER_MAN_NAME=? WHERE DS_ID=?  ";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = getConnection();
            
            // add by feng.yang at ningbo 2011.7.4
			deleteDblink(conn, objId);
            
            
            ps = conn.prepareStatement(sqlStr);
         
            ps.setLong(1, manId.longValue());
            ps.setString(2, manName);
            ps.setLong(3, objId.longValue());
          

            ps.executeUpdate();
        }
        finally {
            cleanUp(conn, null, ps, null);
        }

	}

	public void updateDataSource(DataSourceDto dataSourceDto)
			throws SQLException, UOSException {
		logger.info("executing addDataSource for etl");

		String sqlStr = "update SQ_DATA_SOURCE set DS_NAME=?,DS_TYPE=?,STATE=?,DB_DRIVER =?,DB_URL=?,USER_NAME=?,PASSWORD=?, INIT_NUM=?"
				+ ",INC_NUM=?,MAX_NUM=?, FTP_IP=?, SER_COP=?, STATE_DATE=sysdate ,MEMO=? ,OPER_MAN_ID=?,OPER_MAN_NAME=? ,DB_TYPE=? where DS_ID=?";
				

		Connection conn = null;
		PreparedStatement ps = null;
		
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;
			
			
			ps.setString(dbloop++, dataSourceDto.getDsName());
			ps.setString(dbloop++, dataSourceDto.getDsType());
		
			ps.setString(dbloop++, dataSourceDto.getState());
			ps.setString(dbloop++, dataSourceDto.getDbDriver());
			ps.setString(dbloop++, dataSourceDto.getDbUrl());
			
			ps.setString(dbloop++, dataSourceDto.getUserName());
			ps.setString(dbloop++, dataSourceDto.getPassword());
			ps.setLong(dbloop++, dataSourceDto.getInitNum().longValue());
			ps.setLong(dbloop++, dataSourceDto.getIncNum().longValue());
			ps.setLong(dbloop++, dataSourceDto.getMaxNum().longValue());
			ps.setString(dbloop++, dataSourceDto.getFtpIp());
			ps.setString(dbloop++, dataSourceDto.getSerCop());			
			
			
			ps.setString(dbloop++, dataSourceDto.getMemo());
			ps.setLong(dbloop++, dataSourceDto.getOperManId().longValue());
			ps.setString(dbloop++, dataSourceDto.getOperManName());
			
			ps.setString(dbloop++, dataSourceDto.getDbType());
			
			ps.setLong(dbloop++, dataSourceDto.getDsId().longValue());
			

			

			ps.executeUpdate();
			
			//add by feng.yang at ningbo 2011.7.4
			String dbType = dataSourceDto.getDbType();
			if ("000".equals(dbType)) {
				createDblink(conn, dataSourceDto.getDsId());
			}
			
		} finally {
			cleanUp(conn, null, ps, null);
		}

	}

	public DataSourceDto[] findAllDataSource() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			String sql = "select * from SQ_DATA_SOURCE t where t.state = '10A'";
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			Collection col = getDataSourcesFromRs(rs);
			if(col != null && !col.isEmpty()){
				logger.debug("·½·¨findAllDataSource:=====>" + col.size());
				return (DataSourceDto[]) col.toArray(new DataSourceDto[] {});
			}
			
		} finally {
			cleanUp(conn, null, st, rs);
		}
		return null;
	}

	public DataSourceDto findDataSourceById(Long dsId)
			throws RequiredException, SQLException {
		if(dsId == null )
			throw new RequiredException("·½·¨findDataSourceByIdÖÐÅ×³öµÄÒì³£...");
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "select * from SQ_DATA_SOURCE t where t.state = '10A' and t.ds_id = ?";
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setLong(1, dsId.longValue());
			rs = pst.executeQuery();
			Collection col = getDataSourcesFromRs(rs);
			if(col != null && !col.isEmpty()){
				return (DataSourceDto) col.iterator().next();
			}
			
		} finally {
			cleanUp(conn, null, pst, rs);
		}
		return null;
	}
}
