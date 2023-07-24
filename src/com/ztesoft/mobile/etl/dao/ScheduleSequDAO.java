package com.ztesoft.mobile.etl.dao;

import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.vo.ScheduleMonitorQryDto;
import com.ztesoft.mobile.etl.vo.ScheduleSequDto;
import com.ztesoft.mobile.etl.vo.ScheduleSequPageDto;

public interface ScheduleSequDAO {
	public ScheduleSequPageDto queryDataSourceList(ScheduleSequDto scheduleSequDto,int startIndex, int stepSize)
	throws RequiredException, SystemException, SQLException;
	public Long addDataSource(ScheduleSequDto scheduleSequDto) throws SQLException, UOSException;
	public void updateDataSource(ScheduleSequDto scheduleSequDto) throws SQLException, UOSException;
	public void deleteDataSource(String objId,String manId,String manName) throws SQLException, UOSException;
	public void deleteScheduleSeqBySchId(String SchId, String manId, String manName)throws SQLException, UOSException ;
	public void upSequSch(String SequIdStr, String iSequId) throws SQLException;
	public void downSequSch(String SequIdStr, String iSequId) throws SQLException;
	
	/**
	 * findEtlRuleListByScheduleId
	 * @param scheduleId
	 * @return List
	 * 通过调度方案的标识查找该方案的所有ETL规则
	 * */
	public ScheduleMonitorQryDto[] findEtlRuleListByScheduleId(Long scheduleId) throws SQLException;
}
