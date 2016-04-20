package cn.diffpi.rop.track.common;

import java.util.Map;

import cn.diffpi.kit.HttpCon;
import cn.diffpi.rop.track.config.TrackConfig;
import cn.diffpi.rop.track.course.TrackModel;

public class TrackClient {
	private String ak;
	private Map<String, String> propertys;
	private TrackConfig config = TrackConfig.getConfig();
	
	public TrackClient(String ak){
		this.ak = ak;
	}
	
	public TrackClient build(TrackModel model){
		model.set("ak", ak);
		
		propertys = model.getColumnKeys();
		
		return this;
	}
	
	private String doPost(String url){
		return HttpCon.doPost(url, propertys, null, null, false);
	}
	
	public String entityAdd(){
		return doPost(config.get("entity.add"));
	}
	
	public String entityDelete(){
		return doPost(config.get("entity.delete"));
	}
	
	public String pointAdd(){
		return doPost(config.get("point.add"));
	}
	
}
