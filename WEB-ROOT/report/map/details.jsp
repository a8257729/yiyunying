<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>问题详情</title>
</head>
<link rel="stylesheet" type="text/css" href="/MOBILE/report/map/css/details.css"/>
	<script src="/MOBILE/report/map/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
	<body>
		<%List list = (List)request.getSession().getAttribute("details") ;%>
		<%
			if(list!=null){
			Map map = (Map)list.get(0);
			
		%>
		
		
		<% List user = (List)request.getSession().getAttribute("user"); %>
		<div id="box">
			<div id="box_top">问题详情</div>
			<img id="back" src="/MOBILE/report/map/img/block.png"  onclick="javascript:window.history.back(-1);" style="width:16%;height:35px;position: absolute;top:1px;"/>
			<div id="box_middle">
				<span id="question"><b><%=map.get("QUESTION_TITLE") %></b><br></span>												
			</div>
			<div id="box_middle2">
				<p>
					<%=map.get("QUESTION_CONTENT")%>
				</p>
			</div>
			
			<div id="box_bottom">
				<span id="" style="color:blue;">以上信息是否解决了您的问题？</span>		
			</div>
			<div id="in_kefu" >
				<a >进入客服</a>
			</div>
			
		<%if( user!=null){ 
			for(int i=0;i<user.size();i++){ 
				Map rs = (Map)user.get(i);%>
		
		<div style="display:none">
			<p id="staff_id"><%=rs.get("staff_id")%></p>
			<p id="username"><%=rs.get("username")%></p>
			<p id="mobile_tel"><%=rs.get("mobile_tel")%></p>
			<p id="acronym"><%=rs.get("acronym")%></p>
		</div>
		<%}}%>
		
			<div id="box_end">
				<ul>
					<li><img src="/MOBILE/report/map/img/yes.png"/ id="zan_img"  onclick="assess(this)"></li>
					<li><img src="/MOBILE/report/map/img/no.png"/ id="buzan_img" onclick="assess(this)"></li>
				</ul>
				<ul style="float: left;">
					<li id="zan"><%=map.get("HELPFUL_CNT")%></li>
					<li id="buzan"><%=map.get("UNHELPFUL_CNT")%></li>				
				</ul>
			</div>
		<%}%>	
		</div>
	</body>
	<script type="text/javascript">
	
		$(document).ready(function(){ 
			var staff_id = $("#staff_id").text(); 
			var username = $("#username").text(); 
			var mobile_tel = $("#mobile_tel").text(); 
			var acronym = $("#acronym").text(); 
			$("#in_kefu a:first-child").replaceWith( "<a href="+"http://ltkefu.hnzmtq.com/kefu_isset_record.html?staff_id="+staff_id + "&username="+mobile_tel+"&mobile_tel="+mobile_tel+"&acronym="+ acronym+">进入客服</a>" );
		}); 
	

	
	
		//  赞/不赞数统计

			function assess(a){
				var title = $("#question").find("b").text();
				var id = $(a).attr("ID");
				
				if(id=="zan_img"){
					var zanNum = $("#zan").text();
					zanNum=parseInt(zanNum)+1;
					$("#zan").replaceWith("<li id='zan'>"+zanNum+"</li>");
				}
				if(id=="buzan_img"){
					var buzanNum = $("#buzan").text();
					buzanNum=parseInt(buzanNum)+1;
					$("#buzan").replaceWith("<li id='buzan'>"+buzanNum+"</li>");
				}
				$.ajax({
						type:"post",
						url:'/MOBILE/assessServlet',
						data:{"title":title,"id":id}
					});
			}
			
			
			
	
		
	</script>
</html>