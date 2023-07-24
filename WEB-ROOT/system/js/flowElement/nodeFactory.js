/////////////////////////////////////////////////////
// ZTESoft corp. 2005-8-29
// Author : Xu.fei3
// commits: A factory that creates workflow nodes
/////////////////////////////////////////////////////

function NodeFactory(){
	return null;
}

//This is a static method that creates nodes
NodeFactory.createNode = function(nodeType){
	switch(nodeType){
		case "Start":{
			return new StartNode();
		}
		case "Finish":{
			return new FinishNode();
		}
		case "Tache":{
			return new TacheNode();
		}
		case "SubFlow":{
			return new SubFlowNode();
		}
		case "Control":{
			return new ControlNode();
		}
		case "Parallel":{
			var parallel =  new ParallelNode();
			parallel.addBranch();
			return parallel;
		}
		case "Exception":{
			return new ExceptionNode();
		}
	}
}