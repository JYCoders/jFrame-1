<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统运行异常</title>
</head><body>
<%
	Throwable ex = null;
	if (exception != null)
		exception.printStackTrace();
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception) request.getAttribute("javax.servlet.error.exception");
%>
<div id="content">
<h3>系统运行异常:</h3>
	<div><%if (ex != null) out.println(ex.getMessage());%>${error}</div>
	<div>
		<button onclick="history.back();">Back</button>
	</div>
	<div><a href="#" onclick="document.getElementById('detail_error_msg').style.display=(document.getElementById('detail_error_msg').style.display=='none'?'block':'none')">Administrator click here to get the detail.</a></div>
	<div id="detail_error_msg" style="display: none">
		<textarea style="width:100%; height:400px;border:1px solid #ccc;" readonly="readonly" >
<%if (ex != null) {
	ex.printStackTrace(new java.io.PrintWriter(out));
}%>
${error}
		</textarea> 
	</div>
</div>
</body></html>