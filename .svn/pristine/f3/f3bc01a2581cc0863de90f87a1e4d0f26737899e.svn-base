<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>服务监控</title>
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
/**	by li.jianming **/


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
		 var monitorCallGrid = this.initMonitorCallGrid();
		// this.QryMonitorGrid();
		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '服务耗时统计',
			region: 'center',
			layout: 'border',
			items:[monitorGrid]
		}); 
		
		var monitorPanel = new Ext.Panel({
			border: false,
			title: '服务调用统计',
			region: 'center',
			layout: 'border',
			items:[monitorCallGrid]
		}); 
		
		var flowChartPanel = new Ext.Panel({
		    id:'flowChartPanel',
			border: false,
			title: '服务调用统计图',
			/*region: 'center',
			layout: 'border',*/
			 html : '<iframe id="flowChart" src="/MOBILE/api/busi/stat/shanghai/serv_stat.html" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
		});
		
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[monitorGrid,monitorCallGrid,flowChartPanel],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "monitorGrid":
						{
							oper.QryMonitorGrid();
							break ;
						}
						case "monitorCallGrid":
						{
							oper.QryMonitorCallGrid();
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
	        fields: ['restServiceId', 'servName'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QryServerMonitorAction'
	        }),
	        baseParams:{flag:2}
	        
	    });

	    return sysStore;
	}
	
    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'服务主键ID',dataIndex:'restServLogId',hidden:true },		    
		    {header:'人员编号',dataIndex:'staffId',hidden:true },
		    {header:'服务编号',dataIndex:'restServiceId',hidden:true },
		    {header:'用户',dataIndex:'userName',width:swidth*0.05},	
		    {header:'员工姓名',dataIndex:'staffName',width:swidth*0.05},
		    {header:'服务名',dataIndex:'servName',width:swidth*0.1},
		    {header:'总耗时(毫秒)',dataIndex:'consumeTime',width:swidth*0.1},
		    {header:'调用字节数',dataIndex:'inSize',width:swidth*0.1},
		    {header:'返回字节数',dataIndex:'outSize',width:swidth*0.1},
		    {header:'服务接收时间',dataIndex:'inTimestamp',width:swidth*0.1},
		    {header:'服务返回时间',dataIndex:'outTimestamp',width:swidth*0.1}		    
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'服务耗时统计',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QryServerMonitorAction',
			
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
	
	this.initMonitorCallGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'服务编号',dataIndex:'restServiceId',hidden:true },
		    {header:'服务名',dataIndex:'servName',width:swidth*0.2},
		    {header:'总耗时(毫秒)',dataIndex:'totalConsumeTime',width:swidth*0.1},
		    {header:'调用次数',dataIndex:'countNum',width:swidth*0.1},
		    {header:'平均调用耗时(毫秒)',dataIndex:'avgConsumeTime',width:swidth*0.1}
		]);

		var monitorCallGrid =  new ZTESOFT.Grid({
			id: 'monitorCallGrid',
			region: 'center',					
		    title:'服务调用统计',
		    cm:column,
		    pageSize:16,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/QryServerMonitorAction',
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	
		            }
		        }
		    })
	    });
        		 
	    return monitorCallGrid;	
	}
	
	
	this.QryMonitorGrid = function(){
	    var username = Ext.getCmp('username').getValue();
		var restServiceId = Ext.getCmp('restServiceId').getValue();
		var beginDate = Ext.getCmp('beginDate').getValue();
		var endDate = Ext.getCmp('endDate').getValue();
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {restServiceId:restServiceId,username:username,inTimestamp:beginDate,outTimestamp:endDate,flag:1};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.QryMonitorCallGrid = function(){
		var restServiceId = Ext.getCmp('restServiceId').getValue();
	    Ext.getCmp('monitorCallGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorCallGrid').store.lastOptions != null){
				   Ext.getCmp('monitorCallGrid').store.baseParams = {restServiceId:restServiceId,flag:3};
				}
			}
	    )
	    Ext.getCmp('monitorCallGrid').store.removeAll() ;
		Ext.getCmp('monitorCallGrid').store.load({params:{start:0, limit:16}});
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
                    name: 'restServiceId',
	                id: 'restServiceId',
	                valueField: 'restServiceId',
	                displayField: 'servName',
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
		}	
	}

	function reset(){
		Ext.getCmp('username').setValue('');
		Ext.getCmp('restServiceId').setValue('');
		//Ext.getCmp('serviceName').setValue('');
		Ext.getCmp('beginDate').setValue('');
		Ext.getCmp('endDate').setValue('');
	}
}


</script>