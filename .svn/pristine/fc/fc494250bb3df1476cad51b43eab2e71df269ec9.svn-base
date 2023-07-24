/** by tang.lin 2010-11-9 聊天记录 **/
function ChatLog(){
	this.chatLogRowCount=12;
	//主窗口
	this.mainChatLogWin= function(){
		var centerGirdPanel = this.initCenterGirdPanel();
		
		var mainChatWin = new Ext.Window({
       		id:'chatLogWin',
            closable:false,
            width:getGroupAddWinWidth(),
            height:getGroupAddWinHeight(),
            resizable:false,
            draggable:false,
            plain:true,
            collapsible:false, 
            layout:'border',
            items:[centerGirdPanel]
        });
		return mainChatWin;
	};
	this.initCenterGirdPanel = function(){
		var msgContent = new Ext.form.TextField({id:'msgContent',width: swidth*0.1}); 
		 var beginDate = new Ext.form.DateField({ 
			 id: 'beginDate',
             format: 'Y-m-d',
             allowBlank: true
		    });
		 var endDate = new Ext.form.DateField({ 
			 id: 'endDate',
             format: 'Y-m-d',
             allowBlank: true
		    });
		var column = new Ext.grid.ColumnModel([
		    {header:'记录ID',dataIndex:'imChatLogId',hidden:true },
		    {header:'发送者ID',dataIndex:'senderStaffId',hidden:true },
		    {header:'接收者ID',dataIndex:'receiveStaffId',hidden:true },
		    {header:'发送者',dataIndex:'senderStaffName',width:100 },
		    {header:'接收者',dataIndex:'receiveStaffName',width:100},
		    {header:'发送时间',dataIndex:'msgDate',width:150},
		    {header:'消息内容',dataIndex:'msgContent',width:200}
		    
		]); 
		var centerGridPanel = new ZTESOFT.Grid({
		          id:'centerGridPanel',
		          region: 'center',
		          tbar:['信息查找: ',
		                msgContent,
		                ' 开始时间: ',
		                beginDate,
		                ' 结束时间: ',
		                endDate,
		                '-',{
		                    text:'查询',
		                    onClick:function(){
		                    	chatLog.qryChatLog();
		                    }
		                }],
		          pageSize:this.chatLogRowCount,
		          paging:true,
		          cm:column,
		          url:'/MOBILE/ExtPagingServlet?actionName=QryChatLogPagingAction'
		          });
		Ext.getCmp('centerGridPanel').store.on('beforeload',function(store){
			
			if(Ext.getCmp('centerGridPanel').store.lastOptions != null){
				var qryObj = {};
				qryObj.msgBelongStaffId = session1.staff.staffId;
				qryObj.logStaff = staffId;
				qryObj.start = 0;
				qryObj.limit = this.chatLogRowCount;
				if(Ext.getCmp('beginDate').getValue()!=''){
					qryObj.beginDate = Ext.getCmp('beginDate').getValue().toString();
				}
				if(Ext.getCmp('endDate').getValue()!=''){
					qryObj.endDate = Ext.getCmp('endDate').getValue().toString();
				}
				
				if(Ext.getCmp('msgContent').getValue()!=''){
					qryObj.msgContent = Ext.getCmp('msgContent').getValue();
				}
				Ext.getCmp('centerGridPanel').store.baseParams = qryObj;
			}
		});
		return centerGridPanel;
	}
	//查询聊天记录
	this.qryChatLog = function(){
		var qryObj = {};
		qryObj.msgBelongStaffId = session1.staff.staffId;
		qryObj.logStaff = staffId;
		qryObj.start = 0;
		qryObj.limit = this.chatLogRowCount;
		if(Ext.getCmp('beginDate').getValue()!=''){
			qryObj.beginDate = Ext.getCmp('beginDate').getValue().toString();
		}
		if(Ext.getCmp('endDate').getValue()!=''){
			qryObj.endDate = Ext.getCmp('endDate').getValue().toString();
		}
		
		if(Ext.getCmp('msgContent').getValue()!=''){
			qryObj.msgContent = Ext.getCmp('msgContent').getValue();
		}
		
		Ext.getCmp('centerGridPanel').store.load({params:qryObj});
	}
}

//设置查找添加好友的窗口长宽
function getGroupAddWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.630;
	}else{
		return screen.availWidth*0.530;
	}
}

function getGroupAddWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.64;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.60;
	}
}	
