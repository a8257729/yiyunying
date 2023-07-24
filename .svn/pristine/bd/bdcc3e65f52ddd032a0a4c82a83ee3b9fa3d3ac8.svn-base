<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content=" width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
    <title>查询页面</title>
</head>

<style type="text/css">
	*{padding:0px; margin:0px;}
   #tab{width:100%;}
   #tab ul{list-style:none; height:30px;line-
   height:30px;
        border-bottom:2px #C88 solid;}
   #tab ul li{background:#FFF;cursor:pointer;float:left;text-align: center;
    list-style:none height:35px;width:49.3%;line-height:35px;
    border:1px solid #BBB; border-bottom:2px solid #88B2CC;}
   #tab ul li.on{border-top:2px solid gray; border-bottom:2px solid #FFF;}
   #tab div{height:80px;width:100%;line-height:80px;text-align: center;  
             border:1px solid #336699;}
   .hide{display:none;}
   .search_value:focus{border:1px solid orange;}
	table th{
        text-align:center;
        text-align:justify;
        text-justify:distribute-all-lines;
        text-align-last:justify
	}
	th{width:25%;color:#336699;font-size:14px}
	td{color:#336699;font-size:14px;position:absolute;margin-left:2%}
	#end {
			
			border: 1px solid transparent;
			width: 100%;
			height: 180px;
		}
		
		#bind {
			position: absolute;
			border: 1px solid transparent;
			border-radius: 15px;
			background-color: #1c80d5;
			width: 120px;
			height: 50px;
			margin-left:31%;
			margin-top:50px;
			text-align: center;
			line-height: 50px;
		}
		
		
		#unbind {
			position: absolute;
			border: 1px solid transparent;
			border-radius: 15px;
			background-color: #1c80d5;
			width: 80px;
			height: 50px;
			margin-left:58%;
			margin-top:50px;
			text-align: center;
			line-height: 50px;
			
		}
		.black_overlay {
				display: none;
				position: absolute;
				top: 0%;
				left: 0%;
				width: 100%;
				height: 100%;
				background-color: black;
				z-index: 1001;
				-moz-opacity: 0.8;
				opacity: .80;
				filter: alpha(opacity=80);
			}
			
			.white_content {
				display: none;
				position: absolute;
				top: 38%;
				left: 6%;
				width: 80%;
				height: 28%;
				border: 16px solid lightblue;
				background-color: white;
				z-index: 1002;
				overflow: auto;
			}
			
			.top {
				border: 1px solid transparent;
				width: 99%;
				height: 40px;
				text-align: center;
				line-height: 40px;
			}
		
</style>
<script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
   
  window.onload = function(){
    var myTab = document.getElementById("tab");    //整个div
    var myUl = myTab.getElementsByTagName("ul")[0];//一个节点
    var myLi = myUl.getElementsByTagName("li");    //数组
    var myDiv = myTab.getElementsByTagName("div"); //数组	
    for(var i = 0; i<myLi.length;i++){
        myLi[i].index = i;
        myLi[i].onclick = function(){
            for(var j = 0; j < myLi.length; j++){
                myLi[j].className="off";
                myDiv[j].className = "hide";
            }
            this.className = "on";
            myDiv[this.index].className = "show";
        }
      }
    }
</script>
<body>
<div id="box_tops" style="width:100%;height: 33px;border: 1px solid transparent;background-color:#1c80d5;line-height:33px;text-align:center;color:white;font-size:20px;">
			查询页面
</div>
<img  src="/MOBILE/report/map/img/block.png"  onclick="javascript:window.history.back(-1);" style="width:16%;height:35px;position: absolute;top:1px;"/>
<div id = "tab">
        <ul>
        	<li class="on" id="case_left">根据业务账号查询</li>
        	<li class="off" id="case_right">根据MAC查询</li>
        </ul>
    <div id="firstPage" class="show" style="border:1px solid transparent">
           <input  type="text"  id="search_value1" class="search_value"  placeholder="业务号码格式如2351351A@tv"    style="width:220px;height:30px;"/>
		   <input  type="button"  id="bt1" value="查询"    style="width:50px;height:32px;font-size:14px;background-color:#1c80d5;color:white;" />	 
    </div>
    <div id="secondPage" class="hide" style="border:1px solid transparent">
	
           <input  type="text"  id="search_value2" class="search_value"  placeholder="请输入您需要查询MAC"    style="width:220px;height:30px;"/>
		   <input  type="button"  id="bt2" value="查询"    style="width:50px;height:32px;font-size:14px;background-color:#1c80d5;color:white;" />	
    </div>
	
	<span id="cue1" style="position:absolute;top:17%;left:6%;color:red;font-size:14px;display: none;">业务号码内容不能为空</span>
    <span id="cue2" style="position:absolute;top:17%;left:6%;color:red;font-size:14px;display: none;">MAC不能为空</span>
</div>
<div id="rs1" style="border: 1px transparent ;height: auto;display:none;margin-left:12%">
    	
</div>
<div id="rs2" style="border: 1px transparent ;height: auto;display:none;margin-left:12%">
    	
</div>
<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" class="white_content">
			<div style="text-align: right; cursor: default; height: 40px;" id="move">
				<span style="font-size: 16px;" onclick="CloseDiv('MyDiv','fade')">关闭</span>
			</div>
			<div  class="top" >
				<input type="text" id="search_value" name="search_value" style="width:58%;height:22px;" />
				<input type="button" id="bt" value="绑定" style="width:50px;height:29px;font-size:14px;" />
			</div>
		</div>
		
<div id="fade1" class="black_overlay"></div>
		<div id="MyDiv1" class="white_content">
			<div style="text-align: right; cursor: default; height: 40px;" id="move1">
				<span style="font-size: 16px;" onclick="CloseDiv1('MyDiv1','fade1')">关闭</span>
			</div>
			该号码已被绑定，如需绑定，请先解绑！
		</div>
		
		
<div id="end" style="display:none">
		<input type="button" id="bind" value="绑定" />
		<!--<input type="button" id="unbind" value="解绑" />.-->
</div>
</body>
<script type="text/javascript">
	
	//显示弹出层
	function ShowDiv(show_div,bg_div){
		document.getElementById(show_div).style.display='block';
		document.getElementById(bg_div).style.display='block' ;
		var bgdiv = document.getElementById(bg_div);
		bgdiv.style.width = document.body.scrollWidth;
		// bgdiv.style.height = $(document).height();
		$("#"+bg_div).height($(document).height());
	};
	
	
	
	
	//关闭弹出层
	function CloseDiv(show_div,bg_div){
		document.getElementById(show_div).style.display='none';
		document.getElementById(bg_div).style.display='none';
	};

	
	function ShowDiv1(show_div,bg_div){
		document.getElementById(show_div).style.display='block';
		document.getElementById(bg_div).style.display='block' ;
		var bgdiv = document.getElementById(bg_div);
		bgdiv.style.width = document.body.scrollWidth;
		// bgdiv.style.height = $(document).height();
		$("#"+bg_div).height($(document).height());
	};
	
	function CloseDiv1(show_div,bg_div){
		document.getElementById(show_div).style.display='none';
		document.getElementById(bg_div).style.display='none';
	};
	
	
	//选项切换
	$("ul li").click(function(){
		$("#cue1").css("display","none");
		$("#cue2").css("display","none");
	});
	
	
	$("input[type=button]").click(function(){
		var bt_id = $(this).attr("id");//搜索按钮的id
		var search_value = $(this).prev().val();//搜索框的内容
		if(bt_id=="bt1"){
			if(search_value==""){
				$("#bt1").prev().blur();
				$("#cue1").css("display","block");
				$("#bt1").prev().focus(function(){
					$("#cue1").css("display","none");
				});
			}else{
				$("#rs1").css("display","block");
				account(search_value);
			}	
			
		}else if(bt_id=="bt2"){
			if(search_value==""){
				$("#bt2").prev().blur();
				$("#cue2").css("display","block");
				$("#bt2").prev().focus(function(){
					$("#cue2").css("display","none");
				});
			}else{
				$("#rs2").css("display","block");
				mac(search_value);
				$("#end").css("display","none");
			}		
		}
	});
	
	
	
	//根据业务号码查询
	function account(search_value){
		debugger;
		$.ajax({
				type:"get",
				url:"/MOBILE/userInfoServlet",
				data:{"loginAccount":search_value},
				contentType:"json",
				success:function(data){
					debugger;
					//alert("回调成功!");
					var rs = eval('('+data+')');
					if(rs.loginAccount==""){
						$("#rs1").html("<span>该号码不存在!</span>");
					}else{
					var status = "";
					var platformid= "";
					if(rs.status=="1"){
						status = "正常";
					}else if(rs.status=="2"){
						status = "停机";
					}else if(rs.status=="3"){
						status = "销户";
					}
					
					if(rs.platformid=="1"){
						platformid="华为";
					}else if(rs.platformid=="2"){
						platformid="中兴";
					}
					/*switch(rs.status){
						case 1:status="正常";break;
						case 2:status="停机";break;
						case 3:status="销户";break;
						default:status="";
					}
					switch(rs.platformid){
						case 1:platformid="华为";break;
						case 2:platformid="中兴";break;
						default:platformid="";
					}*/
					
					var html="";
						html+= "<table border='0px ' cellspacing='0' cellpadding='0' style='width:100%'>"+
									"<tr >"+
											"<td colspan='2' style='text-align:left;margin-button:3%;font-size:18px'><b></b></td>"+
									"</tr>"+
									"<tr>"+
											"<th >IPTV账号:</th>"+
											"<td id='account'>"+rs.loginAccount+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th >IPTV状态:</th>"+
											"<td>"+status+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>手机号:</th>"+
											"<td >"+rs.phoneNum+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th >高清标识:</th>"+
											"<td >"+rs.termtype+"</td>"+
									"</tr>"+
									"<tr id='mac_tr'>"+
											"<th >绑定MAC:</th>"+
											"<td id='mac'>"+rs.mac+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th >区域标识:</th>"+
											"<td id='areano'>"+rs.areano+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th >归属平台:</th>"+
											"<td >"+platformid+"</td>"+
									"</tr>"+
								"</table>"
									;
					
					$("#rs1").html(html);					
					$("#end").css("display","block");
					}
				},
				error:function(){
					alert("请求失败!");
				}
			});
			
	}
	
	
	
	
	//根据MAC查询
	function mac(search_value){
		debugger;
		$.ajax({
				type:"get",
				url:"/MOBILE/userInfoServlet",
				data:{"mac":search_value},
				contentType:"json",
				success:function(data){
					//alert("回调成功!");
					debugger;
					var rs = eval('('+data+')');
					if(rs.username==""){
						$("#rs2").html("<span>该MAC不存在!</span>");
					}else{
					var groupid ="";
					var platformType ="";
					if(rs.groupid=="0"){
						groupid="公众";
					}else if(rs.groupid=="1"){
						groupid="酒店";
					}
					
					if(rs.platformType=="1"){
						platformType="华为";
					}else if(rs.platformType=="2"){
						platformType="中兴";
					}
					/*switch(rs.groupid){
						case 0:groupid="公众";break;
						case 1:groupid="酒店";break;
						default:groupid="";
					}*/
					var html="";
						html+= "<table border='0px' cellspacing='0' cellpadding='0'>"+
									"<tr>"+
											"<td colspan='2' style='text-align:left;font-size:18px'><b></b></td>"+
									"</tr>"+
									"<tr >"+
											"<th>绑定MAC:</th>"+
											"<td id='mac'>"+rs.mac+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>IPTV账号:</th>"+
											"<td>"+rs.username+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>IPTV密码:</th>"+
											"<td>"+rs.password+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>平台标识:</th>"+
											"<td>"+platformType+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>分组标识:</th>"+
											"<td>"+groupid+"</td>"+
									"</tr>"+
									"<tr>"+
											"<th>区域标识:</th>"+
											"<td>"+rs.areano+"</td>"+
									"</tr>"+
								"</table>"
									;

					$("#rs2").html(html);
					$("#end").css("display","none");
					}
				},
				error:function(){
					alert("请求失败!");
				}
				
			});
	}
	
	
	
	//显示业务号码结果
	$("#case_left").click(function(){
		$("#rs1").css("display","block");
		$("#end").css("display","block");
		$("#rs2").css("display","none");
	});
	
	//显示mac结果
	$("#case_right").click(function(){
		$("#rs2").css("display","block");
		$("#rs1").css("display","none");
		$("#end").css("display","none");
	});
	
	
	
	//绑定
	$("#bind").click(function(){
		$("#unbind_rs").remove();//移除解绑的结果
		var mac=$("#mac").text();
		

		if(mac!=""&&mac!=null){
			ShowDiv1('MyDiv1','fade1');
			
		}else{
		ShowDiv('MyDiv','fade');
		
		}	
	});
	
	
	$("#bt").click(function(){
			var bind_result = $("#bind_rs td").text();//获取绑定的内容
			$("#bind_rs").remove();//移除绑定的结果
			
			var loginAccount =$("#account").text();
			var areano =$("#areano").text();
			var mac=$("#mac").text();
			var search_value = $("#search_value").val();
			$.ajax({
						type:"get",
						url:"/MOBILE/bindServlet",
						data:{"mac":search_value,"loginAccount":loginAccount,"areano":areano},
						success:function(data){
							var rs = eval('('+data+')');
							CloseDiv('MyDiv','fade');
							var	tr ="<tr id='bind_rs'>"+
										"<th>绑定结果:</th>"+
										"<td>"+rs.resultDesc+"</td>"+
									"</tr>";
							$("table").append(tr);	
							/*if(bind_result==""){
								$("table tr").eq(-1).remove();
								
							}else{
								$("#bind_rs").replaceWith("<tr id='bind_rs'><th>绑定结果:</th><td>"+rs.resultDesc+"</td></tr>");
							}*/	
							
							if(rs.resultCode=="000"){
								$("#mac").replaceWith("<td id='mac'>"+search_value+"</td>");
							}
							

						}
					});				
		});
	
	
	
	//解绑
		/*$("#unbind").click(function() {
	
			$("#bind_rs").remove();//移除绑定的结果
			$("#unbind_rs").remove();//移除解绑的结果
			
			
			//发送到后台的参数
			var mac = ""
			if($("#mac").text()!=null){
				mac = $("#mac").text();
			}
			var mac = $("#mac").text();
			var loginAccount =$("#account").text();
			var areano =$("#areano").text();
			
			
			
				
				$.ajax({
						type:"get",
						url:"/MOBILE/unBindServlet",
						data:{"mac":mac,"loginAccount":loginAccount,"areano":areano},
						success:function(data){	
							var rs = eval('('+data+')');
							var	tr ="<tr id='unbind_rs'>"+
										"<th>解绑结果:</th>"+
										"<td >"+rs.resultDesc+"</td>"+
									"</tr>";
							
							$("table").append(tr);
							if(rs.resultCode=='000'){
								$("#mac").replaceWith("<td id='mac'></td>");
								
							}
							//$("#bind_rs").css("display","none");
						}
					});					
				
		});*/
	
	
	
	
	
	
	//根据业务号码查询用户信息
	/*$("#bt1").click(function(){
		var search_value = $(this).prev().val();
		if(search_value==""){
			$(this).prev().blur();
			$("#cue1").css("display","block");
			$(this).prev().focus(function(){
				$("#cue1").css("display","none");
			});
		}else{
			location.href="/MOBILE/userInfoServlet?loginAccount="+search_value;
		}	
		
	});*/
	
	//根据MAC查询用户信息                                                                                                                                                                                                                                                                                                                             
	/*$("#bt2").click(function(){
		var search_value = $(this).prev().val();
		if(search_value==""){
			$(this).prev().blur();
			$("#cue2").css("display","block");
			$(this).prev().focus(function(){
				$("#cue2").css("display","none");
			});
		}else{
			location.href="/MOBILE/userInfoServlet?mac="+search_value;
		}
	});*/
	
	
</script>
</html>

