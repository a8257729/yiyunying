<%@ page contentType="text/html; charset=utf-8"%>

<html>
	<head>
		<!-- TemplBeginEditable name="doctitle" -->
		<title>人员选择</title>
		<script type="text/javascript" src="../common/js/xml2json.js"></script>
		<script language="javascript" src="../public/script/XmlInter.js"></script>
		<script language="javascript" src="../public/script/FormExt.js"></script>
		<%@ include file="../public/common.jsp"%>
    	<%@ include file="../public/style.jsp"%>   
	</head>
	<body style="width: 100%; height: 100%; overflow: hidden">
		
	</body>
</html>
<script language="JScript">
var _obj = window.dialogArguments;
var session1 = GetSession();

Ext.apply(Ext.form.VTypes, {
    password : function(val, field) {
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },passwordText: '确认密码与密码不一致！',
    
    num: function(val, field){
    	if(isNaN(val)){
    		return false ;
    	}
    	return true ;
    },numText: '请输入数字！',
    
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
	
	// create the Data Store
    var naCombosto = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['id', 'nationName'],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/NationSelAction'
        })
    });
    naCombosto.load();
    
    var jobCombosto = new Ext.data.JsonStore({
        remoteSort: true,
        fields: ['jobName', 'jobId'],
        proxy: new Ext.data.HttpProxy({
            url: '/MOBILE/ExtServlet?actionName=system/GetJobByOrgAction&orgId='+_obj.staffOrgId 
        })
    });
    jobCombosto.load();

	var staffPanel = new Ext.FormPanel({
     	labelAlign: 'left',
     	region: 'center',
	 	frame:true,
        width:Ext.getBody().getSize(),
	 	buttonAlign : 'center',
        title: '人员',
        bodyStyle:'padding:5px 5px 0',
        items: [{
            layout:'column',
            items:[{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '姓名',
                    name: 'staffName',
                    id: 'staffName',  
                    allowBlank:false, 
                    blankText:"姓名不能为空!",     
                    anchor:'90%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '密码',
                    name: 'password',
                    id: 'password',
                    inputType: 'password',
                    allowBlank:false, 
                    blankText:"密码不能为空!", 
                    anchor:'90%'
                }, {
                    xtype:'combo',
                    fieldLabel: '职位',
                    name: 'jobId',
                    id: 'jobId',
                    xtype: 'combo',
                    valueField: 'jobId',
                    displayField: 'jobName',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"职位不能为空!", 
                    store: jobCombosto
                }, {
                    xtype:'combo',
                    fieldLabel: '民族',
                    name: 'nation',
                    id: 'nation',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'nationName',
                    anchor:'90%',
                    mode : 'remote',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"民族不能为空!", 
                    store: naCombosto
                }, {
                    xtype:'textfield',
                    fieldLabel: '办公电话',
                    name: 'officeTel',
                    id: 'officeTel',
                    anchor:'90%'
                }, {
                    xtype:'textfield',
                    fieldLabel: '电子邮箱',
                    name: 'eMail',
                    id: 'eMail',
                    vtype:'email',
                    vtypeText: '请输入正确电子邮件地址格式',
                    anchor:'90%'
                }, {
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
                }, {
                    fieldLabel: '施工能力',
                    name: 'constructAbility',
                    id: 'constructAbility',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    anchor:'90%',
                    triggerAction: 'all',
                    forceSelection: true,
                    allowBlank:false, 
                    blankText:"施工能力不能为空!", 
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [[100,'高'],[101,'中'],[102,'低']]
                    })
                }, {
                    xtype:'textfield',
                    fieldLabel: '身份证号码',
                    name: 'certNo',
                    id: 'certNo',
                    anchor:'90%'
                }]
            },{
                columnWidth:.45,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: '用户名',
                    name: 'shif',
                    id: 'userName',
                    allowBlank:false, 
                    blankText:"用户名不能为空!",  
                    anchor:'90%'
                },{
                    xtype:'textfield',
                    fieldLabel: '确认密码',
                    id :'comfPassword',
                    name: 'comfPassword',
                    initialPassField: 'password',
                    allowBlank:false, 
                    blankText:"确认密码不能为空!", 
        			inputType: 'password',
        			vtype:'password',
                    anchor:'90%'
                },{
                    fieldLabel: '缺省职位',
                    name: 'isBasic',
                    id: 'isBasic',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    anchor:'90%',
                    triggerAction: 'all',
                    forceSelection: true,
                    value: _obj.isBasic,
                    allowBlank:false, 
                    blankText:"缺省职位不能为空!", 
                    editable: false ,
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [[1,'是'],[2,'否']]
                    }),
                    disabled :true
                },{
                    fieldLabel: '有效通信方式',
                    name: 'validCommMode',
                    id: 'validCommMode',
                    xtype: 'combo',
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    anchor:'90%',
                    triggerAction: 'all',
                    forceSelection: true,
                    value: 1 ,
                    editable: false ,
                    store: new Ext.data.ArrayStore({
                    	fields:['id','name'],
                    	data: [[1,'邮件'],[2,'移动电话'],[3,'邮件及手机']]
                    })
                },{
                    xtype:'textfield',
                    fieldLabel: '移动电话',
                    name: 'mobileTel',
                    id: 'mobileTel',
                    anchor:'90%'
                },{
                    xtype:'textfield',
                    fieldLabel: '家庭电话',
                    name: 'homeTel',
                    id: 'homeTel',
                    anchor:'90%'
                },{
                    xtype:'datefield',
                    fieldLabel: '失效日期',
                    name: 'expireDate',
                    id: 'expireDate',
                    format :'Y-m-d',
                    allowBlank:false, 
                    blankText:"失效日期不能为空!", 
        			vtype: 'daterange',
        			startDateField: 'effectDate',
                    anchor:'90%'
                },{
                    xtype:'textfield',
                    fieldLabel: '同时登录次数',
                    name: 'logonNumber',
                    id: 'logonNumber',
                    allowBlank:false, 
                    blankText:"同时登录次数不能为空!",
                    vtype: 'num' ,
                    vtypeText : '',
                    anchor:'90%'
                }]
            },{columnWidth:.1,layout: 'form'}]
        },{
		    xtype:'textfield',
		    width: 300,
		    fieldLabel: '地址1',
		    name: 'address1',
		    id: 'address1',
		    anchor:'90%'
		},{
		    xtype:'textfield',
		    fieldLabel: 'IMSI码',
		    name: 'address2',
		    id: 'address2',
		    anchor:'90%'
		},{
		    xtype:'textarea',
		    fieldLabel: '备注',
		    name: 'comments',
		    id: 'comments',
		    height : 100,
		    anchor:'90%'
		}],buttons: [{
				text:'确定',id:'btSubmit',
				listeners:{"click":function(){
				
					var staffObj = new Object();
			    	staffObj.staffName = Ext.getCmp("staffName").getValue() ;
					staffObj.password = Ext.getCmp("password").getValue() ;
					staffObj.jobId = _obj.jobId ;
					staffObj.isBasic = _obj.isBasic ;
					staffObj.nationId = Ext.getCmp("nation").getValue() ;
					staffObj.officeTel = Ext.getCmp("officeTel").getValue() ;
					staffObj.eMail = Ext.getCmp("eMail").getValue() ;
					staffObj.effectDate = Ext.getCmp("effectDate").getValue() ;
					staffObj.constructAbility = Ext.getCmp("constructAbility").getValue() ;
					staffObj.userName = Ext.getCmp("userName").getValue() ;
					staffObj.isBasic = Ext.getCmp("isBasic").getValue() ;
					staffObj.validCommMode = Ext.getCmp("validCommMode").getValue() ;
					staffObj.mobileTel = Ext.getCmp("mobileTel").getValue() ;
					staffObj.homeTel = Ext.getCmp("homeTel").getValue() ;
					staffObj.logonNumber = Ext.getCmp("logonNumber").getValue() ;
					staffObj.expireDate = Ext.getCmp("expireDate").getValue() ;
					staffObj.certNo = Ext.getCmp("certNo").getValue() ;
					staffObj.address1 = Ext.getCmp("address1").getValue() ;
					staffObj.address2 = Ext.getCmp("address2").getValue() ;
					staffObj.comments = Ext.getCmp("comments").getValue() ;
					
			    	if(staffPanel.getForm().isValid()){
			    		var staffId = _obj.staffId || 0;
			    		var existUserName = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb",
								"isExistUserName",staffObj.userName, staffId);
						
						//用户名是否存在重复


						if(existUserName == true){
							Ext.MessageBox.show({
								title: '提示',
								msg: '该用户名已经存在，请选择别的用户',
							   	buttons: Ext.MessageBox.OK,
							   	width:220,
							   	icon: Ext.MessageBox.ERROR
							});
							return;
					 	}
						
						//检查身份证是否重复
						var existCertNo = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManager",
								"isExistCertNo",staffObj.certNo);
						if(existCertNo == true && _obj.operation == "add")	{
							Ext.MessageBox.show({
								title: '提示',
								msg: '该身份证号码已经存在',
							   	buttons: Ext.MessageBox.OK,
							   	width:220,
							   	icon: Ext.MessageBox.ERROR
							});
							return;
						}
						
						
						//检查职位是否有重复
					 	if(_obj.jobId != Ext.getCmp("jobId").getValue()){
							var existJobArr = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.jobmanager.JobManager","findByStaff",staffId,false);
							if(existJobArr.length>0){
								var flag=false;
								for(var i=0;i<existJobArr.length;i++){
									if(existJobArr[i].jobId != _obj.jobId && existJobArr[i].jobId == Ext.getCmp("jobId").getValue()){
										flag=true;
										break;
									}
								}
								if(flag){
									Ext.MessageBox.show({
										title: '提示',
										msg: '该人员已经拥有该职位',
									   	buttons: Ext.MessageBox.OK,
									   	width:220,
									   	icon: Ext.MessageBox.ERROR
									});
									return;
								}
							}
				  		}
				  		
				  		switch (_obj.operation)
						{
							case "add":
							{
								staffObj.jobId = Ext.getCmp("jobId").getValue() ;
								var _result = callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb",
										"createWithLog", staffObj,_obj.orgId, staffObj.jobId,staffObj.isBasic,session1.staff.staffId,session1.staff.staffName);
								//add by fengyang 2011-1-5
								//call out system.
								staffObj.orgId = _obj.orgId;
								/*
								var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction",
										"executeStaff", staffObj,"CREATE_USERPROFILE");
								
								*/
							    Ext.MessageBox.show({
						           	msg: '系统正在提交数据……',
						           	progressText: 'Saving...',
						           	width:300,
						           	wait:true,
						           	waitConfig: {interval:1000},
						           	icon:'ext-mb-download'
						       	});
						        setTimeout(function(){
						            Ext.MessageBox.hide();
						            Ext.example.msg('','人员新增成功！');
						        }, 1000);
									
								window.returnValue = _result;
								window.close();
								break;
							}
							case "mod":
							{
								//密码校验
								var isChangedPwd = callRemoteFunctionNoTrans("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb",
									"isChangedPwd",_resultObj.password, staffObj.password);
									
								if(isChangedPwd){
									staffObj.isModifyPwd = '1';
								}else{
									staffObj.isModifyPwd = '0';
								}
								staffObj.staffId = staffId;
								var _result = callRemoteFunction("com.zterc.uos.oaas.service.staffmanager.StaffManagerWeb",
										"updateWithLog", staffObj,Ext.getCmp("jobId").getValue(),staffObj.isBasic,session1.staff.staffId,session1.staff.staffName);
								//add by fengyang 2011-1-5
								//call out system.
								staffObj.orgId = _obj.orgId;
								/*
								var flg = callRemoteFunction("com.ztesoft.eoms.webservice.action.UserAndDepartSyncAction",
										"executeStaff", staffObj,"UPDATE_USERPROFILE");
								*/
								
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
						            Ext.example.msg('','人员修改成功！');
						        }, 1000);
									
								window.returnValue = _result;
								window.close();
								break;
							}
						}
			    	}
			    }}
			},{
				text:'取消',
				listeners:{"click":function(){
			    	window.close();
			    }}
			}]
    });
    
   var viewPort = new Ext.Viewport({
		layout:'border',
		renderTo:Ext.getBody(),
		items:[staffPanel]
   });
	      
    if(_obj.operation == 'mod' || _obj.operation == 'view'){
    	staffId = _obj.staffId ;
    	//人员信息填充
        var _resultObj = callRemoteFunctionNoTrans("com.ztesoft.mobile.system.dao.StaffSelDAOImpl", "findStaffByKey", staffId);
    	Ext.getCmp("staffName").setValue(_resultObj.staffName);
    	Ext.getCmp("userName").setValue(_resultObj.userName);
    	Ext.getCmp("password").setValue(_resultObj.password);
    	Ext.getCmp("comfPassword").setValue(_resultObj.password);
    	Ext.getCmp("validCommMode").setValue(_resultObj.validCommMode);
    	Ext.getCmp("officeTel").setValue(_resultObj.officeTel);
    	Ext.getCmp("homeTel").setValue(_resultObj.homeTel);
    	Ext.getCmp("mobileTel").setValue(_resultObj.mobileTel);
    	Ext.getCmp("eMail").setValue(_resultObj.email);
    	Ext.getCmp("effectDate").setValue(_resultObj.effectDate);
    	Ext.getCmp("expireDate").setValue(_resultObj.expireDate);
    	
    	Ext.getCmp("logonNumber").setValue(_resultObj.logonNumber);
    	Ext.getCmp("constructAbility").setValue(_resultObj.constructAbility);
    	
    	Ext.getCmp("certNo").setValue(_resultObj.certNo);
    	Ext.getCmp("address1").setValue(_resultObj.address1);
    	Ext.getCmp("address2").setValue(_resultObj.address2);
    	Ext.getCmp("comments").setValue(_resultObj.comments);
    	
    	naCombosto.on('load',function(ds,records,o){
    		Ext.getCmp("nation").setValue(_resultObj.nationId);
		});
		
		jobCombosto.on('load',function(ds,records,o){
    		Ext.getCmp("jobId").setValue(_resultObj.jobId);
		});
		
		if (_obj.operation == 'view'){
		    Ext.getCmp("btSubmit").hide();
		}
		
    }
});
</script>