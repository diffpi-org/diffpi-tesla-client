package cn.diffpi.rop.wechat.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.diffpi.rop.wechat.core.aes.AesException;
import cn.diffpi.rop.wechat.core.aes.WXBizMsgCrypt;
import cn.diffpi.rop.wechat.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author one__l
 */
public abstract class CoreService {
	
	protected String token;
	protected String encodingAesKey;
	protected String appId;
	protected HttpServletRequest request;
	protected Map<String, String> requestMap;
	protected WXBizMsgCrypt wxcpt;
	
	protected String msgSignature;
	protected String timeStamp;
	protected String nonce;
	protected String echoStr;
	
	public CoreService(String token, String encodingAesKey, String appId , HttpServletRequest request) throws Exception{
		this.token = token;
		this.appId = appId;
		this.encodingAesKey = encodingAesKey;
		this.request = request;
		this.msgSignature = request.getParameter("msg_signature");
		this.timeStamp =  request.getParameter("timestamp");
		this.nonce =  request.getParameter("nonce");
		this.echoStr =  request.getParameter("echostr");
		
		init();
	}
	
	private void init() throws Exception{
		this.wxcpt = new WXBizMsgCrypt(token, encodingAesKey, appId);
		
		String postData = MessageUtil.getPostData(request);
		if(!postData.equals("")){
			this.requestMap = MessageUtil.parseXml(wxcpt.DecryptMsg(msgSignature, timeStamp, nonce, postData));
		}
	}
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public abstract String processRequest(CoreProcessService processService);
	
	
	/***
	 * 验证微信签名
	 * @param request
	 * @return
	 * @throws AesException 
	 */
	public String checkSignature() throws AesException{
		return wxcpt.VerifyURL(msgSignature, timeStamp, nonce, echoStr);
	}
}
