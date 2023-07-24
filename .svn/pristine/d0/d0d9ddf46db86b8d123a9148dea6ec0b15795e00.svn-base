/** by tang.lin 2010-12-14 IM即时群聊天功能操作**/
function ImGroupOper(){
	this.width = getGroupAddWinWidth();
	this.height = getGroupAddWinHeight();
	this.chatWinGroupIds=[];
	this.chatGroupWins = [];
	this.item;
	//查询群信息
	this.getAllGroups = function(){
		var bigGroupTree = new Ext.tree.TreePanel({
			id:'bigGroupTree',
			useArrows: true,
			autoScroll: true,
			animate: true,
        	enableDD: false,
        	containerScroll: true,
			border:false,
			rootVisible:false,
			dataUrl:'/MOBILE/ExtPagingServlet?actionName=QryGroupsByStaffIdAction',
			root:new Ext.tree.AsyncTreeNode()
		});
		Ext.getCmp('bigGroupTree').on('dblclick',function(item){
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
		    	imGroupOper.openGroupChatWin(item);
		    		
		 })
		return bigGroupTree;
	}	
	this.getRightMenuItems = function(type){
		if(type==0){//点击空白区域
			var items= [{
			    	text:'创建群',
			    	handler: imGroupOper.addGroup
			    }];
			return items;
		}else if(type==1){
			var items= [{
		    	text:'创建群',
		    	handler: imGroupOper.addGroup
		    },{
		    	text:'解散群',
		    	handler: function(){
		    		Ext.MessageBox.show({
										           title:'提示',
										           msg: '确认要解散群吗？',
										           buttons: Ext.MessageBox.YESNOCANCEL,
										           fn: imGroupOper.delGroup,
										           width:250,
										       	   icon: Ext.MessageBox.QUESTION
										       });
		    	}
		    },{
		    	text:'修改群信息',
		    	handler: imGroupOper.updateGroup
		    	
		    }];
		}
		return items;
		
	}
	this.addGroup = function(){
		Ext.getCmp('rightmenu').hide();  	
		parent.addGroup(window);
	//	window.showModalDialog("groupAdd.jsp",window,"dialogHeight="+imGroupOper.height+"px;dialogWidth="+imGroupOper.width+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
	}
	this.delGroup = function(btn){
		if(btn=='yes'){
			var param = {'imStaffBiggroupId':Ext.getCmp('bigGroupTree').getSelectionModel().getSelectedNode().id};
			var record = invokeAction('/immanager/DeleteBigGroupAction',param);
			Ext.getCmp('bigGroupTree').getRootNode().reload();
		}
		
	}
	this.updateGroup = function(){
		Ext.getCmp('rightmenu').hide();
		parent.updateGroup(window);
		//window.showModalDialog("groupUpdate.jsp",window,"dialogHeight="+imGroupOper.height+"px;dialogWidth="+imGroupOper.width+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
	}
	//判断聊天窗口是否打开
	this.isChatWinOpen = function(id){
		for(var i=0;i<this.chatWinGroupIds.length;i++){
			if(this.chatWinGroupIds[i]== id){
				return true
			}
		}
		return false;
	}
	 //打开群聊天窗口
	 this.openGroupChatWin = function(item){
		 		if(item.leaf==false){//非叶子节点不能打开聊天窗口
					return
				}
		 		//判断聊天窗口是否打开
		 		//if(this.isChatWinOpen(item.id)){
		 		//	return
		 		//}

		 		this.chatWinGroupIds[this.chatWinGroupIds.length]=item.id;
				var url = "/MOBILE/IM/groupChat.jsp";
				this.item = item;
				//var chatWin = window.open(url,item.id,'height=500,width=650,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
				var chatWin = parent.openGroupChatWin(window);//window.showModelessDialog(url,window,"dialogHeight="+getChatWinHeight()+"px;dialogWidth="+getChatWinWidth()+"px;scroll=no;resizable=yes;Minimize=yes;Maximize=no;status=no;help=no");
				//this.chatGroupWins[this.chatGroupWins.length]=chatWin;
				
		
	 }
	 //移除聊天窗口
 	this.removeWin = function(name){
	
	 	for(var i=0;i<this.chatGroupWins.length;i++){
			if(this.chatGroupWins[i].name == name){
				this.chatGroupWins.remove_chatwin(i);
				this.chatWinGroupIds.remove_chatwin(i);
				break;
			}
		}
		
	 }
	 //接收群聊天消息
	this.receiveGroupMsg = function(event){
		//判断聊天窗口是否打开，如果打开聊天窗口直接在窗口显示消息
		
		//if(this.isChatWinOpen(event.arr.p_groupId)){
		if(parent.isChatWinOpen(event.arr.p_groupId)){
			parent.getUnReadMsgForGroup(event.arr.p_groupId);
			//for(var i=0;i<this.chatWinGroupIds.length;i++){
			//	if(this.chatGroupWins[i].name == parseInt(event.arr.p_groupId)){
			//		this.chatGroupWins[i].chat.getUnReadMsgForGroup();
			//	}
			//}
		}else{//未打开聊天窗口在未读信息窗口显示消息

			imOper.openImMsgWin();
		}
		
	}
}
//设置创建群的窗口长宽
function getGroupAddWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.335;
	}else{
		return screen.availWidth*0.335;
	}
}

function getGroupAddWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.55;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.52;
	}
}