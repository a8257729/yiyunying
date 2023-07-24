package com.ztesoft.mobile.v2.service.common;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobileFeedback;
import com.ztesoft.mobile.v2.entity.common.MobilePhotoRecord;
import com.ztesoft.mobile.v2.entity.common.MobileStaffShortcut;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: ????6:52
 */
public interface MobileCommonService {

    /**
     * ??????
     * @param staffInfo
     * @return
     */
    public Result login(StaffInfo staffInfo) throws Exception;
    
    /**
     * ?????????????
     * @param openId
     * @return
     */
    public Result login(String openId) throws Exception;

    /**
     * ??????
     * @param staffInfo
     * @return
     */
    public Result logout(StaffInfo staffInfo) throws Exception;

    public Result exit(StaffInfo staffInfo) throws Exception;

    public Result updatePassword(Long staffId, String oldPasswd, String newPasswd) throws Exception;

    /** ????????????汾??? */
    public Result getFrameAppCurrentVersion(Integer osType) throws Exception;
    
    public Map getLatestFrameApp(Integer osType) throws Exception;
    
    public Map getAppById(Long appId) throws Exception;
    
    public Map getFrameAppById(Long appId) throws Exception;
    
    public Map getFrameAppByIdServer(Long appId)  throws Exception;
    /** ????????????汾??? */
    public Result addFackback(MobileFeedback feedback) throws Exception;

    public Result addStaffShortcut(MobileStaffShortcut shortcut) throws Exception;

    public Result selStaffShortcut(Long staffId, Integer osType) throws Exception;

    public Result updateStaffShortcut(MobileStaffShortcut shortcut) throws Exception;

    public Result deleteStaffShortcut(Long staffShortcutId) throws Exception;

    /**
     * 
     * @param startNoticeTime - ???: yyyymmddhhmiss????20130303121231
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result selNoticeByPage(String startNoticeTime, String endNoticeTime, int pageIndex, int pageSize) throws Exception;
    
    public Result selNotice(String startNoticeTime, String endNoticeTime) throws Exception;
    
    public Result selNotice(long latestNoticeTimestamp) throws Exception;
    
    /**
     * 
     * @param startNoticeTime - ???: yyyymmddhhmiss????20130303121231
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Result selNoticeByPage(long latestNoticeTimestamp, int pageIndex, int pageSize) throws Exception;
    
    public void writeUploadLog(Map paramMap) throws Exception;
    
    public void writeDownloadLog(Map paramMap) throws Exception;
    
    public void writeExceptionLog(Long restServiceId, String excepName, String excepMsg) throws Exception;
    
    public Result appInit(Integer osType) throws Exception;
    
    /** 
     * ??????ID???λID??????λID???????????????????????????????
     * ????????????У????????????????????????????????????????
     * @param staffId
     * @param jobId
     * @param defaultJobId
     * @param osType
     * @return
     * @throws Exception
     */
    public Result appSync(Long staffId, Long jobId, Long defaultJobId, Integer osType) throws Exception;

    /**
     * ???staffId?????????λ???
     * @param staffId
     * @return
     * @throws Exception
     */
    public Result selDefaultJob(Long staffId) throws Exception;
    
    /**
     * ???staffId??????λ?б????
     * @param staffId
     * @return
     * @throws Exception
     */
    public Result selNormalJobList(Long staffId) throws Exception;
    
    /**
     * ???staffId??????λ?б??????λ???
     * @param staffId
     * @return
     * @throws Exception
     */
    public Result selJobList(Long staffId) throws Exception;
    
    /** ??????????? */
    public Result addPhotoRecord(MobilePhotoRecord record) throws Exception;
    
    /** ???????????????? */
    public void frameAppDownloadCount(Long frameAppId) throws Exception;
    
    /** ?????????????? */
    public void appDownloadCount(Long appId) throws Exception;
    
    /** ???STAFF_ID?????????? */
    public List getStaffFlowCount(Long staffId) throws Exception;
    
    /** ?????????? */
    public boolean updateConnectState(StaffControls ctrl, MobileRestService serv) throws Exception; 
    
    /** ??????????????д???? */
    public boolean isConnectStaffExist(StaffControls ctrl) throws Exception;
    
    /** ?????????????? */
    public boolean insertConnectStaff(StaffControls ctrl, MobileRestService serv) throws Exception;
    
    /** ???APP?????????? */
    public List getAppDownloadCount(Map paramMap) throws Exception;
    
    /** ???APP?????????????????????? */
    public List getAppDownloadLatestMonthStat(Map paramMap) throws Exception;
    
    /** ???APP????????????????????? */
    public List getAppDownloadPerMonthStat(Map paramMap) throws Exception;
    
    public Result getStaffByOpenId(JSONObject json) throws Exception;
    /** ???? */
    public Result accBinding(JSONObject json) throws Exception;
    
    /** ????????????????? */
    public Result addWkOrderPhotoRecord(MobilePhotoRecord record) throws Exception;
    
    public String getWkorderCodeBarFlag(String wkorderId,String codeBar) throws Exception;
    
    /**
     * ???????????????????
     * @param wkOrderId ????ID
     * */
    public Result getMakingDataByWkOrder(String wkOrderId) throws Exception;
    
    /** ????????????? */
    public Result addMakeDataProgress(Map makeData) throws Exception;
    
    /** ???????? */
    public Result commitTerminalInfo(Map makeData) throws Exception;
    
    /** ???OLT */
    public Result getOLTInfoByName(String oltName,String wkOrderId) throws Exception;
    
    /** ???PON?? */
    public Result getOLTPonInfo(String oltId) throws Exception;
    
    /** ?????????*/
    public Result queryTerminalInfo(String orderId) throws Exception;
    
    /**
     * ??????ò??
     * */
    public Result reportCommonQuery(String retrieveDate,String staffId,String reportType) throws Exception;
    
    
    /**
     * ??????ò??test
     * */
    public Result reportCommonTestQuery(String retrieveDate,String staffId) throws Exception;
    
    /**
     * ??????ò??company
     * */
    public Result reportCommonCompanyQuery(String retrieveDate,String areaName) throws Exception;
    
    /**
     * ??????ò??companyStaff
     * */
    public Result reportCommonCompanyStaffQuery(String retrieveDate,String areaName,String company) throws Exception;
    
    /**
     * ??????ò??StaffDay
     * */
    public Result reportCommonStaffDayQuery(String retrieveDate,String staffId) throws Exception; 
    
    /**
     * ??????ò??orderAlarm
     * */
    public Result reportCommonOrderAlarmQuery(String staffId) throws Exception; 
    /**
     * ??????ò??orderDetail
     * */
    public Result reportCommonOrderDetailQuery(String accNbr,String areaNbr) throws Exception;
    /**
     * ??????ò??companyLogin
     * */
    public Result reportCommonCompanyLoginQuery(String retrieveDate,String staffId) throws Exception;
    
    /**
     * ??????ò??companyDwLogin
     * */
    public Result reportCommonCompanyDwLoginQuery(String retrieveDate,String staffId) throws Exception;
    
    /**
     * ??????ò??companyDetail
     * */
    public Result reportCommonCompanyDetailLoginQuery(String retrieveDate,String areaName) throws Exception;
    
    /**
     * ??????ò??staffLogin
     * */
    public Result reportCommonStaffLoginQuery(String retrieveDate,String staffId) throws Exception;
    
    /**
     * ??????ò??ManagerLogin
     * */
    public Result reportCommonManagerLoginQuery(String retrieveDate,String staffId) throws Exception;


    /**
     * 查询改单信息
     * @param orderId
     * @param staffId 
     * @return
     */
	public Result getModifyOrderInfoByOrderId(String orderId, String staffId);
	
	/**
	 * 插入接口日志信息
	 * @param operName
	 * @param orderCode
	 * @param returnDesc
	 */
	public void insertIntfLogInfo(String operName,String orderCode,String returnDesc);

	/**
	 * 校验订单是否已经修改过 
	 * @param orderId
	 * @return
	 */
	public boolean validateModifyOrder(String orderId);

	/**
	 * 扫码装机，根据二维码查询分光器资源信息
	 * @param qrCode
	 * @return
	 */
	public Result getInstallDataByQrCode(String qrCode,String wkOrderId) throws Exception;

	/**
	 * 扫码装机数据制作
	 * @param data
	 * @return
	 */
	public Result addMakeDataSMProgress(Map<String, Object> data) throws Exception;

	/**
	 * 扫码装机进度查询
	 * @param wkOrderId
	 * @return
	 * @throws Exception
	 */
	public Result getSmMakingDataByWkOrder(String wkOrderId) throws Exception;

	public boolean validateSmInstallOrder(String orderId);

	public Result pxCodeBind(String portId, String pxCode, String staffId);

	public boolean validatePxCode(String pxCode);
}
