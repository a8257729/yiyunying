<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用审核</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
		<script type="text/javascript" src="../ext/ux/DateTimeField.js"></script>
		<script type="text/javascript" src="../ext/ux/Spinner.js"></script>
		<script type="text/javascript" src="../ext/ux/SpinnerField.js"></script>
	    <script language="javascript" src="js/apkAudit.js"></script>

		
	</head> 
	<style type="text/css">
		.x-window-dlg .ext-mb-download {
		    background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
		    height:46px;
		}

	</style>
	<body onContextMenu="return false;" style="width: 100%; height: 100%; overflow: hidden">
		<div id="menuMngDiv"></div>
	</body>
</html> 

<script language="JScript">
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var session1 = GetSession();
var staffId = session1.staff.staffId ;
var staffName = session1.staff.staffName ;
var fext = new BSCommon();
var oper = new Oper();

var apkAuditOper = new ApkAuditOper();
//var menuOper = new MenuOper();
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
Ext.apply(Ext.form.VTypes, {


    
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

    var moduelTree = oper.initModuelTree();
    var menuGrid = oper.initMenuGrid();


    oper.initGridEvent();
    oper.initTreeEvent();
    
    var tabs = new Ext.TabPanel({
			region: 'west',
			id : 'tabs',
		    activeTab: 0,
		    resizeTabs:true,
		    tabWidth:80, 
		    width:215,   
		    height:Ext.getBody().getSize().height*0.8, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[moduelTree],
		    		    listeners: {
	            tabchange: function(){
	            	var activeTab = tabs.activeTab.id ;
	            	switch (activeTab){
						case "classGrid":
						{

							break ;
						}
						
					}	
	    		}
	    }
		});

	  
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[tabs,menuGrid]
	});
});

function Oper(){
    var moduleId = -1;
	//目录树初始化
	this.initModuelTree = function(){
	
	    var data = [
			['1','最近注册'],
			['4','最近更新']
		];

		var ds = new Ext.data.Store({
			proxy: new Ext.data.MemoryProxy(data),
			reader: new Ext.data.ArrayReader({}, [
				{name: 'funcStatus',mapping: 0},
				{name: 'funcStatusName',mapping: 1}

			])
		});
   		
		//创建列
		var classColumn = new Ext.grid.ColumnModel({		 
         defaults:{
             css:'height:30px;'
         },        
		 columns:[  
		    {header:'应用状态id',dataIndex:'funcStatus',hidden:true },
		    {header:'应用状态',dataIndex:'funcStatusName',width:200 }      
		    
		]});   
		   
		var statusGrid =  new Ext.grid.GridPanel({
			id: 'statusGrid',
			region: 'west',		
			width:210,		
		    title:'Android',
		    cm:classColumn,
            store:ds,
			
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){	            
             
		            }
		        }
		    })
	    });
	    
	    
		ds.load();
				
		return statusGrid;
	}
	
    this.initTreeEvent = function(){
	    Ext.getCmp('statusGrid').addListener('rowclick',oper.QryMenuGrid); 	    
	}
	 

	this.initMenuGrid = function(){
        
        var checkMenushow = new Ext.grid.CheckboxSelectionModel();
		var menuColumn = new Ext.grid.ColumnModel({
		 defaults:{
             css:'height:50px;'
         },        
		 columns:[	 
		    checkMenushow,   
		    {header:'目录ID',dataIndex:'muneId',hidden:true },	
		    {header:'apkId',dataIndex:'apkId',hidden:true },			
		    {header:'funId',dataIndex:'funId',hidden:true },	
		    {header:'funcRegStaffId',dataIndex:'funcRegStaffId',hidden:true },			        	    
		    {header:'应用图标',dataIndex:'apkIcon',width:60 ,renderer:function (val){ if (val != null){return "<img src='"+val+"'/>"} else {return "<img src='image/001.png'/>";}}},
		    {header:'应用名称',dataIndex:'muneName',width:100 },
		    {header:'软件介绍内容',dataIndex:'apkContent',width:300}, 
		    {header:'应用大小',dataIndex:'apkSize',width:100,width:100},
		    {header:'版本号',dataIndex:'apkVersionNo',width:100}

  	    
		]});   
				
		var apkGrid =  new ZTESOFT.Grid({
			id: 'apkGrid',
			region: 'center',					
		    title:'应用列表',
		    cm:menuColumn,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/AppClassManageAction',
			sm: checkMenushow
	    });
		
		return apkGrid;
	}
	
	this.QryMenuGrid = function(){
        
        var selGridItem = Ext.getCmp('statusGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
            var funcStatus = selGridItem[0].data.funcStatus;
		    Ext.getCmp('apkGrid').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('apkGrid').store.lastOptions != null){
					   Ext.getCmp('apkGrid').store.baseParams = {funcStatus:funcStatus,flag:2};
					}
				}
		    )
	    }
	    Ext.getCmp('apkGrid').store.removeAll() ;
		Ext.getCmp('apkGrid').store.load({params:{start:0, limit:10}});
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
		Ext.getCmp('apkGrid').addListener('rowcontextmenu', oper.rightClickFn);

	}
	
	//菜单组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
				
		var selGridItem = Ext.getCmp('statusGrid').getSelectionModel().getSelections();
		if (selGridItem != null && selGridItem.length >0 && selGridItem[0].data.funcStatus == "1"){
		  var i = 0 ;
		  var rightClick = Ext.getCmp('rightClick');
		
		  rightClick.removeAll();
		  rightClick.insert(i++,new Ext.menu.Item({ text: '应用审核' ,handler: function() {
			rightClick.hide();
			oper.apkAudit();
		  }}));
		  
		  e.preventDefault();
	      rightClick.showAt(e.getXY());
		}
			    
	}
	

    
    this.apkAudit = function(){
    
       var selItem2 = Ext.getCmp('apkGrid').getSelectionModel().getSelections();
	   if(selItem2 ==null || selItem2.length ==0){
            Ext.MessageBox.show({
                 title: '提示',
                 msg: '请选择应用！',
                 buttons: Ext.MessageBox.OK,
                 width:200,
                 icon: Ext.MessageBox.ERROR
             });
             return;
       }
       var apkIds="" ;
       for (j=0;j<selItem2.length;j++){
           var inputParams = new Object();            
	       apkIds = apkIds +selItem2[j].data.muneId +',';           		    

       }
       apkIds = apkIds.substring(0,apkIds.length -1);
       var pmName = staffName ;   	
       apkAuditOper.showModuleInfoPage('audit',pmName,apkIds);
    }
    
}

</script>