   <link rel="stylesheet" type="text/css" title="slate" href="/MOBILE/ext/resources/css/xtheme-slate.css" /> 
   <link rel="stylesheet" type="text/css" title="blue" href="/MOBILE/ext/resources/css/xtheme-blue.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="olive" href="/MOBILE/ext/resources/css/xtheme-olive.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="red" href="/MOBILE/ext/resources/css/xtheme-silverCherry.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="midnight" href="/MOBILE/ext/resources/css/xtheme-midnight.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="orange" href="/MOBILE/ext/resources/css/xtheme-orange.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="green" href="/MOBILE/ext/resources/css/xtheme-green.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="purple" href="/MOBILE/ext/resources/css/xtheme-purple.css" disabled="true"/>
   <link rel="stylesheet" type="text/css" title="access" href="/MOBILE/ext/resources/css/xtheme-access.css" disabled="true"/>   

 <script type="text/javascript">
	function getStyle(){
		var session1 = GetSession();
		var styles = document.getElementsByTagName("link");
		for(var i=0;i<styles.length;i++){
			var a = styles[i];
			if(a.getAttribute("rel").indexOf("style") != -1&& a.getAttribute("title") ){
				a.disabled = true;
				
				if(a.getAttribute("title")==session1.style){
					a.disabled=false;
				}
			}
		}
	}
	getStyle();
</script>