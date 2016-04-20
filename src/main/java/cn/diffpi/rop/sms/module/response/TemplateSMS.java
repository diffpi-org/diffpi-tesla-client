package cn.diffpi.rop.sms.module.response;

public class TemplateSMS {
	// 短信标识符。一个由32个字符组成的短信唯一标识符
	private String createDate;
	// 短信的创建时间
	private String smsId;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public TemplateSMS() {
		super();
		// TODO Auto-generated constructor stub
	}
}
