//遍历表单元素并disable禁止或启用
var disableFormElements=function (formId,flag){
	var es=document.getElementById(formId).elements;
	if(!es)
		return null;
	var len=es.length;
	for(var i=0;i<len;i++){
		var _el=es[i];
		if((_el.tagName=="INPUT"&&_el.type!='button')||_el.tagName=="TEXTAREA"||_el.tagName=="SELECT"||_el.tagName=="RADIO"){
			_el.disabled=flag;
		}
	}
}
var FormHelper=function(){};
//遍历表单元素，如果是单选框或者复选框将取选择状态的值//静态方法
FormHelper.getFormParams=function (formId){
	var es=document.getElementById(formId).elements;
	if(!es)
		return null;
	var paramStr="";
	var map=[];
	for(var i=0,len=es.length;i<len;i++)
	{
		if(es[i].name)
		{
			var tName=es[i].tagName.toUpperCase();
			
			if(tName=="INPUT"&&(es[i].type.toUpperCase()=="CHECKBOX"||es[i].type.toUpperCase()=="RADIO"))
			{
				if(es[i].checked)
				{
					map.push(es[i].name);
				}
				continue;
			}
			paramStr+="&";
			paramStr+=(es[i].name+"="+encodeURIComponent(es[i].value)||"");
		}
	}
	for(var i=0;i<map.length;i++){
		paramStr+="&";
		paramStr+=(map[i]+"="+getRadioValue(map[i])||"");
	}
	return paramStr.substring(1);
}
//实例方法指向静态方法
FormHelper.prototype.getFormParams=FormHelper.getFormParams;

/**
* 初始化下拉框的方法
* 参数如下：
* selectId  下拉框ID 或下拉框表单元素
* list   下拉框数据源数组
* value  数据源数值键
* display 数据源显示值键
* flag 是否显示未选择
* selectIndex 默认选择下标，如果不填写，默认选择第一项
*
* 功能调用添加
* e.onchange 存在，则做为初始化之后的选项事件默认触发。
*/
var fillSelect=function(selectId,list,value,display,flag,selectIndex){
	var _el=typeof selectId=="string"?document.getElementById(selectId):selectId;
	_el.options.length=0;
	var index=0;
	if(flag){
		_el.options[0]=new Option("---\u672a\u9009\u62e9---","");
		//_el.options[0]=new Option("","");
		index++;
	}
	if(typeof list=="string"){
	
	}else{
		if (list && list.length && list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				var n = list[i];
				_el.options[i+index]=new Option(n[display],n[value]);
				_el.options[i+index].title=n[display];
			}
			_el.selectedIndex=selectIndex?selectIndex:0;
			if(_el.onchange){
				_el.onchange();
			}
		}
	} 
}
/**
* 设置radio单选框的选项
*/
var setRadioChecked = function(_name,_value){
	var els=document.getElementsByName(_name);
	if(els&&els.length>0){
		for(var i=0;i<els.length;i++){
			els[i].checked=(els[i].value==_value);
		}	
	}
}
/**
* 设置radio单选框的选项值
*/
var getRadioValue = function(_name){
	var els=document.getElementsByName(_name);
	if(els&&els.length>0){
		for(var i=0;i<els.length;i++){
			if(els[i].checked)
				return els[i].value;
		}		
	}
}
