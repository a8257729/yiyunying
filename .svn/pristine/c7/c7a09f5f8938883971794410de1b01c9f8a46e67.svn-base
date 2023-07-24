package com.ztesoft.eoms.exintf.util;

import java.net.InetAddress;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import com.zterc.uos.UOSException;
import com.zterc.uos.client.ParameterManager;
import com.zterc.uos.oaas.service.jobmanager.JobManager;
import com.zterc.uos.oaas.service.staffmanager.StaffManager;
import com.zterc.uos.oaas.vo.Job;
import com.zterc.uos.oaas.vo.Staff;
import com.zterc.uos.service.parameter.ParameterRepository;
import com.ztesoft.eoms.common.oaas.OaasOperImpl;
import com.ztesoft.eoms.exintf.util.dao.UosConfigDAO;
import com.ztesoft.mobile.common.helper.NamingHelper;

/**
 * 运维系统业务接口用到的一些基础方法
 * 1、获取事务实例
 * 2、获取操作用户
 * 
 * @author Liudong 2008-05
 * 
 */
public class InterfaceHelper {
	private static final int TRANSACTION_TIMEOUT = 45; //事务超时时间
	private OaasOperImpl oaasOperImpl = null;
    private static InterfaceHelper _InterfaceHelper = new InterfaceHelper();
    
    public synchronized static InterfaceHelper getInstance(){
    	if(_InterfaceHelper==null){
    		_InterfaceHelper = new InterfaceHelper();;
    	}
    	return _InterfaceHelper;
    }
	/**
	 * 获取事务实例
	 * 
	 * @return UserTransaction
	 * @throws Exception
	 */
	public UserTransaction getTransaction() throws NamingException {
		Context context = NamingHelper.singleton().getInitialContext();
		UserTransaction userTransaction = null;
		try {
			userTransaction = (UserTransaction) context
					.lookup("java:comp/UserTransaction");
		} catch (NamingException ex) {
			System.out.println("getTransaction(java:comp/UserTransaction)错误:"
					+ ex.getMessage());
			try {
				userTransaction = (UserTransaction) context
						.lookup("jta/usertransaction");
			} catch (NamingException ex2) {
				System.out.println("getTransaction(jta/usertransaction)错误:"
						+ ex2.getMessage());
				throw ex2;
			}
		}
		return userTransaction;
	}
	
    /**
     * 启动事务
     * @param userTransaction UserTransaction
     * @throws Exception
     */
	public void BeginTrans(UserTransaction userTransaction) throws Exception {
        if (userTransaction != null) {
            userTransaction.setTransactionTimeout(TRANSACTION_TIMEOUT); //设置超时时间
            userTransaction.begin();
        } else {
            System.out.println("userTransaction 为Null!");
        }
    }

    /**
     * 提交事务
     * @param userTransaction UserTransaction
     * @throws Exception
     */
	public void CommitTrans(UserTransaction userTransaction) throws Exception {
        if (userTransaction != null) {
            userTransaction.commit();
        } else {
            System.out.println("userTransaction 为Null!");
        }
    }

    /**
     * 回滚事务
     * @param userTransaction UserTransaction
     * @throws Exception
     */
	public void RollbackTrans(UserTransaction userTransaction) throws
            Exception {
        if (userTransaction != null) {
            userTransaction.rollback();
        } else {
            System.out.println("userTransaction 为Null!");
        }
    }
    /**
     * 获取默认操作用户
     * @return
     */
	public  Staff getStaff(){
		return getStaff(null,null,null);
	}
	
	/**
	 * 获取操作用户
	 * @param userName
	 *            用户登录名
	 * @param key
	 *            用户ID
	 * @param orgId
	 *            监控部门
	 * @return Staff
	 */
	public Staff getStaff(String userName, Long key, Long orgId) {
		try { // 根据ID查询
			ParameterManager parameterManager = new ParameterManager();
			StaffManager staffMng = new StaffManager();
			JobManager jobMng = new JobManager();
			Staff staff = null;
			// 根据用户ID查询用户
			if (key != null)
				staff = staffMng.findByKey(key.intValue());
			// 根据UserName查询用户
			if (staff == null) {
				staff = staffMng.findByUserName(userName);
			}
			// 获取系统默认用户
			if (staff == null) {
				String paramValue = parameterManager.findParameter(
						"com.ztesoft.eoms.fault.CreateStaffId").getValue();
				staff = staffMng.findByKey(Integer.parseInt(paramValue));
			}
			// 设置角色
			if (staff != null) {
				Job[] jobs = jobMng.findByStaff(staff.getStaffId(), false);
				if (jobs != null && jobs.length != 0) {
					staff.setJobId(jobs[0].getJobId());
					staff.setJobName(jobs[0].getJobName());
				}
			}
			// 设置用户所在组织即监控部门
			if (orgId != null) {
				String orgName = getOaasOperImpl().getOrgNameByOrgId(
						orgId.longValue());
				staff.setOrgId(orgId.intValue());
				staff.setOrgName(orgName);
			}
			return staff;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	/**
	 * 判断本机是否是群集所指向的机器
	 * @return 如是则返回 true 否则 返回 false 
	 */
    public  boolean isActiveServer(){
    	try {
			InetAddress inet = InetAddress.getLocalHost();
			String paramValue = UosConfigDAO.getInstance().getValue("ActiveServerIP");
			//String paramValue = getValueFromDB("ActiveServerIP");
			
			if(inet.getHostAddress().equalsIgnoreCase(paramValue.trim())){
				//System.out.println("【【【【【【【当前为群集所指IP】】】】】】】】");
				return true;
			}else{
				//System.out.println("【【【【【【【不是群集所指IP】】】】】】】】");
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return true;//异常返回true 让定时作业继续运行
		}
    }
    
	private synchronized OaasOperImpl getOaasOperImpl() {
		if (oaasOperImpl == null) {
			oaasOperImpl = new OaasOperImpl();
		}
		return oaasOperImpl;
	}
	
	public String getValueFromDB(String name) throws UOSException{
		ParameterManager parameterManager = new ParameterManager(false);
		String paramValue = parameterManager.findParameter(name).getValue();
		ParameterRepository.getInstance().removeParameter(name);//只是在缓存中把该值清掉
		return paramValue;
	}
}
