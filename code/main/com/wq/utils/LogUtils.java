package com.wq.utils;

import org.apache.log4j.Logger;

/**
 * 日志操作工具类.
 * 
 * @author wq
 * 
 */
public class LogUtils {

	Logger logger;

	@SuppressWarnings("unchecked")
	public LogUtils(Class cla) {
		logger = Logger.getLogger(cla.getName());
	}

	/**
	 * 控制台打印.
	 * 
	 * @param obj
	 */
	public static void logToConsole(Object obj) {
		System.out.println(ValueUtils.getString(obj));
	}

	/**
	 * 控制台打印.
	 * 
	 * @param obj
	 */
	public static void errToConsole(Object obj) {
		System.err.println(ValueUtils.getString(obj));
	}
	
	/**
	 * log4j提示日志.
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void info(String message) {
		logger.info(message);
	}

	/**
	 * log4j报错日志.
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void err(String message) {
		logger.error(message);
	}
}
