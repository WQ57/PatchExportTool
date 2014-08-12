package com.wq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class DateUtils {

	/**
	 * long类型转日期.
	 * 
	 * @param l
	 * @return
	 */
	public static Date longToDate(Long l) {
		return new Date(l);
	}

	/**
	 * Date转字符串.
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormate) {
		if (ObjectUtils.isEmpty(date)) {
			return null;
		}
		String _dateFormate = "yyyy-MM-dd HH:mm:ss";
		if (dateFormate != null && !dateFormate.equals("")) {
			_dateFormate = dateFormate;
		}
		return new SimpleDateFormat(_dateFormate).format(date);
	}

	/**
	 * 字符串转date.
	 * 
	 * @param date
	 * @param dateFormate
	 * @return
	 */
	public static Date stringToDate(String date, String dateFormate) {
		if (ObjectUtils.isEmpty(date)) {
			return null;
		}
		String _dateFormate = "yyyy-MM-dd HH:mm:ss";
		if (dateFormate != null && !dateFormate.equals("")) {
			_dateFormate = dateFormate;
		}
		try {
			return new SimpleDateFormat(_dateFormate).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
