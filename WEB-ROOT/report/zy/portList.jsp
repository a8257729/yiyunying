<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>端口情况</title>
	</head>
	<link rel="stylesheet" type="text/css" href="/MOBILE/report/css/port.css"/>
	<script src="/MOBILE/report/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/comm.js" type="text/javascript" charset="utf-8"></script>
	<script src="/MOBILE/report/js/mobileType.js" type="text/javascript" charset="utf-8"></script>
	<body>
		
		<div id="box_top">
			<div id="top_title">
				端口情况
			</div>
			<div id="top_back">
				<img src="/MOBILE/report/img/nav_icon_arrow.png" onclick="javascript:window.history.back(-1);" />
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
	</body>
	
	<script type="text/javascript">
	
	
		
	//刷新
	function myFresh(){
		
		window.location.reload();
	}
		
		
		
	//查询
	$(function(){
			var page_index = "1";
			var page_size = "5";
			var paramObject = GetURLRequestParam();
			var page=decodeURI(paramObject.page);
			var staffId=decodeURI(paramObject.staffId);
			debugger;
			var param = {
				eqp_id :paramObject.eqp_id,
				port_name:"",
				pageIndex:page_index,
				pageSize:page_size
			}
			$.ajax({
				url:"/MOBILE/api/client/xj/zy/device/query/port/list1",
				type:"post",
				data:JSON.stringify(param),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					console.log("请求成功!");
					var list = data.resultData.DevicePortList;
					var totalPage = data.resultData.TotalPage;
					$.each(list,function(index,value){
						
					var html = "<div class='bg'></div>"+
							   "<div class='port_info'>"+
									"<div class='port_up'>"+
										"端口:<span>"+list[index].PORT_NAME+"</span>"+
									"</div>"+
									"<div class='port_down'>"+
										"端口状态:<span>"+list[index].STATE_NAME+"</span>"+
									"</div>"+
									"<div class='port_id' style='display:none'>"+
										"port_id:<span>"+list[index].PORT_ID+"</span>"+
									"</div>"+
							   "</div>"
							   ;
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
						paging(currentPage,pageSize,paramObject,page);
						$("#currentPage").text(currentPage);
					});
					
					//尾页
					$("#endPage").click(function(){
						if(Number($("#currentPage").text().trim())==totalPage){
							return;
						}
						var currentPage = totalPage.toString();
						var pageSize = page_size;
						paging(currentPage,pageSize,paramObject,page);
						$("#currentPage").text(currentPage);
					});
					
					//下一页
					$("#lowPage").click(function(){
						debugger;
						var currentPage = Number($("#currentPage").text().trim());
						
						var pageSize = page_size;
						
						if(totalPage-currentPage>0){
							currentPage = currentPage+1;
							paging(currentPage,pageSize,paramObject,page);
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
							paging(currentPage,pageSize,paramObject,page);
							$("#currentPage").text(currentPage);
						}else{
							//alert("已到达第一页");
							return;
						}
					})
					
					
					
					
					
					//跳转 
					$(".port_info").click(function(){
						debugger;
						var port_name = encodeURI(encodeURI($(this).children("div.port_up").find("span").text().trim() ));
						var eqp_name = encodeURI(encodeURI( decodeURI( paramObject.eqp_name ) ));
						var pon_name = encodeURI(encodeURI( decodeURI( paramObject.pon_name ) )); 
						var pon_id = encodeURI(encodeURI( decodeURI( paramObject.pon_id ) ));
						var eqp_id = paramObject.eqp_id;
						var org_id = paramObject.org_id;
						var port_id = $(this).children("div.port_id").find("span").text().trim();
						if(page=="active"){
							location.href="/MOBILE/report/workfloworder/active.html?port_name="+port_name+"&eqp_id="+eqp_id+"&eqp_name="+eqp_name+
						"&org_id="+org_id+"&port_id="+port_id+"&pon_name="+pon_name+"&pon_id="+pon_id;
						}else{
							location.href="/MOBILE/report/zy/deviceManager.jsp?staffId="+staffId;
						}
						
					})	
				}, 
				error:function(e){
					//debugger;
					console.log("请求失败");
					//console.log(rs.resultCode+","+rs.resultMsg);
				}
			})
		});
		
		function paging(currentPage,pageSize,paramObject,page){
			$("#Tbody").text("");
			var paramPaging = {			
				eqp_id :paramObject.eqp_id,
				port_name:"",
				pageIndex:currentPage.toString(),
				pageSize:pageSize.toString()
			}
			
			$.ajax({
				url:"/MOBILE/api/client/xj/zy/device/query/port/list1",
				type:"post",
				data:JSON.stringify(paramPaging),
				contentType:"application/json",
				dataType:"json",
				success:function(data){
					debugger;
					console.log("请求成功!");
					var list = data.resultData.DevicePortList;
					$.each(list,function(index,value){
						
					var html = "<div class='bg'></div>"+
							   "<div class='port_info'>"+
									"<div class='port_up'>"+
										"端口:<span>"+list[index].PORT_NAME+"</span>"+
									"</div>"+
									"<div class='port_down'>"+
										"端口状态:<span>"+list[index].STATE_NAME+"</span>"+
									"</div>"+
									"<div class='port_id' style='display:none'>"+
										"port_id:<span>"+list[index].PORT_ID+"</span>"+
									"</div>"+
							   "</div>"
							   ;
					$("#Tbody").append(html);	   
					});
					
					
					
					//跳转 
					$(".port_info").click(function(){
						debugger;
						var port_name = encodeURI(encodeURI($(this).children("div.port_up").find("span").text().trim() ));
						var eqp_name = encodeURI(encodeURI( decodeURI( paramObject.eqp_name ) ));
						var pon_name = encodeURI(encodeURI( decodeURI( paramObject.pon_name ) )); 
						var pon_id = encodeURI(encodeURI( decodeURI( paramObject.pon_id ) ));
						var eqp_id = paramObject.eqp_id;
						var org_id = paramObject.org_id;
						var port_id = $(this).children("div.port_id").find("span").text().trim();
						if(page=="active"){
							location.href="/MOBILE/report/workfloworder/active.html?port_name="+port_name+"&eqp_id="+eqp_id+"&eqp_name="+eqp_name+
						"&org_id="+org_id+"&port_id="+port_id+"&pon_name="+pon_name+"&pon_id="+pon_id;
						}else{
							location.href="/MOBILE/report/zy/deviceManager.jsp?staffId="+staffId;
						}
						
					})	
				}, 
				error:function(e){
					//debugger;
					console.log("请求失败");
					//console.log(rs.resultCode+","+rs.resultMsg);
				}
			})
			
			
			
			
		}	
		
	</script>
</html>
