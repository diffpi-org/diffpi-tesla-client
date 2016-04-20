package cn.diffpi.core;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cn.dreampie.common.http.HttpRequest;

/**
 * 
 * Created by one__l on 2016年4月15日
 */
public class MyHttpRequest extends HttpRequest{
	
	private MyRequestContext context;
	
	public MyHttpRequest(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}
	
	public InputStream getOriginalStream() throws IOException{
		return super.getContentStream(); 
	}
	
	@Override
	public InputStream getContentStream() throws IOException {
		if(context == null || context.getDecryptStream() == null){
			return super.getContentStream();
		}
		
		return context.getDecryptStream();
	}

	public MyRequestContext getContext() {
		return context;
	}

	public void setContext(MyRequestContext context) {
		this.context = context;
	}
}
