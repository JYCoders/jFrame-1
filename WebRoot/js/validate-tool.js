/*
*验证工具类，支持以下验证方式：
*为空验证
*长度验证
*正则验证
*数值验证
*精度验证
*特殊字符验证
*日期验证
*相等验证
*必选验证
*时间比较验证
*数值比较
*字符串比较
*/

//验证工具对象
var ValidateTool=function(){}

//为空验证
ValidateTool.checkNull = function (o, errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (!o.value&&o.value!='0') {
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};

//comboSelect为空验证
ValidateTool.checkComboSelectNull = function (o, errMsg) {
	var val=o.getSelectedValue();
	var result={};
	if (!val) {
	
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};
//comtext为空验证
ValidateTool.checkComboTextNull = function (o, errMsg) {
	var val=o.getComboText();
	var result={};
	if (!val) {
	
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};
//时间比较验证
ValidateTool.checkTimeSelect  = function (o1,o2,errMsg) {
	var a=o1.getSelectedValue();
	var b=o2.getSelectedValue();
	var result={};
	if (a<=b) {
		result.success=true;
	} else {
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};
//为空验证(可输入下拉框)
ValidateTool.checkComboNull = function (o, errMsg) {
	var val=ValidateTool.trim(o.getComboText());
	var result={};
	if (!val) {
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};
//删除左右两端的空格
ValidateTool.trim=function(str){
    return str.replace(/(^\s*)|(\s*$)/g, "");
}


//长度验证
ValidateTool.checkComboLength = function (o, errMsg,max) {
	var val=ValidateTool.trim(o.getComboText());
	var result={};
	if (val&&val.length>max) {
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};
//长度验证
ValidateTool.checkLength = function (o,errMsg,max) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value&&o.value.length > max) {
		result.success=false;
		result.msg=errMsg;
	}else{
		result.success=true;
	}
	return result;
};

//长度相等验证
ValidateTool.checkLengths = function (o,errMsg,max) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value&&o.value.length == max) {
		result.success=true;
	}else{
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};


//正则验证(flag:正向验证，反向验证）
ValidateTool.checkRegexp = function (o, regexp, errMsg,flag) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value&&(!flag&&!(regexp.test(o.value))||flag&&(regexp.test(o.value)))) {
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};
//数值验证
ValidateTool.checkNumber = function (o, errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if(o.value&&isNaN(o.value)){
		result.success=false;
		result.msg=errMsg;
	} else {
		result.success=true;
	}
	return result;
};

//数值验证
ValidateTool.checkNumberAndNOZero = function (o, errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if(o.value&&isNaN(o.value)){
		result.success=false;
		result.msg=errMsg;
	} else {
		if(o.value>0)
		{
			var index=o.value.toString().indexOf(".");
			if(index!=-1)
			{
				result.success=false;
				result.msg=errMsg;
			}else
			{
				result.success=true;
			}
		}else{
			result.success=false;
			result.msg=errMsg;
		}
	}
	return result;
};
//精度验证

//特殊字符验证
ValidateTool.checkSpecial = function (o, errMsg) {
	return ValidateTool.checkRegexp(o,/^[\u4E00-\u9FA5A-Za-z_][\u4E00-\u9FA5A-Za-z0-9_]{0,}$/,errMsg);
};
ValidateTool.checkSafety =function(o,errMsg){
	return ValidateTool.checkRegexp(o,/[\'\<\>\+\-\*\/\^\=\\\&\|\[\]\{\}\~\`\#\$]+/,errMsg,true);
	//return ValidateTool.checkRegexp(o,/[\'\"\,\<\>\+\-\*\/\%\^\=\\\!\&\|\(\)\[\]\{\}\:\;\~\`\#\$]+/,errMsg,true);
};
//日期验证

//相等验证
ValidateTool.checkEquals = function (o,_value,errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value==_value) {
		result.success=true;
	} else {
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};
//大于验证
ValidateTool.checkGreater = function (o,_value,errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value&&_value&&o.value*1>_value*1) {
		result.success=true;
	} else {
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};
//小于验证
ValidateTool.checkLess = function (o,_value,errMsg) {
	o=typeof o =="string"?document.getElementById(o):o;
	var result={};
	if (o.value&&_value&&o.value*1<_value*1) {
		result.success=true;
	} else {
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};

//必选验证

//时间比较验证
ValidateTool.checkInterval  = function (o1,o2,errMsg) {
	o1=typeof o1 =="string"?document.getElementById(o1):o1;
	o2=typeof o2 =="string"?document.getElementById(o2):o2;
	var result={};
	if (o1.value<=o2.value) {
		result.success=true;
	} else {
		result.success=false;
		result.msg=errMsg;
	}
	return result;
};

function checkMark(){
	var errors=[];
 	errors.push(VT.checkNull("mark","请填写审核意见!"));
 	errors.push(VT.checkLength("mark","请填写少于500字符的审核意见!",500));
 	errors.push(VT.checkSafety("mark","审核意见填写有误!（可能含有特殊字符）"));
 	var errorMsg=VT.getErrorsString(errors," ");
	return errorMsg;
}

//数值比较

//字符串比较


//错误数组
ValidateTool.getErrorsString=function (arr,sep,displayMax){
	var displayMax =displayMax||99999;
	var count=0;
	var result="";
	if(arr!=null&&arr.length>0){
		for(var i=0;i<arr.length;i++){
			if(!arr[i].success){
				result+=arr[i].msg+sep;
				count=count+1;
				if(displayMax==count){
					break;
				}
			}
		}
	}
	return result;
}
function initErrorWindow(){
	errorWin=dhxLayout.dhxWins.createWindow("errorWin",0, 0, 300,300);
	errorWin.center();
	errorWin.setText("错误提示");
	errorWin.button("close").attachEvent("onClick", function(){errorWin.hide();});
	errorWin.setModal(false);
	errorWin.hide();
}


//简写
var VT=ValidateTool;
ValidateTool.eq=ValidateTool.checkEquals;
ValidateTool.gt=ValidateTool.checkGreater;
ValidateTool.lt=ValidateTool.checkLess;


