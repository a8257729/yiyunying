package com.ztesoft.mobile.v2.hnlt.iptv.service;

import java.util.List;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.dao.QuestionLibraryDao;
import com.ztesoft.mobile.v2.hnlt.iptv.dao.QuestionLibraryDaoImpl;
import com.ztesoft.mobile.v2.hnlt.iptv.vo.QuestionLibrary;

public class QuestionLibraryServiceImpl implements QuestionLibraryService{

	QuestionLibraryDao qldao = new QuestionLibraryDaoImpl();
	public List selAll() throws DataAccessException {		
		return qldao.selAll();
	}

	
	public List selCtxByTitle(String title) throws DataAccessException {		
		return qldao.selCtxByTitle(title);
	}


	public List selHotByTitle( String hot_question,String class_name) throws DataAccessException {		
		return qldao.selHotByTitle(hot_question, class_name);
	}
	
	
	public List selClassName() throws DataAccessException {
		// TODO Auto-generated method stub
		return qldao.selClassName();
	}
	
	
	
	public int updateAssessZan(String title) throws DataAccessException {		
		return qldao.updateAssessZan(title);
	}

	public int updateAssessBZ(String title) throws DataAccessException {
		return qldao.updateAssessBZ(title);
	}


	public List selInfoByStaffId(String staffId) throws DataAccessException {
		
		return qldao.selInfoByStaffId(staffId);
	}


	


	
	
	
}
