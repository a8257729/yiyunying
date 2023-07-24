package com.ztesoft.mobile.v2.controller.lightcat;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/light/cat")
@Controller
public class LightCatSnController {

    private Logger logger = LoggerFactory.getLogger(LightCatSnController.class);

    private String baseUrl = "http://192.168.101.29:9031/saat_comb/combService/call";

    private Gson gson = new Gson();
    //正确
    @RequestMapping(value = "/sn",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public String sn(@RequestParam("resId")String resId,String staffId, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        System.out.println(staffId);
        dataParam.put("resId",resId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.qryOntMacByOlt");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }
    //选中确定调用这个
    @RequestMapping(value = "/pon/update",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public String ponUpdate(@RequestParam("obdId")String obdId,@RequestParam("staffId")String staffId, @RequestParam("portId")String portId, HttpServletRequest req, HttpServletResponse rsp) throws Exception{

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("posId",obdId);
        dataParam.put("portId",portId);
        dataParam.put("posRestypeId",2530);
        dataParam.put("portRestypeId",2531);

        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNAllocationResService.updatePosPonPort");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //复制分光器
    @RequestMapping(value = "/copy/beamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String beamsplitterCopy(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String oltName = jsonObject.getString("oltName");
        String net_level = jsonObject.getString("net_level");
        String resId = jsonObject.getString("resId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("olt_name",oltName);
        dataParam.put("net_level",net_level);
        dataParam.put("resId",resId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.copyOLTInfo");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //关联用户
    @RequestMapping(value = "/link/user",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String ponLinkUser(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String obd_port_id = jsonObject.getString("obd_port_id");
        String tele_no = jsonObject.getString("tele_no");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("obd_port_id",obd_port_id);
        dataParam.put("tele_no",tele_no);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNAllocationResService.updatePosPortAndAccountOfAccountAssociated");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //上线后的替换
    @RequestMapping(value = "/replace/user",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String replaceUser(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String obd_port_id = jsonObject.getString("obd_port_id");
        String tele_no = jsonObject.getString("tele_no");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("obd_port_id",obd_port_id);
        dataParam.put("tele_no",tele_no);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNAllocationResService.updatePosPortAndAccount");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }


    //挂测回单
    @RequestMapping(value = "/submit/gc",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submicGc(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String obdId = jsonObject.getString("obdId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("obdId",obdId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.commitOrder");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //释放用户
    @RequestMapping(value = "/release/userByPosPort",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String releaseuserByPosPort(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String obd_port_id = jsonObject.getString("obd_port_id");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("obd_port_id",obd_port_id);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNAllocationResService.releaseAccountByPosPort");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

//    //查看PON口的状态
//    @RequestMapping(value = "/search/posPortByPosId",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String searchposPortByPosId(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
//        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
//        String resId = jsonObject.getString("resId");
//        String staffId = jsonObject.getString("staffId");
//        Map<String, Object> jsonMap = new HashMap<String, Object>();
//        Map bodyParam = new HashMap<String,String>();
//        Map dataParam = new HashMap<String,String>();
//        dataParam.put("resId",resId);
//        bodyParam.put("data", dataParam);
//        jsonMap.put("body", bodyParam);
//        Map sysParam = new HashMap<String,String>();
//        sysParam.put("serviceName", "HNResourceService.qryPosPortByPosId");
//        sysParam.put("staffId",staffId);
//        jsonMap.put("sys", sysParam);
//        String aaa = aaa(gson.toJson(jsonMap));
//        if(!StringUtils.isNotBlank(aaa)){
//            aaa="null";
//        }
//        return aaa;
//    }

    //修改分光器
    @RequestMapping(value = "/update/beamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String beamsplitterUpdate(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String oltName = jsonObject.getString("oltName");
        String net_level = jsonObject.getString("net_level");
        String resId = jsonObject.getString("resId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("olt_name",oltName);
        dataParam.put("net_level",net_level);
        dataParam.put("resId",resId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.modifyOLTInfo");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //插入备注
    @RequestMapping(value = "/insert/beamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String beamsplitterInsert(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String remark = jsonObject.getString("remark");
        String resId = jsonObject.getString("resId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("remark",remark);
        dataParam.put("resId",resId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.insertRmark");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }


    //修改覆盖地址
    @RequestMapping(value = "/update/coveraddress",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryCoverAddress(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String oldAddrSegmId = jsonObject.getString("oldAddrSegmId");
        String newAddrSegmId = jsonObject.getString("newAddrSegmId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("oldAddrSegmId",oldAddrSegmId);
        dataParam.put("newAddrSegmId",newAddrSegmId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNResourceService.modifyRelaAddress");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }


    //分光器能否打标校验
    @RequestMapping(value = "/inventory/beamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String beamsplitterInventory(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String staffId = jsonObject.getString("staffId");
        int resTypeId = jsonObject.getInt("resTypeId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("resTypeId",resTypeId);
        dataParam.put("obdId","");
        dataParam.put("stateId",1);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.updateResCombState");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //分光器能否打标校验
    @RequestMapping(value = "/check/beamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String beamsplitterCheck(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String obdId = jsonObject.getString("obdId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",obdId);
        dataParam.put("resTypeId",2530);
        dataParam.put("stateId",1);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.canUpdateCombState");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }
    //查询下线的ONT
    @RequestMapping(value = "/search/ponOfflineONT",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchponOfflineONT(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String portId = jsonObject.getString("portId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("portId",portId);
        dataParam.put("timeStamp",Calendar.getInstance().getTimeInMillis());
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.qryDownOntInfoByPon");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //查询上线的ONT
    @RequestMapping(value = "/search/ponUpONT",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String searchponUpONT(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String portId = jsonObject.getString("portId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("portId",portId);
        dataParam.put("timeStamp",Calendar.getInstance().getTimeInMillis());
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.qryUpOntInfoByPon");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    //添加覆盖地址
    @RequestMapping(value = "/add/coveraddress",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addCoverAddress(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String addrSegmId = jsonObject.getString("addrSegmId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("addrSegmId",addrSegmId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNResourceService.addResRelaAddress");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }


    //分光器绑定二维码
    @RequestMapping(value = "/bind/QRcodeBeamsplitter",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bindQRcodeBeamsplitter(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String digcode = jsonObject.getString("digcode");
        String staffId = jsonObject.getString("staffId");
        Integer resTypeId = jsonObject.getInt("resTypeId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        dataParam.put("resId",resId);
        dataParam.put("digcode",digcode);
        dataParam.put("account","");
        dataParam.put("resTypeId",resTypeId);
        bodyParam.put("data", dataParam);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNDataGovernService.accountRelaQRCode");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }


    //删除覆盖地址
    @RequestMapping(value = "/delete/coveraddress",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteCoverAddress(@RequestBody String jsonObj, HttpServletRequest req, HttpServletResponse rsp) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonObj);
        String resId = jsonObject.getString("resId");
        String addrSegmId = jsonObject.getString("addrSegmId");
        String staffId = jsonObject.getString("staffId");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map bodyParam = new HashMap<String,String>();
        Map dataParam = new HashMap<String,String>();
        List<Map<String,String>> maps = new ArrayList<Map<String, String>>();
        dataParam.put("resId",resId);
        dataParam.put("addrSegmId",addrSegmId);
        maps.add(dataParam);
        bodyParam.put("data", maps);
        jsonMap.put("body", bodyParam);
        Map sysParam = new HashMap<String,String>();
        sysParam.put("serviceName", "HNResourceService.resDelAddress");
        sysParam.put("staffId",staffId);
        jsonMap.put("sys", sysParam);
        String aaa = aaa(gson.toJson(jsonMap));
        if(!StringUtils.isNotBlank(aaa)){
            aaa="null";
        }
        return aaa;
    }

    public  String aaa(String param) throws MalformedURLException {
        URL url = new URL("http://192.168.101.29:9031/saat_comb/combService/call");
        OutputStream os = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String state = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) (url.openConnection());
            // 设置建立连接的超时时间（单位：毫秒）
            httpURLConnection.setConnectTimeout(10000);
            // 设置传递数据的超时时间（单位：毫秒）
            httpURLConnection.setReadTimeout(90000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json;charset=UTF-8, text/javascript, */*");
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
            httpURLConnection.setRequestProperty("Content-Length", param.getBytes("UTF-8").length + "");
            String line = null;

            os = httpURLConnection.getOutputStream();
            osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(param);
            osw.flush();
            state = httpURLConnection.getResponseCode()+"";
            logger.info("响应代码:" + state);
            logger.info("请求参数:" + param);
            is = httpURLConnection.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");
            reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                resultBuffer.append(line);
            }

        } catch (MalformedURLException e) {
            logger.info(param + " MalformedURLException :" + e.getMessage());
        } catch (IOException e) {
            logger.info(param + " IOException :" + e.getMessage());
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
       return resultBuffer.toString();
    }
}
