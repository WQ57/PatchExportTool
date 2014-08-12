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
 * ���󹤾���.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class ObjectUtils {

	/**
	 * ���public��Ϥ�Ķ����Ա.
	 * 
	 * @param owner
	 *            �����
	 * @param fieldName
	 *            �ֶ�����
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
	 * �õ�ĳ����ľ�̬����.
	 * 
	 * @param ownerClass
	 *            ����
	 * @param fieldName
	 *            �ֶ�����
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
	 * ִ��ĳ������ķ���.
	 * 
	 * @param owner
	 *            ����
	 * @param methodName
	 *            ��������
	 * @param args
	 *            ��������
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
	 * ִ��ĳ����ľ�̬����.
	 * 
	 * @param className
	 *            ����
	 * @param methodName
	 *            ��������
	 * @param args
	 *            ��������
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
	 * �½�ʵ��.
	 * 
	 * @param className
	 *            ����
	 * @param args
	 *            �������
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(Class newoneClass, Object[] args)
			throws Exception {
		if (args != null && args.length > 0) {// �в����Ĺ��캯��
			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Constructor cons = newoneClass.getConstructor(argsClass);
			return cons.newInstance(args);
		}
		// �޲����Ĺ��캯��
		return newInstance(newoneClass);
	}

	/**
	 * �½�ʵ��.
	 * 
	 * @param className
	 *            ����
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(Class newoneClass) throws Exception {
		Constructor cons = newoneClass.getConstructor();
		return cons.newInstance();
	}

	/**
	 * �ж��Ƿ�Ϊĳ�����ʵ��.
	 * 
	 * @param obj
	 *            ����
	 * @param cls
	 *            ����
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isInstance(Object obj, Class cls) {
		return cls.isInstance(obj);
	}

	/**
	 * ��ö����ֶ���������.
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
	 * ��ö����ֶ�����Map[key:�ֶ����ƴ�д,value:��ĸ����].
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
	 * ����ֶ�.
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
	 * ע���ֶ�ֵ.
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
	 * ����ֶ�����.
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getFields(Class obj) {
		return obj.getDeclaredFields();
	}

	/**
	 * ����ֶ�����.
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
	 * �ж��Ƿ�Ϊ��.
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
			// ����������Ԫ��Ϊ��
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
	 * ǿ��ת������.
	 * 
	 * @param obj
	 * @param toObjClass
	 * @return
	 */
	public static Object castObject(Object obj, Class toObjClass) {
		String objStr = ValueUtils.getString(obj);
		String toObjType = toObjClass.getName();
		Object toObj = obj;
		if ("java.lang.Integer".equals(toObjType) || "int".equals(toObjType)) {// ����
			toObj = Integer.parseInt(objStr);
		} else if ("java.lang.Float".equals(toObjType)
				|| "float".equals(toObjType)) {// ������
			toObj = Float.parseFloat(objStr);
		} else if ("java.lang.Double".equals(toObjType)
				|| "double".equals(toObjType)) {// ˫����
			toObj = Double.parseDouble(objStr);
		} else if ("java.lang.Boolean".equals(toObjType)
				|| "boolean".equals(toObjType)) {// ������
			toObj = Boolean.parseBoolean(objStr);
		} else if ("java.lang.Long".equals(toObjType)
				|| "long".equals(toObjType)) {// Long����
			toObj = Long.parseLong(objStr);
		} else if ("java.lang.Short".equals(toObjType)
				|| "short".equals(toObjType)) {// Short����
			toObj = Short.parseShort(objStr);
		} else if ("java.sql.Timestamp".equals(toObjType)) { // Timestamp����
			toObj = Timestamp.valueOf(objStr);
		} else if ("java.util.Date".equals(toObjType)) { // Date����
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
		} else {// �ַ�������
			toObj = objStr;
		}
		return toObj;
	}
}
