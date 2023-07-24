package com.ztesoft.mobile.v2;

import java.io.Serializable;

/**
 * 注：4000以上才用于业务上
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午1:50
 */
public class BaseConstants implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2222202071544547841L;
	
	public static final String STAFF_INFO_KEY = "STAFF_INFO";
	
	public static final String NOTI_API_KEY = "3876051811";
	
	public static final String HOST_IP = "http://220.196.38.12:9001";	// 服务器IP，供推送用
	
	public interface SSOType {
		/** 无验证单点登录 */
		public static final Integer NO_AUTH_SSO = 1;
	}
	
	public interface AppStatus {
        /** 安装操作 */
        public static final Integer INSTALL = 1;
        /** 卸载操作 */
        public static final Integer UNINSTALL = 0;
	}

    /** 操作类型 */
    public interface OptType {
        /** 新增操作 */
        public static final Integer INSERT = 0;
        /** 修改操作 */
        public static final Integer UPDATE = 1;
        /** 删除操作 */
        public static final Integer DELETE = 2;
        /** 查询操作 */
        public static final Integer QUERY = 3;
    }

    /**
     * 操作代码：3000以上才用以业务上。
     */
    public interface OptCode {
        /** 操作成功 */
        public static final Integer OPT_SUCCESS = 1000;
        /** 操作失败 */
        public static final Integer OPT_FAILURE = 1001;
        /** 用户限制接入 */
        public static final Integer LIMIT_ACCESS = 1002;
        /** 流量超过阀值 */
        public static final Integer FLOW_OVERLOAD = 1003;
        /** 用户会话超时 */
        public static final Integer SESSION_TIMEOUT = 1004;
        /** JSON解析错误 */
        public static final Integer JSON_PARSE_ERROR = 1005;
        /** JSON映射错误 */
        public static final Integer JSON_MAPPING_ERROR = 1006;
        /** 未授予权限访问服务 */
        public static final Integer UNAUTHORIZED_ERROR = 1007;
        /** 未配置REST服务 */
        public static final Integer REST_SERV_CONFIG_ERROR = 1008;
        /** REST服务状态设置错误 */
        public static final Integer REST_SERV_STATUS_ERROR = 1009;
        /** REST服务禁止访问 */
        public static final Integer REST_SERV_FORBIDDEN_ERROR = 1010;
        /** 未配置用户限制接入 */
        public static final Integer LIMIT_ACCESS_NOT_CONFIG = 1011;
        /** 用户名为空 */
        public static final Integer USERNAME_NULL = 2001;
        /** 密码为空 */
        public static final Integer PASSWORD_NULL = 2002;
        /** 用户不存在 */
        public static final Integer USERNAME_ERROR = 2003;
        /** 密码错误 */
        public static final Integer PASSWORD_ERROR = 2004;
        /** 手机号码错误 */
        public static final Integer MOBILE_ERROR = 2005;
        /** IMSI码错误 */
        public static final Integer IMSI_ERROR = 2006;
        /** 参数不正确 */
        public static final Integer PARAMETER_ERROR = 2007;
        /** 未知错误 */
        public static final Integer UNKNOWN_ERROR = 2008;
        /** 服务器内部错误 */
        public static final Integer SERVER_INTERNAL_ERROR = 2009;
        /** 数据访问异常 */
        public static final Integer DATA_ACCESS_ERROR = 2010;
        /** 文件上传失败 */
        public static final Integer FILE_UPLOAD_ERROR = 2011;
        /** 应用上传失败 */
        public static final Integer APP_UPLOAD_ERROR = 2012;
        /** 文件上传路径【UPLOAD_SAVE_PATH】未配置 */
        public static final Integer UPLOAD_SAVE_PATH_ERROR = 2013;
        /** 应用上传路径【APP_SAVE_PATH_ERROR】未配置 */
        public static final Integer APP_SAVE_PATH_ERROR = 2014;
        /** 文件IO异常 */
        public static final Integer IO_EXCEPTION = 2015;
        /** 空指针异常 */
        public static final Integer NULL_POINTER_EXCEPTION = 2016;
        /** 图片上传失败 */
        public static final Integer PHOTO_UPLOAD_ERROR = 2017;
        /** 接口调用失败 */
        public static final Integer INVOKE_INTER_ERROR = 2018;
        /** 接口返回错误信息 */
        public static final Integer INTER_INFO_ERROR = 2019;
        /** 接口报文解析失败 */
        public static final Integer WSXML_PARSED_ERROR = 2020;
        /** 条形码为空 */
        public static final Integer CODE_BAR_NULL_ERROR = 2021;
    }

    public interface OptMsg {
        /** 操作成功 */
        public static final String OPT_SUCCESS = "操作成功";
        /** 操作失败 */
        public static final String OPT_FAILURE = "操作失败";
        /** 用户名为空 */
        public static final String USERNAME_NULL = "用户名为空";
        /** 密码为空 */
        public static final String PASSWORD_NULL = "密码为空";
        /** 用户不存在 */
        public static final String USERNAME_ERROR = "用户不存在";
        /** 用户限制接入 */
        public static final String LIMIT_ACCESS = "用户限制接入";
        /** 流量超过阀值 */
        public static final String FLOW_OVERLOAD = "流量超过阀值";
        /** 用户会话超时 */
        public static final String SESSION_TIMEOUT = "用户会话超时";
        /** JSON解析错误 */
        public static final String JSON_PARSE_ERROR = "JSON解析错误";
        /** JSON映射错误 */
        public static final String JSON_MAPPING_ERROR = "JSON映射错误";
        /** 未配置REST服务 */
        public static final String REST_SERV_CONFIG_ERROR = "未配置REST服务，请联系系统管理员";
        /** REST服务状态设置错误 */
        public static final String REST_SERV_STATUS_ERROR = "REST服务状态设置错误，请联系系统管理员";
        /** REST服务禁止访问 */
        public static final String REST_SERV_FORBIDDEN_ERROR = "REST服务禁止访问";
        /** GET测试成功 */
        public static final String TEST_GET_SUCCESS = "GET测试成功";
        /** POST测试成功 */
        public static final String TEST_POST_SUCCESS = "POST测试成功";
        /** 数据访问异常 */
        public static final String DATA_ACCESS_ERROR = "数据访问异常";
        /** 服务器内部错误 */
        public static final String SERVER_INTERNAL_ERROR = "服务器内部错误";
        /** 未知错误 */
        public static final String UNKNOWN_ERROR = "未知错误 ";
        /** 参数错误 */
        public static final String PARAMETER_ERROR = "参数错误";
        /** 密码错误 */
        public static final String PASSWORD_ERROR = "密码错误";
        /** 文件上传失败 */
        public static final String FILE_UPLOAD_ERROR = "文件上传失败";
        /** 应用上传失败 */
        public static final String APP_UPLOAD_ERROR = "应用上传失败";
        /** 文件上传路径【UPLOAD_SAVE_PATH】未配置 */
        public static final String UPLOAD_SAVE_PATH_ERROR = "文件上传路径【UPLOAD_SAVE_PATH】未配置";
        /** 应用上传路径【APP_SAVE_PATH】未配置 */
        public static final String APP_SAVE_PATH_ERROR = "应用上传路径【APP_SAVE_PATH】未配置";
        /** 文件IO异常 */
        public static final String IO_ERROR = "文件IO异常";
        /** 空指针异常 */
        public static final String NULL_POINTER_EXCEPTION = "空指针异常";
        /** 未授予权限访问服务 */
        public static final String UNAUTHORIZED_ERROR = "未授予权限访问服务";
        /** 未配置用户限制接入 */
        public static final String LIMIT_ACCESS_NOT_CONFIG = "未配置用户限制接入，请联系系统管理员";
        /** 图片上传失败 */
        public static final String PHOTO_UPLOAD_ERROR = "图片上传失败";        
        /** 接口调用失败 */
        public static final String INVOKE_INTER_ERROR = "接口调用失败";
        /** 接口报文解析失败 */
        public static final String WSXML_PARSED_ERROR = "接口报文解析失败";
        
    }

    /** 配置编码 */
    public interface ConfigCode {
    	/** 图片上传存储目录 */
    	public static final String UPLOAD_PHOTO_SAVE_PATH = "UPLOAD_PHOTO_SAVE_PATH";
    	/** 巡检图片上传存储目录 */
    	public static final String UPLOAD_PHOTO_SAVE_PATH_INSPECT = "UPLOAD_PHOTO_SAVE_PATH_INSPECT";
        /** 配置编码：文件上传路径 */
        public static final String UPLOAD_APP_SAVE_PATH = "UPLOAD_APP_SAVE_PATH";
        /** 配置编码：应用存放路径 */
        public static final String UPLOAD_FILE_SAVE_PATH = "UPLOAD_FILE_SAVE_PATH";
        /** 配置编码：二维码存放路径 */
        public static final String QRCODE_FILE_PATH = "QRCODE_FILE_PATH";
        /** 开通工单回单图片存放目录 */
        public static final String UPLOAD_WKORDER_PHOTO_PATH ="UPLOAD_WKORDER_PHOTO_PATH";
    }

    public interface CacheKey {
        /** 1分钟缓存 */
        public static final String T01MIN_CACHE = "T01MIN_CACHE";
        /** 2分钟缓存 */
        public static final String T02MIN_CACHE = "T02MIN_CACHE";
        /** 3分钟缓存 */
        public static final String T03MIN_CACHE = "T03MIN_CACHE";
        /** 5分钟缓存 */
        public static final String T05MIN_CACHE = "T05MIN_CACHE";
        /** 10分钟缓存 */
        public static final String T10MIN_CACHE = "T10MIN_CACHE";
        /** 15分钟缓存 */
        public static final String T15MIN_CACHE = "T15MIN_CACHE";
        /** 20分钟缓存 */
        public static final String T20MIN_CACHE = "T20MIN_CACHE";
        /** 30分钟缓存 */
        public static final String T30MIN_CACHE = "T30MIN_CACHE";
        /** 60分钟缓存 */
        public static final String T60MIN_CACHE = "T60MIN_CACHE";
    }
    
    public interface CacheCode {
    	/** NORMAL_REST_SERV_LIST：正常的REST服务列表 */
        public static final String NORMAL_REST_SERV_LIST = "'NORMAL_REST_SERV_LIST'";
    	/** FORBIDDEN_REST_SERV_LIST：禁止的REST服务列表 */
        public static final String FORBIDDEN_REST_SERV_LIST = "'FORBIDDEN_REST_SERV_LIST'";
    	/** REST_SERV_LIST：禁止的REST服务列表 */
        public static final String REST_SERV_LIST = "'REST_SERV_LIST'";
       	/** REST_SERV_LIST：用户控制列表 */
        public static final String STAFF_CONTROLS_LIST = "'STAFF_CONTROLS_LIST'";
    }

    public interface GCode {
        /** 操作代码 */
        public static final String OPT_CODE = "OPT_CODE";
        /** 配置错误 */
        public static final String CONFIG_ERROR = "CONFIG_ERROR";
        /** 是否值 */
        public static final String YES_OR_NO = "YES_OR_NO";
        /** APP系统类型 */
        public static final String APP_OS_TYPE = "APP_OS_TYPE";
        /** REST服务操作类型 */
        public static final String SERV_OPT_TYPE = "SERV_OPT_TYPE";
        
        public static final String RES_SERV_STATUS = "RES_SERV_STATUS";
    }

    public interface AppOsType {
        /** Google Android */
        public static final Integer ANDROID = 1;
        /** Apple iOS */
        public static final Integer IOS = 2;
        /** Microsoft WindowsPhone */
        public static final Integer WP = 3;
    }
    
    /** REST服务状态*/
    public interface RestServStatus {
        /** FORBIDDEN：服务禁止  */
        public static final Integer FORBIDDEN = 0;
        /** NORMAL：服务正常 */
        public static final Integer NORMAL= 1;
        /** NOT_CONFIGED：服务未配置 */
        public static final Integer NOT_CONFIGED = 2;
    }
    
    /** REST服务状态*/
    public interface RestServType {
        /** 平台基础服务  */
        public static final Integer BASIC_SERV = 0;
        /** 业务服务 */
        public static final Integer BUSI_SERV = 1;
    }
    
    /** REST服务操作类型 */
    public interface ServOptType {
        /** REQUEST：服务请求 */
        public static final Integer REQUEST = 0;
        /** RESPONSE：服务响应 */
        public static final Integer RESPONSE= 1;
    }
    
    /** REST服务日志类型*/
    public interface ServLogType {
        /** FORBIDDEN：服务异常  */
        public static final Integer ABNORMAL = 0;
        /** NORMAL：服务正常 */
        public static final Integer NORMAL= 1;
    }

    public interface YesOrNo {
        /** 是 */
        public static final Integer YES = 1;
        /** 否 */
        public static final Integer NO = 0;
    }
    
    /** 用户限制类型 */
    public interface StaffLimitType {
        /** 限制连接 */
        public static final Integer CONNECT_LIMIT = 1;
        /** 不限制连接 */
        public static final Integer CONNECT_NO_LIMIT = 0;
    }
    
    /** 请求XML接口返回结果 */
    public interface XmlResult {
    	public static final String SUCCESS = "000";
    	
    	public static final String WRONG_REQUEST_PARAMS = "001";
    	
    	public static final String WRONG_REQUEST_DATAS = "002";
    	
    	public static final String EXECUTE_EXCEPTION = "003";
    	
    	public static final String OTHER_EXCEPTION = "004";
    }
    
    public interface DownloadObjType {
        /** 未知 */
        public static final Integer UNKNOWN_ID = 0;
        /** MOBILE_DOWNLOAD_LOG的DOWNLOAD_OBJ_ID的关联ID为MOBILE_APP的ID */
        public static final Integer MOBILE_APP_ID = 1;
        /** MOBILE_DOWNLOAD_LOG的DOWNLOAD_OBJ_ID的关联ID为MOBILE_FRAME_APP的ID */
        public static final Integer MOBILE_FRAME_APP_ID = 2;
    }

}
