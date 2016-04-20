package cn.diffpi.core;

import cn.diffpi.security.SecurityManager;
import cn.dreampie.route.core.RouteInvocation;
import cn.dreampie.route.interceptor.Interceptor;

/***
 * 
 * Created by one__l on 2016年4月15日
 */
public class MySecurityIntercepter implements Interceptor {
	
	private SecurityManager securityManager;

	public MySecurityIntercepter(SecurityManager securityManager) {
		this.securityManager = securityManager;
		
	}

	public void intercept(RouteInvocation ri) {
		System.out.println(ri.getMethod());
	}

}
