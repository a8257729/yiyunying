package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: ÏÂÎç2:31
 */
public class VersionInfo extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3463461075812301185L;
	
	/** versionInfo */
	public static final String VERSION_INFO_NODE = "versionInfo";
	
	private Integer versionCode;
    private String versionName;
    private String downloadUrl;
    private String updateLog;
    private String updateTime;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
    
}
