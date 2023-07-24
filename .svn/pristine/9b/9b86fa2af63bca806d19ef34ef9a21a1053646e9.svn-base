package com.ztesoft.mobile.v2.hnlt.iptv.dao;

import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.vo.QuestionLibrary;

public class QuestionLibraryDaoImpl extends BaseDAOImpl implements QuestionLibraryDao{

	public List selAll() throws DataAccessException {
		String sqlStr = "SELECT  CLASS_ID,CLASS_NAME,QUESTION_TITLE,QUESTION_CONTENT,USE_CNT,HELPFUL_CNT,UNHELPFUL_CNT  FROM v_IPTV_QUESTION  ORDER BY  USE_CNT DESC ";		
		return dynamicQueryListByMap(sqlStr, null, null);
	}

	
	public List selCtxByTitle(String title) throws DataAccessException {
		String sqlStr = "SELECT QUESTION_TITLE,QUESTION_CONTENT,HELPFUL_CNT,UNHELPFUL_CNT FROM IPTV_QUESTION WHERE QUESTION_TITLE LIKE '%"+title+"%'";
		return dynamicQueryListByMap(sqlStr, null, null);
	}

	
	public List selHotByTitle(String hot_question,String class_name) throws DataAccessException {
		String sqlStr = "";
		if(class_name!=null){
			sqlStr = "SELECT QUESTION_TITLE FROM V_IPTV_QUESTION WHERE CLASS_NAME='"+class_name+"'";
		}
		
		if(hot_question!=null){
		 sqlStr = "SELECT QUESTION_TITLE,QUESTION_CONTENT FROM v_IPTV_QUESTION WHERE QUESTION_TITLE LIKE '%"+hot_question+"%'";
		}
		return dynamicQueryListByMap(sqlStr, null, null);
	}

	
	public List selClassName() throws DataAccessException {
		// TODO Auto-generated method stub
		String sqlStr = "SELECT CLASS_NAME FROM IPTV_QUESTION_CLASS ";
		return dynamicQueryListByMap(sqlStr, null, null);
	}
	
	
	
	public int updateAssessZan(String title) throws DataAccessException {
		String sqlStr = "UPDATE IPTV_QUESTION SET HELPFUL_CNT=HELPFUL_CNT+1  WHERE QUESTION_TITLE='"+title+ "'";
		return dynamicUpdateBySql(sqlStr);
	}

	
	public int updateAssessBZ(String title) throws DataAccessException {
		String sqlStr = "UPDATE IPTV_QUESTION SET UNHELPFUL_CNT=UNHELPFUL_CNT+1  WHERE QUESTION_TITLE='"+title+ "'";
		return dynamicUpdateBySql(sqlStr);
	}


	public List selInfoByStaffId(String staffId) throws DataAccessException {
		String sqlStr = "select staff_id,username,mobile_tel,acronym  from v_staff_iptv where staff_id = '"+staffId+"' and  rownum=1";
		return dynamicQueryListByMap(sqlStr, null, null);
	}


	
	
	
}
