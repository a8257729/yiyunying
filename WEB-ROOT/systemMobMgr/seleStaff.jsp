<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!-- TemplBeginEditable name="java" -->
<%
String selType = request.getParameter("sel_type");//选择种类{1单选，2多选}
%>
<html>
	<head>

		<title>选择人员</title>
		<link rel="stylesheet" type="text/css" href="/MOBILE/ext/resources/css/ext-all.css" />			
		<script language="javascript" src="../public/script/helper.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<script type="text/javascript" src="/MOBILE/ext/adapter/ext/ext-base-debug.js"></script>
		<script type="text/javascript" src="/MOBILE/ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="../ext/FileUploadField.js"></script>
		<script type="text/javascript" src="/MOBILE/common/ztesoftext/orgTree.js"></script>
		<script type="text/javascript" src="/MOBILE/ext/source/locale/ext-lang-zh_CN.js"></script>
		
        <%@ include file="../public/style.jsp"%>	
       
</head>
<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
       
        <tr valign="top"><td>
		<div id="orgDiv"></div>
		</td>
		<td>
		<div id="staffGird"></div>
		</td>
</tr>
<tr><td align="center" colspan="2"><input type="button" name="commit" value="确认" onclick="returnData()"><input type="button" name="commit" value="取消" onclick="window.close()"></td></tr>
</table>
</body>
<script type="text/javascript">
var session1 = GetSession();
var grid;
Ext.apply(Ext.form.VTypes, {
    daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },

    passwordText : 'Passwords do not match'
});

Ext.onReady(function(){

var _orgJobData = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "findTopOrgs",-1);
	var orgId = 0 ;
	var orgPathCode ;
	var staffName ;
	var officeTel ;
	var userName  ;
	var mobileTel ;
	var qryType = 'qrySelf';
	var password ;
	
	for(var i=0;i<_orgJobData.length;i++){
		_orgJobData[i].text =_orgJobData[i].jobName;
		_orgJobData[i].orgId =_orgJobData[i].orgId;
		_orgJobData[i].orgPathCode =_orgJobData[i].orgPathCode;
		_orgJobData[i].leaf = false;
		_orgJobData[i].children = new Array();
	}
	var tree = new ZTESOFT.OrgTree({
		id:'orgTree',
       	//region: 'west',
       	allOrgs:true,
       	renderTo: 'orgDiv',
       	width:Ext.getBody().getSize().width*0.45,
       	height:Ext.getBody().getSize().height*0.9,
       	checked:false 
       	
	});

    tree.on('click', function(node){ //使节点可以单击展开收起，默认是双击的
	    if(node.leaf == false){
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
		store.load({params:{start:0, limit:15}});
		
    });
  

 //------------------------------------------------------------------------------------//
    function renderIsBasic(value, p, r){
        if(value == 1){
        	return '是';
        }else {
        	return '否';
        }
    }
    // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'Body',
        totalProperty: 'totalCount',
        remoteSort: true,
        fields: [ 
        	'staffId', 'jobId', 'orgId', 'staffName','userName','jobName',
        	'isbasic', 'orgName', 'officeTel', 'mobileTel'
        ],
        proxy: new Ext.data.HttpProxy({
            url:'/MOBILE/ExtServlet?actionName=system/StaffPagingAction'
        }),
        listeners:{
        	load: function(store){
				grid.getSelectionModel().selectRow(0);
			}
        }
    });

 var pagingBar = new Ext.PagingToolbar({
		pageSize: 15,
		store: store,
		displayInfo: true,
		displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		emptyMsg: "没有记录"
    });
    	
	//创建列   
	var column = new Ext.grid.ColumnModel([
	    {header:'人员编号',dataIndex:'staffId',hidden:true },
	    {header:'职位编号',dataIndex:'jobId',hidden:true },
	    {header:'组织编号',dataIndex:'orgId',hidden:true },
	    {header:'姓名',dataIndex:'staffName',width:Ext.getBody().getSize().width*0.52},
	    {header:'用户名',dataIndex:'userName',hidden:true},
	    {header:'职位',dataIndex:'jobName',hidden:true},
	    {header:'职位是否缺省',dataIndex:'isbasic',renderer: renderIsBasic,hidden:true},
	    {header:'组织',dataIndex:'orgName',hidden:true},
	    {header:'办公电话',dataIndex:'officeTel',hidden:true},
	    {header:'移动电话',dataIndex:'mobileTel',hidden:true}
	]);   
	column.defaultSortable = true;//默认可排序   
	
	grid = new Ext.grid.GridPanel({
        el:'staffGird',
        width:Ext.getBody().getSize().width*0.53,   
	    height:Ext.getBody().getSize().height*0.9,
	    title:'人员列表',
        store: store,
        trackMouseOver:false,
        autoScroll: true,
        loadMask: true,
		cm:column,
		bbar: pagingBar,
		sm: new Ext.grid.RowSelectionModel({
	        singleSelect: true,
	        listeners: {
	          
	        }
	    })
    });
	        
	grid.render();
	store.load({params:{qryType:qryType, orgId:orgId, start:0, limit:15}});
});
function returnData(){
             
           if(grid.getSelectionModel().getSelected()!=null){
	      var selNodes = grid.getSelectionModel().getSelected().data;
	       
	       window.returnValue = selNodes;
	       window.close();
	} else{
	   window.returnValue = '';
	    window.close();
	 }
	}
</script>
