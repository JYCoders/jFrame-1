
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改密码</title>
<%@include file="../common_header_easyui.jsp"%>
<style type="text/css">
.condition_container,table .editMenu_form_container,table {
	font-family: "宋体";
	font-size: 12px;
}

.td_Class {
	text-align: right;
	font-size: 12px;
}

.font_red {
	color: red;
}
</style>
</head>
<script type="text/javascript">
/// <summary>修改密码</summary>
function savaPw(){
	  //校验密码
		var yspw = $("#yspw").val();
		var pw1 = $("#xpw1").val();
		var pw2 = $("#xpw2").val();
		if(yspw==""||yspw==null){
			$("#pwtext").text("当前密码不可为空！");
			$("#pwtext").focus();
			return;
		}
		if(yspw==""||yspw==null){
			$("#iptext1").text("新密码不可为空！");
			$("#iptext1").focus();
			return;
		}
		if(pw1==""||pw1==null){
			$("#iptext2").text("新密码确认不可为空！");
			$("#iptext2").focus();
			return;
		}
		if(!/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]{8,16}/.test(pw1)){
			$("#iptext1").text("密码必须是8-16位字母和数字组合");
			$("#iptext1").focus();
			return;
		}
		if(pw1!=pw2){
			$("#xpw1").val("");
			$("#xpw2").val("");
			$("#xpw1").focus();
			$("#iptext1").text("两次密码输入不一致");
			return;
		}
		var encodeYspw=hex_md5(yspw);
		var encodePw1 = hex_md5(pw1);
		var encodePw2 = hex_md5(pw2);
		var BASE_PATH='<%=basePath %>'; 
		//执行修改操作
 		$.ajax({  
	                type: "POST",  
	                dataType: "json",  
	                url: BASE_PATH+'control/user/updatePw.action',  
	                data: { yspw: encodeYspw,pw1: encodePw1,pw2: encodePw2},
	                success: function (data) {  
	                  	$.messager.alert("消息", data, "info");
	                },  
	                error: function() {  
	                    $.messager.alert("消息", "密码修改失败！", "info");  
	                }  
	         }); 
}
/// <summary>根据输入对其他输入框进行处理</summary>
function chgVal(cu){
	var yspw = $("#yspw").val();
		var pw1 = $("#xpw1").val();
		var pw2 = $("#xpw2").val();
	if(cu==1&&yspw!=""){
	$("#pwtext").text("");
	}
	if(cu==2&&pw1!=""){
	$("#iptext1").text("");
	}
	if(cu==3&&pw2!=""){
	$("#iptext2").text("");
	}
}
/// <summary>批量新增更新Demo</summary>
function batchHandle(){
	var _url = '<%=basePath %>control/user/batchHandleDemo.action?etc='+new Date().getTime();
	$.ajax({
		url: _url,
		type:'post',
		success:function(data){
			if(data.flag=='true') {alert('批量新增更新操作完成，请查看后台代码');}
			if(data.flag=='false') {alert('批量处理失败，异常信息：'+data.msg);}
		},
		error:function(){
			alert('批量处理异常!');
		}
	});
}
</script>
 <body style="text-align: center;" >
    <div class = 'div_toolbar' align="left">
			   <a id="btn" href="javascript:void(0);"  class="easyui-linkbutton" iconcls="icon-save" plain="true" onclick="savaPw()">保存</a> 
			   <!--  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <a id="btn" href="javascript:void(0);"  class="easyui-linkbutton" iconcls="icon-debug" plain="true" onclick="batchHandle()" >批量新增修改Demo</a>--> 
	</div>
 <div style="height: 100px"></div>
	<form id="ff" action="" >	       
		<div style="width: 500px; padding-top: 5px;margin:auto; text-align: center;">
			<table style="border-collapse:separate; border-spacing:0px;font-size:14px;background: #F8F8FF;" cellpadding="5" >
				<tr >
					<td width="100px" class="form_td_label" style="border-right: 0px; border-bottom: 0px"><label for="pw">&nbsp;&nbsp;当前密码<span style="color: red;">*</span>：</label></td>
					
					<td width="280px" class="form_td_input" style="border-bottom: 0px"><input id="yspw" name="yspw" class=" search_txtbox_input easyui-validatebox"  type="password"  style="width: 260px;" onchange="chgVal(1)"><font id="pwtext" color="red"></font></td>
				 </tr>
				<tr>
					<td width="100px" class="form_td_label" style="border-right: 0px; border-bottom: 0px"><label for="pw1">&nbsp;&nbsp;新&nbsp;密&nbsp;码<span style="color: red;">*</span>：</label></td>
					
					<td width="280px" class="form_td_input" style="border-bottom: 0px"><input id="xpw1" name="xpw1"  class="search_txtbox_input easyui-validatebox"   type="password"  style="width: 260px;" onchange="chgVal(2)">
					<font id="iptext1" color="red"></font>
					</td>
				 </tr>
				<tr>
					<td width="100px" class="form_td_label" style="border-right: 0px"><label for="pw2">确认新密码<span style="color: red;">*</span>：</label></td>
					
					<td width="280px" class="form_td_input" > <input id="xpw2" name="xpw2"  class="search_txtbox_input easyui-validatebox"   type="password" style="width: 260px;"  onchange="chgVal(3)"><font id="iptext2" color="red"></font></td>
				</tr>
			</table>
			
		</div>
	</form>
	
</body>

 
</html>