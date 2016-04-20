package cn.diffpi.rop.wechat.core;

import javax.servlet.http.HttpServletRequest;

import cn.diffpi.rop.wechat.core.aes.AesException;
import cn.diffpi.rop.wechat.util.MessageUtil;


/**
 * 核心服务类
 * 
 * @author luoxun
 */
public class CoreServiceImpl extends CoreService{
	
	public CoreServiceImpl(String token, String encodingAesKey, String appId , HttpServletRequest request) throws Exception {
		super(token, encodingAesKey, appId , request);
	}
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(CoreProcessService processService) {
		String respMessage = "";
		try {
			processService.init(requestMap);
			
			//得到消息类型
			String msgType = requestMap.get("MsgType");
			
			// 文本消息
			if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				
			}else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
				// 事件类型
				String eventType = requestMap.get("Event").toUpperCase();
				
				if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
					respMessage = processService.subscribe();
				}else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此可以不需要回复消息
					respMessage = processService.unsubscribe();
				}else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
					respMessage = processService.click();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_LOCATION)){
					respMessage = processService.location();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_VIEW)){
					respMessage = processService.view();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SCANCODE_PUSH)){
					respMessage = processService.scancodePush();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SCANCODE_WAITMSG)){
					respMessage = processService.scancodeWaitmsg();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_PIC_WEIXIN)){
					respMessage = processService.picWeixin();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_LOCATION_SELECT)){
					respMessage = processService.locationSelect();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_ENTER_AGENT)){
					respMessage = processService.enterAgent();
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_BATCH_JOB_RESULT)){
					respMessage = processService.batchJobResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(respMessage != null && !respMessage.equals("")){
			try {
				respMessage = wxcpt.EncryptMsg(respMessage,  super.timeStamp , super.nonce);
			} catch (AesException e) {
				e.printStackTrace();
			}
		} else {
			respMessage = "";
		}
		
		return respMessage;
	}
}
