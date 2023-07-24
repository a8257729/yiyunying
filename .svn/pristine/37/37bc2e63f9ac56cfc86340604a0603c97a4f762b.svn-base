package com.ztesoft.mobile.common.webutil.dao;

import java.util.List;

import bsh.This;

import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.iom.workorder.dto.returnDataTypeDto;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class UtilDAOImpl extends BaseDAOImpl implements UtilDAO {
	
  public  List getTextValue(String tablename,String displayFieldName,String valueFiledName)throws DataAccessException{
	  List list;
	  final String sqlStr="select "+displayFieldName +" as display,"+valueFiledName+" as value from "+tablename;
	 
	  return dynamicQueryListByMap(sqlStr);
  }
}
