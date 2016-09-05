
	/* *获取某系统类型的编码和值，并填充到指定的select中
	**/
	function loadMastTypeComList(typeCode,inputId,inputLabel,value){
		
		var url = BASE_PATH + "control/comm/getMiddleWareComList.action?type="+typeCode+"&etc="
					+ new Date().getTime();
		var param = "";
		$("#"+inputId).empty();
		$("#"+inputId).append("<option value='0'> --请选择"+inputLabel+"--</option>");
		$.ajax({
			type: "POST",
			async: false,
			cache: false,
			url: url,
			data: param,
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
			dateType: "json",
			success: function (data){
				if(data!=null && data.length>0){
					$.each(data,function(entryIndex,entry){
						if(entry){
							//判断entry，可以避免检索码表结果中出现为id和text为null的情况（此情况下，entry的size比实际大1个）
							$("#"+inputId).append("<option value='"+ entry['id'] +"'>"+ entry['text'] +"</option>");
						}
	   				 });  
					
					$("#"+inputId).val(value);
					if($("#"+inputId)[0]){
					//	$("#"+inputId)[0].style.width="100%";//解决select下拉框的样式：宽度变成少于100%的问题
					}
				}
			}
		});
	}
	/* *获取某系统类型的编码和值，并填充到指定的EasyuiComboBox中
	**/
	function loadMastTypeComList2(typeCode,inputId,inputLabel,value){
		
		var url = BASE_PATH + "control/comm/getMiddleWareComList.action?type="+typeCode+"&etc="
					+ new Date().getTime();
		var param = "";
		$.ajax({
			type: "POST",
			async: false,
			cache: false,
			url: url,
			data: param,
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
			dateType: "json",
			success: function (data){
				var comData = new Array();
				var tit = "--请选择"+inputLabel+"--";
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
				var str = "#"+inputId;
				$(str).combobox('loadData',comData);
				$(str).combobox('setValue',value);
			}
		});
	}
	/* *获取某系统类型的编码和值，并填充到指定的EasyuiComboBox中
	**/
	function loadMastTypeComList3(typeCode,inputId,inputLabel,value){
		
		var url = BASE_PATH + "control/comm/getMiddleWareComList.action?type="+typeCode+"&etc="
					+ new Date().getTime();
		var param = "";
		$.ajax({
			type: "POST",
			async: false,
			cache: false,
			url: url,
			data: param,
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
			dateType: "json",
			success: function (data){
				var comData = new Array();
				var tit = "--请选择"+inputLabel+"--";
				comData[0] = {"id" : "","text" : tit};
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
				var str = "#"+inputId;
				$(str).combobox('loadData',comData);
				if(comData[value]!=undefined&&comData[value]!="")
					$(str).combobox('setValue',value);
				else
					$(str).combobox('setValue','');
			}
		});
	}

	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	//调用：
	//var time1 = new Date().format("yyyy-MM-dd");
	//var time2 = new Date().format("yyyy-MM-dd HH:mm:ss");  
	
	//通用函数，检查属性是否唯一，并返回见过
	function checkPropertyUnique(propertyName,objInput,url,param){
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
						}
					} else {
						alert(result.resultValue.msg);
					}
				}
			}
		});
	}