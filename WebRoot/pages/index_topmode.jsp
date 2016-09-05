<%@ page language="java" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
		<title>${sessionScope.COMPANY}${sessionScope.SYSTEM}</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/css.css" charset="UTF-8" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>jq_easyui/themes/default/tabs.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>jq_easyui/themes/icon.css">
		<script src="<%=basePath%>js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.easyui.min.js"></script>
	<style type="text/css">
	</style>	
<script type="text/javascript"><!--
	if(top!=self){window.parent.location.href="<%=request.getContextPath()%>/control/login/toIndex";}
	var currentUserMenus = [];
	var menuMap=[];//菜单ID与菜单对象的map;
	var mid=[];
	var homePageMenuId="";
	var homePageMenuId=null;
	var addFlag = true;
	var menuLineCount=-1;
	$(function(){		
///调用加载menu后台方法	
    	$.ajax({
    		type: "POST",
    		async: false,
    		cache: false,
    		url: "<%=basePath%>control/menu/getAllMenusByCurrUser.action?etc="+new Date().getTime(),
    		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    		success: function (data){
    			currentUserMenus=data;
    			getFirstLevelMenus();
    			initMenu2();
    			if(homePageMenuId!=null){
    				firstMenuOnClick(homePageMenuId);//20140815
    			}else{
    				//增加默认欢迎页面
    				var url="<%=basePath%>pages/mainPage/mainPage.jsp";
    				addFlag = true;
    				$('#tt').tabs('add',{  
    				    title:"欢迎",  
    				    content: createFrame(url),  
    				    border:false,
    				    closable:true,
    				    loadingMessage: '正在加载中......'
    				});
    			}
    		}	
    	});
		//tab绑定事件
		$('#tt').tabs({
		  onSelect: function(title,index){
			var menu1Name = getMenu1Name(title);
			selMenu1(menu1Name);
			if(!addFlag){
				for(i=0;i<currentUserMenus.length;i=i+1){
					var item = currentUserMenus[i];
					if(title == item.menuName && item.openStatus == 1){
						var tab = $('#tt').tabs('getSelected'); 
						var url = "<%=basePath%>" + item.linkUrl;
						$('#tt').tabs('update', {
							tab : tab,
							options : {
							content : createFrame(url)
							}
						});
						break;
					  }
				   }
			    }
		      },
			  onAdd:function(title,index){
			  	unbindTabSelectEvent();
				bindTabSelectEvent();
			  	bindTabCloseBtnClickEvent();
			  },
			  onUpdate:function(title,index){
			  	unbindTabSelectEvent();
				bindTabSelectEvent();
			  	bindTabCloseBtnClickEvent();
			  }		  
		  });
		
	 	 $("#right_").mouseover(function(){
		     $("#right_1").css("opacity","0.7");
		 });
		 $("#right_").mouseout(function(){
		    $("#right_1").css("opacity","1");
		 });
		$("#left_").mouseover(function(){
		    $("#left_1").css("opacity","0.7");
		 });
		 $("#left_").mouseout(function(){
		    $("#left_1").css("opacity","1");
		 }); 
	});
	
	//tabs-title,taby页title点击事件绑定
	function bindTabSelectEvent(){
		$('span.tabs-title').bind('click',function(){		
			addFlag = false;			
		});
	}
	//tabs-title,taby页title点击事件解绑
	function unbindTabSelectEvent(){
		$('span.tabs-title').unbind('click');
	}
	//根据一级菜单名设置一级菜单选中样式
	function selMenu1(name){
		var menus = $('.xialaguang');
		for(i=0;i<menus.length;i=i+1){
			if(name == menus[i].innerText){
			//background-color: #AFEEEE;color: #1a529c
				menus[i].style.backgroundColor =  '#E0ECFF';
				menus[i].style.color =  '#1a529c';
			}else{
				menus[i].style.backgroundColor =  '';
				menus[i].style.color =  '';
			}
		}
	}
	//根据二级菜单获取一级菜单title
	function getMenu1Name(title){
		var menu1Name = "";
		if(title == "首页"){
			return "首页";
		}else{
			for(i=0;i<currentUserMenus.length;i=i+1){
				if(title == currentUserMenus[i].menuName){
					var pId = currentUserMenus[i].parentId;
					for(j=0;j<mid.length;j=j+1){
						if(pId == mid[j].menuId){
							menu1Name = mid[j].menuName;
							break;
						}
					}
					break;
				}
			}
		}
		return menu1Name;
	}
	//获取主(一级)菜单
	function getFirstLevelMenus(){
		currentUserMainMenus=[];
		for(var i=0;i<currentUserMenus.length;i++){
			var menu=currentUserMenus[i];
			menuMap[menu.menuId]=menu;
			if(menu.parentId && menu.parentId=='M'){
				if(menu.menuName=="首页")
					homePageMenuId=menu.menuId;
				mid.push(menu);
			}
		}
	}

 
	//加载显示二级菜单信息方法
	function showSubLevelMenuList(parentId,parentName){
		document.getElementById("showSubMenuDiv").style.display="none";
		var subHtml="<div class='showSubMenuDivCss'>";
		var subMenuCount=0;
		for(var i=0;i<currentUserMenus.length;i++){
			var menu=currentUserMenus[i];
			if(menu.parentId==parentId){				
				subHtml+="<div class='subMenu' onmouseover=\"setSubMenuStyle('"+parentId+"','"+menu.menuId+"')\" onmouseout=\"reSetSubMenuStyle('"+menu.menuId
						+"')\" onclick=\"onMenuClick('"+menu.menuId+"')\" id='"+menu.menuId+"' " ;
				if(subMenuCount==0){
					subHtml+=" style:'border-top: white 1px solid;'";
				}
				subHtml+=">"+menu.menuName+"</div>";
				subMenuCount++;
			}
		}
		subHtml+="</div>";
		if(subMenuCount<=0)
			return;
		document.getElementById("showSubMenuDiv").innerHTML=subHtml;
		document.getElementById("showSubMenuDiv").style.display="block";
	}
	
  
	//系统推出事件
 	function exitapp(){
		if(window.confirm('确定退出系统？')){
			document.forms["myform"].action="<%=basePath%>control/login/exitApp.action";
			document.forms["myform"].target="";
			document.forms["myform"].submit();		
		}
	}
		//打开页面并将当前操作的菜单显示
	function onMenuClick(id){
		var menu=menuMap[id];
		if(menu==null)
			return;
		for(var m = 0;m < mid.length; m++){	
			var menuMain = mid[m];
			if(menuMain.menuId.split("_")[0]!= menu.menuId.split("_")[0]){
			  document.getElementById('xuzhong_'+menuMain.menuId.split("_")[0]).style.cssText='';
			}else{
			  document.getElementById('xuzhong_'+menu.menuId.split("_")[0]).style.cssText='background-color: #E0ECFF;color: #1a529c';
			}
		}
		var linkUrl=menu.linkUrl;
		if(linkUrl==null || linkUrl=="" || linkUrl=="null")
			return;
		
		$("#menu_con li a").removeClass("span_click");
		$("#submenu_a_"+id).addClass("span_click");
		
	    //获取token
		if(linkUrl.indexOf('http://')<0){
			linkUrl="<%=basePath%>"+linkUrl;
		}
		addTab(menu,linkUrl);
		$(".children").hide();
	}
	
	function addTab(menu,url){
		if($('#tt').tabs('exists',menu.menuName)){
			addFlag = false;
			$('#tt').tabs('select',menu.menuName)
		}else{
			addFlag = true;
			$('#tt').tabs('add',{  
			    title:menu.menuName,  
			    content: createFrame(url),  
			    border:false,
			    closable:true,
			    loadingMessage: '正在加载中......'
			});	
		}
	}
	function bindTabCloseBtnClickEvent(){
		$('.tabs-close').unbind('click');
		$('.tabs-close').bind('click',function(event){
			
			 if(event.target==this){
				 var titleSpan = $(this).siblings('.tabs-inner').children('.tabs-title');
				 var title =  titleSpan.html();
				 
				 $('#tt').tabs('close',title);
				 event.stopPropagation();
			 }
		});
	}
	function createFrame(url) {
	    var s = '<iframe frameborder="0" scrolling="auto" src="' + url + '" style="width:100%;height:100%;border-style: none;"></iframe>';
	    return s;
	}
	//20140815OnMouseOver事件取消
	function menuOnMouseOver(id){
		$(this).find('.children').animate({ opacity:'show', height:'show' },200);
	$(this).find('.xialaguang').addClass('navhover');
	}
	function firstMenuOnClick(id){	
		$("#nav li a").removeClass("nav_on");
		$("#"+id).addClass("nav_on");
		$("#menu_con div").css({"display":"none"});
		$("#subMenu"+id).css({"display":"block"});		
		onMenuClick(id);
	}
	
	function getHelp(){
		window.open("<%=basePath%>resources/default.htm","用户使用帮助",
				'height=700,width=650,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=yes, status=no');
	}
	var htm='';
	var sendMenus	= [];	
	function initMenu2(){
		for(var m = 0;m < mid.length; m++){
			var chilHtml="";
			var menuclickHtml = "";
			var menuMain = mid[m];
			var flag=false;
			if(menuMain.parentId=='M'){
				htm+='<li class="stmenu" style="height:45px;">';
				htm+='<h3><a href="#" class="xialaguang" id="xuzhong_'+menuMain.menuId+'" onclick="jvascript:onMenuClick(\''+menuMain.menuId+'\') "><span>'+menuMain.menuName+'</span></a></h3>';
				parentId=menuMain.menuId;
				var subHtml='';
				for(var k=0;k<currentUserMenus.length;k++){
					var menu=currentUserMenus[k];
					if(menu.parentId==parentId){
							if(!flag){
								htm+='<ul class="children">';	
								flag=true;
							}
						if(menu.linkUrl!=null&&menu.linkUrl!=undefined&&menu.linkUrl.indexOf("impConfigMapper/getImpConfigInfoList")<0){	
							htm+='<li><h3><a href="#" onclick="jvascript:onMenuClick(\''+menu.menuId+'\') "><span> '+menu.menuName+'</span></a></h3></li>' ;
						}
					}
				}
			if(flag){
				htm+="</ul>";
			}
		}
		
		if(m+1==mid.length){//添加菜单栏折叠按钮
			htm+='<li class="stmenu"  style="float:right;">';
			htm+='<h3><a href="#"><span><img id="expandImg" onclick="mobleTop()" src="<%=basePath%>images/bttnCllps.gif"/></span></a></h3>';
			htm+='</li>';
		}
			htm+='</li>';			
		}
		
		$("#first").append(htm);
		if(menuLineCount<0)
		{
			menuLineCount=parseInt($(".header").width()*0.96/$("#xuzhong_"+menuMain.menuId).width());
		}
		if(mid.length<=menuLineCount)
		{
			$("#right_").hide();
			$("#left_").hide();
			$("#menu_in").css("width","1253px");
			$("#nav-menu").css("width","1253px");
		}
		move();//展示下级子节点
	}

	
	
	//显示或隐藏top
	function mobleTop() {
		var isShowHeader = $(".header").css("display")!="none";
		//resetIFrameHeight(!isShowHeader);
		if(isShowHeader) {			
			$(".header").hide();	
			$("#expandImg").attr("src","<%=basePath%>images/bttnExpnd.gif");
		} else {
			$(".header").show();
			$("#expandImg").attr("src","<%=basePath%>images/bttnCllps.gif");
		}	
	}
	function move(){
		$('.menu > li').hover(function(){
				var menu1Obj = $(this);
			  	var x = menu1Obj.offset().top + menu1Obj[0].clientHeight; 
			  	x = x + "px";
			  	var y = menu1Obj.offset().left; 
			  	y = y + "px";
		  		$(this).find('.children').css("top",x);
		  		$(this).find('.children').css("left",y);
			$(this).find('.children').animate({ opacity:'show', height:'show' },200);
			$(this).find('.xialaguang').addClass('navhover');
		}, function() {
			$('.children').stop(true,true).hide();
			$('.xialaguang').removeClass('navhover');
		});
	}
	
	  function beforeOut()  
      {
      if(event.clientY<0||event.altKey)
            {   
             // window.event.returnValue="确定要退出本页吗？"; 
             document.forms["myform"].action="<%=basePath%>control/login/exitApp.action";
			 document.forms["myform"].target="";
			 document.forms["myform"].submit();  
            }   
      }
        var constMenuHeight=45;
		function move_right()
		{
		    var ff=$("#nav-menu").scrollTop();
		    if(((ff/constMenuHeight)+1)*menuLineCount<mid.length)	 	
		    {
			    $("#nav-menu").scrollTop(ff+constMenuHeight);
			    var id=mid[menuLineCount*((ff/constMenuHeight)+1)].menuId;
			    firstMenuOnClick(id);	
		    } 
		}
		function move_left()
		{		

			 var ff=$("#nav-menu").scrollTop();
			 if(ff>0)
			 {	 	
			   $("#nav-menu").scrollTop(ff-constMenuHeight);
			   var id=mid[menuLineCount*((ff/constMenuHeight)-1)].menuId;	
		     }		    
		}
	
	--></script>
	</head>
	<body onbeforeunload="beforeOut()">
		<div class="align-center">
			<div class="header">
			
				<table style="width: 100%;" border=0>
					<tr height="10px"></tr>
					<tr>
						<td>
							<div style="z-index: 2; position: relative; margin-top: 17px; float: right; padding-right: 40px; text-align: center;">
								<a style="color: white; border: 0; margin-right: 30px;" href="javascript:;">
									<img style="border: 0" src="<%=basePath%>images/help.png" onclick="" />
								</a>
								<!-- getHelp() -->
								<a href="javascript:exitapp();"><img style="border: 0;" src="<%=basePath%>images/shutdown.png"></a>
							</div>
							<div style="font-size: 12px; height: 18px; z-index: 2; position: relative; margin-top: 17px; float: right; 
							padding-right: 30px; font-weight: bold; text-align: right; color: #333333; font-family: Microsoft YaHei;">
								<div style="margin-top: 3px; color: #0A756D; top: 0px;">
									${currUser.deptName}
								</div>
								<div style=" font-style: normal; text-decoration: none; color: #0A756D;">
									${currUser.userName }[${currUser.loginId}]
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id='menu_out' style="padding-left:0px;width:100%;">
			<div id="left_" class="nav_li" onclick="move_left();" style="width:2.1%;height:42px;float:left;">
				<img id="left_1" style="width:100%;heigh:100%;margin-top:7px;" src="<%=basePath%>images/left.gif"></img>
			</div>
				<div id='menu_in' style="width:95.47%;overflow: hidden;float:left;">
					<ul>
						<li id='nav_li' style="overflow: hidden">
							<div id="nav-menu" class="nav-menu" style="width:100%;overflow: hidden"><!-- 滚动的DOM -->
								<ul class="menu" id="first" ></ul><!-- 菜单添加位置 -->
							</div>
						</li>								 					
					</ul>
				</div>			
				<div id="right_" class="nav_li" onclick="move_right();" style="width:2.1%;height:42px;float:left;">
					<img id="right_1" style="width:100%;heigh:100%;margin-top:7px" src="<%=basePath%>images/right.gif"></img>
				</div>		
			</div>
			<!-- 下面旧菜单 -->
			<div id="MenuBar" style="BACKGROUND: url(<%=basePath%>images/defualt_menu_bg.bmp) repeat-x; HEIGHT: 28px; COLOR: #ffffff; 
									FONT-SIZE: 12px;width:100%;display:none;">
				<div id="FirstLevelMenuDiv" style="width: 100%;"></div>
			</div>
			<div id="showSubMenuDiv" class="showSubMenuDivCss" onmouseout="hideSubMenuList()" onmouseover="showSubMenuList()"></div>
			<!-- 旧菜单 -->
			<div id="container" style="width: 100%; height: 715px; 
				background: white; margin: 0px; padding: 0px;overflow: hidden;">
				<div id="tt" class="easyui-tabs" data-options="fit:true" style="margin: 0px; padding: 0px;overflow: hidden;">
				</div>
			</div>
			<div style="text-align: center;width: 100%;margin: 0px;  height: 36px;background-image: url('<%=basePath%>images/bottom_bg.jpg'); 
			z-index: 1; background-repeat: repeat-x;">
				<p style="color: white; padding-top: 10px;">
					${sessionScope.COMPANY}${sessionScope.SYSTEM}&nbsp;&nbsp;&nbsp;&nbsp;建议使用1280X768分辨率

					&nbsp;&nbsp;&nbsp;&nbsp;IE9.0版本浏览器&nbsp;
				</p>
			</div>
			<div style="display: none">
				<form action="" method="post" name="myform"></form>
			</div>

		</div>

	</body>
</html>