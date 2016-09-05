//将form转为AJAX提交
function ajaxSubmit(frmId, successFn) {
	var s = "#" + frmId;
	var frm = $(s)[0];
    var dataPara = getFormJson(frm);
    $.ajax({
        url: frm.action,
        type: frm.method,
        data: dataPara,
        success: successFn
        
    });
}
function ajaxSubmit(frmId, successFn,errorFn) {
	var s = "#" + frmId;
	var frm = $(s)[0];
    var dataPara = getFormJson(frm);
    $.ajax({
        url: frm.action,
        type: frm.method,
        data: dataPara,
        success: successFn,
        error: errorFn
        
    });
}
//将form中的值转换为键值对。
function getFormJson(frm) {
    var o = {};
    var a = $(frm).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });

    return o;
}

/**检测字符串长度
 * strVal:被检测字符串
 * strName:被检测字符串含义
 * maxLength:允许的最大字节长度
 * allowNull：是否可空 true or false
 * allowSpecialChar:是否允许特殊字符 true or false
 * 返回值：通过返回true，否则返回false并弹出框提示不通过原因
 */
function checkStringVal(strValue,strName,maxLength,allowNull,allowSpecialChar){
	strVal = $.trim(strValue);
	//如果可空，通过，返回true
	if(strVal == null || strVal == ""){
		if(!allowNull){
			alert(strName + "不能为空！");
			return false;
		}
	}
	//检测特殊字符
	var vkeyWords = /[`~!@#$%^&*+=\\\[\]\{\}:;'\",.<>\/?]/;
	if(!allowSpecialChar){
		if(vkeyWords.test(strVal)){
			alert(strName + "不能含有特殊字符！");
			return false;
		}
	}
	//检测长度问题
	var eng = 0;
	var chi = 0;
	for(i=0;i<strVal.length;i=i+1){
		var s = strVal.substr(i,1);
		if(s.match(/[\u4e00-\u9fa5]/g)){
			chi = chi + 1;
		}else{
			eng = eng + 1;
		}
	}
	var total = eng + chi * 3;
	//alert("汉字：" + chi + "  非汉字：" + eng);
	if(total > maxLength){
		var num = total - maxLength;
		var num2 =  Math.ceil(num/3);
		alert(strName + " 多出" + num2 + "个汉字字符或者" + num + "个非汉字字符！");
		//return false;
	}
	
	//执行到这里，符合要求
	return true;
}











