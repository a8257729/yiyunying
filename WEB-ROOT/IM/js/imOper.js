/** by tang.lin 2010-10-29 IM即时聊天功能操作**/
//定义数组删除方法
Array.prototype.remove_chatwin=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){return false;} 
    for(var i=0,n=0;i<this.length;i++) 
    { 
        if(this[i]!=this[dx]) 
        { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
} 

function openImMsgWin(title,msg){

	 if(!Ext.isEmpty(Ext.getCmp('imMsgWin'))){
		 if(msg==null){
			 Ext.getCmp('imMsgWin').close();
		 }else{
			 Ext.getCmp('imMsgWin').setTitle(title);
			 document.getElementById('msgDiv').innerHTML=msg;
		 }
		
		 return;
	 }
	 if(msg==null){
		 return;
	 }

	 var msgPanel = new Ext.Panel({
		 border:false,
		 html:'<MARQUEE id=demo onmouseover=demo.stop() style="overflow:hidden;height:20px;border:0px solid #dde5bc;" onmouseout=demo.start() scrollAmount=1 direction=up><div id=msgDiv>'+msg+'</div></MARQUEE>',
		 region: 'center'
	 });
	 var closeBtn = new Ext.Button({
		 region: 'east',
		 text:'关闭',
		 onClick:function(){
		 	Ext.getCmp('imMsgWin').close();
	 	 }	 
	 });
	 var imMsgWin = new Ext.Window({
		 	id:'imMsgWin',
		 	title:title,
	        width:300,
	        height:55,
	        layout:'border',
	        resizable:false,
	        plain :true,
	        closable :true,
	        items:[msgPanel]
	    });
		//imMsgWin.alignTo(this,'c');
		imMsgWin.show(this);
	
	 
}
function ImOper(){
	this.item;
	this.chatWins=[];
	this.chatWinIds=[];
	this.groupSubs ='';
	this.unReadMsgs = [];
	//通知服务器上线
	this.initOper = function(){
		var staffId = session1.staff.staffId;
		//this.serStaffId = serStaffId;
		PL._init();
		this.getGroupSubs();
		var subs = '/user,hb,'+session1.staff.staffId;

		PL.joinListen(subs,'staffId='+staffId);
		PL.subscribe(this.groupSubs,'group');
		PL.publish('/user/login','message = '+session1.staff.staffName+'login...');
		PL.publish('/user/onlineStateChange','staffId='+session1.staff.staffId+'&stateId='+1+'&staffName='+session1.staff.staffName);
		this.openImMsgWin();
		
	}
	this.openImMsgWin = function(){
		var msgList  =  this.getUnReadMsg();
		var count = msgList!=null?msgList.length:0;
		
		//opener.openImMsgWin("未读信息("+count+")",msgList.msgListHtml);

	    openImMsgWin("未读信息("+count+")",msgList.msgListHtml);
		
	}
	//查询应该订阅的群主题
	this.getGroupSubs = function(){
		var groupSubs='';
		var groups = invokeAction('/immanager/QryBigGroupByStaffIdXAction',{'staffId':session1.staff.staffId});
		if(!Ext.isEmpty(groups)&&!Ext.isEmpty(groups.length)){
			
			for(var i=0;i<groups.length;i++){
				if(i==groups.length-1){
					groupSubs+='/group/'+groups[i].imStaffBiggroupId;
				}else{
					groupSubs+='/group/'+groups[i].imStaffBiggroupId+',';
				}
			}
		}
		this.groupSubs = groupSubs;
	
	}
	//查询未读的消息
	this.getUnReadMsg = function(){
		var qryObj = {
					  receiveStaffId:session1.staff.staffId,
					  msgBelongStaffId:session1.staff.staffId
					  
					 };
		var msgList = invokeAction('/immanager/QryUnReadByStaffAction',qryObj);
		msgList.msgListHtml = "";
	
		if(msgList==null||msgList.length==0){
			msgList.msgListHtml =null;
		}else{
			for(var i=0;i<msgList.length;i++){
				var item ={leaf:true,id:msgList[i].senderStaffId};
				if(msgList[i].msgType==1){
					//msgList.msgListHtml+="<a href='#' onclick='imWin.imOper.openChatWin({leaf:true,text:\""+msgList[i].senderStaffName+"\",id:"+msgList[i].senderStaffId+"})'>"+msgList[i].senderStaffName+">>"+":"+msgList[i].msgDate+" "+msgList[i].msgContent+"</a><br>"
					msgList.msgListHtml+="<a href='javascript:imOper.openChatWin({leaf:true,text:\""+msgList[i].senderStaffName+"\",id:"+msgList[i].senderStaffId+"})'>"+msgList[i].senderStaffName+">>"+":"+msgList[i].msgDate+" "+msgList[i].msgContent+"</a><br>"
				   // msgList.msgListHtml+="<div onclick='imOper.openChatWin({leaf:true,text:\""+msgList[i].senderStaffName+"\",id:"+msgList[i].senderStaffId+"})'>"+msgList[i].senderStaffName+">>"+":"+msgList[i].msgDate+" "+msgList[i].msgContent+"</div><br>";
				}else if(msgList[i].msgType==2){
					//msgList.msgListHtml+="<a href='#' onclick='imWin.imGroupOper.openGroupChatWin({leaf:true,text:\""+msgList[i].imStaffBiggroupName+"\",id:"+msgList[i].imStaffBiggroupId+"})'>"+'群消息'+">>"+":"+msgList[i].msgDate+" "+msgList[i].msgContent+"</a><br>"
					msgList.msgListHtml+="<a href='javascript:imGroupOper.openGroupChatWin({leaf:true,text:\""+msgList[i].imStaffBiggroupName+"\",id:"+msgList[i].imStaffBiggroupId+"})'>"+'群消息'+">>"+":"+msgList[i].msgDate+" "+msgList[i].msgContent+"</a><br>"
				}
				
			}
		}
		
		return msgList;
	}
	this.onEvent = function(event){
		//alert("onEvent");

	}
	this.onData = function(event){//所有的事件处理
		//接收状态改变消息
//alert(Ext.getCmp("onlineStateBar").getValue);
//debugger;
//alert("event.arr.p_subject2==="+event.arr.p_subject);
		if(event.arr.p_subject=='/user/onlineStateChange'){
//			if (session1.staff.staffId ==event.arr.staffId){
//				//alert("in");
//				
//				if (session1.staff.remoteAddr !=event.get("remoteAddr")){
//					//alert("同一账号IM已经从ip:"+event.get("remoteAddr")+"登录,请检查 ！");
//					Ext.MessageBox.show({
//				           title: '错误',
//				           msg: "同一账号IM已从其它主机登录,请检查 ",
//				           buttons: Ext.MessageBox.OK,
//				           width:250,
//				           icon: Ext.MessageBox.ERROR
//				       });
//					var menu = Ext.getCmp("onlineStateBar");
//					menu.setText("离线");
//				}
//			}
			//alert("staffId==="+event.arr.staffId);
			//alert("session1.staff.remoteAddr===="+event.get("remoteAddr"));
			imOper.receiveOnlineStateChange(event);
		}	
		
		//接收个人消息
		if(event.arr.p_subject=='/user/chat,hb'){
			if(event.arr.p_to==session1.staff.staffId){
				imOper.receiveChatMsg(event);
				
			}
			
		}
		//接收群消息
		if(imOper.groupSubs.search(event.arr.p_subject)!=-1){
			imGroupOper.receiveGroupMsg(event);
		}
	}
	//判断聊天窗口是否打开
	this.isChatWinOpen = function(id){
		//for(var i=0;i<this.chatWinIds.length;i++){
		//	if(this.chatWinIds[i]== id){
		//		return true
		//	}
		//}
		var id= "chat"+id;
		var array= art.dialog.list;	    
	    var api=array[id];
	    
	    if (api) return true;
	    
		return false;
	}
	
	//接收聊天消息
	this.receiveChatMsg = function(event){
		//判断聊天窗口是否打开，如果打开聊天窗口直接在窗口显示消息
		if(parent.isChatWinOpen(event.arr.p_from)){

               parent.getUnReadMsgForStaff(event.arr.p_from);
			//for(var i=0;i<this.chatWins.length;i++){
				//parent.getChatWin(p_from).chat.getUnReadMsgForStaff();
				//if(this.chatWins[i].name == event.arr.p_from){
				//	this.chatWins[i].chat.getUnReadMsgForStaff();
				//}
			//}
		}else{//未打开聊天窗口在未读信息窗口显示消息
			imOper.openImMsgWin();
		}
		
	}
	
	//接收状态改变消息
	this.receiveOnlineStateChange = function(event){

		//刷新在线列表
		if(!Ext.isEmpty(Ext.getCmp('onlineList'))){

			Ext.getCmp('onlineList').getRootNode().reload();
			Ext.getCmp('onlineList').getRootNode().expand(true);
		}

		//判断是否好友,是自己的好友则刷新好友树
		if(!Ext.isEmpty(Ext.getCmp('groupTree').getNodeById(event.arr.staffId))){
			//刷新好友树
			Ext.getCmp('groupTree').getRootNode().reload();
			Ext.getCmp('groupTree').getRootNode().expand(true);
			
		 var msg = imOper.getMsg(1,event.arr.staffName,event.arr.stateId);
		 //提醒好友消息
		 var tips= new Ext.ToolTip({
			        target: event.arr.staffId,
			        anchor: 'bottom',
			        anchorOffset: 85, 
			        html: msg
		  });
		 tips.showAt([50,80]); 
		
		}
		
	}
	//离线
	this.leaveLine = function(){
		PL.leave();
	}
	//读取在线列表
	this.readOnlineList = function(){
		var tree = new Ext.tree.TreePanel({
			id:'onlineList',
	        useArrows: true,
	        autoScroll: true,
	        animate: true,
	        enableDD: false,
	        containerScroll: true,
	        border: false,
	        dataUrl: '/MOBILE/ExtPagingServlet?actionName=QryOnlineStaffsAction',
	       	root:{
	            nodeType: 'async',
	            text: '在线人员',
	            draggable: false,
	            iconCls:'group_icon',
	            id: 'src'
	        }
	    });
	    return tree;
	}
	//修改登录状态,隐身，繁忙
	this.updateState = function(stateId){
		var booleanFlag = false;
		//发送请求修改登录状态，imStaffOnlineId从服务器session取		Ext.Ajax.request({
			   url: '/MOBILE/ExtPagingServlet?actionName=UpdateOnlineStateAction',
			   success: function(){booleanFlag=true},
			   failure: function(){booleanFlag=false},
			   params: { stateId: stateId }
			});
		return booleanFlag;
		
	}
	//查询好友及其分组
	this.getAllFriends = function(){
		var groupTree = new Ext.tree.TreePanel({
			id:'groupTree',
			useArrows: true,
			autoScroll: true,
			animate: true,
        	enableDD: false,
        	containerScroll: true,
			border:false,
			rootVisible:false,
			dataUrl:'/MOBILE/ExtPagingServlet?actionName=QryOnlineStaffGroupAction',
			root:new Ext.tree.AsyncTreeNode(),
			hiddenPkgs : [],
			keyword:'',
			setKeyword: function(keyword){
				this.keyword = keyword;
			}
		});
		groupTree.filter = new Ext.tree.TreeFilter(groupTree, {
				clearBlank: true,
				autoClear: true
		})
		
		
		return groupTree;
	}

	//获取消息代码 消息类型 msgType:1好友消息，2群消息,msgName,msgText文本
	this.getMsg = function(msgType,msgName,msgTextId){
		var msg = '';
		var msgText = ''
		if(msgTextId==1){
			msgText = '上线了'
		}else if(msgTextId==2||msgTextId==3||msgTextId==0){
			msgText = '离开了'
		}
		if(msgType==1){
			msg='好友消息:'+'<<'+msgName+'>>'+msgText;
		}else if(msgType==2){
			msg='群消息     :'+'<<'+msgName+'>>'+msgText;
		}
		return msg;
		
	}
	//增加好友分组//修改好友分组
	this.addOrUpdateFriGroup = function(type){
		Ext.getCmp('rightmenu').hide();
		var groupName = '';
		var id=0;
		if(type=='update'){
			groupName = im.selNode.text;
		 	id = im.selNode.id.split('_')[1];
		}
		var win = new Ext.Window({
		       		id:'addFriGroupWin',
		            title: '添加好友分组',
		            closable:true,
		            width:240,
		            height:120,
		            layout:'border',
		          	buttonAlign:'center',
		            items:[{
		            	region:'center',
		            	defaultType: 'textfield',
		            	frame:true,
		            	layout:'form',
		            	labelWidth: 60,
		            	items:[{
		            		fieldLabel: '分组名称',
		            		name: 'groupName',
		               		id:  'groupName',
		               		value:groupName
		            	}]
		            }],
		            buttons:[{
		            	text:'确定',
		            	onClick:function(){
		            		if(Ext.getCmp('groupName').getValue()==''){
		            			Ext.MessageBox.show({
											           title: '错误',
											           msg: '请输入分组名称',
											           buttons: Ext.MessageBox.OK,
											           width:250,
											           icon: Ext.MessageBox.ERROR
											       });
											       return;
		            			
		            		}
		            		var obj = new Object();
		            		obj.imStaffGroupName = Ext.getCmp('groupName').getValue();
		            		obj.staffId = session1.staff.staffId;
		            		var retobj ;
		            		if(type=='update'){
		            			obj.imStaffGroupId = id;
		            			var retobj = invokeAction('/immanager/UpdateGroupAction',obj);
		            			if(retobj=='success'){
									Ext.MessageBox.show({
												           title: '消息',
												           msg: '修改好友分组成功！',
												           buttons: Ext.MessageBox.OK,
												           width:250,
												           icon: Ext.MessageBox.INFO
												       });
									Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);										       
									Ext.getCmp('addFriGroupWin').close();
								}
		            		
		            		}else{
		            			var retobj = invokeAction('/immanager/InsertGroupAction',obj);
		            			if(retobj=='success'){
									Ext.MessageBox.show({
												           title: '消息',
												           msg: '添加好友分组成功！',
												           buttons: Ext.MessageBox.OK,
												           width:250,
												           icon: Ext.MessageBox.INFO
												       });
									Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);										       
									Ext.getCmp('addFriGroupWin').close();
								}
		            		}
							
							
							
						}
		            },{
		            	text:'取消',
		            	onClick:function(){
							 Ext.getCmp('addFriGroupWin').close();
						}
		            }
		            ]
		            
		        });
		 win.show();
		
	}
	//删除好友分组
	this.deleteGroup = function(btn){
		if(btn=='yes'){
			var node = im.selNode;
			var id = node.id.split('_')[1];
			var retObj = invokeAction('/immanager/DeleteGroupAction',{imStaffGroupId:id});
			
			if(retObj=='success'){
				Ext.MessageBox.show({
					title:'提示',
					msg: '删除好友分组成功！',
					width:250,
					buttons: Ext.MessageBox.OK
					
				});
				Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);
			}
			
		}
	}
    this.quickAddFriToGroup = function(){
    	Ext.getCmp('rightmenu').hide();
    	var win = new Ext.Window({
    	       		id:'quickAddFriToGroupWin',
    	            title: '快速添加好友',
    	            closable:true,
    	            width:270,
    	            height:120,
    	            layout:'border',
    	          	buttonAlign:'center',
    	            items:[{
    	            	region:'center',
    	            	defaultType: 'textfield',
    	            	frame:true,
    	            	layout:'form',
    	            	labelWidth: 80,
    	            	items:[{
    	            		fieldLabel: '好友用户名',
    	            		name: 'imFriStaff',
    	               		id:  'imFriStaff'
    	               		
    	            	}]
    	            }],
    	            buttons:[{
    	            	text:'确定',
    	            	onClick:function(){
    	            		if(Ext.getCmp('imFriStaff').getValue()==''){
    	            			Ext.MessageBox.show({
    										           title: '错误',
    										           msg: '请输入好友用户名',
    										           buttons: Ext.MessageBox.OK,
    										           width:250,
    										           icon: Ext.MessageBox.ERROR
    										       });
    										       return;
    	            			
    	            		}
    	            		var obj = new Object();
    	            		obj.userName = Ext.getCmp('imFriStaff').getValue();
    	            		obj.imStaffGroupId = im.selNode.id.split('_')[1];
    	            		//查询是否已存在好友

    	            		imOper.addFirendToDB(obj,Ext.getCmp('quickAddFriToGroupWin'));
    	            		
    	            	}
    	            		
    	            },{
    	            	text:'取消',
    	            	onClick:function(){
    						 Ext.getCmp('quickAddFriToGroupWin').close();
    					}
    	            }
    	            ]
    	            
    	        });
    	 win.show();
    	
    }
    //将好友添加到数据库
    this.addFirendToDB = function(obj,win){
    	obj.staffId = session1.staff.staffId;
    	if(obj.userName==session1.staff.userName){
    	            			Ext.MessageBox.show({
    										           title: '错误',
    										           msg: '不能添加自己为好友！',
    										           buttons: Ext.MessageBox.OK,
    										           width:250,
    										           icon: Ext.MessageBox.ERROR
    										           
    										       });
    							return
    	            		}
    	            		var ret = invokeAction('/immanager/QryFriendByGroupAndStaffAction',obj);
    	            		if(ret!=null){
    	            			Ext.MessageBox.show({
    										           title: '错误',
    										           msg: '好友已存在，请勿重复添加！',
    										           buttons: Ext.MessageBox.OK,
    										           width:250,
    										           icon: Ext.MessageBox.ERROR
    										           
    										       });
    							return
    	            		}
    	            		 
    	            		var retObj = invokeAction('/immanager/InsertFriendQuickAction',obj);
    	            		if(retObj=='success'){
    	            			Ext.MessageBox.show({
    											           title: '消息',
    											           msg: '添加好友成功！',
    											           buttons: Ext.MessageBox.OK,
    											           width:250,
    											           icon: Ext.MessageBox.INFO
    											       });
    							Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);	
    							if(!Ext.isEmpty(win)){
    								win.close();
    							}									       
    							
    										      
    	            		}else{
    	            			Ext.MessageBox.show({
    										           title: '错误',
    										           msg: '该用户不存在请重新输入正确的好友用户名！',
    										           buttons: Ext.MessageBox.OK,
    										           width:250,
    										           icon: Ext.MessageBox.ERROR
    										           
    										       });
    							return;
    	            		}

    	
    }
  //移动好友到分组

   this.moveFriToGroup = function(type){
    	Ext.getCmp('rightmenu').hide();
    	var groupName = '';
    	var id=0;
    	if(type=='update'){
    		groupName = im.selNode.text;
    	 	id = im.selNode.id.split('_')[1];
    	}
    	var sysStore = new Ext.data.JsonStore({
            remoteSort: true,
            fields: ['imStaffGroupId', 'imStaffGroupName'],
            proxy: new Ext.data.HttpProxy({
                url: '/MOBILE/ExtPagingServlet?actionName=QryMyGroupsAction'
            })
        });
        sysStore.load();
    	
    	
    	var win = new Ext.Window({
    	       		id:'moveFriToGroupWin',
    	            title: '好友移动到....',
    	            closable:true,
    	            width:240,
    	            height:120,
    	            layout:'border',
    	          	buttonAlign:'center',
    	            items:[{
    	            	region:'center',
    	            	defaultType: 'combo',
    	            	frame:true,
    	            	layout:'form',
    	            	labelWidth: 60,
    	            	items:[{
    	            		fieldLabel: '我的分组',
    	            		name: 'staffGroups',
    	               		id:  'staffGroups',
    	               	    valueField: 'imStaffGroupId',
                   			displayField: 'imStaffGroupName',
                   			mode:'remote',
                   			triggerAction: 'all',
    		                forceSelection: true,
    						editable :false,
    						store:sysStore,
    						width:120
                   			
    	            	}]
    	            }],
    	            buttons:[{
    	            	text:'确定',
    	            	onClick:function(){
    	            		if(Ext.getCmp('staffGroups').getValue()==''){
    	            			Ext.MessageBox.show({
    										           title: '错误',
    										           msg: '请输入分组名称',
    										           buttons: Ext.MessageBox.OK,
    										           width:250,
    										           icon: Ext.MessageBox.ERROR
    										       });
    										       return;
    	            			
    	            		}
    	            		
    	            		var obj = new Object();
    	            		obj.toImStaffGroupId = Ext.getCmp('staffGroups').getValue();
    	            		obj.imFriStaffId = im.selNode.id
    	            		obj.fromImStaffGroupId =im.selNode.parentNode.id.split('_')[1];
    	            		var retobj = invokeAction('/immanager/MoveFriToGroupAction',obj);
    	            			if(retobj=='success'){
    								Ext.MessageBox.show({
    											           title: '消息',
    											           msg: '移动好友成功！',
    											           buttons: Ext.MessageBox.OK,
    											           width:250,
    											           icon: Ext.MessageBox.INFO
    											       });
    								Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);										       
    								Ext.getCmp('moveFriToGroupWin').close();
    							}
    	            		
    	            		}
    						
    						
    						
    					
    	            },{
    	            	text:'取消',
    	            	onClick:function(){
    						 Ext.getCmp('moveFriToGroupWin').close();
    					}
    	            }
    	            ]
    	            
    	        });
    	 win.show();
    }
 //删除好友
   this.deleteFriFromGroup = function(btn){
	   	if(btn=='yes'){
	   			var obj = new Object();
	   	        obj.imFriStaffId = im.selNode.id;
	   	        obj.fromImStaffGroupId =im.selNode.parentNode.id.split('_')[1];
	   	     
	   			var retObj = invokeAction('/immanager/DeleteFriFromGroupAction',obj);		
	   			if(retObj=='success'){
	   				Ext.MessageBox.show({
	   					title:'提示',
	   					msg: '删除好友成功！',
	   					width:250,
	   					buttons: Ext.MessageBox.OK
	   					
	   				});
	   				Ext.getCmp('groupTree').getLoader().load(Ext.getCmp('groupTree').root);
	   			}
	   			
	   	}
   }
 //查询添加好友
 this.addFriToGroup = function(){
   		Ext.getCmp('rightmenu').hide();
   		//var staff = window.showModalDialog("selStaff.jsp",window,"dialogHeight="+getSelStaffWinHeight()+"px;dialogWidth="+getSelStaffWinWidth()+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
   		var staff = parent.addFriToGroup(window);
   		if(Ext.isEmpty(staff)){
   			return;
   		}
   		staff.imStaffGroupId = im.selNode.id.split('_')[1];
   		var obj = Ext.apply(new Object(),staff);
   		obj.constructor.toString();
    	this.addFirendToDB(obj,Ext.getCmp('typeOrgWin'));
 }
 //打开聊天窗口
 this.openChatWin = function(item){

	 		if(item.leaf==false){//非叶子节点不能打开聊天窗口
				return
			}

	 		//判断聊天窗口是否打开
	 		//if(this.isChatWinOpen(item.id)){
	 		//	return
	 		//}
            if (parent.isChatWinOpen(item.id)){
            	return;
            }

	 		this.chatWinIds[this.chatWinIds.length]=item.id;
	 		//parent.chatWinIds[parent.chatWinIds.length]=item.id;
			//var url = "/MOBILE/IM/chat.jsp";
			this.item = item;
			//var chatWin = window.open(url,item.id,'height=500,width=650,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
			//var chatWin = window.showModelessDialog(url,window,"dialogHeight="+getChatWinHeight()+"px;dialogWidth="+getChatWinWidth()+"px;scroll=no;resizable=yes;Minimize=yes;Maximize=no;status=no;help=no");


			var chatWin = parent.openChatWin(window);
		//	this.chatWins[this.chatWins.length]=chatWin;
			
	
 }

 //移除聊天窗口
 this.removeWin = function(name){
	
	 	for(var i=0;i<this.chatWins.length;i++){
			if(this.chatWins[i].name == name){
				this.chatWins.remove_chatwin(i);
				this.chatWinIds.remove_chatwin(i);
				break;
			}
		}
		
  }
  
}

//设置查找添加好友的窗口长宽
function getSelStaffWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.550;
	}else{
		return screen.availWidth*0.600;
	}
}

function getSelStaffWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.7;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.65;
	}
}
//设置聊天窗口的长宽
function getChatWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.550;
	}else{
		return screen.availWidth*0.600;
	}
}

function getChatWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.7;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.65;
	}
}