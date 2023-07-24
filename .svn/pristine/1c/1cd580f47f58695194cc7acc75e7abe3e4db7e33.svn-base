<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
    
    <title>Custom Layouts and Containers - Portal Example</title>

    <!-- ** CSS ** -->
    <!-- base library -->
    <link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

    <!-- overrides to base library -->
    <link rel="stylesheet" type="text/css" href="../ux/css/Portal.css" />

    <!-- page specific -->
    <link rel="stylesheet" type="text/css" href="sample.css" />
  

    <!-- ** Javascript ** -->
    <!-- ExtJS library: base/adapter -->
    <script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>

    <!-- ExtJS library: all widgets -->
    <script type="text/javascript" src="../../ext-all.js"></script>

    <!-- overrides to base library -->
    <!-- extensions -->
    <script type="text/javascript" src="../ux/Portal.js"></script>
    <script type="text/javascript" src="../ux/PortalColumn.js"></script>
    <script type="text/javascript" src="../ux/Portlet.js"></script>

    <!-- page specific -->
	<script type="text/javascript" src="sample-grid.js"></script>
    <script type="text/javascript" src="../shared/examples.js"></script>

	

</head>
<body></body>
</html>
<script type="text/javascript"> 
	
	Ext.onReady(function(){
	

    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];

    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
            xtype:'portal',
            region:'center',
            margins:'35 5 5 0',
            items:[{
                columnWidth:.50,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Grid in a Portlet',
                    layout:'fit',
                    tools: tools,
                    html: "aa"
                },{
                    title: 'Another Panel 1',
                    tools: tools,
                    html: "aa"
                }]
            },{
                columnWidth:.50,
                style:'padding:10px 0 10px 10px',
                items:[{
                    title: 'Panel 2',
                    tools: tools,
                    html: "bbb"
                },{
                    title: 'Another Panel 2',
                    tools: tools,
                    html: "aa"
                }]
            }]
            
            
        }]
    });
});
</script>
