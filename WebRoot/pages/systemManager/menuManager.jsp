<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  

<html>  
	<head>		 
	<title></title>  
  <%@include file="../common_header_easyui.jsp" %>
	<script>
		var BASE_PATH='<%=basePath %>'; 
	</script>  
	</head> 
	<script type="text/javascript"> 
	var menuId="M";
	var parentNode;
	var selectNode;
	$(function(){
		initMenuTree();
		initMenuDetailGrid();//数据列表 
	})
	
	/// <summary>更换菜单树图标</summary>
	function changeTreeIcon(node)
	{
		if(node.id=="M"){
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
			checkbox:false,
			onLoadSuccess:function(node, data) {
				// 获取顶级节点
				var rootNode = $("#tree").tree("getRoot");
				if(selectNode != null){
					var node = $("#tree").tree("find", selectNode.id);
					if(node == null){
						var pNode = $("#tree").tree("find", parentNode.id);
						if(pNode == null){
							$("#tree").tree("select", rootNode.target);
						}else{
							$("#tree").tree("select", pNode.target);
						}
					}else{
						$("#tree").tree("select", node.target);
					}
				}else{
					$("#tree").tree("select", rootNode.target);
				}
			},
			onClick:function(node){
				$("#dg").datagrid("options").url=getMenuDetailUrl();
				$("#dg").datagrid("reload");    // 重新载入当前页面数据  
		   },
			onDblClick:function(node) {
              
              }
		});
	}
	
	/// <summary>初始化菜单列表 </summary>
	function initMenuDetailGrid(){
		$('#dg').datagrid({
		url:getMenuDetailUrl(),
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
		    {field:'menuId',title:'菜单编号', width:110,align:'center',hidden:true},
		    {field:'menuName',title:'菜单名称',width:100,align:'center'},
		    {field:'linkUrl',title:'链接地址',width:220,align:'center'},
		    {field:'ordinal',title:'顺序',width:100,align:'center'},
		    {field:'icon',title:'图片样式',width:100,align:'center'},
		    {field:'iconOpen',title:'图片路径',width:200,align:'center'},
		    {field:'display',title:'菜单描述',width:200,align:'center'}
		]],
		toolbar:[{
			id:'btnadd',
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
			clickMenuDetailToolButtons('add');
			}
		},'-',{
			id:'btnedit',
			text:'编辑',
			iconCls:'icon-edit',
			handler:function(){clickMenuDetailToolButtons('edit');}
		},'-',{
			id:'btndelete',
			text:'删除',
			iconCls:'icon-delete',
			handler:function(){clickMenuDetailToolButtons('del');}
		}],
		onClickRow: function(rowNum,record){
		},
		onDblClickRow:function(rowNum,record){
			showForm("编辑菜单");
			$("#menu_form").form("load",record);
		}
	});
	}
		
		
	/// <summary>获取加载菜单的url</summary>
	function getMenuDetailUrl(){ 		
		 var _url=BASE_PATH+"control/menu/queryMenuDataForGrid?menuId="+menuId+"&etc="+new Date().getTime();
		 var node=$("#tree").tree("getSelected");
		 if(node != null){
			 menuId=node.id;
		 	 var isLeaf =$("#tree").tree("isLeaf",node.target); //获得当前点击结点是否包含子结点
       		 if(isLeaf){//判断不包含子结点
        		 _url=BASE_PATH+"control/menu/queryMenuDataByMenuId?menuId="+menuId+"&etc="+new Date().getTime(); //字节点URL
      		 }
		 	 else{
     		 	 	_url=BASE_PATH+"control/menu/queryMenuDataForGrid?menuId="+menuId+"&etc="+new Date().getTime();
     		 	 }
      		 }
		return _url;
	}
	
	
	/// <summary>显示菜单form表单窗口</summary>
	/// <param name="title">窗口标题</param>
	function showForm(title){
		$("#menu_form_container").css("display","block");
		$("#menu_form_container").window({
			title:title,
		    width:600,  
		    height:330, 
		    left:200,
		    top:70,
		    collapsible:false,
		    minimizable:false,
		    maximizable:false,
		    draggable:true,
		    resizable:false,
		    modal:true
		});
		$("#menu_form_container").window("open");
	}
	
	/// <summary>关闭菜单form表单窗口</summary>
	function closeForm(){
		 $("#menu_form_container").window("close");
	}
	
	/// <summary>菜单模块的工具栏相关方法</summary>
	/// <param name="id">工具栏按钮id</param>
	function clickMenuDetailToolButtons(id){	
		switch(id){  
			case "edit": 
				var row = $("#dg").datagrid("getSelected"); // menuDetailGrid.getSelectedId();
				if(row==null || row==""){
					alert("请选择一条数据进行编辑");
					return;
				}
				showForm("编辑菜单");
				$("#menu_form").form("load",row);
				break;
			case "add": 
				var node=$("#tree").tree("getSelected");
				if(node == null ){
					alert("请在左边的菜单树上选择一个节点作为父节点！");
				    return;
				}
				var isLeaf = $("#tree").tree("isLeaf",node.target);
				var p = $("#tree").tree("getParent",node.target);
				if(isLeaf && (p!=null) && p.id!="M" ){
					 alert("您所选择的节点不能添加子节点！");return;
				}
				showForm("新增菜单");
				cleanWin();
				break;
			case "del":
				delMenuInfo();
			break;	
		} 
	}
		
	/// <summary>清空菜单form表单内容</summary>
	function cleanWin(){
		$("#menuId").val("");
		$("#menuName").val("");
		$("#pageWidth").val("0");
		$("#pageHeight").val("0");
		$("#ordinal").val("0");
		$("#linkUrl").val("");
		$("#display").val("");	
		$("#txtIcon").val("");
		$("#txtIconOpen").val("");
		
	}
	
	/// <summary保存或修改菜单</summary>
	function saveMenu(){
		if(!$("#menu_form").form("validate"))
			return;
		var node=$("#tree").tree("getSelected");
		var url="";
		if($("#menuId").val()==""){
			url="<%=basePath %>control/menu/addMenuModel.action?parentId="+node.id+"&etc="+new Date().getTime() ;
		}else{
			url="<%=basePath %>control/menu/updateMenuModel.action?etc="+new Date().getTime();
		}
		$.ajax({ url: url ,
	      cache: false,
	      async: true,
	      type:"post",
	      data:$("#menu_form").serialize(),
	      dataType:"json",
	      success: function(data){            
             alert(data);
             $('#dg').datagrid('reload');
             selectNode = $("#tree").tree("getSelected");
             parentNode = $("#tree").tree("getParent",selectNode.target);
             initMenuTree();
           }
	  });
		  $("#menu_form_container").window("close");
	}
	
	/// <summary删除菜单</summary>
	function delMenuInfo(){
		var row =  $("#dg").datagrid("getSelected");
		if (row == null || row == "") {
			alert("请选择一行记录进行删除！");
			return;
		}else{
			var id =  $("#dg").datagrid("getSelected").menuId; 
			var node = $("#tree").tree("find",id);
			var isLeaf =$("#tree").tree("isLeaf",node.target);
			if(! isLeaf){
				alert("该菜单下还有子菜单，不能删除，请先删除子菜单！！");
				return;
			}
			if (!window.confirm("删除后无法恢复，确认删除吗？"))
					return;
			var url = BASE_PATH	+ "control/menu/deleteMenuInfo.action?menuId="+id+"&etc=" + new Date().getTime();
			$.ajax({ url: url ,
		      cache: false,
		      async: true,
		      type:"post",
		      dataType:"json",
		      success: function(data){            
	             alert(data);
	             $('#dg').datagrid('reload');
	             selectNode = $("#tree").tree("getSelected");
	             parentNode = $("#tree").tree("getParent",selectNode.target);
	             initMenuTree();
	           }
		  });
		}
	}
		
	</script>  
	<body >
	<div id="menu_form_container"  style="height:100%;display:none" >
	 		<div  class="div_toolbar">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-save" plain="true" id="btn_saveAsset" onclick="saveMenu();">保存</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-hide" plain="true" id="btn_saveAsset" onclick="closeForm();">关闭</a>
		     </div>  
		<div  class="div_infoDisplay" style="height:80%;" >
				<form id="menu_form" name="menu_form" 	action="" method="post"> 
					<table	width="100%"	border="0"	align="center"	cellpadding="0"	cellspacing="1"	> 
						<tr>
							<td	class="form_td_label" >
									菜单名称(<font color='red'>*</font>)： 
								</td> 
								<td	class="form_td_input"> 
									<input type="hidden" name="menuId" id="menuId" value=""/>
									<input type="text" id="menuName" name="menuName" class="form_txtbox_input easyui-validatebox"  required="true"/> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									页面宽度： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="pageWidth" name="pageWidth" class="form_txtbox_input" /> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									页面高度： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="pageHeight" name="pageHeight" class="form_txtbox_input" /> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									显示顺序(<font color='red'>*</font>)： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="ordinal" name="ordinal" class="form_txtbox_input easyui-validatebox"  required="true"/> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label">
									链接地址： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="linkUrl" name="linkUrl" class="form_txtbox_input "  /> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									图片样式： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="txtIcon" name="icon" class="form_txtbox_input " /> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									图片路径： 
								</td> 
								<td	class="form_td_input"> 
									<input type="text" id="txtIconOpen" name="iconOpen" class="form_txtbox_input" /> 
								</td> 
						</tr>
						<tr>
							<td	class="form_td_label" >
									菜单描述： 
							</td> 
							<td	class="form_td_input"> 
								<input type="text" id="display" name="display" class="form_txtbox_input" /> 
							</td> 
						</tr>
						
					</table> 
				</form>
		</div>
	</div>
<div class="easyui-layout"  style="width:100%;height:100%">
	<div id="west" data-options="region:'west',split:true" title="菜单树" style="width:200px">
		<div id="div_tree" style="height:100%; width:190px">  
			<ul id="tree" class="easyui-tree" animate="true" data-options="" />  
		</div>
	</div>
		<div id="center" data-options="region:'center',split:true" title="菜单列表" style="width:950px">
		<div id="div_grid" style="margin: 0px;padding: 0px;width:100%;height:100%">
   	     <table id="dg" ></table>
   		 </div>
	</div>
</div>	
		
	</body> 
</html> 