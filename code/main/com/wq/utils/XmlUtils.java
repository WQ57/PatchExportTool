package com.wq.utils;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

/**
 * Dom4J工具类.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class XmlUtils {

	/**
	 * 字符串转Document
	 * 
	 * @param xml
	 * @return
	 */
	public static Document stringToDocument(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件转Document.
	 * 
	 * @param fileName
	 *            以“/”开头，根据工程项目相对路径读取
	 * @return
	 */
	public static Document fileToDocument(String fileName) {
		InputStream is = XmlUtils.class.getResourceAsStream(fileName);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}

	/**
	 * 输入流转Document
	 * 
	 * @param is
	 *            输入流
	 * @return
	 */
	public static Document inputStreamToDocument(InputStream is) {
		try {
			SAXReader reader = new SAXReader();
			return reader.read(is);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Document转字符串.
	 * 
	 * @param document
	 * @return
	 */
	public static String documentToString(Document document) {
		return document.asXML();
	}

}
