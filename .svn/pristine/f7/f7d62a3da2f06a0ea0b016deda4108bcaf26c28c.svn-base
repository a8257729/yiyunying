<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>应用分类管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
		<script type="text/javascript" src="../ext/ux/DateTimeField.js"></script>
		<script type="text/javascript" src="../ext/ux/Spinner.js"></script>
		<script type="text/javascript" src="../ext/ux/SpinnerField.js"></script>
	    <script language="javascript" src="js/appClass.js"></script>

		
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
var classOper = new ClassOper(); 
var staffName = session1.staff.staffName ;
var fext = new BSCommon();
var oper = new Oper();

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

        var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
		var rightClick1 = new Ext.menu.Menu({
		    id:'rightClick1'
		});
		var nullRightClick1 = new Ext.menu.Menu({
		    id:'nullRightClick1'
		});
    
    oper.QryApkclassGrid();
    oper.initTreeEvent();
    	  
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[moduelTree,menuGrid]
	});
});

function Oper(){
    var moduleId = -1;
	//目录树初始化
	this.initModuelTree = function(){
	
	    //创建列
		var classColumn = new Ext.grid.ColumnModel({		 
         defaults:{
             css:'height:30px;'
         },        
		 columns:[  
		    {header:'osType',dataIndex:'osType',hidden:true },	
		    {header:'id',dataIndex:'id',hidden:true },	
		    {header:'终端平台',dataIndex:'osTypeName',width:230}   
		    
		]});   
		   
		var apkClassGrid =  new ZTESOFT.Grid({
			id: 'apkClassGrid',
			region: 'west',		
			width:240,		
		    title:'终端平台',
		    cm:classColumn,
		    pageSize:200,
		    hideHeaders:true,
			paging:false,
			url:'/MOBILE/ExtServlet?actionName=system/AppClassManageAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){	            
             
		                oper.QryClassGrid();
		            }
		        }
		    })
	    });
				
		return apkClassGrid;
	}
	
	this.QryApkclassGrid = function(){
        
		Ext.getCmp('apkClassGrid').store.on('beforeload',
			function(store){ 
				if(Ext.getCmp('apkClassGrid').store.lastOptions != null){
					Ext.getCmp('apkClassGrid').store.baseParams = {flag:4};
				}
			}
		)

	    Ext.getCmp('apkClassGrid').store.removeAll() ;
		Ext.getCmp('apkClassGrid').store.load({params:{start:0, limit:20}});
	}
	
    this.initTreeEvent = function(){
	    Ext.getCmp('apkClassGrid').addListener('rowclick',oper.QryClassGrid); 	    
	    Ext.getCmp('apkClassGrid').addListener('rowcontextmenu', oper.rightClickFn1);
	    Ext.getCmp('classGrid').addListener('rowcontextmenu', oper.rightClickFn);
	    Ext.getCmp('apkClassGrid').addListener('contextmenu', oper.nullRightClickFn1);  
	    Ext.getCmp('classGrid').addListener('contextmenu', oper.nullRightClickFn);  
	}
	 

	this.initMenuGrid = function(){
        
		var menuColumn = new Ext.grid.ColumnModel({
		 defaults:{
             css:'height:50px;'
         },        
		 columns:[	   
		 		    
			{header:'菜单ID',dataIndex:'menuId',hidden:true },	
		    {header:'路径ID',dataIndex:'pathCode',hidden:true },	
		    {header:'父ID',dataIndex:'parentId',hidden:true },	
		    {header:'menuIconUri',dataIndex:'menuIconUri',hidden:true },	
		    {header:'privCode',dataIndex:'privCode',hidden:true },		    		    
		    {header:'osType',dataIndex:'osType',hidden:true },  	
		    {header:'应用分类',dataIndex:'menuName',width:180},
		    {header:'分类顺序',dataIndex:'menuIndex'}    
  	    
		]});   
				
		var apkGrid =  new ZTESOFT.Grid({
			id: 'classGrid',
			region: 'center',					
		    title:'应用分类列表',
		    cm:menuColumn,
		    pageSize:10,
			paging:true,
			url:'/MOBILE/ExtServlet?actionName=system/AppManagementAction',
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){	            
             
		            }
		        }
		    })
	    });
		
		return apkGrid;
	}
	
	this.QryClassGrid = function(){
        var selGridItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
	        var osType = selGridItem[0].data.osType;
	        Ext.getCmp('classGrid').store.on('beforeload',
				function(store){ 
					if(Ext.getCmp('classGrid').store.lastOptions != null){
					   Ext.getCmp('classGrid').store.baseParams = {flag:3,osType:osType};
					}
				}
		    )
		    Ext.getCmp('classGrid').store.removeAll() ;
			Ext.getCmp('classGrid').store.load({params:{start:0, limit:16}});
		}
	}
		   	      
	
	//菜单组装
	this.rightClickFn = function(classGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加应用分类' ,handler: function() {
			rightClick.hide();
			oper.moduelAdd();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改应用分类' ,handler: function() {
			rightClick.hide();
			oper.moduelMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除应用分类' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.menuDel);
		}}));

		Ext.getCmp('classGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
	    var i = 0 ;
		var nullRightClick = Ext.getCmp('nullRightClick');
		nullRightClick.removeAll();
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '增加应用分类' ,handler: function() {
			nullRightClick.hide();
			oper.moduelAdd();
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    }
    
    this.moduelAdd = function(){
        var selGridItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
	        var osType = selGridItem[0].data.osType;
	    
	    	var pmName ="" ;
	    	var pmId = osType ;  	
	    	classOper.showModuleInfoPage('add',pmName,pmId);
    	}
    	
    }
    
    this.moduelMod = function(){
		var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.menuName ;
    	var pmId = selGridItem[0].data.menuId ;
    	
    	classOper.showModuleInfoPage('mod',pmName,pmId);
    }
    
    this.menuDel =  function(btn){
      if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('classGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "menuDel";
        paramObj.menuId = selGridItem[0].data.menuId;
        
        var retMap = invokeAction("/appmanage/OptAppFunctionAction", paramObj);
        if (retMap){
           oper.QryClassGrid();
        }
      }
    }
    
    this.rightClickFn1 = function(apkClassGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick1');
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加终端平台' ,handler: function() {
			rightClick.hide();
			oper.zdAdd();
		}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改终端平台' ,handler: function() {
			rightClick.hide();
			oper.zdMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除终端平台' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.zdDel);
		}}));

		//Ext.getCmp('apkClassGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn1 = function(e){
	    var i = 0 ;
		var nullRightClick = Ext.getCmp('nullRightClick1');
		nullRightClick.removeAll();
		nullRightClick.insert(i++,new Ext.menu.Item({ text: '增加终端平台' ,handler: function() {
			nullRightClick.hide();
			oper.zdAdd();
		}}));
    	e.preventDefault();
	    nullRightClick.showAt(e.getXY());
    }
    
    
    this.zdAdd = function(){
        var selGridItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
        if (selGridItem != null && selGridItem.length >0){
	        var osType = selGridItem[0].data.osType;
	    
	    	var pmName ="" ;
	    	var pmId = osType ;  	
	    	classOper.showSystemInfoPage('add',pmName,pmId);
    	}
    	
    }
    
    this.zdMod = function(){
		var selGridItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
    	var pmName = selGridItem[0].data.osTypeName ;
    	var pmId = selGridItem[0].data.osType ;
    	
    	classOper.showSystemInfoPage('mod',pmName,pmId);
    }
    
    this.zdDel =  function(btn){
      if (btn == 'yes'){  
        var selGridItem = Ext.getCmp('apkClassGrid').getSelectionModel().getSelections();
        var paramObj = new Object();
        paramObj.type = "sysDel";
        paramObj.id = selGridItem[0].data.id;
        
        var retMap = invokeAction("/mobsystem/ApkRegManageAction", paramObj);  
        if (retMap){
           oper.QryApkclassGrid();
        }
      }
    }
    
}

</script>