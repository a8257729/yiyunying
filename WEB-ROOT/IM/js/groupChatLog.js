/** by tang.lin 2010-11-9 聊天记录 **/
function ChatLog(){
	this.chatLogRowCount=12;
	//主窗口
	this.mainChatLogWin= function(){
		var centerGirdPanel = this.initCenterGirdPanel();
		var viewport = new Ext.Viewport({
			el:'chatLog',
			layout: 'border',
			items:[centerGirdPanel]
		});
//		var mainChatWin = new Ext.Window({
//       		id:'chatLogWin',
//            closable:false,
//           // width:swidth*0.4687,
//           // height:sheight*0.4375,
//            width:609,
//            height:458,
//            x:0,
//            y:0,
//            resizable:false,
//            draggable:false,
//            plain:true,
//            collapsible:false, 
//            layout:'border',
//            items:[centerGirdPanel]
//        });
//		return mainChatWin;
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
		    {header:'接收者',dataIndex:'receiveStaffName',width:100,hidden:true},
		    {header:'发送时间',dataIndex:'msgDate',width:150},
		    {header:'消息内容',dataIndex:'msgContent',width:300}
		    
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
				qryObj.imStaffBiggroupId = groupId;
				qryObj.start = 0;
				qryObj.limit = this.chatLogRowCount;
				qryObj.msgType = 2;
				if(Ext.getCmp('beginDate').getValue()!=''){
					qryObj.msgBeginDate = Ext.getCmp('beginDate').getValue().toString();
				}
				if(Ext.getCmp('endDate').getValue()!=''){
					qryObj.msgEndDate = Ext.getCmp('endDate').getValue().toString();
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
		qryObj.imStaffBiggroupId = groupId;
		qryObj.start = 0;
		qryObj.msgType = 2;
		qryObj.limit = this.chatLogRowCount;
		if(Ext.getCmp('beginDate').getValue()!=''){
			qryObj.msgBeginDate = Ext.getCmp('beginDate').getValue().toString();
		}
		if(Ext.getCmp('endDate').getValue()!=''){
			qryObj.msgEndDate = Ext.getCmp('endDate').getValue().toString();
		}
		
		if(Ext.getCmp('msgContent').getValue()!=''){
			qryObj.msgContent = Ext.getCmp('msgContent').getValue();
		}
		
		Ext.getCmp('centerGridPanel').store.load({params:qryObj});
	}
}
