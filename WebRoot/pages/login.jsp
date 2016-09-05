<%@page language="java" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>${sessionScope.COMPANY}${sessionScope.SYSTEM}</title>
		<%@include file="common_header_easyui.jsp" %>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css"/>

		<script>
		var _key = "c32ad1415f6c89fee76d8457c31efb4b";
		if(top!=self){window.parent.location.href="<%=request.getContextPath()%>/control/login/toIndex";}
	$(function(){
	$("span").attr("style", "height:200px;");//单个属性的设置 
		$('.span').bind({ 
			focus:function(){  
				$(this).hide();
				$(this).siblings("input").focus(); 
			}
		}); 
		$('.login').bind({ 
			focus:function(){  	
				$(this).siblings("span").hide();
			},
			blur:function(){
				var val=$(this).val();
				if(val!=""){
					$(this).siblings("span").hide();
				}else{
					$(this).siblings("span").show();
				} 
			}
		}); 
	})     
	
	function btnLogin_onclick()
    {
    	var loginId = $("#txtUser").val();
    	var userPass = $("#txtPassword1").val();
    	var encodePass = encrypt(_key, hex_md5(userPass));
        if (loginId.replace(" ","")== "" || loginId==loginId.defaultValue || userPass.replace(" ","") == "" || userPass==userPass.defaultValue ){
        	document.getElementById("indicator_container").innerHTML="请输入用户名和密码。";
            return;
        }
       var data1={data:encrypt(_key,(loginId+"&&"+encodePass))};
       var data=encrypt(_key,(loginId+"&&"+encodePass));
       $("#data1").val(data);
       document.getElementById("form2").action="<%=basePath %>control/login/doLogin?etc="+new Date().getTime();
       document.getElementById("form2").submit();
    }
    function tipClear(e){
	var pass1=$("#txtPassword1");
     var kcode=e.which?e.which:e.keyCode;
      if(kcode==8){
    		pass1.val(pass1.val().substring(0,pass1.val().length-1))
    	}
   	  document.getElementById("indicator_container").innerHTML="";
    }
    function resetForm(){
   		$("#txtPassword").val("");
    	$("#txtPassword1").val("");
    	$("#txtUser").val("");
    }
    
    function hiddenpass(e)
    {
    	e=e?e:window.event;
    	var kcode=e.which?e.which:e.keyCode;
    	var pass=$("#txtPassword");
    	var pass1=$("#txtPassword1");
    	if(kcode!=8){
    		var keychar=String.fromCharCode(kcode);
    		pass1.val(pass1.val()+keychar);
    		pass1.val(pass1.val().substring(0,pass.val().length+1))
    	}
    	if(pass.length==0){
    		pass1.val("");
    	}
    }

    function translatePass(e){
 		e=e?e:window.event;
    	var kcode=e.which?e.which:e.keyCode;
    	var pass=$("#txtPassword");
    	var pass1=$("#txtPassword1");
    	var temppass=pass1.val();
    	var temp=pass.val().replace(/●+/g,'');
    	var fhlist="!@#$%^&*()";
    	if(pass.val()==""){
    		temppass="";
    	}/* else if(48<=kcode && kcode<=57 && fhlist.indexOf(temp)<0 && temp!=(kcode-48)){//对应中文输入法按数字键选择
    		temppass=temp;
    	}else if((kcode==16 || kcode==13 ) && temp.length>0){//中文输入法回车,shift,空格选择
    		temppass=temp;
    	} */else if(pass.val().length==1+temppass.length){//对应英文输入法
    		temppass+=temp;
    	}else if(pass.val().length>temppass.length){
    		temppass+=temp;
    	}else{//对应删除
    		temppass=temppass.substring(0,pass.val().length);
    	}
    	pass1.val(temppass);
    	pass.blur();
    	pass.focus();
    }
</script>
	</head>
	<body bgcolor="#FFFFFF"  style="overflow-x : hidden;overflow-y : hidden;" onload="$('#txtUser').focus();">
		<form id="form2" name="form2" action="" method="post" style="display:none">
			<input type="text" id="data1" name="data1">
		</form>
			<table width="1002" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="153">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" valign="top" class="login_bg">
						<table width="550" height="322" border="0" cellpadding="0"
							cellspacing="0" >
							<tr>
								<td height="128" align="center" valign="middle" rowspan="5" width="180px">
									
								</td>
								
								<td height="108" align="center" valign="middle" colspan="2" width="100px">
									
								</td>
							</tr>
							<tr>
								<td height="50" align="center" >
									<label>
										<span class="span" id="spanUser">请输入您的用户名</span>
										<input name="loginId" type="text" class="login user_bg"
											id="txtUser" 
											onkeydown="if (event.keyCode == 13) $('#txtPassword').focus();" autocomplete="off" />
									</label>
								</td>
							</tr>
							<tr>
							<td align="center"  height="78">
									<label>
										<span class="span">请输入您的密码</span>
										<input type="text" name="userPass" style="ime-mode:disabled"
											class="login password_bg" id="txtPassword" 
											onkeyup="translatePass(event);this.value=this.value.replace(/./g,'●');"
											onkeydown="tipClear(event);if (event.keyCode == 13) btnLogin_onclick();">
										<input type="hidden" id='txtPassword1'/>
									</label>
								</td>
							</tr>
							<tr>
							<td align="center" valign="top" height="78"  style="height: 50px;">
								<table width="100%" border="0" >
								<tr>
									<td align="right"><img src="<%=basePath%>images/login_button.jpg"
										onclick="btnLogin_onclick()" width="74" height="34"></td>
									<td align="left"><img src="<%=basePath%>images/login_rest.jpg"
										onclick="resetForm()" width="74" height="34" style="margin-left: 20px;"></td>
								</tr>
								</table>
									
										
										
								</td>
							</tr>	
							
							<tr>
								
								<td align="center" valign="bottom" id="indicator_container"
									style="color: red; font-size: 12px;" >
									${message} ${msg}
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</body>
	<script type="text/javascript">
		$("#txtUser").change( function() {
});
	</script>
</html>