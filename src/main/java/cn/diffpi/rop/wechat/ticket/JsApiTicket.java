package cn.diffpi.rop.wechat.ticket;

/***
 * jsapi  访问票据
 * @author one__
 *
 */
public class JsApiTicket {
	//jsapi访问票据id
	private String ticketId;
	//jsapi访问票据
	private String ticket;
	//创建时间
	private long createtime;
	//凭证有效时间
	private int expiresIn;
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	
}
