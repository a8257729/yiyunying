package com.ztesoft.mobile.etl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.client.SequenceGenerator;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.zterc.uos.helpers.DAOSysException;
import com.zterc.uos.helpers.DAOUtils;
import com.zterc.uos.util.DateUtilities;
import com.ztesoft.mobile.etl.vo.ScheduleMonitorQryDto;
import com.ztesoft.mobile.etl.vo.ScheduleSequDto;
import com.ztesoft.mobile.etl.vo.ScheduleSequPageDto;

public class ScheduleSequDAOImpl extends AbstractDAOImpl implements ScheduleSequDAO{

	private final Logger logger = Logger.getLogger(ScheduleSequDAOImpl.class);

	/**
	 * 分页查询调度顺序
	 */
	public ScheduleSequPageDto queryDataSourceList(ScheduleSequDto ScheduleSequDto,int startIndex, int stepSize) 
	throws RequiredException,SystemException, SQLException {
		StringBuffer sbf = new StringBuffer();
		sbf.append(" select a.SCH_SEQU_ID,a.SCHEDULE_ID,a.ETL_RULE_ID,a.SCH_SEQU,b.ETL_RULE_NAME,b.ETL_TYPE,b.SOURCE_DS_ID,b.TARGET_DS_ID,");
		sbf.append(" a.STATE,a.CREATE_DATE,a.OPER_MAN_NAME,c.ETL_TYPE_NAME,d.ds_name as SOURCE_DS_NAME,e.ds_name AS TARGET_DS_NAME");
		sbf.append(" from SQ_ETL_SCHEDULE_SEQU a ");
		sbf.append(" join SQ_ETL_RULE b on a.etl_rule_id = b.etl_rule_id");
		sbf.append(" join SQ_ETL_TYPE c on b.etl_type = c.etl_type");
		sbf.append(" left join SQ_DATA_SOURCE d on b.source_ds_id = d.ds_id and d.state = '10A'");
		sbf.append(" left join SQ_DATA_SOURCE e on b.target_ds_id = e.ds_id and e.state = '10A'");
		sbf.append(" where a.STATE='10A' and b.STATE='10A'");
		sbf.append(" and SCHEDULE_ID ="+ScheduleSequDto.getScheduleId());
		sbf.append(" order by a.SCH_SEQU asc ");

		return queryDataSourceEX_(sbf, ScheduleSequDto, startIndex, stepSize);
	}

	public ScheduleSequPageDto queryDataSourceEX_(StringBuffer sbf,
			ScheduleSequDto ScheduleSequDto, int startIndex, int stepSize)throws RequiredException, SystemException, SQLException {

		String sqlStr = sbf.toString();
		System.out.println(sqlStr);
		ScheduleSequPageDto ScheduleSequPageDto = new ScheduleSequPageDto();
		int totalRecords = this.getDataSourceListCount(sqlStr, ScheduleSequDto);
		if (stepSize < 0) {
			stepSize = totalRecords - startIndex; 
		}
		// 总页数
		if (totalRecords == 0) {
			ScheduleSequPageDto.setTotalSize(0);
			return ScheduleSequPageDto;
		} else {
			ScheduleSequPageDto.setTotalSize((int) Math
					.ceil((double) totalRecords / stepSize));
		}
		// 最后一页页面记录数可能小于步长
		int currentStepSize = 0;

		if (startIndex >= ScheduleSequPageDto.getTotalSize()) {
			startIndex = ScheduleSequPageDto.getTotalSize();
			currentStepSize = totalRecords - (startIndex - 1) * stepSize;
		} else {
			currentStepSize = stepSize;
		}

		ScheduleSequPageDto.setPageIndex(startIndex); // 当前页
		ScheduleSequPageDto.setPageSize(stepSize); // 步长
		ScheduleSequPageDto.setTotalRecords(totalRecords); // 记录总数

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

			ScheduleSequPageDto.setScheduleSequDto((ScheduleSequDto[]) col.toArray(new ScheduleSequDto[] {}));
			com.zterc.uos.helpers.DAOUtils.dropDataPageTempTable(conn);
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return ScheduleSequPageDto;
	}

	public int getDataSourceListCount(String sql, ScheduleSequDto ScheduleSequDto) throws DAOSysException, SQLException {
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
		ScheduleSequDto scheduleSequDto = null;
		while (rs.next()) {
			scheduleSequDto = new ScheduleSequDto();

			scheduleSequDto.setSchSequId(new Long(rs.getLong("SCH_SEQU_ID")));
			scheduleSequDto.setEtlRuleId(rs.getString("ETL_RULE_ID"));
			scheduleSequDto.setSourceDsId(new Long(rs.getLong("SOURCE_DS_ID")));
			scheduleSequDto.setTargetDsId(new Long(rs.getLong("TARGET_DS_ID")));
			scheduleSequDto.setEtlType(rs.getString("ETL_TYPE"));
			scheduleSequDto.setEtlRuleName(rs.getString("ETL_RULE_NAME"));
			scheduleSequDto.setSchSequ(new Long(rs.getLong("SCH_SEQU")));
			scheduleSequDto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			scheduleSequDto.setOperManName(rs.getString("OPER_MAN_NAME"));
			scheduleSequDto.setEtlTypeName(rs.getString("ETL_TYPE_NAME"));
			scheduleSequDto.setSourceDsName(rs.getString("SOURCE_DS_NAME"));
			scheduleSequDto.setTargetDsName(rs.getString("TARGET_DS_NAME"));
			scheduleSequDto.setState(rs.getString("STATE"));
			col.add(scheduleSequDto);
		}
		return col;
	}


	/**
	 * 删除
	 */
	public void deleteDataSource(String objId, String manId, String manName)throws SQLException, UOSException {
		logger.info("executing deleteDataSource for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE_SEQU  set STATE='10P',STATE_DATE=sysdate,OPER_MAN_ID=?,OPER_MAN_NAME=? WHERE SCH_SEQU_ID=?";
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

	/**
	 * 删除
	 */
	public void deleteScheduleSeqBySchId(String SchId, String manId, String manName)throws SQLException, UOSException {
		logger.info("executing deleteDataSource for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE_SEQU  set STATE='10P',STATE_DATE=sysdate,OPER_MAN_ID=?,OPER_MAN_NAME=? WHERE SCHEDULE_ID=?";
		System.out.println("sqlStr  "+Long.parseLong(SchId)+" vv "+sqlStr);
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			Date now = new Date();
			
			ps.setLong(1, Long.parseLong(manId));
			ps.setString(2, manName);
			ps.setLong(3, Long.parseLong(SchId));
			ps.executeUpdate();
		}
		finally {
			cleanUp(conn, null, ps, null);
		}
	}
	
	/**
	 * 添加
	 */
	public Long addDataSource(ScheduleSequDto ScheduleSequDto) throws SQLException,UOSException {
		String sqlStr = "INSERT INTO  SQ_ETL_SCHEDULE_SEQU(SCH_SEQU_ID,SCHEDULE_ID,ETL_RULE_ID,STATE,CREATE_DATE,STATE_DATE,SCH_SEQU,OPER_MAN_ID,OPER_MAN_NAME)  VALUES(?,?,?,?,?,sysdate,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Long objId = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
            String etlList = ScheduleSequDto.getEtlRuleId();    
            String[] eltstr = etlList.split(",");
            for(int k=0;k<eltstr.length;k++){
            	if(checkIsEist(ScheduleSequDto.getScheduleId().longValue(),eltstr[k])==0){
            		int dbloop = 1;
            		objId = new Long(SequenceGenerator.getSequenceValue("SQ_ETL_SCHEDULE_SEQU", 9));
            		ps.setLong(dbloop++, objId.longValue());
            		ps.setLong(dbloop++, ScheduleSequDto.getScheduleId().longValue());
            		ps.setLong(dbloop++, Long.parseLong(eltstr[k]));
            		ps.setString(dbloop++, ScheduleSequDto.getState());
            		ps.setTimestamp(dbloop++, DateUtilities.dateToTimeStamp(ScheduleSequDto.getCreateDate()));

            		
            		ps.setLong(dbloop++, getMaxSchedId(ScheduleSequDto.getScheduleId().longValue()));
            		ps.setLong(dbloop++, ScheduleSequDto.getOperManId().longValue());
            		ps.setString(dbloop++, ScheduleSequDto.getOperManName());

            		ps.executeUpdate();
            	}
            }
			return objId;
		} finally {
			cleanUp(conn, null, ps, null);
		}
	}

	/**
	 * 取最大执行顺序
	 * @param scheduleId
	 * @return
	 * @throws SQLException
	 */
	public long getMaxSchedId(long scheduleId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select case when max(b.sch_sequ) is null then 0 else max(b.sch_sequ) end + 1 as schSequId from SQ_ETL_SCHEDULE_SEQU b where b.SCHEDULE_ID = "+scheduleId);
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		long schSequId = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				schSequId = rs.getLong("schSequId");
			}
		} finally {
			cleanUp(conn, null, st, null);
		}
		return schSequId;
	}
	/**
	 * 验证此规则是否已存在
	 * @param scheduleId
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	public int checkIsEist(long scheduleId ,String etlRuleId) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as count from SQ_ETL_SCHEDULE_SEQU b where STATE = '10A' AND SCHEDULE_ID = "+scheduleId +" and ETL_RULE_ID ="+etlRuleId);
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
	 * 更新，暂没实现
	 */
	public void updateDataSource(ScheduleSequDto ScheduleSequDto) throws SQLException,UOSException {
		logger.info("executing addDataSource for etl");

		String sqlStr = "update SQ_ETL_SCHEDULE set SCHEDULE_NAME=?,EXEC_START_DATE=?,EXEC_RATE=?,STATE =?,CREATE_DATE=?,STATE_DATE=?,OPER_MAN_ID=?, OPER_MAN_NAME=?,MEMO=? where SCHEDULE_ID=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			int dbloop = 1;

		} finally {
			cleanUp(conn, null, ps, null);
		}

	}


	public void upSequSch(String SequIdStr, String iSequId) throws SQLException {
		System.out.println("SequIdStr  "+SequIdStr +" iSequId "+iSequId);
		String[] sequIdArry = SequIdStr.split(",");
		int seq = 0; 
		String sequId = "";
		LinkedList rigList = new LinkedList();
		for(int i=0; i<sequIdArry.length; i++) {
			if(sequIdArry[i] != null && iSequId.equals(sequIdArry[i])) {
				seq = i;
				sequId = sequIdArry[i];
			}
			rigList.add(sequIdArry[i]);
		}

		if(seq > 0) {
			rigList.remove(seq);
			rigList.add(seq-1, sequId);
			for(int j=0; j<rigList.size(); j++) {
				String vSequId = (String)rigList.get(j);
				upSequ(vSequId, j+1);
			}
		}
	}
	public void downSequSch(String SequIdStr, String iSequId) throws SQLException {
		System.out.println("SequIdStr 3333 "+SequIdStr +" iSequId "+iSequId);
		String[] sequIdArry = SequIdStr.split(",");
		int seq = 0; 
		String sequId = "";
		LinkedList rigList = new LinkedList();
		for(int i=0; i<sequIdArry.length; i++) {
			if(sequIdArry[i] != null && iSequId.equals(sequIdArry[i])) {
				seq = i;
				sequId = sequIdArry[i];
			}
			rigList.add(sequIdArry[i]);
		}

		if(seq < sequIdArry.length-1) {
			rigList.remove(seq);
			rigList.add(seq+1, sequId);
			for(int j=0; j<rigList.size(); j++) {
				String vSequId = (String)rigList.get(j);
				upSequ(vSequId, j+1);
			}
		}
	}
	public int upSequ(String sequId, int seq) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" update SQ_ETL_SCHEDULE_SEQU set SCH_SEQU = " + seq + " where SCH_SEQU_ID = "+sequId);
		Connection conn = null;
		Statement st = null;
		int objId = 0;
		try {
			conn = getConnection();
			st = conn.createStatement();
			objId = st.executeUpdate(sql.toString());
		} finally {
			cleanUp(conn, null, st, null);
		}

		return objId;
	}
	
	/**
	 * findEtlRuleListByScheduleId
	 * @param scheduleId
	 * @return ScheduleMonitorQryDto[]
	 * 通过调度方案的标识查找该方案的所有ETL规则
	 * */
	public ScheduleMonitorQryDto[] findEtlRuleListByScheduleId(Long scheduleId) throws SQLException{
		StringBuffer sbf = new StringBuffer();
		sbf.append(" SELECT SER.ETL_RULE_NAME,SEI.STATE,SESS.SCH_SEQU,SESI.SCH_INST_ID,");
		sbf.append(" SES.SCHEDULE_ID,SESS.SCH_SEQU_ID,SER.ETL_RULE_ID,SEI.ETL_INST_ID,SER.ETL_TYPE");
		sbf.append(" FROM SQ_ETL_SCHEDULE_INST SESI JOIN SQ_ETL_SCHEDULE SES ON SESI.SCHEDULE_ID=SES.SCHEDULE_ID");
		sbf.append(" JOIN SQ_ETL_SCHEDULE_SEQU SESS ON SES.SCHEDULE_ID=SESS.SCHEDULE_ID");
		sbf.append(" JOIN SQ_ETL_RULE SER ON SESS.ETL_RULE_ID=SER.ETL_RULE_ID");
		sbf.append(" JOIN SQ_ETL_INST SEI ON SER.ETL_RULE_ID=SEI.ETL_RULE_ID AND SEI.SCH_INST_ID=SESI.SCH_INST_ID");
		sbf.append(" WHERE SESI.SCH_INST_ID =?");
		sbf.append(" ORDER BY SESS.SCH_SEQU ASC");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List resultList = new ArrayList();
		int loop = 1;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sbf.toString());

			if (scheduleId!= null) {
				ps.setLong(loop++, scheduleId.longValue());
			}
			rs = ps.executeQuery();
			while(rs.next()){
				ScheduleMonitorQryDto scheduleMonitorQryDto = new ScheduleMonitorQryDto();
				scheduleMonitorQryDto.setEtlInstId(new Long(rs.getLong("ETL_INST_ID")));
				scheduleMonitorQryDto.setEtlRuleId(new Long(rs.getLong("ETL_RULE_ID")));
				scheduleMonitorQryDto.setEtlRuleName(rs.getString("ETL_RULE_NAME"));
				scheduleMonitorQryDto.setScheduleId(new Long(rs.getLong("SCHEDULE_ID")));
				scheduleMonitorQryDto.setSchInstId(new Long(rs.getLong("SCH_INST_ID")));
				scheduleMonitorQryDto.setSchSequId(new Long(rs.getLong("SCH_SEQU_ID")));
				scheduleMonitorQryDto.setState(rs.getString("STATE"));
				scheduleMonitorQryDto.setEtlType(rs.getString("ETL_TYPE"));
				resultList.add(scheduleMonitorQryDto);
			}
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return (ScheduleMonitorQryDto[]) resultList.toArray(new ScheduleMonitorQryDto[] {});
	}
}
