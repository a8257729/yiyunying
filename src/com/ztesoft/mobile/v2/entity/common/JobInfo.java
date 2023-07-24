package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午2:45
 */
public class JobInfo extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1595192416701697476L;
	
	/** jobsInfo */
    public static final String JOB_INFO_NODE = "jobInfo";
    /** defaultJob */
    public static final String DEFAULT_JOB_NODE = "defaultJob";
    /** nonDefaultJobList */
    public static final String NORMAL_JOB_LIST_NODE = "normalJobList";
    /** jobList */
    public static final String DEFAULT_JOB_LIST_NODE = "jobList";
    /** jobId */
    public static final String JOB_ID_NODE = "jobId";
    /** defaultJobId */
    public static final String DEFAULT_JOB_ID_NODE = "defaultJobId";
    /** jobName */
    public static final String JOB_NAME_NODE = "jobName";
    /** orgId */
    public static final String ORG_ID_NODE = "orgId";
    /** orgName */
    public static final String ORG_NAME_NODE = "orgName";
    /** areaId */
    public static final String AREA_ID_NODE = "areaId";
    /** basic */
    public static final String BASIC_NODE = "basic";
    
    public static final String  IS_ROOT = "isRoot";
    
    public static final String STAFF_QUERY_TYPE = "staffQueryType";

    private Long jobId;
    private String jobName;
    private Long orgId;
    private String orgName;
    private Long areaId;
    private Integer basic;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Integer getBasic() {
        return basic;
    }

    public void setBasic(Integer basic) {
        basic = basic;
    }
}

