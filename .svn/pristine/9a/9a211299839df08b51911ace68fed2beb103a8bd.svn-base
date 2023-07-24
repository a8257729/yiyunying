
var session1 = GetSession();
var staffId = session1.staff.staffId;
//图标基类
AppIconBase = $.Class({
    create :function(t){
        var box = this.box =  $("<div type='"+t+"'  class='appButton'></div>");
        var _menu1 = {};
        var iconMenuName = "iconMenu";
        /*
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
                    _cloneObj.children(".appButton_appIcon").removeClass().addClass("muneButton_appIcon_");
                    _cloneObj.children(".muneButton_appIcon_").children(".appButton_appIconImg").removeClass().addClass("muneButton_appIconImg_");
                    _cloneObj.children(".appButton_appName").removeClass().addClass("mune_appName_");
                    _cloneObj.children(".mune_appName_").children(".appButton_appName_inner").removeClass().addClass("mune_appName_inner_");  
                                            
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
        }*/
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
              "class" : "muneButton_appIcon_"
        });
        var imgEl = $("<img>",{
            alt:this.name ,
            src:'icon/'+this.icon,
            onerror:"javascript:this.src='icon/min/default.png'",
            "class":"muneButton_appIconImg_",
            id:'icon_app_'+this.appid+'_'+this.asc+'_img'
        });
        imgEl.error(function(){this.src = "icon/min/default.png";});
        appIcon.append(imgEl);            
        var nameDiv = $("<div class='mune_appName_'></div>");
        var name_inner = $("<div>",{
            "class":'mune_appName_inner_',
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
                    delQuickMenu($(this));
                    // 删除“快捷方式”面板图标
                    //Deskpanel.box.find("div[customacceptdrop='-99994']").find("div[appid="+$(this).attr("appid")+"]").remove();
                    //Deskpanel.refreshIcon();
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
             
             document.all.ebizWin.location=url;
             $("#ebizWin").attr("src",url);
             
        });
    }
});
//侧边栏
Sidebar=function(me){
		var quickMenusArr = callRemoteFunctionNoTrans("com.ztesoft.front.FrontManager","qryMyMenu",staffId);

    //装载容器类
    var SideBox = $.Class({
        init :function(ops){
            this.create(ops.location);
        },
        create:function(location){
            this.box =$("<div id='"+location+"Bar'></div>");  
            $('#qickMenu').append(this.box); 
        },
        addPanel:function(sidebar){         
            this.box.append(sidebar.pbox);
        }
    });
    return me ={
         init : function(ops){
            me.create(ops.location);
            me.addIcon(quickMenusArr);
            //me.initDrag();
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
            
            me.box  = $('<div class="qick_mune"></div>');
            //me.box  = $('<div ></div>');
            me.shortcuts_up_btn = $('<div class="shortcuts_up_btn"></div>');
            me.shortcuts_down_btn = $('<div class="shortcuts_down_btn"></div>');
            me.pbox = $('<div id="qickContainer" class="qick_container " > </div>');   
            //me.pbox = $('<div > </div>');            
            //创建父边栏容器
            me[location+'Panel'].addPanel(me.pbox);
            me.location = location;
            me.Icon = [];       
            me.pbox.addClass("mune_pos_"+location);
            //添加上滑按钮
            me.pbox.append(me.shortcuts_up_btn);
            me.pbox.append(me.box);
            //添加下滑按钮
            me.pbox.append(me.shortcuts_down_btn);
            me.leftPanel.box.append(me.pbox);           
            $('#qickMenu').append(me.leftPanel.box);
            me.shortcutsBtn_show();
            me.bindShortcut_btn();
            me.mouse_Scroll();
            //if(UOS_FRONT_PAGE.quickMenuHideFlag == 'Y'){me.bindMouseLeave();}
            me.pbox.css('display','none');
         },
         /*
         addToolList:function(){//添加工具栏
              var docklist = $(tool_list);
              var dockItem2 = $(tool_item);
              var dockItem3 = $(tool_item);
              dockItem2.append(me.jobChange).append(me.sound).append(me.settingtool).append(me.quitSys);
              dockItem3.append(me.pagelet_search_bar);
              docklist.append(dockItem2).append(dockItem3);
              me.pbox.append(docklist);      
         },*/
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
            if(me.box.children(".appButton_").length > 4){
                me.box.css("height",243+"px");
                me.shortcuts_up_btn.show("fade", { }, 500);
                me.shortcuts_down_btn.show("fade", { }, 500);
            }else {
                me.box.css("height",297+"px");
                //删除快捷方式后,快捷栏图标少于等于5个,将已往上面滑的图标下拉回去
                me.shortcutsDownBack();
                me.shortcuts_up_btn.hide("fade", { }, 5);
                me.shortcuts_down_btn.hide("fade", { }, 5);
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
         } /*,
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
                    if(p.hasClass("muneListContainer")){
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
                    ui.helper.children(".appButton_appIcon").removeClass().addClass("muneButton_appIcon_");
                    ui.helper.children(".muneButton_appIcon_").children(".appButton_appIconImg").removeClass().addClass("muneButton_appIconImg_");
                    ui.helper.children(".appButton_appName").removeClass().addClass("mune_appName_");
                    ui.helper.children(".mune_appName_").children(".appButton_appName_inner").removeClass().addClass("mune_appName_inner_");  

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
         }*/
    }
}();
 function delQuickMenu (menuObj){
        //从数据库删除
        callRemoteFunction("com.ztesoft.iom.funcmanager.bl.FuncManager","delMyMenu",menuObj.attr("appid"),staffId);
    }

Sidebar.init({
            location:'left'//初始化sidebar的位置为左侧
        });
Sidebar.show();