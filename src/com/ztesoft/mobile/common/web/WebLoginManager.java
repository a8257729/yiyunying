package com.ztesoft.mobile.common.web;

import java.util.*;
import javax.servlet.http.*;
import com.zterc.uos.*;
import com.zterc.uos.exception.*;
import com.zterc.uos.oaas.exception.*;
import com.zterc.uos.oaas.login.*;
import com.zterc.uos.oaas.service.areamanager.*;
import com.zterc.uos.oaas.service.orgmanager.*;
import com.zterc.uos.oaas.vo.*;
import com.zterc.uos.oaas.web.Messages;
import com.zterc.uos.runtime.*;
import com.zterc.uos.service.logger.dto.*;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import com.zterc.uos.oaas.helpers.ResourceFileUtil;
import com.zterc.uos.model.ParameterDTO;


/**
 * 登陆管理类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class WebLoginManager
    implements WebController {
    public WebLoginManager() {
    }

    private static Logger logger = Logger.getLogger(com.zterc.uos.oaas.web.
        WebLoginManager.class);

    public static void main(String[] args) {
    //    WebLoginManager _instance = new WebLoginManager();
    }

    /**集合对象，保存session 对象的引用*/
    private static Hashtable sessionHt = new Hashtable();
    private static final String PWD_EXPIRE_DATE = "pwd_expire_date";
    private static final String PWD_ALERT_DATE = "pwd_alert_date";
    private static final String IF_PWD_CHANGE_VALIDATE = "pwd_change";
    private static final String LOGON_ERROR_ALLOW_TIME =
        "LOGON_ERROR_ALLOW_TIME";

    //pwd_change 为1 验证密码是否过期
    private static final String PWD_CHANGE_VALIDATE = "1";

    //登陆时返回的值
    //1-密码错误，2-没有职位，3-登陆人数超出限制 4-密码超期 5-密码过期 8-用户被锁定
    private static final String STA_PWD_WRONG = "1";
    private static final String STA_NO_JOB = "2";
    private static final String LOG_ON_NUMBER_EXCEED = "3";
    private static final String PWD_ALERT = "4";
    private static final String PWD_EXPIRE = "5";
    private static final String LOG_SUCCESS = "6";
    private static final String LOG_ON_ERR_NUMBER_EXCEED = "7";
    private static final String STA_LOCKED ="8";

    //地域参数名
    private static final String PROVINCIAL_AREA_ID = "provincialAreaId";//省级地域id
    private static final String NATIVE_AREA_ID ="nativeAreaId";//本地地域id

    /**
     * 依据session id返回指定的session对象
     * @param sessionId String
     * @return HttpSession
     */
    public static HttpSession getSession(String sessionId) {
        return (HttpSession) sessionHt.get(sessionId);
    }

    private HttpServletRequest httpServletRequest = null;
    private HttpServletResponse httpServletResponse = null;

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

	/**
     * 福建要求做单点登陆，只需要用户名，不需要密码。
     *
     * @param username String
     * @throws UOSException
     * @return String
     */
    public StaffLogInfo login(String username) throws UOSException {
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(24 * 60 * 60);
        StaffLogInfo staffLogInfo = new StaffLogInfo();
        LoginManager loginMng = new LoginManager();

        try {
            /*首先判断当前帐户是否被锁定  liangli 2007.01.30*/
            Staff staff = loginMng.findByUserName(username);
            if(staff!=null){
                if(isLocked(staff.getLockedDate())) {
                    staffLogInfo.setReturnStr(STA_LOCKED);
                    return staffLogInfo;
                }
                else{
                    //如果锁定超过3分钟，则解锁  add by liangli 2007.01.30
                    if (staff.getLockedDate() != null) {
                        loginMng.updateLockedDate(staff.getStaffId(), false);
                    }
                }
            }

            /**查找人员所有职位*/
            Job[] jobs = loginMng.findJobsByStaff(staff.getStaffId());
            /**没有职位，非超级管理员*/
            if (jobs.length == 0 && staff.getStaffId() != 0) {
                /*没有职位*/
                staffLogInfo.setReturnStr(STA_NO_JOB);
                return staffLogInfo;
            }

			WorkOrderTranOrg[] workOrderTranOrgs = loginMng
					.findOrgsByStaffId(staff.getStaffId());

			// 保存和工号相关的组织信息。
			session.setAttribute("workOrderTranOrg", workOrderTranOrgs);

            /*保存人员信息*/
            session.setAttribute("staff", staff);
            /*保存职位信息*/
            session.setAttribute("jobs", jobs);
            /*保存语言代码*/
            session.setAttribute("language_code", "zh_cn");
            /**EOMS用*/
            session.setAttribute("staffDutyId", new Long(0));
            session.setAttribute("staff_daylog_id", new Long(0));
            /**允许登陆次数*/
            int logonNumber = staff.getLogonNumber();
            /**数据库中已有的记录*/
            StaffOnLineInfoDto[] logonMemberArr = loginMng.
                queryLogonStaffCount(new Long(staff.getStaffId()));
            //允许次数
            logger.info("logonNumber=" + logonNumber);
            //已经存在次数
            logger.info("logonNumberExist=" + logonMemberArr.length);
            /**判断是否超出允许值*/
            if (logonNumber <= logonMemberArr.length) {
                staffLogInfo.setReturnStr(LOG_ON_NUMBER_EXCEED);
                staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                staffLogInfo.setLogonNumber(String.valueOf(logonMemberArr.
                    length));
                staffLogInfo.setPermitLogonNumber(String.valueOf(logonNumber));
                /**必须强制退出的用户个数*/
                int mustQuitNumber = logonMemberArr.length - logonNumber + 1;
                staffLogInfo.setMustQuitNumber(String.valueOf(mustQuitNumber));
                String[] ipArr = new String[logonMemberArr.length];
                for (int i = 0; i < logonMemberArr.length; i++) {
                    ipArr[i] = logonMemberArr[i].getIp();
                }
                staffLogInfo.setLogonIpAdd(ipArr);
                return staffLogInfo;
            }
            /**判断是否考虑密码过期因素*/
            if (loginMng.findParameter(IF_PWD_CHANGE_VALIDATE) != null &&
                loginMng.findParameter(IF_PWD_CHANGE_VALIDATE).getValue().
                equals(PWD_CHANGE_VALIDATE)) {
                /**上次密码修改时间*/
                Date pwdModDate = staff.getPwdModDate();
                Date now = new Date();
                if (pwdModDate != null &&
                    loginMng.findParameter(PWD_EXPIRE_DATE) != null &&
                    loginMng.findParameter(PWD_ALERT_DATE) != null) {
                    long diff = this.calculateDayInteval(pwdModDate, now);
                    //密码必须修改的天数
                    int pwd_expire_days = Long.valueOf(loginMng.
                        findParameter(
                        PWD_EXPIRE_DATE).getValue()).intValue();
                    //密码建议修改的天数
                    int pwd_alert_days = Long.valueOf(loginMng.
                        findParameter(
                        PWD_ALERT_DATE).getValue()).intValue();
                    //logger.info("*********您上次密码修改距离今天已经有" + diff + "天了*******");
                    //logger.info("*********密码修改时间超过" + pwd_alert_days + "天就会弹出警告*******");
                    //logger.info("*********密码修改时间超过" + pwd_expire_days + "天就会拒绝登陆*******");
                    logger.info("********* Password has been " + diff + " days since the last revision *******");
                    logger.info("********* Password update date over " + diff + " days will be activated warning *******");
                    logger.info("********* Password update date over " + diff + " days will be refused logon *******");

                    if (diff >= pwd_expire_days) {
                        staffLogInfo.setReturnStr(PWD_EXPIRE);
                        staffLogInfo.setDiffDay(new Long(diff));
                        staffLogInfo.setPwdExpireDate(new Long(pwd_expire_days));
                        staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                        staffLogInfo.setUserName(staff.getUserName());
                        staffLogInfo.setPassWord(staff.getPassword());
                        return staffLogInfo;
                    }
                    else if (diff >= pwd_alert_days && diff < pwd_expire_days) {
                        staffLogInfo.setReturnStr(PWD_ALERT);
                        staffLogInfo.setDiffDay(new Long(diff));
                        staffLogInfo.setPwdExpireDate(new Long(pwd_expire_days));
                        staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                        staffLogInfo.setUserName(staff.getUserName());
                        staffLogInfo.setPassWord(staff.getPassword());
                        return staffLogInfo;
                    }
                }
            }
            /**记录登陆日志*/
            SystemLogDto systemLogDto = AddStaffOnlineLogOn(new Long(staff.
                getStaffId()), new Long( -1), /*"系统登陆"*/
                Messages.get(Messages.K_SYSTEM_LOGGING));
            if (staff.getLogonErrNumber() != null) {
                loginMng.updateLogonErrNumber(new Long(staff.getStaffId()),
                                              new Long( -1));
            }
            session.setAttribute("systemLogId", systemLogDto.getId());
            //sessionHt.put(httpServletRequest.getRemoteAddr(), session);
            logger.debug("session resovled :" +
                         httpServletRequest.getRemoteAddr());



        }
        catch (OAASException ex) {
            staffLogInfo.setReturnStr(ex.getMessage());
            return staffLogInfo;
        }
        catch (UOSException ex) {
            staffLogInfo.setReturnStr(ex.getMessage());
            return staffLogInfo;
        }
        staffLogInfo.setReturnStr(LOG_SUCCESS);
        return staffLogInfo;
    }

    /**
     * 验证用户名和密码（未国际化前）
     * @param username String 用户名
     * @param password String 密码
     * @throws OAASException 人员信息
     * @return Staff
     */
    public String login(String username, String password) throws UOSException {
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(24 * 60 * 60);
        try {
            /**验证人员信息*/
            LoginManager loginMng = new LoginManager();
            Staff staff = loginMng.verifyPassword(username, password);
            if (staff == null) {
                //return "用户名和密码不符!";
                return Messages.get(Messages.K_USERNAME_PASSWORD_NOT_MATCH);
            }
            /**查找人员所有职位*/
            Job[] jobs = loginMng.findJobsByStaff(staff.getStaffId());
            /**没有职位，非超级管理员*/
            if (jobs.length == 0 && staff.getStaffId() != 0) {
                //return "您没有任何职位!";
                return Messages.get(Messages.K_NOTING_ANY_JOB);
            }

			WorkOrderTranOrg[] workOrderTranOrgs = loginMng
					.findOrgsByStaffId(staff.getStaffId());

			// 保存和工号相关的组织信息。
			session.setAttribute("workOrderTranOrg", workOrderTranOrgs);

            /*保存人员信息*/
            session.setAttribute("staff", staff);
            /*保存职位信息*/
            session.setAttribute("jobs", jobs);
        }
        catch (OAASException ex) {
            return ex.getMessage();
        }
        return null;
    }

    /**
     * 验证用户名和密码,并保存语言码信息
     * @param username String 用户名
     * @param password String 密码
     * @param language String 语言代码
     * @throws UOSException
     * @return StaffLogInfo 人员和登陆有关信息
     */
    public StaffLogInfo login(String username, String password, String language) throws
        UOSException {
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(24 * 60 * 60);
        StaffLogInfo staffLogInfo = new StaffLogInfo();
        LoginManager loginMng = new LoginManager();

        try {
            ParameterDTO value=loginMng.findParameter("IS_ADD_LOGON_LOG");
            if(value==null || "".equals(value.getValue()) || "Y".equals(value.getValue())){
                /*首先判断当前帐户是否被锁定  liangli 2007.01.30*/
                Staff staffTemp = loginMng.findByUserName(username);
                if (staffTemp != null) {
                    if (isLocked(staffTemp.getLockedDate())) {
                        staffLogInfo.setReturnStr(STA_LOCKED);
                        return staffLogInfo;
                    }
                    else {
                        //如果锁定超过3分钟，则解锁  add by liangli 2007.01.30
                        if (staffTemp.getLockedDate() != null) {
                            loginMng.updateLockedDate(staffTemp.getStaffId(), false);
                        }
                    }
                }
            }


            /**验证人员信息*/
            Staff staff = loginMng.verifyPassword(username, password);
            if (staff == null) {
                /*密码错误*/
                staffLogInfo.setReturnStr(STA_PWD_WRONG);
                /**处理密码登陆错误*/
                StaffLogInfo logonErrResult = this.dealLogonError(username);
                if (logonErrResult != null) {
                    return logonErrResult;
                }
                return staffLogInfo;
            }
            /**查找人员所有职位*/
            Job[] jobs = loginMng.findJobsByStaff(staff.getStaffId());
            /**没有职位，非超级管理员*/
            if (jobs.length == 0 && staff.getStaffId() != 0) {
                /*没有职位*/
                staffLogInfo.setReturnStr(STA_NO_JOB);
                return staffLogInfo;
            }

			WorkOrderTranOrg[] workOrderTranOrgs = loginMng
					.findOrgsByStaffId(staff.getStaffId());

			// 保存和工号相关的组织信息。
			session.setAttribute("workOrderTranOrg", workOrderTranOrgs);

            /*保存人员信息*/
            session.setAttribute("staff", staff);
            /*保存职位信息*/
            session.setAttribute("jobs", jobs);
            /*保存语言代码*/
            session.setAttribute("language_code", language);
            /**EOMS用*/
            session.setAttribute("staffDutyId", new Long(0));
            session.setAttribute("staff_daylog_id", new Long(0));
            if(value==null || "".equals(value.getValue()) || "Y".equals(value.getValue())){
                /**允许登陆次数*/
                int logonNumber = staff.getLogonNumber();
                /**数据库中已有的记录*/
                StaffOnLineInfoDto[] logonMemberArr = loginMng.
                    queryLogonStaffCount(new Long(staff.getStaffId()));
                //允许次数
                logger.info("logonNumber=" + logonNumber);
                //已经存在次数
                logger.info("logonNumberExist=" + logonMemberArr.length);
                /**判断是否超出允许值*/
                if (logonNumber <= logonMemberArr.length) {
                    staffLogInfo.setReturnStr(LOG_ON_NUMBER_EXCEED);
                    staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                    staffLogInfo.setLogonNumber(String.valueOf(logonMemberArr.
                        length));
                    staffLogInfo.setPermitLogonNumber(String.valueOf(
                        logonNumber));
                    /**必须强制退出的用户个数*/
                    int mustQuitNumber = logonMemberArr.length - logonNumber +
                        1;
                    staffLogInfo.setMustQuitNumber(String.valueOf(
                        mustQuitNumber));
                    String[] ipArr = new String[logonMemberArr.length];
                    for (int i = 0; i < logonMemberArr.length; i++) {
                        ipArr[i] = logonMemberArr[i].getIp();
                    }
                    staffLogInfo.setLogonIpAdd(ipArr);
                    return staffLogInfo;
                }
                /**判断是否考虑密码过期因素*/
                if (loginMng.findParameter(IF_PWD_CHANGE_VALIDATE) != null &&
                    loginMng.findParameter(IF_PWD_CHANGE_VALIDATE).getValue().
                    equals(PWD_CHANGE_VALIDATE)) {
                    /**上次密码修改时间*/
                    Date pwdModDate = staff.getPwdModDate();
                    Date now = new Date();
                    if (pwdModDate != null &&
                        loginMng.findParameter(PWD_EXPIRE_DATE) != null &&
                        loginMng.findParameter(PWD_ALERT_DATE) != null) {
                        long diff = this.calculateDayInteval(pwdModDate, now);
                        //密码必须修改的天数
                        int pwd_expire_days = Long.valueOf(loginMng.
                            findParameter(
                                PWD_EXPIRE_DATE).getValue()).intValue();
                        //密码建议修改的天数
                        int pwd_alert_days = Long.valueOf(loginMng.
                            findParameter(
                                PWD_ALERT_DATE).getValue()).intValue();
                        //logger.info("*********您上次密码修改距离今天已经有" + diff + "天了*******");
                        //logger.info("*********密码修改时间超过" + pwd_alert_days + "天就会弹出警告*******");
                        //logger.info("*********密码修改时间超过" + pwd_expire_days + "天就会拒绝登陆*******");
                        logger.info("********* Password has been " + diff +
                                    " days since the last revision *******");
                        logger.info("********* Password update date over " +
                                    diff +
                                    " days will be activated warning *******");
                        logger.info("********* Password update date over " +
                                    diff +
                                    " days will be refused logon *******");

                        if (diff >= pwd_expire_days) {
                            staffLogInfo.setReturnStr(PWD_EXPIRE);
                            staffLogInfo.setDiffDay(new Long(diff));
                            staffLogInfo.setPwdExpireDate(new Long(
                                pwd_expire_days));
                            staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                            staffLogInfo.setUserName(staff.getUserName());
                            staffLogInfo.setPassWord(staff.getPassword());
                            return staffLogInfo;
                        }
                        else if (diff >= pwd_alert_days &&
                                 diff < pwd_expire_days) {
                            staffLogInfo.setReturnStr(PWD_ALERT);
                            staffLogInfo.setDiffDay(new Long(diff));
                            staffLogInfo.setPwdExpireDate(new Long(
                                pwd_expire_days));
                            staffLogInfo.setStaffId(new Long(staff.getStaffId()));
                            staffLogInfo.setUserName(staff.getUserName());
                            staffLogInfo.setPassWord(staff.getPassword());
                            return staffLogInfo;
                        }
                    }
                }
                /**记录登陆日志*/
                SystemLogDto systemLogDto = AddStaffOnlineLogOn(new Long(staff.
                    getStaffId()), new Long( -1), /*"系统登陆"*/
                    Messages.get(Messages.K_SYSTEM_LOGGING));
                if (staff.getLogonErrNumber() != null) {
                    loginMng.updateLogonErrNumber(new Long(staff.getStaffId()),
                                                  new Long( -1));
                }
                session.setAttribute("systemLogId", systemLogDto.getId());
                //sessionHt.put(httpServletRequest.getRemoteAddr(), session);
            }
            logger.debug("session resovled :" +
                         httpServletRequest.getRemoteAddr());



        }
        catch (OAASException ex) {
            staffLogInfo.setReturnStr(ex.getMessage());
            return staffLogInfo;
        }
        catch (UOSException ex) {
            staffLogInfo.setReturnStr(ex.getMessage());
            return staffLogInfo;
        }
        staffLogInfo.setReturnStr(LOG_SUCCESS);
        return staffLogInfo;
    }

    /**
     * 强制进入的记录日志信息
     * @param username String
     * @param password String
     * @param language String
     * @throws UOSException
     */
    public void addLogforced(String username, String password) throws
        UOSException, SQLException {
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(24 * 60 * 60);
        LoginManager loginMng = new LoginManager();
        Staff staff = loginMng.verifyPassword(username, password);

        if (staff != null) {
            SystemLogDto systemLogDto = AddStaffOnlineLogOn(new Long(staff.
                getStaffId()), new Long( -1), /*"系统登陆"*/
                Messages.get(Messages.K_SYSTEM_LOGGING)
                );
            if (staff.getLogonErrNumber() != null) {
                loginMng.updateLogonErrNumber(new Long(staff.getStaffId()),
                                              new Long( -1));
            }
            session.setAttribute("systemLogId", systemLogDto.getId());
            //sessionHt.put(httpServletRequest.getRemoteAddr(), session);
            logger.debug("session resovled :" +
                         httpServletRequest.getRemoteAddr());
        }
    }

    /**
     * 退出系统
     * @throws UOSException
     */
    public void logoff() throws UOSException {
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            Long systemLogId = (Long) session.getAttribute("systemLogId");
            //增加系统退出日志
            if (systemLogId != null) {
                AddStaffOnlineLogOut(systemLogId);
            }
            logger.debug("destory session :" + httpServletRequest.getRemoteAddr());
            sessionHt.remove(httpServletRequest.getRemoteAddr());
            session.invalidate();
            session = null;
        }
    }

    /**
     * 查询职位
     * @throws OAASException
     * @return Job[] 职位列表
     */
    public Job[] findJobs() throws UOSException {
        HttpSession session = httpServletRequest.getSession();
        Job[] jobs = (Job[]) session.getAttribute("jobs");
        ArrayList al = new ArrayList();
        if (jobs != null) {
            for (int i = 0; i < jobs.length; i++) {
                if (jobs[i].getJobName().startsWith("JOB_")) {
                    continue;
                }
                al.add(jobs[i]);
            }
        }
        return (Job[]) al.toArray(new Job[] {});

    }

    /**
     * 查询所有的权限
     * @param jobId int 职位编号，如果是-1，则表示没有职位，要以默认的职位登录
     * @throws OAASException
     * @return boolean 是否有权限
     */
    public String findPrivs(int jobId) throws UOSException {
        LoginManager loginMng = new LoginManager();
        HttpSession session = httpServletRequest.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        Job[] jobs = (Job[]) session.getAttribute("jobs");
        if (jobs == null) {
            // return "没有任何权限!";
            return Messages.get(Messages.K_NOTHING_ANY_PRIVILEGE);
        }
        String[] privCodes = null;
        Job job = null;
        Job specialJob = null;
        Organization org = new Organization();
        OrganizationManager orgMng = null;
        Organization[] orgs = null;
        Job[] normalJobs = null;



        //循环找出职位和特殊职位
        for (int i = 0; i < jobs.length; i++) {
            if (jobs[i].getJobId() == jobId) {
                job = jobs[i];
            }
            if (jobs[i].getJobName().startsWith("JOB_")) {
                specialJob = jobs[i];
            }
        }

        int specialJobId = -1 ;
        if(specialJob!=null){
            specialJobId =  specialJob.getJobId();
        }

	  logger.debug("Prepare to get all buttons " + jobId);
        java.util.List allButtons = loginMng.listAllBtnByJobId(jobId,specialJobId);
        logger.debug("Total buttons is " + allButtons.size());
        session.setAttribute("funcButton", allButtons);

        try {
            //超级管理员
            if (specialJob == null && job == null) {
                if (staff != null && staff.getStaffId() == 0) {
                    privCodes = loginMng.findLoginPrivs();
                    job = new Job();
                    job.setJobId( -1);
                    // job.setJobName("超级管理员");
                    job.setJobName(Messages.get(Messages.K_SUPER_ADMINISTRATOR));
                    org.setOrgId(0);
                    org.setOrgName("无");
                    jobs = new Job[1];
                    jobs[0] = job;
                    session.setAttribute("jobs", jobs);
                }
                else {
                    privCodes = null;
                }
            }
            //有普通职位且无特殊职位
            else if (job != null && specialJob == null) {
                privCodes = loginMng.findPrivsByJob(jobId);
                org.setOrgId(job.getOrgId());
                org.setOrgName(job.getOrgPathName());
            }
            //仅有缺省职位
            else if (job == null && specialJob != null) {
                privCodes = loginMng.findPrivsByJob(specialJob.getJobId());
                job = new Job();
                job.setJobId( -1);
                //job.setJobName("无(以个人身份登录)");
                job.setJobName(Messages.get(Messages.K_NOT_JOB));
                org.setOrgId(specialJob.getOrgId());
                org.setOrgName(specialJob.getOrgPathName());
                jobs = new Job[1];
                jobs[0] = job;
                session.setAttribute("jobs", jobs);
            }
            //既有普通职位也有缺省职位
            else {
                privCodes = loginMng.findPrivsByJobs(jobId,
                    specialJob.getJobId());
                org.setOrgId(job.getOrgId());
                org.setOrgName(job.getOrgPathName());
            }
            //无任何权限
            if (privCodes == null || privCodes.length == 0) {
                // return "没有任何权限!";
                return Messages.get(Messages.K_NOTHING_ANY_PRIVILEGE);
            }
            //查询出区域对象
            Area area = null;
            if (org.getOrgId() != 0) {
                orgMng = new OrganizationManager();
                org = orgMng.findByKey(org.getOrgId());
                if (org.getAreaId() != 0) {
                    AreaManager areaMng = new AreaManager();
                    area = areaMng.findByKey(org.getAreaId());
                }
            }
            else {
                area = new Area();
                area.setAreaId(0);
                area.setAreaName("");

            }
            StringBuffer sb = new StringBuffer(privCodes[0]);
            for (int i = 1; i < privCodes.length; i++) {
                sb.append("," + privCodes[i]);
            }
            //把非隐藏职位和对应的组织放入session中
            Collection orgCol = new ArrayList();
            Collection normalJobCol = new ArrayList();
            for (int i = 0; i < jobs.length; i++) {
                if (!jobs[i].getJobName().startsWith("JOB_")) {
                    Organization _org = new Organization();
                    _org.setOrgId(jobs[i].getOrgId());
                    _org.setOrgName(jobs[i].getOrgName());
                    orgCol.add(_org);
                    normalJobCol.add(jobs[i]);
                }
            }
            orgs = (Organization[]) orgCol.toArray(new Organization[] {});
            normalJobs = (Job[]) normalJobCol.toArray(new Job[] {});

			ParameterDTO paramterDto=loginMng.findParameter("IS_EOMS");
            if(paramterDto != null && "Y".equals(paramterDto.getValue())){
                Lan lan = new Lan();
                //查找LAN信息
                //lan = orgMng.findLan(org.getOrgId());
                //根据EOMS需求，增加lan信息
                session.setAttribute("lan", lan);
            }
            //把职位、组织和权限信息写入session
            session.setAttribute("job", job);
            session.setAttribute("specialJob", specialJob);
            session.setAttribute("org", org);
            session.setAttribute("area", area);
            session.setAttribute("privilege", sb.toString());
            session.setAttribute("orgs", orgs);
            session.removeAttribute("jobs");
            session.setAttribute("jobs", normalJobs);
            return null;
        }
        catch (OAASException ex) {
            return ex.getMessage();
        }
    }

    /**
     * 从sessio 中取出权限对象
     * @return String[] 权限编号数组
     */
    public String[] getPrivs() {
        HttpSession session = httpServletRequest.getSession();
        return (String[]) session.getAttribute("privilege");
    }

    /**
     * 清掉数据库中相同ip地址的在线记录
     * @throws UOSException
     */
    public void clearBadOnlineInfo() throws
        UOSException {
        LoginManager loginMng = new LoginManager();
        String ip = httpServletRequest.getRemoteAddr();
        if (ip == null) {
            throw new UOSException(new CommonError(CommonError.
                COMMON_ERROR),
                                   "ip Address is null");
        }
        //清掉数据库中相同ip地址的登陆在线记录
        loginMng.setAbnormalByIp(ip);
    }

    /**
     * 清掉数据库中相同ip地址的在线记录
     * @throws UOSException
     */
    public void setAbnormalByIps(String[] ips) throws
        UOSException {
        LoginManager loginMng = new LoginManager();
        //强制用户退出
        for (int i = 0; i < ips.length; i++) {
            HttpSession session = getSession(ips[i]);
            sessionHt.remove(ips[i]);
            if (session != null) {
                logger.debug("destory session forced :" +
                             "and ip=" + ips[i]);
                session.invalidate();
                session = null;
            }
        }

        //清掉数据库中相同ip地址的登陆在线记录
        loginMng.setAbnormalByIps(ips);

    }

    /**
     * 增加人员登陆信息
     * @param staffId Long
     * @param moduleId Long
     * @param logDetail String
     * @throws PBException
     */
    public SystemLogDto AddStaffOnlineLogOn(Long staffId, Long moduleId,
                                            String logDetail) throws
        UOSException {
        try {
            LoginManager loginMng = new LoginManager();
            StaffOnLineInfoDto dto = new StaffOnLineInfoDto();
            dto.setLogType(StaffOnLineInfoDto.LOG_TYPE_LOGINFO);
            java.util.Date now = new java.util.Date();
            dto.setCreateDate(now);
            dto.setStaffId(staffId);
            dto.setModuleId(moduleId);
            dto.setLogDetail(logDetail);
            dto.setOnLineState(StaffOnLineInfoDto.LOG_STATE_LOGON);
            dto.setIp(httpServletRequest.getRemoteAddr());
            dto.setMachineName("");
            dto.setLogOnDate(now);
            return loginMng.addSystemLog(dto);
        }
        catch (UOSException ex) {
            throw ex;
        }
    }

    /**
     * 增加人员退出信息
     * @param staffId Long
     * @param moduleId Long
     * @param logDetail String
     * @throws PBException
     */
    public void AddStaffOnlineLogOut(Long logInfoId) throws UOSException {
        LoginManager loginMng = new LoginManager();
        Date now = new Date();
        try {
            loginMng.updateStaffOnLineInfo(logInfoId, now);
        }
        catch (UOSException ex) {
            throw ex;
        }
    }

    /**
     * 计算输入日期间相差天数
     * @param input YYYY-MM-DD
     */
    private long calculateDayInteval(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return diff / (1000 * 60 * 60 * 24);

    }

    /**
     * 处理用户登陆失败的情况
     * @param username String
     * @throws UOSException
     * @return String
     */
    private StaffLogInfo dealLogonError(String username) throws UOSException {
        LoginManager loginMng = new LoginManager();
        try {
            if (loginMng.findParameter(LOGON_ERROR_ALLOW_TIME) != null) {
                int logonErrAllowTime = Long.valueOf(loginMng.findParameter(LOGON_ERROR_ALLOW_TIME).getValue()).intValue();
                Staff staff = loginMng.findByUserName(username);
                if (staff != null && staff.getStaffId() != 0) {
                    Long logonErrNumber = staff.getLogonErrNumber();
                    if (logonErrNumber != null) {
                        if (logonErrNumber.intValue() >= logonErrAllowTime) {
                            StaffLogInfo staffLogInfo = new StaffLogInfo();
                            staffLogInfo.setLogonErrAllowTimes(new Long(logonErrAllowTime));
                            staffLogInfo.setReturnStr(LOG_ON_ERR_NUMBER_EXCEED);
                            //如果超出错误登录次数，就锁定该用户帐号 add by liangli 2007.01.30
                            loginMng.updateLockedDate(staff.getStaffId(),true);
                            return staffLogInfo;
                        }
                        else {
                            loginMng.updateLogonErrNumber(new Long(staff.
                                getStaffId()), logonErrNumber);
                        }
                    }
                    else {
                        loginMng.updateLogonErrNumber(new Long(staff.getStaffId()), null);
                    }
                }
            }
        }
        catch (UOSException ex) {
            throw ex;
        }
        return null;
    }


    public boolean isLocked(Date lockedDate){
        boolean ret = false;
        if(lockedDate==null) return ret;

        long now = System.currentTimeMillis(); //当前系统时间(毫秒级)
        long diff = now - lockedDate.getTime();
        diff = diff / (1000 * 60);
        return (diff<3);
    }

    //**/
    public String  getAppParam(String key){
      String value = ResourceFileUtil.getPropertie("app.properties",key);
      return value;
    }

    /**
     * 职位所在区域校验：安徽需求
          省级职位可以访问任何本地网系统，本地网职位只能访问本地系统
     * @param jobId String
     * @return boolean
     * liangli 2007.03.05
     */
    public boolean areaCheck(String jobId) throws UOSException {
        boolean flag = true;
        try {
            String provAreaId = getAppParam(PROVINCIAL_AREA_ID);
            String natiAreaId = getAppParam(NATIVE_AREA_ID);
            //未做配置地域限制则直接返回true
            if(provAreaId=="" || natiAreaId==""){
                return true;
            }
            OrganizationManager om = new OrganizationManager();
            Organization orgObj = om.findByJob(Integer.parseInt(jobId));
            String areaId = String.valueOf(orgObj.getAreaId());

            //当前职位是省级职位或为当前本地网职位，返回true
            if(areaId.equals(provAreaId) || isNativeArea(natiAreaId,areaId)){
                flag = true;
            }
            else{
                flag = false;
            }
        }
        catch (UOSException ex) {
            logger.error("area validate exception befor logon ："+ex);
            throw ex;
        }
        return flag;
    }

    private boolean isNativeArea(String natiAreaId,String inAreaId){
        boolean flag = false;

        //解析本地地域集合id
        String[] arr = natiAreaId.split(",");
        String temp = "";
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            temp = arr[i];
            if (temp != null && temp.length() > 0) {
                if (inAreaId.equals(temp)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }


	public LogonButton[] getButtonsByTacheId(String tacheId)
			throws UOSException {
		java.util.List rlist = new java.util.LinkedList();
		HttpSession session = httpServletRequest.getSession();
		java.util.List btnList = (java.util.List) session
				.getAttribute("funcButton");

		logger.debug("Fetch buttons's tacheId is " + tacheId);
		if (tacheId == null) {
			throw new SystemException("TacheId can not be null!");
		}

		if (btnList != null) {
			int size = btnList.size();
			LogonButton curRow = null;
			for (int i = 0; i < size; i++) {
				curRow = (LogonButton) btnList.get(i);
				if (tacheId.equals(curRow.getTacheId())
						&& "0".equals(curRow.getIsBatch())) {
					rlist.add(curRow);
				}
			}
		}
		logger.debug("Fetch buttons from session successful" + rlist.size());
		return (LogonButton[]) rlist.toArray(new LogonButton[] {});
	}

	public LogonButton[] getButtonsByTacheIdWithOrder(String tacheId)
			throws UOSException {
		java.util.List rlist = new java.util.LinkedList();
		HttpSession session = httpServletRequest.getSession();
		java.util.List btnList = (java.util.List) session
				.getAttribute("funcButton");

		logger.debug("Fetch buttons's tacheId is " + tacheId);
		if (tacheId == null) {
			throw new SystemException("TacheId can not be null!");
		}

		if (btnList != null) {
			int size = btnList.size();
			LogonButton curRow = null;
			for (int i = 0; i < size; i++) {
				curRow = (LogonButton) btnList.get(i);
				if (tacheId.equals(curRow.getTacheId())) {
					rlist.add(curRow);
				}
			}
		}
		logger.debug("Fetch buttons from session successful" + rlist.size());
		return (LogonButton[]) rlist.toArray(new LogonButton[] {});
	}
	
    

}
