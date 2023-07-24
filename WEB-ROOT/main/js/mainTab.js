/*tang.lin 打开IM消息提醒窗口 2010-11-03*/
var isClosed = false;//IM是否关闭
var helpIsClosed = true ;  //小助手是否已关闭,默认为关闭var navigIsClosed = true;  //定制导航是否关闭,默认为关闭var imWin;//IM窗口引用
var im;		//
var session1 = new GetSession();
var username = session1.staff.userName;
var testCnt = 0;
var menuOper = new MenuOPer();
var currentModuleId = 0;//当前模块ID
/*
//监控Ext对象
function extObj(){
	var cmpMgr = Ext.ComponentMgr;
	window.parent.frames["topFrame"].document.getElementById('testId').innerHTML = 'ext组件数:'+cmpMgr.all.getCount();
	setTimeout('extObj()',1000);
}
extObj();
*/
var staffId = session1.staff.staffId;
var jobId = session1.job.jobId;
var specialJobId = session1.job.specialJobId;
	var modules = menuOper.queryDBMenu();
	
	var moduleId = GetUrlParameter("moduleId");
	var moduleName = GetUrlParameter("moduleName");
	if(moduleId==null){
		moduleId=modules[0].id;
		moduleName=modules[0].name;
	}
	var mainItems ="" ;
	var type = GetUrlParameter("type");
	moduleId=modules[0].id;
	moduleName=modules[0].name;
	mainItems ="[ mainTab, leftmenu]";
	currentModuleId = moduleId;
	

function MenuOPer(){
	this.queryDBMenu = function(){
		var tmpStr = new Array();
		var recArr = callRemoteFunctionNoTrans("com.ztesoft.iom.funcmanager.MenuManger","queryModuleByStaffId",staffId,jobId,specialJobId);
		
		if(recArr.length<1){ 
			return;
		}
		return recArr;
	}
	
}
var obj = new Object();

function reloadLeftMenu(moduleId,moduleName){
	
	var leftmenu = Ext.getCmp('leftmenu');
	//移除控件解决内存泄漏
	for(var i=0;i<leftmenu.length;i++){
		Ext.destroy(leftmenu.items.get(i));
	}
	var e = Ext.getCmp("treeModule_"+currentModuleId);
	Ext.destroy(e);
	leftmenu.removeAll();
	leftmenu.setTitle(moduleName);
	var moduleTrees = new Array();
	var treeArr = qryMenuByModule(moduleId,moduleName);
	if(Ext.isEmpty(treeArr)||Ext.isEmpty(treeArr.length)){
		Ext.example.msg('','无菜单列表');
		return
	}
	
	for(var i=0;i<treeArr.length;i++){
		moduleTrees.push(treeArr[i]);
	}
	
	
	for(var i=0;i<moduleTrees.length;i++){
		leftmenu.add({title:moduleTrees[i].getRootNode().text,items:[moduleTrees[i]]});
	}		 
	leftmenu.addEvents('nodeClick');
	leftmenu.initTreeEvent();
	leftmenu.doLayout();
	leftmenu.expand();
	currentModuleId=moduleId;
	
}

Ext.onReady(main);

function main() {
	Ext.BLANK_IMAGE_URL = '../resources/images/ext/s.gif';
	Ext.QuickTips.init();
	//Ext.lib.Ajax.defaultPostHeader += ";charset=utf-8";

    	var head = new Ext.Panel( {
    		id : 'head',
			region : 'north',
			border : false,
			html : '',
			height : 20
		});

		var moduleArrHm  = new Array();
		var obj = new Object();
		obj.region = 'south';
		obj.html = ''
	    obj.height = 0
		var foot = new Ext.Panel(obj);
		
  		var moduleTrees = new Array();
		//模块树

		var treeArr = qryMenuByModule(moduleId,moduleName);
	
		 for(var i=0;i<treeArr.length;i++){
		 				
			moduleTrees.push(treeArr[i]);
		 }
			
		
		var leftmenu = new Morik.Office.LeftMenu( {
			id:'leftmenu',
			title : moduleName,
	        trees : moduleTrees
       
		});

		
		var mainTab = null;
			mainTab = new Morik.Office.MainingPanel( {
			id:'mainTab',
			style : 'padding:0 0 0 0',
			autoScroll : true,
			region : 'center',
			deferredRender : false,
			activeTab : 0,
			resizeTabs : true,
			inTabWidth : 100,
			tabWidth : 115,
			minTabWidth: 115,
			plugins: new Ext.ux.TabCloseMenu(),
			enableTabScroll : true,
			items : [{
				id:'mainPage',
				title : '首页',
				closable : false,
				html : '<div id=div_mainPage><iframe src="middle.html" width="100%" height="100%"></div>'
			}]
		});
		
		

		// 5、建立leftmenu和mainTab两者之间的关系
		leftmenu.on("nodeClick", function(nodeAttr) {
			
			if(nodeAttr.showModel ==1){
				var recObj = parent.showModelessDialog(nodeAttr.url,"_blank","dialogHeight="+parent.screen.height+"px;dialogWidth="+parent.screen.width+"px;scroll=yes;resizable=yes;Minimize=yes;Maximize=yes");
			}else{
				mainTab.loadTab(nodeAttr);
			}
		});

		
		// 6、创建布局
		var viewport = new Ext.Viewport( {
			id:'mainView',
			layout : 'border',
			style : 'none',
			items : eval(mainItems)

		});
		
		Ext.getCmp('leftmenu').collapse();
		
}



//根据module id查询菜单
function qryMenuByModule(moduleId,moduleName){
	if(moduleId==114){
		testCnt++;
	}
	var param = new Object();
		param.jobId = jobId;
		param.staffId = staffId;
		param.specialJobId = specialJobId;
		param.moduleId = moduleId;
	//查询所有模块菜单	
	
	var menuArr = invokeAction("/system/QueryMenuByModuleAction",param);
	var treeList = new Array();
	
	//获得成本管理模块菜单 moduleId = 401
	var menuArr2 = menuArr[moduleId];
	

	var menuChildrenArray = new Array();
	if(Ext.isEmpty(menuArr2)||Ext.isEmpty(menuArr2.length)){
		return;
	}
	//生成面板菜单
	for(var k=0;k<menuArr2.length;k++){
		//子菜单

		if(typeof(menuArr2[k].url)!='undefined'){
			var menuNode1 = new Object();
			menuNode1.id = 'mod'+menuArr2[k].id;
			menuNode1.menuId = menuArr2[k].id;
			menuNode1.menuName = menuArr2[k].name;
			menuNode1.text = menuArr2[k].name;
			menuNode1.url = GetRemoteURL(menuArr2[k].url); 
			menuNode1.showModel =  menuArr2[k].showModel;
			menuNode1.leaf = true;
			menuChildrenArray.push(menuNode1);
			continue;
		}
		var subModules = menuArr[menuArr2[k].id];
		
		var menuNodes = new Array();
		if(typeof(subModules)!='undefined')	{
			for(var i=0;i<subModules.length;i++){
			var menuNode = new Object();
				menuNode.id = 'mod'+subModules[i].id;
				menuNode.text = subModules[i].name;
				menuNode.menuId = subModules[i].id;
				menuNode.menuName = subModules[i].name;
				
				 
			if(typeof(subModules[i].url)=='undefined'){
				
				menuNode.leaf = false;
				var children = new Array();
				
				var subMenu = menuArr[subModules[i].id];
				if(typeof(subMenu)!='undefined'){
					for(var j=0;j<subMenu.length;j++){
						var childNode = new Object();
						childNode.id = 'menu'+subMenu[j].id;
						childNode.text = subMenu[j].name;
						childNode.url = GetRemoteURL(subMenu[j].url);
						childNode.showModel = subMenu[j].showModel;
						childNode.leaf = true;
						children.push(childNode);
					}
				}
				menuNode.children = children;
				
			}else{
				menuNode.url = GetRemoteURL(subModules[i].url);
				menuNode.showModel = subModules[i].showModel;
				menuNode.leaf = true;
			}
			if(menuNode.leaf==false&&typeof(menuNode.children.length)!='undefined'&&menuNode.children.length!=0){
				menuNodes.push(menuNode);
			}else if(menuNode.leaf==true){
				menuNodes.push(menuNode);
			}
			
			
		}
		}
		
		
			
			var obj = new Object();
		
			obj.border = false;
			obj.rootVisible = false;
			obj.collapsible = false;
			obj.autoScroll = true;
			obj.autoHeight = false;
			//obj.height = 150;
			obj.containerScroll = true;
			obj.root = new Ext.tree.AsyncTreeNode({
			
				text:menuArr2[k].name,
				expanded:false,
				children:menuNodes
			}); 
			
		var tree = new Ext.tree.TreePanel(obj);
		
		if(typeof(menuNodes.length)!='undefined'&&menuNodes.length!=0){
			treeList.push(tree);
		}
		
	}
	var obj = new Object();
			obj.id="treeModule_"+moduleId;
			obj.border = false;
			obj.rootVisible = false;
			obj.collapsible = false;
			obj.autoScroll = true;
			obj.autoHeight = false;
			obj.height = 150;
			obj.containerScroll = true;
			obj.root = new Ext.tree.AsyncTreeNode({
				text:moduleName,
				expanded:false,
				children:menuChildrenArray
			}); 
	var tree = new Ext.tree.TreePanel(obj);
	
	if(typeof(menuChildrenArray.length)!='undefined'&&menuChildrenArray.length!=0){
		treeList.push(tree);
	}
	
	return treeList;
}

function openHelp(){
	if(helpIsClosed){		//如果已关闭,则打开
		helpIsClosed = false;
		showHelp();
	}
}


function showHelp(){
	
	/*
	OpenShowDlg("help.jsp",500,650,window);*/
	
	
	var docPanel = new Ext.Panel({
		title:'帮助文档',
		frame:true,		
		region:'west',
		width:180,
		height:200,
		//anchor: '95%',
		html:"<p><a href=\"javascript:openHelperDoc();\">OSS门户主要功能介绍</a></p><p><a href=\"javascript:downloadFile('OSSMH_INTRODUCTION.doc')\">OSS门户操作手册</a></p>"
	
	});

	var shorcutsPanel = new Ext.Panel({
		title:'快捷功能',
		frame:true,
		region:'center',
		width:180,
		height:200,
		//anchor: '95%',
		html:'<p><a href="javascript:makeNavig()">导航定制</a></p><p><a href="javascript:staffMod()">个人信息修改</a></p><p><a href="javascript:openIM(false)">即时通讯(IM)</a></p>'
	
	});
	
   var win = new Ext.Window({
        title: '门户小助手',
        width:360,
        height:300,
        //border:false,
        frame:true,
        plain:true,
        layout: 'border',
        items: [docPanel,shorcutsPanel,{
        	title:'其它',
        	frame:true,
        	xtype:'panel',
        	region:'south',
   			width:450,
        	html:"<p><a href=\"javascript:downloadFile('OSSMH.url')\">添加快捷桌面</a></p><p><a href=\"javascript:openIM(true)\">在线客户</a></p>" + 
        			 "<p><a href=\"javascript:openWithMe()\">关于我们</a></p>"
        }],
        buttonAlign:'center',
        buttons:[{
        	text: '关闭',
        	handler:function(){
        		win.close();
        	}
        }],
        listeners:{
        	close:function(){
        		helpIsClosed = true;		//表示关闭
        	}
        }
        
    });

    win.show(this);
	
}

function downloadFile(fileName){
	var parObj = new Object();
	parObj.fileName = fileName;
	var result = invokeAction('/document/CheckFileExistAction',parObj);		//判断该文件在服务器上是否存在
	
	if("Y"==result){
		var downloadForm = Ext.getDom("downloadForm");
		downloadForm.fileName.value = fileName;
	    downloadForm.action = "../eomsFileDownload";   
	    downloadForm.target="_self";
	    downloadForm.submit();		
	}else{
		Ext.Msg.show({
			title:'操作提示',
			msg:'该文件已删除,如有需要请与管理员联系!',
			buttons: Ext.MessageBox.OK,
       		icon: Ext.MessageBox.ERROR
		});	 
	}
}

function makeNavig(){
	//如果已关闭
	if(navigIsClosed){
		navigIsClosed = false;
		showNavig();
	}
}

function showNavig(){
	
		var i = 0;
        var navHandler = function(direction) {
            if (direction == -1) {
                i--;
                if (i < 0) { i = 0; }
            }

            if (direction == 1) {
                i++;
                if (i > 3) { i = 3; return false; }
            }

            var btnPrev = Ext.getCmp("move-prev");
			var btnNext = Ext.getCmp("move-next");

            if (i == 0) {
                btnPrev.hide();
            }else {
                btnPrev.show();
            }

			if(i==1 && direction == 1){
				var skins = document.getElementsByName("skin");
				var skinValue = "N";  //默认不设置

				Ext.each(skins,function(skin){
					if(skin.checked==true){
						skinValue = skin.value;
					}
				});
				if("Y"==skinValue){
					changeStyle();
				}
			}

			if(i==2 && direction == 1){
				var navigs = document.getElementsByName("navig");
				var navigValue = "N";  //默认不设置

				Ext.each(navigs,function(navig){
					if(navig.checked==true){
						navigValue = navig.value;
					}
				});
				if("Y"==navigValue ){
					changeNavig();
				}			
			}
			
            if (i == 3) {
                btnNext.setText( "完成");
                btnPrev.hide();
                btnNext.on('click',function(){
                	win.close();
                });
                if(direction == 1){
					var indexPages = document.getElementsByName("indexPage");
					var indexPageValue = "N";  //默认不设置

					Ext.each(indexPages,function(indexPage){
						if(indexPage.checked==true){
							indexPageValue = indexPage.value;
						}
					});
					if("Y"==indexPageValue ){
						changeIndexPage();
					}                
                }
            }else{
				btnNext.setText( "下一步&raquo;");
			}

            card.getLayout().setActiveItem(i);

        };
        
        var card = new Ext.Panel({
        	region: 'center',
            frame:true,
            layout: 'card',
            activeItem: 0, // make sure the active item is set on the container config!
            bodyStyle: 'padding:15px',
            defaults: {
                border: false
            },
            items: [{
                id: 'card-0',
                html: '<h2 align=center>第一步</h2><fieldSet><p><h3>系统皮肤定制</h3></p>' + 
					'<p>&nbsp;&nbsp;&nbsp;&nbsp;系统皮肤设置功能可以允许您给个人、职位、部门进行设置系统的皮肤，' +
					'设置后相应人员或者具备了该职位的人员或者某部门人员登录系统后，皮肤将展显您设置的皮肤。</p>' + 
					'<p><table width="100%"><tr align="center"><td><img width="150px" src="../resources/images/blue.jpg"></td>' +
					'<td><img width="150px" src="../resources/images/olive.jpg"></td>' +
					'<td><img width="150px" src="../resources/images/red.jpg"></td></tr></table></p><hr><p>' +
					'是否设置系统皮肤:<input type="radio" name="skin" value="Y"/>是' +
					'<input type="radio" name="skin" value="N" checked=true/>否</p></fieldSet>' + 
					'<p style="font-size:12px;color:blue;border:0;height:22px;padding-top:2px;">' +
					'<i>不再定制,全部一键恢复默认值(注意:只能恢复个人设置)</i>&nbsp;&nbsp;<input type="button" onclick="setDefault()" ' +
					'style="background:white url(../resources/images/button_03.gif) no-repeat;border:0;height:22px;width:60px; padding-top:2px;" value="一键恢复"></p>'
            },{
                id: 'card-1',
                html: '<h2 align=center>第二步</h2><fieldSet><p><h3>系统导航区域定制</h3></p>' + 
					'<p>&nbsp;&nbsp;&nbsp;&nbsp;系统导航区域设置功能可以允许您给个人、职位、部门进行定制系统的Logo图标，' +
					'系统名称、是否展显登录人信息、是否展显登录人部门信息、是否展当前日期以及他们的显示顺序，统过该定制门户支持虚拟门户和二级门户。</p>' + 
					'<p><table width="100%"><tr align="center"><td><img width="600px" src="../resources/images/top.jpg"></td></tr></table></p><hr>' +
					'<p>是否设置系统导航区域:<input type="radio" name="navig" value="Y"/>是' +
					'<input type="radio" name="navig" value="N" checked=true/>否</p></fieldSet>' + 
					'<p style="font-size:12px;color:blue;border:0;height:22px;width:370px; padding-top:2px;"><i>' +
					'不再定制,全部一键恢复默认值(注意:只能恢复个人设置)</i>&nbsp;&nbsp;<input type="button" onclick="setDefault()" ' +
					'style="background:white url(../resources/images/button_03.gif) no-repeat;border:0;height:20px;width:60px; padding-top:2px;" value="一键恢复"></p>'
 
            },{
                id: 'card-2',
                html: '<h2 align=center>第三步</h2><fieldSet><p><h3>系统个性化首页定制</h3></p>' + 
					'<p>&nbsp;&nbsp;&nbsp;&nbsp;系统个性化首页设置功能可以允许您给个人、职位、部门进行定制系统首页展显内容及各个元素布局。' +
					'<p><table width="100%"><tr align="center"><td><img width="150px" src="../resources/images/index01.jpg"></td>' +
					'<td><img width="150px" src="../resources/images/index02.jpg"></td></tr></table></p><hr>' +
					'<p>是否进行个性化首页定制:<input type="radio" name="indexPage" value="Y"/>是' +
					'<input type="radio" name="indexPage" value="N" checked=true/>否</p></fieldSet>' + 
					'<p style="font-size:12px;color:blue;border:0;height:22px;width:370px; padding-top:2px;"><i>' +
					'不再定制,全部一键恢复默认值(注意:只能恢复个人设置)</i>&nbsp;&nbsp;<input type="button" onclick="setDefault()" ' +
					'style="background:white url(../resources/images/button_03.gif) no-repeat;border:0;height:20px;width:60px; padding-top:2px;" value="一键恢复"></p>'
 
			},{
                id: 'card-3',
                html: '<h2 align=center>第四步</h2><p align=center><h3>完成定制。如果想配置生效请重新登录系统，谢谢你的使用!</h3></p>'				
			}]        
        }); 
        
       var win = new Ext.Window({
       		id:'makeNavig',
            title: '导航式系统定制向导',
            closable:true,
            width:535,
            height:410,
            //border:false,
            plain:true,
            layout: 'border',
            items: [card],
            //buttonAlign:'center',
            buttons:[{
            	id: 'move-prev',
            	text: '&laquo;上一步',
            	handler: navHandler.createDelegate(this, [-1]),
            	hidden:true
            },{
                id: 'move-next',
                text: '下一步&raquo;',
                handler: navHandler.createDelegate(this, [1])          
            }],
            listeners:{
            	close:function(){
            		navigIsClosed = true;		//表示关闭
            	}
            }
        });

        win.show(this);
}

function setDefault(){
	Ext.Msg.confirm('提示信息','确定需要将所有的设置都恢复到默认状态吗?',function(btn){
		if(btn=="yes"){
			var navig = new Object();
			navig.staffId = staffId;
			var result = invokeAction("/outerSystem/DelPersonNavigAction",navig);
			if("success"==result){
				Ext.Msg.alert("操作提示","恢复成功,需要重新登录才能显示效果!");
			}else{
				Ext.Msg.alert("操作提示","恢复失败,请重试或与和理员联系!");
			}	
		}
	});
}

function changeNavig(){
	OpenShowDlg("../systemMgr/navigationConfig.jsp",500,600,window);
}

function changeIndexPage(){
	OpenShowDlg("../systemMgr/portalConfig.jsp",window.screen.height,window.screen.width,window);
}

function changeStyle(){
	OpenShowDlg("../systemMgr/changeStyle.jsp",450,500,window);
	/*
	if(!Ext.isEmpty(Ext.getCmp("selStyleWin"))){
		return
	}
	  var curStyle = session1.style;
	  var tabs = new Ext.TabPanel({
	  		id:'styleTabs',
            region: 'center',
            margins:'3 3 3 0', 
            activeItem:curStyle,
            defaults:{autoScroll:true},
            
			minTabWidth: 115,  
        	tabWidth:135,
        	height:50,
        	items:[{
        		id:'blue',
                title: '蓝',
                tabWidth:135,
                minTabWidth: 115, 
                html:"<img src='../resources/images/blue.jpg'>",
                value:'blue'
            },{
            	id:'red',
                title: '红',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/red.jpg'>",
                value:'red'
            },{
            	id:'gree',
                title:'绿',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/gree.jpg'>",
                value:'gree'
               
            },{
            	id:'purple',
                title:'紫',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/purple.jpg'>",
                value:'purple'
               
            },{
            	id:'access',
                title:'黑',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/access.jpg'>",
                value:'access'
               
            },{
            	id:'olive',
                title:'橄榄',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/olive.jpg'>",
                value:'olive'
               
            },{
            	id:'midnight',
                title:'深黑',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/midnight.jpg'>",
                value:'midnight'
               
            },{
            	id:'orange',
                title:'橘黄',
                tabWidth:135,
                minTabWidth: 115, 
                html: "<img src='../resources/images/orange.jpg'>",
                value:'orange'
               
            }]
        });
      
       var win = new Ext.Window({
       		id:'selStyleWin',
            title: '皮肤选择',
            closable:true,
            width:535,
            height:410,
            //border:false,
            plain:true,
            layout: 'border',
          
            items: [tabs],
             buttonAlign:'center',
            buttons: [{
	            text: '确定',
	            onClick:selStyle
	        },{
	            text: '关闭',
	            onClick:function(){
	            	win.close();
	            }
	        }]
        });

        win.show();*/

}


//在ext window中打开个人工作台

function openPlatformInWin(){
	
	if(!Ext.isEmpty(Ext.getCmp("platformWin"))){
		return
	}
	
	var win = new Ext.Window({
       		id:'platformWin',
            title: '个人工作台',
            closable:true,
            width:850,
            height:450,
            plain:true,
            html:'<iframe src="/MOBILE/systemMgr/personalWorkPlatform.jsp" width="100%" height="100%">',
            buttonAlign:'center',
            buttons: [{
	            text: '在工作区打开',
	            onClick:doPlatformAction
	        },{
	            text: '关闭',
	            onClick:function(){
	            	win.close();
	            }
	        }]
        });
        win.show(this);

}
function doPlatformAction(){
	var node = new Object();
	node.id = 'mod5183';
	node.menuId = 5183;
	node.text = '个人工作台';
	node.menuName = '个人工作台';
	node.url = "/MOBILE/systemMgr/personalWorkPlatform.jsp";
	node.showModel = '0';
	node.leaf = true;
	Ext.getCmp("mainTab").loadTab(node);
	Ext.getCmp("platformWin").close();
}
function selStyle(){
	var curStyle = Ext.getCmp('styleTabs').getActiveTab().value;
	var styles = window.parent.topFrame.document.getElementsByTagName("link");
	
	var param = new Object();
	param.staffId = parseInt(session1.staff.staffId);
	param.staffName = session1.staff.staffName;
	param.title = curStyle;
	invokeAction("/system/InsertPersonStyleAction",param);
	for(var i=0;i<styles.length;i++){
		var a = styles[i];
		if(a.getAttribute("rel").indexOf("style") != -1 ){
			a.disabled = true;
			if(a.getAttribute("title")==curStyle)a.disabled=false;
		}
	}
	var val = Ext.MessageBox.confirm('友情提醒', '需要重新登录才能完整显示皮肤，确认要重新登录吗？',confirm);

}
function confirm(btn){
	if(btn=="yes"){
		window.parent.close();
	}else{
		Ext.getCmp("selStyleWin").close();
	}
	
}

//在ext window新闻详情
function openNewsDetailWin(newsId){
	
	if(!Ext.isEmpty(Ext.getCmp("newsformWin"))){
		return
	}
	
	var win = new Ext.Window({
       		id:'newsformWin',
            title: '新闻详情',
            closable:true,
            width:800,
            height:530,
            plain:true,
            html:'<iframe src="/MOBILE/infomanager/newsDetail.jsp?newsId='+newsId+'" width="100%" height="100%">',
            buttonAlign:'center',
            buttons: [{
	            text: '在工作区打开',
	            listeners:{
                    		"click":function(){
                    		doNewsformAction(newsId)
                    		}}
	            
	        },{
	            text: '关闭',
	            onClick:function(){
	            	win.close();
	            }
	        }]
        });
        win.show(this);

}

function doNewsformAction(newsId){
	var node = new Object();
	node.id = 'mod'+newsId;
	node.menuId = newsId;
	node.text = '新闻详情';
	node.menuName = '新闻详情';
	node.url = "/MOBILE/infomanager/newsDetail.jsp?newsId="+newsId;
	node.showModel = '0';
	node.leaf = true;
	Ext.getCmp("mainTab").loadTab(node);
	Ext.getCmp("newsformWin").close();
}

//在ext window公告信息详情
function openInfoDetailWin(infoId){
	
	if(!Ext.isEmpty(Ext.getCmp("infoformWin"))){
		return
	}
	
	var win = new Ext.Window({
       		id:'infoformWin',
            title: '公告详情',
            closable:true,
            width:800,
            height:530,
            plain:true,
            html:'<iframe src="/MOBILE/infomanager/infoDetail.jsp?infoId='+infoId+'" width="100%" height="100%">',
            buttonAlign:'center',
            buttons: [{
	            text: '在工作区打开',
	            listeners:{
                    		"click":function(){
                    		doInfoformAction(infoId)
                    		}}
	            
	        },{
	            text: '关闭',
	            onClick:function(){
	            	win.close();
	            }
	        }]
        });
        win.show(this);

}
//在ext window邮件详情 不过可以通用
function openComDetailWin(url,winId,title){
	
	if(!Ext.isEmpty(Ext.getCmp(winId))){
		return
	}
	
	var win = new Ext.Window({
       		id:winId,
            title: title,
            closable:true,
            width:800,
            height:530,
            plain:true,
            html:'<iframe src="'+url+'" width="100%" height="100%">',
            buttonAlign:'center',
            buttons: [{
	            text: '在工作区打开',
	            listeners:{
                    		"click":function(){
                    		openNode(url,winId,title);
                    		}}
	            
	        },{
	            text: '关闭',
	            onClick:function(){
	            	win.close();
	            }
	        }]
        });
        win.show(this);

}
function openNode(url,winId,title){
	var node = new Object();
	node.id = 'mod'+winId;
	node.menuId = winId;
	node.text = title;
	node.menuName = title;
	node.url = url;
	node.showModel = '0';
	node.leaf = true;
	Ext.getCmp("mainTab").loadTab(node);
	Ext.getCmp(winId).close();
}

function doInfoformAction(infoId){
	var node = new Object();
	node.id = 'mod'+infoId;
	node.menuId = infoId;
	node.text = '公告详情';
	node.menuName = '公告详情';
	node.url = "/MOBILE/infomanager/infoDetail.jsp?infoId="+infoId;
	node.showModel = '0';
	node.leaf = true;
	Ext.getCmp("mainTab").loadTab(node);
	Ext.getCmp("infoformWin").close();
}


//在ext window  bbs详情
 var  wintitle ;
function openBbsDetailWin(titleId,bbsTitle){
	    if(!Ext.isEmpty(Ext.getCmp("disTitleformWin"))){
		   return
	    }
	    
        wintitle = new Ext.Window({
            id: 'disTitleformWin',
            title: '看贴',
            closable: true,
            width:820,
            height:530,
            //plain:true,
            //layout: 'border',
            html: '<iframe src="/MOBILE/bbs/titleList.jsp?titleName=' + bbsTitle + '&bbsTitleId=' + titleId + '" width="100%" height="100%">'
            //items: [qryForm]
        });
        wintitle.show(this);

}

//增加或刷新首页

function setMainPageTab(){

	var mainNode = new Object();
	mainNode.id = 'mainPage';
	mainNode.text = '首页';
	mainNode.url = 'middle.html';
	mainNode.showModel = '0';
	mainNode.leaf = true;
	mainNode.closable = false;
	
	Ext.getCmp("mainTab").loadTab(mainNode);

}
//从个人工作台加载tab页

function PersonalLoadTab(nodeAttr){
	Ext.getCmp("mainTab").loadTab(nodeAttr);
}
//进入外系统首页

function systemsLoadTab(nodeAttr){
	Ext.getCmp("mainTab").loadTab(nodeAttr);
}

function openIM(isToServer){
	if(!isClosed){	
		var url = "../IM/IM.jsp";
		if(isToServer){
			var serStaffMap = invokeAction('/immanager/ImServerIsOnLineAction',new Object());
			if(serStaffMap.error){
				Ext.Msg.show({
					title:'错误提示',
					msg:serStaffMap.error,
	 				buttons: Ext.MessageBox.OK,
	           		icon: Ext.MessageBox.ERROR					
				});
			}else{
				if(serStaffMap.isOnLine=="Y"){
					var serStaffId = serStaffMap.serStaffId;
					var serStaffName = serStaffMap.serStaffName;
					url += "?serStaffId=" + serStaffId + "&serStaffName=" + serStaffName;	
					isClosed = true;
					imWin = window.showModelessDialog(url,window,"dialogHeight="+500+"px;dialogWidth="+300+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
				}else{
					Ext.Msg.alert("提示信息","对不起,管理员不在线上,请稍后联系!");
				}
			}
		}else{
			isClosed = true;
			imWin = window.showModelessDialog(url,window,"dialogHeight="+500+"px;dialogWidth="+300+"px;scroll=no;resizable=no;Minimize=yes;Maximize=no;status=no;help=no");
		}
	//如果已经打开
	}else{
		var serStaffMap = invokeAction('/immanager/ImServerIsOnLineAction',new Object());
		if(serStaffMap.error){
			Ext.Msg.show({
				title:'错误提示',
				msg:serStaffMap.error,
 				buttons: Ext.MessageBox.OK,
           		icon: Ext.MessageBox.ERROR					
			});
		}else{
			if(serStaffMap.isOnLine=="Y"){
				var serStaffId = serStaffMap.serStaffId;
				var serStaffName = serStaffMap.serStaffName;
				im.openChatWinForStaff(serStaffId,serStaffName,true);
			}else{
				Ext.Msg.alert("提示信息","对不起,管理员不在线上,请稍后联系!");
			}
		}
	}
}

function openWithMe(){
	
   var withMeWin = new Ext.Window({
        title: '关于我们',
        width:300,
        height:200,
        //border:false,
        //frame:true,
        html :"<p>版本号: V 1.0.0.1</p>" +
        	  "<p>版权所有: 南京中兴软创科技有限责任公司 1999-2030</p>" + 
        	  "<p>保留所有权利。</p>" + 
        	  "<p><a href=\"mailto:server@ztesoft.com.cn\" >联系我们: server@ztesoft.com.cn</a></p>",
       // buttonAlign:'center',
        buttons:[{
        	text: '关闭',
        	handler:function(){
        		withMeWin.close();
        	}
        }]
    });

    withMeWin.show(this);
}

function staffMod(){
		
		var _obj = new Object();
		_obj.operation = "mod";
		_obj.orgId = session1.org.orgId;
		_obj.staffId = session1.staff.staffId ;
		_obj.jobId = session1.job.jobId ;
		//_obj.password = password ;
		
		var newObj = window.showModalDialog("../oaas/StaffInfo.jsp", _obj, "dialogWidth:680px;dialogHeight:520px;status:no");
	
		
}


function openImMsgWin(title,msg){

	 if(!Ext.isEmpty(Ext.getCmp('imMsgWin'))){
		 if(msg==null){
			 Ext.getCmp('imMsgWin').close();
		 }else{
			 Ext.getCmp('imMsgWin').setTitle(title);
			 document.getElementById('msgDiv').innerHTML=msg;
		 }
		
		 return;
	 }
	 if(msg==null){
		 return;
	 }
	 var msgPanel = new Ext.Panel({
		 border:false,
		 html:'<MARQUEE id=demo onmouseover=demo.stop() style="overflow:hidden;height:20px;border:0px solid #dde5bc;" onmouseout=demo.start() scrollAmount=1 direction=up><div id=msgDiv>'+msg+'</div></MARQUEE>',
		 region: 'center'
	 });
	 var closeBtn = new Ext.Button({
		 region: 'east',
		 text:'关闭',
		 onClick:function(){
		 	Ext.getCmp('imMsgWin').close();
	 	 }	 
	 });
	 var imMsgWin = new Ext.Window({
		 	id:'imMsgWin',
		 	title:title,
	        width:300,
	        height:55,
	        layout:'border',
	        resizable:false,
	        plain :true,
	        closable :true,
	        items:[msgPanel]
	    });
		//imMsgWin.alignTo(this,'c');
		imMsgWin.show(this);
	
	 
}

function openHelperDoc(){
	var helperNode = new Object();
	helperNode.id = 'helperDocPage';
	helperNode.text = '门户帮助手册';
	helperNode.url = '/MOBILE/helperdoc/helperDoc.jsp';
	helperNode.showModel = '0';
	helperNode.leaf = true;
	helperNode.closable = true;
	
	Ext.getCmp("mainTab").loadTab(helperNode);
}

