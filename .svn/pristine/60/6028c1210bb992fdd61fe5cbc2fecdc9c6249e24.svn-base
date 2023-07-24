function SelOper(){
    
    this.selePriv = function() {
       var selNodes =  window.showModalDialog("selectMobMune.jsp",null,"dialogWidth=600px;dialogHeight=500px");


        var formName = "";
        var teachName = "";
        var formId = "";
        if (selNodes != null && selNodes != undefined) {
            formName = selNodes[0].data.formName;
            teachName = selNodes[0].data.teachName;
            formId = selNodes[0].data.formId;
        }
        Ext.getCmp("m_to_page_name").setValue(formName);
        Ext.getCmp("m_to_page").setValue(teachName);
        Ext.getCmp("m_nextFormId").setValue(formId);
        
    }
    
    this.selPrivInStatOper = function(cmp){//selectMobMune selIndexMobMenu
    	var selNodes =  window.showModalDialog("selectMobMune.jsp",null,"dialogWidth=600px;dialogHeight=500px");

        var formName = "";
        var teachName = "";
        var formId = "";
        if (selNodes != null && selNodes != undefined) {
            formName = selNodes[0].data.formName;
            teachName = selNodes[0].data.teachName;
            formId = selNodes[0].data.formId;
        }
        
        Ext.getCmp("m_"+cmp+"_nextFormId_name").setValue(formName);
        Ext.getCmp("m_"+cmp+"_nextFormId").setValue(formId);
        
    }
    this.selFormIdInTransfer = function(){//selectMobMune selIndexMobMenu
    	var selNodes =  window.showModalDialog("selectMobMune.jsp",null,"dialogWidth=600px;dialogHeight=500px");

        var formName = "";
        var formId = "";
        if (selNodes != null && selNodes != undefined) {
            formName = selNodes[0].data.formName;
            formId = selNodes[0].data.formId;
        }
        
        Ext.getCmp("m_nextFormId_name").setValue(formName);
        Ext.getCmp("m_nextFormId").setValue(formId);
        
    }
    this.selIndexInTransfer = function(){//selectMobMune selIndexMobMenu
    	var selNodes =  window.showModalDialog("selIndexMobMenu.jsp",null,"dialogWidth=600px;dialogHeight=500px");

        var filedName = "";
        var filedId = "";
        if (selNodes != null && selNodes != undefined) {
            filedName = selNodes[0].data.filedName;
            filedId = selNodes[0].data.filedId;
        }
        
        Ext.getCmp("m_nextFiledId_name").setValue(filedName);
        Ext.getCmp("m_nextFiledId").setValue(filedId);
        
    }
    
    this.selectOtherSys = function(gridId, pos0, component0, value0, pos1, component1, value1) {
       var selNodes =  window.showModalDialog("selectOtherSysDialog.jsp",gridId,"dialogWidth=600px;dialogHeight=700px");


        if (selNodes != null && selNodes != undefined) {
        	if(pos0  != null && pos0 != undefined 
        		&&component0  != null && component0 != undefined 
        		&& value0  != null && value0 != undefined){
           		var v0 = eval("selNodes["+pos0+"][0].data."+value0+";");
        		Ext.getCmp(component0).setValue(v0);
        	}
        	if(pos1  != null && pos1 != undefined 
        		&&component1  != null && component1 != undefined 
        		&& value1  != null && value1 != undefined){
           		var v1 = eval("selNodes["+pos1+"][0].data."+value1+";");
        		Ext.getCmp(component1).setValue(v1);
        	}
        }
                
    }
    this.selectTeachName = function() {
       var selNodes =  window.showModalDialog("selectMobMune.jsp",null,"dialogWidth=600px;dialogHeight=500px");

        var teachName = "";
        if (selNodes != null && selNodes != undefined) {
            teachName = selNodes[0].data.teachName;
        }
        Ext.getCmp("v_to_page").setValue(teachName);
        
    }
    
    this.selectTeachName2 = function(component0) {
       var selNodes =  window.showModalDialog("selectMobMune.jsp",null,"dialogWidth=600px;dialogHeight=500px");

        var teachName = "";
        if (selNodes != null && selNodes != undefined) {
            teachName = selNodes[0].data.teachName;
        }
        Ext.getCmp(component0).setValue(teachName);
        
    }
    
    //////////////////////////add by guo.jinjun at 2012-05-11 10:00:35
    //	layer 代表着层次,0代表系统列表,1代表apk列表,2代表功能列表;
    //	pos代表数据所在的层,对应layer的值,从0开始;
    //	value就是值,component是存放value的控件id
    this.selectOtherApkMng = function(layer, pos0, component0, value0, pos1, component1, value1, pos2, component2, value2) {
       var selNodes =  window.showModalDialog("/MOBILE/systemMgr/otherApkMng.jsp",layer,"dialogWidth=700px;dialogHeight=500px");


        if (selNodes != null && selNodes != undefined) {
        	if(pos0  != null && pos0 != undefined 
        		&&component0  != null && component0 != undefined 
        		&& value0  != null && value0 != undefined){
           		var v0 = eval("selNodes["+pos0+"][0].data."+value0+";");
        		Ext.getCmp(component0).setValue(v0);
        	}
        	if(pos1  != null && pos1 != undefined 
        		&&component1  != null && component1 != undefined 
        		&& value1  != null && value1 != undefined){
           		var v1 = eval("selNodes["+pos1+"][0].data."+value1+";");
        		Ext.getCmp(component1).setValue(v1);
        	}
        	if(pos2  != null && pos2 != undefined 
        		&&component2  != null && component2 != undefined 
        		&& value2  != null && value2 != undefined){
           		var v2 = eval("selNodes["+pos2+"][0].data."+value2+";");
        		Ext.getCmp(component2).setValue(v2);
        	}
        }
                
    }
    
}
  

  
  