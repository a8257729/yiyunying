/////////////////////////////////////////////////////
// ZTESoft corp. 2005-9-22
// Author : Xu.fei3
// commits: Constants of the whole designer
/////////////////////////////////////////////////////

//下面是一些开关常数
var TACHEINSERTLIMIT = true;									//是否限制流程图中不能出现串行的同样环节

//这下面放的是跟界面相关的常量
var NODEBARICONBASE = "./js/resources/";						//节点栏里面的图标路径
var FLOWNODEICONBASE = "./js/flowElement/resources/";			//流程节点的图标路径

//这下面开始放一些跟流程相关的常量
var JOINTYPE_AND = "and";
var JOINTYPE_OR = "or";
var JOINTYPE_XOR = "xor";

var SPLIT_TYPE_AND = "and";
var SPLIT_TYPE_OR = "or";
var SPLIT_TYPE_XOR = "xor";

//下面是跟流程库有关的常量
var AREA = 0;
var CATALOG = 1;
var TEMPLATE = 2;
var VERSION = 3;

//这里是环节参与者模板的编码
var TACHE_STARTER = 1;
var TACHE_STARTER_ORGANIZATION = 2;
var TACHE_STARTER_POSITION = 3;
var TACHE_PERFORMER = 4;
var TACHE_PERFORMER_ORGANIZATION = 5;
var TACHE_PERFORMER_POSITION = 6;
var ORGANIZATION = 7;
var POSITION = 8;
var STAFF = 9;
var LAST_TACHE = 10;
var RULE = 11;


//这个公用方法暂时放在这里
//这个方法取得的是前面添加了字符的UUID
function getUUID(){
	var now = new Date();
	var uuid = "A" + parseInt(Math.random()*1e5) + "-" + now.getSeconds() + "-" + parseInt(Math.random()*1e5) + "-" + now.getMilliseconds() + "-" + parseInt(Math.random()*1e5);
	//var uuid = callRemoteFunction("com.zterc.uos.workflow.processdefinition.client.ProcessDefinitionClient", "getActivityId");
	return uuid;
}