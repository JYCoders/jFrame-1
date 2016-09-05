$(function(){
	$('#tt2').datagrid({
		title:'My Title',
		iconCls:'icon-save',
		width:600,
		height:350,
		nowrap: false,
		striped: true,
		fit: true,
		url:'datagrid_data.json',
		sortName: 'code',
		sortOrder: 'desc',
		idField:'code',
		frozenColumns:[[
               {field:'ck',checkbox:true},
               {title:'code',field:'code',width:80,sortable:true}
		]],
		columns:[[
	        {title:'Base Information',colspan:3},
			{field:'opt',title:'Operation',width:100,align:'center', rowspan:2,
				formatter:function(value,rec){
					return '<span style="color:red">Edit Delete</span>';
				}
			}
		],[
			{field:'name',title:'Name',width:120},
			{field:'addr',title:'Address',width:120,rowspan:2,sortable:true},
			{field:'col4',title:'Col41',width:150,rowspan:2}
		]],
		pagination:true,
		rownumbers:true
	});
});
		
function showeditpaper(){
	$('#win').window({  
    	width:900,  
		height:600, 
		maximized:true,
		minimizable:false,
		collapsible:false,
		modal:true  
  	});  
}
function showreadpaper(){
	$('#winread').window({  
	  width:900,  
	  height:600, 
	  maximized:true,
	  minimizable:false,
	  collapsible:false,
	  modal:true  
	});  
}	

function CloseWin() //这个不会提示是否关闭浏览器   
{   
	window.opener=null;   
	//window.opener=top;   
	window.open("","_self");   
	window.close();   
}
 
function sysback(){
	window.location.href="login_exit.do";
}

function goto(opt){
	var v_title=$(opt).attr("title");
	var v_url=$(opt).attr("url");
	var v_iconCls=$(opt).attr("iconCls");
	addTab($("#maintabs"),{title:v_title,href:v_url,iconCls:v_iconCls});
}

function addTab(opt, val) {
	var tt = opt;
	var title = val.title;
	var v_iconCls=val.iconCls;
	var href = val.href;
	if (tt.tabs("exists", title)) {
		tt.tabs("select", title);
	} else {
		if (href) {
			var content = "<iframe scrolling=\"no\" frameborder=\"0\"  src=\"" + href + "\" style=\"width:100%;height:100%;padding:0px;\"></iframe>";
		} else {
		    
			var content =val.content?val.content:"\u672a\u5b9e\u73b0";
		} 
		var v_closable=(val.closable==undefined)?true:val.closable;
		tt.tabs("add", {title:title,closable:v_closable,content:content,iconCls:v_iconCls}); 
	}
}

function getDate() {
	var day="";
	var month="";
	var ampm="";
	var ampmhour="";
	var myweekday="";
	var year="";
	mydate=new Date();
	myweekday=mydate.getDay();
	mymonth=mydate.getMonth()+1;
	myday= mydate.getDate();
	year= mydate.getFullYear();
	if(myweekday == 0)
	weekday=" 星期日 ";
	else if(myweekday == 1)
	weekday=" 星期一 ";
	else if(myweekday == 2)
	weekday=" 星期二 ";
	else if(myweekday == 3)
	weekday=" 星期三 ";
	else if(myweekday == 4)
	weekday=" 星期四 ";
	else if(myweekday == 5)
	weekday=" 星期五 ";
	else if(myweekday == 6)
	weekday=" 星期六 ";
	return year+"年"+mymonth+"月"+myday+"日 "+weekday;
}

function getTime() {
    mydate=new Date();
	var year=mydate.getFullYear();
	var month=mydate.getMonth()+1;
	var day=mydate.getDate();
	var hh=mydate.getHours();
	var mm=mydate.getMinutes();
	var ss=mydate.getSeconds();	
	if(mm.toString().length == 1){
		mm = "0" + mm
	}
	if(ss.toString().length == 1){
		ss = "0" + ss
	}
	return year+"年"+month+"月"+day+"日 "+hh+":"+mm+":"+ss;
}

function getNowTime() {
    mydate=new Date();
	var year=mydate.getFullYear();
	var month=mydate.getMonth()+1;
	var day=mydate.getDate();
	var hh=mydate.getHours();
	var mm=mydate.getMinutes();
	var ss=mydate.getSeconds();	
	if(month.toString().length == 1){
		month = "0" + month
	}
	if(day.toString().length == 1){
		day = "0" + day
	}
	if(hh.toString().length == 1){
		hh = "0" + hh
	}
	if(mm.toString().length == 1){
		mm = "0" + mm
	}
	if(ss.toString().length == 1){
		ss = "0" + ss
	}
	return year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss;
}

String.prototype.trim = function()
{
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}
String.prototype.lTrim = function()
{
    return this.replace(/(^[\s]*)/g, "");
}
String.prototype.rTrim = function()
{
    return this.replace(/([\s]*$)/g, "");
}

function isNumber(content)        
{    
   
	var NUM = content;  
	
 	var i,j,strTemp;        
 	strTemp=".0123456789";  
 	if ( NUM.length==0)        
 		return false;    
 	for(i=0;i<NUM.length;i++)   
 	{        
  		j=strTemp.indexOf(NUM.charAt(i));        
  		if (j==-1)        
  		{    
   			return false;        
  		}      
 	}       
 	j=strTemp.indexOf(NUM.charAt(0));   
 	if(j==0){         
  		return false;   
 	}
 	return true;        
}

function clearForm(formObj){
    for(var i=0; i<formObj.elements.length; i++)   
    {
        if(formObj.elements[i].type == "text")   
        {
            formObj.elements[i].value = "";   
        }
        
        if(formObj.elements[i].type == "hidden")
        {
            formObj.elements[i].value = "";   
        }
        
        else if(formObj.elements[i].type == "password")   
        {
            formObj.elements[i].value = "";   
        }   
        else if(formObj.elements[i].type == "radio")   
        {
            formObj.elements[i].checked = false;   
        }   
        else if(formObj.elements[i].type == "checkbox")   
        {
            formObj.elements[i].checked = false;   
        }   
        else if(formObj.elements[i].type == "select-one")   
        {   
            if(formObj.elements[i].options.length > 0){
                formObj.elements[i].options[0].selected = true;  
            } 
        }   
        else if(formObj.elements[i].type == "select-multiple")   
        {      
            for(var j = 0; j < formObj.elements[i].options.length; j++)   
            {   
                formObj.elements[i].options[j].selected = false;   
            }   
        }   
        else if(formObj.elements[i].type == "file")   
        {   
            //formObj.elements[i].select();   
            //document.selection.clear();              
            // for IE, Opera, Safari, Chrome   
            var file = formObj.elements[i];   
             if (file.outerHTML) {   
                 file.outerHTML = file.outerHTML;   
             } else {   
                 file.value = "";  // FF(包括3.5)   
            }   
        }   
        else if(formObj.elements[i].type == "textarea")   
        {   
            formObj.elements[i].value = "";   
        }   
    }    
}

function illegalChar(str){
    var pattern=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
    if(pattern.test(str)){
    	return false; 
    }
    return true;
}

/// <summary>添加ajax公共事件处理</summary>
$(document).ready(function(){   
	/// <summary>ajax异常捕获</summary>
	$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {  	 
	   	$.messager.defaults={ok:"确定"};
	   	var errMsg = jqXHR.responseText;
	   	if(errMsg == null || errMsg == '') {
	   		errMsg = '服务器无响应！';
	   	}
    	$.messager.alert('异常消息', errMsg, 'error');
  	});
	
	/// <summary>ajax开启logging等待窗口</summary>
	$(document).ajaxSend(function(event) {
	   	//logging_show();//关闭logging等待窗口
  	});
	
	/// <summary>ajax关闭logging等待窗口</summary>
	$(document).ajaxComplete(function(event, jqXHR, ajaxSettings) {  	 
	   	//logging_hidden();//关闭logging等待窗口
  	});
});

/// <summary>datagrid 选择方式</summary>
/// <param name="dg">datagrid对象</param>
/// <param name="rowSelect">是否点击行选中，true-是，false-否</param>
function datagrid_select(dg, rowSelect) {
	if(rowSelect == null || rowSelect != false) {
		rowSelect = true;
	}
	var panel = dg.datagrid('getPanel');
    var rows = panel.find('tr[datagrid-row-index]');
    
    // 是否点击行选中=false-否时，重新绑定click事件
    if(!rowSelect) {
    	rows.unbind('click').bind('click',function(e){
    		var index = $(this).attr('datagrid-row-index');
    		var cks = rows.find('div.datagrid-cell-check input[type=checkbox]');
    		var ck = cks[index];
    		
    		datagrid_onClickRow(dg, index);//选中的全部去掉
    		
    		if($(ck).attr('checked')) {
    			datagrid_onUnselect(dg, index);// 调用按钮取消选择事件
    		} else {
    			datagrid_onSelect(dg, index);// 调用按钮选择事件
    		}
    		
    		//原先的代码，2013-01-28注释
    		//if($(ck).attr('checked')) {
    		//	dg.datagrid('unselectRow', index);
    		//} else {
    		//	dg.datagrid('selectRow', index);
    		//}
    		
        	//return false;
    	});
    } else {
    	rows.unbind('click').bind('click',function(e){
    		var index = $(this).attr('datagrid-row-index');
    		var cks = rows.find('div.datagrid-cell-check input[type=checkbox]');
    		var ck = cks[index];
    		if($(ck).attr('checked')) {
    			datagrid_onUnselect(dg, index);// 调用按钮取消选择事件
    		} else {
    			datagrid_onSelect(dg, index);// 调用按钮选择事件
    		}
    	});
    }
                  
    // 重新绑定checkbox的click事件
    rows.find('div.datagrid-cell-check input[type=checkbox]').unbind().bind('click', function(e){
    	var index = $(this).parent().parent().parent().attr('datagrid-row-index');
        if ($(this).attr('checked')){
            dg.datagrid('selectRow', index);
            datagrid_onSelect(dg, index);// 调用按钮选择事件
        } else {
            dg.datagrid('unselectRow', index);
            datagrid_onUnselect(dg, index);// 调用按钮取消选择事件
        }
        e.stopPropagation();
    });
}

/// <summary>datagrid onSelect事件</summary>
/// <param name="dg">datagrid对象</param>
function datagrid_onSelect(dg, index) {
	if(dg.datagrid('options').singleSelect) {
		return; // 单选状态，不控制全选按钮
	}
	// 全选按钮对象获取
	var headerCk = dg.parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0);
	//判断是否全部选中
	var panel = dg.datagrid('getPanel');
	var rows = panel.find('tr[datagrid-row-index]');
	var cks = rows.find('div.datagrid-cell-check input[type=checkbox]');
	var allSelect = true;//默认行全部选中
	for(var i=0; i < cks.length; i++) {
		if(i == index) {
			continue;
		}
		var ck = cks[i];
		if(!$(ck).attr('checked')) {
			allSelect = false;
			break;
		}
	}
	
	// 控制全选按钮状态
	if(allSelect) {
		// 行全部选中,全选按钮选中
		if(!headerCk.attr("checked")) {
			headerCk.attr("checked", true);
		}
	} else {
		// 行没有全部选中，全选按钮取消
		if(headerCk.attr("checked")) {
			headerCk.attr("checked", false);
		}
	}
}

/// <summary>datagrid onUnselect事件</summary>
/// <param name="dg">datagrid对象</param>
function datagrid_onUnselect(dg, index) {
	if(dg.datagrid('options').singleSelect) {
		return; // 单选状态，不控制全选按钮
	}
	// 全选按钮对象获取
	var headerCk = dg.parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0);
	if(headerCk.attr("checked")) {
		headerCk.attr("checked", false);
	}
}

/// <summary>datagrid_onClickRow事件,多选模式下，点击一行，其他行的选中状态去掉</summary>
/// <param name="dg">datagrid对象</param>
function datagrid_onClickRow(dg, index) {
	if(dg.datagrid('options').singleSelect) {
		return; // 单选状态，不控制全选按钮
	}
	
	//清除所有的选择
	dg.datagrid('clearSelections');

	//var panel = dg.datagrid('getPanel');
	//var rows = panel.find('tr[datagrid-row-index]');
	//var cks = rows.find('div.datagrid-cell-check input[type=checkbox]');
	//for(var i=0; i < cks.length; i++) {
		//if(i == index) {
		//	continue;
		//}
	//	var ck = cks[i];
	//	if($(ck).attr('checked')) {
	//		dg.datagrid('unselectRow', i);
	//	}
	//}
}

function isImage(fileName) {
	var type = fileName.substring(fileName.lastIndexOf("."));
	if(type == ".jpg" || type == ".JPG" ||
	   type == ".png" || type == ".PNG" ||
	   type == ".gif" || type == ".GIF" ||
	   type == ".jepg" || type == ".JEPG" ||
	   type == ".ico" || type == ".ICO" ||
	   type == ".bmp" || type == ".BMP") {
		return true;
	}
	return false;
}