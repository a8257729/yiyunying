package com.ztesoft.mobile.v2.shiro;

import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.service.auth.MobileAuthService;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.util.Base64Utils;
import com.ztesoft.mobile.v2.util.CryptUtils;
import com.ztesoft.mobile.v2.util.Encodes;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.Serializable;

public class ShiroAuthenRealm extends AuthorizingRealm {

    private static final Logger logger = Logger.getLogger(ShiroAuthenRealm.class);

	private MobileAuthService authService;

    public MobileAuthService getAuthService() {
        return authService;
    }

    @Autowired(required = false)
    public void setAuthService(MobileAuthService authService) {
        this.authService = authService;
    }

    /**
	 * 认证回调函数,登录时调用方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        if(logger.isDebugEnabled()) {
            logger.debug("调用ShiroAuthenRealm的doGetAuthenticationInfo方法");
        }

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        StaffInfo staff = null;
        boolean flag = false;
        try {
            staff = this.getAuthService().getStaffByUsername(token.getUsername());
            if (staff != null) {

                byte[] salt = Base64Utils.decode(staff.getSalt());

                return new SimpleAuthenticationInfo(new ShiroUser(staff.getUsername(), staff.getStaffName()), staff.getPassword(),
                        ByteSource.Util.bytes(salt), getName());

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DisabledAccountException();
        }
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(logger.isDebugEnabled()) {
            logger.debug("调用ShiroAuthenRealm的doGetAuthorizationInfo方法");
        }

		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        StaffInfo user = null;
        try {
            user = this.getAuthService().getStaffByUsername(shiroUser.username);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
/*		for (Role role : user.getRoleList()) {
			
		}*/
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(MobileAuthService.HASH_ALGORITHM);
		matcher.setHashIterations(MobileAuthService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

    /** * 更新用户授权信息缓存. */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principal, getName());

        clearCachedAuthorizationInfo(principals);
    }

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String username;
		public String staffName;

		public ShiroUser(String username, String staffName) {
			this.username = username;
			this.staffName = staffName;
		}

		public String getStaffName() {
			return staffName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return username;
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this, "username");
		}

		/**
		 * 重载equals,只比较loginName
		 */
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj, "username");
		}
	}

    private boolean validatePasswrod(String passwd1, String passwd2) {
        boolean flag = false;
        byte[] bytes = passwd1.getBytes();
        String mPasswd1 = "{SHA}" + new String(Base64It.encode(bytes));
        if (passwd1.equals(passwd2) || mPasswd1.equals(passwd2)) {
            flag = true;
        }
        return flag;
    }



}
