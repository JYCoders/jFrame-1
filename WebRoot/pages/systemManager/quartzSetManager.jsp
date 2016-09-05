 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>		
	<title></title>
	<%@include file="../common_header_easyui.jsp" %>
	<script type="text/javascript" src="<%=basePath%>pages/systemManager/quartzSetManager.js"></script>	
	<script>
		
	</script>  
	</head>
	
	<script type="text/javascript">
		var BASE_PATH;
		/// <summary>初始化页面布局</summary>
		function doOnload(){
			BASE_PATH='<%=basePath %>'; 
			initTaskGrid(); // 初始化任务列表
		    initCombox(); // 初始化下拉框
		    searchTask(); // 初始化数据
		}
		
		/// <summary>初始化下拉框</summary>
		function initCombox() {
			// 初始化交互方式
		    /* $('#taskProcClass').combobox({  
			     width:260,
		         editable:false
		     }); */
			// 激活点击文本框也显示下拉面板的功能
		     $(".combo").click(function(){
		    	$(this).prev().combobox("showPanel");
	    	 });
	    	 loadMastTypeComList3("START_MODE", "cmbStartMode", "启动方式", 0); // 绑定启动方式下拉框
	    	 loadMastTypeComList3("CYCLE", "cmbTaskCycle", "频度", 0); // 绑定频度下拉框
	    	 $("#cmbTaskProcClass").combobox("loadData", ${className}); // 绑定启动类下拉框
	    }
		
		/// <summary>初始化任务列表</summary>
		function initTaskGrid(){
			$('#dgTaskInfo').datagrid({
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				striped:true,//条纹
				width: $("#div_grid")[0].clientWidth-0,	
				height: $("#div_grid")[0].clientHeight-5,
				pagination:true,//分页控件
				pageNumber:1,
				pageSize:15,   
				pageList:[10,15,20,30,40,50],
				columns:[[ 
				    {field:'taskId',title:'日志',width:70,align:'center',
				    	formatter:function(value,rec){
		                      return "<img id=\"hand\" style=\"cursor: pointer;\" width=\"14\" src=\"../../images/search.png\" onClick=\"onSearchLog('" + value + "')\" />";
		                  }
		             },
				    {field:'taskName',title:'任务名称',width:150,align:'left'},
				    {field:'taskProcName',title:'启动类名称',width:130,align:'left'},
					{field:'taskProcClass',title:'启动类',width:260,align:'left'},
					{field:'taskCycle',title:'频度',width:60,align:'center'},
					{field:'ctiveTimeName',title:'调度时间说明',width:260,align:'left'},
					{field:'startMode',title:'启动方式',width:80,align:'center'},
					{field:'startStat',title:'启动状态',width:80,align:'center'},
					{field:'exeStat',title:'执行状态',width:100,align:'center'}
				]],
				toolbar:[{
						id:'btnAdd',
						text:'新增任务',
						iconCls:'icon-add',
						handler:addTask
					},'-',{
						id:'btnEdit',
						text:'编辑任务',
						iconCls:'icon-edit',
						handler:editTask
					},'-',{
						id:'btnDelete',
						text:'删除任务',
						iconCls:'icon-delete',
						handler:deleteTask
					},'-',{
						id:'btnTriggerTask',
						text:'立即执行任务',
						iconCls:'icon-component',
						handler:triggerTask
					},'-',{
						id:'btnResumeTask',
						text:'启动任务',
						iconCls:'icon-redo',
						handler:resumeTask
					},{
						id:'btnPauseTask',
						text:'停用任务',
						iconCls:'icon-undo',
						handler:pauseTask
					}],
				onClickRow: function(rowNum,record){
					if(record.startStat == "启动") {
						$("#btnResumeTask").hide();
	                	$("#btnPauseTask").show();
	                } else {
	                	$("#btnResumeTask").show();
						$("#btnPauseTask").hide();
	                } 
				},
				onDblClickRow: function(rowNum,record){
					showForm("编辑调度任务");
				    $("#frmTaskDetail").form("clear"); // 重置表单数据
					loadFormData(record.taskId); // 加载表单数据
				}
			});
		}
		
		/// <summary>加载日志信息数据</summary>
		function loadLogInfo(taskId){
			$('#dgLogInfo').datagrid({
				url:getLogUrl(taskId),
				singleSelect:true,
				rownumbers:true,
				striped:true,//条纹
				fitColumns:true,
				width:$("#div_grid1")[0].clientWidth-0,		
				height:$("#div_grid1")[0].clientHeight-5,
				pagination:true,//分页控件
				pageNumber:1,
				pageSize:10,   
				pageList:[10,15,20,30,40,50],
				columns:[[ 
				    {field:'taskInstid',title:'详细信息',width:70,align:'center',
				    	formatter:function(value,rec){
		                      return "<img id=\"hand\" style=\"cursor: pointer;\" width=\"14\" src=\"../../images/search.png\" onClick=\"loadLogDetail('" + value + "')\" />";
		                }
			        },
				    {field:'taskName',title:'调用任务名称',width:160,align:'center'},
				    {field:'startDate',title:'启动时间',width:130,align:'center'},
					{field:'endDate',title:'结束时间',width:130,align:'center'},
					{field:'excStat',title:'执行状态',width:60,align:'center'},
					{field:'excSrc',title:'执行来源',width:60,align:'center'}
				]]
			});
		}
		
		/// <summary>新增任务</summary>
	    function addTask() {
			showForm("新增调度任务");
			$("#frmTaskDetail")[0].reset(); // 重置表单数据
			loadMastTypeComList3("START_MODE", "cmbStartMode", "启动方式", 0); // 绑定启动方式下拉框
	    	loadMastTypeComList3("CYCLE", "cmbTaskCycle", "频度", 0); // 绑定频度下拉框
	    	$("#cmbTaskProcClass").combobox("setValue", "");
	    }
	    
	    /// <summary>变更任务</summary>
	    function editTask () {
	    	var row = $('#dgTaskInfo').datagrid('getSelected');
	    	if(row == null){
				alert("请选择一行进行编辑！");
				return;
			}
			showForm("编辑调度任务");
			$("#frmTaskDetail").form("clear"); // 重置表单数据
			loadFormData(row.taskId); // 加载表单数据
	    }
	    
	    /// <summary>删除任务</summary>
	    function deleteTask () {
	    	var row = $('#dgTaskInfo').datagrid('getSelected');
	    	if(row == null){
				alert("请选择一行进行删除！");
				return;
			}
			if(!checkTaskHaveDetail(row.taskId)){
				return;
			} 
			deleteTaskById(row.taskId);
	    }
	    
	    /// <summary>立即执行任务</summary>
	    function triggerTask() {
	    	var row = $('#dgTaskInfo').datagrid('getSelected');
	    	if(row == null){
				alert("请选择一条任务进行执行！");
				return;
			}
			if (!confirm("确认立即执行【"+row.taskName+"】的任务吗？")) {
			 	return;
			}
			onTriggerJob(row.taskId);
	    }
	    
	    /// <summary>启动任务</summary>
	    function resumeTask() {
	    	var row = $('#dgTaskInfo').datagrid('getSelected');
	    	if(row == null){
				alert("请选择一条任务进行启动！");
				return;
			}
			if (!confirm("确认启动【"+row.taskName+"】的任务吗？")) {
			 	return;
			}
			onDynamicAdd(row.taskId);
	    }
	    
	    /// <summary>停用任务</summary>
	    function pauseTask() {
	    	var row = $('#dgTaskInfo').datagrid('getSelected');
	    	if(row == null){
				alert("请选择一条任务进行停用！");
				return;
			}
			if (!confirm("确认停用【"+row.taskName+"】的任务吗？")) {
			 	return;
			}
			onDeleteJob(row.taskId);
	    }
		
		/// <summary>查询</summary>
		function searchTask() {
			var _url = "<%=basePath %>control/task/getAllTask.action?etc="+new Date().getTime(); //字节点URL
		    var taskName = $("#txtTaskName").val();
		    var taskStatus = $("#taskStatus").datebox("getValue");
			if (taskStatus == "-1") {
				taskStatus = "";
			}
			
			// 查询参数
			var queryParams = $('#dgTaskInfo').datagrid('options').queryParams;
			
			// 参数重新赋值
			queryParams.taskName = encodeURIComponent(taskName.trim());			
			queryParams.startStat = taskStatus;

			$('#dgTaskInfo').datagrid('options').queryParams = queryParams;
			$('#dgTaskInfo').datagrid('options').url = _url;
			
			// 接口列表重新加载    
			$("#dgTaskInfo").datagrid('reload');
		}
		
		/// <summary>通过taskID查询调度任务信息，并填充到表单中</summary>
		/// <param name="taskId">调度任务ID</param>
		function loadFormData(taskId){
			var url = "<%=basePath %>control/task/queryById.action?taskId="+taskId+"&etc="+new Date().getTime();
			$.ajax({
		        type: "POST",
		        url: url,
		        async:false,
		        dataType: "json",
		        success: function (data) {
					$("#frmTaskDetail").form("load", data);
				}
			});	
		}
		
		/// <summary>显示用户form表单窗口</summary>
		/// <param name="title">窗口标题</param>
		function showForm(title){
			$("#form_container").css("display","block");
			$("#form_container").window({
				title:title,
			    width:650,  
			    height:400, 
			    top:40,
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    draggable:true,
			    resizable:false,
			    modal:true
			});
			$("#form_container").window("open");
		}
		
		/// <summary>关闭用户form表单窗口</summary>
		function closeForm(){
		 	$("#form_container").window("close");
		}
		
		/// <summary>校验页面元素并保存</summary>
		function checkAndSave(){
		    var easyuiValidata = $("#frmTaskDetail").form('validate');
                  if(!easyuiValidata){
                  return;
            }
            // 校验必选项是否选择了值
            var taskProcClas = $("#cmbTaskProcClass").combobox("getValue");
            if (taskProcClas == null || taskProcClas.length == 0) {
            	alert("请选择启动类");
            	$("#cmbTaskProcClass").focus();
            	return;
            }
            var startMode = $("#cmbStartMode").combobox("getValue");
            if (startMode == null || startMode.length == 0) {
            	alert("请选择启动方式");
            	$("#cmbStartMode").focus();
            	return;
            }
		    var taskId = $("#taskId").val();
			if(taskId != null && taskId.length > 0){//若编辑ID存在，则是修改，若编辑ID不存在，则是新增
		    	updateTaskInfo();
		    } else {
		    	addTaskInfo();
		    }
			
		}

	    /// <summary>新增任务信息</summary>
		function addTaskInfo(){
			var url = "<%=basePath %>control/task/saveQuartzSetLog?etc="+new Date().getTime();
			var params = $("#frmTaskDetail").serialize();
			$.ajax({ url: url,
		    		 cache: false,
		     		 async: false,
				     type: "post",
				     data: params,
				     dataType:"json",
				     success: function(data){            
			            alert(data.resultValue);
						if (data.successful) {
							closeForm();
							searchTask();
						}
					},
					error: function(request) {
                    	alert("提交失败!请刷新页面重新提交!");
                    	return;
                    }
                });
			
		}
		
		/// <summary>更新任务信息</summary>
		function updateTaskInfo(){
			var url = "<%=basePath %>control/task/updateQuartzInfo?etc="+new Date().getTime();
			var params = $("#frmTaskDetail").serialize(); // form表单参数
			$.ajax({ url: url,
		    		 cache: false,
		     		 async: false,
				     type: "post",
				     data: params,
				     dataType:"json",
				     success: function(data){            
			            alert(data.resultValue);
						if (data.successful) {
							closeForm();
							searchTask();
						}
					},
					error: function(request) {
                    	alert("更新失败!请刷新页面重新提交!");
                    	return;
                    }
                });
		}
			
		/// <summary>通过ID删除任务信息</summary>
		function deleteTaskById(taskId){
			var url = "<%=basePath %>control/task/deleteById.action?taskId="+taskId+"&etc="+new Date().getTime();
			if (!confirm("删除后无法恢复，确认删除吗？")) {
				return;
			}
			$.ajax({url: url,
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						alert(data.resultValue);
						if (data.successful) {
							searchTask();
						}
					},
					error: function(request) {
                    	alert("删除失败!请刷新页面重新删除!");
                    	return;
                    }
 			});
		}
		
		/// <summary>初始化任务</summary>
		function initTask() {
			$.ajax({url:"<%=basePath%>control/task/init.action?etc=" + new Date().getTime(),
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.successful){
							alert("启动成功");
						} else {
							alert("启动失败,原因" + data.resultValue);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>添加任务</summary>
		/// <param name="taskId">调度任务ID</param>
		function onDynamicAdd(taskId) {
			$.ajax({url:"<%=basePath %>control/task/dynamicAdd.action?taskId=" + taskId + "&etc=" + new Date().getTime(),
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.resultCode == 1){
							alert("添加成功");
						} else {
							alert("添加失败,原因" + data.resultMsg);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>立即执行任务</summary>
		/// <param name="taskId">调度任务ID</param>
		function onTriggerJob(taskId) {
			$.ajax({url:"<%=basePath %>control/task/triggerJob.action?taskId=" + taskId + "&etc=" + new Date().getTime(), 
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.resultCode == 1){
							alert("执行成功");
						} else {
							alert("执行失败,原因" + data.resultMsg);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>暂停执行任务</summary>
		/// <param name="taskId">调度任务ID</param>
		function onPauseJob(taskId) {
			$.ajax({url:"<%=basePath %>control/task/pauseJob.action?taskId=" + taskId + "&etc=" + new Date().getTime(),
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.resultCode == 1){
							alert("暂停成功");
						} else {
							alert("暂停失败,原因" + data.resultMsg);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>恢复暂停任务</summary>
		/// <param name="taskId">调度任务ID</param>
		function onResumeJob(taskId) {
			$.ajax({url:"<%=basePath %>control/task/resumeJob.action?taskId=" + taskId + "&etc=" + new Date().getTime(),
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.resultCode == 1){
							alert("恢复任务成功");
						} else {
							alert("恢复任务失败,原因" + data.resultMsg);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>删除任务</summary>
		/// <param name="taskId">调度任务ID</param>
		function onDeleteJob(taskId) {
			$.ajax({url:"<%=basePath %>control/task/deleteJob.action?taskId=" + taskId + "&etc=" + new Date().getTime(),
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){            
						if(data.resultCode == 1){
							alert("停用任务成功");
						} else {
							alert("停用任务失败,原因" + data.resultMsg);
						}
						
						// 重新加载Grid
						searchTask();
					}
 			});
		}

		/// <summary>查看日志</summary>
		/// <param name="taskId">调度任务ID</param>
		function onSearchLog(taskId) {
		   initLogInfoWin(); // 初始化日志窗体
		   loadLogInfo(taskId);
		}
		
		/// <summary>获得调度时间公式</summary>
		function getCronExp(){
			$("#form_cronexp").css("display","block");
			$("#form_cronexp").window({
				title:"调度任务表达式配置",
			    width:980,  
			    height:300, 
			    top:60,
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    draggable:true,
			    resizable:false,
			    modal:true
			});
			$("#form_cronexp").window("open");
			// 解析调度时间表达式
			analysisCroExpression();
		}
		
		function saveBDS(){
			if(creatBDS()){
				$("#form_cronexp").window("close");
			}
		}
		
		function closeBDS(){
			$("#form_cronexp").window("close");
		}
		/// <summary>初始化日志窗体</summary>
		function initLogInfoWin(){
			$("#div_grid1").css("display","block");
			$("#div_grid1").window({
				title:"调度日志信息",
			    width:700,  
			    height:450, 
			    top:40,
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    draggable:true,
			    resizable:false,
			    modal:true
			});
			$("#div_grid1").window("open");
		}

		// 日志信息url
		function getLogUrl(taskId){
		    var _url = "<%=basePath %>control/logMessage/queryQuartzLog?etc="+new Date().getTime(); //字节点URL
		    if(taskId){
				_url += "&taskId="+encodeURIComponent(encodeURIComponent(taskId));				
			}	
			return _url;
		}
		
		/// <summary>查看日志详细信息</summary>
		function loadLogDetail(taskInstid){
		   initLogDetailWin(); // 初始化日志窗体
		   loadLogDetailInfo(taskInstid);
		}
		
		/// <summary>初始化日志窗体</summary>
		function initLogDetailWin(){
			$("#div_grid2").css("display","block");
			$("#div_grid2").window({
				title:"调度日志信息",
			    width:700,  
			    height:450, 
			    top:40,
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    draggable:true,
			    resizable:false,
			    modal:true
			});
			$("#div_grid2").window("open");
		}
		
		/// <summary>加载日志详细信息数据</summary>
		function loadLogDetailInfo(taskInstid){
			$('#logType').combobox('setValue','');
			$('#dgLogDetailInfo').datagrid({
				url:getLogDetallUrl(taskInstid),
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				striped:true,//条纹
				width:$("#div_grid1")[0].clientWidth-10,		
				height:$("#div_grid1")[0].clientHeight-40,
				pagination:true,//分页控件
				pageNumber:1,
				pageSize:10,   
				pageList:[10,15,20,30,40,50],
				columns:[[ 
				    {field:'excStats',title:'执行状态',width:70,align:'center'},
				    {field:'logType',title:'信息类型',width:70,align:'center'},
				    {field:'logInfo',title:'日志内容',width:330,align:'center',formatter:textBtn},
					{field:'logDate',title:'记录时间',width:150,align:'center'}
				]],
				view : $.orzView
			});
		}
		
		function textBtn(value, row, index){
	        return	"<span   title='"+ value +"'>"
						+ displayTDsCharts(value,180) +"</span>";
	    } 
	    
	    function displayTDsCharts(str,cd) {
    		if (str==null)
    			{
    			return "";
    			}
    		var showStr = null;
    		var subOpt = getCharLength(str,cd);
    		var len = subOpt.realLength;
    		var count = subOpt.count;
    		if(len>cd) {//判断长度是否超过30个字符
    			showStr = str.substring(0,count)+"...";
    		}else {
    			showStr = str;
    		}
    		return showStr;
    	}

    	function getCharLength(str,cd){
    		var subOpt = {};
    		var realLength = 0, len = str.length, charCode = -1,count = 0;
    		for (var i = 0; i < len; i++) {
    			charCode = str.charCodeAt(i);
    			if (charCode >= 0 && charCode <= 128) {
    				realLength += 1;
    			} else {
    				realLength += 2;
    			}
    			if(realLength<=cd){
        			count ++;
    			}
    		}
    		subOpt.realLength = realLength;
    		subOpt.count = count;
    		return subOpt;
    	}
		
		//获取日志明细Url
		function getLogDetallUrl(taskInstid){
		    //获得查询过滤条件值
			var _url = "<%=basePath %>control/logMessage/queryQuartzDetailLog?etc="+new Date().getTime(); //字节点URL
		    if(taskInstid){
				_url += "&taskInstid="+encodeURIComponent(encodeURIComponent(taskInstid));				
			}	
			return _url;
		}

		//判断待删除任务是否已产生日志
		function checkTaskHaveDetail(taskId){
			var flag = false;
			$.ajax({
			        url:"<%=basePath %>control/task/checkTaskHaveDetail.action?taskId="+taskId+"&etc="+new Date().getTime(),
			        type: 'POST',
			        dataType: 'json',
			        contentType: "application/json; charset=utf-8",
			        data: "",
			        async: false,
			        error: function(json){
			        	flag=false;
			        },
			        success: function(json){
			        	if(json.resultCode==0){
			        		flag=true;
			        		
			        		}else{
			        		flag=false;	
			        		alert("该任务已执行并生成日志，无法删除");
			        		}         	
			    }
			});
		return flag;
	  }
		
		//查询日志明细
		function queryLogDetailByType(){
			var logType = $('#logType').combobox("getValue");
			var queryParams = $("#dgLogDetailInfo").datagrid('options').queryParams;
			queryParams = $.extend(queryParams, {
	            'logType': logType });
	       	$("#dgLogDetailInfo").datagrid('options').queryParams = queryParams;
	       	$("#dgLogDetailInfo").datagrid('load');
	       	queryParams = $.extend(queryParams, {'logType': ''});
	       	$("#dgLogDetailInfo").datagrid('options').queryParams = queryParams;
		}
  </script>
  
  <body class="easyui-layout" onload="doOnload()">
  	<div data-options="region:'north',title:'任务查询条件'" style="height:60px; text-align:center" > 
  	<div id="condition_container" style="height:100%;text-align:center;background-color:#EDF4FA" >  
		<table border="0" align="left" cellpadding="0" cellspacing="0" class="tb_side" style="margin-top:5px;"> 
			<tr>
			     <td nowrap="nowrap" width="35px"></td>
				<td align="left" >任务名称：</td>
				<td>
					<input type="text" align="left" id="txtTaskName" class="search_txtbox_input" size="17"></input> 
				</td>
				<td align="left" style="padding-left: 12px; font-size:12px">任务状态：</td>
				<td>
					<select id="taskStatus" name="status" class="easyui-combobox" style="width: 150px;"><option value="-1">--请选择--</option><option value="1">启动</option><option value="2">停用</option></select>
				</td>
				<td nowrap="nowrap" width="25px"></td>
				<td>
				<a href="javascript:searchTask();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				</td>
			</tr>
		</table> 
	</div>
	</div>
	<div data-options="region:'center',title:'任务查询结果'">
		<div id="div_grid" style="margin: 0px; padding: 0px;height:100%;">
			<table id="dgTaskInfo"></table>
		</div>
	</div>
	<div id="form_container"  style="height:100%;display:none" >
		<div class="div_toolbar" >
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="checkAndSave();">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true"  onclick="closeForm();">关闭</a>
		</div>
	<div class="div_infoDisplay" style="height:70%;">
		<form id="frmTaskDetail" name="frmTaskDetail" action="" method="post">
			<table align="center">
				<tr>
					<td class="form_td_label" style="width:140px;">
						任务名称(<font color='red'>*</font>)：
					</td>
					<td class="form_td_input">
						<input type="text" class="form_txtbox_input easyui-validatebox"  id="txtTaskName" name="taskName" required="true" value=""/>
						<input type="hidden" name="taskId" id="taskId" size="25"/>
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						启动类名称：
					</td>
					<td class="form_td_input">
						<input type="text" class="form_txtbox_input easyui-validatebox" id="txtTaskProcName" name="taskProcName" value=""/>
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						启动类(<font color='red'>*</font>)：
					</td>
					<td class="form_td_input">
						<input class="easyui-combobox" id="cmbTaskProcClass" name="taskProcClass" data-options="valueField:'id',textField:'text',panelHeight:150" style="width: 400px;" required="true"/>						</td>
				</tr>
				<tr>
					<td class="form_td_label">
						 启动方式(<font color='red'>*</font>)：
					</td>
					<td class="form_td_input">
						<input class="easyui-combobox"  id="cmbStartMode" name="startMode" data-options="valueField:'id',textField:'text',panelHeight:150" style="width: 400px;" required="true"/>	
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						任务描述：
					</td>
					<td class="form_td_input">
						<textarea class="txtarea" rows="5" id="txtaTaskDesc"  name="taskDesc"></textarea> 
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						频度：
					</td>
					<td class="form_td_input">
						<input class="easyui-combobox"  id="cmbTaskCycle" name="taskCycle" data-options="valueField:'id',textField:'text',panelHeight:150" style="width: 400px;"/>	
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						调度时间公式：
					</td>
					<td class="form_td_input">
						<input type="text" class="form_txtbox_input easyui-validatebox" id="txtCronExpression" name="cronExpression"  value="" onclick="getCronExp()"/>
					</td>
				</tr>
				<tr>
					<td class="form_td_label">
						调度时间说明：
					</td>
					<td class="form_td_input" colspan="3">
						<textarea class="txtarea" rows="3" id="txtaCtiveTimeName"  name="ctiveTimeName"></textarea> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
	
	<div id="form_cronexp" style="display:none" >
		<div class="div_toolbar" >
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="saveBDS();">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true"  onclick="closeBDS();">关闭</a>
		</div>
		
		<div class="div_taskmode">
			<div style="text-align:center;padding:10px;">
		   		任务设置方式：
		   		<input type="radio" name = "set_fs" value="0" checked="checked" style="width: 15px;"
		   			onclick="setPanel('无')"/>无任务
		   		<input type="radio" name = "set_fs" value="1" style="width: 15px;"
		   			onclick="setPanel('方式1')"/>方式1
		   		<input type="radio" name = "set_fs" value="2" style="width: 15px;" onclick="setPanel('方式2')"/>方式2
			</div>
			<div id ="div_panel" class="div_schedule">
			</div>
		</div>
		<div id="temp_fs1" style="display: none;">
			<div id ="div_num" style="margin: 10px;text-align: center;">
	   			<br/>
	   			频率：每<input id="input_num" type="text" style="width: 30px" value="1"/>
	   			<select id="select_pl" onchange="changeDivPlDetail();" style="width: 55px">
	   				<option value="s">秒</option>
	   				<option value="min">分钟</option>
	   				<option value="h">小时</option>
	   				<option value="d">天</option>
	   			</select>
	   			执行一次
	   		</div>
	   		<div id="div_pl_Detail" style="text-align: center;" >
	   			起始秒：<input id="qs_ss" type="text" value="0" style="width: 30px"/>秒
			</div>
		</div>
		<div id="temp_fs2" style="display: none;">
			<div class="div_month">
		   		选择月：<input type="checkbox" name="checkAllMonth" value="all" style="width: 15px;"/>全选
			   	<table>
			   		<tr>
				   		<td><input type="checkbox" name="checkMonth"  value="1" style="width: 15px;"/>1月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="2" style="width: 15px;"/>2月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="3" style="width: 15px;"/>3月</td>
			   		</tr>
			   		<tr>
				   		<td><input type="checkbox" name="checkMonth"  value="4" style="width: 15px;"/>4月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="5" style="width: 15px;"/>5月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="6" style="width: 15px;"/>6月</td>
			   		</tr>
			   		<tr>
				   		<td><input type="checkbox" name="checkMonth"  value="7" style="width: 15px;"/>7月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="8" style="width: 15px;"/>8月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="9" style="width: 15px;"/>9月</td>
			   		</tr>
			   		<tr>
				   		<td><input type="checkbox" name="checkMonth"  value="10" style="width: 15px;"/>10月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="11" style="width: 15px;"/>11月</td>
				   		<td><input type="checkbox" name="checkMonth"  value="12" style="width: 15px;"/>12月</td>
			   		</tr>
			   	</table>
			</div>
			<div class="div_day">
		   		选择日：<input type="checkbox" name="checkAllDay"  value="all" style="width: 15px;"/>全选
			   	<table>
				   	<tr>
				   		<td><input type="checkbox" name="checkDay" value="1" style="width: 15px;"/>1号</td>
				   		<td><input type="checkbox" name="checkDay"  value="2" style="width: 15px;"/>2号</td>
				   		<td><input type="checkbox" name="checkDay"  value="3" style="width: 15px;"/>3号</td>
				   		<td><input type="checkbox" name="checkDay"  value="4" style="width: 15px;"/>4号</td>
				   		<td><input type="checkbox" name="checkDay"  value="5" style="width: 15px;"/>5号</td>
				   		<td><input type="checkbox" name="checkDay"  value="6" style="width: 15px;"/>6号</td>
				   		<td><input type="checkbox" name="checkDay"  value="7" style="width: 15px;"/>7号</td>
				   		<td><input type="checkbox" name="checkDay"  value="8" style="width: 15px;"/>8号</td>
				   	</tr>
				   	<tr>
				   		<td><input type="checkbox" name="checkDay"  value="9" style="width: 15px;"/>9号</td>
				   		<td><input type="checkbox" name="checkDay"  value="10" style="width: 15px;"/>10号</td>
				   		<td><input type="checkbox" name="checkDay"  value="11" style="width: 15px;"/>11号</td>
				   		<td><input type="checkbox" name="checkDay"  value="12" style="width: 15px;"/>12号</td>
				   		<td><input type="checkbox" name="checkDay"  value="13" style="width: 15px;"/>13号</td>
				   		<td><input type="checkbox" name="checkDay"  value="14" style="width: 15px;"/>14号</td>
				   		<td><input type="checkbox" name="checkDay"  value="15" style="width: 15px;"/>15号</td>
				   		<td><input type="checkbox" name="checkDay"  value="16" style="width: 15px;"/>16号</td>
				   	</tr>
				   	<tr>
				   		<td><input type="checkbox" name="checkDay"  value="17" style="width: 15px;"/>17号</td>
				   		<td><input type="checkbox" name="checkDay"  value="18" style="width: 15px;"/>18号</td>
				   		<td><input type="checkbox" name="checkDay"  value="19" style="width: 15px;"/>19号</td>
				   		<td><input type="checkbox" name="checkDay"  value="20" style="width: 15px;"/>20号</td>
				   		<td><input type="checkbox" name="checkDay"  value="21" style="width: 15px;"/>21号</td>
				   		<td><input type="checkbox" name="checkDay"  value="22" style="width: 15px;"/>22号</td>
				   		<td><input type="checkbox" name="checkDay"  value="23" style="width: 15px;"/>23号</td>
				   		<td><input type="checkbox" name="checkDay"  value="24" style="width: 15px;"/>24号</td>
				   	</tr>
				   	<tr>
				   		<td><input type="checkbox" name="checkDay"  value="25" style="width: 15px;"/>25号</td>
				   		<td><input type="checkbox" name="checkDay"  value="26" style="width: 15px;"/>26号</td>
				   		<td><input type="checkbox" name="checkDay"  value="27" style="width: 15px;"/>27号</td>
				   		<td><input type="checkbox" name="checkDay"  value="28" style="width: 15px;"/>28号</td>
				   		<td><input type="checkbox" name="checkDay"  value="29" style="width: 15px;"/>29号</td>
				   		<td><input type="checkbox" name="checkDay"  value="30" style="width: 15px;"/>30号</td>
				   		<td><input type="checkbox" name="checkDay"  value="31" style="width: 15px;"/>31号</td>
				   	</tr>
			   	</table>
			</div>
			<div class="div_week">
	   			选择周：<input type="checkbox"  name="checkAllWeek" value="all" style="width: 15px;"/>全选
			   	<table>
			   		<tr>
				   		<td><input type="checkbox" name="checkWeek" value="2" style="width: 15px;"/>周一</td>
				   		<td><input type="checkbox" name="checkWeek" value="3" style="width: 15px;"/>周二</td>
			   		</tr>
			   		<tr>
				   		<td><input type="checkbox" name="checkWeek"  value="4" style="width: 15px;"/>周三</td>
				   		<td><input type="checkbox" name="checkWeek"  value="5" style="width: 15px;"/>周四</td>
			   		</tr>
			   		<tr>
				   		<td><input type="checkbox" name="checkWeek"  value="6" style="width: 15px;"/>周五</td>
				   		<td><input type="checkbox" name="checkWeek"  value="7" style="width: 15px;"/>周六</td>
			   		</tr>
			   		<tr>
			   			<td><input type="checkbox" name="checkWeek"  value="1" style="width: 15px;"/>周日</td>
			   		</tr>
			   	</table>
			</div>
			<div class="div_time">
		        <br/>
		   		执行时间：<br/><br/>
		   		<select id="select_hh" style="width: 40px">${option_hh}</select>时
		   		<select id="select_mm" style="width: 40px">${option_mm}</select>分
		   		<select id="select_ss" style="width: 40px">${option_ss}</select>秒
			</div>
		</div>
	</div>
	<div id="div_grid1" style="margin: 0px; padding: 0px;display:none;">
		<table id="dgLogInfo"></table>
	</div>
	<div id="div_grid2" style="margin: 0px; padding: 0px;display:none;">
		<div style="height:8%; width:100%;text-align:center;background-color:#EDF4FA"> 
		<table border="0" align="left" cellpadding="0" cellspacing="0" class="tb_side" style="margin-top:5px;">
			<tr>
			  <td nowrap="nowrap" width="20px"></td>
			  <td align="right">
			     信息类型：<select id="logType" name="logType" class="easyui-combobox" align="center" panelHeight="85">
							<option value="" selected = "selected">--请选择--</option>
							<option value="0">成功</option>
							<option value="1">失败</option>
							<option value="9">消息</option>
						</select>
				</td>
				<td width="40px"></td>
				<td align="left">
					<a href="javascript:queryLogDetailByType();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				</td>
		   </tr>
		</table>	
		</div>
		<table id="dgLogDetailInfo"></table>
	</div>
	</body>
</html>
	