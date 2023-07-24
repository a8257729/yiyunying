package com.ztesoft.mobile.v2.controller.zwt;


import com.google.gson.Gson;
import com.zterc.uos.util.data.Base64;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/zwt/api/")
public class ZwtApiController {

    public static final String GET_SESSION_INFO = "http://192.168.101.175:11009/bdAccount/session/info";

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }




    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }



    public static final String sessionInfoSql= "SELECT PASSWORD FROM uos_staff WHERE userName = ? and state = '1'";

    public static final String sessionInfoWhere = "userName:userName";

    @RequestMapping("/getSessionInfo")
    public Object getSessionInfo(@RequestBody Map<String, Object> data, ModelMap model,
                                 HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> param = (Map<String, Object>) data.get("param");
        String session = (String) param.get("session");

        RestTemplate restTemplate = new RestTemplate();
        Map forObject = restTemplate.getForObject(GET_SESSION_INFO+"?sessionId="+session,  Map.class);

        Map<String,Object> map = forObject;
        Map body = (Map)map.get("UNI_BSS_BODY");
        if(body==null){
            return null;
        }
        Map get_session_info_by_id_rsp = (Map)body.get("GET_SESSION_INFO_BY_ID_RSP");
        if(get_session_info_by_id_rsp==null){
            return null;
        }
        Map rsp = (Map)get_session_info_by_id_rsp.get("RSP");
        if(rsp==null){
            return null;
        }
        Map respData = (Map)rsp.get("RESP_DATA");
        if(respData==null){
            return null;
        }

        String phoneNumber = (String)respData.get("LOGIN_PHONE_NUMBER");
        if(phoneNumber==null){
            return null;
        }
        Map map1 = new HashMap();
        map1.put("userName",phoneNumber);
        Result result = getCommonService().commonQueryObjectBySql(sessionInfoSql, map1, sessionInfoWhere);
        Map map2 = (Map)result.getResultData().get("data_info");
        String password = (String)map2.get("PASSWORD");
        if(password==null){
          return null;
        }
        password = pwdDecode(password);
        Map map3 = new HashMap();
        map3.put("pwd",password);
        map3.put("user",phoneNumber);
        return map3;
    }




    /**
     * 公客密码 解密
     * @param pwd 加密密码
     * @return 明文密码
     */
    public static String pwdDecode(String pwd){
        if(pwd==null){
            return null;
        }
        if(pwd.length()<=5){
            return null;
        }
        String s = pwd.substring(5);
        char[] Str2 = new char[s.length()];
        s.getChars(0, s.length(), Str2, 0);
        byte[] decode = Base64.decode(Str2);
        String s1 = new String(decode);
        return s1;
    }




}
