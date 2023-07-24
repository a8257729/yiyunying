Ext.ns("ZTESOFT");
ZTESOFT.Combo=Ext.extend(Ext.form.ComboBox,{
    constructor:function(config){
       this.displayField='display';
       this.valueField='value';
       this.mode='remote';
       if(Ext.isEmpty(config.name)){
          this.name='typeId';
       }
      if(Ext.isEmpty(config.id)){
          this.id='typeId';
       }
      this.triggerAction ='all';
      this.editable= false;
      
      var comboStore=new Ext.data.JsonStore({
          remoteSort:true,
          fields:['display','value'],
          proxy:new Ext.data.HttpProxy({
            url:'/MOBILE/ExtServlet?actionName=common/ComboUtil&tbnameId='+config.tbnameId
          })
      });  //tbnameId需要用户设置参数;
      this.store=comboStore;
      ZTESOFT.Combo.superclass.constructor.apply(this, arguments);
    }
});
Ext.reg('ztesoftCombo',ZTESOFT.Combo);
/**
*usage: two step is required.
*eg: 
  1)在web页面
   var panel=new Ext.form.FormPanel(
    items:[{
    		xtype:'ztesoftCombo',
    		tbnameId:'wk_order_type',
    		fieldLabel:'任务类型',
    		id:'worktype',
            name:'worktype'
    		}]  
   );
  2)到 "combo.xml"文件配置使用的数据库表和字段;
   <table ID="wk_order_type">
	    <table_name>EOMS_WORK_ORDER_TYPE</table_name>
	    <display_field>WK_ORDER_TYPE_NAME</display_field>
	    <value_field>WK_ORDER_TYPE_ID</value_field>
   </table>
**/
 /** author:lcq 
 *  createDate:2012-09-20
  **/