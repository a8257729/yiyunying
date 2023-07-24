/////////////////////////////////////////////////////
// ZTESoft corp. 2006-04-26
// Author : Xu.fei3
// commits: Main module for the web workflow designer
/////////////////////////////////////////////////////


//全局变量
var workArea = new WorkArea();
var controller = new Controller();
var nodeBar = new NodeBar();

var flowOperation = new FlowOperation();
var tacheOperation = new TacheOperation();

//初始化
ExecWait("main()");

//////////////////////////////////////////
//function函数区
function main(){
	document.body.oncontextmenu = function(){
		return false;
	};
	
	controller.clear();
	flowOperation.init();
	tacheOperation.init();
	
	if(window.frameElement != null){
		//在框架中
		document.all.areaTree.height = (window.frameElement.scrollHeight - 105)*0.4;
		document.all.flowLibrary.height = (window.frameElement.scrollHeight - 105)*0.6;
		document.all.tacheLibrary.height = window.frameElement.scrollHeight - 80;
	}else{
		//在模态窗口中
		document.all.areaTree.height = (window.screen.availHeight - 115)*0.4;
		document.all.flowLibrary.height = (window.screen.availHeight - 115)*0.6;
		document.all.tacheLibrary.height = window.screen.availHeight - 90;
	}
}