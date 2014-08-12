package com.wq.utils;

import org.apache.log4j.Logger;

/**
 * ��־����������.
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
	 * ����̨��ӡ.
	 * 
	 * @param obj
	 */
	public static void logToConsole(Object obj) {
		System.out.println(ValueUtils.getString(obj));
	}

	/**
	 * ����̨��ӡ.
	 * 
	 * @param obj
	 */
	public static void errToConsole(Object obj) {
		System.err.println(ValueUtils.getString(obj));
	}
	
	/**
	 * log4j��ʾ��־.
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void info(String message) {
		logger.info(message);
	}

	/**
	 * log4j������־.
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	public void err(String message) {
		logger.error(message);
	}
}
