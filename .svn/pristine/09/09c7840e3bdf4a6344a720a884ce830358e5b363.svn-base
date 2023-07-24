function StatOper(){
	this.showMenuInfoPage = function(operator,pmName,pmId){
	    var selItemtree = Ext.getCmp('moduelTree').getSelectionModel().selNode ;
		var selItem = Ext.getCmp('menuGrid').getSelectionModel().getSelections();
		var selGridItem = Ext.getCmp('statGrid').getSelectionModel().getSelections();
        if(selItem ==null){
           Ext.MessageBox.show({
	          	title: '提示',
		        msg: '请选择环节名称！',
		        buttons: Ext.MessageBox.OK,
		        width:200,
		        icon: Ext.MessageBox.ERROR
		    });
			return;
        }
        
            
        //var statCycleStore = new Ext.data.ArrayStore({fields:['statCycle','statCycleName'], data: [[1,'月报'],[2,'周报'],[3,'日报']]});
        //var statRangeStore = new Ext.data.ArrayStore({fields:['statRange','statRangeName'], data: [[1,'省级'],[2,'市级']]});
        var statTypeStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'总和/平均数等'],[2,'明细']]});
        var staticStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'否'],[2,'是']]});
        var isSumStore = new Ext.data.ArrayStore({fields:['id','value'], data: [[1,'显示'],[0,'不显示']]});
		var infoPage = new Ext.FormPanel({
			region: 'center',
			labelAlign: 'left',
		 	frame:true,
	        width:Ext.getBody().getSize(),
	        bodyStyle:'padding:5px',
	        buttonAlign: 'center',
	        labelWidth: 70,
	        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                	//bodyStyle: 'padding-right:5px;',
                	xtype: 'fieldset',
                	title: '统计周期',
                	id : 'm_stat_cycle',
                	autoHeight: true,
                	defaultType: 'checkbox',
                	layout : 'column',
                	items: [{
                    	checked: false,
                    	boxLabel: '月报',
                    	name: 'm_is_month',
                    	id: 'm_is_month'
                		},{
                    	checked: false,
                    	boxLabel: '周报',
                    	name: 'm_is_week',
                    	id: 'm_is_week'
                		},{
                    	checked: false,
                    	boxLabel: '日报',
                    	name: 'm_is_day',
                    	id: 'm_is_day'
                		},{
                    	checked: false,
                    	boxLabel: '其他',
                    	name: 'm_is_other',
                    	id: 'm_is_other',
                    	handler: function(checkbox, checked){
                    		Ext.getCmp('m_stat_cycle_display_name').setDisabled(!checked);
                    		if(checked){
                    			Ext.getCmp('m_stat_cycle_display_name').setValue('其他');
                    		} else {
                    			Ext.getCmp('m_stat_cycle_display_name').setValue('其他周期的名称');
                    		}
                    	}
                		},{
                		xtype : 'textfield',
                		id : 'm_stat_cycle_display_name',
                		emptyText : '其他周期的名称',
                		width : 100,
                		disabled : true
                		}]                	
                },{
                	layout : 'column',
                	items: [{
                		xtype: 'fieldset',
                		title: '统计范围',
                		id : 'm_stat_range',
                		autoHeight: true,
                		defaultType: 'checkbox',
                		columnWidth : .2,
                		layout : 'anchor',
                		items : [{
                   			checked: false,
                   			boxLabel: '省级',
                   			name: 'm_is_province',
                   			id: 'm_is_province',
                   			handler: function(checkbox, checked){
                    				Ext.getCmp('m_prv_display_name').setDisabled(!checked);
                    				Ext.getCmp('m_prv_nextFormId_name').setDisabled(!checked);
                    				Ext.getCmp('m_b_prv').setDisabled(!checked);
                    			}
                			},{
                   			checked: false,
                   			boxLabel: '市级',
                   			name: 'm_is_city',
                   			id: 'm_is_city',
                   			handler: function(checkbox, checked){
                    				Ext.getCmp('m_city_display_name').setDisabled(!checked);
                    				Ext.getCmp('m_city_nextFormId_name').setDisabled(!checked);
                    				Ext.getCmp('m_b_city').setDisabled(!checked);
                    			}
                			}]               		
                		},{columnWidth : .1},{
                		columnWidth : .7,
                		layout : 'form',
                		labelWidth : 80,
                		items : [{
                			xtype : 'textfield',
                			id : 'm_prv_display_name',
                			fieldLabel : '省级显示别名',
                			value : '省级',
                			disabled : true
                   			},{
                			xtype : 'textfield',
                			id : 'm_city_display_name',
                			fieldLabel : '市级显示别名',
                			value : '市级',
                			disabled : true
                   			}] 
                		}]
                }]
 			},{columnWidth:.05,layout: 'form'},{
                columnWidth:.45,
                layout: 'form',
                labelWidth : 80,
                items: [{
                    xtype:'combo',
                    fieldLabel: '是否主页',
                    name: 'm_is_main_page',
                    id: 'm_is_main_page',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: staticStore,
                    width: '160'
                },{
					xtype : 'textfield',
					fieldLabel : '每行记录数',
					name : 'm_row_count',
					id : 'm_row_count',
					value : '1'
				},{
					xtype: 'compositefield',
					fieldLabel: '省级对应环节',
					items: [{
						xtype: 'textfield',
	                	name: 'm_prv_nextFormId_name',
	                  	id: 'm_prv_nextFormId_name',
					   	width: '160',
					  	//allowBlank:false, 
					 	readOnly: true,
					 	disabled : true
					    },{
					    xtype: 'button',
					    id : 'm_b_prv',
					    text: '..',
					    disabled : true,
					    handler: function(){selOper.selPrivInStatOper('prv');}
					}]
				},{
				    xtype: 'hidden',
				    name: 'm_prv_nextFormId',
				    id: 'm_prv_nextFormId',
				    value : '0'
				},{
					xtype: 'compositefield',
					fieldLabel: '市级对应环节',
					items: [{
						xtype: 'textfield',
	                	name: 'm_city_nextFormId_name',
	                  	id: 'm_city_nextFormId_name',
					   	width: '160',
					  	//allowBlank:false, 
					 	readOnly: true,
					 	disabled : true
					    },{
					   	xtype: 'button',
					    id : 'm_b_city',
					  	text: '..',
					  	disabled : true,
					 	handler: function(){selOper.selPrivInStatOper('city');}
					}]		
				},{
				    xtype: 'hidden',
				    name: 'm_city_nextFormId',
				    id: 'm_city_nextFormId',
				    value : '0'
				},{
					xtype:'combo',
                    fieldLabel: '最后一条',
                    name: 'm_is_sum',
                    id: 'm_is_sum',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: isSumStore,
                    width: '160'
				},{
                    xtype:'combo',
                    fieldLabel: '统计方式',
                    name: 'm_stat_type',
                    id: 'm_stat_type',
                    valueField: 'id',
                    displayField: 'value',
                    mode: 'local',
                    triggerAction: 'all',
                    editable : false ,
                    value: '1',
                    store: statTypeStore,
                    width: '160'
                }]
            },{columnWidth:.1,layout: 'form'}]}],
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
			    			var objq = new Object();
			    			objq.type = "selStatIsExit";
			    			objq.statCycle = infoPage.getCheckBoxValue('m_stat_cycle');
			    			objq.formId = selItem[0].data.formId;
			 				var tmpObj = invokeAction("/mobsystem/SelMobMuneAction", objq);
			 				if(tmpObj != ""){
							  	Ext.MessageBox.show({
						          	title: '提示',
							        msg: '该字段已存在，请重新指定！',
							        buttons: Ext.MessageBox.OK,
							        width:200,
							        icon: Ext.MessageBox.ERROR
							    });
								return;
							}
							
			    			var inputParams = new Object();
			    			inputParams.statCycle = infoPage.getCheckBoxValue('m_stat_cycle');
			    			inputParams.formId = selItem[0].data.formId;
			    			inputParams.isMainPage = Ext.getCmp("m_is_main_page").getValue();
                            inputParams.statRange = infoPage.getCheckBoxValue('m_stat_range');
                            inputParams.statType = Ext.getCmp("m_stat_type").getValue();
                            inputParams.statCycleDisplayName = Ext.getCmp("m_stat_cycle_display_name").getValue();
                            inputParams.prvDisplayName = Ext.getCmp("m_prv_display_name").getValue();
                            inputParams.cityDisplayName = Ext.getCmp("m_city_display_name").getValue();
                            inputParams.prvNextFormId = Ext.getCmp("m_prv_nextFormId").getValue();
                            inputParams.cityNextFormId = Ext.getCmp("m_city_nextFormId").getValue();
                            inputParams.rowCount = Ext.getCmp("m_row_count").getValue();
                            inputParams.isSum = Ext.getCmp("m_is_sum").getValue();
							inputParams.type = "add";
							var retMap = invokeAction("/mobsystem/InsertStatDisplayAction", inputParams);
			    			
			    			break ;
			    		}
			    		case "mod":{
			    			resultStr = '修改成功！';
		
			    			var inputParams = new Object();
			    			inputParams.statId = selGridItem[0].data.statId;
			    			inputParams.statCycle = infoPage.getCheckBoxValue('m_stat_cycle');
                            inputParams.isMainPage = Ext.getCmp("m_is_main_page").getValue();
                            inputParams.statRange = infoPage.getCheckBoxValue('m_stat_range');
                            inputParams.statType = Ext.getCmp("m_stat_type").getValue();
                            inputParams.statCycleDisplayName = Ext.getCmp("m_stat_cycle_display_name").getValue();
                            inputParams.prvDisplayName = Ext.getCmp("m_prv_display_name").getValue();
                            inputParams.cityDisplayName = Ext.getCmp("m_city_display_name").getValue();
                            inputParams.prvNextFormId = Ext.getCmp("m_prv_nextFormId").getValue();
                            inputParams.cityNextFormId = Ext.getCmp("m_city_nextFormId").getValue();
                            inputParams.rowCount = Ext.getCmp("m_row_count").getValue();
                            inputParams.isSum = Ext.getCmp("m_is_sum").getValue();
							inputParams.type = "mod";
							var retMap = invokeAction("/mobsystem/InsertStatDisplayAction", inputParams);
			    			
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
			        Ext.getCmp('statGrid').store.removeAll();
			        Ext.getCmp('statGrid').store.reload();
			    }}
	        },{
	            text: '取消',
	            listeners:{"click":function(){
			    	menuWin.close();
			    }}
	        }]
	    });
	    Ext.apply(infoPage, {
					// 获取 m_stat_cycle统计周期 或者 m_stat_range统计范围 内复选框值, 格式为1-0-1类似
					getCheckBoxValue : function(cmpId) {
						var returnValue = "";
						if (Ext.getCmp(cmpId) == null) {
							returnValue = '00';
						} else {
							var isFirst = true;
							var count = 0;
							while (true && count<4) {
								if (Ext.getCmp(cmpId).getComponent(count) != null) {
									if (Ext.getCmp(cmpId).getComponent(count).getValue()) {
										if(!isFirst){
											returnValue += '-';
										} else {
											isFirst = false;
										}
										returnValue += count+1;
									}
									count++;
								} else {
									break;
								}
							}
						}
						return returnValue;
					},
					// 设置 m_stat_cycle统计周期 或者 m_stat_range统计范围 内复选框值
					setCheckBoxValue : function(cmpId, resultString) {
						var checkArr = new Array();
						checkArr = resultString.split("-");
						for(var count in checkArr){
							if (Ext.getCmp(cmpId).getComponent(parseInt(checkArr[count])-1) != null) {
								Ext.getCmp(cmpId).getComponent(parseInt(checkArr[count])-1).setValue(true);
							}
						}
					}
				});
	    
		
		menuWin = new Ext.Window({
	        title: '流转管理',
		    closable:true,
		    width:650,
		    height:250,
		    plain:true, 
		    layout: 'border',
		    items: [infoPage]
		});
		
		menuWin.show(this);
		
		if(operator == 'mod'){
			infoPage.setCheckBoxValue("m_stat_cycle",selGridItem[0].data.statCycle);
			infoPage.setCheckBoxValue("m_stat_range",selGridItem[0].data.statRange);
			Ext.getCmp("m_is_main_page").setValue(selGridItem[0].data.isMainPage);
			Ext.getCmp("m_stat_type").setValue(selGridItem[0].data.statType);
            Ext.getCmp("m_stat_cycle_display_name").setValue(selGridItem[0].data.statCycleDisplayName);
           	Ext.getCmp("m_prv_display_name").setValue(selGridItem[0].data.prvDisplayName);
           	Ext.getCmp("m_city_display_name").setValue(selGridItem[0].data.cityDisplayName);
          	Ext.getCmp("m_prv_nextFormId").setValue(selGridItem[0].data.prvNextFormId);
          	Ext.getCmp("m_prv_nextFormId_name").setValue(selGridItem[0].data.prvNextFormIdName);
          	Ext.getCmp("m_city_nextFormId").setValue(selGridItem[0].data.cityNextFormId);
          	Ext.getCmp("m_city_nextFormId_name").setValue(selGridItem[0].data.cityNextFormIdName);
         	Ext.getCmp("m_row_count").setValue(selGridItem[0].data.rowCount);
         	Ext.getCmp("m_is_sum").setValue(selGridItem[0].data.isSum);
		}
	}	
	this.moduleDel = function(btn){
    	var selButGrid = Ext.getCmp('statGrid').getSelectionModel().getSelections();
    
		var paramObj = new Object();
  			paramObj.statId = selButGrid[0].data.statId;
			paramObj.type = "del";
			var retMap = invokeAction("/mobsystem/InsertStatDisplayAction", paramObj);
			 				
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
            Ext.example.msg('','删除菜单成功！');
        }, 1000);
        Ext.getCmp('statGrid').store.removeAll();
        Ext.getCmp('statGrid').store.reload();
    }

} 