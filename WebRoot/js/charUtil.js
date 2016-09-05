var hint = "提示窗";
var delckmsg = "是否确认删除该信息?";

//$.post("../../getCookie",{'t':'getcookie'},function(json){//获取样式
//	$("#themesLink").attr('href','../../inc/themes/'+json+'/easyui.css');
//});

function setCookie(str){//设置样式
	$.post("../../setCookie",{'t':'setcookie','val':str},function(json){
		$("#themesLink").attr('href','../../inc/themes/'+str+'/easyui.css');
	});
}

function loaderror(){
	$.messager.alert(hint,'列表信息加载失败！','error');
}

function dataerror(){
	$.messager.alert(hint,'数据处理失败，请重试或联系管理员！','error');
}

function mainsuccess(){
	$.messager.show({title:'提示',msg:'操作成功！',showType:'show'});
}

function editmsg(){
	$.messager.alert(hint,'请选择您所需要修改的信息!','info');
}

function delmsg(){
	$.messager.alert(hint,'请选择您所需要删除的信息!','info');
}

function maskHide(){//隐藏遮蔽
	$.mask.hide();
}

function getDate(){
	var date=new Date();
	var Month=(date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1));
	var Day=date.getDate()>9?date.getDate():("0"+date.getDate());
	return date.getFullYear()+"-"+Month+"-"+Day;
}
/********获取当天日期前n天的日期*************/
function getDateStr(n) {
    var dd = new Date();
    dd.setDate(dd.getDate()+n);//获取n天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}
/***获取当天前一个月的日期***/
function getLastMonthDate(){
	var date=new Date();
	var Month=(date.getMonth()+1)>9?(date.getMonth()):("0"+(date.getMonth()));
	var Day=date.getDate()>9?date.getDate():("0"+date.getDate());
	return date.getFullYear()+"-"+Month+"-"+Day;
}
/*****获取当月的第一天************/
function getMonthFirstDate(){
	 var myDate = new Date();
	 var year = myDate.getFullYear();
	 var month = myDate.getMonth()+1;
	 if (month<10){
	     month = "0"+month;
	 }
	 var firstDay = year+"-"+month+"-"+"01";
	 return firstDay;
}
/*****获取当月的最后一天***************/
function getMonthEndDate(){
	var date=new Date();
	var year=date.getFullYear(); 
	var month=date.getMonth(); 
	var day=date.getDate();
	var enddaytime=new Date(year,month+1,1);
	var endday = (new Date(enddaytime.getTime()-1000*60*60*24)).getDate();
	month=month+1;
	if (month<10){
	     month = "0"+month;
	 }
	return year+"-"+month+"-"+endday;
}
function getMonth(){
	var date=new Date();
	var Month=(date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1));
	return date.getYear()+"-"+Month;
}
function getMonthByNum(t){
	var date=new Date();
	var mon = parseInt((date.getMonth()+t+1))%12==0?12:parseInt((date.getMonth()+t+1))%12;
	if(mon<0){
		mon = mon+12;
	}
	var yea=0;
	if(t>-1){
		yea = parseInt((date.getMonth()+t)/12)
	}else if(date.getMonth()+1+t<1){
		yea = -parseInt(((Math.abs(t)-date.getMonth()-1)/12))-1
	}
	var Month=mon>9?mon:("0"+mon);
	return (date.getFullYear()+yea)+"-"+Month;
}
function getMonthByNumYjs(t){
	var date=new Date();
	var mon = parseInt((date.getMonth()+t+1))%12==0?12:parseInt((date.getMonth()+t+1))%12;
	if(mon<0){
		mon = mon+12;
	}
	var yea=0;
	if(t>-1){
		yea = parseInt((date.getMonth()+t)/12)
	}else if(date.getMonth()+1+t<1){
		yea = -parseInt(((Math.abs(t)-date.getMonth()-1)/12))-1
	}
	var Month=mon>9?mon:("0"+mon);
	return (date.getFullYear()+yea)+""+Month;
}
function howday(sDate,eDate){//时间差
	var sArr = sDate.split("-");
	var eArr = eDate.split("-");
	var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
	var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
	var result = (eRDate-sRDate)/(24*60*60*1000);
	return result;
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    } else {
        return this.replace(reallyDo, replaceWith);
    }
};

function _right(value,row,index) {
	if(value==undefined) value="";
	return value+"&nbsp;";
}

function _left(value,row,index) {
	if(value==undefined) value="";
	return "&nbsp;"+value;
}

function _date(value,row,index){
	if(value==undefined) value="";
	return value.substr(0,10);
}

function _datetime(value,row,index){
	if(value==undefined) value="";
	return value.substr(0,10)+"&nbsp;"+value.substr(11,8);
}

function accept(id){
	if (endEditing()){
		$(id).datagrid('acceptChanges');
	}
	
	$(id).datagrid('acceptChanges');
}

//合并行
function mergeCellsByField(tableID,colList){
    var ColArray = colList.split(",");
    var tTable = $('#'+tableID);
    var TableRowCnts=tTable.datagrid("getRows").length;
    var tmpA;
    var tmpB;
    var PerTxt = "";
    var CurTxt = "";
    var alertStr = "";
    //for (j=0;j<=ColArray.length-1 ;j++ )
    for (j=ColArray.length-1;j>=0 ;j-- )
    {
        //当循环至某新的列时，变量复位。
        PerTxt="";
        tmpA=1;
        tmpB=0;
        
        //从第一行（表头为第0行）开始循环，循环至行尾(溢出一位)
        for (i=0;i<=TableRowCnts ;i++ )
        {
            if (i==TableRowCnts)
            {
                CurTxt="";
            }
            else
            {
                CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];
            }
            if (PerTxt==CurTxt)
            {
                tmpA+=1;
            }
            else
            {
                tmpB+=tmpA;
                tTable.datagrid('mergeCells',{
                    index:i-tmpA,
                    field:ColArray[j],
                    rowspan:tmpA,
                    colspan:null
                });
                tmpA=1;
            }
            PerTxt=CurTxt;
        }
    }
}
/***
 * 计算输入的字符串的长度，如果是汉字，则算3个长度，除汉字外其他字符的长度都算1
 */
function getByteLen(val) {
    var len = 0;
    for (var i = 0; i < val.length; i++) {
       var length = val.charCodeAt(i);
       if(length>=0&&length<=128)
        {
            len += 1;
        }
        else
        {
            len += 3;
        }
    }
    return len;
}
/****************************************扩展验证***************************************/
(function($)
{
    jQuery.fn.setfocus = function()
    {
        return this.each(function()
        {
            var dom = this;
            setTimeout(function()
            {
                try { dom.focus(); } catch (e) { }
            }, 0);
        });
    };
})(jQuery);

$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    ZIP: {
        validator: function (value, param) {
            return /^[1-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    date: {
    	validator: function (value, param) {
    		return /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/.test(value);
    	},
      message: '日期格式不正确，正确的日期格式是"yyyy-mm-dd"。'
	},
    oldDateValid:{
    	validator : function(value,param) { //参数value为当前文本框的值
            var currDate = new Date();
            var date = $.fn.datebox.defaults.parser(value);  
            varify = date < currDate;  
            return varify;  
        },  
        message : '选择的日期不能大于当前日期！'
    },
    startDateValid:{
    	validator : function(value,param) { //参数value为当前文本框的值
    		txtDate = $("#"+param[0]).datetimebox('getValue');//获取被比较时间的值
    		if(txtDate==null||txtDate.replaceAll(" ","")==""){
    			return true;
    		}
    		var endDate = $.fn.datebox.defaults.parser(txtDate);  
            var startDate = $.fn.datebox.defaults.parser(value); 
            varify = startDate <= endDate;  
            return varify;  
        },  
        message : '开始日期不能要大于结束日期！'
    },
    endDateValid:{
    	validator : function(value,param) { //参数value为当前文本框的值
    		txtDate = $("#"+param[0]).datetimebox('getValue');//获取被比较时间的值  
    		var startDate = $.fn.datebox.defaults.parser(txtDate);  
            var endDate = $.fn.datebox.defaults.parser(value); 
            varify = startDate <= endDate;  
            return varify;  
        },  
        message : '结束日期不能小于开始日期！'
    },
    ip: {
    	validator: function (value, param) {
    		return /^((0|(?:[1-9]\d{0,1})|(?:1\d{2})|(?:2[0-4]\d)|(?:25[0-5]))\.){3}((?:[1-9]\d{0,1})|(?:1\d{2})|(?:2[0-4]\d)|(?:25[0-5]))$/.test(value);
    	},
      message: 'IP地址格式不正确'
	},
    port: {
    	validator: function (value, param) {
    		return /^[1-9]\d*$/.test(value);
    	},
      message: '端口格式不正确'
	},
    telephone: {
    	validator: function (value, param) {
    		return /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(value);
    	},
      message: '固定电话格式不正确'
	},
    mobile: {
//        validator: function (value, param) {
//            return /^1[3|4|5|8][0-9]\d{4,8}$/.test(value);
//        },
        validator: function (value, param) {
            return /^1[3|4|5|8][0-9]\d{8}$/.test(value);
        },
        message: '手机号码不正确'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message:'请输入正确的身份证号码'
    },
    bankCard: {
        validator: function (value, param) {
    		return  /^\d{19}$/g.test(value);
        },
        message:'请输入正确的银行卡号'
    }
});

$.extend($.fn.datagrid.defaults.editors, {   
    text_readonly: {   
        init: function(container, options){   
            var input = $('<input type="text" onfocus="this.blur()" readonly="readonly"  style="background-color:transparent;border:0px" class="datagrid-editable-input">').appendTo(container);   
            return input;   
        },   
        getValue: function(target){   
            return $(target).val();   
        },   
        setValue: function(target, value){   
            $(target).val(value);   
        },   
        resize: function(target, width){   
            var input = $(target);   
            if ($.boxModel == true){   
                input.width(width - (input.outerWidth() - input.width()));   
            } else {   
                input.width(width);   
            }   
        }   
    }   
});

//jQuery.extend(jQuery.fn.datagrid.defaults.editors, {
//        combotree: {
//            init: function(container, options){
//                var editor = jQuery('<input type="text">').appendTo(container);
//                editor.combotree(options);
//                return editor;
//            },
//            destroy: function(target){
//                jQuery(target).combotree('destroy');
//            },
//            getValue: function(target){
//                var temp = jQuery(target).combotree('getValues');
//                //alert(temp);
//                return temp.join(',');
//            },   
//            setValue: function(target, value){
//                var temp = value.split(',');
//                //alert(temp);
//                jQuery(target).combotree('setValues', temp);
//            },
//            resize: function(target, width){
//                jQuery(target).combotree('resize', width);
//            }
//    }
//});

/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
};

var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
};

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};

/*
   如果t的长度大于len就截取
*/
function operatorString(t,len){
   	var value = $(t).val();
   	if(value.length > len ){
   	    value = value.substring(0,len);
   	    $(t).val(value);
   	}
}

/**  
 * 扩展两个方法  
 */  
$.extend($.fn.datagrid.methods, {   
    /**
     * 开打提示功能  
     * @param {} jq  
     * @param {} params 提示消息框的样式  
     * @return {}  
     */  
    doCellTip : function(jq, params) {   
        function showTip(data, td, e) {   
            if ($(td).text() == "")   
                return;   
            data.tooltip.text($(td).text()).css({   
                        top : (e.pageY + 10) + 'px',   
                        left : (e.pageX + 20) + 'px',   
                        'z-index' : $.fn.window.defaults.zIndex,   
                        display : 'block'   
                    });   
        };   
        return jq.each(function() {   
            var grid = $(this);   
            var options = $(this).data('datagrid');   
            if (!options.tooltip) {   
                var panel = grid.datagrid('getPanel').panel('panel');   
                var defaultCls = {   
                    'border' : '1px solid #333',   
                    'padding' : '1px',   
                    'color' : '#333',   
                    'background' : '#f7f5d1',   
                    'position' : 'absolute',   
                    'max-width' : '200px',   
                    'border-radius' : '4px',   
                    '-moz-border-radius' : '4px',   
                    '-webkit-border-radius' : '4px',   
                    'display' : 'none'   
                }   
                var tooltip = $("<div id='celltip'></div>").appendTo('body');   
                tooltip.css($.extend({}, defaultCls, params.cls));   
                options.tooltip = tooltip;   
                panel.find('.datagrid-body').each(function() {   
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length   
                            ? $(this).find('> div.datagrid-body-inner')[0]   
                            : this;   
                    $(delegateEle).undelegate('td', 'mouseover').undelegate(   
                            'td', 'mouseout').undelegate('td', 'mousemove')   
                            .delegate('td', {   
                                'mouseover' : function(e) {   
                                    if (params.delay) {   
                                        if (options.tipDelayTime)   
                                            clearTimeout(options.tipDelayTime);   
                                        var that = this;   
                                        options.tipDelayTime = setTimeout(   
                                                function() {   
                                                    showTip(options, that, e);   
                                                }, params.delay);   
                                    } else {   
                                        showTip(options, this, e);   
                                    }   
  
                                },   
                                'mouseout' : function(e) {   
                                    if (options.tipDelayTime)   
                                        clearTimeout(options.tipDelayTime);   
                                    options.tooltip.css({   
                                                'display' : 'none'   
                                            });   
                                },   
                                'mousemove' : function(e) {   
                                    var that = this;   
                                    if (options.tipDelayTime) {   
                                        clearTimeout(options.tipDelayTime);   
                                        options.tipDelayTime = setTimeout(   
                                                function() {   
                                                    showTip(options, that, e);   
                                                }, params.delay);   
                                    } else {   
                                        showTip(options, that, e);   
                                    }   
                                }   
                            });   
                });   
  
            }   
  
        });   
    },   
    /**
     * 关闭消息提示功能  
     * @param {} jq  
     * @return {}  
     */  
    cancelCellTip : function(jq) {   
        return jq.each(function() {   
                    var data = $(this).data('datagrid');   
                    if (data.tooltip) {   
                        data.tooltip.remove();   
                        data.tooltip = null;   
                        var panel = $(this).datagrid('getPanel').panel('panel');   
                        panel.find('.datagrid-body').undelegate('td',   
                                'mouseover').undelegate('td', 'mouseout')   
                                .undelegate('td', 'mousemove')   
                    }   
                    if (data.tipDelayTime) {   
                        clearTimeout(data.tipDelayTime);   
                        data.tipDelayTime = null;   
                    }   
                });   
    }   
});

function getMonthDateByNum(t){
	var date=new Date();
	var Month=(date.getMonth()+t+1)>9?(date.getMonth()+t+1):("0"+(date.getMonth()+t+1));
	var Day=date.getDate()>9?date.getDate():("0"+date.getDate());
	return date.getFullYear()+"-"+Month+"-"+Day;
}
/**
 *  如果combobox的value和text值一样，表示这条数据删除了，这里做个处理
 */
function _comboboxClean(id){
	var cval = $('#'+id).combobox('getValue');
	var ctext = $('#'+id).combobox('getText');
	if(cval == ctext){
			$('#'+id).combobox('setValue','无此编码数据');
	}
}