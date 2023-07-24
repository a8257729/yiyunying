<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>业务调用耗时和次数监控</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
	</head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
	<div id='sysGird'/>
						
	</body>
</html>

<script language="JScript">
/**	by liu.zhi **/


var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var callRowCount = 5; 

var oper = new Oper(); 

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
          
		 var monitorGrid = this.initMonitorGrid();
		 this.QryMonitorGrid();
		 
		 var avgTimeConsumGrid = this.initAvgTimeConsumGrid();
         this.QryAvgTimeGrid();
                
		 var timeConsumRankGrid = this.initTimeConsumRankGrid();		 		 
         this.QryTimeConsumRank();
         
		 var successRateCountGrid = this.initSuccessRateCountGrid();
		 this.QrySuccessRateCount();
		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '耗时统计',
			region: 'center',
			layout: 'border',
			items:[monitorGrid]
		}); 
		
		var avgTimeConsumPanel = new Ext.Panel({
			border: false,
			title: '平均耗时统计',
			region: 'center',
			layout: 'border',
			items:[avgTimeConsumGrid]
		}); 
		
		var timeConsumRankPanel = new Ext.Panel({
		    id:'timeConsumRankPanel',
			border: false,
			title: '耗时排名统计',
			region: 'center',
			layout: 'border',
			items:[timeConsumRankGrid]
		}); 
		
		var successRateCountPanel = new Ext.Panel({
		    id:'successRateCountPanel',
			border: false,
			title: '调用成功率统计',
			region: 'center',
			layout: 'border',
			items:[successRateCountGrid]
		}); 
		
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[monitorGrid, avgTimeConsumGrid,timeConsumRankPanel,successRateCountPanel],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "monitorGrid":
						{
							oper.QryMonitorGrid();
							break ;
						}
						case "avgTimeConsumGrid":
						{
                            oper.QryAvgTimeGrid();                           
							break ;
						}
						case "timeConsumRankPanel":
						{
                            oper.QryTimeConsumRank();
							break ;
						}
						case "successRateCountPanel":
						{	
                            oper.QrySuccessRateCount();
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
	
	this.initServeStore = function(){
	     var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: ['serviceId', 'serviceName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction'
	        }),
	        baseParams:{flag:1}
	        
	    });

	    return sysStore;
	}
	
    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },		    
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',hidden:true },
		    {header:'用户',dataIndex:'username',width:swidth*0.05},		    
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.1},
		    {header:'服务端请求转发耗时(毫秒)',dataIndex:'firstTimeconsuming',width:swidth*0.13},
		    {header:'业务系统响应耗时(毫秒)',dataIndex:'secondTimeconsuming',width:swidth*0.13},
		    {header:'服务端响应转发耗时(毫秒)',dataIndex:'thirdTimeconsuming',width:swidth*0.13},
		    {header:'总耗时(毫秒)',dataIndex:'totalTimeconsuming',width:swidth*0.1},
		    {header:'调用时间',dataIndex:'createTime',width:swidth*0.1},
		    {header:'服务接收时间',dataIndex:'nreceiveTime',width:swidth*0.1},
		    {header:'服务派发外系统时间',dataIndex:'ssendTime',width:swidth*0.1},
		    {header:'外系统服务回馈时间',dataIndex:'sreceiveTime',width:swidth*0.1},
		    {header:'服务返回时间',dataIndex:'nbackTime',width:swidth*0.1}
		    
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'耗时统计',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return monitorGrid;	
	}
	
	this.QryMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:7};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initQryPanel = function(){
	    var serveStore = this.initServeStore();
	    
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
            text: '查询',onClick:doQry},{text: '重置',onClick:reset
        }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户',
                    name: 'username',
                    id: 'username',
                    anchor:'95%'
                },{
                    xtype:'datefield',
                    fieldLabel: '开始日期',
                    name: 'beginDate',
                    id: 'beginDate',
                    format :'Y-m-d',
                    anchor:'95%'              
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    fieldLabel: '服务',
                    xtype: 'combo',
                    name: 'serviceId',
	                id: 'serviceId',
	                valueField: 'serviceId',
	                displayField: 'serviceName',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:serveStore
                },{
                    xtype:'datefield',
                    fieldLabel: '结束日期',
                    name: 'endDate',
                    id: 'endDate',
                    format :'Y-m-d',
                    anchor:'95%'
                }]
            }]
        }]
	});
	   
       return qryPanel;
	}
	
	function doQry(){
	
	    var activeTab = Ext.getCmp('tabs').activeTab.id ;
	    switch (activeTab){
			case "monitorGrid":
			{
				oper.QryMonitorGrid();
				break ;
			}
			case "avgTimeConsumGrid":
			{
                oper.QryAvgTimeGrid();                           
				break ;
			}
			case "timeConsumRankPanel":
			{
                oper.QryTimeConsumRank();
				break ;
			}
			case "successRateCountPanel":
			{	
                oper.QrySuccessRateCount();
				break ;
			}
		}	
	    

	}

	function reset(){
		Ext.getCmp('username').setValue('');
		Ext.getCmp('serviceId').setValue('');
		//Ext.getCmp('serviceName').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}
	
	this.initAvgTimeConsumGrid = function(){
	   
		var column = new Ext.grid.ColumnModel([
		    {header:'服务编号',dataIndex:'serviceId',width:swidth*0.1 },
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.1},
		    {header:'服务端请求转发平均耗时(毫秒)',dataIndex:'avgServerAskTime',width:swidth*0.2},
		    {header:'业务系统响应平均耗时(毫秒)',dataIndex:'avgBusiAnswerTime',width:swidth*0.2},
		    {header:'服务端响应转发平均耗时(毫秒)',dataIndex:'avgServerAnswerTime',width:swidth*0.2}
		  
		]);

		var avgTimeConsumGrid =  new ZTESOFT.Grid({
			id:'avgTimeConsumGrid',
	    	region: 'center',
	        title:'平均耗时统计',
			cm:column,
			pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	//oper.outSysStaffGirdQry(rec);
	                        }
	                    }
	        })
	    });
   
	    return avgTimeConsumGrid;
		
	}
	
	this.QryAvgTimeGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('avgTimeConsumGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('avgTimeConsumGrid').store.lastOptions != null){
				   Ext.getCmp('avgTimeConsumGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:2};
				}
			}
	    )
	    Ext.getCmp('avgTimeConsumGrid').store.removeAll() ;
		Ext.getCmp('avgTimeConsumGrid').store.load({params:{start:0, limit:16}});
	}
		
	this.initTimeConsumRankGrid = function (){
  		
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',width:swidth*0.3},
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.3}
		]);

		var timeConsumRankGrid =  new ZTESOFT.Grid({
			id:'timeConsumRankGrid',
	    	region: 'center',
	        title:'服务列表',
			cm:column,
			pageSize:3,
			paging:true,
			height:150,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                           oper.QryServerAskRank10(rec);
	                           oper.QryInterAskRank10(rec);
	                           oper.QryServerRespondRank10(rec);
	                        }
	                    }
	        })
	    });
	    

		var column1 = new Ext.grid.ColumnModel([

		    {header:'第一名(毫秒)',dataIndex:'ServerAsk1',width:swidth*0.08},
		    {header:'第二名(毫秒)',dataIndex:'ServerAsk2',width:swidth*0.08},
		    {header:'第三名(毫秒)',dataIndex:'ServerAsk3',width:swidth*0.08},
		    {header:'第四名(毫秒)',dataIndex:'ServerAsk4',width:swidth*0.08},
		    {header:'第五名(毫秒)',dataIndex:'ServerAsk5',width:swidth*0.08},
		    {header:'第六名(毫秒)',dataIndex:'ServerAsk6',width:swidth*0.08},
		    {header:'第七名(毫秒)',dataIndex:'ServerAsk7',width:swidth*0.08},
		    {header:'第八名(毫秒)',dataIndex:'ServerAsk8',width:swidth*0.08},
		    {header:'第九名(毫秒)',dataIndex:'ServerAsk9',width:swidth*0.08},
		    {header:'第十名(毫秒)',dataIndex:'ServerAsk10',width:swidth*0.08}
		]);

		var serverAskRank10Grid =  new ZTESOFT.Grid({
			id:'serverAskRank10Grid',
	    	region: 'center',
	        title:'服务端请求转发耗时前十名',
			cm:column1,
			pageSize:10,
			paging:false,
			height:75,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {

	                        }
	                    }
	        })
	    });
	    
		var column2 = new Ext.grid.ColumnModel([

		    {header:'第一名(毫秒)',dataIndex:'interAsk1',width:swidth*0.08},
		    {header:'第二名(毫秒)',dataIndex:'interAsk2',width:swidth*0.08},
		    {header:'第三名(毫秒)',dataIndex:'interAsk3',width:swidth*0.08},
		    {header:'第四名(毫秒)',dataIndex:'interAsk4',width:swidth*0.08},
		    {header:'第五名(毫秒)',dataIndex:'interAsk5',width:swidth*0.08},
		    {header:'第六名(毫秒)',dataIndex:'interAsk6',width:swidth*0.08},
		    {header:'第七名(毫秒)',dataIndex:'interAsk7',width:swidth*0.08},
		    {header:'第八名(毫秒)',dataIndex:'interAsk8',width:swidth*0.08},
		    {header:'第九名(毫秒)',dataIndex:'interAsk9',width:swidth*0.08},
		    {header:'第十名(毫秒)',dataIndex:'interAsk10',width:swidth*0.08}
		    
		]);

		var interAskRank10Grid =  new ZTESOFT.Grid({
			id:'interAskRank10Grid',
	    	region: 'center',
	        title:'业务系统响应耗时前十名',
			cm:column2,
			pageSize:10,
			paging:false,
			height:75,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {

	                        }
	                    }
	        })
	    });
   

		var column3 = new Ext.grid.ColumnModel([

		    {header:'第一名(毫秒)',dataIndex:'serverRespond1',width:swidth*0.08},
		    {header:'第二名(毫秒)',dataIndex:'serverRespond2',width:swidth*0.08},
		    {header:'第三名(毫秒)',dataIndex:'serverRespond3',width:swidth*0.08},
		    {header:'第四名(毫秒)',dataIndex:'serverRespond4',width:swidth*0.08},
		    {header:'第五名(毫秒)',dataIndex:'serverRespond5',width:swidth*0.08},
		    {header:'第六名(毫秒)',dataIndex:'serverRespond6',width:swidth*0.08},
		    {header:'第七名(毫秒)',dataIndex:'serverRespond7',width:swidth*0.08},
		    {header:'第八名(毫秒)',dataIndex:'serverRespond8',width:swidth*0.08},
		    {header:'第九名(毫秒)',dataIndex:'serverRespond9',width:swidth*0.08},
		    {header:'第十名(毫秒)',dataIndex:'serverRespond10',width:swidth*0.08}
		]);

		var serverRespondRank10Grid =  new ZTESOFT.Grid({
			id:'serverRespondRank10Grid',
	    	region: 'center',
	        title:'服务端响应转发耗时前10名',
			cm:column3,
			height:75,		
			pageSize:10,
			paging:false,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {

	                        }
	                    }
	        })
	    });
        var timeConsumRankPanel = new Ext.Panel({
			border: false,
			title: '耗时排名统计',
			region: 'center',
			layout: 'form',
			items:[timeConsumRankGrid,serverAskRank10Grid,interAskRank10Grid,serverRespondRank10Grid]
		}); 
		
	    return timeConsumRankPanel;
	
	}
	
	this.QryTimeConsumRank = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('timeConsumRankGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('timeConsumRankGrid').store.lastOptions != null){
				   Ext.getCmp('timeConsumRankGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:1};
				}
			}
	    )
	    Ext.getCmp('timeConsumRankGrid').store.removeAll() ;
		Ext.getCmp('timeConsumRankGrid').store.load({params:{start:0, limit:3}});
		
        /*Ext.getCmp('timeConsumRankGrid').store.on('load',function(){
			Ext.getCmp('timeConsumRankGrid').getSelectionModel().selectRow(0);
		});*/
				
	}
	
	this.QryServerAskRank10 = function(serverRow){
		var serviceId = serverRow.data.serviceId;
        var username = Ext.getCmp('username').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		//绑定发送请求参数

		Ext.getCmp('serverAskRank10Grid').store.on('beforeload',function(store){
			if(Ext.getCmp('serverAskRank10Grid').store.lastOptions != null){
				Ext.getCmp('serverAskRank10Grid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:3};
			}
		});
		Ext.getCmp('serverAskRank10Grid').store.removeAll();
		Ext.getCmp('serverAskRank10Grid').store.load();
	}
	
	this.QryInterAskRank10 = function(serverRow){
		var serviceId = serverRow.data.serviceId;
        var username = Ext.getCmp('username').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		//绑定发送请求参数

		Ext.getCmp('interAskRank10Grid').store.on('beforeload',function(store){
			if(Ext.getCmp('interAskRank10Grid').store.lastOptions != null){
				Ext.getCmp('interAskRank10Grid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:4};
			}
		});
		Ext.getCmp('interAskRank10Grid').store.removeAll();
		Ext.getCmp('interAskRank10Grid').store.load();
	}
	
	this.QryServerRespondRank10 = function(serverRow){
		var serviceId = serverRow.data.serviceId;
        var username = Ext.getCmp('username').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
		//绑定发送请求参数

		Ext.getCmp('serverRespondRank10Grid').store.on('beforeload',function(store){
			if(Ext.getCmp('serverRespondRank10Grid').store.lastOptions != null){
				Ext.getCmp('serverRespondRank10Grid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:5};
			}
		});
		Ext.getCmp('serverRespondRank10Grid').store.removeAll();
		Ext.getCmp('serverRespondRank10Grid').store.load();
	}
	
	this.initSuccessRateCountGrid = function (){
  		
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'id',hidden:true },
		    {header:'服务编号',dataIndex:'serviceId',width:swidth*0.15},
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.15},
		    {header:'成功次数',dataIndex:'successTimes',width:swidth*0.15},
		    {header:'失败次数',dataIndex:'faultTimes',width:swidth*0.15},
		    {header:'成功率',dataIndex:'successRate',width:swidth*0.15}
		]);

		var serverGrid =  new ZTESOFT.Grid({
			id:'serverGrid',
	    	region: 'center',
	        title:'服务列表',
			cm:column,
			pageSize:4,
			paging:true,
			height:170,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                           
	                        }
	                    }
	        })
	    });
	    

		var column1 = new Ext.grid.ColumnModel([
		    {header:'排名',dataIndex:'successRank',width:swidth*0.2 },
		    {header:'服务编号',dataIndex:'serviceId',width:swidth*0.2 },
		    {header:'服务名',dataIndex:'serviceName',width:swidth*0.2},
		    {header:'调用成功率(%)',dataIndex:'successRate',width:swidth*0.2}
		]);

		var successRateCount10Grid =  new ZTESOFT.Grid({
			id:'successRateCount10Grid',
	    	region: 'center',
	        title:'调用成功率后十名',
			cm:column1,
			pageSize:10,
			paging:false,
			height:400,
			url:'/MOBILE/ExtServlet?actionName=system/QrySystemMonitorTimeAndCountAction',
			sm: new Ext.grid.RowSelectionModel({
	                    singleSelect: true,
	                    listeners: {
	                        rowselect: function(sm, row, rec) {
	                        	//oper.outSysStaffGirdQry(rec);
	                        }
	                    }
	        })
	    });
	    
		var successRateCountPanel = new Ext.Panel({
			border: false,
			title: '调用成功率统计',
			region: 'center',
			layout: 'form',
			items:[serverGrid,successRateCount10Grid]
		}); 
		
	    return successRateCountPanel;
	
	}
	
	this.QrySuccessRateCount = function(){
	    var username = Ext.getCmp('username').getValue();
		var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();

	    Ext.getCmp('serverGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('serverGrid').store.lastOptions != null){
				   Ext.getCmp('serverGrid').store.baseParams = {serviceId:serviceId,username:username,beginDate:beginDate,endDate:endDate,flag:13};
				}
			}
	    )
	    Ext.getCmp('serverGrid').store.removeAll() ;
		Ext.getCmp('serverGrid').store.load({params:{start:0, limit:4}});
		
		Ext.getCmp('successRateCount10Grid').store.removeAll() ;
		oper.QrySuccessRateRank();
	}
	
	this.QrySuccessRateRank = function(){
        var serviceId = Ext.getCmp('serviceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('successRateCount10Grid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('successRateCount10Grid').store.lastOptions != null){
				   Ext.getCmp('successRateCount10Grid').store.baseParams = {serviceId:serviceId,beginDate:beginDate,endDate:endDate,flag:6};
				}
			}
	    )
	    Ext.getCmp('successRateCount10Grid').store.removeAll() ;
		Ext.getCmp('successRateCount10Grid').store.load();
		
		
	}
	
}


</script>