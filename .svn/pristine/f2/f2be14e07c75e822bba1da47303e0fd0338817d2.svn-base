package com.ztesoft.mobile.v2.service.auth;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.util.Base64Utils;
import com.ztesoft.mobile.v2.util.Digests;
import com.ztesoft.mobile.v2.util.Encodes;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("authService")
public class MobileAuthServiceImpl implements MobileAuthService {

    public static final Logger logger = Logger.getLogger(MobileAuthServiceImpl.class);

    private MobileCommonDAO getMobileCommonDAO() {
        String daoName = MobileCommonDAOImpl.class.getName();
        return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(StaffInfo staff) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        staff.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(staff.getPassword().getBytes(), salt, HASH_INTERATIONS);
        staff.setPassword(Encodes.encodeHex(hashPassword));
    }

    public StaffInfo getStaffByUsername(String username) throws Exception {
        Map<String, Object> staffMap = getMobileCommonDAO().getStaffByUsername(username);

        if(null == staffMap || 0 == staffMap.size()) return null;

        StaffInfo staff = new StaffInfo();

        Long staffId = MapUtils.getLong(staffMap, StaffInfo.STAFF_ID_NODE);
        String staffName = MapUtils.getString(staffMap, StaffInfo.STAFF_NAME_NODE);
        String tUsername = MapUtils.getString(staffMap, StaffInfo.USERNAME_NODE);
        String tPassword =   MapUtils.getString(staffMap, StaffInfo.PASSWORD_NODE);
        String email = MapUtils.getString(staffMap, StaffInfo.EMAIL_NODE);
        String imsi =  MapUtils.getString(staffMap, StaffInfo.IMSI_NODE);
        String mobile = MapUtils.getString(staffMap, StaffInfo.MOBILE_NODE);
        String salt =  Base64Utils.encodeObject(tUsername + staffId);

        staff.setStaffId(staffId);
        staff.setStaffName(staffName);
        staff.setUsername(tUsername);
        staff.setPassword(tPassword);
        staff.setEmail(email);
        staff.setImsi(imsi);
        staff.setMobile(mobile);
        staff.setSalt(salt);

        return staff;
    }


}
