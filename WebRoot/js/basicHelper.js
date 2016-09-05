//获得年份下拉框
function initYearSelect(ids){
	var currYear=new Date().getYear();
	var data=[];	
	for(var i=2012;i<2021;i++){
		var obj={'id':i,'name':i+'年'};
		data.push(obj);
	}
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	var selectIndex=currYear-2012;
	
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			
			fillSelect(ids[i],data,"id","name",false,selectIndex);
		}
	}
}
//获取月份
function initMonthSelect(ids){
	var currMonth=new Date().getMonth();
	var data=[];	
	for(var i=1;i<13;i++){
		if (i != 3 && i != 6 && i != 9 && i != 12){
			var obj={'id':i,'name':i+'月'};
			data.push(obj);
		}	
	}
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	var selectIndex=currMonth;
	
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			
			fillSelect(ids[i],data,"id","name",false);
		}
	}
}
//任职资格级别
function initQualificSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"qualific/getQualificInfoByStatus.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//能力素质要项，所有叶子
function initAbilityItemSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"abilityItem_json/getAllChild.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//单位模块
function initDeptGroupSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"base/allDeptsGroup.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//主考部门
function initMainDeptSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"base/queryMainDeptsForGrid.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//指标分类
function initContentTypeSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"basic/allContentType.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//业务分类
function initBusinessTypeSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"basic/allBusinessType.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",true);
			}
		}
	}
}

//指标性质
function initPropertySelect(ids){
	var data=[];
	var obj={'id':'havePro','name':'原有'};
	data.push(obj);
	var obj={'id':'addPro','name':'新增'};
	data.push(obj);
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

//精确度
function initPrecisionSelect(ids){
	var data=[];
	var obj={'id':'0','name':'整数'};
	data.push(obj);
	for(var i=1;i<=4;i++){
		var obj={'id':i,'name':"小数点后"+i+'位'};
		data.push(obj);
	}
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}


//周期
function initPeriodSelect(ids){
	var data=[];	
	var data=[];
	var obj={'id':'1','name':'年度'};
	data.push(obj);
	var obj={'id':'2','name':'季度'};
	data.push(obj);
	var obj={'id':'3','name':'月度'};
	data.push(obj);
	
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

//指标单位
function initUnitSelect(ids){
	var data=[];	
	var data=[];
	var obj={'id':'元','name':'元'};
	data.push(obj);
	var obj={'id':'万元','name':'万元'};
	data.push(obj);
	var obj={'id':'%','name':'%'};
	data.push(obj);
	var obj={'id':'篇','name':'篇'};
	data.push(obj);
	var obj={'id':'次','name':'次'};
	data.push(obj);
	var obj={'id':'个','name':'个'};
	data.push(obj);
	var obj={'id':'部','name':'部'};
	data.push(obj);
	
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

//指标属性
function initAttributeSelect(ids){
	var data=[];
	var obj={'id':'1','name':'考核'};
	data.push(obj);
	var obj={'id':'2','name':'考察'};
	data.push(obj);
	
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

//计分方式
function initCalcTypeSelect(ids){
	var data=[];
	var obj={'id':'1','name':'得分'};
	data.push(obj);
	var obj={'id':'2','name':'附加'};
	data.push(obj);
	var obj={'id':'3','name':'扣分'};
	data.push(obj);
	
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

//初始化期数选择
function initIssueSelect(ids){
	var d=new Date();
	var month=d.getMonth()+1;
	var selectIndex=0;
	var data=[];
	var obj={'id':'1','name':'一期'};
	data.push(obj);
	var obj={'id':'2','name':'二期'};
	data.push(obj);
	var obj={'id':'3','name':'三期'};
//	data.push(obj);
//	var obj={'id':'4','name':'四期'};
	data.push(obj);
	switch(month){
		case 1:
		case 2:
		case 3:
			selectIndex=1;
			break;
		case 4:
		case 5:
		case 6:
			selectIndex=2;
			break;
		case 7:
		case 8:
		case 9:
			selectIndex=3;
			break;
//		case 10:
//		case 11:
//		case 12:
//			selectIndex=3;
//			break;
		default:
			selectIndex=0;
		break;
	}
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true,selectIndex);
		}
	}
}

//初始化协考部门窗口
function initSecondDeptWin(valueId,displayId){
		secondDeptWin=dhxLayout.dhxWins.createWindow("secondDeptWin",0, 0, 550,300);
		secondDeptWin.setText("选择协考部门");
		secondDeptWin.center();
		secondDeptWin.button("close").attachEvent("onClick", function(){
			secondDeptWin.setModal(false);
			secondDeptWin.hide();
		});
		
		
		myLayout = secondDeptWin.attachLayout("1c");
		myLayout.cells("a").hideHeader();
		
		initSecondGrid();  //实始化协考部门的grid表头
		
		var tool=secondDeptWin.attachToolbar();
		tool.setIconsPath(BASE_PATH+"dhtmlx2.6/imgs/common/");
        tool.addButton("saveSecondDept", 1, "保存", "save.gif", "save.gif");
		tool.addButton("close", 2, "关闭", "hide.gif", "hide.gif");
		tool.attachEvent("onClick",function(id){
			if(id=="saveSecondDept"){
				saveSecondDept(valueId,displayId);
			}else if(id=="close"){
				secondDeptWin.setModal(false);
				secondDeptWin.hide();
			}
		});
		
		loadSecondGrid("");
		secondDeptWin.attachEvent("onHide",function(win){ 
			secondDeptWin.setModal(false); 
		});  
		secondDeptWin.setModal(false);
		secondDeptWin.hide();
}

//初始化年度考核单位类型窗口
function initDeptsTypeWin(valueId,displayId){
	deptsTypeWin=dhxLayout.dhxWins.createWindow("deptsTypeWin",0, 0, 300,300);
	deptsTypeWin.setText("选择考核单位");
	deptsTypeWin.center();
	deptsTypeWin.button("close").attachEvent("onClick", function(){
		deptsTypeWin.setModal(false);
		deptsTypeWin.hide();
	});
	var tool=deptsTypeWin.attachToolbar();
	tool.setIconsPath(BASE_PATH+"dhtmlx2.6/imgs/common/");
    tool.addButton("saveDeptsType", 1, "确认", "save.gif", "save.gif");
	tool.addButton("close", 2, "关闭", "hide.gif", "hide.gif");
	tool.attachEvent("onClick",function(id){
		if(id=="saveDeptsType"){
			saveDeptsType(valueId,displayId);
		}else if(id=="close"){
			deptsTypeWin.setModal(false);
			deptsTypeWin.hide();
		}
	});
	
	initDeptsTypeTree("");
	
	deptsTypeWin.setModal(false);
	deptsTypeWin.hide();
}

//初始化年度考核单位树
function initDeptsTypeTree(checkValue){
	tree = deptsTypeWin.attachTree();
	tree.setSkin('dhx_skyblue');		
	tree.setImagePath(BASE_PATH+"dhtmlx2.6/imgs/csh_dhx_skyblue/");
	tree.setStdImages("folderClosed.gif", "folderOpen.gif", "folderClosed.gif");
	tree.enableCheckBoxes(true);
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);//是否为默认级联多选状态
	
	tree.attachEvent("onCheck", function(id,state){
		//将2级的没有子级公司的复选框设置为不可选
		var level = tree.getLevel(id);
		if(level==2){
			if(tree.hasChildren(id)==0){
				tree.setCheck(id,false);
			}
		}
	});
	
	var url=BASE_PATH+"index/queryDeptTypesTree.action?etc="+new Date().getTime(); 
	tree.setXMLAutoLoading(url);
	
	
	
	tree.loadXML(url,function(){
		tree.openItem(tree.getItemIdByIndex("0",0));
		if(checkValue==null || checkValue==""){
		}else{
			var check = checkValue.split(",");
			
			for(var i = 0;i < check.length;i++){
				tree.setCheck(check[i],1);
			}
		}
	});
}

//初始化并加载协考部门数据
function initSecondGrid(){
	secondDeptGrid = myLayout.cells("a").attachGrid();
	secondDeptGrid.setHeader("#master_checkbox,id,协考单位名称");
	secondDeptGrid.setInitWidthsP("20,0,80");
    secondDeptGrid.setColAlign("center,center,center");
    secondDeptGrid.setColTypes("ch,ro,ro");
    secondDeptGrid.setSkin("dhx_skyblue");
    secondDeptGrid.init();
    secondDeptGrid.setColumnHidden(1,true);
}

//加载协考部门
function  loadSecondGrid(checkObj){
   	var url=BASE_PATH+"base/querySecondDeptsForGrid.action?etc="+new Date().getTime();
   	secondDeptGrid.clearAndLoad(url,function(){
   		var ids = "";
   		 secondDeptGrid.forEachRow(function(id){
      		ids = secondDeptGrid.cells2(secondDeptGrid.getRowIndex(id),1).getValue();
      		var ides = ids.split(",");
      		var checkId = checkObj.split(",");
      		for(var j =0;j< checkId.length;j++){
      			for(var i = 0 ;i < ides.length;i++){
					if(ides[i]==checkId[j]){
						secondDeptGrid.cellById(ides[i],0).setValue(1);
					}
				}
      		}
   		 });
   	},"json");
}

//保存选择的协考部门数据
function saveSecondDept(valueId,displayId){
	var id = secondDeptGrid.getCheckedRows(0);
	if(id){
		var ids = id.split(",");
		var name = "";
		for(var i = 0; i < ids.length;i++){
			name += secondDeptGrid.cells2(secondDeptGrid.getRowIndex(ids[i]),2).getValue()+",";
		}
		name = name.substring(0, name.length - 1);  //去除最后一位的逗号
		document.getElementById(valueId).value=ids;
		document.getElementById(displayId).value=name;
		document.getElementById(displayId).title=name;
		secondDeptWin.setModal(false);
		secondDeptWin.hide();
	}else{
		//alert("当前没有选择协考部门");
		secondDeptWin.setModal(false);
		secondDeptWin.hide();
		document.getElementById(valueId).value="";
		document.getElementById(displayId).value="";
	}
}

//获取考核单位类型文本
function getCheckDeptsTypeText(){	
	var selectTexts = "";
	var rootId = tree.getItemIdByIndex(0,0);
	var checkState = tree.isItemChecked(rootId);
	if(checkState==1){
		selectTexts = tree.getItemText(rootId);
	}
	else if(checkState==2){
		var subIds = tree.getSubItems(rootId);
		var ids = subIds.split(",");
		
		for(var i = 0; i< ids.length;i++){
			if(tree.isItemChecked(ids[i])==1){
				selectTexts +=","+tree.getItemText(ids[i]);
			}			
		}
		
		for(var i = 0; i< ids.length;i++){					
			if(tree.isItemChecked(ids[i])==2){	
				var subIdsCompany = tree.getSubItems(ids[i]);				
				var idCompanys = subIdsCompany.split(",");
				for(var j = 0; j< idCompanys.length;j++){
					if(tree.isItemChecked(idCompanys[j])==1)					
						selectTexts +=","+tree.getItemText(idCompanys[j]);
				}			
			}
		}
	}else
	{
		selectTexts="";
	}
	return selectTexts;	
}

//获取考核单位类型id
function getCheckDeptsTypeIds(id){
	var selectIds = "";
	var selectTexts = "";
	var parentId="";
	var parentText="";
	//获取所选择的单位编号
	if(id!=null && id.length>0){
		var ids = id.split(",");
		for(var i = 0; i < ids.length;i++){
			 var level = tree.getLevel(ids[i]);
			if(level==3){
				selectIds +=","+ids[i];
			}
		}
	}
	return selectIds;
}

//保存考核单位类型选择的数据并将选择页面关闭
function saveDeptsType(valueId,displayId){
	var id = tree.getAllChecked();
	if(id!=null && id!=""){
		var selectTexts = getCheckDeptsTypeText();
		var selectIds = getCheckDeptsTypeIds(id);
		//获取选择的单位类型的第一位值
		var firstI = selectIds.charAt(0);
		var firstN = selectTexts.charAt(0);
		//如果第一位值是逗号，则将其逗号去除
		if(firstI==','){
			selectIds = selectIds.substring(1, selectIds.length);  //去除第一位的逗号
		}
		if(firstN==','){
			selectTexts = selectTexts.substring(1, selectTexts.length);  //去除第一位的逗号
		}
		
		document.getElementById(valueId).value=selectIds;
		document.getElementById(displayId).value=selectTexts;
		
		deptsTypeWin.setModal(false);
		deptsTypeWin.hide();
	}else{
		alert("当前没有选择考核单位类型数据");
		document.getElementById(valueId).value="";
		document.getElementById(displayId).value="";
		deptsTypeWin.setModal(false);
		deptsTypeWin.hide();
	}
}

//加载指标集里的复制年份数据
function initIndexFlowSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"index/allIndexFlow.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				for(var k=0;k<data[0].length;k++){
					data[0][k].displayYear=data[0][k].year+"年";
				}
				fillSelect(ids[i],data[0],"year","displayYear",true);
			}
		}
	}
}

//清除掉已经存在的年份数据
function removeExistsYears(yearSelect,copyYearSelect){
	var yearResOpt = document.getElementById(yearSelect).options;
	var copyYearOpt = document.getElementById(copyYearSelect).options;
	for(var j = 0;j < copyYearOpt.length;j++){
		for(var i = 0; i < yearResOpt.length;i++){
			var value = yearResOpt[i].value;
			if(value!="" && copyYearOpt[j].value!=""){
				if(value==copyYearOpt[j].value){
					yearResOpt.remove(i);
				}
			}
		}
	}
}

//设置表单部门值，默认为当前用户的所在部门
function setDeptValue(id){
	var url =  BASE_PATH+"base/getCurrentUserDeptId.action?etc="+new Date().getTime();
	dhtmlxAjax.get(url,function(loader){
		var resultText = loader.xmlDoc.responseText;
		var data = JSON.parse("["+resultText+"]");
		if(data[0].success){
			document.getElementById(id).value=data[0].msg;
		}
	});
}
//获得是否下拉框
function initTrueFalseSelect(ids){
	var data=[];
	var obj={'id':'T','name':'是'};
	data.push(obj);
	var obj={'id':'F','name':'否'};
	data.push(obj);
	
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		for(var i=0;i<ids.length;i++){
			fillSelect(ids[i],data,"id","name",true);
		}
	}
}

function createCondition(ids){
	var url = BASE_PATH+"basic/allTopicType.action?etc="+new Date().getTime();
	dhtmlxAjax.post(url,"",function(loader){
		var condition = "";
		var json = eval("[" + loader.xmlDoc.responseText + "]");
		for(var i =0;i<json[0].length;i++){
			if(i>0)
				condition +='&nbsp;'+ '<input type="radio" name="q_topicType" value="'+json[0][i].id+'"/>'+json[0][i].name;
			else
				condition +='&nbsp;'+ '<input type="radio" name="q_topicType" checked="checked" value="'+json[0][i].id+'"/>'+json[0][i].name;

		}
		document.getElementById(ids).innerHTML = condition;
	});
}

//获取当前用户的所在部门
function getCurrentUserDeptIds(){
	var url =  BASE_PATH+"base/getCurrentUserDeptId.action?etc="+new Date().getTime();
	var loader=dhtmlxAjax.getSync(url);
	var resultText=loader.xmlDoc.responseText;//ajax result [string]
	var data=JSON.parse("["+resultText+"]");// 转成对象
	if(data[0].success){
		return data[0].msg;
	}
}	

    
//显示指标详情
function showIndexDetail(idAndYear){
	var arr=(idAndYear+"").split("^^^^^");
	var id=arr[0];
	var year=arr[1];
	var url=BASE_PATH+"pams/index/showIndexDetail.jsp?etc="+new Date().getTime()+"&year="+year+"&id="+id;
	window.showModalDialog(url,'指标详情','dialogWidth=800px;dialogHeight=560px');
}

//获取当前年份所有考核的关键指标,返回map
function queryAllIndexDetail(q_year){
	var map = [];
	var _url = BASE_PATH + "index/queryIndexDetailsByYear.action?etc="
			+ new Date().getTime() + "&year=" + q_year;
	var loader = dhtmlxAjax.getSync(_url);
	var resultText = loader.xmlDoc.responseText;//ajax result [string]
	var data = JSON.parse(resultText);// 转成对象
	for (var i = 0; i < data.length; i++) {
		var key=data[i].indexId;
     	var value=data[i];
        map[key]=value;
	}
	return map;
}

//获取当前年份所有考核的关键指标,返回map
function queryIndexDetailsByYearAndBusinessType(q_year,businessType){
	var map = [];
	var _url = BASE_PATH + "performance/queryIndexDetailsByYearAndBusinessType.action?etc="
			+ new Date().getTime() + "&year=" + q_year+"&businessType="+businessType;
	var loader = dhtmlxAjax.getSync(_url);
	var resultText = loader.xmlDoc.responseText;//ajax result [string]
	var data = JSON.parse(resultText);// 转成对象
	for (var i = 0; i < data.length; i++) {
		var key=data[i].indexId;
     	var value=data[i].dataType;
        map[key]=value;
	}
	return map;
}



/** 对单元格数据进行验证* */
function validateCellChange(rId, cind, title,grid) {
	if (title) {
		grid.cellById(rId, cind).cell._attrs.title = "错误信息提示：" + title;
		grid.setCellTextStyle(rId, cind, "color:red;border:1px solid gray;");
	} else {
		grid.setCellTextStyle(rId, cind, "");
	}
}

//初始化板块
function initDeptsGroupSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"base/allDeptsGroup.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				
				fillSelect(ids[i],data[0],"id","name",true);
				var gs = document.getElementById("q_groupId");
				gs.options[0]=new Option("汇总","1");
				gs.selectedIndex=0;
			}
		}
	}
}

//初始化指标
function initIndexOfNumberSelect(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var year = document.getElementById("divYear").value;
		var loader=dhtmlxAjax.getSync(BASE_PATH+"index/queryIndexOfDataTypeIsNumber.action?year="+year+"&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"indexId","indexName",true);
			}
		}
	}
	
}

function initBusinessType(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"index/queryIndexOfDataTypeIsNumber.action?year="+year+"&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"indexId","indexName",true);
			}
		}
	}
}
//==================================员工职业通道====================================
//初始化职类，职序，职系，职种下拉框（不进行上下级关联）
function initPositionType(ids,type){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"postType/queryPositionByTypeId.action?actionString="+type+"&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",false);
			}
		}
	}
}

//初始化申报的等级
function initPostType(ids,type,ispg){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"authenticDeclare/isCanShowCombData.action?actionString="+type+"&data="+ispg+"&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		
		if(data[0]&&data[0].message==null){
			var loader1=dhtmlxAjax.getSync(BASE_PATH+"authenticDeclare/getCombData.action?actionString="+type+"&data="+ispg+"&etc="+new Date().getTime());
			var resultText1=loader1.xmlDoc.responseText;//ajax result [string]
			var data1=JSON.parse("["+resultText1+"]");// 转成对象
			if(data1[0]){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data1[0],"id","name",false);
				}
			}
		}else{
			alert(data[0].message);
			return false;
		}
	}
	return true;
}

//初始化申报的等级
function initAllPostType(ids){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"authenticDeclare/getAllCombData.action?etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
				for(var i=0;i<ids.length;i++){
					fillSelect(ids[i],data[0],"id","name",false);
				}
		}
	}
}

//初始化申报的等级(id:申报Id)
function initPostTypeLevel(ids,id){
	if(ids&&typeof ids=='string'){
		ids=[ids];
	}
	if(ids&&ids.length>0){
		var loader=dhtmlxAjax.getSync(BASE_PATH+"authenticDeclareAudit/getResetLevelComb.action?actionString="+id+"&etc="+new Date().getTime());
		var resultText=loader.xmlDoc.responseText;//ajax result [string]
		var data=JSON.parse("["+resultText+"]");// 转成对象
		if(data[0]){
			for(var i=0;i<ids.length;i++){
				fillSelect(ids[i],data[0],"id","name",false);
			}
		}
	}
}

//获取审核状态
function getStatusData(){
	var data=[];
	var status1={"id":10,"text":"破格未提交"};
	var status2={"id":20,"text":"破格提交待审核"};
	var status3={"id":30,"text":"正常申报未提交"};
	var status4={"id":40,"text":"待评分"};
	var status5={"id":50,"text":"已评分"};
	var status6={"id":60,"text":"评价驳回"};
	var status7={"id":70,"text":"待审核"};
	var status8={"id":80,"text":"审核通过"};
	var status9={"id":90,"text":"审核不通过"};
	var status10={"id":100,"text":"待考察"};
	var status11={"id":110,"text":"考察通过"};
	var status12={"id":120,"text":"考察不通过"};
	var status13={"id":150,"text":"认证流程结束"};
	data.push(status1);
	data.push(status2);
	data.push(status3);
	data.push(status4);
	data.push(status5);
	data.push(status6);
	data.push(status7);
	data.push(status8);
	data.push(status9);
	data.push(status10);
	data.push(status11);
	data.push(status12);
	data.push(status13);
	return data;
}

//验证数据
function vailData(rId,cind,myGrid,title){
	var value=myGrid.cellById(rId,cind).getValue();
	if(value==null || value==""){
		myGrid.cellById(rId, cind).cell._attrs.title = "错误信息："+title;
		myGrid.setCellTextStyle(rId, cind,
		"color:red;border:1px solid red;");
		return false;
	}else{
		myGrid.cellById(rId, cind).cell._attrs.title = "";
		myGrid.setCellTextStyle(rId, cind, "color:black;");
		return true;
	}
}

