package com.wq.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ֵ���ù�����.
 * 
 * @author wuqing
 * 
 */
public class ValueUtils {

	/**
	 * �������벢ȥ����ѧ������, Ĭ��С����2λ.
	 * 
	 * @param value
	 *            String, double, Double, BigDecimal
	 * @return
	 */
	public static String toNuSicen(Object value) {
		return toNuSicen(value, 2);
	}

	/**
	 * �������벢ȥ����ѧ������.
	 * 
	 * @param value
	 *            String, double, Double, BigDecimal
	 * @param precision
	 *            ������λС��
	 * @return
	 */
	public static String toNuSicen(Object value, int precision) {
		Object result = "";
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(precision);
		df.setMaximumFractionDigits(precision);
		df.setGroupingUsed(false);
		if (ObjectUtils.isEmpty(value)) {
			return df.format(0);
		} else if (value instanceof BigDecimal) {
			result = value;
		} else if (value instanceof String) {
			result = new BigDecimal(String.valueOf(value));
		} else if (value instanceof Number) {
			result = Double.parseDouble(value.toString());
		} else {
			throw new IllegalArgumentException(value
					+ "need extends Number or String");
		}
		return df.format(result);
	}

	/**
	 * ֵ���� --> String.
	 * 
	 * @param value
	 * @return
	 */
	public static String getString(Object value) {
		String result = "";
		if (!ObjectUtils.isEmpty(value)) {
			String sValue = value.toString().trim();
			if (value instanceof Number) {
				if (value instanceof Double || value instanceof BigDecimal) {
					if (!"Infinity".equals(sValue) && !"NaN".equals(sValue)) {
						result = ValueUtils.toNuSicen(value);
					} else {
						result = "0";
					}
				} else {
					result = sValue;
				}
			} else {
				result = sValue;
			}
		}
		return result.trim();
	}

	/**
	 * ֵ���� --> long.
	 * 
	 * @param value
	 * @return
	 */
	public static long getLong(Object value) {
		try {
			return Long.parseLong(getString(value));
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * ֵ���� --> double.
	 * 
	 * @param value
	 * @return
	 */
	public static double getDouble(Object value) {
		try {
			return Double.parseDouble(getString(value));
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * ֵ���� --> int.
	 * 
	 * @param value
	 * @return
	 */
	public static int getInt(Object value) {
		try {
			return Integer.parseInt(getString(value));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * ֵ���� --> boolean.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean getBoolean(Object value) {
		try {
			String v = getString(value);
			if ("1".equals(v)) {
				return true;
			} else if ("0".equals(v)) {
				return false;
			} else if ("Y".equals(v)) {
				return true;
			} else if ("N".equals(v)) {
				return false;
			} else if ("0SA".equals(v)) {
				return true;
			} else if ("0SX".equals(v)) {
				return false;
			} else {
				return Boolean.parseBoolean(v);
			}
		} catch (Exception e) {
			return false;
		}
	}

}
