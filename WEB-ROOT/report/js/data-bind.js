
//	
var reportUrl = "/MOBILE/api/client/xj/kt/report/query";
var reportUrl2 = "/MOBILE/api/client/xj/kt/report/querytest";
var reportUrl3 = "/MOBILE/api/client/xj/kt/report/queryCompany";
var reportUrl4 = "/MOBILE/api/client/xj/kt/report/queryCompanyStaff";
var reportUrl5 = "/MOBILE/api/client/xj/kt/report/queryStaffDay";
var reportUrl6 = "/MOBILE/api/client/xj/kt/report/orderAlarm";
var reportUrl7 = "/MOBILE/api/client/xj/kt/report/orderDetail";
var reportUrl8 = "/MOBILE/api/client/xj/kt/report/companyLogin";
var reportUrl9 = "/MOBILE/api/client/xj/kt/report/companyDwLogin";
var reportUrl10 = "/MOBILE/api/client/xj/kt/report/companyDetail";
var reportUrl11 = "/MOBILE/api/client/xj/kt/report/staffLogin";
var reportUrl12 = "/MOBILE/api/client/xj/kt/report/managerLogin";
var waiting = null;
(function(w) {
	
	w.doAjaxCall = function(url, data, callback) { //ajax请求
		console.log("查询参数："+JSON.stringify(data));
		mui.ajax(url, {
			data: data,
			dataType: 'json', //服务器返回json格式数据
			type: 'post', //HTTP请求类型
			timeout: 10000, //超时时间设置为10秒；
			headers: {
				'Content-Type': 'application/json'
			},
			success: function(data) {
				waiting && waiting.close();
				if(data.resultCode!="1000"){
					mui.alert(data.resultMsg, function(){
					
				    }, "确定");
				}else{
					//服务器返回响应
				    callback && callback.call(this, data.resultData.DataContent);
				}
				
			},
			error: function(xhr, type, errorThrown) {
				waiting && waiting.close();
				//异常处理
				console.log(type);
				mui.alert("请求失败,请重试", function(){
					
				}, "确定");
				
			}
		});

	};
	
	//通用查询方法
	w.query_report_data = function(callback,AreaId,BeginDate,EndDate,StaffId,Flg){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			AreaId:AreaId,
			BeginDate:BeginDate,
			EndDate:EndDate,
			StaffId:StaffId,
			Flg:Flg
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl,reqData,callback);
	};
	
	
	w.showData = function(grad_pc,area_name){
		
		$(".mui-tr").each(function(i){ 
			if(grad_pc==0 && area_name=='湖南省'){
				$("#tabel").attr('freezerownum','2');
				if(this.getAttribute('is_dw') == '' || this.getAttribute('is_dw')==null){
					if(this.getAttribute('grad_pc').length==1 || this.getAttribute('grad_pc').length==4){
						this.style.display="";
					}else{
						this.style.display="none";
					}
				}else{
					this.style.display="none";
				}
				
			}else if(grad_pc.length==4){
				

				if(this.getAttribute('area_name')=='湖南省' && this.getAttribute('grad_pc') == 0){
					this.style.display="";
				}else if(this.getAttribute('grad_pc')==grad_pc){
					this.style.display="";
				}else if(this.getAttribute('grad_pc').indexOf(grad_pc) >= 0 && (this.getAttribute('is_dw') == '' || this.getAttribute('is_dw')==null)){
					this.style.display="";
					$("#tabel").attr('freezerownum','3');
					$("#tabel_tableHeadClone").attr('freezerownum','3');
				}else{
					this.style.display="none";
				}
			}else if(grad_pc == 0 && area_name ==''){
				if(this.getAttribute('is_dw') == '' || this.getAttribute('is_dw')==null){
					if(this.getAttribute('grad_pc').length==1 || this.getAttribute('grad_pc').length==4){
						this.style.display="";
					}else{
						this.style.display="none";
					}
				}else{
					this.style.display="none";
				}
				
			}
		}); 
		
	}
	
	w.showDwData = function(grad_pc,area_name,area_id){

		$(".mui-tr").each(function(i){ 
			if(this.getAttribute('is_dw')!=null && this.getAttribute('is_dw') !=''){
				if(this.getAttribute('area_id')==area_id){
					if(this.style.display == "none"){
						this.style.display="";
					}else{
						this.style.display="none";
					}

				}
			}	
				

		}); 
		
	}
	
	

	
	//在途装机工单（日报）
	w.rep_work_order_ing_zj_province = function(callback,AreaId,BeginDate,EndDate){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			AreaId:AreaId,
			BeginDate:BeginDate,
			EndDate:EndDate,
			Flg:'1'
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl,reqData,callback);
	};
	
	//在途装机工单（日报）
	w.rep_work_order_ing_zj_city = function(callback,AreaId,BeginDate,EndDate){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			AreaId:AreaId,
			BeginDate:BeginDate,
			EndDate:EndDate,
			Flg:'2'
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl,reqData,callback);
	};
	
	//在途装机工单（日报）
	w.rep_work_order_ing_zj_county = function(callback,AreaId,BeginDate,EndDate){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			AreaId:AreaId,
			BeginDate:BeginDate,
			EndDate:EndDate,
			Flg:'3'
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl,reqData,callback);
	};
	
	
	//通用查询方法
	w.query_report_data2 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl2,reqData,callback);
	};
	
	
	w.query_report_data3 = function(callback,BeginDate,areaName){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			areaName:areaName,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl3,reqData,callback);
	};
	
	w.query_report_data4 = function(callback,BeginDate,areaName,company){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			areaName:areaName,
			company:company,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl4,reqData,callback);
	};
	
	
	w.query_report_data5 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl5,reqData,callback);
	};
	
	w.query_report_data6 = function(callback,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			StaffId:StaffId,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl6,reqData,callback);
	};
	
	w.query_report_data7 = function(callback,accNbr,areaNbr){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			accNbr:accNbr,
			areaNbr:areaNbr,
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl7,reqData,callback);
	};
	
	w.query_report_data8 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
			
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl8,reqData,callback);
	};
	
	w.query_report_data9 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
			
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl9,reqData,callback);
	};
	
	w.query_report_data10 = function(callback,BeginDate,areaName){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			areaName:areaName,
			
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl10,reqData,callback);
	};
	
	w.query_report_data11 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
			
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl11,reqData,callback);
	};
	
	w.query_report_data12 = function(callback,BeginDate,StaffId){
		var data = {
			ChartType:'rep_work_order_ing_zj',
			BeginDate:BeginDate,
			StaffId:StaffId,
			
		}
		
		var reqData = {
			params:JSON.stringify(data)
		}
		//waiting = plus.nativeUI.showWaiting("正在加载中...");
		doAjaxCall(reportUrl12,reqData,callback);
	};
})(window);