package cn.diffpi.rop.wechat.ticket;

import cn.diffpi.kit.HttpCon;
import cn.dreampie.common.util.json.Jsoner;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class TicketManager {
	
	private static final TicketCache TICKET_CACHE = new TicketCache();
	
	private static JsApiTicket craeteToken(String ticket_url , String ticketId) throws Exception{
		JsApiTicket jsApiTicket = null;
		
		if(!validateToken(ticketId)){//验证JsApiTicket 是否过期
			JSONObject jsonObject = Jsoner.toObject(HttpCon.doGet(ticket_url, null, null, null, true));
			
			// 如果请求成功
			if (null != jsonObject) {
				try {
					jsApiTicket = new JsApiTicket();
					jsApiTicket.setTicket(jsonObject.getString("ticket"));
					jsApiTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));
					jsApiTicket.setCreatetime(System.currentTimeMillis());
					
					TICKET_CACHE.put(jsApiTicket);
				} catch (JSONException e) {
					jsApiTicket = null;
					// 获取ticket失败
				}
			}
		}else{
			jsApiTicket = TICKET_CACHE.get(ticketId);
		}
		
		return jsApiTicket;
	}
	
	public static synchronized boolean validateToken(String ticketId) {
		boolean ticketValidate = true;
		if(ticketId != null && TICKET_CACHE.get(ticketId) != null){
			long createtime = TICKET_CACHE.get(ticketId).getCreatetime();
			long currenttime = System.currentTimeMillis();
			
			int time_difference = (int)(currenttime - createtime);
			if(time_difference/1000 >= TICKET_CACHE.get(ticketId).getExpiresIn()){
				ticketValidate = false;
				TICKET_CACHE.remove(ticketId);//移除过期的ticket
			}
		}else{
			ticketValidate = false;
		}
		
		return ticketValidate;
	}
	
	public static JsApiTicket getJsApiTicket(String ticket_url , String appId) throws Exception {
		return craeteToken(ticket_url, appId);
	}
}
