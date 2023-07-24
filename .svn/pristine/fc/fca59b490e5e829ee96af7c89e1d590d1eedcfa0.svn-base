;(function(w){

var MESSAGE_CLASSES = {
	"0" : "应用升级推送",
	"1" : "预警信息推送",
	"2" : "公告消息推送"
};
	
var MESSAGE_CLASSES_LIST = {
	fields:['id','value'], 
	data: [
		[0,'应用升级推送'],
		[1,'预警信息推送'],
		[2,'公告消息推送']
	]
};

var OPT_BUTTON_FUNC = {
	/*"0" : '<input type="button" class="cssButton" onclick="PushMessageQueue.renderPushButton()" value="重新推送"  />',
	"1" : '<input type="button" class="cssButton" onclick="PushMessageQueue.renderPushButton()" value="立即推送"  />',
	"4" : '<input type="button" class="cssButton" onclick="PushMessageQueue.renderPushButton()" value="重新推送"  />' */
	"0" : '<a href="#" style="text-decoration:none" onclick="PushMessageQueue.renderPushButton()">重新推送</a>',
	"1" : '<a href="#" style="text-decoration:none" onclick="PushMessageQueue.renderPushButton()">立即推送</a>',
	"4" : '<a href="#" style="text-decoration:none" onclick="PushMessageQueue.renderPushButton()">重新推送</a>'
}
	
var MESSAGE_TYPE =  {
	"0" : "立即发送"
};

var MESSAGE_TYPE_LIST = {
	fields:['id','value'], 
	data: [
		[0,'立即推送']
	]
};

var MESSAGE_QUEUE_STATUS = {
	"0": '<font color="blue">已推送</font>',
	"1": '<font color="red">未推送</font>',
	"2": '<font color="green">推送中</font>',
	"3": "已取消",
	"4": "推送失败"
};


var MESSAGE_QUEUE_STATUS_LIST = {
	fields:['id','value'], 
	data: [
		[0,"已推送"],
		[1,"未推送"],
		[2,"推送中"],
		[3,"已取消"],
		[4,"推送失败"]
	]
};

var configJson = {

		"push_message_store" : {
			"id": "push_message_store",
    		"root": "Body",
    		"totalProperty": "totalCount",
    		"remoteSort": true,
			"baseParams": {"optype": "ALL","pagin":"Y"}, 
			"proxy": new Ext.data.HttpProxy({url: '/MOBILE/ExtServlet?actionName=system/SelPushMessageAction'}),
    		"fields": ["pushMessageId", "messageTitle", "messageContent", 
						"messageTime", "messageStatus", "messageStaffId", "messageStaffName", 
						"messageClasses", "messageResend", "messageResendNums", 
						"messageResendInterval", "messageSync", "messageType", 
						"messageStartTime", "messageEndTime", "messageSendNums", 
						"messageSuccessNums", "messageFailureNums", "messageTotalNums", 
						"remarks","messageUrl"]
	   	},
		//推送列表显示字段
		"push_message_headers": [
			{"header":"推送消息ID","dataIndex":"pushMessageId",hidden:true},
			{"header":"推送分类","dataIndex":"messageClasses",width:100,renderer:function(v){return MESSAGE_CLASSES[v]}},
			{"header":"推送标题","dataIndex":"messageTitle",width:100},
			{"header":"推送类型","dataIndex":"messageType",width:100,renderer:function(v){return MESSAGE_TYPE[v]}},
			{"header":"推送状态","dataIndex":"messageStatus",hidden:true},
			{"header":"推送内容","dataIndex":"messageContent",width:400},
			{"header":"推送内容","dataIndex":"messageUrl",width:200},
			{"header":"创建时间","dataIndex":"messageTime",width:150},
			{"header":"创建人ID","dataIndex":"messageStaffId",width:100,hidden:true},
			{"header":"创建人","dataIndex":"messageStaffName",width:100},
			{"header":"备注","dataIndex":"remarks",width:200}
		],

		//推送配置页面字段
	    "push_message_config_fields": [{
            layout:'column',
            items:[{
                columnWidth:.49,
                layout: 'form',
                items: [{
					xtype:'combo',
					fieldLabel: '消息分类',
					name: 'm_messageClasses',
					id: 'm_messageClasses',
					valueField: 'id',
					displayField: 'value',
					mode: 'local',
					triggerAction: 'all',
					editable : false,
					store: new Ext.data.SimpleStore(MESSAGE_CLASSES_LIST),
					value: 0,
					anchor:'95%'
                }, {
					xtype:'combo',
					fieldLabel: '推送类型',
					name: 'm_messageType',
					id: 'm_messageType',
					valueField: 'id',
					displayField: 'value',
					mode: 'local',
					triggerAction: 'all',
					editable : false,
					store: new Ext.data.SimpleStore(MESSAGE_TYPE_LIST),
					value: 0,
					anchor:'95%'
                }, {
					xtype:'textfield',
					fieldLabel: '消息标题',
					name: 'm_messageTitle',
					id: 'm_messageTitle',
					allowBlank:true, 
					anchor:'95%'
                }]
	            },{
	            columnWidth:.02,layout: 'form'},{
                columnWidth:.49,
                layout: 'form',
                items: [{
					xtype:'textfield',
					fieldLabel: '创建人',
					name: 'm_messageStaffName',
					id: 'm_messageStaffName',
					allowBlank:false,
					disabled : true,
					blankText:"创建人不能为空!",
					anchor:'95%'	
                },{
					xtype:'textfield',
					fieldLabel: '备注',
					name: 'm_remarks',
					id: 'm_remarks',
					maxLength:100,	
					allowBlank:true, 
					anchor:'95%'
                },{
					xtype:'textfield',
					fieldLabel: '消息URL',
					name: 'm_messageUrl',
					id: 'm_messageUrl',
					maxLength:100,	
					allowBlank:true, 
					anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]},{
				xtype:'textarea',
				fieldLabel: '消息内容',
				name: 'm_messageContent',
				id: 'm_messageContent',
				height : 100,
				allowBlank:false,
				maxLength:1000,				
				blankText:"消息内容不能为空!",
				anchor:'95%'
           },{
				xtype: 'hidden',
				fieldLabel: '创建人ID',
				name: 'm_messageStaffId',
				id: 'm_messageStaffId'
		},{
				xtype: 'hidden',
				fieldLabel: 'ID',
				name: 'm_pushMessageId',
				id: 'm_pushMessageId'
		}],
			
		"push_message_config_buttons" : [{
	        text: '确定',
			id:'btn_ok'
		},{
			text: '取消', 
			id:'btn_cancel'
		}],
		
		"push_message_config_win" : {
			title: '推送消息配置',
		    closable:true,
		    width:650,
		    height:350,
		    plain:true, 
		    layout: 'border'
		},
		
		"push_message_grid" : {
			id:"push_message_grid",
			region:"center",
	        pageSize:10,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelPushMessageAction',
			width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:"推送消息列表",
		    trackMouseOver:false,
		    autoScroll: true,
		    loadMask: true
		},
		
		//推送详情显示字段
		"push_message_details_panel" : {
	        region: 'south',
	     	labelAlign: 'right',
		 	frame:true,
	        title: '推送信息详情',
	        bodyStyle:'padding:5px 5px 5px 5px',
	        height:Ext.getBody().getSize().height*0.5,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
						xtype:'combo',
						fieldLabel: '消息分类',
						name: 'p_messageClasses',
						id: 'p_messageClasses',
						valueField: 'id',
						displayField: 'value',
						mode: 'local',
						triggerAction: 'all',
						editable : false,
						disabled:true,
						store: new Ext.data.SimpleStore(MESSAGE_CLASSES_LIST),
						value: 0,
						anchor:'95%'
	                }, {
						xtype:'textfield',
						fieldLabel: '创建人',
						name: 'p_messageStaffName',
						id: 'p_messageStaffName',
						disabled:true,
						anchor:'95%'	
	                }, {
						xtype:'textfield',
						fieldLabel: '消息标题',
						name: 'p_messageTitle',
						id: 'p_messageTitle',
						disabled:true,
						anchor:'95%'
					}]
	            },
				{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
						xtype:'combo',
						fieldLabel: '推送类型',
						name: 'p_messageType',
						id: 'p_messageType',
						valueField: 'id',
						displayField: 'value',
						disabled:true,
						mode: 'local',
						triggerAction: 'all',
						editable : false,
						store: new Ext.data.SimpleStore(MESSAGE_TYPE_LIST),
						value: 0,
						anchor:'90%'
	                },{
						xtype:'textfield',
						fieldLabel: '备注',
						disabled:true,
						name: 'p_remarks',
						id: 'p_remarks',
						anchor:'90%'
	                }]
	            }]
	        },{
			    xtype:'textarea',
			    fieldLabel: '消息内容',
			    name: 'p_messageContent',
			    id: 'p_messageContent',
				disabled:true,
			    height : 80,
			    anchor:'100%'
			}]
	    },
		
		"grids_panel_config" : {
			border: false,
			title: "推送消息管理",
			region: 'north',
		    height:Ext.getBody().getSize().height*0.5, 
		    plain:true
		},

		"grids_panel_tabs" : {
			id : "grids_panel_tabs",
			region: 'south',
		    activeTab: 0,  
		    height:Ext.getBody().getSize().height*0.5, 
		    plain:true,
		    defaults:{autoScroll: true}
		},
		
		"push_message_optype" : {
			"I" : "新建推送消息",
			"U" : "修改推送消息",
			"D" : "删除推送消息"
		}, 
					
		"push_message_queue_grid" : {
			id:"push_message_queue_grid",
			region:"center",
	        pageSize:10,
	        paging:true,
	        url: '/MOBILE/ExtServlet?actionName=system/SelPushMessageQueueAction',
			width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:"推送队列列表",
		    trackMouseOver:false,
		    autoScroll: true,
		    loadMask: true
		},
		
		"push_message_queue_headers" : [
			{"header":"ID","dataIndex":"pushMessageQueueId",width:100,hidden:true},
			{"header":"推送消息ID","dataIndex":"pushMessageId",width:100,hidden:true},
			{"header":"接收人ID","dataIndex":"receiverId",hidden:true},
			{"header":"接收人","dataIndex":"receiverName",width:100},
			{"header":"推送时间","dataIndex":"messageQueueTime",width:150},
			{"header":"创建人ID","dataIndex":"senderId",width:100,hidden:true},
			{"header":"创建人","dataIndex":"senderName",width:100},
			{"header":"推送状态","dataIndex":"messageQueueStatus",width:100,renderer:function(v){return MESSAGE_QUEUE_STATUS[v]}},
		
			{"header":"备注","dataIndex":"remarks",width:100},
			{"header":"操作","dataIndex":"messageQueueStatus",width:100,renderer:function(v){
				return OPT_BUTTON_FUNC[v]}
			}
		],
		
		"push_message_queue_store" : {
			"id": "push_message_queue_store",
    		"root": "Body",
    		"totalProperty": "totalCount",
    		"remoteSort": true,
			"baseParams": {"optype": "ALL","pagin":"Y"}, 
			"proxy": new Ext.data.HttpProxy({url: '/MOBILE/ExtServlet?actionName=system/SelPushMessageQueueAction'}),
    		"fields":["pushMessageQueueId", "pushMessageId", "receiverId", "receiverName", "receiverTokens", "messageQueueTime", "senderId", "senderName", "messageQueueStatus", "remarks"]	
		},
		
		//推送队列配置页面字段
	    "push_message_queue_config_fields": [{
            layout:'column',
            items:[{
                columnWidth:.49,
                layout: 'form',
                items: [{
					xtype:'textfield',
					fieldLabel: '创建人',
					name: 'k_senderName',
					id: 'k_senderName',
					allowBlank:true,
					disabled : true,
					anchor:'95%'	
                },{
					xtype:'textfield',
					fieldLabel: '消息标题',
					name: 'k_messageTitle',
					id: 'k_messageTitle',
					allowBlank:true, 
					disabled:true,
					anchor:'95%'
                }]
	            },{
	            columnWidth:.02,layout: 'form'},{
                columnWidth:.49,
                layout: 'form',
                items: [{
					xtype:'compositefield',
					fieldLabel: '接收人',
					name: 'k_receiverName',
					id: 'k_receiverName',
					items: [
					{
							xtype:'textfield',
							name: 'j_pn_user_name',
							id: 'j_pn_user_name',
							blankText:"接收人不能为空！",
							allowBlank:false,
							width: '160',
							readOnly: true
					    },{
							xtype: 'button',
							name: 'j_pn_user_btn',
							id : 'j_pn_user_btn',
							text: '..',
							handler: function(){
								//打开在线人员列表
								PushMessageQueue.queryOnlineUser();
							}
						}
					],
					anchor:'95%'	
                },{
					xtype:'textfield',
					fieldLabel: '备注',
					name: 'k_remarks',
					id: 'k_remarks',
					maxLength:100,	
					allowBlank:true, 
					anchor:'95%'
                }]
            },{columnWidth:.1,layout: 'form'}]},{
				xtype:'textarea',
				fieldLabel: '消息内容',
				name: 'k_messageContent',
				id: 'k_messageContent',
				height : 100,
				allowBlank:true,
				maxLength:1000,				
				disabled:true,
				anchor:'95%'
		},{
				xtype: 'hidden',
				fieldLabel: 'ID',
				name: 'k_pushMessageQueueId',
				id: 'k_pushMessageQueueId'
		},{
				xtype: 'hidden',
				fieldLabel: '推送消息ID',
				name: 'k_pushMessageId',
				id: 'k_pushMessageId'
		},{
				xtype: 'hidden',
				fieldLabel: '创建人ID',
				name: 'k_senderId',
				id: 'k_senderId'
		},{
				xtype: 'hidden',
				fieldLabel: '消息URL',
				name: 'k_messageUrl',
				id: 'k_messageUrl'
		},{
			xtype: 'hidden',
			fieldLabel: '接收人ID',
			name: 'k_receiverId',
			id: 'k_receiverId'
		},{
				xtype: 'hidden',
				fieldLabel: '推送队列状态',
				name: 'k_messageQueueStatus',
				id: 'k_messageQueueStatus'
		},{
				xtype: 'hidden',
				fieldLabel: '用户令牌',
				name: 'k_receiverTokens',
				id: 'k_receiverTokens'
		}],
		"push_message_queue_config_buttons" : [
		{
	        text: '确定并推送',
			id:'btn_ok_send', 
			listeners : {
				'click' : function() {
					Ext.MessageBox.confirm('提示', '确定要立即推送？', function(btn){
						if('no' == btn) {
							return;
						}
					});
				}
			}
		},{
			text: '确定但不推送', 
			id:'btn_ok_unsend'
		},{
			text: '取消', 
			id:'btn_cancel'
		}],
		
		"push_message_queue_config_win" : {
			title: '推送队列配置',
		    closable:true,
		    width:650,
		    height:300,
		    plain:true, 
		    layout: 'border'
		}
};
	w.configJson = configJson;
})(window);
