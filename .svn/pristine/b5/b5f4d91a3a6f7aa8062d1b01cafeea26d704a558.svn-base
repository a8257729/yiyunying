/** by tang.lin 2011-1-4 聊天 **/
function GroupChat(groupId){
	this.welcomeWord = '<B>欢迎使用ZTESOFT IM....</B>';
	this.groupId = groupId;
	this.mainChatWin = function(){
		var centerPanel = this.initCenterPanel();
		var eastPanel = this.initEastPanel();
		var mainPanel = new Ext.Panel({
			region: 'center',
			title: item.text,
			closable:false,
			draggable:false,
            plain:true,
            resizable:false,
            collapsible:false, 
            layout:'border',
            items:[centerPanel,eastPanel]
		});
		var mainChatWin = new Ext.Viewport({
       		id:'chatWin_G_'+groupId,
	        layout:'border',
            items:[mainPanel]
        });
		return mainChatWin
	}
	//左边主面板初始化
	this.initCenterPanel = function(){
		var contentPanel = this.initContentPanel();
		var sendEditor = this.initSendEditor();
		var centerPanel = new Ext.Panel({
			region: 'center',
			layout:'border',
			items:[contentPanel,sendEditor],
			bbar :[{
						id:'sendBtn_G_'+groupId,
						text:'发  送',
						width:80,
						onClick:function(){
							
		            		chat.sendMessage();
		            	}
				},{
					id:'closeBtn_G_'+groupId,
					text:'关  闭',
					width:80,
					onClick:function(){
		            	art.dialog.close();
		            }
				},{
					id:'logBtn_G_'+groupId,
					text:'查看聊天记录',
					width:80,
					onClick:function(){
						chat.showChatLog();
		            }
				}]
		});
		return centerPanel;
		 
	}
	//
	this.initContentPanel = function(){
		var receiveEditor = this.initReceiveEditor();
		 var contentPanel = new Ext.Panel({
		 		region: 'center',
		 		items:[receiveEditor]
         });
		 return contentPanel;
	}
	//接收聊天消息区

	this.initReceiveEditor = function(){
		receiveEditor = new Ext.Panel({
			region: 'south',
			id:'receiveEditor_G_'+groupId,
			//html:'<div style="height:'+getMainChatWinHeight()*0.59+'px;width:'+getMainChatWinWidth()*0.69+'px; overflow-y:auto"  id=div_'+groupId+' >'+this.welcomeWord+'</div>',
			html:'<div style="height:260px;width:480px; overflow-y:auto"  id=div_'+groupId+' >'+this.welcomeWord+'</div>',
			enableSourceEdit:false,
		 	enableLinks :false,
		 	enableColors :false,
		 	enableFormat :false,
		 	enableFontSize :false,
		 	enableColors :false,
		 	enableAlignments :false,
		 	enableLists :false,
		 	enableSourceEdit :false,
		 	enableLinks :false,
		 	enableFont :false,
		 	readOnly:true

		});
		
		return receiveEditor;
		
	}
	//发送消息区
	this.initSendEditor = function(){
//		var sendEditor = new Ext.form.HtmlEditor({
//					region: 'south',
//					id:'sendEditor_G_'+groupId,
//					enableSourceEdit:false,
//					enableLinks :false,
//					height:getMainChatWinHeight()*0.3,
//					value:' '
//		});
		var sendEditor = new Ext.form.HtmlEditor({
			  id:'sendEditor_G_'+groupId,
			  region: 'south',
		      autoHeight:false,
		      height:getMainChatWinHeight()*0.3,
		      width:510,
		      fieldLabel:'HTML字段',
		      createLinkText:'创建超链接',
		      defaultLinkValue:"http://www.",
		      enableAlignments:true,
		      enableColors:false,
		      enableFont:true,
		      enableFontSize:true,
		      enableFormat:true,
		      enableLinks:false,
		      enableLists:false,
		      enableSourceEdit:false,
		      fontFamilies:['宋体','隶书','黑体'],
		      value:''
		      });
		return sendEditor;
		
	}
	//发送消息
	this.sendMessage = function(){
		//2.不为空时可以发送消息，通过pushlet发送到指定消息订阅人

		if(Ext.getCmp('sendEditor_G_'+groupId).getValue()==''){//判断消息是否为空
				alert('发送消息不能为空');
		}else{
			//获取发送内容

			var sendMessage = Ext.getCmp('sendEditor_G_'+groupId).getValue();
			//3.往服务器发送消息

			window.opener.PL.publish('/group/'+groupId,'message='+ sendMessage);
			//4.在本地文本域显示历史聊天记录
			//时间
			var now = new Date()
			var time = now.getYear()+"-"+(now.getMonth()+1)+"-"+now.getDay()+ " " + now.getHours()
		        + ":" + now.getMinutes() + ":"
		        + now.getSeconds() + "";
			var sender = session1.staff.staffName
			chat.setReceiveEditorMsg(sendMessage,time,sender);
			//5.清空本地文本发送域
			Ext.getCmp('sendEditor_G_'+groupId).setValue('');
		}
	}
	//本地显示聊天消息
	this.setReceiveEditorMsg  = function(msg,time,sender){
		//获取已有内容
		var oldContent = document.all['div_'+groupId].innerHTML;
		//拼装消息
		msg =  oldContent+' <br><font size=3><b>'+sender+' '+time+' </b></font><br>'+msg +"";
		document.all['div_'+groupId].innerHTML = msg;
		document.all['div_'+groupId].scrollTop= document.all['div_'+groupId].scrollHeight;
	
	
		
	}
	//右边面板显示
	this.initEastPanel = function(){
		var enorth = new Ext.Panel({
			region:'north',
			title:'群公告',
			html:'<div id=newsDiv_'+groupId+'></div>',
			height:sheight*0.2537
		})
		var ecenter = new Ext.tree.TreePanel({
			id:'staffsList',
			region:'center',
			title:'群成员',
	        useArrows: true,
	        autoScroll: true,
	        animate: true,
	        enableDD: false,
	        containerScroll: true,
	        rootVisible:false,
	        border: false,
	        dataUrl: '/MOBILE/ExtPagingServlet?actionName=QryStaffByGroupIdAction&imStaffBiggroupId='+this.groupId,
	       	root:{
	            nodeType: 'async',
	            draggable: false,
	            iconCls:'group_icon',
	            id: 'src'
	        }
	    });
	   
		var eastPanel = new Ext.Panel({
				region: 'east',
				layout: 'border',
				items:[enorth,ecenter],
				width:getMainChatWinWidth()*0.3
		});
		return eastPanel;
		
	}
	//加载群信息
	this.initGroupInfo = function(){
		//加载公告信息
		var param = {'imStaffBiggroupId':this.groupId};
		var record = invokeAction('/immanager/QryBigGroupByIdAction',param);
		
		document.all['newsDiv_'+groupId].innerHTML = record.groupNews;
	}
	this.getUnReadMsgForGroup = function(){
		
		var qryObj = {
				  senderStaffId:this.groupId,
				  msgBelongStaffId:session1.staff.staffId
		};
		
		var msgList = invokeAction('/immanager/QryUnReadByStaffAction',qryObj);
		for(var i=0;i<msgList.length;i++){
			chat.setReceiveEditorMsg(msgList[i].msgContent,msgList[i].msgDate,msgList[i].senderStaffName);
		}
		if(msgList!=null){
			var msgList = invokeAction('/immanager/UpdateMsgReadStateAction',qryObj);
		}
		

	}
	this.showChatLog = function(){
		parent.showGroupChatLog(this.groupId);
		//window.showModalDialog("groupChatLog.jsp",this.groupId,"dialogHeight="+sheight*0.4375+"px;dialogWidth="+swidth*0.4687+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
	}
	this.openImMsgWin = function(){
		var msgList  =  this.getUnReadMsgForGroup();
		var count = msgList!=null?msgList.length:0;
		opener.openImMsgWin("未读信息("+count+")",msgList.msgListHtml);
	}
	//设置未读消息状态
	this.updateReadState = function(){
		var qryObj = {
				  senderStaffId:this.groupId,
				  msgBelongStaffId:session1.staff.staffId,
				  readState:1,
				  oldReadState:0
				  
		};
		var msgList = invokeAction('/immanager/UpdateReadStateAction',qryObj);
	}
}

function getMainChatWinWidth(){
	
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.548;
	}else{
		return screen.availWidth*0.596;
	}
}

function getMainChatWinHeight(){
	
	if(sheight>=800){
		return screen.availHeight*0.70;
	}else{
		return screen.availHeight*0.645;
	}
}