package com.wq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties������.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class PropertiesUtils {
	/**
	 * �����ļ�.
	 */
	private Properties prop;
	/**
	 * ������.
	 */
	private InputStream is;

	/**
	 * ���캯��.
	 * 
	 * @param filename
	 *            �ԡ�/����ͷ�����ݹ�����Ŀ·����ȡ
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
	 * ��ȡ����.
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getProperties(String propertyName) {
		return prop.getProperty(propertyName);
	}
}
