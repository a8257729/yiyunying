<%@ page language="java"  contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>湖南联通IPTV系统</title>
<link rel="stylesheet" type="text/css" href="/MOBILE/report/map/css/index.css"/>
</head>
<script src="/MOBILE/report/map/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
<body>
	
		<% List list = (List)request.getSession().getAttribute("questionLibrary"); %>
		<% List user = (List)request.getSession().getAttribute("user"); %>
	<div id="index">
		<div id="log">
			<div id="box_tops">
	
			</div>
			<img src="/MOBILE/report/map/img/log.png"/ >
			<span id="biaoti">湖南联通IPTV系统</span>
			<img id="back" src="/MOBILE/report/map/img/block.png"  onclick="javascript:window.history.back(-1);" style="width:16%;height:35px;position: absolute;top:2px;"/>
		</div>
		<div id="question">
			<div id="leftbox">
								 
						<a >进入客服</a>				 
							
			</div>
			<div id="rightbox" >
									  
					<a id="aa">问题题库</a>							                                                                                                                                                                                                              
							
			</div>		
		</div>
		<div id="hot" >
			<span style="font-size:15px;color:red;">热门问题</span>	
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
	
	<%
	if(list!=null){
	for(int i=0;i<10;i++){ 
		 Map map = (Map)list.get(i);
	%>
		<div class="tr">
			<span ><%=i+1%>、<span><%=map.get("QUESTION_TITLE") %></span></span>
		</div>
	<%}
	}%>
		
	
		
	


</body>
<script type="text/javascript">
		
		$(document).ready(function(){ 
			var staff_id = $("#staff_id").text(); 
			var username = $("#username").text(); 
			var mobile_tel = $("#mobile_tel").text(); 
			var acronym = $("#acronym").text(); 
			$("#leftbox a").replaceWith( "<a href="+"http://ltkefu.hnzmtq.com/kefu_isset_record.html?staff_id="+staff_id + "&username="+mobile_tel+"&mobile_tel="+mobile_tel+"&acronym="+ acronym+">进入客服</a>" );
		}); 
		
 		
 		//问题题库
		$("#rightbox").click(function(){
 			location.href="/MOBILE/classNameServlet";
 		})
 		
 		//热门问题
 		$(".tr").click(function(){
 			//获取问题名称
 			var hot_question = $(this).find("span span").text();	
			location.href="/MOBILE/detailsServlet?search_value="+hot_question;
 			
 		});
		function gb(){
			window.close();
		}
</script>
</html>