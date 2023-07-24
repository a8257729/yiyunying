package com.ztesoft.eoms.oaas.privilege.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAO;

public interface PrivDAO extends BaseDAO {
	public List findPrivsJobNotHold(int jobId, int defaultJobId,int parentId) throws SQLException ;
	public List findPrivsJobNotHoldnew(int jobId1, int jobId2, int defaultJobId,int parentId) throws SQLException ;
	public List findPrivsJobNotHold(int jobId1, int jobId2, int jobId3, int defaultJobId,int parentId) throws SQLException ;
	public Map findPrivsByJob(int jobId,int parentId) throws SQLException ;
	public Map findTopPrivClassesMap() throws SQLException ;
	public List findSubPrivClassesMap(int parentId) throws SQLException ;
	public List findPrivClassesList(int parentId) throws SQLException ;
	public List findPrivByClass(int privClassId) throws SQLException ;
	public List findJobPrivTreeList(int parentId,int jobId) throws SQLException ;
	public List findAllJobPrivTreeList(int parentId,int jobId) throws SQLException ;
}
