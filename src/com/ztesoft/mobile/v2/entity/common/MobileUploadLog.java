package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

import java.util.Date;

public class MobileUploadLog extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005995011373871080L;
	
	public static final String TABLE_NAME = "MOBILE_UPLOAD_LOG";

    public static final String UPLOAD_LOG_NODE = "uploadLog";
    
    public static final String UPLOAD_LOG_ID_NODE = "uploadLogId";
    public static final String FILE_NAME_NODE = "fileName";
    public static final String FILE_PATH_NODE ="filePath";
    public static final String FILE_TYPE_NODE = "fileType";
    public static final String UPLOAD_TIME_NODE = "uploadTime";
    public static final String UPLOAD_STAFF_ID_NODE = "uploadStaffId";
    public static final String UPLOAD_STAFF_USERNAME_NODE = "uploadUsername";
    public static final String UPLOAD_STAFF_NAME_NODE = "uploadStaffName";
	
	private Long uploadLogId;
	
	private String fileName;
	
	private String filePath;
	
	private String fileType;
	
	private Date uploadTime;
	
	private Long uploadStaffId;
	
	private String uploadUsername;
	
	private String uploadStaffName;

	public Long getUploadLogId() {
		return uploadLogId;
	}

	public void setUploadLogId(Long uploadLogId) {
		this.uploadLogId = uploadLogId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getUploadStaffId() {
		return uploadStaffId;
	}

	public void setUploadStaffId(Long uploadStaffId) {
		this.uploadStaffId = uploadStaffId;
	}

	public String getUploadUsername() {
		return uploadUsername;
	}

	public void setUploadUsername(String uploadUsername) {
		this.uploadUsername = uploadUsername;
	}

	public String getUploadStaffName() {
		return uploadStaffName;
	}

	public void setUploadStaffName(String uploadStaffName) {
		this.uploadStaffName = uploadStaffName;
	}
	
}
