package com.ztesoft.mobile.etl.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import sun.net.ftp.FtpClient;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.dao.DataSourceDAO;
import com.ztesoft.mobile.etl.dao.DataSourceDAOFactory;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.DataSourcePageDto;

public class DataSourceManager {
	private static Logger log = Logger.getLogger(DataSourceManager.class
			.getName());
	
	/**
	 * ����Դ�����ظ����
	 * @param dsId
	 * @param dsName
	 * @return
	 * @throws RequiredException
	 * @throws SQLException
	 * @author feng.yang at Feb 24, 2011
	 */
	public boolean isNameRepeat(Long dsId, String dsName)
			throws RequiredException, SQLException {
		DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
		dsName = dsName.trim().toUpperCase();
		if (dsId != null && dsId.longValue() != 0) {
			DataSourceDto ds = dataSourceDAO.findDataSourceById(dsId);

			String old = ds.getDsName() == null ? "":ds.getDsName().trim()
					.toUpperCase();
			if (dsName.equals(old)) {
				return false;
			}
		}
		int count = dataSourceDAO.countDsByName(dsName);
		if (count == 0) {
			return false;
		}
		return true;

	}

	/**
	 * queryMonitorObjForFront
	 * @param searchConditionDto
	 * @param startIndex
	 * @param stepSize
	 * @return MonitorObjPageDto
	 * 
	 * */
	public DataSourcePageDto queryDataSourceList(
			DataSourceDto dataSourceDto, int startIndex,
			int stepSize) throws RequiredException, SystemException {

		DataSourcePageDto dataSourcePageDto = null;
		try {
			DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
			dataSourcePageDto = dataSourceDAO.queryDataSourceList(
					dataSourceDto, startIndex, stepSize);
		} catch (SQLException ex) {
			log.error(ex.getMessage());
			throw new SystemException(ex);
		}
		return dataSourcePageDto;
	}
	public Long addDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException {
		DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
		return dataSourceDAO.addDataSource(dataSourceDto);
	}
	
	/**
	 * updateMonitorObj
	 * @param monitorObjDto
	 * @throws UOSException 
	 * 
	 * */
	public void updateDataSource(DataSourceDto dataSourceDto) throws SQLException, UOSException {
		DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
		dataSourceDAO.updateDataSource(dataSourceDto);
	}
	
	/**
	 * deleteMonitorObj
	 * @param objId
	 * @throws UOSException 
	 * 
	 * */
	public void deleteDataSource(Long objId,Long manId,String manName) throws SQLException, UOSException {
		DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
		dataSourceDAO.deleteDataSource(objId,manId,manName);
	}
	
	/**
	 * ��ѯ���е�����Դ��Ϣ
	 * @author xie.shuanghong  2010-01-20
	 * @return
	 * @throws SQLException
	 */
	public DataSourceDto[] findAllDataSource()throws SQLException{
		DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
		return dataSourceDAO.findAllDataSource();
	}
	
	//����Դ����   Ҫ�����ṩ�ķ������в���
	public boolean connTest(DataSourceDto dataSourceDto) throws Exception {
		if(dataSourceDto.getDsType().equalsIgnoreCase("000")){  //���ݿ�	
			Connection conn = null;
			try{
				Driver driver = (Driver) (Class.forName(dataSourceDto.getDbDriver())
						.newInstance());
				DriverManager.registerDriver(driver); // ע�� JDBC ��������
				conn = DriverManager.getConnection(dataSourceDto.getDbUrl(),dataSourceDto.getUserName(), dataSourceDto.getPassword());
			}catch(Exception ce){	
				
				throw new Exception("��������Դ��"+dataSourceDto.getDsName()+"ʧ�ܣ�"+ce.getMessage()+ce.toString());
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				return true;
			}			
		}else if(dataSourceDto.getDsType().equalsIgnoreCase("001")){  //ftp�ļ�	
			try {
				/* ����FTP������ */
				FtpClient ftpclient = null;
				if (ftpclient == null) {
					//ftpclient = new FtpClient(dataSourceDto.getFtpIp());
					//ftpclient.login(dataSourceDto.getUserName(), dataSourceDto.getPassword());
					//ftpclient.binary();
					return true;
				}
			} catch (Exception ex) {
				throw new Exception("����FTP��Ϣ::::host:" + dataSourceDto.getDsName() +"��ʱ�����\n" + ex);
			}	
		}
		return false;
	}
}
