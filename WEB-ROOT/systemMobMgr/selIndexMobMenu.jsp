<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>移动菜单管理</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
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

var oper = new Oper();
function Oper(){
    var moduleId = -1;
	//目录树初始化
	this.initModuelTree = function(){
		var moduelTree = new Ext.tree.TreePanel({
			id: 'moduelTree',
	    	title:'移动菜单目录',
	    	region: 'west',
	        autoScroll:true,
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl:'/MOBILE/ExtServlet?actionName=system/MobMuneTreeAction&type=model'
	        }),
	        containerScroll: true,
	        border: false,
	        width:Ext.getBody().getSize().width*0.2,
		    height:Ext.getBody().getSize().height*0.92
	    });
	     var confRoleRoot = new Ext.tree.AsyncTreeNode({
	        text: '移动菜单目录',
	        draggable:false,
	        parentId:'null',
	        pathName:'null',
	        pathCode:'null',
	        id:'0'
	    });
	    moduelTree.setRootNode(confRoleRoot);
	    moduelTree.expandAll();
	    new Ext.tree.TreeSorter(moduelTree, {folderSort:true});
	    return moduelTree ;
	}
	 
	//列表初始化

	this.initMenuGrid = function(){
		var menuStore = new Ext.data.JsonStore({
			id: 'menuStore',
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'formId', 'formName', 'muneId','formName','teachName','intefaceTypeName','displayTypeName','displayType','intefaceType','intefaceUrl','intefaceName','keyName'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/SelJsonCreateAction'
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
		    {header:'菜单ID',dataIndex:'formId',hidden:true },		    
		    {header:'目录ID',dataIndex:'muneId',hidden:true },
		    {header:'显示方式ID',dataIndex:'displayType',hidden:true },
		    {header:'接口类型ID',dataIndex:'intefaceType',hidden:true },
		    
		    {header:'页面名称',dataIndex:'formName',width:100 },
		    {header:'页面代码',dataIndex:'teachName',width:100 },
		    {header:'接口类型',dataIndex:'intefaceTypeName',width:100 },
		    {header:'显示方式',dataIndex:'displayTypeName',width:100 },
		    {header:'接口地址',dataIndex:'intefaceUrl',width:200 },
		    {header:'接口方法',dataIndex:'intefaceName',width:100 },
		    {header:'关键字段',dataIndex:'keyName',width:100 }	    
		    
		]);   
		menuColumn.defaultSortable = true;
		
	   var pagingBar = new Ext.PagingToolbar({
			pageSize: 50,
			store: menuStore,
			displayInfo: true,
			displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg: "没有记录"
       });
    
		var menuGrid = new Ext.grid.GridPanel({
			id: 'menuGrid',
	        region: 'center',
	        width:Ext.getBody().getSize().width*0.8,
		    height:Ext.getBody().getSize().height*0.5,
		    title:'菜单页面列表',
	        store: menuStore,
	        trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
			cm:menuColumn,
			bbar: pagingBar,
			sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	oper.qryGirdData(rec.data.formId);
		            }
		        }
		    })/*,
			listeners: {
                "rowdblclick": function() {
                     var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections(); 
				    	window.returnValue = selItem;
	                    window.close();
                }
            }*/
		
	    });
		
		
		return menuGrid;
	}
	this.qryGirdData = function(formId){
			
			Ext.getCmp('indexGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('indexGrid').store.lastOptions != null){
							
							Ext.getCmp('indexGrid').store.baseParams = {formId:formId};
						}
					}
			)
			Ext.getCmp('indexGrid').store.removeAll() ;
			Ext.getCmp('indexGrid').store.load({params:{start:0, limit:50}});
		
	}
	this.initIndPanel = function(){
		var column = new Ext.grid.ColumnModel([
			{header:'属性ID',dataIndex:'filedId',hidden:true },
		    {header:'页面ID',dataIndex:'formId',hidden:true },
		    {header:'是否显示ID',dataIndex:'isDisplay',hidden:true },
		    {header:'是否传值ID',dataIndex:'isPassValue',hidden:true },
            {header:'字段类型ID',dataIndex:'filedType',hidden:true},
		    {header:'字段名称',dataIndex:'filedName',width:150},
		    {header:'中文名称',dataIndex:'filedLable',width:150},
		    {header:'字段类型',dataIndex:'filedType',width:100},
		    {header:'字段类型',dataIndex:'typeName',width:100},
		    {header:'是否显示',dataIndex:'isDisplayName',width:100},
		    {header:'是否传值',dataIndex:'isPassValueName',width:100},
		    {header:'是否必填',dataIndex:'isRequired',width:100},
		    
		    {header:'下拉数据',dataIndex:'checkedData',width:150},
		    {header:'选择数据',dataIndex:'selectData',width:150}
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'indexGrid',
	    	title : '页面字段配置',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelFiledInfoAction',
			listeners: {
                "rowdblclick": function() {
                     var selItem = Ext.getCmp('indexGrid').getSelectionModel().getSelections(); 
				    	window.returnValue = selItem;
	                    window.close();
                }
			}
		});
		return gridPanel;
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

			                	//var selProductId = Ext.getCmp("selProductId").getValue() ;
			                	Ext.getCmp('menuGrid').store.on('beforeload',
				                     function(store){ 
										store.baseParams = {start:0,limit:50,moduleId:moduleId};
									}
								)
		   
		    Ext.getCmp('menuGrid').store.removeAll() ;
			Ext.getCmp('menuGrid').store.load();
	    });
	 
	}
	
  this.initBtnPanel = function(){
	  var btnPanel = new Ext.FormPanel({
			id:'btnPanel',
	     	region: 'south',
		    border:false,
	        buttonAlign: 'center',
	        collapsible:false, 
	        height:Ext.getBody().getSize().height*0.08,
	        buttons: [{
		            text: '确定',
		            id:'submitBtn',
		            listeners:{"click":function(){
				    	var selItem = Ext.getCmp('indexGrid').getSelectionModel().getSelections(); 
				    	window.returnValue = selItem;
	                    window.close();
				    }}
		        },{
		            text: '取消',
		             id:'cancelBtn',
			         listeners:{"click":function(){
				    	window.close();
				    }}
		        }]
	    	});
	    	return btnPanel;
    	}
    	
   
   
}

Ext.onReady(function(){

    var moduelTree = oper.initModuelTree();
    var menuGrid = oper.initMenuGrid();
    var indPanel = oper.initIndPanel(); 
    var btnPanel = oper.initBtnPanel();
    oper.initTreeEvent();
    
    var tabs = new Ext.TabPanel({
			region: 'south',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.8,   
		    height:Ext.getBody().getSize().height*0.42, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[indPanel]
		});
	
    var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[menuGrid,tabs]
	}); 
    
    var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[moduelTree,earthviewport,btnPanel]
	});
});
</script>