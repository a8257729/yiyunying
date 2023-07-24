package com.ztesoft.android.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.CommonXmlToJson;
import com.ztesoft.android.util.ParserToList;
import com.ztesoft.android.util.TestDataUtil;
import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class CenterAutoServlet extends HttpServlet{
    	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String jsonPara = req.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("�������??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);

		String teachName = TimeUtil.strObj(jsonObject.get("ToPage"));   //���Ϊ��ǰҪ����Ļ���
		String keyId = TimeUtil.strObj(jsonObject.get("id"));               //����ID
        String returnData = TimeUtil.strObj(jsonObject.get("returnData"));
        String orgId = TimeUtil.strObj(jsonObject.get("OrgId"));             //����ID
        String jobId = TimeUtil.strObj(jsonObject.get("JobId"));             //��ǰְλ
        String defaultJobId = TimeUtil.strObj(jsonObject.get("DefaultJobId")); //Ĭ��ְλ
        String userName = TimeUtil.strObj(jsonObject.get("UseName"));   //��¼�˺�
        
        String body = TimeUtil.strObj(jsonObject.get("body"));   //������������ֱ�����뵽ҵ��ϵͳ
		
		String jsondata = "";
		System.out.println("keyId-------------->>  "+keyId);

		try {
			Map mappingdata = getServiceDAO().getIntefaceInfoByTeach(teachName);
			//��ӳ�����Ҷ�Ӧ����������
//			String intefaceUrl = mappingdata.get("intefaceUrl")+"";       //�ӿڵ�ַ
//			String intefaceUrl2 = mappingdata.get("intefaceUrl2")+"";     //���˵��еĵ�ַ��������ַ����������������intefaceUrl2
			String intefaceType = mappingdata.get("intefaceType")+"";     //�ӿ����ͣ���servlet����webservice 
			//String intefaceName = mappingdata.get("intefaceName")+"";     //�ӿ�����
			String displayType = returnData.equals("-1")?(String)mappingdata.get("displayType"):returnData;  //��ʾ��ʽ (list�б�������ϸ�����Ǳ�),���ص�ҳ�����б�ʶ(returnData)�����ݵĻ�����˵����ҳ��һ���ǻص�ҳ�棬��ʱ��Ҫ�����⴦��
			String nowpage = mappingdata.get("teachName")+"";           //��ǰ����,list��ҳ��Ҫ
			String formName = mappingdata.get("formName")+"";
            String formId = mappingdata.get("formId")+"";
            String keyName = mappingdata.get("keyName")+"";
            System.out.println("displayType--->  "+displayType);
            List<Map> fileList = getServiceDAO().getFiledByFormId(formId);
            
            String butPri= getServiceDAO().getPriButByFormId(formId,jobId,defaultJobId);
			
           
			//System.out.println("butPri--->  "+butPri.size());
			//------------------------ģ�ƴӽӿڹ����� json����------------------------------------------------//
			String json = null;
			if(intefaceType.equals("1")){   //webserivice��ʽ
				if(displayType.equals("2")||displayType.equals("14")){//�б�

					json="{\"result\":\"000\",\"body\":{\"listdata\":[{\"OrderCode\":\"STAFF-1297182\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"���豸��\",\"CreateDate\":\"2012-01-05 13:52:43.0\",\"WorkOrderID\":\"2169865\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J011\",\"Address\":[],\"SlaTime\":\"2012-04-05 12:57:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"STAFF-1297142\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"���豸��\",\"CreateDate\":\"2012-03-12 20:25:55.0\",\"WorkOrderID\":\"2176341\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J011\",\"Address\":[],\"SlaTime\":\"2012-04-01 11:21:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20110822021\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"�ն�ʩ��\",\"CreateDate\":\"2012-03-14 18:05:48.0\",\"WorkOrderID\":\"2176541\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"C012\",\"Address\":[],\"SlaTime\":\"2012-04-01 11:21:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120315021\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"pan����\",\"CreateDate\":\"2012-03-15 17:25:02.0\",\"WorkOrderID\":\"2176654\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"panwaixian\",\"Address\":[],\"SlaTime\":[],\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120315021\",\"WorkOrderType\":\"׷��\",\"CustName\":[],\"TacheName\":\"pan����\",\"CreateDate\":\"2012-03-15 17:25:33.0\",\"WorkOrderID\":\"2176656\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"panwaixian\",\"Address\":[],\"SlaTime\":[],\"CustLinkPerson\":[],\"ServiceName\":[]},{\"OrderCode\":\"20120316041\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"�������\",\"CreateDate\":\"2012-03-16 14:52:09.0\",\"WorkOrderID\":\"2176895\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J009\",\"Address\":[],\"SlaTime\":\"2012-04-05 12:57:35.0\",\"CustLinkPerson\":[],\"ServiceName\":[]}],\"pagenum\":\"1\",\"bodyresult\":\"000\",\"totalpage\":\"46\",\"errordesc\":\"���ҳɹ�\",\"totalnum\":\"272\",\"QueryMethod\":\"queryWorkOrderForEBiz\",\"pagesize\":\"6\"}}";
				}
				if(displayType.equals("3")){//��ϸ
					json="{\"result\":\"000\",\"body\":{\"listdata\":{\"OrderCode\":\"STAFF-1297005\",\"WorkOrderType\":\"��������\",\"CustName\":[],\"TacheName\":\"����\",\"CreateDate\":\"2012-01-04 16:59:09.0\",\"WorkOrderID\":\"2169684\",\"AccNbr\":[],\"CustLinkPhone\":[],\"TacheCode\":\"J017\",\"Address\":[],\"SlaTime\":\"2012-04-04 00:00:00.0\",\"CustLinkPerson\":[],\"ServiceName\":[]},\"Result\":\"000\",\"listdata1\":{\"Panel1\":[],\"Mdfh\":\"1-7-50\",\"Mdfv\":\"1-1-5-48\",\"PanelP1\":[]},\"QueryMethod\":\"queryWorkOrderDetailForEBiz\",\"ErrorDesc\":\"��ѯ�ɹ�\"}}";
					//json="{\"result\": \"000\",\"total\": \"1\", \"body\": {\"listdata\": [{\"newsType\":\"��ǰ�澯���\",\"createDate\":\"2011-04-01 16:58:20.0\",\"title\":\"δ���\",\"infoTypeName\":\"��Ϣ�ж�\",\"infoState\":\"ǩ��\",\"issuerName\":\"����Ա\",\"content\":\"��������\",\"infoId\":\"1182\"}]}}";
				}
			}else if(intefaceType.equals("2")){
				//HttpClientUtile testSendData = new HttpClientUtile();
				//json = HttpClientUtile.SendHttpClient(url,passData); 
				//String url = intefaceUrl;//"http://192.168.43.22:7001/MOBILE/ceshiDataServlet";//intefaceUrl2+intefaceName;
				//jsonObject.put("type", "1");
				//JSONObject datas = new JSONObject();				
				//datas.put("params", passData);				
				//datas.put("type", "1");
	
				Map m = getServiceDAO().getIntefaceInfoByName(teachName);
				json = CommonXmlToJson.xmlToJsonData(m,body);
				       
			}else if(intefaceType.equals("4")){  //��������
				Map m = getServiceDAO().getIntefaceInfoByName(teachName);
				json = new TestDataUtil().getData(m,1,body);
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
			if(displayType.equals("returnData")){          //returnDataΪ�ͻ��˷��ص�ָ���ص���ʶ
				jsondata = "{\"result\": \"002\",\"dataId\":\"00001\"}";//DoServiceDaoImpl.CommonDoService(intefaceUrl,intefaceName,"233"); //��ʱ�ӿڷ�����裬��ָ����ʽ����json����
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
//				if(access_num.equals("52412450")){
//				   json = "{\"result\": \"000\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"���׽�322������Ԫ�ֹ���01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ047\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ048\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\", \"SEQ\": 3}]}}";
//				}else {
//					json = "{\"result\": \"2001\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"���׽�322������Ԫ�ֹ���01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ047\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ048\",\"NODE_NAME\": \"�Ϻ�·34-3���ſ�GJ046\", \"SEQ\": 3}]}}";
//				}
				jsondata = ParserToList.JsonToJsonListTree(json, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("8")){
	            String type = null;
//				if(json==null){
//					if(nowpage.equals("modegldl")){
//						type = "OBD";
//						if(CODE.equals("DT-POS-003777")){
//							json = "{\"result\": \"000\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-003777\", \"NAME\": \"���׽�322������Ԫ�ֹ���03\" }}";
//						}else if(CODE.equals("DT-POS-0035102")){
//							json = "{\"result\": \"000\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-0035102\", \"NAME\": \"���׽�322������Ԫ�ֹ���03\" }}";
//						}else {
//							json = "{\"result\": \"2004\", \"body\": {\"TYPE\":\"OBD\", \"CODE\": \"DT-POS-003777\", \"NAME\": \"���׽�322������Ԫ�ֹ���03\" }}";
//						}
//					}else if(nowpage.equals("addgldl")){
//						type = "NODE";
//						if(CODE.equals("250NJ.JLA001GB001A044")){
//							json = "{\"result\":\"000\",\"body\": {\"TYPE\":\"NODE\",\"CODE\":\"250NJ.JLA001GB001A044\",\"NAME\":\"�Ԣ���ն˺�001��ԢA¥\"}}";
//						}else {
//							json = "{\"result\":\"2002\",\"body\": {\"TYPE\":\"NODE\",\"CODE\":\"250NJ.JLA001GB001A044\",\"NAME\":\"�Ԣ���ն˺�001��ԢA¥\"}}";
//						}
//					}
//				}
	            List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				jsondata = ParserToList.JsonToJsonListTree2(json,type, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("9")){
				List<Map> fileNodeList = getServiceDAO().getFiledNodeByFormId(formId);
				json = "{\"result\":\"000\",\"body\":{\"LineConfig\":{\"state\":\"1\",\"time_con\":\"45����\" },\"TermConfig\":{ \"state\":\"1\",\"time_con\":\"22����\"},\"InNetActive\":{\"state\":\"1\",\"time_con\":\"245����\"}, \"PortConfig\":{ \"state\":\"3\", \"time_con\":\"245����\"},\"OutNetActive\":{\"state\":\"0\",\"time_con\":\"0����\"},\"Construction\":{\"state\":\"0\",\"time_con\":\"0����\"}}}";
				jsondata = ParserToList.JsonToJsonListTree3(json,null, keyName,formName,nowpage,displayType, fileList, butPri,fileNodeList);
			}
			if(displayType.equals("10")){

				jsondata = ParserToList.JsonToJsonStream(json,keyName,formName,nowpage,displayType,fileList);
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
					//jsondata = "{\"templet\":{\"result\":\"000\",\"body\":[{\"L0\":\"ftth_lv\",\"L2\":\"����\",\"L3\":\"2\",\"L1\":\"FTTH�ɹ���\"},{\"L0\":\"ftth_auto_count\",\"L2\":\"����\",\"L3\":\"2\",\"L1\":\"�ɹ���/��������\"},{\"L0\":\"fttb_lv\",\"L2\":\"����\",\"L3\":\"3\",\"L1\":\"FTTB�ɹ���\"},{\"L0\":\"fttb_auto_count\",\"L2\":\"����\",\"L3\":\"3\",\"L1\":\"�ɹ���/��������\"},{\"L0\":\"ftthb_lv\",\"L2\":\"\",\"L3\":\"4\",\"L1\":\"FTTB�ɹ���\"},{\"L0\":\"ftthb_auto_count\",\"L2\":\"\",\"L3\":\"4\",\"L1\":\"�ɹ���/��������\"}]},\"result\":\"000\",\"body\":{\"ftth_lv\":\"95.1%\",\"ftthb_auto_count\":\"253377/271128\",\"ftthb_lv\":\"93.45%\",\"ftth_auto_count\":\"190646/200476\",\"fttb_auto_count\":\"62731/70652\",\"fttb_lv\":\"88.79%\"}}";
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
			//�����سɹ� jsondata = "{\"result\": \"000\",\"dataId\":"\00001"\}";  //���� 
			//���ɹ���Ϊ jsondata = "{\"result\": \"002\",\"dataId\":"\00001"\}"; //ҵ��ϵͳ�����쳣
			//System.out.println("json--> "+jsondata.toString());
		} catch (DataAccessException e) {
			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    

		System.out.println("ԭ�ַ���="+jsondata);   
	    System.out.println("ԭ��="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("ѹ������ַ���="+newstr);   
	    System.out.println("ѹ����ĳ�="+newstr.length());  
	      
		resp.setContentType("text/plain;charset=ISO-8859-1");
		resp.getWriter().write(newstr);


	}
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}