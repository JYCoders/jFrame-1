<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
	<head>		
	<title></title>
	 <%@include file="../common_header_easyui.jsp" %>
	<script>
		var BASE_PATH='<%=basePath%>'; 
	</script>
	<style type="text/css">
		.td_Class{
			text-align:right;
			font-size:12px;
		}
		.font_red{
			color:red;
		}
	</style>
  </head>
  <script type="text/javascript">
		
		$(function(){
			initRoleGrid();//数据列表
			initMenuTree();
		})
		
		/// <summary>更换菜单树图标</summary>
		function changeTreeIcon(node)
		{
			if(node.attributes.menuId=="M"){
				node.iconCls="icon-menuDomain";
			}else if(node.attributes.parentId=="M"){
				node.iconCls="icon-menuSub";
			}else{
				node.iconCls="icon-menuChild";
			}
			if(node.children.length==0){
				return;
			}else{
				for(var i=0;i<node.children.length;i++){
					changeTreeIcon(node.children[i]);
				}
			}
		}
		
		/// <summary>初始化菜单树</summary>
		function initMenuTree(){
			var treeData = null;
			var url = "<%=basePath%>control/menu/getMenuTree.action?etc="+new Date().getTime();		
			$.ajax({
				type: "POST",
				async: false,
				cache: false,
				url:url,
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				success: function (data){	
				     treeData=data;
				    data[0].iconCls="icon-menuDomain";
				    var child2=data[0].children;
			 		for(var i=0;i<child2.length;i++){
			 			child2[i].iconCls="icon-menuSub";
			 			var child3=child2[i].children;
			 			for(var j=0;j<child3.length;j++)
			 			child3[j].iconCls="icon-menuChild";
			 			
			 		}
				}	
			});
		
			$("#tree").tree({
				data:treeData,
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
		
		/// <summary>菜单模块的工具栏相关方法</summary>
		/// <param name="id">工具栏按钮id</param>
		function clickToolButtons(id){  
			switch(id){
				case "save":
					var row= $('#dg').datagrid('getSelected');
					if(!row){
						alert("请选择一个角色进行菜单保存！");
						return;
					}
					var id=row.roleId; 
					var nodes = $("#tree").tree("getChecked");
					var ids="M,";
					for(var i=0;i<nodes.length;i++){
						ids=ids+nodes[i].id+",";
						var p=$("#tree").tree("getParent",nodes[i].target);
						if(p!=null)
						ids=ids+p.id+",";
					}
					var url="<%=basePath%>control/role/saveRoleMenus.action?roleId="+id+"&menuIdsTemp="+ids+"&etc="+new Date().getTime();
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
			        var root = $('#tree').tree('getRoot');
					$("#tree").tree('check',root.target);
			        break;
			    case "reverse"://反选
			   		var root = $('#tree').tree('getRoot');
			    	var nodes = $('#tree').tree('getChecked');
			    	$("#tree").tree('check',root.target);
					for(var i=0;i<nodes.length;i++)
					$("#tree").tree("uncheck",nodes[i].target);
			        break;
			    case "uncheckAll"://清空
			       	var nodes = $("#tree").tree("getChecked");
					for(var i=0;i<nodes.length;i++)
					$("#tree").tree("uncheck",nodes[i].target);
			        break;
			}
		}
		
		/// <summary>初始化角色数据列表</summary>
		function  initRoleGrid(){
	     	$('#dg').datagrid({
			url:"<%=basePath%>control/role/allRolesForGridNoCheckBox.action?etc="+new Date().getTime(),
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
			columns:[[ 
			    {field:'roleId',title:'角色编码', width:110,align:'center',hidden:true},
			    {field:'roleName',title:'角色名称',width:340,align:'center'},
			    {field:'roleDesc',title:'角色描述',width:500,align:'center'}
			]],
			toolbar:[{
				id:'btnadd',
				text:'新增',
				iconCls:'icon-add',
				handler:function(){
					showRoleForm("新增角色");
					$("#roleId").val("");
					$("#roleName").val("");
					$("#roleDesc").val("");
				}
			},'-',{
				id:'btnedit',
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){
					var row= $('#dg').datagrid('getSelected');
					if(!row){
						alert("请选择一行进行编辑！");
						return;
					}
					showRoleForm("编辑角色");
					$("#form1").form("load",row);
				}
			},'-',{
				id:'btndelete',
				text:'删除',
				iconCls:'icon-delete',
				handler:function(){
					var row= $('#dg').datagrid('getSelected');
					if(!row){
						alert("请选择一行进行删除！");
						return;
					}
					var rid = row.roleId;
					delRole(rid);
				}
			}],
			onClickRow: function(rowNum,record){
			    doMenuCheckByRole(record.roleId);//根据角色选中菜单
			},
			onDblClickRow:function(rowNum,record){
				showRoleForm("编辑角色");
				$("#form1").form("load",record);
			}
		});
		}
		
		/// <summary>显示角色form表单窗口</summary>
		/// <param name="title">窗口标题</param>
		function showRoleForm(title){
			$("#form_container").css("display","block");
			$("#form_container").window({
				title:title,
			    width:660,  
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
		
		/// <summary>关闭角色form表单窗口</summary>
		function closeForm(){
		 $("#form_container").window("close");
		}
		
		/// <summary>清空角色form表单内容</summary>
		function resetRole(){
			$("#roleName").val("");
			$("#roleDesc").val("");
		}
		
		/// <summary>角色form表单提交验证和保存</summary>
		function checkAndSave(){
		   if(!$("#form1").form("validate"))
			   return;
		    var roleId=document.getElementById("roleId").value;
		    if(roleId&&roleId.replace(" ","")!=""){//若编辑ID存在，则是修改，若编辑ID不存在，则是新增
		    	doUpdate();
		        $("#form_container").window("close");
		    }else{
		    	doAdd();
		    	$("#form_container").window("close");
		    }
		}
		
	    /// <summary>角色新增</summary>
		function doAdd(){
		  $.ajax({ url: "<%=basePath %>control/role/saveRoleModel.action?etc="+new Date().getTime() ,
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
		
		
		/// <summary>角色修改</summary>
		function doUpdate(){
		    $.ajax({
			type: "post",
			cache: false,
			async: false,
			url: BASE_PATH + "control/role/updateRoleModel.action?etc="+ new Date().getTime(),
			data:$("#form1").serialize(),
			dateType: "json",
			success: function (data){
				 alert(data);
	             $('#dg').datagrid('reload');
				}
			});
		}
		
		/// <summary>删除角色</summary>
		/// <param name="id">菜单id </param>
		function delRole(id){
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
				url: BASE_PATH + "control/role/delRoleModel.action?roleId=" + id + "&etc=" + new Date().getTime(),
				dateType: "json",
				success: function (data){
					 alert(data);
		             $('#dg').datagrid('reload');
					}
				});
			}
		}
		
		/// <summary>根据角色选中菜单</summary>
		/// <param name="rowId">datagrid中选中的菜单行</param>
		function doMenuCheckByRole(rowId){
			var nodes = $("#tree").tree("getChecked");//初始化菜单树的多选框为未选择状态
			for(var i=0;i<nodes.length;i++)
			$("#tree").tree("uncheck",nodes[i].target);
			$.ajax({
			type: "post",
			cache: false,
			async: false,
			url: "<%=basePath%>control/menu/allMenusByRoleId.action?roleId="+rowId+"&etc="+new Date().getTime(),
			dateType: "json",
			success: function (data){
				    if(data!=null &&　data.length>0){
				    	for(var i=0;i<data.length;i++){
				    		var node = $('#tree').tree('find', data[i].menuId);
				    		var isLeaf =$("#tree").tree("isLeaf",node.target);
				    		if(node && isLeaf)
							$('#tree').tree('check', node.target);
				    		
				    	}
				    }
				}
			});
		}	
		
</script>
  <body >
  <div id="form_container"  style="height:100%;display:none" >
	<div  class="div_toolbar">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true" id="btn_saveAsset" onclick="checkAndSave();">保存</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true" id="btn_saveAsset" onclick="closeForm();">关闭</a>
	 </div>  
						
						
  	<div class="div_infoDisplay" style="height:70%;" >
		<form id="form1" name="form1" action="" method="post">
			<table  width="100%" align="center" cellpadding="0" cellspacing="1" >
				<tr>
					<td class="form_td_label" >
						角色名称(<label class="font_red">*</label>)：
					</td>
					<td class="form_td_input">
						<input type="hidden" name="roleId" id="roleId" value=""/>
						<input name="roleName" id="roleName"   class="form_txtbox_input easyui-validatebox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td class="form_td_label" rowspan="3">
						角色描述(50字以内)：
					</td>
					<td class="form_td_input"  rowspan="3" >
						<textarea name="roleDesc" id="roleDesc"  rows="3"  class="txtarea"></textarea>
					</td>
				</tr>	
			</table>
		</form>
	</div>
</div>
<div class="easyui-layout" data-options="fit:true" style="width:100%;height:100%">
	<div id="west" data-options="region:'center',split:true" title="角色列表" style="width:900px">
		<div id="div_grid" style="margin: 0px;padding: 0px;width:100%;height:100%">
   	     <table id="dg" ></table>
   		 </div>
	</div>
	<div id="center" data-options="region:'east',split:true" title="菜单列表" style="width:300px">
		<div id="div_tree" style="height:100%; overflow-y:scroll;">  
		<div  class="div_toolbar">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true"  onclick="clickToolButtons('save');">保存</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-checkall" plain="true" onclick="clickToolButtons('checkAll');">全选</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-collapseall" plain="true" onclick="clickToolButtons('reverse');">反选</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-uncheckall" plain="true"  onclick="clickToolButtons('uncheckAll');">清空</a>
		</div>  
			<ul id="tree" class="easyui-tree" animate="true" data-options="" />  
		</div>
	</div>
</div>	
  </body>
</html>
