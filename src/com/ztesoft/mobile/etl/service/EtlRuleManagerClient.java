package com.ztesoft.mobile.etl.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.etl.vo.DSDataDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetParamDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetTypeDto;
import com.ztesoft.mobile.etl.vo.EtlRuleDto;
import com.ztesoft.mobile.etl.vo.EtlRulePageDto;
import com.ztesoft.mobile.etl.vo.EtlTypeDto;

public class EtlRuleManagerClient {
	
	private final static Logger logger = Logger.getLogger(EtlRuleManagerClient.class);
	private EtlRuleManager etlRuleManager = null;

	public EtlRuleManagerClient() {
		etlRuleManager = new EtlRuleManager();
	}
	 
	/**
	 * 分页查询所有的ETL规则
	 * @param etlRuleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 * @throws SQLException
	 */
	public EtlRulePageDto queryAllEtlRule(EtlRuleDto etlRuleDto, int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException{
		
		return etlRuleManager.queryAllEtlRule(etlRuleDto,false,startIndex, stepSize);
	}
	
	/**
	 * 分页查询所有的ETL规则
	 * @param etlRuleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 * @throws SQLException
	 */
	public EtlRulePageDto queryFetchEtlRule(EtlRuleDto etlRuleDto, int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException{
		
		return etlRuleManager.queryFetchEtlRule(etlRuleDto,false,startIndex, stepSize);
	}
	/**
	 * 分页查询所有的ETL规则
	 * @param etlRuleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 * @throws SQLException
	 */
	public EtlRulePageDto queryAllScheEtlRule(EtlRuleDto etlRuleDto,int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException{
		
		return etlRuleManager.queryAllEtlRule(etlRuleDto,true,startIndex, stepSize);
	}
	
	/**
	 * 创建一条ETL规则
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlRule(EtlRuleDto etlRuleDto,EtlDatasetParamDto[] paramArr)throws SQLException, UOSException, UnsupportedEncodingException{
		return etlRuleManager.createEtlRule(etlRuleDto,paramArr);
	}
	/**
	 * 创建一条ETL抽取规则
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createFetchEtlRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException, UnsupportedEncodingException{
		return etlRuleManager.createFetchEtlRule(etlRuleDto);
	}
	/**
	 * 创建一条ETL清洗规则
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlCleanRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException, UnsupportedEncodingException{
		return etlRuleManager.createEtlCleanRule(etlRuleDto);
	}
	/**
	 * 创建一条ETL转换规则
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlTransRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException{
		return etlRuleManager.createEtlTransRule(etlRuleDto);
	}
	
	/**
	 * 更新一条ETL规则
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public boolean updateEtlRule(EtlRuleDto etlRuleDto,EtlDatasetParamDto[] paramArr){
		try{
			etlRuleManager.updateEtlRule(etlRuleDto,paramArr);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("更新ETL规则时出错,详细信息: " + ex.getMessage());
		}
		return false;		
	}
	
	/**
	 * 更新一条ETL规则
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public boolean updateFetchEtlRule(EtlRuleDto etlRuleDto){
		try{
			etlRuleManager.updateFetchEtlRule(etlRuleDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("更新ETL抽取规则时出错,详细信息: " + ex.getMessage());
		}
		return false;		
	}
	/**
	 * 更新一条ETL规则
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public boolean updateEtlTransRule(EtlRuleDto etlRuleDto){
		try{
			etlRuleManager.updateEtlTransRule(etlRuleDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("更新ETL转换规则时出错,详细信息: " + ex.getMessage());
		}
		return false;		
	}
	/**
	 * 更新一条ETL清洗规则
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public boolean updateEtlCleanRule(EtlRuleDto etlRuleDto){
		try{
			etlRuleManager.updateEtlCleanRule(etlRuleDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("更新ETL抽取规则时出错,详细信息: " + ex.getMessage());
		}
		return false;		
	}
	
	/**
	 * 更新ETL的数据集和类型
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public boolean updateEtlRuleDs(EtlRuleDto etlRuleDto)throws SQLException{
		try{
			etlRuleManager.updateEtlRuleDs(etlRuleDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("更新ETL规则数据集时出错,详细信息: " + ex.getMessage());
		}
		return false;		
	}	
	
	/**
	 * 删除一条ETL规则
	 * @param etlRuleId
	 * @throws SQLException
	 */
	public boolean delEtlRule(Long etlRuleId){
		try{
			etlRuleManager.delEtlRule(etlRuleId);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("删除ETL规则时出错,详细信息: " + ex.getMessage());
		}
		return false;
	}
	
	/**
	 * 根据ID查询一条ETL规则记录
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	public EtlRuleDto findEtlRuleById(Long etlRuleId)throws SQLException{
		return etlRuleManager.findEtlRuleById(etlRuleId);
	}
	
	/**
	 * 查询所有的ETL类型
	 * @return
	 * @throws SQLException
	 */
	public EtlTypeDto[] findAllEtlType()throws SQLException{
		return etlRuleManager.findAllEtlType();
	}
	
	/**
	 * 查询所有的ETL数据集的类型
	 * @return
	 * @throws SQLException
	 */
	public EtlDatasetTypeDto[] findAllEtlDatasetType()throws SQLException{
		return etlRuleManager.findAllEtlDatasetType();
	}

	/**
	 * 通过ID号查询参数
	 * @param paramId
	 * @return
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public EtlDatasetParamDto findDSParamById(Long paramId)throws SQLException, RequiredException{
		return etlRuleManager.findDSParamById(paramId);
	}
	
	
	/**
	 * 通过ETL规则ID获取数据集参数列表
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	public EtlDatasetParamDto[] findDSParamByRuleId(Long etlRuleId)throws SQLException,RequiredException{
		return etlRuleManager.findDSParamByRuleId(etlRuleId);
	}
	
	/**
	 * 创建一条数据集参数
	 * @param etlDatasetParamDto
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createDSParam(EtlDatasetParamDto etlDatasetParamDto)throws SQLException, UOSException, UnsupportedEncodingException{
		return etlRuleManager.createDSParam(etlDatasetParamDto);
	}
	
	/**
	 * 修改一条数据集参数
	 * @param etlDatasetParamDto
	 * @throws SQLException
	 */
	public boolean updateDSParam(EtlDatasetParamDto etlDatasetParamDto){
		try{
			etlRuleManager.updateDSParam(etlDatasetParamDto);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除一条数据集参数
	 * @param etlDatasetParamDto
	 * @throws SQLException
	 */
	public boolean delDSParamById(Long paramId){
		try{
			etlRuleManager.delDSParamById(paramId);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public DSDataDto execDatasetSql(Long dsId,String exeSql,EtlDatasetParamDto[] paramArr,int rowNum) throws RequiredException, SQLException{
		logger.debug("sql ===============================: : " + StringUtil.decode(exeSql));
		return etlRuleManager.execDatasetSql(dsId,StringUtil.decode(exeSql),paramArr, rowNum);
	}
	
	/**
	 * 检测ETL业务规则名称是否唯一
	 * @param etlRuleName
	 * @return
	 * @throws SQLException
	 */
	public boolean isEtlRuleNameOnlyOne(String etlRuleName,Long etlRuleId)throws SQLException{
		return etlRuleManager.isEtlRuleNameOnlyOne(etlRuleName,etlRuleId);
	}
	
}
