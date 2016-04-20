package cn.diffpi.rop.track.course;

import java.util.HashMap;
import java.util.Map;

public class TrackModel {
	/***
	 * 开发者自定义的属性字段 
	 */
	protected Map<String, String> columnKeys = new HashMap<String, String>();

	public void setModel(Map<String, String> columns) {
		columnKeys.putAll(columns);
	}
	
	public TrackModel set(String key , String value) {
		columnKeys.put(key , value);
		
		return this;
	}
	
	public Object get(String columnKey){
		return columnKeys.get(columnKey);
	}
	
	public Map<String, String> getColumnKeys(){
		return columnKeys;
	}
}
