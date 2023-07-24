package com.ztesoft.mobile.etl.dao;

import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.zterc.uos.helpers.DAOSysException;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.DataSourcePageDto;



public interface DataSourceDAO {
	public DataSourcePageDto queryDataSourceList(DataSourceDto dataSourceDto,int startIndex, int stepSize)
	throws RequiredException, SystemException, SQLException;
	public Long addDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException;
	
	/**
	 * updateMonitorObj
	 * @param monitorObjDto
	 * @throws UOSException 
	 * 
	 * */
	public void updateDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException;
	
	/**
	 * deleteMonitorObj
	 * @param objId
	 * @throws UOSException 
	 * 
	 * */
	public void deleteDataSource(Long objId,Long manId,String manName) throws SQLException, UOSException;
	
	/**
	 * 通过ID查询数据源信息
	 * @author xie.shuanghong  2010-01-26
	 * @param dsId
	 * @return
	 * @throws SQLException
	 */
	DataSourceDto findDataSourceById(Long dsId)throws RequiredException,SQLException;
	
	/**
	 * 查询所有的数据源信息
	 * @author xie.shuanghong  2010-01-20
	 * @return
	 * @throws SQLException
	 */
	DataSourceDto[] findAllDataSource()throws SQLException;
	
	/**
	 * 根据名字查询纪录数，用于重复检查
	 * @param dsName
	 * @return
	 * @throws DAOSysException
	 * @throws SQLException
	 * @author feng.yang at Feb 24, 2011
	 */
	public int countDsByName(String dsName) throws DAOSysException,
			SQLException;
}
