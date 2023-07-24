/////////////////////////////////////////////////////
// ZTESoft corp. 2006-11-29
// Author : Xu.fei3
// commits: Controller module of the designer
/////////////////////////////////////////////////////

function Controller(){
	//指向界面上的列表
	this.flowTree = document.all.flowLibrary;
	this.tacheTree = document.all.tacheLibrary;
	this.tabs = document.all.flowTabs;
	this.tacheTree.onItemClick = Controller.tacheTreeClick;
	
	//用来寄存事件源
	this.eventElement = null;
	
	//当前活动的控制节点
	this.activeNode = null;
	
	//当前打开的流程
	this.activeFlow = null; 
	
	//当前流程树上面的选中节点
	this.activeTreeItem = null;
	
	//不论什么节点、线条，不论在什么层次，都在这里保存一份引用，供事件处理的时候查找事件源
	this.allNodes = new Array();
	this.allLines = new Array();
	this.allFlows = new Array();
	
	//这里是右键菜单
	//这个菜单进行节点的操作
	this.nodeMenu = new ContextMenu("节点操作>", 140);
	this.nodeMenu.addItem("查看节点属性", "controller.viewNodeProperty();");
	this.nodeMenu.addItem("设置节点属性", "controller.setNodeProperty();");
	this.nodeMenu.addItem("设置节点异常", "controller.setNodeException();");
	this.nodeMenu.addItem("SEPRATOR");
	//-----ur31747-------add by yang.kai2---------2008-10-07----------
	this.nodeMenu.addItem("派单规则配置","controller.dispatchRuleConfig()");
	this.nodeMenu.addItem("同步规则配置","controller.synchronousRuleConfig()");
	this.nodeMenu.addItem("SEPRATOR");
	//--------------------------------------------------------------
	this.nodeMenu.addItem("删除节点", "controller.removeNode();");
	this.nodeMenu.create();
	
	//查看状态下的节点操作
	this.nodeViewMenu = new ContextMenu("节点查看操作", 140);
	this.nodeViewMenu.addItem("查看节点属性", "controller.viewNodeProperty();");
	this.nodeViewMenu.create();
	
	//这个菜单进行异常节点的操作
	this.exceptionMenu = new ContextMenu("异常节点操作", 140);
	this.exceptionMenu.addItem("查看异常属性", "controller.viewExceptionProperty();");
	this.exceptionMenu.addItem("设置异常属性", "controller.setExceptionProperty();");
	this.exceptionMenu.create();
	
	//这个菜单进行异常节点的查看操作
	this.exceptionViewMenu = new ContextMenu("异常节点查看操作", 140);
	this.exceptionViewMenu.addItem("查看异常属性", "controller.viewExceptionProperty();");
	this.exceptionViewMenu.create();
	
	//这个菜单进行并行节点的操作
	this.parallelMenu = new ContextMenu("并行节点操作", 150);
	this.parallelMenu.addItem("查看并行节点属性", "controller.viewParallelProperty();");
	this.parallelMenu.addItem("设置并行节点属性", "controller.setParallelProperty();");
	this.parallelMenu.addItem("SEPRATOR");
	this.parallelMenu.addItem("删除并行节点", "controller.removeNode();");
	this.parallelMenu.create();
	
	//这个菜单进行查看并行节点的操作
	this.parallelViewMenu = new ContextMenu("并行节点查看操作", 150);
	this.parallelViewMenu.addItem("查看并行节点属性", "controller.viewParallelProperty();");
	this.parallelViewMenu.create();
	
	//这个菜单进行控制节点的操作
	this.controlMenu = new ContextMenu("控制节点操作", 150);
	this.controlMenu.addItem("查看控制节点属性", "controller.viewControlNodeProperty();");
	this.controlMenu.addItem("设置控制节点属性", "controller.setControlNodeProperty();");
	this.controlMenu.addItem("SEPRATOR");
	this.controlMenu.addItem("删除控制节点", "controller.removeNode();");
	this.controlMenu.create();
	
	//这个菜单进行控制节点的查看操作
	this.controlViewMenu = new ContextMenu("控制节点查看操作", 150);
	this.controlViewMenu.addItem("查看控制节点属性", "controller.viewControlNodeProperty();");
	this.controlViewMenu.create();
	
	//这个菜单进行分支的操作
	this.branchMenu = new ContextMenu("分支操作", 140);
	this.branchMenu.addItem("删除分支", "controller.removeBranch();");
	this.branchMenu.create();
	
	//这个菜单进行子流程节点的操作
	this.subFlowMenu = new ContextMenu("子流程节点操作", 140);
	this.subFlowMenu.addItem("删除子流程", "controller.removeNode();");
	this.subFlowMenu.create();
	
	//查看状态下的子流程节点操作
	this.subFlowViewMenu = new ContextMenu("子流程节点查看操作", 140);
	this.subFlowViewMenu.create();
	
	//这个菜单进行线条的操作
	this.lineMenu = new ContextMenu("线条操作", 140);
	this.lineMenu.addItem("查看线条属性", "controller.viewLineProperty();");
	this.lineMenu.addItem("设置线条属性", "controller.setLineProperty();");
	this.lineMenu.create();
	
	//这个菜单进行线条的查看操作
	this.lineViewMenu = new ContextMenu("线条查看操作", 150);
	this.lineViewMenu.addItem(">查看线条属性", "controller.viewLineProperty();");
	this.lineViewMenu.create();
	
	//这个菜单进行控制线条的操作
	this.controlLineMenu = new ContextMenu("控制线条操作", 160);
	this.controlLineMenu.addItem("查看控制线条属性", "controller.viewControlLineProperty();");
	this.controlLineMenu.addItem("设置控制线条属性", "controller.setControlLineProperty();");
	this.controlLineMenu.addItem("SEPRATOR");
	this.controlLineMenu.addItem("删除控制线条", "controller.removeControlLine();");
	this.controlLineMenu.create();
	
	//这个菜单进行控制线条的查看操作
	this.controlLineViewMenu = new ContextMenu("控制线条查看操作", 160);
	this.controlLineViewMenu.addItem("查看控制线条属性", "controller.viewControlLineProperty();");
	this.controlLineViewMenu.create();
	
	//这个菜单进行并行节点分支合并条件的操作
	this.relationMenu = new ContextMenu("合并条件操作", 160);
	this.relationMenu.addItem("查看合并条件属性", "controller.viewRelationProperty();");
	this.relationMenu.addItem("设置合并条件属性", "controller.setRelationProperty();");
	this.relationMenu.create();
	
	//这个菜单进行查看并行节点分支合并条件的操作
	this.relationViewMenu = new ContextMenu("合并条件查看操作", 160);
	this.relationViewMenu.addItem("查看合并条件属性", "controller.viewRelationProperty();");
	this.relationViewMenu.create();
	
	//初始化
	this.init = function(){
		workArea.clear();
		workArea.setTitle("未命名版本");
		this.allNodes = new Array();
		this.allLines = new Array();
		workArea.initialize();
	}
	
	//清除
	this.clear = function(){
		workArea.clear();
		workArea.setTitle("");
		this.allNodes = new Array();
		this.allLines = new Array();
	}
	
	//刷新标签页
	this.refreshTabs = function(){
		var index = 0;
		for(var i=0; i<this.allFlows.length; i++){
			if(this.allFlows[i] == this.activeFlow){
				index = i;
				break;
			}
		}
		
		var tabStr = "<ZTEsoft:tabStrip class='tab_ioms' selectedIndex='" + index + "'>"
			+ "<ZTEsoft:tabs width='100%' height='0'>";
		
		if(this.allFlows.length > 0){
			for(var i=0; i<this.allFlows.length; i++){
				tabStr += "<ZTEsoft:page Text=\"" + this.allFlows[i].name
					+ "<input type='image' src='./js/resources/close.gif' style='border:none; width:10px; height:10px; background:transparent;' "
					+ "onclick='Controller.closeVersion(" + i + ")' />"
					+ "\" onclick='Controller.switchVersion(" + i + ")'>"
					+ "</ZTEsoft:page>";
			}
		}else{
			tabStr += "<ZTEsoft:page Text='" + "当前没有打开的流程" + "'></ZTEsoft:page>";
		}
		
		tabStr += "</ZTEsoft:tabs></ZTEsoft:tabstrip>";
		this.tabs.innerHTML = tabStr;
		
		Controller.switchVersion(index);
	}
	
	//删除并行节点上面的一个分支
	this.removeBranch = function(){
		this.branchMenu.hide();
		workArea.showInsertPosition(false);
		
		if(!Controller.editable){
			return;
		}
		
		if(!window.confirm("该分支的下属节点都将被删除，确认要删除分支吗？")){
			return;
		}
		
		var src = this.eventElement;
		var srcNode = null;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(srcNode != null){
					break;
				}
				
				for(var j=0; j<this.allNodes[i].branches.length; j++){
					if(src == this.allNodes[i].branches[j].icon){
						srcNode = this.allNodes[i];
						branchIndex = j;
						break;
					}
				}
			}
		}
		
		if(srcNode.branches.length <= 1){
			alert("并行结构上至少要保留一个分支！");
		}else{
			srcNode.removeBranch(branchIndex);
			this.activeFlow.isStructureDirty = true;
			workArea.repaint();
			workArea.repaint();
			workArea.repaint();
		}
	}
	
	//删除一个节点
	this.removeNode = function(){
		this.nodeMenu.hide();
		this.controlMenu.hide();
		this.parallelMenu.hide();
		this.subFlowMenu.hide();
		
		workArea.showInsertPosition(false);
		var src = this.eventElement;
		
		var srcNode = null;
		
		//先找到这个节点
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
				break;
			}
		}
		
		if((srcNode.getNodeType()=="Start")||(srcNode.getNodeType()=="Finish")||(srcNode.getNodeType()=="Exception")){
			alert("本节点不允许删除");
			return;
		}
		
		//检测它是不是工作区的直属节点
		for(var i=0; i<workArea.nodes.length; i++){
			if(workArea.nodes[i] == srcNode){
				//先处理线条，然后才能处理节点
				var startLine;
				var endLine;
				
				for(var j=0; j<workArea.lines.length; j++){
					if(workArea.lines[j].startNode == srcNode){
						startLine = workArea.lines[j];
					}
					
					if(workArea.lines[j].endNode == srcNode){
						endLine = workArea.lines[j];
					}
				}
				
				startLine.startNode = endLine.startNode;
				workArea.removeLine(endLine);
				this.removeRelationalException(srcNode);
				workArea.removeNode(srcNode);
				this.activeFlow.isStructureDirty = true;
				workArea.repaint();
				workArea.repaint();
				return;
			}
		}
		
		//如果不是工作区的直属节点，那么需要到并行节点中去查找
		var parent = null;
		for(var i=0; i<this.allNodes.length; i++){
			if(parent != null){
				break;
			}
			
			if(this.allNodes[i].getNodeType() == "Parallel"){
				for(var j=0; j<this.allNodes[i].branches.length; j++){
					if(parent != null){
						break;
					}
					
					for(var k=0; k<this.allNodes[i].branches[j].nodes.length; k++){
						if(this.allNodes[i].branches[j].nodes[k] == srcNode){
							parent = this.allNodes[i];
							break;
						}
					}
				}
			}
		}
		
		//先处理线条，再处理节点
		var startLine;
		var endLine;
		
		for(var i=0; i<parent.branches.length; i++){
			for(var j=0; j<parent.branches[i].lines.length; j++){
				if(parent.branches[i].lines[j].startNode == srcNode){
					startLine = parent.branches[i].lines[j];
				}
				
				if(parent.branches[i].lines[j].endNode == srcNode){
					endLine = parent.branches[i].lines[j];
				}
			}
		}
		
		startLine.startNode = endLine.startNode;
		parent.removeLine(endLine);
		this.removeRelationalException(srcNode);
		parent.removeNode(srcNode);
		this.activeFlow.isStructureDirty = true;
		
		workArea.repaint();
		workArea.repaint();
	}

	//删除节点关联的异常，在删除节点的时候调用
	this.removeRelationalException = function(node){
		//找异常节点
		var exceptionNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Exception"){
				exceptionNode = this.allNodes[i];
				break;
			}
		}

		//列举所有异常
		var exceptions = new Array();
		for(var i=0; i<exceptionNode.activity.extendedAttributes.length; i++){
			if(exceptionNode.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				for(var j=0; j<exceptionNode.activity.extendedAttributes[i].value.length; j++){
					exceptions.push(exceptionNode.activity.extendedAttributes[i].value[j]);
				}
				break;
			}
		}
		
		//删除关联的异常
		for(var i=0; i<exceptions.length; i++){
			if((exceptions[i].startActivityId == node.activity.id)
				|| (exceptions[i].endActivityId == node.activity.id)){
				exceptions.splice(i, 1);
				i--;
			}
		}

		//覆盖现有流程的异常
		for(var i=0; i<exceptionNode.activity.extendedAttributes.length; i++){
			if(exceptionNode.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				exceptionNode.activity.extendedAttributes[i].value = exceptions;
				break;
			}
		}
	}
	
	//删除控制线条
	this.removeControlLine = function(){
		this.controlLineMenu.hide();
		workArea.showInsertPosition(false);
		var src = this.eventElement;
		
		var srcNode = null;
		var lineIndex;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(srcNode != null){
				break;
			}
			
			if(this.allNodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.allNodes[i].controlLines.length; j++){
					if(this.allNodes[i].controlLines[j].icon == src){
						srcNode = this.allNodes[i];
						lineIndex = j;
						break;
					}
				}
			}
		}
		
		srcNode.removeControlLine(lineIndex);
		this.activeFlow.isStructureDirty = true;
		workArea.repaint();
		workArea.repaint();
	}
		
	//设置分支属性
	this.setBranchProperty = function(){
	}
	
	//----------------ur31747------add by yang.kai2 2008-10-06-------------------------------
	//查看工单派发规则
	this.dispatchRuleConfig = function(){	
		this.nodeMenu.hide();
		var src = this.eventElement;
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//先找到环节id
		var tacheId = null;
		var bpmObj = new Object();
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		
		bpmObj.tacheId = tacheId;
		//alert("tacheId=="+bpmObj.tacheId);
		window.OpenShowDlg('../workconfig/dispatchRuleList.jsp',600,800,bpmObj);
	}// end dispatchRuleConfig

	this.synchronousRuleConfig = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//先找到环节id
		var tacheId = null;
		var bpmObj = new Object();
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		
		bpmObj.tacheId = tacheId;
		//alert("tacheId=="+bpmObj.tacheId);
		window.OpenShowDlg('../flow/synRuleList.jsp',600,800,bpmObj);
		
	}
	
	//--------------------------------------------


	//查看控制节点属性
	this.viewControlNodeProperty = function(){
		this.controlViewMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "view";
		obj.activity = srcNode.activity;
		
		OpenShowDlg("./dialog/controlProperty.jsp", 200, 300, obj);
	}
	
	//设置控制节点属性
	this.setControlNodeProperty = function(){
		this.controlMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "update";
		obj.activity = srcNode.activity;
		
		var result = OpenShowDlg("./dialog/controlProperty.jsp", 200, 300, obj);
		
		if(result){
			srcNode.activity = result.activity;
			srcNode.updateData();
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//查看节点属性
	this.viewNodeProperty = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "view";
		obj.activity = srcNode.activity;
		obj.application = srcNode.application;
		obj.participant = srcNode.participant;
		
		//先找到环节id
		var tacheId;
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		
		obj.tachePath = this.findTachePath(tacheId);
		
		OpenShowDlg("./dialog/nodeProperty.jsp", 300, 600, obj);
	}
	
	//设置节点属性
	this.setNodeProperty = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "update";
		obj.activity = srcNode.activity;
		obj.application = srcNode.application;
		obj.participant = srcNode.participant;
		obj.dataFields = workArea.dataFields;
		obj.formalParameters = workArea.formalParameters;
		obj.allNodes = this.allNodes;
		obj.allLines = this.allLines;
		
		//先找到环节id
		var tacheId;
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}

		//这个节点是否处于并行结构中？
		var parentParallel = null;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				for(var j=0; j<this.allNodes[i].branches.length; j++){
					for(var k=0; k<this.allNodes[i].branches[j].nodes.length; k++){
						if(this.allNodes[i].branches[j].nodes[k] == srcNode){
							parentParallel = this.allNodes[i];
							break;
						}
					}
				}
			}
		}
		
		obj.parentParallel = parentParallel;
		obj.tachePath = this.findTachePath(tacheId);
		
		var result = OpenShowDlg("./dialog/nodeProperty.jsp", 300, 600, obj);
		
		if(result){
			//首先要更新环节的基本属性
			srcNode.activity = result.activity;
			srcNode.application = result.application;
			srcNode.participant = result.participant;
			srcNode.updateData();
			this.activeFlow.isDataDirty = true;
			
			//然后，把这个并行结构里面原来设置了的协同环节去掉
			//因为在一个并行结构里面只能设置一个主协同环节
			if(parentParallel != null){
				for(var i=0; i<parentParallel.branches.length; i++){
					for(var j=0; j<parentParallel.branches[i].nodes.length; j++){
						if((parentParallel.branches[i].nodes[j] != srcNode)
							&& (parentParallel.branches[i].nodes[j].getNodeType() == "Tache")){
							with(parentParallel.branches[i].nodes[j].activity){
								for(var k=0; k<extendedAttributes.length; k++){
									if(extendedAttributes[k].name == "ExCollaborate"){
										extendedAttributes[k].value = "False";
									}
								}
							}
						}
					}
				}
			}
		}
	}

	//设置节点上的异常
	this.setNodeException = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}

		//到异常节点的扩展属性里面去找自己对应的异常配置
		var exceptionNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Exception"){
				exceptionNode = this.allNodes[i];
				break;
			}
		}

		var allExceptionConfigs = new Array();	//这里为什么不写null而写new Array，是因为有的时候异常还没有初始化，遍历它的时候会出错
		var exceptionConfigs = new Array();

		//这里找到的是所有异常
		for(var i=0; i<exceptionNode.activity.extendedAttributes.length; i++){
			if(exceptionNode.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				allExceptionConfigs = exceptionNode.activity.extendedAttributes[i].value;
				break;
			}
		}

		//把从这个节点开始的异常抓出来
		for(var i=0; i<allExceptionConfigs.length; i++){
			if(allExceptionConfigs[i].startActivityId == srcNode.activity.id){
				exceptionConfigs.push(allExceptionConfigs[i]);
			}
		}

		//找环节id
		var tacheId;
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}

		//得到的数据是个XML，要转成数组
		var reasonDataXml = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "qryReturnReasonByTache", tacheId);
		var reasonData = new Array();
		var doc = makeDOM();
		doc.loadXML(reasonDataXml);
		root = doc.documentElement;

		//从XML转换成对象
		for(var i=0; i<root.childNodes.length; i++){
			var reason = new Object();
			for(var j=0; j<root.childNodes[i].attributes.length; j++){
				reason[root.childNodes[i].attributes[j].name] = root.childNodes[i].attributes[j].value;
			}
			reasonData.push(reason);
		}

		//过滤掉当前环节上面配过的异常
		for(var i=0; i<reasonData.length; i++){
			for(var j=0; j<exceptionConfigs.length; j++){
				if(exceptionConfigs[j].returnReasonId == reasonData[i].ID){
					reasonData.splice(i, 1);
					i--;
					break;
				}
			}
		}

		if(reasonData.length == 0){
			ErrorHandle("没有适用的异常！");
			return;
		}else{
			//这里，就要把所有可以回退到的节点标记起来
			//下面这一段是搜索可回滚节点的过程
			var nodePointer = srcNode;
			var linePointer = null;
			var jumpableNodes = new Array();
			
			//下面的找法基于一个先决条件：nodePointer指向的节点始终是环节节点，因此肯定只有一根线条流入
			while(true){
				//取得以这个节点作为终止节点的线条，无论如何，这个总是有的
				for(var i=0; i<this.allLines.length; i++){
					if(this.allLines[i].endNode == nodePointer){
						linePointer = this.allLines[i];
						break;
					}
				}
				
				//找到这里就是结束了
				if(linePointer == null){
					break;
				}
				
				//有开始节点，那么很好办
				if(linePointer.startNode != null){
					nodePointer = linePointer.startNode;
					if(nodePointer.getNodeType() == "Parallel"){
						var nodes = this.getAllNodesInParallel(nodePointer);
						for(var i=0; i<nodes.length; i++){
							if((nodes[i].getNodeType() == "Tache") || (nodes[i].getNodeType() == "Start") 
								|| (nodes[i].getNodeType() == "Parallel")){
								jumpableNodes.push(nodes[i]);
							}
						}
					}
				}else{
					//没有开始节点，那么这个线条是并行节点某分支的第一根线条
					var found = false;
					for(var i=0; i<this.allNodes.length; i++){
						if(found){
							break;
						}
						
						if(this.allNodes[i].getNodeType() == "Parallel"){
							for(var j=0; j<this.allNodes[i].branches.length; j++){
								if(found){
									break;
								}
								
								for(var k=0; k<this.allNodes[i].branches[j].nodes.length; k++){
									if(found){
										break;
									}
									
									if(this.allNodes[i].branches[j].nodes[k].activity.id == nodePointer.activity.id){
										nodePointer = this.allNodes[i];
										found = true;
										break;
									}
								}
							}
						}
					}
				}
				
				if((nodePointer.getNodeType() == "Tache") || (nodePointer.getNodeType() == "Start")
					|| (nodePointer.getNodeType() == "Parallel")){
					jumpableNodes.push(nodePointer);
				}
				linePointer = null;
			}

			for(var i=0; i<jumpableNodes.length; i++){
				jumpableNodes[i].setSelected(true);
			}
			workArea.status = "AddException";
			this.activeNode = srcNode;
			this.reasonData = exceptionConfigs;
		}
	}

	//查找并行结构中的所有节点
	this.getAllNodesInParallel = function(parallel){
		var nodes = new Array();

		for(var i=0; i<parallel.branches.length; i++){
			for(var j=0; j<parallel.branches[i].nodes.length; j++){
				if(parallel.branches[i].nodes[j].getNodeType() == "Parallel"){
					nodes.push(parallel.branches[i].nodes[j]);
					var subNodes = this.getAllNodesInParallel(parallel.branches[i].nodes[j]);
					for(var k=0; k<subNodes.length; k++){
						nodes.push(subNodes[k]);
					}
				}else{
					nodes.push(parallel.branches[i].nodes[j]);
				}
			}
		}

		return nodes;
	}

	//查找并行结构中的所有线条
	this.getAllLinesInParallel = function(parallel){
		var lines = new Array();

		for(var i=0; i<parallel.branches.length; i++){
			for(var j=0; j<parallel.branches[i].lines.length; j++){
				lines.push(parallel.branches[i].lines[j]);
			}

			for(var j=0; j<parallel.branches[i].nodes.length; j++){
				if(parallel.branches[i].nodes[j].getNodeType() == "Parallel"){
					var subLines = this.getAllLinesInParallel(parallel.branches[i].nodes[j]);
					for(var k=0; k<subLines.length; k++){
						lines.push(subLines[k]);
					}
				}
			}
		}

		return lines;
	}
	
	this.findTachePath = function(tacheId){
		//这里，把环节全路径生成出来，到对话框里面去显示
		var tachePath = new Array();
		
		//然后到环节库上去定位节点
		var tacheNode;
		for(var i=0; i<this.tacheTree.items.length; i++){
			if((this.tacheTree.items[i].id == tacheId) && (this.tacheTree.items[i].flag == "E")){
				tacheNode = this.tacheTree.items[i];
				break;
			}
		}
		
		tachePath.push(tacheNode.name);
		while(tacheNode.getParentItem() != null){
			tacheNode = tacheNode.getParentItem();
			tachePath.push(tacheNode.name);
		}
		
		tachePath.reverse();
		return tachePath.join("/");
	}
	
	//设置异常属性
	this.setExceptionProperty = function(){
		this.exceptionMenu.hide();
		var src = this.eventElement;
		
		//查找当前操作的节点
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		var obj = new Object();
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		obj.title=controller.activeFlow.title;
		//ur:61223 end
		obj.flowId = controller.activeFlow.id;
		obj.operation = "update";
		obj.activity = srcNode.activity;
		
		obj.allNodes = this.allNodes;
		obj.allLines = this.allLines;
		//modify by chen.jixin 2010-09-09 for ur:61223 begin
		var configs = OpenShowDlg("./dialog/exception/exceptionProperty.jsp", 300, 600, obj);
		//ur:61223 end
		if(configs != null){
			var exceptionConfigs = new Object();
			exceptionConfigs.name = "ExExceptionConfigs";
			exceptionConfigs.value = configs;
			
			//放到异常节点的扩展属性中
			for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
				if(srcNode.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
					srcNode.activity.extendedAttributes.splice(i, 1);
					break;
				}
			}
			srcNode.activity.extendedAttributes.push(exceptionConfigs);
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//查看异常属性
	this.viewExceptionProperty = function(){
		this.exceptionMenu.hide();
		var src = this.eventElement;
		
		//查找当前操作的节点
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		var obj = new Object();
		//add by chen.jixin 2010-09-09 for ur:61223 begin
		obj.title=controller.activeFlow.title;
		obj.flowId = controller.activeFlow.id;
		//ur:61223 end
		obj.operation = "view";
		obj.activity = srcNode.activity;
		
		obj.allNodes = this.allNodes;
		obj.allLines = this.allLines;
		
		OpenShowDlg("./dialog/exception/exceptionProperty.jsp", 300, 600, obj);
	}

	//添加异常
	this.addException = function(startNode, endNode){
		var args = new Object();
		args.startNode = startNode;
		args.endNode = endNode;
		args.selectedReasons = this.reasonData;
		args.flowId = controller.activeFlow.id; //add by ji.dong 2010-06-09 ur:56260
		
		//modify by chen.jixin 2010-09-09 for ur:61223
		var configs = OpenShowDlg("./dialog/exception/nodeException.jsp", 540, 400, args);
		//ur:61223 end
		if(configs != null){
			var haveAttributes = false;

			var exceptionNode;
			for(var i=0; i<this.allNodes.length; i++){
				if(this.allNodes[i].getNodeType() == "Exception"){
					exceptionNode = this.allNodes[i];
					break;
				}
			}

			with(exceptionNode.activity){
				for(var i=0; i<extendedAttributes.length; i++){
					if(extendedAttributes[i].name == "ExExceptionConfigs"){
						haveAttributes = true;
						for(var j=0; j<configs.length; j++){
							extendedAttributes[i].value.push(configs[j]);
						}
						ErrorHandle("异常成功设置！");
						this.activeFlow.isDataDirty = true;
						break;
					}
				}
				
				//如果扩展属性还没有加上去
				if(haveAttributes == false){
					var exceptionConfigs = new Object();
					exceptionConfigs.name = "ExExceptionConfigs";
					exceptionConfigs.value = configs;
					extendedAttributes.push(exceptionConfigs);
					ErrorHandle("异常成功设置！");
				}
			}
		}
	}
	
	//查看线条属性
	this.viewLineProperty = function(){
		this.lineMenu.hide();
		var src = this.eventElement;
		
		var srcLine;
		for(var i=0; i<this.allLines.length; i++){
			if((this.allLines[i].line == src) || (this.allLines[i].textBox == src)){
				srcLine = this.allLines[i];
			}
		}
		
		var obj = new Object();
		obj.operation = "view";
		obj.transition = srcLine.transition;
		obj.dataFields = workArea.dataFields;
		obj.formalParameters = workArea.formalParameters;
		
		OpenShowDlg("./dialog/lineProperty.jsp", 280, 500, obj);
	}
	
	//查看子流程属性
	this.viewSubFlowProperty = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "view";
		obj.activity = srcNode.activity;
		obj.application = srcNode.application;
		obj.participant = srcNode.participant;
		
		//先找到环节id
		var tacheId;
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		
		obj.tachePath = this.findTachePath(tacheId);
		
		OpenShowDlg("./dialog/subFlowProperty.jsp", 280, 480, obj);
	}
	
	//设置子流程属性
	this.setSubFlowProperty = function(){
		this.nodeMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].icon == src){
				srcNode = this.allNodes[i];
			}
		}
		
		//组装对象，传入对话框
		var obj = new Object();
		obj.operation = "update";
		obj.activity = srcNode.activity;
		obj.application = srcNode.application;
		obj.participant = srcNode.participant;
		
		//这里，要把当前工作区中存在的环节提取出来，组成一个数组，该环节自身除外
		obj.taches = new Array();
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i] != srcNode){
				if(this.allNodes[i].getNodeType() == "SubFlow"){
					var tache = new Object();
					tache.id = this.allNodes[i].activity.id;
					tache.name = this.allNodes[i].activity.name;
					obj.taches.push(tache);
				}
			}
		}
		
		//先找到环节id
		var tacheId;
		for(var i=0; i<srcNode.activity.extendedAttributes.length; i++){
			if(srcNode.activity.extendedAttributes[i].name == "ExTacheId"){
				tacheId = srcNode.activity.extendedAttributes[i].value;
				break;
			}
		}
		
		obj.tachePath = this.findTachePath(tacheId);
		
		var result = OpenShowDlg("./dialog/subFlowProperty.jsp", 280, 480, obj);
		
		if(result){
			//首先要更新环节的基本属性
			srcNode.activity = result.activity;
			srcNode.application = result.application;
			srcNode.participant = result.participant;
			srcNode.updateData();
		}
	}
	
	//设置线条属性
	this.setLineProperty = function(){
		this.lineMenu.hide();
		var src = this.eventElement;
		
		var srcLine;
		for(var i=0; i<this.allLines.length; i++){
			if((this.allLines[i].line == src)	|| (this.allLines[i].textBox == src)){
				srcLine = this.allLines[i];
			}
		}
		
		var obj = new Object();
		obj.operation = "update";
		obj.transition = srcLine.transition;
		obj.dataFields = workArea.dataFields;
		obj.formalParameters = workArea.formalParameters;
		
		var result = OpenShowDlg("./dialog/lineProperty.jsp", 500, 500, obj);
		
		if(result){
			srcLine.transition = result;
			srcLine.repaint();
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//查看控制线条属性
	this.viewControlLineProperty = function(){
		this.controlLineMenu.hide();
		var src = this.eventElement;
		
		var srcLine;
		var found = false;
		for(var i=0; i<this.allNodes.length; i++){
			if(found){
				break;
			}
			
			if(this.allNodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.allNodes[i].controlLines.length; j++){
					if(this.allNodes[i].controlLines[j].icon == src){
						srcLine = this.allNodes[i].controlLines[j];
						found = true;
						break;
					}
				}
			}
		}
		
		var obj = new Object();
		obj.operation = "view";
		obj.transition = srcLine.transition;
		obj.dataFields = workArea.dataFields;
		obj.formalParameters = workArea.formalParameters;
		
		OpenShowDlg("./dialog/lineProperty.jsp", 280, 500, obj);
	}
	
	//设置控制线条属性
	this.setControlLineProperty = function(){
		this.controlLineMenu.hide();
		var src = this.eventElement;
		
		var srcLine;
		var found = false;
		for(var i=0; i<this.allNodes.length; i++){
			if(found){
				break;
			}
			
			if(this.allNodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.allNodes[i].controlLines.length; j++){
					if(this.allNodes[i].controlLines[j].icon == src){
						srcLine = this.allNodes[i].controlLines[j];
						found = true;
						break;
					}
				}
			}
		}
		
		var obj = new Object();
		obj.operation = "update";
		obj.transition = srcLine.transition;
		obj.dataFields = workArea.dataFields;
		obj.formalParameters = workArea.formalParameters;
		
		var result = OpenShowDlg("./dialog/lineProperty.jsp", 500, 500, obj);
		
		if(result){
			srcLine.transition = result;
			srcLine.repaint();
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//设置并行节点属性
	this.setParallelProperty = function(){
		this.parallelMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(this.allNodes[i].icon == src){
					srcNode = this.allNodes[i];
					break;
				}
			}
		}
		
		var obj = srcNode.activity;
		obj.operation = "update";
		obj = OpenShowDlg("./dialog/parallelProperty.jsp", 180, 300, obj);
		
		if(obj){
			srcNode.activity = obj;
			srcNode.updateData();
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//查看并行节点属性
	this.viewParallelProperty = function(){
		this.parallelMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(this.allNodes[i].icon == src){
					srcNode = this.allNodes[i];
					break;
				}
			}
		}
		
		var obj = srcNode.activity;
		obj.operation = "view";
		OpenShowDlg("./dialog/parallelProperty.jsp", 180, 300, obj);
	}
	
	//设置并行节点中的分支条件合并属性
	this.setRelationProperty = function(){
		this.relationMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(this.allNodes[i].relationIcon == src){
					srcNode = this.allNodes[i];
					break;
				}
			}
		}
		
		var obj = srcNode.relationData;
		obj.operation = "update";
		obj = OpenShowDlg("./dialog/relationProperty.jsp", 200, 300, obj);
		
		if(obj){
			srcNode.relationData = obj;
			srcNode.updateData();
			this.activeFlow.isDataDirty = true;
		}
	}
	
	//查看并行节点中的分支条件合并属性
	this.viewRelationProperty = function(){
		this.relationMenu.hide();
		var src = this.eventElement;
		
		var srcNode;
		
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(this.allNodes[i].relationIcon == src){
					srcNode = this.allNodes[i];
					break;
				}
			}
		}
		
		var obj = srcNode.relationData;
		obj.operation = "view";
		OpenShowDlg("./dialog/relationProperty.jsp", 200, 300, obj);
	}
	
	//从对象生成图形
	this.loadByObject = function(object){
		workArea.clear();
		this.allNodes = new Array();
		this.allLines = new Array();
		
		workArea.id = object.id;
		workArea.name = object.name;
		
		//存放并行节点的堆栈
		var parallelStack = new Array();
		var nodeParent = workArea;
		
		//恢复节点数据
		for(var i=0; i<object.activities.length; i++){
			//生成节点
			var nodeType;
			for(var j=0; j<object.activities[i].extendedAttributes.length; j++){
				if(object.activities[i].extendedAttributes[j].name == "nodeType"){
					nodeType = object.activities[i].extendedAttributes[j].value;
					break;
				}
			}
			
			//只要不是并行上面的关系图标，都是可以创建出节点的
			if(nodeType != "Relation"){
				var node = NodeFactory.createNode(nodeType);
				node.activity = object.activities[i];
				
				//下面这一步，是因为在创建并行节点的时候，已经默认创建了一个分支，所以要把parentId补充到这个分支上去
				if(nodeType == "Parallel"){
					for(var j=0; j<node.branches[0].lines[0].transition.extendedAttributes.length; j++){
						if(node.branches[0].lines[0].transition.extendedAttributes[j].name == "parentId"){
							node.branches[0].lines[0].transition.extendedAttributes[j].value = node.activity.id;
							break;
						}
					}
				}
				
				//并行节点添加分支，并把节点的分支序号跟节点序号设置出来
				for(var j=0; j<object.activities[i].extendedAttributes.length; j++){
					if(object.activities[i].extendedAttributes[j].name == "numOfBranch"){
						var num = object.activities[i].extendedAttributes[j].value - 1;
						for(var k=0; k<num; k++){
							node.addBranch();
						}
					}
					
					if(object.activities[i].extendedAttributes[j].name == "branchIndex"){
						node.setBranchIndex(object.activities[i].extendedAttributes[j].value);
					}
					
					if(object.activities[i].extendedAttributes[j].name == "nodeIndex"){
						node.setNodeIndex(object.activities[i].extendedAttributes[j].value);
					}
				}
			}
			
			//如果是并行节点，先压栈，等后面来了对应的关系节点再添加
			if(nodeType == "Parallel"){
				parallelStack.push(node);
				nodeParent = node;
			}else{
				if(nodeType == "Relation"){
					node = parallelStack.pop();
					node.relationData = object.activities[i];
					if(parallelStack.length > 0){
						nodeParent = parallelStack[parallelStack.length-1];
					}else{
						nodeParent = workArea;
					}
				}
				
				if(nodeType == "Control"){
					node.parentNode = nodeParent;
				}
				
				nodeParent.insertNode(node);
			}
		}
		
		//恢复线条数据
		for(var i=0; i<object.transitions.length; i++){
			var startNode = null;
			var endNode = null;
			var parentNode = null;
			var parentId;
			
			for(var j=0; j<object.transitions[i].extendedAttributes.length; j++){
				if(object.transitions[i].extendedAttributes[j].name == "parentId"){
					parentId = object.transitions[i].extendedAttributes[j].value;
					break;
				}
			}
			
			//根据ID，把线条的开始、结束和父节点找到
			for(var j=0; j<this.allNodes.length; j++){
				if(this.allNodes[j].getNodeType() == "Parallel"){
					if(this.allNodes[j].relationData.id == object.transitions[i].from){
						startNode = this.allNodes[j];
					}else if(this.allNodes[j].activity.id == object.transitions[i].from){
						startNode = this.allNodes[j];
					}
				}else{
					if(this.allNodes[j].activity.id == object.transitions[i].from){
						startNode = this.allNodes[j];
					}
				}
				
				if(this.allNodes[j].activity.id == object.transitions[i].to){
					endNode = this.allNodes[j];
				}
				
				if(this.allNodes[j].activity.id == parentId){
					parentNode = this.allNodes[j];
				}
			}
			
			//如果父节点没有找到，那么是工作区，因为工作区的ID设置成0了
			if(parentNode == null){
				parentNode = workArea;
			}
			
			//添加线条
			var lineType;
			for(var j=0; j<object.transitions[i].extendedAttributes.length; j++){
				if(object.transitions[i].extendedAttributes[j].name == "LineType"){
					lineType = object.transitions[i].extendedAttributes[j].value;
					break;
				}
			}
			
			if((lineType == "控制线条") || (lineType == "Control")){
				startNode.addEndNode(endNode, object.transitions[i]);
			}else if(endNode != null){
				parentNode.insertLine(startNode, endNode, object.transitions[i]);
			}else{
				//这里是并行节点每个分支最后一根线
				var branchIndex = -1;
				
				if((startNode != null) && (startNode != parentNode)){
					for(var j=0; j<startNode.activity.extendedAttributes.length; j++){
						if(startNode.activity.extendedAttributes[j].name == "branchIndex"){
							branchIndex = startNode.activity.extendedAttributes[j].value;
							break;
						}
					}
				}

				if(branchIndex != -1){
					for(var j=0; j<parentNode.branches[branchIndex].lines.length; j++){
						if((parentNode.branches[branchIndex].lines[j].startNode != null)
						&& (parentNode.branches[branchIndex].lines[j].startNode.activity.id == object.transitions[i].from)){
							parentNode.branches[branchIndex].lines[j].transition = object.transitions[i];
						}
					}
				}else{
					for(var j=0; j<parentNode.branches.length; j++){
						if(parentNode.branches[j].nodes.length == 0){
							branchIndex = j;
							break;
						}
					}
					//虽然线条无序，但是为什么这里还可以写0？是因为这个分支只有唯一一根线条，就是空分支线
					parentNode.branches[branchIndex].lines[0].transition = object.transitions[i];
				}
			}
		}
		
		//恢复application数据
		for(var i=0; i<object.applications.length; i++){
			//根据这个application的id，到activity里面去查找对应的tool
			for(var j=0; j<this.allNodes.length; j++){
				if(this.allNodes[j].activity.implementation.tool.id == object.applications[i].id){
					this.allNodes[j].application = object.applications[i];
					break;
				}
			}
		}
		
		//恢复participant数据
		for(var i=0; i<object.participants.length; i++){
			//先找到这个参与者对应的activityId
			var activityId;
			for(var j=0; j<object.participants[i].extendedAttributes.length; j++){
				if(object.participants[i].extendedAttributes[j].name == "ExActivityID"){
					activityId = object.participants[i].extendedAttributes[j].value;
					break;
				}
			}
			
			//到所有节点里面去找一下，找到对应的activity
			for(var j=0; j<this.allNodes.length; j++){
				if(this.allNodes[j].activity.id == activityId){
					this.allNodes[j].participant = object.participants[i];
					break;
				}
			}
		}
		
		if(object.dataFields != null){
			workArea.dataFields = object.dataFields;
		}
		
		if(object.formalParameters != null){
			workArea.formalParameters = object.formalParameters;
		}
		
		workArea.repaint();
		workArea.repaint();
		workArea.repaint();
	}
	
	//从XPDL生成图形
	this.loadByXPDL = function(xpdl){
		//window.prompt("", xpdl);
		var xpdlParser = new XPDLParser();
		xpdlParser.loadXPDLString(xpdl);
		
		var flows = xpdlParser.xpdlToObject();
		
		//显示子流程
		if(flows.length > 1){
			for(var i=1; i<flows.length; i++){
				var subFlow = new Object();
				subFlow.name = "子流程" + i;
				this.loadByObject(flows[i]);
				subFlow.xpdl = this.generateXPDL();
				this.allFlows.push(subFlow);
			}
		}
		
		this.loadByObject(flows[0]);
	}
	
	//打开流程
	this.openVersion = function(){
		//检查当前流程是否已经打开了
		for(var i=0; i<this.allFlows.length; i++){
			if(this.allFlows[i].id == this.activeTreeItem.id){
				//已经有一份打开的，先把当前查看的版本暂存一下，防止已经作了修改
				if(this.activeFlow != null){
					this.activeFlow.xpdl = this.generateXPDL();
					this.activeFlow.editable = Controller.editable;
					this.activeFlow.title = workArea.getTitle();
					
					this.loadByXPDL(this.allFlows[i].xpdl);
					this.activeFlow = this.allFlows[i];
				}
				
				this.activeFlow = this.allFlows[i];
				this.refreshTabs();
				return;
			}
		}
		
		//没有打开的版本，准备新开一个，旧的也要暂存一下
		if(this.activeFlow != null){
			this.activeFlow.xpdl = this.generateXPDL();
			this.activeFlow.editable = Controller.editable;
			this.activeFlow.title = workArea.getTitle();
		}
		
		var xpdl = null;
		
		if(arguments[0] != null){
			xpdl = arguments[0];
		}else{
			xpdl = window.prompt("输入XPDL", "");
		}
		
		if((xpdl != null) && (xpdl != "")){
			var newFlow = new Object();
			newFlow.name = this.activeTreeItem.name;
			newFlow.id = this.activeTreeItem.id;
			newFlow.packageId = this.activeTreeItem.getParentItem().id;
			newFlow.packageName = this.activeTreeItem.getParentItem().name;
			newFlow.areaId = flowOperation.areaTree.selectedItem.areaId;
			newFlow.xpdl = xpdl;
			newFlow.isDataDirty = false;
			newFlow.isStructureDirty = false;
			this.allFlows.push(newFlow);
			
			this.clear();
			
			this.loadByXPDL(xpdl);
			this.activeFlow = newFlow;
			
			var pointer = flowOperation.flowTree.selectedItem;
			flowOperation.setTitle(pointer, false);
			newFlow.title = workArea.getTitle();
			
			this.refreshTabs();
		}else{
			throw "流程读取失败！所读取的流程可能尚未绘制。";
		}
	}
	
	//绘制流程
	this.drawVersion = function(){
		//如果已经在打开列表中，那么切换出来即可
		for(var i=0; i<this.allFlows.length; i++){
			if(this.allFlows[i].id == this.activeTreeItem.id){				
				//已经有一份打开的，先把当前查看的版本暂存一下，防止已经作了修改
				if(this.activeFlow != null){
					this.activeFlow.xpdl = this.generateXPDL();
					this.activeFlow.editable = Controller.editable;
					this.activeFlow.title = workArea.getTitle();
					
					this.loadByXPDL(this.allFlows[i].xpdl);
					this.activeFlow = this.allFlows[i];
				}
				
				this.activeFlow = this.allFlows[i];
				Controller.editable = true;
				this.refreshTabs();
				return;
			}
		}
		
		//不为空，就是说原来有流程处于打开状态，切换了之后要先转换成数据暂存起来
		if(this.activeFlow != null){
			this.activeFlow.xpdl = this.generateXPDL();
			this.activeFlow.editable = Controller.editable;
			this.activeFlow.title = workArea.getTitle();
		}
		
		var xpdl = null;
		
		if(arguments[0] != null){
			xpdl = arguments[0];
		}else{
			xpdl = window.prompt("输入XPDL", "");
		}
		
		//现在开始，要新建一个流程图了
		var newFlow = new Object();
		newFlow.name = this.activeTreeItem.name;
		newFlow.id = this.activeTreeItem.id;
		newFlow.packageId = this.activeTreeItem.getParentItem().id;
		newFlow.packageName = this.activeTreeItem.getParentItem().name;
		newFlow.areaId = flowOperation.areaTree.selectedItem.areaId;
		this.activeFlow = newFlow;
		this.allFlows.push(newFlow);
		
		if((xpdl != null) && (xpdl != "")){
			this.clear();
			
			this.loadByXPDL(xpdl);
			newFlow.xpdl = xpdl;
			newFlow.isDataDirty = false;
			newFlow.isStructureDirty = false;
		}else{
			this.init();
			workArea.id = getUUID();
			workArea.name = this.activeTreeItem.getParentItem().name;
			newFlow.isDataDirty = true;
			newFlow.isStructureDirty = true;
		}
		Controller.editable = true;
		
		var pointer = flowOperation.flowTree.selectedItem;
		flowOperation.setTitle(pointer, true);
		newFlow.title = workArea.getTitle();
		newFlow.editable = Controller.editable;
		
		this.refreshTabs();
	}
	
	//校验流程的完整性
	this.verify = function(showResult){
		if(this.activeFlow == null){
			ErrorHandle("目前没有打开的版本，无法校验！");
			return;
		}

		//并行结构中空分支的数目不可多于一个
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Parallel"){
				if(this.allNodes[i].branches.length <= 1){
					ErrorHandle("并行节点的分支数不足两条！");
					return false;
				}else{
					var emptyBranchNum = 0;
					for(var j=0; j<this.allNodes[i].branches.length; j++){
						if(this.allNodes[i].branches[j].nodes.length == 0){
							emptyBranchNum ++;
						}
					}
					
					if(emptyBranchNum > 1){
						ErrorHandle("并行节点的空分支不能超过一条！");
						return false;
					}
				}
			}
		}

		//异常原因中，起始、终止环节是否已经不存在？
		//到异常节点的扩展属性里面去找自己对应的异常配置
		var nodeNotExist = false;
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Exception"){
				exceptionNode = this.allNodes[i];
				break;
			}
		}
		
		//这里为什么不写null而写new Array，是因为有的时候这个流程里面没有设置异常，遍历它的时候会出错
		var allExceptionConfigs = new Array();

		//这里找到的是所有异常
		for(var i=0; i<exceptionNode.activity.extendedAttributes.length; i++){
			if(exceptionNode.activity.extendedAttributes[i].name == "ExExceptionConfigs"){
				allExceptionConfigs = exceptionNode.activity.extendedAttributes[i].value;
				break;
			}
		}

		var startFound;
		var endFound;
		var allNodesLength = this.allNodes.length;
		var tempExceCfg = null;
		var errExceCfgs = new Array();
		for(var i=0; i<allExceptionConfigs.length; i++){
			tempExceCfg = allExceptionConfigs[i];
			var startActivityId = tempExceCfg.startActivityId;
			var endActivityId = tempExceCfg.endActivityId;

			if(startActivityId == 0){
				startFound = true;
			}else{
				startFound = false;
			}
			endFound = false;
     
			for(var j=0; j<allNodesLength; j++){
				if((!startFound) && (this.allNodes[j].activity.id == startActivityId)){
					startFound = true;
				}

				if((!endFound) && (this.allNodes[j].activity.id == endActivityId)){
					endFound = true;
				}
			}

			//开始跟结束没有都找到，这个节点可能已经被删掉了
			if(!(startFound && endFound)){
				
				//modify by liangli 2006.12.22  给出所有异常属性列表，方便用户重新配置
				errExceCfgs[errExceCfgs.length] = tempExceCfg;
				
				//return false;
			}
		}
		
		if(errExceCfgs.length>0){			  
			OpenShowDlg("dialog/exception/errExceptionProperty.jsp", 330, 550, errExceCfgs);
			return false;
		}

/*
		//下面这一段校验流程中第一个活动节点的参与者类型不能为上个环节指定，因为此时上个环节不存在
		var exTempletCode;
		var firstNode = this.allNodes[1];
		for(var i=0; i<firstNode.participant.extendedAttributes.length; i++){
			if(firstNode.participant.extendedAttributes[i].name == "ExTempletCode"){
				exTempletCode = firstNode.participant.extendedAttributes[i].value;
				break;
			}
		}

		if(exTempletCode == LAST_TACHE){
			ErrorHandle("第一个节点的参与者类型不能设置为由上个环节指定！");
			return false;
		}
*/		

		//校验所有使用的环节是否有已经在环节管理中被删除的
		var tacheIds = new Array();
		var tacheNames = new Array();
		for(var i=0; i<this.allNodes.length; i++){
			if(this.allNodes[i].getNodeType() == "Tache"){
				var node = this.allNodes[i];
				for(var j=0; j<node.activity.extendedAttributes.length; j++){
					if(node.activity.extendedAttributes[j].name == "ExTacheId"){
						tacheIds.push(node.activity.extendedAttributes[j].value);
					}
					if(node.activity.extendedAttributes[j].name == "ExTacheName"){
						tacheNames.push(node.activity.extendedAttributes[j].value);
					}
				}
			}
		}
		var faultArr = new Array();
		if(tacheIds.length > 0){
			var resultArr = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.TacheManager", "isInvalidTaches", tacheIds.join(","));
			for(var i=0; i<resultArr.length; i++){
				if(resultArr[i] == 0){
					faultArr.push(tacheNames[i]);
				}
			}
		}

		if(faultArr.length != 0){
			ErrorHandle("引用了已删除或无有效版本的环节：" + faultArr.join(","));
			return false;
		}

		//校验所有使用的异常原因是否有已经在异常原因管理中被删除的
		var reasonIds = new Array();
		var reasonNames = new Array();
		for(var i=0; i<allExceptionConfigs.length; i++){
			reasonIds.push(allExceptionConfigs[i].returnReasonId);
			reasonNames.push(allExceptionConfigs[i].returnReasonName);
		}

		faultArr = new Array();
		if(reasonIds.length > 0){
			
			var resultArr = callRemoteFunctionNoTrans("com.ztesoft.iom.tache.ReturnReasonManager", "isInvalidReturnReason", reasonIds.join(","));
			for(var i=0; i<resultArr.length; i++){
				if(resultArr[i] == 0){
					faultArr.push(reasonNames[i]);
				}
			}
		}

		if(faultArr.length != 0){
			ErrorHandle("引用了已被删除的异常原因：" + faultArr.join(","));
			return false;
		}

		//是否显示结果
		if(showResult){
			ErrorHandle("校验通过！");
		}
		return true;
	}
	
	//生成XPDL
	this.generateXPDL = function(){
		var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		
		var package = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "findPackageById", version.packageId);
		
		workArea.name = package.name;
		workArea.processState = version.state;
		workArea.editDate = version.editDate;
		workArea.validFromDate = version.validFromDate;
		workArea.validToDate = version.validToDate;
		workArea.description = version.description || "";
		workArea.editUser = version.editUser;
		workArea.version = version.version;
		
		var workflowXPDL = workArea.generateXPDL();
		
		var xpdl = "<?xml version='1.0' encoding='UTF-8'?>";
		
		//包的基本信息
		xpdl += "<Package xmlns='http://www.wfmc.org/2002/XPDL1.0' "
			+ "xmlns:xpdl='http://www.wfmc.org/2002/XPDL1.0' "
			+ "xmlns:xsd='http://www.w3.org/2000/10/XMLSchema' "
			+ "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
			+ "xsi:noNamespaceSchemaLocation='TC-1025_schema_10_xpdl.xsd' "
			+ "Id='" + workArea.id + "' "
			+ "Name='" + workArea.name + "'>";
		
		//包定义头
		xpdl += workArea.packageHeaderXml;
		
		//流程数据
		xpdl += "<WorkflowProcesses>";
		xpdl += "<WorkflowProcess Id='" + workArea.id
			 + "' Name='" + workArea.name
			 + "' AccessLevel='PUBLIC'>";
			 
		xpdl += workflowXPDL;
		
		xpdl += "</WorkflowProcess>";
		xpdl += "</WorkflowProcesses>";
		
		xpdl += "</Package>";
		
		return xpdl;
	}
}

Controller.editable = false;

//分支上面的菜单
Controller.showBranchMenu = function(){
	if(!Controller.editable){
		return;
	}
		// modified by lyh 090309 ur:37389 begin
	 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			 return;
		 }
	 // modified by lyh 090309 ur:37389 end
	controller.eventElement = window.event.srcElement;
	controller.branchMenu.popMenu();
}

//节点上面的菜单
Controller.showNodeMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
		 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			 controller.nodeViewMenu.popMenu();
		  }else{
		 
			controller.nodeMenu.popMenu();
		  }
		   // modified by lyh 090309 ur:37389 end
	}else{
		controller.nodeViewMenu.popMenu();
	}
}

//异常节点上面的菜单
Controller.showExceptionMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
		controller.exceptionMenu.popMenu();
	}else{
		controller.exceptionViewMenu.popMenu();
	}
}

//并行节点上面的菜单
Controller.showParallelMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
		 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			 controller.parallelViewMenu.popMenu();
		 }else{
			 controller.parallelMenu.popMenu();
		 }
	 // modified by lyh 090309 ur:37389 end
	}else{
		controller.parallelViewMenu.popMenu();
	}
}

//控制节点上面的菜单
Controller.showControlMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
	  var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			 controller.controlViewMenu.popMenu();
		 }else{
			 controller.controlMenu.popMenu();
		 }
	 // modified by lyh 090309 ur:37389 end
	}else{
		controller.controlViewMenu.popMenu();
	}
}

//子流程节点上面的菜单
Controller.showSubFlowMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
	 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			//controller.SubFlowViewMenu.popMenu();
		 }else{
			 controller.subFlowMenu.popMenu();
		 }
	 // modified by lyh 090309 ur:37389 end
	}else{
		//controller.SubFlowViewMenu.popMenu();
	}
}

//线条上面的菜单
Controller.showLineMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
				// modified by lyh 090309 ur:37389 begin
		 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			 if(version.stateName.indexOf("失效") >= 0){
				controller.lineViewMenu.popMenu();
			 }else{
				controller.lineMenu.popMenu();
			 }
		 // modified by lyh 090309 ur:37389 end
	}else{
		controller.lineViewMenu.popMenu();
	}
}

//控制线条上面的菜单
Controller.showControlLineMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
	 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			controller.controlLineViewMenu.popMenu();
		 }else{
			controller.controlLineMenu.popMenu();
		 }
	 // modified by lyh 090309 ur:37389 end
	}else{
		controller.controlLineViewMenu.popMenu();
	}
}

//分支合并条件上面的菜单
Controller.showRelationMenu = function(){
	controller.eventElement = window.event.srcElement;
	
	if(Controller.editable){
			// modified by lyh 090309 ur:37389 begin
	 var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
		 if(version.stateName.indexOf("失效") >= 0){
			controller.relationViewMenu.popMenu();
		 }else{
			controller.relationMenu.popMenu();
		 }
	 // modified by lyh 090309 ur:37389 end
	}else{
		controller.relationViewMenu.popMenu();
	}
}

//点击节点
Controller.nodeClick = function(){
	if(!Controller.editable){
		return;
	}
	
	workArea.showInsertPosition(false);
	var src = window.event.srcElement;
	var nodeIndex = -1;
	
	//如果原来有选中节点，那么要取消选中
	if((controller.activeNode != null) && (controller.activeNode.getNodeType() != "Control")){
		controller.activeNode.setSelected(false);
	}
	
	//先找到点的是哪个节点
	for(var i=0; i<controller.allNodes.length; i++){
		if(src == controller.allNodes[i].icon){
			nodeIndex = i;
			break;
		}
	}

	switch(workArea.status){
		case "AddControlLine":{
			//添加控制线条
			if((controller.allNodes[nodeIndex].getNodeType() != "Control") && (controller.allNodes[nodeIndex].getSelected())){
				controller.activeNode.addEndNode(controller.allNodes[nodeIndex]);
				controller.activeFlow.isStructureDirty = true;
				controller.activeNode.controlLines[controller.activeNode.controlLines.length-1].icon.oncontextmenu
					 = Controller.showControlLineMenu;
				workArea.status = "Mouse";
				controller.activeNode.parentNode.showSibling(controller.activeNode, false);
				workArea.repaint();
				workArea.repaint();
				return;
			}
			break;
		}
		case "AddException":{
			//添加异常
			if(controller.allNodes[nodeIndex].getSelected()){
				//把边框去掉
				for(var i=0; i<controller.allNodes.length; i++){
					if((controller.allNodes[i].getNodeType()!="Control") && (controller.allNodes[i].getSelected())){
						controller.allNodes[i].setSelected(false);
					}
				}

				//加个异常
				controller.addException(controller.activeNode, controller.allNodes[nodeIndex]);
				workArea.status = "Mouse";
				return;
			}
			break;
		}
	}

	//如果点在控制节点上
	if(controller.allNodes[nodeIndex].getNodeType() == "Control"){
		if(workArea.status != "AddControlLine"){
			controller.activeNode = controller.allNodes[nodeIndex];
			controller.activeNode.parentNode.showSibling(controller.activeNode, true);
			workArea.status = "AddControlLine";
		}else{
			controller.activeNode = controller.allNodes[nodeIndex];
			controller.activeNode.parentNode.showSibling(controller.activeNode, false);
			workArea.status = "Mouse";
		}
	}else{
		//controller.activeNode = controller.allNodes[nodeIndex];
		//controller.activeNode.setSelected(true);
	}
}

//点击插入点
Controller.ovalClick = function(){
	if(!Controller.editable){
		return;
	}

	//这一段，如果环节有重复，就不准插，但是有一种情况例外，就是两个相同环节位于并列的分支上，互相不能形成串
	if((TACHEINSERTLIMIT) && (workArea.status == "Tache")){
		var tacheId = controller.tacheTree.selectedItem.id;

		var tacheNodes = new Array();
		var tacheExist = false;
		for(var i=0; i<controller.allNodes.length; i++){
			var node = controller.allNodes[i];
			if(node.getNodeType() == "Tache"){
				for(var j=0; j<node.activity.extendedAttributes.length; j++){
					if(node.activity.extendedAttributes[j].name == "ExTacheId"){
						if(node.activity.extendedAttributes[j].value == tacheId){
							tacheNodes.push(node);
							break;
						}
					}
				}
			}
		}

		for(var num=0; num<tacheNodes.length; num++){
			if(tacheExist){
				break;
			}

			var tacheNode = tacheNodes[num];

			//如果插在这个节点上面呢？要从那个活动节点往上找，看是否经过这个线
			//这里，就要把所有可以回退到的节点标记起来
			//下面这一段是搜索可到达节点的过程
			var nodePointer = tacheNode;
			var linePointer = null;
			var passedLines = new Array();
			
			//下面的找法基于一个先决条件：nodePointer指向的节点始终是环节节点，因此肯定只有一根线条流入
			while(true){
				for(var i=0; i<controller.allLines.length; i++){
					if(controller.allLines[i].endNode == nodePointer){
						linePointer = controller.allLines[i];
						passedLines.push(linePointer);
						break;
					}
				}
				
				//找到这里就是结束了
				if(linePointer == null){
					break;
				}
				
				//有开始节点，那么很好办
				if(linePointer.startNode != null){
					nodePointer = linePointer.startNode;
					if(nodePointer.getNodeType() == "Parallel"){
						var lines = controller.getAllLinesInParallel(nodePointer);
						for(var i=0; i<lines.length; i++){
							passedLines.push(lines[i]);
						}
					}
				}else{
					//没有开始节点，那么这个线条是并行节点某分支的第一根线条
					var found = false;
					for(var i=0; i<controller.allNodes.length; i++){
						if(found){
							break;
						}
						
						if(controller.allNodes[i].getNodeType() == "Parallel"){
							for(var j=0; j<controller.allNodes[i].branches.length; j++){
								if(found){
									break;
								}
								
								for(var k=0; k<controller.allNodes[i].branches[j].nodes.length; k++){
									if(found){
										break;
									}
									
									if(controller.allNodes[i].branches[j].nodes[k].activity.id == nodePointer.activity.id){
										nodePointer = controller.allNodes[i];
										found = true;
										break;
									}
								}
							}
						}
					}
				}
				
				linePointer = null;
			}

			tacheExist = false;
			for(var i=0; i<passedLines.length; i++){
				if(passedLines[i].oval == window.event.srcElement){
					tacheExist = true;
					break;
				}
			}

			//已经存在这个环节的活动，那么先从插入点往上回溯到开始，看这个活动节点是否位于从插入点到开始的路上
			if(tacheExist == false){
				//这里，就要把所有可以回退到的节点标记起来
				//下面这一段是搜索可到达节点的过程
				var nodePointer = null;

				var linePointer = null;
				for(var i=0; i<controller.allLines.length; i++){
					if(window.event.srcElement == controller.allLines[i].oval){
						linePointer = controller.allLines[i];
						break;
					}
				}

				if(linePointer.startNode == null){
					if(linePointer.endNode != null){
						nodePointer = linePointer.endNode;
					}else{
						//这种情况，是一个空分支线，这个时候直接将nodePointer指向这个并行节点
						for(var i=0; i<controller.allNodes.length; i++){
							if(nodePointer != null){
								break;
							}

							if(controller.allNodes[i].getNodeType() == "Parallel"){
								for(var j=0; j<controller.allNodes[i].branches.length; j++){
									if(nodePointer != null){
										break;
									}

									if((controller.allNodes[i].branches[j].lines.length == 1)
										&& (controller.allNodes[i].branches[j].lines[0] == linePointer)){
										nodePointer = controller.allNodes[i];
									}
								}
							}
						}
					}
				}

				var jumpableNodes = new Array();
				
				//下面的找法基于一个先决条件：nodePointer指向的节点始终是环节节点，因此肯定只有一根线条流入
				while(true){
					if(nodePointer != null){
						for(var i=0; i<controller.allLines.length; i++){
							if(controller.allLines[i].endNode == nodePointer){
								linePointer = controller.allLines[i];
								break;
							}
						}
					}
					
					//找到这里就是结束了
					if(linePointer == null){
						break;
					}
					
					//有开始节点，那么很好办
					if(linePointer.startNode != null){
						nodePointer = linePointer.startNode;
						if(nodePointer.getNodeType() == "Parallel"){
							var nodes = controller.getAllNodesInParallel(nodePointer);
							for(var i=0; i<nodes.length; i++){
								jumpableNodes.push(nodes[i]);
							}
						}
					}else{
						//没有开始节点，那么这个线条是并行节点某分支的第一根线条
						var found = false;
						for(var i=0; i<controller.allNodes.length; i++){
							if(found){
								break;
							}
							
							if(controller.allNodes[i].getNodeType() == "Parallel"){
								for(var j=0; j<controller.allNodes[i].branches.length; j++){
									if(found){
										break;
									}
									
									for(var k=0; k<controller.allNodes[i].branches[j].nodes.length; k++){
										if(found){
											break;
										}
										
										if(controller.allNodes[i].branches[j].nodes[k].activity.id == nodePointer.activity.id){
											nodePointer = controller.allNodes[i];
											found = true;
											break;
										}
									}
								}
							}
						}
					}
					
					if(nodePointer.getNodeType() == "Tache"){
						jumpableNodes.push(nodePointer);
					}
					linePointer = null;
				}

				tacheExist = false;
				for(var i=0; i<jumpableNodes.length; i++){
					var node = jumpableNodes[i];
					for(var j=0; j<node.activity.extendedAttributes.length; j++){
						if(node.activity.extendedAttributes[j].name == "ExTacheId"){
							if(node.activity.extendedAttributes[j].value == tacheId){
								tacheExist = true;
								break;
							}
						}
					}
				}
			}
		}

		if(tacheExist){
			alert("同一个流程中，一个环节不能出现可串行的两次！");
			return;
		}
	}

	//满足条件了，那么可以插入节点
	workArea.showInsertPosition(false);
	var src = window.event.srcElement;
	
	var found = false;
	var parentNode;
	var nodeIndex;
	var lineIndex;
	var branchIndex;
	
	//这里是在工作区的直属线条上面查找事件源
	for(var i=0; i<workArea.lines.length; i++){
		if(workArea.lines[i].oval == src){
			found = true;
			parentNode = workArea;
			branchIndex = 0;
			nodeIndex = workArea.lines[i].endNode.getNodeIndex();
			lineIndex = i;
			break;
		}
	}
	
	//如果在工作区的直属线条上面没有找到，那么到复合节点中查找
	if(!found){
		for(var i=0; i<controller.allNodes.length; i++){
			if(controller.allNodes[i].getNodeType() == "Parallel"){
				for(var j=0; j<controller.allNodes[i].branches.length; j++){
					for(var k=0; k<controller.allNodes[i].branches[j].lines.length; k++){
						if(controller.allNodes[i].branches[j].lines[k].oval == src){
							found = true;
							parentNode = controller.allNodes[i];
							branchIndex = j;
							lineIndex = k;
							if(controller.allNodes[i].branches[j].lines[k].endNode == null){
								nodeIndex = controller.allNodes[i].branches[j].nodes.length;
							}else{
								nodeIndex = controller.allNodes[i].branches[j].lines[k].endNode.getNodeIndex();
							}
							break;
						}
					}
					if(found){
						break;
					}
				}
			}
		}
	}
	
	var node = NodeFactory.createNode(workArea.status);
	node.setBranchIndex(branchIndex);
	node.setNodeIndex(nodeIndex);
	
	if((workArea.status == "Tache") && (controller.tacheTree.selectedItem.flag == "E")){
		node.setText(controller.tacheTree.selectedItem.name);
		
		//环节ID作为扩展属性
		var tacheId = new Object();
		tacheId.name = "ExTacheId";
		tacheId.value = controller.tacheTree.selectedItem.id;
		
		//环节编码作为扩展属性
		var tacheCode = new Object();
		tacheCode.name = "ExTacheCode";
		tacheCode.value = controller.tacheTree.selectedItem.TACHE_CODE;
		
		//环节名称作为扩展属性
		var tacheName = new Object();
		tacheName.name = "ExTacheName";
		tacheName.value = controller.tacheTree.selectedItem.name;
		
		//操作类型作为扩展属性
		var operType = new Object();
		operType.name = "ExOperType";
		operType.value = "1";					//默认操作方式是“装”
		
		//是否可撤单
		var withdraw = new Object();
		withdraw.name = "ExWithdraw";
		withdraw.value = "true";
		
		//是否可变更
		var change = new Object();
		change.name = "ExChange";
		change.value = "true";
		
		node.activity.extendedAttributes.push(tacheId);
		node.activity.extendedAttributes.push(tacheCode);
		node.activity.extendedAttributes.push(tacheName);
		node.activity.extendedAttributes.push(operType);
		node.activity.extendedAttributes.push(withdraw);
		node.activity.extendedAttributes.push(change);
	}
	
	if(parentNode == workArea){
		if(parentNode.nodes.length > 0){
			parentNode.lines[lineIndex].startNode = node;
			parentNode.insertLine(parentNode.nodes[nodeIndex-1], node);
		}
	}else{
		parentNode.insertLine(node, parentNode.branches[branchIndex].lines[lineIndex].endNode);
		parentNode.branches[branchIndex].lines[lineIndex].endNode = node;
	}
	
	parentNode.insertNode(node);
	controller.activeFlow.isStructureDirty = true;
	//将这个节点插入到工作区的所有节点列表
	
	if(workArea.status == "Control"){
		//控制节点要把线条坐标调整好
		workArea.status = "AddControlLine";
		controller.activeNode = node;
		node.parentNode = parentNode;
		parentNode.showSibling(node, true);
	}
	
	workArea.repaint();
	workArea.repaint();
}

//从环节库中添加环节
Controller.tacheTreeClick = function(){
	if(!Controller.editable){
		return;
	}
	if(controller.tacheTree.selectedItem.flag == "E"){
		// modified by lyh 090309 ur:37389 begin
			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			if(version.stateName.indexOf("失效") >= 0){
				workArea.showInsertPosition(false);
				//ErrorHandle("流程为失效状态，不可编辑");
			}else{
					workArea.status = "Tache";
					workArea.showInsertPosition(true);
			}
			// modified by lyh 090309 ur:37389 end
	}else{
		workArea.showInsertPosition(false);
	}
}

//添加分支
Controller.branchAddClick = function(){
	if(!Controller.editable){
		return;
	}
	// modified by lyh 090309 ur:37389 begin
			var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
			if(version.stateName.indexOf("失效") >= 0){
				return;
			 }
	// modified by lyh 090309 ur:37389 end
	var src = window.event.srcElement;
	for(var i=0; i<controller.allNodes.length; i++){
		if((controller.allNodes[i].getNodeType() == "Parallel") && (controller.allNodes[i].iconAdd == src)){
			controller.allNodes[i].addBranch();
			controller.activeFlow.isStructureDirty = true;
			break;
		}
	}
	workArea.repaint();
	workArea.repaint();
	workArea.repaint();
}

//Handles the mouse click event on node bar button
Controller.nodeBarClick = function(){
	//先把功能按钮的事件处理掉，这里是不需要编辑状态的按钮
	switch(window.event.srcElement.name){
		case "Export":{
			//导出按钮
			if(controller.activeFlow != null){
				workArea.exportFlow();
			}else{
				ErrorHandle("当前没有打开的流程，无法导出流程图形！");
			}
			return;
		}
		case "ViewXPDL":{
			//查看XPDL按钮
			if(controller.activeFlow != null){
				window.prompt("", controller.generateXPDL());
			}else{
				ErrorHandle("当前没有打开的流程，无法查看XPDL！");
			}
			return;
		}
		case "Variable":{
			//设置流程变量按钮
			flowOperation.setFlowVariable();
			return;
		}
		case "Parameter":{
			//设置流程参数按钮
			flowOperation.setFlowParameter();
			return;
		}
		case "Rule":{
			//流程模板适用规则
			if(controller.activeFlow != null){
				var obj = new Object();
				obj.id = controller.activeFlow.packageId;
				obj.name = controller.activeFlow.packageName;
				obj.areaId = controller.activeFlow.areaId;
				OpenShowDlg("./dialog/flow/packageApplyRuleList.jsp", 420, 600, obj);
			}else{
				ErrorHandle("当前没有打开的流程，无法从按钮配置流程模板适用规则！");
			}
			return;
		}
	}
	
	//下面几个按钮需要确认编辑状态
	if(!Controller.editable){
		ErrorHandle("不是编辑状态，不能执行此项操作！");
		return;
	}
	
	switch(window.event.srcElement.name){
		case "Save":{
			//保存按钮
			flowOperation.saveVersion();
			return;
		}
		case "Verify":{
			//校验按钮
			controller.verify(true);
			return;
		}
	}
	
	//下面处理的是动作
	if(workArea.status == "AddControlLine"){
		alert("控制节点没有处理完，不能添加节点！");
		return;
	}
	
	switch(window.event.srcElement.name){
		case "0":{
			workArea.status = "Mouse";
			break;
		}
		case "1":{
			workArea.status = "Parallel";
			break;
		}
		case "2":{
			workArea.status = "Control";
			break;
		}
		case "3":{
			workArea.status = "SubFlow";
			break;
		}
		case "4":{
			workArea.status = "Finish";
			break;
		}
	}
	
			// modified by lyh 090309 ur:37389 begin
	var version = callRemoteFunctionNoTrans("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "selectProcessDefinitionById", controller.activeFlow.id);
    if(version.stateName.indexOf("失效") >= 0){
		workArea.showInsertPosition(false);
		 ErrorHandle("流程为失效状态，不可编辑");
	}else{
		if(workArea.status != "Mouse"){
			workArea.showInsertPosition(true);
		}else{
			workArea.showInsertPosition(false);
		}
	}
	// modified by lyh 090309 ur:37389 end
}

//取消节点的选中状态
Controller.cancelNodeSelect = function(){
	if(!Controller.editable){
		return;
	}
	for(var i=0; i<controller.allNodes.length; i++){
		if(controller.allNodes[i].getNodeType() != "Control"){
			controller.allNodes[i].setSelected(false);
		}
	}
	
	workArea.status = "Mouse";
}

//关闭一个版本
Controller.closeVersion = function(index){
	//关闭的时候要检查是否处于可编辑状态
	if(Controller.editable){
		if(controller.activeFlow.isDataDirty){
			var param = new Object();
			param.content = "原流程处于编辑状态，是否保存？";
			
			var decide = OpenShowDlg("./dialog/YesNoCancelDialog.jsp", 180, 330, param);
			
			if(decide == null){
				//取消的情况，什么都不做
				return;
			}else if(decide == "YES"){
				//是，那么保存原来版本
				flowOperation.saveVersion();
			}else{
				//否，那么不保存
			}
		}else if(controller.activeFlow.isStructureDirty){
			var decide = ErrorHandle("原流程处于编辑状态，是否保存？", 3, null, true, null);
			
			if(decide == true){
				//是，那么保存原来版本，但是这里必须要另存了
				flowOperation.saveVersion();
			}else{
				//否，那么不保存
			}
		}
	}
	
	controller.allFlows.splice(index, 1);
	controller.activeFlow = null;
	
	controller.clear();
	Controller.editable = false;
	controller.refreshTabs();
}

//切换到某版本
Controller.switchVersion = function(index){
	//如果现在打开的就是这个版本，就不刷新了
	if(controller.activeFlow == controller.allFlows[index]){
		return;
	}
	
	if(controller.activeFlow != null){
		controller.activeFlow.xpdl = controller.generateXPDL();
	}
	
	controller.activeFlow = controller.allFlows[index];
	
	controller.clear();
	
	if(controller.allFlows[index].xpdl != null){
		ExecWait("controller.loadByXPDL(controller.allFlows[" + index + "].xpdl)");
	}
	
	if(controller.allFlows[index].title != ""){
		workArea.setTitle(controller.allFlows[index].title);
	}
	
	Controller.editable = controller.allFlows[index].editable;
}