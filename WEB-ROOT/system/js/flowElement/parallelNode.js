/////////////////////////////////////////////////////
// ZTESoft corp. 2006-01-23
// Author : Xu.fei3
// commits: Implementation of the parallel node
/////////////////////////////////////////////////////

//并行节点模块
function ParallelNode(){
	_extends(this, AbstractFlowNode);
	
	this.type = "multiple";				//多分支

	this.selected = false;				//是否被选中
	this.collapsed = false;				//是否折叠
	var iconBase = FLOWNODEICONBASE;
	
	this.setNodeType("Parallel");
	
	this.branches = new Array();	//分支数组
	
	//节点数据数据对象
	this.activity.name = "并行节点";
	
	var numOfBranchAttribute = new Object();
	numOfBranchAttribute.name = "numOfBranch";
	numOfBranchAttribute.value = 0;
	this.activity.extendedAttributes.push(numOfBranchAttribute);
	
	this.baseLeft = -16;
	this.baseTop = -16;
	
	//节点的合并条件数据对象
	this.relationData = new Activity();
	this.relationData.name = "合并条件";
	
	this.relationData.extendedAttributes[0] = new Object();
	this.relationData.extendedAttributes[0].name = "nodeType";
	this.relationData.extendedAttributes[0].value = "Relation";
	
	this.setMaxDepth(2);			//这个深度是其所有分支的最大深度，每当结构发生改变，值应当重新计算
	this.setMaxExtent(0);			//这个广度是其最右边一个分支的广度，每当结构发生改变，值应当重新计算，默认没分支，所以为0
	
	//展开状态下的面板
	this.panel = document.createElement("div");
	this.panel.style.position = "absolute";
	this.panel.style.pixelLeft = this.baseLeft;
	this.panel.style.pixelTop = this.baseTop;
	
	//折叠状态下的面板
	this.collapsePanel = document.createElement("div");
	this.collapsePanel.style.position = "absolute";
	this.collapsePanel.style.pixelLeft = this.baseLeft;
	this.collapsePanel.style.pixelTop = this.baseTop;
	
	//并行结构的图标
	this.icon = document.createElement("img");
	this.icon.style.position = "absolute";
	this.icon.style.width = "33px";
	this.icon.style.height = "18px";
	this.icon.src = iconBase + "parallel.gif";
	this.icon.onclick = Controller.nodeClick;
	//this.icon.ondblclick = Controller.viewParallelProperty;
	this.icon.oncontextmenu = Controller.showParallelMenu;
	
	//折叠状态的图标
	var collapseIcon = document.createElement("img");
	collapseIcon.style.width = "32px";
	collapseIcon.style.height = "32px";
	collapseIcon.src = iconBase + "parallel_collapsed.gif";
	
	//添加分支的按钮
	this.iconAdd = document.createElement("img");
	this.iconAdd.style.width = "16px";
	this.iconAdd.style.height = "16px";
	this.iconAdd.src = iconBase + "add.gif";
	this.iconAdd.style.position = "absolute";
	this.iconAdd.style.pixelLeft = 40;
	this.iconAdd.style.pixelTop = 30;
	this.iconAdd.alt = "点击添加分支";
	this.iconAdd.onclick = Controller.branchAddClick;
	
	//顶端的竖直线条

	var topLine = document.createElement("v:line");
	topLine.strokecolor = "#5682ce";
	
	//上面的水平线条

	var topHorizontalLine = document.createElement("v:line");
	topHorizontalLine.strokecolor = "#5682ce";
	
	//下面左边的水平线条

	var leftHorizontalLine = document.createElement("v:line");
	leftHorizontalLine.strokecolor = "#5682ce";
	
	//下面右边的水平线条

	var rightHorizontalLine = document.createElement("v:line");
	rightHorizontalLine.strokecolor = "#5682ce";
	
	//关系图标
	this.relationIcon = document.createElement("img");
	this.relationIcon.style.width = "17px";
	this.relationIcon.style.height = "18px";
	this.relationIcon.src = iconBase + "parallel_and.gif";
	this.relationIcon.style.position = "absolute";
	this.relationIcon.style.pixelLeft = 8;
	this.relationIcon.style.pixelTop = 20 + (this.maxDepth-1)*this.spaceY;
	this.relationIcon.oncontextmenu = Controller.showRelationMenu;
	
	this.panel.appendChild(this.icon);
	this.panel.appendChild(this.iconAdd);
	this.panel.appendChild(topLine);
	this.panel.appendChild(topHorizontalLine);
	this.panel.appendChild(leftHorizontalLine);
	this.panel.appendChild(this.relationIcon);
	this.panel.appendChild(rightHorizontalLine);
	
	this.collapsePanel.appendChild(collapseIcon);
	
	//得到节点名称，所有并行节点的名称都是“并行节点”

	this.getText = function(){
		return this.activity.name;
	}
	
	//添加一个分支

	this.addBranch = function(){
		var branch = new Object();
		
		branch.maxExtent = 1;								//最大宽度，供右边的分支确定位置
		branch.maxDepth = 1;								//最大深度，用来确定竖直长度
		branch.maxControlExtent = 1;				//最大的控制节点宽度
		branch.maxMultipleExtent = 1;				//最大的并行结构宽度
		branch.nodes = new Array();
		branch.lines = new Array();
		
		if(this.branches.length == 0){
			branch.innerExtent = 0;
		}else{
			branch.innerExtent = this.branches[this.branches.length-1].innerExtent
				 + this.branches[this.branches.length-1].maxExtent;
		}
		
		branch.topLine = document.createElement("v:line");
		branch.topLine.strokecolor = "#5682ce";
		branch.topLine.from = "0, 0";
		branch.topLine.to = "0, 0";
		
		branch.icon = document.createElement("img");
		branch.icon.src = "./js/flowElement/resources/branch.gif";
		branch.icon.style.position = "absolute";
		branch.icon.style.pixelLeft = 9 + branch.innerExtent*this.spaceX;
		branch.icon.style.pixelTop = 40;
		branch.icon.oncontextmenu = Controller.showBranchMenu;
		
		var flowLine = new FlowLine();
		flowLine.setArrow(false);
		
		var attribute = new Object();
		attribute.name = "parentId";
		attribute.value = this.activity.id;
		flowLine.transition.extendedAttributes.push(attribute);
		
		this.panel.appendChild(branch.topLine);
		this.panel.appendChild(branch.icon);
		this.panel.appendChild(flowLine.line);
		
		branch.lines.push(flowLine);
		controller.allLines.push(flowLine);
		
		this.branches.push(branch);
		
		this.repaint();
	}
		
	//删除指定的分支

	this.removeBranch = function(branchIndex){		
		//删除分支上本来就有的东西
		this.panel.removeChild(this.branches[branchIndex].topLine);
		this.panel.removeChild(this.branches[branchIndex].icon);
		
		//删除分支上面的节点

		while(this.branches[branchIndex].nodes.length > 0){
			this.removeNode(this.branches[branchIndex].nodes[0]);
		}
		
		//删除分支上面的线条

		while(this.branches[branchIndex].lines.length > 0){
			this.removeLine(this.branches[branchIndex].lines[0]);
		}
		
		this.branches.splice(branchIndex, 1);
	}
	
	//删除指定的节点

	this.removeNode = function(node){
		if((node.getNodeType() == "Parallel") || (node.getNodeType() == "Control")){
			node.clear();
		}
		
		//先从全局引用数组中把这个节点的引用去掉

		for(var i=0; i<controller.allNodes.length; i++){
			if(controller.allNodes[i] == node){
				controller.allNodes.splice(i, 1);
				break;
			}
		}
		
		//如果有控制节点的线条指向它，要先把线条拿掉

		for(var i=0; i<this.branches[node.getBranchIndex()].nodes.length; i++){
			if(this.branches[node.getBranchIndex()].nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.branches[node.getBranchIndex()].nodes[i].endNodes.length; j++){
					if(this.branches[node.getBranchIndex()].nodes[i].endNodes[j] == node){
						this.branches[node.getBranchIndex()].nodes[i].removeControlLine(j);
						break;
					}
				}
			}
		}
		
		//从分支上删除这个节点
		for(var i=0; i<this.branches[node.getBranchIndex()].nodes.length; i++){
			if(this.branches[node.getBranchIndex()].nodes[i] == node){
				if((node.getNodeType() == "Parallel") || (node.getNodeType() == "Control")){
					node.clear();
				}
				
				this.panel.removeChild(node.panel);
				this.branches[node.getBranchIndex()].nodes.splice(i, 1);
				break;
			}
		}
	}
	
	//删除指定的线条

	this.removeLine = function(line){
		//先从全局引用数组中把这个线条的引用去掉

		for(var i=0; i<controller.allLines.length; i++){
			if(controller.allLines[i] == line){
				controller.allLines.splice(i, 1);
				break;
			}
		}
		
		//从分支上删除这个线条
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].lines.length; j++){
				if(this.branches[i].lines[j] == line){
					this.panel.removeChild(line.line);
					this.branches[i].lines.splice(j, 1);
					return;
				}
			}
		}
	}
	
	//显示插入点

	this.showInsertPosition = function(flag){
		//直属线条上面的插入点
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].lines.length; j++){
				this.branches[i].lines[j].repaint();
				if(flag){
					if(!this.panel.contains(this.branches[i].lines[j].oval)){
						this.panel.appendChild(this.branches[i].lines[j].oval);
					}
				}else{
					if(this.panel.contains(this.branches[i].lines[j].oval)){
						this.panel.removeChild(this.branches[i].lines[j].oval);
					}
				}
			}
			
			//复合节点显示插入点

			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(this.branches[i].nodes[j].getNodeType() == "Parallel"){
					this.branches[i].nodes[j].showInsertPosition(flag);
				}
			}
		}
	}
	
	//显示兄弟节点
	this.showSibling = function(node, flag){
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(this.branches[i].nodes[j] == node){
					for(var m=0; m<this.branches[i].nodes.length; m++){
						if(m != j){
							if(this.branches[i].nodes[m].getNodeType() != "Control"){
								this.branches[i].nodes[m].setSelected(flag);
							}
						}
					}
					return;
				}
			}
		}
	}
	
	//设置选中
	this.setSelected = function(flag){
		if(flag){
			this.icon.style.border = "1px solid #ff0000";
		}else{
			this.icon.style.border = "none";
		}
		this.selected = flag;
	}
	
	//取得选中状态

	this.getSelected = function(){
		return this.selected;
	}
	
	//插入节点
	this.insertNode = function(node){
		var branchIndex = node.getBranchIndex();
		var nodeIndex = node.getNodeIndex();
		
		//将这个节点下方的节点下移
		for(var i=this.branches[branchIndex].nodes.length; i>nodeIndex; i--){
			this.branches[branchIndex].nodes[i] = this.branches[branchIndex].nodes[i-1];
		}
		this.branches[branchIndex].nodes[nodeIndex] = node;
		
		this.panel.appendChild(node.panel);
		
		this.repaint();
		
		controller.allNodes.push(node);
	}

	//插入线条
	this.insertLine = function(startNode, endNode){
		if(startNode == this){
			startNode = null;
			
			for(var i=0; i<this.branches[endNode.getBranchIndex()].lines.length; i++){
				//原来放在最上面的那根线，要弄下去

				if(this.branches[endNode.getBranchIndex()].lines[i].startNode == null){
					this.branches[endNode.getBranchIndex()].lines[i].startNode
						 = this.branches[endNode.getBranchIndex()].nodes[this.branches[endNode.getBranchIndex()].nodes.length-1];
					break;
				}
			}
		}
		
		var flowLine = new FlowLine();
		flowLine.startNode = startNode;
		flowLine.endNode = endNode;
		
		var attribute = new Object();
		attribute.name = "parentId";
		attribute.value = this.activity.id;
		flowLine.transition.extendedAttributes.push(attribute);
		
		if(arguments.length == 3){
			flowLine.transition = arguments[2];
		}
		
		//线条其实是不需要排序插入的
		var branchIndex;
		if(startNode != null){
			branchIndex = startNode.getBranchIndex();
		}else{
			branchIndex = endNode.getBranchIndex();
		}
		this.branches[branchIndex].lines.push(flowLine);
		
		flowLine.repaint();
		this.panel.appendChild(flowLine.line);
		
		controller.allLines.push(flowLine);
	}
	
	//折叠
	this.collapse = function(){
		this.collapsed = true;
		this.setMaxDepth(1);
		this.setMaxExtent(1);
		
		var parent = this.panel.parentNode;
		if(parent != null){
			parent.removeChild(this.panel);
			parent.appendChild(this.collapsePanel);
		}
		
		this.repaint();
	}
	
	//展开
	this.expand = function(){
		this.collapsed = false;
		this.setMaxDepth(2);
		this.setMaxExtent(1);
		
		var parent = this.collapsePanel.parentNode;
		if(parent != null){
			parent.removeChild(this.collapsePanel);
			parent.appendChild(this.panel);
		}
		
		this.repaint();
	}
	
	//清除所有内部元素

	this.clear = function(){
		while(this.branches.length > 0){
			this.removeBranch(this.branches.length - 1);
		}
	}
	
	//生成控制线条的布局参数
	this.generateControlParam = function(lines, branchIndex){
		//首先，确定控制线条的布局优先级规则

		//优先级最高的是起始节点A与终止节点B之间没有其他控制线条的

		//其次是起始节点A与终止节点B之间有控制线条，但是这些控制线条的起始与终止节点都在A、B之间
		//最后是其他控制线条
		//在同一类型的控制线条之间，布局的优先级顺序按照上>下、内>外的规则
		
		//把每根线条的优先级确定出来

		for(var i=0; i<lines.length; i++){
			lines[i].extent = this.getMaxNoControlExtent(lines[i], branchIndex) + 1;
			lines[i].type = this.judgeControlLineType(lines[i], branchIndex);
		}
		
		//布局数组
		this.leftExtentArray = new Array();
		this.rightExtentArray = new Array();
		
		var index = 0;
		
		//对布局数组进行赋值

		//第一轮，针对并行节点的赋值

		for(var i=0; i<this.branches[branchIndex].nodes.length-1; i++){
			if(this.branches[branchIndex].nodes[i].getNodeType() == "Parallel"){
				var leftExtent = this.branches[branchIndex].nodes[i].getMaxExtent() - 1;
				var rightExtent = this.branches[branchIndex].nodes[i].getMaxExtent() - 1;
		
				//并行节点的开始节点

				this.leftExtentArray[index] = 0;
				this.rightExtentArray[index] = 0;
				index ++;
		
				//并行节点的内部节点

				for(var j=1; j<=this.branches[branchIndex].nodes[i].getMaxDepth(); j++){
					this.leftExtentArray[index] = leftExtent;
					this.rightExtentArray[index] = rightExtent;
					index ++;
				}
			}else{
				this.leftExtentArray[index] = 0;
				this.rightExtentArray[index] = 0;
				index ++;
			}
		}
		
		//没有优化过的算法，不区分优先级

		for(var i=0; i<lines.length; i++){
			//针对控制线条的赋值

			var leftExtent = 1;
			var rightExtent = 1;
			
			var startIndex = lines[i].startIndex;
			var endIndex = lines[i].endIndex;
			
			//临时交换，为了计算方便而已
			if(startIndex > endIndex){
				var temp = startIndex;
				startIndex = endIndex;
				endIndex = temp;
			}
			
			var startNodeDepth = this.countNodeDepth(startIndex, branchIndex);
			var endNodeDepth = this.countNodeDepth(endIndex, branchIndex);
			
			for(var j=startNodeDepth; j<=endNodeDepth; j++){
				if(this.leftExtentArray[j] >= leftExtent){
					leftExtent = this.leftExtentArray[j] + 1;
				}
				if(this.rightExtentArray[j] >= rightExtent){
					rightExtent = this.rightExtentArray[j] + 1;
				}
			}
			
			if(rightExtent <= leftExtent){
				lines[i].direction = "right";
				lines[i].extent = rightExtent;
				
				for(j=startIndex; j<=endIndex; j++){
					var nodeDepth = this.countNodeDepth(j, branchIndex);
					this.rightExtentArray[nodeDepth] = lines[i].extent;
				}
			}else{
				lines[i].direction = "left";
				lines[i].extent = leftExtent;
				
				for(j=startIndex; j<=endIndex; j++){
					var nodeDepth = this.countNodeDepth(j, branchIndex);
					this.leftExtentArray[nodeDepth] = lines[i].extent;
				}
			}
		}
		
		/*
		//优化过的算法
		//优先级最高（type=1）的
		for(var i=0; i<lines.length; i++){
			if(lines[i].type == 1){
				//针对控制线条的赋值

				var leftExtent = 1;
				var rightExtent = 1;
				
				var startIndex = lines[i].startIndex;
				var endIndex = lines[i].endIndex;
				
				//临时交换，为了计算方便而已
				if(startIndex > endIndex){
					var temp = startIndex;
					startIndex = endIndex;
					endIndex = temp;
				}
				
				var startNodeDepth = this.countNodeDepth(startIndex);
				var endNodeDepth = this.countNodeDepth(endIndex);
				
				for(var j=startNodeDepth; j<=endNodeDepth; j++){
					if(this.leftExtentArray[j] >= leftExtent){
						leftExtent = this.leftExtentArray[j] + 1;
					}
					if(this.rightExtentArray[j] >= rightExtent){
						rightExtent = this.rightExtentArray[j] + 1;
					}
				}
				
				if(rightExtent <= leftExtent){
					lines[i].direction = "right";
					lines[i].extent = rightExtent;
					
					for(j=startIndex; j<=endIndex; j++){
						var nodeDepth = this.countNodeDepth(j);
						this.rightExtentArray[nodeDepth] = lines[i].extent;
					}
				}else{
					lines[i].direction = "left";
					lines[i].extent = leftExtent;
					
					for(j=startIndex; j<=endIndex; j++){
						var nodeDepth = this.countNodeDepth(j);
						this.leftExtentArray[nodeDepth] = lines[i].extent;
					}
				}
				
				line[i].type = 0;
			}
		}
	
		//这一轮把优先级次高（type=2）的算出来

		var isType2Exist = true;
		while(isType2Exist){
			//如果还有type=2这种优先级的线条
			for(var i=0; i<lines.length; i++){
				if(lines[i].type == 2){
					if(this.judgeControlLineNoContain(lines[i], lines)){
						//这条线之间不包含其他控制线条了，或者包含的是已经被计算过的优先级低的线条

						//针对控制线条的赋值

						var leftExtent = 1;
						var rightExtent = 1;
						
						var startIndex = lines[i].startIndex;
						var endIndex = lines[i].endIndex;
						
						//临时交换，为了计算方便而已
						if(startIndex > endIndex){
							var temp = startIndex;
							startIndex = endIndex;
							endIndex = temp;
						}
						
						var startNodeDepth = this.countNodeDepth(startIndex);
						var endNodeDepth = this.countNodeDepth(endIndex);
						
						for(var j=startNodeDepth; j<=endNodeDepth; j++){
							if(this.leftExtentArray[j] >= leftExtent){
								leftExtent = this.leftExtentArray[j] + 1;
							}
							if(this.rightExtentArray[j] >= rightExtent){
								rightExtent = this.rightExtentArray[j] + 1;
							}
						}
						
						if(rightExtent <= leftExtent){
							lines[i].direction = "right";
							lines[i].extent = rightExtent;
							
							for(j=startIndex; j<=endIndex; j++){
								var nodeDepth = this.countNodeDepth(j);
								this.rightExtentArray[nodeDepth] = lines[i].extent;
							}
						}else{
							lines[i].direction = "left";
							lines[i].extent = leftExtent;
							
							for(j=startIndex; j<=endIndex; j++){
								var nodeDepth = this.countNodeDepth(j);
								this.leftExtentArray[nodeDepth] = lines[i].extent;
							}
						}
						
						//降低优先级

						lines[i].type = 0;
						break;
					}
				}
			}
			isType2Exist = false;
				
			for(var i=0; i<lines.length; i++){
				if(lines[i].type == 2){
					isType2Exist = true;
					break;
				}
			}
		}
		
		//最后是type=3的

		var isType3Exist = true;
		while(isType3Exist){
			//如果type=3的这种线条还能够找到			
			for(var i=0; i<lines.length; i++){
				if(lines[i].type == 3){
					//针对控制线条的赋值

					var leftExtent = 1;
					var rightExtent = 1;
					
					var startIndex = lines[i].startIndex;
					var endIndex = lines[i].endIndex;
					
					//临时交换，为了计算方便而已
					if(startIndex > endIndex){
						var temp = startIndex;
						startIndex = endIndex;
						endIndex = temp;
					}
					
					var startNodeDepth = this.countNodeDepth(startIndex);
					var endNodeDepth = this.countNodeDepth(endIndex);
					
					for(var j=startNodeDepth; j<=endNodeDepth; j++){
						if(this.leftExtentArray[j] >= leftExtent){
							leftExtent = this.leftExtentArray[j] + 1;
						}
						if(this.rightExtentArray[j] >= rightExtent){
							rightExtent = this.rightExtentArray[j] + 1;
						}
					}
					
					if(rightExtent <= leftExtent){
						lines[i].direction = "right";
						lines[i].extent = rightExtent;
						
						for(j=startIndex; j<=endIndex; j++){
							var nodeDepth = this.countNodeDepth(j);
							this.rightExtentArray[nodeDepth] = lines[i].extent;
						}
					}else{
						lines[i].direction = "left";
						lines[i].extent = leftExtent;
						
						for(j=startIndex; j<=endIndex; j++){
							var nodeDepth = this.countNodeDepth(j);
							this.leftExtentArray[nodeDepth] = lines[i].extent;
						}
					}
					lines[i].type = 0;
					break;
				}
			}
			
			isType3Exist = false;
			for(var i=0; i<lines.length; i++){
				if(lines[i].type == 3){
					isType3Exist = true;
					break;
				}
			}
		}
		*/
		return lines;
	}
	
	//从节点的索引来取得这个节点的纵向位置
	this.countNodeDepth = function(nodeIndex, branchIndex){
		var index = 0;
		for(var i=0; i<nodeIndex; i++){
			index += this.branches[branchIndex].nodes[i].getMaxDepth();
		}
		return index;
	}
	
	//检索控制线条两个端点A、B之间的节点是否还存在跳转线条（两个端点都在A、B之间的那种）
	this.judgeControlLineNoContain = function(line, lines){
		var startIndex = line.startIndex;
		var endIndex = line.endIndex;
		
		//临时交换，为了计算方便而已
		if(startIndex > endIndex){
			var temp = startIndex;
			startIndex = endIndex;
			endIndex = temp;
		}
		
		for(var i=0; i<lines.length; i++){
			if(lines[i] != line){
				if((lines[i].startIndex <= line.endIndex) && (lines[i].startIndex >= line.startIndex)
				&& (lines[i].endIndex <= line.endIndex) && (lines[i].startIndex >= line.startIndex)
				&& lines[i].type != 0){
					return false;
				}
			}
		}
		return true;
	}
	
	//计算一条控制节点所跳过的最大非控制节点宽度
	this.getMaxNoControlExtent = function(line, branchIndex){
		var startIndex = line.startIndex;
		var endIndex = line.endIndex;
		
		//临时交换，为了计算方便而已
		if(startIndex > endIndex){
			var temp = startIndex;
			startIndex = endIndex;
			endIndex = temp;
		}
		
		var maxExtent = 1;
		
		for(var i=startIndex+1; i<endIndex; i++){
			if(this.branches[branchIndex].nodes[i].getNodeType() != "Control"){
				if(this.branches[branchIndex].nodes[i].getMaxExtent() > maxExtent){
					maxExtent = this.branches[branchIndex].nodes[i].getMaxExtent();
				}
			}
		}
		
		if(line.endIndex < line.startIndex){
			if(this.branches[branchIndex].nodes[line.endIndex].getMaxExtent() > maxExtent){
				maxExtent = this.branches[branchIndex].nodes[line.endIndex].getMaxExtent();
			}
		}
		
		return maxExtent;
	}
	
	//判断一条控制线条的优先级

	this.judgeControlLineType = function(line, branchIndex){
		var type = 1;
		
		for(var i=0; i<this.branches[branchIndex].nodes.length; i++){
			if(this.branches[branchIndex].nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.branches[branchIndex].nodes[i].controlLines.length; j++){
					type = 2;
					var startIndex = this.branches[branchIndex].nodes[i].controlLines[j].startNode.getNodeIndex();
					var endIndex = this.branches[branchIndex].nodes[i].controlLines[j].endNode.getNodeIndex();
					
					if(endIndex > startIndex){
						if((endIndex-line.endIndex)*(startIndex-line.startIndex) > 0){
							type = 3;
						}
					}else{
						if((endIndex-line.endIndex)*(startIndex-line.startIndex) < 0){
							type = 3;
						}
					}
				}
			}
		}
		
		return type;
	}
	
	//收集所有直属控制节点包含的控制线条
	this.gatherControlLines = function(branchIndex){
		var lines = new Array();
	
		for(var i=0; i<this.branches[branchIndex].nodes.length; i++){
			if(this.branches[branchIndex].nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.branches[branchIndex].nodes[i].controlLines.length; j++){
					var line = new Object();
					line.startIndex = this.branches[branchIndex].nodes[i].controlLines[j].startNode.getNodeIndex();
					line.endIndex = this.branches[branchIndex].nodes[i].controlLines[j].endNode.getNodeIndex();
					
					lines.push(line);
				}
			}
		}
		
		return lines;
	}
	
	//更新数据
	this.updateData = function(){
		for(var i=0; i<this.activity.extendedAttributes.length; i++){
			if(this.activity.extendedAttributes[i].name == "numOfBranch"){
				this.activity.extendedAttributes[i].value = this.branches.length;
			}
			
			if(this.activity.extendedAttributes[i].name == "branchIndex"){
				this.activity.extendedAttributes[i].value = this.getBranchIndex();
			}
			
			if(this.activity.extendedAttributes[i].name == "nodeIndex"){
				this.activity.extendedAttributes[i].value = this.getNodeIndex();
			}
		}
		
		//这次是更新深度及广度信息
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				this.branches[i].nodes[j].setBranchIndex(i);
				this.branches[i].nodes[j].setNodeIndex(j);
			}
		}
		
		/*
		//更新控制节点的数据

		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(this.branches[i].nodes[j].getNodeType() == "Control"){
					var maxControlExtent;
					for(var k=0; k<this.branches[i].nodes[j].endNodes.length; k++){
						maxControlExtent = 1;
						
						//现在要找到从本节点跟结束节点之间的最大宽度

						var startIndex = j;
						var endIndex = 0;
						
						//这一遍把结束节点标记出来
						for(var m=0; m<this.branches[i].nodes.length; m++){
							if(this.branches[i].nodes[m] == this.branches[i].nodes[j].endNodes[k]){
								endIndex = m;
								break;
							}
						}
						
						if(startIndex < endIndex){
							for(var m=startIndex+1; m<=endIndex; m++){
								if(this.branches[i].nodes[m].getMaxExtent() > maxControlExtent-1){
									maxControlExtent = this.branches[i].nodes[m].getMaxExtent() + 1;
								}
							}
						}else{
							for(var m=endIndex; m<startIndex; m++){
								if(this.branches[i].nodes[m].getMaxExtent() > maxControlExtent-1){
									maxControlExtent = this.branches[i].nodes[m].getMaxExtent() + 1;
								}
							}
						}
						
						this.branches[i].nodes[j].controlLines[k].maxExtent = maxControlExtent;
					}
					this.branches[i].nodes[j].repaint();
				}
			}
		}
		*/
		
		//更新控制线条的数据

		for(var branchIndex=0; branchIndex<this.branches.length; branchIndex++){
			var lines = this.gatherControlLines(branchIndex);
			lines = this.generateControlParam(lines, branchIndex);
	
			for(var i=0; i<lines.length; i++){
				var startNode = this.branches[branchIndex].nodes[lines[i].startIndex];
				
				for(var j=0; j<startNode.controlLines.length; j++){
					if(this.branches[branchIndex].nodes[lines[i].endIndex] == startNode.controlLines[j].endNode){
						startNode.controlLines[j].maxExtent = lines[i].extent;
						startNode.controlLines[j].hDirection = lines[i].direction;
						startNode.repaint();
					}
				}
			}
		}
		
		//更新节点的广度和深度数据
		this.setMaxExtent(0);
		this.setMaxDepth(1);
		for(var i=0; i<this.branches.length; i++){
			this.branches[i].maxDepth = 1;
			this.branches[i].maxExtent = 1;
			
			for(var j=0; j<this.branches[i].nodes.length; j++){
				this.branches[i].maxDepth += this.branches[i].nodes[j].getMaxDepth();
				
				if(this.branches[i].nodes[j].getMaxExtent() > this.branches[i].maxExtent){
					this.branches[i].maxExtent = this.branches[i].nodes[j].getMaxExtent();
				}
			}
			
			this.setMaxExtent(this.getMaxExtent() + this.branches[i].maxExtent);
			
			if(this.branches[i].maxDepth > this.getMaxDepth() - 1){
				this.setMaxDepth(this.branches[i].maxDepth + 1);
			}
		}
		
		//先计算一下内部基准坐标

		if(this.branches.length > 1){
			this.baseLeft = -(this.getMaxExtent()-this.branches[0].maxExtent) * this.getSpaceX() / 2;
		}else{
			this.baseLeft = 0;
		}
		this.baseTop = 0;
		
		//更新节点的横向位置数据

		this.branches[0].innerExtent = 0;
		for(var i=1; i<this.branches.length; i++){
			this.branches[i].innerExtent = this.branches[i-1].innerExtent
				 + (this.branches[i].maxExtent + this.branches[i-1].maxExtent)/2;
		}
		
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				this.branches[i].nodes[j].setInnerExtent(this.branches[i].innerExtent);
			}
		}
		
		//更新节点的纵向位置数据

		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(j == 0){
					this.branches[i].nodes[j].setInnerDepth(0);
				}else{
					this.branches[i].nodes[j].setInnerDepth(this.branches[i].nodes[j-1].getInnerDepth() + this.branches[i].nodes[j-1].getMaxDepth());
				}
			}
		}
		
		//刷新节点数据
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				this.branches[i].nodes[j].repaint();
			}
		}
	}
	
	//重绘分支结构
	this.repaint = function(){
		//如果折叠，不必考虑子节点跟线条了

		if(this.collapsed){
			this.collapsePanel.style.pixelLeft = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX();
			this.collapsePanel.style.pixelTop = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY();
			return;
		}
		
		//更新自身位置
		this.panel.style.pixelLeft = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX();
		this.panel.style.pixelTop = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY();
		
		//遍历一下，看看是否还有节点或者线条没有添加上来

		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(!this.panel.contains(this.branches[i].nodes[j].panel)){
					this.panel.appendChild(this.branches[i].nodes[j].panel);
				}
			}
			
			for(var j=0; j<this.branches[i].lines.length; j++){
				if(!this.panel.contains(this.branches[i].lines[j].line)){
					this.panel.appendChild(this.branches[i].lines[j].line);
				}
			}
		}
		
		this.updateData();
		
		//重新设置主图标的坐标
		this.icon.style.pixelLeft = 2;		//实际上这个2不是常数，是它的相对横坐标坐标加上this.baseLeft后的结果
		this.icon.style.pixelTop = this.baseTop + 5;
		
		//刷新关系图标的位置

		this.relationIcon.style.pixelLeft = 10;			//实际上这个10不是常数，是它的相对横坐标坐标加上this.baseLeft后的结果
		this.relationIcon.style.pixelTop = this.baseTop + (this.getMaxDepth()-1)*this.getSpaceY() + 10;
		
		//刷新上面一段竖线的位置
		var startX = 18;
		var startY = this.baseTop + 19;
		var endX = startX;
		var endY = this.baseTop + 25;
		topLine.from = startX + "," + startY;
		topLine.to = endX + "," + endY;
		
		//分支数目不足两个的时候，上下两边的水平线是不该显示出来的
		if(this.branches.length <= 1){
			topHorizontalLine.from = "0, 0";
			topHorizontalLine.to = "0, 0";
			leftHorizontalLine.from = "0, 0";
			leftHorizontalLine.to = "0, 0";
			rightHorizontalLine.from = "0, 0";
			rightHorizontalLine.to = "0, 0";
		}else{
			//刷新上面水平线条的位置

			startX = this.baseLeft + (this.branches[0].innerExtent + 1/2)*this.getSpaceX() - 32;
			startY = this.baseTop + 25;
			
			endX = this.baseLeft + (this.branches[this.branches.length-1].innerExtent+1/2)*this.getSpaceX() - 32;
			endY = startY;
			
			topHorizontalLine.from = startX + "," + startY;
			topHorizontalLine.to = endX + "," + endY;
			
			//下面左边的水平线
			startY = this.baseTop + (this.getMaxDepth()-1)*this.getSpaceY() + 18;
			endY = startY;
			
			leftHorizontalLine.from = startX + "," + startY;
			leftHorizontalLine.to = 6 + "," + endY;
			
			//下面右边的水平线			
			rightHorizontalLine.from = 29 + "," + startY;
			if(endX < 29){
				endX = 29;
			}
			rightHorizontalLine.to = endX + "," + endY;
		}
		
		//添加分支图标的位置更新

		this.iconAdd.style.pixelLeft = endX + 30;
		
		//分支重绘
		for(var i=0; i<this.branches.length; i++){
			startX = this.baseLeft + (this.branches[i].innerExtent+1/2)*this.getSpaceX() - 32;
			startY = this.baseTop + 25;
			
			endX = startX;
			endY = this.baseTop + 30;
			
			this.branches[i].topLine.from = startX + "," + startY;
			this.branches[i].topLine.to = endX + "," + endY;
			
			this.branches[i].icon.style.pixelLeft = startX - 7;
			this.branches[i].icon.style.pixelTop = startY + 5;
			
			startY = this.baseTop + 46;
			endY =  this.baseTop + (this.getMaxDepth()-1)*this.getSpaceY() + 18;
			
			//中间的一根线要短一些

			if(2*i == this.branches.length-1){
				//endY -= 10;
			}
			
			//刷新节点
			for(var j=0; j<this.branches[i].nodes.length; j++){
				var left = this.baseLeft;
				var top = this.getSpaceY();
				
				this.branches[i].nodes[j].setOffsetLeft(left);
				this.branches[i].nodes[j].setOffsetTop(top);
				this.branches[i].nodes[j].setSpaceX(this.getSpaceX());
				this.branches[i].nodes[j].setSpaceY(this.getSpaceY());
				this.branches[i].nodes[j].updateData();
				this.branches[i].nodes[j].repaint();
			}
			
			//每个分支上第一根线条没有起始节点，最后一根线条没有结束节点，需要在这里设置坐标
			for(var j=0; j<this.branches[i].lines.length; j++){
				if(this.branches[i].lines[j].startNode == null){
					this.branches[i].lines[j].line.from = startX + "," + startY;
				}
				
				if(this.branches[i].lines[j].endNode == null){
					this.branches[i].lines[j].line.to = endX + "," + endY;
					this.branches[i].lines[j].setArrow(false);
				}
			}
			
			for(var j=0; j<this.branches[i].lines.length; j++){
				if(this.branches[i].lines[j].endNode == null){
					this.branches[i].lines[j].setArrow(false);
				}else{
					this.branches[i].lines[j].setArrow(true);
				}
				this.branches[i].lines[j].setSpaceX(this.getSpaceX());
				this.branches[i].lines[j].setSpaceY(this.getSpaceY());
				this.branches[i].lines[j].repaint();
			}
		}
		
		for(var i=0; i<this.branches.length; i++){
			//刷新控制节点，是为了再刷新一次控制线条

			for(var j=0; j<this.branches[i].nodes.length; j++){
				if(this.branches[i].nodes[j].getNodeType() == "Control"){
					var left = this.baseLeft;
					var top = this.getSpaceY();
					
					this.branches[i].nodes[j].setOffsetLeft(left);
					this.branches[i].nodes[j].setOffsetTop(top);
					this.branches[i].nodes[j].setSpaceX(this.getSpaceX());
					this.branches[i].nodes[j].setSpaceY(this.getSpaceY());
					this.branches[i].nodes[j].updateData();
					this.branches[i].nodes[j].repaint();
				}
			}
		}
		
		//对外提供的连接点位置刷新，这里的坐标系跟所有内部节点、线条的坐标系不同，要按照父节点的算！

		this.topPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.topPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 3;
		
		this.bottomPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 18;
		this.bottomPoint.y = this.getOffsetTop() + (this.getInnerDepth()+this.getMaxDepth()-1)*this.getSpaceY() + 30;
		
		this.leftPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() - 1;
		this.leftPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 10;
		
		this.rightPoint.x = this.getOffsetLeft() + this.getInnerExtent()*this.getSpaceX() + 37;
		this.rightPoint.y = this.getOffsetTop() + this.getInnerDepth()*this.getSpaceY() + 10;
	}
	
	//生成节点的XPDL
	this.generateActivityXPDL = function(){
		//并行节点首先要生成一个路由节点

		var xpdl = "<Activity Id='" + this.activity.id + "' Name='" + this.activity.name + "'>";
		
		xpdl += "<Route />";
		
		//流入流出方式
		if(this.activity.transitionRestrictions.length > 0){
			xpdl += "<TransitionRestrictions>";
			for(var i=0; i<this.activity.transitionRestrictions.length; i++){
				xpdl += "<TransitionRestriction>"
					 + "<Join Type='"
					 + this.activity.transitionRestrictions[i].joinType
					 + "'/><Split Type='"
					 + this.activity.transitionRestrictions[i].splitType
					 + "'/></TransitionRestriction>";
			}
			xpdl += "</TransitionRestrictions>";
		}else{
			xpdl += "<TransitionRestrictions>";
			xpdl += "<TransitionRestriction>"
				 + "<Join Type='AND'/><Split Type='AND'/></TransitionRestriction>";
			xpdl += "</TransitionRestrictions>";
		}
		
		//加上描述
		if(this.activity.description){
			xpdl += "<Description>" + this.activity.description + "</Description>";
		}else{
			xpdl += "<Description />"
		}
		
		//扩展属性

		xpdl += "<xpdl:ExtendedAttributes>";
		
		//加上本节点所处的分支序号和节点序号

		for(var i=0; i<this.activity.extendedAttributes.length; i++){
			xpdl += "<xpdl:ExtendedAttribute Name='" + this.activity.extendedAttributes[i].name
				 + "' Value='" + this.activity.extendedAttributes[i].value +"' />";
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		xpdl += "</Activity>";
		
		//现在开始处理各分支上的子节点

		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				xpdl += this.branches[i].nodes[j].generateActivityXPDL();
			}
		}
		
		//最后要生成并行节点最后的条件合并节点，这个也作为路由节点处理
		xpdl += "<Activity Id='" + this.relationData.id + "' Name='" + this.relationData.name + "'>";
		
		xpdl += "<Route />";
		
		//流入流出方式
		if(this.relationData.transitionRestrictions.length > 0){
			xpdl += "<TransitionRestrictions>";
			for(var i=0; i<this.relationData.transitionRestrictions.length; i++){
				xpdl += "<TransitionRestriction>"
					 + "<Join Type='"
					 + this.relationData.transitionRestrictions[i].joinType
					 + "'/><Split Type='"
					 + this.relationData.transitionRestrictions[i].splitType
					 + "'/></TransitionRestriction>";
			}
			xpdl += "</TransitionRestrictions>";
		}else{
			xpdl += "<TransitionRestrictions>";
			xpdl += "<TransitionRestriction>"
				 + "<Join Type='AND'/><Split Type='AND'/></TransitionRestriction>";
			xpdl += "</TransitionRestrictions>";
		}
		
		//加上描述
		if(this.relationData.description){
			xpdl += "<Description>" + this.relationData.description + "</Description>";
		}else{
			xpdl += "<Description />"
		}
		
		//扩展属性

		xpdl += "<xpdl:ExtendedAttributes>";
		
		//其他扩展属性

		for(var i=0; i<this.relationData.extendedAttributes.length; i++){
			xpdl += "<xpdl:ExtendedAttribute Name='" + this.relationData.extendedAttributes[i].name
				 + "' Value='" + this.relationData.extendedAttributes[i].value +"' />";
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		xpdl += "</Activity>";
		
		return xpdl;
	}
	
	//生成线条的XPDL
	this.generateTransitionXPDL = function(){
		var xpdl = "";
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].lines.length; j++){
				xpdl += "<Transition Id='"+ this.branches[i].lines[j].transition.id
					 + "' Name='" + this.branches[i].lines[j].transition.name + "' From='";
				
				//每个分支的第一条线的开始节点是分支节点自身
				if(this.branches[i].lines[j].startNode == null){
					xpdl += this.activity.id;
				}else if(this.branches[i].lines[j].startNode.getNodeType() == "Parallel"){
					//这一步是什么意思，就是说如果开始节点是并行节点，那线条不是直接从它出来的，而是从下面的合并节点
					xpdl += this.branches[i].lines[j].startNode.relationData.id;
				}else{
					xpdl += this.branches[i].lines[j].startNode.activity.id;
				}
				xpdl += "' To='";
				
				//每个分支的最后一条线的结束节点是关系节点
				if(this.branches[i].lines[j].endNode == null){
					xpdl += this.relationData.id;
				}else{
					xpdl += this.branches[i].lines[j].endNode.activity.id;
				}
				xpdl += "'>";
		
				if(this.branches[i].lines[j].transition.condition){
					xpdl += "<xpdl:Condition><xpdl:Xpression>"
						 + "<![CDATA[" + this.branches[i].lines[j].transition.condition + "]]>"
						 + "</xpdl:Xpression></xpdl:Condition>";
				}else{
					xpdl += "<xpdl:Condition><xpdl:Xpression/></xpdl:Condition>";
				}
				
				//加上描述
				if(this.branches[i].lines[j].transition.description){
					xpdl += "<Description>" + this.branches[i].lines[j].transition.description + "</Description>";
				}else{
					xpdl += "<Description />"
				}
				
				//扩展属性

				xpdl += "<xpdl:ExtendedAttributes>";
				for(var k=0; k<this.branches[i].lines[j].transition.extendedAttributes.length; k++){
					xpdl += "<xpdl:ExtendedAttribute Name='" + this.branches[i].lines[j].transition.extendedAttributes[k].name
						 + "' Value='" + this.branches[i].lines[j].transition.extendedAttributes[k].value +"' />";
				}
				xpdl += "</xpdl:ExtendedAttributes>";
				
				xpdl += "</Transition>";
			}
			
			//现在开始处理各分支上的子节点中的线条

			for(var j=0; j<this.branches[i].nodes.length; j++){
				if((this.branches[i].nodes[j].getNodeType() == "Parallel") 
					|| (this.branches[i].nodes[j].getNodeType() == "Control")){
					xpdl += this.branches[i].nodes[j].generateTransitionXPDL();
				}
			}
		}
		return xpdl;
	}
	
	//生成application的XPDL
	this.generateApplicationXPDL = function(){
		var xpdl = "";
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if((this.branches[i].nodes[j].getNodeType() == "Tache")
					|| (this.branches[i].nodes[j].getNodeType() == "Parallel")){
					xpdl += this.branches[i].nodes[j].generateApplicationXPDL();
				}
			}
		}
		return xpdl;
	}
	
	//生成participant的XPDL
	this.generateParticipantXPDL = function(){
		var xpdl = "";
		for(var i=0; i<this.branches.length; i++){
			for(var j=0; j<this.branches[i].nodes.length; j++){
				if((this.branches[i].nodes[j].getNodeType() == "Tache")
					|| (this.branches[i].nodes[j].getNodeType() == "Parallel")){
					xpdl += this.branches[i].nodes[j].generateParticipantXPDL();
				}
			}
		}
		return xpdl;
	}
}