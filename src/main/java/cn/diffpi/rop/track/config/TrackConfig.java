package cn.diffpi.rop.track.config;

import cn.dreampie.common.util.properties.Prop;
import cn.dreampie.common.util.properties.Proper;

public class TrackConfig {
	
	private Prop prop;
	private static TrackConfig CONFIG = null;
	
	public static TrackConfig getConfig(){
		if(CONFIG == null){
			CONFIG = new TrackConfig();
		}
		
		return CONFIG;
	}
	
	private TrackConfig(){
		this.prop = Proper.use("rop/yingyan/yingyan.properties");
	}
	
	public String get(String name){
		return prop.get(name);
	}
}
