<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>消息推送查询</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/pushMsgQuery.js"></script>
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>			
	</body>
</html>

<script language="JScript">
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var oper = new Oper();
var vMessageType = {"0":"立即推送"};

Ext.apply(Ext.form.VTypes, {

    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },
	numText: '请输入数字！',
    
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } 
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    }
});

Ext.onReady(function(){
		var mainPanel = oper.initMainPanel();
		var viewport = new Ext.Viewport({
			el:'sysGird',
			layout: 'border',
			items:[mainPanel]
	});
});

function Oper(){

	this.initMainPanel = function(){
         var qryPanel = this.initQryPanel();
		 var pushMsgQueryGrid = this.initpushMsgQueryGrid();		 
		 this.QryPushMsgQueryGrid();
		 //this.initGridMenu();
		 this.initGridEvent();
		 		 
         var pushMsgQueryPanel = new Ext.Panel({
			border: false,
			title: '消息推送查询',
			region: 'center',
			layout: 'border',
			items:[pushMsgQueryGrid]
		}); 

		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[pushMsgQueryGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "pushMsgQueryGrid":
						{
							oper.QryPushMsgQueryGrid();
							break ;
						}						
					}	
	    		}
	        }
		});
	
		 var mainPanel = new Ext.Panel({
	 		region: 'center',
	 		layout:'border',
	 		items:[qryPanel,tabs]
	 	});
	 	return mainPanel;
	}

    this.initpushMsgQueryGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'消息标题',dataIndex:'messageTitle',width:swidth*0.15},
		    {header:'消息内容',dataIndex:'msssageContent',width:swidth*0.40},
		    {header:'推送方式',dataIndex:'messageType',width:swidth*0.10,renderer:function(v){return vMessageType[v];}},
		    {header:'接收人',dataIndex:'receiverName',width:swidth*0.10},
		    {header:'推送时间',dataIndex:'msssageQueueTime',width:swidth*0.15},
		    {header:'发送人',dataIndex:'senderName',width:swidth*0.10},
            {header:'接收人ID',dataIndex:'receiverId',hidden:true},
            {header:'发送人ID',dataIndex:'senderId',hidden:true}
		]);

		var pushMsgQueryGrid =  new ZTESOFT.Grid({
			id: 'pushMsgQueryGrid',
			region: 'center',					
		    title:"消息推送查询",
		    cm:column,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelPushMessageQueryAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            }
		        }
		    })
	    });
	    return pushMsgQueryGrid;
	};
	
	this.QryPushMsgQueryGrid = function(){
		var beginDate = Ext.getCmp('m_beginDate').getValue();
		var endDate = Ext.getCmp('m_endDate').getValue();
        var messageType = Ext.getCmp('m_messageType').getValue();
        var receiverName = Ext.getCmp('m_receiverName').getValue();
        var senderName = Ext.getCmp('m_senderName').getValue();
	    Ext.getCmp('pushMsgQueryGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('pushMsgQueryGrid').store.lastOptions != null){
				   Ext.getCmp('pushMsgQueryGrid').store.baseParams = {
                       messageType:messageType,
                       senderName:senderName,
                       receiverName:receiverName,
                       beginDate:beginDate,
                       endDate:endDate
                   };
				}
			}
	    );
	    Ext.getCmp('pushMsgQueryGrid').store.removeAll() ;
		Ext.getCmp('pushMsgQueryGrid').store.load({params:{optype:'ALL',start:0, limit:10}});
	};
	
	this.initQryPanel = function(){
	    var messageTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'立即推送']]});
	    var qryPanel = new Ext.FormPanel({
		id:'qryPanel',
		region: 'north',
		frame:true,
		title: '查询条件',
	    bodyStyle:'padding:5px 5px 0',
        labelWidth: swidth*0.06,
        collapsible:true,
        height:Ext.getBody().getSize().height*0.23,
        width:Ext.getBody().getSize().width*0.8,
        buttons:[{
            text: '查询',onClick:doQuery},{text: '重置',onClick:reset
        }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    xtype:'datefield',
                    fieldLabel: '开始时间',
                    name: 'm_beginDate',
                    id: 'm_beginDate',
                    format :'Y-m-d',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '发送人',
                    name: 'm_senderName',
                    id: 'm_senderName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    xtype:'datefield',
                    fieldLabel: '结束日期',
                    name: 'm_endDate',
                    id: 'm_endDate',
                    format :'Y-m-d',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '接收人',
                    name: 'm_receiverName',
                    id: 'm_receiverName',
                    anchor:'95%'
                }]
            },{
                columnWidth:.33,
                layout: 'form',
                items: [{
                    fieldLabel: '推送方式',
                    xtype: 'combo',
                    name: 'm_messageType',
                    id: 'm_messageType',
                    valueField: 'id',
                    value:0,
                    displayField: 'value',
                    mode:'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable :false,
                    anchor:'95%',
                    store:messageTypeStore
                }]
            }]
        }]
	 });
     return qryPanel;
	};
	
	function doQuery(){
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "pushMsgQueryGrid": {
				oper.QryPushMsgQueryGrid();
				break ;
			}
		}
	}

    function reset(){
		Ext.getCmp('m_beginDate').setValue('');
		Ext.getCmp('m_endDate').setValue('');
		Ext.getCmp('m_messageType').setValue('0');
		Ext.getCmp('m_receiverName').setValue('');
        Ext.getCmp('m_senderName').setValue('');
	}

	this.initGridEvent = function(){
		Ext.getCmp('pushMsgQueryGrid').store.removeAll() ;
		Ext.getCmp('pushMsgQueryGrid').store.load();
	};

}
</script>