package com.ztesoft.mobile.systemMobMgr.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;


public class Privilege implements Serializable {

		  private String privCode;
		  private String privName;
		  private Long privClassId;
		  private String privType;
		  private String comments;
		  private String state;

		  

		  public String getPrivCode() {
			return privCode;
		}

		public void setPrivCode(String privCode) {
			this.privCode = privCode;
		}

		public String getPrivName() {
			return privName;
		}

		public void setPrivName(String privName) {
			this.privName = privName;
		}

	

		public Long getPrivClassId() {
			return privClassId;
		}

		public void setPrivClassId(Long privClassId) {
			this.privClassId = privClassId;
		}

		public String getPrivType() {
			return privType;
		}

		public void setPrivType(String privType) {
			this.privType = privType;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public static List convertDtoArrayToMapList(Privilege[] _dots) {
		    if (_dots == null || _dots.length == 0) {
		      return null;
		    }
		    List _mapList = new ArrayList();
		    Map _map = null;
		    for (int i = 0; i < _dots.length; i++) {
		      _map = new HashMap();
		      _map.put("privCode", _dots[i].getPrivCode());
		      _map.put("privName", _dots[i].getPrivName());
		      _map.put("privClassId", _dots[i].getPrivClassId());
		      _map.put("privType", _dots[i].getPrivType());
		      _map.put("state", _dots[i].getState());
		      _mapList.add(_map);
		    }
		    return _mapList;
		  }

		  public static Privilege[] convertMapListToDtoArray(List _list) {
		    if (_list == null || _list.size() == 0) {
		      return null;
		    }
		    Privilege[] _dots = new Privilege[_list.size()];
		    Map _map = null;
		    for (int i = 0; i < _list.size(); i++) {
		      _map = (Map) _list.get(i);
		      _dots[i] = new Privilege();
		      _dots[i].setPrivCode(MapUtils.getString(_map, "privCode"));
		      _dots[i].setPrivName(MapUtils.getString(_map, "privName"));
		      _dots[i].setPrivClassId(MapUtils.getLong(_map, "privClassId"));
		      _dots[i].setPrivType(MapUtils.getString(_map, "privType"));
		      _dots[i].setState(MapUtils.getString(_map, "state"));
		    }

		    return _dots;
		  }
}
