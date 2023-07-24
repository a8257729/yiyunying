package com.ztesoft.mobile.etl.dao;

import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.vo.ScheduleDto;
import com.ztesoft.mobile.etl.vo.ScheduleInstDto;
import com.ztesoft.mobile.etl.vo.SchedulePageDto;

public interface ScheduleDAO {
	public SchedulePageDto queryDataSourceList(ScheduleDto scheduleDto,int startIndex, int stepSize)
	throws RequiredException, SystemException, SQLException;
	public Long addDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException;
	public void updateDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException;	
	public void deleteDataSource(String objId,String manId,String manName) throws SQLException, UOSException;
	public int checkScheduleName(String scheduleName) throws SQLException;
	public Long addScheduleInst(ScheduleInstDto scheduleInstDto) throws SQLException,UOSException;
	public int checkIsEist(String columnName,String values,String tableName,String type) throws SQLException;
	
	public void updateScheduleState(long scheduleId, String state,
			String manId, String manName) throws SQLException, UOSException;
	
	public int checkIsRunning(long scheduleId) throws SQLException;
}
