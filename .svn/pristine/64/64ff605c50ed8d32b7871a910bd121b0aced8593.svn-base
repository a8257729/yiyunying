<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>服务管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
	<script type="text/javascript" src="js/restServicePage.js"></script></head>
	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id='sysGird' />
	</body>
</html>
<script type="text/javascript">
   

var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var callRowCount = 5; 
var session1 = GetSession();
var oper = new Oper(); 
var restOper = new RestOper();
/**
Ext.apply(Ext.form.VTypes, {
    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },passwordText: '确认密码与密码不一致！',
    
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！',
    
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
*/
   Ext.onReady(function(){
		var mainPanel = oper.initMainPanel();
	   var reigthEvent =oper.initGridEvent();
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
		
		 		 
         var monitorPanel = new Ext.Panel({
			border: false,
			title: '服务管理',
			region: 'center',
			layout: 'border',
			items:[monitorGrid]
		}); 
		
				
		var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[monitorGrid],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "monitorGrid":
						{
							oper.QryMonitorGrid();
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
	    this.initMonitorGrid = function(){
    
		var column = new Ext.grid.ColumnModel([
		    {header:'主键',dataIndex:'restServiceId',hidden:true },		    
		    {header:'服务状态编号',dataIndex:'servStaus',hidden:true },
		    {header:'服务类型编号',dataIndex:'servType',hidden:true },
		    {header:'服务名称',dataIndex:'servName',width:swidth*0.25},		    		    
		    {header:'服务地址',dataIndex:'servAddr',width:swidth*0.3},
		    {header:'服务状态',dataIndex:'servStausName',width:swidth*0.1},	
		     {header:'服务类型',dataIndex:'servTypeName',width:swidth*0.1},	
		   {header:'时间',dataIndex:'state',width:swidth*0.13,hidden:true},
		    {header:'时间',dataIndex:'stateDate',width:swidth*0.13,hidden:true}, 
		    {header:'创建时间',dataIndex:'buildTime',width:swidth*0.13,hidden:true},
		    {header:'更新时间',dataIndex:'updateTime',width:swidth*0.13,hidden:true}
		]);

		var monitorGrid =  new ZTESOFT.Grid({
			id: 'monitorGrid',
			region: 'center',					
		    title:'服务列表',
		    cm:column,
		    pageSize:15,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/SelRestServiceAction',
			
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
	    var servName = Ext.getCmp('servName').getValue();
	       var servAddr = Ext.getCmp('servAddr').getValue();
		var servStaus = Ext.getCmp('servStaus').getValue();
	    Ext.getCmp('monitorGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('monitorGrid').store.lastOptions != null){
				   Ext.getCmp('monitorGrid').store.baseParams = {servName:servName,servAddr:servAddr,servStaus:servStaus,flag:1};
				}
			}
	    )
	    Ext.getCmp('monitorGrid').store.removeAll() ;
		Ext.getCmp('monitorGrid').store.load({params:{start:0, limit:16}});
	}
	
	this.initServeStore = function(){
	     var sysStore = new Ext.data.JsonStore({
			id: 'sysStore',
	        remoteSort: true,
	        fields: ['mcode', 'mname'],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/api/param/list/SERV_STATUS'
	        }),
	          baseParams:{flag:1}
	    
	        
	    });

	    return sysStore;
	}
	
	this.initQryPanel = function(){
	 
	     var sysStore = this.initServeStore();
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
        },{
               text: '刷新缓存',
             onClick:reFlush
           }],
        items: [{
            layout:'column',
            items:[{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '服务名称',
                    name: 'servName',
                    id: 'servName',
                    anchor:'95%'
                },
                {
                    xtype:'textfield',
                    fieldLabel: '服务地址',
                    name: 'servAddr',
                    id: 'servAddr',
                    anchor:'95%'
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    fieldLabel: '服务状态',
                    xtype: 'combo',
                    name: 'servStaus',
	                id: 'servStaus',
	                valueField: 'mcode',
	                displayField: 'mname',
	                mode:'remote',
	                triggerAction: 'all',
	                forceSelection: true,
					editable :false,
					anchor:'95%',
	                store:sysStore
                }]
            }]
        }]
	});
	   
       return qryPanel;
	}
	

	     //定义菜单列表菜单
    this.initGridMenu = new function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	this.initGridEvent = function(){
		Ext.getCmp('monitorGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('monitorGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	    
	//菜单组装
	this.rightClickFn = function(monitorGrid,rowIndex,e){
	
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		Ext.getCmp('monitorGrid').getSelectionModel().selectRow(rowIndex);
		
			rightClick.insert(i++,new Ext.menu.Item({ text: '添加服务' ,handler: function() {
					rightClick.hide();
					oper.apkReg();
			}}));
		


			rightClick.insert(i++,new Ext.menu.Item({ text: '修改服务' ,handler: function() {
				rightClick.hide();
				oper.apkMod();
			}}));
			
			rightClick.insert(i++,new Ext.menu.Item({ text: '删除服务' ,handler: function() {
				rightClick.hide();
				Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.apkDel);
			}}));
			
		//	rightClick.insert(i++,new Ext.menu.Item({ text: '服务详情' ,handler: function() {
		//		rightClick.hide();
			//	oper.apkDetail();
		//	}}));

				
		Ext.getCmp('monitorGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	   this.nullRightClickFn = function(e){

	
	    var i =0;
		var nullRightClick = Ext.getCmp('nullRightClick');
        nullRightClick.removeAll();

		nullRightClick.insert(i++,new Ext.menu.Item({ text: '服务添加' ,handler: function() {
			nullRightClick.hide();
			oper.apkReg();
		}}));
         
         
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    	
    }
 
	//添加调用的弹出框
   this.apkReg = function(){
		   var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
		  //  var appid=   selGridItem[0].data.appId;
          restOper.funcInfoPage('add',null,null,null);
    }
    //应用修改
    this.apkMod = function (){
    	restOper.funcInfoPage('mod',null,null,null);
    	
    }
	 //应用删除
    this.apkDel = function (btn){
		 
    	if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('monitorGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "del";
        paramObj.optfunmenu='app';
        paramObj.restServiceId = selGridItem[0].data.restServiceId;
        var retMap = invokeAction("/rest/OptRestServiceAction", paramObj);
        if (retMap){
           oper.QryMonitorGrid();
        }
      }
    	
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
		function reFlush(){
			Ext.MessageBox.confirm('提示', '是否要刷新REST服务列表缓存？', oper.restFlush);
			
		}
   this.restFlush = function (btn){
		 
    
        var paramObj = new Object();
        paramObj.type = "flush";
        paramObj.optfunmenu='app';
<%--        var retMap = invokeAction("/rest/OptRestServiceAction", paramObj);--%>
<%--        if (retMap){--%>
<%--           oper.QryMonitorGrid();--%>
<%--        }--%>
      }
    	
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
		Ext.getCmp('servName').setValue('');
		Ext.getCmp('servStaus').setValue('');
		Ext.getCmp('servAddr').setValue('');
	}
</script>