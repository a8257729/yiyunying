package com.ztesoft.android.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

  
/** 
 * JsonUtil测试类 
 * 
 */  
public class XmlToJsonUtils {  
  
    /** 
     * 从json串转换成实体对象，且实体中Date属性能正确转换 
     *  void 
     */  
//    public void testGetDtoFromJsonObjStr1() {  
//        String json = "{'name':'get','dateAttr':'2009-11-12'}";  
//        Person ps = (Person) JsonUtils.getDtoFromJsonObjStr(json, Person.class);  
//        //print: get  
//        System.out.println(ps.getName());  
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//        //print: 2009-11-12  
//        System.out.println(sdf.format(ps.getDateAttr()));  
//    }  
//  
//    /** 
//     * 从json串转换成实体对象，并且实体集合属性存有另外实体Bean 
//     *  void 
//     */  
//    public void testGetDtoFromJsonObjStr3() {  
//        String json = "{'data':[{'name':'get'},{'name':'set'}]}";  
//        Map classMap = new HashMap();  
//        classMap.put("data", Person.class);  
//        MyBean myBean = (MyBean) JsonUtils.getDtoFromJsonObjStr(json, MyBean.class,  
//                classMap);  
//        //print: class comm.test.Person name =get         
//        System.out.println(myBean.getData().get(0).getClass() + " name ="  
//                + ((Person) myBean.getData().get(0)).getName());  
//        //print: class comm.test.Person name =set  
//        System.out.println(myBean.getData().get(1).getClass() + " name ="  
//                + ((Person) myBean.getData().get(1)).getName());  
//    }  
  
    /** 
     * 把一个json数组串转换成普通数组 
     *  void 
     */  
    public void testGetArrFromJsonArrStr() {  
        String json = "['get',1,true,null]";  
        Object[] objArr = JsonUtils.getArrFromJsonArrStr(json);  
        for (int i = 0; i < objArr.length; i++) {  
            System.out.println(objArr[i].getClass() + " " + objArr[i]);  
        }  
        /*  print: 
            class java.lang.String get 
            class java.lang.Integer 1 
            class java.lang.Boolean true 
            class net.sf.json.JSONNull null 
         */  
    }  
  
    /** 
     * 把一个json数组串转换成实体数组 
     *  void 
     */  
//    public void testGetDtoArrFromJsonArrStr1() {  
//        String json = "[{'name':'get'},{'name':'set'}]";  
//        Object[] objArr = JsonUtils.getDtoArrFromJsonArrStr(json, Person.class);  
//        for (int i = 0; i < objArr.length; i++) {  
//            System.out.println(objArr[i].getClass() + " name = "  
//                    + ((Person) objArr[i]).getName());  
//        }  
//        /*  print: 
//            class comm.test.Person name = get 
//            class comm.test.Person name = set 
//         */  
//    }  
  
    /** 
     * 把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean 
     *  void 
     */  
//    public void testGetDtoArrFromJsonArrStr2() {  
//        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";  
//        Map classMap = new HashMap();  
//        classMap.put("data", Person.class);  
//        Object[] objArr = JsonUtils.getDtoArrFromJsonArrStr(json, MyBean.class, classMap);  
//        for (int i = 0; i < objArr.length; i++) {  
//            System.out.println(((MyBean) objArr[i]).getData().get(0).getClass()  
//                    + " name = "  
//                    + ((Person) ((MyBean) objArr[i]).getData().get(0)).getName());  
//        }  
//        /*  print: 
//            class comm.test.Person name = get 
//            class comm.test.Person name = set 
//         */  
//    }  
  
    /** 
     * 把一个json数组串转换成存放普通类型元素的集合 
     *  void 
     */  
    public void testGetListFromJsonArrStr1() {  
        String json = "['get',1,true,null]";  
        List list = JsonUtils.getListFromJsonArrStr(json);  
        for (int i = 0; i < list.size(); i++) {  
            System.out.println(list.get(i).getClass() + " " + list.get(i));  
        }  
        /*  print: 
            class java.lang.String get 
            class java.lang.Integer 1 
            class java.lang.Boolean true 
            class net.sf.json.JSONNull null 
         */  
    }  
  
    /** 
     * 把一个json数组串转换成集合，且集合里存放的为实例Bean 
     *  void 
     */  
    public void testGetListFromJsonArrStr2() {  
        String json = "[{'name':'get'},{'name':'set'}]";  
        List list = JsonUtils.getListFromJsonArrStr(json, Person.class);  
        for (int i = 0; i < list.size(); i++) {  
            System.out.println(list.get(i).getClass() + " name = "  
                    + ((Person) list.get(i)).getName());  
        }  
        /*  print: 
            class comm.test.Person name = get 
            class comm.test.Person name = set 
         */  
    }  
  
    /** 
     * 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean 
     *  void 
     */  
//    public void testGetListFromJsonArrStr3() {  
//        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";  
//        Map classMap = new HashMap();  
//        classMap.put("data", Person.class);  
//        List list = JsonUtils.getListFromJsonArrStr(json, MyBean.class, classMap);  
//        for (int i = 0; i < list.size(); i++) {  
//            System.out.println(((MyBean) list.get(i)).getData().get(0).getClass()  
//                    + " name = "  
//                    + ((Person) ((MyBean) list.get(i)).getData().get(0)).getName());  
//        }  
//        /*  print: 
//            class comm.test.Person name = get 
//            class comm.test.Person name = set 
//         */  
//    }  
  
    /** 
     * 把json对象串转换成map对象 
     *  void 
     */  
    public void testGetMapFromJsonObjStr() {  
        String json = "{'name':'get','int':1,'double':1.1,'null':null}";  
        Map map = JsonUtils.getMapFromJsonObjStr(json);  
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {  
            System.out.println(map.get(iter.next()).getClass());  
        }  
        /*  print: 
            class java.lang.Double 
            class net.sf.json.JSONNull 
            class java.lang.Integer 
            class java.lang.String 
         */  
    }  
  
    /** 
     * 把json对象串转换成map对象，且map对象里存放的为其他实体Bean 
     *  void 
     */  
    public void testGetMapFromJsonObjStr2() {  
        String json = "{'data1':{'name':'get'},'data2':{'name':'set'}}";  
        Map map = JsonUtils.getMapFromJsonObjStr(json, Person.class);  
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {  
            String key = (String) iter.next();  
            System.out.println(map.get(key).getClass() + " name="  
                    + ((Person) map.get(key)).getName());  
        }  
        /*  print: 
            class comm.test.Person name=set 
            class comm.test.Person name=get 
         */  
    }  
  
    /** 
     * 把json对象串转换成map对象，且map对象里 
     * 存放的其他实体Bean还含有另外实体Bean 
     *  void 
     */  
//    public void testGetMapFromJsonObjStr3() {  
//        String json = "{'mybean':{'data':[{'name':'get'}]}}";  
//        Map classMap = new HashMap();  
//        classMap.put("data", Person.class);  
//        Map map = JsonUtils.getMapFromJsonObjStr(json, MyBean.class, classMap);  
//        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {  
//            String key = (String) iter.next();  
//            Object o = ((MyBean) map.get(key)).getData().get(0);  
//            System.out.println(o.getClass() + " name=" + ((Person) o).getName());  
//        }  
//        /*  print: 
//            class comm.test.Person name=get 
//         */  
//    }  
  
//    /** 
//     * 实体Bean转json串 
//     *  void 
//     */  
//    public void testgetJsonStr1() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        MyBean myBean = new MyBean();  
//        List list = new ArrayList();  
//        list.add(ps);  
//  
//        myBean.setData(list);  
//        //print: {"data":[{"dateAttr":"2009-09-12 07:24:54","name":"get"}]}  
//        System.out.println(JsonUtils.getJsonStr(myBean));  
//    }  
//  
//    /** 
//     * map转json串 
//     *  void 
//     */  
//    public void testgetJsonStr2() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Map map = new LinkedHashMap();  
//        map.put("person1", ps);  
//  
//        //print: {"person1":{"dateAttr":"2009-09-12 07:24:27","name":"get"}}  
//        System.out.println(JsonUtils.getJsonStr(map));  
//    }  
  
    /** 
     * 数组转json串 
     *  void 
     */  
//    public void testgetJsonStr3() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Person[] personArr = new Person[1];  
//        personArr[0] = ps;  
//        //print: [{"dateAttr":"2009-09-12 07:23:54","name":"get"}]  
//        System.out.println(JsonUtils.getJsonStr(personArr));  
//    }  
  
//    /** 
//     * list转json串 
//     *  void 
//     */  
//    public void testgetJsonStr4() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        List list = new ArrayList();  
//        list.add(ps);  
//  
//        //print: [{"dateAttr":"2009-09-12 07:22:49","name":"get"}]  
//        System.out.println(JsonUtils.getJsonStr(list));  
//    }  
//  
//    /** 
//     * set转json串 
//     *  void 
//     */  
//    public void testgetJsonStr5() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Set set = new LinkedHashSet();  
//        set.add(ps);  
//  
//        //print: [{"dateAttr":"2009-09-12 07:22:16","name":"get"}]  
//        System.out.println(JsonUtils.getJsonStr(set));  
//    }  
  
    /** 
     * json对象串转XML 
     *  void 
     */  
    public String GetXMLFromJson1(String jsonData) {  

    	//try {
    		System.out.println("jsonDatajsonData "+jsonData);
    		
    		String reg =" type=\"string\"| class=\"array\"| class=\"object\"";
    		String rep = "(?:"+reg+")";
    		String xmlstr = JsonUtils.getXMLFromObj(jsonData).replaceAll(rep, "").replaceAll("<o>", "<Data>").replaceAll("</o>", "</Data>");

    		return xmlstr;
//    	} catch (UnsupportedEncodingException e) {
//    		e.printStackTrace();
//    	} //解压后需进行转码,gbk; 
    	//return null;
    }  
  
    /** 
     * json数组串转XML 
     *  void 
     */  
    public void testGetXMLFromObj2() {  
        System.out.println(JsonUtils.getXMLFromObj("[1,2,3]"));  
    }  
//  
//    /** 
//     * java数组转XML 
//     *  void 
//     */  
//    public void testGetXMLFromObj3() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Person[] personArr = new Person[2];  
//        personArr[0] = ps;  
//  
//        System.out.println(JsonUtils.getXMLFromObj(personArr));  
//        /*  print: 
//            <?xml version="1.0" encoding="UTF-8"?> 
//            <a> 
//                <e class="object"> 
//                    <dateAttr type="string">2009-09-12 06:58:55</dateAttr> 
//                    <name type="string">get</name> 
//                </e> 
//            </a> 
//         */  
//    }  
//  
//    /** 
//     * list转XML 
//     *  void 
//     */  
//    public void testGetXMLFromObj4() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        List list = new ArrayList();  
//        list.add(ps);  
//  
//        System.out.println(JsonUtils.getXMLFromObj(list));  
//        /*  print: 
//            <?xml version="1.0" encoding="UTF-8"?> 
//            <a> 
//                <e class="object"> 
//                    <dateAttr type="string">2009-09-12 07:02:31</dateAttr> 
//                    <name type="string">get</name> 
//                </e> 
//            </a> 
//         */  
//    }  
//  
//    /** 
//     * set转XML 
//     * void 
//     */  
//    public void testGetXMLFromObj5() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Set set = new LinkedHashSet();  
//        set.add(ps);  
//  
//        System.out.println(JsonUtils.getXMLFromObj(set));  
//        /*  print: 
//            <?xml version="1.0" encoding="UTF-8"?> 
//            <a> 
//                <e class="object"> 
//                    <dateAttr type="string">2009-09-12 07:04:38</dateAttr> 
//                    <name type="string">get</name> 
//                </e> 
//            </a> 
//         */  
//    }  
//  
//    /** 
//     * map转XML 
//     *  void 
//     */  
//    public void testGetXMLFromObj6() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        Map map = new HashMap();  
//        map.put("person1", ps);  
//  
//        System.out.println(JsonUtils.getXMLFromObj(map));  
//        /*  print: 
//            <?xml version="1.0" encoding="UTF-8"?> 
//            <o> 
//                <person1 class="object"> 
//                    <dateAttr type="string">2009-09-12 07:08:35</dateAttr> 
//                    <name type="string">get</name> 
//                </person1> 
//            </o> 
//         */  
//    }  
//  
//    /** 
//     * 实体Bean转XML 
//     *  void 
//     */  
//    public void testGetXMLFromObj7() {  
//        Person ps = new Person();  
//        ps.setDateAttr(new Date());  
//        ps.setName("get");  
//        System.out.println(JsonUtils.getXMLFromObj(ps));  
//        /*  print: 
//            <?xml version="1.0" encoding="UTF-8"?> 
//            <o> 
//                <dateAttr type="string">2009-09-12 07:13:02</dateAttr> 
//                <name type="string">get</name> 
//            </o> 
//         */  
//    }  
  
    /** 
     * 从XML对象串转json串 
     *  void 
     */  
    public String GetJsonStrFromXML1(String xmlData) {  
//        String xml = "<o><dateAttr type='string'>2009-09-12 07:13:02</dateAttr>"  
//                + "<name type='string'>get</name></o>";  
        //print: {"dateAttr":"2009-09-12 07:13:02","name":"get"}  
        //System.out.println(JsonUtils.getJsonStrFromXML(xmlData));  
        return JsonUtils.getJsonStrFromXML(xmlData);
    }  
  
    /** 
     * 从XML数组串转json串 
     *  void 
     */  
    public void GetJsonStrFromXML2(String xmlData) {  
//        String xml = "<a class='array'><e class='object'><dateAttr type='string'>2009-09-12 07:04:38</dateAttr>"  
//                + "<name type='string'>get</name></e></a>";  
        //print: [{"dateAttr":"2009-09-12 07:04:38","name":"get"}]  
        System.out.println(JsonUtils.getJsonStrFromXML(xmlData));  
    }  
    //String datas = "<Data><IntfCode>FinishResInfoChange</IntfCode><Params><SysParam><ServiceNo>56789876</ServiceNo><RegionCode>731</RegionCode></SysParam><ResInfo><RES_ID>811002</RES_ID><RES_TYPE>Box_LineSeq</RES_TYPE><RES_CODE>G</RES_CODE><RES_NAME>10</RES_NAME></ResInfo></Params></Data>";
//    static String datas="	<root><msgHead>										"+
//    "				<fromSystem>JSIOM@js.cn</fromSystem>							"+
//    "				<toSystem>IRES@js.cn</toSystem>							"+
//    "				<createDate>23</createDate>							"+
//    "			</msgHead>								"+
//    "			<msgBody>								"+
//    "				<orderInfo>							"+
//    "					<orderId>34</orderId>						"+
//    "					<orderCode>45</orderCode>						"+
//    "					<orderTitle>56</orderTitle>						"+
//    "					<orderPriority>67</orderPriority>						"+
//    "					<limitDate>we</limitDate>						"+
//    "					<acceptDate>23</acceptDate>						"+
//    "					<acceptStaff>ewe</acceptStaff>						"+
//    "					<acceptDepartment>3ee</acceptDepartment>						"+
//    "					<productName>ew</productName>						"+
//    "					<actionName>dds</actionName>						"+
//    "					<accessNumber>re</accessNumber>						"+
//    "				</orderInfo>							"+
//    "				<custInfo>							"+
//    "					<custName>dsf</custName>						"+
//    "					<custCode>dsf</custCode>						"+
//    "					<custType>sg</custType>						"+
//    "					<custContact>dfsd</custContact>						"+
//    "					<custAddress>dsf</custAddress>						"+
//    "					<custManagerName>r32</custManagerName>						"+
//    "					<custManagerTel>df</custManagerTel>						"+
//    "				</custInfo>							"+
//    "				<serviceInfo>							"+
//    "					<description>fsd</description>						"+
//    "					<flowRequire>df</flowRequire>						"+
//    "					<networkLevel>g3</networkLevel>						"+
//    "					<maintenanceLevel>dsfdsf</maintenanceLevel>						"+
//    "					<terminalType>324</terminalType>						"+
//    "				</serviceInfo>							"+
//    "				<resInfo>dsfsdf</resInfo>							"+
//    "				<workOrders>							"+
//    "					<workOrderInfo>						"+
//    "						<workOrderId>sdfds</workOrderId>					"+
//    "						<workOrderCode>sdf</workOrderCode>					"+
//    "						<workOrderLevel>dsf</workOrderLevel>					"+
//    "						<tacheName>sdf</tacheName>					"+
//    "						<partyType>wer</partyType>					"+
//    "						<partyId>sdf</partyId>					"+
//    "						<partyName>sdf</partyName>					"+
//    "						<createDate>sdf</createDate>					"+
//    "						<limitDate>wre</limitDate>					"+
//    "					</workOrderInfo>						"+
//    "				</workOrders>							"+
//    "			</msgBody>								"+
//    "		</root>        								";	

//    static String datas = "	<Data>						"+
//    "		<IntfCode> FinishResInfoChange</IntfCode>					"+
//    "		<OrderNo> 20110101</OrderNo>					"+
//    "		<Params>					"+
//    "			<ServiceNo>56789876</ServiceNo>				"+
//    "			<RegionCode>731</RegionCode>				"+
//    "			<ResInfo>				"+
//    "				<RES_ID>811002</RES_ID>			"+
//    "				<RES_TYPE> Box_LineSeq </RES_TYPE>			"+
//    "				<RES_CODE>G</RES_CODE>			"+
//    "				<RES_NAME> 10</RES_NAME>			"+
//    "			</ResInfo>				"+
//    "			<ResInfo>				"+
//    "				<RES_ID>811002</RES_ID>			"+
//    "				<RES_TYPE> Box_LineSeq </RES_TYPE>			"+
//    "				<RES_CODE>G</RES_CODE>			"+
//    "				<RES_NAME> 10</RES_NAME>			"+
//    "			</ResInfo>				"+
//    "		</Params>					"+
//    "	</Data>						";

    static String datas = "<?xml version=\"1.0\" encoding=\"GBK\"?><Data><Params><JobId>4213</JobId><PageNum>1</PageNum><PageSize>2</PageSize><ProductNbr/><SysParam/><UseName>zhangkai</UseName></Params><QueryMethod>queryWorkOrderForEBiz</QueryMethod></Data>";
    public static void main(String[] args) {  
    	XmlToJsonUtils test = new XmlToJsonUtils();
    
    	String ndatas = "";
    	List l = new ArrayList();
    	Map m = new HashMap();
    	m.put("name1", "<Params>|</Params>");
    	m.put("mname1", "");
    	Map m2 = new HashMap();
    	m2.put("name1", "ResInfo");
    	m2.put("mname1", "listdata");
    	l.add(m);
    	l.add(m2);
    	
    
    	
    	String s = "(?:"+m.get("name1")+")";
        ndatas = datas.replaceAll("(?:<Params>|</Params>)", "").replaceAll("ResInfo", "listdata");
    	//ndatas = datas.replaceAll(s, m.get("mname1")+"").replaceAll("(?:ResInfo)", "listdata");
   
    	
    	System.out.println("ndatas --> "+ndatas);
    	String json = test.GetJsonStrFromXML1(ndatas);  
	
    	
//    	 JSONObject jsondata = new JSONObject();
// 		jsondata.put("listdata", json);
 		
// 		JSONObject resultobj = new JSONObject();
// 		resultobj.put("result", "000");
// 		resultobj.put("body", json);

//    	System.out.println(resultobj.toString()); 
    	
//    	String reg =" type=\"string\"| class=\"array\"| class=\"object\"";
//    	String rp = "(?:"+reg+")";
//    	System.out.println(rp);
    	String newxml = test.GetXMLFromJson1(json);

    	System.out.println(newxml); 
    	
    }  
}  
