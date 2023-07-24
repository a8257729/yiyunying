/////////////////////////////////////////////////////
// ZTESoft corp. 2006-02-20
// Author : Xu.fei3
// commits: Creates the workflow node bar
/////////////////////////////////////////////////////

function NodeBar(){
	this.panel = document.all.nodeBarDiv;
	this.panel.style.border = "1px solid #c0c0c0";
	
	//创建节点图标
	var iconBase = NODEBARICONBASE;
	var iconSuffix = "_small.gif";

	icons = new Array();
	
	for(var i=0; i<4; i++){
		icons[i] = document.createElement("img");
		icons[i].style.width = "16px";
		icons[i].style.height = "16px";
	}
	
	icons[0].src = iconBase + "mouse" + iconSuffix;
	icons[0].alt = "鼠标状态";
	
	icons[1].src = iconBase + "parallel" + iconSuffix;
	icons[1].alt = "并行结构";
	
	icons[2].src = iconBase + "control" + iconSuffix;
	icons[2].alt = "控制结构";
	
	icons[3].src = iconBase + "subflow" + iconSuffix;
	icons[3].alt = "子流程节点";
	
	//创建节点按钮
	buttons = new Array();
	
	for(var i=0; i<4; i++){
		buttons[i] = document.createElement("button");
		buttons[i].name = i;
		buttons[i].style.width="20px";
		buttons[i].style.height="20px";
		buttons[i].style.border="1px solid #ffffff";
		buttons[i].style.backgroundColor = "#ffffff";
		buttons[i].appendChild(icons[i]);
		
		this.panel.appendChild(buttons[i]);
	}
	
	//初始化事件
	for(var i=0; i<buttons.length; i++){
		buttons[i].onclick = Controller.nodeBarClick;
	}
	
	this.panel.appendChild(document.createElement("hr"));
	
	//保存按钮
	this.saveButton = document.createElement("button");
	this.saveButton.name = "Save";
	this.saveButton.style.width="20px";
	this.saveButton.style.height="20px";
	this.saveButton.style.border="1px solid #ffffff";
	this.saveButton.style.backgroundColor = "#ffffff";
	
	var saveIcon = document.createElement("img");
	saveIcon.style.width = "16px";
	saveIcon.style.height = "16px";
	saveIcon.src = iconBase + "save" + iconSuffix;
	saveIcon.alt = "保存";
	
	this.saveButton.appendChild(saveIcon);
	this.saveButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.saveButton);
	
	//校验按钮
	this.verifyButton = document.createElement("button");
	this.verifyButton.name = "Verify";
	this.verifyButton.style.width="20px";
	this.verifyButton.style.height="20px";
	this.verifyButton.style.border="1px solid #ffffff";
	this.verifyButton.style.backgroundColor = "#ffffff";
	
	var verifyIcon = document.createElement("img");
	verifyIcon.style.width = "16px";
	verifyIcon.style.height = "16px";
	verifyIcon.src = iconBase + "verify" + iconSuffix;
	verifyIcon.alt = "校验";
	
	this.verifyButton.appendChild(verifyIcon);
	this.verifyButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.verifyButton);
	
	//导出按钮
	this.exportButton = document.createElement("button");
	this.exportButton.name = "Export";
	this.exportButton.style.width="20px";
	this.exportButton.style.height="20px";
	this.exportButton.style.border="1px solid #ffffff";
	this.exportButton.style.backgroundColor = "#ffffff";
	
	var exportIcon = document.createElement("img");
	exportIcon.style.width = "16px";
	exportIcon.style.height = "16px";
	exportIcon.src = iconBase + "export" + iconSuffix;
	exportIcon.alt = "导出流程图";
	
	this.exportButton.appendChild(exportIcon);
	this.exportButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.exportButton);
	
	//查看XPDL按钮
	this.viewXPDLButton = document.createElement("button");
	this.viewXPDLButton.name = "ViewXPDL";
	this.viewXPDLButton.style.width="20px";
	this.viewXPDLButton.style.height="20px";
	this.viewXPDLButton.style.border="1px solid #ffffff";
	this.viewXPDLButton.style.backgroundColor = "#ffffff";
	
	var viewXPDLIcon = document.createElement("img");
	viewXPDLIcon.style.width = "16px";
	viewXPDLIcon.style.height = "16px";
	viewXPDLIcon.src = iconBase + "xpdl" + iconSuffix;
	viewXPDLIcon.alt = "查看XPDL" ;
	
	this.viewXPDLButton.appendChild(viewXPDLIcon);
	this.viewXPDLButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.viewXPDLButton);
	
	this.panel.appendChild(document.createElement("hr"));
	
	//设置流程参数按钮
	this.parameterButton = document.createElement("button");
	this.parameterButton.name = "Parameter";
	this.parameterButton.style.width="20px";
	this.parameterButton.style.height="20px";
	this.parameterButton.style.border="1px solid #ffffff";
	this.parameterButton.style.backgroundColor = "#ffffff";
	
	var parameterIcon = document.createElement("img");
	parameterIcon.style.width = "16px";
	parameterIcon.style.height = "16px";
	parameterIcon.src = iconBase + "parameter" + iconSuffix;
	parameterIcon.alt = "设置流程参数";
	
	this.parameterButton.appendChild(parameterIcon);
	this.parameterButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.parameterButton);
	
	//设置流程变量按钮
	this.variableButton = document.createElement("button");
	this.variableButton.name = "Variable";
	this.variableButton.style.width="20px";
	this.variableButton.style.height="20px";
	this.variableButton.style.border="1px solid #ffffff";
	this.variableButton.style.backgroundColor = "#ffffff";
	
	var variableIcon = document.createElement("img");
	variableIcon.style.width = "16px";
	variableIcon.style.height = "16px";
	variableIcon.src = iconBase + "variable" + iconSuffix;
	variableIcon.alt = "设置流程变量";
	
	this.variableButton.appendChild(variableIcon);
	this.variableButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.variableButton);
	
	//配置流程模板适用规则按钮
	this.ruleButton = document.createElement("button");
	this.ruleButton.name = "Rule";
	this.ruleButton.style.width="20px";
	this.ruleButton.style.height="20px";
	this.ruleButton.style.border="1px solid #ffffff";
	this.ruleButton.style.backgroundColor = "#ffffff";
	
	var ruleIcon = document.createElement("img");
	ruleIcon.style.width = "16px";
	ruleIcon.style.height = "16px";
	ruleIcon.src = iconBase + "rule" + iconSuffix;
	ruleIcon.alt = "配置适用规则";
	
	this.ruleButton.appendChild(ruleIcon);
	this.ruleButton.onclick = Controller.nodeBarClick;
	this.panel.appendChild(this.ruleButton);
}