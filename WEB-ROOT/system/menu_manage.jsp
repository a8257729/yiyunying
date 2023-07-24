<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<title>菜单管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/BSCommon.js"></script>
		<script language="javascript" src="js/moduelMng.js"></script>
		<script language="javascript" src="js/menuMng.js"></script>
		<script language="javascript" src="js/moduelSel.js"></script>
	</head>
	<style type="text/css">
		.x-window-dlg .ext-mb-download {
		    background:transparent url(../ext/examples/message-box/images/download.gif) no-repeat top left;
		    height:46px;
		}
	</style>
	<body onContextMenu="return false;"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="menuMngDiv"></div>
	</body>
</html>

<script language="JScript">

/**	by fang.li   2010.10 **/

var session1 = GetSession();
var staffId = session1.staff.staffId ;
var fext = new BSCommon();
var oper = new Oper();
var moduelOper = new ModuelOper();
var menuOper = new MenuOper();
var menuSelOper = new MenuSelOper();

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
    oper.initTreeEvent();
    oper.initGridEvent();
    
    var treeMenu = oper.initTreeMenu();
    var infoPanel = oper.initInfoPanel();
    
    var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[menuGrid,infoPanel]
	}); 
    
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[moduelTree,earthviewport]
	});
});

function Oper(){
	//菜单目录树初始化
	this.initModuelTree = function(){
		var moduelTree = new Ext.tree.TreePanel({
			id: 'moduelTree',
	    	title:'菜单目录',
	    	region: 'west',
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/ModuleTreeAction'
	        }),
	        containerScroll: true,
	        border: false,
	        width:Ext.getBody().getSize().width*0.2,
		    height:Ext.getBody().getSize().height,
	        rootVisible: false,
	        root: new Ext.tree.AsyncTreeNode({
	            id:'0'
	        })
	    });
	    new Ext.tree.TreeSorter(moduelTree, {folderSort:true});
	    return moduelTree ;
	}
	
	//菜单列表初始化
	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'id', 'name', 'urlString', 'iconFileName','privCode','displayIndex','showModel','comments'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/MenuGridAction'
	        }),
	        baseParams:{moduleId:0},
	        listeners:{
	        	load: function(store){
					Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
				}
	        }
	    });
		
		//创建列
		var menuColumn = new Ext.grid.ColumnModel([
		    {header:'动作编号',dataIndex:'id',hidden:true },
		    {header:'显示类型',dataIndex:'showModel',hidden:true },
		    {header:'备注',dataIndex:'comments',hidden:true },
		    {header:'菜单名称',dataIndex:'name',width:200 },
		    {header:'连接页面',dataIndex:'urlString',width:200},
		    {header:'图标文件',dataIndex:'iconFileName',width:200},
		    {header:'权限代码',dataIndex:'privCode',width:130},
		    {header:'显示顺序',dataIndex:'displayIndex',width:100}
		]);   
		menuColumn.defaultSortable = true;
		
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'菜单列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	Ext.getCmp("name").setValue(rec.data.name);
		            	Ext.getCmp("urlString").setValue(rec.data.urlString);
		            	Ext.getCmp("iconFileName").setValue(rec.data.iconFileName);
		            	Ext.getCmp("privCode").setValue(rec.data.privCode);
		            	Ext.getCmp("displayIndex").setValue(rec.data.displayIndex);
		            	Ext.getCmp("comment").setValue(rec.data.comments);
		            	
		            	var  showModel = rec.data.showModel;
		            	if(showModel == '0'){
		            		Ext.getCmp("showModel").setValue("非模态");
		            	}else{
		            		Ext.getCmp("showModel").setValue("模态");
		            	}
		            }
		        }
		    })
	    });
		menuStore.load({params:{moduleId:0}});
		return menuGrid;
	}
	
	//菜单详情表单
	this.initInfoPanel = function(){
		var infoPanel = new Ext.FormPanel({
	        region: 'south',
	     	labelAlign: 'left',
		 	frame:true,
	        title: '菜单信息',
	        bodyStyle:'padding:5px 5px 0',
	        height:Ext.getBody().getSize().height*0.5,
	        width:Ext.getBody().getSize().width*0.8,
	        items: [{
	            layout:'column',
	            items:[{
	                columnWidth:.5,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '菜单名称',
	                    name: 'name',
	                    id: 'name',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '链接页面',
	                    name: 'urlString',
	                    id: 'urlString',
	                    anchor:'100%'
	                }, {
	                    xtype:'textfield',
	                    fieldLabel: '图标文件',
	                    name: 'iconFileName',
	                    id: 'iconFileName',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'},{
	                columnWidth:.4,
	                layout: 'form',
	                items: [{
	                    xtype:'textfield',
	                    fieldLabel: '显示类型',
	                    name: 'showModel',
	                    id: 'showModel',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '显示顺序',
	                    name: 'displayIndex',
	                    id: 'displayIndex',
	                    vtype: 'num',
	                    anchor:'100%'
	                },{
	                    xtype:'textfield',
	                    fieldLabel: '权限代码',
	                    name: 'privCode',
	                    id: 'privCode',
	                    anchor:'100%'
	                }]
	            },{columnWidth:.1,layout: 'form'}]
	        },{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'comment',
			    id: 'comment',
			    height : 130,
			    anchor:'100%'
			}]
	    });
		return infoPanel;	    
	}
	
	//定义菜单目录树事件
	this.initTreeEvent = function(){
		Ext.getCmp('moduelTree').on('click', function(node){ //使节点可以单击展开收起，默认是双击的
		    if(node.isLeaf() == false){
				if(node.expanded == false){
					node.expand();//展开
				}else{
					node.collapse();//收起
				} 
		    }
		    moduleId = node.id ;
		    Ext.getCmp('menuGrid').store.on('beforeload',
				function(store){ 
					if(store.lastOptions != null){
						store.baseParams = {moduleId:moduleId};
					}
				}
			)
		    Ext.getCmp('menuGrid').store.removeAll() ;
			Ext.getCmp('menuGrid').store.load();
	    });
	    
	    Ext.getCmp('moduelTree').on("contextmenu",oper.menuContextMenu,Ext.getCmp('moduelTree'));
	}
	
	//定义菜单目录右键菜单
	this.initTreeMenu = function(){
		var treeMenu = new Ext.menu.Menu({
	        id : 'treeMenu'
		});
		return treeMenu ;
	}
	
	//装菜单目录邮件菜单
	this.menuContextMenu = function(node,e){
		var treeMenu = Ext.getCmp('treeMenu');
    	node.select();
    	var parentId = node.attributes.parentId ;
    	var i = 1 ;
    	treeMenu.removeAll();
    	if(parentId == 0){
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '修改模块' ,handler: function() {
				treeMenu.hide();
				oper.moduelMod();
			}}));
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加子模块' ,handler: function() {
				treeMenu.hide();
				oper.subModuelAdd();
			}}));
    	}else{
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加模块' ,handler: function() {
				treeMenu.hide();
				oper.moduelAdd();
			}}));
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '修改模块' ,handler: function() {
				treeMenu.hide();
				oper.moduelMod();
			}}));
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '删除模块' ,handler: function() {
				treeMenu.hide();
				oper.muduleDelVlt();
			}}));
			treeMenu.add(new Ext.menu.Separator());
			i++ ;
    		treeMenu.insert(i++,new Ext.menu.Item({ text: '增加子模块' ,handler: function() {
				treeMenu.hide();
				oper.subModuelAdd();
			}}));
    	}
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
    }
    
    this.moduelAdd = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.parentNode.text ;
    	var pmId = selModuleNode.parentNode.id ;
    	
    	moduelOper.showModuleInfoPage('add',pmName,pmId);
    }
    
    this.moduelMod = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.parentNode.text ;
    	var pmId = selModuleNode.parentNode.id ;
    	
    	moduelOper.showModuleInfoPage('mod',pmName,pmId);
    }
    
    this.muduleDelVlt = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var selMenu = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
    	
		if(selModuleNode.childNodes.length > 0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除子目录',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
			return false;
		}
		if(selMenu.length > 0){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除当前目录下的菜单',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
			return false;
		}
		var isExistSubPrivClass = callRemoteFunction("com.ztesoft.iom.system.MenuManager","isExistSubPrivClass",selModuleNode.id);
		if(isExistSubPrivClass){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: '请先删除该菜单目录下的权限类别',
	           buttons: Ext.MessageBox.OK,
	           width:200,
	           icon: Ext.MessageBox.ERROR
	       	});
		  	return false;
		}
		Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.moduleDel);
    }
    
    this.moduleDel = function(btn){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	if(btn == 'no'){
    		return ;
    	}
		objAttr = callRemoteFunction("com.ztesoft.iom.system.MenuManager","delMenuCatalog",selModuleNode.id);
		Ext.MessageBox.show({
           	msg: '系统正在提交数据……',
           	progressText: 'Saving...',
           	width:300,
           	wait:true,
           	waitConfig: {interval:100},
           	icon:'ext-mb-download'
       	});
        setTimeout(function(){
            Ext.MessageBox.hide();
            Ext.example.msg('','删除模块成功！');
        }, 1000);
        Ext.getCmp('moduelTree').root.reload();
    }
    
    this.subModuelAdd = function(){
    	var selModuleNode = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
    	var pmName = selModuleNode.text ;
    	var pmId = selModuleNode.id ;
    	
    	moduelOper.showModuleInfoPage('addSub',pmName,pmId);
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
		Ext.getCmp('menuGrid').addListener('rowcontextmenu', oper.rightClickFn);
		Ext.getCmp('menuGrid').addListener('contextmenu', oper.nullRightClickFn);
	}
	
	//菜单组装
	this.rightClickFn = function(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加菜单' ,handler: function() {
			rightClick.hide();
			oper.menuAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改菜单' ,handler: function() {
			rightClick.hide();
			oper.menuMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除菜单' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', oper.menuDel);
		}}));
		
		rightClick.add(new Ext.menu.Separator());
		i++ ;
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '更改目录' ,handler: function() {
			rightClick.hide();
			oper.menuModuelMod();
		}}));
			
		Ext.getCmp('menuGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	this.nullRightClickFn = function(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加菜单' ,handler: function() {
			nullRightClick.hide();
			oper.menuAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }
    
    //菜单方法
    this.menuAdd = function(){
    	menuOper.showMenuInfoPage('add');
    }
    
    this.menuMod = function(){
    	menuOper.showMenuInfoPage('mod');
    }
    
    this.menuDel = function(btn){
    	if(btn == 'no'){
    		return ;
    	}
    	var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var objAttr = callRemoteFunction("com.ztesoft.iom.system.MenuManager","delMenu",selItem[0].data.id);
		
		Ext.MessageBox.show({
           	msg: '系统正在提交数据……',
           	progressText: 'Saving...',
           	width:300,
           	wait:true,
           	waitConfig: {interval:100},
           	icon:'ext-mb-download'
       	});
        setTimeout(function(){
            Ext.MessageBox.hide();
            Ext.example.msg('','成功删除菜单！');
        }, 1000);
        
        Ext.getCmp('menuGrid').store.removeAll();
        Ext.getCmp('menuGrid').store.reload();
    }
    
    this.menuModuelMod = function(){
    	menuSelOper.showModuleSelPage();
    }
}

</script>