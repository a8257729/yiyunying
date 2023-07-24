<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>参数设置</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<%@ include file="../public/remoteFunctionJs.jsp"%>

		
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
	<script language="JScript">
var swidth = GetScreenWidth();
var sheight = GetScreenHeight();
var session1 = GetSession();
var staffId = session1.staff.staffId ;
var oper = new Oper();
var store;
var rightClick = new Ext.menu.Menu({
	    id:'rightClick'
	});	
	
function rightClickFn(grid,rowIndex,e){

		rightClick.removeAll();
		var i = 0 ;
		rightClick.insert(i++,new Ext.menu.Item({ text: '新增' ,handler: function() {
				rightClick.hide();
				oper.showMenuInfoPage('add');
			}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '修改' ,handler: function() {
				rightClick.hide();
				oper.showMenuInfoPage('mod');
			}}));
		rightClick.insert(i++,new Ext.menu.Item({ text: '删除' ,handler: function() {
				rightClick.hide();
				oper.paramDel();
			}}));
		grid.getSelectionModel().selectRow(rowIndex);
	    e.preventDefault();
	    rightClick.showAt(e.getXY());
	}
Ext.onReady(function(){

    var paramGrid = oper.initParamGrid();
    paramGrid.addListener('rowcontextmenu', rightClickFn);

	var formPanel = new Ext.FormPanel({
		id:'westForm',
		region: 'west',
		frame:true,
	    title:'查询条件',
		//labelWidth: 80,
        collapsible:true,
        width:350,
        height:300,
        buttons: [{ text: "查询", handler: function() {
            gcode = Ext.getCmp("gcode").getValue() ;
			mname = Ext.getCmp("mname").getValue() ;
			target  = Ext.getCmp("target").getValue() ;

			store.on('beforeload',function(store){
					if(store.lastOptions != null){
						store.baseParams = {gcode:gcode,mname:mname,target:target};
					     }
					})
					store.removeAll() ;
				    store.load({params:{start:0, limit:200,actionType:'qryMobileParamByCond'}});        
                  
          } }],
        items:[ {
                fieldLabel: "参数分类编码",
                xtype: "textfield",
                name: "gcode",
                id: "gcode",
                width: 200},
                {
                fieldLabel: "参数名称",
                xtype: "textfield",
                name: "mname",
                id: "mname",
                width: 200},
                {
                fieldLabel: "参数适用对象",
                xtype: "textfield",
                name: "target",
                id: "target",
                width: 200}
                
              ]
	});
	
	var viewport = new Ext.Viewport({
		el:'menuMngDiv',
		layout: 'border',
		items:[formPanel,paramGrid]
	});      

});

function Oper(){
	this.initParamGrid=function(){

		    store = new Ext.data.JsonStore({
	        root: 'Body',
	        totalProperty: 'totalCount',
	        remoteSort: true,
	        fields: [ 
	        	'paramId', 'gcode', 'mcode','mname', 'target','memo'
	        ],
	        proxy: new Ext.data.HttpProxy({
	            url: '/MOBILE/ExtServlet?actionName=system/QryMobileParamAction'
	        }),
	        listeners:{
	        	load: function(store){
					grid.getSelectionModel().selectRow(0);
				}
	        }
	    });
		
		//创建列   
		var column = new Ext.grid.ColumnModel([
		    {header:'参数ID',dataIndex:'paramId',hidden:true},
		    {header:'参数分类编码',dataIndex:'gcode',width:180},
		    {header:'参数编码',dataIndex:'mcode',width:120},
		    {header:'参数名称',dataIndex:'mname',width:180},
		    {header:'参数适用对象',dataIndex:'target',width:120},
		    {header:'备注',dataIndex:'memo',width:220}
		]);   
		column.menuDisabled=true;
		
		var gridView = new Ext.grid.GridView({
			sortAscText:'升序',
			sortDescText:'降序',
			columnsText:'列名'
		});		
		
		var grid = new Ext.grid.GridPanel({
			id:'paramGrid',
	        region: 'center',
	        collapsible:true, 
	        width:Ext.getBody().getSize().width,   
		    height:Ext.getBody().getSize().height,
		    title:'参数列表',
	        store: store,
	        //trackMouseOver:false,
	        autoScroll: true,
	        loadMask: true,
	        viewConfig : gridView,
			cm:column,
			//tbar:initToolBars(),
			//bbar:pagingBar,
			sm:new Ext.grid.RowSelectionModel({
				singleSelect:true,
				listeners:{
					rowselect:function(sm,row,rec) {
	                	//var sysId = rec.data.sysId;
	                	//Ext.Msg.alert('Message', sysId);
	                    //Ext.getCmp('detailForm').getForm().loadRecord(rec);
	          // qryAPKVerData(rec.data.apkCode);      
	                    //Ext.getCmp('menuGrid').getSelectionModel().selectRow(0);
	                }
				}
			})
	    });
	    store.load({params:{start:0, limit:200,actionType:'qryMobileParam'}});
		
	    return grid;
	}
	this.paramDel = function(){
    	var selItem = Ext.getCmp('paramGrid').getSelectionModel().getSelections();
		var paramObj = new Object();
			paramObj.oper = "del";
			paramObj.paramId = selItem[0].data.paramId;
			var retMap = invokeAction("/mobsystem/InsertMobileParamAction", paramObj);
			 				
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
            Ext.example.msg('','删除成功！');
        }, 1000);
        Ext.getCmp('paramGrid').store.removeAll();
        Ext.getCmp('paramGrid').store.reload();
    }
	
	this.showMenuInfoPage = function(operator){
	
		var selItem = Ext.getCmp('paramGrid').getSelectionModel().getSelections();
			
		var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[0,'适用对象1'],[1,'适用对象2']]});

var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth: 80,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '参数分类编码',
                    name: 'gcode',
                    id: 'm_gcode',
	                allowBlank:false, 
	                blankText:"参数分类编码不能为空!",
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '参数名称',
                    name: 'mname',
                    id: 'm_mname',
	                allowBlank:false, 
	                blankText:"参数名称不能为空!",
                    anchor:'95%'	
                }]
	            },{
	            columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    id: 'm_mcode',
                    name: 'mcode',
                    fieldLabel: '参数编码',
                    xtype: 'textfield',   
                    allowBlank:false, 
                    maxLength:4,
                    maxLengthText:"长度超过了4个字符",
	                blankText:"参数编码不能为空!",                                  
                    anchor:'90%',                    
                    editable : false ,                   
                    blankText:"参数编码不能为空!",
                    store: staticStore
                },{
                    xtype:'combo',
                    fieldLabel: '参数适用对象',
                    name: 'target',
                    id: 'm_target',
                    value: 0,
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
	                allowBlank:false, 
	                blankText:"参数适用对象不能为空!",
                    anchor:'90%',
                    store: staticStore
                }]
            },{columnWidth:.1,layout: 'form'}]},{
			    xtype:'textarea',
			    fieldLabel: '备注',
			    name: 'memo',
			    id: 'm_memo',
			    height : 100,
			    anchor:'95%'
			},{
			    xtype:'hidden',
			    name: 'm_paramId',
			    id: 'paramId'
			}],
			buttons: [{
	            text: '确定',
	            listeners:{"click":function(){
			    	if(!infoPage.getForm().isValid()){
			    		return ;
			    	}
			    	var resultStr ;
			    	
			    	switch (operator){
			    		case "add":{
			    			resultStr = '新增成功！';			    								  			    			
			    			/*var inputParams = new Array();
			    			
			    			inputParams.push({ParamName:'NAME', DataType:2, ParamValues:([Ext.getCmp("m_gcode").getValue()])});
			    			inputParams.push({ParamName:'SHOW_MODEL', DataType:2, ParamValues:([Ext.getCmp("m_mcode").getValue()])});
			    			inputParams.push({ParamName:'URL_STRING', DataType:2, ParamValues:([Ext.getCmp("m_mname").getValue()])});
			    			inputParams.push({ParamName:'DISPLAY_INDEX', DataType:2, ParamValues:([Ext.getCmp("m_target").getValue()])});
			    			inputParams.push({ParamName:'ICON_FILE_NAME', DataType:2, ParamValues:([Ext.getCmp("m_memo").getValue()])});

			    			*/
			    			var inputParams = new Object();
			    			inputParams.gcode = Ext.getCmp("m_gcode").getValue();
			    			inputParams.mcode = Ext.getCmp("m_mcode").getValue();
			    			inputParams.mname = Ext.getCmp("m_mname").getValue();
			    			inputParams.target = Ext.getCmp("m_target").getValue();
			    			inputParams.memo = Ext.getCmp("m_memo").getValue();
			    			inputParams.type = "0";
			    			inputParams.oper = "add";
							var retMap = invokeAction("/mobsystem/InsertMobileParamAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
			    			
			    			var inputParams = new Object();
			    			inputParams.paramId = Ext.getCmp("paramId").getValue();
			    			inputParams.gcode = Ext.getCmp("m_gcode").getValue();
			    			inputParams.mcode = Ext.getCmp("m_mcode").getValue();
			    			inputParams.mname = Ext.getCmp("m_mname").getValue();
			    			inputParams.target = Ext.getCmp("m_target").getValue();
			    			inputParams.memo = Ext.getCmp("m_memo").getValue();
			    			
			    			//objAttr = callRemoteFunction("com.ztesoft.iom.system.MenuManager","modMenu",inputParams);
			    			inputParams.type = "0";
			    			inputParams.oper = "mod";
							var retMap = invokeAction("/mobsystem/InsertMobileParamAction", inputParams);
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
			        menuWin.close();
			        Ext.getCmp('paramGrid').store.removeAll();
			        Ext.getCmp('paramGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
				  
		menuWin = new Ext.Window({
	        title: '参数管理',
		    closable:true,
		    width:650,
		    height:320,
		    plain:true,
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			Ext.getCmp("paramId").setValue(selItem[0].data.paramId);
			Ext.getCmp("m_gcode").setValue(selItem[0].data.gcode);
			Ext.getCmp("m_mcode").setValue(selItem[0].data.mcode);
			Ext.getCmp("m_mname").setValue(selItem[0].data.mname);	
			Ext.getCmp("m_target").setValue(selItem[0].data.target);		
			Ext.getCmp("m_memo").setValue(selItem[0].data.memo);
		}
	
	}
}
</script>
</html>