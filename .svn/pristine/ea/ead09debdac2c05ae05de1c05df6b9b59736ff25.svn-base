package com.ztesoft.mobile.outsystem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class SysRegionDAOImpl extends BaseDAOImpl implements SysRegionDAO {
private static final String TABLE_NAME = "SYS_REGION";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "SYS_REGION_ID:sysRegionId,SYS_REGION_NAME:sysRegionName,SYS_REGION_CODE:sysRegionCode,SYS_REGION_DESC:sysRegionDesc,REMARK:remark";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("sysRegionId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SYS_REGION_ID:sysRegionId,SYS_REGION_NAME:sysRegionName,SYS_REGION_CODE:sysRegionCode,SYS_REGION_DESC:sysRegionDesc,REMARK:remark";
		String wherePatternStr = "SYS_REGION_ID:sysRegionId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "SYS_REGION_ID:sysRegionId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SYS_REGION_ID AS sysRegionId,  SYS_REGION_NAME AS sysRegionName,  SYS_REGION_CODE AS sysRegionCode,  SYS_REGION_DESC AS sysRegionDesc,  REMARK AS remark FROM SYS_REGION WHERE SYS_REGION_ID=?";
		String wherePatternStr = "SYS_REGION_ID:sysRegionId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAllRegions(Map paramMap) throws DataAccessException {
		String sqlStr ="SELECT   SYS_REGION_ID AS sysRegionId,  SYS_REGION_NAME AS sysRegionName,  SYS_REGION_CODE AS sysRegionCode,  SYS_REGION_DESC AS sysRegionDesc,  REMARK AS remark FROM SYS_REGION ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
public static void main(String[] args) {
	SysRegionDAOImpl test = new SysRegionDAOImpl();
	try {
		  Map paramMap = new HashMap();
		paramMap.put("sysRegionId", "1");
		paramMap.put("sysRegionName", "1");
		paramMap.put("sysRegionCode", "1");
		paramMap.put("sysRegionDesc", "1");
		paramMap.put("remark", "1");
		test.insert(paramMap);
//		test.update(paramMap);
//		test.delete(paramMap);
//		System.out.println(test.selById(paramMap));
//		System.out.println(test.selAll(paramMap));
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}

