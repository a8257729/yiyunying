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
function IM(){
	this.item;//最近一次打开聊天窗口的NODE
	this.selNode;
	this.chatWins=[];
	this.lock=0;
	this.closed = false;//IM窗口是否关闭
	this.serStaffId;	//默认的IM管理员ID
	this.init = function(session,serStaffId){
		var staffId = session1.staff.staffId;
		this.serStaffId = serStaffId;
		PL._init();
		PL.joinListen('/user,hb,'+staffId,'staffId='+staffId);
		PL.publish('/user/login','message = '+session1.staff.staffName+'login...');
	}
	//事件处理
	this.onData = function(event){
		
		//接收登录消息		
		if(event.arr.p_subject=='/user/login'){
			if(!Ext.isEmpty(Ext.getCmp('onlineList'))){
				Ext.getCmp('onlineList').getRootNode().reload();
			}
			
		}	
		//接收发送到自己的消息
		if(event.arr.p_subject=='/user/chat'&&event.arr.p_to==session1.staff.staffId){
					//打开聊天窗口
					
					
					//设置接收的消息
				  var message = event.arr.message;
				  var currWinIndex =-1;
				  for(var i=0;i<im.chatWins.length;i++){
				  	if(im.chatWins[i].name ==event.arr.p_from){
				  		currWinIndex = i;
				  		break;
				  	}
				  }
				  var oldContent = '';
				  if(currWinIndex==-1){
				  	im.openChatWin({id:event.arr.p_from,text:event.arr.staffName});
				  }else{
				  	 oldContent = im.chatWins[currWinIndex].receiveEditor.getValue();
				  }
	              var now = new Date();
				  var time = now.getYear()+"-"+(now.getMonth()+1)+"-"+now.getDay()+ " " + now.getHours()
				                                    + ":" + now.getMinutes() + ":"
				                                    + now.getSeconds() + "";
				  var showMessage = oldContent+' <br>'+event.arr.p_from+' '+time+' <br>'+message  ; 
				     
				  for(var i=0;i<im.chatWins.length;i++){
				  	if(im.chatWins[i].name ==event.arr.p_from){
				  		currWinIndex = i;
				  		break;
				  	}
				  }
				  while(typeof(im.chatWins[currWinIndex].receiveEditor)=='undefined'){
				  	
				  }	
				  im.chatWins[currWinIndex].receiveEditor.setValue(message);                    
				  
					
				  
		
		}
	}
	this.openIM = function(){
		
		if(!Ext.isEmpty(Ext.getCmp("IMWin"))){
			return
		}
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
	    tree.on('dblclick',function(item){
	    	if(item.id==session1.staff.staffId){
	    		alert("对不起，您不能和自己聊天");
	    		return;	
	    	}
	    	im.openChatWin(item);
	    		
	    })
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
				root:new Ext.tree.AsyncTreeNode()
		});
	  
	   groupTree.on('dblclick',function(item){
	    	if(item.id==session1.staff.staffId){
	    		
	    		Ext.MessageBox.show({
										           title: '错误',
										           msg: '对不起，您不能和自己聊天',
										           buttons: Ext.MessageBox.OK,
										           width:250,
										           icon: Ext.MessageBox.ERROR
										       });
	    		return;	
	    	}
	    	im.openChatWin(item);
	    		
	    })
		var userInfoPanel = new Ext.Panel({
			bodyStyle:"background-image:url('../IM/image/IM_logo.gif')",
			id:'userInfoPanel',
			region: 'north',
			plain:true,
			html:getImFlash(),
			height:80
		});
		
		var rightmenu = new Ext.menu.Menu({
				id:'rightmenu'
		});
		 var friendsTabs = new Ext.TabPanel({
		  		id:'friendsTabs',
	            region: 'center',
	            margins:'3 3 3 0', 
	            defaults:{autoScroll:true},
				minTabWidth: 85,  
	        	tabWidth:85,
	        	activeTab: 0,
	        	deferredRender : false,
	        	resizeTabs : true,
				inTabWidth : 85,
	        	height:50,
	        	items:[{
	        		id:'friendTab',
	                title: '我的好友',
	                tabWidth:135,
	                minTabWidth: 115, 
	                items:[groupTree]
	                
	                
	            },{
	            	id:'groupTab',
	                title: '我的群组',
	                tabWidth:135,
	                minTabWidth: 115, 
	                html: "功能未开放。。。",
	                value:'red'
	            },{
	            	id:'onlineTab',
	                title:'所有在线',
	                tabWidth:135,
	                minTabWidth: 115, 
	                items:[tree]
	                
	               
	            }]
	        });
	    groupTree.on('contextmenu',function(node,event){
	    		node.select(); 
	    		im.selNode =node; 
	    		im.lock=1;
	   			Ext.getCmp('rightmenu').removeAll();
	   					var items; 
	   					if(im.selNode.leaf==false){
	   						items = getRightMenu(1);
	   					}else{
	   						items = getRightMenu(2);
	   					}
  						
	   					//添加菜单项
	   					
	   					for(var i=0;i<items.length;i++){
	   						
	   						Ext.getCmp('rightmenu').add(items[i]);
	   					}
	   					
	   			Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
	   	});
	    Ext.getCmp('friendTab').on('render',function(){
	    	im.lock=0;
  			Ext.getCmp('friendTab').el.dom.oncontextmenu = function(e){
  					//	alert("空白 "+im.lock)
  						if(im.lock!=0){
  							im.lock=0;
				    		return;
				    	}	
						Ext.getCmp('rightmenu').removeAll();
  						var items = getRightMenu(0);
	   					//添加菜单项
	   					
	   					for(var i=0;i<items.length;i++){
	   						
	   						Ext.getCmp('rightmenu').add(items[i]);
	   					}
	   					
	   					Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
		                window.event ? window.event.returnValue = false : e.preventDefault();   
            };  
	   	});
	   	
		var win = new Ext.Window({
	       		id:'IMWin',
	            closable:false,
	            resizable:false,
	            draggable:false,
	            width:300,
	            height:500,
	            plain:true,
	            layout:'border',
	            items:[userInfoPanel,friendsTabs]
	            
	            
	        });
	        win.on('close',function(){
	        	
	        });
	        win.show();
	        Ext.getCmp('onlineList').getRootNode().reload();
	        
	 }
	 this.leaveIM = function(){
	 	PL.leave();
	 }
	 this.openChatWin = function(item){
	 		if(item.leaf==false){//非叶子节点不能打开聊天窗口
	 			return
	 		}
	 		var url = "chat.jsp";
	 		if(item.isMain){
	 			url = "../../IM/" + url;
	 		}
	 		this.item = item;
	 		var chatWin = window.open(url,item.id,'height=500,width=650,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
	 		this.chatWins[this.chatWins.length]=chatWin;
	 
	 }
	 
	 /**
	  * 添加为某个特定的人弹出聊天框(xsh 2010-10-25)
	  * @param {} staffId		聊天对方的ID
	  * @param {} staffName		聊天对方的姓名
	  * @param {} isMain		表示是否是从MainTab页面直接打开对话框
	  */
	 this.openChatWinForStaff = function(staffId,staffName,isMain){
		if(staffId==session1.staff.staffId){
			Ext.MessageBox.show({
				title: '错误',
				msg: '对不起，您不能和自己聊天',
				buttons: Ext.MessageBox.OK,
				width:250,
				icon: Ext.MessageBox.ERROR
			});
			return;	
		}
		
	 	var item = new Object();
	 	item.leaf= true;
	 	item.id=staffId;
	 	item.text=staffName;
	 	item.isMain = isMain;
	 	this.openChatWin(item);
	 }
	 
	 this.removeWin = function(name){
	 	
	 	for(var i=0;i<this.chatWins.length;i++){
			if(this.chatWins[i].name == name){
				this.chatWins.remove_chatwin(i);
				break;
			}
		}
		
	 }
}
function addOrUpdateFriGroup(type){
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
function getRightMenu(type){
	if(type==0){//点击空白区域
		var items= [{
		    	text:'添加好友分组',
		    	handler: addOrUpdateFriGroup
		    }];
	
		return items;
	
	}else if(type==1){//点击分组节点
		var items= [{
		    	text:'添加好友分组',
		    	handler: addOrUpdateFriGroup
		    },{
		    	text:'修改好友分组',
		    	onClick:function(){
		    						addOrUpdateFriGroup('update');
						            }
		    },{
		    	text:'删除好友分组',
		    	onClick:function(){
		    							Ext.getCmp('rightmenu').hide();
						            	 Ext.MessageBox.show({
									           title:'提示',
									           msg: '删除好友分组将会删除分组下所有的好友，确认要删除吗？',
									           buttons: Ext.MessageBox.YESNOCANCEL,
									           fn: deleteGroup,
									           width:250,
									       	   icon: Ext.MessageBox.QUESTION
									       });
						            }
		    	
		    },{
		    	text:'快速添加好友',
		    	onClick:function(){
		    						quickAddFriToGroup();
						            }
		    	
		    },{
		    	text:'查询添加好友',
		    	onClick:function(){
		    						addFriToGroup();
						            }
		    	
		    }];
	
		return items;
	}else if(type==2){//点击好友节点
		var items= [{
		    	text:'发送即时消息',
		    	onClick:function(){
		    						Ext.getCmp('rightmenu').hide();
		    						if(item.id==session1.staff.staffId){
							    		Ext.MessageBox.show({
										           title: '错误',
										           msg: '对不起，您不能和自己聊天',
										           buttons: Ext.MessageBox.OK,
										           width:250,
										           icon: Ext.MessageBox.ERROR
										       });
							    		return;	
							    	}
							    
							    	im.openChatWin(im.selNode);
						            }
		    },{
		    	text:'移动好友到分组',
		    	onClick:function(){
		    					moveFriToGroup('type');
						            }
		    },{
		    	text:'删除好友',
		    	onClick:function(){
		    							Ext.getCmp('rightmenu').hide();
						            	 Ext.MessageBox.show({
									           title:'提示',
									           msg: '确认要删除好友吗？',
									           buttons: Ext.MessageBox.YESNOCANCEL,
									           fn: deleteFriFromGroup,
									           width:250,
									       	   icon: Ext.MessageBox.QUESTION
									       });
						            }
		    	
		    }];
	
		return items;
	}
}
//删除好友分组
function deleteGroup(btn){
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
//删除好友
function deleteFriFromGroup(btn){
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
//移动好友到分组
function moveFriToGroup(type){
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
//快速添加好友到分组
function quickAddFriToGroup(){
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
	            		addFirendToDB(obj,Ext.getCmp('quickAddFriToGroupWin'));
	            		
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
//查询添加好友
function addFriToGroup(){
		Ext.getCmp('rightmenu').hide();
		window.showModalDialog("friendAdd.jsp",window,"dialogHeight="+450+"px;dialogWidth="+550+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
	}
//添加好友操作
function addFirendToDB(obj,win){
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
//获取flash背景
function getImFlash(){

	var html = 'test' ; 
   return html;

}


