<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html> 
	<head>
		<meta charset="UTF-8">
		<meta name="format-detection" content="telephone=yes"/>
		<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>开通代办工单列表</title>
	</head>
	<link rel="stylesheet" type="text/css" href="/MOBILE/report/css/ktdb_orderList.css"/>
	<script src="/MOBILE/report/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/comm.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/mobileType.js" type="text/javascript" charset="utf-8"></script>
	<script language="javascript" src="/MOBILE/report/workfloworder/js/common.js"type="text/javascript"></script>
			
	<body>
		<div id="box_top">
			<div id="top_title"> 
				开通代办
			</div>
			<div id="top_back">
				<img src="/MOBILE/report/img/nav_icon_arrow.png" onclick="javascript:window.history.back(-1);" />
			</div>
			<div id="top_fresh">
				<img src="/MOBILE/report/img/fault_order_refresh.png" onclick="myFresh()"/>
			</div>
		</div>
		<div id="box_middle">
			<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div id="selectType" >
								<select id="type"  style="height:30px;">
									<option value="">全部</option>
									<option value="0">未预约</option>
									<option value="1">已预约</option>
									<option value="2">业务号码</option>
									<option value="3">最新到达</option>
									<option value="4">最新预约</option>
								</select>
							</div>
						</td>
						<td>
							<div id="search_content" >
								<input type="text" name="" id="search_value"   style="height:25px;" />
								<img  id="delectImg" src="/MOBILE/report/img/ktdb_icon_delete.png" />
							</div>
						</td>
						<td>
							<div id="queryBt">
								<input type="button" id="bt" value="查询"  style="height:30px;width:50px;"/>
							</div>
						</td>
					</tr>
				</table>
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
		<div class="loading"  style="display:none">
        	<div class="pic">
        		<img src="/MOBILE/report/img/5-121204193Q9-50.gif"/>
        	</div>
    	</div>
		<div id="top_bg" style="display:none">
			<div id="top_case">
				<div id="top_case_title">
					提示框
				</div>
				<div id="top_case_info">
					没有相关信息
				</div>
				<div id="top_case_close">
					<button>关闭</button>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	
			
		var paramInfo = new Array();
		var paramObject = GetURLRequestParam();
		var staffId=decodeURI(paramObject.staffId);//"233371";
		var flag = true;
		window.onload=function(){
			debugger;
			$(".loading").css("display","block");//开启遮罩
			paramQuery();
			
		}
		
		//参数查询--------------------------
		function  paramQuery(){
			console.log("参数查询方法");
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
		
		//查询
		$("#bt").click(function(){
			$(".loading").css("display","block");//开启遮罩
			$("#Tbody").html("");
			$("#fenye").css("display","none");
			queryInfo(paramInfo);
			
			
		})
		
		//聚焦时显示清空按钮
		$("#search_value").focus(function(){
			$("#delectImg").css("display","block");
		});
		
		
		
		//清空输入框的内容
		$("#delectImg").click(function(){
			$("#search_value").val("");
			$("#delectImg").css("display","none");
			
		});
		
		

		//内容展示---------------------------------------------------------------------------------------
		//获取后台数据展示到jsp页面上
		function queryInfo(paramInfo){
			debugger;
			var selected = $("#type").val();//查询类型
			var contentText = $("#search_value").val();//业务号码
			
			var job_id = paramInfo.JOB_ID!=null?paramInfo.JOB_ID:"0";
			var page_index = "1";
			var page_size = "5";
			var acc_nbr = contentText!=null?contentText:"";//业务号码  默认""  
			var create_date =""; //到达时间  默认""  1:正序  0：倒序
			var bok_time = "";//预约时间 默认""  1:正序  0：倒序
			var bok_state = "";//预约状态  默认""  1：已预约  0：未预约
			
			if(selected=="1"||selected=="0"){
				bok_state=selected;
			}else if(selected=="3"){
				create_date="1";
			}else if(selected=="4"){
				bok_time="1";
			}
			var ControllerUrl = "/MOBILE/api/client/xj/kt/private/page1";
			console.log(bok_state);
	
			 var param = {
				username :paramInfo.USERNAME!=null?paramInfo.USERNAME:"",
				jobId:job_id.toString(),
				pageIndex:page_index,
				pageSize:page_size,
				accNbr:acc_nbr,
				createDate:create_date,
				bokTime:bok_time,
				bokState:bok_state
			}
			

			$.ajax({
				url:ControllerUrl,
				type:"post",
				data:JSON.stringify(param),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					var arr = new Array();
					console.log("请求成功!");
					var list = data.resultData.WorkOrderList;
					var totalPage = data.resultData.TotalPage;
					$.each(list,function(index,value){

					var html = "<div class='bg'></div>"+
							  "<div class='detail'>"+
							   	"<div class='detail_top'>"+
										"<ul>"+
											"<li class='more_img'><img src='/MOBILE/report/img/report_icon_title.png' /></li>"+
											"<li class='name'>"+list[index].CustName+'('+list[index].ContactPhone +')'+"</li>"+
											"<li class='tel_img'><a href=tel:"+list[index].ContactPhone+"><img src='/MOBILE/report/img/fault_order_telephone.png' /></a></li>"+
										"</ul>"+
								"</div>"+
								"<div class='state_box' >"+
									"<ul class='ul_state'>"+
				
									"</ul>"+
								"</div>"+
								"<div class='address' >"+
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
											"<td>"+list[index].ServiceName+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>业务号码:</th>"+
											"<td>"+list[index].AccNbr+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>到单时间:</th>"+
											"<td>"+list[index].CreateDate+"</td>"+
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
										"<tr class='orderId' style='display:none'>"+
											"<th >订单ID:</th>"+
											"<td>"+list[index].orderId+"</td>"+
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
					$(".loading").css("display","none");//关闭遮罩
					
					//首页
					$("#firstPage").unbind("click").click(function(){
						if(Number($("#currentPage").text().trim())==1){
							return;
						}
						var currentPage = "1";
						var pageSize = page_size;
						paging(currentPage,pageSize,job_id,param.accNbr,param.createDate,param.bokTime,param.bokState,ControllerUrl);
						$("#currentPage").text(currentPage);
					});
					

					
					//尾页
					$("#endPage").unbind("click").click(function(){
						if(Number($("#currentPage").text().trim())==totalPage){
							return;
						}
						var currentPage = totalPage.toString();
						var pageSize = page_size;
						paging(currentPage,pageSize,job_id,param.accNbr,param.createDate,param.bokTime,param.bokState,ControllerUrl);
						$("#currentPage").text(currentPage);
					});
					
					//跳转
					/*$("#currentPage").click(function(){
						var objecTive = $(this).text().trim().toString();
						var pageSize = page_size;
						paging(objecTive,pageSize,job_id);
					});*/
					
					
					//下一页
					$("#lowPage").unbind("click").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());
						var pageSize = page_size;
						if(totalPage-currentPage>0){
							currentPage = currentPage+1;
							paging(currentPage,pageSize,job_id,param.accNbr,param.createDate,param.bokTime,param.bokState,ControllerUrl);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达最后一页");
							return;
						}
					});
					
					//上一页
					$("#upPage").unbind("click").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());
						var pageSize = page_size;
						if(currentPage>1){
							currentPage = currentPage-1;
							paging(currentPage,pageSize,job_id,param.accNbr,param.createDate,param.bokTime,param.bokState,ControllerUrl);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达第一页");
							return;
						}
					});
						
						$(".end").click(function(){						
							var WorkOrderID =  $(this).children("table").find("tr").eq(4).find("td").text().trim();
							var WorkOrderType = $(this).children("table").find("tr").eq(5).find("td").text().trim();
							var OrderCode = $(this).children("table").find("tr").eq(6).find("td").text().trim();
							var WorkOrderCode= $(this).children("table").find("tr").eq(7).find("td").text().trim();
							var orderId= $(this).children("table").find("tr").eq(9).find("td").text().trim();
							//alert(WorkOrderID+','+WorkOrderType+','+OrderCode+','+WorkOrderCode);
							var JOB_ID= paramInfo.JOB_ID;
							var ORG_ID= paramInfo.ORG_ID;
							var ORG_NAME= paramInfo.ORG_NAME;
							var STAFF_NAME= paramInfo.STAFF_NAME;
							var USERNAME= paramInfo.USERNAME;
							
							window.name="ktdb:WorkOrderID="+WorkOrderID+",WorkOrderType="+WorkOrderType+",OrderCode="+OrderCode+",WorkOrderCode="+WorkOrderCode+
							",JOB_ID="+JOB_ID+",ORG_ID="+ORG_ID+",ORG_NAME="+ORG_NAME+",STAFF_NAME="+STAFF_NAME+",USERNAME="+USERNAME+",staffId="+staffId+",orderId="+orderId;
							location.href="/MOBILE/report/workfloworder/detailInfo.html";
						});
					
				},
				error:function(e){
					console.log("请求失败");
				}
			})
			
		}	
		//})
		
		
		
		//分页查询
		function paging(currentPage,pageSize,job_id,accNbr,createDate,bokTime,bokState,ControllerUrl){
			
			
			$("#Tbody").text("");
			$(".loading").css("display","block");//打开遮罩
			
			var paramPaging = {
				username :paramInfo.USERNAME!=null?paramInfo.USERNAME:"",//未知
				jobId:job_id==""?"0":job_id.toString(),//未知
				pageIndex:currentPage.toString(),
				pageSize:pageSize.toString(),
				accNbr:accNbr,
				createDate:createDate,
				bokTime:bokTime,
				bokState:bokState
			}
			
			
			
			
			$.ajax({
				url:ControllerUrl,
				type:"post",
				data:JSON.stringify(paramPaging),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					console.log("请求成功!");
					var list = data.resultData.WorkOrderList;
					var totalPage = data.resultData.TotalPage;
					if(list.length<1){
						$("#top_case_info").text("没有查询到相关信息");
					}
					$("#top_case_info").text(data.resultMsg);
					$.each(list,function(index,value){

					var html = "<div class='bg'></div>"+
							  "<div class='detail'>"+
							   	"<div class='detail_top'>"+
										"<ul>"+
											"<li class='more_img'><img src='/MOBILE/report/img/report_icon_title.png' /></li>"+
											"<li class='name'>"+list[index].CustName+'('+list[index].ContactPhone +')'+"</li>"+
											"<li class='tel_img'><a href=tel:"+list[index].ContactPhone+"><img src='/MOBILE/report/img/fault_order_telephone.png' /></a></li>"+
										"</ul>"+
								"</div>"+
								"<div class='state_box' >"+
									"<ul class='ul_state'>"+
										
									"</ul>"+
								"</div>"+
								"<div class='address' >"+
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
											"<td>"+list[index].ServiceName+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>业务号码:</th>"+
											"<td>"+list[index].AccNbr+"</td>"+
										"</tr>"+
										"<tr>"+
											"<th>到单时间:</th>"+
											"<td>"+list[index].CreateDate+"</td>"+
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
										"<tr class='orderId' style='display:none'>"+
										"<th >订单ID:</th>"+
										"<td>"+list[index].orderId+"</td>"+
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
					$(".loading").css("display","none");//关闭遮罩
					$(".end").click(function(){
							debugger;
							var WorkOrderID =  $(this).children("table").find("tr").eq(4).find("td").text().trim();
							var WorkOrderType = $(this).children("table").find("tr").eq(5).find("td").text().trim();
							var OrderCode = $(this).children("table").find("tr").eq(6).find("td").text().trim();
							var WorkOrderCode= $(this).children("table").find("tr").eq(7).find("td").text().trim();
							var orderId= $(this).children("table").find("tr").eq(9).find("td").text().trim();
							//alert(WorkOrderID+','+WorkOrderType+','+OrderCode+','+WorkOrderCode);
							var JOB_ID= paramInfo.JOB_ID;
							var ORG_ID= paramInfo.ORG_ID;
							var ORG_NAME= paramInfo.ORG_NAME;
							var STAFF_NAME= paramInfo.STAFF_NAME;
							var USERNAME= paramInfo.USERNAME;
							
							window.name="ktdb:WorkOrderID="+WorkOrderID+",WorkOrderType="+WorkOrderType+",OrderCode="+OrderCode+",WorkOrderCode="+WorkOrderCode+
							",JOB_ID="+JOB_ID+",ORG_ID="+ORG_ID+",ORG_NAME="+ORG_NAME+",STAFF_NAME="+STAFF_NAME+",USERNAME="+USERNAME+",staffId="+staffId+",orderId="+orderId;
							location.href="/MOBILE/report/workfloworder/detailInfo.html";
						})
				}
			});
				
			
		}
		
		//查询
		//聚焦时显示清空按钮
		$("#search_value").focus(function(){
			$("#delectImg").css("display","block");
		})
		
		//清空输入框的内容
		$("#delectImg").click(function(){
			$("#search_value").val("");
			$("#delectImg").css("display","none");
			
		})
		
		//关闭提示框
		$("#top_case_close").click(function(){
			$("#top_bg").css("display","none");
		})
		
		
		
		//刷新
		function myFresh(){
			window.location.reload();
		}
	
		
	</script>
</html>