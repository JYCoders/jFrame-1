//遍历表单元素并disable禁止或启用
var disableFormElements=function (formId,flag){
	var es=document.getElementById(formId).elements;
	if(!es)
		return null;
	var len=es.length;
	for(var i=0;i<len;i++){
		var _el=es[i];
		if(_el.tagName=="INPUT"||_el.tagName=="TEXTAREA"||_el.tagName=="SELECT"||_el.tagName=="RADIO"){
			_el.disabled=flag;
		}
	}
}
var setValue = function (_id, _value,_type) {
	var _el = document.getElementById(_id);
	if(!_el)
		return;
	if(_type&&_type=="date"){
		_el.value = _value.substring(0,10)||"";
	}
	else if(_el.tagName=="INPUT"||_el.tagName=="TEXTAREA"){
		_el.value = _value?""+_value:_value==0?"0":"";
	}
	else if(_el.tagName=="SELECT")
	{
		_el.selectedIndex=0;
		var ops=_el.options;
		for(var i=0;i<ops.length;i++){
			if(ops[i].value==_value)
				_el.selectedIndex=i;
			else
				_el.value=_value;
		}
	}
	_el = null;
};

var setRadioChecked = function(_name,_value){
	var els=document.getElementsByName(_name);
	if(els&&els.length>0){
		for(var i=0;i<els.length;i++){
			els[i].checked=(els[i].value==_value);
		}	
	}
}
var getRadioValue = function(_name){
	var els=document.getElementsByName(_name);
	if(els&&els.length>0){
		for(var i=0;i<els.length;i++){
			if(els[i].checked)
				return els[i].value;
		}		
	}
}

var getValue = function (_id) {
	var _value
	var _el = document.getElementById(_id);
	if(!_el)
		return;
	else if(_el.tagName=="INPUT"||_el.tagName=="TEXTAREA"){
		 _value  = _el.value;
	}
	else if(_el.tagName=="SELECT")
	{
		_el.selectedIndex=0;
		var ops=_el.options;
		_value =ops[_el.selectedIndex].value;
	}
	return _value;
};
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
	获取服务器根路径（相当于<%=basePath%>）
*/
function getRootPath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return(prePath+postPath+"/");
}

//路径转换
if(typeof(basePath)=="undefined"||!basePath)
	basePath=getRootPath();
//初始化主题
//参数为主题下拉框ID或下拉框ID集合
/*function fillTopicSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		dhtmlxAjax.get(basePath+"jysoft/servlet/indexSystem.do?method=getRootIndexByRole&etc="+new Date().getTime(),function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"indexId","indexName");
				}
			}
		});		
	}
}*/
//参数为主题下拉框ID或下拉框ID集合
function fillTopicSelects(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/indexSystem.do?method=getRootIndex&etc="+new Date().getTime());
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"indexId","indexName");
				}
			}	
	}
}
//单位类别填充
function fillCompanyTypeSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var url = basePath+"jysoft/servlet/companyTypeInfo.do?method=getCompanyTypeInfos&etc="+new Date().getTime();
		var loader = dhtmlxAjax.getSync(url);
		var resultText = loader.xmlDoc.responseText;
		var data = JSON.parse(resultText);
		if(!data.errorCode){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data,"companyTypeId","companyTypeName");
			}
		}
	}
}
//单位填充
function fillCompanySelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		dhtmlxAjax.get(basePath+"jysoft/servlet/companyInfo.do?method=getCompanyInfos&etc="+new Date().getTime(),function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"companyInfo","companyName");
				}
			}
		});		
	}
}

//根据体系类别进行单位填充
function fillCompanySelectBySystemType(ids,val,corpCode) {
	if(ids&&typeof ids=='string'){
	ids=[ids];
	}
//	alert("val++"+val);
	var url = basePath + "jysoft/servlet/companyTypeInfo.do?method=queryBySystemType&systemType="+val+"&corpCode="+corpCode+"&etc=" + new Date().getTime();
    dhtmlxAjax.get(url, function(loader) {
        var resultText = loader.xmlDoc.responseText; //ajax result [string]
        var data = JSON.parse(resultText);// 转成对象
        if (!data.errorCode) {
            for (var i = 0; i < ids.length; i++) {
                fillSelect(ids[i], data, "companyTypeId", "companyTypeName");
            }
        }
    });
}
//根据填报进行单位填充
function fillCompanySelectByWriteId(ids,val) {
	if(ids&&typeof ids=='string'){
	ids=[ids];
	}
    var loader=dhtmlxAjax.getSync(basePath + "jysoft/servlet/companyTypeInfo.do?method=fillCompanyByWriteId&writeId="+val+"+&etc=" + new Date().getTime());
    var resultText = loader.xmlDoc.responseText; //ajax result [string]
    var data = JSON.parse(resultText);// 转成对象
    
    if (!data.errorCode) {
        for (var i = 0; i < ids.length; i++) {
            fillSelect(ids[i], data, "companyTypeId", "companyTypeName");
        }
    }
}

//根据所有单位填充
function fillCompanyAllSelect(ids) {
	if(ids&&typeof ids=='string'){
	ids=[ids];
	}
	var url=basePath + "jysoft/servlet/companyTypeInfo.do?method=getCompanyTypeInfos&etc=" + new Date().getTime();
    var loader=dhtmlxAjax.getSync(url);
    var resultText = loader.xmlDoc.responseText; //ajax result [string]
    var data = JSON.parse(resultText);// 转成对象
   
    if (!data.errorCode) {
        for (var i = 0; i < ids.length; i++) {
            fillSelect(ids[i], data, "companyTypeId", "companyTypeName");
        }
    }
}
//根据主题填充周期--填报
function fillPeriodSelectByType(ids,topicId,periodB){
	if(ids&&typeof ids=='string'){
			ids=[ids];
		}
		if(ids&&ids.length>0){
		    var params = "&topicId="+document.getElementById("theme").value;	
			dhtmlxAjax.post(basePath + "jysoft/servlet/AppraiseCycleTopicServlet.do?method=getPeriodByIndexId&etc=" + new Date().getTime(),params, function(loader) {
	        var resultText = loader.xmlDoc.responseText; //ajax result [string]
	        var data = JSON.parse(resultText);// 转成对象
	        if (!data.errorCode){
		        for(var i=0;i<ids.length;i++){
		           fillSelect("period",data,"cycleId","cycleName");
		           }
		        }
		    });
		}
	}
//根据主题填充周期--分析	
function fillCycleIdSelectByType(ids,topicId,periodB){
	if(ids&&typeof ids=='string'){
			ids=[ids];
		}
		if(ids&&ids.length>0){
		    var params = "&topicId="+document.getElementById("indexId").value;
			dhtmlxAjax.post(basePath + "jysoft/servlet/AppraiseCycleTopicServlet.do?method=getPeriodByIndexId&etc=" + new Date().getTime(),params, function(loader) {
	        var resultText = loader.xmlDoc.responseText; //ajax result [string]
	        var data = JSON.parse(resultText);// 转成对象
	        if (!data.errorCode){
		        for(var i=0;i<ids.length;i++){
		           fillSelect("cycleId",data,"cycleId","cycleName");
		           }
		        }
		    });
		}
	}
	/*
//主题
function fillTopicSelect(ids){
		if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/indexSystem.do?method=getRootIndex&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse(resultText);// 转成对象
		if(!data.errorCode){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data,"indexId","indexName");
			}
		}			
	}
}
*/
//体系类别
function fillSystemTypeSelect(ids,cordCode){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			var url = basePath+"jysoft/servlet/systemTypeServlet.do?method=fillSystemType&corpCode="+cordCode+"&etc="+new Date().getTime();
			var loader=dhtmlxAjax.getSync(url);
			var resultText = loader.xmlDoc.responseText;
			var data = JSON.parse(resultText);
			if(!data.errorCode){
				fillSelect(ids[i],data,"typeId","typeName",true);
			} 
		}
	}
}

//体系类别
function fillSystemTypeSelectAndDefault(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
	/**
		var data=[];
		var obj1={"systemType":"appraise","systemTypeName":"评价类别"};
		var obj2={"systemType":"stat","systemTypeName":"统计类别"};
		data.push(obj1);
		data.push(obj2);
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"systemType","systemTypeName",true);
		}
	*/	
	for(var i=0;i<ids.length;i++){
		var url = basePath+"jysoft/servlet/systemTypeServlet.do?method=fillSystemType&etc="+new Date().getTime();
		var loader=dhtmlxAjax.getSync(url);
		var resultText = loader.xmlDoc.responseText;
		var data = JSON.parse(resultText);
		if(!data.errorCode){
				fillSelect(ids[i],data,"typeId","typeName",true);
				setSelectByValue(ids[i],'appraise');
		} 
		}
	}
}

//角色
function fillRoleSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/indexRight.do?method=getAllRoles&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse(resultText);// 转成对象
		if(!data.errorCode){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data,"roleId","roleName");
			}
		}	
	}
}
//历史版本填充
function fillVersionSelect(ids,writeId){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		dhtmlxAjax.get(basePath+"jysoft/servlet/indexWriteHistoryServlet.do?method=queryVersionsByWriteId&etc="+new Date().getTime()+"&writeId="+writeId,function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"versionId","versionName",true);
				}
			}
		});
	}
}
//指标单位填充
function fillunitNameSelect(data){
		var data_url=(basePath+"jysoft/servlet/indexUnit.do?method=queryAll&etc="+new Date().getTime());
			dhtmlxAjax.get(data_url,function(loader){
				var resultText=loader.xmlDoc.responseText;//ajax result [string]
				var data=JSON.parse(resultText);// 转成JSON对象
				if(!data.errorCode){//不是错误对象
					combo_unitName=new dhtmlXCombo("combo_unitName","",400);
					combo_unitName.attachEvent("onOpen",txtChange);
					for(var i=0;i<data.length;i++){
	    				combo_unitName.addOption(data[i]["unitName"],data[i]["unitName"]);
		    		}
				}else
					alert(data.errorCode);
			});
}
//初始化年份
//参数为年份下拉框ID或下拉框ID集合
function fillYearSelect(ids){
	var begin=2005;
	var end=2020;
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var data=[];
		for(var i=begin;i<=end;i++){
			var c={};
			c.year=i;
			data.push(c);			
		}
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"year","year",true);
			setDefaultFullYearSelect(ids[i]);
		}
	}
}
//设置默认选择当前年份
function setDefaultYearSelect(el){
	var date=new Date();
	var year=date.getYear();
	setSelectByValue(el,year);
}
//设置默认选择当前年份，全称
function setDefaultFullYearSelect(el){
       
	var date=new Date();
	var year=date.getFullYear();
	setSelectByValue(el,year);
}


//选中某下拉框的某个值
function setSelectByValue(el,optionValue){
	el=typeof el=="string"?document.getElementById(el):el;//本身可以为元素ID或元素对象
	if(el&&el.options&&el.options.length>0){
		for(var i=0;i<el.options.length;i++){
			if(el.options[i].value==optionValue){
				el.options[i].selected=true;
				break;
			}		
		}
	}
}

//初始化周期代默认值的，默认值是月报
//参数为周期下拉框ID或下拉框ID集合
function fillPeriodSelectAndDefault(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		dhtmlxAjax.get(basePath+"jysoft/servlet/appraiseCycle.do?method=queryAll",function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"cycleId","cycleName",true);
					setSelectByValue(ids[i],'05');
				}
			}
		});
	}
}
//初始化周期
//参数为周期下拉框ID或下拉框ID集合
/*
function fillPeriodSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		dhtmlxAjax.get(basePath+"jysoft/servlet/appraiseCycle.do?method=queryAll",function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"cycleId","cycleName",true);
				}
			}
		});
	}
}
*/

//初始化周期--分析
/*
* 因调用的地方过多，本应删除此方法，在0816日期内暂时保留原接口代码
*/
function fillTopicIdByTopIdSelect(ids,topicId){
	fillPeriodByTopIdSelect(ids,topicId);
}
//主题
function fillTopicSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/indexSystem.do?method=getRootIndex&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse(resultText);// 转成对象
		if(!data.errorCode){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data,"indexId","indexName");
			}
		}			
	}
}
//初始化周期--方案,填报
/**
* 陶富改于20100816，解决多个页面重复代码问题，此处问题严重以后待整改
* 修改topicId 为外部获取。
*/

function fillPeriodByTopIdSelect(ids,topicId){
		if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var params = "&topicId="+topicId;
		dhtmlxAjax.post(basePath+"jysoft/servlet/appraiseCycle.do?method=queryAllByTopicId&etc="+new Date().getTime(),params,function(loader){
			var resultText=loader.xmlDoc.responseText;//ajax result [string]
			var data=JSON.parse(resultText);// 转成对象
			if(!data.errorCode){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data,"cycleId","cycleName",true,1);
				}
			}
		});
	}
}
//或者周期类型
function getPeriodTypeName(cycleId){
	var cycleName;
	switch(cycleId){
		case "01":
			cycleName="年报";
			break;
		case "02":
			cycleName="半年报";
			break;
		case "03":
			cycleName="季报";
			break;
		case "04":
			cycleName="双月报";
			break;
		case "05":
			cycleName="月报";
			break;
		case "06":
			cycleName="周报";
			break;
	 }
	 return cycleName;
}
//周期变换成中文显示
function getReportDateName(reportDate){
	var year=reportDate.substring(0,4);
	var cycle=reportDate.substring(4,6);
	var cycleNum=reportDate.substring(6,10);
	
	 return year+"年第"+Number(cycleNum).toFixed(0)+"期"+getPeriodTypeName(cycle);
}
//主题修改事件
var reportYear=createCycleNums('期',1,1);//年份周期
var reportHalfYear=createCycleNums('期',1,2);//半年周期
var reportSeason=createCycleNums('期',1,4);//季度周期
var reportDoubleM=createCycleNums('期',1,6);//双月周期
var reportM=createCycleNums('期',1,12);//月份周期
var reportMonth=createCycleNums('月',1,12);//月份
var reportWeek=createWeekNums('周',1,53);//周数；
//构造周期基础数组的方法
function createCycleNums(textSuffix,begin,end){
	if(end>=begin){
		var arr=[];
		for(var i=begin;i<=end;i++){
			var cycleNum={};
			cycleNum.text=(i>9?""+i:"0"+i)+textSuffix;
			cycleNum.val=i>9?""+i:"0"+i;	
			arr.push(cycleNum);		
		}
		return arr;	
	}
	return null;
}
//构造周期基础数组的方法
function createWeekNums(textSuffix,begin,end){
	if(end>=begin){
		var arr=[];
		for(var i=begin;i<=end;i++){
			var cycleNum={};
			cycleNum.text="第"+(i>9?""+i:"0"+i)+textSuffix;
			cycleNum.val=i>9?""+i:"0"+i;	
			arr.push(cycleNum);		
		}
		return arr;	
	}
	return null;
}
//针对填报填充周期下拉框
function fillCycleNumSelect_tb(selectId,period){
     document.getElementById("startDate").value="";
	 document.getElementById("endDate").value="";
	switch(period){
		case "01":
			fillSelect(selectId,reportYear,"val","text",true);
			break;
		case "02":
			fillSelect(selectId,reportHalfYear,"val","text",true);
			break;
		case "03":
			fillSelect(selectId,reportSeason,"val","text",true);
			break;
		case "04":
			fillSelect(selectId,reportDoubleM,"val","text",true);
			break;
		case "05":
			fillSelect(selectId,reportM,"val","text",true);
			break;
	}
}
//填充期数下拉框
function fillCycleNumSelect(selectId,period){
	switch(period){
		case "01":
			fillSelect(selectId,reportYear,"val","text");
			break;
		case "02":
			fillSelect(selectId,reportHalfYear,"val","text");
			break;
		case "03":
			fillSelect(selectId,reportSeason,"val","text");
			break;
		case "04":
			fillSelect(selectId,reportDoubleM,"val","text");
			break;
		case "05":
			fillSelect(selectId,reportM,"val","text");
			break;
	}
}
//填充月数下拉框
function fillMonthNumSelect(selectId){
  fillSelect(selectId,reportMonth,"val","text");
}
//分析主题周期级联事件
function attachTopicIdChangeEvent(topicId,cycleId,systemId){
	var el=document.getElementById(topicId);//主题下拉框
	el.onchange=function(){fillPeriodByTopIdSelect(cycleId,this.value);
	  var param=document.getElementById(systemId);
		if(param&&!isEmptyORNull(param.value)){
		   systemTypeChangeEvent();
		}
	}//主题变动时填充周期类别下拉框
}

//填报，方案主题周期级联事件
/*
* 因调用的地方过多，本应删除此方法，在0816日期内暂时保留原接口代码
*/
function attachAppTopicIdChangeEvent(theme,period){
	attachTopicIdChangeEvent(theme,period);
}

function attachPeriodChangeEventForChannelDataFile(period,cycleNum){
	var el=document.getElementById(period);//周期类别下拉框
	fillCycleNumSelect(cycleNum,"05");
	//el.onchange=function(){fillCycleNumSelect(cycleNum,this.value);}//周期类别变动时填充周期下拉框
}

function attachPeriodChangeEvent(period,cycleNum){
	var el=document.getElementById(period);//周期类别下拉框
	el.onchange=function(){fillCycleNumSelect(cycleNum,this.value);}//周期类别变动时填充周期下拉框
}

//根据周期填充期数
function fillCucleNumEvent(cycleId,cycleNum){
	var value=document.getElementById(cycleId).value;//周期类别下拉框
	fillCycleNumSelect(cycleNum,value);
}
 
/*
* 因调用的地方过多，本应删除此方法，在0816日期内暂时保留原接口代码
*/
function attachPeriodChangeEvent_tb(period,cycleNum){
	attachPeriodChangeEvent(period,cycleNum);
}
//填充报表日期下拉框
//填充日期combo
function loadDate(date,topicId,cycleId,systemType,corpCode,format){
	var fmt=format||1;
	if(date){
		date.unSelectOption();
		date.clearAll();
		date.setComboText("");
		var params = "&indexId="+topicId+"&cycleId="+cycleId;
		params+="&systemType="+systemType;
		params+="&corpCode="+corpCode;
		params+="&etc="+new Date().getTime();
		var url = basePath+"jysoft/servlet/analysis.do?method=queryIndexWrite"+params;
		var loader=dhtmlxAjax.getSync(url);
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse(resultText);// 转成对象
		if(!data.errorCode){
			for(var i=0;i<data.length;i++){
				if(data[i].writeState > 4)
					date.addOption([[data[i].reportDate, getReportDate(data[i].reportDate,fmt)]]);
			}
		}
	}
}

/**
	生成guid
*/
function newGuid(p_toLowerCase, p_length){
    var toLowerCase = p_toLowerCase||false;
    var length =p_length|| 42;
    var result = "";
    for (var i = 1; i <= length; i++) {
        var n = Math.floor(Math.random() * 16.0);
        if (n < 10){
            result += n;            
        }
        else if (n == 10){
            result += "a";
        }
        else if (n == 11){
            result += "b";
        }
        else if (n == 12){
            result += "c";
        }
        else if (n == 13){
            result += "d";
        }
        else if (n == 14){
            result += "e";
        }
        else if (n == 15){
            result += "f";
        }
        /*
        if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
        {
            result += "-";
        }
        */
    }
    if (toLowerCase){
        result = result.toLowerCase();
    }
    else{
        result = result.toUpperCase();
    }
    return result;
}
//去除字符串首尾空白符号
String.prototype.trim=function(){
	return this.replace(/^\s+|\s+$/g,'');
}	

var dayTime = " 00:00:00"; //每天的什么时间
var monthDay = "-21";  //每月的第26天
	//获取开始时间
function getStartDate(curYear,cycleId,cycleNum){
		var dateSpace=""; 
		var tempNum = "";
		var tempYear = "";
		if(cycleId=='01') dateSpace = (curYear-1)+"-"+(cycleNum*12)+monthDay+dayTime;   //年报
		if(cycleId=='02') {
			if(((cycleNum*6)-6)=='0'){
				tempYear = curYear-1;
				tempNum = 12;
			}else{
			 	tempYear = curYear;
			 	tempNum = ((cycleNum*6)-6);
			 	tempNum = "0"+tempNum;
			 }
			dateSpace = tempYear+"-"+tempNum+monthDay+dayTime;  //半年报
		}
		if(cycleId=='03'){
			if(((cycleNum*3)-3)=='0'){
				tempYear = curYear-1;
				tempNum = 12;
			}else{
			 	tempYear = curYear;
			 	tempNum = "0"+((cycleNum*3)-3);
			 }
			dateSpace = tempYear+"-"+tempNum+monthDay+dayTime; //季报
		}
		 
		if(cycleId=='04'){
			if(((cycleNum*2)-2)=='0'){
				tempYear = curYear-1;
				tempNum = 12;
			}else{
			 	tempYear = curYear;
			 	tempNum = ((cycleNum*2)-2);
			 }
			 if(tempNum<10) tempNum = "0"+tempNum;
			dateSpace = tempYear+"-"+tempNum+monthDay+dayTime; //双月报
		} 
		if(cycleId=='05'){
			if(cycleNum-1=='0'){
				tempYear = curYear-1;
				tempNum = 12;
			}else{
			 	tempYear = curYear;
			 	tempNum = cycleNum-1;
			 }
			if(tempNum<10) tempNum = "0"+tempNum;
			dateSpace = tempYear+"-"+tempNum+monthDay+dayTime; //月报
		} 
		return dateSpace;
	}
	
//获取结束时间
function getEndDate(curYear,cycleId,cycleNum){
		var endDate=""; 
		if(cycleId=='01') endDate = curYear+"-"+(cycleNum*12)+monthDay+dayTime;   //年报
		if(cycleId=='02'){
			if((cycleNum*6)<10) tempNum = "0"+(cycleNum*6);
			else tempNum = (cycleNum*6);
			endDate = curYear+"-"+tempNum+monthDay+dayTime;  //半年报
		} 
		if(cycleId=='03'){
			if((cycleNum*3)<10) tempNum = "0"+(cycleNum*3);
			else tempNum = (cycleNum*3);
			endDate = curYear+"-"+tempNum+monthDay+dayTime; //季报
		} 
		if(cycleId=='04') {
			if((cycleNum*2)<10) tempNum = "0"+(cycleNum*2);
			else tempNum = (cycleNum*2);
			endDate = curYear+"-"+tempNum+monthDay+dayTime; //双月报
		}
		if(cycleId=='05') endDate = curYear+"-"+cycleNum+monthDay+dayTime; //月报
		return endDate;
	}

function initConditionCell(cell,formId){
	var form = document.getElementById(formId);
	var arr = form.getElementsByTagName("label");//取出所有label
	var els = [];//去除数组中属性为隐藏的label
	for(var i=0;i < arr.length ; i++){
		if(!arr[i].style.display||arr[i].style.display=="block"){
			els.push(arr[i]);
		}
	}
	var labelWidth = els.length>5?'23%':((100-8)/els.length+"%");//标签长度百分比
	var cols = els.length>5? 4 : els.length;//每页显示列宽
	if(els.length>0&&cell&&cell.setHeight){
		var rows=(els.length/cols).toFixed(0); //实际行数
		cell.setHeight(rows*25+50);
	 	for(var i=0;i<els.length;i++){
	 		els[i].style.width = labelWidth;
	 	}
	}
}
/**
*初始化日历控件，
* 1.必要参数input_id为表单text的ID
* 2.非必要参数conf，为拓展配置，目前支持timeSuffix默认时间，dateFormat 日期格式
*/
function  initCalendar(input_id,conf){
	var conf=conf||{};
	var cal1 = new dhtmlxCalendarObject(input_id, false, {
				isYearEditable : true,
				isMonthEditable : true,
				skin : "dhx_skyblue"
			});
	cal1.timeSuffix = conf.timeSuffix||"00:00:00";
	cal1.setYearsRange(2000, 2020);
	cal1.loadUserLanguage('zh_CN');
	cal1.setDateFormat(conf.dateFormat||"%Y-%m-%d %H:%i:%s");
	cal1.draw();
	cal1.setSkin("dhx_skyblue");
	cal1.hide();
	
	document.getElementById(input_id).onclick=function (){
		var initTexts=["-- 开 始 --","-- 结 束 --",""];
		var initValue=this.value;
		
		if(isInit()){
			var d=new Date();
			cal1.setDate(d.getYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+cal1.timeSuffix);//将初始值显示为当前时间
		}
		//判断是否初始值
		function isInit(){
		 	var flag=false;
		 	for(var i=0;i<initTexts.length;i++){
		 		if(initValue==initTexts[i]){
		 			flag=true;
		 			break;
		 		}
		 	}
			return flag;
		}
	}
	return cal1;
}


//判断是否启用流程
function isEnableAppraiseAudit(){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/common.do?method=isEnableAppraiseAudit&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		if(resultText=='"true"'){
			return true;
		}else{
			return false;
		}
	}

//获取公司级别
function getCompanyLevel(){
		var loader=dhtmlxAjax.getSync(basePath+"jysoft/servlet/common.do?method=getCompanyLevel&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		return resultText;
	}

//长字符串截取
function getLongStringFormat(val,maxlength){
	var formatString="";
	var defaultLength=10;
	if(!(val && typeof(val)=='string')){
		return;
	}
	if(maxlength && typeof(maxlength)=='number'){
		defaultLength=maxlength;
	}
	if(val.length>defaultLength){
		var count=Math.floor(val.length/defaultLength);
		var remain=val.length%defaultLength;
		for(var i=0;i<count;i++){
			formatString+=val.substring(i*defaultLength,i*defaultLength+defaultLength)+"<br>";
		}
		formatString+=val.substring(count*defaultLength,count*defaultLength+defaultLength);
	}else{
		formatString+=val;
	}
	return formatString;
}
//判断变量value是否为空
function isEmptyORNull(value){
    if(value!=null&&value!="null"&&value.replace(/[ ]/g,"")!=""){
        return false;
    }else{
      return true;
    }
}

//删除左右两端的空格
function trim(value){
    if(isEmptyORNull(value)){
      return "";
    }
  return value.replace(/(^\s*)|(\s*$)/g, "");
}


