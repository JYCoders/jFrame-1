<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>		
	<title></title>
	<%@include file="../common_header_easyui.jsp" %>
  </head>
  <script type="text/javascript">
		var loginStateColumn = ${loginStateColumn};
		var BASE_PATH='<%=basePath%>'; 
		$(function(){
			initUserGrid();//数据列表
			initRoleTree();
		})
		
		/// <summary>初始化角色树</summary>
		function initRoleTree(){
			var url = "<%=basePath%>control/role/getRoleTree.action?etc="+new Date().getTime();		
			$("#tree").tree({
				url:url,
				checkbox:true,
				onLoadSuccess:function(node, data) {
					// 获取顶级节点
					var rootNode = $('#tree').tree('getRoot');
					if (rootNode != null) {
						// 默认设置顶级节点选中
						$('#tree').tree('select', rootNode.target);
						
					}
				},
				onClick:function(node){
					
			   },
				onDblClick:function(node) {
	              
	              }
			});
		}
		
		/// <summary>初始化用户数据列表</summary>
		function  initUserGrid(){
			var columnsStatu="";
			if(loginStateColumn==0){
				columnsStatu = "[[ {field:'loginId',title:'登录名',width:200,align:'center'},"
							  +"{field:'userName',title:'用户中文名',width:130,align:'center'},"
							  +"{field:'deptName',title:'部门',width:300,align:'center'},"
							  +"{field:'lockState',title:'锁定状态',width:200,align:'center', formatter:lockState}"
							  +"]]";
			}
			if(loginStateColumn==1){
				columnsStatu = "[[ {field:'loginId',title:'登录名',width:150,align:'center'},"
					  +"{field:'userName',title:'用户中文名',width:230,align:'center'},"
					  +"{field:'deptName',title:'部门',width:250,align:'center'},"
					  +"{field:'lockState',title:'锁定状态',width:100,align:'center', formatter:lockState},"
					  +"{field:'loginOnTime',title:'登录状态',width:100,align:'center',formatter:loginState}"
					  +"]]";
			}
			$("#dg").datagrid({
			url:"<%=basePath %>control/user/allUsersForGrid.action?etc="+new Date().getTime(),
			singleSelect:true,
			rownumbers:true,
			striped:true,//条纹
			fitConlumns:true,
			width:$("#div_grid")[0].clientWidth,
			height:$("#div_grid")[0].clientHeight-5,
			pagination:true,//分页控件
			pageNumber:1,
			pageSize:15,   
			pageList:[10,15,20,30,40,50],
			columns:eval(columnsStatu),
			toolbar:[{
				id:'btnadd',
				text:'新增',
				iconCls:'icon-add',
				handler:function(){clickToolButtons('add');}
			},'-',{
				id:'btnedit',
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){clickToolButtons('update');}
			},'-',{
				id:'btndelete',
				text:'删除',
				iconCls:'icon-delete',
				handler:function(){clickToolButtons('delete');}
			}],
			onClickRow: function(rowNum,record){
				doRoleCheckByUser(record.userId);//根据用户选中角色
			},
			onDblClickRow:function(rowNum,record){
				showUserForm("编辑用户");
				$("#addUser").css("display","none");
				$("#editUser").css("display","block");
				$("#form1").form("load",record);
			}
		});
		}
		
		/// <summary>返回用户状态</summary>
		/// <param name="value">数据值</param>
		/// <param name="rowData">datagrid行数据</param>
		/// <param name="rowIndex">datagrid行索引</param>
		function lockState(value, rowData, rowIndex){
			if(value=="解锁"){
				return "<button  style=\"cursor: pointer;\"  onClick=\"openlockUser('"+rowData.loginId+"')\" >"+value+"</button>";
			}else if(value=="未锁定"){
			    return "未锁定";
			}
        }
		/// <summary>返回用户登录状态</summary>
		/// <param name="value">数据值</param>
		/// <param name="rowData">datagrid行数据</param>
		/// <param name="rowIndex">datagrid行索引</param>
		function loginState(value, rowData, rowIndex){
			if(value=="已登录"){
				return "<button  style=\"cursor: pointer;\"  onClick=\"killLoginUser('"+rowData.loginId+"')\" >解除已登录</button>";
			}else if(value=="未登录"){
			    return "未登录";
			}
		}
		/// <summary>解锁用户</summary>
		/// <param name="loginId">用户登录id</param>
		function openlockUser(loginId){
			var url = "<%=basePath %>control/audit/openLockUser?etc=" + new Date().getTime() + "&loginId=" + loginId;
			// 异步获取节点元素信息
			$.ajax({  
				type: "POST",  
                dataType: "json",  
                url: url,  
                data: {},
                cache:false, 
                success: function (data) {
                    alert(data);
                    initUserGrid();
                },  
                error: function() {  
                    $.messager.alert("消息", "解锁失败！", "info");  
                }  
	         }); 
		}
		/// <summary>注销登录的用户</summary>
		/// <param name="loginId">用户登录id</param>
		function killLoginUser(loginId){
			var url = "<%=basePath %>control/user/killLoginUser?etc=" + new Date().getTime() + "&loginId=" + loginId;
			// 异步获取节点元素信息
			$.ajax({  
				type: "POST",  
                dataType: "json",  
                url: url,  
                data: {},
                cache:false, 
                success: function (data) {
                    alert(data);
                    initUserGrid();
                },  
                error: function() {  
                    $.messager.alert("消息", "关闭已登录用户失败！", "info");  
                }  
	         });
		}
		/// <summary>显示用户form表单窗口</summary>
		/// <param name="title">窗口标题</param>
		function showUserForm(title){
			$("#form_container").css("display","block");
			$("#form_container").window({
				title:title,
			    width:600,  
			    height:200, 
			    top:100,
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
		
		/// <summary>清空用户form表单内容</summary>
		function clearFormVal(){
			$("#userName").val("--请选择--");
			$("#userId").val("");
			$("#empId").val("");
			$("#deptName").val("--自动生成--");
			$("#deptId").val("");
			$("#loginId").val("");
			
	    }
		
		/// <summary>用户工具栏相关方法</summary>
		/// <param name="id">工具栏按钮id</param>
		function clickToolButtons(id){  
			switch(id){
				case "add":				  
				    showUserForm("新增用户");
					clearFormVal();
					$("#addUser").css("display","block");
					$("#editUser").css("display","none");
					break;
				case "update":
					var row= $("#dg").datagrid('getSelected');
					if(!row){
						alert("请选择一行进行编辑！");
						return;
					}
					showUserForm("编辑用户");
					$("#addUser").css("display","none");
					$("#editUser").css("display","block");
					$("#form1").form("load",row);
					$("#userPass").val("");
					break;
				case "delete":
					var row= $("#dg").datagrid('getSelected');
					if(!row){
						alert("请选择一行进行删除！");
						return;
					}
					var userId = row.userId;
					delUser(userId);
					break;
			}
		}
		
		/// <summary>用户form表单提交验证和保存</summary>
		function checkAndSave(){
		   if(!$("#form1").form("validate"))
			   return;
		   var userId=$("#userId").val();
		   if(checkLoginIdExists()){
			   alert("用户已存在,不能保存");
			   return ;
		   }
		   if(userId==null || userId==""){
			   doAdd();
			   $("#form_container").window("close");
		   }else{
			   doUpdate();
			   $("#form_container").window("close");
		   }
		   
		}
		
		/// <summary>验证登录ID是否已存在</summary>
		function checkLoginIdExists(){
			var oldId=$("#userId").val();
			var loginId=$("#loginId").val();
			$.ajax({ url: "<%=basePath %>control/user/isLoginIdExists.action?oldId="+oldId+"&loginId="+loginId+"&etc="+new Date().getTime() ,
		      cache: false,
		      async: true,
		      type:"post",
		      dataType:"json",
		      success: function(data){    
				     return data.success;
	           }
		  });
			return false;
		} 
		
		/// <summary>用户新增</summary>
		function doAdd(){
			$("#userPass").val("dcms123456");
		    $.ajax({ url: "<%=basePath %>control/user/saveUserModel.action?etc="+new Date().getTime() ,
		      cache: false,
		      async: true,
		      type:"post",
		      data:$("#form1").serialize(),
		      dataType:"json",
		      success: function(data){            
	             alert(data);
	             $('#dg').datagrid('reload');
	           }
		  });
		}
		
		/// <summary>用户修改</summary>
		function doUpdate(){
		$("#userPass").val("");
		    $.ajax({
			type: "post",
			cache: false,
			async: false,
			url: BASE_PATH + "control/user/updateUserModel.action?etc="+ new Date().getTime(),
			data:$("#form1").serialize(),
			dateType: "json",
			success: function (data){
				 alert(data);
	             $('#dg').datagrid('reload');
				}
			});
		}
		
		/// <summary>删除用户</summary>
		/// <param name="id">用户id </param>
		function delUser(id){
			if(!id){
				alert("请选择一行记录进行删除！");
				return;
			}else{
				if (!window.confirm("删除后无法恢复，确认删除吗？"))
					return;
				$.ajax({
				type: "post",
				cache: false,
				async: false,
				url: BASE_PATH + "control/user/delUserModel.action?actionId=" + id + "&etc=" + new Date().getTime(),
				dateType: "json",
				success: function (data){
					 alert(data);
		             $('#dg').datagrid('reload');
					}
				});
			}
		}
		
		/// <summary>角色模块的工具栏方法</summary>
		/// <param name="id">工具栏按钮id </param>
		function clickRoleToolButtons(id){  
			switch(id){
				case "save":
					var row= $('#dg').datagrid('getSelected');
					if(!row){
						alert("请选择一个用户进行角色保存！");
						return;
					}
					var id=row.userId; 
					var nodes = $("#tree").tree("getChecked");
					var ids="";
					for(var i=0;i<nodes.length;i++)
						ids=ids+nodes[i].id+",";
					var url="<%=basePath%>control/user/saveUserRoles.action?userId="+id+"&operate="+ids+"&etc="+new Date().getTime();
					$.ajax({
						type: "post",
						cache: false,
						async: false,
						url: url,
						dateType: "json",
						success: function (data){
							 alert(data);
							}
						});
		       		break;
			     case "checkAll"://全选
			        var roots = $('#tree').tree('getRoots');
					for(var i=0;i<roots.length;i++)
					$("#tree").tree("check",roots[i].target);
			        break;
			    case "reverse"://反选
			   		var roots = $('#tree').tree('getRoots');
			    	var nodes = $('#tree').tree('getChecked');
					for(var i=0;i<roots.length;i++)
					$("#tree").tree("check",roots[i].target);
					for(var i=0;i<nodes.length;i++)
					$("#tree").tree("uncheck",nodes[i].target);
			        break;
			    case "uncheckAll"://清空
			       	var roots = $('#tree').tree('getRoots');
					for(var i=0;i<roots.length;i++)
					$("#tree").tree("uncheck",roots[i].target);
			        break;
			}
		}
		
		
		/// <summary>重置密码</summary>
		function doResetPass(){
			$("#userPass").val("dcms123456");
		    $.ajax({ url: "<%=basePath %>control/user/updateUserModel.action?etc="+new Date().getTime() ,
		      cache: false,
		      async: true,
		      type:"post",
		      data:$("#form1").serialize(),
		      dataType:"json",
		      success: function(data){            
	             alert(data);
	           }
		  });
		}
					
		/// <summary>根据用户选中角色</summary>
		/// <param name="rowId">datagrid中选中的用户行</param>
		function doRoleCheckByUser(rowId){
			var nodes = $("#tree").tree("getChecked");//初始化菜单树的多选框为未选择状态
			for(var i=0;i<nodes.length;i++)
			$("#tree").tree("uncheck",nodes[i].target);
			$.ajax({
			type: "post",
			cache: false,
			async: false,
			url: "<%=basePath%>control/role/getRolesByUserId.action?userId="+rowId+"&etc="+new Date().getTime(),
			dateType: "json",
			success: function (data){
				    if(data!=null &&　data.length>0){
				    	for(var i=0;i<data.length;i++){
				    		var node = $('#tree').tree('find', data[i].roleId);
							$('#tree').tree('check', node.target);
				    		
				    	}
				    }
				}
			});
		}
		
		/// <summary>弹出用户中文名选择的窗口</summary>
		function initSelectWindow(){
			$('#diag').dialog({    
			    title: '用户选择',    
			    width: 380,    
			    height: 400, 
			    top:50,
			    closed: false,    
			    cache: false,        
			    modal: true,
			    toolbar:[{
					text:'选择',
					iconCls:'icon-ok',
					handler:seletOrg
				},{
					text:'不选',
					iconCls:'icon-close',
					handler:function(){$('#diag').window('close');}
				}]

			});
			initOrgTree();
		}
		
		/// <summary>初始化组织结构树</summary>
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
					//initNode(node);				
				},
				onDblClick:function(node){
					var nodeParent = $('#orgTree').tree('getParent', node.target);
					var node_type = node.attributes.node_type;
						if(node_type == 'emp'){
							$('#diag').window('close');
							$('#userName').val(node.text);
							$('#empId').val(node.id);
							$('#deptName').val(nodeParent.text);
							$('#deptId').val(nodeParent.id);
						}  
				}
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
		
		/// <summary>选择组织机构</summary>
		function seletOrg(){
			var node = $('#orgTree').tree('getSelected');
			var nodeParent = $('#orgTree').tree('getParent', node.target);
			var node_type = node.attributes.node_type;
			if(node_type == 'emp'){
				$('#diag').window('close');
				$('#userName').val(node.text);
				$('#empId').val(node.id);
				$('#deptName').val(nodeParent.text);
				$('#deptId').val(nodeParent.id);
			}
		}
  </script>
  <body >
  <div id="form_container"  style="height:100%;display:none">
		<div id="addUser" class="div_toolbar" style="display: none">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="checkAndSave();">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true"  onclick="closeForm();">关闭</a>
		</div>
		<div id="editUser" class="div_toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="checkAndSave();">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-reset" plain="true" id="resetpass" onclick="doResetPass();">重置密码</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true"  onclick="closeForm();">关闭</a>
		</div>
  	<div  class="div_infoDisplay" style="height:70%;">
		<form id="form1" name="form1" action="" method="post">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" >
						<tr>
							<td class="form_td_label" >
								登录名(<font color="red">*</font>)：
							</td>
							<td class="form_td_input">
								<input name="loginId" id="loginId" class="form_txtbox_input easyui-validatebox"  data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td class="form_td_label" >
								用户中文名(<font color="red">*</font>)：
							</td>
							<td class="form_td_input">
							     <input type="hidden"  name="userId" id = "userId" value=""/>
								 <input type="text" name="userName" id="userName" value="--请选择--" readonly onclick="initSelectWindow()" class="form_txtbox_input" data-options="required:true"/>
								 <input id="empId" name="empId" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="form_td_label" >
								部门(<font color="red">*</font>)：
							</td>
							<td class="form_td_input">
							    <input id="deptId" name="deptId" type="hidden"/>
							    <input id= "deptName" name="deptName" value="--自动生成--" readonly class="form_txtbox_input easyui-validatebox" data-options="required:true"/>
							</td>
						</tr>
					</table>
		<input type="password" name="userPass" id="userPass" size="26"  style="visibility: hidden;"/>
		</form>
	</div>
</div>
<div class="easyui-layout" data-options="fit:true" style="width:100%;height:100%">
	<div id="west" data-options="region:'center',split:true" title="用户列表" style="width:900px">
		<div id="div_grid" style="margin: 0px;padding: 0px; width:100%;height:100%">
   	     <table id="dg" ></table>
   		 </div>
	</div>
	<div id="center" data-options="region:'east',split:true" title="角色列表" style="width:300px">
		<div id="div_tree" style="height:100%; overflow-y:scroll;">  
		<div  class="div_toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="clickRoleToolButtons('save');">保存</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-checkall" plain="true"  onclick="clickRoleToolButtons('checkAll');">全选</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-collapseall" plain="true"  onclick="clickRoleToolButtons('reverse');">反选</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-uncheckall" plain="true"  onclick="clickRoleToolButtons('uncheckAll');">清空</a>
		</div>  
			<ul id="tree" class="easyui-tree" animate="true" data-options="" />  
		</div>
	</div>
</div>
		<div id = 'diag'>
			<div id = 'orgTree' style="margin: 5px 5px 3px 5px"></div>
		</div>	
  </body>
</html>
