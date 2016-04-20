package cn.diffpi.resource.track.entity;

import java.util.Map;

import cn.diffpi.core.kit.BaseSv;
import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.DateUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.track.entity.model.Entity;
import cn.diffpi.response.ErrModelException;
import cn.diffpi.rop.track.common.TrackClient;
import cn.diffpi.rop.track.config.TrackConfig;
import cn.diffpi.rop.track.course.TrackModel;
import cn.dreampie.common.util.Maper;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

/**
 *  Created by one__l on 2016年3月14日
 */
@API("/track/entitys")
public class EntityResource extends ApiResource{
	
	private TrackClient client = new TrackClient(TrackConfig.getConfig().get("ak"));
	
	@GET
	public Map entitys(){
		SplitPage page = getModel(SplitPage.class);
		page.addQueryParam(getModel("query"));
		BaseSv.me.splitPageBaseSql(page , "select *", "from rop_track_entity");
		
		throw ErrModelException.notExistError("entity", "name", "张三" , getLocale());
		
		//return Maper.of("page",page);
	}
	
	@GET("/:id")
	public Entity entity(Integer id){
		return Entity.dao.findById(id);
	}
	
	@POST
	public boolean save(Entity entity) {
		entity.set("create_time", DateUtil.getCurrentDate(DateUtil.DATE_MIN));
		if(entity.get("id") == null && entity.save()){
			
			TrackModel model = new TrackModel();
			model.setModel(entity.getMap());
			
			System.out.println(client.build(model).entityAdd());
			return true;
		} else {
			return false;
		}
	}
	
	@DELETE("/:id")
	public boolean delete(Integer id) {
		Entity entity = Entity.dao.findById(id);
		
		if(entity != null && entity.delete()){
			TrackModel model = new TrackModel();
			model.setModel(entity.getMap());
			
			System.out.println(client.build(model).entityDelete());
			
			return true;
		}
		
		return false;
	}
	
}
