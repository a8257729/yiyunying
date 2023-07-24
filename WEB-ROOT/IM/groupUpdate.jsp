<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<link rel="stylesheet" type="text/css" href="style/im.css" />
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>修改群信息</title>
	</head> 
	
	<body >
		<div id='groupAdd'></div>
	</body>
</html>
<script>
	var opener = art.dialog.data('opener');//window.dialogArguments;

	var session1 = GetSession();//当前用户session
	var swidth =getGroupAddWinWidth();//分辨率宽度

	var sheight = getGroupAddWinHeight();//分辨率高度

	var groupOper = new GroupOper();
	var	groupStaffCnt =10; //群成员数量

	Ext.onReady(function(){
		var groupWin = Ext.getCmp('groupWin');
		if(Ext.isEmpty(groupWin)){
			groupWin = groupOper.initGroupAddWin();
			
			groupOper.initData();
		}
	
	});
	function GroupOper(){
		this.width = 600;//opener.imGroupOper.width;
		this.height = 400;
		this.initData = function(){
			var param = {'imStaffBiggroupId':opener.Ext.getCmp('bigGroupTree').getSelectionModel().getSelectedNode().id};
			var record = invokeAction('/immanager/QryBigGroupByIdAction',param);
			Ext.getCmp('groupInfoPanel').getForm().loadRecord(new Ext.data.Record(record));
			Ext.getCmp('groupStaffGridPanel').store.load({params:{ start:0, limit:groupStaffCnt}});
		}
		this.initGroupAddWin = function(){
			var mainTabPanel = this.mainTabPanel();
			var win = new Ext.Viewport({
	       		id:'groupWin',
	            layout:'border',
	            items:[mainTabPanel]
	           
       		 });
			return win;
		}
		this.mainTabPanel = function(){
			var initGroupInfoPanel = this.initGroupInfoPanel();
			var initGroupStaffGridPanel = this.initGroupStaffGridPanel();
			var mainTabPanel = new Ext.TabPanel({
				region: 'center',
				activeTab: 0,
				title: '创建群',
				closable:false,
	            resizable:false,
	            draggable:false,
			    defaults:{autoScroll: true},
			    items:[initGroupInfoPanel,initGroupStaffGridPanel],
			     buttonAlign:'center',
		        buttons: [{
		            text: '确 定',
		            onClick:function(){
		            	var param = {};
		            	param.imStaffBiggroupId = opener.Ext.getCmp('bigGroupTree').getSelectionModel().getSelectedNode().id
		            	param.imStaffBiggroupName = Ext.getCmp("imStaffBiggroupName").getValue();
		            	param.groupNews = Ext.getCmp("groupNews").getValue();
		            	if(param.imStaffBiggroupName.trim()==''){
		            		Ext.MessageBox.show({
													           title: '消息',
													           msg: "群名称不能为空",
													           buttons: Ext.MessageBox.OK,
													           width:250,
													           icon: Ext.MessageBox.INFO
													       });
		            		return;
		            	}
		            	
		            	if(len(param.groupNews)>=400){
		            		Ext.MessageBox.show({
													           title: '消息',
													           msg: "输入公告不能超过400字节",
													           buttons: Ext.MessageBox.OK,
													           width:250,
													           icon: Ext.MessageBox.INFO
													       });
		            	
		            		return;
		            	}
		            	param.staffId = session1.staff.staffId;
		            	var staffs =[];
		            	
		            	for(var i =0;i<Ext.getCmp('groupStaffGridPanel').getStore().getCount();i++){
		              		var obj ={};
		              		
		              		obj.staffId = Ext.getCmp('groupStaffGridPanel').getStore().getAt(i).data.staffId;
		              		obj.staffName = Ext.getCmp('groupStaffGridPanel').getStore().getAt(i).data.staffName;
		              		staffs[staffs.length] = obj;
	              		}
	              		
	              		
	              		param.groupStaffList = staffs;
	              		
	              		var retVal = invokeAction('/immanager/UpdateBigGroupAction',param);
		            	if(retVal =='success'){
		            		opener.Ext.getCmp('bigGroupTree').getRootNode().reload();
		            		art.dialog.close();
		            		//window.close();
		            	}
		            }
		        },{
		            text: '关闭',
		            onClick:function(){
				  		//window.close();
				  		art.dialog.close();
		            
		            }
		        }]
			});
			return mainTabPanel;
		}
		this.initGroupInfoPanel = function(){
			 var groupInfoPanel = new Ext.FormPanel({
			 	id:'groupInfoPanel',
		        labelAlign: 'top',
		        title: '群信息',
		        bodyStyle:'padding:5px',
		        items: [{
		            layout:'column',
		            border:false,
		            items:[{
		               	columnWidth:0.5,
		                layout: 'form',
		                border:false,
		                items: [ {
		                    xtype:'textfield',
		                    fieldLabel: '群号',
		                    id:'imStaffBiggroupId',
		                    name: 'imStaffBiggroupId',
		                    readOnly:true,
		                    anchor:'95%'
		                }]
		            },{
		               	columnWidth:0.5,
		                layout: 'form',
		                border:false,
		                items: [ {
		                    xtype:'textfield',
		                    fieldLabel: '创建人',
		                    id:'staffName',
		                    name: 'staffName',
		                    readOnly:true,
		                    anchor:'95%'
		                }]
		           
		        },{
		               	columnWidth:0.5,
		                layout: 'form',
		                border:false,
		                items: [ {
		                    xtype:'textfield',
		                    fieldLabel: '名称',
		                    id:'imStaffBiggroupName',
		                    name: 'imStaffBiggroupName',
		                    anchor:'95%'
		                }]
		            },{
		               	columnWidth:0.5,
		                layout: 'form',
		                border:false,
		                items: [ {
		                    xtype:'textfield',
		                    fieldLabel: '创建时间',
		                    id:'createDate',
		                    name: 'createDate',
		                    readOnly:true,
		                    anchor:'95%'
		                }]
		            }]
		        },{
		            xtype:'textarea',
		            fieldLabel: '群公告',
		            id:'groupNews',
		            name: 'groupNews',
		            height:150,
                    width:this.width-35,
		            deferredRender: false,
		            defaults:{bodyStyle:'padding:10px'}
		            
		        }]
				
   			 });
   			 return groupInfoPanel;
		}
		this.initGroupStaffGridPanel = function(){
		    
			var column = new Ext.grid.ColumnModel([
			    {header:'用户ID',dataIndex:'staffId',hidden:true },
			    {header:'姓名',dataIndex:'staffName',width:100 }
			   
			]); 
			var groupStaffGridPanel = new ZTESOFT.Grid({
			          id:'groupStaffGridPanel',
			          title: '群成员',
			          height:this.height,
			          width:this.width,
			          pageSize:groupStaffCnt,
			          paging:true,
			          tbar:this.staffAddBar(),
			          cm:column,
			          url:'/MOBILE/ExtPagingServlet?actionName=QryGroupStaffListByIdAction'
			});
			 groupStaffGridPanel.getRowByStaffId = function(staffId){
			 	 for(var i=0;i< groupStaffGridPanel.getStore().getCount();i++){
					if(staffId== groupStaffGridPanel.getStore().getAt(i).data.staffId){
						var obj = new Object();	
						obj.staffId = groupStaffGridPanel.getStore().getAt(i).data.staffId;
						obj.staffName = groupStaffGridPanel.getStore().getAt(i).data.staffName;
						return obj;
					}
				}
				return null;
			 }
			 groupStaffGridPanel.delData = function(){
				 var g1Rows = groupStaffGridPanel.getSelectionModel().getSelections();
				 if(Ext.isEmpty(g1Rows)){
				 	return;
				 }
				
				 for(var i=0;i<g1Rows.length;i++){
				 	if(g1Rows[i].data.staffId==session1.staff.staffId){
					 	Ext.MessageBox.show({
													           title: '消息',
													           msg: '不能移除群创建者！',
													           buttons: Ext.MessageBox.OK,
													           width:250,
													           icon: Ext.MessageBox.INFO
													       });
						return ;												       
					 }
				 }
				 for(var i=0;i<g1Rows.length;i++){
				 	
				 	groupStaffGridPanel.getStore().remove(g1Rows[i]);
				 }
				 
			 }
			groupStaffGridPanel.store.on('beforeload',
				function(store){ 
					if(groupStaffGridPanel.store.lastOptions != null){
						groupStaffGridPanel.store.baseParams = {'imStaffBiggroupId':opener.Ext.getCmp('bigGroupTree').getSelectionModel().getSelectedNode().id};
					}
				}
			)
			groupStaffGridPanel.on("rowdblclick",function(){
				groupStaffGridPanel.delData();
			});
			return groupStaffGridPanel;
		}
		this.staffAddBar = function(){
			
			var tb = new Ext.Toolbar();
			tb.add({
	            text:'添加成员',
	            onClick:function(){
	            	groupOper.addStaff();
	            }
	        },{
	            text:'删除成员',
	            onClick:function(){
	            	Ext.getCmp('groupStaffGridPanel').delData();
	            }
	        });
	        return tb;
		}
		this.addStaff = function(){
	   		//var staffs = window.showModalDialog("groupStaffAdd.jsp",window,"dialogHeight="+450+"px;dialogWidth="+550+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
	   		var staffs = parent.addStaff(window);
	   		if(!Ext.isEmpty(staffs)){
	   			for(var i=0;i<staffs.length;i++){
		   		 	if(Ext.getCmp('groupStaffGridPanel').getRowByStaffId(staffs[i].staffId)==null){
		   		 		Ext.getCmp('groupStaffGridPanel').getStore().add(new Ext.data.Record(staffs[i]));
		   		 	}
		   		 	
		   		 }
	   		}
		 }
		
}
//设置创建群的窗口长宽
function getGroupAddWinWidth(){
	if(screen.availWidth >= 1280){
		return screen.availWidth*0.630;
	}else{
		return screen.availWidth*0.530;
	}
}

function getGroupAddWinHeight(){
	if(screen.availHeight < 1024){
		return screen.availHeight*0.63;
	}else if(screen.availHeight >= 1024){
		return screen.availHeight*0.60;
	}
}
function len(s) { 
var l = 0; 
var a = s.split(""); 
for (var i=0;i<a.length;i++) { 
if (a[i].charCodeAt(0)<299) { 
l++; 
} else { 
l+=2; 
} 
} 
return l; 
}
</script>

