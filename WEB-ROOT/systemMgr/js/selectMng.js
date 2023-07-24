function SelOper(){
    
    this.selectApkCode = function() {
       var selNodes =  window.showModalDialog("selectApkDialog.jsp",null,"dialogWidth=600px;dialogHeight=500px");


        var apkCode = "";
        if (selNodes != null && selNodes != undefined) {
            apkCode = selNodes[0].data.apkCode;
        }
        Ext.getCmp("m_apk_code").setValue(apkCode);        
    }
    
    this.selectOtherSys = function(gridId, pos0, component0, value0, pos1, component1, value1) {
       var selNodes =  window.showModalDialog("/MOBILE/systemMobMgr/selectOtherSysDialog.jsp",gridId,"dialogWidth=600px;dialogHeight=700px");


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
    
}