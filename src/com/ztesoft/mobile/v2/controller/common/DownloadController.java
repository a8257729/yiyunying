package com.ztesoft.mobile.v2.controller.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.entity.app.MobileApp;
import com.ztesoft.mobile.v2.entity.app.MobileFrameApp;
import com.ztesoft.mobile.v2.entity.common.MobileDownloadLog;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.util.BaseUtils;

/**
 * User: heisonyee
 * Date: 13-3-27
 * Time: 下午11:18
 */
@Controller("downloadController")
public class DownloadController extends WebConfigController {
	
	private static final Long ANON_STAFF_ID = -1L;
	private static final String ANON_STAFF_NAME = "匿名用户";
	private static final String ANON_USERNAME = "匿名用户";
	
	private static final String ANDROID_OS_NAME = "android";
	private static final String IOS_OS_NAME = "ios";
	private static final String WP_OS_NAME = "wp";

    private static final Logger logger = Logger.getLogger(DownloadController.class);
    
    private MobileCommonService mobileCommonService;
    
    private MobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

    @Autowired(required = false)
	public void setMobileCommonService(MobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	/** 根据appId下载FrameApp */
    @RequestMapping(value = {"/client/downloads/frame/app/{appId}/{staffId}"})
    @FilterTag()
    public void downloadFrameApp(@PathVariable("appId") Long appId, @PathVariable("staffId") Long staffId, 
                HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用FramApp下载服务");
    	}
    	
    	Map resultMap = this.getMobileCommonService().getFrameAppById(appId);
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用FramApp下载服务后");
    	}
    	String filePath = (String) resultMap.get(MobileFrameApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get(MobileFrameApp.APP_NAME_NODE);
    	String fileType = BaseUtils.getFileSuffix(fileName);
    	if(StringUtils.isBlank(filePath)) {
    		if(logger.isDebugEnabled()) {
        		logger.debug("filePath为空");
        	}
    		throw new IOException();
    	}
    	String path=request.getSession( ).getServletContext( ).getRealPath( "/" ); 
       	//服务器所在目录  
       	logger.debug("======================: " + path);
        File file=new File(path+filePath);
        
        if(!file.exists()) {
        	if(logger.isDebugEnabled()) {
        		logger.debug("文件不存在");
        	}
        	throw new IOException();
        }
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        if(logger.isDebugEnabled()) {
    		logger.debug("调用FramApp下载服务继续2");
    	}
        //根据ID更新下载统计
        this.updateFrameAppDownloadCount(appId);
        
        //
        this.writeDownloadLog(staffId, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_FRAME_APP_ID);
        
    }
    
	/** 根据appId下载APP */
    @RequestMapping(value = {"/client/downloads/apps/{appId}/{staffId}"})
    @FilterTag()
    public void downloadApp(@PathVariable("appId") Long appId, @PathVariable("staffId") Long staffId, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用APP下载服务");
    	}
    	
    	Map resultMap = this.getMobileCommonService().getAppById(appId);
    	
    	String filePath = (String) resultMap.get(MobileApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get(MobileApp.APP_NAME_NODE);
    	String fileType = BaseUtils.getFileSuffix(fileName);
    	
    	if(StringUtils.isBlank(filePath)) {
    		throw new IOException();
    	}
    	
        File file=new File(filePath);
        
        if(!file.exists()) {
        	throw new IOException();
        }
    	
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        
        //根据ID更新下载统计
        this.updateAppDownloadCount(appId);
        
        //若没异常抛出，则写下载日志
        this.writeDownloadLog(staffId, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_APP_ID);
    }
    
    /** 根据appId下载appFrame */
    @RequestMapping(value = {"/server/downloads/appFrame/{appId}"})
    @FilterTag
    public void downloadFrameAppForServer(@PathVariable("appId") Long appId,
                HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用appFrame供服务器下载服务");
    	}
    	
    	Map resultMap = this.getMobileCommonService().getFrameAppByIdServer(appId);
    	
    	String filePath = (String) resultMap.get(MobileFrameApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get("name");
    	String fileType = BaseUtils.getFileSuffix(filePath);
    	
    	if(StringUtils.isBlank(filePath)) {
    		throw new IOException();
    	}
    	
        File file=new File(filePath);
        
        if(!file.exists()) {
        	throw new IOException();
        }
    	
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        
        //根据ID更新下载统计
        this.updateFrameAppDownloadCount(appId);
        
        //若没异常抛出，则写下载日志
        this.writeDownloadLog(ANON_STAFF_ID, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_FRAME_APP_ID);
    }
    
    /** 根据appId下载APP */
    @RequestMapping(value = {"/server/downloads/app/{appId}"})
    @FilterTag
    public void downloadAppForServer(@PathVariable("appId") Long appId,
                HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用APP供服务器下载服务");
    	}
    	
    	Map resultMap = this.getMobileCommonService().getAppById(appId);
    	
    	String filePath = (String) resultMap.get(MobileFrameApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get(MobileFrameApp.APP_NAME_NODE);
    	String fileType = BaseUtils.getFileSuffix(filePath);
    	
    	if(StringUtils.isBlank(filePath)) {
    		throw new IOException();
    	}
    	
        File file=new File(filePath);
        
        if(!file.exists()) {
        	throw new IOException();
        }
    	
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        
        //根据ID更新下载统计
        this.updateAppDownloadCount(appId);
        
        //若没异常抛出，则写下载日志
        this.writeDownloadLog(ANON_STAFF_ID, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_APP_ID);
    }

    /** 下载最新的APP(非客户端) */
    @RequestMapping(value = {"/web/downloads/frame/app/latest/{osName}"})
    @FilterTag
    public void downloadLatestFrameApp(@PathVariable("osName") String osName,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

    	if(logger.isDebugEnabled()) {
    		logger.debug("调用下载最新FramApp服务");
    	}
    	
    	Map resultMap = Collections.EMPTY_MAP;
    	
    	if(ANDROID_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.ANDROID);
    	} else if(IOS_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.IOS);
    	} else if(WP_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.WP);
    	} else {
    		//Do nothing?
    	}
    	String filePath = (String) resultMap.get(MobileFrameApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get(MobileFrameApp.APP_NAME_NODE);
    	String fileType = BaseUtils.getFileSuffix(fileName);
    	
    	if(StringUtils.isBlank(filePath)) {
    		throw new IOException();
    	}
    			
        File file=new File(filePath);
        
        if(!file.exists()) {
        	throw new IOException();
        }
        
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        
        Long appId = MapUtils.getLong(resultMap, MobileApp.APP_ID_NODE);
        //根据ID更新下载统计
        this.updateFrameAppDownloadCount(appId);
        
        //
        this.writeDownloadLog(ANON_STAFF_ID, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_FRAME_APP_ID);
    }
    
    /** 简化下载地址，供短信推送用 */
    @RequestMapping(value = {"/app/latest/{osName}"})
    @FilterTag
    public void downloadLatestFrameApp2(@PathVariable("osName") String osName,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

    	if(logger.isDebugEnabled()) {
    		logger.debug("调用下载最新FramApp服务");
    	}
    	
    	Map resultMap = Collections.EMPTY_MAP;
    	
    	if(ANDROID_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.ANDROID);
    	} else if(IOS_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.IOS);
    	} else if(WP_OS_NAME.equalsIgnoreCase(osName)) {
    		resultMap = this.getMobileCommonService().getLatestFrameApp(Constants.AppOsType.WP);
    	} else {
    		//Do nothing?
    	}
    	String filePath = (String) resultMap.get(MobileFrameApp.FILE_PATH_NODE);
    	String fileName = (String) resultMap.get(MobileFrameApp.APP_NAME_NODE);
    	String fileType = BaseUtils.getFileSuffix(fileName);
    	
    	if(StringUtils.isBlank(filePath)) {
    		throw new IOException();
    	}
    			
        File file=new File(filePath);
        
        if(!file.exists()) {
        	throw new IOException();
        }
        
        InputStream input = FileUtils.openInputStream(file);
        byte[] data = IOUtils.toByteArray(input);

        wrapResponse(response, file.getName(), data.length);

        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
        
        Long appId = MapUtils.getLong(resultMap, MobileApp.APP_ID_NODE);
        //根据ID更新下载统计
        this.updateFrameAppDownloadCount(appId);
        
        //
        this.writeDownloadLog(ANON_STAFF_ID, ANON_STAFF_NAME, ANON_USERNAME, 
        		fileName, filePath, fileType, appId, Constants.DownloadObjType.MOBILE_FRAME_APP_ID);
    }
    
    private void wrapResponse(HttpServletResponse response, String fileName, int dataLength) {
        //设置响应头为二进制
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + dataLength);
    }
    
    /** 写下载日志 */
    private void writeDownloadLog(Long staffId, String staffName, String username,
    		String fileName, String filePath, String fileType, Long downloadObjId, Integer downloadObjType) {
        //写上传日志
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用下载日志记录");
    	}
    	
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileDownloadLog.DOWNLOAD_STAFF_ID_NODE, staffId);
        paramMap.put(MobileDownloadLog.DOWNLOAD_STAFF_NAME_NODE, staffName);
        paramMap.put(MobileDownloadLog.DOWNLOAD_STAFF_USERNAME_NODE, username);
        paramMap.put(MobileDownloadLog.FILE_NAME_NODE, fileName);
        paramMap.put(MobileDownloadLog.FILE_PATH_NODE, filePath);
        paramMap.put(MobileDownloadLog.FILE_TYPE_NODE, fileType);
        paramMap.put(MobileDownloadLog.DOWNLOAD_OBJ_ID_NODE, downloadObjId);
        paramMap.put(MobileDownloadLog.DOWNLOAD_OBJ_TYPE_NODE, downloadObjType);
        paramMap.put(MobileDownloadLog.DOWNLOAD_TIME_NODE, new Date());
        //To be more???
        try {
			getMobileCommonService().writeDownloadLog(paramMap);
		} catch (Exception e) {
			//若上传日志写入失败，打印错误堆栈，但不处理失败
			e.printStackTrace();
		}
    }
    
    public void updateFrameAppDownloadCount(Long frameAppId) {
    	
    	if(logger.isDebugEnabled()) {
    		logger.debug("调用框架应用下载统计更新");
    	}
    	
    	try {
    		this.getMobileCommonService().frameAppDownloadCount(frameAppId);
    	} catch(Exception ex) {
    		if(logger.isDebugEnabled()) {
    			logger.debug("更新MOBILE_FRAME_APP的DOWNLOAD_COUNT失败");
    		}
    		ex.printStackTrace();
    	}
    	
    }
    
    public void updateAppDownloadCount(Long appId) {
    	try {
    		this.getMobileCommonService().appDownloadCount(appId);
    	} catch(Exception ex) {
    		if(logger.isDebugEnabled()) {
    			logger.debug("更新MOBILE_APP的DOWNLOAD_COUNT失败");
    		}
    		ex.printStackTrace();
    	}
    }

}
