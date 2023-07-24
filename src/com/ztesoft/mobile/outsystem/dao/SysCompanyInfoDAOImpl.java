package com.ztesoft.mobile.outsystem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class SysCompanyInfoDAOImpl extends BaseDAOImpl implements SysCompanyInfoDAO {
private static final String TABLE_NAME = "SYS_COMPANY_INFO";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "COMPANY_ID:companyId,COMPANY_NAME:companyName,COMPANY_CODE:companyCode,COMPANY_ADDRESS:companyAddress,COMPANY_PHONE:companyPhone,MANAGER_NAME:managerName,MANAGER_PHONE:managerPhone,LOCALE_NUM:localeNum,REMARK:remark";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("companyId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "COMPANY_ID:companyId,COMPANY_NAME:companyName,COMPANY_CODE:companyCode,COMPANY_ADDRESS:companyAddress,COMPANY_PHONE:companyPhone,MANAGER_NAME:managerName,MANAGER_PHONE:managerPhone,LOCALE_NUM:localeNum,REMARK:remark";
		String wherePatternStr = "COMPANY_ID:companyId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "COMPANY_ID:companyId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   COMPANY_ID AS companyId,  COMPANY_NAME AS companyName,  COMPANY_CODE AS companyCode,  COMPANY_ADDRESS AS companyAddress,  COMPANY_PHONE AS companyPhone,  MANAGER_NAME AS managerName,  MANAGER_PHONE AS managerPhone,  LOCALE_NUM AS localeNum,  REMARK AS remark FROM SYS_COMPANY_INFO WHERE COMPANY_ID=?";
		String wherePatternStr = "COMPANY_ID:companyId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   COMPANY_ID AS companyId,  COMPANY_NAME AS companyName,  COMPANY_CODE AS companyCode,  COMPANY_ADDRESS AS companyAddress,  COMPANY_PHONE AS companyPhone,  MANAGER_NAME AS managerName,  MANAGER_PHONE AS managerPhone,  LOCALE_NUM AS localeNum,  REMARK AS remark FROM SYS_COMPANY_INFO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
public static void main(String[] args) {
	SysCompanyInfoDAOImpl test = new SysCompanyInfoDAOImpl();
	try {
		  Map paramMap = new HashMap();
		paramMap.put("companyId", "1");
		paramMap.put("companyName", "1");
		paramMap.put("companyCode", "1");
		paramMap.put("companyAddress", "1");
		paramMap.put("companyPhone", "1");
		paramMap.put("managerName", "1");
		paramMap.put("managerPhone", "1");
		paramMap.put("localeNum", "1");
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

