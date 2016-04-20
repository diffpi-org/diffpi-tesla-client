package cn.diffpi.rop.track.course.model;

import cn.diffpi.rop.track.course.TrackModel;

public class Entity extends TrackModel{
	/***
	 * service的ID，作为其唯一标识
	 */
	private Integer service_id;
	/***
	 * entity名称，作为其唯一标识。 同一service服务中entity_name不可重复。
	 */
	private String entity_name;
	
	public Integer getService_id() {
		return service_id;
	}
	
	public Entity setService_id(Integer service_id) {
		this.service_id = service_id;
		super.columnKeys.put("service_id", service_id.toString());
		
		return this;
	}
	
	public String getEntity_name() {
		return entity_name;
	}
	
	public Entity setEntity_name(String entity_name) {
		this.entity_name = entity_name;
		super.columnKeys.put("entity_name", entity_name);
		
		return this;
	}
	
}
