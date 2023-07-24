var chatWinIds=[];

//判断聊天窗口是否打开
function isChatWinOpen(id){
		//for(var i=0;i<this.chatWinIds.length;i++){
		//	if(this.chatWinIds[i]== id){
		//		return true
		//	}
		//}
	    var isOpen = false;
		var chatid= "chat"+id;
    
		var array = art.dialog.list;	    
	    var api = array[chatid];

	    if (api) {
	    	isOpen = true;
	    	var wrap = api.DOM.wrap;
	        var $wrap = $(wrap);     
	        if($wrap.is(":hidden")){
	            isOpen = false;
	         }
	    } 
		return isOpen;
}

function getUnReadMsgForStaff(id){
	var array = art.dialog.list;
	var chatid = "chat"+id;
	var api = array[chatid];
    var chatWin = api.iframe.contentWindow;

	return chatWin.chat.getUnReadMsgForStaff();
}

function getUnReadMsgForGroup(id){
	var array = art.dialog.list;
	var chatid = "chat"+id;
	var api = array[chatid];
    var chatWin = api.iframe.contentWindow;

	return chatWin.chat.getUnReadMsgForGroup();
}

function addFriToGroup(win,imOper){
	 art.dialog.data('opener', win); 

	 art.dialog.open("../IM/selStaff.jsp",
             {   
         "id" :"addFriToGroup",
         title: "查询添加好友",
         max:false,
         resize: false,
         drag: true,
         top:100,
         left:800,
         width:620,
         height:500, 
         lock:true,
         effect:'fade',
         close: function () {
 			var staff = art.dialog.data('staff');//
 	   		if(win.Ext.isEmpty(staff)){
 	   			return;
 	   		}
 	   		staff.imStaffGroupId = win.im.selNode.id.split('_')[1];
 	   		var obj = win.Ext.apply(new Object(),staff);
 	   		obj.constructor.toString();
 	   	    imOper.addFirendToDB(obj,win.Ext.getCmp('typeOrgWin'));
 
 		},
			beforeShow: function() {
				Navbar.zIndex("11982");
				Navbar.hide();
			},
			beforeHide: function() {
				Navbar.zIndex("1982");
			}
     }
	 
 );
	 
}
function addStaff(win){
	 art.dialog.data('opener', win); 
	 var staffs;
	 art.dialog.open("../IM/groupStaffAdd.jsp",
            {   
        "id" :"addStaff",
        title: "群成员添加",
        max:false,
        resize: false,
        drag: true,
        top:100,
        left:800,
        width:556,
        height:460, 
        lock:true,
        effect:'fade',
        close: function () {
			var staffs = art.dialog.data('staffs');// 
	   		if(!win.Ext.isEmpty(staffs)){

	   			for(var i=0;i<staffs.length;i++){
		   		 	if(win.Ext.getCmp('groupStaffGridPanel').getRowByStaffId(staffs[i].staffId)==null){
		   		 		win.Ext.getCmp('groupStaffGridPanel').getStore().add(new win.Ext.data.Record(staffs[i]));
		   		 	}
		   		 	
		   		 }
	   		}
		},
			beforeShow: function() {
				Navbar.zIndex("11982");
				Navbar.hide();
			}
    }

);

}
function addGroup(win){
	art.dialog.data('opener', win);
    art.dialog.open("../IM/groupAdd.jsp",
                {   
            "id" :"addGroup",
            title: "添加群",
            max:false,
            resize: false,
            drag: true,
            top:100,
            left:800,
            width:600,
            height:500, 
            lock:false,
            effect:'fade',
			beforeShow: function() {
				Navbar.zIndex("11982");
				Navbar.hide();
			},
			beforeHide: function() {
				Navbar.zIndex("1982");
			}
        }
    );
 }
function updateGroup(win){
    art.dialog.data('opener', win);    
    art.dialog.open("../IM/groupUpdate.jsp",
                {   
            "id" :"updateGroup",
            title: "修改群",
            max:false,
            resize: false,
            drag: true,
            top:100,
            left:800,
            width:600,
            height:500, 
            lock:false,
            effect:'fade',
			beforeShow: function() {
				Navbar.zIndex("11982");
				Navbar.hide();
			},
			beforeHide: function() {
				Navbar.zIndex("1982");
			}
        }
    );
 }

function openChatWin(win){

    art.dialog.data('opener', win);   
    var id = "chat"+win.imOper.item.id;

    var array= art.dialog.list;
    
    var api=array[id];

	if (!api){
      art.dialog.open("../IM/chat.jsp",
                {   
            "id" :id,
            title: "聊天对话框",
            max:false,
            resize: false,
            drag: true,
            top:100,
            left:800,
            width:600,
            height:500, 
            lock:false,
            effect:'fade',
//            close:function(){
//                me.closeMinTask(id);
//				if(BottomBar.getALLItemID().length == 0) {
//					Navbar.show();
//				}
//            },
			beforeShow: function() {
				Navbar.zIndex("11982");
				Navbar.hide();
			},
			beforeHide: function() {
				Navbar.zIndex("1982");
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
	}
	
	
 } 
 function showChatLog(staffId){
	    art.dialog.data('staffId', staffId);   
        var id = "chatLog"+staffId;
	    art.dialog.open("../IM/chatLog.jsp",
	                {   
	            "id" :id,
	            title: "聊天记录",
	            max:false,
	            resize: true,
	            drag: true,
	            top:120,
	            left:500,
	            width:getMainChatWinWidth(),
	            height:455, 
	            lock:true,
	            effect:'fade',
//	            close:function(){
//	                me.closeMinTask(id);
//					if(BottomBar.getALLItemID().length == 0) {
//						Navbar.show();
//					}
//	            },
				beforeShow: function() {
					Navbar.zIndex("11982");
					Navbar.hide();
				},
				beforeHide: function() {
					Navbar.zIndex("1982");
				}
	        }
	    );
	 }
 
 function openGroupChatWin(win){

	    art.dialog.data('opener', win);   
	    var id = "chat"+win.imGroupOper.item.id;

	    var array= art.dialog.list;
 
	    var api=array[id];

		if (!api){
	      art.dialog.open("../IM/groupChat.jsp",
	                {   
	            "id" :id,
	            title: "群聊天对话框",
	            max:false,
	            resize: false,
	            drag: true,
	            top:100,
	            left:700,
	            width:750,
	            height:508, 
	            lock:false,
	            effect:'fade',
//	            close:function(){
//	                me.closeMinTask(id);
//					if(BottomBar.getALLItemID().length == 0) {
//						Navbar.show();
//					}
//	            },
				beforeShow: function() {
					Navbar.zIndex("11982");
					Navbar.hide();
				},
				beforeHide: function() {
					Navbar.zIndex("1982");
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
		}
		
		
	 } 
 
 function showGroupChatLog(groupId){
	    art.dialog.data('groupId', groupId);   
        var id = "chatLog"+groupId;
	    art.dialog.open("../IM/groupChatLog.jsp",
	                {   
	            "id" :id,
	            title: "群聊天记录",
	            max:false,
	            resize: false,
	            drag: true,
	            top:120,
	            left:500,
	            width:getMainChatWinWidth(),
	            height:455, 
	            lock:true,
	            effect:'fade',
//	            close:function(){
//	                me.closeMinTask(id);
//					if(BottomBar.getALLItemID().length == 0) {
//						Navbar.show();
//					}
//	            },
				beforeShow: function() {
					Navbar.zIndex("11982");
					Navbar.hide();
				},
				beforeHide: function() {
					Navbar.zIndex("1982");
				}
	        }
	    );
	 }
 
 function getMainChatWinWidth(){
		
		if(screen.availWidth >= 1280){
			return screen.availWidth*0.548;
		}else{
			return screen.availWidth*0.596;
		}
	}