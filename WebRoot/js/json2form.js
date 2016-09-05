/**  
**json对象数据设置到表单域中  
*/  
function jsonObjectToForm(formId, jsonObject){
    var form =document.forms[formId]||document.getElementById(formId);
    var eValue;
    for(var i = 0, max = form.elements.length; i < max; i++) {   
       	var e = form.elements[i];   
       	if(e.name){
	       	var eName = e.name;   
	        if(eName.indexOf('.') > 0){   
	            var dotIndex = eName.indexOf('.');   
	            var parentName = eName.substring(0, dotIndex);   
	            var childName = eName.substring(dotIndex+1);   
	            //迭代判断eName，组装成json数据结构   
	            eValue = iterValueFromJsonObject(jsonObject, parentName, childName);   
	        }else{   
	            eValue = jsonObject[eName];   
	        }   
	        if((eValue==0||eValue) && eValue != "undefined" && eValue != "null"){   
	            switch(e.type){   
	                case 'checkbox':    
	                case 'radio':    
	                    if(e.value == eValue){   
	                        e.checked = true;   
	                    }   
	                    break;   
	                case 'hidden':    
	                case 'password':    
	                case 'textarea':   
	                case 'text':    
	                    e.value = eValue;   
	                    break;   
	                case 'select-one':   
	                case 'select-multiple':   
	                    for(j = 0; j < e.options.length; j++){   
	                        op = e.options[j];   
	                        //alert("eName : " + eName + "; op value : " + op.value + "; eValue : " + eValue);   
	                        if(op.value == eValue){   
	                            op.selected = true;   
	                        }   
	                    }   
	                    break;   
	                case 'button':    
	                case 'file':    
	                case 'image':    
	                case 'reset':    
	                case 'submit':    
	                default:     
	            }   
	        } 
	     }  
    }   
} 
/**  
* 迭代json数据对象设置到表单域中  
*/  
function iterValueFromJsonObject(jsonObject, parentName, childName){   
    //pArrayIndex用于判断元素是否是数组标示   
	var  pArrayIndex = parentName.indexOf('[');   
    //判断是否集合数据，不是则只是对象属性   
    if(pArrayIndex < 0){   
        var dotIndex = childName.indexOf('.');   
        if(dotIndex > 0){   
            return iterValueFromJsonObject(jsonObject[parentName], childName.substring(0, dotIndex), childName.substring(dotIndex+1));   
        }else{   
            return jsonObject[parentName][childName]   
        }   
    }else{   
        var pArray = jsonObject[parentName.substring(0, pArrayIndex)];   
        //取得集合下标，并判断对应下标是否存在js对象   
        var arrayIndex = parentName.substring(pArrayIndex+1, parentName.length-1);   
        var c = jsonObject[parentName.substring(0, pArrayIndex)][arrayIndex];   
        if(c){
	        var dotIndex = childName.indexOf('.');   
	        if(dotIndex > 0){   
	            return iterValueFromJsonObject(jsonObject[parentName.substring(0, pArrayIndex)][arrayIndex], childName.substring(0, dotIndex), childName.substring(dotIndex+1));   
	        }else{   
	            return jsonObject[parentName.substring(0, pArrayIndex)][arrayIndex][childName]   
	        } 
        }  
    }   
}  