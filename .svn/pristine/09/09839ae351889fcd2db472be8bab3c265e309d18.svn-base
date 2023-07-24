package com.ztesoft.mobile.etl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.client.SequenceGenerator;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.zterc.uos.helpers.DAOSysException;
import com.zterc.uos.helpers.DAOUtils;
import com.ztesoft.mobile.etl.vo.EtlDatasetParamDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetTypeDto;
import com.ztesoft.mobile.etl.vo.EtlRuleDto;
import com.ztesoft.mobile.etl.vo.EtlRulePageDto;
import com.ztesoft.mobile.etl.vo.EtlTypeDto;

public class EtlRuleDAOImpl extends AbstractDAOImpl implements EtlRuleDAO {

	private final static Logger logger = Logger.getLogger(EtlRuleDAOImpl.class);

	public EtlRulePageDto queryAllEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule,
			int startIndex, int stepSize) throws RequiredException,
			SystemException, SQLException {

		EtlRulePageDto pageDto = new EtlRulePageDto();

		StringBuffer sqlStr = new StringBuffer(
				"SELECT T.*,D1.DS_NAME SOURCE_DS_NAME,D2.DS_NAME TARGET_SOURCE_NAME,")
				.append(
						"Y.ETL_TYPE_NAME,S.DATASET_TYPE_NAME,R.DS_TYPE,R.DS_TPYE_NAME")
				.append(
						" FROM SQ_ETL_RULE T LEFT JOIN SQ_DATA_SOURCE D1 ON T.SOURCE_DS_ID = D1.DS_ID")
				.append(
						" LEFT JOIN SQ_DATA_SOURCE D2 ON T.TARGET_DS_ID = D2.DS_ID")
				.append(" LEFT JOIN SQ_ETL_TYPE Y ON Y.ETL_TYPE = T.ETL_TYPE")
				.append(
						" LEFT JOIN SQ_ETL_DATASET_TYPE S ON S.DATASET_TYPE_ID = T.DATASET_TYPE")
				.append(
						" LEFT JOIN SQ_DATA_SOURCE_TYPE R ON R.DS_TYPE = D1.DS_TYPE")
				 .append(" WHERE T.STATE='10A'");
				 

		if (etlRuleDto.getEtlRuleName() != null
				&& !"".equals(etlRuleDto.getEtlRuleName().trim())) {
			sqlStr.append(" AND T.ETL_RULE_NAME LIKE '%").append(
					etlRuleDto.getEtlRuleName().trim()).append("%'");
		}
		if (etlRuleDto.getEtlType() != null
				&& !"".equals(etlRuleDto.getEtlType().trim())) {
			sqlStr.append(" AND T.ETL_TYPE = '").append(etlRuleDto.getEtlType().trim()).append("'");
		}
		
		//����Ǵӵ��ȷ�����򿪲�ѯ,��ֻ��ѯ�����Ѿ�ѡ��ETL���ȷ����е�ETL����
		if(isSchedule){
			sqlStr.append(" AND NOT EXISTS (SELECT 1 FROM SQ_ETL_SCHEDULE_SEQU SC WHERE SC.ETL_RULE_ID = T.ETL_RULE_ID AND SC.STATE = '10A')");
		}
		
		
		sqlStr.append(" ORDER BY T.STATE,T.STATE_DATE DESC");

		logger.debug("sqlStr===>" + sqlStr.toString());
		int totalRecords = getEtlRuleCount(sqlStr.toString());
		// ��ҳ��
		if (totalRecords == 0) {
			pageDto.setTotalSize(0);
			return pageDto;
		} else {
			pageDto.setTotalSize((int) Math.ceil((double) totalRecords
					/ stepSize));
		}
		// ���һҳҳ���¼�����С�ڲ���?
		int currentStepSize = 0;

		if (startIndex >= pageDto.getTotalSize()) {
			startIndex = pageDto.getTotalSize();
			currentStepSize = totalRecords - (startIndex - 1) * stepSize;
		} else {
			currentStepSize = stepSize;
		}

		pageDto.setPageIndex(startIndex); // ��ǰҳ
		pageDto.setPageSize(stepSize); // ����
		pageDto.setTotalRecords(totalRecords); // ��¼����

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(DAOUtils.populateQuerySQL(sqlStr
					.toString(), (startIndex - 1) * stepSize, currentStepSize,
					conn));
			rs = ps.executeQuery();
			List etlRuleList = getEtlRuleFromRs(rs);

			pageDto.setEtlRuleDto((EtlRuleDto[]) etlRuleList
					.toArray(new EtlRuleDto[] {}));
			com.zterc.uos.helpers.DAOUtils.dropDataPageTempTable(conn);
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return pageDto;
	}
	public EtlRulePageDto queryFetchEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule,
			int startIndex, int stepSize) throws RequiredException,
			SystemException, SQLException {

		EtlRulePageDto pageDto = new EtlRulePageDto();

		StringBuffer sqlStr = new StringBuffer(
				"SELECT T.*,D1.DS_NAME SOURCE_DS_NAME,D2.DS_NAME TARGET_SOURCE_NAME,")
				.append(
						"Y.ETL_TYPE_NAME,S.DATASET_TYPE_NAME,R.DS_TYPE,R.DS_TPYE_NAME")
				.append(
						" FROM SQ_ETL_RULE T LEFT JOIN SQ_DATA_SOURCE D1 ON T.SOURCE_DS_ID = D1.DS_ID")
				.append(
						" LEFT JOIN SQ_DATA_SOURCE D2 ON T.TARGET_DS_ID = D2.DS_ID")
				.append(" LEFT JOIN SQ_ETL_TYPE Y ON Y.ETL_TYPE = T.ETL_TYPE")
				.append(
						" LEFT JOIN SQ_ETL_DATASET_TYPE S ON S.DATASET_TYPE_ID = T.DATASET_TYPE")
				.append(
						" LEFT JOIN SQ_DATA_SOURCE_TYPE R ON R.DS_TYPE = D1.DS_TYPE")
				.append(" WHERE T.STATE='10A'");
		if (etlRuleDto.getEtlType() != null
				&& !"".equals(etlRuleDto.getEtlType().trim())) {	    
		sqlStr.append(" AND t.etl_type = '"+etlRuleDto.getEtlType()+"'");
		}else{
			sqlStr.append(" AND t.etl_type in ('000','003')");
		}

		if (etlRuleDto.getEtlRuleName() != null
				&& !"".equals(etlRuleDto.getEtlRuleName().trim())) {
			sqlStr.append(" AND T.ETL_RULE_NAME LIKE '%").append(
					etlRuleDto.getEtlRuleName().trim()).append("%'");
		}
		
		//����Ǵӵ��ȷ�����򿪲�ѯ,��ֻ��ѯ�����Ѿ�ѡ��ETL���ȷ����е�ETL����
		if(isSchedule){
			sqlStr.append(" AND  SC.STATE = '10A' AND NOT EXISTS (SELECT 1 FROM SQ_ETL_SCHEDULE_SEQU SC WHERE SC.ETL_RULE_ID = T.ETL_RULE_ID AND SC.STATE = '10A')");
		}
		sqlStr.append(" ORDER BY T.STATE,T.STATE_DATE DESC");

		logger.debug("sqlStr===>" + sqlStr.toString());
		int totalRecords = getEtlRuleCount(sqlStr.toString());
		// ��ҳ��
		if (totalRecords == 0) {
			pageDto.setTotalSize(0);
			return pageDto;
		} else {
			pageDto.setTotalSize((int) Math.ceil((double) totalRecords
					/ stepSize));
		}
		// ���һҳҳ���¼�����С�ڲ���?
		int currentStepSize = 0;

		if (startIndex >= pageDto.getTotalSize()) {
			startIndex = pageDto.getTotalSize();
			currentStepSize = totalRecords - (startIndex - 1) * stepSize;
		} else {
			currentStepSize = stepSize;
		}

		pageDto.setPageIndex(startIndex); // ��ǰҳ
		pageDto.setPageSize(stepSize); // ����
		pageDto.setTotalRecords(totalRecords); // ��¼����

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(DAOUtils.populateQuerySQL(sqlStr
					.toString(), (startIndex - 1) * stepSize, currentStepSize,
					conn));
			rs = ps.executeQuery();
			List etlRuleList = getEtlRuleFromRs(rs);

			pageDto.setEtlRuleDto((EtlRuleDto[]) etlRuleList
					.toArray(new EtlRuleDto[] {}));
			com.zterc.uos.helpers.DAOUtils.dropDataPageTempTable(conn);
		} finally {
			cleanUp(conn, null, ps, rs);
		}
		return pageDto;
	}

	private List getEtlRuleFromRs(ResultSet rs) throws SQLException {
		List etlRuleList = new ArrayList();
		EtlRuleDto etlRuleDto = null;
		while (rs.next()) {
			etlRuleDto = new EtlRuleDto();
			etlRuleDto.setEtlRuleId(new Long(rs.getLong("ETL_RULE_ID")));
			etlRuleDto.setEtlRuleName(rs.getString("ETL_RULE_NAME"));
			etlRuleDto.setEtlType(rs.getString("ETL_TYPE"));
			logger.debug("ETL_TYPE===========>" + rs.getString("ETL_TYPE"));
			etlRuleDto.setEtlTypeName(rs.getString("ETL_TYPE_NAME"));
			etlRuleDto.setSourceDsId(new Long(rs.getLong("SOURCE_DS_ID")));
			etlRuleDto.setSourceDataset(rs.getString("SOURCE_DATASET"));
			etlRuleDto.setSourceDsType(rs.getString("DS_TYPE"));
			etlRuleDto.setSourceDsTypeName(rs.getString("DS_TPYE_NAME"));
			etlRuleDto.setDatasetType(rs.getString("DATASET_TYPE"));
			etlRuleDto.setDatasetTypeName(rs.getString("DATASET_TYPE_NAME"));
			etlRuleDto.setSourceDsName(rs.getString("SOURCE_DS_NAME"));
			etlRuleDto.setTargetDsId(new Long(rs.getLong("TARGET_DS_ID")));
			etlRuleDto.setTargetDsName(rs.getString("TARGET_SOURCE_NAME"));
			etlRuleDto.setFileDir(rs.getString("FILE_DIR"));
			etlRuleDto.setFileName(rs.getString("FILE_NAME"));
			etlRuleDto.setFileSeparator(rs.getString("FILE_SEPARATOR"));
			etlRuleDto.setFetchType(rs.getLong("FETCH_TYPE"));
			etlRuleDto.setSourceTbName(rs.getString("SOURCE_TB_NAME"));
			etlRuleDto.setProcName(rs.getString("PROC_NAME"));
			etlRuleDto.setTargetTbName(rs.getString("TARGET_TB_NAME"));
			if(etlRuleDto.getFetchType().longValue()==1){
				etlRuleDto.setFetchTypeText("����?");
			}else if(etlRuleDto.getFetchType().longValue()==2){
				etlRuleDto.setFetchTypeText("�洢���?");
			}		
			
			String state = rs.getString("STATE");
			if ("10A".equals(state)) {
				etlRuleDto.setStateText("��Ч");
			} else {
				etlRuleDto.setStateText("��Ч");
			}
			etlRuleDto.setState(state);
			etlRuleDto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			etlRuleDto.setStateDate(rs.getTimestamp("STATE_DATE"));
			etlRuleDto.setOperManId(new Long(rs.getLong("OPER_MAN_ID")));
			etlRuleDto.setOperManName(rs.getString("OPER_MAN_NAME"));
			etlRuleDto.setMemo(rs.getString("MEMO"));
			etlRuleDto.setIsSupBreak(rs.getString("IS_SUP_BREAK"));
			etlRuleDto.setFileBakDir(rs.getString("FILE_BAK_DIR"));
			etlRuleDto.setFetchMode(rs.getLong("FETCH_MODE"));
			etlRuleDto.setUpColName(rs.getString("UP_COL_NAME"));
			etlRuleDto.setUpColDataType(rs.getString("UP_COL_DATA_TYPE"));
			etlRuleList.add(etlRuleDto);
		}
		return etlRuleList;
	}

	public int getEtlRuleCount(String sql) throws DAOSysException, SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			String countSql = DAOUtils.preparedCalculateSQL(sql);
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(countSql);
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			cleanUp(conn, null, st, rs);
		}
	}

	public Long createEtlRule(EtlRuleDto etlRuleDto) throws SQLException,
			UOSException {
		Connection conn = null;
		PreparedStatement pst = null;
		Long etlRuleId = null;
		int dbloop = 1;
		try {
			String sql = "insert into SQ_ETL_RULE(ETL_RULE_ID,ETL_RULE_NAME,ETL_TYPE,"
					+ "SOURCE_DS_ID,SOURCE_DATASET,DATASET_TYPE,TARGET_DS_ID,FILE_DIR,"
					+ "FILE_NAME,FILE_SEPARATOR,STATE,CREATE_DATE,STATE_DATE,OPER_MAN_ID,"
					+ "OPER_MAN_NAME,MEMO,IS_SUP_BREAK,FILE_BAK_DIR)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate,?,?,?,?,?)";

			logger.debug("createEtlRule sql===========>: " + sql);
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			etlRuleId = new Long(SequenceGenerator.getSequenceValue(
					"SQ_ETL_RULE", 9));
			logger.debug("createEtlRule etlRuleId===========>: " + etlRuleId);
			pst.setLong(dbloop++, etlRuleId.longValue());
			pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
			pst.setString(dbloop++, etlRuleDto.getEtlType());
			pst.setLong(dbloop++, etlRuleDto.getSourceDsId().longValue());
			pst.setString(dbloop++, etlRuleDto.getSourceDataset());
			pst.setString(dbloop++, etlRuleDto.getDatasetType());
			pst.setLong(dbloop++, etlRuleDto.getTargetDsId().longValue());
			pst.setString(dbloop++, etlRuleDto.getFileDir());
			pst.setString(dbloop++, etlRuleDto.getFileName());
			pst.setString(dbloop++, etlRuleDto.getFileSeparator());
			pst.setString(dbloop++, etlRuleDto.getState());
			pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
			pst.setString(dbloop++, etlRuleDto.getOperManName());
			pst.setString(dbloop++, etlRuleDto.getMemo());
			pst.setString(dbloop++, etlRuleDto.getIsSupBreak());
			pst.setString(dbloop++, etlRuleDto.getFileBakDir());

			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}

		return etlRuleId;
	}
	
	public Long createEtlCleanRule(EtlRuleDto etlRuleDto) throws SQLException,
	UOSException {
	Connection conn = null;
	PreparedStatement pst = null;
	Long etlRuleId = null;
	int dbloop = 1;
	try {
		String sql = "insert into SQ_ETL_RULE(ETL_RULE_ID,ETL_RULE_NAME,TARGET_TB_NAME,ETL_TYPE,"
				+ "STATE,CREATE_DATE,STATE_DATE,OPER_MAN_ID,"
				+ "OPER_MAN_NAME)"
				+ "values(?,?,?,?,?,sysdate,sysdate,?,?)";
	
		
		conn = getConnection();
		pst = conn.prepareStatement(sql);
		etlRuleId = new Long(SequenceGenerator.getSequenceValue(
				"SQ_ETL_RULE", 9));
		logger.debug("createEtlCleanRule etlRuleId===========>: " + etlRuleId);
		pst.setLong(dbloop++, etlRuleId.longValue());
		pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
		pst.setString(dbloop++, etlRuleDto.getTargetTbName());
		pst.setString(dbloop++, etlRuleDto.getEtlType());
		pst.setString(dbloop++, etlRuleDto.getState());
		pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
		pst.setString(dbloop++, etlRuleDto.getOperManName());
	
		pst.execute();
	} finally {
		cleanUp(conn, null, pst, null);
	}
	
	return etlRuleId;
	}
	public Long createEtlTransRule(EtlRuleDto etlRuleDto) throws SQLException,
	UOSException {
	Connection conn = null;
	PreparedStatement pst = null;
	Long etlRuleId = null;
	int dbloop = 1;
	try {
		String sql = "insert into SQ_ETL_RULE(ETL_RULE_ID,ETL_RULE_NAME,ETL_TYPE,"
				+ "STATE,CREATE_DATE,STATE_DATE,OPER_MAN_ID,"
				+ "OPER_MAN_NAME,PROC_NAME)"
				+ "values(?,?,?,?,sysdate,sysdate,?,?,?)";
	
		
		conn = getConnection();
		pst = conn.prepareStatement(sql);
		etlRuleId = new Long(SequenceGenerator.getSequenceValue(
				"SQ_ETL_RULE", 9));
		logger.debug("createEtlCleanRule etlRuleId===========>: " + etlRuleId);
		pst.setLong(dbloop++, etlRuleId.longValue());
		pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
		pst.setString(dbloop++, etlRuleDto.getEtlType());
		pst.setString(dbloop++, etlRuleDto.getState());
		pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
		pst.setString(dbloop++, etlRuleDto.getOperManName());
		pst.setString(dbloop++, etlRuleDto.getProcName());
	
		pst.execute();
	} finally {
		cleanUp(conn, null, pst, null);
	}
	
	return etlRuleId;
	}
	public Long createFetchEtlRule(EtlRuleDto etlRuleDto) throws SQLException,
	UOSException {
	Connection conn = null;
	PreparedStatement pst = null;
	Long etlRuleId = null;
	int dbloop = 1;
	try {
		String sql = "insert into SQ_ETL_RULE(ETL_RULE_ID,ETL_RULE_NAME,ETL_TYPE,"
				+ "SOURCE_DS_ID,TARGET_DS_ID,"
				+ "STATE,CREATE_DATE,STATE_DATE,OPER_MAN_ID,"
				+ "OPER_MAN_NAME,MEMO,SOURCE_TB_NAME,TARGET_TB_NAME,PROC_NAME,FETCH_TYPE,FETCH_MODE,UP_COL_NAME,UP_COL_DATA_TYPE,FILE_DIR,FILE_NAME,FILE_BAK_DIR)"
				+ "values(?,?,?,?,?,?,sysdate,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
		logger.debug("createEtlRule sql===========>: " + sql);
		conn = getConnection();
		pst = conn.prepareStatement(sql);
		etlRuleId = new Long(SequenceGenerator.getSequenceValue(
				"SQ_ETL_RULE", 9));
		logger.debug("createEtlRule etlRuleId===========>: " + etlRuleId);
		pst.setLong(dbloop++, etlRuleId.longValue());
		pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
		pst.setString(dbloop++, etlRuleDto.getEtlType());
		pst.setLong(dbloop++, etlRuleDto.getSourceDsId().longValue());
		pst.setLong(dbloop++, etlRuleDto.getTargetDsId().longValue());
		pst.setString(dbloop++, etlRuleDto.getState());
		pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
		pst.setString(dbloop++, etlRuleDto.getOperManName());
		pst.setString(dbloop++, etlRuleDto.getMemo());
		pst.setString(dbloop++, etlRuleDto.getSourceTbName());
		pst.setString(dbloop++, etlRuleDto.getTargetTbName());
		pst.setString(dbloop++, etlRuleDto.getProcName());
		pst.setLong(dbloop++, etlRuleDto.getFetchType().longValue());
		pst.setLong(dbloop++, etlRuleDto.getFetchMode().longValue());
		pst.setString(dbloop++, etlRuleDto.getUpColName());
		pst.setString(dbloop++, etlRuleDto.getUpColDataType());
		
		pst.setString(dbloop++, etlRuleDto.getFileDir());
		pst.setString(dbloop++, etlRuleDto.getFileName());
		pst.setString(dbloop++, etlRuleDto.getFileBakDir());

		pst.execute();
	} finally {
		cleanUp(conn, null, pst, null);
	}
	
		return etlRuleId;
	}
	

	public void delEtlRule(Long etlRuleId) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			String sql = "UPDATE SQ_ETL_RULE T SET T.STATE='10P',T.STATE_DATE = sysdate WHERE T.ETL_RULE_ID = ?";
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			// pst.setTimestamp(1, DateUtilities.dateToTimeStamp(new Date()));
			pst.setLong(1, etlRuleId.longValue());

			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}
	}

	public EtlRuleDto findEtlRuleById(Long etlRuleId) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"SELECT T.*,D1.DS_NAME SOURCE_DS_NAME,D2.DS_NAME TARGET_SOURCE_NAME,")
					.append(
							"Y.ETL_TYPE_NAME,S.DATASET_TYPE_NAME,R.DS_TYPE,R.DS_TPYE_NAME")
					.append(
							" FROM SQ_ETL_RULE T LEFT JOIN SQ_DATA_SOURCE D1 ON T.SOURCE_DS_ID = D1.DS_ID")
					.append(
							" LEFT JOIN SQ_DATA_SOURCE D2 ON T.TARGET_DS_ID = D2.DS_ID")
					.append(
							" LEFT JOIN SQ_ETL_TYPE Y ON Y.ETL_TYPE = T.ETL_TYPE")
					.append(
							" LEFT JOIN SQ_ETL_DATASET_TYPE S ON S.DATASET_TYPE_ID = T.DATASET_TYPE")
					.append(
							" LEFT JOIN SQ_DATA_SOURCE_TYPE R ON R.DS_TYPE = D1.DS_TYPE")
					// .append(" WHERE T.STATE='10A' AND T.ETL_RULE_ID = ? ");
					.append(" WHERE T.ETL_RULE_ID = ? ");

			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setLong(1, etlRuleId.longValue());
			rs = pst.executeQuery();
			List etlRuleList = getEtlRuleFromRs(rs);
			if (etlRuleList != null && !etlRuleList.isEmpty()) {
				return (EtlRuleDto) etlRuleList.get(0);
			}
		} finally {
			cleanUp(conn, null, pst, rs);
		}
		return null;
	}
	public void updateFetchEtlRule(EtlRuleDto etlRuleDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"update SQ_ETL_RULE T SET T.ETL_RULE_NAME = ?,")
					.append("T.SOURCE_DS_ID = ?,T.TARGET_DS_ID = ?,")
					.append("T.STATE_DATE = sysdate,T.OPER_MAN_ID = ?,T.OPER_MAN_NAME = ?,T.SOURCE_TB_NAME = ?,T.TARGET_TB_NAME = ?,T.PROC_NAME = ?,T.FETCH_TYPE = ? ,T.FETCH_MODE = ? ,T.UP_COL_NAME = ?,T.UP_COL_DATA_TYPE = ?")
					.append(",T.FILE_DIR=?,T.FILE_NAME=?,T.FILE_BAK_DIR=?")
					.append(" WHERE T.ETL_RULE_ID = ?");
			logger.debug("updateFetchEtlRule sql===========>: " + sqlStr);
			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
			pst.setLong(dbloop++, etlRuleDto.getSourceDsId().longValue());
			pst.setLong(dbloop++, etlRuleDto.getTargetDsId().longValue());
			pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
			pst.setString(dbloop++, etlRuleDto.getOperManName());
			pst.setString(dbloop++, etlRuleDto.getSourceTbName());
			pst.setString(dbloop++, etlRuleDto.getTargetTbName());
			pst.setString(dbloop++, etlRuleDto.getProcName());
			pst.setLong(dbloop++, etlRuleDto.getFetchType().longValue());
			pst.setLong(dbloop++, etlRuleDto.getFetchMode().longValue());
			pst.setString(dbloop++, etlRuleDto.getUpColName());
			pst.setString(dbloop++, etlRuleDto.getUpColDataType());
			
			pst.setString(dbloop++, etlRuleDto.getFileDir());
			pst.setString(dbloop++, etlRuleDto.getFileName());
			pst.setString(dbloop++, etlRuleDto.getFileBakDir());
			pst.setLong(dbloop++, etlRuleDto.getEtlRuleId().longValue());

			
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}
	}
	public void updateEtlCleanRule(EtlRuleDto etlRuleDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"update SQ_ETL_RULE T SET T.ETL_RULE_NAME = ?,T.TARGET_TB_NAME = ?,")
					.append("T.STATE_DATE = sysdate,T.OPER_MAN_ID = ?,T.OPER_MAN_NAME = ?")
					.append("WHERE T.ETL_RULE_ID = ?");
			logger.debug("updateEtlCleanRule sql===========>: " + sqlStr);
			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
			pst.setString(dbloop++, etlRuleDto.getTargetTbName());
			pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
			pst.setString(dbloop++, etlRuleDto.getOperManName());
			
			pst.setLong(dbloop++, etlRuleDto.getEtlRuleId().longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}
	} 
	public void updateEtlTransRule(EtlRuleDto etlRuleDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"update SQ_ETL_RULE T SET T.ETL_RULE_NAME = ?,")
					.append("T.STATE_DATE = sysdate,T.OPER_MAN_ID = ?,T.OPER_MAN_NAME = ?,T.PROC_NAME = ? ")
					.append("WHERE T.ETL_RULE_ID = ?");
			logger.debug("updateEtlCleanRule sql===========>: " + sqlStr);
			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
			pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
			pst.setString(dbloop++, etlRuleDto.getOperManName());
			pst.setString(dbloop++, etlRuleDto.getProcName());
			pst.setLong(dbloop++, etlRuleDto.getEtlRuleId().longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}
	}
	public void updateEtlRule(EtlRuleDto etlRuleDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"update SQ_ETL_RULE T SET T.ETL_RULE_NAME = ?,T.ETL_TYPE = ?,")
					.append(
							"T.SOURCE_DS_ID = ? ,T.SOURCE_DATASET = ?,T.DATASET_TYPE = ?,T.TARGET_DS_ID = ?,T.FILE_DIR = ?,")
					.append(
							"T.FILE_NAME = ?,T.FILE_SEPARATOR=?,T.STATE_DATE = sysdate,T.OPER_MAN_ID = ?,T.OPER_MAN_NAME = ?,")
					.append(
							"T.MEMO= ?,T.IS_SUP_BREAK = ?,T.FILE_BAK_DIR = ? WHERE T.ETL_RULE_ID = ?");
			logger.debug("updateEtlRule sql===========>: " + sqlStr);
			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setString(dbloop++, etlRuleDto.getEtlRuleName());
			pst.setString(dbloop++, etlRuleDto.getEtlType());
			pst.setLong(dbloop++, etlRuleDto.getSourceDsId().longValue());
			pst.setString(dbloop++, etlRuleDto.getSourceDataset());
			pst.setString(dbloop++, etlRuleDto.getDatasetType());
			pst.setLong(dbloop++, etlRuleDto.getTargetDsId().longValue());
			pst.setString(dbloop++, etlRuleDto.getFileDir());
			pst.setString(dbloop++, etlRuleDto.getFileName());
			pst.setString(dbloop++, etlRuleDto.getFileSeparator());
			pst.setLong(dbloop++, etlRuleDto.getOperManId().longValue());
			pst.setString(dbloop++, etlRuleDto.getOperManName());
			pst.setString(dbloop++, etlRuleDto.getMemo());
			pst.setString(dbloop++, etlRuleDto.getIsSupBreak());
			pst.setString(dbloop++, etlRuleDto.getFileBakDir());
			pst.setLong(dbloop++, etlRuleDto.getEtlRuleId().longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}
	}
	
	public void updateEtlRuleDs(EtlRuleDto etlRuleDto)throws SQLException{
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			String sql = "update SQ_ETL_RULE T SET T.SOURCE_DATASET = ?,T.DATASET_TYPE = ? WHERE T.ETL_RULE_ID = ?";
			logger.debug("updateEtlRuleDs sql===========>: " + sql);
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(dbloop++, etlRuleDto.getSourceDataset());
			pst.setString(dbloop++, etlRuleDto.getDatasetType());
			pst.setLong(dbloop++, etlRuleDto.getEtlRuleId().longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}		
	}

	public EtlDatasetTypeDto[] findAllEtlDatasetType() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List typeList = new ArrayList();
		try {
			String sql = "select * from SQ_ETL_DATASET_TYPE";
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EtlDatasetTypeDto dto = new EtlDatasetTypeDto();
				dto.setDatasetTypeId(rs.getString("DATASET_TYPE_ID"));
				dto.setDatasetTypeName(rs.getString("DATASET_TYPE_NAME"));
				typeList.add(dto);
			}
			if (typeList != null && !typeList.isEmpty()) {
				logger.debug("�������ݼ����͵ĳ���Ϊ:=====>" + typeList.size());
				return (EtlDatasetTypeDto[]) typeList
						.toArray(new EtlDatasetTypeDto[] {});
			}

		} finally {
			cleanUp(conn, null, st, rs);
		}

		return null;
	}

	public EtlTypeDto[] findAllEtlType() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List typeList = new ArrayList();
		try {
			String sql = "select * from SQ_ETL_TYPE";
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EtlTypeDto dto = new EtlTypeDto();
				dto.setEtlType(rs.getString("ETL_TYPE"));
				dto.setEtlTypeName(rs.getString("ETL_TYPE_NAME"));
				typeList.add(dto);
			}
			if (typeList != null && !typeList.isEmpty()) {
				logger.debug("�����ETL���͵ĳ���Ϊ:=====>" + typeList.size());
				return (EtlTypeDto[]) typeList.toArray(new EtlTypeDto[] {});
			}

		} finally {
			cleanUp(conn, null, st, rs);
		}
		return null;
	}

	public EtlDatasetParamDto findDSParamById(Long paramId)
			throws SQLException, RequiredException {
		
		if (paramId == null)
			throw new RequiredException("����ID����Ϊ��......");
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		EtlDatasetParamDto dto = null;
		try {
			String sql = "SELECT * FROM SQ_ETL_DATASET_PARAM T WHERE T.PARAM_ID = ?";
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setLong(1, paramId.longValue());
			rs = pst.executeQuery();
			if (rs.next()) {
				dto = new EtlDatasetParamDto();
				dto.setParamId(new Long(rs.getLong("PARAM_ID")));
				dto.setEtlRuleId(new Long(rs.getLong("ETL_RULE_ID")));
				dto.setParamCode(rs.getString("PARAM_CODE"));
				dto.setQuerySql(rs.getString("QUERY_SQL"));
				dto.setOperManId(new Long(rs.getLong("OPER_MAN_ID")));
				dto.setOperManName(rs.getString("OPER_MAN_NAME"));
			}
		
		} finally {
			cleanUp(conn, null, pst, rs);
		}
		return dto;
	}

	public EtlDatasetParamDto[] findDSParamByRuleId(Long etlRuleId)
			throws SQLException, RequiredException {

		if (etlRuleId == null)
			throw new RequiredException("ETL����ID����Ϊ��......");

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List typeList = new ArrayList();
		try {
			String sql = "SELECT * FROM SQ_ETL_DATASET_PARAM T WHERE T.ETL_RULE_ID = ?";
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setLong(1, etlRuleId.longValue());
			rs = pst.executeQuery();
			while (rs.next()) {
				EtlDatasetParamDto dto = new EtlDatasetParamDto();
				dto.setParamId(new Long(rs.getLong("PARAM_ID")));
				dto.setEtlRuleId(new Long(rs.getLong("ETL_RULE_ID")));
				dto.setParamCode(rs.getString("PARAM_CODE"));
				dto.setQuerySql(rs.getString("QUERY_SQL"));
				dto.setOperManId(new Long(rs.getLong("OPER_MAN_ID")));
				dto.setOperManName(rs.getString("OPER_MAN_NAME"));
				typeList.add(dto);
			}
			if (typeList != null && !typeList.isEmpty()) {
				logger.debug("�����ETL���͵ĳ���Ϊ:=====>" + typeList.size());
				return (EtlDatasetParamDto[]) typeList
						.toArray(new EtlDatasetParamDto[] {});
			}

		} finally {
			cleanUp(conn, null, pst, rs);
		}
		return null;
	}

	public Long createDSParam(EtlDatasetParamDto etlDatasetParamDto)
			throws SQLException, UOSException {
		Connection conn = null;
		PreparedStatement pst = null;
		Long paramId = null;
		int dbloop = 1;
		try {
			String sql = "INSERT INTO SQ_ETL_DATASET_PARAM(PARAM_ID,ETL_RULE_ID,PARAM_CODE,"
					+ "QUERY_SQL,OPER_MAN_ID,OPER_MAN_NAME)VALUES(?,?,?,?,?,?)";

			logger.debug("createEtlRule sql===========>: " + sql);
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			paramId = new Long(SequenceGenerator.getSequenceValue(
					"SQ_ETL_DATASET_PARAM", 9));
			logger.debug("createDSParam paramId===========>: " + paramId);
			pst.setLong(dbloop++, paramId.longValue());
			pst.setLong(dbloop++, etlDatasetParamDto.getEtlRuleId().longValue());
			pst.setString(dbloop++, etlDatasetParamDto.getParamCode());
			pst.setString(dbloop++, etlDatasetParamDto.getQuerySql());
			pst.setLong(dbloop++, etlDatasetParamDto.getOperManId().longValue());
			pst.setString(dbloop++, etlDatasetParamDto.getOperManName());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}

		return paramId;
	}

	public void delDSParamById(Long paramId) throws SQLException,
			RequiredException {

		if (paramId == null)
			throw new RequiredException("��ݼ�����ID����Ϊ��......");
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			String sql = "DELETE FROM SQ_ETL_DATASET_PARAM T WHERE T.PARAM_ID = ?";
			logger.debug("delDSParamById paramId===========>: " + paramId);
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setLong(1, paramId.longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}

	}

	public void updateDSParam(EtlDatasetParamDto etlDatasetParamDto)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			StringBuffer sqlStr = new StringBuffer(
					"UPDATE SQ_ETL_DATASET_PARAM T SET T.PARAM_CODE = ? ,")
					.append("T.QUERY_SQL = ?,T.OPER_MAN_ID = ?,T.OPER_MAN_NAME = ? WHERE T.PARAM_ID = ?");
			logger.debug("updateDSParam sql===========>: " + sqlStr);
			conn = getConnection();
			pst = conn.prepareStatement(sqlStr.toString());
			pst.setString(dbloop++, etlDatasetParamDto.getParamCode());
			pst.setString(dbloop++, etlDatasetParamDto.getQuerySql());
			pst.setLong(dbloop++, etlDatasetParamDto.getOperManId().longValue());
			pst.setString(dbloop++, etlDatasetParamDto.getOperManName());
			pst.setLong(dbloop++, etlDatasetParamDto.getParamId().longValue());
			pst.execute();
		} finally {
			cleanUp(conn, null, pst, null);
		}

	}

	public boolean isEtlRuleNameOnlyOne(String etlRuleName,Long etlRuleId) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) FROM SQ_ETL_RULE T WHERE T.ETL_RULE_NAME = ? AND T.ETL_RULE_ID <> ?";
			//���Ϊ��?,���ʼ���?-1
			if(etlRuleId == null )
				etlRuleId = new Long(-1) ;
			
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, etlRuleName.trim());
			pst.setLong(2, etlRuleId.longValue());
			rs = pst.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				logger.debug("====�������ͬ��Ƶ���" + count + "��=====");
				if(count >= 1){
					return false;
				}			
			}

		} finally {
			cleanUp(conn, null, pst, rs);
		}
		
		return true;
	}

}
