<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	
<title>问题库</title>
<link rel="stylesheet" type="text/css" href="/MOBILE/report/map/css/problem_library.css" />
</head>
<script src="/MOBILE/report/map/js/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
<body>

	<%List className = (List)request.getSession().getAttribute("className") ;%>
	<div id="box">
		<div id="box_tops">
			问题库
		</div>
		<img id="back" src="/MOBILE/report/map/img/block.png"  onclick="javascript:window.history.back(-1);" style="width:16%;height:35px;position: absolute;top:1px;"/>
		<div id="box_top">
			<ul>
				<li><input type="text" name="search_case" id="context" value="" style="width:190px;height:22px;border:2px solid gainsboro;margin-left:25%;margin-top:21px" /></li>				
				<li><input type="button" name="search_button" id="bt" value="搜索" style="width:60px;height:28px;background-color:#1c80d5;margin-left:48px;margin-top:20px;" /></li>
					
			</ul>
		</div>
		
		<div id="box_middle">
			<ul>
				<li>
					<%
						if(className!=null){
						for(int i=0;i<className.size();i++){ 
						Map map = (Map)className.get(i);
						
					%>
					<div class="rectangle_case">					
							<span class="span_font"><%=map.get("CLASS_NAME") %></span>
					</div>
					
					<%}
					}%>
				</li>
			</ul>
		</div>

	</div>
</body>
<script type="text/javascript">
	//搜索
	$("#bt").click(function(){
			//获取输入框中的内容
			var hot_question = $("#context").val();
			//只显示搜索到的内容
			
			if(hot_question==""||hot_question==null){
				alert("您搜索的内容不能为空，请重新输入后点搜索！");
			}else{
				location.href="/MOBILE/blurSelTitleServlet?hot_question="+hot_question;
			}
		});

	$(".rectangle_case").click(function() {
		var class_name = $(this).find("span").text();
		$("#box_middle").css("display","none");
		$.ajax({
				type:"get",
				url:"/MOBILE/blurSelTitleServlet",
				data:{"class_name":class_name},
				contentType:"json",
				success:function(data){
					var rs = eval('('+data+')');
					var html="";
					for(var i =0;i<rs.length;i++){
						html+= "<div class='tr'>"+
									"<tr>"+
											"<span ><span>"+(i+1)+"、</span>"+rs[i].QUESTION_TITLE+"</span>"+
									"</tr>"+
								"</div>";				
					}
					$("#box_middle").after(html);
					$(".tr").click(function(){
					//获取问题名称
						var search_value = $(this).find("span span").text();
						location.href="/MOBILE/detailsServlet?search_value="+search_value;
					});

				},
				error:function(){
					alert("请求失败!");
				}
			});
		
	});
	
	
		
	
		
</script>
</html>