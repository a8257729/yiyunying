package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.util.HttpClientUtile;
import com.ztesoft.android.util.ParserToList;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class CopyOfCenterAutoHandler extends AbstractHandler {

	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数XX??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String teachName = jsonObject.get("topage")==null?"-1":jsonObject.getString("topage");   //这个为当前要请法语的环节
		
		String keyId = jsonObject.get("id")==null?"-1":jsonObject.getString("id");
        String returnData = jsonObject.get("returnData")==null?"-1":jsonObject.getString("returnData");
        String jobId = jsonObject.get("jobId")==null?"-1":jsonObject.getString("jobId");
        String defaultJobId = jsonObject.get("defaultJobId")==null?"-1":jsonObject.getString("defaultJobId");
        
        String CODE = jsonObject.get("CODE")==null?"-1":jsonObject.getString("CODE");
        String OBD_CODE = jsonObject.get("OBD_CODE")==null?"-1":jsonObject.getString("OBD_CODE");
        String access_num = jsonObject.get("access_num")==null?"-1":jsonObject.getString("access_num");
        String passData = jsonObject.get("passData")==null?"-1":jsonObject.getString("passData");   //这个为当前要请法语的环节
		System.out.println("name--> "+teachName +" returnData--> "+returnData);
		String jsondata = "";
		System.out.println("keyId-------------->>  "+keyId);

		try {
			Map mappingdata = getServiceDAO().getIntefaceInfoByTeach(teachName);
			//从映射表查找对应的请求数据
			String intefaceUrl = mappingdata.get("intefaceUrl")+"";       //接口地址
			String intefaceUrl2 = mappingdata.get("intefaceUrl2")+"";     //主菜单中的地址，两个地址，都保留，现在用intefaceUrl2
			String intefaceType = mappingdata.get("intefaceType")+"";     //接口类型，是servlet还是webservice 
			String intefaceName = mappingdata.get("intefaceName")+"";     //接口名称
			String displayType = returnData.equals("-1")?(String)mappingdata.get("displayType"):returnData;  //显示方式 (list列表，还是明细，还是表单),当回单页面特有标识(returnData)有数据的话，则说明此页面一定是回单页面，这时需要做特殊处理。
			String nowpage = mappingdata.get("teachName")+"";           //当前环节,list分页需要
			String formName = mappingdata.get("formName")+"";
            String formId = mappingdata.get("formId")+"";
            String keyName = mappingdata.get("keyName")+"";
            System.out.println("displayType--->  "+displayType);
            List<Map> fileList = getServiceDAO().getFiledByFormId(formId);
           // List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
            String butPri= getServiceDAO().getPriButByFormId(formId,jobId,defaultJobId);
			
           
			//System.out.println("butPri--->  "+butPri.size());
			//------------------------模似从接口过来的 json数据------------------------------------------------//
			String json = null;
			if(intefaceType.equals("1")){   //webserivice方式
//				if((displayType.equals("2")||displayType.equals("3")) && !displayType.equals("returnData")){  //from
//					System.out.println("intefaceUrl->  "+intefaceUrl);
//					String tempIntefaceUrl = intefaceUrl2.equals("")?intefaceUrl:intefaceUrl2;   //如果intefaceUrl2没有，则用intefaceUrl
//					json = DoServiceDaoImpl.CommonDoService(tempIntefaceUrl,intefaceName,"233");
//				}
				if(displayType.equals("2")||displayType.equals("14")){//列表
					json="{\"result\": \"000\",\"total\": \"23\", \"body\": {\"listdata\": [{\"issuerName\":\"test\",\"newsType\":\"当前告警检查\",\"createDate\":\"2011-04-01 16:58:20.0\",\"newsId\":\"1182\",\"infoState\":\"签单\"},{\"issuerName\":\"管理员\",\"newsType\":\"检查应用系统的日志\",\"createDate\":\"2010-12-28 09:04:59.0\",\"newsId\":\"1021\",\"infoState\":\"签单\"},{\"issuerName\":\"ossmh\",\"newsType\":\"操作日志检查\",\"createDate\":\"2010-10-13 11:47:15.0\",\"newsId\":\"465\",\"infoState\":\"签单\"},{\"issuerName\":\"ossmh\",\"newsType\":\"设备与业务运行情况检查\",\"createDate\":\"2010-10-13 11:46:21.0\",\"newsId\":\"464\",\"infoState\":\"签单\"},{\"issuerName\":\"ossmh\",\"newsType\":\"当前告警检查\",\"createDate\":\"2010-10-13 11:44:01.0\",\"newsId\":\"463\",\"infoState\":\"签单\"},{\"issuerName\":\"ossmh\",\"auditStaffName\":\"ossmh\",\"newsType\":\"检查应用系统的日志\",\"createDate\":\"2010-10-13 11:42:47.0\",\"newsId\":\"462\",\"infoState\":\"回单\"},{\"issuerName\":\"swgAdmin\",\"auditStaffName\":\"swgAdmin\",\"newsType\":\"操作日志检查\",\"createDate\":\"2010-09-21 15:24:11.0\",\"newsId\":\"242\",\"infoState\":\"回单\"}]}}";
				}
				if(displayType.equals("3")){//明细
					json="{\"result\": \"000\",\"total\": \"1\", \"body\": {\"listdata\": [{\"newsType\":\"当前告警检查\",\"createDate\":\"2011-04-01 16:58:20.0\",\"title\":\"未完成\",\"infoTypeName\":\"信息中断\",\"infoState\":\"签单\",\"issuerName\":\"管理员\",\"content\":\"测试内容\",\"infoId\":\"1182\"}]}}";
				}
			}else if(intefaceType.equals("2")){
				HttpClientUtile testSendData = new HttpClientUtile();
//				String sle = "{\"body\": {\"user_name\": \"zhangyanan\", \"pwd\": \"zhangyanan\" },\"engine_version\": \"1.0\",\"device_info\": {\"screen\": \"480*800\", \"model\": \"xt800\"}}";
//				//String responseBody = testSendData.SendHttpClient("http://202.102.116.111:8080/irec/check-staff!checkStaff?",sle);  
//				
//				JSONObject sesdata = new JSONObject().fromObject(sle);
//				JSONObject body = sesdata.getJSONObject("body");
//				//mDataSource.setSessionId(body.optString("session_id"));
//				
//				String user_id = body.get("user_id")==null?"-1":body.getString("user_id");   
//				String session_id = body.get("session_id")==null?"-1":body.getString("session_id");   
//				
//				System.out.println(user_id +" d "+session_id); 
				
				String url = intefaceUrl;//"http://192.168.43.22:7001/MOBILE/ceshiDataServlet";//intefaceUrl2+intefaceName;

				//jsonObject.put("type", "1");

				JSONObject datas = new JSONObject();
				
				datas.put("params", passData);
				
				//datas.put("type", "1");
//				jsonObject.put("session_id", session_id);
				System.out.println("url------------->> "+url +" nnnnnnn "+passData);
				json = HttpClientUtile.SendHttpClient(url,passData); 
				System.out.println("json------->> "+json);
			}

			if(displayType.equals("2")||displayType.equals("14")){   //list
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				jsondata = ParserToList.JsonToJsonList(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
				System.out.println("jsondata------->> "+jsondata);
			}
			if(displayType.equals("3")){   //display
				String tabs = getServiceDAO().getSearchTab(formId);
				String fileNodeList = getServiceDAO().getFiledNodeStrByFormId(formId);
				
				jsondata = ParserToList.JsonToJsonDetail(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList,tabs);
			} 
			if(displayType.equals("4")){   //from
				jsondata = ParserToList.JsonToJsonForm(json, keyName,formName,nowpage,displayType, fileList, butPri);
			} 
			if(displayType.equals("returnData")){          //returnData为客户端返回的指定回单标识
				jsondata = "{\"result\": \"002\",\"dataId\":\"00001\"}";//DoServiceDaoImpl.CommonDoService(intefaceUrl,intefaceName,"233"); //此时接口服务端需，按指定格式返回json数据
			}
			if(displayType.equals("13")){
				 String tabs = getServiceDAO().getSearchTab(formId);
				 System.out.println("tabs--> "+tabs);
				jsondata = ParserToList.JsonMethodName(nowpage,formName,displayType,butPri,fileList,tabs); 
				
			}
			if(displayType.equals("15")){
				 String tabs = getServiceDAO().getSearchTab2(formId);
				 System.out.println("tabs--> "+tabs);
				jsondata = ParserToList.JsonMethodName(nowpage,formName,displayType,butPri,fileList,tabs); 
				
			}
			if(displayType.equals("7")){  //listTree
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				if(access_num.equals("52412450")){
				   json = "{\"result\": \"000\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"长白街322号三单元分管器01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ047\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ048\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\", \"SEQ\": 3}]}}";
				}else {
					json = "{\"result\": \"2001\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"长白街322号三单元分管器01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ047\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ048\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\", \"SEQ\": 3}]}}";
				}
				jsondata = ParserToList.JsonToJsonListTree(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("8")){
	            String type = null;
				if(json==null){
					if(nowpage.equals("modegldl")){
						type = "OBD";
						if(CODE.equals("DT-POS-003777")){
							json = "{\"result\": \"000\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-003777\", \"NAME\": \"长白街322号三单元分管器03\" }}";
						}else if(CODE.equals("DT-POS-0035102")){
							json = "{\"result\": \"000\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-0035102\", \"NAME\": \"长白街322号三单元分管器03\" }}";
						}else {
							json = "{\"result\": \"2004\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-003777\", \"NAME\": \"长白街322号三单元分管器03\" }}";
						}
					}else if(nowpage.equals("addgldl")){
						type = "NODE";
						if(CODE.equals("250NJ.JLA001GB001A044")){
							json = "{\"result\":\"000\",\"body\": {\"TYPE\":\"NODE\",\"CODE\":\"250NJ.JLA001GB001A044\",\"NAME\":\"杨公寓光终端盒001公寓A楼\"}}";
						}else {
							json = "{\"result\":\"2002\",\"body\": {\"TYPE\":\"NODE\",\"CODE\":\"250NJ.JLA001GB001A044\",\"NAME\":\"杨公寓光终端盒001公寓A楼\"}}";
						}
					}
				}
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				jsondata = ParserToList.JsonToJsonListTree2(json,type, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("9")){
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				json = "{\"result\":\"000\",\"body\":{\"LineConfig\":{\"state\":\"1\",\"time_con\":\"45分钟\" },\"TermConfig\":{ \"state\":\"1\",\"time_con\":\"22分钟\"},\"InNetActive\":{\"state\":\"1\",\"time_con\":\"245分钟\"}, \"PortConfig\":{ \"state\":\"3\", \"time_con\":\"245分钟\"},\"OutNetActive\":{\"state\":\"0\",\"time_con\":\"0分钟\"},\"Construction\":{\"state\":\"0\",\"time_con\":\"0分钟\"}}}";
				jsondata = ParserToList.JsonToJsonListTree3(json,null, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("10")){
//				if(nowpage.equals("checkgdld")){
//					if(OBD_CODE.equals("DT-POS-003777")){
//						jsondata = "{\"result\":\"000\"}";
//					}else {
//						jsondata = "{\"result\":\"2003\"}";
//					}
//					
//				}else {
//				   jsondata = "{\"result\":\"000\"}";//1111如果有异常
//				}
				jsondata = json;
			}
			if(displayType.equals("11")){
				jsondata = "{\"result\": \"000\",\"body\": {\"statCycle\": \"1-2\",\"statRange\": \"1-2\"}}";
			}
			if(displayType.equals("12")){
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				String statFiledTemple = getServiceDAO().getStatFiledByFormId(formId);
				String transferTemple = getServiceDAO().getStatTransferByFormId(formId);
				String statNodeTemple = JsonUtil.getJsonByList(fileNodeList);
				if(teachName.equals("statpage")){
					json = "{\"result\": \"000\",\"body\": {\"statCycle\": \"1-2\",\"statRange\": \"1-2\"}}";
				}else if(teachName.equals("statlist")){
					//jsondata = "{\"templet\":{\"result\":\"000\",\"body\":[{\"L0\":\"ftth_lv\",\"L2\":\"详情\",\"L3\":\"2\",\"L1\":\"FTTH成功率\"},{\"L0\":\"ftth_auto_count\",\"L2\":\"详情\",\"L3\":\"2\",\"L1\":\"成功数/工单总数\"},{\"L0\":\"fttb_lv\",\"L2\":\"详情\",\"L3\":\"3\",\"L1\":\"FTTB成功率\"},{\"L0\":\"fttb_auto_count\",\"L2\":\"详情\",\"L3\":\"3\",\"L1\":\"成功数/工单总数\"},{\"L0\":\"ftthb_lv\",\"L2\":\"\",\"L3\":\"4\",\"L1\":\"FTTB成功率\"},{\"L0\":\"ftthb_auto_count\",\"L2\":\"\",\"L3\":\"4\",\"L1\":\"成功数/工单总数\"}]},\"result\":\"000\",\"body\":{\"ftth_lv\":\"95.1%\",\"ftthb_auto_count\":\"253377/271128\",\"ftthb_lv\":\"93.45%\",\"ftth_auto_count\":\"190646/200476\",\"fttb_auto_count\":\"62731/70652\",\"fttb_lv\":\"88.79%\"}}";
				   json = "{\"result\":\"000\",\"body\":{\"ftth_lv\":\"95.1%\",\"ftthb_auto_count\":\"253377/271128\",\"ftthb_lv\":\"93.45%\",\"ftth_auto_count\":\"190646/200476\",\"fttb_auto_count\":\"62731/70652\",\"fttb_lv\":\"88.79%\"}}";
				}else if(teachName.equals("statdetail")){
					json = "{\"result\":\"000\",\"body\":{\"nt\":{\"auto_lv\":\"93.12%\",\"auto_count\":\"7094/7618\"},\"yc\":{\"auto_lv\":\"91.89%\",\"auto_count\":\"10093/10984\"},\"nj\":{\"auto_lv\":\"94.25%\",\"auto_count\":\"34343/36437\"},\"xz\":{\"auto_lv\":\"97.33%\",\"auto_count\":\"20550/21113\"},\"sz\":{\"auto_lv\":\"97.33%\",\"auto_count\":\"36488/37488\"},\"sq\":{\"auto_lv\":\"98.47%\",\"auto_count\":\"7208/7320\"},\"lyg\":{\"auto_lv\":\"98.13%\",\"auto_count\":\"13382/13637\"},\"zj\":{\"auto_lv\":\"90.13%\",\"auto_count\":\"12875/14285\"},\"ha\":{\"auto_lv\":\"89.06%\",\"auto_count\":\"6723/7549\"},\"cz\":{\"auto_lv\":\"98.66%\",\"auto_count\":\"19643/19910\"},\"tz\":{\"auto_lv\":\"93.59%\",\"auto_count\":\"4893/5228\"},\"wx\":{\"auto_lv\":\"91.61%\",\"auto_count\":\"6421/7009\"},\"yz\":{\"auto_lv\":\"91.89%\",\"auto_count\":\"10933/11898\"}}}";
				}else if(teachName.equals("statInfoDetail")){
					json = "{\"result\":\"000\",\"body\":{\"ftth_e8c_auto_count\":\"131815/138846\",\"ftth_onu_auto_count\":\"21417/24786\",\"ftth_e8c_lv\":\"94.94%\",\"ftth_onu_lv\":\"86.41%\"}}";
				}else if(teachName.equals("statAllInfoDetail")){
					json = "{\"result\":\"000\",\"body\":{\"nt\":{\"auto_lv\":\"93.12%\",\"auto_count\":\"7094/7618\"},\"yc\":{\"auto_lv\":\"91.89%\",\"auto_count\":\"10093/10984\"},\"nj\":{\"auto_lv\":\"94.25%\",\"auto_count\":\"34343/36437\"},\"xz\":{\"auto_lv\":\"97.33%\",\"auto_count\":\"20550/21113\"},\"sz\":{\"auto_lv\":\"97.33%\",\"auto_count\":\"36488/37488\"},\"sq\":{\"auto_lv\":\"98.47%\",\"auto_count\":\"7208/7320\"},\"lyg\":{\"auto_lv\":\"98.13%\",\"auto_count\":\"13382/13637\"},\"zj\":{\"auto_lv\":\"90.13%\",\"auto_count\":\"12875/14285\"},\"ha\":{\"auto_lv\":\"89.06%\",\"auto_count\":\"6723/7549\"},\"cz\":{\"auto_lv\":\"98.66%\",\"auto_count\":\"19643/19910\"},\"tz\":{\"auto_lv\":\"93.59%\",\"auto_count\":\"4893/5228\"},\"wx\":{\"auto_lv\":\"91.61%\",\"auto_count\":\"6421/7009\"},\"yz\":{\"auto_lv\":\"91.89%\",\"auto_count\":\"10933/11898\"}}}";
				}else if(teachName.equals("cityStatList")){
					json = "{\"result\":\"000\",\"body\":{\"ftth_lv\":\"95.1%\",\"ftthb_auto_count\":\"253377/271128\",\"ftthb_lv\":\"93.45%\",\"ftth_auto_count\":\"190646/200476\",\"fttb_auto_count\":\"62731/70652\",\"fttb_lv\":\"88.79%\"}}";
				}else if(teachName.equals("cityStatDetail")){
					json = "{\"result\":\"000\",\"body\":{\"rg\":{\"auto_lv\":\"87.74%\",\"auto_count\":\"973/1109\"},\"ha\":{\"auto_lv\":\"93.53%\",\"auto_count\":\"1114/1191\"},\"hm\":{\"auto_lv\":\"96.6%\",\"auto_count\":\"1194/1236\"},\"ntsq\":{\"auto_lv\":\"95.93%\",\"auto_count\":\"3182/3317\"},\"tz\":{\"auto_lv\":\"88.13%\",\"auto_count\":\"1403/1592\"},\"qd\":{\"auto_lv\":\"92.27%\",\"auto_count\":\"2125/2303\"},\"rd\":{\"auto_lv\":\"86.6%\",\"auto_count\":\"517/597\"}}}";
				}
				else {
					json = "{\"result\": \"000\",\"body\": {\"statCycle\": \"1-2\",\"statRange\": \"1-2\"}}";
				}
				 
				jsondata = ParserToList.JsonStatData(json, statFiledTemple, transferTemple, statNodeTemple);
			}
			//即返回成功 jsondata = "{\"result\": \"000\",\"dataId\":"\00001"\}";  //正常 
			//不成功则为 jsondata = "{\"result\": \"002\",\"dataId\":"\00001"\}"; //业务系统操作异常
			//System.out.println("json--> "+jsondata.toString());
		} catch (DataAccessException e) {
			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    

		//System.out.println("原字符串="+jsondata);   
	    //System.out.println("原长="+jsondata.length());   
		String newstr = ZipUtil.compress(jsondata);   
	    //System.out.println("压缩后的字符串="+newstr);   
	    //System.out.println("压缩后的长="+newstr.length());  
		///ResponseObject resObj = new ResponseObject();
		//resObj.setResponse(newstr);
		paramMap.put("response", newstr);
		//return resObj;

	}
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}