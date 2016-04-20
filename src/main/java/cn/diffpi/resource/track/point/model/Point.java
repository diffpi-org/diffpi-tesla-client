package cn.diffpi.resource.track.point.model;

import java.util.Map.Entry;

import cn.diffpi.resource.BaseModel;
import cn.diffpi.rop.track.common.TrackClient;
import cn.diffpi.rop.track.config.TrackConfig;
import cn.diffpi.rop.track.course.TrackModel;
import cn.dreampie.orm.annotation.Table;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by one__l on 2016年3月16日 轨迹点
 */
@Table(name = "rop_track_point", cached = true)
public class Point extends BaseModel<Point> {
	public final static Point dao = new Point();
	
	private TrackClient client = new TrackClient(TrackConfig.getConfig().get("ak"));
	
	public boolean save(JSONObject pointObj){
		
		Point point = new Point();
		for (Entry<String, Object> ele : pointObj.entrySet()) {
			point.set(ele.getKey(), ele.getValue());
		}
		
		point.set("loc_time", System.currentTimeMillis()/1000L);
		
		TrackModel model = new TrackModel();
		model.setModel(point.getMap());
		
		System.out.println(client.build(model).pointAdd());
		
		return point.save();
	}
}
