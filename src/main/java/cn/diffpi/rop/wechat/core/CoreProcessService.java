package cn.diffpi.rop.wechat.core;

import java.util.Map;

public interface CoreProcessService {
	
	/***
	 * 参数初始化
	 * @param requestMap  微信消息map
	 */
	public void init(Map<String, String> requestMap);
	
	
	/***
	 * 订阅事件
	 * @return
	 */
	public String subscribe();
	
	/***
	 * 取消订阅事件
	 * @return
	 */
	public String unsubscribe();
	
	/***
	 * 上报地理位置事件
	 * @return
	 */
	public String location();
	
	/***
	 * 点击菜单拉取消息的事件
	 * @return
	 */
	public String click();
	
	/***
	 * 点击菜单跳转链接的事件
	 * @return
	 */
	public String view();
	
	/****
	 * 扫码推事件的事件
	 * @return
	 */
	public String scancodePush();
	
	/***
	 * 扫码推事件且弹出“消息接收中”提示框的事件
	 */
	public String scancodeWaitmsg();
	
	/***
	 * 弹出系统拍照发图的事件
	 * @return
	 */
	public String picSysphoto();
	
	/***
	 * 弹出拍照或者相册发图的事件
	 * @return
	 */
	public String picPhotoOrAlbum();
	
	/***
	 * 弹出微信相册发图器的事件
	 * @return
	 */
	public String picWeixin();
	
	/***
	 * 弹出地理位置选择器的事件
	 * @return
	 */
	public String locationSelect();
	
	/***
	 * 成员进入应用的事件
	 * 本事件在成员进入企业号的应用时触发，如果企业需要接收此事件，请打开应用回调模式中的相应开关。 
	 * @return
	 */
	public String enterAgent();
	
	/***
	 * 异步任务完成事件
	 * 本事件是成员在使用异步任务接口时，用于接收任务执行完毕的结果通知
	 * @return
	 */
	public String batchJobResult();
	
}
