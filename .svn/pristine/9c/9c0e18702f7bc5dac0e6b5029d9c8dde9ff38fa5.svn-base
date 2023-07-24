/** by tang.lin 2010-10-29 IM即时聊天界面 **/
function ImUI(){
	this.lock=0;
	this.selNode;
	
	//初始化IM主窗口
	this.initImWin = function(){
		//获取用户信息面板
		var userInfoPanel = this.initUserInfoPanel();
		//获取好友tab页
		var friendsTabs = this.initFriendsTabs(); 
		//初始化右键菜单
		var rightmenu = new Ext.menu.Menu({
			id:'rightmenu'
		});
		var mainPanel = new Ext.Panel({
				closable:false,
	            resizable:false,
	            draggable:false,
	            plain:true,
	            region: 'center',
	            layout:'border',
	            items:[userInfoPanel,friendsTabs]
		});
		var win = new Ext.Viewport({
	       		id:'IMWin',
	            
	            
	            plain:true,
	            layout:'border',
	            items:[mainPanel]
        });
        return win;
        
	}
	//初始化用户信息面板
	this.initUserInfoPanel = function(){
		var userInfoPanel = new Ext.Panel({
			id:'userInfoPanel',
			region: 'north',
			plain:false,
			html:this.getUserInfoHTML(),
			height:sheight*0.1,
			bbar: ['好友查找: ',  new Ext.app.SearchField({width: swidth*0.07}),'状态:',{
				id:'onlineStateBar',
	            text: '在线',
	            iconCls: 'online_state',
	            defaults: { style: 'width:50'},
	            menu: [{
	            			text: '在线',
	            			value:1,
	            			handler:imUI.updateOnline
	            		},{
	            			text: '隐身',
	            			value:2,
	            			handler:imUI.updateOnline
	            		},{
	            			text: '繁忙',
	            			value:3,
	            			handler:imUI.updateOnline
	            		},{
	            			text: '离线',
	            			value:0,
	            			handler:imUI.updateOnline
	            		}
	                  ]
	        }]
		});
		return userInfoPanel ;
	}
	//改变登录状态
	this.updateOnline = function(item){
		//更新在线表
		imOper.updateState(item.value);
		//更新下拉菜单
		Ext.getCmp('onlineStateBar').setText(item.text);
		//发送通知--改变状态		
		PL.publish('/user/onlineStateChange','staffId='+session1.staff.staffId+'&stateId='+item.value+'&staffName='+session1.staff.staffName);
	}
	//获取用户面板html
	this.getUserInfoHTML = function(){
		return "姓名:"+session1.staff.staffName+'<br>职位:'+session1.job.jobName+'<br>组织:'+session1.org.orgPathName;
	}
	this.initFriendsTabs = function(){
		var friendsTabs = new Ext.TabPanel({
		  		id:'friendsTabs',
	            region: 'center',
	            margins:'3 3 3 0', 
	            defaults:{autoScroll:true},
				//minTabWidth: swidth*0.066,  
	        	tabWidth:150,//getFriendTabWidth(),
	        	activeTab: 0,
	        	deferredRender : false,
	        	resizeTabs : true,
				inTabWidth : swidth*0.066,
	        	height:sheight*0.0625,
	        	items:[{
	        		id:'friendTab',
	                title: '我的好友',
	                tabWidth:swidth*0.08,
	                //minTabWidth: swidth*0.09, 
	                items:[imOper.getAllFriends()]
	            },{
	            	id:'groupTab',
	                title: '我的群组',
	                tabWidth:swidth*0.08,
	                //minTabWidth: swidth*0.09, 
	                items:[imGroupOper.getAllGroups()]
	               
	            },{
	            	id:'onlineTab',
	                title:'所有在线',
	                tabWidth:swidth*0.08,
	                //minTabWidth: swidth*0.09, 
	                items:[imOper.readOnlineList()]
	            }]
	        });
		Ext.getCmp('friendTab').on('render',function(){
				im.lock=0;
	  			Ext.getCmp('friendTab').el.dom.oncontextmenu = function(e){
	  						if(im.lock!=0){
	  							im.lock=0;
					    		return;
					    	}	
							Ext.getCmp('rightmenu').removeAll();
	  						var items = imUI.getRightMenuItems(0);
		   					//添加菜单项
		   					for(var i=0;i<items.length;i++){
		   						Ext.getCmp('rightmenu').add(items[i]);
		   					}
		   					Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
			                window.event ? window.event.returnValue = false : e.preventDefault();   
	            };  
		});
		Ext.getCmp('groupTab').on('render',function(){
			im.lock=0;
  			Ext.getCmp('groupTab').el.dom.oncontextmenu = function(e){
  						if(im.lock!=0){
  							im.lock=0;
				    		return;
				    	}	
						Ext.getCmp('rightmenu').removeAll();
  						var items = imGroupOper.getRightMenuItems(0);
	   					//添加菜单项
	   					for(var i=0;i<items.length;i++){
	   						Ext.getCmp('rightmenu').add(items[i]);
	   					}
	   					Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
		                window.event ? window.event.returnValue = false : e.preventDefault();   
            };  
		});
		Ext.getCmp('groupTree').on('contextmenu',function(node,event){
					
		    		node.select(); 
		    		im.selNode =node; 
		    		im.lock=1;
		   			Ext.getCmp('rightmenu').removeAll();
		   					var items; 
		   					if(im.selNode.leaf==false){
		   						items = imUI.getRightMenuItems(1);
		   					}else{
		   						items = imUI.getRightMenuItems(2);
		   					}
    	   					//添加菜单项
    	   					for(var i=0;i<items.length;i++){
		   						
		   						Ext.getCmp('rightmenu').add(items[i]);
		   					}
		   					
		   			Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
		});
		Ext.getCmp('bigGroupTree').on('contextmenu',function(node,event){
			node.select(); 
    		im.selNode =node; 
    		im.lock=1;
   			Ext.getCmp('rightmenu').removeAll();
   					var items; 
   					//非创建人不能解散群和修改群信息
   					
   					if(node.attributes.staffId == session1.staff.staffId){
   						items = imGroupOper.getRightMenuItems(1);	
   					}else{
   						items = imGroupOper.getRightMenuItems(0);
   					}
   					
   					
   					//添加菜单项
   					for(var i=0;i<items.length;i++){
   						
   						Ext.getCmp('rightmenu').add(items[i]);
   					}
   					
   			Ext.getCmp('rightmenu').showAt([window.event.clientX,window.event.clientY]);
		});
		Ext.getCmp('groupTree').on('dblclick',function(item){
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
		    	var menu = Ext.getCmp("onlineStateBar");
				if (menu.getText()=="离线") {
		    		Ext.MessageBox.show({
				           title: '错误',
				           msg: '对不起，离线状态不能聊天',
				           buttons: Ext.MessageBox.OK,
				           width:250,
				           icon: Ext.MessageBox.ERROR
				       });
                    return;	
				}
				
		    	imOper.openChatWin(item);
		    		
		    })
		return friendsTabs;
	}
	this.getRightMenuItems = function(type){
		if(type==0){//点击空白区域
			var items= [{
			    	text:'添加好友分组',
			    	handler: imOper.addOrUpdateFriGroup
			    }];
		
			return items;
		
		}else if(type==1){//点击分组节点
			var items= [{
			    	text:'添加好友分组',
			    	handler: imOper.addOrUpdateFriGroup
			    },{
			    	text:'修改好友分组',
			    	onClick:function(){
			    						imOper.addOrUpdateFriGroup('update');
							            }
			    },{
			    	text:'删除好友分组',
			    	onClick:function(){
			    							Ext.getCmp('rightmenu').hide();
							            	 Ext.MessageBox.show({
										           title:'提示',
										           msg: '删除好友分组将会删除分组下所有的好友，确认要删除吗？',
										           buttons: Ext.MessageBox.YESNOCANCEL,
										           fn: imOper.deleteGroup,
										           width:250,
										       	   icon: Ext.MessageBox.QUESTION
										       });
							            }
			    	
			    },{
			    	text:'快速添加好友',
			    	onClick:function(){
			    		imOper.quickAddFriToGroup();
							            }
			    	
			    },{
			    	text:'查询添加好友',
			    	onClick:function(){
			    		imOper.addFriToGroup();
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
								    
			    						imOper.openChatWin(im.selNode);
							            }
			    },{
			    	text:'移动好友到分组',
			    	onClick:function(){
			    					imOper.moveFriToGroup('type');
							            }
			    },{
			    	text:'删除好友',
			    	onClick:function(){
			    							Ext.getCmp('rightmenu').hide();
							            	 Ext.MessageBox.show({
										           title:'提示',
										           msg: '确认要删除好友吗？',
										           buttons: Ext.MessageBox.YESNOCANCEL,
										           fn: imOper.deleteFriFromGroup,
										           width:250,
										       	   icon: Ext.MessageBox.QUESTION
										       });
							            }
			    	
			    }];
		
			return items;
		}
		
	}
	
}

function getIMWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.247;
	}else{
		return screen.availWidth*0.345;
	}
}

function getFriendTabWidth(){
	if(screen.availWidth >= 1280){
		return swidth*0.066;
	}else{
		return swidth*0.095;
	}
}

function getIMWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.73;
	}else{
		return screen.availHeight*0.60;
	}
}



