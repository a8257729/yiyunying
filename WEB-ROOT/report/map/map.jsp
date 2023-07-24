<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <title>我的百度地图</title>
    <style type="text/css">
			body,
			html,
			#allmap {
				width: 100%;
				height: 100%;
				overflow: hidden;
				margin: 0;
				font-family: "微软雅黑";
			}
			
			#up-map-div {
				width: 320px;
				height: auto;
				bottom: 0px;
				left: 0px;
				position: absolute;
				z-index: 9999;
				border: 1px solid blue;
				background-color: #FFFFFF;
			}
			
			#button {
				width: 320px;
				height: autopx;
				top: 0px;
				left: 0px;
				position: absolute;
				z-index: 9999;
				border: 1px solid red;
				background-color: sandybrown;
				text-align: center;
			}
			
			#search {
				width: 120px;
			}
		</style>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=jkm4QmpXpHracFtl2dSSzaVx7RN7FO3U"></script>
		<script type="text/javascript" src="jquery-3.2.1.min.js"></script>
</head>
<body>
	<div id="allmap">
		</div>
		<div id="button">
			<input type="button" id="bt" value="隐藏" />
		</div>
		<div id="up-map-div" style="overflow:scroll;width:auto;height:150px;">
			<div>
				<input type="text" name="content" id="content" value="" />
				<input type="button" name="search" id="search" value="搜索" onclick="test()" />
			</div>
			<table border="1" cellspacing="0px" cellpadding="0px" style="width:100%;height:100%;">
				<tr>
					<th>地址</th>
					<th>坐标点X</th>
					<th>坐标点Y</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			 
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑9栋701</td>
					<td>112.948541</td>
					<td>28.237526</td>
					<td>未上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
	
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑</td>
					<td>112.948541</td>
					<td>28.235521</td>
					<td>已上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
				<tr>
					<td>湖南省长沙市雨花区金域华府</td>
					<td>112.948541</td>
					<td>28.235424</td>
					<td>未上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑9栋701</td>
					<td>112.949161</td>
					<td>28.235426</td>
					<td>已上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑9栋701</td>
					<td>112.952222</td>
					<td>28.237685</td>
					<td>已上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑9栋701</td>
					<td>112.945646</td>
					<td>28.238608</td>
					<td>已上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
				<tr>
					<td>湖南省长沙市雨花区湘核佳苑9栋701</td>
					<td>111.377959</td>
					<td>28.202419</td>
					<td>已上图</td>
					<td><input type="button" name="upimg" class="upimg" value="上图" /></td>
				</tr>
			</table>
			<input type="button" id="firstPage" value="首页" />
			<input type="button" id="upPage" value="上一页" />
			<a href="" id="currentPage">1</a>/
			<a href="" id="totalPage">4</a>
			<input type="button" id="lowPage" value="下一页" />
			<input type="button" id="endPage" value="尾页" />
		</div>
</body>
<script type="text/javascript">
		//创建和初始化地图函数：
		function initMap() {
			createMap(); //创建地图
			setMapEvent(); //设置地图事件
			addMapControl(); //向地图添加控件
		}initMap(); //创建和初始化地图

		//创建地图函数：
		function createMap() {
			var map = new BMap.Map("allmap"); //在百度地图容器中创建一个地图
			window.map = map; //将map变量存储在全局

			var x = 112.945333;
			var y = 28.233971;
			var point = new BMap.Point(x, y);
			//必须调用此方法对地图进行初始化，否则不能进行任何操作

			map.centerAndZoom(point, 16);
			var marker = new BMap.Marker(point); // 创建标注  
			map.addOverlay(marker); // 将标注添加到地图中*/

			//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			marker.enableDragging() //覆盖物可以拖拽

			//隐藏表格中坐标列
			$('tr th:nth-child(2)').hide();
			$('tr th:nth-child(3)').hide();
			$('tr td:nth-child(2)').hide();
			$('tr td:nth-child(3)').hide();

			//点击覆盖物，展示覆盖物所在位置信息
			marker.addEventListener("click", getAttr);
			
			function getAttr() {
				console.log("信息窗口")
				var opts = {
					width: 200, // 信息窗口宽度
					height: 100, // 信息窗口高度
					title: "当前位置：", // 信息窗口标题
				}

				//console.log(p.lng+','+p.lat);
				var geoc = new BMap.Geocoder();
				geoc.getLocation(point, function(rs) {
					var addComp = rs.addressComponents;
					// 创建信息窗口对象    
					var infoWindow = new BMap.InfoWindow(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber, opts);
					map.openInfoWindow(infoWindow, point); //开启信息窗口
					console.log(infoWindow);

				});
			}
		}
			//给表格里的点添加到地图上并生成覆盖物
			function addMark(point) {

				var marker = new BMap.Marker(point);
				map.addOverlay(marker);
				
				marker.addEventListener("click", getAttr);

				function getAttr() {
					console.log("信息窗口")
					var opts = {
						width: 200, // 信息窗口宽度
						height: 100, // 信息窗口高度
						title: "当前位置：", // 信息窗口标题
					}

					var p = marker.getPosition(); //获取marker的位置
					var geoc = new BMap.Geocoder();
					geoc.getLocation(p, function(rs) {
						var addComp = rs.addressComponents;
						// 创建信息窗口对象    
						var infoWindow = new BMap.InfoWindow(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber, opts);
						map.openInfoWindow(infoWindow, point); //开启信息窗口

					});
				}
				
				

			}
			if(document.createElement('canvas').getContext) {
				// 判断当前浏览器是否支持绘制海量点
				var tr = document.getElementsByTagName("tr");
				var td = document.getElementsByTagName("td");
				var points = []; // 添加海量点数据

				/*定义一个k*l长度的二维数组*/
				var depData = new Array();
				for(var k = 0; k < tr.length - 1; k++) {
					depData[k] = new Array();
					for(var l = 0; l < td.length / (tr.length - 1); l++) {
						depData[k][l] = ""; /*此处初始化二维数组为空字符串*/
					}

				}
				/*将表格内数据写入二维数组*/
				for(var i = 0; i < tr.length - 1; i++) {
					for(var j = 0; j < td.length / (tr.length - 1); j++) {
						depData[i][j] = td[i * (td.length / (tr.length - 1)) + j].innerText;
						var point = new BMap.Point(depData[i][1], depData[i][2]);
						addMark(point);
					}
					//console.log('第' + (i + 1) + '行X坐标:' + depData[i][1] + ',' + 'Y坐标：' + depData[i][2]);

					/*points.push(new BMap.Point(depData[i][1], depData[i][2]));
					var options = {
						size: BMAP_POINT_SIZE_BIG,
						shape: BMAP_POINT_SHAPE_STAR,
						color: 'red'
					}*/

				}

				

			} else {
				alert('请在chrome、safari、IE8+以上浏览器查看本示例');
			}


			//地图搜索
			$("#search").click(function test() {
				alert("搜索成功")
			})

			//定位
			$("tr td:first-child").click(function() {
				var address = $(this).html();
				var x = $(this).next().first().html();
				var y = $(this).next().first().next().first().html()

				console.log(x + ',' + y);
				var point = new BMap.Point(x, y);
				map.centerAndZoom(point, 16);
				var marker = new BMap.Marker(point); // 创建标注    
				map.addOverlay(marker); // 将标注添加到地图中

				marker.addEventListener("click", getAttr);

				function getAttr() {

					var opts = {
						width: 200, // 信息窗口宽度
						height: 100, // 信息窗口高度
						title: "当前位置：", // 信息窗口标题
						enableMessage: true, //设置允许信息窗发送短息
						message: "亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
					}

					var p = marker.getPosition(); //获取marker的位置
					var geoc = new BMap.Geocoder();
					geoc.getLocation(p, function(rs) {
						var addComp = rs.addressComponents;
						var infoWindow = new BMap.InfoWindow(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber, opts);
						var infoWindow = new BMap.InfoWindow(
							address + "X坐标" + p.lng + ",Y坐标" + p.lat, opts);
						map.openInfoWindow(infoWindow, point); //开启信息窗口
					});

				}

			})

			//上图
			function reload() {
				var controlClick = 1;
				$(".upimg").click(function() {

					if(controlClick) {
						controlClick = controlClick - 1;
						var bt = this;
						var address = $(bt).parent().parent().children().eq(0).text(); //地址
						console.log(address)
						var state = $(bt).parent().parent().children().eq(3).text(); //状态
						console.log(state);

						function reUpImg() {

							alert("请选择需要上图的点")
							$('tr th:nth-child(2)').show();
							$('tr th:nth-child(3)').show();
							$('tr td:nth-child(2)').show();
							$('tr td:nth-child(3)').show();

							//点击某点弹出提示框，是否确认改点为上图点、
							map.addEventListener("click", showInfo);

							function showInfo(e) {
								console.log("进行上图下一步");

								function upimg() {
									if(confirm("确认改点为上图点？")) {
										//如果是获取点击处的点并在该处生成覆盖物	
										alert("开始上图")
										var point = new BMap.Point(e.point.lng, e.point.lat);
										var marker = new BMap.Marker(point); // 创建标注    
										map.clearOverlays(); //清除之前的覆盖物

										map.addOverlay(marker); // 将移动后的标注添加到地图中

										$(bt).parent().parent().children().eq(3).replaceWith('<td>已上图</td>')
										
										
										controlClick = controlClick + 1;
										var ps = marker.getPosition()
										console.log(ps.lng + ',' + ps.lat);
										map.removeEventListener("click", showInfo);
										marker.addEventListener('click', getAttr)

										function getAttr() {
											console.log("信息窗口")
											var opts = {
												width: 200, // 信息窗口宽度
												height: 100, // 信息窗口高度
												title: "当前位置：", // 信息窗口标题
											}

											var p = marker.getPosition(); //获取marker的位置
											var geoc = new BMap.Geocoder();
											geoc.getLocation(p, function(rs) {
												var addComp = rs.addressComponents;
												// 创建信息窗口对象    
												var infoWindow = new BMap.InfoWindow(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber, opts);
												map.openInfoWindow(infoWindow, point); //开启信息窗口

												//marker.removeEventListener('click', getAttr);
											});
										}
										
										alert('上图成功！')
										//上完图之后，把上完的点坐标添加到数据库中，并把更新之后的坐标展示在地图上
										//用ajax 传递id  地址  坐标 状态 到 update 方法，更新后再调用查询方法到jsp页面
										location.reload();
										

									} else {
										alert("取消上图");
										//reload()
										location.reload();

									}

								}
								upimg();
							}
						}
						reUpImg();
					}

				})
			}
			reload();
			//显示或者隐藏表格信息
			$('#bt').click(function biao() {
				var div = document.getElementById("up-map-div");
				var content = document.getElementById("bt");
				if(div.style.display != "none") {
					div.style.display = "none";
					content.value = "显示";

				} else {
					div.style.display = "block";
					content.value = "隐藏";
				}

			})

			//表格分页

			var totalNum = parseInt($("table").find("tr").length); //总记录数
			//console.log('总记录数：'+totalNum);
			var pageSize = 3; //每页展示的数据数v
			//Math.ceil(total/pageSize);
			var totalPage = Math.ceil(totalNum / pageSize); //parseInt($("#totalPage").text());//总页数
			var currentPage = parseInt($("#currentPage").text()); //当前页码
			//console.log(currentPage);
			$("#totalPage").replaceWith('<a href="" id="totalPage">' + totalPage + '</a>');

			$("table").find("tr").hide().slice((currentPage - 1) * pageSize, currentPage * pageSize).show();

			//下一页
			$("#lowPage").click(function() {
				currentPage = currentPage + 1;
				$("#currentPage").replaceWith('<a href="" id="currentPage">' + currentPage + '</a>');
				console.log('当前页码：' + currentPage);
				if(currentPage < totalPage) {
					$("table").find("tr").hide().slice((currentPage - 1) * pageSize, currentPage * pageSize).show();
					//总记录数-当前页码*每页展示数《每页展示的数  就提示

				} else {
					alert("已到达最后一页");
					$("#lowPage").unbind();
				}

			})

			//上一页
			$("#upPage").click(function() {

				currentPage = currentPage - 1;
				console.log('当前页码：' + currentPage);
				if(currentPage > 0) {
					$("#currentPage").replaceWith('<a href="" id="currentPage">' + currentPage + '</a>');
					$("table").find("tr").hide().slice((currentPage - 1) * pageSize, currentPage * pageSize).show();
					//总记录数-当前页码*每页展示数《每页展示的数  就提示

				} else {
					alert("已到达第一页");
					$("#upPage").unbind();
				}

			})

			//首页
			$("#firstPage").click(function() {
				currentPage = 1;
				$("#currentPage").replaceWith('<a href="" id="currentPage">' + currentPage + '</a>');
				$("table").find("tr").hide().slice((currentPage - 1) * pageSize, currentPage * pageSize).show();

			})

			//尾页
			$("#endPage").click(function() {
				currentPage = totalPage;
				$("#currentPage").replaceWith('<a href="" id="currentPage">' + currentPage + '</a>');
				$("table").find("tr").hide().slice((currentPage - 1) * pageSize, currentPage * pageSize).show();
			})

		

			function myFun(result) {
				var cityName = result.name;
				map.setCenter(cityName);
				//alert("您目前所在的城市:"+cityName);
			}
			var myCity = new BMap.LocalCity();
			myCity.get(myFun);

			function theLocation() {
				var city = document.getElementById("cityName").value;
				if(city != "") {
					map.centerAndZoom(city, 14); // 用城市名设置地图中心点
				}
			}
	

		//地图事件设置函数：
		function setMapEvent() {

			map.addControl(new BMap.MapTypeControl()); //查看模式
			map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放

			var size = new BMap.Size(10, 20);
			map.addControl(new BMap.CityListControl({
				anchor: BMAP_ANCHOR_TOP_LEFT,
				offset: size,

			}));

		}

		//地图控件添加函数：
		function addMapControl() {
			//向地图中添加缩放控件
			var ctrl_nav = new BMap.NavigationControl({
				anchor: BMAP_ANCHOR_TOP_LEFT,
				type: BMAP_NAVIGATION_CONTROL_LARGE
			});
			map.addControl(ctrl_nav);

			//向地图中添加缩略图控件
			var ctrl_ove = new BMap.OverviewMapControl({
				anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
				isOpen: 1
			});
			map.addControl(ctrl_ove);

			//向地图中添加比例尺控件
			var ctrl_sca = new BMap.ScaleControl({
				anchor: BMAP_ANCHOR_BOTTOM_LEFT
			});
			map.addControl(ctrl_sca);

		}

		
	</script>
</html>