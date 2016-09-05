<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="java.io.*" %>
<html>
<head>
  <title>错误提示信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script type="text/javascript">
  function showStackMsg(){
	  document.getElementById('divStackMsg').style.display=(document.getElementById('divStackMsg').style.display=='none'?'block':'none');
}
  </script>
</head>

<body>
<br/>
<table cellspacing="0" cellpadding="0" align="center" width="100%" border="0">
  <tbody>
  <tr>
    <td width="25" rowspan="3"></td>
    <td width="5"><img height="28" alt="" src="<%=request.getContextPath()%>/images/errorImgs/menuname_left.gif" width="5" /></td>
    <td align="right" width="60" background="<%=request.getContextPath()%>/images/errorImgs/menuname_bg.gif" />&nbsp;</td>
    <td align="right" background="<%=request.getContextPath()%>/images/errorImgs/menuname_bg.gif">
      <p style="margin-top: 0px; color: #ffffff">∷∷∷</p>
    </td>
    <td align="center" background="<%=request.getContextPath()%>/images/errorImgs/menuname_bg.gif">
      <span class="title"  style="color: #ffffff">提示信息</span>
    </td>
    <td background="<%=request.getContextPath()%>/images/errorImgs/menuname_bg.gif">
      <p style="margin-top: 0px; color: #ffffff">∷∷∷</p>
    </td>
    <td align="center" width="60" background="<%=request.getContextPath()%>/images/errorImgs/menuname_bg.gif" />&nbsp;</td>
    <td width="8"><img height="28" alt="" src="<%=request.getContextPath()%>/images/errorImgs/menuname_right.gif" width="8" /></td>
    <td width="22" rowspan="3"></td>
  </tr>
  <tr>
    <td background="<%=request.getContextPath()%>/images/errorImgs/part1_44.gif"></td>
    <td colSpan="5" height="200" align="center">
    <img alt="" src="<%=request.getContextPath()%>/images/errorImgs/Llerror.gif" width="66" />
  	<h3>系统错误，请联系管理员！</h3> 
  	<div style="text-align: left;font-weight: normal;color: red;">
  <%--   	<h5>访问URL：${errorpath}</h5>
  	<h5>错误信息：${errorMsg}</h5>  --%> 
  	<h5 >错误信息：您的访问出错了！！！</h5>
  	
  	
  	</div>
 	<!-- <a href="javascript:void(0);" onclick="showStackMsg();"> 详细信息 </a>    -->
    <div id="divStackMsg" style="display: none;text-align: left;">
    <%	Exception ex = (Exception)request.getAttribute("ex");
  	
  		ex.printStackTrace(new java.io.PrintWriter(out));
  	%>
    </div>   
    </td>
    <td background="<%=request.getContextPath()%>/images/errorImgs/part1_48.gif"></td>
  </tr>
  <tr>
    <td valign="top"><img height="11" alt="" src="<%=request.getContextPath()%>/images/errorImgs/botom_1.bmp" width="5"></td>
    <td valign="top" background="<%=request.getContextPath()%>/images/errorImgs/botom_1.bmp" colSpan="5"></td>
    <td valign="top"><img height="11" alt="" src="<%=request.getContextPath()%>/images/errorImgs/botom_1.bmp" width="8"></td>
  </tr>
  </tbody>
</table>
<br/>
</body>
</html>
