package com.ztesoft.mobile.v2.core;

import com.ztesoft.mobile.v2.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午2:51
 */
public class Result  extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9173362517684032594L;
	
	public static final String RESULT_CODE_NODE = "resultCode";
    public static final String RESULT_MSG_NODE = "resultMsg";
    public static final String RESULT_DATA_NODE = "resultData";

    private Integer resultCode;
    private String resultMsg;
    private Map<Object, Object> resultData = Collections.EMPTY_MAP;

    public Result(Integer resultCode, String resultMsg, Map<Object, Object> resultData) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        setResultData(resultData);
    }

    public Result(Integer resultCode, String resultMsg) {
        this(resultCode, resultMsg, null);
    }

    public Result() {

    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Map<Object, Object> getResultData() {
        return (resultData != null? resultData :Collections.EMPTY_MAP);
    }

    public void setResultData(Map<Object, Object> resultData) {
        this.resultData = (resultData != null? resultData :Collections.EMPTY_MAP);
    }

    /** 服务器内部错误 */
    public static Result buildServerError() {
        return new Result(Constants.OptCode.SERVER_INTERNAL_ERROR, Constants.OptMsg.SERVER_INTERNAL_ERROR);
    }
    
    /** 返回工单错误信息提示 */
    public static Result buildWorkOrderError(String returnDetail) {
        return new Result(9999, returnDetail);
    }
    
    /** 数据库访问异常 */
    public static Result buildDataAccessError() {
        return new Result(Constants.OptCode.DATA_ACCESS_ERROR, Constants.OptMsg.DATA_ACCESS_ERROR);
    }
    
    /** 参数错误 */
    public static Result buildParameterError() {
        return new Result(Constants.OptCode.PARAMETER_ERROR, Constants.OptMsg.PARAMETER_ERROR);
    }
    
    /** 未知错误 */
    public static Result buildUnknownError() {
        return new Result(Constants.OptCode.UNKNOWN_ERROR, Constants.OptMsg.UNKNOWN_ERROR);
    }
    
    /** 文件上传失败 */
    public static Result buildFileUploadError() {
        return new Result(Constants.OptCode.FILE_UPLOAD_ERROR, Constants.OptMsg.FILE_UPLOAD_ERROR);
    }
    
    /** 图片上传失败 */
    public static Result buildPhotoUploadError() {
        return new Result(Constants.OptCode.PHOTO_UPLOAD_ERROR, Constants.OptMsg.PHOTO_UPLOAD_ERROR);
    }
    
    /** 应用上传失败 */
    public static Result buildAppUploadError() {
        return new Result(Constants.OptCode.APP_UPLOAD_ERROR, Constants.OptMsg.APP_UPLOAD_ERROR);
    }
    
    /** 文件上传配置错误 */
    public static Result buildFileUploadConfigError() {
        return new Result(Constants.OptCode.UPLOAD_SAVE_PATH_ERROR, Constants.OptMsg.UPLOAD_SAVE_PATH_ERROR);
    }
    
    /** 应用上传配置错误 */
    public static Result buildAppUploadConfigError() {
        return new Result(Constants.OptCode.APP_SAVE_PATH_ERROR, Constants.OptMsg.APP_SAVE_PATH_ERROR);
    }

    /** 用户会话超时 */
    public static Result buildSessionTimeout() {
        return new Result(Constants.OptCode.SESSION_TIMEOUT, Constants.OptMsg.SESSION_TIMEOUT);
    }
    
    /** 操作未授权 */
    public static Result buildUnauthorized() {
        return new Result(Constants.OptCode.UNAUTHORIZED_ERROR, Constants.OptMsg.UNAUTHORIZED_ERROR);
    }
    
    /** JSON解析错误 */
    public static Result buildJsonParseError() {
        return new Result(Constants.OptCode.JSON_PARSE_ERROR, Constants.OptMsg.JSON_PARSE_ERROR);
    }
    
    /** JSON映射错误 */
    public static Result buildJsonMappingError() {
        return new Result(Constants.OptCode.JSON_MAPPING_ERROR, Constants.OptMsg.JSON_MAPPING_ERROR);
    }
    
    /** REST服务配置错误 */
    public static Result buildRestServConfigError() {
        return new Result(Constants.OptCode.REST_SERV_CONFIG_ERROR, Constants.OptMsg.REST_SERV_CONFIG_ERROR);
    }
    
    /** REST服务状态错误 */
    public static Result buildRestServStatusError() {
        return new Result(Constants.OptCode.REST_SERV_STATUS_ERROR, Constants.OptMsg.REST_SERV_STATUS_ERROR);
    }
    
    /** REST服务禁止访问 */
    public static Result buildRestServForbiddenError() {
        return new Result(Constants.OptCode.REST_SERV_FORBIDDEN_ERROR, Constants.OptMsg.REST_SERV_FORBIDDEN_ERROR);
    }
    
    /** 接口调用失败 */
    public static Result buildInvokeInterError() {
        return new Result(Constants.OptCode.INVOKE_INTER_ERROR, Constants.OptMsg.INVOKE_INTER_ERROR);
    }
    
    /** 接口调用失败 */
    public static Result buildInvokeInterError(String msg) {
        return new Result(Constants.OptCode.INVOKE_INTER_ERROR, msg);
    }
    
    /** 接口报文解析失败 */
    public static Result buildWSXmlParsedError() {
        return new Result(Constants.OptCode.WSXML_PARSED_ERROR, Constants.OptMsg.WSXML_PARSED_ERROR);
    }
    
    /** 接口返回信息  */
    public static Result buildInterInfoError(String msg) {
        return new Result(Constants.OptCode.INTER_INFO_ERROR, msg);
    }
    
    public static Result buildSuccess() {
        return new Result(Constants.OptCode.OPT_SUCCESS, Constants.OptMsg.OPT_SUCCESS);
    }
    
    public static Result buildSuccess(Map<Object, Object> resultData) {
        return new Result(Constants.OptCode.OPT_SUCCESS, Constants.OptMsg.OPT_SUCCESS, resultData);
    }

    public static Result buildFailure() {
        return new Result(Constants.OptCode.OPT_FAILURE, Constants.OptMsg.OPT_FAILURE);
    }
    
    /** 用户限制接入 */
    public static Result buildLimitAccessError() {
        /** 用户限制接入 */
        return new Result(Constants.OptCode.LIMIT_ACCESS, Constants.OptMsg.LIMIT_ACCESS);
    }
    
    /** 未配置用户限制接入 */
    public static Result buildLimitAccessNotConfigError() {
        return new Result(Constants.OptCode.LIMIT_ACCESS_NOT_CONFIG, Constants.OptMsg.LIMIT_ACCESS_NOT_CONFIG);
    }
    
    /** 流量超过阀值 */
    public static Result buildFlowOverloadError() {
        return new Result(Constants.OptCode.FLOW_OVERLOAD, Constants.OptMsg.FLOW_OVERLOAD);
    }
    
    /** 接口返回信息  */
    public static Result buildCodeBarError(String msg) {
        return new Result(Constants.OptCode.CODE_BAR_NULL_ERROR, msg);
    }

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
    
}
