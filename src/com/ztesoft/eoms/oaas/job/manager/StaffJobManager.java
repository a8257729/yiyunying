package com.ztesoft.eoms.oaas.job.manager;

import java.sql.SQLException;
import java.util.Map;
import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.vo.Job;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.oaas.job.dao.JobSelDAO;
import com.ztesoft.eoms.oaas.job.impl.JobSelDAOImpl;
import com.ztesoft.eoms.oaas.job.util.JobJsonUtil;


/**
 * 人员管理配置职位
 * @author FangLi
 *
 */
public class StaffJobManager {
	private static final long serialVersionUID = 3587760284114455925L;
	
	
	/**
     * 根据顶级组织查询职位
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 职位权限数组
     */
    public static String findJobsNotHoldJson(String orgPathCode ,int orgId) throws Exception {
        try {
        	
            Job[] jobs = getJobSelDAO().getJobByPathCode(orgPathCode);
            if (jobs.length == 0) {
                return JobJsonUtil.getOrgSelJson(null, jobs,orgId,"可配置职位");
            }
            Map jobOrgMap = getJobSelDAO().getOrgByPathCode(orgPathCode);

            return JobJsonUtil.getOrgSelJson(jobOrgMap, jobs ,orgId,"可配置职位");
        }
        catch (SQLException ex) {
        	OAASError error = new OAASError(OAASError.STAFF_JOB_ASSIGH_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * 查询人员已有职位
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 职位权限数组
     */
    public static String findHoldJobsJson(String orgPathCode ,int orgId, int staffId) throws Exception {
        try {
        	
            Job[] jobs = getJobSelDAO().findByStaff(staffId);
            if (jobs.length == 0) {
                return JobJsonUtil.getOrgSelJson(null, jobs,orgId,"人员特有职位");
            }
            Map jobOrgMap = getJobSelDAO().getOrgByPathCode(orgPathCode);
            return JobJsonUtil.getOrgSelJson(jobOrgMap, jobs ,orgId,"人员特有职位");
        }
        catch (SQLException ex) {
        	OAASError error = new OAASError(OAASError.STAFF_JOB_ASSIGH_ERROR);
            throw new OAASException(error, ex);
        }
    }

	private static JobSelDAO getJobSelDAO() {
        String daoName = JobSelDAOImpl.class.getName();
        return (JobSelDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
