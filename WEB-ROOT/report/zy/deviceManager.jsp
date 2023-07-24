<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>设备管理</title>
	</head>
	<link rel="stylesheet" type="text/css" href="/MOBILE/report/css/deviceManager.css"/>
	<script src="/MOBILE/report/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/comm.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/mobileType.js" type="text/javascript" charset="utf-8"></script>
	<body>
		<div id="box">
	
		<div id="box_top">
			<div id="top_title" >
				设备管理
			</div>
			<div id="top_back">
				<img src="/MOBILE/report/img/nav_icon_arrow.png" onclick="javascript:window.history.back(-1);" />
			</div>
		</div>
		
		<div id="ipt" style="border:1px solid transparent transparent gainsboro transparent;height:130px;">
			<div name="wg" id="grid"  style="line-height:30px;color:black;border:2px solid #87CEEB;background-color:white;">				
					请选择网格
			</div>
			<div name="sb" id="device" style="line-height:30px;color:black;border:2px solid #87CEEB;background-color:white;">
				所有
			</div>
			<input type="text" name="address" id="address" value=""  placeholder="请输入地址"; style="color:black; background:white; border:2px solid #87CEEB;"/>
			<button id="device_bt" onclick="queryInfo('1','5')">查询</button>
            <div id="sel1">
				
			</div>
			<div id="sel2">
			
			</div> 		
		</div>
		<div id="Tbody"></div>
		<div id="fenye" style="display:none">
			<input type="button" id="firstPage" value="首页" />
			<input type='button' id='upPage' value='上一页'/>
			<a href='' id='currentPage' onclick="return false">1</a>/
			<a href='' id='totalPage' onclick="return false"></a>
			<input type='button' id='lowPage' value='下一页'/>
			<input type="button" id="endPage" value="尾页" />
		</div>
		<div id="bgc">
			<div id="bgc_wg">
				
			</div>
		</div>
		
		<div id="bgc_device">
			<div id="bgc_device_select">
				<div class="device_type" >
					OBD
				</div>
				<div class="fenge">	</div>
				<div class="device_type">
					LAN
				</div>
				<div class="fenge">	</div>
				<div class="device_type">
					ONU
				</div>
				<div class="fenge">	</div>
				<div class="device_type">
					所有
				</div>
			</div>
		</div>
		
	</div>	
</br>
	</body>
	<script type="text/javascript">
		
	
		
		
///弹出下拉选择----------------------------------------------------------------------------------------------------------
		$("#grid").click(function(){
			$("#bgc").css("display","block");
		})
		
		$("#device").click(function(){
			$("#bgc_device").css("display","block");
		})
		
		var grid_id="";
		var org_id = "";
		
//下拉选择内容-------------------------------------------------------------------------------------------------------------
		var paramObject = GetURLRequestParam();
		var page=decodeURI(paramObject.page);
		var staffId=decodeURI(paramObject.staffId);
		window.onload=function(){
			gridQuery();
			
		}
		function gridQuery(){
			debugger;
			
			var param = {	
				staffId:staffId.toString()//未知
			}
			$.ajax({
				url:"/MOBILE/api/client/xj/zy/device/query/param/init1",
				type:"post",
				data:JSON.stringify(param),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					//console.log(data);
					var grid_list = data.resultData.STAFF_GIRD_LIST;
					var device_list = data.resultData.DEVICE_TYPE_LIST;
					var org_name = grid_list[0].ORG_NAME;
					org_id = grid_list[0].ORG_ID;
					$("#grid").text(org_name);
					
					//网格选择
					$.each(grid_list,function(index,value){
						var html = "<div class='wg_ct' id="+grid_list[index].ORG_ID+">"+
										grid_list[index].ORG_NAME+
									"</div>"+
									"<div class='fenge'></div>"
									;
						$("#bgc_wg").append(html);
			
					});
					$(".wg_ct").click(function(){
						var sel_context = $(this).text();				
						grid_id=$(this).attr("ID");
						$(this).css("color","#f36f2a");
						$("#grid").text(sel_context);
						$("#bgc").css("display","none");
						
					});
					
					//设备类型
					/*$.each(device_list,function(index,value){
					var device_html = "<div id='bgc_device_select'>"+
												"<div class='device_type' >"+
														device_list[index].TYPE_NAME+
												"</div>"+
												"<div class='fenge'></div>"+
									  "</div>";
						$("#bgc_device").append(device_html);
					});*/
					$(".device_type").click(function(){
						var sel_context = $(this).text();
						
						$("#device").text(sel_context);
						$("#bgc_device").css("display","none");
					});
					
				},
				error:function(e){
					console.log("参数查询失败");
				}
			});
		}
		
		
		
		
	
		
		var orgId ="";
		var deviceTypem ="";
		
		
		//查询
		function queryInfo(page_index,page_size){
			if(page_index==""||page_index==null){
				alert("无信息展示!");
				return;
			}
			
			orgId = grid_id==""?org_id.toString():grid_id; //网格
			deviceType = $("#device").text();//设备类型
			var address = $("#address").val();//地址
			
			/*2530 OBD
			  2511 ONU
			  901  LAN
			  null 所有
			*/
			if($.trim(deviceType)==$.trim("OBD")){
				deviceType="2530";
			}else if($.trim(deviceType)==$.trim("ONU")){
				deviceType="2511";
			}else if($.trim(deviceType)==$.trim("LAN")){
				deviceType="901";
			}else{
				deviceType="null";
			}
			
			
			var param = {
				org_id :orgId,//网格
				res_type_id:deviceType,//设备类型
				eqp_name:address,//地址
				pageIndex:page_index.toString(),
				pageSize:page_size.toString()
			}

			$.ajax({
				type:"post",
				url:"/MOBILE/api/client/xj/zy/device/query/list1",
				contentType:"application/json",
				data: JSON.stringify(param),
				dataType:"json",
				success:function(data){	
					$(".info").replaceWith("");//防多重复生成
					var list = data.resultData.DeviceList;
					var totalPage = data.resultData.TotalPage;//总页数
					$.each(list,function(index,value){
						var html = "";
						html+="<div class='info' >"+		
								"<div class='detail_top'>"+
									"<li class='detail_top_img'>"+
										"<img src='/MOBILE/report/img/report_icon_title.png'  class='more_img'/>"+
									"</li>"+
									"<li class='detail_top_tit'>"+
										list[index].EQP_NAME+
									"</li>"+
									"<li class='detail_top_sqe'>"+
										list[index].EQP_TYPE+
									"</li>"+		
								"</div>"+
								"<div class='detail_bottom'>"+
									"<div class='jx'>"+	
										"局向:<span>"+list[index].DIRECTION+"</span>"+
									"</div>"+
									"<div class='bz_dz'>"+
										"标准地址:<span>"+list[index].ADDR+"</span>"+					
									"</div>"+
									"<div class='az_dz'>"+
										"安装地址:<span>"+list[index].INSTALL_ADDR+"</span>"+									
									"</div>	"+
									"<div class='fg_dz'>"+
										"覆盖地址:<span>"+list[index].RANGE_ADDR+"</span>"+										
									"</div>"+
									"<div class='sb_info'>"+
										"设备端口信息:<span>"+list[index].PORT_INFO+"</span>"+											
									"</div>"+
									"<div class='eqp_id' style='display:none'>"+
										"eqp_id:<span>"+list[index].EQP_ID+"</span>"+											
									"</div>"+
									"<div class='org_id' style='display:none'>"+
										"org_id:<span>"+list[index].ORG_ID+"</span>"+											
									"</div>"+
									"<div class='pon_name' style='display:none'>"+
										"pon_name:<span>"+list[index].PON_NAME+"</span>"+											
									"</div>"+
									"<div class='pon_id' style='display:none'>"+
										"pon_id:<span>"+list[index].PON_ID+"</span>"+											
									"</div>"+
								"</div>"+
							"</div>";
							$("#Tbody").append(html);
	
					});
					
					$("#totalPage").text(totalPage);
					$("#fenye").css("display","block");
					
					
					//首页
					$("#firstPage").click(function(){
						if(Number($("#currentPage").text().trim())==1){
							return;
						}
						var currentPage = "1";
						var pageSize = page_size;
						paging(currentPage,pageSize,address);
						$("#currentPage").text(currentPage);
					});
					
					//尾页
					$("#endPage").click(function(){
						if(Number($("#currentPage").text().trim())==totalPage){
							return;
						}
						var currentPage = totalPage.toString();
						var pageSize = page_size;
						paging(currentPage,pageSize,address);
						$("#currentPage").text(currentPage);
					});
					
					//下一页
					$("#lowPage").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());				
						var pageSize = page_size;						
						if(totalPage-currentPage>0){
							currentPage = currentPage+1;
							paging(currentPage,pageSize,address);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达最后一页");
							return;
						}
					})
					
					//上一页
					$("#upPage").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());					
						var pageSize = page_size;					
						if(currentPage>1){
							currentPage = currentPage-1;
							paging(currentPage,pageSize,address);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达第一页");
							return;
						}
					})
					
						
			
					$(".info").click(function(){
							debugger;
							var epq = $(this).children("div.detail_top").children("li.detail_top_tit").text().trim();
							var pon = $(this).children("div.detail_bottom").children("div.pon_name").find("span").text().trim();
							var port = $(this).children("div.detail_bottom").children("div.sb_info").find("span").text().trim().split("|")[0];
							
							var eqp_id = $(this).children("div.detail_bottom").children("div.eqp_id").find("span").text().trim();
							var org_id = $(this).children("div.detail_bottom").children("div.org_id").find("span").text().trim();
							var port_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.sb_info").find("span").text().trim().split("|")[0] ));
							var eqp_name = encodeURI(encodeURI($(this).children("div.detail_top").children("li.detail_top_tit").text().trim()));
							var pon_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.pon_name").find("span").text().trim()));
							var pon_id = $(this).children("div.detail_bottom").children("div.pon_id").find("span").text().trim();
							
							//window.name="deviceManager:eqp_id="+eqp_id+",port_name="+port_name+",eqp_name="+eqp_name+",org_id="+org_id+",pon_name="+pon_name+",pon_id="+pon_id;	
							location.href="/MOBILE/report/zy/portList.jsp?eqp_id="+eqp_id+"&port_name="+port_name+"&eqp_name="+eqp_name+"&org_id="+org_id+"&pon_name="+pon_name+"&pon_id="+pon_id+"&page="+page+"&staffId="+staffId;
					});
				},
				error:function(e){
					
					console.log("设备管理查询请求失败！");
				}
			});
			
		}
		
		
		//分页查询
		function paging(currentPage,pageSize,address){
			$("#Tbody").text("");
			var paramPaging = {
				org_id :orgId,//网格
				res_type_id:deviceType,//设备类型
				eqp_name:address,//地址
				pageIndex:currentPage.toString(),
				pageSize:pageSize.toString()
			}

			$.ajax({
				type:"post",
				url:"/MOBILE/api/client/xj/zy/device/query/list1",
				contentType:"application/json",
				data: JSON.stringify(paramPaging),
				dataType:"json",
				success:function(data){	
					$(".info").replaceWith("");//防多重复生成
					var list = data.resultData.DeviceList;
					var totalPage = data.resultData.TotalPage;//总页数
					$.each(list,function(index,value){
						var html = "";
						html+="<div class='info' >"+		
								"<div class='detail_top'>"+
									"<li class='detail_top_img'>"+
										"<img src='/MOBILE/report/img/report_icon_title.png'  class='more_img'/>"+
									"</li>"+
									"<li class='detail_top_tit'>"+
										list[index].EQP_NAME+
									"</li>"+
									"<li class='detail_top_sqe'>"+
										list[index].EQP_TYPE+
									"</li>"+		
								"</div>"+
								"<div class='detail_bottom'>"+
									"<div class='jx'>"+	
										"局向:<span>"+list[index].DIRECTION+"</span>"+
									"</div>"+
									"<div class='bz_dz'>"+
										"标准地址:<span>"+list[index].ADDR+"</span>"+					
									"</div>"+
									"<div class='az_dz'>"+
										"安装地址:<span>"+list[index].INSTALL_ADDR+"</span>"+									
									"</div>	"+
									"<div class='fg_dz'>"+
										"覆盖地址:<span>"+list[index].RANGE_ADDR+"</span>"+										
									"</div>"+
									"<div class='sb_info'>"+
										"设备端口信息:<span>"+list[index].PORT_INFO+"</span>"+											
									"</div>"+
									"<div class='eqp_id' style='display:none'>"+
										"eqp_id:<span>"+list[index].EQP_ID+"</span>"+											
									"</div>"+
									"<div class='org_id' style='display:none'>"+
										"org_id:<span>"+list[index].ORG_ID+"</span>"+											
									"</div>"+
									"<div class='pon_name' style='display:none'>"+
										"pon_name:<span>"+list[index].PON_NAME+"</span>"+											
									"</div>"+
									"<div class='pon_id' style='display:none'>"+ 
										"pon_id:<span>"+list[index].PON_ID+"</span>"+											
									"</div>"+
								"</div>"+
							"</div>";
							$("#Tbody").append(html);
	
					})
					
					$(".info").click(function(){
							debugger;
							var eqp_id = $(this).children("div.detail_bottom").children("div.eqp_id").find("span").text().trim();
							var org_id = $(this).children("div.detail_bottom").children("div.org_id").find("span").text().trim();
							var port_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.sb_info").find("span").text().trim().split("|")[0] ));
							var eqp_name = encodeURI(encodeURI($(this).children("div.detail_top").children("li.detail_top_tit").text().trim()));
							var pon_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.pon_name").find("span").text().trim()));
							var pon_id = $(this).children("div.detail_bottom").children("div.pon_id").find("span").text().trim();
							
							//window.name="deviceManager:eqp_id="+eqp_id+",port_name="+port_name+",eqp_name="+eqp_name+",org_id="+org_id+",pon_name="+pon_name+",pon_id="+pon_id;	
							location.href="/MOBILE/report/zy/portList.jsp?eqp_id="+eqp_id+"&port_name="+port_name+"&eqp_name="+eqp_name+"&org_id="+org_id+"&pon_name="+pon_name+"&pon_id="+pon_id+"&page="+page+"&staffId="+staffId;
					});
					
				}
			});
		}
		
		
		//弹DIV提示框-----------------------------------------------------------------------------------------------------------------------------
		/*function tips(content){
			if($(content).attr("class")=="device_type"){
				$("#tip_context").replaceWith("<li id='tip_context'>确认选择此设备？</li>");
			}else{
				$("#tip_context").replaceWith("<li id='tip_context'>确认选择此网格？</li>")
			}
			$("#tip").css("display","block");//弹出提示框
			
			//根据确认还是取消进行相应的操作
			$("#tip_end button").click(function(){
				
				if($(this).text()=="确认"){
						$("#bgc").css("display","none");//关闭下拉框
						$("#tip").css("display","none");//关闭提示框
						var sel_context = $(content).text();
						if($(content).attr("class")=="device_type"){//如果点击的是网格下拉按钮，就把内容替换到网格框中
							$("#device option").replaceWith("<option value=''>"+sel_context+"</option>");
							
						}else{//如果点击的是设备类型下拉按钮，就把内容替换到设备类型框中
							$("#grid option").replaceWith("<option value=''>"+sel_context+"</option>");
							
						}
						$(content).parent().parent().css("display","none");
				}else if($(this).text()=="取消"){
					
						$("#tip").css("display","none");
				}
			})
		}*/
		
		//查询
		/*$("#device_bt").click(function(){
			debugger;
			orgId = grid_id==""?org_id.toString():grid_id; //网格
			deviceType = $("#device").text();//设备类型
			var address = $("#address").val();//地址
			
			
			if($.trim(deviceType)==$.trim("OBD")){
				deviceType="2530";
				console.log(deviceType);
			}else if($.trim(deviceType)==$.trim("ONU")){
				deviceType="2511";
				console.log(deviceType);
			}else if($.trim(deviceType)==$.trim("LAN")){
				deviceType="901";
				console.log(deviceType);
			}else{
				deviceType="null";
			}
			
			
			var param = {
				org_id :orgId,//网格
				res_type_id:deviceType,//设备类型
				eqp_name:address,//地址
				pageIndex:"1",
				pageSize:"5"
			}

			$.ajax({
				type:"post",
				url:"/MOBILE/api/client/xj/zy/device/query/list1",
				contentType:"application/json",
				data: JSON.stringify(param),
				dataType:"json",
				success:function(data){	
					$(".info").replaceWith("");//防多重复生成
					var list = data.resultData.DeviceList;
					$.each(list,function(index,value){
						var html = "";
						html+="<div class='info' >"+		
								"<div class='detail_top'>"+
									"<li class='detail_top_img'>"+
										"<img src='/MOBILE/report/img/report_icon_title.png'  class='more_img'/>"+
									"</li>"+
									"<li class='detail_top_tit'>"+
										list[index].EQP_NAME+
									"</li>"+
									"<li class='detail_top_sqe'>"+
										list[index].EQP_TYPE+
									"</li>"+		
								"</div>"+
								"<div class='detail_bottom'>"+
									"<div class='jx'>"+	
										"局向:<span>"+list[index].DIRECTION+"</span>"+
									"</div>"+
									"<div class='bz_dz'>"+
										"标准地址:<span>"+list[index].ADDR+"</span>"+					
									"</div>"+
									"<div class='az_dz'>"+
										"安装地址:<span>"+list[index].INSTALL_ADDR+"</span>"+									
									"</div>	"+
									"<div class='fg_dz'>"+
										"覆盖地址:<span>"+list[index].RANGE_ADDR+"</span>"+										
									"</div>"+
									"<div class='sb_info'>"+
										"设备端口信息:<span>"+list[index].PORT_INFO+"</span>"+											
									"</div>"+
									"<div class='eqp_id' style='display:none'>"+
										"eqp_id:<span>"+list[index].EQP_ID+"</span>"+											
									"</div>"+
									"<div class='org_id' style='display:none'>"+
										"org_id:<span>"+list[index].ORG_ID+"</span>"+											
									"</div>"+
									"<div class='pon_name' style='display:none'>"+
										"pon_name:<span>"+list[index].PON_NAME+"</span>"+											
									"</div>"+
									"<div class='pon_id' style='display:none'>"+
										"pon_id:<span>"+list[index].PON_ID+"</span>"+											
									"</div>"+
								"</div>"+
							"</div>";
							$("#ipt").after(html);
	
					})
					//alert("查询成功！");
					//console.log("设备管理查询请求成功！");
					$(".info").click(function(){
							debugger;
							var eqp_id = $(this).children("div.detail_bottom").children("div.eqp_id").find("span").text().trim();
							var org_id = $(this).children("div.detail_bottom").children("div.org_id").find("span").text().trim();
							var port_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.sb_info").find("span").text().trim().split("|")[0] ));
							var eqp_name = encodeURI(encodeURI($(this).children("div.detail_top").children("li.detail_top_tit").text().trim()));
							var pon_name = encodeURI(encodeURI($(this).children("div.detail_bottom").children("div.pon_name").find("span").text().trim()));
							var pon_id = $(this).children("div.detail_bottom").children("div.pon_id").find("span").text().trim();
							
							//window.name="deviceManager:eqp_id="+eqp_id+",port_name="+port_name+",eqp_name="+eqp_name+",org_id="+org_id+",pon_name="+pon_name+",pon_id="+pon_id;	
							location.href="/MOBILE/report/zy/portList.jsp?eqp_id="+eqp_id+"&port_name="+port_name+"&eqp_name="+eqp_name+"&org_id="+org_id+"&pon_name="+pon_name+"&pon_id="+pon_id+"&page="+page+"&staffId="+staffId;
					});
				},
				error:function(e){
					
					console.log("设备管理查询请求失败！");
				}
			});
			
		});*/
		
		
	</script>
</html>
