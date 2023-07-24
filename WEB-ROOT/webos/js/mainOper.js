//用户ID,职位ID,特有职位ID
var session1 = GetSession();
var staffId = session1.staff.staffId;
var jobId = session1.job.jobId;
var orgId = session1.org.orgId;
var specialJobId = session1.job.specialJobId;
var swidth =GetScreenWidth();//分辨率宽度
var sheight = GetScreenHeight();//分辨率高度

$(function() {
    //查询菜单信息
    var menuArrStr = callRemoteFunctionNoTrans("com.ztesoft.front.FrontManager","qryMyPrivAllMenu",staffId,jobId,specialJobId,-1);
    var menuArr = eval("("+menuArrStr+")");
	//快捷栏菜单
    var quickMenusArr = callRemoteFunctionNoTrans("com.ztesoft.front.FrontManager","qryMyMenu",staffId);

    UOS_FRONT_PAGE.initMenu(menuArr,quickMenusArr);
    UOS_FRONT_PAGE.init();
});
