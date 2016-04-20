package cn.diffpi.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.dreampie.orm.Model;

public abstract class BaseModel<T extends Model<T>> extends Model<T>{
	
	public Map<String, String> getMap(){
		
		Map<String, String> propMap = new HashMap<String, String>();
		
		for (Entry<String, Object> obj : super.getAttrs().entrySet()) {
			propMap.put(obj.getKey(), String.valueOf(obj.getValue()));
		}
		
		return propMap;
	}
	
}
