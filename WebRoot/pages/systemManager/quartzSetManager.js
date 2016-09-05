var str_ss = '起始秒：<input id="qs_ss" value="0" type="text" style="width: 30px"/>秒';
var str_min = '起始分钟：<input id="qs_min" value="0" type="text" style="width: 30px"/>分'
					+'<input id="qs_min_ss" value="0" type="text" style="width: 30px"/>秒';
var str_hh = '起始小时：<input id="qs_hh" value="0" type="text" style="width: 30px"/>时'
				+'<input id="qs_hh_min" value="0" type="text" style="width: 30px"/>分'
				+'<input id="qs_hh_min_ss" value="0" type="text" style="width: 30px"/>秒';
var str_dd = '起始日：<input id="qs_dd" value="1" type="text" style="width: 30px"/>号'
					+'<input id="qs_dd_hh" value="0" type="text" style="width: 30px"/>时'
					+'<input id="qs_dd_hh_min" value="0" type="text" style="width: 30px"/>分'
					+'<input id="qs_dd_hh_min_ss" value="0" type="text" style="width: 30px"/>秒';
			
var BASE_PATH;
$(function(){
	BASE_PATH = $("#hid_basePath").val();
	getFS1FS2();
});

function toolBarClick1(id){
	switch(id){
		case "btn_save" :
			var easyuiValidata = $("#frmBasicInfo").form('validate');
			if(!easyuiValidata){
				return;
			}
			var v = creatBDS();
			if(v){
				saveBasicInfo();
			}
			break;
	}
}
function toolBarClick(id){
	switch(id){
		case "btn_add" :
			initPauWin();
			break;
		case "btn_edit" :
			openEditWin();
			break;
		case "btn_delete" :
			deleteRelByRelId();
			break;
	}
}
//详细信息设置中，点击 “删除”按钮，删除一行
function deleteRelByRelId(){
	var relId = mGrid.getSelectedRowId();
	if(relId == null || relId == ""){
		alert("请选择要删除的行！");
		return;
	}
	var v = confirm("确定要删除：选中的行?");
	if(v){
		$.ajax({
	        type: 'POST',
	        url: BASE_PATH + "control/flowinterface/deleteRelById.actioninstId="+param_instId+"&intfId="+intfId+"&relId="+id+"&sysId="+selectedSysId+"&etc="+new Date().getTime(),
	        dataType: 'json',
	        data: {},
	        success: function (data) {
	        	if(data.indexOf("成功")>= 0){
	        		mGrid.deleteRow(relId);
	        	}else{
	        		alert(data);
	        	}
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            alert("请求发送失败！");
	        }
	    });
	}
}
var editWin;
function openEditWin(){
	var id = mGrid.getSelectedRowId();
	if(id == null || id == ""){
		alert("请选择要编辑的行！");
		return;
	}
	var intfId = $("#hid_intfId").val();
	var index = mGrid.getRowIndex(id);
	editWin=dhxLayout.dhxWins.createWindow("editWin", 20, 200, (document.body.clientWidth-40), 450);
	editWin.setText("编辑接口与系统表关系");
	editWin.button("close").attachEvent("onClick",function(){
		closeEditWin();
	});
	editWin.button("minmax1").hide();
	editWin.button("park").hide();
	
	var url= BASE_PATH + "control/flowinterface/toEditRel.action?instId="+param_instId+"&intfId="+intfId+"&relId="+id+"&sysId="+selectedSysId+"&etc="+new Date().getTime();		
	w = editWin.attachURL(url);
	
	editWin.setModal(true);  
	editWin.hide("close");
	editWin.show();
}

function closeEditWin(){
	editWin.setModal(false);
	editWin.hide();
}
function refreshGrid(){
	mGrid.clearAll();
	var intfId =$("#hid_intfId").val() ;
	var _url= BASE_PATH + "control/flowinterface/selIntfRel?instId="+param_instId+"&intfId="+intfId+"&etc="+new Date().getTime();
	mGrid.clearAndLoad(_url, "json");
	closeEditWin();
}
//初始化供数系统或者用数系统弹出框，提供系统选择功能
var pauWin;//provider_and_user_win 弹出框
function initPauWin(){ 
	var intfId = $("#hid_intfId").val();
	if(intfId == "" || intfId == null){
		alert("没有获取到接口ID，请先保存接口基本信息！");
		return;
	}
	pauWin=dhxLayout.dhxWins.createWindow("pauWin", 20, 200, (document.body.clientWidth-40), 450);
	pauWin.setText("设置接口与系统表关系");
	pauWin.button("close").attachEvent("onClick",function(){
		closePauWin();
	});
	pauWin.button("minmax1").hide();
	pauWin.button("park").hide();
	
	var url= BASE_PATH + "control/flowinterface/toAddRel.action?instId="+param_instId+"&intfId="+intfId+"&sysId="+selectedSysId+"&etc="+new Date().getTime();		
	sysTree = pauWin.attachURL(url);
	
	pauWin.setModal(true);  
	pauWin.hide("close");
	pauWin.show();
}

function closePauWin(){
	pauWin.setModal(false);
	pauWin.hide();
}

//保存接口基本信息
function saveBasicInfo(){
	ajaxSubmit("frmBasicInfo", function(data){
		var res = data//.replace( /^\s+|\s+$/g, "" );
		if(res.indexOf("失败") < 0){
			$("#hid_intfId").val(res);
			alert("保存成功！");
		}else{
			alert(res);
		}
	},function(arg1,arg2,arg3){
		alert("请求处理错误！");
	})
}

//调度时间设置部分，
function changeDivPlDetail(){
	var v = $("#select_pl").val();
	switch(v){
		case "s":
			$("#div_pl_Detail").html(str_ss);
			break;
		case "min":
			$("#div_pl_Detail").html(str_min);
			break;
		case "h":
			$("#div_pl_Detail").html(str_hh);
			break;
		case "d":
			$("#div_pl_Detail").html(str_dd);
			break;
	}
}

//时，分，秒，日生成quartz表达式
function creatDescription1(){
	var num = $("#input_num").val();
	var pl = $("#select_pl").val();
	
	var bds;//表达式
	var bds_hy;//表达式含义
	if(num == null || num == ""){
		alert("请设置 执行频率");
		return false;
	}
	switch(pl){
		case "s":
			var ss = $("#qs_ss").val();
			bds = ss + "/"+num+" * * * * ?";
			bds_hy = "每分钟的第"+ss+"秒开始，每"+num+"秒执行一次。";
			break;
		case "min":
			var min = $("#qs_min").val();
			var min_ss = $("#qs_min_ss").val();
			bds = min_ss + " "+min+"/"+num+" * * * ?";
			bds_hy = "每小时的第"+min+"分"+min_ss+"秒开始，每"+num+"分钟执行一次。";
			
			break;
		case "h":
			var hh = $("#qs_hh").val();
			var hh_min = $("#qs_hh_min").val();
			var hh_min_ss = $("#qs_hh_min_ss").val();
			bds = hh_min_ss + " "+hh_min+" "+hh+"/"+num+" * * ?";
			bds_hy = "每天的第"+hh+"时"+hh_min+"分"+hh_min_ss+"秒开始，每"+num+"小时执行一次。";
			
			break;
		case "d":
			var dd = $("#qs_dd").val();
			var dd_hh = $("#qs_dd_hh").val();
			var dd_hh_min = $("#qs_dd_hh_min").val();
			var dd_hh_min_ss = $("#qs_dd_hh_min_ss").val();
			bds = dd_hh_min_ss + " "+ dd_hh_min + " "+dd_hh + " "+dd+"/"+num+" * ?";
			bds_hy = "每月的"+dd+"号"+dd_hh+"时"+dd_hh_min+"分"+dd_hh_min_ss+"秒开始，每"+num+"天执行一次。";
			
			break;
	}
	$("#span_ms2").html(bds_hy);
	$("#span_ms1").html(bds);
	$("#hid_timeValName").val(bds_hy);
	$("#hid_timeVal").val(bds);
	$("#txtCronExpression").val(bds);
	$("#txtaCtiveTimeName").val(bds_hy);
	return true;
}

function getYear(startDate,endDate){
	if(endDate == null || endDate == ""){
		return "";
	}
	var start = startDate.substr(0,4);
	var end = endDate.substr(0,4);
	if(start == end){
		return start;
	}else{
		return start+"-"+end;
	}
}
//方式2生成表达式
function creatDescription2(){
	var allMonth = $("input[name='checkAllMonth']");
	var allDay = $("input[name='checkAllDay']");
	var allWeek = $("input[name='checkAllWeek']");
	var allMonthChecked = $(allMonth[0]).attr("checked");
	var allDayChecked = $(allDay[0]).attr("checked");
	var allWeekChecked = $(allWeek[0]).attr("checked");
	var m = "" ;//表达式中的月
	var d = "" ;//表达式中的日
	var w ="" ;//表达式中的周
	var m_ms = "" ;//表达式中的月的描述
	var d_ms = "" ;//表达式中的日的描述
	var w_ms ="" ;//表达式中的周的描述
	if(allMonthChecked){
		m = "*";
		m_ms = "每个月的";
	}else{
		var hasChecked = false;
		$("input[name='checkMonth']").each(function(){
			if($(this).attr("checked")){
				hasChecked = true;
				m = m + $(this).val() + ",";
				m_ms = m_ms + $(this).val() + "月,";
			}
		});
		if(hasChecked){
			m = m.substring(0, m.length - 1);
			m_ms = m_ms.substring(0, m_ms.length - 1);
			m_ms = "每个"+m_ms + "的";
		}
		else{
			m = "*";
			m_ms = "每个月的";
		}
	}
	if(allDayChecked){
		d = "*";
		d_ms="每一天的";
	}else{
		var hasChecked = false;
		$("input[name='checkDay']").each(function(){
			if($(this).attr("checked")){
				hasChecked = true;
				d = d + $(this).val() + ",";
				d_ms=d_ms + $(this).val() + "号,";
			}
		});
		if(hasChecked){
			d = d.substring(0, d.length - 1);
			d_ms = d_ms.substring(0, d_ms.length - 1);
			d_ms=d_ms + "的";
		}else{
			d = "?";
			
		}
	}
	if(allWeekChecked){
		w = "*";
		w_ms = "每一天的";
	}else{
		var hasChecked = false;
		$("input[name='checkWeek']").each(function(){
			if($(this).attr("checked")){
				hasChecked = true;
				w = w + $(this).val() + ",";
				w_ms = w_ms + getWeekDay($(this).val()) + ",";
			}
		});
		if(hasChecked){
			w = w.substring(0, w.length - 1);
			w_ms = w_ms.substring(0, w_ms.length - 1);
			w_ms = "每一个"+w_ms + "的"
		}else{
			w = "?";
			
		}
	}
	var ss = $("#select_ss").val();
	var mm = $("#select_mm").val();
	var hh = $("#select_hh").val();
	var bds ;//表达式
	var bds_hy;//表达式含义
	if(d_ms == "" && w_ms == ""){
		//周和日都没有选
		w_ms = "每一天的";
		d = "*";
	}
	bds = ss + " " + mm + " " + hh + " " + d + " " + m + " " + w;
	bds_hy = m_ms + d_ms + w_ms
	+ hh+":" + mm+":" + ss + "执行 ";

	$("#span_ms2").html(bds_hy);
	$("#span_ms1").html(bds);
	$("#hid_timeValName").val(bds_hy);
	$("#hid_timeVal").val(bds);
	$("#txtCronExpression").val(bds);
	$("#txtaCtiveTimeName").val(bds_hy);
	return true;
}
//根据value返回星期几
function getWeekDay(value){
	switch(value){
		case "2":
			return "星期一";
		case "3":
			return "星期二";
		case "4":
			return "星期三";
		case "5":
			return "星期四";
		case "6":
			return "星期五";
		case "7":
			return "星期六";
		case "1":
			return "星期日";
	}
}
//创建表达式
function creatBDS(){
	var fs = $("input[name='set_fs']:checked").val();
	if(fs == "0"){
		$("#span_ms2").html("");
		$("#span_ms1").html("");
		$("#hid_timeValName").val("");
		$("#hid_timeVal").html("");
		return true;
	}
	if(fs == "1"){
		return creatDescription1();
	}
	if(fs == "2"){
		return creatDescription2();
	}
	
}

var fs1;
var fs2 ;
//进入页面执行
function getFS1FS2(){
	fs1 = $("#temp_fs1").html();
	fs2 = $("#temp_fs2").html();
	$("#temp_fs1").html("");
	$("#temp_fs2").html("");
}
//方式切换
function setPanel(which){
	if(which == "无"){
		$("#span_ms2").html("");
		$("#span_ms1").html("");
		$("#hid_timeValName").val("");
		$("#hid_timeVal").val("");
		$("#div_panel").html("");
	}
	
	if(which == "方式1"){
		$("#div_panel").html(fs1);
	}
	if(which == "方式2"){
		$("#div_panel").html(fs2);
		checkBoxEventBind();
	}
}

//checkbax全选,单选事件
function checkBoxEventBind(){
	$("input[name='checkAllMonth']").click(function(){
		var check = $(this).attr("checked");
		if(check=="checked"){
			$("input[name='checkMonth']").attr("checked",true);
			}else{
			$("input[name='checkMonth']").attr("checked",false);
			}
		//$("input[name='checkMonth']").attr("checked",check);
	});
	$("input[name='checkMonth']").click(function(){
		var check = $(this).attr("checked");
		if(check){
			var allChecked = true;
			$("input[name='checkMonth']").each(function(){
				if(!$(this).attr("checked")){
					allChecked = false;
					return false;
				}
			});
			if(allChecked){
				$("input[name='checkAllMonth']").attr("checked",true);
			}
		}else{
			$("input[name='checkAllMonth']").attr("checked",false);
		}
		
	});
	$("input[name='checkAllDay']").click(function(){
		var check = $(this).attr("checked");
		if(check == "checked"){
			if(check){
				$("input[name='checkWeek']").attr("checked",false);
				$("input[name='checkAllWeek']").attr("checked",false);
				$("input[name='checkWeek']").attr("disabled",true);
				$("input[name='checkAllWeek']").attr("disabled",true);
			}else{
				$("input[name='checkWeek']").attr("disabled",false);
				$("input[name='checkAllWeek']").attr("disabled",false);
			}
			//$("input[name='checkDay']").attr("checked",check);
			$("input[name='checkDay']").attr("checked",true);
		}else{
			$("input[name='checkWeek']").attr("checked",false);
			$("input[name='checkAllWeek']").attr("checked",false);
			$("input[name='checkWeek']").attr("disabled",false);
			$("input[name='checkAllWeek']").attr("disabled",false);
			$("input[name='checkDay']").attr("checked",false);
		}
	});
	$("input[name='checkDay']").click(function(){
		var check = $(this).attr("checked");
		if(check){
			$("input[name='checkWeek']").attr("checked",false);
			$("input[name='checkAllWeek']").attr("checked",false);
			$("input[name='checkWeek']").attr("disabled",true);
			$("input[name='checkAllWeek']").attr("disabled",true);
			var allChecked = true;
			$("input[name='checkDay']").each(function(){
				if(!$(this).attr("checked")){
					allChecked = false;
					return false;
				}
			});
			if(allChecked){
				$("input[name='checkAllDay']").attr("checked",true);
			}
		}else{
			$("input[name='checkAllDay']").attr("checked",false);
			var hasNoChecked = true;
			$("input[name='checkDay']").each(function(){
				if($(this).attr("checked")){
					hasNoChecked = false;
					return false;
				}
			});
			if(hasNoChecked){
				$("input[name='checkWeek']").attr("disabled",false);
				$("input[name='checkAllWeek']").attr("disabled",false);
			}
		}
	});
	$("input[name='checkAllWeek']").click(function(){
		var check = $(this).attr("checked");
		if(check == "checked" ){
			if(check){
				$("input[name='checkDay']").attr("checked",false);
				$("input[name='checkAllDay']").attr("checked",false);
				$("input[name='checkDay']").attr("disabled",true);
				$("input[name='checkAllDay']").attr("disabled",true);
			}else{
				$("input[name='checkDay']").attr("disabled",false);
				$("input[name='checkAllDay']").attr("disabled",false);
			}
			$("input[name='checkWeek']").attr("checked",true);
			//$("input[name='checkWeek']").attr("checked",check);
		}else{
			$("input[name='checkDay']").attr("checked",false);
			$("input[name='checkAllDay']").attr("checked",false);
			$("input[name='checkDay']").attr("disabled",false);
			$("input[name='checkAllDay']").attr("disabled",false);
			$("input[name='checkWeek']").attr("checked",false);
		}
	});
	$("input[name='checkWeek']").click(function(){
		var check = $(this).attr("checked");
		if(check){
			$("input[name='checkDay']").attr("checked",false);
			$("input[name='checkAllDay']").attr("checked",false);
			$("input[name='checkDay']").attr("disabled",true);
			$("input[name='checkAllDay']").attr("disabled",true);
			var allChecked = true;
			$("input[name='checkWeek']").each(function(){
				if(!$(this).attr("checked")){
					allChecked = false;
					return false;
				}
			});
			if(allChecked){
				$("input[name='checkAllWeek']").attr("checked",true);
			}
		}else{
			$("input[name='checkAllWeek']").attr("checked",false);
			var hasNoChecked = true;
			$("input[name='checkWeek']").each(function(){
				if($(this).attr("checked")){
					hasNoChecked = false;
					return false;
				}
			});
			if(hasNoChecked){
				$("input[name='checkDay']").attr("disabled",false);
				$("input[name='checkAllDay']").attr("disabled",false);
			}
		}
	});
}

//解析表达式，并给页面控件设置
function analysisCroExpression(){
	var bds = $("#txtCronExpression").val();
	if(bds == null || bds == ""){
		return;
	}
	var arr = bds.split(" ");
	if(!(arr.length != 6 || arr.length != 7)){	
		return;//表达式格式不正确
	}
	var pessition = "";//记录arr数组中“/”出现的位置
	for(i=0;i<arr.length;i++){
		if(arr[i].indexOf("/") > 0){
			pessition = i.toString();
			break;
		}
	}
	if(pessition != ""){	//方式1
		$("input[name='set_fs']").get(1).checked = true;//方式1方式2选中
		$("#div_panel").html(fs1);
		switch(pessition){
			case "0":		//每n秒钟
				//$("#div_pl_Detail").html(str_ss);
				$("#select_pl").val("s");
				var arr_1 = arr[0].split("/");
				$("#input_num").val(arr_1[1]);
				$("#qs_ss").val(arr_1[0]);
				break;
			case "1":		//每n分钟
				$("#div_pl_Detail").html(str_min);
				$("#select_pl").val("min");
				var arr_1 = arr[1].split("/");
				$("#input_num").val(arr_1[1]);
				$("#qs_min").val(arr_1[0]);
				$("#qs_min_ss").val(arr[0]);
				break;
			case "2":		//每n小时
				$("#div_pl_Detail").html(str_hh);
				$("#select_pl").val("h");
				var arr_1 = arr[2].split("/");
				$("#input_num").val(arr_1[1]);
				$("#qs_hh").val(arr_1[0]);
				$("#qs_hh_min").val(arr[1]);
				$("#qs_hh_min_ss").val(arr[0]);
				break;
			case "3":		//每n天
				$("#div_pl_Detail").html(str_dd);
				$("#select_pl").val("d");
				var arr_1 = arr[3].split("/");
				$("#input_num").val(arr_1[1]);
				$("#qs_dd").val(arr_1[0]);
				$("#qs_dd_hh").val(arr[2]);
				$("#qs_dd_hh_min").val(arr[1]);
				$("#qs_dd_hh_min_ss").val(arr[0]);
				break;
	
		}
	}else{					//方式2
		$("input[name='set_fs']").get(2).checked = true;//方式1方式2选中
		$("#div_panel").html(fs2);
		checkBoxEventBind();
		$("#select_ss").val(arr[0]);
		$("#select_mm").val(arr[1]);
		$("#select_hh").val(arr[2]);
		if(arr[4] == "*"){
			$("input[name='checkAllMonth']").attr("checked",true);
			$("input[name='checkMonth']").attr("checked",true);
		}else{
			arr_1 = arr[4].split(",");
			for(i=0;i<arr_1.length;i++){
				var str = "input[name='checkMonth'][value='"+arr_1[i]+"']"
				$(str).attr("checked",true);
			}
			
		}
		if(arr[3] == "*"){
			$("input[name='checkAllDay']").attr("checked",true);
			$("input[name='checkDay']").attr("checked",true);
		}else if(arr[3] == "?"){
			
		}else{
			arr_1 = arr[3].split(",");
			for(i=0;i<arr_1.length;i++){
				var str = "input[name='checkDay'][value='"+arr_1[i]+"']"
				$(str).attr("checked",true);
			}
			$("input[name='checkWeek']").attr("disabled",true);
			$("input[name='checkAllWeek']").attr("disabled",true);
			
		}
		if(arr[5] == "*"){
			$("input[name='checkAllWeek']").attr("checked",true);
			$("input[name='checkWeek']").attr("checked",true);
		}else if(arr[5] == "?"){
			
		}else{
			arr_1 = arr[5].split(",");
			for(i=0;i<arr_1.length;i++){
				var str = "input[name='checkWeek'][value='"+arr_1[i]+"']"
				$(str).attr("checked",true);
			}
			$("input[name='checkDay']").attr("disabled",true);
			$("input[name='checkAllDay']").attr("disabled",true);
			
		}
	}
	
}
