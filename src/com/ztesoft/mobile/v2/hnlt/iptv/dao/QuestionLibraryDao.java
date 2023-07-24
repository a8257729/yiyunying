package com.ztesoft.mobile.v2.hnlt.iptv.dao;

import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.vo.QuestionLibrary;

public interface QuestionLibraryDao  extends BaseDAO{
	
	
	//��ѯ���е�����
	public List selAll() throws DataAccessException;
	
	//������������ģ����ѯ��������
	public List selCtxByTitle(String title) throws DataAccessException;
	
	//���������ѯ��
	public List selHotByTitle(String hot_question,String class_name) throws DataAccessException;
	
	
	//��ѯ������������
	public List selClassName() throws DataAccessException;
	
	//��������ӵ�iptv_question���ݱ���
	public int updateAssessZan(String title) throws DataAccessException;
	
	public int updateAssessBZ(String title) throws DataAccessException;
	
	//����staffID ��ѯ staffname,moblie,areaid
	public List selInfoByStaffId(String staffId) throws DataAccessException;
	
	
}
