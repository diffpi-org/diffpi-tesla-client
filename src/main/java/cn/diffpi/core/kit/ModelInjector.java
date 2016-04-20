package cn.diffpi.core.kit;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.diffpi.kit.StrKit;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.orm.Model;

/**
 * ModelInjector
 */
public final class ModelInjector {
	
	@SuppressWarnings("unchecked")
	public static <T> T inject(Class<?> modelClass, HttpRequest request, boolean skipConvertError) {
		String modelName = modelClass.getSimpleName();
		return (T)inject(modelClass, StrKit.firstCharToLowerCase(modelName), request, skipConvertError);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final <T> T inject(Class<?> modelClass, String modelName, HttpRequest request, boolean skipConvertError) {
		Object model = null;
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (model instanceof Model)
			injectActiveRecordModel((Model)model, modelName, request, skipConvertError);
		else
			injectCommonModel(model, modelName, request, modelClass, skipConvertError);
		
		return (T)model;
	}
	
	private static final void injectCommonModel(Object model, String modelName, HttpRequest request, Class<?> modelClass, boolean skipConvertError) {
		Method[] methods = modelClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set") == false)	// only setter method
				continue;
			
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1)						// only one parameter
				continue;
			
			String attrName = methodName.substring(3);
			String value = request.getQueryParam(modelName + "." + StrKit.firstCharToLowerCase(attrName));
			if (value != null) {
				try {
					method.invoke(model, TypeConverter.convert(types[0], value));
				} catch (Exception e) {
					if (skipConvertError == false)
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static final void injectActiveRecordModel(Model<?> model, String modelName, HttpRequest request, boolean skipConvertError) {
		String modelNameAndDot = modelName + ".";
		
		Map<String, List<String>> parasMap = request.getQueryParams();
		for (Entry<String, List<String>> e : parasMap.entrySet()) {
			String paraKey = e.getKey();
			if (paraKey.startsWith(modelNameAndDot)) {
				String paraName = paraKey.substring(modelNameAndDot.length());
				Class colType = model.getColumnType(paraName);
				if (colType == null) return;
					//throw new ActiveRecordException("The model attribute " + paraKey + " is not exists.");
				
				List<String> paraValue = e.getValue();
				try {
					// Object value = Converter.convert(colType, paraValue != null ? paraValue[0] : null);
					Object value = paraValue.get(0) != null ? TypeConverter.convert(colType, paraValue.get(0)) : null;
					model.set(paraName, value);
				} catch (Exception ex) {
					if (skipConvertError == false)
						throw new RuntimeException("Can not convert parameter: " + modelNameAndDot + paraName, ex);
				}
			}
		}
	}
}

