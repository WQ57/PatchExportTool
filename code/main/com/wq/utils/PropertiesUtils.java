package com.wq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties工具类.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class PropertiesUtils {
	/**
	 * 配置文件.
	 */
	private Properties prop;
	/**
	 * 输入流.
	 */
	private InputStream is;

	/**
	 * 构造函数.
	 * 
	 * @param filename
	 *            以“/”开头，根据工程项目路径读取
	 */
	public PropertiesUtils(String filename) {
		prop = new Properties();
		is = getClass().getResourceAsStream(filename);
		try {
			prop.load(is);
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取属性.
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getProperties(String propertyName) {
		return prop.getProperty(propertyName);
	}
}
