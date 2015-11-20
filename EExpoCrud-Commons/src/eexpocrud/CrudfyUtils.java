package eexpocrud;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import eexpocrud.CrudAnnotation.CrudId;
import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudAnnotation.NotShow;
import eexpocrud.CrudAnnotation.Show;

public class CrudfyUtils {
	public static final String INPUT_ORIGINAL_SUFIX = "_ORIGINAL";
	public static final String VALUEOF_METHOD = "valueOf";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_FULLDATE = PATTERN_DATE + "_hh:mm:ss";
	public static final SimpleDateFormat universalDateFormat = new SimpleDateFormat(PATTERN_DATE);
	public static final SimpleDateFormat universalFullDateFormat = new SimpleDateFormat(PATTERN_FULLDATE);
	
	private static final Class<?>[] suporttedClasses = { Date.class, String.class };
	private static List<Class<?>> suporttedClassesList = Arrays.asList(CrudfyUtils.suporttedClasses);
	
	private final static Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		primitiveMap.put(boolean.class, Boolean.class);
		primitiveMap.put(byte.class, Byte.class);
		primitiveMap.put(short.class, Short.class);
		primitiveMap.put(char.class, Character.class);
		primitiveMap.put(int.class, Integer.class);
		primitiveMap.put(long.class, Long.class);
		primitiveMap.put(float.class, Float.class);
		primitiveMap.put(double.class, Double.class);
	}
	
	public static void saveOnSession(HttpServletRequest req, String k, Object v) {
		req.getSession().setAttribute(k, v);
	}
	
	@SuppressWarnings("unchecked")
	public static <V> V getFromSession(HttpServletRequest req, String k) {
		return (V) req.getSession().getAttribute(k);
	}
	
	public static Class<?> isSupportedClass(Class<?> c) {
		if (primitiveMap.containsKey(c)) {
			return primitiveMap.get(c);
		} else if (primitiveMap.values().contains(c)) {
			return c;
		} else if (suporttedClassesList.contains(c)) {
			return c;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("serial")
	public static class ParseDateException extends Exception {
		public ParseDateException(String input) {
			super("O texto \"" + input + "\" nao pode ser convertido em Data.");
		}
	}
	
	@SuppressWarnings("serial")
	public static class GenericParseException extends RuntimeException {
		String input, varName;
		Class<?> clazz;
		
		public GenericParseException(String input, Class<?> clazz) {
			super("O texto \"" + input + "\" nao pode ser convertido em \"" + clazz.getCanonicalName()
					+ "\".");
			
			this.input = input;
			this.clazz = clazz;
			// this.className = clazz.getCanonicalName();
		}
	}
	
	@SuppressWarnings("serial")
	public static class FusionBeanException extends RuntimeException {
		public ArrayList<GenericParseException> exceptionList = new ArrayList<GenericParseException>();
		
		public FusionBeanException() {
			super();
		}
	}
	
	public static <B extends Object> B beanFusion(B bOld, Map<String, String> params)
			throws FusionBeanException {
		B result = bOld;
		Field[] fields = bOld.getClass().getFields();
		
		try {
			FusionBeanException fbe = new FusionBeanException();
			params = detectChange(params);
			for (Field f : fields) {
				f.setAccessible(true);
				// String value = params.get(f.getName() + INPUT_ORIGINAL_SUFIX);
				String value = params.get(f.getName());
				if (value != null) {
					try {
						f.set(result, parseFromString(value, f.get(bOld)));
					} catch (IllegalAccessException iae) {
						GenericParseException gpe = new GenericParseException(value, f.getType());
						gpe.initCause(iae);
						gpe.clazz = f.getType();
						fbe.exceptionList.add(gpe);
					} catch (GenericParseException e) {
						e.clazz = f.getType();
						fbe.exceptionList.add(e);
					}
				}
			}
			if (!fbe.exceptionList.isEmpty()) {
				throw new FusionBeanException();
			} else {
				return result;
			}
		} catch (FusionBeanException e) {
			throw e;
		}
	}
	
	private static Map<String, String> detectChange(Map<String, String> params) {
		Map<String, String> result = new TreeMap<String, String>();
		for (String k_o : params.keySet()) {
			if (k_o.contains(INPUT_ORIGINAL_SUFIX)) {
				String k_mod = k_o.replace(INPUT_ORIGINAL_SUFIX, "");
				String v_mod = params.get(k_mod);
				if (v_mod != null) {
					if (!v_mod.equals(params.get(k_o))) {
						result.put(k_mod, v_mod);
					}
				}
			}
		}
		return result;
	}
	
	public static void main_(String[] args) throws GenericParseException {
		// int i1 = 100;
		// String i1 = "100";
		int i1 = 1000;
		String s2 = "200";
		System.out.println(parseFromString(s2, i1) + 5);
		Date d1 = new Date(System.currentTimeMillis());
		System.out.println(universalFullDateFormat.format(parseFromString("2015-01-29_23:58:59", d1)));
		Date d2 = new Date(System.currentTimeMillis());
		System.out.println(universalFullDateFormat.format(parseFromString("2015-01-29", d2.getClass())));
	}
	
	
	
//	public static Object parseFromString2Obj(String valueOriginal, Class<?> clazz) throws GenericParseException {
//		return parseFromString(valueOriginal, clazz);
//	}
	
	
	@SuppressWarnings("unchecked")
	public static <C> C parseFromString(String valueOriginal, Class<C> clazz) throws GenericParseException {
		
		try {
			if (clazz.equals(String.class)) {
				return (C) valueOriginal;
			} else if (clazz.equals(Date.class)) {
				if (valueOriginal.length() > 11) {
					return (C) universalFullDateFormat.parseObject(valueOriginal);
				} else {
					return (C) universalDateFormat.parseObject(valueOriginal);
				}
			} else {
				Class<?> c = isSupportedClass(clazz);
				if (c != null) {
					// Class<?> objClass = objNew.getClass();
					Method m = clazz.getMethod(VALUEOF_METHOD, String.class);
					Object _result = m.invoke(null, valueOriginal);
					return (C) _result;
				}
			}
			
		} catch (Exception ex) {
			GenericParseException gpe = new GenericParseException(valueOriginal, clazz);
			gpe.initCause(ex);
			throw gpe;
		}
		throw new GenericParseException(valueOriginal, clazz);
		
	}
	
	public static <T extends Object> T getParam(HttpServletRequest req, String paramName, Class<T> type) {
		String p = req.getParameter(paramName);
		if (p != null) {
			return parseFromString(p, type);
		} else {
			return null;
		}
	}
	
	// public static <T extends Object> T parseFromString(String valueOriginal) throws GenericParseException {
	// Object o = new Object();
	// T t = (T) o;
	// Class<T> c = (Class<T>) t.getClass();
	//
	//
	// return parseFromString(valueOriginal, c);
	// }
	
	@SuppressWarnings("unchecked")
	public static <T> T parseFromString(String valueOriginal, T objNew) throws GenericParseException {
		
		// List<Class<?>> _suporttedClasses = Arrays.asList(suporttedClasses);
		return (T) parseFromString(valueOriginal, objNew.getClass());
		
		/*****
		 * try { if (objNew instanceof String) { return (T) valueOriginal; } else if (objNew instanceof Date) { if
		 * (valueOriginal.length() > 11) { return (T) universalFullDateFormat.parseObject(valueOriginal); } else { return (T)
		 * universalDateFormat.parseObject(valueOriginal); } } else { Class<?> c = isSupportedClass(objNew.getClass()); if (c !=
		 * null) { Class<?> objClass = objNew.getClass(); Method m = objClass.getMethod(VALUEOF_METHOD, String.class); Object
		 * _result = m.invoke(objNew, valueOriginal); return (T) _result; } }
		 * 
		 * } catch (Exception ex) { GenericParseException gpe = new GenericParseException(valueOriginal, objNew.getClass());
		 * gpe.initCause(ex); throw gpe; } throw new GenericParseException(valueOriginal, objNew.getClass());
		 ****/
	}
	
	// public static <B extends Object> B beanFusion(B bNew, B bOld) {
	// B result;
	// Field[] fields = bNew.getClass().getFields();
	// for (Field f : fields) {
	// Class<?> c = f.getType();
	// f.setAccessible(true);
	// if (c.equals(String.class) || c.equals(char.class) || c.equals(Integer.class)
	// || c.equals(int.class) || c.equals(Long.class) || c.equals(long.class)
	// || c.equals(Double.class) || c.equals(double.class) || c.equals(Boolean.class)
	// || c.equals(boolean.class) || c.equals(Float.class) || c.equals(float.class)) {
	// try {
	// if (f.get(bNew) != null && !f.get(bNew).toString().equals("")) {
	//
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	// }
	// return null;
	// }
	//
	
	public static boolean showable(Field f, DisplayType dt) {
		
		if (f.isAnnotationPresent(NotShow.class)) {
			if (containsDisplayType(f.getAnnotation(NotShow.class).value(), dt, DisplayType.all)) {
				return false;
			}
		}
		
		if (f.isAnnotationPresent(Show.class)) {
			if (containsDisplayType(f.getAnnotation(Show.class).value(), DisplayType.all, dt)) {
				return true;
			}
			if (containsDisplayType(f.getAnnotation(Show.class).value(), DisplayType.none)) {
				return false;
			}
		}
		return true;
	}
	
	// public static boolean isSupported(Class<?> clazz){
	// if(suporttedClassesList.contains(clazz)){
	// return true;
	// }else {
	// return false;
	// }
	// }
	
	private static boolean containsDisplayType(DisplayType[] dts, DisplayType... toFind) {
		ArrayList<DisplayType> al = (new ArrayList<DisplayType>(Arrays.asList(dts)));
		for (DisplayType dt : toFind) {
			if (al.contains(dt)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSupported(Class<?> type) {
		if (isSupportedClass(type) != null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	// public static Class<?> findIdClass(Class<?> entityClass) {
	// Field[] fs = entityClass.getDeclaredFields();
	// for (Field f : fs) {
	// if(isIdField(f)){
	// return f.getType();
	// }
	// }
	// return null;
	// }
	
	public static Field findIdField(Class<?> entityClass) {
		Field[] fs = entityClass.getDeclaredFields();
		for (Field f : fs) {
			if (isIdField(f)) {
				return f;
			}
		}
		return null;
	}
	
	public static boolean isIdField(Field field) {
		if (field.isAnnotationPresent(CrudId.class) || field.isAnnotationPresent(Id.class)
				|| field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Faz a transfusao dos valores do bean <code>Original</code> p o bean <code>Receptor</code>. 
	 * Receptor precisa estender Original. 
	 * @param receptor
	 * @param original
	 */
	public static <R extends O, O extends Object> void beanTransfusion(R receptor, O original) {
		@SuppressWarnings("rawtypes")
		Class oc = original.getClass();
		Field[] oFields = oc.getFields();
		List<Field> cFields = Arrays.asList(receptor.getClass().getFields());
		ArrayList<String> cFieldsName = new ArrayList<>();
		for (Field f : cFields) {
			cFieldsName.add(f.getName());
		}
		try {
			
			for (Field of : oFields) {
				of.setAccessible(true);
				if (cFieldsName.contains(of.getName())) {
					Field cf = oc.getField(of.getName());
					cf.setAccessible(true);
					if (cf.getType().equals(of.getType())) {
						cf.set(receptor, of.get(original));
					}
				}
			}
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			
		}
		
	}
	
	public static String urlfy(String input, String sep) {
		return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("|", sep)
				.replaceAll("[^\\p{ASCII}]", sep).replaceAll("[^ \\w]", "").trim().replaceAll("\\s+", sep)
//				.toLowerCase(Locale.ENGLISH)
				;
		
	}
	
	public static String urlfy(String input) {
		return urlfy(input, "-");
	}
	
	
	public static <T> String encodeToURL(T t){
		try {
			return  URLEncoder.encode(t+"", StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {			
//			e.printStackTrace();
		}
		return t +"";
	}

	
	//
	// public static void main(String[] args) throws FusionBeanException {
	// FilterImgUrlEntity e = new FilterImgUrlEntity();
	// e.id = 445L;
	// e.filter = "filtra isso!!!";
	// e.qtde = 2;
	// e.nome = "Nome do Filtro";
	// e.data = new Date();
	// e.remove = false;
	//
	// System.out.println(isSupportedClass(int.class));
	//
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("filter" + INPUT_ORIGINAL_SUFIX, "filtra isso!!!");
	// params.put("remove" + INPUT_ORIGINAL_SUFIX, "false");
	// params.put("qtde" + INPUT_ORIGINAL_SUFIX, "2");
	// params.put("data" + INPUT_ORIGINAL_SUFIX, "1998-11-28");
	//
	// params.put("filter" , "filtra de novo!!!");
	// params.put("remove" , "true");
	// params.put("qtde" , "9001");
	// params.put("data" , "2010-09-22");
	//
	// System.out.println(e);
	// FilterImgUrlEntity eMod = beanFusion(e, params);
	// System.out.println(eMod);
	//
	// }
	
	//
	// private static MethodHandles.Lookup lookup = MethodHandles.lookup();
	//
	//
	// private static boolean setterMethodExist(Class<?> c, String fieldName){
	// MethodHandle mh;
	// try {
	// mh = lookup.findSetter(c, fieldName, Date.class);
	// mh.invoke();
	// } catch (WrongMethodTypeException e) {
	// return false;
	//
	// } catch (Throwable e) {
	// // e.printStackTrace();
	// // return false;
	// }
	// return true;
	// }
	//
	// public static void beanable(Object obj){
	// Field [] fs = obj.getClass().getFields();
	// for (Field f : fs) {
	// String name = f.getName();
	// System.out.println(f.getName()+ " "+setterMethodExist(obj.getClass(), name));
	// StringBuilder sb = new StringBuilder();
	// sb.append("set");
	// sb.append(Character.toUpperCase(name.charAt(0)));
	// sb.append(f.getName().subSequence(1, name.length()));
	// System.out.println(sb);
	// }
	// }
	//
	//
	// public static void main(String[] args) {
	// beanable(new FilterImgUrlEntity());
	// }
	
}
