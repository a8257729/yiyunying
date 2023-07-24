package com.ztesoft.mobile.etl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.zterc.uos.util.DateUtilities;
import com.ztesoft.mobile.etl.util.TimeUtil;
import com.ztesoft.mobile.etl.vo.ScheduleDto;
import com.ztesoft.mobile.etl.vo.ScheduleInstDto;
import com.ztesoft.mobile.etl.vo.SchedulePageDto;

public class ScheduleDAOImpl extends AbstractDAOImpl implements ScheduleDAO{

	private final Logger logger = Logger.getLogger(ScheduleDAOImpl.class);

	/**
	 * 分页查询方案调度数据
	 */
	public SchedulePageDto queryDataSourceList(ScheduleDto scheduleDto,int startIndex, int stepSize) throws RequiredException,SystemException, SQLException {

		StringBuffer sbf = new StringBuffer();
		sbf.append(" select a.SCHEDULE_ID,a.SCHEDULE_NAME,a.EXEC_START_DATE,a.EXEC_RATE,a.STATE,a.CREATE_DATE,a.STATE_DATE,a.OPER_MAN_ID,a.OPER_MAN_NAME,a.MEMO,a.STATE as InstState from SQ_ETL_SCHEDULE a  ");
		sbf.append(" where a.STATE='10A' and a.SCHEDULE_NAME like '%"+scheduleDto.getScheduleName()+"%' ");
		sbf.append(" order by a.CREATE_DATE desc ");
		System.out.println("sbf.toString()  "+sbf.toString());
		logger.info("sbf.toString()"+sbf.toString());
		return queryDataSourceEX_(sbf, scheduleDto, startIndex, stepSize);
	}

	public SchedulePageDto queryDataSourceEX_(StringBuffer sbf,
			ScheduleDto scheduleDto, int startIndex, int stepSize)throws RequiredException, SystemException, SQLException {

		String sqlStr = sbf.toString();
		System.out.println(sqlStr);
		SchedulePageDto schedulePageDto = new SchedulePageDto();
		int totalRecords = this.getDataSourceListCount(sqlStr, scheduleDto);
		if (stepSize < 0) {
			stepSize = totalRecords - startIndex;
		}
		// 总页�?


		if (totalRecords == 0) {
			schedulePageDto.setTotalSize(0);
			return schedulePageDto;
		} else {
			schedulePageDto.setTotalSize((int) Math
					.ceil((double) totalRecords / stepSize));
		}
		// �?后一页页面记录数可能小于步长
		int currentStepSize = 0;

		if (startIndex >= schedulePageDto.getTotalSize()) {
			startIndex = schedulePageDto.getTotalSize();
			currentStepSize = totalRecords - (startIndex - 1) * stepSize;
		} else {
			currentStepSize = stepSize;
		}

		schedulePageDto.setPageIndex(startIndex); // 当前�?


		schedulePageDto.setPageSize(stepSize); // 步长
		schedulePageDto.setTotalRecords(totalRecords); // 记录总数

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loop = 1;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(DAOUtils.populateQuerySQL(sqlStr,
					(startIndex - 1) * stepSize, currentStepSize, conn));

			rs = ps.executeQuery();
			Collection col = getDataSourcesFromRs(rs);

			schedulePageDto.setScheduleDto((ScheduleDto[]) col.toArray(new ScheduleDto[] {}));
			com.zterc.uos.helpers.DAOUtils.dropDataPageTempTable(conn);
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return schedulePageDto;
	}

	public int getDataSourceListCount(String sql, ScheduleDto scheduleDto) throws DAOSysException, SQLException {
		String sqlStrCount = com.zterc.uos.helpers.DAOUtils
		.preparedCalculateSQL(sql);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int loop = 1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sqlStrCount);

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
		ScheduleDto scheduleDto = null;
		while (rs.next()) {
			scheduleDto = new ScheduleDto();
			scheduleDto.setScheduleName(rs.getString("SCHEDULE_NAME"));
			scheduleDto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			scheduleDto.setExecRate(new Long(rs.getLong("EXEC_RATE")));
			scheduleDto.setExecStartDate(rs.getTimestamp("EXEC_START_DATE"));
			scheduleDto.setMemo(rs.getString("MEMO"));
			scheduleDto.setOperManId(new Long(rs.getLong("OPER_MAN_ID")));
			scheduleDto.setOperManName(rs.getString("OPER_MAN_NAME"));
			scheduleDto.setScheduleId(new Long(rs.getLong("SCHEDULE_ID")));
			scheduleDto.setState(rs.getString("STATE"));
			scheduleDto.setStateDate(rs.getTimestamp("STATE_DATE"));
			scheduleDto.setInstState(rs.getString("InstState"));
			col.add(scheduleDto);
		}
		return col;
	}


	/**
	 * 验证用户名是否存�?


	 */
	public int checkScheduleName(String scheduleName) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as count from SQ_ETL_SCHEDULE b where b.STATE = '10A' AND b.SCHEDULE_NAME = '"+scheduleName+"'");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} finally {
			cleanUp(conn, null, st, null);
		}
		return count;
	}
	/**
	 * 通用方法，验证数据在表中是否有存�?


	 * @param columnName        列名
	 * @param values            �?


	 * @param tableName         表名
	 * @param type              这个类型用于判断是字符串还是数字�?


	 * @return
	 * @throws SQLException
	 */
	public int checkIsEist(String columnName,String values,String tableName,String type) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as count from "+tableName+" b where "+columnName+" in ("+values+")");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} finally {
			cleanUp(conn, null, st, null);
		}
		return count;
	}
	
	/**
	 * 删除方案数据
	 */
	public void deleteDataSource(String objId, String manId, String manName)throws SQLException, UOSException {
		logger.info("executing deleteDataSource for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE  set STATE='10P',STATE_DATE=sysdate,OPER_MAN_ID=?,OPER_MAN_NAME=? WHERE SCHEDULE_ID=?";
		System.out.println("sqlStr  "+Long.parseLong(objId)+" vv "+sqlStr);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			
			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
		
			ps.setLong(1, Long.parseLong(manId));
			ps.setString(2, manName);
			ps.setLong(3, Long.parseLong(objId));
			ps.executeUpdate();
		}
		finally {
			cleanUp(conn, null, ps, null);
		}
	}

	public int checkIsRunning(long scheduleId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(ei.etl_inst_id) as count");
		sql.append("  from SQ_ETL_SCHEDULE sc");
		sql.append("  join SQ_ETL_SCHEDULE_INST si on sc.schedule_id = si.schedule_id");
		sql.append("                              and sc.state <> '10P'");
		sql.append("                              and si.state <> '10P'");
		sql.append("  join SQ_ETL_INST ei on si.sch_inst_id = ei.sch_inst_id");
		sql.append(" where ei.state = '10I'");
		sql.append(" and sc.schedule_id = ?");


		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, scheduleId);
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} finally {
			cleanUp(conn, null, ps, null);
		}
		return count;
	}
	
	/**
	 * 添加方案数据
	 */
	public Long addDataSource(ScheduleDto scheduleDto) throws SQLException,UOSException {
		String sqlStr = "INSERT INTO  SQ_ETL_SCHEDULE(SCHEDULE_ID,SCHEDULE_NAME,EXEC_START_DATE,EXEC_RATE,STATE,CREATE_DATE,STATE_DATE,OPER_MAN_ID,OPER_MAN_NAME,MEMO,SCHE_CATALOG_ID,PACKAGEDEFINEID)  VALUES(?,?,?,?,?,?,sysdate,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Long objId = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;
			objId = new Long(SequenceGenerator.getSequenceValue("SQ_ETL_SCHEDULE", 9));
			ps.setLong(dbloop++, objId.longValue());
			ps.setString(dbloop++, scheduleDto.getScheduleName());
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(scheduleDto.getExecStartDate()));
			ps.setLong(dbloop++, scheduleDto.getExecRate().longValue());
			ps.setString(dbloop++, scheduleDto.getState());
			
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(scheduleDto.getCreateDate()));
			
			ps.setLong(dbloop++, scheduleDto.getOperManId().longValue());
			ps.setString(dbloop++, scheduleDto.getOperManName());
			ps.setString(dbloop++, scheduleDto.getMemo());
			ps.setLong(dbloop++, scheduleDto.getScheCatalogId().longValue());
			ps.setString(dbloop++, scheduleDto.getPackageDefineId());

			ps.executeUpdate();
			return objId;
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}

	
	/**
	 * 添加调度方案实例
	 */
	public Long addScheduleInst(ScheduleInstDto scheduleInstDto) throws SQLException,UOSException {
		String sqlStr = "INSERT INTO  SQ_ETL_SCHEDULE_INST(SCH_INST_ID,SCHEDULE_ID," +
				"ETL_START_DATE,ETL_NEXT_DATE,STATE,CREATE_DATE,STATE_DATE,MEMO,OPER_MAN,OPER_MAN_ID,OPER_MAN_TYPE)  VALUES(?,?,?,?,?,sysdate,sysdate,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Long objId = null;
		try {
			//update by fengyang 2011-02-23 
			String next = TimeUtil.format(scheduleInstDto.getEtlStartDate(),"yyyy-MM-dd HH:mm:ss");
			String nextDate = TimeUtil.dateAddOrDele(next, scheduleInstDto.getExecRate()); 
			
			//String nextDate = TimeUtil.dateAddOrDele(scheduleInstDto.getEtlStartDate().toString(), scheduleInstDto.getExecRate());  
            //end
            System.out.println("nextDate--->  "+nextDate);
			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;
			objId = new Long(SequenceGenerator.getSequenceValue("SQ_ETL_SCHEDULE_INST", 9));
			ps.setLong(dbloop++, objId.longValue());
			ps.setLong(dbloop++, scheduleInstDto.getScheduleId().longValue()); 
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(scheduleInstDto.getEtlStartDate()));
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(TimeUtil.strToDate(nextDate)));
			ps.setString(dbloop++, scheduleInstDto.getState());
			
			
			ps.setString(dbloop++, scheduleInstDto.getMemo());
			ps.setString(dbloop++, scheduleInstDto.getOperMan());
			ps.setString(dbloop++, scheduleInstDto.getOperManId());
			ps.setString(dbloop++, scheduleInstDto.getOperManType());

			ps.executeUpdate();
			
			updateScheduleState(scheduleInstDto.getScheduleId().longValue(), "10S", 
					scheduleInstDto.getOperManId(), scheduleInstDto.getOperMan());
			return objId;
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}
	
	/**
	 * 删除方案数据
	 */
	public void updateScheduleState(long scheduleId, String state,
			String manId, String manName) throws SQLException, UOSException {
		logger.info("executing updateScheduleState for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE  set STATE=?,STATE_DATE=sysdate,OPER_MAN_ID=?,OPER_MAN_NAME=? WHERE SCHEDULE_ID=?";
		System.out.println("updateScheduleState:" + sqlStr);
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int loop = 1;
			ps.setString(loop++, state);
			ps.setLong(loop++, Long.parseLong(manId));
			ps.setString(loop++, manName);
			ps.setLong(loop++, scheduleId);
			ps.executeUpdate();
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}
	/**
	 * 更新方案数据
	 */
	public void updateDataSource(ScheduleDto scheduleDto) throws SQLException,UOSException {
		logger.info("executing addDataSource for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE set SCHEDULE_NAME=?,EXEC_START_DATE=?,EXEC_RATE=?,STATE =?,CREATE_DATE=?,STATE_DATE=sysdate,OPER_MAN_ID=?, OPER_MAN_NAME=?,MEMO=?,SCHE_CATALOG_ID=?,PACKAGEDEFINEID=? where SCHEDULE_ID=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;
			ps.setString(dbloop++, scheduleDto.getScheduleName());
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(scheduleDto.getExecStartDate()));
			ps.setLong(dbloop++, scheduleDto.getExecRate().longValue());
			ps.setString(dbloop++, scheduleDto.getState());
			
			ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(scheduleDto.getCreateDate()));
			

			ps.setLong(dbloop++, scheduleDto.getOperManId().longValue());
			ps.setString(dbloop++, scheduleDto.getOperManName());
			ps.setString(dbloop++, scheduleDto.getMemo());
			ps.setLong(dbloop++, scheduleDto.getScheCatalogId().longValue());
			ps.setString(dbloop++, scheduleDto.getPackageDefineId());
			
			ps.setLong(dbloop++, scheduleDto.getScheduleId().longValue());
			ps.executeUpdate();

		} finally {
			cleanUp(conn, null, ps, null);
		}

	}


}
