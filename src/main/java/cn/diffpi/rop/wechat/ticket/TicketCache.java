package cn.diffpi.rop.wechat.ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketCache {
	private Map<String , JsApiTicket> ticketMap = new HashMap<String, JsApiTicket>();
	
    public void put(JsApiTicket ticket){
    	ticketMap.put(ticket.getTicketId(), ticket);
    }
	
	public void remove(String ticketId){
		ticketMap.remove(ticketId);
	}
	
	public boolean contains(JsApiTicket ticket){
		return ticketMap.containsKey(ticket.getTicketId());
	}
	
	public Map<String , JsApiTicket> getAll(){
		return ticketMap;
	}
	
	public JsApiTicket get(String ticketId){
		return ticketMap.get(ticketId);
	}
}
