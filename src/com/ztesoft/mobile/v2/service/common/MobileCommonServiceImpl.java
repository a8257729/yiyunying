package com.ztesoft.mobile.v2.service.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.common.helper.MD5Utils;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.message.dao.MobileMessageDAO;
import com.ztesoft.mobile.message.dao.MobileMessageDAOImpl;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAOImpl;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileDownloadLogDAO;
import com.ztesoft.mobile.v2.dao.common.MobileDownloadLogDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileExceptionDAO;
import com.ztesoft.mobile.v2.dao.common.MobileExceptionDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileFeedbackDAO;
import com.ztesoft.mobile.v2.dao.common.MobileFeedbackDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileNoticeDAO;
import com.ztesoft.mobile.v2.dao.common.MobileNoticeDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobilePhotoRecordDAO;
import com.ztesoft.mobile.v2.dao.common.MobilePhotoRecordDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileStaffShortcutDAO;
import com.ztesoft.mobile.v2.dao.common.MobileStaffShortcutDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileUploadLogDAO;
import com.ztesoft.mobile.v2.dao.common.MobileUploadLogDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffInfoDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffInfoDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;
import com.ztesoft.mobile.v2.entity.app.MobileApp;
import com.ztesoft.mobile.v2.entity.app.MobileFrameApp;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.MobileExceptionLog;
import com.ztesoft.mobile.v2.entity.common.MobileFeedback;
import com.ztesoft.mobile.v2.entity.common.MobileNotice;
import com.ztesoft.mobile.v2.entity.common.MobilePhotoRecord;
import com.ztesoft.mobile.v2.entity.common.MobileStaffShortcut;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.common.VersionInfo;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.entity.interf.MobileStaffMapping;
import com.ztesoft.mobile.v2.entity.menu.MobileMenu;
import com.ztesoft.mobile.v2.entity.menu.MobileMenuCatalog;
import com.ztesoft.mobile.v2.entity.menu.MobileMenuConfig;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.util.CryptUtils;

/**
 * User: heisonyee
 * 每锟斤拷Service锟洁都锟斤拷锟斤拷锟斤拷示锟斤拷锟阶筹拷锟届常锟斤拷锟斤拷锟诫处锟??锟届常锟斤拷锟斤拷锟?
 * 锟斤拷锟揭ｏ拷锟斤拷锟斤拷锟斤拷锟届常锟斤拷锟斤拷锟节斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟睫改猴拷删锟斤拷锟斤拷锟斤拷锟斤拷锟狡ｏ拷
 * Date: 13-2-25
 * Time: 锟斤拷锟斤拷6:52
 */
@Service("commonService")
public class MobileCommonServiceImpl extends BaseService implements MobileCommonService {
	
	private static final Logger logger = Logger.getLogger(MobileCommonServiceImpl.class);

    private MobilePhotoRecordDAO getMobilePhotoRecordDAO() {
        String daoName = MobilePhotoRecordDAOImpl.class.getName();
        return (MobilePhotoRecordDAO) BaseDAOFactory.getImplDAO(daoName);
    }	
	
    private MobileMenuDAO getMobileMenuDAO() {
        String daoName = MobileMenuDAOImpl.class.getName();
        return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    
    private MobileMenuConfigDAO getMobileMenuConfigDAO() {
        String daoName = MobileMenuConfigDAOImpl.class.getName();
        return (MobileMenuConfigDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
	private MobileStaffMappingDAO getMobileStaffMappingDAO() {
        String daoName = MobileStaffMappingDAOImpl.class.getName();
        return (MobileStaffMappingDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
	private MobileExceptionDAO getMobileExceptionDAO() {
        String daoName = MobileExceptionDAOImpl.class.getName();
        return (MobileExceptionDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
	private MobileAppDAO getMobileAppDAO() {
        String daoName = MobileAppDAOImpl.class.getName();
        return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
    private MobileCommonDAO getMobileCommonDAO() {
        String daoName = MobileCommonDAOImpl.class.getName();
        return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
    }

	private MobileRestServLogDAO getMobileRestServLogDAO() {
		String daoName = MobileRestServLogDAOImpl.class.getName();
		return (MobileRestServLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
    
    private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    private MobileFrameAppDAO getMobileFrameAppDAO() {
        String daoName = MobileFrameAppDAOImpl.class.getName();
        return (MobileFrameAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    private MobileFeedbackDAO getMobileFeedbackDAO() {
        String daoName = MobileFeedbackDAOImpl.class.getName();
        return (MobileFeedbackDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    private MobileStaffShortcutDAO getMobileStaffShortcutDAO() {
        String daoName = MobileStaffShortcutDAOImpl.class.getName();
        return (MobileStaffShortcutDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    private MobileNoticeDAO getMobileNotificationDAO() {
        String daoName = MobileNoticeDAOImpl.class.getName();
        return (MobileNoticeDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    
    private MobileMessageDAO getMobileMessageDAO() {
        String daoName = MobileMessageDAOImpl.class.getName();
        return (MobileMessageDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    
    private MobileUploadLogDAO getMobileUploadLogDAO() {
        String daoName = MobileUploadLogDAOImpl.class.getName();
        return (MobileUploadLogDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    
    private MobileDownloadLogDAO getMobileDownloadLogDAO() {
        String daoName = MobileDownloadLogDAOImpl.class.getName();
        return (MobileDownloadLogDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
	private MobileSessionRecordDAO getMobileSessionRecordDAO()
	{
		String daoName = MobileSessionRecordDAOImpl.class.getName();
		return (MobileSessionRecordDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
    private MobileStaffInfoDAO getStaffInfoDAO() {
        String daoName = MobileStaffInfoDAOImpl.class.getName();
        return (MobileStaffInfoDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    
    public Result login(StaffInfo staffInfo) throws Exception {
    	
    	long inTimestamp = System.currentTimeMillis();
    	boolean flag = false;
    	
        Result result = new Result();

        String username = staffInfo.getUsername();
        String password = staffInfo.getPassword();

        if(StringUtils.isBlank(username)) {
        	return new Result(Constants.OptCode.USERNAME_NULL,Constants.OptMsg.USERNAME_NULL);
        }
        
        if(StringUtils.isBlank(password)){
            return new Result(Constants.OptCode.PASSWORD_NULL,Constants.OptMsg.PASSWORD_NULL);
        }

        Map<String , Object> staffMap = getMobileCommonDAO().getStaffByUsername(username);

        //锟斤拷证锟矫伙拷锟角凤拷锟斤拷锟?
        String mUsername = MapUtils.getString(staffMap, StaffInfo.USERNAME_NODE, null);
        if(StringUtils.isBlank(mUsername)) {
            return new Result(Constants.OptCode.USERNAME_ERROR,Constants.OptMsg.USERNAME_ERROR);
        }
        
        if(Constants.SSOType.NO_AUTH_SSO.equals(staffInfo.getSsoType())) {	//锟斤拷锟斤拷锟诫单锟斤拷锟铰?
        	if(logger.isDebugEnabled()) {
        		logger.debug("科大无密码验证单点登录");
        	}
        } else {
            //锟斤拷证锟斤拷锟斤拷锟角凤拷锟斤拷确
            String mPassword = MapUtils.getString(staffMap, StaffInfo.PASSWORD_NODE, null);
            //锟斤拷锟斤拷
            String dePassword = CryptUtils.decryptString(password);
            
            if(!validatePasswrod(dePassword, mPassword)) {
                return new Result(Constants.OptCode.PASSWORD_ERROR,Constants.OptMsg.PASSWORD_ERROR);
            }
        }
        
        //锟叫讹拷锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 
        Integer mConnectLimit = MapUtils.getInteger(staffMap, StaffInfo.CONNECT_LIMIT_NODE);
		if(Constants.StaffLimitType.CONNECT_LIMIT.equals(mConnectLimit)) {
			return result = Result.buildLimitAccessError();
		} else if(Constants.StaffLimitType.CONNECT_NO_LIMIT.equals(mConnectLimit)) {
			//
		} else {
			return result = Result.buildLimitAccessNotConfigError();
		}

        Long staffId = MapUtils.getLong(staffMap, StaffInfo.STAFF_ID_NODE, null);

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        //锟斤拷证通锟斤拷
        //1.  锟斤拷去锟斤拷map锟斤拷删锟斤拷锟斤拷锟斤拷锟街碉拷锟斤拷锟斤拷诎锟饺?拷锟斤拷锟?
        staffMap.remove(StaffInfo.PASSWORD_NODE);

        //2. 锟斤拷员锟斤拷息
        resultData.put(StaffInfo.STAFF_INFO_NODE, staffMap);

        //3. 锟斤拷取默锟斤拷职位锟斤拷息
        Map defaultJob = getMobileCommonDAO().getDefaultJob(staffId);
        resultData.put(JobInfo.DEFAULT_JOB_NODE, defaultJob);
        //3. 锟斤拷取锟斤拷默锟斤拷职位锟叫憋拷
        List<Map> normalJobList = getMobileCommonDAO().getNormalJobList(staffId);
        resultData.put(JobInfo.NORMAL_JOB_LIST_NODE, normalJobList);
        //4. 锟斤拷取锟剿猴拷映锟斤拷锟斤拷息
        List<Map> staffMappingList = this.getMobileStaffMappingDAO().getMappingInfoByStaffId(staffId);
        resultData.put(MobileStaffMapping.STAFF_MAPPING_LIST_NODE, staffMappingList);
        
        result = Result.buildSuccess();
        result.setResultData(resultData);
        
        flag = true;
        
//        //写锟斤拷录锟斤拷录锟斤拷
//        try {
//        	
//        	System.out.println("CHNELIN INSERT LOGIN MSG");
//        	
//        	Map loginInfoMap = new HashMap();
//        	loginInfoMap.put("userName", username);
//        	Map retMap = getStaffInfoDAO().selByStaffId(loginInfoMap);
//        	String staff = MapUtils.getString(retMap, "staffId");
//        	loginInfoMap.put("staffId",staff );
//        	String orgId = MapUtils.getString(retMap, "orgId");
//        	loginInfoMap.put("orgId", orgId); 
//        	logger.info("CHNELIN INSERT LOGIN MSG11111111111");
//        	getStaffInfoDAO().insert(loginInfoMap);
//        	logger.info("CHNELIN INSERT LOGIN MSG22222222222");
//
//        	getStaffInfoDAO().insert(loginInfoMap);
//        	logger.info("CHNELIN INSERT -------------------77777777777777");
//        	getStaffInfoDAO().insert(loginInfoMap);
//        	logger.info("CHNELIN INSERT------------------ 88888888888888888");
//        	getStaffInfoDAO().insert(loginInfoMap);
//        	logger.info("CHNELIN INSERT ------------------9999999999999999999999999999999");
//        } catch(Exception ex) {
//        	ex.printStackTrace();
//        	if(logger.isDebugEnabled()) {
//		
//        	}
//        }
        
        //写REST_SERV_LOG锟斤拷志锟斤拷锟斤拷志锟斤拷锟届常锟斤拷锟斤拷影锟斤拷锟斤拷锟侥斤拷锟斤拷
        long outTimestamp = System.currentTimeMillis();
        try {
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		
    		//TODO ????
    		//paramMap.put(MobileRestServLog.REST_SERVICE_ID_NODE, restServLog.getRestServiceId());	//写锟斤拷锟斤拷锟斤拷
    		paramMap.put(MobileRestServLog.STAFF_ID_NODE, staffId==null? staffId : -1L);
    		paramMap.put(MobileRestServLog.SERV_LOG_TYPE_NODE, flag==true? Constants.ServLogType.NORMAL:Constants.ServLogType.ABNORMAL);
    		paramMap.put(MobileRestServLog.IN_TIMESTAMP_NODE, inTimestamp);
    		paramMap.put(MobileRestServLog.OUT_TIMESTAMP_NODE, outTimestamp);
    		paramMap.put(MobileRestServLog.IN_CONTENT_NODE, username.getBytes().length + password.getBytes().length);
    		paramMap.put(MobileRestServLog.OUT_CONTENT_NODE, JSONObject.fromObject(result).toString());
    		paramMap.put(MobileRestServLog.IN_SIZE_NODE, JSONObject.fromObject(staffInfo).toString().getBytes().length);
    		paramMap.put(MobileRestServLog.OUT_SIZE_NODE, JSONObject.fromObject(result).toString().getBytes().length);
    		
    		this.getMobileRestServLogDAO().insert(paramMap);
        } catch(Exception ex) {
        	ex.printStackTrace();
        	if(logger.isDebugEnabled()) {
        		
        	}
        }

        
        
        return result;
    }

public Result login(String openId) throws Exception {
      	
        Result result = new Result();

        Map<String , Object> staffMap = getMobileCommonDAO().getStaffByUsername(openId);

        //锟斤拷证锟矫伙拷锟角凤拷锟斤拷锟?
        String mUsername = MapUtils.getString(staffMap, StaffInfo.USERNAME_NODE, null);
        if(StringUtils.isBlank(mUsername)) {
            return new Result(Constants.OptCode.USERNAME_ERROR,Constants.OptMsg.USERNAME_ERROR);
        }

        Long staffId = MapUtils.getLong(staffMap, StaffInfo.STAFF_ID_NODE, null);

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        //锟斤拷证通锟斤拷
        //1.  锟斤拷去锟斤拷map锟斤拷删锟斤拷锟斤拷锟斤拷锟街碉拷锟斤拷锟斤拷诎锟饺?拷锟斤拷锟?
        staffMap.remove(StaffInfo.PASSWORD_NODE);

        //2. 锟斤拷员锟斤拷息
        resultData.put(StaffInfo.STAFF_INFO_NODE, staffMap);

        //3. 锟斤拷取默锟斤拷职位锟斤拷息
        Map defaultJob = getMobileCommonDAO().getDefaultJob(staffId);
        resultData.put(JobInfo.DEFAULT_JOB_NODE, defaultJob);
        //3. 锟斤拷取锟斤拷默锟斤拷职位锟叫憋拷
        List<Map> normalJobList = getMobileCommonDAO().getNormalJobList(staffId);
        resultData.put(JobInfo.NORMAL_JOB_LIST_NODE, normalJobList);
        //4. 锟斤拷取锟剿猴拷映锟斤拷锟斤拷息
        List<Map> staffMappingList = this.getMobileStaffMappingDAO().getMappingInfoByStaffId(staffId);
        resultData.put(MobileStaffMapping.STAFF_MAPPING_LIST_NODE, staffMappingList);
        
        result = Result.buildSuccess();
        result.setResultData(resultData);

        return result;
    }
    @Transactional(rollbackFor={Throwable.class})
    public Result logout(StaffInfo staffInfo) throws Exception {
         return null;
    }

    @Transactional(rollbackFor={Throwable.class})
    public Result exit(StaffInfo staffInfo) throws Exception {
        return null;
    }

    @Transactional(rollbackFor={Throwable.class})
    public Result updatePassword(Long staffId, String oldPasswd ,String newPasswd) throws Exception {
    	
    	String mOldPasswd = CryptUtils.decryptString(oldPasswd);
    	String mNewPasswd = CryptUtils.decryptString(newPasswd);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(StaffInfo.STAFF_ID_NODE, staffId);

        String mPasswd = getMobileCommonDAO().getPasswordByStaffId(staffId);
        if(!validatePasswrod(mOldPasswd, mPasswd)) {
            return new Result(Constants.OptCode.PASSWORD_ERROR, Constants.OptMsg.PASSWORD_ERROR);
        }
        
        //锟斤拷锟斤拷锟斤拷证通锟斤拷锟睫革拷锟斤拷锟斤拷
        String tNewPasswd = "{SHA}" + new String(Base64It.encode(mNewPasswd.getBytes()));    //锟斤拷锟斤拷
        getMobileCommonDAO().updatePasswordByStaffId(staffId, tNewPasswd);
            //
        return Result.buildSuccess();
    }

    public Result getFrameAppCurrentVersion(Integer osType) throws Exception {
        if(null == osType)
            return Result.buildParameterError();

        Map resultMap = getMobileFrameAppDAO().getCurrentVersion(osType);
        
        if(resultMap.containsKey("filePath")) {
            //锟斤拷锟斤拷锟??锟斤拷实锟斤拷锟侥硷拷路锟斤拷
            resultMap.remove("filePath");
        }
        
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        resultData.put(MobileFrameApp.FRAME_APP_NODE, resultMap);
        
        Result result = Result.buildSuccess();
        result.setResultData(resultData);
        return result;
    }
    
    public Map getLatestFrameApp(Integer osType) throws Exception {
        if(null == osType) return Collections.EMPTY_MAP;
        
        Map resultMap = Collections.EMPTY_MAP;
        try {
        	resultMap = getMobileFrameAppDAO().getCurrentVersion(osType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public Result addFackback(MobileFeedback feedback) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileFeedback.TITLE_NODE, feedback.getTitle());
        paramMap.put(MobileFeedback.CONTENT_NODE, feedback.getContent());
        paramMap.put(MobileFeedback.CONTACT_NODE, feedback.getContact());
        paramMap.put(MobileFeedback.CONTACT_TYPE_NODE, feedback.getContactType());
        paramMap.put(MobileFeedback.FEEDBACK_TIME_NODE, new Date());
        
        getMobileFeedbackDAO().insert(paramMap);
        Result result  = Result.buildSuccess();
        
/*        if(true) {
            throw new Exception("锟街讹拷锟阶筹拷锟届常锟斤拷证锟斤拷锟斤拷");
        }*/
        
        return result;
    }

    public Result selStaffShortcut(Long staffId, Integer osType) throws Exception {
        if(null == staffId || null == osType) {
            return Result.buildParameterError();
        }

        getMobileStaffShortcutDAO().selStaffShortcut(staffId, osType);
        Result result = Result.buildSuccess();
        
        return result;
    }

    @Transactional(rollbackFor={Throwable.class})
    public Result addStaffShortcut(MobileStaffShortcut shortcut) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileStaffShortcut.STAFF_ID_NODE, shortcut.getStaffId());
        paramMap.put(MobileStaffShortcut.MENU_ID_NODE, shortcut.getMenuId());
        paramMap.put(MobileStaffShortcut.OS_TYPE_NODE, shortcut.getOsType());
        paramMap.put(MobileStaffShortcut.OPT_TIME_NODE, new Date());
        //
        getMobileStaffShortcutDAO().insert(paramMap);
        Result result  = Result.buildSuccess();
        
        return result;
    }

    @Transactional(rollbackFor={Throwable.class})
    public Result updateStaffShortcut(MobileStaffShortcut shortcut) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileStaffShortcut.STAFF_ID_NODE, shortcut.getStaffId());
        paramMap.put(MobileStaffShortcut.MENU_ID_NODE, shortcut.getMenuId());
        paramMap.put(MobileStaffShortcut.OS_TYPE_NODE, shortcut.getOsType());
        paramMap.put(MobileStaffShortcut.OPT_TIME_NODE, new Date());
        //
        getMobileStaffShortcutDAO().update(paramMap);
        Result result = Result.buildSuccess();
        
        return result;
    }

    @Transactional(rollbackFor={Throwable.class})
    public Result deleteStaffShortcut(Long staffShortcutId) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(MobileStaffShortcut.STAFF_SHORTCUT_ID_NODE, staffShortcutId);
        
        getMobileStaffShortcutDAO().delete(paramMap);
        Result result = Result.buildSuccess();
        
        return result;
    }

    public Result selNoticeByPage(String startNoticeTime, 
    		String endNoticeTime, int pageIndex, int pageSize) throws Exception {
        Result result = null;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(startNoticeTime)) {
            paramMap.put("startNoticeTime", startNoticeTime);
        }
        if(StringUtils.isNotBlank(endNoticeTime)) {
            paramMap.put("endNoticeTime", endNoticeTime);
        }
        paramMap.put("pageIndex", pageIndex);
        paramMap.put("pageSize", pageSize);
        
        try {
            Map resultMap = getMobileNotificationDAO().selByPage(paramMap);
            
            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileNotice.NOTICE_PAGE_NODE, resultMap);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.buildServerError();
        }
        return result;
    }
    
    public Result selNoticeByPage(long latestNoticeTimestamp, int pageIndex, int pageSize) throws Exception {
    	Date date = new Date(latestNoticeTimestamp);
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return selNoticeByPage(df.format(date), null, pageIndex, pageSize);
    }
    
    public Result selNotice(long latestNoticeTimestamp) throws Exception {
    	Date date = new Date(latestNoticeTimestamp);
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	return selNotice(df.format(date), null);
    }
    
    /**
     */
    public Result selNotice(String startNoticeTime, String endNoticeTime) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	String vStartNoticeTime = null;
    	String vEndNoticeTime = null;
    	//锟斤拷锟斤拷-1锟斤拷锟斤拷么锟斤拷说锟斤拷锟角碉拷一锟轿伙拷取
    	if("-1".equals(startNoticeTime) && "-1".equals(endNoticeTime)) {
    		if(logger.isDebugEnabled()) {
    			logger.debug("客户端第一次获取公共消息");
    		}
    		//
    		vStartNoticeTime = null;
    		vEndNoticeTime = df.format(new Date());
    	} else {
    		vStartNoticeTime = startNoticeTime;
    		vEndNoticeTime = df.format(new Date());
    	}

    	
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startNoticeTime", vStartNoticeTime);
        paramMap.put("endNoticeTime", vEndNoticeTime);
        
        List resultList = getMobileNotificationDAO().selAll(paramMap);
        
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        resultData.put(MobileNotice.START_NOTICE_TIME_NODE, vStartNoticeTime);
        resultData.put(MobileNotice.END_NOTICE_TIME_NODE, vEndNoticeTime);
        resultData.put(MobileNotice.NOTICE_LIST_NODE, resultList);
        
        Result result = Result.buildSuccess();
        result.setResultData(resultData);
        
        return result;
    }
    
    @Transactional(rollbackFor={Throwable.class})
    public void writeUploadLog(Map paramMap) throws Exception {
    	getMobileUploadLogDAO().insert(paramMap);
    }
    
    @Transactional(rollbackFor={Throwable.class})
	public void writeDownloadLog(Map paramMap) throws Exception {
    	getMobileDownloadLogDAO().insert(paramMap);
	}
    
    /**
    *
    * @param passwd1 - 锟矫伙拷锟斤拷锟斤拷锟斤拷锟斤拷
    * @param passwd2 - 锟斤拷菘锟斤拷取锟斤拷锟斤拷
    * @return
    */
   private boolean validatePasswrod(String passwd1, String passwd2) {
       boolean flag = false;
       byte[] bytes = passwd1.getBytes();
       String mPasswd1 = "";
       if (passwd2.contains("{SHA}")) {
    	   mPasswd1 = "{SHA}" + new String(Base64It.encode(bytes));
       } else if (passwd2.contains("{MD5}")) {
    	   mPasswd1 = "{MD5}" + new String(MD5Utils.EncoderByMd5(passwd1));
       }
       
       if (passwd1.equals(passwd2) || mPasswd1.equals(passwd2)) {
           flag = true;
       }
       return flag;
   }
   
   public Result appInit(Integer osType) throws Exception {
	   List funcAppList = this.getMobileAppDAO().getFuncAppList(osType);
	   //List paramList = this.getMobileParamDAO().getParamForApp(osType);
       Map frameAppMap = this.getMobileFrameAppDAO().getCurrentVersion(osType);
       
        //锟斤拷锟斤拷锟??锟斤拷实锟斤拷锟侥硷拷路锟斤拷
       if(frameAppMap.containsKey("filePath")) {
          frameAppMap.remove("filePath"); 
       }

       Map<Object, Object> resultMap = new HashMap<Object, Object>();
       resultMap.put(MobileApp.INTEGR_APP_NODE, funcAppList);
       resultMap.put(VersionInfo.VERSION_INFO_NODE, frameAppMap);
       //resultMap.put(MobileParam.PARAM_NODE, paramList);
       //???To be more???
       
       Result result = Result.buildSuccess();
       result.setResultData(resultMap);
       
       return result;
   }

	public Map getAppById(Long appId) {
//		if(null == osType)
//            return Result.buildParameterError();

        Map map = null;
        try {
            Map resultMap = this.getMobileAppDAO().getAppById(appId);
            //锟斤拷锟斤拷锟??锟斤拷实锟斤拷锟侥硷拷路锟斤拷
//            resultMap.remove("filePath");
            
//            result = Result.buildSuccess();
//            result.setResultData(resultMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
//            result = Result.buildServerError();
        }
        return map;
	}
	
	public Map getFrameAppById(Long appId) {
        if(null == appId) return Collections.EMPTY_MAP;
        
        Map resultMap = Collections.EMPTY_MAP;
        try {
        	resultMap = getMobileFrameAppDAO().getFrameAppById(appId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
	}
	
	public Map getFrameAppByIdServer(Long appId) {
        if(null == appId) return Collections.EMPTY_MAP;
       Map paramMap= new HashMap();
       paramMap.put("frameAppId", appId);
        Map resultMap = Collections.EMPTY_MAP;
        try {
        	resultMap = getMobileFrameAppDAO().selById(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
	}	
	@Transactional(rollbackFor={Throwable.class})
	public void writeExceptionLog(Long restServiceId, 
			 String excepName, String excepMsg) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobileExceptionLog.EXCEPTION_NAME_NODE, excepName);
		paramMap.put(MobileExceptionLog.EXCEPTION_MSG_NODE, excepMsg);
		paramMap.put(MobileExceptionLog.EXCEPTION_TIME_NODE, new Date());
		this.getMobileExceptionDAO().insert(paramMap);
	}

	public Result appSync(Long staffId, Long jobId, Long defaultJobId,
			Integer osType) throws Exception {
		
        List<Map> menuCagalogList = getMobileMenuDAO().queryMenuCatalog(staffId, jobId, defaultJobId, osType);
        List<Map> menuList = getMobileMenuDAO().querySubMenu(staffId, jobId, defaultJobId, osType);
        List<Map> menuConfigList = getMobileMenuConfigDAO().selMenuConfigByCons(staffId, jobId, defaultJobId, osType);

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        //锟剿碉拷锟斤拷锟斤拷锟?
        resultData.put(MobileMenuCatalog.MENU_CATALOG_NODE, menuCagalogList);
        resultData.put(MobileMenu.MENU_NODE, menuList);
        resultData.put(MobileMenuConfig.MENU_CONFIG_NODE, menuConfigList);
        //锟剿猴拷映锟斤拷锟斤拷锟?
        List<Map> staffMappingList = this.getMobileStaffMappingDAO().getMappingInfoByStaffId(staffId);
        resultData.put(MobileStaffMapping.STAFF_MAPPING_LIST_NODE, staffMappingList);
        
        
		//TODO 锟竭硷拷未锟斤拷锟?
		return Result.buildSuccess();
	}
	
    public Result selDefaultJob(Long staffId) throws Exception {
    	if(null == staffId) 
    		return Result.buildParameterError();
    	
    	Map<Object, Object> resultData = new HashMap<Object, Object>();
    	//锟斤拷取默锟斤拷职位锟斤拷息
        Map defaultJob = getMobileCommonDAO().getDefaultJob(staffId);
        resultData.put(JobInfo.DEFAULT_JOB_NODE, defaultJob);
        
    	return Result.buildSuccess(resultData);
    }
    
    public Result selNormalJobList(Long staffId) throws Exception {
    	if(null == staffId) 
    		return Result.buildParameterError();
    	
    	Map<Object, Object> resultData = new HashMap<Object, Object>();
    	//锟斤拷取锟斤拷默锟斤拷职位锟叫憋拷
    	List<Map> normalJobList = getMobileCommonDAO().getNormalJobList(staffId);
    	resultData.put(JobInfo.NORMAL_JOB_LIST_NODE, normalJobList);
        
    	return Result.buildSuccess(resultData);
    }
    
    public Result selJobList(Long staffId) throws Exception {
    	if(null == staffId) 
    		return Result.buildParameterError();
    	
    	Map<Object, Object> resultData = new HashMap<Object, Object>();
    	
    	//锟斤拷取默锟斤拷职位锟斤拷息
        Map defaultJob = getMobileCommonDAO().getDefaultJob(staffId);
        resultData.put(JobInfo.DEFAULT_JOB_NODE, defaultJob);
    	
    	//锟斤拷取锟斤拷默锟斤拷职位锟叫憋拷
    	List<Map> normalJobList = getMobileCommonDAO().getNormalJobList(staffId);
    	resultData.put(JobInfo.NORMAL_JOB_LIST_NODE, normalJobList);
        
    	return Result.buildSuccess(resultData);
    }
    
    @Transactional(rollbackFor={Throwable.class})
    public Result addPhotoRecord(MobilePhotoRecord record) throws Exception {
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put(MobilePhotoRecord.PHOTO_RECORD_ID_NODE, record.getPhotoRecordId());
    	paramMap.put(MobilePhotoRecord.PHOTO_NAME_NODE, record.getPhotoName());
    	paramMap.put(MobilePhotoRecord.PHOTO_OWNER_NODE, record.getPhotoOwner());
    	paramMap.put(MobilePhotoRecord.BATCH_NO_NODE, record.getBatchNo());
    	paramMap.put(MobilePhotoRecord.UPLOAD_TIME_NODE, new Date());
    	paramMap.put(MobilePhotoRecord.FILE_PATH_NODE, record.getFilePath());
    	paramMap.put(MobilePhotoRecord.FB_ID, record.getFbId());
    	paramMap.put(MobilePhotoRecord.RESOURCE_ID, record.getResourceId());
    	paramMap.put(MobilePhotoRecord.RESOURCE_NAME, record.getResourceName());
    	paramMap.put(MobilePhotoRecord.WORK_ORDER_ID, record.getWorkOrderId());
    	Map resultMap = getMobilePhotoRecordDAO().insert(paramMap);
    	
    	Result result = null;
    	if(null == resultMap || 0 == resultMap.size()) {
    		result = Result.buildPhotoUploadError();
    	} else {
    		if(resultMap.containsKey(MobilePhotoRecord.FILE_PATH_NODE)) {
    			resultMap.remove(MobilePhotoRecord.FILE_PATH_NODE);
    		}
    		result = Result.buildSuccess(resultMap);
    	}
    	return result;
    }

	public void frameAppDownloadCount(Long frameAppId) throws Exception {
		if(null == frameAppId) 
			return;
		
		this.getMobileFrameAppDAO().updateDownloadCount(frameAppId);
	}

	public void appDownloadCount(Long appId) throws Exception {
		if(null == appId) 
			return;
		this.getMobileAppDAO().updateDownloadCount(appId);
	}

	/** 锟斤拷锟斤拷锟斤拷锟斤拷状态 */
	@Transactional(rollbackFor = {Throwable.class})
	public boolean updateConnectState(StaffControls ctrl, MobileRestService serv)
			throws Exception {

		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		try {
			paramMap.put(StaffInfo.STAFF_ID_NODE, ctrl.getStaffId());
			paramMap.put("connectState", "1");
			getMobileSessionRecordDAO().updateConnectState(paramMap);

			paramMap.put("lastVisitTime", new Date());
			paramMap.put("serviceId", serv.getRestServiceId());
			paramMap.put("serviceName", serv.getServName());
			
			getMobileSessionRecordDAO().updateLastVisitTime(paramMap);

			if(logger.isDebugEnabled()) {
				logger.debug("更新用户连接状态成功：" + paramMap);
			}
			
			return true;

			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(logger.isDebugEnabled()) {
				logger.debug("更新用户连接状态失败");
			}
			
		}
		return false;
	}
	
	/** 锟斤拷取锟斤拷锟竭硷拷锟斤拷欠锟斤拷写锟斤拷锟皆? */
	@Transactional(rollbackFor = {Throwable.class})
	public boolean isConnectStaffExist(StaffControls ctrl)
			throws Exception {

		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		try {
			paramMap.put(StaffInfo.STAFF_ID_NODE, ctrl.getStaffId());
			Map retMap = getMobileSessionRecordDAO().selById(paramMap);
			if(logger.isDebugEnabled()) {
				logger.debug("获取用户连接状态成功：" + paramMap);
			}
			
			if (retMap == null) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(logger.isDebugEnabled()) {
				logger.debug("获取用户连接状态失败");
			}
		}
		return false;
	}
	
	/** 锟斤拷锟斤拷锟斤拷员锟斤拷锟斤拷状态 */
	@Transactional(rollbackFor = {Throwable.class})
	public boolean insertConnectStaff(StaffControls ctrl, MobileRestService serv)
			throws Exception {

		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		try {
			paramMap.put(StaffInfo.STAFF_ID_NODE, ctrl.getStaffId());
			paramMap.put(StaffInfo.STAFF_NAME_NODE, ctrl.getStaffName());
			paramMap.put(StaffInfo.USERNAME_NODE, ctrl.getUsername());
			paramMap.put("lastVisitTime", new Date());
			paramMap.put("createTime", new Date());
			paramMap.put("serviceId", serv.getRestServiceId());
			paramMap.put("serviceName", serv.getServName());
			paramMap.put("state", 1);
			
			getMobileSessionRecordDAO().insert(paramMap);
			
			// 默锟斤拷1000M
			paramMap.put("userFlowLimit", "1000");
			paramMap.put("userConnPriv", "1");
			paramMap.put("connectState", "1");
			getMobileSessionRecordDAO().updateUserFlowLimit(paramMap);
			getMobileSessionRecordDAO().updateUserConnPriv(paramMap);
			getMobileSessionRecordDAO().updateConnectState(paramMap);

			if(logger.isDebugEnabled()) {
				logger.debug("新增人员在线状态成功：" + paramMap);
			}
			
			return true;

			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(logger.isDebugEnabled()) {
				logger.debug("新增人员在线状态失败");
			}
			
		}
		return false;
	}

	public List getStaffFlowCount(Long staffId) throws Exception {
		return this.getMobileRestServLogDAO().getStaffFlowCount(staffId);
	}
	
	public List getAppDownloadCount(Map paramMap) throws Exception {
		return this.getMobileDownloadLogDAO().selAppDownloadStat(paramMap);
	}
	
	public List getAppDownloadLatestMonthStat(Map paramMap) throws Exception {
		return this.getMobileDownloadLogDAO().selAppDownloadLatestMonthStat(paramMap);
	}
	
	public List getAppDownloadPerMonthStat(Map paramMap) throws Exception {
		return this.getMobileDownloadLogDAO().selAppDownloadPerMonthStat(paramMap);
	}
	
	public Result getStaffByOpenId(JSONObject json) throws Exception {
		System.out.println("getStaffbyopenid...........................................");
		  Result   result=  null;
		 String openId = json.optString("openid");
	     String yxwxType = json.optString("type");
	     try
	     {
		 Map staffYxwxMap = getMobileCommonDAO().getStaffByOpenId(openId, yxwxType);
		 result = Result.buildSuccess(staffYxwxMap);
	     }catch(Exception e){
	    	    e.printStackTrace();
	        	result= Result.buildFailure();
	     }
		return result;
	}
    /**
     *  锟剿号帮拷
     * 
     *  */
    public Result accBinding(JSONObject json) throws Exception {
        String username = json.optString("username");
        String password = json.optString("password");
        String openId = json.optString("openid");
        String yxwxType = json.optString("yxwxType");
        logger.debug("username="+username+",password="+password+",openid="+openId+",yxwxType="+yxwxType);
        Map<String , Object> staffMap = getMobileCommonDAO().getStaffByUsername(username);

        //锟斤拷证锟矫伙拷锟角凤拷锟斤拷锟?
        String mUsername = MapUtils.getString(staffMap, StaffInfo.USERNAME_NODE, null);
        if(StringUtils.isBlank(mUsername)) {
            return new Result(Constants.OptCode.USERNAME_ERROR,Constants.OptMsg.USERNAME_ERROR);
        }        
        //锟斤拷证锟斤拷锟斤拷锟角凤拷锟斤拷确
       String mPassword = MapUtils.getString(staffMap, StaffInfo.PASSWORD_NODE, null);
            //锟斤拷锟斤拷
        String dePassword = CryptUtils.decryptString(password);
            
        if(!validatePasswrod(dePassword, mPassword)) {
                return new Result(Constants.OptCode.PASSWORD_ERROR,Constants.OptMsg.PASSWORD_ERROR);
        }
        Result   result=  Result.buildSuccess();
        try
        {
         Map paramMap = new HashMap();
         paramMap.put("username", username);
         paramMap.put("openid", openId);
         paramMap.put("type", yxwxType);
         //锟斤拷锟叫断达拷锟节诧拷锟斤拷锟节ｏ拷锟斤拷锟斤拷锟斤拷锟津不革拷锟铰ｏ拷锟斤拷锟斤拷锟斤拷锟?
         Map staffYxwxMap = getMobileCommonDAO().getStaffByOpenId(openId, yxwxType);

         if (staffYxwxMap==null)
            getMobileCommonDAO().insertUosStaffYxwx(paramMap);
         else{
        	 staffYxwxMap.put("username", username);
        	 getMobileCommonDAO().updateUosStaffYxwx(staffYxwxMap);
         }
        }catch(Exception e){
        	e.printStackTrace();
        	result= Result.buildFailure();
        }
    	return result;
    }

    @Transactional(rollbackFor = {Throwable.class})
	public Result addWkOrderPhotoRecord(MobilePhotoRecord record)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put(MobilePhotoRecord.PHOTO_RECORD_ID_NODE, record.getPhotoRecordId());
    	paramMap.put(MobilePhotoRecord.PHOTO_NAME_NODE, record.getPhotoName());
    	paramMap.put(MobilePhotoRecord.PHOTO_OWNER_NODE, record.getPhotoOwner());
    	paramMap.put(MobilePhotoRecord.UPLOAD_TIME_NODE, new Date());
    	paramMap.put(MobilePhotoRecord.FILE_PATH_NODE, record.getFilePath());
    	paramMap.put(MobilePhotoRecord.WORK_ORDER_ID, record.getWorkOrderId());
    	paramMap.put(MobilePhotoRecord.RESOURCE_ID, record.getResourceId());//锟借备ID
    	paramMap.put(MobilePhotoRecord.RESOURCE_NAME, record.getResourceName());//锟剿匡拷ID
    	paramMap.put(MobilePhotoRecord.FB_ID, record.getFbId());//锟斤拷锟斤拷锟斤拷
    	Map resultMap = getMobilePhotoRecordDAO().insertWkOrderPhotoRecord(paramMap);
    	
    	Result result = null;
    	if(null == resultMap || 0 == resultMap.size()) {
    		result = Result.buildPhotoUploadError();
    	} else {
    		result = Result.buildSuccess(resultMap);
    	}
    	return result;
	}

	public String getWkorderCodeBarFlag(String wkorderId, String codeBar)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobilePhotoRecord.WORK_ORDER_ID, wkorderId);
		paramMap.put("codeBar", codeBar);
		Map resultMap = getMobileSessionRecordDAO().getWkorderCodeBarFlag(paramMap);
		if(resultMap == null || resultMap.size() == 0)
		{
			return "0";
		}
		return MapUtils.getString(resultMap, "bar_check_flag", null);
	}

	public Result getMakingDataByWkOrder(String wkOrderId) throws Exception {
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> progressMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = getMobileCommonDAO().getMakeDataByWkOrderId(wkOrderId);
		if (resultMap == null || resultMap.size() == 0) 
		{
			resultData.put("data_info", resultMap);
			resultData.put("progress_info", progressMap);
			result = Result.buildSuccess();
			result.setResultData(resultData);
			return result;
		}
		String returnNum = MapUtils.getString(resultMap, "returnNum",null);
		if ("-1".equals(returnNum)) 
		{
			logger.error("根据工单查询数据制作界面信息失败");
			result = Result.buildServerError();
			return result;
		}
		String makingFlag = MapUtils.getString(resultMap, "makingFlag",null);
		if ("1".equals(makingFlag)) {
			//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖?拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟较?
			logger.info("查询数据制作进度信息:"+resultMap);
			progressMap = getMobileCommonDAO().getMakeProgressByWkOrderId(wkOrderId,"");
			if (progressMap!=null && progressMap.size() > 0)
			{
				returnNum = MapUtils.getString(progressMap, "returnNum",null);
				if ("-1".equals(returnNum)) 
				{
					//锟斤拷询锟斤拷锟斤拷锟较⑹э拷芙锟斤拷锟较?拷锟斤拷氐锟角疤?
					logger.error("根据工单查询数据制作进度信息失败");
					result = Result.buildServerError();
					return result;
				}
			}
		}
		//锟斤拷询锟缴癸拷锟斤拷锟斤拷息锟斤拷锟截碉拷前台
		resultData.put("data_info", resultMap);
		resultData.put("progress_info", progressMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}

	@Transactional(rollbackFor={Throwable.class})
	public Result addMakeDataProgress(Map makeData) throws Exception {
		Map resultMap = getMobileCommonDAO().addMakeDataInfo(makeData);
    	Result result = null;
    	if(null == resultMap || 0 == resultMap.size()) {
    		result = Result.buildParameterError();
    	} else {
    		result = Result.buildSuccess(resultMap);
    	}
    	return result;
	}

	public Result commitTerminalInfo(Map makeData) throws Exception {
		Map resultMap = getMobileCommonDAO().commitTerminalInfo(makeData);
    	Result result = null;
    	if(null == resultMap || 0 == resultMap.size()) {
    		result = Result.buildParameterError();
    	} else {
    		result = Result.buildSuccess(resultMap);
    	}
    	return result;
	}

	public Result getOLTInfoByName(String oltName,String wkOrderId) throws Exception {
		if(null == oltName) 
    		return Result.buildParameterError();
		
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		List<Map> oltList = getMobileCommonDAO().qryOltByName(oltName,wkOrderId);
		resultData.put("olt_list", oltList);
		
		return Result.buildSuccess(resultData);
	}

	public Result getOLTPonInfo(String oltId) throws Exception {
		if(null == oltId) 
    		return Result.buildParameterError();
		
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		List<Map> ponList = getMobileCommonDAO().qryOltPonPortById(oltId);
		resultData.put("olt_pon_list", ponList);
		
		return Result.buildSuccess(resultData);
	}

	public Result queryTerminalInfo(String orderId) throws Exception {
		if (orderId == null)
			return Result.buildParameterError();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		Map<String,Object> terminalInfo = new HashMap<String,Object>();
		terminalInfo = getMobileCommonDAO().getTerminalByWkOrderId(orderId);
		if (terminalInfo !=null){
			String returnNum = MapUtils.getString(terminalInfo, "returnNum",null);
			if ("-1".equals(returnNum)){
				String errMsg = MapUtils.getString(terminalInfo, "flag_desc",null);
				logger.error("根据工单查询数终端信息失败:"+orderId);
				return Result.buildWorkOrderError(errMsg+orderId);
			}
		}
		resultData.put("data_info", terminalInfo);
		return Result.buildSuccess(resultData);
	}

	public Result reportCommonQuery(String retrieveDate,String staffId,String reportType) throws Exception {
		String report_str = getMobileCommonDAO().getReportInfo(retrieveDate, staffId, reportType);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonTestQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportTestInfo(retrieveDate, staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonCompanyQuery(String retrieveDate,String areaName) throws Exception {
		String report_str = getMobileCommonDAO().getReportCompanyInfo(retrieveDate, areaName);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	
	public Result reportCommonCompanyStaffQuery(String retrieveDate,String areaName,String company) throws Exception {
		String report_str = getMobileCommonDAO().getReportCompanyStaffInfo(retrieveDate, areaName,company);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonStaffDayQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportStaffDayInfo(retrieveDate, staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonOrderAlarmQuery(String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getOrderAlarmInfo(staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}

	public Result reportCommonOrderDetailQuery(String accNbr,String areaNbr) throws Exception {
		String report_str = getMobileCommonDAO().getOrderDetailInfo(accNbr,areaNbr);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}

	public Result reportCommonCompanyLoginQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportCompanyLoginInfo(retrieveDate,staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonCompanyDwLoginQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportCompanyDwLoginInfo(retrieveDate,staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonCompanyDetailLoginQuery(String retrieveDate,String areaName) throws Exception {
		String report_str = getMobileCommonDAO().getReportCompanyDetailLoginInfo(retrieveDate,areaName);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonStaffLoginQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportStaffLoginInfo(retrieveDate,staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}
	
	public Result reportCommonManagerLoginQuery(String retrieveDate,String staffId) throws Exception {
		String report_str = getMobileCommonDAO().getReportManagerLoginInfo(retrieveDate,staffId);
//		logger.info("锟斤拷台锟斤拷锟截的憋拷锟斤拷锟斤拷息:"+report_str);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("DataContent", report_str);
		return Result.buildSuccess(resultData);
	}

	public Result getModifyOrderInfoByOrderId(String orderId, String staffId) {
		// TODO Auto-generated method stub
		Map<String, Object> orderIdMap = getMobileCommonDAO().getProduceOrderIdByOrderId(orderId);
		Map<String, Object> regionMap = getMobileCommonDAO().getRegionInfoByOrderId(orderId);
		Map<String, Object> staffMap = getMobileCommonDAO().getStaffInfoByStaffId(staffId);
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("produceOrderId", orderIdMap.get("produceOrderId"));
		resultData.put("eparchyCode", regionMap.get("eparchyCode"));
		resultData.put("countyCode", regionMap.get("countyCode"));
		resultData.put("countyName", regionMap.get("countyName"));
		
		//手机号为空时传递工号
		String mobileTel = (String) staffMap.get("mobileTel");
		if(StringUtil.isNull(mobileTel)){
			resultData.put("userName", staffMap.get("userName"));
		}else{
			resultData.put("userName", mobileTel);
		}
//		resultData.put("userName", staffMap.get("userName"));
		resultData.put("serviceType", regionMap.get("serviceType"));
		logger.info(orderIdMap.toString());
		logger.info(regionMap.toString());
		logger.info(staffMap.toString());
		resultData.put("orderCode", regionMap.get("orderCode"));
		logger.info("orderCode:" + regionMap.get("orderCode"));
		return Result.buildSuccess(resultData);
	}
	
	public void insertIntfLogInfo(String operName,String orderCode,String returnDesc){
		try {
			getMobileCommonDAO().insertIntfLog(orderCode, operName, returnDesc);
		} catch (DataAccessException e) {
			logger.error("插入接口信息失败" + e.getMessage());
		}
	}
	
	public boolean validateModifyOrder(String orderId){
		boolean validateResult = false;
		validateResult = getMobileCommonDAO().validateModifyOrder(orderId);
		return validateResult;
	}
	
	public Result getInstallDataByQrCode(String qrCode, String wkOrderId) throws Exception {
		Result result = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = getMobileCommonDAO().getInstallDataByQrCode(qrCode,wkOrderId);

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("data_info", resultMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}
	
	@Transactional(rollbackFor={Throwable.class})
	public Result addMakeDataSMProgress(Map makeData) throws Exception {
		Map resultMap = getMobileCommonDAO().addMakeDataSMInfo(makeData);
    	Result result = null;
    	if(null == resultMap || 0 == resultMap.size()) {
    		result = Result.buildParameterError();
    	} else {
    		result = Result.buildSuccess(resultMap);
    	}
    	return result;
	}
	
	public Result getSmMakingDataByWkOrder(String wkOrderId) throws Exception {
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> progressMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = getMobileCommonDAO().getMakeDataByWkOrderId(wkOrderId);
		if (resultMap == null || resultMap.size() == 0) {
			resultData.put("data_info", resultMap);
			resultData.put("progress_info", progressMap);
			result = Result.buildSuccess();
			result.setResultData(resultData);
			return result;
		}
		String returnNum = MapUtils.getString(resultMap, "returnNum",null);
		if ("-1".equals(returnNum)) 
		{
			logger.error("根据工单查询数据制作界面信息失败");
			result = Result.buildServerError();
			return result;
		}
		String makingFlag = MapUtils.getString(resultMap, "makingFlag",null);
		if ("1".equals(makingFlag)) {
			logger.info("查询数据制作进度信息:"+resultMap);
			progressMap = getMobileCommonDAO().getMakeProgressByWkOrderId(wkOrderId,"sm");
			if (progressMap!=null && progressMap.size() > 0)
			{
				returnNum = MapUtils.getString(progressMap, "returnNum",null);
				if ("-1".equals(returnNum)) 
				{
					logger.error("根据工单查询数据制作进度信息失败");
					result = Result.buildServerError();
					return result;
				}
			}
		}
		resultData.put("data_info", resultMap);
		resultData.put("progress_info", progressMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}
	
	public boolean validateSmInstallOrder(String orderId){
		boolean validateResult = false;
		validateResult = getMobileCommonDAO().validateSmInstallOrder(orderId);
		return validateResult;
	}
	
	public Result pxCodeBind(String portId, String pxCode,String staffId) {
		Result result = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = getMobileCommonDAO().pxCodeBind(portId,pxCode,staffId);

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("data_info", resultMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}
	
	public boolean validatePxCode(String pxCode){
		boolean validateResult = false;
		validateResult = getMobileCommonDAO().validatePxCode(pxCode);
		return validateResult;
	}
	

	
}
