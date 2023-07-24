package com.ztesoft.mobile.service.bo;

public class StatObject {
	//
	private int is_success;	//1:成功；0：失败
	//唯一码
	//private long uuid = -1;
	//对象创建时的timestamp
	private long init_timestamp;
	//员工ID
	private long staff_id = -1;
	//员工名称
	private String staff_name;
	//
	private String username;	//= "";
	//组织ID
	private long org_id = -1;
	//职位ID
	private long job_id = -1;
	//调用RESTful服务的ID
	private long service_id = -1;
	//服务名称
	private String service_name;
	//文件名称
	private String file_name = "";
	//WS服务平台发送接口的内容
	private String in_message;
	//WS服务接口返回平台的内容
	private String out_message;
	//客户端调用平台的毫秒数
	private long c2p_timestamp;
	//平台掉用接口的毫秒数
	private long p2i_timestamp;
	//接口返回到平台的毫秒数
	private long i2p_timestamp;
	//平台返回到客户端的毫秒数
	private long p2c_timestamp;
	
	//客户端调用平台的包大小(单位:B)
	private long c2p_packsize;
	//平台掉用接口的包大小(单位:B)
	private long p2i_packsize;
	//接口返回到平台的包大小(单位:B)
	private long i2p_packsize;
	//平台返回到客户端的包大小(单位:B)
	private long p2c_packsize;
	
/*	public long getUuid() {
		return uuid;
	}
	public void setUuid(long uuid) {
		this.uuid = uuid;
	}*/
	public int getIs_success() {
		return is_success;
	}
	public void setIs_success(int is_success) {
		this.is_success = is_success;
	}
	public long getInit_timestamp() {
		return init_timestamp;
	}
	public void setInit_timestamp(long init_timestamp) {
		this.init_timestamp = init_timestamp;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getC2p_timestamp() {
		return c2p_timestamp;
	}
	public void setC2p_timestamp(long c2p_timestamp) {
		this.c2p_timestamp = c2p_timestamp;
	}
	public long getP2i_timestamp() {
		return p2i_timestamp;
	}
	public void setP2i_timestamp(long p2i_timestamp) {
		this.p2i_timestamp = p2i_timestamp;
	}
	public long getI2p_timestamp() {
		return i2p_timestamp;
	}
	public void setI2p_timestamp(long i2p_timestamp) {
		this.i2p_timestamp = i2p_timestamp;
	}
	public long getP2c_timestamp() {
		return p2c_timestamp;
	}
	public void setP2c_timestamp(long p2c_timestamp) {
		this.p2c_timestamp = p2c_timestamp;
	}
	public long getC2p_packsize() {
		return c2p_packsize;
	}
	public void setC2p_packsize(long c2p_packsize) {
		this.c2p_packsize = c2p_packsize;
	}
	public long getP2i_packsize() {
		return p2i_packsize;
	}
	public void setP2i_packsize(long p2i_packsize) {
		this.p2i_packsize = p2i_packsize;
	}
	public long getI2p_packsize() {
		return i2p_packsize;
	}
	public void setI2p_packsize(long i2p_packsize) {
		this.i2p_packsize = i2p_packsize;
	}
	public long getP2c_packsize() {
		return p2c_packsize;
	}
	public void setP2c_packsize(long p2c_packsize) {
		this.p2c_packsize = p2c_packsize;
	}
	
	//////////////////////////////////////////////////
	public long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(long org_id) {
		this.org_id = org_id;
	}
	public long getJob_id() {
		return job_id;
	}
	public void setJob_id(long job_id) {
		this.job_id = job_id;
	}
	public long getService_id() {
		return service_id;
	}
	public void setService_id(long service_id) {
		this.service_id = service_id;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getIn_message() {
		return in_message;
	}
	public void setIn_message(String in_message) {
		this.in_message = in_message;
	}
	public String getOut_message() {
		return out_message;
	}
	public void setOut_message(String out_message) {
		this.out_message = out_message;
	}
}
