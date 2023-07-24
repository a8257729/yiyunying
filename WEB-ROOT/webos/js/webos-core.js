UOS_FRONT_PAGE = {
    //web项目名
    webPath: "/MOBILE",
    //存放一级菜单对象
    menuNameObjArr:new Array(),
    //存放二级菜单对象(即导航栏下的分组菜单)
    secMenuArr:new Array(),
    //存放所有三级菜单对象的数组
    thdMenuArr:new Array(),
    quickMenusArr:new Array(),
    //快捷栏隐藏配置标志：为Y,鼠标移出快捷栏时隐藏快捷栏; 为N，不隐藏。
    quickMenuHideFlag: 'N',
    //切换职位url配置
    selJobUrl: "/MOBILE/main/selectJob.jsp?_style=darkblue",
    //桌面菜单个数：
    deskMenuNum : 24,
    //接口方法一：初始化菜单
    initMenu : function(menuArr,quickMenusArr){
        // 增加快捷方式栏
        if(quickMenusArr && quickMenusArr.length > 0){
            var _quick_id = -99994;
            this.menuNameObjArr.push({"id":_quick_id, "title":"快捷方式", "url":"./icon/ztesoft/monitor.png"});
            //二级菜单                     
            this.secMenuArr.push({"pmid":_quick_id, "id":_quick_id, "name":"默认分组"});
            var obj = null;
            for(var i=0, _max=quickMenusArr.length; i<_max; i++){// 快捷方式目前只查询25个，都可以放入
                obj = {};
                obj.appid = quickMenusArr[i].id;
                obj.icon = quickMenusArr[i].iconFileName;
                obj.name = quickMenusArr[i].name;
                obj.asc = quickMenusArr[i].displayIndex;
                obj.url = quickMenusArr[i].url;
                obj.moduleId = _quick_id;
                this.thdMenuArr.push(obj);
            }
        }
        for (var i = 0; i < menuArr[-1].length; i++) {
            //导航栏(即一级菜单)所需的数据
            this.menuNameObjArr.push({"id":menuArr[-1][i]["id"],"title":menuArr[-1][i]["name"],"url":"icon/"  + (menuArr[-1][i]["iconFileName"] || "1.png")});
            if(menuArr[menuArr[-1][i].id]){    
                //Util.getSecMenus(this.secMenuArr,menuArr,menuArr[-1][i].id,menuArr[-1][i].name);
                Util.getSecMenus(this.secMenuArr,menuArr,menuArr[-1][i].id,"默认分组");
            }
        }
        for (var i = 0; i < menuArr[-1].length; i++) {//三级菜单，即桌面图标
            if(menuArr[menuArr[-1][i].id]){
                Util.getSubMenus(this.thdMenuArr,menuArr,menuArr[-1][i].id);
            }
        }
        this.quickMenusArr = quickMenusArr;
    },
    //接口方法二：初始化整个界面
    init:function(){
        Body.init();
        Desktop.init();  
        Sidebar.init({
            location:'left'//初始化sidebar的位置为左侧
        });
        BottomBar.init();//初始化下部栏
        Deskpanel.init().refresh();
        Navbar.init();//初始化导航条  
        this.openMessage("msg");
    },
    //接口方法三：公告弹出通知
    openMessage:function(message){
        MessageBox.addMessage(message);
    },
    //接口方法四：关闭公告
    closeMessage:function(){
        MessageBox.closeMessage();
    },
    //接口方法五：从快捷栏删除菜单
    delQuickMenu:function(menuObj){
        //从数据库删除
        callRemoteFunction("com.ztesoft.iom.funcmanager.bl.FuncManager","delMyMenu",menuObj.attr("appid"),staffId);
    },
    //接口方法六：增加菜单到快捷栏
    addQuickMenu:function(menuObj){
        //增加到数据库
        callRemoteFunction("com.ztesoft.iom.funcmanager.bl.FuncManager","addMyMenu",menuObj.attr("appid"),staffId);
    }
};
//工具类
Util = {
    formatmodel : function (str, model) {
        for (var k in model) {
            var re = new RegExp("{" + k + "}", "g");
            str = str.replace(re, model[k])
        }
        return str;
    },
    getSecMenus:function(arr,menuArr,id,name){  
        var temp = 0;
        for(var i = 0;i<menuArr[id].length;i++){
            if(menuArr[menuArr[id][i].id]){                         
                var obj = new Object();
                obj.pmid = id;
                obj.id = menuArr[id][i].id;
                obj.name = menuArr[id][i].name;                     
                arr.push(obj);
            }else if(menuArr[id][i].moduleId == id && menuArr[id][i].moduleId != temp){
                //假如二级菜单与三级菜单并存（即一级菜单下直接就是有链接的菜单了，就增加一个默认分组作为二级菜单，分组名称取与一级菜单名相同）
                temp = id;
                var obj = new Object();
                obj.pmid = id;
                obj.id = id;
                obj.name = name;                        
                arr.push(obj);
            }
        }
    },
    getSubMenus:function(arr,menuArr,id){
        for(var i = 0;i<menuArr[id].length;i++){
            if(menuArr[menuArr[id][i].id]){
                this.getSubMenus(arr,menuArr,menuArr[id][i].id);
            }else{
                var obj = new Object();
                obj.appid = menuArr[id][i].id;
                obj.icon = menuArr[id][i].iconFileName;
                obj.name = menuArr[id][i].name;
                obj.asc = menuArr[id][i].displayIndex;
                obj.url = menuArr[id][i].url;
                obj.moduleId = menuArr[id][i].moduleId;
                arr.push(obj);
            }
        }
    }
}

//面板类
Panel=function(me){
    return me={
        hitTest:function(panel,x,y){//碰撞检测，检测坐标[x,y]是否落在panel里面
            var pl,pt;
            return !(
                  x<(pl=panel.offset().left)
                ||y<(pt=panel.offset().top)
                ||x>pl+panel.width()
                ||y>pt+panel.height()
            );
        },
        getIdx:function(panel){//获取节点在panel是第几个儿子节点
            var ci=0;
            while(panel=panel.prev()){
                ci++;
            }
            return ci;
        },
        unSelecte:function(){//清除选中
            return window.getSelection?function(){window.getSelection().removeAllRanges();}:function(){document.selection.empty();};
        }()
    };
}();

//BODY
Body=function(me){
    return me={
        init:function(){
            me.create();
            me.bindEvent();
        },
        create:function(){
            me.box=$('body');
            me.setStyle();
        },
        bindEvent:function(){//清除选中
            function move(evt){
                window.getSelection?window.getSelection().removeAllRanges():document.selection.empty();
            }
            function up(evt){
                $(document).unbind('mousemove',move).unbind('mouseup',up);
            }
            $(document).bind('mousedown',function(){
                $(document).bind('mousemove',move).bind('mouseup',up);
            });
        },
        addPanel:function(panel){
            me.box.append(panel);
        },
        setStyle:function(){
            me.box.css({
                    backgroud:"none repeat scroll 0 0 transparent",                 
                    display: "block",
                    height:"500px"
            });
            
        }
        
    };
}();

//创建桌面最外层类
Desktop=function(me){
    return me={ 
        init:function(){
            me.create();
            //me.setMenu();//绑定右键
            return me;
        }, 
        create:function(){      
            me.box=$("<div id='desktop'  style='position: fixed;'></div>");
            Body.addPanel(me.box);
        },
        addPanel:function(panel){
            me.box.append(panel);
        },
        show:function(){
            me.box.show();
        },
        hide:function(){
            me.box.hide();
        },
        setMenu:function(){
            var MenuData = [
                    [{
                        text: "显示桌面",
                        imgurl:"icon/ztesoft/showdesktop.png",
                        func: function() {
                            
                        }
                    },{
                        text: "关闭所有",
                        imgurl:"icon/ztesoft/close.png",
                        func: function() {
                            Windows.closeAllWindow();
                        }
                    }, {
                        text: "锁屏",
                        func: function() {
                            
                        }
                    }],
                    [{
                        text: "系统设置",
                        func: function() {
                             
                        }
                    },{
                        text: "主题设置",
                        func: function() {                              
                          Windows.openSys({
                            id :'themSetting',
                            title :'设置主题',
                            width :650,
                            height:500,
                            content :document.getElementById("themeSetting_wrap")
                         });
                        }
                    },
                    {
                        text: "图标设置",
                        data: [[{
                            text: "大图标",
                            imgurl:"icon/ztesoft/selected.png",
                            func: function() {
                                Deskpanel.desktopsContainer.removeClass("desktopSmallIcon");
                            }
                        }, {
                            text: "小图标",
                            func: function() {
                                Deskpanel.desktopsContainer.addClass("desktopSmallIcon");
                            }
                        }]]
                    }],
                    [{
                        text:"注销",
                        func:function(){
                        
                        }
                    }]
                ];
           me.box.smartMenu(MenuData, {
                name: "image"    
            });
        }
    };
}();

//桌面内部面板
Deskpanel = function(me){
    
    var desktopWrapper = "<div id='desktopWrapper'></div>";//最外层容器
    
    var desktopsContainer = "<div id='desktopsContainer' class='desktopsContainer'  >";
    var desktopContainer = "<div class='desktopContainer' index='{index}' >";
    var desktopAppListener = "<div class='appListContainer' customacceptdrop='{index}' index='{index}' _olddisplay='block' >";//内部监听容器
    
    var _systemFrontPageBox = "<div style = 'position:absolute;margin-left:-120px;margin-top:-120px;left:20px'></div>";
    var _personalConsoleBox = "<div  style = 'position:absolute;margin-left:-120px;margin-top:-120px;left:20px'></div>"
    
    var defautlSpace ={//默认尺寸
            left:20,
            top:50,
            right:0,
            bottom:120
        };
    return me ={
        init:function(){
            me.create();
            me.space();
            me.personalConsoleBox = $(_personalConsoleBox);
           
            me.personalConsoleBox
                    .append($("<iframe id = 'frame_-99991'  frameborder='0' scrolling='yes' src = 'perWorkench.jsp' width='100%' height='100%'/>"));
            me.box.find("div[customacceptdrop='" + parseInt(-99991) + "']")
                    .append(me.personalConsoleBox);
                    
            me.systemFrontPageBox = $(_systemFrontPageBox);
            
            me.systemFrontPageBox
                    .append($("<iframe id = 'frame_-99990'   frameborder='0' scrolling='yes'  src = 'ebizIndex.jsp' width='100%' height='100%'/>"));
            me.box.find("div[customacceptdrop='" + parseInt(-99990) + "']")
                    .append(me.systemFrontPageBox);
            //浏览器改变刷新
            $(window).resize(me.refresh);
            return me;
        },      
        create:function(){
            me.loadFlag = {};
            me.box=$(desktopWrapper);//桌面外层面板           
            me.desktopsContainer = $(desktopsContainer);
            me.createDesktopsContainer(UOS_FRONT_PAGE.secMenuArr.length);   //创建桌面外层容器
            me.box.append(me.desktopsContainer);
            me.box.css({"left": "73px","right": "0px"});
            me.desktopsContainer.css("left",73);
            Desktop.addPanel(me.box);
            me.Icon=[];
        },
        bindEvent:function(id){
            //桌面图标拖拽
            me.desktopsContainer.find(".appListContainer").each(function(){
                var desk = $(this);
                var index = desk.attr("index");
                    for(var item in UOS_FRONT_PAGE.secMenuArr){
                        if(UOS_FRONT_PAGE.secMenuArr[item].pmid ==id&&UOS_FRONT_PAGE.secMenuArr[item].id==index){
                            desk.sortable({
                            items:".appButton",
                            connectWith :".dock_middle",                    
                            opacity :"0.6",
                            scroll :false,
                            start:function(event,ui){
                            },
                            stop: function(event, ui) {                         
                                var p = ui.item.parent();   
                                if(p.hasClass("dock_middle")){
                                    ui.item.removeAttr("style");//落在侧边栏
                                    //修改拖动后的样式
                                    ui.item.removeClass().addClass("appButton_");
                                    ui.item.children(".appButton_appIcon").removeClass().addClass("appButton_appIcon_");
                                    ui.item.children(".appButton_appIcon_").children(".appButton_appIconImg").removeClass().addClass("appButton_appIconImg_");
                                    ui.item.children(".appButton_appName").removeClass().addClass("appButton_appName_");
                                }
                                Deskpanel.switchCurrent(index);
                                Deskpanel.refreshIcon();    
                            }
                        }).disableSelection();  
                        me.desktopsContainer.find(".appButton").draggable({
                            helper: "clone",
                            opacity :"0.6",
                            scroll :false
                        });
                        return;
                    }
                }
            });
        },          
        createDesktopsContainer:function(n){//桌面外层容器 n创建几层桌面   
            me.desktopsContainer.append(me.addContainer('-99990'));
            me.desktopsContainer.append(me.addContainer('-99991'));
            if(n&&n!=0){
                for(var i =1;i<=n;i++){ //一共有secMenuArr.length个桌面               
                    //用二级菜单的id标识桌面secMenuArr
                    me.desktopsContainer.append(me.addContainer(UOS_FRONT_PAGE.secMenuArr[i-1].id))//填充容器
                    //me.desktopsContainer.append(me.addContainer(i))//填充容器
                }
            }
        },
        addContainer:function(i){       //添加容器
            var c = me.createDesktopContainer(i);   
            var a = me.createDesktopAppListener(i);
            c.append(a);    
            return c ;
        },  
        createDesktopContainer:function(n){     //容器项
            return  $(Util.formatmodel(desktopContainer,{"index":n}));          
        },
        createDesktopAppListener:function(n){//容器监听项
            return  $(Util.formatmodel(desktopAppListener,{"index":n}));
        },  
        addIcons:function(id){//添加应用 ops为所有三级菜单数组 数组的元素ops[i]也是一个数组
            var tempArr = new Array();
            var ops = new Array();
            for(var i = 0;i<UOS_FRONT_PAGE.secMenuArr.length;i++){
                if(UOS_FRONT_PAGE.secMenuArr[i].pmid==id){
                    for(var j = 0,count = 0;j<UOS_FRONT_PAGE.thdMenuArr.length&&count<UOS_FRONT_PAGE.deskMenuNum;j++){
                        if(UOS_FRONT_PAGE.thdMenuArr[j].moduleId == UOS_FRONT_PAGE.secMenuArr[i].id){                           
                            tempArr.push(UOS_FRONT_PAGE.thdMenuArr[j]);
                            count++;
                        }
                    }
                    me.addIcon(tempArr,UOS_FRONT_PAGE.secMenuArr[i].id);
                    tempArr.length = 0;
                }
            }
            me.loadFlag[id]=true;
        },
        addIcon:function(icon,idx){//添加应用 idx 第几桌面
            if(icon){               
                if($.isArray(icon)){//传入是数组
                    $.each(icon,function(){                     
                        me.addIcon(this,idx);//添加应用程序
                    });
                    return me ;
                }
                var Icon = AppIconBase_Table(icon);//传入的是ID还是图标对象
                me.Icon.push(Icon);
                me.box.find("div[customacceptdrop='"+parseInt(idx)+"']").append(Icon.box);
            }       
        },
        addIconFromObj:function(_obj, idx){// 根据已有桌面图标对象，加入新的图标（用于“快捷方式”面板）
            if(_obj){
                var _o = {};
                _o.appid = _obj.attr("appid");
                _o.url= _obj.attr("url");
                _o.moduleId= parseInt(idx);
                _o.name = _obj.attr("name");
                _o.asc = _obj.attr("asc");
                _o.icon = _obj.attr("icon");
                var Icon = AppIconBase_Table(_o);//传入的是ID还是图标对象
                me.Icon.push(Icon);
                me.box.find("div[customacceptdrop='"+parseInt(idx)+"']").append(Icon.box);
                me.bindEvent();// 重新绑定事件
            }
        },
        getIconNumByIdx:function(idx){
            return me.box.find("div[customacceptdrop='"+parseInt(idx)+"']").find(".appButton").length;
        },
        addCurrnet:function(n){//根据index设置当前桌面样式
            me.desktopsContainer.find(".desktopContainer[index='"+n+"']").addClass("desktop_current");      
        },
        removeCurrent:function(n){//根据index移除当前桌面样式
            me.desktopsContainer.find(".desktopContainer[index='"+n+"']").removeClass("desktop_current");   
        },  
        switchCurrent:function(n){//切换index桌面样式
            var dc = me.desktopsContainer;
            dc.find(".desktopContainer[index='"+n+"']")
              .addClass("desktop_current")
              .siblings().removeClass("desktop_current");
        },
        space:function(){//设置桌面各面板尺寸位置   
            var ops = defautlSpace;
            ('top' in ops)&&(typeof ops.top=='string'?me.spaceTop+=ops.top:me.spaceTop=+ops.top||0);
            ('left' in ops)&&(typeof ops.left=='string'?me.spaceLeft+=ops.top:me.spaceLeft=+ops.left||0);
            ('right' in ops)&&(typeof ops.right=='string'?me.spaceRight+=ops.top:me.spaceRight=+ops.right||0);
            ('bottom' in ops)&&(typeof ops.bottom=='string'?me.spaceBottom+=ops.top:me.spaceBottom=+ops.bottom||0);
            return me;          
        },
        refresh:function(){//刷新桌面               
            var ww = $(window).width(),//浏览器宽
                wh = $(window).height();//浏览器高              
            me.width = ww-me.spaceRight -me.spaceLeft;//容器宽
            me.height =wh-me.spaceTop - me.spaceBottom;//容器高 
            var desktopContainer = me.desktopsContainer.find(".desktopContainer");
            var appContainer = desktopContainer.find(".appListContainer");
            
            $(desktopContainer).each(function(i){//容器宽高
                var left = parseInt($(this).css("left"));
                if(left==0){
                   $(this).css({
                        left:0,
                        height:me.height-me.spaceBottom
                    }); 
                }else{
                    $(this).css({
                        left:ww*(i+1),
                        height:me.height-me.spaceBottom
                    });
                }
            });
            $("#zoomWallpaperGrid,#zoomWallpaper").width(ww).height(wh);//背景图片div
            
            var r = me.row = ~~(me.height/112);//行数
            
            me.desktopsContainer.css({//设置应用容器样式和位置
                left:me.spaceLeft,
                top:me.spaceTop,
                width:me.width,
                height:me.height        
            });
            var xx = me.width+";"+me.spaceLeft
            appContainer.each(function(){
                $(this).css({
                    width:me.width-me.spaceLeft,
                    height:me.height,
                    "margin-left": 28,
                    "margin-top": 64,
                    display: "block"
                }); 
            });
            
	        me.personalConsoleBox.css({
	                    width : $(window).width(),
	                    height : $(window).height() - 50
	                });
            me.systemFrontPageBox.css({
                        width : $(window).width(),
                        height : $(window).height() - 50
                    });
            me.refreshIcon();
            Sidebar.refresh();;
        },
        refreshIcon:function(id){//刷新应用           
            var r = ~~(me.height/112);
            me.desktopsContainer.find(".appListContainer").each(function(){
                if(!id){
                    var icon = $(this).children();
                    for(var j= 0 ;j<icon.length;j++){
                        var leftI=~~(j/r),
                            topI=j%r;               
                        $(icon[j]).css({
                            left:leftI*142,
                            top:topI*112
                        });             
                    };
                }else{
                    var desk = $(this);
                    var index = desk.attr("index");
                    for(var item in UOS_FRONT_PAGE.secMenuArr){
                        if(UOS_FRONT_PAGE.secMenuArr[item].pmid ==id&&UOS_FRONT_PAGE.secMenuArr[item].id==index){
                            var icon = $(this).children();
                            for(var j= 0 ;j<icon.length;j++){
                                var leftI=~~(j/r),
                                    topI=j%r;               
                                $(icon[j]).css({
                                    left:leftI*142,
                                    top:topI*112
                                });             
                            };
                            return;
                        }
                    }
                }
            });
        },
        moveIconTo:function(icon,idx2){//目标位置
            var ids=(Panel.getIdx(icon.box));
            if(idx>idx2){//往前移
                me.box.children(".appListContainer[index='1']").append(icon.box,idx2);
            }else if(idx<idx2){//往后移
                me.box.children(".appListContainer[index='1']").append(icon.box,idx2+1);
            }
            me.Icon.splice(idx,1);
            me.Icon.splice(idx2,0,icon);
            me.refresh();
        
        },
        removeIcon:function(icon){
            var idx = (Panel.getIdx(icon.box));
            me.Icon.splice(idx,1);
            icon.box.remove();
            me.refresh();
        },
        getIdx:function(ex,ey){
            ex-=me.spaceLeft+me.spaceRight;
            ey-=me.spaceTop+me.spaceBottom;
            return (~~(ex/142))*me.row+(~~(ey/112));
        }
    };
    
    
}();

//侧边栏
Sidebar=function(me){
    var tool_list = "<div class='dock_tool_list' id='dockToolList' >";
    var tool_item = "<div class='dock_tool_item'></div>";
    //var shortcuts_up_btn = "<div class='shortcuts_up_btn'></div>";
    //var shortcuts_down_btn = "<div class='shortcuts_down_btn'></div>";
    var tool_a ="<a title='{title}' cmd='{cmd}' class='dock_tool_icon dock_tool_{key}' href='#'></a>";

    //装载容器类
    var SideBox = $.Class({
        init :function(ops){
            this.create(ops.location);
        },
        create:function(location){
            this.box =$("<div id='"+location+"Bar'></div>");  
            Desktop.addPanel(this.box); 
        },
        addPanel:function(sidebar){         
            this.box.append(sidebar.pbox);
        }
    });
    return me ={
         init : function(ops){
            me.create(ops.location);
            me.addIcon(UOS_FRONT_PAGE.quickMenusArr);
            me.addToolList();
            me.initDrag();
         },
         show: function(){
            if(me.pbox.css('display')=='block'){
                me.pbox.hide("fade", { }, 500);
            }else if(me.pbox.css('display')=='none'){
                me.pbox.show("fade", { }, 500);
            }
         },
         create:function(location){//创建
            //创建上左右 侧边栏容器
            me.leftPanel = SideBox({location:'left'});
            
            me.box  = $('<div class="dock_middle"></div>');
            me.shortcuts_up_btn = $('<div class="shortcuts_up_btn"></div>');
            me.shortcuts_down_btn = $('<div class="shortcuts_down_btn"></div>');
            me.pbox = $('<div id="dockContainer" class="dock_container " > </div>');            
            //创建父边栏容器
            me[location+'Panel'].addPanel(me.pbox);
            me.location = location;
            me.Icon = [];       
            me.pbox.addClass("dock_pos_"+location);
            //添加上滑按钮
            me.pbox.append(me.shortcuts_up_btn);
            me.pbox.append(me.box);
            //添加下滑按钮
            me.pbox.append(me.shortcuts_down_btn);
            me.leftPanel.box.append(me.pbox);           
            Desktop.addPanel(me.leftPanel.box);
            me.createChangeJobTool();
            me.createSoundTool();
            me.createSettingTool();
            me.createQuitBtn();
            me.createSearchTool();
            me.shortcutsBtn_show();
            me.bindShortcut_btn();
            me.mouse_Scroll();
            if(UOS_FRONT_PAGE.quickMenuHideFlag == 'Y'){me.bindMouseLeave();}
            me.pbox.css('display','none');
         },
         
         addToolList:function(){//添加工具栏
              var docklist = $(tool_list);
              var dockItem2 = $(tool_item);
              var dockItem3 = $(tool_item);
              dockItem2.append(me.jobChange).append(me.sound).append(me.settingtool).append(me.quitSys);
              dockItem3.append(me.pagelet_search_bar);
              docklist.append(dockItem2).append(dockItem3);
              me.pbox.append(docklist);      
         },
         bindMouseLeave: function(){
            me.pbox.mouseleave(function(e){
                //假如右键删除快捷菜单还存在，me.pbox不消失
                if($("#smartMenu_quikmenu").css("display") == 'none' || $("#smartMenu_quikmenu").css("display") == undefined){
                    me.pbox.hide("fade", { }, 500);
                }
            });
         },
         bindShortcut_btn:function(){//绑定上移下移按钮事件
            me.shortcuts_down_btn.click(function(){
                var firstChildMtPX = $.trim(me.box.children(".appButton_:nth-child(1)").css("margin-top"));
                var firstChildMt = parseInt(firstChildMtPX.substring(0,parseInt(firstChildMtPX.lastIndexOf("px"))));
                var n = (-firstChildMt/45)+1;
                if(me.box.children(".appButton_").length - n >= 9){
                    for(var i=1; i<=n; i++){
                        var currentPX = $.trim(me.box.children(".appButton_:nth-child("+i+")").css("margin-top"));
                        var current = parseInt(currentPX.substring(0,parseInt(currentPX.lastIndexOf("px"))));
                        me.box.children(".appButton_:nth-child("+i+")").css("margin-top",(current-45)+"px");
                    }
                }
            }); 
            me.shortcuts_up_btn.click(function(){      
                var firstChildMtPX = $.trim(me.box.children(".appButton_:nth-child(1)").css("margin-top"));
                var firstChildMt = parseInt(firstChildMtPX.substring(0,parseInt(firstChildMtPX.lastIndexOf("px"))));
                for(var i=(-firstChildMt/45); i>=1; i--){
                    var currentPX = $.trim(me.box.children(".appButton_:nth-child("+i+")").css("margin-top"));
                    var current = parseInt(currentPX.substring(0,parseInt(currentPX.lastIndexOf("px"))));
                    me.box.children(".appButton_:nth-child("+i+")").css("margin-top",(current+45)+"px");
                }
            });
         },
         createSearchTool:function(){
             //创建搜索栏
            me.pagelet_search_bar = $("<div class='pagelet_search_bar' id='pagelet_search_bar'></div>");
            me.pageletSearchInput = $("<input>",{
                "class" : "pagelet_search_input",
                "value"  : "关键词..."
            });
            me.pageletSearchButton = $("<input>",{
                "id" : "pageletSearchButton",
                "class" : "pagelet_search_button",
                "title" :"搜索..."
            }); 
            me.pagelet_search_bar.append(me.pageletSearchInput).append(me.pageletSearchButton);
            me.pageletSearchInput.focus(function(){
                $(this).val("");                
            }).blur(function(){ 
                $(this).val("关键词...");}
            );
            me.pageletSearchInput.bind('keyup',function(){
                //将已往上面滑的图标下拉回去
                me.shortcutsDownBack();

                var value=$.trim($(this).val());
                if(value != "" && value != "关键词..."){
                    me.box.children(".appButton_").css("display","none");
                    me.box.children(".appButton_:[title*="+value+"]").css("display","block");
                }else{
                    me.box.children(".appButton_").css("display","block");
                }
            });
         },
         createChangeJobTool:function(){//切换职位
            me.jobChange =$(Util.formatmodel(tool_a,{
                "cmd":"jobChange",
                "title":"切换职位",
                "key":"pinyin"
            }));
            me.jobChange.click(function(e){
                art.dialog.open(UOS_FRONT_PAGE.selJobUrl,{title: '切换职位', width: 877, height: 550, min:false, max:false});
            });
         }, 
         createSoundTool:function(){//声音设置
            var sound = me.sound= $(Util.formatmodel(tool_a,{
                "cmd":"Sound",
                "title":"静音",
                "key":"sound"
            }));
            sound.toggle(function(){
                $(this).addClass("dock_tool_sound_mute").attr("title","取消静音");  
            
            },function(){
                $(this).removeClass("dock_tool_sound_mute").attr("titile","静音");
            });
            me.sound.click(function(e){
                UOS_FRONT_PAGE.openMessage();
            });
         },
         createSettingTool:function(){//注销
            me.settingtool = $(Util.formatmodel(tool_a,{
                "cmd":"Setting",
                "title":"注销",
                "key":"setting"
            }));
            me.settingtool.click(function(){
                //clean session1
                window.location ="cleanSession.jsp";
                window.location ='<%=basePath%>' + UOS_FRONT_PAGE.webPath;
            });
         },
         createQuitBtn:function(){//退出              
             var quitSys = me.quitSys= $(Util.formatmodel(tool_a,{
                "cmd":"quitSys",
                "title":"退出",
                "key":"theme"
              }));
             me.quitSys.click(function(){
                 window.close();
             });
         },
         addIcon:function(icon,idx){
            if(icon){
                if($.isArray(icon)){//传入的是数组
                    $.each(icon,function(){
                        me.addIcon(this.valueOf());
                    });
                    return me;
                }

                var Icon=AppIconBase_Side(icon);
                me.shortcutsBtn_show();
                if(idx!=undefined){
                    me.Icon.splice(idx,0,Icon);
                    me.box.append(Icon.box,idx);                
                }else{
                    me.Icon.push(Icon);
                    me.box.append(Icon.box);
                }
            }
         },
         removeIcon:function(icon){
            var idx = (Panel.getIdx(icon.box));
            me.Icon.splice(idx,1);
            $(icon.box).remove();        
         },
         refresh:function(){
            var test = me.pbox;
            me.pbox.css("margin-top",$(window).height()-parseInt(me.pbox.css("height"))-47);
         },
         shortcutsBtn_show:function(){
            if(me.box.children(".appButton_").length > 9){
                me.box.css("height",443+"px");
                me.shortcuts_up_btn.show("fade", { }, 500);
                me.shortcuts_down_btn.show("fade", { }, 500);
            }else {
                me.box.css("height",497+"px");
                //删除快捷方式后,快捷栏图标少于等于9个,将已往上面滑的图标下拉回去
                me.shortcutsDownBack();
                me.shortcuts_up_btn.hide("fade", { }, 10);
                me.shortcuts_down_btn.hide("fade", { }, 10);
            }
         },
         shortcutsDownBack:function(){
            //将已往上面滑的图标下拉回去
            var firstChildMtPX = $.trim(me.box.children(".appButton_:nth-child(1)").css("margin-top"));
            var firstChildMt = parseInt(firstChildMtPX.substring(0,parseInt(firstChildMtPX.lastIndexOf("px"))));
            var n = (-firstChildMt/45)+1;               
            for(var i = 1; i<n; i++)
            {
                me.shortcuts_up_btn.click();
            }
         },
         scrollFunc:function(e){//滚轮事件处理函数,返回：滚轮方向 1：向上 -1：向下
            var direct=0;
            e=e || window.event;
            if(e.wheelDelta){
                direct=e.wheelDelta>0?1:-1;
            }else if(e.detail){
                direct=e.detail<0?1:-1;
            }
            if(me.shortcuts_up_btn.css("display") == 'block' && direct == 1){
                //alert("向上");
                me.shortcuts_up_btn.click();
            }else if(me.shortcuts_down_btn.css("display") == 'block' && direct == -1){
                //alert("向下");
                me.shortcuts_down_btn.click();
            }
         },
         mouse_Scroll:function(){
            //为me.box注册滚轮事件
            if(me.box[0].addEventListener){
                me.box[0].addEventListener('DOMMouseScroll',me.scrollFunc,false);
            }//W3C
            me.box[0].onmousewheel=me.scrollFunc;//IE/Opera/Chrome
         },
         initDrag:function(){//绑定元素拖拽
            me.box.sortable({
                items:".appButton_",
                opacity :"0.6", 
                scroll :true,
                containment: me.box,
                start:function(event,ui){
                },
                stop:function(event,ui){
                    var item = ui.item;
                    var p = item.parent();
                    if(p.hasClass("appListContainer")){
                        item.css("position","absolute");
                    }
                    Deskpanel.refreshIcon();
                }
            }).disableSelection();
             me.box.droppable({
                drop:function(event,ui){
                    if($(this).find(".appButton_[appid='"+ui.helper.attr("appid")+"']").attr("appid") == ui.helper.attr("appid"))return;
                    // 添加“快捷方式”面板图标
                    Deskpanel.addIconFromObj(ui.helper.clone(), -99994);
                    Deskpanel.refreshIcon();
                    //要设计数据库侧边栏表模型,以保存侧边栏菜单
                    //修改拖动后的样式
                    ui.helper.removeAttr("style");
                    ui.helper.attr("title",ui.helper.attr("name"));
                    ui.helper.removeClass().addClass("appButton_");
                    ui.helper.children(".appButton_appIcon").removeClass().addClass("appButton_appIcon_");
                    ui.helper.children(".appButton_appIcon_").children(".appButton_appIconImg").removeClass().addClass("appButton_appIconImg_");
                    ui.helper.children(".appButton_appName").removeClass().addClass("appButton_appName_");
                    ui.helper.children(".appButton_appName_").children(".appButton_appName_inner").removeClass().addClass("appButton_appName_inner_");  

                    //假如超过10快捷菜单就呈现能控制上下滑动的三解形按钮
                    //dockItem.append(me.jobChange).append(me.sound);
                    
                    me.shortcutsBtn_show();
                    //添加快捷方式到侧边栏
                    UOS_FRONT_PAGE.addQuickMenu(ui.helper.clone());
                    $(this).append(ui.helper.clone());

                    //添加右键菜单
                    var quikmenu = [ [{text:"删除快捷方式",imgurl:"icon/ztesoft/clear.gif",func:function(){UOS_FRONT_PAGE.delQuickMenu($(this));$(this).remove();Sidebar.shortcutsBtn_show();}}] ];
                    $(this).find(".appButton_[appid='"+ui.helper.attr("appid")+"']").smartMenu(quikmenu, {name: "quikmenu",offsetX :10,offsetY :10});
                    //绑定事件
                    $(this).find(".appButton_[appid='"+ui.helper.attr("appid")+"']").live("click",function(e){
                        e.preventDefault();
                        e.stopPropagation();
                        var id = ui.helper.attr("appid");
                        var title = $.trim(ui.helper.text());
                        var url =ui.helper.attr("url");
                        var icon =ui.helper.find("img").attr("src").split("/")[1];         
                        Windows.openApp(id,title,url,icon,window.screen.width,window.screen.height-205);
                    });
                    
                }
            });
         }
    }
}();

//导航栏
Navbar =function(me){
    var _box = "<div  id='navbar' class='no_sysbtn' style='width:240px;'></div>";
    var _innerBox = "<div class='indicator_container nav_current_{index}' id='indicatorContainer'><div id='more_box' class='mainDiv' style='display:none'><ul id='more_box_ul' class='mainUl'></ul></div> </div>";
    var _userbox = "<div class='indicator indicator_header' id='navbarHeaderImg' cmd='user' title='{title}'><img src='{url}' alt='{title}' class='indicator_header_img' ></div>";
    var _abox  = "<a class='indicator indicator_{num}'  cmd='switch' index='{index}'    title='桌面{index}'><span class='indicator_icon_bg'></span><span class='indicator_icon indicator_icon_{num}'>{text}</span></a>";
    var _abox2 = "<a class='indicator indicator_{key}'  cmd='{key}' title='{title}'></a>";
    
    var _upDownBox = "<div style='position: absolute; margin-top: 62px; margin-left: 422px; cursor: pointer;'><img id='down_arrow_img' src='./icon/ztesoft/down.png' ></div>";
    var _leftLineBox = "<div style='position: absolute; margin-top: 3px; margin-left: 247px;'><img id='down_arrow_img' src='./icon/ztesoft/top_line.png' ></div>";
    var _rightLineBox = "<div style='position: absolute; margin-top: 3px; margin-left: 755px;'><img id='down_arrow_img' src='./icon/ztesoft/top_line.png'></div>";
    var _moreImgBox = "<div id='moreImgBox' style='position: absolute; margin-top: 33px; margin-left: 736px;display:none;'><img id='more_img' src='./icon/ztesoft/more.png'></div>";
    //由图片和文字组成的
    var _abox3 = "<div class='indicator indicator_header' id='navbarHeaderImg{num}' cmd='switch' cid='{cid}' index='{index}' title='{title}'><img src='{url}' onerror='javascript:this.src=\"icon/default.png\"' alt='{title}' class='indicator_header_img'><div class='indicator_header_title'>{title}</div></div>";
    
    var _boxUnder = "<div  id='navbar2' class='boxUnder' style=''></div>";
    var _boxUnderSpan = "<span  class='boxUnderSpan' ></span>";
    var _boxUnderWrapper = "<div class='boxUnderWrapper' cmd='switch' scid='{scid}' ><span class='lineGround' style=''></span></div>";
    var _innerBoxUnder = "<div class='innerBoxUnder'  scid='{scid}' cmd='switch'  style='' title='{title}'></div>";
    
    var _boxUnderSpan_ = "<span  class='boxUnderSpan_' ></span>";
    var _boxUnderWrapper_header = "<div class='boxUnderWrapper_header'><span class='lineGround' style=''></span></div>";
    var _boxUnderWrapper_tail = "<div class='boxUnderWrapper_tail'><span class='lineGround' style=''></span></div>";

    //二级菜单超过10个时，点击“更多”按钮图标，显示的面板,里面装载多余的二级菜单
    var _boxUnderSpan_M = "<span  class='boxUnderSpanM' ></span>";
    var moreBtn = "<div id='moreBtn' class='moreBtn' title='更多..'></div>";
    //var _boxUnder_more = "<div  id='navbar2more' class='navbar2more'></div>";

    var selectedMenu = null;
    var defaultnum=522;
    return me = {
        init :function(){
            me.create();    
            me.bindEvent();//绑定导航按钮单击事件 
            me.bindDownArrowEvent();
            me.bindEventForSecondMenu();//绑定导航二级菜单按钮单击事件
            me.overlapFix();//修复桌面图标重叠bug
            me.setPosition();
            me.mouse_Scroll();
            me.showed = true;
        },
                
        zIndex: function(val) {
            $('#navbar').css("z-index", val);
        },

        hide: function() {
            if(me.showed) {
                //$('#navbar').css("z-index","1997");
                $("#navbar").fadeIn("fast",function(){
                    me.showed = false;
                    $("#navbar").animate({marginTop:'-54px'}, "slow");
                });
            }
        },

        show: function() {
            if(!me.showed) {
                //$('#navbar').css("z-index","1997");
                $("#navbar").fadeIn("fast",function(){
                    me.showed = true;
                    $("#navbar").animate({marginTop:'0px'}, "normal");
                });
            }
        },
        
        bindDownArrowEvent: function() {
            $("#down_arrow_img").bind("click", function(){
                if(me.showed) {
                    me.hide();
                } else  {
                    me.show();
                }
            });
        },

        bindEvent:function(){
           //
           me.innerbox.find("div[cmd='switch']").click(function(){
                var _this = $(this);
                var cmd = _this.attr("cmd[switch]");
                var cid = _this.attr("cid");
                var classname = $.trim(me.innerbox.attr("class"));
                var currentindex = parseInt(classname.substring(parseInt(classname.lastIndexOf("_"))+1));   
                me.boxUnder.find(".boxUnderSpan").remove();
                me.boxUnder.find(".boxUnderSpan_").remove();
                me.boxUnder.find(".boxUnderSpanM").remove();
                $("#navbar2more").find(".boxUnderSpan").remove();
                if(cid=='-99990'||cid=='-99991'){
                		$("#frame_"+cid).attr("src",$("#frame_"+cid).attr("src")); 
                    var index = cid;
                }else{
                    var ops = new Object();
                    ops.id = cid;
                    selectedMenu = null;
                    me.createSecondMenu(ops);
                    //加载对应一级菜单下的桌面
                    if(!Deskpanel.loadFlag[cid]){
                        Deskpanel.addIcons(cid);
                        Deskpanel.bindEvent(cid);
                        Deskpanel.refreshIcon(cid);
                    }
                    me.bindEventForSecondMenu();
                    var index = parseInt(me.boxUnder.children().children().next().attr("scid"));
                }
                me.bindSwitchDesktopAnimate(index,currentindex);
                me.innerbox.removeClass().addClass("indicator_container nav_current_"+parseInt(index));
           });
           
           // 鼠标移上去图片变大为39px，其中div[cid=-99993]是用于查找“扩展工具”
           me.innerbox.find("div[cmd='switch'],div[cid=-99993]").mouseover(function(){
                $(this).find(":first-child").css({width:45,height:45});
           });
           // 鼠标移走后变回35px，其中div[cid=-99993]是用于查找“扩展工具”
           me.innerbox.find("div[cmd='switch'],div[cid=-99993]").mouseout(function(){
                $(this).find(":first-child").css({width:35,height:35});
           });
           
           me.box.find("#moreImgBox").hover(
                function () {
                    $(this).find("#more_img").attr({ src: "./icon/ztesoft/more1.png", alt: "更多" });
                },
                function () {
                    $(this).find("#more_img").attr({ src: "./icon/ztesoft/more.png", alt: "更多" });
                }
           );
           
           me.box.find("#moreImgBox").powerFloat({
           //弹出更多的一级菜单层
                //eventType: "click",
                zIndex:20000,
                offsets:{x:-220},
                target:$("#more_box")
           });
        },
        //二级菜单绑定单击事件
        bindEventForSecondMenu:function(){
           me.boxUnder.find("div[cmd='switch']").click(function(){
                var _this = $(this);
                var cmd = _this.attr("cmd[switch]");
                var classname = $.trim(me.innerbox.attr("class"));
                var currentindex = parseInt(classname.substring(parseInt(classname.lastIndexOf("_"))+1));
                var index = parseInt(_this.attr("scid"));
                me.bindSwitchDesktopAnimate(index,currentindex);
                me.innerbox.removeClass().addClass("indicator_container nav_current_"+parseInt(index));
           });
           if($("#navbar2more").children().hasClass("boxUnderSpan")){
                $("#navbar2more").find("div[cmd='switch']").click(function(){
                    var _this = $(this);                    
                    var classname = $.trim(me.innerbox.attr("class"));
                    var currentindex = parseInt(classname.substring(parseInt(classname.lastIndexOf("_"))+1));
                    var index = parseInt(_this.attr("scid"));
                    me.bindSwitchDesktopAnimate(index,currentindex);
                    me.innerbox.removeClass().addClass("indicator_container nav_current_"+parseInt(index));
                });
           }
        },
        bindSwitchDesktopAnimate:function(t,c){//切换动画事件 t 目标桌面  c当前桌面
            //关闭窗口
            Windows.hideAllWindow();
            //
            var left = 0;
            var c = parseInt(c);            
            if(t<c){//往左移动
                left = -2000;
            }else{//往右移动
                left = 2000;
            }           
            var cdesk=Deskpanel.desktopsContainer.find(".desktopContainer[index="+c+"]");
                cdesk.removeClass("desktop_current");
                cdesk.stop().animate({
                    left: left
                }, 'slow', function(){
                            
                }); 
            var idesk =Deskpanel.desktopsContainer.find(".desktopContainer[index="+t+"]");
            idesk.removeClass("desktop_current").addClass("desktop_current");
            idesk.stop().animate({
                    left:0
                 }, 'slow', function(){
                    
            });
        },
        create:function(){//创建导航
            me.box = $(_box);
            me.box.append($("<div class='indicator_wrapper'></div>").append($(_upDownBox)).append($(_leftLineBox)).append($(_rightLineBox)).append($(_moreImgBox)));
            me.box.find(".indicator_wrapper").append(me.createInnerbox());
            //应用集下拉
            me.boxUnder = $(_boxUnder);
            Desktop.addPanel(me.box);
            Desktop.addPanel(me.boxUnder);
            var navbar = $("#navbar");
            navbar.css('left' ,(document.body.clientWidth-875)/2+'px');
        },
        
        createSecondMenu:function(ops){//创建二级极菜单项(相当于分组)ops为对象
            var defaultFlag = '',secondMenuCounter=0;
            me.boxUnderSpanH = $(_boxUnderSpan_); 
            me.boxUnderSpanH.append($(_boxUnderWrapper_header));//横条头
            me.boxUnder.append(me.boxUnderSpanH);

            for(var i =0;i<UOS_FRONT_PAGE.secMenuArr.length;i++){
                if(ops.id && UOS_FRONT_PAGE.secMenuArr[i].pmid == ops.id){
                    var objH = Util.formatmodel(_boxUnderWrapper,{
                        "scid":UOS_FRONT_PAGE.secMenuArr[i].id
                    });
                    var obj = Util.formatmodel(_innerBoxUnder,{
                        "scid":UOS_FRONT_PAGE.secMenuArr[i].id,
                        "title":UOS_FRONT_PAGE.secMenuArr[i]["name"]
                    });
                    
                    me.boxUnderSpan = $(_boxUnderSpan);
                    me.boxUnderWrapper = $(objH);//横条
                    //me.boxUnderWrapper.css({"border-bottom": "6px solid #4b8cdc"});
                    me.innerBoxUnder = $(obj).html(UOS_FRONT_PAGE.secMenuArr[i]["name"]);//字

                   //绑定鼠标悬停事件--横条           
                    me.boxUnderWrapper.bind("mouseover",function(e){//鼠标悬停执行的发动作
                        $(e.target).removeClass().addClass("boxUnderWrapper_");
                        $(e.target).next().css({"font-weight":"bold","color":"#333"});
                    });

                    //绑定鼠标悬停事件-- 字              
                    me.innerBoxUnder.bind("mouseover",function(e){//鼠标悬停执行的发动作
                        $(e.target).prev().removeClass().addClass("boxUnderWrapper_");
                        $(e.target).css({"font-weight":"bold","color":"#333"});
                    });

                    //绑定事件--横条          
                    me.boxUnderWrapper.bind("mouseout",function(e){//鼠标
                        if(selectedMenu !=  $(e.target).attr("scid")){
                            $(e.target).removeClass().addClass("boxUnderWrapper");
                            $(e.target).next().css({"font-weight":"normal","color":"#666"});
                        }                       
                    });

                    //绑定事件-- 字          
                    me.innerBoxUnder.bind("mouseout",function(e){//鼠标
                        if(selectedMenu !=  $(e.target).attr("scid")){
                            $(e.target).prev().removeClass().addClass("boxUnderWrapper");
                            $(e.target).css({"font-weight":"normal","color":"#666"});
                        }
                    });

                    //绑定事件--横条          
                    me.boxUnderWrapper.bind("mouseup",function(e){//鼠标
                        selectedMenu = $(e.target).attr("scid");
                        me.highlightMenu();
                    });

                    //绑定事件-- 字          
                    me.innerBoxUnder.bind("mouseup",function(e){//鼠标
                        selectedMenu = $(e.target).attr("scid");
                        me.highlightMenu();
                    });                 
                    
                    me.boxUnderSpan.append(me.boxUnderWrapper).append(me.innerBoxUnder);

                    if(secondMenuCounter < 9){//假如有超过10个的二级菜单<------------
                        
                        //将boxUnderSpan加入到boxUnder
                        if(UOS_FRONT_PAGE.secMenuArr[i]["name"] == '默认分组'){
                            defaultFlag = "d"+i;
                            me.boxUnderSpan.attr("defaultFlag",defaultFlag);
                            me.boxUnderSpan.insertAfter(me.boxUnderSpanH);
                        }else{
                            me.boxUnder.append(me.boxUnderSpan);
                        }
                        secondMenuCounter++;
                    }else{//超过10个，添加更多按钮，把多的二级菜单加入到面板
                        if(secondMenuCounter == 9){//<---------------------
                            //更多...按钮只加一次
                            me.boxUnderSpanM = $(_boxUnderSpan_M);
                            me.boxUnderSpanM.append($(moreBtn));//更多按钮
                            me.boxUnder.append(me.boxUnderSpanM);
                        }
                        //面板
                        me.boxUnderMore = $("#navbar2more");
                        me.boxUnderMore.append(me.boxUnderSpan);
                        secondMenuCounter++;
                    }
                }
            }
            if(secondMenuCounter >= 10){//假如有超过10个二级菜单则powerFloat
                $("#moreBtn").powerFloat({
                    eventType: "click",
                    position:"2-3",
                    target: $("#secondMenuMorePanel")   
                });
            }
            
            me.boxUnderSpanT = $(_boxUnderSpan_);
            me.boxUnderSpanT.append($(_boxUnderWrapper_tail));//横条尾
            me.boxUnder.append(me.boxUnderSpanT);

            //设置居中
            if(secondMenuCounter < 10){
                var spanNum = $("#navbar2").children().length-2;
                $("#navbar2").css({"left":(document.body.clientWidth-spanNum*81-36)/2+'px'});
            }else{
                var spanNum = $("#navbar2").children().length-3;
                $("#navbar2").css({"left":(document.body.clientWidth-spanNum*81-52)/2+'px'});
            }

            //点击大导航栏后默认选中第一个二级菜单
            if(selectedMenu == null){
                var classname = $.trim(me.innerbox.attr("class"));
                var currentindex = parseInt(classname.substring(parseInt(classname.lastIndexOf("_"))+1));
                me.boxUnder.children("span:nth-child(2)").children().next().mouseup();
            }
            //alert(secondMenuCounter);
        },
        scrollFunc:function(e){//滚轮事件处理函数,返回：滚轮方向 1：向上 -1：向下
            var direct=0;
            e=e || window.event;
            if(e.wheelDelta){
                direct=e.wheelDelta>0?1:-1;
            }else if(e.detail){
                direct=e.detail<0?1:-1;
            }
            if(direct == 1){
                //找到当前选中的，假如当前选中的前面有二级菜单，则选中前一个
                var prevspan = me.boxUnder.find(".boxUnderWrapper_").parent(".boxUnderSpan").prev();
                if(prevspan.attr("class") == "boxUnderSpan"){
                    prevspan.children(".boxUnderWrapper").click();
                    prevspan.children(".boxUnderWrapper").mouseup();
                }
            }else if(direct == -1){
                //找到当前选中的，假如当前选中的后面还有二级菜单，则选中下一个
                var nextspan = me.boxUnder.find(".boxUnderWrapper_").parent(".boxUnderSpan").next();
                if(nextspan.attr("class") == "boxUnderSpan"){
                    nextspan.children(".boxUnderWrapper").click();
                    nextspan.children(".boxUnderWrapper").mouseup();
                }
            }
        },
        mouse_Scroll:function(){
            //为Desktop注册滚轮事件
            if($("#zoomWallpaperGrid")[0].attachEvent){//IE/Opera/Chrome
                if($.browser.msie && ($.browser.version == '8.0' || $.browser.version == '9.0' || $.browser.version == '10.0')){//IE8.0\9.0\10.0
                    $("#zoomWallpaperGrid")[0].attachEvent("onmousewheel",me.scrollFunc);
                }else{//对IE7.0
                    $("#desktopsContainer")[0].attachEvent("onmousewheel",me.scrollFunc);
                }
            }else if($("#zoomWallpaperGrid")[0].addEventListener){//火狐
                $("#desktopsContainer")[0].addEventListener("DOMMouseScroll",me.scrollFunc);
            }
        },
        highlightMenu:function(){//先清除样式后高亮
            if(selectedMenu != null){
                me.boxUnder.find(".boxUnderWrapper_").removeClass().addClass("boxUnderWrapper");
                me.boxUnder.find(".innerBoxUnder").css({"font-weight":"normal","color":"#666"});
                me.boxUnder.find(".boxUnderWrapper[scid='"+selectedMenu+"']").removeClass().addClass("boxUnderWrapper_");
                me.boxUnder.find(".innerBoxUnder[scid='"+selectedMenu+"']").css({"font-weight":"bold","color":"#333"});
                //对多于10个二级菜单的面板
                $("#navbar2more").find(".boxUnderWrapper_").removeClass().addClass("boxUnderWrapper");
                $("#navbar2more").find(".innerBoxUnder").css({"font-weight":"normal","color":"#666"});
                $("#navbar2more").find(".boxUnderWrapper[scid='"+selectedMenu+"']").removeClass().addClass("boxUnderWrapper_");
                $("#navbar2more").find(".innerBoxUnder[scid='"+selectedMenu+"']").css({"font-weight":"bold","color":"#333"});
            }
        },
        createInnerbox:function(){//创建内部容器
            var con = me.innerbox =   $(Util.formatmodel(_innerBox,{
                "index":'-99999'
            }));
            con.append(me.createFrontPage());
            con.append(me.createWorkbench());
            me.createIndicator();
            return con;
        },
        //创建工作台
        createWorkbench:function(){
            var workbench = $(Util.formatmodel(_abox3, {
                "num":2,
                "index":1,
                "cid":-99991,
                "url":"./icon/ztesoft/workbench.png",
                "title":"个人工作台" 
            }));
            workbench.click(function(){     
            });
            return workbench;
        },
        //创建首页
        createFrontPage:function(){
            var frontpage = $(Util.formatmodel(_abox3,{
                "num":1,
                "index":0,
                "cid":-99990,
                "url":"./icon/ztesoft/logo.png",
                "title":"首页"    
            }));
            return frontpage;
        },
        //创建扩展工具
        createToolBox:function(){
            (function(){
                 var themsSetting = $("#themeSetting_body");             
                 $("a",themsSetting).click(function(){
                        var a  = $(this);
                        var themeid = a.attr("themeid");
                        var src = themeid.substring(themeid.indexOf("_")+1,themeid.length);
                        var h = $(window).height();
                        var w = $(window).width();
                        $("#zoomWallpaper").attr("src","images/bg/"+src+".jpg").width(w).height(h);
                        $("#zoomWallpaperGrid").width(w).height(h);
                        $("a",themsSetting).removeClass("themeSetting_selected");
                        a.addClass("themeSetting_selected");
                 });
             
            })(); 
            var extools = $(Util.formatmodel(_abox3,{
                "num":4,
                "index":-99993,
                "cid":-99993,
                "url":"./icon/ztesoft/toolbox.png",
                "title":"扩展工具"  
            }));
            extools.removeAttr("cmd");
            extools.bind("click", function(){
                Windows.openToolBox();
            });
            return extools;
        },      
        createIndicator:function(){//创建导航项
            var _max = UOS_FRONT_PAGE.menuNameObjArr.length;
            for(var i =0; i<_max && i<7; i++){// "快捷方式"占用一个位置
                var obj = Util.formatmodel(_abox3,{
                    "num":i+10,
                    "index":i+10,
                    "url":UOS_FRONT_PAGE.menuNameObjArr[i]["url"],
                    "cid":UOS_FRONT_PAGE.menuNameObjArr[i]["id"],
                    "title":UOS_FRONT_PAGE.menuNameObjArr[i]["title"]
                });
                me.innerbox.append(obj);
            }
            if(_max > 7){// 多于7个，则增加“更多”按钮，弹出面板
                me.box.find("#moreImgBox").show();
                for(var i=7; i<_max; i++){
                     var obj = Util.formatmodel(_abox3,{
                        "num":i+10,
                        "index":i+10,
                        "url":UOS_FRONT_PAGE.menuNameObjArr[i]["url"],
                        "cid":UOS_FRONT_PAGE.menuNameObjArr[i]["id"],
                        "title":UOS_FRONT_PAGE.menuNameObjArr[i]["title"]
                    });
                    me.innerbox.find("#more_box_ul").append($("<li class='subLi'></li>").append(obj));
                }
            }
            //添加扩展工具
            me.innerbox.append(me.createToolBox());
            // 当个数不够时控制扩展工具的左边位置
            if(_max < 7){
                var _left = (7 - _max) * 84;
                me.innerbox.find("div[cid=\"-99993\"]").css({marginLeft: _left});
            }
        },
        createMoreObj:function(){// 创建“更多”按钮
            
        },
        setPosition :function(){//设置位置
            var ww = $(window).width();
            var mw = me.box.width();
        },
        overlapFix :function(){//修复桌面图标重叠bug
            me.innerbox.find("div[index='0']").click();
        }
    }
}();
//拖拽效果容器
DockEffectBox = function(me){
    
    var _lbox ="<div id='dockleft' class='dock_drap_effect dock_drap_effect_left' style='display: none;'></div>";
    return me = {        
         init : function(){
            me.create();
         },
         create :function(){        
            me.lbox = $(_lbox);
            me.addDesktop();
         },
         addDesktop :function(){
            Desktop.addPanel(me.lbox);
         },
         show:function(){
            me.lbox.show();
         },
         hide:function(){
            me.lbox.hide();
         }
    }
}();
//底部栏容器类
BottomBar = function(me){
    
    var _box = "<div id='bottomBar' class='bottomBar' style='z-index: 12132;'></div>";  
    var _bottomBarBgTask = "<div class='bottomBarBgTask'></div>";
    var _NextBox = "<div id='taskNextBox' class='taskNextBox' _olddisplay='' style='display: none;'><a id='taskNext' class='taskNext' hidefocus='true' href='#'></a></div>";
    var _PreBox = "<div id='taskPreBox' class='taskPreBox' _olddisplay='' style='display: none;'><a id='taskPre' class='taskPre' hidefocus='true' href='#'></a></div>";
    var _taskContainner = "<div id='taskContainer' class='taskContainer' style=''></div>";
    var _leftBottomContainer = "<div id='leftBottomContainer' class='leftBottomContainer' style=''></div>";
    var _rightBottomContainer = "<div id='rightBottomContainer' class='rightBottomContainer' style=''></div>";
    var _rightBottomToolBox = "<div id={id} style='float:left;cursor:pointer;width:40px;height:40px'  title='{title}'><img src = '{icon}' class='rightImg' style='width:30px;height:30px'></div>";
    var _leftBottomToolBox = "<div id={id} style='float:left;cursor:pointer;margin-top:15px'  title='{title}'><img src = '{icon}' class='quickImg' style='width:124px;height:47px'></div>";
    return me  = {
        init:function(){
            me.create();
            Desktop.addPanel(me.box);
            Desktop.addPanel(me.bottomBarBgTask);
            
            me.initTools();
        },
        initTools:function(){
            //为下部栏的左边增加工具按钮
            var quickTool = $(Util.formatmodel(_leftBottomToolBox,{
                'id':'QuickTool',
                'icon':'./icon/ztesoft/quick_logo.png',
                'title':'快捷栏'
            }));
            BottomBar.addLeftBottomItem(quickTool); 
            quickTool.bind('mouseover',function(e){
                $(this).find('img').attr("src","./icon/ztesoft/quick_logo_over.png");
            }).bind('mouseout',function(e){
                $(this).find('img').attr("src","./icon/ztesoft/quick_logo.png");
            });
            quickTool.click(function(){                  
                Sidebar.show();
            });

            var chatTool = $(Util.formatmodel(_rightBottomToolBox,{
                'id':'ChatTool',
                'icon':'./icon/friendgroup.png',
                'title':'IM'
            }));
            BottomBar.addRightBottomItem(chatTool);
            chatTool.click(function(e){
           	 var array= art.dialog.list;
           	 var id = 1000000*Math.random(); 
             var api=array["im2012"+id];

        	if (!api){
        	art.dialog.open("../IM/IM.jsp",
                    {   
                        "id" :"im2012"+id,
                        title: "IM",
                        max:false,
                        resize: false,
                        drag: true,
                        top:50,
                        left:"100%",
                        width:"25%",
                        height:570, 
                        lock:false,
                        effect:'fade',
                        close:function(){

        		  		//opener.isClosed = false;

        				//通知服务器IM下线
        				//imOper.leaveLine();
        				//关闭所有子窗口
        				//for(var i=0;i<imOper.chatWins.length;i++){
        				//	imOper.chatWins[i].close();
        				//}
  
        				$.ajax({
        					type:'post',//可选get
        					url:'/MOBILE/ExtPagingServlet',
        					data:{actionName:'LogoutIMAction'},//多个参数
        					dataType:'text',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
        					success:function(msg){
        					  //alert("success");
        					},
        					error:function(){
        					  alert("注销IM失败");
        					}
        					})

        	           },
//                        close:function(){
//                            me.closeMinTask(id);
//							if(BottomBar.getALLItemID().length == 0) {
//								Navbar.show();
//							}
//                        },
						beforeShow: function() {
							Navbar.zIndex("11981");
							Navbar.hide();
						},
						beforeHide: function() {
							Navbar.zIndex("1981");
						}
                    }
                );
        	} else {
                var wrap = api.DOM.wrap;
                var $wrap = $(wrap);     
                if($wrap.is(":hidden")){
                    api.show();
                }else{
                    api.hide();
                }

        		//art.dialog.["im2012"].show();
        	}
            });

            var freshDesk = $(Util.formatmodel(_rightBottomToolBox,{
                'id':'freshDesk',
                'icon':'./icon/systemtray.png',
                'title':'显示桌面'
            }));
            BottomBar.addRightBottomItem(freshDesk);
            freshDesk.click(function(e){
                Windows.hideAllWindow();
            });
            var messageTool = $(Util.formatmodel(_rightBottomToolBox,{
                'id':'MessageTool',
                'icon':'./icon/kmix.png',
                'title':'公告'
            }));
            BottomBar.addRightBottomItem(messageTool);
            messageTool.click(function(e){
                MessageBox.addMessage();
            });
            
           me.rightBottomContainer.find("img").each(function(i) {
                $(this).bind("mouseover",function(e) {
                    $(e.target).css({width:38,height:38});
                });
                $(this).bind("mouseout",function(e) {
                    $(e.target).css({width:30,height:30});
                });
            });
        },
        create:function(){
            var box =me.box = $(_box);
            me.innerbox = $("<div id='taskContainerInner' class='taskContainerInner' style=''></div>");
            me.leftBottomBox = $("<div id='leftBottomInner' class='leftBottomInner' style=''></div>");
            
            me.taskContainner = $(_taskContainner);
            me.leftBottomContainer = $(_leftBottomContainer);
            
            me.taskContainner.append(me.innerbox);
            me.leftBottomContainer.append(me.leftBottomBox);
            
            box.append(_NextBox);
            box.append(me.leftBottomContainer);
            box.append(me.taskContainner);
            
            box.append(_PreBox);
            
            var bottomBarBgTask = me.bottomBarBgTask = $(_bottomBarBgTask);
            
            me.rightBottomContainer = $(_rightBottomContainer);
            bottomBarBgTask.append(me.rightBottomContainer);
        },
        addRightBottomItem :function(item){
            me.rightBottomContainer.append(item);
            var len = me.rightBottomContainer.children().length;
            var id = item.attr("id");
            var w = item.width()*len;
            me.rightBottomContainer.width(w);
            me.rightBottomContainer.css({"width":(w)});   
        },
        addItem:function(item){//像底部任务栏添加任务项
            me.innerbox.append(item);
            var len = me.innerbox.children().length;
            var id = item.attr("id");
            var tid = item.attr("tid");
            var w = item.width()*len+20;
            me.taskContainner.width(w);
            me.innerbox.css({"margin-left": 0,"width":(w)});   
            me.setCurrent(id);
            
            var MenuData = [
                    [{
                        text: "打开/隐藏",
                        imgurl:"icon/ztesoft/open.png",
                        func: function() {
                            Windows.showWindow(tid);
                        }
                    },{
                        text: "显示桌面",
                        imgurl:"icon/ztesoft/showdesktop.png",
                        func: function() {
                            Windows.hideAllWindow();
                        }
                    },{
                        text: "关闭自己",
                        imgurl:"icon/ztesoft/showdesktop.png",
                        func: function() {
                            Windows.closeWindow(tid);
                        }
                    },{
                        text: "关闭其他",
                        imgurl:"icon/ztesoft/showdesktop.png",
                        func: function() {
                            Windows.closeAllWindow(tid);
                        }
                    }, {
                        text: "关闭全部",
                        imgurl:"icon/ztesoft/close.png",
                        func: function() {
                            Windows.closeAllWindow();
                        }
                    }]];
            item.smartMenu(MenuData,{name: "taskBarMenu_"+tid});
        },
        getItem:function(id){//根据ID查询底部任务栏
            return me.innerbox.find("a[tid='"+id+"']");
        },
        getItemNum:function(){//得到当前任务数
            return me.innerbox.children().size();
        },
        setCurrent:function(id){            
            me.addCurrent(id);
            me.removeItemSibling(id);       
        },      
        addCurrent:function(id){//设置当前任务栏样式         
            me.innerbox
            .find("#"+id)
            .addClass("taskCurrent");               
        },
        removeItemSibling:function(id){//移除当前任务同类样式
            me.innerbox
            .find("#"+id)
            .siblings()
            .removeClass("taskCurrent");        
        },
        getALLItemID :function(){//得到当前任务栏所有任务ID
            var items = me.innerbox.children();
            var idArray =[];
            items.each(function(){
                var id =$(this).attr("id");
                id  =id.substring(id.lastIndexOf("_")+1,id.length);         
                idArray.push(id);
            })
            return idArray ; 
        },
        addLeftBottomItem : function(item){
            me.leftBottomBox.append(item);
            var len = me.leftBottomBox.children().length;
            var id = item.attr("id");
            var w = item.width()*len+1;
            me.leftBottomContainer.width(w);
            me.leftBottomContainer.css({"margin-left": 0,"width":(w)});   
        }
    }
}();
//任务类
Task = $.Class({
    init :function(op){
        this.create(op);
    },
    create:function(op) {
        var task =$("<div>", {
          "class": "taskGroup taskGroupAnaWidth",
          id: "taskGroup_"+op.id+"_"+op.id,
          "tid": op.id
        });
        var taskItemIcon = $("<div>",{
            "class":"taskItemIcon"
        });
        $("<img src='icon/min/"+op.icon+"' onerror='javascript:this.src=\"icon/min/default.png\"'/><div class='taskItemIconState'></div>").appendTo(taskItemIcon);
        var taskItemTxt  = $("<div>",{
            "class":"taskItemTxt",
            text : op.title
        });
        var taskItemBox  = $("<div>",{
            "class":"taskItemBox"
        });         
        var taskA =$("<a>",{
            "class":"taskItem fistTaskItem",
            "href" :"#",
            id  : "taskItem_"+op.id,
            "title" :op.title,
            "tid" :op.id,
            "appid":op.id+"_"+op.id
        });
        taskA.append(taskItemIcon).append(taskItemTxt);
        taskItemBox.append(taskA);
        task.append(taskItemBox);
        this.box = task ;
    }
});
//任务栏左边工具类
LeftBottomTool = Task.extend({
    init :function(op){
        this.create(op);
    },
    create:function(op) {
        this._super(op);
        this.box.css('width','40px');
    }
});
MessageBox=function(me){
    return me={
        addMessage : function(message){
            var content = "<div style='height:180px;overflow-y:auto;overflow-x:hidden;margin-left:5px;margin-top:-3px;padding: 10px 10px;'>";
            if(message){
                if(!me.messages){
                me.messages = new Array();
                me.messages.push(me.messages.length+1+"、"+DateToString(new Date(),true)+"："+message);
                }else{
                    me.messages.push(me.messages.length+1+"、"+DateToString(new Date(),true)+"："+message);
                }
            }
            for(var i =0;i<me.messages.length;i++){
                content = content + "<p class='workWrap'>"+me.messages[i]+"</p>";
            }
            content  = content+"</div>";
            if(message){
                Windows.openMessage(content);
            }else{
                Windows.openMessage(content,true);
            }
            
        },
        closeMessage:function(){
            Windows.closeMessage();
        }
}}();

//窗体类 集成artDialog
Windows = function(me){ 
    return me = {
        showWindow : function(id){//art弹出 
            var array= art.dialog.list;
            var taskIds = BottomBar.getALLItemID();
            var taskLen = taskIds.length;
            var api=array[id];
            var wrap = api.DOM.wrap;
            var $wrap = $(wrap);            
            if(taskLen >1){//判断任务个数 显示切换和焦点切换
                if($wrap.is(":hidden")){    
                     for(var i = 0;i<taskLen;i++){
                        var tempTask = array[taskIds[i]];
                        tempTask.hide();
                     }
                     api.show();
                }else{
                     api.hide();
                }
            }else{              
                if($wrap.is(":visible")){
                    api.hide();
                }else{
                    api.show();
                }
            }   
        },
        hideWindow :function(id,effect){
            if(effect){
                art.dialog.list[id].hide('fade',{},500);
            }else{
                art.dialog.list[id].hide();
            }
        },
        closeWindow :function(id,effect){
            if(effect){
                art.dialog.list[id].close('fade',{},500);
            }else{
                art.dialog.list[id].close();
            }
        },
        closeMinTask:function(id){//关闭任务
            $("#taskGroup_"+id+"_"+id).remove();
        },
        closeAllWindow:function(id){//关闭所有窗体
            var list = art.dialog.list;
            for (var i in list) {
                if(i!='ToolBox'&&i!='MessageBar'&&i!='FRONT_PAGE'){
                    if(!id){
                        list[i].close();
                    }else if(i!=id){
                        list[i].close();
                    }
                }
            };
            Navbar.show();
        }, 
        hideAllWindow: function() {
            var list = art.dialog.list;
            for (var i in list) {
                if(i!='ToolBox'&&i!='MessageBar'&&i!='FRONT_PAGE'){
                    list[i].hide();
                }
            };
            Navbar.show();
        },
        openToolBox :function(){
            this.openSys({
                    id: "ToolBox",
                    title: "扩展工具",
                    width : 350,
                    height: 260,
                    padding:"5px 5px",
                    content : document.getElementById("themeSetting_wrap"),
                    drag: false,
                    left: '100%',
                    top: '0%',
                    max:false,
                    min:false,
                    zIndex:1999,
                    resize: false,
                    beforeShow: function() {
                        Navbar.zIndex("1980");
                    },
                    effect:'fade'
                });
        },
        openSys :function(op){//打开系统窗体
            art.dialog(op);
        },
        hideToolBox: function(){
            this.hideWindow('ToolBox','fade');
        },
        openApp:function(id,title,url,icon,width,height,beforeHide, beforeShow){//打开应用窗体
            var taskInner =BottomBar.innerbox;
            var taskItem = BottomBar.getItem(id);
            if(taskItem.length==1){
                taskItem.click();
                return ;
            }else{
                var len = BottomBar.getItemNum();
                if(len>7&&len!=0){  
                    this.openSys({
                        icon: 'warning',
                        path:'.',
                        content: '请关闭任务栏任务之后再打开新的',
                        min:false,
                        max:false,
                        lock:true,
                        fixed:true,
                        resize:false,
                        effect:'fade',
                        ok:true
                    });
                    return false;
                }   
                var task = Task({//创建最小化任务图标
                    "id":id,
                    "title":title,
                    "icon":icon                     
                });
                BottomBar.addItem(task.box);    
                task.box.click(function(){
                    if($("#dockContainer").css('display')=='block'){//假如侧边栏存在就隐藏它
                        $("#dockContainer").css('display',"none");
                    }
                    me.showWindow(id);    
                    BottomBar.setCurrent(task.box.attr("id"));
                });
                art.dialog.open(url,/** 弹出ART窗体*/
                    {   
                        "id" :id,
                        title: title,
                        max:false,
                        resize: false,
                        drag: true,
                        top:0,
                        left:0,
                        width:width,
                        height:height,  
                        effect:'fade',
                        fresh:true,
                        close:function(){
                            me.closeMinTask(id);
                            if(BottomBar.getALLItemID().length == 0) {
                                Navbar.show();
                            }
                        },
                        init:function(){
                            this.iframe.src = this.iframe.src;
                        },
                        beforeShow: function() {
                            Navbar.zIndex("11980");
                            Navbar.hide();
                        },
                        beforeHide: function() {
                            Navbar.zIndex("1980");
                            Navbar.show();
                        }
                    }
                );
            }
        },
        openMessage:function(message,flag){
            var array= art.dialog.list;
            var api=array['MessageBar'];
            if(!api){
                this.openSys({
                    id: 'MessageBar',
                    title: '公告',
                    content: message,
                    width: 200,
                    height: 130,
                    left: '100%',
                    top :'93%',
                    padding:'0px 0px',
                    drag: false,
                    resize: false,
                    min:true,
                    max:false,
                    effect:'fade'
                });
            }else{
                var wrap = api.DOM.wrap;
                var $wrap = $(wrap);     
                if($wrap.is(":hidden")){
                    api.show();
                    api.content(message);
                }else{
                    if(flag){
                        api.hide();
                    }else{
                        api.content(message);
                    }
                }
            }
        },
        closeMessage:function(){
            this.hideWindow('MessageBar','fade');
        }
    }
}();
//图标基类
AppIconBase = $.Class({
    create :function(t){
        var box = this.box =  $("<div type='"+t+"'  class='appButton'></div>");
        var _menu1 = {};
        var iconMenuName = "iconMenu";
        if(this.moduleId == -99994) {
            iconMenuName = "iconMenuDel";
            _menu1 = {
                text: "删除快捷方式",
                func: function() {
                    UOS_FRONT_PAGE.delQuickMenu($(this));
                    // 删除“快捷方式”面板图标
                    Deskpanel.box.find("div[customacceptdrop='-99994']").find("div[appid="+$(this).attr("appid")+"]").remove();
                    Deskpanel.refreshIcon();
                    // 删除侧边栏图标
                    Sidebar.box.find("div[appid="+$(this).attr("appid")+"]").remove();
                    Sidebar.shortcutsBtn_show();
                }
            };
        } else {
            _menu1 = {
                text: "创建快捷方式",
                imgurl:"icon/ztesoft/quick.png",
                func: function() {
                    var _cloneObj = $(this).clone();
                    if(Sidebar.box.find(".appButton_[appid='"+_cloneObj.attr("appid")+"\']").attr("appid") == _cloneObj.attr("appid")){
                        //alert("已存在该快捷方式");
                        return;
                    };
                    //修改拖动后的样式
                    _cloneObj.removeAttr("style");
                    _cloneObj.attr("title",_cloneObj.attr("name"));
                    _cloneObj.removeClass().addClass("appButton_");
                    _cloneObj.children(".appButton_appIcon").removeClass().addClass("appButton_appIcon_");
                    _cloneObj.children(".appButton_appIcon_").children(".appButton_appIconImg").removeClass().addClass("appButton_appIconImg_");
                    _cloneObj.children(".appButton_appName").removeClass().addClass("appButton_appName_");
                    _cloneObj.children(".appButton_appName_").children(".appButton_appName_inner").removeClass().addClass("appButton_appName_inner_");  
                                            
                    Sidebar.shortcutsBtn_show();
                    //添加快捷方式到侧边栏
                    UOS_FRONT_PAGE.addQuickMenu(_cloneObj);
                    Sidebar.box.append(_cloneObj);
                    
                    // 添加“快捷方式”面板图标
                    Deskpanel.addIconFromObj($(this).clone(), -99994);
                    Deskpanel.refreshIcon();
                    
                    //添加右键菜单
                    var quikmenu = [ [{text:"删除快捷方式",func:function(){UOS_FRONT_PAGE.delQuickMenu($(Sidebar.box));$(Sidebar.box).remove();Sidebar.shortcutsBtn_show();}}] ];
                    Sidebar.box.find(".appButton_[appid='"+_cloneObj.attr("appid")+"']").smartMenu(quikmenu, {name: "quikmenu",offsetX :10,offsetY :10});
                }
            };
        }
        var menuData = [
                    [_menu1,{
                        text: "小图标",
                        func: function() {
                        }
                    }, {
                        text: "大图标",
                        imgurl:"icon/ztesoft/selected.png",
                        func: function() {
                            
                        }
                    }]];
        box.smartMenu(menuData, {name: iconMenuName});
    }
});
//来至桌面的图标
AppIconBase_Table = AppIconBase.extend({   
    init : function(iconObj){   
        this.appid = iconObj.appid;
        this.url= iconObj.url,
        this.moduleId= iconObj.moduleId,
        this.name = iconObj.name,
        this.asc = iconObj.asc,
        this.icon = iconObj.icon
        this.type ='TABLE'; 
        this.create();
        this.bindEvent();
    },      
    create:function(){   
        this._super('TABLE');         
        this.box.attr({
            id:"icon_app_"+this.appid+"_"+this.asc,
            uid :"app_"+this.appid,
            appid:this.appid,
            moduleId:this.moduleId,
            url:this.url,
            name:this.name,
            asc:this.asc,
            icon:this.icon
        });
        var appIcon =$("<div>",{
              id : "icon_app_"+this.appid+"_"+this.asc+"_icon_div",
              "class" : "appButton_appIcon"
        });
        var imgEl = $("<img>",{
            alt:this.name ,
            src:'icon/'+this.icon,
            "class":"appButton_appIconImg",
            id:'icon_app_'+this.appid+'_'+this.asc+'_img'
        });
        imgEl.error(function(){this.src = "icon/default.png"});
        appIcon.append(imgEl);    
        
        var nameDiv = $("<div class='appButton_appName'></div>");
        var name_inner = $("<div>",{
            "class":'appButton_appName_inner',
            id:'icon_app_'+this.appid+'_'+this.asc+'_name',
            text:this.name      
        });
        var name_right =$("<div class='appButton_appName_inner_right'></div>");
        nameDiv.append(name_inner).append(name_right);
        this.box.append(appIcon).append(nameDiv);
    },
    bindEvent:function(){//绑定事件
        // 导航栏的“快捷方式”，所在页面的控件，不再绑定事件
        if(this.box.attr('moduleId') == -99994)
            return;
        $("#"+this.box.attr('id')).live("click",function(e){
             e.preventDefault();
             e.stopPropagation();
             var _this = $(this);
             var id = _this.attr("appid");
             var title = $.trim(_this.text());
             var url = ctxPath +_this.attr("url");
             var icon =_this.find("img").attr("src").split("/")[1]; 
             
             Windows.openApp(id,title,url,icon,$(window).width(),$(window).height()-90);
        });
    }
});

//来至侧边框的图标
AppIconBase_Side = AppIconBase.extend({
    
    init:function(iconObj){
        this.appid = iconObj.id;
        this.url= iconObj.url,
        this.name = iconObj.name,
        this.asc = iconObj.displayIndex,
        this.icon = iconObj.iconFileName,
        this.moduleId = iconObj.moduleId;
        this.tx = 2; 

        this.create();  
        this.bindEvent();  
        this.rightMenu();
    },
    create : function(){
        this._super(2);
        this.box.attr({
            id:"icon_app_"+this.appid+"_"+this.asc,
            appid:this.appid,
            title:this.name,
            "class":"appButton_",
            url:this.url,
            uid :"app_"+this.appid         
        });
        
        var appIcon =$("<div>",{
              id : "icon_app_"+this.appid+"_"+this.asc+"_icon_div",
              "class" : "appButton_appIcon_"
        });
        var imgEl = $("<img>",{
            alt:this.name ,
            src:'icon/'+this.icon,
            onerror:"javascript:this.src='icon/min/default.png'",
            "class":"appButton_appIconImg_",
            id:'icon_app_'+this.appid+'_'+this.asc+'_img'
        });
        imgEl.error(function(){this.src = "icon/min/default.png"});
        appIcon.append(imgEl);            
        var nameDiv = $("<div class='appButton_appName_'></div>");
        var name_inner = $("<div>",{
            "class":'appButton_appName_inner_',
            id:'icon_app_'+this.appid+'_'+this.asc+'_name',
            text:this.name     
        });
        var name_right =$("<div class='appButton_appName_inner_right'></div>");
        nameDiv.append(name_inner).append(name_right);
        var deleteDiv = $("<div",{
            title:'卸载应用' ,
            id :'icon_app_'+this.appid+'_'+this.asc+'_delete',
            "class":'appButton_delete'      
        }); 
        
        this.box.append(appIcon).append(nameDiv).append(deleteDiv);
    },
    rightMenu:function(){
        var quikmenu = [
            [{
                text:"删除快捷方式",
                imgurl:"icon/ztesoft/clear.gif",
                func:function(){
                    UOS_FRONT_PAGE.delQuickMenu($(this));
                    // 删除“快捷方式”面板图标
                    Deskpanel.box.find("div[customacceptdrop='-99994']").find("div[appid="+$(this).attr("appid")+"]").remove();
                    Deskpanel.refreshIcon();
                    // 删除侧边栏图标
                    $(this).remove();
                    Sidebar.shortcutsBtn_show();
                }
            }]
        ];
        this.box.smartMenu(quikmenu, {
            name: "quikmenu",
            offsetX :10,
            offsetY :10
        });
    },
    bindEvent:function(){//绑定事件
        $("#"+this.box.attr('id')).live("click",function(e){
             e.preventDefault();
             e.stopPropagation();
             var _this = $(this);
             var id = _this.attr("appid");
             var title = $.trim(_this.text());
             var url =_this.attr("url");
             var icon =_this.find("img").attr("src").split("/")[1];         
             Windows.openApp(id,title,url,icon,$(window).width(),$(window).height()-90);
        });
    }
});