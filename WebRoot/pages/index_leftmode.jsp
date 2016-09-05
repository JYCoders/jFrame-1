<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<%@include file="common_header_easyui.jsp" %>
	<title>${sessionScope.COMPANY}${sessionScope.SYSTEM}</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
		
<body class="easyui-layout" onload="doOnload()">
	<!-- 页面头部信息 -->
	<div data-options="region:'north',split:false,border:false">
		<div class="header">
			<div style="font-size: 30px; height: 18px; position: relative; margin-top: 24px; float: left; 
						padding-left: 270px; font-weight: bold; text-align: center; color: #00706b; font-family: SimHei">
						安徽考试系统</div>
			<div style="position: relative; float: right; text-align: center;" class="header-right">
				<div style="margin-top: 20px; margin-left: 5px;">
				<a style="color: white; border: 0; margin-right: 10px;" href="javascript:;">
					<img style="border: 0" src="<%=basePath%>images/help.png" onclick="" />
				</a>
				<a href="javascript:exitapp();"><img style="border: 0;" src="<%=basePath%>images/shutdown.png"></a>
				</div>
			</div>
			<div style="font-size: 12px; height: 18px; z-index: 2; position: relative; margin-top: 20px; float: right; 
						padding-right: 5px; font-weight: bold; text-align: right; color: #333333; font-family: Microsoft YaHei;">
				<div style="margin-top: 3px; color: #0A756D; top: 0px;">
					${currUser.deptName}
				</div>
				<div style=" font-style: normal; text-decoration: none; color: #0A756D;">
					${currUser.userName }[${currUser.loginId}]
				</div>
			</div>
		</div>
	</div>

	<!--页面导航-->
	<div data-options="region:'west',split:true,border:false"
		iconCls="icon-navigation" title="系统导航" style="width: 160px; padding: 1px; overflow: hidden;">
		<div id="acdMenu" class="easyui-accordion" data-options="fit:true,border:false">
		</div>
	</div>
	
	<!--页面显示部分-->
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<div class="easyui-tabs" id="maintabs" data-options="fit:true,border:false">
		</div>
	</div>
	
	<!-- 页面底部信息 -->
	<div data-options="region:'south',split:false"
		style="text-align: center;width: 100%; height: 50px; margin: 0px; background-image: url('<%=basePath%>images/bottom_bg.jpg'); 
			z-index: 1; background-repeat: repeat;">
		<p style="color: white; padding-top: 10px;">
			${sessionScope.COMPANY}${sessionScope.SYSTEM}&nbsp;&nbsp;&nbsp;&nbsp;建议使用1280X768分辨率

			&nbsp;&nbsp;&nbsp;&nbsp;IE9.0版本浏览器&nbsp;
		</p>
	</div>
	<div style="display: none">
		<form action="" method="post" name="myform"></form>
	</div>
</body>

<script type="text/javascript">
	var curUserMenus = []; // 当前用户具有权限的菜单集合
	var menuMap = []; //菜单ID与菜单对象的MAP;
	var firstLevelMenus = []; // 一级主菜单集合
	var homePageMenuId = ""; // 主页菜单ID
	var addFlag = true; // 菜单添加标记
	var defualtImages = "images/defualt_menu.gif"; // 默认图片
	var defualtIconCls = "icon-defualtIconCls"; // 默认样式
	var defualtMainPageIconCls = "icon-mainpage"; // 默认主页图片样式
	var defualtAcdIconCls = "icon-defualtAcdIconCls"; // 默认手风琴菜单样式
	
	/// <summary>初始化页面</summary>
	function doOnload(){
	 	BASE_PATH = '<%=basePath %>'
	    
		initMenu(); // 初始化菜单
	}
		
	/// <summary>初始化菜单</summary>
	function initMenu() {
		var url = "<%=basePath%>control/menu/getAllMenusByCurrUser.action?etc="+new Date().getTime();
		
		// 异步获取具有权限的菜单项
		$.ajax({
			url: url,
			cache: false,
			type: "get",
			dataType:"json",
			success: function(data){
				curUserMenus = data;
				
				// 获取一级菜单
				getFirstLevelMenus();
				
				// 初始化手风琴式菜单
				initAccordionMenus(); 
				
				// 初始化首页
				initMainPage();
			},
			error: function(request) {
                alert("获取菜单数据失败!");
                return;
            }
		});
	}
	
	/// <summary>获取主(一级)菜单</summary>
	function getFirstLevelMenus(){
		for(var i = 0; i < curUserMenus.length; i++){
			var menu = curUserMenus[i];
			menuMap[menu.menuId] = menu;
			if(menu.parentId && menu.parentId === 'M'){
				if(menu.menuName === "首页") {
					homePageMenuId = menu.menuId;
				}
				firstLevelMenus.push(menu);
			}
		}
	}
	
	/// <summary>初始化手风琴式菜单</summary>
	function initAccordionMenus() {
		var menuImages = ""; // 菜单图片
		var menuIconCls = ""; // 菜单样式
		for(var i = 0; i < firstLevelMenus.length; i++) {
			var parentId = ""; // 父菜单ID
			var chilHtml = "";
			var menuclickHtml = "";
			var mainMenu = firstLevelMenus[i]; // 一级主菜单
			var flag = false;
			if(mainMenu.parentId == 'M') {
				var menuHtml = new Array(); //页面脚本
				parentId = mainMenu.menuId;
				for(var j = 0; j < curUserMenus.length; j++) {
					var menu = curUserMenus[j];
					if(menu.parentId === parentId){
						if(!flag) {
							menuHtml.push("<div class=\"eui-menu\"><ul>");
							flag = true;
						}
						// 判断菜单图片是否为空，如果为空则设置为默认图片
						if (menu.iconOpen != null && menu.iconOpen.length > 0) {
							menuImages = BASE_PATH + menu.iconOpen;
						} else {
							menuImages = BASE_PATH + defualtImages;
						}
						
						// 判断菜单图片样式是否为空，如果为空则设置为默认图片样式
						if (menu.icon != null && menu.icon.length > 0) {
							menuIconCls = menu.icon;
						} else {
							menuIconCls = defualtIconCls;
						}
						menuHtml.push("<li><a href=\"#\" onClick=\"onMenuClick('" + menu.menuId + "', false)\" title=\"" + menu.menuName + "\" iconCls=\"" + menuIconCls + "\">");
						menuHtml.push("<img src=\"" + menuImages + "\" width=\"40\" height=\"40\" border=\"0\"></img> <br/>");
						menuHtml.push(menu.menuName);
						menuHtml.push("</a></li>");
					}
				}
				if(flag){
					menuHtml.push("</ul></div>");
				}
				
				// 生成手风琴
				$('#acdMenu').accordion('add',{
					title: mainMenu.menuName,
					iconCls: mainMenu.icon != null && mainMenu.icon.length > 0 ? mainMenu.icon : defualtAcdIconCls,
					content: menuHtml.join(""),
					selected: homePageMenuId == mainMenu.menuId ? true : false
				});
			}
		}
	}
	
	/// <summary>初始化首页</summary>
	function initMainPage() {
		if (homePageMenuId != null && homePageMenuId.length > 0) {
			// 初始化展示首页
			for(var i = 0; i < curUserMenus.length; i++) {
				var menu = curUserMenus[i];
				if(menu.parentId === homePageMenuId){
					onMenuClick(menu.menuId, true);
				}
			}
		} else {
			//增加默认欢迎页面
			var url="<%=basePath%>pages/mainPage/mainPage.jsp";
			addFlag = true;
			$('#maintabs').tabs('add',{  
			    title:"欢迎页",  
			    content: createFrame(url), 
			    iconCls: defualtMainPageIconCls,
			    border:false,
			    closable:true,
			    loadingMessage: '正在加载中......'
			});
		}
	}
	
	/// <summary>点击菜单事件</summary>
	/// <param name="id">点击的菜单ID</param>
	/// <param name="isMainPage">是否为首页</param>
	function onMenuClick(id, isMainPage){
		var menu = menuMap[id];
		if(menu == null) {
			return;
		}
		var linkUrl = menu.linkUrl; // 获取访问路径
		if(linkUrl == null || linkUrl == "" || linkUrl == "null") {
			return;
		}

		if(linkUrl.indexOf('http://') < 0 ){
			linkUrl = "<%=basePath%>"+linkUrl;
		}
		addTab(menu, linkUrl, isMainPage);
	}
	
	/// <summary>添加Tab页</summary>
	/// <param name="menu">菜单</param>
	/// <param name="url">访问路径</param>
	/// <param name="isMainPage">是否为首页</param>
	function addTab(menu, url, isMainPage){
		var tabIconCls = "";
		if($('#maintabs').tabs('exists', menu.menuName)) {
			addFlag = false;
			$('#maintabs').tabs('select', menu.menuName)
		} else {
			addFlag = true;
			if (isMainPage) {
				tabIconCls = menu.icon != null && menu.icon.length > 0 ? menu.icon : defualtMainPageIconCls;
			} else {
				tabIconCls = menu.icon != null && menu.icon.length > 0 ? menu.icon : defualtIconCls;
			}
			$('#maintabs').tabs('add',{  
			    title: menu.menuName,  
			    content: createFrame(url), 
			    iconCls: tabIconCls,
			    border: false,
			    closable: true,
			    loadingMessage: '正在加载中......'
			});	
		}
	}
	
	/// <summary>创建Frame框架</summary>
	/// <param name="url">访问路径</param>
	function createFrame(url) {
	    var frame = '<iframe frameborder="0" scrolling="auto" src="' + url + '" style="width:100%;height:99.5%;border-style: none;"></iframe>';
	    return frame;
	}
	
	/// <summary>系统退出事件</summary>
 	function exitapp(){
		if(window.confirm('确定退出系统？')){
			document.forms["myform"].action="<%=basePath%>control/login/exitApp.action?etc="+new Date().getTime();
			document.forms["myform"].target="";
			document.forms["myform"].submit();		
		}
	}
</script>
</html>