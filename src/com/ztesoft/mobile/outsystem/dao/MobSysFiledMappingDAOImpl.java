package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobSysFiledMappingDAOImpl  extends BaseDAOImpl implements MobSysFiledMappingDAO {

	private static final String TABLE_NAME = "MOBILE_SYS_FILED_MAPPING";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "FILED_MAPPING_ID:filedMappingId,MAPPING_CODE:mappingCode,SYSTEM_FILEDS:systemFileds,MAPPING_FILEDS:mappingFileds,SYSTEM_FILEDS2:systemFileds2,MAPPING_FILEDS2:mappingFileds2,SYSTEM_FILEDS3:systemFileds3,MAPPING_FILEDS3:mappingFileds3,SYSTEM_FILEDS4:systemFileds4,MAPPING_FILEDS4:mappingFileds4,SYSTEM_FILEDS5:systemFileds5,MAPPING_FILEDS5:mappingFileds5,SYSTEM_FILEDS6:systemFileds6,MAPPING_FILEDS6:mappingFileds6,SYSTEM_FILEDS7:systemFileds7,MAPPING_FILEDS7:mappingFileds7,SYSTEM_FILEDS8:systemFileds8,MAPPING_FILEDS8:mappingFileds8";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("filedMappingId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "FILED_MAPPING_ID:filedMappingId,TRANSFER_RESULT:transferResult,SYSTEM_FILEDS:systemFileds,MAPPING_FILEDS:mappingFileds,SYSTEM_FILEDS2:systemFileds2,MAPPING_FILEDS2:mappingFileds2,SYSTEM_FILEDS3:systemFileds3,MAPPING_FILEDS3:mappingFileds3,SYSTEM_FILEDS4:systemFileds4,MAPPING_FILEDS4:mappingFileds4,SYSTEM_FILEDS5:systemFileds5,MAPPING_FILEDS5:mappingFileds5,SYSTEM_FILEDS6:systemFileds6,MAPPING_FILEDS6:mappingFileds6,SYSTEM_FILEDS7:systemFileds7,MAPPING_FILEDS7:mappingFileds7,SYSTEM_FILEDS8:systemFileds8,MAPPING_FILEDS8:mappingFileds8";
		String wherePatternStr = "FILED_MAPPING_ID:filedMappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FILED_MAPPING_ID:filedMappingId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   FILED_MAPPING_ID AS filedMappingId, MAPPING_CODE AS mappingCode, SYSTEM_FILEDS AS systemFileds,  MAPPING_FILEDS AS mappingFileds,  SYSTEM_FILEDS2 AS systemFileds2,  MAPPING_FILEDS2 AS mappingFileds2,  SYSTEM_FILEDS3 AS systemFileds3,  MAPPING_FILEDS3 AS mappingFileds3,  SYSTEM_FILEDS4 AS systemFileds4,  MAPPING_FILEDS4 AS mappingFileds4,  SYSTEM_FILEDS5 AS systemFileds5,  MAPPING_FILEDS5 AS mappingFileds5, SYSTEM_FILEDS6 AS systemFileds6, MAPPING_FILEDS6 AS mappingFileds6, SYSTEM_FILEDS7 AS systemFileds7, MAPPING_FILEDS7 AS mappingFileds7, SYSTEM_FILEDS8 AS systemFileds8, MAPPING_FILEDS8 AS mappingFileds8 ");
		sqlStr.append(" FROM MOBILE_SYS_FILED_MAPPING ");
		if (!MapUtils.getString(paramMap, "mappingCode").equals("0")) {
			sqlStr.append(" WHERE MAPPING_CODE ='"+MapUtils.getString(paramMap, "mappingCode")+"'");
		}		
//		sqlStr.append(" order by MAPPING_ID asc ");
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   FILED_MAPPING_ID AS filedMappingId,  MAPPING_CODE AS mappingCode, SYSTEM_FILEDS AS systemFileds,  MAPPING_FILEDS AS mappingFileds,  SYSTEM_FILEDS2 AS systemFileds2,  MAPPING_FILEDS2 AS mappingFileds2,  SYSTEM_FILEDS3 AS systemFileds3,  MAPPING_FILEDS3 AS mappingFileds3,  SYSTEM_FILEDS4 AS systemFileds4,  MAPPING_FILEDS4 AS mappingFileds4,  SYSTEM_FILEDS5 AS systemFileds5,  MAPPING_FILEDS5 AS mappingFileds5, SYSTEM_FILEDS6 AS systemFileds6, MAPPING_FILEDS6 AS mappingFileds6, SYSTEM_FILEDS7 AS systemFileds7, MAPPING_FILEDS7 AS mappingFileds7, SYSTEM_FILEDS8 AS systemFileds8, MAPPING_FILEDS8 AS mappingFileds8  FROM MOBILE_SYS_FILED_MAPPING";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selByName(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateTrsfRs(Map paramMap) throws DataAccessException {
		String updatePatternStr = "TRANSFER_RESULT:transferResult,TRANSFER_BEFORE:transferBefore";
		String wherePatternStr = "FILED_MAPPING_ID:filedMappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

}
