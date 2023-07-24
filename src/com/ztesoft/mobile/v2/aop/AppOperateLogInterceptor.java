package com.ztesoft.mobile.v2.aop;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * app操作日志记录拦截器
 */
public class AppOperateLogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(AppOperateLogInterceptor.class);


    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        String params = "";
        String requestUri = "";
        try {
            params = getJsonParam(httpServletRequest);
            logger.debug("<<<请求参数为：>>>:" + params);
            requestUri = httpServletRequest.getRequestURI();
            if (StringUtils.isNotBlank(requestUri) && requestUri.indexOf("//") != -1) {
                requestUri = requestUri.replace("//", "/");
            }
            logger.debug("requestUri:" + requestUri);
            String method = "";
            String staffId = "";
            if (StringUtils.isNotBlank(params)) {
                Map paramMap = JSONObject.fromObject(params);
//        String userName = (String) paramMap.get("userName");
//        logger.info("userName:"+ userName);
                if (paramMap != null) {
                    if (paramMap.get("method") != null) {
                        method = (String) paramMap.get("method");
                    }
                    if (paramMap.get("staffId") != null) {
                        staffId = (String) paramMap.get("staffId");
                    }
                    logger.debug("<<<请求method为：>>>:" + method);
                    logger.debug("<<<请求staffId为：>>>:" + staffId);
                }
            }
            //直接通过前台特别请求后台保存日志的请求，不需要在拦截器中做保存日志记录操作
            if ("/MOBILE/api/client/common/commonQueryObjectBySql".equals(requestUri)
                    && "saveOperLog".equals(method)) {
                return;
            }

            //记录操作日志
            StringBuilder sb = new StringBuilder();
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("url", requestUri);
            param.put("operMethod", method);
            param.put("staffId", staffId);
            param.put("operResult", "success");
            param.put("url1", requestUri);
            param.put("operMethod1", method);
            sb.append("insert into inf_app_operate_log(url,oper_method,oper_staff_id,oper_result,function_id) ");
            sb.append("values(?,?,?,?,(select id from inf_app_function_relation where url = ? and             ");
            sb.append(" (method = ? or method is null)))                                                      ");
     //       Result result = getCommonService().commonInsertObjectBySql(sb.toString(), param);
     //       logger.debug("日志保存结果：" + result.getResultMsg());
        } catch (Exception exception) {
            //日志记录失败，不能影响业务正常运行
            logger.error("日志记录出错:" + exception.getMessage());
            logger.error("rquestUri:" + requestUri);
            logger.error("rquestParams:" + params);
        }
    }


    private String getJsonParam(HttpServletRequest request) {
        String params = "";
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer("");
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        params = sb.toString();
        return params;
    }
}
