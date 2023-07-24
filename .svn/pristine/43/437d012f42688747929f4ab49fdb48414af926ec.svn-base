/////////////////////////////////////////////////////
// ZTESoft corp. 2006-11-23
// Author : Xu.fei3
// commits: Operations on flow tree
/////////////////////////////////////////////////////

_import("BSCommon");
//流程库的操作
function FlowOperation(){
	_extends(this, BSCommon, '"form1"');
	this.areaTree = document.all.areaTree;
	this.flowTree = document.all.flowLibrary;
	
	this.selectedFlow = this.flowTree.selectedItem;
	
	//这个菜单在鼠标右键点击空白区域的时候出来

	this.divMenu = new ContextMenu('空白区域菜单', 140);
	this.divMenu.addItem('新增流程目录', "flowOperation.addFlowCatalog();");
	this.divMenu.create();
	
	//这个菜单在鼠标右键点击流程目录的时候出来

	this.flowCatalogMenu = new ContextMenu('流程目录菜单', 140);
	this.flowCatalogMenu.addItem('新增流程目录', "flowOperation.addFlowCatalog();");
	this.flowCatalogMenu.addItem('新增流程子目录', "flowOperation.addFlowSubCatalog();");
	this.flowCatalogMenu.addItem('修改流程目录', "flowOperation.modifyFlowCatalog();");
	this.flowCatalogMenu.addItem('删除流程目录', "flowOperation.deleteFlowCatalog();");
	this.flowCatalogMenu.addItem("SEPARATOR");
	this.flowCatalogMenu.addItem('新增流程模板', "flowOperation.addFlowTmp();");
	this.flowCatalogMenu.create();
	
	//这个菜单在鼠标右键点击流程模板的时候出来
	this.flowTmpMenu = new ContextMenu('流程模板菜单', 140);
	this.flowTmpMenu.addItem('新增流程模板', "flowOperation.addFlowTmp();");
	this.flowTmpMenu.addItem('修改流程模板', "flowOperation.modifyFlowTmp();");
	this.flowTmpMenu.addItem('删除流程模板', "flowOperation.deleteFlowTmp();");
	this.flowTmpMenu.addItem('另存流程模板', "flowOperation.saveFlowTmpAs();");
	this.flowTmpMenu.addItem("SEPARATOR");
	this.flowTmpMenu.addItem('新增流程版本', "flowOperation.addVersion();");
	this.flowTmpMenu.addItem("SEPARATOR");
	this.flowTmpMenu.addItem('配置适用规则', "flowOperation.configRule();");
	this.flowTmpMenu.create();
	
	//这个菜单在鼠标右键点击流程版本的时候出来	
	this.flowVersionMenu = new ContextMenu('流程版本菜单', 150);
	var obj = callRemoteFunctionNoTrans("com.zterc.uos.client.ParameterManager","findParameter","is_con_flow_menu");			
	if(obj!=null && obj.value=="Y")
	{
		var session = GetSession();
		if (session.hasPriv("addFlowVersion")){
			this.flowVersionMenu.addItem('新增流程版本', "flowOperation.addVersion();");
		}

		this.flowVersionMenu.addItem('查看流程版本信息', "flowOperation.viewVersionInfo();");

		if (session.hasPriv("modifyFlowVersion")){
			this.flowVersionMenu.addItem('修改流程版本信息', "flowOperation.modifyVersionInfo();");
		}
		
		if (session.hasPriv("deleteFlowVersion")){
			this.flowVersionMenu.addItem('删除流程版本', "flowOperation.deleteVersion();");
		}

		if (session.hasPriv("saveFlowVersionAs")){
			this.flowVersionMenu.addItem('另存流程版本', "flowOperation.saveVersionAs();");
		}

		this.flowVersionMenu.addItem("SEPARATOR");

		this.flowVersionMenu.addItem('打开流程版本', "ExecWait('flowOperation.openVersion()')");

		if (session.hasPriv("designerDrawVersion")){
			this.flowVersionMenu.addItem('绘制流程版本', "ExecWait('flowOperation.drawVersion()')");
		}

		if (session.hasPriv("designerSaveVersion")){
			this.flowVersionMenu.addItem('保存流程版本', "flowOperation.saveVersion();");
		}
		
		if (session.hasPriv("designerForceVersionActive") || session.hasPriv("designerForceVersionExpire")){
			this.flowVersionMenu.addItem("SEPARATOR");
		}

		if (session.hasPriv("designerForceVersionActive")){
			this.flowVersionMenu.addItem('版本强制生效', "flowOperation.forceVersionEffect();");
		}

		if (session.hasPriv("designerForceVersionExpire")){
			this.flowVersionMenu.addItem('版本强制失效', "flowOperation.forceVersionExpire();");
		}
	}else{
		this.flowVersionMenu.addItem('新增流程版本', "flowOperation.addVersion();");
		this.flowVersionMenu.addItem('查看流程版本信息', "flowOperation.viewVersionInfo();");
		this.flowVersionMenu.addItem('修改流程版本信息', "flowOperation.modifyVersionInfo();");
		this.flowVersionMenu.addItem('删除流程版本', "flowOperation.deleteVersion();");
		this.flowVersionMenu.addItem('另存流程版本', "flowOperation.saveVersionAs();");
		this.flowVersionMenu.addItem("SEPARATOR");
		this.flowVersionMenu.addItem('打开流程版本', "ExecWait('flowOperation.openVersion()')");
		this.flowVersionMenu.addItem('绘制流程版本', "ExecWait('flowOperation.drawVersion()')");
		this.flowVersionMenu.addItem('保存流程版本', "flowOperation.saveVersion();");
		this.flowVersionMenu.addItem("SEPARATOR");
		this.flowVersionMenu.addItem('版本强制生效', "flowOperation.forceVersionEffect();");
		this.flowVersionMenu.addItem('版本强制失效', "flowOperation.forceVersionExpire();");
	}
	this.flowVersionMenu.create();
	
	//初始化，加载数据
	this.init = function(){
		var session = GetSession();
		//mod by 陈智堃 2010-06-23 UR-56729 不再使用登录人区域作为权限控制
		//var data = callRemoteFunction("com.zterc.uos.oaas.service.areamanager.AreaManagerWeb", "findSubAreaByJobXml", session.area.areaId, session.job.jobId, session.job.specialJobId);
		var data = callRemoteFunction("com.zterc.uos.oaas.service.areamanager.AreaManagerWeb", "findAreasXml");
		this.areaTree.loadByXML(data);
		
		//模拟点击一下

		if(this.areaTree.items.length > 0){
			this.areaClick();
		}
	}
	
	//在区域树上面的点击事件

	this.areaClick = function(){
		var areaId = this.areaTree.selectedItem.areaId;
		
		var data = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findTopPackageCatalogsXml", areaId);
		this.flowTree.loadByXML(data);
		
		for(var i=0; i<this.flowTree.items.length; i++){
			this.flowTree.items[i].setImage("./js/resources/catalog.gif");
		}
	}
	
	//流程树上面任意一个节点点击了以后做的事情
	this.flowClick = function(){
		//点击的位置没改变，就什么都不做
		switch(parseInt(this.flowTree.selectedItem.type)){
			//流程目录
			case CATALOG:{
				//目录下面可能有子目录和模板


				if((this.flowTree.selectedItem.items == null) || (this.flowTree.selectedItem.items.length == 0)){
					var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackagesByCatalogIdXml", this.flowTree.selectedItem.id);
					this.flowTree.selectedItem.insertByXML(xmlData);
					this.flowTree.selectedItem.expand(true);
		
					for(var i=0; i<this.flowTree.selectedItem.items.length; i++){
						if(this.flowTree.selectedItem.items[i].type == CATALOG){
							this.flowTree.selectedItem.items[i].setImage("./js/resources/catalog.gif");
						}else{
							this.flowTree.selectedItem.items[i].setImage("./js/resources/package.gif");
						}
					}
				}
				break;
			}
			//流程模板
			case TEMPLATE:{
				//模板下面有版本


				if((this.flowTree.selectedItem.items == null) || (this.flowTree.selectedItem.items.length == 0)){
					var xmlData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findDefinitionsByPackageIdXml", this.flowTree.selectedItem.id);
					this.flowTree.selectedItem.insertByXML(xmlData);
					this.flowTree.selectedItem.expand(true);
					
					for(var i=0; i<this.flowTree.selectedItem.items.length; i++){
						this.flowTree.selectedItem.items[i].setImage("./js/resources/version.gif");
					}
				}
				break;
			}
			//流程版本
			case VERSION:{
				//什么都不做
				break;
			}
		}
	}
	
	//流程树上面双击

	this.flowDoubleClick = function(){
		//点击的位置没改变，就什么都不做
		if(this.flowTree.selectedItem == this.selectedFlow){
			return;
		}
		
		//如果流程图处于编辑状态，应当首先提示用户保存
		if(Controller.editable){
			var param = new Object();
			param.content = '原流程处于编辑状态，是否保存？';
			
			var decide = OpenShowDlg("./dialog/YesNoCancelDialog.jsp", 180, 330, param);
			
			if(decide == null){
				//取消的情况，要恢复上次点击的位置
				this.selectedFlow.setSelected();
				return;
			}else if(decide == "YES"){
				//是，那么保存原来版本
				this.selectedFlow.setSelected();
				this.saveVersion();
				controller.clear();
			}else{
				//否，那么不保存


				Controller.editable = false;
				controller.clear();
			}
		}else{
			//不是可编辑状态，也要清除掉原来的图形
			controller.clear();			
		}

		this.selectedFlow = this.flowTree.selectedItem;
		switch(parseInt(this.flowTree.selectedItem.type)){
			//流程目录
			case CATALOG:{
				break;
			}
			//流程模板
			case TEMPLATE:{
				break;
			}
			//流程版本
			case VERSION:{
				this.openVersion();
				break;
			}
		}
	}
	
	//根据点击节点类型的不同来显示不同菜单
	this.showMenu = function(){
		if(this.flowTree.selectedItem != null){
			switch(parseInt(this.flowTree.selectedItem.type)){
				//流程目录
				case CATALOG:{
					this.flowCatalogMenu.popMenu();
					break;
				}
				//流程模板
				case TEMPLATE:{
					this.flowTmpMenu.popMenu();
					break;
				}
				//流程版本
				case VERSION:{
					this.flowVersionMenu.popMenu();
					break;
				}
			}
		}else{
			this.divMenu.popMenu();
		}
	}
	
	//隐藏菜单
	this.hideMenu = function(){
		this.flowCatalogMenu.hide();
		this.flowTmpMenu.hide();
		this.flowVersionMenu.hide();
	}
	
	//新增流程目录
	this.addFlowCatalog = function(){
		this.hideMenu();
		
		//这个对象用来存放新增目录的信息


		var obj = new Object();
		obj.operation = "add";
		obj.areaId = this.areaTree.selectedItem.areaId;
		
		if((this.flowTree.selectedItem==null) || (this.flowTree.selectedItem.getParentItem()==null)){
			obj.parentId = 0;
			obj.siblingNames = this.gatherChildrenName(this.flowTree, false, CATALOG);	
		}else{
			obj.parentId = this.flowTree.selectedItem.getParentItem().id;
			obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), false, CATALOG);
		}
		
		var result = OpenShowDlg("./dialog/flow/flowCatalog.jsp", 180, 350, obj);
		
		if(result != null){
			var newNode = this.flowTree.createTreeNode();
			
			//因为result是一个完整的catalog，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.catalogId;
			resultConvert.name = result.catalogName;
			resultConvert.type = CATALOG;
			
			newNode.clone(resultConvert);
			
			//根据判断，确定这个节点该放在根上面还是挂在某个节点上
			if(obj.parentId == 0){
				this.flowTree.add(newNode);
			}else{
				this.flowTree.selectedItem.getParentItem().add(newNode);
			}
			
			newNode.setImage("./js/resources/catalog.gif");

			//添加操作日志
			var log = "[AddFlowCatalog]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//新增流程子目录


	this.addFlowSubCatalog = function(){
		this.hideMenu();
		
		//子目录的父节点id为当前操作节点的id
		var obj = new Object();
		obj.operation = "add";
		obj.areaId = this.areaTree.selectedItem.areaId;
		obj.parentId = this.flowTree.selectedItem.id;
		obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem, false, CATALOG);
		
		var result = OpenShowDlg("./dialog/flow/flowCatalog.jsp", 180, 350, obj);
		
		if(result != null){
			//把新节点添加到树上


			var newNode = this.flowTree.createTreeNode();
			
			//因为result是一个完整的catalog，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.catalogId;
			resultConvert.name = result.catalogName;
			resultConvert.type = CATALOG;
			
			newNode.clone(resultConvert);
			this.flowTree.selectedItem.add(newNode);
			
			newNode.setImage("./js/resources/catalog.gif");

			//添加操作日志
			var log = "[AddFlowSubCatalog]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//修改流程目录
	this.modifyFlowCatalog = function(){
		this.hideMenu();
		
		var obj = new Object();
		obj.operation = "update";
		obj.areaId = this.areaTree.selectedItem.areaId;
		obj.catalogId = this.flowTree.selectedItem.id;
		
		if(this.flowTree.selectedItem.getParentItem() == null){
			obj.siblingNames = this.gatherChildrenName(this.flowTree, true, CATALOG);
		}else{
			obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), true, CATALOG);
		}
		
		var result = OpenShowDlg("./dialog/flow/flowCatalog.jsp", 180, 350, obj);
		
		if(result != null){
			//刷新节点，因为result是一个完整的catalog，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.catalogId;
			resultConvert.name = result.catalogName;
			resultConvert.type = CATALOG;
			
			this.flowTree.selectedItem.clone(resultConvert);
			this.flowTree.selectedItem.refresh();

			//添加操作日志
			var log = "[ModifyFlowCatalog]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//删除流程目录
	this.deleteFlowCatalog = function(){
		this.hideMenu();
		
		//删除的话，不需要额外弹出窗口，直接提示用户删除即可，但是要判断是否存在子节点


		if((this.flowTree.selectedItem.items != null) && (this.flowTree.selectedItem.items.length > 0)){
			ErrorHandle('流程目录不为空！');
			return;
		}
		
		if(confirm('是否确定要删除流程目录？')){
			var flowCatalogId = this.flowTree.selectedItem.id;
			callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "deletePackageCatalog", flowCatalogId);			
			this.flowTree.selectedItem.remove();

			//添加操作日志
			var log = "[DeleteFlowCatalog]id:" + flowCatalogId;
			this.addOperationLog(log);
		}
	}
	
	//新增流程模板模板
	this.addFlowTmp = function(){
		this.hideMenu();
		
		//这个对象用来存放新增目录的信息


		var obj = new Object();
		obj.operation = "add";
		
		//如果是点击在流程目录上面的操作，那么id是树上所选择的节点Id
		//如果是点击在流程模板上面的操作，那么id是树上所选择的节点的父节点的id，也就是流程目录的id
		var selItem = this.flowTree.selectedItem;
		
		if(selItem.type == CATALOG){
			obj.catalogId = selItem.id;
			obj.catalogName = selItem.name;
			obj.siblingNames = this.gatherChildrenName(selItem, false, TEMPLATE);
		}else{
			obj.catalogId = selItem.getParentItem().id;
			obj.catalogName = selItem.getParentItem().name;
			obj.siblingNames = this.gatherChildrenName(selItem.getParentItem(), false, TEMPLATE);
		}
		
		obj.ownerAreaId = this.areaTree.selectedItem.areaId;
		obj.ownerAreaName = this.areaTree.selectedItem.areaName;//add by liangli 2007.01.20 为了实现流程模板的跨域另存


		
		result = OpenShowDlg("./dialog/flow/flowTemplate.jsp", 240, 360, obj);
		
		if(result != null){
			var newNode = this.flowTree.createTreeNode();
			
			//因为result是一个完整的package，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.packageId;
			resultConvert.name = result.name;
			resultConvert.type = TEMPLATE;
			
			newNode.clone(resultConvert);
			
			//这个节点挂到树上
			if(selItem.type == CATALOG){
				selItem.add(newNode);
			}else{
				selItem.getParentItem().add(newNode);
			}
			
			newNode.setImage("./js/resources/package.gif");

			//添加操作日志
			var log = "[AddFlowTemplate]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//修改流程模板
	this.modifyFlowTmp = function(){
		this.hideMenu();
		var selItem = this.flowTree.selectedItem;
		var obj = new Object();
		obj.operation = "update";
		obj.catalogId = selItem.getParentItem().id;
		obj.catalogName = selItem.getParentItem().name;
		obj.packageId = selItem.id;
		obj.ownerAreaId = this.areaTree.selectedItem.areaId;
		obj.siblingNames = this.gatherChildrenName(selItem.getParentItem(), true, TEMPLATE);
		obj.ownerAreaName = this.areaTree.selectedItem.areaName;//add by liangli 2007.01.20 为了实现流程模板的跨域另存


		var result = OpenShowDlg("./dialog/flow/flowTemplate.jsp", 240, 360, obj);
		
		if(result != null ){
			//因为result是一个完整的package，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.packageId;
			resultConvert.name = result.name;
			resultConvert.type = TEMPLATE;
			
			selItem.clone(resultConvert);
			selItem.refresh();

			//添加操作日志
			var log = "[ModifyFlowTemplate]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//删除流程模板
	this.deleteFlowTmp = function(){
		this.hideMenu();
		
		//删除流程模板的时候，需要先判断模板下是否有激活状态的版本
		var selItems = this.flowTree.selectedItem.items;
		var selSize = selItems.length;
		if((selItems != null) && ( selSize > 0)){
			for(var i=0; i<selSize; i++){
				if(selItems[i].name.indexOf('激活') >= 0){
					ErrorHandle('该流程模板下存在激活的版本，不能删除！');
					return;
				}
			}
		}
		
		if(confirm('是否确定要删除流程模板？')){
			var flowTemplateId = this.flowTree.selectedItem.id;
			callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "deletePackage", flowTemplateId);			
			this.flowTree.selectedItem.remove();

			//添加操作日志
			var log = "[DeleteFlowTemplate]id:" + flowTemplateId;
			this.addOperationLog(log);
		}
	}
	
	//另存流程模板
	this.saveFlowTmpAs = function(){
		this.hideMenu();
		var selItem = this.flowTree.selectedItem;
		var selItems = selItem.items;
		var selSize = selItems.length;
		if((selItems == null)
			|| (selSize < 1)){
			ErrorHandle('该流程模板下没有激活的流程版本，不能另存！');
			return;
		}else{
			var hasEffectVersion = false;
			
			for(var i=0; i<selSize; i++){
				if(selItems[i].name.indexOf('激活') > 0){
					hasEffectVersion = true;
					break;
				}
			}
			
			if(!hasEffectVersion){
				ErrorHandle('该流程模板下没有激活的流程版本，不能另存！');
				return;
			}
		}
		
		var obj = new Object();
		obj.operation = "saveas";
		obj.catalogId = selItem.getParentItem().id;
		obj.packageId = selItem.id;
		obj.catalogName = selItem.getParentItem().name;
		obj.ownerAreaId = this.areaTree.selectedItem.areaId;
		obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), false, TEMPLATE);
		obj.ownerAreaName = this.areaTree.selectedItem.areaName;//add by liangli 2007.01.20 为了实现流程模板的跨域另存


		var result = OpenShowDlg("./dialog/flow/flowTemplate.jsp", 240, 360, obj);
		
		if(result != null && (result.areaId == obj.ownerAreaId || result.catalogId == obj.catalogId)){
			var newNode = this.flowTree.createTreeNode();
			
			//因为result是一个完整的package，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.packageId;
			resultConvert.name = result.name;
			resultConvert.type = TEMPLATE;
			
			newNode.clone(resultConvert);
			
			//这个节点挂到树上
			this.flowTree.selectedItem.getParentItem().add(newNode);
		
		  	
			newNode.setImage("./js/resources/package.gif");
			
			//加载这个模板下面复制过来的版本


			var versionsXml = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findDefinitionsByPackageIdXml", resultConvert.id);
			newNode.insertByXML(versionsXml);
			newNode.expand(true);	
			
			for(var i=0; i<newNode.items.length; i++){
				newNode.items[i].setImage("./js/resources/version.gif");
			}

			//添加操作日志
			var log = "[SaveFlowTemplateAs]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
		
	}
	
	//配置适用规则
	this.configRule = function(){
		this.hideMenu();
		
		var obj = new Object();
		obj.id = this.flowTree.selectedItem.id;
		obj.name = this.flowTree.selectedItem.name;
		obj.areaId = this.areaTree.selectedItem.areaId;
		OpenShowDlg("./dialog/flow/packageApplyRuleList.jsp", 420, 600, obj);
	}
	
	//新增流程版本
	this.addVersion = function(){
		this.hideMenu();
		
		//这个对象用来存放新增版本的信息


		var obj = new Object();
		obj.operation = "add";
		
		//如果是点击在流程模板上面的操作，那么id是树上所选择的节点Id
		//如果是点击在流程版本上面的操作，那么id是树上所选择的节点的父节点的id，也就是流程模板的id
		if(this.flowTree.selectedItem.type == TEMPLATE){
			obj.packageId = this.flowTree.selectedItem.id;
			obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem, false);
			obj.path = this.getPath(this.flowTree.selectedItem);
		}else{
			obj.packageId = this.flowTree.selectedItem.getParentItem().id;
			obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), false);
			obj.path = this.getPath(this.flowTree.selectedItem.getParentItem());
		}
		
		result = OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
		
		if(result != null){
			var newNode = this.flowTree.createTreeNode();
			
			//因为result是一个完整的流程定义，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.packageDefineId;
			resultConvert.name = result.version + "(" + result.stateName + ")";
			resultConvert.type = VERSION;
			
			newNode.clone(resultConvert);
			
			//这个节点挂到树上
			if(this.flowTree.selectedItem.type == TEMPLATE){
				this.flowTree.selectedItem.add(newNode);
			}else{
				this.flowTree.selectedItem.getParentItem().add(newNode);
			}
			
			newNode.setImage("./js/resources/version.gif");

			//添加操作日志
			var log = "[AddFlowVersion]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//绘制流程版本
	this.drawVersion = function(){
		this.hideMenu();
		
	// modified by lyh 090309 ur:37389 begin
		var session = GetSession();
		if (!session.hasPriv("drawExpiredVersion")){
			if(this.flowTree.selectedItem.name.indexOf('失效') > 0){
				ErrorHandle('不能修改失效版本的流程图形！');
				return;
			}
		}
		// modified by lyh 090309 ur:37389 end
		
		controller.activeTreeItem = this.flowTree.selectedItem;
		
		var xpdl = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "getXPDL", this.flowTree.selectedItem.id);
		
		controller.drawVersion(xpdl);
		
		var pointer = this.flowTree.selectedItem;
		this.setTitle(pointer, true);
		Controller.editable = true;
	}
	
	//打开流程版本
	this.openVersion = function(){
		this.hideMenu();
		
		controller.activeTreeItem = this.flowTree.selectedItem;
		
		var xpdl = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "getXPDL", this.flowTree.selectedItem.id);
		
		try{
			controller.openVersion(xpdl);
		}catch(e){
			ErrorHandle('读取失败，该流程版本可能尚未绘制！');
			return;
		}
	}
	
	//查看流程版本信息
	this.viewVersionInfo = function(){
		this.hideMenu();
		
		//这个对象用来存放修改版本的信息


		var obj = new Object();
		obj.packageId = this.flowTree.selectedItem.getParentItem().id;
		obj.packageDefineId = this.flowTree.selectedItem.id;
		obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), true);
		obj.path = this.getPath(this.flowTree.selectedItem.getParentItem());
		obj.operation = "view";
		
		OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
	}
	
	//修改流程版本信息
	this.modifyVersionInfo = function(){
		this.hideMenu();
		
		if(this.flowTree.selectedItem.name.indexOf('锁定') < 0){
			ErrorHandle('只能修改锁定版本的信息！');
			return;
		}
		
		//这个对象用来存放修改版本的信息


		var obj = new Object();
		obj.operation = "update";
		obj.packageId = this.flowTree.selectedItem.getParentItem().id;
		obj.packageDefineId = this.flowTree.selectedItem.id;
		obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), true);
		obj.path = this.getPath(this.flowTree.selectedItem.getParentItem());
		result = OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
		
		if(result != null){
			//因为result是一个完整的流程版本，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = result.packageDefineId;
			
			resultConvert.name = result.version + "(" + result.stateName + ")";
			resultConvert.type = VERSION;
			
			this.flowTree.selectedItem.clone(resultConvert);
			this.flowTree.selectedItem.refresh();

			//添加操作日志
			var log = "[ModifyFlowVersion]";
			for (var key in result){
				log += key + ":" + result[key];
			}
			this.addOperationLog(log);
		}
	}
	
	//删除流程版本
	this.deleteVersion = function(){
		this.hideMenu();
		
		if(this.flowTree.selectedItem.name.indexOf('激活') >= 0){
			ErrorHandle('不能删除处于激活状态的版本！');
			return;
		}
		
		//删除的话，不需要额外弹出窗口，直接提示用户删除即可
		if(confirm('是否确定要删除流程版本？')){
			//如果已经这个流程版本已经打开了，要先关闭掉


			for(var i=0; i<controller.allFlows.length; i++){
				if(this.flowTree.selectedItem.id == controller.allFlows[i].id){
					controller.allFlows.splice(i, 1);
					controller.activeFlow = null;
					
					controller.clear();
					Controller.editable = false;
					controller.refreshTabs();
					break;
				}
			}

			var flowVersionId = this.flowTree.selectedItem.id;
			callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "deleteProcessDefinition", flowVersionId);
			this.flowTree.selectedItem.remove();

			//添加操作日志
			var log = "[DeleteFlowVersion]id:" + flowVersionId;
			this.addOperationLog(log);
		}
	}
	
	//保存流程版本
	this.saveVersion = function(){
		this.hideMenu();
		
		if(!Controller.editable){
			ErrorHandle('当前不是编辑状态，不可保存！');
			return;
		}
		
		//校验不通过的时候，用户决定是否保存
		if(!controller.verify(false)){
			if(!confirm('流程校验不通过，是否保存？')){
				return;
			}
		}
		
		try{
			var result = null;
			
			//设置流程定义的一些信息


			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			
			// added by lyh 090309 ur:37389 begin
			 if(version.stateName.indexOf('失效') >= 0){
               if((controller.activeFlow.isStructureDirty)){ 
                    ErrorHandle('流程结构有变动，不可保存，请重新绘制流程');
                    return;
                  }
                     
               }
             // added by lyh 090309 ur:37389 end
			//如果是激活的版本，需要提示是否另存


			if(version.stateName.indexOf('激活') >= 0){	
				//如果流程的结构改变了，只好另存


				if((controller.activeFlow.isStructureDirty)){
					ErrorHandle('流程结构有变动，因此只能保存为新版本！');
					
					var obj = new Object();
					obj.operation = "saveas";
					obj.areaId = controller.activeFlow.areaId;
					obj.packageId = controller.activeFlow.packageId;
					obj.packageDefineId = controller.activeFlow.id;
					
					var versions = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryProDefByPackageId", obj.packageId);
					obj.siblingNames = new Array();
					for(var i=0; i<versions.length; i++){
						obj.siblingNames.push(versions[i].version);
					}

					obj.path = new Array();
					var title = workArea.getTitle();
					var pathArray = title.split(" / ").reverse();
					
					for(var i=2; i<pathArray.length; i++){
						var item = new Object();
						item.name = pathArray[i];
						obj.path.push(item);
					}
					
					obj.xpdl = controller.generateXPDL();
					
					result = OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
					
					//新建出来了


					if(result != null){
						var newNode = this.flowTree.createTreeNode();
						
						//因为result是一个完整的流程定义，而flowTree上面显示的属性名称不同，要转换一下


						var resultConvert = new Object();
						resultConvert.id = result.packageDefineId;
						resultConvert.name = result.version + "(" + result.stateName + ")";
						resultConvert.type = VERSION;
						
						newNode.clone(resultConvert);
						
						//这个节点挂到树上，这里可能另存到别的模板下了
						//在流程树上定位


						var found = false;						
						for(var i=0; i<this.flowTree.items.length; i++){
							if(this.flowTree.items[i].id == result.packageId){
								found = true;
								this.flowTree.items[i].setSelected(true);
								
								var nodePointer = this.flowTree.items[i];
								while(nodePointer != null){
									nodePointer.expand(true);
									nodePointer = nodePointer.getParentItem();
								}
								break;
							}
						}
						
						//没找到就不用在界面上加了
						if(found){
							this.flowTree.selectedItem.add(newNode);
							newNode.setImage("./js/resources/version.gif");
						}
						
						controller.activeFlow.isStructureDirty = false;
						controller.activeFlow.isDataDirty = false;

						//添加操作日志
						var log = "[SaveFlowVersionAs]id:" + resultConvert.id;
						this.addOperationLog(log);

						ErrorHandle('保存成功');
						return;
					}else{
						ErrorHandle('取消本次操作！');
						return;
					}
				}else{
					var param = new Object();
					param.content = '原流程处于激活状态，是否要另存一个版本？';
					
					var decide = OpenShowDlg("./dialog/YesNoCancelDialog.jsp", 180, 330, param);
					
					if(decide == null){
						ErrorHandle('取消本次操作！');
						return;
					}else if(decide == "YES"){
						//是，那么另存一个版本


						var obj = new Object();
						obj.operation = "saveas";
						obj.areaId = controller.activeFlow.areaId;
						obj.packageId = controller.activeFlow.packageId;
						obj.packageDefineId = controller.activeFlow.id;

						obj.path = new Array();
						var title = workArea.getTitle();
						var pathArray = title.split(" / ").reverse();
						
						for(var i=2; i<pathArray.length; i++){
							var item = new Object();
							item.name = pathArray[i];
							obj.path.push(item);
						}
						
						var versions = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "qryProDefByPackageId", obj.packageId);
						obj.siblingNames = new Array();
						for(var i=0; i<versions.length; i++){
							obj.siblingNames.push(versions[i].version);
						}
						
						obj.xpdl = controller.generateXPDL();
						
						result = OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
						
						//新建出来了


						if(result != null){
							var newNode = this.flowTree.createTreeNode();
							
							//因为result是一个完整的流程定义，而flowTree上面显示的属性名称不同，要转换一下


							var resultConvert = new Object();
							resultConvert.id = result.packageDefineId;
							resultConvert.name = result.version + "(" + result.stateName + ")";
							resultConvert.type = VERSION;
							
							newNode.clone(resultConvert);
							
							//这个节点挂到树上，这里可能另存到别的模板下了
							//在流程树上定位


							var found = false;						
							for(var i=0; i<this.flowTree.items.length; i++){
								if(this.flowTree.items[i].id == result.packageId){
									found = true;
									this.flowTree.items[i].setSelected(true);
									
									var nodePointer = this.flowTree.items[i];
									while(nodePointer != null){
										nodePointer.expand(true);
										nodePointer = nodePointer.getParentItem();
									}
									break;
								}
							}
							
							//没找到就不用在界面上加了
							if(found){
								this.flowTree.selectedItem.add(newNode);
								newNode.setImage("./js/resources/version.gif");
							}
							
							controller.activeFlow.isStructureDirty = false;
							controller.activeFlow.isDataDirty = false;

							//添加操作日志
							var log = "[SaveFlowVersionAs]id:" + resultConvert.id;
							this.addOperationLog(log);

							ErrorHandle('保存成功');
							return;
						}else{
							ErrorHandle('取消本次操作！');
							return;
						}
					}else{
						var xpdl = controller.generateXPDL();
						callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "setXPDL", controller.activeFlow.id, xpdl);
						
						//因为直接保存在激活的版本上面，所以要load一次


						callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "loadProcessDefinitionById", controller.activeFlow.id);
						
						controller.activeFlow.isStructureDirty = false;
						controller.activeFlow.isDataDirty = false;

						//添加操作日志
						var log = "[SaveFlowVersion]id:" + controller.activeFlow.id;
						this.addOperationLog(log);

						ErrorHandle('保存成功');
					}
				}
			}else{
				var xpdl = controller.generateXPDL();
				callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "setXPDL", controller.activeFlow.id, xpdl);
				
				controller.activeFlow.isStructureDirty = false;
				controller.activeFlow.isDataDirty = false;

				//添加操作日志
				var log = "[SaveFlowVersion]id:" + controller.activeFlow.id;
				this.addOperationLog(log);

				ErrorHandle('保存成功');
			}
		}catch(ex){
			ErrorHandle(ex);
		}
	}
	
	//另存流程版本
	this.saveVersionAs = function(){
		this.hideMenu();
		
		//设置流程定义的一些信息


		var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", this.flowTree.selectedItem.id);
		
		//这个对象用来存放新增版本的信息


		var obj = new Object();
		obj.operation = "saveas";
		obj.packageId = this.flowTree.selectedItem.getParentItem().id;
		obj.packageDefineId = this.flowTree.selectedItem.id;
		obj.siblingNames = this.gatherChildrenName(this.flowTree.selectedItem.getParentItem(), false);
		obj.path = this.getPath(this.flowTree.selectedItem.getParentItem());
		
		obj.xpdl = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "getXPDL", this.flowTree.selectedItem.id);;
		obj.areaId = this.areaTree.selectedItem.areaId;
		obj.areaName = this.areaTree.selectedItem.areaName;
		
		result = OpenShowDlg("./dialog/flow/flowVersion.jsp", 290, 360, obj);
		
		if(result != null){
			//添加操作日志
			var log = "[SaveFlowVersionAs]id:" + result.packageDefineId;
			this.addOperationLog(log);

			if(result.packageId == this.flowTree.selectedItem.getParentItem().id){
				var newNode = this.flowTree.createTreeNode();
				
				//因为result是一个完整的流程定义，而flowTree上面显示的属性名称不同，要转换一下


				var resultConvert = new Object();
				resultConvert.id = result.packageDefineId;
				resultConvert.name = result.version + "(" + result.stateName + ")";
				resultConvert.type = VERSION;
				
				newNode.clone(resultConvert);
				
				//这个节点挂到树上
				this.flowTree.selectedItem.getParentItem().add(newNode);
				newNode.setImage("./js/resources/version.gif");
			}else{
				//当前选中的流程模板不是新增这个版本的父模板，那么界面上要去找到那个模板，刷新一下


				for(var i=0; i<this.flowTree.items.length; i++){
					if((this.flowTree.items[i].type == TEMPLATE) && (result.packageId == this.flowTree.items[i].id)){
						var newNode = this.flowTree.createTreeNode();
						
						//因为result是一个完整的流程定义，而flowTree上面显示的属性名称不同，要转换一下


						var resultConvert = new Object();
						resultConvert.id = result.packageDefineId;
						resultConvert.name = result.version + "(" + result.stateName + ")";
						resultConvert.type = VERSION;
						
						newNode.clone(resultConvert);
						
						//这个节点挂到树上
						this.flowTree.items[i].add(newNode);
						newNode.setImage("./js/resources/version.gif");
						break;
					}
				}
			}
		}
	}
	
	//设置流程变量
	this.setFlowVariable = function(){
		var obj = new Object();
		obj.dataFields = workArea.dataFields;
		
		if(Controller.editable){
		 // modified by lyh 090309 ur:37389 begin
			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			if(version.stateName.indexOf('失效') >= 0){
				obj.operation = "view";
			}else{
				obj.operation = "update";
			}
		// modified by lyh 090309 ur:37389 end	
		}else if(controller.allNodes.length > 0){
			obj.operation = "view";
		}else{
			ErrorHandle('当前没有打开的版本，不能设置流程变量！');
			return;
		}
		
		var result = OpenShowDlg("./dialog/flow/flowVariable.jsp", 240, 500, obj);
		
		if(result != null){
			workArea.dataFields = result.dataFields;
			controller.activeFlow.isDataDirty = true;
		}
	}
	
	//设置流程参数
	this.setFlowParameter = function(){
		var obj = new Object();
		obj.formalParameters = workArea.formalParameters;
		
		if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			if(version.stateName.indexOf('失效') >= 0){
				obj.operation = "view";
			}else{
				obj.operation = "update";
			}
		// modified by lyh 090309 ur:37389 end	
		}else if(controller.allNodes.length > 0){
			obj.operation = "view";
		}else{
			ErrorHandle('当前没有打开的版本，不能设置流程参数！');
			return;
		}
		
		var result = OpenShowDlg("./dialog/flow/flowParameter.jsp", 240, 500, obj);
		
		if(result != null){
			workArea.formalParameters = result.formalParameters;
			controller.activeFlow.isDataDirty = true;
		}
	}
	
	//版本强制生效
	this.forceVersionEffect = function(){
		this.hideMenu();
		
		this.selectedFlow = this.flowTree.selectedItem;
		
		var xpdl = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "getXPDL", this.flowTree.selectedItem.id);
		if((xpdl == null) || (xpdl == "")){
			ErrorHandle('该流程版本尚未绘制流程，不能被激活！');
			return;
		}
		
		if(this.flowTree.selectedItem.name.indexOf('锁定') < 0){
			alert('只能激活处于锁定状态的版本！');
			return;
		}
		
		//下面这一段取得原来处于激活状态的版本
		var parentNode = this.flowTree.selectedItem.getParentItem();
		var oldEffectVersion = null;
		for(var i=0; i<parentNode.items.length; i++){
			if(parentNode.items[i].name.indexOf('激活') > 0){
				oldEffectVersion = parentNode.items[i];
				break;
			}
		}
		
		//原来有没有激活版本，提示是不一样的
		if(oldEffectVersion == null){
			if(!confirm('确定要激活流程？')){
				return;
			}
		}else{
			if(!confirm('确定要激活流程？该模板下原处于激活状态的流程版本将会被强制失效!')){
				return;
			}
		}
		
		try{
			//先把原来模板下激活状态的版本失效掉


			if(oldEffectVersion != null){
				oldEffectVersion.setSelected();
				
				var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", this.flowTree.selectedItem.id);
				
				var newVersion;
				
				newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "disableProcessDefinition", version);
				
				if(newVersion.state == "10C"){
					//因为newVersion是一个完整的版本，而flowTree上面显示的属性名称不同，要转换一下


					var resultConvert = new Object();
					resultConvert.id = version.packageDefineId;
					resultConvert.name = version.version + "(" + newVersion.stateName + ")";
					resultConvert.type = VERSION;
					
					this.flowTree.selectedItem.clone(resultConvert);
					this.flowTree.selectedItem.refresh();
					
					//更新已经打开的流程版本的标题
					for(var i=0; i<controller.allFlows.length; i++){
						if(this.flowTree.selectedItem.id == controller.allFlows[i].id){
							controller.allFlows[i].name = this.flowTree.selectedItem.name;
							//已经失效了，就不再可以编辑了
							controller.allFlows[i].editable = false;
							controller.refreshTabs();
							controller.allFlows[i].title = controller.allFlows[i].title.replace(/"激活"/g, '失效');
							controller.allFlows[i].title = controller.allFlows[i].title.replace(/"锁定"/g, '失效');
							break;
						}
					}
					
					//流程标题更改
					if(controller.activeFlow!=null && (controller.activeFlow.id == this.flowTree.selectedItem.id)){
						this.setTitle(this.flowTree.selectedItem, controller.activeFlow.editable);
					}
				}
			}
			
			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", this.selectedFlow.id);
			
			var newVersion;
			//下面开始激活


			this.selectedFlow.setSelected();
			newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "enableProcessDefinition", version);
			
			//因为newVersion是一个完整的版本，而flowTree上面显示的属性名称不同，要转换一下


			var resultConvert = new Object();
			resultConvert.id = version.packageDefineId;
			resultConvert.name = version.version + "(" + newVersion.stateName + ")";
			resultConvert.type = VERSION;
			
			this.flowTree.selectedItem.clone(resultConvert);
			this.flowTree.selectedItem.refresh();
			
			//更新已经打开的流程版本的标题
			for(var i=0; i<controller.allFlows.length; i++){
				if(this.flowTree.selectedItem.id == controller.allFlows[i].id){
					controller.allFlows[i].name = this.flowTree.selectedItem.name;
					controller.allFlows[i].title = controller.allFlows[i].title.replace(/"锁定"/g, '激活');
					controller.refreshTabs();

					break;
				}
			}

			//流程标题更改
			if((controller.activeFlow != null) && (controller.activeFlow.id == this.flowTree.selectedItem.id)){
				this.setTitle(this.flowTree.selectedItem, controller.activeFlow.editable);
			}

			//添加操作日志
			var log = "[ActiveFlowVersion]id:" + version.packageDefineId;
			this.addOperationLog(log);

			ErrorHandle('版本已激活！');
		}catch(ex){
			ErrorHandle(ex);
		}
	}
	
	//版本强制失效
	this.forceVersionExpire = function(){
		this.hideMenu();

		if(this.flowTree.selectedItem.name.indexOf('失效') > 0){
			ErrorHandle('该版本已经是失效状态了！');
			return;
		}
		
		if(!confirm('确定要使得该流程强制失效？')){
			return;
		}
		
		var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", this.flowTree.selectedItem.id);
		
		var newVersion;
		
		try{
			newVersion = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "disableProcessDefinition", version);
			
			if(newVersion.state == "10C"){
				//因为newVersion是一个完整的版本，而flowTree上面显示的属性名称不同，要转换一下


				var resultConvert = new Object();
				resultConvert.id = version.packageDefineId;
				resultConvert.name = version.version + "(" + newVersion.stateName + ")";
				resultConvert.type = VERSION;
				
				this.flowTree.selectedItem.clone(resultConvert);
				this.flowTree.selectedItem.refresh();
				
				//更新已经打开的流程版本的标题
				for(var i=0; i<controller.allFlows.length; i++){
					if(this.flowTree.selectedItem.id == controller.allFlows[i].id){
						controller.allFlows[i].name = this.flowTree.selectedItem.name;
						//已经失效了，就不再可以编辑了
						controller.allFlows[i].editable = false;
						controller.allFlows[i].title = controller.allFlows[i].title.replace(/"激活"/g, '失效');
						controller.allFlows[i].title = controller.allFlows[i].title.replace(/"锁定"/g, '失效');
						controller.refreshTabs();
						break;
					}
				}

				//流程标题更改
				if((controller.activeFlow != null) && (controller.activeFlow.id == this.flowTree.selectedItem.id)){
					this.setTitle(this.flowTree.selectedItem, controller.activeFlow.editable);
				}

				//添加操作日志
				var log = "[DeactiveFlowVersion]id:" + newVersion.packageDefineId;
				this.addOperationLog(log);

				ErrorHandle('版本已强制失效！');
			}else{
				ErrorHandle('版本强制失效失败！');
			}
		}catch(ex){
			ErrorHandle(ex);
		}
	}
	
	//高级查询
	this.queryFlowTmp = function(){
		var obj = GetSession();		
		var result = OpenShowDlg("./dialog/flow/flowQuery.jsp", 550, 580, obj);
		
		//在流程树上定位
		
		if(result){
			result.areaFlag="1";
			this.gotoTemp(result);
		}
	}

	//查询流程模板
	this.qryFlow = function(){
		var tempService = "";
		var serviceName = "";
		var prodName = "";
		var objData;
		var transData = new Array();
		var qryObj = new Object();
		var tempArea = document.all.areaTree.selectedItem.areaId;

		if(this.form.majorVersionNum.value < 0){
			ErrorHandle('主版本号必须是大于0的整数>');
			return;
		}

		if(this.form.minorVersionNum.value < 0 || this.form.minorVersionNum.value > 9){
			ErrorHandle('次版本号必须是0到9之间的整数');
			return;
		}
		var versionStr = (this.form.majorVersionNum.value * 10 / 10) + "." + (this.form.minorVersionNum.value * 10 / 10);
		qryObj.serviceName = document.all.serviceNameInput.value || null;
		qryObj.prodName = document.all.prodNameInput.value || null;
		if(this.form.majorVersionNum.value!="0")qryObj.comSrv = versionStr || null;
		
		if(qryObj.prodName==null && qryObj.serviceName==null && this.form.majorVersionNum.value=="0"){
			this.areaClick();
			return;
		}
		var serviceIds = callRemoteFunctionNoTrans("com.ztesoft.iom.product.ProductManager", "qryService", qryObj);
		if(serviceIds!=null && serviceIds.length>0){
			tempService =serviceIds.toString();
		}
		if(tempService!=null && tempService.length>0)
		objData = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient",	"findProcessByAreaIdServiceIds",tempService,tempArea);
		if(objData == null){
		  		ErrorHandle('没有匹配结果！',3);
	      }
	      else if(objData.length == 0){
		  		ErrorHandle('没有匹配结果！',3);
	      }else{
			var processDefDto = null;
			var tempPackage = new Array();
			var tempDto = null;
			for(var i=0;i<objData.length;i++){
				tempDto = objData[i];
				
				processDefDto = new Object();
				processDefDto.packageCatalogName = tempDto.catalogName;
				processDefDto.packageName = tempDto.name ;
				processDefDto.processDefinitionVersion = tempDto.version ;
				processDefDto.processDefinitionId = tempDto.packageId ;
				processDefDto.areaId = tempDto.areaId ;
				processDefDto.pathCode = tempDto.pathCode ;
				
				if(i==0){
					processDefDto.areaFlag="1";
					transData[transData.length] = processDefDto;
					tempPackage[i] = processDefDto.packageCatalogName;
				}else{
					var addFalg = false;
					for(var t in tempPackage){
						if(tempPackage[t]==processDefDto.packageCatalogName){
							addFalg = true;
						}
				    }
					if(!addFalg){
						transData[transData.length] = processDefDto;
						tempPackage[i] = processDefDto.packageCatalogName;
					}
				}
				
				
			 }
		  }
		for(var j =0;j<transData.length;j++){
			this.gotoTemp(transData[j]);
		}
	}

	this.addMajor = function(amount){
		var majorVersionNum1;
		try{
			majorVersionNum1 = parseInt(this.form.majorVersionNum.value);
			majorVersionNum1 += amount;
			if(majorVersionNum1 < 0){
				majorVersionNum1 = 0;
			}
			this.form.majorVersionNum.value = majorVersionNum1;
		}catch(e){
			ErrorHandle(e);
		}
	}

	//\u589e\u52a0\u6b21\u7248\u672c\u53f7
	this.addMinor = function(amount){
		var minorVersionNum1;
		try{
			minorVersionNum1 = parseInt(this.form.minorVersionNum.value);
			minorVersionNum1 += amount;
			if(minorVersionNum1 < 0){
				minorVersionNum1 = 9;
			}
			if(minorVersionNum1 > 9){
				minorVersionNum1 = 0;
			}
			this.form.minorVersionNum.value = minorVersionNum1;
		}catch(e){
			ErrorHandle('次版本号必须是0到9之间的整数');
		}
	}

	/*根据流程模板在树上定位*/ 
	this.gotoTemp = function(packageDto){
		result = packageDto;
		//在流程树上定位


		if(result != null){
			//先定位区域
			
			if(result.areaFlag=='1'){
				for(var i=0; i<this.areaTree.items.length; i++){
					if(this.areaTree.items[i].areaId == result.areaId){
						this.areaTree.items[i].setSelected(true);
						this.areaClick();
						break;
					}
				}
			}
			
			//定位目录
			var path = result.pathCode.split(".");
			var nodePointer = this.flowTree;
			for(var i=0; i<path.length; i++){
				for(var j=0; j<nodePointer.items.length; j++){
					if(nodePointer.items[j].id == path[i]){
						nodePointer = nodePointer.items[j];
						break;
					}
				}
				nodePointer.setSelected(true);
				this.flowClick();
			}

			//目录都已经展开了，现在是模板定位


			for(var i=0; i<nodePointer.items.length; i++){
				if(nodePointer.items[i].name == result.packageName){
					nodePointer = nodePointer.items[i];
					nodePointer.setSelected(true);
					break;
				}
			}
			this.flowClick();
		}
		
	}
	
	
	
	//收集子节点名字，返回类型为数组，用来校验重名，noSelect参数用来设置是否过滤树上的选中节点
	//filterType用来选择过滤节点类型，当同级有目录跟模板的时候用
	this.gatherChildrenName = function(node, noSelect, filterType){
		var childrenNames = new Array();
		
		if(node == this.flowTree){
			for(var i=0; i<this.flowTree.rootItems.length; i++){
				if(noSelect){
					if(this.flowTree.rootItems[i] != this.flowTree.selectedItem){
						childrenNames.push(this.flowTree.rootItems[i].name);
					}
				}else{
					childrenNames.push(this.flowTree.rootItems[i].name);
				}
			}
		}else if(node.items != null){
			//如果是流程模板，那么下属的是流程版本，取名字的时候应当把状态去掉


			if(node.type == TEMPLATE){
				for(var i=0; i<node.items.length; i++){
					if(noSelect){
						if(node.items[i] != this.flowTree.selectedItem){
							var name = node.items[i].name.substr(0, node.items[i].name.length-4);
							childrenNames.push(name);
						}
					}else{
						var name = node.items[i].name.substr(0, node.items[i].name.length-4);
						childrenNames.push(name);
					}
				}
			}else{
				for(var i=0; i<node.items.length; i++){
					if((filterType == CATALOG) && (node.items[i].type == CATALOG)){
						if(noSelect){
							if(node.items[i] != this.flowTree.selectedItem){
								childrenNames.push(node.items[i].name);
							}
						}else{
							childrenNames.push(node.items[i].name);
						}
					}else if((filterType == TEMPLATE) && (node.items[i].type == TEMPLATE)){
						if(noSelect){
							if(node.items[i] != this.flowTree.selectedItem){
								childrenNames.push(node.items[i].name);
							}
						}else{
							childrenNames.push(node.items[i].name);
						}
					}
				}
			}
		}
		return childrenNames;
	}
	
	//收集流程模板的完整路径


	this.getPath = function(node){
		var path = new Array();
		
		var pointer = node;
		while(pointer.getParentItem() != null){
			path.push(pointer);
			pointer = pointer.getParentItem();
		}
		path.push(pointer);
		
		return path;
	}
	
	//设置工作区的标题
	this.setTitle = function(pointer, editable){
		if(pointer == null){
			workArea.setTitle('未命名版本');
			return;
		}
		
		var title = pointer.name;
		while(pointer.getParentItem() != null){
			pointer = pointer.getParentItem();
			title = pointer.name + " / " + title;
		}
		
		if(editable){
			title += '/ 动作：编辑';
		}else{
			title += '/ 动作：查看';
		}
		workArea.setTitle(title);
	}
	
	//从session取得areaId
	this.getAreaId = function(){
		var session = GetSession();
		var area = session.area;
		return area.areaId;
	}

	//记录操作日志
	this.addOperationLog = function(content){
		var session = GetSession();
		callRemoteFunction("com.ztesoft.iom.funcmanager.bl.FuncManager", "addDesignerLog", session.staff.staffId, content);
	}
}