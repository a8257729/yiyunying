<!DOCTYPE html>
<%@ page contentType="text/html;charset= gb2312"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>百度地图的Hello, World</title>

		<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js">
</script>
		<script language="javascript" src="../public/script/FormExt.js">
</script>
		<script language="javascript" src="/MOBILE/public/script/helper.js">
</script>
		<script language="javascript"
			src="/MOBILE/public/script/xworkAction.js">
</script>
	</head>
	<body>
		<div style="width: 920px; height: 450px; border: 1px solid gray"
			id="WirelessCheckMap"></div>
		<table>
			<tr>
				<td>
					地址
				</td>
				<td>
					<input type="text" id="deviceAddress" />
				</td>
				<td>
					<input type="checkbox" name="deviceTypes" value="1" onclick="oper.showbySelect();"/>
					模板路径
					<input type="checkbox" name="deviceTypes" value="2" onclick="oper.showbySelect();"/>
					巡检实例
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="查询" onclick="oper.searchWirelessCheck();" />
				</td>
			</tr>
		</table>


	</body>
</html>
<script type="text/javascript">
var oper = new Oper();
var markers = [];
var paths = [];
var map = new BMap.Map("WirelessCheckMap"); // 创建Map实例
var point = new BMap.Point(113.266897, 23.135782); // 创建点坐标
//debugger;
map.centerAndZoom(point, 15); // 初始化地图,设置中心点坐标和地图级别。
var myCity = new BMap.LocalCity();//根据IP地位地图
myCity.get(oper.myFun);
/*var marker = new BMap.Marker(new BMap.Point(0,0));
 map.addEventListener("click", function (e) {
 alert( e.point.lng + ", " + e.point.lat);
 marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));  // 创建标注
 map.addOverlay(marker);              // 将标注添加到地图中
 var label = new BMap.Label("我是文字标注哦",{offset:new BMap.Size(20,-10)});
 marker.setLabel(label);
 });
 marker.addEventListener("click", function(){   
 alert("您点击了标注");   
 });  */
oper.init();

map.addEventListener("tilesloaded", oper.showMakers);
map.addEventListener("zoomend", oper.showMakers);
map.addEventListener("moveend", oper.showMakers);
function Oper() {
	
	var retMapDetail = new Object();
	var retMapInst = new Object();
	
	
	this.init = function() {
		var contextMenu = new BMap.ContextMenu();
		//初始化右键菜单
		var txtMenuItem = [ {
			text : '在此添加标注',
			callback : function(p) {
				//增加设备标注
				var labelContent = window.showModalDialog('/MOBILE/map/deviceCreate.jsp', null, 'dialogWidth:400px;dialogHeight:300px');
				if (labelContent) {
					var deviceCode = labelContent.deviceCode;
					var deviceName = labelContent.deviceName;
					var deviceTypeValue = labelContent.deviceType;
					var deviceType = labelContent.deviceTypeCode;
					var deviceAddress = labelContent.deviceAddress;
					var labelText = deviceTypeValue + "：" + deviceName + "<br>"
							+ deviceAddress;
					alert(labelText);
					var label = new BMap.Label(labelText, {
						offset : new BMap.Size(20, -10)
					});
					var marker = new BMap.Marker(p);
					var px = map.pointToPixel(p);
					//alert(p.lng+ ", " + p.lat);
					var objq = new Object();
					objq.type = "add";
					objq.lng = p.lng;
					objq.lat = p.lat;
					objq.deviceCode = deviceCode;
					objq.deviceName = deviceName;
					objq.deviceType = deviceType;
					objq.deviceAddress = deviceAddress;
					//保存设备与标注的关系
					invokeAction("/mobsystem/MapOfDeviceInfoAciton", objq);
					oper.addMarker(p, labelText);
					oper.showMakers();
					/*map.addOverlay(marker);
					marker.setLabel(label);
					//增加点击标注事件。
					marker.addEventListener("click", function(){   
						alert("您点击了标注");   
					});*/
				}
			}
		} ];
		for ( var i = 0; i < txtMenuItem.length; i++) {
			contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,
					txtMenuItem[i].callback, 100));
		}
		;
		map.centerAndZoom(point, 15);
		map.addContextMenu(contextMenu);

		map.enableScrollWheelZoom(); // 启用滚轮放大缩小。
		map.enableKeyboard();
	}

	this.searchWirelessCheck = function() {
		var objq = new Object();
		markers = [];
		paths = [];
		var deviceTypes = "";
		var deviceTyeList = document.getElementsByName("deviceTypes");
		for ( var i = 0; i < deviceTyeList.length; i++) {
			if (deviceTyeList[i].checked) {
				if (deviceTypes == "") {
					deviceTypes = deviceTyeList[i].value;
				} else {
					deviceTypes = deviceTypes + "," + deviceTyeList[i].value;
				}
			}
		}
		//alert(deviceTypes);
		objq.flag = "searchWirelessCheck";
		objq.wirelessCheckTemplateId = "0";
		map.clearOverlays();//移除所有标注
		retMapDetail = invokeAction("/mobsystem/WirelessCheckDetailManaAction", objq);
		//debugger;
		oper.addPaths(retMapDetail,"blue");
		oper.addMarkers(retMapDetail);
		
		objq = new Object;
		objq.flag = "searchWirelessCheck";
		objq.wirelessCheckDetailId = "0";
		objq.planTaskId = "0";
		retMapInst = invokeAction("/mobsystem/WirelessCheckInstManaAction", objq);
		oper.addPaths(retMapInst,"green");
		oper.addMarkers(retMapInst);
		oper.showMakers();
	}
	//添加路线
	this.addPaths = function(retMap, color){
		for ( var j = 1; j < retMap.length; j++) {
			var point1 = new BMap.Point(retMap[j-1].coordinateY, retMap[j-1].coordinateX);
			var point2 = new BMap.Point(retMap[j].coordinateY, retMap[j].coordinateX);
			var polyline = new BMap.Polyline([
			  	point1,
			  	point2
			], 
			{
				strokeColor:color, 
				strokeWeight:6, 
				strokeOpacity:0.5
			});
			paths[paths.length] = polyline;
			map.addOverlay(polyline);
		}
	}
	//把查询到的标注保存到makers数组中
	this.addMarkers = function(retMap) {
		for ( var i = 0; i < retMap.length; i++) {
			var point = new BMap.Point(retMap[i].coordinateY, retMap[i].coordinateX);
			if (i == 0) {
				map.centerAndZoom(point, 15);
			}
			var labelText = retMap[i].comments;
			var marker = new BMap.Marker(point);
			marker.enableMassClear();//允许覆盖物在map.clearOverlays方法中被清除。
			var infoWindow = new BMap.InfoWindow(labelText, opts); // 创建信息窗口对象
			marker.addEventListener("click", function() {
				this.openInfoWindow(infoWindow);
			});
			markers[markers.length] = marker;
		}
		
	}
	this.showbySelect = function(){
		//debugger;
		map.clearOverlays();//移除所有标注		
		markers = [];
		paths = [];
		var deviceTypes = "";
		var deviceTyeList = document.getElementsByName("deviceTypes");
		for ( var i = 0; i < deviceTyeList.length; i++) {
			if (deviceTyeList[i].checked) {
				switch(deviceTyeList[i].value){
				case "1":
					oper.addPaths(retMapDetail,"blue");
					oper.addMarkers(retMapDetail);
					break;
				case "2":
					oper.addPaths(retMapInst,"green");
					oper.addMarkers(retMapInst);
					break;
				}
			}			
		}
		oper.showMakers();
	}
	
	this.showMakers = function() {
		for ( var i = 0; i < markers.length; i++) {
			var result = BMapLib.GeoUtils.isPointInRect(markers[i].point, map.getBounds());
			if (result == true) { //在可视范围内则增加相应标注
				map.addOverlay(markers[i]);
			} else { //不在可视范围内则删除相应标注
				map.removeOverlay(markers[i]);
			}
		}
	}
	var opts = {
		width : 250, // 信息窗口宽度
		height : 100, // 信息窗口高度
		title : "设备信息" // 信息窗口标题
	}
	this.myFun = function(result) {
		var cityName = result.name;
		map.setCenter(cityName);
	}
	

}
</script>