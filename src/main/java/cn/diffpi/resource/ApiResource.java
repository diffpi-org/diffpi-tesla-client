package cn.diffpi.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import cn.diffpi.core.kit.BaseSv;
import cn.diffpi.core.kit.ModelInjector;
import cn.diffpi.core.kit.SplitPage;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.core.Resource;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by one__l on 15-1-16.
 */
@API("/api/b/v1.0")
public class ApiResource extends Resource {
	/**
	 * 可以做一些公用的方法
   */
	
	public SplitPage splitPageBaseSql(Integer start , Integer end){
		
		SplitPage page = getModel(SplitPage.class);
		page.addQueryParam(getModel("query"));
		
		
		BaseSv.me.splitPageBaseSql(page , "select *", "from rop_track_entity");
		
		return null;
	}
	
	public Map<String, String> getModel(String modelName) {
		Map<String, String> map = new HashMap<String, String>();
		String modelNameAndDot = modelName + ".";
		Map<String, List<String>> parasMap = getRequest().getQueryParams();
		for (Entry<String, List<String>> e : parasMap.entrySet()) {
			String paraKey = e.getKey();
			if (paraKey.startsWith(modelNameAndDot)) {
				String paraName = paraKey.substring(modelNameAndDot.length());
				map.put(paraName, e.getValue().get(0));
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getModel(Class<T> modelClass){
		return (T)ModelInjector.inject(modelClass, getRequest(), false);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getModel(Class<T> modelClass , String modelName){
		return (T)ModelInjector.inject(modelClass, modelName , getRequest(), false);
	}
	
	public Locale getLocale(String language){
		language = StringUtils.isEmpty(language) ? "zh_CN" : language;
		return new Locale(language);
	}
	
	public Locale getLocale() {
        return getRequest().getLocale();
    }
	
}
