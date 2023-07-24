package com.ztesoft.android.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class CeshiDataDAOImpl extends BaseDAOImpl implements CeshiDataDAO{

	public String getData(String params) throws DataAccessException { 
		System.out.println("params" + params);
		JSONObject jsonObject = new JSONObject().fromObject(params);
		String name3 = jsonObject.get("createDate")==null?"-1":jsonObject.getString("createDate");
		String pot = jsonObject.get("pot")==null?"-1":jsonObject.getString("pot");
		String type = jsonObject.get("type")==null?"-1":jsonObject.getString("type");
		String method = jsonObject.get("method")==null?"sel":jsonObject.getString("method");
		String resultjson = "[]";
		StringBuffer sql = new StringBuffer();
		if(method.equals("sel")){
			
			if(type.equals("-1")){//资源查询 MOBILE_TEST_INFO
				sql.append(" SELECT NAME1 AS name1,NAME2 AS name2,NAME3 AS name3,NAME4 AS name4,NAME5 AS name19,NAME6 AS name20,NAME7 AS name21,NAME8 AS name22,NAME9 AS name23,NAME10 AS name24 FROM MOBILE_TEST_INFO where 1=1 ");
				if(!name3.equals("-1")&&!name3.equals("")){
					sql.append(" and NAME3 LIKE '%"+name3+"'%");
				}
			}else if(type.equals("2")){  //线路查询
				sql.append(" SELECT NAME1 AS name1,NAME2 AS name2,NAME3 AS name3,NAME4 AS name4,NAME5 AS name5 FROM MOBILE_TEST_ROUND_INFO where 1=1 ");

			}else if(type.equals("3")){  //端口查询
				sql.append(" SELECT a.NAME1 AS name1,a.NAME2 AS name2,a.NAME3 AS name3,a.NAME4 AS name4,a.NAME5 AS name5 FROM MOBILE_TEST_POT_INFO a where a.name1 not in(select c.name1 from MOBILE_TEST_POT_INFO c ,MOBILE_TEST_POT_INFO2 b where c.name1=b.name2 and b.name1 = '"+name3+"')");

			}else if(type.equals("4")){  //已分配端口查询
				sql.append(" SELECT a.NAME1 AS name1,a.NAME2 AS name2,a.NAME3 AS name3,a.NAME4 AS name4,a.NAME5 AS name5 FROM MOBILE_TEST_POT_INFO a ,MOBILE_TEST_POT_INFO2 b where a.name1=b.name2 and b.name1 = '"+name3+"'");
			}else if(type.equals("7")){  //查待办
				sql.append(" SELECT NAME1 AS name1,NAME2 AS name2,NAME3 AS name3,NAME4 AS name4,NAME5 AS name19,NAME6 AS name20,NAME7 AS name21,NAME8 AS name22,NAME9 AS name23,NAME10 AS name24 FROM MOBILE_TEST_WORK_INFO where 1=1 and NAME8='10A' ");
				if(!name3.equals("-1")&&!name3.equals("")){
					sql.append(" and NAME1 LIKE '%"+name3+"'%");
				}
			}
			System.out.println("dddd" + sql);
			resultjson = JsonUtil.getJsonByList(dynamicQueryListByMap(sql.toString(), null, null));

		}else if(method.equals("del")) {
			if(type.equals("5")){
				
				sql.append("delete from MOBILE_TEST_POT_INFO2 where NAME1='"+name3+"'");
				dynamicUpdateBySql(sql.toString());
			}else if(type.equals("8")){
				StringBuffer sql3= new StringBuffer();
				sql.append("update MOBILE_TEST_WORK_INFO set   NAME8='10B' where NAME1='"+name3+"'");
				dynamicUpdateBySql(sql3.toString());
			}else if(type.equals("9")){
				StringBuffer sql2 = new StringBuffer();
				sql2.append("update MOBILE_TEST_WORK_INFO set NAME8='10C' where NAME1='"+name3+"'");
				dynamicUpdateBySql(sql2.toString());
			}
			
		}else if(method.equals("add")){
			 if(type.equals("6")){
				sql.append("delete from MOBILE_TEST_POT_INFO2 where NAME1='"+name3+"'");
				dynamicUpdateBySql(sql.toString());
				
				StringBuffer sql2 = new StringBuffer();				
				Long nextId = getPKFromMem("MOBILE_TEST_POT_INFO2", 9);
				sql2.append("insert INTO MOBILE_TEST_POT_INFO2 (ID,NAME1,NAME2) values("+nextId+",'"+name3+"','"+pot+"')");
				dynamicUpdateBySql(sql2.toString());
			}
		}
		
		return resultjson;
		
		//return "\"body\":{\"listdata\":[{\"seqId\":\"1\",\"filedLable\":\"创建时间\",\"filedId\":221,\"filedType\":\"text\",\"checkedData\":null,\"filedName\":\"createDate\",\"isRequired\":\"Y\",\"indexId\":\"1\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"},{\"seqId\":\"2\",\"filedLable\":\"查询\",\"filedId\":222,\"filedType\":\"button\",\"checkedData\":null,\"filedName\":\"issuerName\",\"isRequired\":\"N\",\"indexId\":\"1\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"},{\"seqId\":\"3\",\"filedLable\":\"类型\",\"filedId\":262,\"filedType\":\"text\",\"checkedData\":null,\"filedName\":\"newsType\",\"isRequired\":\"Y\",\"indexId\":\"2\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"2\"}],\"listdata2\":[{\"seqId\":\"1\",\"filedLable\":\"创建时间\",\"filedId\":221,\"filedType\":\"button\",\"checkedData\":null,\"filedName\":\"createDate\",\"isRequired\":\"Y\",\"indexId\":\"1\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"},{\"seqId\":\"2\",\"filedLable\":\"发布人\",\"filedId\":222,\"filedType\":\"button\",\"checkedData\":null,\"filedName\":\"issuerName\",\"isRequired\":\"N\",\"indexId\":\"1\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"}],\"listdata3\":[{\"seqId\":\"1\",\"filedLable\":\"创建时间2\",\"filedId\":221,\"filedType\":\"textarea\",\"checkedData\":null,\"filedName\":\"createDate2\",\"isRequired\":\"Y\",\"indexId\":\"1\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"},{\"seqId\":\"2\",\"filedLable\":\"发布人2\",\"filedId\":222,\"filedType\":\"text\",\"checkedData\":null,\"filedName\":\"issuerName2\",\"isRequired\":\"N\",\"indexId\":\"2\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"},{\"seqId\":\"3\",\"filedLable\":\"类型2\",\"filedId\":262,\"filedType\":\"text\",\"checkedData\":null,\"filedName\":\"newsType2\",\"isRequired\":\"Y\",\"indexId\":\"3\",\"selectData\":null,\"isPassValue\":\"1\",\"isDisplay\":\"1\"}]}";
	}

	public void updateData(String params) throws DataAccessException {
		System.out.println("params" + params);
		JSONObject jsonObject = new JSONObject().fromObject(params);
		String name3 = jsonObject.get("createDate")==null?"-1":jsonObject.getString("createDate");
		String type = jsonObject.get("type")==null?"-1":jsonObject.getString("type");
	
		StringBuffer sql = new StringBuffer();
		sql.append("update MOBILE_TEST_POT_INFO set NAME3=");
		
	}
	
}

