<%@ page contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title>组织</title>
		<%@ include file="../public/common.jsp"%>
		<%@ include file="../public/style.jsp"%>
		<link rel="stylesheet" type="text/css" href="../ext/examples/ux/treegrid/treegrid.css"/>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridSorter.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridColumnResizer.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridNodeUI.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridLoader.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGridColumns.js"></script>
		<script type="text/javascript" src="../ext/examples/ux/treegrid/TreeGrid.js"></script>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
	</head>

	<body onContextMenu="return false;"
		background="../images/main/background.gif"
		style="width: 100%; height: 100%; overflow: hidden">
		<div id="orgMngDiv"></div>
	</body>
</html>

<script language="JScript">
var _obj = window.dialogArguments;
var session1 = GetSession();

Ext.apply(Ext.form.VTypes, {
    daterange : function(val, field) {
        var date = field.parseDate(val);
        if(!date){
            return;
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
        return true;
    }
});

Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
    var orgTmpCombosto = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['orgTmpId', 'orgTmpName'],
        proxy: new Ext.data.HttpProxy({
            url:'/MOBILE/ExtServlet?actionName=system/OrgTmpCmoSelAction&orgTmpId='+ _obj.orgTmpId
           
        })
    });
    orgTmpCombosto.load();
    
    var areaCombosto = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['areaId', 'areaName'],
        proxy: new Ext.data.HttpProxy({
            url:'/MOBILE/ExtServlet?actionName=system/AreaCmoSelAction&areaId='+ _obj.areaId 

        })
    });
    areaCombosto.load();
    
	var orgPanel = new Ext.FormPanel({
     	labelAlign: 'left',
     	region: 'center',
	 	frame:true,
        width:Ext.getBody().getSize(),
        title: '组织',
        bodyStyle:'padding:5px',
        buttonAlign: 'center',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '名称',
                    name: 'orgName',
                    id: 'orgName',  
                    allowBlank:false, 
                    blankText:"名称不能为空!",
                    anchor:'90%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '别名',
                    name: 'alias',
                    id: 'alias',
                    anchor:'90%'
                }, {
                    xtype:'combo',
                    fieldLabel: '组织模板',
                    name: 'orgTemp',
                    id: 'orgTemp',
                    xtype: 'combo',
                    valueField: 'orgTmpId',
                    displayField: 'orgTmpName',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"组织模板不能为空!", 
                    store: orgTmpCombosto
                }, {
                    xtype:'textfield',
                    fieldLabel: '电话',
                    name: 'tel',
                    id: 'tel',
                    anchor:'90%'
                },  {
                    xtype:'datefield',
                    fieldLabel: '生效日期',
                    name: 'effectDate',
                    id: 'effectDate',
                    format :'Y-m-d',
                    allowBlank:false, 
                    blankText:"生效日期不能为空!", 
        			vtype: 'daterange',
        			endDateField: 'expireDate',
                    anchor:'90%'
                }]
            },{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '编号',
                    name: 'orgCode',
                    id: 'orgCode',
                    allowBlank:false, 
                    blankText:"编号不能为空!", 
                    anchor:'90%'
                },{
                    xtype:'textfield',
                    fieldLabel: '简称',
                    name: 'acronym',
                    id: 'acronym',
                    anchor:'90%'
                }, {
                    xtype:'combo',
                    fieldLabel: '区域',
                    name: 'areaId',
                    id: 'areaId',
                    xtype: 'combo',
                    valueField: 'areaId',
                    displayField: 'areaName',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"区域不能为空!", 
                    store: areaCombosto
                }, {
                    xtype:'textfield',
                    fieldLabel: '传真',
                    name: 'fax',
                    id: 'fax',
                    anchor:'90%'
                },{
                    xtype:'datefield',
                    fieldLabel: '失效日期',
                    name: 'expireDate',
                    id: 'expireDate',
                    format :'Y-m-d',
                    allowBlank:false,
                    blankText:"失效日期不能为空!",
                    minValue: new Date(),
        			vtype: 'daterange',
        			startDateField: 'effectDate',
                    anchor:'90%'
                }]
            }]
        },{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'comments',
		    id: 'comments',
		    height : 100,
		    anchor:'85%'
		}],buttons: [{
				text:'确定',
				listeners:{"click":function(){
					if(orgPanel.getForm().isValid()){
						var selItem = _obj.orgTree.getSelectionModel().selNode ;
						
						if(_obj.index == 0){
							var itemsArr = null;
							if(selItem.parentNode){
								itemsArr = selItem.parentNode.childNodes;
							}else{
								itemsArr= _obj.orgTree.getRootNode().childNodes;
							}
							
							for(var i=0;i<itemsArr.length;i++){
								if (itemsArr[i].attributes.orgName == Ext.getCmp("orgName").getValue()){
									ErrorHandle('指定的组织与现有组织重名，请指定另一组织名称');
									return;
								}
								if (itemsArr[i].attributes.orgCode == Ext.getCmp("orgCode").getValue()){
									ErrorHandle('指定的编码与现有编码重名，请指定另一编码名称');
									return;
								}
							}
						}
						
						var orgName = Ext.getCmp("orgName").getValue();
						var index = Ext.getCmp("orgTemp").getValue();
						if(index == -1)	{
							ErrorHandle('技能配置情况');
							return;
						}
						
						var obj = new Object();
						obj.orgId = _obj.orgId || 0;
						obj.orgCode = Ext.getCmp("orgCode").getValue() || "";
						obj.orgName = orgName;
						obj.alias = Ext.getCmp("alias").getValue() || "";
						obj.acronym = Ext.getCmp("acronym").getValue() || "";
						obj.tel = Ext.getCmp("tel").getValue() || "";
						obj.areaId = Ext.getCmp("areaId").getValue();
						obj.parentId = _obj.parentId || 0;
						//obj.areaName = this.form.areaName.value || "";
				
						obj.fax = Ext.getCmp("fax").getValue() || "";
						obj.effectDate = Ext.getCmp("effectDate").getValue();
						obj.expireDate = Ext.getCmp("expireDate").getValue();
						obj.orgTmpId = Ext.getCmp("orgTemp").getValue();
						//obj.orgTmpName = this.form.orgTmpName[index].innerText;
						obj.comments = Ext.getCmp("comments").getValue() || "";
				
						obj.orgTypeId = "A01";
						
						var msg ;
						switch (_obj.operation){
							case "add":{
								msg = '组织新增成功！' ;
								obj = callRemoteFunction("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "createWithLog", obj,session1.staff.staffId,session1.staff.staffName);
								
								//add by fengyang 2011-1-5
								//call out system
								//var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction","excuteOrg", obj,"","CREATE_DEPARTMENT");
								
								window.returnValue = obj;
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
						            Ext.example.msg('',msg);
						        }, 2000);
								break;
							}
							case "mod":{
								var orgId = _obj.orgId|| 0; ;
								var _resultObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.dao.OrganizationDAOImpl", "findByKey", orgId);
        						var oldOrgPathName = _resultObj.orgPathName;
        						
        						
								callRemoteFunction("com.zterc.uos.oaas.service.orgmanager.OrganizationManagerWeb", "updateWithLog", obj,session1.staff.staffId,session1.staff.staffName);
								//add by fengyang 2011-1-5
								//call out system
								//alert(oldOrgPathName);
								//var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction","excuteOrg", obj,oldOrgPathName,"UPDATE_DEPARTMENT");
								
								window.returnValue = obj;
								break;
							}
						}
						window.close();
					}
			    }}
			},{
				text:'取消',
				listeners:{"click":function(){
			    	window.close();
			    }}
			}]
    });
    //orgPanel.render(document.all.orgMngDiv);
    
    var viewPort = new Ext.Viewport({
		layout:'border',
		renderTo:Ext.getBody(),
		items:[orgPanel]
   });
    
    if(_obj.operation == 'mod'){
    	var orgId = _obj.orgId ;
    	//人员信息填充
        var _resultObj = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.orgmanager.dao.OrganizationDAOImpl", "findByKey", orgId);
        Ext.getCmp("orgName").setValue(_resultObj.orgName);
        Ext.getCmp("alias").setValue(_resultObj.alias);
        Ext.getCmp("tel").setValue(_resultObj.tel);
        Ext.getCmp("effectDate").setValue(_resultObj.effectDate);
        Ext.getCmp("orgCode").setValue(_resultObj.orgCode);
        Ext.getCmp("acronym").setValue(_resultObj.acronym);
        Ext.getCmp("fax").setValue(_resultObj.fax);
        Ext.getCmp("expireDate").setValue(_resultObj.expireDate);
        Ext.getCmp("comments").setValue(_resultObj.comments);
        
        areaCombosto.on('load',function(ds,records,o){
    		Ext.getCmp("areaId").setValue(_resultObj.areaId);
		});
		
		orgTmpCombosto.on('load',function(ds,records,o){
    		Ext.getCmp("orgTemp").setValue(_resultObj.orgTmpId);
		});
    }
});
</script>
