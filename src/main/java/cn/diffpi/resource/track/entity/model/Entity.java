package cn.diffpi.resource.track.entity.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by one__l on 2016年3月14日 轨迹实体类
 */
@Table(name = "rop_track_entity", cached = false)
public class Entity extends BaseModel<Entity> {
	public final static Entity dao = new Entity();
}
