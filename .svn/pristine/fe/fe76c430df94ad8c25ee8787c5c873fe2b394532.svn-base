package com.ztesoft.mobile.v2.controller.iptv;

import com.google.gson.Gson;
import com.ztesoft.mobile.v2.core.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: TODO
 * @author: lck
 * @date: 2020Äê10ÔÂ14ÈÕ 17:04
 */
@RequestMapping("/iptv")
@Controller
public class IptvFaultDiagnoseController {

    private String baseUrl = "http://192.168.101.175:11004/iptv/faultDiagnose";

    private Gson gson = new Gson();

    public static void main(String[] args){
        String url = "http://192.168.101.175:11004/iptv/faultDiagnose";
        RestTemplate rest = new RestTemplate();
        Map param = new HashMap();
        param.put("ukType","1");
        param.put("ukValue","073108922484A@tv");
        Map<String,Object> resMap = rest.postForObject(url, param, Map.class);
        System.out.println(resMap);
    }

    @RequestMapping(value = "/faultDiagnose",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody String iptvFaultDiagnose(HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        String ukType = req.getParameter("ukType");
        String ukValue = req.getParameter("ukValue");
        String flag = req.getParameter("flag");
        RestTemplate rest = new RestTemplate();
        Map param = new HashMap();
        param.put("ukType",ukType);
        param.put("ukValue",ukValue);
        param.put("flag",flag);
        Map<String,Object> resMap = rest.postForObject(baseUrl, param, Map.class);
        Iterator<Map.Entry<String, Object>> iterator = resMap.entrySet().iterator();
        List<Object> listData = new ArrayList<Object>();
        while(iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            if(next.getKey().equals("code")||next.getKey().equals("msg")||next.getKey().equals("ukTestItem")){
                continue;
            }
            listData.add(next.getValue());
        }
        return gson.toJson(listData);
    }
}
