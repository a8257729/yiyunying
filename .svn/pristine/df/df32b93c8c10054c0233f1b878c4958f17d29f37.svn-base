package com.ztesoft.mobile.v2.hnlt.iptv.dao;

import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.vo.QuestionLibrary;

public interface QuestionLibraryDao  extends BaseDAO{
	
	
	//查询所有的问题
	public List selAll() throws DataAccessException;
	
	//根据问题名称模糊查询问题内容
	public List selCtxByTitle(String title) throws DataAccessException;
	
	//根据问题查询答案
	public List selHotByTitle(String hot_question,String class_name) throws DataAccessException;
	
	
	//查询所有问题类名
	public List selClassName() throws DataAccessException;
	
	//将评论添加到iptv_question数据表中
	public int updateAssessZan(String title) throws DataAccessException;
	
	public int updateAssessBZ(String title) throws DataAccessException;
	
	//根据staffID 查询 staffname,moblie,areaid
	public List selInfoByStaffId(String staffId) throws DataAccessException;
	
	
}
