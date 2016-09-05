<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>首页</title>
</head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<body style="overflow:hidden;">
	<!-- <img src="<%=basePath %>images/mainPage${sessionScope.SYSTEM}.jpg" 
			width="105%" height="105%"
			 style="z-index:-100;position:absolute;left:0;top:0;"> -->
	<table style=" height: 300px; width: 700px" align="center" >
		<tr height="200px"></tr>
		<tr>
			<th width="100%"> <font color="green" size="20px">自定义框架首页</font></th>
		</tr>
	</table>
</body>
</html>
