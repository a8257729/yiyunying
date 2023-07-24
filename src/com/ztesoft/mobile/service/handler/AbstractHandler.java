package com.ztesoft.mobile.service.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.service.bo.StatObject;

public abstract class AbstractHandler implements Handler {
	
	private Map rtMap = new HashMap();
	
	private static final String CONF_PATH = "cfg/monitor.properties";

	private static final String MON_LOG_PATH;
	
	static {
		String path = getPath();
		String separator = System.getProperty("file.separator");
		if("/".equals(separator)) {  //Linux/Unix
			MON_LOG_PATH = (path == null ? path:"/tmp/mon_logs/");
		} else if("\\".equals(separator)){	//Windows
			MON_LOG_PATH = (path == null ? path:"E:\\MobileDeploy\\MOBILE\\mon_logs\\");
		} else {
			MON_LOG_PATH = "N/A";
		}
		System.out.println("监控日志文件的存放目录是： " + MON_LOG_PATH);
	}
	
	protected String file_name;	//TODO 跟UUID一致？
	
	protected StatObject statObj;
	
	/**
	 * 子类的构造方法必须调用父类的
	 */
	public AbstractHandler() {
		//生成UUID
		long uuid = Math.abs(UUID.randomUUID().getMostSignificantBits());
		//文件名与UUID值一直，这样可以保持文件名的唯一性
		this.file_name = String.valueOf(uuid) + ".json";
		
		this.statObj = new StatObject();
		//this.statObj.setUuid(uuid);
		this.statObj.setFile_name(this.file_name);
		this.statObj.setInit_timestamp(System.currentTimeMillis());
		
	}
	
	/**
	 * 必须实现的
	 * @param paramMap
	 */
	protected abstract void processHandle(Map paramMap) throws Exception;

	private void initStatObject(Map paramMap) {
		String jsonPara = MapUtils.getString(paramMap, "params", "{}");
		System.out.println("传入参数: " + jsonPara);
		//转JSON
		JSONObject json = JSONObject.fromObject(jsonPara);
		
		if(null != json) {
			this.statObj.setStaff_id(json.optLong("StaffId", -1L));
			this.statObj.setJob_id(json.optLong("JobId", -1L));
			this.statObj.setOrg_id(json.optLong("OrgId", -1L));
			this.statObj.setUsername(json.optString("UseName", null));
		}
		
		this.statObj.setService_id(MapUtils.getLongValue(paramMap, "restServiceId", -1L));
		this.statObj.setService_name(MapUtils.getString(paramMap, "restServiceName"));
	}
	
	protected void beforeHandle(Map paramMap){
		initStatObject(paramMap);
		//
		System.out.println("调用处理前的方法...");
		this.countC2p_timestamp();
		long packsize = 0L;
		if(null != paramMap) {
			packsize = this.getByteLen(MapUtils.getString(paramMap, "params", null));
		}
		this.countC2p_packsize(packsize);
	}
	
	protected void afterHandle(Map paramMap){
		System.out.println("调用处理后的方法...");
		this.countP2c_timestamp();
		long packsize = 0L;
		if(null != paramMap) {
			packsize = this.getByteLen(MapUtils.getString(paramMap, "params", null));
		}
		this.countP2c_packsize(packsize);
	}
	
	public void finalHandle(Map paramMap) {
		//（1）写文件到指定的目录下
		System.out.println("处理完毕后的方法...");
		this.writeFile();
	}
	
	/**
	 * 调用WS之前，Map里面必须有requestXml；发送的xml报文的大小是基于它来计算的。
	 * @throws Exception
	 */
	protected void beforeCallWS(Map paramMap) {
		//
		System.out.println("调用WS服务之前的方法");
		this.countP2i_timestamp();
		long packsize = 0L;
		if(null != paramMap) {
			packsize = this.getByteLen(MapUtils.getString(paramMap, "requestXml", null));
			this.statObj.setOut_message(MapUtils.getString(paramMap, "requestXml", null));
		}
		this.countP2i_packsize(packsize);
	}	
	
	protected void callWS(Map paramMap) {
		
	}	
	
	/**
	 * 调用WS之后，ap里面必须有responseXml；发送的xml报文的大小是基于它来计算的。
	 * @throws Exception
	 */	
	protected void afterCallWS(Map paramMap) {
		System.out.println("调用WS服务之后的方法");
		this.countI2p_timestamp();
		long packsize = 0L;
		if(null != paramMap) {
			packsize = this.getByteLen(MapUtils.getString(paramMap, "responseXml", null));
			this.statObj.setIn_message(MapUtils.getString(paramMap, "responseXml", null));
		}
		this.countI2p_packsize(packsize);
	}
	
	public void handle(Map paramMap)  throws Exception {
		//调用处理前的方法
		beforeHandle(paramMap);
		//调用处理方法
		processHandle(paramMap);
		//调用处理后的方法
		afterHandle(paramMap);
		//
		finalHandle(paramMap);

		this.rtMap.putAll(paramMap);
	}
	
	public Map getResultMap() {
		return this.rtMap;
	}
	
	/**
	 * 记录客户端到平台的毫秒数
	 */
	protected void countC2p_timestamp() {
		long c2p_timestamp = System.currentTimeMillis();
		this.statObj.setC2p_timestamp(c2p_timestamp);
	}
	/**
	 * 记录客户端到接口的毫秒数
	 */	
	protected void countP2i_timestamp() {
		long p2i_timestamp = System.currentTimeMillis();
		this.statObj.setP2i_timestamp(p2i_timestamp);
	}
	/**
	 * 记录接口返回平台的毫秒数
	 */
	protected void countI2p_timestamp() {
		long i2p_timestamp = System.currentTimeMillis();
		this.statObj.setI2p_timestamp(i2p_timestamp);
	}
	/**
	 * 记录平台返回客户端的毫秒数
	 */	
	protected void countP2c_timestamp() {
		long p2c_timestamp = System.currentTimeMillis();
		this.statObj.setP2c_timestamp(p2c_timestamp);
	}

	/**
	 * 记录客户端到平台的包大小
	 */
	protected void countC2p_packsize(long packsize) {
		this.statObj.setC2p_packsize(packsize);
	}
	/**
	 * 记录客户端到接口的包大小
	 */	
	protected void countP2i_packsize(long packsize) {
		this.statObj.setP2i_packsize(packsize);
	}
	/**
	 * 记录接口返回平台的包大小
	 */
	protected void countI2p_packsize(long packsize) {
		this.statObj.setI2p_packsize(packsize);
	}
	/**
	 * 记录平台返回客户端的包大小
	 */	
	protected void countP2c_packsize(long packsize) {
		this.statObj.setP2c_packsize(packsize);
	}
	
	protected String toXmlStr() {
		//自己手工拼XML字符串
		StringBuffer xmlStrBuf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		xmlStrBuf.append("<data>")
					//.append("<uuid>").append(this.statObj.getUuid()).append("</uuid>")
					.append("<init_timestamp>").append(this.statObj.getInit_timestamp()).append("</init_timestamp>")
					.append("<staff_id>").append(this.statObj.getStaff_id()).append("</staff_id>")
					.append("<org_id>").append(this.statObj.getOrg_id()).append("</org_id>")
					.append("<job_id>").append(this.statObj.getJob_id()).append("</job_id>")
					.append("<service_id>").append(this.statObj.getService_id()).append("</service_id>")
					.append("<file_name>").append(this.statObj.getFile_name()).append("</file_name>")
					.append("<c2p_timestamp>").append(this.statObj.getC2p_timestamp()).append("</c2p_timestamp>")
					.append("<p2i_timestamp>").append(this.statObj.getP2i_timestamp()).append("</p2i_timestamp>")
					.append("<i2p_timestamp>").append(this.statObj.getI2p_timestamp()).append("</i2p_timestamp>")
					.append("<p2c_timestamp>").append(this.statObj.getP2c_timestamp()).append("</p2c_timestamp>")
					.append("<c2p_packsize>").append(this.statObj.getC2p_packsize()).append("</c2p_packsize>")
					.append("<p2i_packsize>").append(this.statObj.getP2i_packsize()).append("</p2i_packsize>")
					.append("<i2p_packsize>").append(this.statObj.getI2p_packsize()).append("</i2p_packsize>")
					.append("<p2c_packsize>").append(this.statObj.getP2c_packsize()).append("</p2c_packsize>")
					.append("<in_message>").append(this.statObj.getIn_message()).append("</i2p_packsize>")
					.append("<out_message>").append(this.statObj.getOut_message()).append("</p2c_packsize>")				
				.append("</data>");
		return xmlStrBuf.toString();
	}
	
	protected String toJsonStr() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("init_timestamp", this.statObj.getInit_timestamp());
		jsonObj.put("staff_id", this.statObj.getStaff_id());
		jsonObj.put("staff_name", this.statObj.getStaff_name());
		jsonObj.put("org_id", this.statObj.getOrg_id());
		jsonObj.put("username", this.statObj.getUsername());
		jsonObj.put("job_id", this.statObj.getJob_id());
		jsonObj.put("service_id", this.statObj.getService_id());
		jsonObj.put("service_name", this.statObj.getService_name());
		jsonObj.put("file_name", this.statObj.getFile_name());
		
		jsonObj.put("c2p_timestamp", this.statObj.getC2p_timestamp());
		jsonObj.put("p2i_timestamp", this.statObj.getP2i_timestamp());
		jsonObj.put("i2p_timestamp", this.statObj.getI2p_timestamp());
		jsonObj.put("p2c_timestamp", this.statObj.getP2c_timestamp());
		
		jsonObj.put("c2p_packsize", this.statObj.getC2p_packsize());
		jsonObj.put("p2i_packsize", this.statObj.getP2i_packsize());
		jsonObj.put("i2p_packsize", this.statObj.getI2p_packsize());
		jsonObj.put("p2c_packsize", this.statObj.getP2c_packsize());
		
		jsonObj.put("in_message", "$#$" + this.statObj.getIn_message() +"$#$");
		jsonObj.put("out_message","$#$" + this.statObj.getOut_message() +"$#$");
		
		//
		//System.out.println("json Str: " + jsonObj.toString());
		return jsonObj.toString();
	}

	/**
	 * 写到指定路径的目录
	 * @param path
	 */
	public void writeFile(String path) {
		if(StringUtils.isBlank(path)) {
			path = MON_LOG_PATH;
		}
		//String xmlStr = this.toXmlStr();
		String jsonStr = this.toJsonStr();
		File file = new File(path + this.file_name);
		try {
			FileUtils.writeStringToFile(file, jsonStr, "UTF-8");
			System.out.println(MON_LOG_PATH + "写入文件[" + this.file_name + "]");
		} catch (IOException e) {
			System.out.println("生成文件报错...");
			e.printStackTrace();
		}
	}	
	
	/**
	 * 写到默认目录
	 */
	public void writeFile() {
		this.writeFile(MON_LOG_PATH);
	}
	
	//获取字节码数
	private long getByteLen(String str) {
		byte[] strByte = new byte[0];
		if(StringUtils.isNotBlank(str)){
			strByte = str.getBytes();
		}
		return strByte.length;
	}

	private String getStaffNameById(Long staffId) {
		return null;
	}
	
	public static String getPath() {
		Properties prop = new Properties();
		try{
			InputStream in = AbstractHandler.class.getClassLoader().getResourceAsStream("cfg/monitor.properties");
			if(null == in) {
				throw new IOException(CONF_PATH+"配置文件未找到");
			} else {
				prop.load(in);
			}
		} catch(IOException e) {
			System.out.println(CONF_PATH+"配置文件未找到...");
			e.printStackTrace();
		}
		return prop.getProperty("MON_LOG_PATH");
	}
}
