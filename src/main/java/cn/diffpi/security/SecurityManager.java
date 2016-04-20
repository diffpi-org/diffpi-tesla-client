package cn.diffpi.security;


/**
 * Created by one__l on 2016年4月15日
 */
public interface SecurityManager {
	
	/***
     * 对请求服务的参数进行验证
     * @return
     */
    public boolean isValiAuthParam();
    
    /**
     * 对请求服务的上下文进行授权校验
     * @return
     */
	boolean isValiAuth();
	
	/**
     * 对请求服务的上下文进行授权是否过期
     * @return
     */
	boolean isValiAuthTimeout();
    
    /***
     * 对请求服务的token是否正确
     * @return
     */
    public boolean isValiToken();
    
    /***
     * 对请求服务的token检查是否过期
     * @return
     */
    public boolean isValiTokenTimeout();
	
	/***
	 * 对请求服务的签名进行验证
	 * @return
	 */
    boolean isValiSign();
    
    /***
     * 对请求服务的加密字符进行解密验证
     * @return
     */
    public boolean isValiDecrypt();
    
    
}	
