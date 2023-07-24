package com.ztesoft.mobile.etl.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.vo.ScheduleDto;
import com.ztesoft.mobile.etl.vo.ScheduleInstDto;
import com.ztesoft.mobile.etl.vo.SchedulePageDto;

public class ScheduleMangerClient {

	private static Logger _logger = Logger.getLogger(ScheduleMangerClient.class);
	private ScheduleManager scheduleManager = null;

	public ScheduleMangerClient() {
		scheduleManager = new ScheduleManager();
	}

	/**
	 * 查询
	 * @param scheduleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 */
	public SchedulePageDto queryDataSourceList(ScheduleDto scheduleDto, int startIndex, int stepSize)throws RequiredException, SystemException {
		SchedulePageDto schedulePageDto = null;
		schedulePageDto = scheduleManager.queryDataSourceList(scheduleDto, startIndex, stepSize);
	
		return schedulePageDto;
	}
	/**
	 * 添加
	 * @param scheduleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 */
	public Long addDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException {
		return scheduleManager.addDataSource(scheduleDto);
	}
	
	/**
	 * 更新
	 * @param scheduleDto
	 * @throws SQLException
	 * @throws UOSException
	 */
	public void updateDataSource(ScheduleDto scheduleDto) throws SQLException, UOSException {
		scheduleManager.updateDataSource(scheduleDto);
	}

	/**
	 * 删除
	 * @param objId
	 * @param manId
	 * @param manName
	 * @throws SQLException
	 * @throws UOSException
	 */
	public void deleteDataSource(String objId,String manId,String manName) throws SQLException, UOSException {
	scheduleManager.deleteDataSource(objId,manId,manName); 
	}
	/**
	 * 验证调度名称是否存在
	 * @throws SQLException
	 */
	public int checkScheduleName(String scheduleName) throws SQLException {
		return scheduleManager.checkScheduleName(scheduleName);
	}
	/**
	 * 启动，即生成调度方案实例
	 * @param scheduleInstDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 */
	public Long addScheduleInst(ScheduleInstDto scheduleInstDto) throws SQLException,UOSException{
		return scheduleManager.addScheduleInst(scheduleInstDto);
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
	public int checkIsEist(String columnName,String values,String tableName,String type) throws SQLException{
		return scheduleManager.checkIsEist(columnName, values, tableName, type);
	}
	
	public void suspendSchedule(long scheduleId,String manId,String manName) throws SQLException, UOSException{
		scheduleManager.suspendSchedule(scheduleId, manId, manName);
	}
	
	public void restartSchedule(long scheduleId,String manId,String manName) throws SQLException, UOSException{
		scheduleManager.restartSchedule(scheduleId, manId, manName);
	}
	
	public int checkIsRunning(long scheduleId) throws SQLException{
		return scheduleManager.checkIsRunning(scheduleId);
	}
}
