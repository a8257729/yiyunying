package com.ztesoft.mobile.v2.dao.menu;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileMenuPrivDAO extends BaseDAO{
	public Map getAllParentPrivData(int moduleId,int jobId, int defaultJobId)throws Exception;
	public Map getAllHasPrivData(int jobId, int defaultJobId )throws Exception;
	public Map getAllSubPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception;
	public Map getAllRolePrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception; //��ɫȨ�޿�ѡ��
	public Map getAllHasRolePrivData(int roleId)throws Exception;                     //���н�ɫ
	public Map getAllButPrivData(int moduleId,int jobId,int specialJobId,int _jobId, int _defaultJobId,String staffId)throws Exception; //�����ò�����ťȨ��
	public Map getHasButPrivData(int jobId, int defaultJobId)throws Exception;  //ְλ�����ò���Ȩ��
	
	public Map getAllRoleButPrivData(int moduleId,int jobId,int specialJobId,int roleId,String staffId)throws Exception;  //��ɫ�����ò���Ȩ��
	public Map getHasRoleButPrivData(int roleId)throws Exception;//��ɫ���в���Ȩ��
	
	public int getAreaInfo(int areaId) throws Exception; //���area��Ϣ
}
