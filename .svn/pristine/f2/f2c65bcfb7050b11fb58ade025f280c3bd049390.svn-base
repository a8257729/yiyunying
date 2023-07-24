package com.ztesoft.eoms.common.helper;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import org.apache.commons.collections.MapUtils;

public class CollectionHelper {

	/**
	 * 本方法的功能是把list里面map对象里key值拼装成字符串返回
	 * 
	 * @param list1
	 *            [map1{key=value,key1=value1,key3=value3,key4=value4},
	 *            map2{key=value,key1=value1,key3=value3,key4=value4}]
	 * @param key
	 *            key
	 * @param defaultValue
	 * @return value,value
	 */
	public static String getFitIdstr(List list1, Object key, String defaultValue) {
		if (list1 == null || list1.isEmpty()) {
			return defaultValue;
		}
		StringBuffer idStr = new StringBuffer();
		for (int i = 0; i < list1.size(); i++) {// 遍历
			String temp = MapUtils.getString((Map) list1.get(i), key);
			if (temp != null && temp.length() > 0) {
				idStr.append(temp.trim());
				if (i < list1.size()) {
					idStr.append(",");
				}
			}
		}
		if (idStr.length() > 0 && idStr.charAt(idStr.length() - 1) == ',') {// 把字符串最后一个字符','去掉
			idStr.deleteCharAt(idStr.length() - 1);
		}
		list1 = null;
		return idStr.length() > 0 ? idStr.toString() : defaultValue;
	}

	/**
	 * 把两个list合并成一个笛卡尔集
	 * 
	 * <example>如下例子里面crossJoinKey="id"
	 * 
	 * @param crossList
	 *            [{id=100,staffId=2664,staffFactor=50,staffName=eoms_test},
	 *            {id=100,staffId=2667,staffFactor=50,staffName=good} ]
	 * @param beCrossList
	 *            [{id=100,manHour=1.5,stdTaskId=2563},
	 *            {id=100,manHour=2.5,stdTaskId=2556} ]
	 * @return [{id=100,staffId=2664,staffFactor=50,staffName=eoms_test,manHour=1.5,stdTaskId=2563},
	 *         {id=100,staffId=2664,staffFactor=50,staffName=eoms_test,manHour=2.5,stdTaskId=2556},
	 *         {id=100,staffId=2667,staffFactor=50,staffName=good,manHour=1.5,stdTaskId=2563},
	 *         {id=100,staffId=2667,staffFactor=50,staffName=good,manHour=2.5,stdTaskId=2556} ]
	 */
	public static List crossJoin(List crossList, List beCrossList,
			Object crossJoinKey) {
		if (crossList == null || crossList.isEmpty()) {
			return beCrossList;
		}
		if (beCrossList == null || beCrossList.isEmpty()) {
			return crossList;
		}
		// 首先把beCrossList转换成如下格式的,以crossJoinKey对应的键值为key,构造一个map对象
		// {
		// 100=[{id=100,manHour=1.5,stdTaskId=2563},{id=100,manHour=2.5,stdTaskId=2556}],
		// 101=[{id=101,manHour=3.5,stdTaskId=2567},{id=101,manHour=8.5,stdTaskId=2559}]
		// }
		Map crossMap = new HashMap();
		for (int i = 0; i < beCrossList.size(); i++) {
			Object obj = MapUtils.getObject((Map) beCrossList.get(i),
					crossJoinKey);
			if (crossMap.containsKey(obj)) {
				Collection col = (Collection) MapUtils.getObject(crossMap, obj);
				col.add(beCrossList.get(i));
			} else if (obj != null) {
				Collection col = new ArrayList();
				col.add(beCrossList.get(i));
				crossMap.put(obj, col);
			}
		}
		 //System.out.println("crossMap=" + crossMap);
		// 遍历crossList，把crossMap里面的相应的对象集放入
		List resultList = new LinkedList();
		for (int j = 0; j < crossList.size(); j++) {
			Map para = (Map) crossList.get(j);
			Object obj = MapUtils.getObject(para, crossJoinKey);
			if (obj != null && crossMap.containsKey(obj)) {
				Collection col = (Collection) MapUtils.getObject(crossMap, obj);
				if (col != null && col.size() > 0) {
					Iterator it = col.iterator();
					while (it.hasNext()) {
						// 这种写法会有问题的，因为Map对象这样指向的，只是一个链接，
						// 如果改变temp的值，会直接影响it所对应的Collection里的值
						// Map temp=(Map) it.next();
						// temp.putAll(para);
						Map temp = new HashMap();
						temp.putAll((Map) it.next());
						temp.putAll(para);
						resultList.add(temp);
						temp = null;
					}
				}
			}
		}
		crossMap = null;
		beCrossList = null;
		crossList = null;

		return resultList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * 测试crossJoin
		 */
		List crossList = new LinkedList();
		Map para1 = new HashMap(); //
		// id=100,staffId=2664,staffFactor=50,staffName=eoms_test
		para1.put("id", new Integer(100));
		para1.put("staffId", new Integer(2664));
		para1.put("staffFactor", new Float(50.0));
		para1.put("staffName", "eoms_test");
		crossList.add(para1);
		Map para2 = new HashMap(); // id=100,staffId=2667,staffFactor=50,staffName=good
		para2.put("id", new Integer(100));
		para2.put("staffId", new Integer(2667));
		para2.put("staffFactor", new Float(50.0));
		para2.put("staffName", "good");
		crossList.add(para2);

		List beCrossList = new LinkedList(); // {id=100,manHour=1.5,stdTaskId=2563},
		Map para3 = new HashMap();
		para3.put("id", new Integer(101));
		para3.put("manHour", new Float(1.5));
		para3.put("stdTaskId", new Integer(2563));
		beCrossList.add(para3); // {id=100,manHour=2.5,stdTaskId=2556}
		Map para4 = new HashMap();
		para4.put("id", new Integer(101));
		para4.put("manHour", new Float(2.5));
		para4.put("stdTaskId", new Integer(2556));
		beCrossList.add(para4);

		List result = crossJoin(crossList, beCrossList, "id");
		System.out.println(result);

		/*
		 * 测试结果 [{staffId=2664, manHour=1.5, stdTaskId=2563, staffFactor=50.0,
		 * id=100, staffName=eoms_test}, {staffId=2664, manHour=2.5,
		 * stdTaskId=2556, staffFactor=50.0, id=100, staffName=eoms_test},
		 * {staffId=2667, manHour=1.5, stdTaskId=2563, staffFactor=50.0, id=100,
		 * staffName=good}, {staffId=2667, manHour=2.5, stdTaskId=2556,
		 * staffFactor=50.0, id=100, staffName=good}]
		 */

		/*
		 * 测试getFitIds();
		 * 
		 * List crossList = new LinkedList(); Map para1 = new HashMap(); // //
		 * id=100,staffId=2664,staffFactor=50,staffName=eoms_test
		 * para1.put("id", new Integer(100)); para1.put("staffId", new
		 * Integer(2664));
		 * 
		 * crossList.add(para1); Map para2 = new HashMap(); //
		 * id=100,staffId=2667,staffFactor=50,staffName=good para2.put("id", new
		 * Integer(102)); para2.put("staffId", new Integer(2667));
		 * 
		 * crossList.add(para2); Map para3 = new HashMap(); //
		 * id=100,staffId=2667,staffFactor=50,staffName=good para3.put("id", new
		 * Integer(122)); para3.put("staffId", new Integer(2667));
		 * crossList.add(para3);
		 * 
		 * System.out.println(getFitIdstr(crossList, "id", null));
		 */
	}

}
