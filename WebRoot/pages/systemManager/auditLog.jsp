<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@include file="../common_header_easyui.jsp"%>
		<script>
		var BASE_PATH='<%=basePath%>';
		</script>
	</head>
<script type="text/javascript">
	         /// <summary>加载页面时初始化方法</summary>
			function doOnload() {
				//默认日期
			    $("#loginTime").datebox("setValue", currentYesToDate());
			    initCombox();
			 	initAuditUserGrid();//数据列表 
			}
		
			/// <summary>获取当前时间并按照YYYYMMDD进行拼接方法</summary>
			function currentYesToDate()
			  {
			     var mydate=new Date();
			     mydate.setDate(mydate.getDate());
			     var myyear=mydate.getFullYear();
			     var mymonth=mydate.getMonth()+1;
			     var myday=mydate.getDate();
			     if(myday<=0){
			       myday = myday+1;
			     }
			     if (myday<10) myday = "0"+myday;
			     if(mymonth<10) mymonth = "0"+mymonth;
			     var myfullDate=myyear+"-"+mymonth+"-"+myday;
			     return myfullDate;
			  }
			
			/// <summary初始化用户审计日志datagrid方法</summary>
			function initAuditUserGrid(){
				$('#dg_audit_user').datagrid({
					url:getAuditUrl(),
					singleSelect:true,
					rownumbers:true,
					striped:true,//条纹
					fitColumns:true,
					width:$("#dgwindow")[0].clientWidth-0,	
					height:$("#dgwindow")[0].clientHeight-5,
					pagination:true,//分页控件
					pageNumber:1,
					pageSize:15,   
					pageList:[10,15,20,30,40,50],
					columns:[[ 
					    //{field:'id',title:'ID',width:80,align:'center'},
					    {field:'login',title:'当日登陆记录',width:80,align:'center',formatter:auditUser},
					    {field:'loginId',title:'登陆账号',width:120,align:'center'},
					    {field:'userName',title:'用户名称',width:140,align:'center'},
						{field:'loginTime',title:'登陆时间',width:160,align:'center'},
						{field:'loginOutTime',title:'退出时间',width:160,align:'center'},
						{field:'loginState',title:'登陆状态',width:100,align:'center'},
						{field:'addMac',title:'MAC',width:150,align:'center'},
						{field:'addIp',title:'IP',width:150,align:'center'},
						{field:'audState',title:'审计状态',width:120,align:'center'}
					]],
					view : $.orzView
				});
			}
			
			/// <summary>formatter,给相应的记录增加方法和图片的方法</summary>
			function auditUser(val,row){
			    return '<img alt="" style="width:15px;height:15px;" src="<%=basePath%>jq_easyui/themes/icons//detailview.gif" onmouseover="this.style.cursor=\'hand\'" onClick="auditUserDayHis(\''
					+ row.loginId + '\');">';
		}
   
		/// <summary>获取加载datagrid所需的url参数方法</summary>
		function getAuditUrl() {
			var _url = BASE_PATH + "control/audit/auditUserQuery?etc="
					+ new Date().getTime(); //字节点URL
			var loginState = $("#loginState").combobox("getValue");
			if (loginState) {
				_url += "&loginState="
						+ encodeURIComponent(encodeURIComponent(loginState));
			}
			var loginTime = ($("#loginTime").datebox("getValue")).replace(/-/g, "");
			if (loginTime) {
				_url += "&loginTime="
						+ encodeURIComponent(encodeURIComponent(loginTime));
			}
			return _url;
		}
	    
		/// <summary>初始化查询条件中有下拉框方法</summary>
		function initCombox() {
			// 初始化交互方式
			$('#loginState').combobox({
				width : 154,
				editable : false
			});
			// 激活点击文本框也显示下拉面板的功能
			$(".combo").click(function() {
				$(this).prev().combobox("showPanel");
			});
		}
 
		/// <summary>加载弹出窗口方法</summary>
		function initWindow() {
			$('#div_audit_user_history_grid').window({
				title : '当日用户登陆记录',
				width : 960,
				height : 360,
				minimizable : false,
				maximizable : false,
				resizable : false,
				collapsible : false,
				modal : true
			});
		}
 
		/// <summary>加载用户当日数据方法</summary>
		/// <param name="val">获取url所需的参数</param>
		function auditUserDayHis(val) {
			initWindow();
			$('#dg_audit_user_history').datagrid({
				url : getAuditDayUrl(val),
				singleSelect : true,
				rownumbers : true,
				striped : true,//条纹
				width : $("#div_audit_user_history_grid")[0].clientWidth-0,
				height : $("#div_audit_user_history_grid")[0].clientHeight-5,
				fitColumns : true,
				pagination : true,//分页控件
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 10, 15, 20, 30, 40, 50 ],
				columns : [ [ {
					field : 'loginId',
					title : '登陆账号',
					width : 80,
					align : 'center'
				}, {
					field : 'userName',
					title : '用户名称',
					width : 120,
					align : 'center'
				}, {
					field : 'loginTime',
					title : '登陆时间',
					width : 150,
					align : 'center'
				}, {
					field : 'loginOutTime',
					title : '退出时间',
					width : 150,
					align : 'center'
				}, {
					field : 'loginState',
					title : '登陆状态',
					width : 80,
					align : 'center'
				}, {
					field : 'addMac',
					title : 'MAC',
					width : 120,
					align : 'center'
				}, {
					field : 'addIp',
					title : 'IP',
					width : 120,
					align : 'center'
				}, {
					field : 'audState',
					title : '审计状态',
					width : 80,
					align : 'center'
				} ] ],
				view : $.orzView
			});
		}
		
		/// <summary>拼接URL方法</summary>
		/// <param name="loginId">拼接url所需的标识参数</param>
		function getAuditDayUrl(loginId) {
			var _url = BASE_PATH + "control/audit/auditUserDayQuery?etc="
					+ new Date().getTime(); //字节点URL
			var loginState = $("#loginState").combobox("getValue");
			if (loginState) {
				_url += "&loginState="
						+ encodeURIComponent(encodeURIComponent(loginState));
			}
			var loginTime = ($("#loginTime").datebox("getValue")).replace(/-/g, "");
			if (loginTime) {
				_url += "&loginTime="
						+ encodeURIComponent(encodeURIComponent(loginTime));
			}
			if (loginId) {
				_url += "&loginId="
						+ encodeURIComponent(encodeURIComponent(loginId));
			}
			return _url;
		}
</script>
<body class = "easyui-layout" style="margin: 2px 2px 2px 2px;background-color: #DFEFFE" onload="doOnload()">
		 <div data-options="region:'north',title:'用户审计'" style="height:60px;text-align:center" > 
		    <div style="height:100%; width:100%;text-align:center;background-color:#EDF4FA"> 
					<table  border="0" align="left" cellpadding="0" cellspacing="0" class="tb_side" style="margin-top:5px;"> 
						<tr>
						    <td nowrap="nowrap" width="35px"></td>
							<td align="left" style="padding-left: 12px; font-size:12px">
								登陆时间：<input id="loginTime"  class="easyui-datebox" style="width:120px; " editable="false"></input> 					
							</td>
							<td nowrap="nowrap" width="35px"></td>
							<td align="left" style="padding-left: 12px; font-size:12px">	
								登陆状态：<select id="loginState" class="easyui-combobox" style="width:120px"  name="selse" panelHeight="85" >
									<option value="">--请选择--</option>
									<option value="0">失败</option>
									<option value="1">成功</option>
									<option value="2">锁定</option>
								</select> 	
							</td>
							<td nowrap="nowrap" width="40px"></td>
						    <td align="right">
								<a href="javascript:initAuditUserGrid();"  class="easyui-linkbutton" iconCls="icon-search" nowrap="nowrap">查询</a> 
							</td>
						</tr>
					</table> 
				</div> 
		 </div>
		 
		 <div data-options="region:'center'" id="dgwindow"> 
		 		<div id="div_audit_user_grid" style="margin: 0px; padding: 0px;">
					<table id="dg_audit_user"></table>
				</div>
				<div id="div_audit_user_history_grid" style="margin: 0px; padding: 0px;">
					<table id="dg_audit_user_history"></table>
				</div>
		 </div>
	</body>
</html>
