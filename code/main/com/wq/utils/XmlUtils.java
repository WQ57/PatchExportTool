package com.wq.utils;

import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

/**
 * Dom4J������.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
public class XmlUtils {

	/**
	 * �ַ���תDocument
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
	 * �ļ�תDocument.
	 * 
	 * @param fileName
	 *            �ԡ�/����ͷ�����ݹ�����Ŀ���·����ȡ
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
	 * ������תDocument
	 * 
	 * @param is
	 *            ������
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
	 * Documentת�ַ���.
	 * 
	 * @param document
	 * @return
	 */
	public static String documentToString(Document document) {
		return document.asXML();
	}

}
