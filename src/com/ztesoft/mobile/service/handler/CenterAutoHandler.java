package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.dao.BaseDataDAO;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.BaseNativService;
import com.ztesoft.android.service.CommonXmlToJson;
import com.ztesoft.android.service.NativeService;
import com.ztesoft.android.util.ParserToList;
import com.ztesoft.android.util.TestDataUtil;
import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class CenterAutoHandler extends AbstractHandler {

	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数XX??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);

		String teachName = TimeUtil.strObj(jsonObject.get("ToPage"));   //这个为当前要请求的环节
		String StaffId = TimeUtil.strObj(jsonObject.get("StaffId"));               //主键ID
        String returnData = TimeUtil.strObj(jsonObject.get("returnData"));
        String orgId = TimeUtil.strObj(jsonObject.get("OrgId"));             //部门ID
        String jobId = TimeUtil.strObj(jsonObject.get("JobId"));             //当前职位
        String defaultJobId = TimeUtil.strObj(jsonObject.get("DefaultJobId")); //默认职位
        String userName = TimeUtil.strObj(jsonObject.get("UseName"));   //登录账号
        String queryType = TimeUtil.strObj(jsonObject.get("queryType"));  //针对查询列表，如果是有条件，则要先查条件，则查相关条件下的数据
        String body = TimeUtil.strObj(jsonObject.get("body"));   //这个里面的数据直接输入到业务系统
		
		String jsondata = "";
	
		try {
			Map mappingdata = getServiceDAO().getIntefaceInfoByTeach(teachName);
			//从映射表查找对应的请求数据
//			String intefaceUrl = mappingdata.get("intefaceUrl")+"";       //接口地址
//			String intefaceUrl2 = mappingdata.get("intefaceUrl2")+"";     //主菜单中的地址，两个地址，都保留，现在用intefaceUrl2
			String intefaceType = mappingdata.get("intefaceType")+"";     //接口类型，是servlet还是webservice 
			String sysCode = mappingdata.get("sysCode")+"";     //系统编码
			String displayType = returnData.equals("-1")?(String)mappingdata.get("displayType"):returnData;  //显示方式 (list列表，还是明细，还是表单),当回单页面特有标识(returnData)有数据的话，则说明此页面一定是回单页面，这时需要做特殊处理。
			String nowpage = mappingdata.get("teachName")+"";           //当前环节,list分页需要
			String formName = mappingdata.get("formName")+"";
            String formId = mappingdata.get("formId")+"";
            String keyName = mappingdata.get("keyName")+"";

            List<Map> fileList = getServiceDAO().getFiledByFormId(formId);         
            //String butPri= getServiceDAO().getPriButByFormId(formId,jobId,defaultJobId);//权限
            String butPri= getServiceDAO().getPriButByFormId(formId); //无权限
			
			//------------------------模似从接口过来的 json数据------------------------------------------------//
			String json = null;
			if(intefaceType.equals("1")){   //webserivice方式
				if("form".equals(queryType)){
					json="{\"result\": \"000\",\"body\":{\"listdata\": [],\"bodyresult\": \"000\",  \"errordesc\": \"查找成功\"}}";
				}else {
					JSONObject bodyobject = new JSONObject().fromObject(body);   //查询是否有映射账号,如果有则用账号账号信息	            
					String mappingData = getServiceDAO().getStaffMappingInfo(StaffId, sysCode);

					if(!"[]".equals(mappingData) && !"null".equals(mappingData)){
						JSONObject mappingDataObject = new JSONObject().fromObject(mappingData);	               

						if(!"null".equals(mappingDataObject.getString("StaffId"))){
							bodyobject.put("StaffId", mappingDataObject.get("StaffId"));
						}
						if(!"null".equals(mappingDataObject.getString("UseName"))){
							bodyobject.put("UseName", mappingDataObject.get("UseName"));
						}				
						if(!"null".equals(mappingDataObject.getString("JobId"))){
							bodyobject.put("JobId", mappingDataObject.get("JobId"));
						}
						if(!"null".equals(mappingDataObject.getString("OrgId"))){
							bodyobject.put("OrgId", mappingDataObject.get("OrgId"));
						}
					}
					Map m = getServiceDAO().getIntefaceInfoByName(teachName);
					json = CommonXmlToJson.xmlToJsonData(m,bodyobject.toString());
				}

			}else if(intefaceType.equals("2")){  //为servlet方式

				if(displayType.equals("2")||displayType.equals("14")){//列表
					json="{\"result\":\"000\",\"body\":{\"listdata\":[{\"OrderCode\":\"STAFF-1297182\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"配设备号\",\"CreateDate\":\"2012-01-05 13:52:43.0\",\"WorkOrderID\":\"2169865\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J011\",\"Address\":[],\"SlaTime\":\"2012-04-05 12:57:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"STAFF-1297142\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"配设备号\",\"CreateDate\":\"2012-03-12 20:25:55.0\",\"WorkOrderID\":\"2176341\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J011\",\"Address\":[],\"SlaTime\":\"2012-04-01 11:21:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20110822021\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"终端施工\",\"CreateDate\":\"2012-03-14 18:05:48.0\",\"WorkOrderID\":\"2176541\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"C012\",\"Address\":[],\"SlaTime\":\"2012-04-01 11:21:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120315021\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"pan外线\",\"CreateDate\":\"2012-03-15 17:25:02.0\",\"WorkOrderID\":\"2176654\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"panwaixian\",\"Address\":[],\"SlaTime\":[],\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120315021\",\"WorkOrderType\":\"追单\",\"CustName\":[],\"TacheName\":\"pan外线\",\"CreateDate\":\"2012-03-15 17:25:33.0\",\"WorkOrderID\":\"2176656\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"panwaixian\",\"Address\":[],\"SlaTime\":[],\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120316041\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"配物理号\",\"CreateDate\":\"2012-03-16 14:52:09.0\",\"WorkOrderID\":\"2176895\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J009\",\"Address\":[],\"SlaTime\":\"2012-04-05 12:57:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]}],\"pagenum\":\"1\",\"bodyresult\":\"000\",\"totalpage\":\"46\",\"errordesc\":\"查找成功\",\"totalnum\":\"272\",\"QueryMethod\":\"queryWorkOrderForEBiz\",\"pagesize\":\"6\"}}";
				}
				if(displayType.equals("3")){//明细
					json="{\"result\":\"000\",\"body\":{\"listdata\":{\"OrderCode\":\"STAFF-1297005\",\"WorkOrderType\":\"正常工单\",\"CustName\":[],\"TacheName\":\"配线\",\"CreateDate\":\"2012-01-04 16:59:09.0\",\"WorkOrderID\":\"2169684\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J017\",\"Address\":[],\"SlaTime\":\"2012-04-04 00:00:00.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},\"Result\":\"000\",\"listdata1\":{\"Panel1\":[],\"Mdfh\":\"1-7-50\",\"Mdfv\":\"1-1-5-48\",\"PanelP1\":[]},\"QueryMethod\":\"queryWorkOrderDetailForEBiz\",\"ErrorDesc\":\"查询成功\"}}";
					//json="{\"result\": \"000\",\"total\": \"1\", \"body\": {\"listdata\": [{\"newsType\":\"当前告警检查\",\"createDate\":\"2011-04-01 16:58:20.0\",\"title\":\"未完成\",\"infoTypeName\":\"信息中断\",\"infoState\":\"签单\",\"issuerName\":\"管理员\",\"content\":\"测试内容\",\"infoId\":\"1182\"}]}}";
				}
				//HttpClientUtile testSendData = new HttpClientUtile();
				//json = HttpClientUtile.SendHttpClient(url,passData); 
				//String url = intefaceUrl;//"http://192.168.43.22:7001/MOBILE/ceshiDataServlet";//intefaceUrl2+intefaceName;
				//jsonObject.put("type", "1");
				//JSONObject datas = new JSONObject();				
				//datas.put("params", passData);				
				//datas.put("type", "1");
			}else if(intefaceType.equals("4")){  //测试数据
				if("form".equals(queryType)){
					json="{\"result\": \"000\",\"body\":{\"listdata\": [],\"bodyresult\": \"000\",  \"errordesc\": \"查找成功\"}}";
				}else {
					Map m = getServiceDAO().getIntefaceInfoByName(teachName);
					json = new TestDataUtil().getData(m,1,body);
				}
			}else if(intefaceType.equals("5")){
				//NativeService nativeService = new NativeService();
				//json = nativeService.commonGetData(teachName, body);
				BaseNativService nativeSer = new BaseNativService();
				BaseDataDAO dataDao= nativeSer.getDao("ReasonDAOImpl");
				json = dataDao.getDataByStr(teachName, body,null);

			} else if("6".equals(intefaceType)) {   //针对ESB情况
                JSONObject rootObj = new JSONObject();
                rootObj.put("result", "测试");
                rootObj.put("nowpage", "PAGE_1041");
                rootObj.put("displayType", "2");

                //listdata
                JSONArray listdataArr = new JSONArray();

                JSONObject l1Obj = new JSONObject();
                l1Obj.put("id", "1");
                l1Obj.put("custName", "测试客户1");
                l1Obj.put("custNo", "0023334132");

                JSONObject l2Obj = new JSONObject();
                l2Obj.put("id", "2");
                l2Obj.put("custName", "测试客户2");
                l2Obj.put("custNo", "889414126");

                listdataArr.add(l1Obj);
                listdataArr.add(l2Obj);

                JSONObject bodyObj = new JSONObject();
                bodyObj.put("listdata", listdataArr);

                rootObj.put("body", bodyObj);
                json =  rootObj.toString();
            }

			if(displayType.equals("2")||displayType.equals("14")){   //list
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				jsondata = ParserToList.JsonToJsonList(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
				//System.out.println("jsondata------->> "+jsondata);
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
				jsondata = ParserToList.JsonToJsonListTree(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("8")){
	            String type = null;
	            List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				jsondata = ParserToList.JsonToJsonListTree2(json,type, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("9")){
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				json = "{\"result\":\"000\",\"body\":{\"LineConfig\":{\"state\":\"1\",\"time_con\":\"45分钟\" },\"TermConfig\":{ \"state\":\"1\",\"time_con\":\"22分钟\"},\"InNetActive\":{\"state\":\"1\",\"time_con\":\"245分钟\"}, \"PortConfig\":{ \"state\":\"3\", \"time_con\":\"245分钟\"},\"OutNetActive\":{\"state\":\"0\",\"time_con\":\"0分钟\"},\"Construction\":{\"state\":\"0\",\"time_con\":\"0分钟\"}}}";
				jsondata = ParserToList.JsonToJsonListTree3(json,null, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("10")){

				System.out.println("json------------->> "+json);
				jsondata = ParserToList.JsonToJsonStream(json,keyName,formName,nowpage,displayType,fileList);
				System.out.println("jsondata--> "+jsondata);
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