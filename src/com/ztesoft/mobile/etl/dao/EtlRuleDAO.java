package com.ztesoft.mobile.etl.dao;

import java.sql.SQLException;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.etl.vo.EtlDatasetParamDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetTypeDto;
import com.ztesoft.mobile.etl.vo.EtlRuleDto;
import com.ztesoft.mobile.etl.vo.EtlRulePageDto;
import com.ztesoft.mobile.etl.vo.EtlTypeDto;

public interface EtlRuleDAO {
	
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
	EtlRulePageDto queryAllEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule, int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException;
	
	/**
	 * 创建一条ETL规则
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 */
	Long createEtlRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException;
	
	/**
	 * 创建一条ETL抽取规则
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 */
	Long createFetchEtlRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException;
	/**
	 * 创建一条ETL清洗规则
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 */
	public Long createEtlCleanRule(EtlRuleDto etlRuleDto) throws SQLException,UOSException;
	
	/**
	 * 更新一条ETL规则
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	void updateEtlRule(EtlRuleDto etlRuleDto)throws SQLException;
	
	public void updateEtlCleanRule(EtlRuleDto etlRuleDto) throws SQLException ;
	
	/**
	 * 更新ETL的数据集和类型
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	void updateEtlRuleDs(EtlRuleDto etlRuleDto)throws SQLException;
	
	/**
	 * 删除一条ETL规则
	 * @param etlRuleId
	 * @throws SQLException
	 */
	void delEtlRule(Long etlRuleId)throws SQLException;
	
	/**
	 * 根据ID查询一条ETL规则记录
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	EtlRuleDto findEtlRuleById(Long etlRuleId)throws SQLException;
	
	/**
	 * 查询所有的ETL类型
	 * @return
	 * @throws SQLException
	 */
	EtlTypeDto[] findAllEtlType()throws SQLException;
	
	/**
	 * 查询所有的ETL数据集的类型
	 * @return
	 * @throws SQLException
	 */
	EtlDatasetTypeDto[] findAllEtlDatasetType()throws SQLException;
	
	/**
	 * 通过ID号查询参数
	 * @param paramId
	 * @return
	 * @throws SQLException
	 * @throws RequiredException
	 */
	EtlDatasetParamDto findDSParamById(Long paramId)throws SQLException, RequiredException;
	
	/**
	 * 通过ETL规则ID获取数据集参数列表
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	EtlDatasetParamDto[] findDSParamByRuleId(Long etlRuleId)throws SQLException,RequiredException;
	
	/**
	 * 创建一条数据集参数
	 * @param etlDatasetParamDto
	 * @return
	 * @throws SQLException
	 */
	Long createDSParam(EtlDatasetParamDto etlDatasetParamDto)throws SQLException, UOSException;
	
	/**
	 * 修改一条数据集参数
	 * @param etlDatasetParamDto
	 * @throws SQLException
	 */
	void updateDSParam(EtlDatasetParamDto etlDatasetParamDto)throws SQLException;
	
	/**
	 * 删除一条数据集参数
	 * @param etlDatasetParamDto
	 * @throws SQLException 
	 */
	void delDSParamById(Long paramId)throws SQLException,RequiredException;
	
	/**
	 * 检测ETL业务规则名称是否唯一
	 * @param etlRuleName
	 * @return
	 * @throws SQLException
	 */
	boolean isEtlRuleNameOnlyOne(String etlRuleName,Long etlRuleId)throws SQLException;
	
	public EtlRulePageDto queryFetchEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule,
			int startIndex, int stepSize) throws RequiredException,
			SystemException, SQLException ;
	public void updateFetchEtlRule(EtlRuleDto etlRuleDto) throws SQLException ;
	
	public Long createEtlTransRule(EtlRuleDto etlRuleDto) throws SQLException,
	UOSException;
	public void updateEtlTransRule(EtlRuleDto etlRuleDto) throws SQLException;
}
