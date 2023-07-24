/** by tang.lin 2010-11-5 聊天 **/
 String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {   
　 if (!RegExp.prototype.isPrototypeOf(reallyDo)) {   
    return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);   
   } else {   
        return this.replace(reallyDo, replaceWith);   
    }
} 
 
function Chat(){
	this.welcomeWord = '<B>欢迎使用ZTESOFT IM....</B>';
	//初始化聊天窗口
	this.initChatWin = function(){
		//看是否已经打开窗口，对每个好友只能打开一个聊天窗口
		var chatWin = Ext.getCmp('chatWin_'+staffId);
		if(Ext.isEmpty(chatWin)){
			chatWin = this.mainChatWin();
			chatWin.show();
		}
		//查看所有未读消息
		this.getUnReadMsgForStaff();
		//设置消息状态为已读
		this.updateReadState();
		//设置未读消息提醒
		 window.opener.imOper.openImMsgWin();
		
	}
	//设置未读消息状态
	this.updateReadState = function(){
		var qryObj = {
				  senderStaffId:staffId,
				  msgBelongStaffId:session1.staff.staffId,
				  readState:1,
				  oldReadState:0
				  
		};
		var msgList = invokeAction('/immanager/UpdateReadStateAction',qryObj);
	}
	//查询对此用户的未读消息
	this.getUnReadMsgForStaff = function(){
		var qryObj = {
				  senderStaffId:staffId,
				  msgBelongStaffId:session1.staff.staffId
		};
		var msgList = invokeAction('/immanager/QryUnReadByStaffAction',qryObj);

		for(var i=0;i<msgList.length;i++){

			chat.setReceiveEditorMsg(msgList[i].msgContent,msgList[i].msgDate,msgList[i].senderStaffName);
		}
		if(msgList!=null){
			this.updateReadState();
		}

	}
	//主窗口
	this.mainChatWin = function(){

		var centerPanel = this.initCenterPanel();
		//var eastPanel = this.initEastPanel();
		var mainPanel = new Ext.Panel({
			region: 'center',
			title: item.text,
			closable:false,
            draggable:false,
            plain:true,
            resizable:false,
            collapsible:false, 
            layout:'border',
            items:[centerPanel]//,eastPanel]
		});
		var mainChatWin = new Ext.Viewport({
       		id:'chatWin_'+staffId,
       		el:'chat',
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
						id:'sendBtn_'+staffId,
						text:'发  送',
						width:80,
						handler : chat.sendMessage
				},{
					id:'closeBtn_'+staffId,
					text:'关  闭',
					width:80,
					onClick:function(){
		            	//window.close();
					    art.dialog.close();
		            }
				},{
					id:'logBtn_'+staffId,
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
		 		//width:getMainChatWinWidth()*0.77,
		 	 	items:[receiveEditor]
         });
		 return contentPanel;
	}
	//接收聊天消息区
	this.initReceiveEditor = function(){
		receiveEditor = new Ext.Panel({
			region: 'south',
			id:'receiveEditor_'+staffId,
			html:'<div style="height:270px;width:590px; overflow-y:auto"  id=div_'+staffId+' >'+this.welcomeWord+'</div>',
			//html:'  <div style=\"overflow:auto;width:500px;border:1px solid black\"><table style=\"width:500px;height:300px\" ><tr><td>内容</td></tr></table></div>',
			value:this.welcomeWord,
//			enableSourceEdit:false,
//		 	enableLinks :false,
//		 	enableColors :false,
		 	enableFormat :false,
//		 	enableFontSize :false,
//		 	enableColors :false,
//		 	enableAlignments :false,
//		 	enableLists :false,
//		 	enableSourceEdit :false,
//		 	enableLinks :false,
//		 	enableFont :false,
		 	readOnly:true
		 	

		});
		return receiveEditor;
		
	}
	//发送消息区
	this.initSendEditor = function(){
		//var sendEditor = new Ext.form.HtmlEditor({
		//			region: 'south',
		//			id:'sendEditor_'+staffId,
		//			enableSourceEdit:false,
		//			enableLinks :false,
		//			height:getMainChatWinHeight()*0.3,
		//			value:''
		//});
		var sendEditor = new Ext.form.HtmlEditor({
			  id:'sendEditor_'+staffId,
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

		//Ext.getCmp('sendEditor_'+staffId).onEditorEvent = function (e) {  

		//	       this.updateToolbar();  
		//	        var keyCode = (document.layers) ? keyStroke.which : e.keyCode;  
		//	        if (keyCode == 13 && e.ctrlKey) chat.sendMessage();   
		//	   } 
		return sendEditor;
		
	}
	//发送消息
	this.sendMessage = function(){
		//2.不为空时可以发送消息，通过pushlet发送到指定消息订阅人
		if(Ext.getCmp('sendEditor_'+staffId).getValue()==''){//判断消息是否为空
				Ext.MessageBox.show({
													           title: '消息',
													           msg: "发送消息不能为空",
													           buttons: Ext.MessageBox.OK,
													           width:250,
													           icon: Ext.MessageBox.INFO
													       });
				
		}else{
			//获取发送内容
			var sendMessage = Ext.getCmp('sendEditor_'+staffId).getValue();
			//3.往服务器发送消息
			window.opener.PL.publish('/user/chat,hb','p_to='+ staffId+ '&staffName='+session1.staff.staffName+'&message='+ sendMessage);
			//4.在本地文本域显示历史聊天记录
			//时间
			var now = new Date()
			var time = now.getYear()+"-"+(now.getMonth()+1)+"-"+now.getDay()+ " " + now.getHours()
		        + ":" + now.getMinutes() + ":"
		        + now.getSeconds() + "";
			var sender = session1.staff.staffName
			chat.setReceiveEditorMsg(sendMessage,time,sender);
			//5.清空本地文本发送域
			Ext.getCmp('sendEditor_'+staffId).setValue('');
		}
	}
	//本地显示聊天消息
	this.setReceiveEditorMsg  = function(msg,time,sender){
		//获取已有内容
		var oldContent = document.all['div_'+staffId].innerHTML;
		//拼装消息
		msg =  oldContent+' <br><b>'+sender+' '+time+' </b><br>'+msg;
        //替换STRONG
		msg = msg.replaceAll("<STRONG>","<b>").replaceAll("</STRONG>","</b>");
		//替换EM
		msg = msg.replaceAll("<EM>","<i>").replaceAll("</EM>","</i>");
		document.all['div_'+staffId].innerHTML = msg;
		document.all['div_'+staffId].scrollTop= document.all['div_'+staffId].scrollHeight;
		
	}
	//右边面板显示
	this.initEastPanel = function(){
		var enorth = new Ext.Panel({
			region:'north',
			bodyStyle:"background-image:url('../IM/image/CANGR6A1CABB.jpg');background-repeat:no-repeat;background-position:center;",
			height:sheight*0.3537
		})
		var ecenter = new Ext.Panel({
			region:'center',
			bodyStyle:"background-image:url('../IM/image/CANGR6A1CABB.jpg'); background-repeat:no-repeat;background-position:center;"
		})
		var eastPanel = new Ext.Panel({
				region: 'east',
				layout: 'border',
				items:[enorth,ecenter],
				width:getMainChatWinWidth()*0.2
		});
		return eastPanel;
		
	}
	//查看聊天记录
	this.showChatLog = function(){
		parent.showChatLog(staffId);
		//window.showModalDialog("chatLog.jsp",staffId,"dialogHeight="+sheight*0.4375+"px;dialogWidth="+swidth*0.4687+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
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
		return screen.availHeight*0.72;
	}else{
		return screen.availHeight*0.66;
	}
}