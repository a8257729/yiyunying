package com.ztesoft.mobile.etl.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.etl.dao.DataSourceDAO;
import com.ztesoft.mobile.etl.dao.DataSourceDAOFactory;
import com.ztesoft.mobile.etl.dao.EtlRuleDAO;
import com.ztesoft.mobile.etl.dao.EtlRuleDAOFactory;
import com.ztesoft.mobile.etl.util.DataSourceHelper;
import com.ztesoft.mobile.etl.vo.DSDataDto;
import com.ztesoft.mobile.etl.vo.DataSourceDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetParamDto;
import com.ztesoft.mobile.etl.vo.EtlDatasetTypeDto;
import com.ztesoft.mobile.etl.vo.EtlRuleDto;
import com.ztesoft.mobile.etl.vo.EtlRulePageDto;
import com.ztesoft.mobile.etl.vo.EtlTypeDto;

public class EtlRuleManager {
	
	private final static Logger logger = Logger.getLogger(EtlRuleManager.class);
	
	private EtlRuleDAO etlRuleDAO = EtlRuleDAOFactory.getDAO();
	private DataSourceDAO dataSourceDAO = DataSourceDAOFactory.getDAO();
	 
	/**
	 * ��ҳ��ѯ���е�ETL����
	 * @param etlRuleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 * @throws SQLException
	 */
	public EtlRulePageDto queryAllEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule, int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException{
		
		return etlRuleDAO.queryAllEtlRule(etlRuleDto,isSchedule, startIndex, stepSize);
	}
	/**
	 * ��ҳ��ѯ���е�ETL��ȡ����
	 * @param etlRuleDto
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws RequiredException
	 * @throws SystemException
	 * @throws SQLException
	 */
	public EtlRulePageDto queryFetchEtlRule(EtlRuleDto etlRuleDto,boolean isSchedule, int startIndex,
			int stepSize) throws RequiredException, SystemException,
			SQLException{
		
		return etlRuleDAO.queryFetchEtlRule(etlRuleDto,isSchedule, startIndex, stepSize);
	}
	
	/**
	 * ����һ��ETL����
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlRule(EtlRuleDto etlRuleDto,EtlDatasetParamDto[] paramArr)throws SQLException, UOSException, UnsupportedEncodingException{
		
		Long etlRuleId = etlRuleDAO.createEtlRule(etlRuleDto);
		
		return etlRuleId;
	}
	/**
	 * ����һ��ETL��ȡ����
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createFetchEtlRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException, UnsupportedEncodingException{
	
		Long etlRuleId = etlRuleDAO.createFetchEtlRule(etlRuleDto);
		
		return etlRuleId;
	}
	/**
	 * ����һ��ETL��ϴ����
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlCleanRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException, UnsupportedEncodingException{
	
		Long etlRuleId = etlRuleDAO.createEtlCleanRule(etlRuleDto);
		
		return etlRuleId;
	}
	/**
	 * ����һ��ETLת������
	 * @param etlRuleDto
	 * @return
	 * @throws SQLException
	 * @throws UOSException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createEtlTransRule(EtlRuleDto etlRuleDto)throws SQLException, UOSException{
	
		Long etlRuleId = etlRuleDAO.createEtlTransRule(etlRuleDto);
		
		return etlRuleId;
	}
	
	/**
	 * ����һ��ETL����
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 * @throws UnsupportedEncodingException 
	 */
	public void updateEtlRule(EtlRuleDto etlRuleDto,EtlDatasetParamDto[] paramArr)throws SQLException, UnsupportedEncodingException, UOSException{
		etlRuleDto.setSourceDataset(StringUtil.decode(etlRuleDto.getSourceDataset()));
		etlRuleDAO.updateEtlRule(etlRuleDto);
		
	}
	/**
	 * ����һ��ETLת������
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 * @throws UnsupportedEncodingException 
	 */
	public void updateEtlTransRule(EtlRuleDto etlRuleDto)throws SQLException{
		
		etlRuleDAO.updateEtlTransRule(etlRuleDto);
		
	}
	/**
	 * ����һ��ETL��ϴ����
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 * @throws UnsupportedEncodingException 
	 */
	public void updateEtlCleanRule(EtlRuleDto etlRuleDto)throws SQLException, UnsupportedEncodingException, UOSException{
		
		etlRuleDAO.updateEtlCleanRule(etlRuleDto);
		
	}
	
	/**
	 * ����һ��ETL��ȡ����
	 * @param etlRuleDto
	 * @throws SQLException
	 * @throws UOSException 
	 * @throws UnsupportedEncodingException 
	 */
	public void updateFetchEtlRule(EtlRuleDto etlRuleDto)throws SQLException, UnsupportedEncodingException, UOSException{
	
		etlRuleDAO.updateFetchEtlRule(etlRuleDto);
		
	}
	
	/**
	 * ���������ETL��������б� <br>
	 * �������param��paramId==0��Ϊ��,�򴴽���֮�����
	 * @param etlRuleId
	 * @param paramArr
	 * @throws UOSException 
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 */
	private void saveOrUpdateDSParams(Long etlRuleId,EtlDatasetParamDto[] paramArr) throws UnsupportedEncodingException, SQLException, UOSException{
		if(etlRuleId != null){
			if(paramArr != null && paramArr.length > 0){
				for(int i=0;i< paramArr.length ;i++){
					EtlDatasetParamDto param = paramArr[i];
					param.setEtlRuleId(etlRuleId);
					param.setQuerySql(StringUtil.decode(param.getQuerySql()));
					if(StringUtil.isNull(param.getParamId()) || param.getParamId().longValue() ==0){
						createDSParam(param);
					}else{
						updateDSParam(param);
					}
				}
			}
		}
	}
	
	
	/**
	 * ����ETL�����ݼ�������
	 * @param etlRuleDto
	 * @throws SQLException
	 */
	public void updateEtlRuleDs(EtlRuleDto etlRuleDto)throws SQLException{
		etlRuleDAO.updateEtlRuleDs(etlRuleDto);
	}
	
	/**
	 * ɾ��һ��ETL����
	 * @param etlRuleId
	 * @throws SQLException
	 */
	public void delEtlRule(Long etlRuleId)throws SQLException{
		etlRuleDAO.delEtlRule(etlRuleId);
	}
	
	/**
	 * ����ID��ѯһ��ETL�����¼
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	public EtlRuleDto findEtlRuleById(Long etlRuleId)throws SQLException{
		return etlRuleDAO.findEtlRuleById(etlRuleId);
	}
	
	/**
	 * ��ѯ���е�ETL����
	 * @return
	 * @throws SQLException
	 */
	public EtlTypeDto[] findAllEtlType()throws SQLException{
		return etlRuleDAO.findAllEtlType();
	}
	
	/**
	 * ��ѯ���е�ETL���ݼ�������
	 * @return
	 * @throws SQLException
	 */
	public EtlDatasetTypeDto[] findAllEtlDatasetType()throws SQLException{
		return etlRuleDAO.findAllEtlDatasetType();
	}

	/**
	 * ͨ��ID�Ų�ѯ����
	 * @param paramId
	 * @return
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public EtlDatasetParamDto findDSParamById(Long paramId)throws SQLException, RequiredException{
		return etlRuleDAO.findDSParamById(paramId);
	}
	
	/**
	 * ͨ��ETL����ID��ȡ���ݼ������б�
	 * @param etlRuleId
	 * @return
	 * @throws SQLException
	 */
	public EtlDatasetParamDto[] findDSParamByRuleId(Long etlRuleId)throws SQLException,RequiredException{
		return etlRuleDAO.findDSParamByRuleId(etlRuleId);
	}
	
	/**
	 * ����һ�����ݼ�����
	 * @param etlDatasetParamDto
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public Long createDSParam(EtlDatasetParamDto etlDatasetParamDto)throws SQLException, UOSException, UnsupportedEncodingException{
		etlDatasetParamDto.setQuerySql(StringUtil.decode(etlDatasetParamDto.getQuerySql()));
		return etlRuleDAO.createDSParam(etlDatasetParamDto);
	}
	
	/**
	 * �޸�һ�����ݼ�����
	 * @param etlDatasetParamDto
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public void updateDSParam(EtlDatasetParamDto etlDatasetParamDto)throws SQLException, UnsupportedEncodingException{
		etlDatasetParamDto.setQuerySql(StringUtil.decode(etlDatasetParamDto.getQuerySql()));
		etlRuleDAO.updateDSParam(etlDatasetParamDto);
	}
	
	/**
	 * ɾ��һ�����ݼ�����
	 * @param etlDatasetParamDto
	 * @throws SQLException
	 */
	public void delDSParamById(Long paramId)throws SQLException,RequiredException{
		etlRuleDAO.delDSParamById(paramId);
	}
	
	public DSDataDto execDatasetSql(Long etlRuleId,int rowNum) throws RequiredException, SQLException{
		EtlRuleDto dto = etlRuleDAO.findEtlRuleById(etlRuleId);
		
		//���û��תԴ����Դ��ID,�����ID�����ݿ���ȥ��ѯ��ETL�������ϸ��Ϣ
		if(dto != null){
			DataSourceDto dsDto = dataSourceDAO.findDataSourceById(dto.getSourceDsId());
			EtlDatasetParamDto[] paramArr = etlRuleDAO.findDSParamByRuleId(etlRuleId);
			if(dsDto != null){
				return DataSourceHelper.findData(dsDto, dto, paramArr, rowNum);
			}
		}
		return null;
		
	}
	
	public DSDataDto execDatasetSql(Long dsId,String exeSql,EtlDatasetParamDto[] paramArr,int rowNum) throws RequiredException, SQLException{
		DataSourceDto dsDto = dataSourceDAO.findDataSourceById(dsId);
		if(dsDto != null){
			return DataSourceHelper.findData(dsDto,exeSql, paramArr, rowNum);
		}
		return null;
	}
	
	/**
	 * ���ETLҵ����������Ƿ�Ψһ
	 * @param etlRuleName
	 * @return
	 * @throws SQLException
	 */
	public boolean isEtlRuleNameOnlyOne(String etlRuleName,Long etlRuleId)throws SQLException{
		return etlRuleDAO.isEtlRuleNameOnlyOne(etlRuleName,etlRuleId);
	}
	
	
}
