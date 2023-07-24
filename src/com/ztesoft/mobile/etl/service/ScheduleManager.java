package com.ztesoft.mobile.etl.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.dao.ScheduleDAO;
import com.ztesoft.mobile.etl.dao.ScheduleDAOFactory;
import com.ztesoft.mobile.etl.dao.ScheduleSequDAO;
import com.ztesoft.mobile.etl.dao.ScheduleSequDAOFactory;
import com.ztesoft.mobile.etl.vo.ScheduleDto;
import com.ztesoft.mobile.etl.vo.ScheduleInstDto;
import com.ztesoft.mobile.etl.vo.SchedulePageDto;

public class ScheduleManager {

	private static Logger log = Logger.getLogger(ScheduleManager.class.getName());
	public SchedulePageDto queryDataSourceList(ScheduleDto scheduleDto, int startIndex,int stepSize) throws RequiredException, SystemException {

		SchedulePageDto schedulePageDto = null;
		try {
			ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
			schedulePageDto = scheduleDAO.queryDataSourceList(scheduleDto, startIndex, stepSize);
		} catch (SQLException ex) {
			log.error(ex.getMessage());
			throw new SystemException(ex);
		}
		return schedulePageDto;
	}
	public Long addDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		return scheduleDAO.addDataSource(scheduleDto);
	}

	public void updateDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		scheduleDAO.updateDataSource(scheduleDto);
	}
	public void deleteDataSource(String objId,String manId,String manName) throws SQLException, UOSException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		ScheduleSequDAO scheduleSequDAO = ScheduleSequDAOFactory.getDAO();
		scheduleDAO.deleteDataSource(objId,manId,manName); 
		scheduleSequDAO.deleteScheduleSeqBySchId(objId, manId, manName);
	}
	
	public int checkScheduleName(String scheduleName) throws SQLException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		return scheduleDAO.checkScheduleName(scheduleName); 
	}
	public Long addScheduleInst(ScheduleInstDto scheduleInstDto) throws SQLException,UOSException{
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		return scheduleDAO.addScheduleInst(scheduleInstDto); 
	}
	public int checkIsEist(String columnName,String values,String tableName,String type) throws SQLException{
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		return scheduleDAO.checkIsEist(columnName, values, tableName, type); 
	}
	
	public void suspendSchedule(long scheduleId,String manId,String manName) throws SQLException, UOSException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		scheduleDAO.updateScheduleState(scheduleId, "1PU", manId, manName);
	}
	
	public void restartSchedule(long scheduleId,String manId,String manName) throws SQLException, UOSException {
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		scheduleDAO.updateScheduleState(scheduleId, "10S", manId, manName);
	}
	
	public int checkIsRunning(long scheduleId) throws SQLException{
		ScheduleDAO scheduleDAO = ScheduleDAOFactory.getDAO();
		return scheduleDAO.checkIsRunning(scheduleId);
	}
}
