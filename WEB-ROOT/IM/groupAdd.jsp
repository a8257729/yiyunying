<%@ page contentType="text/html; charset=utf-8" %>
<html>
	<head>
		
		<%@ include file="../public/common.jsp"%>	
		<%@ include file="../public/style.jsp"%>	
		<link rel="stylesheet" type="text/css" href="style/im.css" />
		<script  type="text/javascript" src="../webos/js/jquery-1.7.1.min.js"></script>
		<script  type="text/javascript" src="../webos/js/jquery.artDialog.source.js"></script>
		<script  type="text/javascript" src="../webos/js/iframeTools.js"></script>
		<script language="javascript" src="../public/script/SessionJs.jsp"></script>
		<script language="javascript" src="../public/script/helper.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script language="javascript" src="../public/script/xworkAction.js"></script>
		<title>创建群</title>
	</head> 
	
	<body >
		<div id='groupAdd'></div>
	</body>
</html>
<script>
	var opener = art.dialog.data('opener');//window.dialogArguments;
	
	var session1 = GetSession();//当前用户session
	var groupOper = new GroupOper();
	Ext.onReady(function(){
		var groupWin = Ext.getCmp('groupWin');
		if(Ext.isEmpty(groupWin)){
			groupWin = groupOper.initGroupAddWin();
		
			
		}
	
	});
	function GroupOper(){
		this.width = 600;//opener.imGroupOper.width;
		this.height = 400;//opener.imGroupOper.height;
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
				
			    closable:false,
	            resizable:false,
	            draggable:false,
	            width:this.width,
	            height:this.height,
	            
			    defaults:{autoScroll: true},
			    items:[initGroupInfoPanel,initGroupStaffGridPanel],
			    buttonAlign:'center',
		        buttons: [{
		            text: '创 建',
		            onClick:function(){
		            	var param = {};
		            	
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
		            	var staffs =[{staffId:session1.staff.staffId,staffName:session1.staff.staffName}];
		            	for(var i =0;i<Ext.getCmp('groupStaffGridPanel').getStore().getCount();i++){
		              		var obj ={};
		              		obj.staffId = Ext.getCmp('groupStaffGridPanel').getStore().getAt(i).data.staffId;
		              		
		              		obj.staffName = Ext.getCmp('groupStaffGridPanel').getStore().getAt(i).data.staffName;
		              		staffs[staffs.length] = obj;
	              		}
	              		param.groupStaffList = staffs;
	              		var retVal = invokeAction('/immanager/CreateBigGroupAction',param);
		            	if(retVal =='success'){
		            		opener.Ext.getCmp('bigGroupTree').getRootNode().reload();
		            		//window.close();	            		
		            		//parent.closeWin("addGroup");
		            		art.dialog.close();
		            	}
		            }
		        },{
		            text: '关 闭',
		            onClick:function(){
				  		//window.close();
		                //parent.closeWin("addGroup");
		                art.dialog.close();
		            }
		        }]
			});
			return mainTabPanel;
		}
		this.initGroupInfoPanel = function(){
			 var groupInfoPanel = new Ext.FormPanel({
		        labelAlign: 'top',
		        title: '群信息',
		        bodyStyle:'padding:5px',
		        width:this.width,
		        height:this.height,
		        items: [{
		            layout:'column',
		            border:false,
		            items:[{
		               
		                layout: 'form',
		                border:false,
		                items: [ {
		                    xtype:'textfield',
		                    fieldLabel: '名称',
		                    id:'imStaffBiggroupName',
		                    name: 'imStaffBiggroupName',
		                    anchor:'95%'
		                }]
		            }]
		        },{
		            xtype:'textarea',
		            fieldLabel: '群公告 (限400字节)',
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
			          pageSize:10,
			          paging:true,
			          tbar:this.staffAddBar(),
			          cm:column,
			          url:''
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
				 	groupStaffGridPanel.getStore().remove(g1Rows[i]);
				 }
				 
			 }
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

