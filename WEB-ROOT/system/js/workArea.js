/////////////////////////////////////////////////////
// ZTESoft corp. 2006-02-21
// Author : Xu.fei3
// commits: Implementation of workflow workarea
/////////////////////////////////////////////////////

function WorkArea(){
	_extends(this, AbstractElement);
	this.panel = document.all.workAreaDiv;
	
	//标题
	this.titleDiv = document.all.workAreaTitle;
	
	//这个状态记录了在节点选择栏上的选项
	this.status = "Mouse";
	
	//各种属性
	this.processState = "10B";
	this.editDate = "";
	this.validFromDate = "";
	this.validToDate = "";
	this.description = "";
	this.editUser = "";
	this.version = "";
	
	this.packageHeaderXml = "";
	
	//流程变量
	this.dataFields = new Array();
	
	//流程形参
	this.formalParameters = new Array();
	
	//起始的基准坐标
	this.baseLeft = 100;
	this.baseTop = 20;
	
	//节点的横向和纵向间距
	this.spaceX = 100;
	this.spaceY = 70;
	
	//各类元素的集合
	this.nodes = new Array();					//这个是直属节点
	this.lines = new Array();					//这个是直属线条
	
	//初始化
	this.initialize = function(){
		var startNode = NodeFactory.createNode("Start");
		startNode.setNodeIndex(0);
		startNode.setInnerDepth(0);
		startNode.setSpaceX(this.spaceX);
		startNode.setSpaceY(this.spaceY);
		startNode.setOffsetLeft(this.baseLeft);
		startNode.setOffsetTop(this.baseTop);
		
		var finishNode = NodeFactory.createNode("Finish");
		finishNode.setNodeIndex(1);
		finishNode.setInnerDepth(1);
		finishNode.setSpaceX(this.spaceX);
		finishNode.setSpaceY(this.spaceY);
		finishNode.setOffsetLeft(this.baseLeft);
		finishNode.setOffsetTop(this.baseTop);
		
		var excptionNode = NodeFactory.createNode("Exception");
		excptionNode.setNodeIndex(2);
		excptionNode.setInnerExtent(1);
		excptionNode.setInnerDepth(0);
		excptionNode.setSpaceX(this.spaceX);
		excptionNode.setSpaceY(this.spaceY);
		excptionNode.setOffsetLeft(this.baseLeft);
		excptionNode.setOffsetTop(this.baseTop);
		
		this.insertNode(startNode);
		this.insertNode(finishNode);
		this.insertNode(excptionNode);
		
		this.insertLine(startNode, finishNode);
		this.setTitle("未命名版本");
		
		this.repaint();
	}
	
	//设置标题
	this.setTitle = function(title){
		this.titleDiv.innerText = title;
	}
	
	//获取标题
	this.getTitle = function(){
		return this.titleDiv.innerText;
	}
	
	//显示插入点
	this.showInsertPosition = function(flag){
		//直属的线条
		for(var i=0; i<this.lines.length; i++){
			this.lines[i].repaint();
			if(flag){
				if(!this.panel.contains(this.lines[i].oval)){
					this.panel.appendChild(this.lines[i].oval);
				}
			}else{
				if(this.panel.contains(this.lines[i].oval)){
					this.panel.removeChild(this.lines[i].oval);
				}
			}
		}
		
		//复合节点显示插入点
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Parallel"){
				this.nodes[i].showInsertPosition(flag);
			}
		}
	}
	
	//是否显示给定节点的兄弟节点
	this.showSibling = function(node, flag){
		for(var i=0; i<this.nodes.length; i++){
			if((this.nodes[i] != node)){
				if((this.nodes[i].getNodeType() != "Start")
				 && (this.nodes[i].getNodeType() != "Exception")
				 && (this.nodes[i].getNodeType() != "Control")){
					this.nodes[i].setSelected(flag);
				}
			}
		}
	}
	
	//插入节点，因为工作区是单分支的
	this.insertNode = function(node){
		//节点排序插入，其下方所有节点下移
		for(var i=this.nodes.length; i>node.getNodeIndex(); i--){
			this.nodes[i] = this.nodes[i-1];
		}
		this.nodes[node.getNodeIndex()] = node;
		
		node.repaint();
		this.panel.appendChild(node.panel);
		
		controller.allNodes.push(node);
	}
	
	//插入线条
	this.insertLine = function(startNode, endNode){
		var flowLine = new FlowLine();
		flowLine.startNode = startNode;
		flowLine.endNode = endNode;
		
		var attribute = new Object();
		attribute.name = "parentId";
		attribute.value = "0";
		flowLine.transition.extendedAttributes.push(attribute);
		
		if(arguments.length == 3){
			flowLine.transition = arguments[2];
		}
		
		flowLine.repaint();
		this.panel.appendChild(flowLine.line);
		
		//线条其实是不需要排序插入的
		this.lines.push(flowLine);
		controller.allLines.push(flowLine);
	}
	
	//删除节点
	this.removeNode = function(node){
		//先从全局引用数组中去掉这个节点
		for(var i=0; i<controller.allNodes.length; i++){
			if(controller.allNodes[i] == node){
				controller.allNodes.splice(i, 1);
				break;
			}
		}
		
		//如果有控制节点的线条指向它，要先把线条拿掉
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.nodes[i].endNodes.length; j++){
					if(this.nodes[i].endNodes[j] == node){
						this.nodes[i].removeControlLine(j);
						break;
					}
				}
			}
		}
		
		//从工作区的节点列表中删除
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i] == node){
				//非简单节点，要把内部的元素先清除
				if((this.nodes[i].getNodeType() == "Parallel") || (this.nodes[i].getNodeType() == "Control")){
					this.nodes[i].clear();
				}
				
				this.panel.removeChild(node.panel);
				this.nodes.splice(i, 1);
				break;
			}
		}
	}
	
	//删除线条
	this.removeLine = function(line){
		//先从全局引用数组中去掉这个线条
		for(var i=0; i<controller.allLines.length; i++){
			if(controller.allLines[i] == line){
				controller.allLines.splice(i, 1);
				break;
			}
		}
		
		//从工作区的线条列表中删除
		for(var i=0; i<this.lines.length; i++){
			if(this.lines[i] == line){
				this.panel.removeChild(line.line);
				this.lines.splice(i, 1);
				break;
			}
		}
	}

	//清除流程图
	this.clear = function(){
		this.showInsertPosition(false);
		
		for(var i=0; i<this.lines.length; i++){
			if(this.panel.contains(this.lines[i].line)){
				this.panel.removeChild(this.lines[i].line);
			}
		}
		this.lines = new Array();
		
		for(var i=0; i<this.nodes.length; i++){
			if((this.nodes[i].getNodeType() == "Control") || (this.nodes[i].getNodeType() == "Parallel")){
				this.nodes[i].clear();
			}
			
			if(this.panel.contains(this.nodes[i].panel)){
				this.panel.removeChild(this.nodes[i].panel);
			}
		}
		this.nodes = new Array();
		
		this.status = "Mouse";
		
		this.dataFields = new Array();
		this.formalParameters = new Array();
	}
	
	//设置流程扩展属性
	this.setExtendedArributes = function(){
		//开始节点
		var startAttribute = new Object();
		startAttribute.name = "ExStartOfWF";
		
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Start"){
				startAttribute.value = this.nodes[i].activity.id;
				break;
			}
		}
		
		for(var i=0; i<this.extendedAttributes.length; i++){
			if(this.extendedAttributes[i].name == "ExStartOfWF"){
				this.extendedAttributes.splice(i, 1);
				break;
			}
		}
		this.extendedAttributes.push(startAttribute);
		
		//异常节点
		var exceptionAttribute = new Object();
		exceptionAttribute.name = "ExExceptionOfWF";
		
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Exception"){
				exceptionAttribute.value = this.nodes[i].activity.id;
				break;
			}
		}
		
		for(var i=0; i<this.extendedAttributes.length; i++){
			if(this.extendedAttributes[i].name == "ExExceptionOfWF"){
				this.extendedAttributes.splice(i, 1);
				break;
			}
		}
		this.extendedAttributes.push(exceptionAttribute);
		
		//结束节点
		var endAttributes = new Object();
		endAttributes.name = "ExEndOfWFs";
		endAttributes.value = new Array();
		
		for(var i=0; i<this.extendedAttributes.length; i++){
			if(this.extendedAttributes[i].name == "ExEndOfWFs"){
				this.extendedAttributes.splice(i, 1);
				break;
			}
		}
		
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Finish"){
				var endAttribute = new Object();
				endAttribute.name = "ExEndOfWF";
				endAttribute.value = this.nodes[i].activity.id;
				endAttributes.value.push(endAttribute);
			}
		}
		this.extendedAttributes.push(endAttributes);
		
		//版本状态
		var stateAttribute = new Object();
		stateAttribute.name = "ExStateOfWF";
		
		stateAttribute.value = this.processState;
		
		for(var i=0; i<this.extendedAttributes.length; i++){
			if(this.extendedAttributes[i].name == "ExStateOfWF"){
				this.extendedAttributes.splice(i, 1);
				break;
			}
		}
		this.extendedAttributes.push(stateAttribute);
	}
	
	//生成控制线条的布局参数
	this.generateControlParam = function(lines){
		//首先，确定控制线条的布局优先级规则
		//优先级最高的是起始节点A与终止节点B之间没有其他控制线条的
		//其次是起始节点A与终止节点B之间有控制线条，但是这些控制线条的起始与终止节点都在A、B之间
		//最后是其他控制线条
		//在同一类型的控制线条之间，布局的优先级顺序按照上>下、内>外的规则
		
		//把每根线条的优先级确定出来
		for(var i=0; i<lines.length; i++){
			lines[i].extent = this.getMaxNoControlExtent(lines[i]) + 1;
			lines[i].type = this.judgeControlLineType(lines[i]);
		}
		
		//布局数组
		this.leftExtentArray = new Array();
		this.rightExtentArray = new Array();
		
		var index = 0;
		
		//对布局数组进行赋值
		//第一轮，针对并行节点的赋值
		for(var i=0; i<this.nodes.length-1; i++){
			if(this.nodes[i].getNodeType() == "Parallel"){
				var leftExtent = this.nodes[i].getMaxExtent() - 1;
				var rightExtent = this.nodes[i].getMaxExtent() - 1;
		
				//并行节点的开始节点
				this.leftExtentArray[index] = 0;
				this.rightExtentArray[index] = 0;
				index ++;
		
				//并行节点的内部节点
				for(var j=1; j<=this.nodes[i].getMaxDepth(); j++){
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
	this.countNodeDepth = function(nodeIndex){
		var index = 0;
		for(var i=0; i<nodeIndex; i++){
			index += this.nodes[i].getMaxDepth();
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
	this.getMaxNoControlExtent = function(line){
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
			if(this.nodes[i].getNodeType() != "Control"){
				if(this.nodes[i].getMaxExtent() > maxExtent){
					maxExtent = this.nodes[i].getMaxExtent();
				}
			}
		}
		
		if(line.endIndex < line.startIndex){
			if(this.nodes[line.endIndex].getMaxExtent() > maxExtent){
				maxExtent = this.nodes[line.endIndex].getMaxExtent();
			}
		}
		
		return maxExtent;
	}
	
	//判断一条控制线条的优先级
	this.judgeControlLineType = function(line){
		var type = 1;
		
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.nodes[i].controlLines.length; j++){
					type = 2;
					var startIndex = this.nodes[i].controlLines[j].startNode.getNodeIndex();
					var endIndex = this.nodes[i].controlLines[j].endNode.getNodeIndex();
					
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
	this.gatherControlLines = function(){
		var lines = new Array();
		
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Control"){
				for(var j=0; j<this.nodes[i].controlLines.length; j++){
					var line = new Object();
					line.startIndex = this.nodes[i].controlLines[j].startNode.getNodeIndex();
					line.endIndex = this.nodes[i].controlLines[j].endNode.getNodeIndex();
					
					lines.push(line);
				}
			}
		}
		
		return lines;
	}
	
	//更新数据
	this.updateData = function(){
		//在这个方法里，两次调用了各节点的更新数据方法，这一次更新的是节点的深度和广度数据
		for(var i=0; i<this.nodes.length; i++){
			this.nodes[i].setNodeIndex(i);
			this.nodes[i].setSpaceX(this.spaceX);
			this.nodes[i].setSpaceY(this.spaceY);
			this.nodes[i].updateData();
		}
		
		//计算工作区最大节点广度
		var maxExtent = 0;
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getMaxExtent() > maxExtent){
				maxExtent = this.nodes[i].getMaxExtent();
			}
		}
		
		for(var i=1; i<this.nodes.length; i++){
			this.nodes[i].setInnerDepth(this.nodes[i-1].getInnerDepth() + this.nodes[i-1].getMaxDepth());
		}
		
		//更新节点位置
		for(var i=0; i<this.nodes.length; i++){
			this.nodes[i].setOffsetLeft(this.baseLeft + ((maxExtent-1)/2)*this.spaceX);
			this.nodes[i].setOffsetTop(this.baseTop);
			
			//这一次更新的是位置数据
			this.nodes[i].updateData();
		}
		
		var lines = this.gatherControlLines();
		lines = this.generateControlParam(lines);

		for(var i=0; i<lines.length; i++){
			var startNode = this.nodes[lines[i].startIndex];
			
			for(var j=0; j<startNode.controlLines.length; j++){
				if(this.nodes[lines[i].endIndex] == startNode.controlLines[j].endNode){
					startNode.controlLines[j].maxExtent = lines[i].extent;
					startNode.controlLines[j].hDirection = lines[i].direction;
					startNode.repaint();
				}
			}
		}
	}
	
	//重绘工作区
	this.repaint = function(){
		this.updateData();
		
		//遍历一下，看看是否还有节点或者线条没有添加上来
		for(var i=0; i<this.nodes.length; i++){
			if(!this.panel.contains(this.nodes[i].panel)){
				this.panel.appendChild(this.nodes[i].panel);
			}
		}
		
		for(var i=0; i<this.lines.length; i++){
			if(!this.panel.contains(this.lines[i].line)){
				this.panel.appendChild(this.lines[i].line);
			}
		}
		
		//这个是异常节点，放在开始节点的右边两格
		this.nodes[this.nodes.length-1].setInnerExtent(this.nodes[0].getInnerExtent()+2);
		this.nodes[this.nodes.length-1].setInnerDepth(this.nodes[0].getInnerDepth());
		
		//重绘子节点
		for(var i=0; i<this.nodes.length; i++){
			this.nodes[i].repaint();
		}
		
		//重绘线条
		for(var i=0; i<this.lines.length; i++){
			this.lines[i].repaint();
		}
		
		//重绘控制线条
		for(var i=0; i<this.nodes.length; i++){
			if(this.nodes[i].getNodeType() == "Control"){
				//调用这个节点的重绘方法是为了让它包含的线条重绘
				this.nodes[i].repaint();
			}
		}
	}
	
	//生成XPDL
	this.generateXPDL = function(){
		this.setExtendedArributes();
			 
		var headerXml = "<ProcessHeader DurationUnit='m'>"
			 + "<Created>" + this.editDate + "</Created>"
			 + "<Description>" + this.description + "</Description>"
			 + "<Priority>" + "1" + "</Priority>"
			 + "<ValidFrom>" + this.validFromDate + "</ValidFrom>"
       + "<ValidTo>" + this.validToDate + "</ValidTo>"
       + "</ProcessHeader>";
    
    var redefinableHeaderXml = "<RedefinableHeader PublicationStatus='UNDER_TEST'>"
       + "<Author>" + this.editUser + "</Author>"
       + "<Version>" + this.version + "</Version>"
       + "<Countrykey>GB</Countrykey>"
       + "</RedefinableHeader>";
    
    this.packageHeaderXml = "<PackageHeader DurationUnit='m'>"
    	 + "<XPDLVersion>1.0</XPDLVersion>"
			 + "<Vendor>ZTERC UOSFlow V5.0</Vendor>"
			 + "<Created>" + this.editDate + "</Created>"
			 + "<Description>" + this.description + "</Description>"
			 + "<Priority>" + "1" + "</Priority>"
			 + "<ValidFrom>" + this.validFromDate + "</ValidFrom>"
       + "<ValidTo>" + this.validToDate + "</ValidTo>"
       + "</PackageHeader>" + redefinableHeaderXml;
       
    //流程变量的XPDL
    var variableXml = "<DataFields>";
    for(var i=0; i<workArea.dataFields.length; i++){
			variableXml += "<DataField Id='" + workArea.dataFields[i].variableName
				 + "' Name='" + workArea.dataFields[i].variableName + "'>";
			
			//数据类型
			variableXml += "<xpdl:DataType>";
			variableXml += "<xpdl:BasicType Type='" + workArea.dataFields[i].dataType + "'/>";
			variableXml += "</xpdl:DataType>";
			
			//初始值
			variableXml += "<InitialValue>" + workArea.dataFields[i].initialValue + "</InitialValue>";
			
			//描述
			variableXml +=
				 "<xpdl:Description>" + workArea.dataFields[i].description + "</xpdl:Description>";
			
			variableXml += "</DataField>";
    }
    variableXml += "</DataFields>";
    
    //流程参数的XPDL
    var parameterXml = "<xpdl:FormalParameters>";
    for(var i=0; i<workArea.formalParameters.length; i++){
			parameterXml += "<xpdl:FormalParameter Id='" + workArea.formalParameters[i].paramName
				 + "' Name='" + workArea.formalParameters[i].paramName
				 + "' Mode='" + workArea.formalParameters[i].paramType + "'>";
			
			//数据类型
			parameterXml += "<xpdl:DataType>";
			parameterXml += "<xpdl:BasicType Type='" + workArea.formalParameters[i].dataType + "'/>";
			parameterXml += "</xpdl:DataType>";
			
			//描述
			parameterXml +=
				 "<xpdl:Description>" + workArea.formalParameters[i].description + "</xpdl:Description>";
			
			parameterXml += "</xpdl:FormalParameter>";
    }
    parameterXml += "</xpdl:FormalParameters>";
       
		//参与者的XPDL
		var participantsXml = "<Participants>";
		for(var i=0; i<this.nodes.length; i++){
			if((this.nodes[i].getNodeType() == "Tache") || (this.nodes[i].getNodeType() == "Parallel")){
				participantsXml += this.nodes[i].generateParticipantXPDL();
			}
		}		
		participantsXml += "</Participants>";
		
		//应用程序的XPDL
		var applicationsXml = "<Applications>";
		for(var i=0; i<this.nodes.length; i++){
			if((this.nodes[i].getNodeType() == "Tache") || (this.nodes[i].getNodeType() == "Parallel")){
				applicationsXml += this.nodes[i].generateApplicationXPDL();
			}
		}
		applicationsXml += "</Applications>"
		
		//活动的XPDL
		var activitiesXml = "<Activities>";
		for(var i=0; i<this.nodes.length; i++){
			activitiesXml += this.nodes[i].generateActivityXPDL();
		}
		activitiesXml += "</Activities>";
		
		//转移的XPDL
		var transitionsXml = "<Transitions>";
		for(var i=0; i<this.lines.length; i++){
			transitionsXml += this.lines[i].generateTransitionXPDL();
		}
		for(var i=0; i<this.nodes.length; i++){
			if((this.nodes[i].getNodeType() == "Parallel") || ((this.nodes[i].getNodeType() == "Control"))){
				transitionsXml += this.nodes[i].generateTransitionXPDL();
			}
		}
		transitionsXml += "</Transitions>";
		
		var xpdl;
		
		xpdl = "";			 
			 
		xpdl += headerXml + redefinableHeaderXml + parameterXml + participantsXml + variableXml + applicationsXml + activitiesXml + transitionsXml;
		
		//扩展属性
		xpdl += "<xpdl:ExtendedAttributes>";
		for(var i=0; i<this.extendedAttributes.length; i++){
			if(this.extendedAttributes[i].name == "ExEndOfWFs"){
				xpdl += "<xpdl:ExtendedAttribute Name='" + this.extendedAttributes[i].name + "'>";
				xpdl += "<xpdl:ExEndOfWFs>";
				
				//结束节点
				var ends = this.extendedAttributes[i].value;
				
				for(var j=0; j<ends.length; j++){
					xpdl += "<xpdl:ExEndOfWF Name='" + ends[j].name
						 + "' Value='" + ends[j].value + "' />";
				}
				xpdl += "</xpdl:ExEndOfWFs>";
				xpdl += "</xpdl:ExtendedAttribute>";
				
			}else{
				xpdl += "<xpdl:ExtendedAttribute Name='" + this.extendedAttributes[i].name
					 + "' Value='" + this.extendedAttributes[i].value + "' />";
			}
		}
		xpdl += "</xpdl:ExtendedAttributes>";
		
		return xpdl;
	}
	
	//流程图导出
	this.exportFlow = function(){
		var html = "<html xmlns:v='urn:schemas-microsoft-com:vml'>"
			+ "<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'>"
			+ "<title>" + this.titleDiv.innerText + "</title>"
			+ "<style>v\\:*{Behavior: url(#default#VML);}</style></head><body>"
			+ this.panel.outerHTML
			+ "</body></html>";
		
		document.frames.htmlFrame.document.all.inputHtml.value = html;
		document.frames.htmlFrame.document.all.htmlForm.submit();
	}
}