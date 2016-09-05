var swfFileArray=[];//现有支持的图片类型数组，下面为数组内值
swfFileArray.push("AngularGauge.swf");     	//仪表盘
swfFileArray.push("Bar2D.swf");				//2D横向单柱状
swfFileArray.push("Column2D.swf");			//2D单柱状图
swfFileArray.push("Column3D.swf");			//3D单柱状图
swfFileArray.push("Doughnut2D.swf");		//2D环饼图
swfFileArray.push("Doughnut3D.swf");		//3D环饼图
swfFileArray.push("Line2D.swf");				//单线图
swfFileArray.push("MSBar2D.swf");			//复合2D横柱状
swfFileArray.push("MSBar3D.swf");			//复合3D横柱状
swfFileArray.push("MSColumn2D.swf");		//复合2D柱状图
swfFileArray.push("MSColumn3D.swf");		//复合3D柱状图
swfFileArray.push("MSCombi2D.swf");			//复合2D组合图
swfFileArray.push("MSCombi3D.swf");			//复合3D组合图
swfFileArray.push("MSLine.swf");			//复合线图（多线图）
swfFileArray.push("Pie2D.swf");				//2D饼图
swfFileArray.push("Pie3D.swf");				//3D饼图
swfFileArray.push("SSGrid.swf");			//数据列表图  
swfFileArray.push("StackedColumn3DLineDY.swf"); //

/**
* 通用的简单图形渲染方法
* @param chartDiv  图形所属DIV的ID
* @param dataXml   图形依赖的XML格式数据
* @param swf       渲染图形的swf文件名，比如:
				   Column2D.swf,Pie3D.swf,MSLine.swf,AngularGauge.swf等
* @param width,height 图形长宽，默认均为100%
*/
function renderSingleChart(chartDiv,dataXml,swf,width,height){
	if(!chartDiv||!document.getElementById(chartDiv)){
		alert("无法生成图形,所属DIV不存在！");
		return;
	}
	if(!dataXml||!(dataXml.toLowerCase().indexOf('<chart')>-1||dataXml.toLowerCase().indexOf('<graph')>-1)){
		alert("无法生成图形,请求返回数据格式不正确！");
		return;
	}
	if(!swf){
		alert("无法生成图形,未指定图形类型！");
		return;
	}
	if(!isSwfFileExists(swf)){
		alert("无法生成图形,指定图形类型不存在！");
		return;
	}
	
	var _width=width||'100%';
	var _height=height||'100%';
	var chartId=chartDiv+"_chart";
	
	if(!FusionCharts(chartId)){
  		var chart= new FusionCharts(BASE_PATH+"fusioncharts/swf/"+swf+"?ChartNoDataText=无数据可显示",
  								chartId,_width,_height, "0", "1" );
    	chart.setDataXML(dataXml); 
    	chart.render(chartDiv); 
   	}else{
   		FusionCharts(chartId).setDataXML(dataXml);
   	}
}

/**
* 判断当前图形类型类型是否支持（存在于现有数组中）
* @param swf   图形类型文件名
*/
function isSwfFileExists(swf){
	for(var i=0;i<swfFileArray.length;i++){
		if(swf==swfFileArray[i]){
			return true;
		}
	}
	return false;
}

