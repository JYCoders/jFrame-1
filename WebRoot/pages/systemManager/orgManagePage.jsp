<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构、员工维护页面</title>
<%@include file="../common_header_easyui.jsp"%>

<script>
		var BASE_PATH='<%=basePath%>'; 
	</script>

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
 .tab_css{
    	width: 500px;
		border: 0px solid silver;
		border-collapse:collapse;
		font-size: 12px;
		margin: 0px;
		padding: 0 px;
		display: none;
    }
	.tab_css input{width: 350px;}
.butnToolBar{
	height: 35px;
	width: 100%;
	background-image: url("../../images/layoutcell.gif");
}
.btn-location{
  margin: 4px;
  margin-left: 45px;
}
</style>
</head>
<body class = "easyui-layout" style="margin: 2px 2px 2px 2px" onload="doOnloadBase()">
	    <div data-options="region:'west',split:true" style="width:220px;">
		   <div class="easyui-tree" id="orgTree" style="margin: 10px 10px 5px 10px"></div>
	    </div>
		<div  id = "bodyright" data-options="region:'center',split:true" style="width:1000px;">
			<div class = 'div_toolbar' >
			   <a id="btn" href="javascript:void(0);"  class="easyui-linkbutton" iconcls="icon-save" plain="true" onclick="addAndUpdate()">保存</a> 
			</div>
	    <div id="emp_Info_div" style="height:90%;display:none;padding-top: 20px;text-align: center;">
		   <form id="emp_info_form" name="emp_info_form" action="" method="post">
			<table id="emp_Info_table" class="tab_css" align="center">
				<tr align="center">
					<td class="form_td_label" style="width: 200px">员工编号(<font color='red'>*</font>)：</td>
					<td class="form_td_input" style="text-align: left;"><input id="emp_code" class="search_txtbox_input easyui-validatebox" required="true" data-options="max:9999.99,min:0" maxlength="50"/></td>
				</tr>
				<tr align="center">
					<td class="form_td_label">员工姓名(<font color='red'>*</font>)：</td>
					<td class="form_td_input" style="text-align: left;"> <input id="emp_name" class="search_txtbox_input easyui-validatebox" required="true"></input></td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">员工性别：</td>
					<td class="form_td_input" style="text-align: left;"><select id="emp_sex" name="emp_sex" class="easyui-combobox" style="width: 350px" panelHeight="70" >
							<option value="0">请选择员工性别</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">出生日期(<font color='red'>*</font>)：</td>
					<td class="form_td_input" style="text-align: left;" >
						<input id="txtJDate" name="txtJDate" class="easyui-datebox" style="width:350px;" required="true" editable="false"/>
					</td>
				</tr>
				<tr align="center">
					<td class="form_td_label">所属组织机构：</td>
					<td class="form_td_input" style="text-align: left;">
					<input id="org_id" name="org_id" type="hidden" />
					<input id="orgname" name="orgname" class="search_txtbox_input easyui-validatebox" style="width: 350px" readonly/>
					</td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">移动电话：</td>
					<td class="form_td_input" style="text-align: left;"><input id="mob_tel" class='search_txtbox_input'/>
					</td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">固定电话：</td>
					<td class="form_td_input" style="text-align: left;"><input id="fix_tel" class='search_txtbox_input'/></td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">电子邮箱：</td>
					<td class="form_td_input" style="text-align: left;"><input id="email" class='search_txtbox_input'/></td>
				</tr>
				
			</table>
			<table id="saveEmp_table" width="100%" border="0" cellpadding="0" cellspacing="1" style="display:none">
				<tr align="center">
					<td  align="center"><label
						id="tipsEmp" class="font_red"></label></td>
				</tr>
			</table>	
		</form>


		<form id="org_info_form" name="org_info_form" action="" method="post">
			<table id="org_Info_table" class="tab_css" align="center">
	
				<tr align="center">
					<td class="form_td_label" style="width: 200px">组织机构编号(<font color='red'>*</font>)：</td>
					<td class="form_td_input" style="text-align: left;">
						<input id="org_code" name="org_code" class="search_txtbox_input easyui-validatebox" required="true"/>
					</td>
				</tr>
				<tr align="center">
					<td class="form_td_label" >组织机构名称(<font color='red'>*</font>)：</td>
					<td class="form_td_input" style="text-align: left;"><input id="org_name" name="org_name" class="search_txtbox_input easyui-validatebox" required="true"/></td>
				</tr>
				<tr align="center">
					<td  class="form_td_label">组织机构类型：</td>
					<td class="form_td_input" style="text-align: left;">
							<input id="node_type" name="org_id" class="easyui-combobox" style="width: 350px;" panelHeight="90"
						data-options="valueField:'id',textField:'text'"/>
					</td>
					
				</tr>
			</table>
				<table id="saveOrg_table" width="100%" border="0" cellpadding="0" cellspacing="1" style="display:none">
				
					<tr>
						<td  align="center"><label
							id="tipsOrg" class="font_red"></label></td>
					</tr>
				</table>
		</form>
	</div>
		</div>
		<div id="empmenu" class="easyui-menu" style="width:120px;">
			<div onclick="deleteEmpInfo()" data-options="iconCls:'icon-delete'">删除员工</div>
			<div onclick="refreshTree()" data-options="iconCls:'icon-refresh'">刷新</div>
		</div>
		<div id="orgmenu" class="easyui-menu" style="width:120px;">
			<div onclick="addNewOrgInfo()" data-options="iconCls:'icon-usergroup-add'">添加组织机构</div>
			<div onclick="deleteOrgInfo()" data-options="iconCls:'icon-delete'">删除组织机构</div>
			<div onclick="addEmpInfo()" data-options="iconCls:'icon-user-add'">添加员工</div>
			<div onclick="refreshTree()" data-options="iconCls:'icon-refresh'">刷新</div>
		</div>
		<div id="orgempmenu" class="easyui-menu" style="width:120px;">
		    <div onclick="deleteOrgInfo()" data-options="iconCls:'icon-delete'">删除组织机构</div>
			<div onclick="addEmpInfo()" data-options="iconCls:'icon-user-add'">添加员工</div>
			<div onclick="refreshTree()" data-options="iconCls:'icon-refresh'">刷新</div>
		</div>
</body>
<script type="text/javascript">
		var con_type = '';
		var nodeTemp = null ;

		/// <summary>加载页面时初始化方法</summary>
		function doOnloadBase(){
			//初始化组织树
			initOrgTree();
		}
		
		/// <summary>初始化组织树方法</summary>
		function initOrgTree(){
			var treeData = null;
			//获取加载easyUI树所需的数据
			$.ajax({
				type: "POST",
				async: false,
				cache: false,
				url: BASE_PATH+"control/org/OrgTree.action?etc=" + new Date().getTime(),
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				success: function (data){
					//更换树节点图片
				    treeData=data;
					
					//修改树图标
					changeIconCls(data[0]);
					data[0].iconCls="icon-orgroot";
				}	
			});
			//初始化树，并增加相应的单击方法
			$('#orgTree').tree({
		    	data:treeData,
		    	checkbox:false,
			    lines:true,
			    onLoadSuccess:function(){
			    	if(nodeTemp!=null){
			      $('#orgTree').tree('select', nodeTemp.target);}
			    }, 
			    onLoadError:function(){
					alert("组织机构加载失败！");
				},
				onClick:function(node){
					initNode(node);				
				},
				
		    });	 
		}
		
		/// <summary>更换树节点图片方法</summary>
		/// <param name="data">树节点参数</param>
		function changeIconCls(data){
			if(data.children.length>0){
				if(data.attributes.node_type== 'emp'){
					data.iconCls='icon-user';
				}else{
					data.iconCls='icon-usergroup';
				}	
				for(var i =0;i<data.children.length;i++){				
					changeIconCls(data.children[i]);
				}
			}else{
				if(data.attributes.node_type== 'emp'){
					data.iconCls='icon-user';
				}else{
					data.iconCls='icon-usergroup';
				}
			}
			
		}
		
		/// <summary>加载树节点的基本数据方法</summary>
		/// <param name="node">节点</param>
		function initNode(node){
			var tempNode = node.attributes.node_type;
			var url = null;
			var params = null;
			//根据树节点类型是组织（org）还是员工（emp）来从相应的表中取出数据
			if(tempNode == 'emp') {
				url = '<%=basePath %>control/org/findEmpInfoByEmpId.action?etc=' + new Date().getTime();
				params = {'emp_id':node.id};
				initEmp();
				}else {
				url = '<%=basePath %>control/org/getOrgInfoByOrgId.action?etc=' + new Date().getTime();
				params = {'org_id':node.id};
				initOrg();
				}
			$.ajax({
		        type:'POST',
		        url: url,
		        dataType: 'json',
		        data: params,
		        success: function (data) {
		        	if(tempNode == 'emp'){initEmpFormData(data);}else{
		        		intiOrgFormData(data);
		        	}
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            alert("获取数据失败！");
		        }
		    });
		}
		
		/// <summary>初始化员工table方法</summary>
		function initEmp(){
			//initSelect4Org();
			$('#emp_Info_div')[0].style.display = 'block';
			document.getElementById("org_Info_table").style.display = "none";//将该DOM设为行内元素
			document.getElementById("saveOrg_table").style.display = "none";
			document.getElementById("emp_Info_table").style.display = "inline";
			document.getElementById("saveEmp_table").style.display = "inline";
		}
		
		/// <summary>初始化组织table方法</summary>
		function initOrg(){
			$('#emp_Info_div')[0].style.display = 'block';
			document.getElementById("org_Info_table").style.display = "inline";//将该DOM设为行内元素
			document.getElementById("saveOrg_table").style.display = "inline";
			document.getElementById("emp_Info_table").style.display = "none";
			document.getElementById("saveEmp_table").style.display = "none";
		}
		
		/// <summary>初始化组织Form数据方法</summary>
		/// <param name="data">组织数据</param>
		function intiOrgFormData(data){
			con_type = 'updateOrg';
			data.org_id; //组织机构ID
			$('#org_code').val(data.org_code); //组织机构编号
			$('#org_name').val(data.org_name);//组织机构名称
			var nodeType = data.node_type;  //组织机构父节点
			var datas, json;
			if(nodeType=='1'){			
				json = '[{"id":"0","text":"请选择组织机构类型"},{"id":"1","text":"公司"}]';					
			}else if(nodeType=='2'){
				json = '[{"id":"0","text":"请选择组织机构类型"},{"id":"1","text":"公司"},{"id":"2","text":"直属部门"}]';
			}else{
				json = '[{"id":"0","text":"请选择组织机构类型"},{"id":"3","text":"分公司"},{"id":"4","text":"地市公司"}]';
			}
			datas = $.parseJSON(json);
			$('#node_type').combobox("loadData", datas);
			$('#node_type').combobox('setValue',data.node_type); //组织机构类型
		}
		
		/// <summary>初始化员工Form数据方法</summary>
		/// <param name="data">员工数据</param>
		function initEmpFormData(data){
			var birth = null;
			con_type = 'updateEmp';
		        data.emp_id;  //员工ID
			$('#emp_code').val(data.emp_code);  //员工编号
			$('#emp_name').val(data.emp_name);   //员工姓名  
			if(data.emp_birth!=null&&data.emp_birth.trim()!=''){
				birth = data.emp_birth.substr(0,4) + "-" + data.emp_birth.substr(4,2) + "-" + data.emp_birth.substr(6,2);
			}else{
				birth = '';
			}
			
			$('#txtJDate').datebox('setValue', birth); //员工出生日期
			$('#emp_sex').combobox('setValue',data.emp_sex);  //员工性别
			$('#org_id').val(data.org_id);  //组织机构id
			$('#orgname').val(data.org_name);  //所属组织机构名字
			$('#mob_tel').val(data.mob_tel);   //移动电话
			$('#fix_tel').val(data.fix_tel);   //固定电话
			$('#email').val(data.email);   //邮件
		}
		
		/// <summary>员工所属单位下拉菜单初始化方法</summary>
		function initSelect4Org(){
			var deptUrl = "<%=basePath%>control/org/getOrgCodeList.action?etc="+ new Date().getTime();
			var comData = new Array();
				$.ajax({
				type: "POST",
				async: false,
				cache: false,
				url: deptUrl,
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				success: function (result){			
					comData[0] = {"org_id" : "jx00_0","org_name" : "请选择"};
						if(result != null && result.length >0){
							for(i=0;i<result.length;i=i+1){
								comData[i+1] = result[i];					
						}
					}
				}	
			});
			$('#org_id').combobox('loadData',comData);
		};
		
		/// <summary>保存或更新员工或组织方法</summary>
		function addAndUpdate(){
			var node =  $('#orgTree').tree('getSelected');
			var tempNode = node.attributes.node_type;
			if(tempNode == 'emp'||con_type == 'addEmp'){
				//保存或更新员工
				checkAndSave4Emp(node);
			}else{
				//保存或更新组织
				checkAndSave4Org(node);
			}
			
		}
		
		/// <summary>保存或更新员工</summary>
		/// <param name="node">被选中的节点</param>
		function checkAndSave4Emp(node) {
			//员工编码不能为中文的校验
			var str =$("#emp_code").val();
			if(/.*[\u4e00-\u9fa5]+.*$/.test(str)){
			  alert('员工编号不能包含中文，请重新输入！');
			  $("#emp_code").focus();
			  return;
			}
			var emp_id = node.id;
			var easyuiValidata = $("#emp_info_form").form('validate');
			if(!easyuiValidata){
				return;
			}
			if (con_type=="addEmp") {
				//插入操作	
				var params ={
						'org_id':$("#org_id").val(),
						'emp_code':$("#emp_code").val(),
						'emp_name':$("#emp_name").val(),
						'emp_sex':$("#emp_sex").combobox('getValue'),
						'emp_birth':($("#txtJDate").datebox("getValue")).replace(/-/g,""),
						'mob_tel':$("#mob_tel").val(),
						'fix_tel':$("#fix_tel").val(),
						'email':$("#email").val()
				   };
				var url = BASE_PATH + "control/org/insertEmpInfo.action?etc="
					+ new Date().getTime();
				//执行新增员工
				addAndUpdateDetail(url, params,node);		
			} else {
				//更新操作
				var params ={
						'emp_id':emp_id,
						'org_id':$("#org_id").val(),
						'emp_code':$("#emp_code").val(),
						'emp_name':$("#emp_name").val(),
						'emp_sex':$("#emp_sex").combobox('getValue'),
						'emp_birth':($("#txtJDate").datebox("getValue")).replace(/-/g,""),
						'mob_tel':$("#mob_tel").val(),
						'fix_tel':$("#fix_tel").val(),
						'email':$("#email").val()
				   };
				var url = BASE_PATH + "control/org/updateEmpInfo.action?etc="
				+ new Date().getTime();
				//执行更新员工
				addAndUpdateDetail(url, params,node);
			}
		}
		
		/// <summary>保存或更新方法</summary>
		/// <param name="_url">保存或更新组织/员工的url</param>
		/// <param name="_params">保存或更新的具体参数</param>
		/// <param name="node">组织树被选中的节点</param>
		function addAndUpdateDetail(_url,_params,node){
			$.ajax({
		        type:'POST',
		        url: _url,
		        dataType: 'json',
		        data: _params,
		        success: function (data) { 
		        	    alert(data.resultValue);
		        	    nodeTemp = node;
		        	    initOrgTree();       	    
		        	    //$('#emp_Info_div')[0].style.display = 'none';				
		        },
		        error: function () {
		            alert("更新数据失败！");
		        }
		    });	
		}
		
		/// <summary>保存或更新组织机构信息方法</summary>
		/// <param name="node">组织树被选中的节点</param>
		function checkAndSave4Org(node) {
			var str =$("#org_code").val();
			if(/.*[\u4e00-\u9fa5]+.*$/.test(str)){
			  alert('组织机构编号不能包含中文，请重新输入！');
			  $("#org_code").focus();
			  return;
			}
			var orgid = node.id;
			var easyuiValidata = $("#org_info_form").form('validate');
			if(!easyuiValidata){
				return;
			}	
			if (con_type=="addOrg") {
				//插入操作
				var repeat=checkOrgCodeUnique();
				if(repeat || repeat==undefined)
				{
					return;
				}
				if($("#org_code").val()=="1"){
					alert("组织机构编号有误请重新输入");
					return;
				}
				var params = {
						'org_code':$("#org_code").val(),
						'org_name':$("#org_name").val(),
						'node_type':$("#node_type").combobox('getValue'),
						'p_id':orgid
							};
				var url = BASE_PATH + "control/org/insertOrgInfo.action?etc="
				+ new Date().getTime();
				//执行新增组织
				addAndUpdateDetail(url,params,node);
			} else {
				if($("#org_code").val()=="1"){
					alert("组织机构编号有误请重新输入");
					return;
				}
				var params = {
						'org_id':orgid,
						'org_code':$("#org_code").val(),
						'org_name':$("#org_name").val(),
						'node_type':$("#node_type").combobox('getValue')
							};
				var url = BASE_PATH + "control/org/updateOrgInfo.action?etc="+ new Date().getTime();
				//执行更新组织
				addAndUpdateDetail(url, params,node);
			}
		}
		
		/// <summary>检查组织机构编号唯一性</summary>
		function checkOrgCodeUnique(){
			var objInput = $("#org_code").val($.trim($("#org_code").val()));
				var propertyName="组织机构编码";
				var url=BASE_PATH+"control/org/checkOrgCodeUnique.action?org_code="+objInput.val()+"&etc="+new Date().getTime();
				var param = "";
				//具体检查方法
				var right= checkPropertyUnique1(propertyName,objInput,url,param);
				 return right;
		}
		
		/// <summary>通用函数，检查属性是否唯一，并返回见过方法</summary>
		/// <param name="propertyName">组织机构编码</param>
		/// <param name="objInput">输入的组织编码</param>
		/// <param name="url">检验所请求的url</param>
		/// <param name="param">所需的基本参数</param>
		function checkPropertyUnique1(propertyName,objInput,url,param){
			$.ajax({
				type: "POST",
				async: false,
				cache: false,
				url: url,
				data: param,
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				dateType: "json",
				success: function (result){
					//返回结果是json格式
					if(result!=null && result.resultValue!=null){
						if(result.successful){
							if(result.resultValue.countNo!=null&&result.resultValue.countNo>0){
								alert("此"+propertyName+"["+objInput.val()+"]已经存在，请重新输入！");
								objInput.focus();
								objInput.val(objInput.val());
								right= true;
							}else{
								right= false;
							}
						} else {
							alert(result.resultValue.msg);
					        right= false;
						}
					}
				}
			});
			return right;
		}
		
		/// <summary>组织树右击弹出的菜单</summary>
		$('#orgTree').tree({
			onContextMenu: function(e, node){
				e.preventDefault();
				// 查找节点
				$('#orgTree').tree('select', node.target);
				// 显示快捷菜单
				//如果节点是员工则显示其对应的菜单：删除员工/刷新
				if(node.id =='0'){					
					$('#orgmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});	
				}else if(node.attributes.nodeid=='3'||node.attributes.nodeid=='4'){
					$('#orgmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});	
				}else if(node.attributes.nodeid=='2'){
					$('#orgempmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});	
				}else{
					$('#empmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});	
				}
			}
		});
		
		/// <summary>刷新树方法</summary>
		function refreshTree(){
			initOrgTree();
		}
		
	    /// <summary>删除员工方法</summary>
		function deleteEmpInfo() {
			var url = "";
			var id =$('#orgTree').tree('getSelected').id;
			var params = "emp_id=" + id;
				url = BASE_PATH + "control/org/deleteEmpInfo.action?etc="
						+ new Date().getTime() + "&emp_id=" + id;
	
			$.messager.confirm('删除','删除员工后无法恢复数据，是否确定删除?',function(result){   
				if (result){
			         $.post(url, null, function(data) {
							if(data.successful){
								alert(data.resultValue);
								$('#emp_Info_div')[0].style.display = 'none';
								initOrgTree();
							}else{
								alert(data.resultValue);
							}
					},"json");
				 }   
				});	
		}
		
		/// <summary>增加员工方法</summary>
		function addEmpInfo(){
			var node = $('#orgTree').tree('getSelected'); 
			con_type = 'addEmp';
			initEmp();
			document.getElementById("emp_info_form").reset();
			$('#org_id').val(node.id);
			$('#orgname').val(node.text);
		}
		
		/// <summary>删除组织机构方法</summary>
		function deleteOrgInfo() {
			var node = $('#orgTree').tree('getSelected');
			var isleaf = $('#orgTree').tree('isLeaf',node.target)
			var id = node.id;
			var url = "";
			if (id == null || id == "") {
				alert("请选择一条数据进行编辑");
				return;
			} else if(!isleaf){
				alert("该节点下有子节点请先删除所有子节点！");return ;
			}else{
				url = BASE_PATH + "control/org/deleteOrgInfo.action?etc="
						+ id + new Date().getTime() + "&org_id=" + id;
			}
	
			$.messager.confirm('删除','删除部门后无法恢复数据，是否确定删除?',function(result){   
				if (result){
			         $.post(url, null, function(data) {
							if(data){
								alert('删除成功！');
								$('#emp_Info_div')[0].style.display = 'none';
								initOrgTree();
							}else{
								alert('删除失败！');
							}
					},"json");
				 }   
				});	
			
		}
		
		/// <summary>增加组织机构方法</summary>
		function addNewOrgInfo(){
			var node = $('#orgTree').tree('getSelected');
			var id = node.attributes.nodeid;
			con_type = 'addOrg';
			initOrg();
			document.getElementById("org_info_form").reset();
			if(node.id=='0'){			
				json = '[{"id":"0","text":"请选择组织机构类型"},{"id":"3","text":"分公司"},{"id":"4","text":"地市公司"},{"id":"2","text":"直属单位"}]';			
			}else if(node.attributes.nodeid=='3'||node.attributes.nodeid=='4'){
				json = '[{"id":"2","text":"直属单位"}]';
			}
			datas = $.parseJSON(json);
			$('#node_type').combobox("loadData", datas);
			initOrg();
		}
</script>


</html>
