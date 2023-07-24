/*
 * by fang.li   2010-10	
 * 菜单-菜单模块管理页面
 */
 
function MenuSelOper(){
	this.isExistMenuOther = function(inMenuName,objAttr){
		//其它菜单目录下的所有菜单，如果菜单名有与入参相同的则直接返回false
		
		if(inMenuName==null || inMenuName.length<1) return false;
		inMenuName = this.Trim(inMenuName);
		
		var size  = entityTL.items.length;
		var tempMenu = null;
	
		for(var i=0;i<objAttr.length;i++){
			tempMenu = objAttr[i];
			if(tempMenu!=null && inMenuName==tempMenu.NAME){
				return true;
			}
		}
		return false;
	}

	this.showModuleSelPage = function(){
		var moduleSelForm = new Ext.FormPanel({
			labelAlign: 'left',
        	region: 'center',
        	autoScroll: true,
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	                xtype:'treepanel',
	                id:'moduleSelTree',
	                name:'moduleSelTree',
	                autoScroll:true,
			        loader: new Ext.tree.TreeLoader({
			        	dataUrl:'/MOBILE/ExtServlet?actionName=system/ModuleTreeAction'
			        }),
			        containerScroll: true,
			        border: false,
			        rootVisible: false,
			        root: new Ext.tree.AsyncTreeNode({
			            id:'0'
			        })
	            }],
	        buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
	            	
	            	var selModuleTreeItem = moduleSelForm.items.items[0].getSelectionModel().selNode ;
	            	var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections()[0];
	            	
	            	if(selModuleTreeItem && selModuleTreeItem.id && (selModuleTreeItem.id != selItem.data.id)){
						fext.clearParam();
						fext.addParam(1, "MODULE_ID",selModuleTreeItem.id);
						objAttr = fext.callRemoteFunctionQuery("qryMenuList");
						if(menuSelOper.isExistMenuOther(selItem.NAME,objAttr)) {
				    		Ext.MessageBox.show({
					          	title: '提示',
						        msg: '该菜单目录名称已存在，请重新指定！',
						        buttons: Ext.MessageBox.OK,
						        width:200,
						        icon: Ext.MessageBox.ERROR
						    });
							return;
						}
						
						callRemoteFunction("com.ztesoft.iom.system.MenuManager","changePrivClass",selItem.data.privCode,selModuleTreeItem.attributes.privClassId);
						objAttr = callRemoteFunction("com.ztesoft.mobile.system.dao.MenuDAOImpl","updateMenuModule",selModuleTreeItem.id,selItem.data.id);
						
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
				            Ext.example.msg('','成功更改菜单目录！');
				        }, 1000);
				 		moduleSelWin.close();
				 		Ext.getCmp('menuGrid').store.removeAll() ;
						Ext.getCmp('menuGrid').store.load();
					}
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	moduleSelWin.close();
			    }}
	        }]
		});
		
		new Ext.tree.TreeSorter(moduleSelForm.items.items[0], {folderSort:true});
		
		moduleSelWin = new Ext.Window({
	        title: '菜单模块',
		    closable:true,
		    width:300,
		    height:500,
		    plain:true,
		    layout: 'border',
		    items: [moduleSelForm]
		});
		
		moduleSelWin.show(this);
	}
}