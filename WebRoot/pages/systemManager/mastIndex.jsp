<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="../common_header_easyui.jsp" %>
<script>
var BASE_PATH='<%=basePath %>'; 
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>码表管理</title>
  </head>
<style>
.dialog-toolbar  {
    BACKGROUND: url(../../images/toolbar_bg.jpg) #ffffff repeat-x left top; 
    margin-top:2px;
    margin-left:1px; 
    border: 1px solid silver;
    padding: 0px 0px;
}
  </style>
  <script type="text/javascript">
		  var nodeRoot="";
		  var selectTreeNode="";
		  var pCode = '';
		  var flag = true;
		  /// <summary>加载页面时初始化方法</summary>
		  $(document).ready(function(){
		    	 //初始化下拉框
		    	 initCombox()
		    	 //加载初始化码表树
		    	 initBaseTree();
		    	 initSmallTree();
		     });
		  function initBaseTree(){
			  var baseData = [{id: 'node',text: '码表管理',iconCls:'icon-codingtype'}];
			  $('#mastTree').tree({
				  data:baseData,
				  checkbox:false,
		 		  lines:true,
		 		  animate:true,
		 		  onLoadSuccess:function(node, data) {
		 			  if(flag==true){
						//初始化根节点被选中
						nodeRoot =  $('#mastTree').tree('getRoot');
						var node = $('#mastTree').tree('find', nodeRoot.id);
						$('#mastTree').tree('select', node.target);	}					
						//初始化码表datagrid
				    	loadMastGrid("",node.id);
				    	
					},
					onLoadError:function(){
						alert("码表数据加载失败！");
					},
					onClick:function(node){
						flag=false;
						var nodeParent = $('#mastTree').tree('getParent',node.target);
						var nodeChild = $('#mastTree').tree('getChildren',node.target);
						if(nodeChild.length==0){
							initSmallTree(node.id);
						}
						if(nodeParent==null){
							selectTreeNode = node.id;
							changeStatu1(node.id);
						}else if(nodeParent.id=='combox'){
							changeStatu2(node.id);
						}else{
							selectTreeNode = node.id;
							changeStatu1(node.id);
						}
					}
			  });
		  }
		/// <summary>初始化码表树分步加载方法</summary>
		  function initSmallTree(_nodeId){
			  var _url = BASE_PATH+"control/master/getSynocMastTree.action";
			  $.ajax({
				  type:'post',
				  async:false,
				  cache:false,
				  data:{'nodeId':_nodeId},
				  dataType:"json",
				  url:_url,
				  contentType:'application/x-www-form-urlencoded;charset=UTF-8',
				  success: function (data){
					  var selected = $('#mastTree').tree('getSelected');
					  $('#mastTree').tree('append', {
							parent: selected.target,
							data: data
						});

				  }
				  
			  });
		  }	 
	     /// <summary>单击树根节点触发的方法方法</summary>
	     /// <param name="_id">节点id</param>
	     function changeStatu1(_id){
		    	if($('#div_dg')[0].style.display=='none'){
		    		$('#layoutRight').layout('expand','north');
		    	}		    	
		    	$('#div_dg2')[0].style.display='none';
		    	$('#div_dg')[0].style.display='block';
		    	$('#divMastVal')[0].style.display='none';
		    	$('#divMastVal').hide();
			    $('#dg').datagrid('options').url = BASE_PATH + "control/master/getMastInfoList.action?etc=" + new Date().getTime();
			    var queryParams = $("#dg").datagrid("options").queryParams;
			    queryParams.masterCode = "";
				queryParams.masterName = "";
				queryParams.useFlag = "";
				queryParams.pid = _id;
				$("#dg").datagrid("options").queryParams = queryParams;
			    $('#dg').datagrid('reload');
	     }

	     /// <summary>单击叶子节点触发的方法方法</summary>
	     /// <param name="_id">节点id</param>
	     function changeStatu2(_id){
	    	 if($('#div_dg2')[0].style.display=='none'){
	    		 $('#layoutRight').layout('collapse','north');
	    	 }
				$('#div_dg')[0].style.display='none';
				$('#div_dg2')[0].style.display='block';
				$('#divMastVal').show();				
				initMasterVal(_id);
				initSingleBase(_id);				
	     }
     
	     /// <summary>单击叶子节点时初始化码表值上方码表基本信息方法</summary>
	     /// <param name="_id">节点id</param>
	     function initSingleBase(_id){
	    	 $.ajax({
	 			url: "<%=basePath %>control/master/getSignleMastByID.action?master_code="+_id+"&etc=" + new Date().getTime(),
	     		cache: false,
	      		async: true,
	 		    type:"post",
	 		    dataType:"json",
	 		    success: function(data){
	 		    	$('#valMasterCode').val(data.master_code);
	 		    	$('#valMasterName').val(data.master_name);
	 		    	$('#valMasterUseFlag').val(data.use_flag);
	 		    	$('#valMasterRemark').val(data.remark);
	 		    	$('#valMasterPid').val(data.p_id);
	 		    	$('#valMasterVal').val(data.master_val);
	 		    	
	 		    	$('#valMasterCode').attr('disabled',true);
	 		    	$('#valMasterName').attr('disabled',true);
	 		    	$('#valMasterUseFlag').attr('disabled',true);
	 		    	$('#valMasterRemark').attr('disabled',true);
	 		    	$('#valMasterPid').attr('disabled',true);
	 		    	$('#valMasterVal').attr('disabled',true);
	 		    }
	    	     });
	     }
 
	 	/// <summary>初始化启用标识下拉框方法</summary>
	 	function initCombox() {
	 	    $(".combo").click(function(){
	 	   		$(this).prev().combobox("showPanel");
	 	  	});
	 	    loadMastTypeComList3("USE_FLAG", "cmbuseFlag", "启用标识", 0); // 绑定码表启用标识下拉框
	 	    loadMastTypeComList3("USE_FLAG", "cmbuseFlag2", "启用标识", 0); // 绑定码表值启用标识下拉框
	 	}

		/// <summary>初始化码表datagrid方法</summary>
		/// <param name="url">初始化所需的url</param>
		function loadMastGrid(url,_id) {
		    if(url=="" || url==null){
		         url = BASE_PATH + "control/master/getMastInfoList.action?pid="+_id+"&etc=" + new Date().getTime();
		    }
			$("#dg").datagrid({
				url:url,
				singleSelect:true,
				rownumbers:true,
				striped:true,//条纹
				width:self.parent.$(window).width()-257,	
				height:self.parent.$(window).height()-90,
				pagination:true,//分页控件
				pageNumber:1,
				pageSize:10,
				fitColumns:true,
				pageList:[10,15,20,30,40,50],
				columns:[[ 
				    {field:'masterCode',title:'代码编号',align:'center',width:260},
				    {field:'masterName',title:'代码名称',align:'center',width:180},
				    {field:'masterVal',title:'代码值',align:'center',width:200},
				    {field:'remark',title:'代码描述',width:350},
				    {field:'pid',title:'上级代码',width:100,align:'center'},
				    {field:'ndType',title:'节点类型',width:100,align:'center'},
				    {field:'useFlag',title:'启用标识',width:100,align:'center'},
				    {field:'sortNum',title:'显示顺序',width:100,align:'center'}
				]],
				toolbar:[{
					id:'btnadd',
					text:'新增',
					iconCls:'icon-add',
					handler:function(){clickMenuDetailToolButtonsOfHead('add');}  
				},'-',{
					id:'btnedit',
					text:'编辑',
					iconCls:'icon-edit',
					handler:function(){clickMenuDetailToolButtonsOfHead('edit');}  
				},'-',{
					id:'delete',
					text:'删除',
					iconCls:'icon-delete',
					handler:delMastTypeInfo   
				}],
				
				view : $.orzView,
				onLoadSuccess: function(data){
			    	//默认选中第一行
					$(this).datagrid('selectRow', 0);
			        var row = $('#dg').datagrid('getSelected'); //mastDetailGrid.getSelectedId();
					if(row!=undefined ||row!=null){
			        typeId=row.id;
					masterCode=row.masterCode;}
				}
			});
		}
		/// <summary>码表工具栏delete方法</summary>
		function delMastTypeInfo(){
			var row = $("#dg").datagrid("getSelected");
			if(row == null || row == ""){
				alert("请选择一行记录进行删除！");
				return;
			}
			var _masterCode = row.masterCode;
			var id = row.id;
			if (!window.confirm("删除后无法恢复，确认删除吗？"))
				return;
				id=encodeURIComponent(encodeURIComponent(id));
				$.ajax({
				url: "<%=basePath %>control/master/getMastValInfoList.action?masterCode="+_masterCode+"&etc=" + new Date().getTime(),
	    		cache: false,
	     		async: true,
			    type:"post",
			    dataType:"json",
			    success: function(data){   
					if(data.total>0){
						alert("该类型值下面有属性值，不能删除！");
						return;
				  	}
		      	  	var url = BASE_PATH + "control/master/deleteMastType.action?id="+id+"&etc=" + new Date().getTime();
				    $.ajax({
					    url: url,
						cache: false,
						async: false,
						type: "post",
						dataType:"json",
						success: function(data){            
							alert(data.resultValue);
							if (data.successful) {
					        	 flag=true;
					        	 initBaseTree();
						    	 initSmallTree();
							}
						},
						error: function(request) {
				            alert(data.resultValue);
				            return;
			            }
		 			});
		        }});
			
		}
		/// <summary>码表查询</summary>
		function searchMastTypeInfo(){
			//如果不是根节点，则查询功能失效
			var nodeTemp = $('#mastTree').tree('getSelected')
			//if(nodeTemp.id!=nodeRoot.id){return;}
			var masterCode=encodeURIComponent($("#txtMasterCode").val());
			var masterName=encodeURIComponent($("#txtMasterName").val());
			var useFlag =$("#cmbxUseFlag").combobox("getValue");
			var url= BASE_PATH + "control/master/getMastInfoList.action?etc=" + new Date().getTime();//+params
			// 查询参数		
			var queryParams = $("#dg").datagrid("options").queryParams;
			// 参数重新赋值
			queryParams.masterCode = masterCode;
			queryParams.masterName = masterName;
			queryParams.useFlag = useFlag;
			
	        $("#dg").datagrid("options").queryParams = queryParams;
	        $("#dg").datagrid("options").url = url;
			
	        // 接口列表重新加载
	        $("#dg").datagrid("reload");
		}
		/// <summary>初始化码表值列表</summary>
		function initMasterVal(_id) {
	        $('#dg2').datagrid({
				url:BASE_PATH +"control/master/getMastValInfoList.action?masterCode="+_id+"&etc=" + new Date().getTime(),
				singleSelect:true,
				rownumbers:true,
				striped:true,//条纹
				fitColumns:true,
				width:$('#dpd')[0].clientWidth-1,
				height:$('#dpd')[0].clientHeight-120,
				pagination:true,//分页控件
				pageNumber:1,
				pageSize:10,   
				pageList:[5,10,20,30,40,50],
				idField:'ID',
				columns:[[ 
				    {field:'valName',title:'值名称',width:260,align:'center'},
				    {field:'valCode',title:'值编码',width:100,align:'center'},
				    {field:'masterCode',title:'代码编号',width:260,align:'center'},
				    {field:'remark',title:'代码描述',width:390,align:'center'},
				    {field:'sortNum',title:'显示顺序',width:100,align:'center'},
				    {field:'useFlag',title:'启用标识',width:100,align:'center'}
				]],
				view : $.orzView,
				toolbar:[{
					id:'btnadd',
					text:'新增',
					iconCls:'icon-add',
					handler:function(){addMasterValByType();}  
				},'-',{
					id:'btnedit',
					text:'编辑',
					iconCls:'icon-edit',
					handler:function(){editMasterValByType();}  
				},'-',{
					id:'delete',
					text:'删除',
					iconCls:'icon-delete',
					handler:delMasterValByType }],
				view : $.orzView,
				onClickRow: function(rowNum,record){
				},
				onDblClickRow: function(rowIndex, rowData){
					editMasterValByType();
				}
			});
		}  
	
		/// <summary>初始化新增或编辑码表弹出框方法</summary>
		/// <param name="flag">判断是新增还是编辑的标识，add新增码表，edit编辑码表</param>
		function clickMenuDetailToolButtonsOfHead(flag){
			var _title = "";
			var nodes = $('#mastTree').tree('getSelected');	
			if(flag == 'add'){				
				pCode = nodes.text;
				_title='新增码表信息';
				initDialog(_title);
				$("#editMast_form").form("clear");// 重置表单数据
				$("#editMast_form #txtmasterCode").attr("disabled", false) ;; 
				$('#pid').val(nodes.id);
				$('#pidName').val(nodes.text);
				$("#editMast_form #cmbuseFlag").combobox("setValue", "");	
				$('#txtpid').combobox({disabled:true,editable:false});
			}
			if(flag == 'edit'){
				_title='编辑码表信息';				
				var row = $('#dg').datagrid('getSelected'); //mastDetailGrid.getSelectedId();
				if(row=="" || row==null){
					alert('请选择一条数据进行编辑');return;}
				    initDialog(_title);
					$('#pidName').val(nodes.text);
					$("#editMast_form #txtmasterCode").attr("disabled", true);					 			
				     getMastInfoById(row.id,nodes.id);
			  }			
			}

		///<summary>加载弹出框方法</summary>
		/// <param name="_title">弹出窗的标题</param>
		function initDialog(_title){
			$('#editMastDiv')[0].style.display='block';
			$('#editMastDiv').dialog({    
			    title: _title,    
			    width: 600,    
			    height: 370,    
			    closed: false,    
			    cache: false,        
			    modal: true,
			    toolbar:[{
					text:'保存',
					iconCls:'icon-save',
					handler:addMastType
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){$('#editMastDiv').window('close');}
				}]
			  });
		}
		///<summary>初始化上级代码方法</summary>
		/// <param name="id">码表id</param>
		/// <param name="lab"></param>
		/// <param name="value">上级代码值</param>
		function loadParentComlist(id,lab,value){
			var url = BASE_PATH + "control/master/getParentComList.action?etc="+ new Date().getTime();
			var param = "";
			$.ajax({
				type: "POST",
				cache: false,
				async: false,
				url: url,
				data: param,
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				dateType: "json",
				success: function (data){
			    	    var comData = new Array();
						var tit = "--请选择"+lab+"--";
						comData[0] = {"id" : "0","text" : tit};
						var num = 1;
						if(data!=null && data.length>0){
							$.each(data,function(entryIndex,entry){
								if(entry){
									//判断entry，可以避免检索码表结果中出现为id和text为null的情况（此情况下，entry的size比实际大1个）
									//$("#"+inputId).append("<option value='"+ entry['id'] +"'>"+ entry['text'] +"</option>");
									comData[num] = entry;
									num = num + 1;
								}
			   				 });  
						}
						var str = "#"+id;		
						$(str).combobox('loadData',comData);
						$(str).combobox('setValue',value);
				   		}
				   });	
			}
			
		/// <summary>执行码表新增</summary>
		function addMastType(){
			var masterCode = $("#editMast_form #txtmasterCode").val();
			if(masterCode==''){
				alert('代码编号不能为空');return;
			}
			var useFlag = $("#cmbuseFlag").combobox("getValue");
			if (useFlag == null || useFlag.length == 0) {
		    	alert("请选择启用标识");
		    	$("#cmbuseFlag").focus();
		    	return;
		    }
		    	var temp  = $("#editMast_form").serialize();
			        $.ajax({ 
			        url: "<%=basePath %>control/master/addMastTypeInfo.action?&etc="+new Date().getTime() ,
					cache: false,
			 		async: true,
					type:"post",
					data:$("#editMast_form").serialize(),
					dataType:"json",
					success: function(data){            
			        	alert(data.resultValue);
			        	if(data.resultValue.indexOf("成功")!=-1){
			        	$('#editMastDiv').window('close');
			        	 flag=true;
			        	 initBaseTree();
				    	 initSmallTree();
						}
			  		}});
		}
		
		///<summary>依据码表id获取码表基本信息方法</summary>
		/// <param name="id">码表Form每一行的ID</param>
		function getMastInfoById(id,_pid) {
			id=encodeURIComponent(encodeURIComponent(id));
			var url = BASE_PATH + "control/master/getMastInfoById.action?id=" + id + "&etc=" + new Date().getTime();
			$.ajax({
		        type: "POST",
		        url: url,
		        async:false,
		        dataType: "json",
		        success: function (data) {
					$("#editMast_form").form("load", data);
					$('#pid').val(_pid);
				}
			}); 
		}
		
		/// <summary>增加码表值</summary>
		function addMasterValByType(){
			typeId=$('#mastTree').tree('getSelected').id;
			masterCode = $('#valMasterCode').val();
			initvalPau("新增码表值");
			$("#modifyApply_form").form("clear");
			$("#modifyApply_form #txtvalCode").attr('disabled',false) ;
			$("#modifyApply_form #txttypeId").val(typeId);
			$("#modifyApply_form #txtmasterCode2").val(masterCode);
			$("#modifyApply_form #cmbuseFlag2").combobox("setValue", "");
			$("#modifyApply_form").form("validate");
				
		}
		
		/// <summary>编辑码表值</summary>
		function editMasterValByType(){
			var row = $('#dg2').datagrid('getSelected'); //mastDetailGrid.getSelectedId();
				if(row=="" || row==null){
					alert("请选择一条码表值数据进行编辑");return;
				}
				initvalPau("编辑码表值");
				$("#modifyApply_form").form("clear");// 重置表单数据
				getMastValInfoById(row.id);// 加载表单数据
				$("#modifyApply_form").form("validate");
			
		}
		
		/// <summary>删除码表值</summary>
		function delMasterValByType(){
			var node = $('#mastTree').tree('getSelected');
			var row = $("#dg2").datagrid("getSelected"); //mastDetailGrid.getSelectedId();
				if(row=="" || row==null){
					alert("请选择一条码表值数据进行删除");return;
				}
				if (!window.confirm("删除后无法恢复，确认删除吗？"))
						return;
				var url = BASE_PATH
					+ "control/master/deleteMastVal.action?id="+row.id+"&etc=" + new Date().getTime();
				$.ajax({
					url: url,
					cache: false,
					async: false,
					type: "post",
					dataType:"json",
					success: function(data){   
						alert(data.resultValue);
						if (data.successful) {				
							 $("#dg2").datagrid("load");
						}
					},
					error: function(request) {
		            	alert("删除失败!请刷新页面重新删除!");
		            	return;
		            }
				});
		}
		
		///<summary>加载编辑码表值窗口方法</summary>
		/// <param name="_title">弹出窗的标题</param>
		function initvalPau(_title){
			$('#editModifyApply_form_container')[0].style.display='block';
			$('#editModifyApply_form_container').dialog({    
			    title: _title,    
			    width: 500,    
			    height: 300,    
			    closed: false,    
			    cache: false,        
			    modal: true,
			    toolbar:[{
					text:'保存',
					iconCls:'icon-save',
					handler:addOrUpdateMastValInfo
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){$('#editModifyApply_form_container').window('close');}
				}]
			  });  
		}
		/// <summary>获取码表值基本信息 用于Form表单各项值自动匹配回填</summary>
		/// <param name="id">码表值Form每一行的ID</param>
		function getMastValInfoById(id){
			id=encodeURIComponent(encodeURIComponent(id));
			var url = BASE_PATH + "control/master/getMastValInfoById.action?id=" + id + "&etc=" + new Date().getTime();
			$.ajax({
		        type: "POST",
		        url: url,
		        async:false,
		        dataType: "json",
		        success: function (data) {
					$("#modifyApply_form").form("load", data);
				}
			});
		}
		/// <summary>执行修改 新增或修改码表值信息 </summary>
		function addOrUpdateMastValInfo() {
			var sortNum=$("#txtsortNum").val();
			$("#txtsortNum").numberbox("setValue", sortNum);
			var useFlag = $("#cmbuseFlag2").combobox("getValue");
			if (useFlag == null || useFlag.length == 0) {
		    	alert("请选择启用标识");
		    	$("#cmbuseFlag2").focus();
		    	return;
		    }
			if(!$("#modifyApply_form").form("validate")) return;
					$.ajax({ 
						url: "<%=basePath %>control/master/updateMastValInfo.action?etc="+new Date().getTime() ,
				    	cache: false,
				     	async: true,
						type:"post",
						data:$("#modifyApply_form").serialize(),
						dataType:"json",
						success: function(data){     
							if(data.successful){							
								alert(data.resultValue);
								if(data.resultValue.indexOf("成功")!=-1){
									$('#editModifyApply_form_container').window('close');
									//changeStatu2(selectTreeNode);
									$('#dg2').datagrid('reload');
								}
							}else{
								alert(data.resultValue);
							}
					    }
					});
		}
  </script>
  <body class = "easyui-layout" style="margin: 2px 2px 2px 2px" >
    <div data-options="region:'west',split:true" title="码表管理" style="width:250px;">
		<ul  class="easyui-tree" id="mastTree" style="margin: 10px 10px 5px 10px"></ul>
	</div>
	
	<div data-options="region:'center',split:true" style="width:1000px;">
    	<div id = 'layoutRight' class = "easyui-layout" data-options="fit:true">
   <div id = 'searchbox' data-options="region:'north',title:'码表查询',split:true" style="height:65px;"> 
 	<div id="condition_container" style="height:100%; width:100%;text-align:center;background-color:#EDF4FA">
			<table id="conmmTable1" border="0" align="left" cellpadding="0" cellspacing="0" class="tb_side" style="margin-top:3px;">
				<tr>
					<td nowrap="nowrap" width="35px"></td>
					<td align="left" style="padding-left: 12px; font-size:12px">
						代码编号：<input type="text" align="left" class="search_txtbox_input" id="txtMasterCode" name="master_Code" />
					</td>
					<td align="left" style="padding-left: 12px; font-size:12px">
						代码名称：<input type="text" align="left" class="search_txtbox_input" id="txtMasterName" name="master_Name" />
					</td>
					
					<td align="left" style="padding-left: 12px; font-size:12px">
						启用标识：	<select id="cmbxUseFlag" name="use_Flag" class="easyui-combobox" style="width: 150px;" panelHeight="70">
									<option value="">---请选择---	</option>
									<option value="1">使用		</option>
									<option value="0">不使用		</option>
								</select>
					</td>					
					<td nowrap="nowrap" width="40px"></td>
					<td align="right">
					<a id = 'btnn' href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMastTypeInfo();">查询</a>
					</td>
				</tr>
			</table>
		</div>
    	    </div>
    	    <div data-options="region:'center'" id='dpd'>
    	    	<div id="div_dg">   <!--  class="easyui-panel"   style="fit:true;border:false" -->
    	    	    <table id="dg"></table>
    	    	</div>
    	    	<div id="div_dg2" class="div_infoDisplay" style="display: none;margin-top: 0px" align="center">
    	    		<table border="0" style="width: 100%">	
						<tr>
						   <td class = 'form_td_label' align="right" style="width: 10%">代码编号：</td>
						   <td class = 'td_labelnext'  align="left" style="width: 40%">
						       &nbsp;<input id = 'valMasterCode' type="text" class="search_txtbox_input" style="width: 90%"/>
						   </td>
						   <td class = 'form_td_label'  align="right" style="width: 10%">代码名称：</td>
						   <td class = 'td_labelnext' align="left" style="width: 40%">
						      &nbsp;<input id = 'valMasterName' type="text"  class="search_txtbox_input" style="width: 90%" readonly="readonly"/>
						   </td>
						</tr>
						
						<tr>
						   <td class = 'form_td_label'  align="right" style="width: 10%">代码描述：</td>
						   <td class = 'td_labelnext' align="left" style="width: 40%">
						        &nbsp;<input id = 'valMasterRemark' type="text"  class="search_txtbox_input" style="width: 90%" readonly="readonly"/>
						   </td>
						   <td class = 'form_td_label'  align="right" style="width: 10%">代码值：</td>
						   <td class = 'td_labelnext' align="left" style="width: 40%">
						        &nbsp;<input id = 'valMasterVal' type="text"  class="search_txtbox_input" style="width: 90%" readonly="readonly"/>  
						   </td>
						</tr>
						
						<tr>
						   <td class = 'form_td_label'  align="right" style="width: 10%">上级代码：</td>
						   <td class = 'td_labelnext' align="left" style="width: 40%">
						         &nbsp;<input id = 'valMasterPid' type="text"  class="search_txtbox_input" style="width: 90%" readonly="readonly"/>
						   </td>
						   <td class = 'form_td_label'  align="right" style="width: 10%">启用标识：</td>
						   <td class = 'td_labelnext' align="left" style="width: 40%">
						       &nbsp;<input id = 'valMasterUseFlag' type="text"  class="search_txtbox_input" style="width: 90%" readonly="readonly"/>
						   </td>
						</tr>
    	    		</table>
    	    	</div>
    	    	<div id="divMastVal" style="padding-top: 0px">
    	    		<table id="dg2" style="height: 100%; width: 100%"></table>
    	    	</div>
    	    </div>
    	</div>
    </div>
    <div id="editMastDiv"  class="div_infoDisplay" style="display: none"><br/>
			<form action="" id="editMast_form" name="editMast_form" method="post">
				<input type="hidden" id="id" name="id" value=""/>
				<input type="hidden" id="masterCode" name="masterCode" value=""/>
					<table align="center" >
						<tr>
							<td class="form_td_label" style="width:140px;">代码编号：</td>
							<td class="form_td_input">
								<input type="text"  class="form_txtbox_input easyui-validatebox" required="true"
								id="txtmasterCode" name="masterCode"  value=""/>
							</td>
						</tr>
						
						<tr>
							<td class="form_td_label">代码名称：</td>
							<td class="form_td_input">
								<input type="text" class="form_txtbox_input easyui-validatebox"
								id="txtmasterName" name="masterName"  />
							</td>
						</tr>
						<tr>
							<td class="form_td_label">代码值：</td>
							<td class="form_td_input">
								<input type="text" class="form_txtbox_input easyui-validatebox"
								id="txtmasterVal" name="masterVal"  />
							</td>
						</tr>
						<tr>
							<td class="form_td_label">代码描述：</td>
							<td class="form_td_label_area">
								<textarea class="txtarea" rows="3" id="txtaremark"  name="remark" style="width: 400px;"></textarea>
							</td>
						</tr>
	
						<tr>
							<td class="form_td_label">上级代码：</td>
							<td class="form_td_input">
								<input type="text" class="form_txtbox_input easyui-validatebox"	id="pidName" name="pidName" disabled="disabled"/>
								<input type="hidden" class = "form_txtbox_input easyui-validatebox" id="pid" name="pid">
							</td>
						</tr>
	
						<tr>
							<td class="form_td_label">节点类型：</td>
							<td class="form_td_input"><input type="text"  class="form_txtbox_input easyui-validatebox" 
								id="txtNdType" name="ndType" />
							</td>
						</tr>
						
						<tr>
							<td class="form_td_label">启用标识：</td>
							<td class="form_td_input" >
								<input class="easyui-combobox"  id="cmbuseFlag" name="useFlag" data-options="valueField:'id',textField:'text',panelHeight:150" style="width: 400px;"/>
							</td>
						</tr>
						<tr>
							<td class="form_td_label">显示顺序：</td>
							<td colspan="3" class="form_td_input">
								<input type="text"   id="txtsortNum" name="sortNum"  class="form_txtbox_input easyui-numberbox"
								required="true" data-options="max:9999.99,min:0,precision:0" />
								<font color="red" style="font-size: 12px;">(只能输入数字)</font>
							</td>
						</tr>
				</table>				
			</form>
		</div>
    	
		<div id="editModifyApply_form_container" class="div_infoDisplay"  style="display: none"><br/>
			<form id="modifyApply_form" name="modifyApply_form" class="form" action="" method="post">
				<input type="hidden" id="txtid2" name="id" value=""/>
				<input type="hidden" id="txttypeId" name="typeId" value=""/>
				
				<table align="center">
					<tr>
						<td class="form_td_label">代码编号：</td>
						<td class="form_td_input" align="left">
							<input type="text" style="width:240px;color:#888" class="search_txtbox_input easyui-validatebox"
							id="txtmasterCode2" name="masterCode" value="" readonly/> 
						</td>
					</tr>
					
					<tr>
						<td class="form_td_label">启用标识：</td>
						<td class="form_td_input" align="left">
							<input class="easyui-combobox"  id="cmbuseFlag2" name="useFlag" data-options="valueField:'id',textField:'text',panelHeight:150" style="width: 244px;"/>
						</td>
					</tr>
					<tr>
					
						<td class="form_td_label">值名称：</td>
						<td  class="form_td_input" align="left">
							<input type="text" style="width:240px;" class="search_txtbox_input easyui-validatebox" 
							id="txtvalName" name="valName" />
						</td>
					</tr>
					<tr>
						<td class="form_td_label">值编码：</td>
						<td class="form_td_input" align="left">
							<input type="text" style="width:240px;" class="search_txtbox_input easyui-validatebox"
							id="txtvalCode" name="valCode" value=""  />
						</td>
					</tr>
					<tr>
						<td class="form_td_label">显示顺序：</td>
						<td colspan="3" class="form_td_input" align="left">
							<input type="text"   id="txtsortNum" name="sortNum"  class="search_txtbox_input easyui-numberbox"
							style="width: 240px;"  required="true" data-options="max:9999.99,min:0,precision:0" value=""/>
							<font color="red" style="font-size: 12px;">(只能输入数字)</font>
						</td>
					</tr>
					<tr>
						<td class="form_td_label">描述：</td>
						<td class="form_td_input" align="left">
							<textarea class="txtarea" rows="2" id="txtremark2"  name="remark" style="width: 240px;"></textarea>
						</td>
					</tr>					
				</table>
			</form>
		</div>
  </body>
</html>
