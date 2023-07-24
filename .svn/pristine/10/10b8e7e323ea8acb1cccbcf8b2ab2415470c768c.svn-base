<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>外系统管理</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="js/otherSysMng.js"></script>
		<script language="javascript" src="js/interfaceMng.js"></script>
		<script language="javascript" src="js/selectMng.js"></script>
        <script language="javascript" src="js/interfXmlImportMgr.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
			<div id='sysGird'></div>				
	</body>
</html>

<script language="JScript">
Ext.apply(Ext.form.VTypes, {
    sysRegionChk : function(val, field) {
        if(!val || '-----'==val){
        	return false;
        }
        return true;
    },sysRegionChkText:'请选择系统所属的域!'
});
Ext.apply(Ext.form.VTypes, {
    ssoTypeChk : function(val, field) {
        if(!val || '-----'==val){
        	return false;
        }
        return true;
    },ssoTypeChkText:'请选择单点登录类型!'
});


var otherSysOper = new OtherSysOper();
var interfaceOper = new InterfaceManagerOper();
var interfXmlImportOper = new InterfXmlImportOper();
var selOper = new SelOper();
var operator = 'add';

Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'sysId', 'sysCode', 'sysName', 'sysRegionId','sysRegionName','sysIpAddress','sysAppAddress',
        	'sysIcon', 'sysDesc', 'companyId','companyName', 'remark','ssoTypeName','ssoTypeId'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/QryOuterSystemAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'系统ID',dataIndex:'sysId',hidden:true},
	    {header:'系统编码',dataIndex:'sysCode',width:90},
	    {header:'系统名称',dataIndex:'sysName',width:100},
	    {header:'系统所属域ID',dataIndex:'sysRegionId',hidden:true},
	    {header:'系统所属域',dataIndex:'sysRegionName',width:100,hidden:true},
	    {header:'系统IP地址',dataIndex:'sysIpAddress',width:100,hidden:true},
	    {header:'系统应用路径',dataIndex:'sysAppAddress',width:120,hidden:true},
	    {header:'单点登录类型',dataIndex:'ssoTypeName',width:100,hidden:true},
	    {header:'单点登录类型Id',dataIndex:'ssoTypeId',width:100,hidden:true},
	    {header:'系统图标路径',dataIndex:'sysIcon',width:80,hidden:true},
	    {header:'厂家ID',dataIndex:'companyId',hidden:true},
	    {header:'厂家名称',dataIndex:'companyName',width:100,hidden:true},
	    {header:'系统描述',dataIndex:'sysDesc',width:200,hidden:true},
	    {header:'备注',dataIndex:'remark',width:200,hidden:true}
	]);   
	column.menuDisabled=true;
	
    var pagingBar = new Ext.PagingToolbar({
		pageSize: 7,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	var gridView = new Ext.grid.GridView({
		sortAscText:'升序',
		sortDescText:'降序',
		columnsText:'列名'
	});

 	  
 	var sysStore = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['sysRegionId', 'sysRegionName'],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/QryAllSysRegionAction'
        })
    });
    sysStore.load();
    
    var ssoTypeStore = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['ssoTypeId', 'ssoTypeName'],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/QryAllSSOTypeAction'
        })
    });
    ssoTypeStore.load();
     
    var compStore = new Ext.data.JsonStore({
    	remoteSort:true,
    	fields:['companyId','companyName'],
    	proxy: new Ext.data.HttpProxy({
    		url:'/MOBILE/ExtServlet?actionName=system/QryAllSysCompanyAction'
    	})
    });
    
    compStore.load();
 
    	 
	var qryForm = new Ext.FormPanel({
		id:'qryForm',
		region: 'west',
		frame:true,
		title:'查询条件',
		labelWidth: 60,
        collapsible:true,
        width:200,
        buttons:[{text: '查询',onClick:doQry},{text: '重置',onClick:reset}],
        defaultType: 'textfield',
        items:[{
        		fieldLabel:'系统编码',
                name:'sysCode',
                id:'sysCode',
                width:120
        	  },{
        		fieldLabel:'系统名称',
                name:'sysName',
                id:'sysName',
                width:120
              },{
              	xtype:'combo',
              	fieldLabel: '系统所属域',
              	name: 'sysRegionId',
                id: 'sysRegionId',
                valueField: 'sysRegionId',
                displayField: 'sysRegionName',
                width:120,
                mode:'remote',
                triggerAction: 'all',
                forceSelection: true,
				editable :false,
                store:sysStore
              }]
	});
	
	function doQry(){
		var sysCode = Ext.getCmp('sysCode').getValue();
		var sysName = Ext.getCmp('sysName').getValue();
		var sysRegionId = Ext.getCmp('sysRegionId').getValue();
		store.baseParams = {sysCode:sysCode,sysName:sysName,sysRegionId:sysRegionId};
		Ext.getCmp('outerSystemGrid').store.load({params:{start:0, limit:7}});
	}

	function reset(){
		Ext.getCmp('sysCode').setValue('');
		Ext.getCmp('sysName').setValue('');
		Ext.getCmp('sysRegionId').setValue('');
	}
	
	var grid = new Ext.grid.GridPanel({
		id:'outerSystemGrid',
        region: 'west',
        collapsible:true, 
        width:Ext.getBody().getSize().width*0.2,   
	    height:Ext.getBody().getSize().height,
	    title:'系统列表',
        store: store,
        //trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
        viewConfig : gridView,
		cm:column,
		tbar:initToolBars(),
		bbar:pagingBar,
		sm:new Ext.grid.RowSelectionModel({
			singleSelect:true,
			listeners:{
				rowselect:function(sm,row,rec) {
                	//var sysId = rec.data.sysId;
                	//Ext.Msg.alert('Message', sysId);
                    //Ext.getCmp('detailForm').getForm().loadRecord(rec);
                    qryOtherSysData(rec.data.sysCode);      
                    Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(0);
                }
			}
		})
    });
    store.load({params:{start:0, limit:7}});
    
    function qryOtherSysData(sysCode){
			
			Ext.getCmp('otherSysGrid').store.on('beforeload',
					function(store){
						if(Ext.getCmp('otherSysGrid').store.lastOptions != null){
							
							Ext.getCmp('otherSysGrid').store.baseParams = {sysCode:sysCode};
						}
					}
			)
			Ext.getCmp('otherSysGrid').store.on('load',
					function(store){      
                    	Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(0);
					}
			)
			Ext.getCmp('otherSysGrid').store.removeAll() ;
			Ext.getCmp('otherSysGrid').store.load({params:{start:0, limit:10}});
	}
    
    //添加右键事件
	grid.on("rowcontextmenu",rowOuterSystemMenu);
	grid.on("contextmenu",outerSystemMenu);
	
	//定义菜单
	var treeMenu = new Ext.menu.Menu({
        id : 'treeMenu'
	});
	
	//在Gird表单外弹出的菜单
	function outerSystemMenu(e){
		treeMenu.removeAll();
		treeMenu.addItem(new Ext.menu.Item({text:'增加系统',handler:function(){
			treeMenu.hide();
			operOuterSystem("add");		//新增操作
		}}));
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
	}
	
	//在Gird表单内弹出的菜单
	function rowOuterSystemMenu(node,rowIndex,e){
		node.getSelectionModel().selectRow(rowIndex);			//选中所在的选择项上
		treeMenu.removeAll();
		treeMenu.addItem(new Ext.menu.Item({text:'增加系统',handler:function(item,e){
			treeMenu.hide();
			operOuterSystem("add");		//新增操作
		}}));
		treeMenu.addItem(new Ext.menu.Item({text:'修改系统',handler:function(){
			treeMenu.hide();
			var record = node.getSelectionModel().getSelected(); //获取当前选中的整条记录，前提是必须设置为行选择模式
			operOuterSystem("update");		//更新操作
		
			Ext.getCmp('operOuterForm').getForm().loadRecord(record);
			 
		}}));
		treeMenu.addItem(new Ext.menu.Item({text:'删除系统',handler:function(){
			treeMenu.hide();
			var sysId = node.getSelectionModel().getSelected().data.sysId; 		//获取用户选择的系统ID
			Ext.Msg.confirm("删除提示","确定要删除所选的系统吗?",function(btn){
				if(btn=="yes"){
					delOuterSystem(sysId);
				}
			});
		}}));
		
		treeMenu.showAt([e.getXY()[0], e.getXY()[1]]);
		
	}
	
	//增加或修改外系统
	function operOuterSystem(oper){
		var mess = "add"==oper?"新增":"更新";
	    var outerForm = new Ext.FormPanel({
	    	id:"operOuterForm",
	    	labelAlign: 'left',
	 		frame:true,
	 		height:Ext.getBody().getSize().height*0.57,
        	items:[{
        		layout:'column',
        		items:[{
        			layout:'form',
        			columnWidth:.5,
        			defaultType: 'textfield',
        			items:[{
	        			fieldLabel:'系统名称',
			       		name:'sysName',
			       		allowBlank:false, 
			            blankText:"系统名称不能为空!"
        			},{
 		        		fieldLabel:'系统IP地址',
		       			name:'sysIpAddress',
		       			allowBlank:false, 
		            	blankText:"系统IP地址不能为空!"       			
        			},{
        				xtype:'combo',
		       			fieldLabel:"厂家名称",
		       			name:"companyId",
		       			id:'companyId_1',
		       			valueField: 'companyId',
                		displayField: 'companyName',
                		mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
		       			allowBlank:false, 
		            	blankText:"厂家名称不能为空!",
		            	editable :false,
		                store:compStore  		
        			}]
        		},{
        			layout:'form',
        			columnWidth:.5,
        			defaultType: 'textfield',
        			items:[{
		        		fieldLabel:'系统编码',
		       			name:'sysCode',
		       			id: 'sysCode_1',
		       			allowBlank:false, 
		            	blankText:"系统编码不能为空!"        			
        			},{
						xtype:'combo',
		              	fieldLabel: '系统所属域',
		              	name: 'sysRegionId',
		              	id: 'sysRegionId_1',
		                valueField: 'sysRegionId',
		                displayField: 'sysRegionName',
		                mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
						editable :false,		                
		       			allowBlank:false, 
		            	blankText:"系统所属域不能为空!",		                
						vtype:'sysRegionChk',
		                store:sysStore       			
        			},{
						xtype:'combo',
		              	fieldLabel: '单点登录类型',
		              	name: 'ssoTypeId',
		              	id: 'ssoTypeId_1',
		                valueField: 'ssoTypeId',
		                displayField: 'ssoTypeName',
		                mode:'remote',
		                triggerAction: 'all',
		                forceSelection: true,
						editable :false,		                
		       			allowBlank:false, 
		            	blankText:"单点登录类型不能为空!",	                
						vtype:'ssoTypeChk',
		                store:ssoTypeStore       			
        			}]
        		}]
        	},{
      			xtype:'textfield',
        		fieldLabel:'系统应用路径',
       			name:'sysAppAddress',
       			allowBlank:false, 
            	blankText:"系统应用路径不能为空!",
            	vtype: 'url' ,
                vtypeText : '请输入正确的URL路径!',
                maxLength:200,
                maxLengthText:'系统应用路径的最大长度不能超过200',
       			width:400   			
	       	},{
	       		xtype:'textfield',
       			fieldLabel:"系统图标路径",
       			name:"sysIcon",
       			maxLength:90,
       			maxLengthText:'系统图标路径的最大长度不能超过90',
       			width:400   			
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"系统描述",
       			name:"sysDesc",
       			maxLength:200,
       			maxLengthText:'系统描述的最大长度不能超过200'
	       	},{
       			xtype:"textarea",
       			width:400,
       			fieldLabel:"备注",
       			name:"remark",
       			maxLength:200,
       			maxLengthText:'备注的最大长度不能超过200'
	       	},{
       			xtype:"hidden",
       			name:"sysId"
       			
	       	}],
	       	buttons:[{
	            text: '确定',
	            listeners:{"click":function(){
	            	if(outerForm.getForm().isValid()){
		            	var outerSys = outerForm.getForm().getValues();
		            	var sysRegionId_1 = Ext.getCmp("sysRegionId_1").getValue();
		            	var companyId_1 = Ext.getCmp("companyId_1").getValue();
		            	var ssoTypeId_1 = Ext.getCmp("ssoTypeId_1").getValue();
		            	outerSys.ssoTypeId = ssoTypeId_1;
		            	outerSys.sysRegionId = sysRegionId_1;
		            	outerSys.companyId = companyId_1;
		        
		            	var result = invokeAction("/outerSystem/InsertOuterSysemAction",outerSys);
		           		if("success"==result){
		           			Ext.example.msg(mess +"提示",mess+'成功');
		           			
		           			doQry();		//重新查询,更新列表
		           			win.close();
		           		}else{
		           			Ext.Msg.alert(mess +"提示",mess+'失败');
		           		}		            	
	            	}
			    }}
		    },{
	            text: '取消',
	            listeners:{"click":function(){
			    	win.close();
			    }}
		    }]
        	
	    });
	
		if(oper == "update"){
			Ext.getCmp('sysCode_1').setReadOnly(true);
		}	
		
		var win = new Ext.Window({
			id:"outerWin",   
            title: mess + "系统",
        	width:Ext.getBody().getSize().width*0.60, 
            plain: true,                       
            layout: 'anchor',   
            items: [outerForm]               
        });
          
        win.show();   
	}

	
    
    /*var detailForm = new Ext.FormPanel({
    	id:'detailForm',
		region: 'south',
		labelAlign: 'left',
	 	frame:true,
        title:'系统详情',
        collapsible:true, 
        //store:formStore,
        height:Ext.getBody().getSize().height*0.4,
        width:Ext.getBody().getSize().width*0.85,
        layout:'table',
        layoutConfig:{columns:3},//将父容器分成3列

		autoScroll: true,
	    items:[{
			xtype:"panel",
			layout:"form",
			defaultType: 'textfield',
       		items:[{
				fieldLabel:'系统名称',
	       		name:'sysName'
	       		//disabled:true
			}]
       	},{
       		xtype:"panel",
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
        		fieldLabel:'系统编码',
       			name:'sysCode'
       			//disabled:true      		
       		}]
       	},{
       		xtype:"panel",
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
        		fieldLabel:'系统所属域',
       			name:'sysRegionName'
       			//disabled:true      		
       		}]
       	},{
       	    xtype:"panel",
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
        		fieldLabel:'系统IP地址',
       			name:'sysIpAddress'
       			//disabled:true          		
       		}]
       	},{
       		xtype:"panel",
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
       			fieldLabel:"厂家名称",
       			name:"companyName"
       		}]
       	},{
       		xtype:"panel",
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
       			fieldLabel:"单点登录类型",
       			name:"ssoTypeName"
       		}]
       	},{
       	    xtype:"panel",
       	    colspan:3,
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
        		fieldLabel:'系统应用路径',
       			name:'sysAppAddress',
       			width:400
       			//disabled:true         		
       		}]           	
       	},{
       		xtype:"panel",
       		colspan:3,
       		layout:"form",
       		defaultType: 'textfield',
       		items:[{
       			fieldLabel:"系统图标路径",
       			name:"sysIcon",
       			width:400
       			//disabled:true
       		}]
       	},{
       		xtype:"panel",
       		colspan:3,
       		layout:"form",
       		items:[{
       			xtype:"textarea",
       			width:630,
       			fieldLabel:"系统描述",
       			name:"sysDesc"
       			//disabled:true
       		}]
       	},{
       		xtype:"panel",
       		colspan:3,
       		layout:"form",
       		items:[{
       			xtype:"textarea",
       			width:630,
       			fieldLabel:"备注",
       			name:"remark"
       			//disabled:true
       		}]
       	}]
	});   */
	    
	
	function initToolBars(){
		
		var tb = new Ext.Toolbar();
		tb.add({
            text:'增加系统',
             // <-- icon
             onClick:add// assign menu by instance
        });
		tb.add({
            text:'修改系统',
              // <-- icon
            onClick:update// assign menu by instance
        });
        tb.add({
            text:'删除系统',
             // <-- icon
            onClick:del
        });
      
        
        return tb;
	}
	function add(){
		operOuterSystem('add');
	}
	
	function update(){
		var record = Ext.getCmp('outerSystemGrid').getSelectionModel().getSelected(); //获取当前选中的整条记录，前提是必须设置为行选择模式
		if(record){
			operOuterSystem("update");		//更新操作
			Ext.getCmp('operOuterForm').getForm().loadRecord(record);		
		}else{
			Ext.Msg.alert("操作提示","请选择需要修改的系统!");
		}

		
	}
	function del(){
		var record = Ext.getCmp('outerSystemGrid').getSelectionModel().getSelected();
		if(record){
			Ext.Msg.confirm("删除提示","确定要删除所选的系统吗?",function(btn){
				if(btn=="yes"){
					delOuterSystem(record.data.sysId);
				}
			});		
		}else{
			Ext.Msg.alert("操作提示","请选择需要删除的系统!");
		}
	}
	
	function delOuterSystem(sysId){
		var outerSys = new Object();
	    outerSys.sysId = sysId;
	    var result = invokeAction("/outerSystem/DelOuterSystemAction",outerSys);
   		if("success"==result){
   			Ext.example.msg("提示",'删除系统成功');
   			doQry();	
   		}else{
   			Ext.example.msg("提示",'删除系统失败');
   		}	
	}
	
	
	
 /**
  * 系统接口地址表

  */
  
function renderInterfaceVersion(v){
	switch(v){
		case null:
			return '无';
			break;
		case '1':
			return '方式1';
			break;
		case '2': 
			return '方式2';
			break;
		case '3': 
			return '方式3';
			break;
		default: 
			return 'error';
	}
}
function renderMethodType(v){
	switch(v){
		case '1':
			return '类名';
			break;
		case '2': 
			return '接口';
			break;
		case '3': 
			return 'servlet';
			break;
		case '4': 
			return '测试数据';
			break;	
		default: 
			return 'error';
	}
}

//列表初始化


	function initOtherSysGrid(){
		//创建列

		//'sysotherSysId', 'systemCode', 'methodType','methodotherSys','interfaceVersion','apkCode'
		var column = new Ext.grid.ColumnModel([
		    {header:'接口地址ID',dataIndex:'sysAddressId',hidden:true },		    
		    {header:'系统编码',dataIndex:'systemCode',hidden:true },
		    {header:'接口编码',dataIndex:'interfaceCode',width:100 },
		    {header:'方法类型',dataIndex:'methodType',width:100,renderer:function(v){return renderMethodType(v);}},
		    {header:'方法地址',dataIndex:'methodAddress',width:200 },
		    {header:'调用方式',dataIndex:'interfaceVersion',width:100,renderer:function(v){return renderInterfaceVersion(v);}},
		    {header:'空间名称',dataIndex:'interfaceNamespace',width:100 },
		    {header:'数据格式',dataIndex:'dataType',width:80,renderer:function(v){return v=='1'?'Json格式':'Xml格式';} },
		    {header:'接口方法',dataIndex:'interfaceMethod',width:100 },
		    {header:'接口名称',dataIndex:'apkCode',width:100 }
		]);   
		
		var gridPanel = new ZTESOFT.Grid({
	    	id:'otherSysGrid',
	    	title : '系统接口地址表',
	    	region: 'north',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:10,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelOtherSysAction',
	        sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	qryInterfaceGridData(rec.data.sysAddressId);
		            }
		        }
		    })
			
		});
		return gridPanel;
    }
    
    function qryInterfaceGridData(sysAddressId){
			
			Ext.getCmp('interfaceGrid').store.on('beforeload',
					function(store){ 
						if(Ext.getCmp('interfaceGrid').store.lastOptions != null){
							
							Ext.getCmp('interfaceGrid').store.baseParams = {sysAddressId:sysAddressId};
						}
					}
			);
			Ext.getCmp('interfaceGrid').store.on('load',
					function(store){      
                    	Ext.getCmp('interfaceGrid').getSelectionModel().selectRow(0);
					}
			);
			Ext.getCmp('interfaceGrid').store.removeAll() ;
			Ext.getCmp('interfaceGrid').store.load({params:{start:0, limit:10}});
		
	}
	
	    //定义菜单列表菜单
    function initGridMenu(){
    	var rightClick = new Ext.menu.Menu({
		    id:'rightClick'
		});
		var nullRightClick = new Ext.menu.Menu({
		    id:'nullRightClick'
		});
    }
    
	//定义菜单列表事件
	function initGridEvent(){
		initGridMenu();
		Ext.getCmp('otherSysGrid').addListener('rowcontextmenu', rightClickFn);
		Ext.getCmp('otherSysGrid').addListener('contextmenu', nullRightClickFn);
	}
	
	//菜单组装
	function rightClickFn(otherSysGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '增加接口' ,handler: function() {
			rightClick.hide();
			otherSysAdd();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改接口' ,handler: function() {
			rightClick.hide();
			otherSysMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除接口' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', otherSysOper.moduleDel);
		}}));
		
		Ext.getCmp('otherSysGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	
	function nullRightClickFn(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '增加接口' ,handler: function() {
			nullRightClick.hide();
			otherSysAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

	//菜单方法
    function otherSysAdd(){
    	otherSysOper.showMenuInfoPage('add');
    }
    
    function otherSysMod(){
    	otherSysOper.showMenuInfoPage('mod');
    }
    
//    function otherSysDel(btn){
//    	if(btn == 'no'){
//    		return ;
//    	}
//    	var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
//		
//		var paramObj = new Object();
//  			paramObj.formId = selItem[0].data.sysAddressId;
//			paramObj.type = "del";
//			var retMap = invokeAction("/mobsystem/InsertMobileOtherSysManagerAction", paramObj);
//		
//		Ext.MessageBox.show({
//           	msg: '系统正在提交数据……',
//           	progressText: 'Saving...',
//           	width:300,
//           	wait:true,
//           	waitConfig: {interval:100},
//           	icon:'ext-mb-download'
//       	});
//        setTimeout(function(){
//            Ext.MessageBox.hide();
//            Ext.example.msg('','成功删除菜单！');
//        }, 1000);
//        
//        Ext.getCmp('otherSysGrid').store.removeAll();
//        Ext.getCmp('otherSysGrid').store.reload();
//    }
    
    //接口映射配置
    ///////////////////////////////////////
    function initInterfacePanel(){
		var column = new Ext.grid.ColumnModel([
			{header:'接口映射ID',dataIndex:'mappingId',hidden:true },
		    {header:'系统接口ID',dataIndex:'sysAddressId',hidden:true },
		    {header:'服务编码',dataIndex:'mappingCode',width:120}, 
		    {header:'服务类型',dataIndex:'restServiceType',width:100,renderer:function(v){return v=='2'?'二次开发':'透传';} },
		    {header:'服务方法类型',dataIndex:'mappingName',width:100 },  
		    {header:'服务参数名',dataIndex:'mappingMethod',width:100},
		    {header:'业务接口名称',dataIndex:'intefaceName',width:100},
		    {header:'业务接口方法',dataIndex:'intefaceMethod',width:150},
		    {header:'业务接口参数',dataIndex:'intefaceParams',width:150},
		    {header:'描述',dataIndex:'comments',width:150}
		   
		]); 
		var gridPanel = new ZTESOFT.Grid({
	    	id:'interfaceGrid',
	    	title : '服务映射管理',
	    	region: 'center',
	    	width:10,
	    	//tbar:this.initGridToolsBar(),
	        pageSize:50,
	        cm:column,
	        paging:true,
	        url:'/MOBILE/ExtServlet?actionName=system/SelMobInterfaceAction',
	        sm: new Ext.grid.RowSelectionModel({
		        singleSelect: true,
		        listeners: {
		            rowselect: function(sm, row, rec){
		            	qryXmljsonData(rec.data.mappingCode);
		            	
		            }
		        }
		    })
			
		});
		return gridPanel;
    }
    function qryXmljsonData(mappingCode){
		var inputParams = new Object();
		inputParams.type = 'sel';
		inputParams.mappingCode = mappingCode==null?0:mappingCode;
		var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
		if(retMap.totalSize == '0' || retMap.totalSize == 0){
			operator = 'add';
			detailForm.getForm().reset();
		}
		else {
			operator = 'mod';
			detailForm.getForm().setValues(retMap.resultList[0]);
		}
	}
    //定义接口映射配置列表事件
	function initInterfaceGridEvent(){
		Ext.getCmp('interfaceGrid').addListener('rowcontextmenu', rightClickFn2);
		Ext.getCmp('interfaceGrid').addListener('contextmenu', nullRightClickFn2);
	}
	
	//接口映射配置组装
	function rightClickFn2(menuGrid,rowIndex,e){
		var i = 0 ;
		var rightClick = Ext.getCmp('rightClick');
		
		rightClick.removeAll();
		rightClick.insert(i++,new Ext.menu.Item({ text: '接口映射配置' ,handler: function() {
			rightClick.hide();
			interfaceAdd();
		}}));
		 rightClick.insert(i++,new Ext.menu.Item({ text: '修改接口映射' ,handler: function() {
			rightClick.hide();
			interfaceMod();
		}}));
		
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除接口映射' ,handler: function() {
			rightClick.hide();
			Ext.MessageBox.confirm('提示', '你确定要删除吗？', interfaceOper.moduleDel);
		}}));


        rightClick.insert(i++,new Ext.menu.Item({ text: '上传请求报文XML格式' ,handler: function() {
            rightClick.hide();
            interfaceXmlImport(0);
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '下载请求报文XML格式' ,handler: function() {
            rightClick.hide();
            interfaceXmlImportView(0);
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '上传响应报文XML格式' ,handler: function() {
            rightClick.hide();
            interfaceXmlImport(1);
        }}));

        rightClick.insert(i++,new Ext.menu.Item({ text: '下载响应报文XML格式' ,handler: function() {
            rightClick.hide();
            interfaceXmlImportView(1);
        }}));

        Ext.getCmp('interfaceGrid').getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
	function nullRightClickFn2(e){
		var nullRightClick = Ext.getCmp('nullRightClick');
    	nullRightClick.removeAll();
		nullRightClick.add(new Ext.menu.Item({ text: '接口映射配置' ,handler: function() {
			nullRightClick.hide();
			interfaceAdd();
		}}));
    	nullRightClick.showAt(e.getXY());
    }

      //接口映射配置操作方法
    function interfaceAdd(){
            interfaceOper.showMenuInfoPage('add');
    }
    function interfaceMod(){
            interfaceOper.showMenuInfoPage('mod');
    }
    
    
    /////////////////////////////////////////////////////////////
    //add by guo.jinjun at 2012-05-18 23:31:26
    var staticStore = new Ext.data.ArrayStore({fields:['id','value'],
     data: [["listdata",'listdata'],["listdata1",'listdata1'],["listdata2",'listdata2'],["listdata3",'listdata3'],
	["result",'result'],["body",'body'],["bodyresult",'bodyresult'],["totalpage",'totalpage'],["pagesize",'pagesize'],
	["errordesc",'errordesc'],["pagenum",'pagenum'],["totalnum",'totalnum']]});
    
    var detailForm = new Ext.FormPanel({
    	id:'xml2jsonForm',
		region: 'south',
		labelAlign: 'left',
	 	frame:true,
        title:'xml转json管理',
        collapsible:true, 
        height:Ext.getBody().getSize().height*0.4,
        width:Ext.getBody().getSize().width*0.85,
        layout:'table',
        layoutConfig:{columns:4},//将父容器分成4列

		autoScroll: true,
		defaults : {
       		//labelAlign: 'top'
       		labelWidth : 90
       		//width : 200
       	},
	    items:[{
			xtype:'panel',
			colspan:2,
			//width:Ext.getBody().getSize().width*0.75,
			layout:'form',
			defaultType: 'textfield',
       		items:[{
				xtype: 'hidden',
               	name: 'filedMappingId',
                id: 'm_filedMappingId'
			}]
       	},{
			xtype:'panel',
			colspan:2,
			//width:Ext.getBody().getSize().width*0.75,
			layout:'form',
			defaultType: 'textfield',
       		items:[{
				xtype: 'hidden',
               	name: 'mappingCode',
                id: 'n_mapping_code'
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段1',
               	id : 'm_system_fileds',
               	name : 'systemFileds',               	
				allowBlank: false,
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段1',
                id : 'm_mapping_fileds',
                name : 'mappingFileds',
                valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段2',
               	id : 'm_system_fileds2',
               	name : 'systemFileds2',
               	width : 270
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		colspan:2,
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段2',
                id : 'm_mapping_fileds2',
                name : 'mappingFileds2',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段3',
               	id : 'm_system_fileds3',
               	name : 'systemFileds3',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段3',
                id : 'm_mapping_fileds3',
                name : 'mappingFileds3',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段4',
               	id : 'm_system_fileds4',
               	name : 'systemFileds4',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段4',
                id : 'm_mapping_fileds4',
                name : 'mappingFileds4',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段5',
               	id : 'm_system_fileds5',
               	name : 'systemFileds5',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段5',
                id : 'm_mapping_fileds5',
                name : 'mappingFileds5',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段6',
               	id : 'm_system_fileds6',
               	name : 'systemFileds6',
       			width : 100
			}]
       	},{
       		xtype:'panel',
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段6',
                id : 'm_mapping_fileds6',
                name : 'mappingFileds6',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 100
       		}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段7',
               	id : 'm_system_fileds7',
               	name : 'systemFileds7',
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段7',
                id : 'm_mapping_fileds7',
                name : 'mappingFileds7',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'textfield',
       		items:[{
               	fieldLabel : '业务系统字段8',
               	id : 'm_system_fileds8',
               	name : 'systemFileds8',
       			width : 270
			}]
       	},{
       		xtype:'panel',
       		colspan:2,
       		layout:'form',
       		defaultType: 'combo',
       		items:[{
        		fieldLabel : '映射成字段8',
                id : 'm_mapping_fileds8',
                name : 'mappingFileds8',
               	valueField: 'id',
                displayField: 'value',
                mode: 'local',
                triggerAction: 'all',
                value: '',
                store: staticStore,
				allowBlank: true,
       			width : 270
       		}]
       	}],
       	buttons: [{
	            text: '更新',
	            listeners:{'click':function(){
			    	
       				var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
					var selItem2 = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
					var formValue = Ext.getCmp('xml2jsonForm').getForm().getValues();
			    	var resultStr;
			    	
			    	switch (operator){
			    		case 'add':{
			    			resultStr = '新增成功！';
							
			    			//var inputParams = new Object();
			    			var inputParams = formValue;
			    			inputParams.mappingCode = selItem2[0].data.mappingCode;
                            inputParams.methodAddress = selItem[0].data.methodAddress;
							inputParams.type = 'add';
							var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
			    			
							qryOtherSysData(Ext.getCmp('v_sysCode').getValue());
			    			break ;
			    		}
			    		case 'mod':{
			    			resultStr = '修改成功！';
		
			    			var inputParams = formValue;
			    			/*var inputParams = new Object();
			    			inputParams.filedMappingId = formValue.filedMappingId;
			    			inputParams.systemFileds = Ext.getCmp('m_system_fileds').getValue();
			    			inputParams.systemFileds2 = Ext.getCmp('m_system_fileds2').getValue();
			    			inputParams.systemFileds3 = Ext.getCmp('m_system_fileds3').getValue();
			    			inputParams.systemFileds4 = Ext.getCmp('m_system_fileds4').getValue();
			    			inputParams.systemFileds5 = Ext.getCmp('m_system_fileds5').getValue();			    			
			    			inputParams.systemFileds6 = Ext.getCmp('m_system_fileds6').getValue();
			    			inputParams.systemFileds7 = Ext.getCmp('m_system_fileds7').getValue();
			    			inputParams.systemFileds8 = Ext.getCmp('m_system_fileds8').getValue();
                            inputParams.mappingFileds = Ext.getCmp('m_mapping_fileds').getValue();
                            inputParams.mappingFileds2 = Ext.getCmp('m_mapping_fileds2').getValue();
                            inputParams.mappingFileds3 = Ext.getCmp('m_mapping_fileds3').getValue();
                            inputParams.mappingFileds4 = Ext.getCmp('m_mapping_fileds4').getValue();
                            inputParams.mappingFileds5 = Ext.getCmp('m_mapping_fileds5').getValue();
                            inputParams.mappingFileds6 = Ext.getCmp('m_mapping_fileds6').getValue();
                            inputParams.mappingFileds7 = Ext.getCmp('m_mapping_fileds7').getValue();
                            inputParams.mappingFileds8 = Ext.getCmp('m_mapping_fileds8').getValue();*/
                            inputParams.transferResult = '';
                            //inputParams.methodAddress = selItem[0].data.methodAddress;
							inputParams.type = 'mod';
							var retMap = invokeAction('/mobsystem/InsertMobSysFiledMappingAction', inputParams);
			    			
			    			break ;
			    		}
			    	}
			    	
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
			            Ext.example.msg('',resultStr);
			        }, 1000);
			        //Ext.getCmp('xmljsonGrid').store.removeAll();
			        //Ext.getCmp('xmljsonGrid').store.reload();
			    }}
	        },{
	            text: '调试', 
	            listeners:{'click':function(){
	        		debugXml();
			    }}
	        }]
	});  
    
    function debugXml(){
    	var selItem = Ext.getCmp('otherSysGrid').getSelectionModel().getSelections();
		var selItem2 = Ext.getCmp('interfaceGrid').getSelectionModel().getSelections();
    	var formValue1 = Ext.getCmp('xml2jsonForm').getForm().getValues();
    	var isSuccess = 'failure';
    	var infoPage1 = new Ext.FormPanel({
			region: 'center',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        items: [{
	        	layout:'column',       	
		        labelWidth : 80,
		        labelAlign: 'top',
	        	items: [{
	        		columnWidth:.5,
	        		layout:'form',  
	        		items: [{
	        			xtype : 'textarea',
		               	fieldLabel : '转换之前',
		               	id : 'v_transfer_before',
		               	name : 'v_transfer_before',
		               	value : '获取xml失败,接口不通',
						width: 350,
					    height : 200
	        		}]		        	
			    },{columnWidth:.02},{
			    	columnWidth:.48,layout:'form',  
	        		items: [{
			        	xtype : 'textarea',
		               	fieldLabel : '转换后结果',
		               	id : 'v_transfer_result',
		               	name : 'v_transfer_result',
		               	value : '转换失败',
						width: 350,
					    height : 200
					}]
			    }]
	        }],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
						if(isSuccess == 'success'){
							var inputParams = new Object();
			    			inputParams.filedMappingId = formValue1.filedMappingId;
			    			inputParams.transferBefore = Ext.getCmp('v_transfer_before').getValue();
							inputParams.transferResult = Ext.getCmp('v_transfer_result').getValue();
							
	                        inputParams.type = "updateTrsfRs";
							var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
							
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
					            Ext.example.msg('','调试结果存入数据库成功');
					        }, 1000);
					        menuWin.close();
						}	            		
			    	}
			    }
			},{
	            text: '取消',
	            listeners:{"click":function(){
	            		menuWin.close();
			    	}
			    }
			}]
		});
    	var menuWin = new Ext.Window({
		        title: 'xml转换调试',
			    closable:true,
			    width:800,
			    height:300,
			    plain:true, 
			    layout: 'border',
			    items: [infoPage1]
			});
		menuWin.show(this);
		
		var inputParams = formValue1;
		inputParams.methodAddress = selItem[0].data.methodAddress;	//接口  方法地址	
		inputParams.interfaceNamespace = selItem[0].data.interfaceNamespace;	//空间名称
		inputParams.interfParamsName = selItem2[0].data.mappingMethod;		//接口参数名

		inputParams.interfMethodType = selItem2[0].data.mappingName;		//接口方法类型
		inputParams.mappingCode = selItem2[0].data.mappingCode;		//映射编码
		inputParams.intefaceParams = selItem2[0].data.intefaceParams;		//接口参数
		inputParams.intefaceMethodParam = selItem2[0].data.intefaceMethod;		//接口方法参数
		inputParams.intefaceType=selItem[0].data.methodType;  //方法类型
		inputParams.dataType=selItem[0].data.dataType;  //数据格式
		inputParams.interfaceMethod=selItem[0].data.interfaceMethod;  //方法名称 
		inputParams.type = "debug";
		var retMap = invokeAction("/mobsystem/InsertMobSysFiledMappingAction", inputParams);
		Ext.getCmp('v_transfer_before').setValue(retMap.transferBefore);
		Ext.getCmp('v_transfer_result').setValue(retMap.transferResult);
		isSuccess = retMap.result;
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////

    function interfaceXmlImport(type) {
        if(0 ==  type) {	//导入请求报文xml格式
            interfXmlImportOper.showMenuInfoPage('imp');
        } else if(1 == type) { //导入响应报文xml格式
            interfXmlImportOper.showMenuInfoPage('emp');
        }
    }

    function  interfaceXmlImportView(type) {
        if(0 ==  type) {	//导入请求报文xml格式
            interfXmlImportOper.showMenuInfoPage('imp_v');
        } else if(1 == type) { //导入响应报文xml格式
            interfXmlImportOper.showMenuInfoPage('emp_v');
        }
    }
	
	//系统接口地址表

	var otherSysGrid = initOtherSysGrid();
	initGridEvent();
	var interfaceGrid = initInterfacePanel();
	initInterfaceGridEvent();
	
	/*var tabs = new Ext.TabPanel({
			region: 'center',
			id : 'tabs',
		    activeTab: 0,
		    width:Ext.getBody().getSize().width*0.85,   
		    height:Ext.getBody().getSize().height*0.4, 
		    plain:true,
		    defaults:{autoScroll: true},
		    items:[interfaceGrid,detailForm]
		});*/
	
	var viewport = new Ext.Viewport({
		el:'sysGird',
		layout: 'border',
		items:[grid,{
			layout:'border',
			region:'center',
			items:[otherSysGrid,interfaceGrid,detailForm]
		}]
		//items:[qryForm,grid,detailForm]
	});
	
});
	
	


</script>