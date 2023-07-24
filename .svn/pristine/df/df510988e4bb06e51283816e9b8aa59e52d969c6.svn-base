<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>选择人员</title>
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="staffMngDiv"></div>
	</body>
</html>

<script language="JScript">

var session1 = GetSession();

Ext.onReady(function(){
	var orgId = 0 ;
	var orgPathCode ;
	var staffName ;
	var officeTel ;
	var userName  ;
	var mobileTel ;
	var qryType = 'qrySub';
	var password ;
	
	var tree = new ZTESOFT.OrgTree({
		region: 'west',
       	allOrgs: false
	});
	
    tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的

	    if(node.isLeaf() == false){
			if(node.expanded == false){
				node.expand();//展开
			}else{
				node.collapse();//收起
			}
	    }
	    orgId = node.attributes.orgId ;
	    orgPathCode = node.attributes.orgPathCode ;
	    store.on('beforeload',
			function(store){ 
				if(store.lastOptions != null){
					store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType};
				}
			}
		)
	    store.removeAll() ;
		store.load({params:{start:0, limit:5}});
    });
    
    //查询条件面板
    var qryPanel = new Ext.FormPanel({
		region: 'north',
     	labelAlign: 'left',
        labelWidth :70,
	 	frame:true,
        title: '查询条件',
        bodyStyle:'padding:5px 5px 5px 5px',
        height:Ext.getBody().getSize().height*0.2,
        width:Ext.getBody().getSize().width*0.6,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '姓名',
                    name: 'staffName',
                    id: 'staffName',
                    anchor:'95%',
                    listeners: {
						specialkey: function(f,e){
							if (e.getKey() == e.ENTER) {
								qryPanel.getForm().submit();
							}
						}}
					}, {
                    xtype:'textfield',
                    fieldLabel: '办公电话',
                    name: 'officeTel',
                    id: 'officeTel',
                    anchor:'95%'
                }]
            },{
                columnWidth:.3,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'userName',
                    id: 'userName',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: '移动电话',
                    name: 'mobileTel',
                    id: 'mobileTel',
                    anchor:'95%'
                }]
            },{
                columnWidth:.4,
                layout: 'form',
                items: [{
                    name: 'qryType',
                    fieldLabel: '查询类别',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    id: 'qryType',
                    mode: 'local',
                    anchor:'95%',
                    triggerAction: 'all',
                    editable : false ,
                    value: 'qrySub',
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [['qrySub','查询本组织及下属组织'],['qrySelf','查询本组织'],['qryNull','查询无职位人员']]
                    })
                },{
                    buttons: [{ 
                    	text: '查询' ,
                    	listeners:{
                    		"click":function(){
			                	staffName = Ext.getCmp("staffName").getValue() ;
			                	officeTel = Ext.getCmp("officeTel").getValue() ;
			                	userName  = Ext.getCmp("userName").getValue() ;
			                	mobileTel = Ext.getCmp("mobileTel").getValue() ;
			                	qryType   = Ext.getCmp("qryType").getValue() ;
			                	store.on('beforeload',function(store){
									if(store.lastOptions != null){
										store.baseParams = {orgPathCode:orgPathCode,orgId:orgId,staffName:staffName,officeTel:officeTel,userName:userName,mobileTel:mobileTel,qryType:qryType};
									}
								})
								store.removeAll() ;
								store.load({params:{start:0, limit:5}});
			            	}
			            }},{
                    	text: '重置' ,
                    	listeners:{"click":function(){
			                qryPanel.form.reset();
			            }} 
                    }]
                }]
            }]
        }]
    });
    
    function renderIsBasic(value, p, r){
        if(value == 1){
        	return '是';
        }else{
        	return '否';
        }
    }
    
    function renderIscanGrant(value, p, r){
        if(value == 0){
        	return '<span style="color:red;">否</span>';
        }else{
        	return '<span style="color:green;">可</span>';
        }
    }
    
    function renderSource(value, p, r){
        if(value == 2){
        	return '特有';
        }else{
        	return '职位';
        }
    }
    
    // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'staffId', 'jobId', 'orgId', 'staffName','userName', 'orgPathName', 'officeTel', 'mobileTel'
        ],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/StaffPagingAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });
	
	//创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'人员编号',dataIndex:'staffId',hidden:true },
	    {header:'职位编号',dataIndex:'jobId',hidden:true },
	    {header:'组织编号',dataIndex:'orgId',hidden:true },
	    {header:'姓名',dataIndex:'staffName',width:100},
	    {header:'用户名',dataIndex:'userName',width:100},
	    {header:'组织',dataIndex:'orgPathName',width:230},
	    {header:'办公电话',dataIndex:'officeTel',width:100},
	    {header:'移动电话',dataIndex:'mobileTel',width:100}
	]);   
	column.defaultSortable = true;//默认可排序   
	
    var pagingBar = new Ext.PagingToolbar({
		pageSize: 5,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
	
	var grid = new Ext.grid.GridPanel({
		region: 'center',
		id:'staffGrid',
	    title:'人员列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		buttonAlign :'center',
		buttons:[{
            	text: '确定',onClick:doSubmit},{text: '取消',onClick:doCancel
        }],
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	            rowselect: function(sm, row, rec) {
	            	
	            }
	        }
	    }),
        bbar: pagingBar
    });
	
	function doCancel(){
	
	   window.close();
	    
	}
	
	function doSubmit(){
	   var selItem2 = Ext.getCmp('staffGrid').getSelectionModel().getSelections();
	   if(selItem2 ==null || selItem2.length ==0){
            Ext.MessageBox.show({
                 title: '提示',
                 msg: '请选择人员！',
                 buttons: Ext.MessageBox.OK,
                 width:200,
                 icon: Ext.MessageBox.ERROR
             });
             return;
       }
       
       if( selItem2.length >1){
            Ext.MessageBox.show({
                 title: '提示',
                 msg: '只能选择一个人员！',
                 buttons: Ext.MessageBox.OK,
                 width:200,
                 icon: Ext.MessageBox.ERROR
             });
             return;
       }
       var strJson = ["staffId", "staffName"];
    
       var valueArray = new Array();
       var inputParams = new Object();
       for(i=0;i<strJson.length;i++) {
          inputParams[strJson[i]] = selItem2[0].data[strJson[i]];         
       }
       valueArray.push(inputParams);
       window.returnValue = valueArray;
       window.close();
    }
	
	var earthviewport = new Ext.Panel({
		border: false ,
		region: 'center',
		layout: 'border',
		items:[qryPanel,grid]
	}); 
	
	var viewport = new Ext.Viewport({
		el:'staffMngDiv',
		layout: 'border',
		items:[tree,earthviewport]
	});
});

</script>