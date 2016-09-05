
	<%@page import="java.util.*"%>
	<%@page import="java.text.SimpleDateFormat"%>
	<%
		Object o = request.getSession().getAttribute("currUser");

		if(o!=null){
			//response.sendRedirect(request.getContextPath()+"/control/login/publish");
		}else{
		}
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "">
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>jq_easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>jq_easyui/themes/icon.css">
	<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/AESUtil.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/md5.js"></script>
