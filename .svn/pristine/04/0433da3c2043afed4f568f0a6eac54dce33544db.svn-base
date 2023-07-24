function UploadOper(){
    
    //上传APK
    this.addFile = function(paramName){       
        var fileForm = new Ext.FormPanel({
            region : 'center',
            labelWidth : 55,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            autoScroll : true,
            border : false,
            fileUpload : true,
            items : [{
                xtype : 'textfield',
                fieldLabel : '选择文件',
                name : 'userfile',
                id : 'userfile',
                inputType : 'file',
                allowBlank : false,
                blankText : '文件不能为空',
                height : 25,
                anchor : '98%'
            }],
            buttons : [{
                text : '上传',
                type : 'submit',
                handler : function() {
                    var furl = "";
                    furl = fileForm.form.findField('userfile').getValue();
                    var type = furl.substring(furl.length - 4).toLowerCase();
                    var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
                    if (furl == null || furl == "") {
                        return;
                    }
                    if (type != '.apk') {
                        alert('仅支持apk格式的文件');
                        return;
                    }
                    fileForm.form.submit({
                        url : '../addFileServlet?fileIdsStr=' + filename+'&type=.apk',
                        waitMsg : '正在上传......',
                        waitTitle : '请等待',
                        method : 'POST',
                        success : function(form, action) {
                            thisSLoc = self.location.href.split("MOBILEPJ");
                            thisUPage = thisSLoc[0];
                            var element = document.createElement("a");
                            element.src = "/MOBILEPJ/" + action.result.fileURL;
                            element.target = '_blank';
                            element.innerHTML = filename;
                            
                            Ext.getCmp(paramName).setValue(action.result.fileURL); 
                             Ext.getCmp("appSize").setValue(Math.round(parseInt(action.result.appSize)/1024)); 
                         // win.returnValue = action.result.fileName;   
                            form.reset();
                            win.close();
                        },
                        failure : function(form, action) {
                            form.reset();
                            if (action.failureType == Ext.form.Action.SERVER_INVALID)
                                Ext.MessageBox.alert('警告', '上传失败，仅支持apk格式的文件');
                        }
                    });
                }
            }, {
                text : '关闭',
                type : 'submit',
                handler : function() {
                    win.close(this);
                }
            }]
        });
    
        var win = new Ext.Window( {
            title : "上传apk文件",
            id : 'fileWin',
            width : 450,
            height : 220,
            modal : true,
            border : false,
            layout : "fit",
            items : fileForm
    
        });
        
        win.show();
    
    }
    
    //上传图标 上传图片类型,在前台的拦截
    var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
    this.addImageFile = function(paramUrl,paramName,preview){    

        var fileForm = new Ext.FormPanel({
            region : 'center',
            labelWidth : 55,
            frame : true,
            //bodyStyle : 'padding:5px 5px 0',
            //autoScroll : true,
            border : false,
            fileUpload : true,
            //layout : 'form',
            items : [{
                xtype : 'textfield',
                fieldLabel : '选择文件',
                name : 'upload',
                id : 'upload',
                inputType : 'file',
                allowBlank : false,
                blankText : '文件不能为空',
                height : 25,
                anchor : '80%'
            },{
                xtype : 'box',
                id : 'browseImage',
                fieldLabel : "预览图片",
                autoEl : {
                    width : 300,
                    height : 350,
                    tag : 'img',
                    // type : 'image',
                    src : Ext.BLANK_IMAGE_URL,
                    style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
                    complete : 'off',
                    id : 'imageBrowse'
                }
            }],
            listeners : {
                'render' : function(f) {

                    this.form.findField('upload').on('render', function() {
                    //通過change事件
                        Ext.get('upload').on('change', function(field, newValue, oldValue) {
                            var url =  Ext.get('upload').dom.value;
                    
                            if (img_reg.test(url)) {                
                                if (Ext.isIE) {
                                    var image = Ext.get('imageBrowse').dom;
                                    image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片

                                    image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
                                
                                }// 支持FF
                                else {
                                    Ext.get('imageBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                                }
                            }
                        }, this);
                    }, this);
                }
            },
            buttons : [{
                text : '上传',
                type : 'submit',
                handler : function() {
                    var furl = "";
                    furl = fileForm.form.findField('upload').getValue();
                    var type = furl.substring(furl.lastIndexOf('.')).toLowerCase();
                    var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.lastIndexOf('.'));

                    if (filename == null || filename == "") {
                        alert('请先填写文件名');
                        return;
                    }
                    if (furl == null || furl == "") {
                        return;
                    }
                    if (!img_reg.test(furl)) {
                        alert('仅支持jpg/jepg/png/bmp格式的文件');
                        return;
                    }
                    fileForm.form.submit({
                        url : '../uploadPhotoServlet?fileIdsStr=' + filename+'&type=' + type,
                        waitMsg : '正在上传......',
                        waitTitle : '请等待',
                        method : 'POST',
                        success : function(form, action) {                                                      

                            Ext.getCmp(paramUrl).setValue(action.result.fileURL);
                            Ext.getCmp(paramName).setValue(action.result.fileName);
                            
                            if (Ext.isIE) {
                                if (preview != ""){
                                    var image = Ext.get(preview).dom;
                                    //image.src = '\\MOBILE\\download\\photos\\Chrysanthemum_20121108100620.jpg';// 覆盖原来的图片
                                    //image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = action.result.fileURL;
                                    image.src = action.result.fileURL;           
                                }
                            }// 支持FF
                            else {
                                //Ext.get('photoBrowse').dom.src = Ext.get('upload').dom.files.item(0).getAsDataURL();
                            }
                            
                            form.reset();
                            win.close();
                        },
                        failure : function(form, action) {
                            form.reset();
                            if (action.failureType == Ext.form.Action.SERVER_INVALID)
                                Ext.MessageBox.alert('警告', '上传失败，仅支持jpg/jpeg/png/bmp格式的文件');
                        }
                    });
                }
            }, {
                text : '关闭',
                type : 'submit',
                handler : function() {
                    win.close(this);
                }
            }]
        });
    
        var win = new Ext.Window( {
            title : "上传图片文件",
            id : 'uploadPhotoWin',
            width : 400,
            height : 450,
            modal : true,
            border : false,
            layout : 'fit',
            items : fileForm
    
        });
        
        win.show();
    
    }
    
      this.addImageFile = function (imageUrl,photoView) {
		var imagefileForm = new Ext.FormPanel({
			region : 'center',
			labelWidth : 55,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			autoScroll : true,
			border : false,
			fileUpload : true,
			
			items : [
				{					
				xtype : 'textfield',
				fieldLabel : '选择图片',
				name : 'imageFile',
				id : 'imageFile',
				inputType : 'file',
				allowBlank : false,
				blankText : '图片不能为空',
				height : 25,
				anchor : '98%'
			}],
			buttons : [{
				text : '上传',
				type : 'submit',
				handler : function() {
					var furl = "";
					furl = imagefileForm.form.findField('imageFile').getValue();
					var type = furl.substring(furl.length - 4).toLowerCase();
					var filename = furl.substring(furl.lastIndexOf("\\")+1, furl.length-4);
					if (furl == null || furl == "") {
						return;
					}
					imagefileForm.form.submit({
						url : '/MOBILE/api/server/upload/image',
						waitMsg : '正在上传......',
						waitTitle : '请等待',
						scope:this,
						method : 'POST',
						success : function(form, action) {
							var jsonData = Ext.util.JSON.decode(action.response.responseText);
							var success = jsonData.success;
							var fileName = jsonData.fileName;
							var filePath = jsonData.filePath;
							var fileSize= jsonData.fileSize;
							Ext.getCmp('v_menuIconUrl').setValue(filePath);
							Ext.getCmp('photoView').getEl().dom.src=filePath;
							form.reset();
							imageWin.close();
						
						},
						failure : function(form, action) {
							form.reset();
							if (action.failureType == Ext.form.Action.SERVER_INVALID)
								Ext.MessageBox.alert('警告', '上传失败，文件格式不对');
						}
					});
				}
			}, {
				text : '关闭',
				type : 'submit',
				handler : function() {
					win.close(this);
				}
			}]
		});
	
		var imageWin = new Ext.Window( {
			title : "上传图片",
			id : 'fileWin',
			width : 400,
			height : 200,
			modal : true,
			border : false,
			layout : "fit",
			items : imagefileForm
	
		});
		
		imageWin.show();
	
	}
}