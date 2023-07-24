<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="format-detection" content="telephone=yes"/>
		<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>故障代办工单</title>
	</head>
	<link rel="stylesheet" type="text/css" href="/MOBILE/report/css/ktdb_orderList.css"/>
	<script src="/MOBILE/report/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/comm.js" type="text/javascript" charset="utf-8"></script>
	<body>
		<div id="box_top">
			<div id="top_title">
				故障代办工单
			</div>
			<div id="top_back">
				<img src="/MOBILE/report/img/nav_icon_arrow.png" onclick="javascript:window.history.back(-1);" />
			</div>
			<div id="top_fresh">
				<img src="/MOBILE/report/img/fault_order_refresh.png" onclick="myFresh()"/>
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
		<!--<div id="box_middle">
			<div id="search_case">
				<div id="search_img">
					<img src="/MOBILE/report/img/ktdb_icon_magnifier.png" />
				</div>

				<div id="search_content">
					<input type="text" name="" id="search_value" value="" style="width:200px;height:26px;"/>
				</div>
			</div>
		</div>-->
		
	</body>
	<script type="text/javascript">
		
	    var page_size = '5';
	//判断手机系统类型安卓还是ios
	var browser = {
		versions: function () {
			var u = navigator.userAgent, app = navigator.appVersion;
			return { //移动终端浏览器版本信息 
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器 
			iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器 
			iPad: u.indexOf('iPad') > -1, //是否iPad 
			};
		}(),
	}
	if (browser.versions.iPhone || browser.versions.iPad || browser.versions.ios) {
		$("#box_top").css("display","none");
	}
	if (browser.versions.android) {
		$("#box_top").css("display","block");
	}
		var paramInfo = new Array();
		var paramObject = GetURLRequestParam();
		var staffId=decodeURI(paramObject.staffId);
		window.onload=function(){
			debugger;
			paramQuery();
			
		}
		
		//参数查询---------------------------------------------------------------------------------------
		function  paramQuery(){
			debugger;
			var paramId = {	
				staff_id:staffId.toString()
			}
			
			$.ajax({
				url:"/MOBILE/api/client/xj/kt/private/params",
				type:"post",
				data:JSON.stringify(paramId),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					console.log("参数查询成功！");
					paramInfo = data.resultData;				
					queryInfo(paramInfo);	
					
				},
				error:function(e){
					console.log("参数查询失败");
				}
			});
			
		}
		
		
		

		//内容展示---------------------------------------------------------------------------------------
		//获取后台数据展示到jsp页面上
		function queryInfo(paramInfo){
			debugger;
			var job_id = "";
			if(paramInfo.JOB_ID!=null){
				job_id = paramInfo.JOB_ID.toString();
			}
			
			 var param = {
				username :paramInfo.USERNAME!=null?paramInfo.USERNAME:"",//未知
				jobId:job_id==""?"0":job_id,//未知
				pageIndex:"1",
				pageSize:"5"
			}	
			$.ajax({
				url:"/MOBILE/api/client/xj/bz/faultorder/private/page1",
				type:"post",
				data:JSON.stringify(param),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					console.log("请求成功!");
					var list = data.resultData.WorkOrderList
					var totalPage = data.resultData.TotalPage;
					$.each(list,function(index,value){

					var html = "<div class='bg'></div>"+
							  "<div class='detail'>"+
							   	"<div class='detail_top'>"+
										"<ul>"+
											"<li class='more_img'><img src='/MOBILE/report/img/report_icon_title.png' /></li>"+
											"<li class='name'>"+list[index].CustLinkPerson+'('+list[index].CustLinkPhone +')'+"</li>"+
											"<li class='tel_img'><a href=tel:"+list[index].CustLinkPhone+"><img src='/MOBILE/report/img/fault_order_telephone.png' /></a></li>"+
										"</ul>"+
								"</div>"+
								"<div class='state_box' >"+
									"<ul class='ul_state'>"+
										
									"</ul>"+
								"</div>"+
								"<div class='address'>"+
									"<ul >"+
										"<li class='address_img'>"+
											"<img src='/MOBILE/report/img/robbing_order_addr.png' />"+
										"</li>"+
										"<li class='address_ct'>"+
											list[index].Address+
										"</li>"+
									"</ul>"+
								"</div>"+
								"<div class='end'>"+
									"<table border='0' cellspacing='0' cellpadding='0'>"+
										"<tr>"+
											"<th>服务名称:</th>"+
											"<td>"+list[index].OrderTitle+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>业务号码:</th>"+
											"<td>"+list[index].AccNbr+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>到单时间:</th>"+
											"<td>"+list[index].AcceptDate+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>预约时间:</th>"+
											"<td>"+list[index].BokTime+"</td>"+
										"</tr>"+
										"<tr class='work_orderId' style='display:none'>"+
											"<th>工单ID:</th>"+
											"<td >"+list[index].WorkOrderID+"</td>"+
										"</tr>"+
										"<tr class='work_orderType' style='display:none'>"+
											"<th>工单类型:</th>"+
											"<td >"+list[index].WorkOrderType+"</td>"+
										"</tr>"+
										"<tr class='order_code' style='display:none'>"+
											"<th>订单编码:</th>"+
											"<td >"+list[index].OrderCode+"</td>"+
										"</tr>"+
										"<tr class='work_order_code' style='display:none'>"+
											"<th >工单编码:</th>"+
											"<td>"+list[index].WorkOrderCode+"</td>"+
										"</tr>"+
										"<tr class='book_state' style='display:none'>"+
											"<th >预约状态:</th>"+
											"<td>"+list[index].beSpeakState+"</td>"+
										"</tr>"+
									"</table>"+
								"</div>"+
							"</div>";
							$("#Tbody").append(html);
				
					});	
						var tdCount=$(".book_state td");
						for(var i=0;i<tdCount.length;i++){
						var val = $(tdCount[i]).text();
						if(val=="1"){
						  $(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state1'><div>已预约</div></li>");
						}
						if(val=="0"){
							$(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state3'><div>未预约</div></li>");
						}
						if(val==""){
							$(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state2'><div>待回单</div></li>");
						}
						}
						
						
					$("#totalPage").text(totalPage);
					$("#fenye").css("display","block");
					
					//首页
					$("#firstPage").click(function(){
						if(Number($("#currentPage").text().trim())==1){
							return;
						}
						var currentPage = "1";
						var pageSize = page_size;
						paging(currentPage,pageSize,job_id);
						$("#currentPage").text(currentPage);
					});
					
					//尾页
					$("#endPage").click(function(){
						if(Number($("#currentPage").text().trim())==totalPage){
							return;
						}
						var currentPage = totalPage.toString();
						var pageSize = page_size;
						paging(currentPage,pageSize,job_id);
						$("#currentPage").text(currentPage);
					});
					
					//跳转
					/*$("#currentPage").click(function(){
						var objecTive = $(this).text().trim().toString();
						var pageSize = page_size;
						paging(objecTive,pageSize,job_id);
					});*/
					
					
					//下一页
					$("#lowPage").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());
						var pageSize = page_size;
						if(totalPage-currentPage>0){
							currentPage = currentPage+1;
							paging(currentPage,pageSize,job_id);
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
							paging(currentPage,pageSize,job_id);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达第一页");
							return;
						}
					})
						
						
						
						
						
						$(".end").click(function(){
							
							var WorkOrderID =  $(this).children("table").find("tr").eq(4).find("td").text().trim();
							var WorkOrderType = $(this).children("table").find("tr").eq(5).find("td").text().trim();
							var OrderCode = $(this).children("table").find("tr").eq(6).find("td").text().trim();
							var WorkOrderCode= $(this).children("table").find("tr").eq(7).find("td").text().trim();
							var JOB_ID= paramInfo.JOB_ID;
							var ORG_ID= paramInfo.ORG_ID;
							var ORG_NAME= paramInfo.ORG_NAME;
							var STAFF_NAME= paramInfo.STAFF_NAME;
							var USERNAME= paramInfo.USERNAME;
							
							window.name="ktdb:WorkOrderID="+WorkOrderID+",WorkOrderType="+WorkOrderType+",OrderCode="+OrderCode+",WorkOrderCode="+WorkOrderCode+
							",JOB_ID="+JOB_ID+",ORG_ID="+ORG_ID+",ORG_NAME="+ORG_NAME+",STAFF_NAME="+STAFF_NAME+",USERNAME="+USERNAME+",staffId="+staffId;
							location.href="/MOBILE/report/workfloworder/faultdetail.html";
						})
				},
				error:function(e){
					debugger;
					console.log("请求失败");
				}
			})
		}	
		//})
		
		
		function paging(currentPage,pageSize,job_id){
			
			$("#Tbody").text("");
			var paramPaging = {
				username :paramInfo.USERNAME!=null?paramInfo.USERNAME:"",//未知
				jobId:job_id==""?"0":job_id,//未知
				pageIndex:currentPage.toString(),
				pageSize:pageSize.toString()
			}
			$.ajax({
				url:"/MOBILE/api/client/xj/bz/faultorder/private/page1",
				type:"post",
				data:JSON.stringify(paramPaging),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					console.log("请求成功!");
					var list = data.resultData.WorkOrderList
					$.each(list,function(index,value){

					var html = "<div class='bg'></div>"+
							  "<div class='detail'>"+
							   	"<div class='detail_top'>"+
										"<ul>"+
											"<li class='more_img'><img src='/MOBILE/report/img/report_icon_title.png' /></li>"+
											"<li class='name'>"+list[index].CustLinkPerson+'('+list[index].CustLinkPhone +')'+"</li>"+
											"<li class='tel_img'><a href=tel:"+list[index].CustLinkPhone+"><img src='/MOBILE/report/img/fault_order_telephone.png' /></a></li>"+
										"</ul>"+
								"</div>"+
								"<div class='state_box' >"+
									"<ul class='ul_state'>"+
										
									"</ul>"+
								"</div>"+
								"<div class='address'>"+
									"<ul>"+
										"<li class='address_img'>"+
											"<img src='/MOBILE/report/img/robbing_order_addr.png' />"+
										"</li>"+
										"<li class='address_ct'>"+
											list[index].Address+
										"</li>"+
									"</ul>"+
								"</div>"+
								"<div class='end'>"+
									"<table border='0' cellspacing='0' cellpadding='0'>"+
										"<tr>"+
											"<th>服务名称:</th>"+
											"<td>"+list[index].OrderTitle+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>业务号码:</th>"+
											"<td>"+list[index].AccNbr+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>到单时间:</th>"+
											"<td>"+list[index].AcceptDate+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>预约时间:</th>"+
											"<td>"+list[index].BokTime+"</td>"+
										"</tr>"+
										"<tr class='work_orderId' style='display:none'>"+
											"<th>工单ID:</th>"+
											"<td >"+list[index].WorkOrderID+"</td>"+
										"</tr>"+
										"<tr class='work_orderType' style='display:none'>"+
											"<th>工单类型:</th>"+
											"<td >"+list[index].WorkOrderType+"</td>"+
										"</tr>"+
										"<tr class='order_code' style='display:none'>"+
											"<th>订单编码:</th>"+
											"<td >"+list[index].OrderCode+"</td>"+
										"</tr>"+
										"<tr class='work_order_code' style='display:none'>"+
											"<th >工单编码:</th>"+
											"<td>"+list[index].WorkOrderCode+"</td>"+
										"</tr>"+
										"<tr class='book_state' style='display:none'>"+
											"<th >预约状态:</th>"+
											"<td>"+list[index].beSpeakState+"</td>"+
										"</tr>"+
									"</table>"+
								"</div>"+
							"</div>";
							$("#Tbody").append(html);
				
					});	
						var tdCount=$(".book_state td");
						for(var i=0;i<tdCount.length;i++){
							
						var val = $(tdCount[i]).text();
						if(val=="1"){
						  $(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state1'><div>已预约</div></li>");
						}
						if(val=="0"){
							$(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state3'><div>未预约</div></li>");
						}
						if(val==""){
							$(tdCount[i]).parents(".end").prev().prev().find("ul").append("<li class='state2'><div>待回单</div></li>");
						}
						}
						
		
						
						$(".end").click(function(){
							
							var WorkOrderID =  $(this).children("table").find("tr").eq(4).find("td").text().trim();
							var WorkOrderType = $(this).children("table").find("tr").eq(5).find("td").text().trim();
							var OrderCode = $(this).children("table").find("tr").eq(6).find("td").text().trim();
							var WorkOrderCode= $(this).children("table").find("tr").eq(7).find("td").text().trim();
							var JOB_ID= paramInfo.JOB_ID;
							var ORG_ID= paramInfo.ORG_ID;
							var ORG_NAME= paramInfo.ORG_NAME;
							var STAFF_NAME= paramInfo.STAFF_NAME;
							var USERNAME= paramInfo.USERNAME;
							
							window.name="ktdb:WorkOrderID="+WorkOrderID+",WorkOrderType="+WorkOrderType+",OrderCode="+OrderCode+",WorkOrderCode="+WorkOrderCode+
							",JOB_ID="+JOB_ID+",ORG_ID="+ORG_ID+",ORG_NAME="+ORG_NAME+",STAFF_NAME="+STAFF_NAME+",USERNAME="+USERNAME+",staffId="+staffId;
							location.href="/MOBILE/report/workfloworder/faultdetail.html";
						})
				},
				error:function(e){
					debugger;
					console.log("请求失败");
				}
			})
			
			
		}
	
		
		
		//刷新
		function myFresh(){
			window.location.reload();
		}
		
		//清空搜索框的内容
		$("#delete").click(function(){
			var search_value = $("#search_value").val("");
		});
		 
		
		 
		
		//地图
		$(".map_img").click(function(){
			alert("调用地图接口");
		});
		
		
		
		//状态
		$(".state div").click(function(){
			alert($(this).text());
		})
		
		
	</script>
</html>