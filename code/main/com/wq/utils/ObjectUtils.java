package com.wq.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象工具类.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class ObjectUtils {

	/**
	 * 获得public熟悉的对象成员.
	 * 
	 * @param owner
	 *            类对象
	 * @param fieldName
	 *            字段名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object getProperty(Object owner, String fieldName)
			throws Exception {
		Class ownerClass = owner.getClass();
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(owner);
		return property;
	}

	/**
	 * 得到某个类的静态属性.
	 * 
	 * @param ownerClass
	 *            类型
	 * @param fieldName
	 *            字段名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object getStaticProperty(Class ownerClass, String fieldName)
			throws Exception {
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(ownerClass);
		return property;
	}

	/**
	 * 执行某个对象的方法.
	 * 
	 * @param owner
	 *            对象
	 * @param methodName
	 *            方面名称
	 * @param args
	 *            方法参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	/**
	 * 执行某个类的静态方法.
	 * 
	 * @param className
	 *            类型
	 * @param methodName
	 *            方法名称
	 * @param args
	 *            方法参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeStaticMethod(Class ownerClass,
			String methodName, Object[] args) throws Exception {
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(null, args);
	}

	/**
	 * 新建实例.
	 * 
	 * @param className
	 *            类型
	 * @param args
	 *            构造参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(Class newoneClass, Object[] args)
			throws Exception {
		if (args != null && args.length > 0) {// 有参数的构造函数
			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Constructor cons = newoneClass.getConstructor(argsClass);
			return cons.newInstance(args);
		}
		// 无参数的构造函数
		return newInstance(newoneClass);
	}

	/**
	 * 新建实例.
	 * 
	 * @param className
	 *            类型
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(Class newoneClass) throws Exception {
		Constructor cons = newoneClass.getConstructor();
		return cons.newInstance();
	}

	/**
	 * 判断是否为某个类的实例.
	 * 
	 * @param obj
	 *            对象
	 * @param cls
	 *            类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isInstance(Object obj, Class cls) {
		return cls.isInstance(obj);
	}

	/**
	 * 获得对象字段名称数组.
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getListFields(Class c) {
		List<String> list = new ArrayList<String>();
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			list.add(fields[i].getName());
		}
		return list;
	}

	/**
	 * 获得对象字段名称Map[key:字段名称大写,value:字母名称].
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getMapUpCaseFields(Class c) {
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			map.put(fields[i].getName().toUpperCase(), fields[i].getName());
		}
		return map;
	}

	/**
	 * 获得字段.
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object obj, String fieldName)
			throws Exception {
		String methodName = "get"
				+ String.valueOf(fieldName.charAt(0)).toUpperCase()
				+ fieldName.substring(1);
		return invokeMethod(obj, methodName, new Object[] {});
	}

	/**
	 * 注入字段值.
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value)
			throws Exception {
		String methodName = "set"
				+ String.valueOf(fieldName.charAt(0)).toUpperCase()
				+ fieldName.substring(1);
		invokeMethod(obj, methodName, new Object[] { value });
	}

	/**
	 * 获得字段数组.
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getFields(Class obj) {
		return obj.getDeclaredFields();
	}

	/**
	 * 获得字段数组.
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Field> getMapFields(Class<?> obj) {
		Map<String, Field> map = new HashMap<String, Field>();
		Field[] fields = obj.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			map.put(fields[i].getName(), fields[i]);
		}
		return map;
	}

	/**
	 * 判断是否为空.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)
				&& ((((String) value).trim().length() <= 0) || "null"
						.equalsIgnoreCase((String) value))) {
			return true;
		}
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
			return true;
		}
		if (value instanceof Object[]) { // all item in [] are null :
			// 数组中所有元素为空
			Object[] t = (Object[]) value;
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					if (t[i] instanceof String) {
						if (((String) t[i]).trim().length() > 0
								|| "null".equalsIgnoreCase((String) t[i])) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return true;
		}
		if ((value instanceof Collection)
				&& ((Collection<?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Dictionary)
				&& ((Dictionary<?, ?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Map) && ((Map<?, ?>) value).size() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 强制转换类型.
	 * 
	 * @param obj
	 * @param toObjClass
	 * @return
	 */
	public static Object castObject(Object obj, Class toObjClass) {
		String objStr = ValueUtils.getString(obj);
		String toObjType = toObjClass.getName();
		Object toObj = obj;
		if ("java.lang.Integer".equals(toObjType) || "int".equals(toObjType)) {// 整形
			toObj = Integer.parseInt(objStr);
		} else if ("java.lang.Float".equals(toObjType)
				|| "float".equals(toObjType)) {// 浮点型
			toObj = Float.parseFloat(objStr);
		} else if ("java.lang.Double".equals(toObjType)
				|| "double".equals(toObjType)) {// 双精度
			toObj = Double.parseDouble(objStr);
		} else if ("java.lang.Boolean".equals(toObjType)
				|| "boolean".equals(toObjType)) {// 布尔型
			toObj = Boolean.parseBoolean(objStr);
		} else if ("java.lang.Long".equals(toObjType)
				|| "long".equals(toObjType)) {// Long类型
			toObj = Long.parseLong(objStr);
		} else if ("java.lang.Short".equals(toObjType)
				|| "short".equals(toObjType)) {// Short类型
			toObj = Short.parseShort(objStr);
		} else if ("java.sql.Timestamp".equals(toObjType)) { // Timestamp类型
			toObj = Timestamp.valueOf(objStr);
		} else if ("java.util.Date".equals(toObjType)) { // Date类型
			if(ObjectUtils.isEmpty(obj)){
				return null;
			}
			DateFormat df = DateFormat.getDateInstance();
			try {
				toObj = df.parseObject(objStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {// 字符串类型
			toObj = objStr;
		}
		return toObj;
	}
}
