package com.jysoft.framework.util;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class MyWebBinding  implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		//1. ʹ��spring�Դ��CustomDateEditor
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
		//2. �Զ����PropertyEditorSupport
		binder.registerCustomEditor(Date.class, new DateConvertEditor());

	}
}
