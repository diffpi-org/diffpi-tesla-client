package cn.diffpi.rop.sms.module.request;

public class TemplateSMS {
	// 应用id
	private String appId;
	// 模板id
	private String templateId;
	// 短信接收端手机号码集合，用英文逗号分开，每批发送的手机号数量不得超过100个（国内短信不要加前缀，国际短信号码前须带相应的国家区号，如日本：0081）
	private String to;
	// 内容数据，用于替换模板中{数字}，若有多个替换内容，用英文逗号隔开即可
	private String param;

	public TemplateSMS() {
		super();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
