/////////////////////////////////////////////////////
// ZTESoft corp. 2005-10-19
// Author : Xu.fei3
// commits: Implementation of the workflow node
/////////////////////////////////////////////////////

//所有流程节点的基类，包含通用的成员变量和方法
function AbstractFlowNode(){
	this.activity = new Activity();
	
	var branchIndexAttribute = new Object();
	branchIndexAttribute.name = "branchIndex";
	branchIndexAttribute.value = 0;
	this.activity.extendedAttributes.push(branchIndexAttribute);
	
	var nodeIndexAttribute = new Object();
	nodeIndexAttribute.name = "nodeIndex";
	nodeIndexAttribute.value = 0;
	this.activity.extendedAttributes.push(nodeIndexAttribute);
	
	//上面、下面、左面、右面的连接点，用来连线
	this.topPoint = new Object();
	this.bottomPoint = new Object();
	this.leftPoint = new Object();
	this.rightPoint = new Object();
	
	var maxDepth = 0;				//本节点最大深度
	var maxExtent = 0;			//本节点最大广度

	var branchIndex = 0;		//记录这个节点位于某复合结构中的深度次序
	var nodeIndex = 0;			//记录这个节点位于某复合结构中的广度次序

	var innerDepth = 0;			//记录这个节点位于某复合结构中的深度位置
	var innerExtent = 0;		//记录这个节点位于某复合结构中的广度位置

	var offsetLeft = 0;			//设置基准横坐标
	var offsetTop = 0;			//设置基准纵坐标

	var spaceX = 0;					//节点的横向间距
	var spaceY = 0;					//节点的纵向间距

	//设置节点类型

	this.setNodeType = function(type){
		var attribute = new Object();
		attribute.name = "nodeType";
		attribute.value = type;
		this.activity.extendedAttributes.push(attribute);
	}
	
	//获取节点类型
	this.getNodeType = function(){
		for(var i=0; i<this.activity.extendedAttributes.length; i++){
			if(this.activity.extendedAttributes[i].name == "nodeType"){
				return this.activity.extendedAttributes[i].value;
			}
		}
	}
	
	//获取最大深度
	this.getMaxDepth = function(){
		return maxDepth;
	}
	
	//设置最大深度
	this.setMaxDepth = function(depth){
		maxDepth = depth;
	}
	
	//获取最大广度
	this.getMaxExtent = function(){
		return maxExtent;
	}
	
	//设置最大广度
	this.setMaxExtent = function(extent){
		maxExtent = extent;
	}
	
	//获取深度次序
	this.getBranchIndex = function(){
		return branchIndex;
	}
	
	//设置深度次序
	this.setBranchIndex = function(index){
		branchIndex = index;
	}
	
	//获取广度次序
	this.getNodeIndex = function(){
		return nodeIndex;
	}
	
	//设置广度次序
	this.setNodeIndex = function(index){
		nodeIndex = index;
	}
	
	//获取深度位置
	this.getInnerDepth = function(){
		return innerDepth;
	}
	
	//设置深度位置
	this.setInnerDepth = function(depth){
		innerDepth = depth;
	}
	
	//获取广度位置
	this.getInnerExtent = function(){
		return innerExtent;
	}
	
	//设置广度位置
	this.setInnerExtent = function(extent){
		innerExtent = extent;
	}
	
	//设置左基准坐标
	this.setOffsetLeft = function(left){
		offsetLeft = left;
	}
	
	//获取左基准坐标
	this.getOffsetLeft = function(){
		return offsetLeft;
	}
	
	//设置上基准坐标
	this.setOffsetTop = function(top){
		offsetTop = top;
	}
	
	//获取上基准坐标
	this.getOffsetTop = function(){
		return offsetTop;
	}
	
	//获取横向间距
	this.getSpaceX = function(){
		return spaceX;
	}
	
	//设置横向间距
	this.setSpaceX = function(x){
		spaceX = x;
	}
	
	//获取纵向间距
	this.getSpaceY = function(){
		return spaceY;
	}
	
	//设置纵向间距
	this.setSpaceY = function(y){
		spaceY = y;
	}
}