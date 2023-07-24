<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>系统监控</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/enumMng.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
			<div id='menuMngDiv'></div>				
	</body>
</html>

<script language="JScript">

Ext.apply(Ext.form.VTypes, {
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！'
});
var oper = new Oper();
var otherApkOper = new OtherApkOper();
Ext.onReady(function(){

	var otherApkGrid = oper.initMenuGrid();
	var otherApkPanel = oper.initInfoPanel();
	oper.initGridMenu();
	oper.initGridEvent();
	
	
	
	var otherPanel = new Ext.Panel({
		border: false,
		title: '系统监控',
		region: 'center',
		layout: 'border',
		items:[otherApkGrid]
	}); 
	
	var tabs = new Ext.TabPanel({
		region: 'center',
		id : 'tabs',
	    activeTab: 0,
	    width:Ext.getBody().getSize().width*0.85,   
	    height:Ext.getBody().getSize().height*0.9, 
	    plain:true,
	    defaults:{autoScroll: true},
	    items:[otherPanel]
	});
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[tabs]
	});

});

function Oper(){
	//APK管理列表初始化
	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'id', 'phoneModel', 'staffName', 'staffId','systemCode','startDate','endDate'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/MobLoginLogAction'
	        }),
	        //baseParams:{moduleId:0},//根据moduleId来载入
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectFirstRow();
				}
	        }
	    });
		
		//创建列'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'标识Id',dataIndex:'id',hidden:true },
		    {header:'手机型号',dataIndex:'phoneModel',width:150},
		    {header:'用户名',dataIndex:'staffName',width:150 },
		    {header:'用户ID',dataIndex:'staffId',width:150},
		    {header:'访问系统编码',dataIndex:'systemCode',width:200},
		    {header:'登录时间',dataIndex:'startDate',width:200},
		    {header:'登出时间',dataIndex:'endDate',width:200}
		    
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'系统监控',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	// Ext.getCmp("p_id").setValue(rec.data.id);
		            
		            }
		        }
		    })
	    });
		//menuStore.load({params:{moduleId:0}});//根据moduleId来载入
		return menuGrid;
	}
	
	//APK详情表单'apkId', 'apkCode', 'apkVersionNo', 'apkPackage','apkUrl','forceUpdate','createDate','comments'
	this.initInfoPanel = function(){
		var infoPanel1 = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: '枚举详细信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.5,
	        width:Ext.getBody().getSize().width,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.4,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '枚举ID',
	                    name: 'p_enumId',
	                    id: 'p_enumId',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '枚举名称',
	                    name: 'p_enumName',
	                    id: 'p_enumName',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '枚举图标',
	                    name: 'p_enumImage',
	                    id: 'p_enumImage',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '枚举类型',
	                    name: 'p_enumType',
	                    id: 'p_enumType',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '是否显示',
	                    name: 'p_isShow',
	                    id: 'p_isShow',
	                    anchor:'100%'
	                }]
	            }]
	        }]
	    });
		return infoPanel1;	    
	}
	
	//定义APK管理列表菜单
    this.initGridMenu = function(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义APK管理列表事件
	this.initGridEvent = function(){
		Ext.getCmp('menuGrid').store.removeAll() ;
		Ext.getCmp('menuGrid').store.load();
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//APK管理组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加枚举' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改枚举' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除枚举' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', otherApkOper.moduleDel);
		}}));
	
			
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加枚举' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //APK管理方法
    this.menuAdd = function(){
    	otherApkOper.showMenuInfoPage('add');
    }
    
    this.menuMod = function(){
    	otherApkOper.showMenuInfoPage('mod');
    }
    
  

}




</script>
