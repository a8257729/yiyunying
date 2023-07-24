package com.ztesoft.mobile.v2.controller.newFunction;

import com.ibm.bpe.util.Base64;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * app我的页面中功能处理类
 *
 * @author Dell
 */
@Controller("myController")
public class MyController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(MyController.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }


    /**
     * 修改密码
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/myController/changePwd"})
    public @ResponseBody
    Result changePwd(@RequestBody Map<String, Object> data, ModelMap model,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        String modifyPwd = (String) data.get("modifyPwd");
        BigDecimal staffId = new BigDecimal((String)data.get("staffId"));
        logger.info("修改用户密码，staffId:" + staffId);
        Result result = null;

        //将修改后的密码加密保存
		StringBuilder sb = new StringBuilder();
		sb.append("{SHA}").append(Base64.encode(modifyPwd.getBytes()));
		String encryptPwd = sb.toString();
		logger.info("修改后的密码为：" + encryptPwd);

        String[] outParamArray = new String[1];
        String sqlParam = "proc_change_staff_pwd(?,?,?)";
        logger.info("调用的存储过程为：" + sqlParam);
        List<Object> list = new ArrayList<Object>();
        outParamArray[0] = "result";
        list.add(encryptPwd);
        list.add(staffId);
        list.add(BigDecimal.valueOf(0));

        Result submitResult = getCommonService().commonQueryObjectByProcedure(sqlParam, list, 2,
                outParamArray);
        Map pointMap = (Map<String, Object>) submitResult.getResultData().get("data_info");
        logger.info("返回结果:" + submitResult.getResultData());

        return submitResult;
    }

	public static void main(String[] args) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("modifyPwd", "ZTEsoft123");
		new MyController().changePwd(data, null, null, null);
	}


}
