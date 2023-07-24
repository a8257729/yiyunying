package com.ztesoft.mobile.etl.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.DataSourcePageDto;




public class DataSourceManagerClient {
	private static Logger _logger = Logger
			.getLogger(DataSourceManagerClient.class);
	private DataSourceManager dataSourceManager = null;

	public DataSourceManagerClient() {
		dataSourceManager = new DataSourceManager();
	}

	public DataSourcePageDto queryDataSourceList(
			DataSourceDto dataSourceDto, int startIndex, int stepSize)
			throws RequiredException, SystemException {
		DataSourcePageDto dataSourcePageDto = null;
		dataSourcePageDto = dataSourceManager.queryDataSourceList(
				dataSourceDto, startIndex, stepSize);
		return dataSourcePageDto;
	}
	
	
	/**
	 * @param dataSourceDto
	 * @return
	 * @throws RequiredException
	 * @throws SQLException
	 * @author feng.yang at Feb 24, 2011
	 */
	public int isNameExist(DataSourceDto dataSourceDto)
			throws RequiredException, SQLException {
		Long dsId = dataSourceDto.getDsId();
		String dsName = dataSourceDto.getDsName();
		if (dataSourceManager.isNameRepeat(dsId, dsName)) {
			return 1;
		}
		return 0;
	}
	
	public Long addDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException {
		return dataSourceManager.addDataSource(dataSourceDto);
	}
	
	/**
	 * updateMonitorObj
	 * @param monitorObjDto
	 * @throws UOSException 
	 * 
	 * */
	public void updateDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException {
		dataSourceManager.updateDataSource(dataSourceDto);
	}
	
	/**
	 * deleteMonitorObj
	 * @param objId
	 * @throws UOSException 
	 * 
	 * */
	public void deleteDataSource(Long objId,Long manId,String manName) throws SQLException, UOSException {
		dataSourceManager.deleteDataSource(objId, manId, manName);
	}
	public boolean connTest(DataSourceDto dataSourceDto) throws Exception{
		return dataSourceManager.connTest(dataSourceDto);
	}
	
	/**
	 * 查询所有的数据源信息
	 * @author xie.shuanghong  2010-01-20
	 * @return
	 * @throws SQLException
	 */
	public DataSourceDto[] findAllDataSource()throws SQLException{
		return dataSourceManager.findAllDataSource();
	}
}
