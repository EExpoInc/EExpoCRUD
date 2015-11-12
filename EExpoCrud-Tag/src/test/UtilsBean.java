package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class UtilsBean {

	
	public static <E> E populate2(Class<E> clazz, Map<String, String[]> map) {
		
		try {
			E bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static <E> E populate(Class<E> clazz, Map<String, String[]> map) {
		Field[] fields = clazz.getDeclaredFields();
		E pojo = null;
				
		try {
			pojo = clazz.newInstance();

			for (int i = 0; i < fields.length; i++) {

				String elementName = fields[i].getName();
				if (map.containsKey(elementName)) {
					if(map.get(elementName).length == 1){
						String attMap = map.get(elementName)[0];
						String propertyType = fields[i].getType().getSimpleName();
						fields[i].setAccessible(true);
						
						if (propertyType.equalsIgnoreCase(int.class.getSimpleName().toLowerCase())) {
							fields[i].set(pojo, Integer.parseInt(attMap));
						} else if (propertyType.equalsIgnoreCase(long.class.getSimpleName())) {
							fields[i].set(pojo, Long.parseLong(attMap));
						} else if (propertyType.equalsIgnoreCase(boolean.class.getSimpleName().toLowerCase())) {						
							fields[i].set(pojo, Boolean.parseBoolean(attMap));						 
//							if(attMap.toLowerCase().equals("on")){
//								fields[i].set(pojo, true);
//							}else{							
//								fields[i].set(pojo, false);
//							}
//							
						}else {
							fields[i].set(pojo, attMap);
						}						
					}else{
						List<String> array = (ArrayList<String>) Arrays.asList(map.get(elementName));						
						fields[i].set(pojo, array);
					}
				}
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return pojo;
	}

	
	
	public static void main(String[] args) {
		HashMap<String, String[]> req = new HashMap<>();
		String[] param1 = new String[1]; 
		param1[0] =  "123456";
		req.put("id", param1);
		
		
		String[] param2 = new String[1]; 
		param2[0] =  "teste de filtro";		
		req.put("filter", param2);

		String[] param3 = new String[1]; 
		param3[0] =  "true";		
		req.put("remove", param3);

		
		FilterImgUrlEntity e = populate(FilterImgUrlEntity.class, req);
		System.out.println(e.id);
		System.out.println(e.filter);
		System.out.println(e.remove);
		
		
	}
	
	
	
	
}
