package com.wq.ibaits;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.wq.sys.SysConstant;
import com.wq.utils.XmlUtils;

/**
 * Ibaits�ӿڹ���.
 * 
 * @author qingwu
 * 
 */
public class IbaitsFactory {

	/**
	 * sql���ӳ��Map.
	 */
	private static Map<String, IbaitsVO> sqlMap = null;

	/**
	 * ��ȡsqlMap���ӳ��.
	 * 
	 * @return
	 */
	public static Map<String, IbaitsVO> getSqlMap() {
		if (sqlMap == null) {
			initSqlMap();
		}
		return sqlMap;
	}

	/**
	 * ��ʼ��sqlMap.
	 */
	@SuppressWarnings("unchecked")
	public static void initSqlMap() {
		sqlMap = new HashMap<String, IbaitsVO>();
		synchronized (sqlMap) {
			// ���sqlMapConfig��Dom4J�ĵ�����
			Document sqlMapCfgDoc = XmlUtils
					.fileToDocument(SysConstant.SQL_MAP_CONFIG);
			// �����sqlXml�ļ��б�
			List<Element> importXmlList = sqlMapCfgDoc.getRootElement()
					.elements("sqlMap");
			for (int i = 0; i < importXmlList.size(); i++) {
				// �����sqlXml�ļ��ľ���sql���
				String importXmlUrl = importXmlList.get(i).attributeValue(
						"resource");
				if (importXmlUrl.charAt(0) != '/') {
					importXmlUrl = "/" + importXmlUrl;
				}
				Document importXmlDoc = XmlUtils.fileToDocument(importXmlUrl);
				List<Element> sqlXmlList = importXmlDoc.getRootElement()
						.elements();
				for (int j = 0; j < sqlXmlList.size(); j++) {
					String optType = sqlXmlList.get(j).getName();
					if (!optType.equals(IbaitsProxy.DELETE)
							&& !optType.equals(IbaitsProxy.UPDATE)
							&& !optType.equals(IbaitsProxy.SELECT)
							&& !optType.equals(IbaitsProxy.INSERT)) {
						continue;
					}
					IbaitsVO ibaitsVO = new IbaitsVO();
					ibaitsVO.setId(sqlXmlList.get(j).attributeValue("id"));
					ibaitsVO.setSql(sqlXmlList.get(j).getText());
					ibaitsVO.setOptType(sqlXmlList.get(j).getName());
					sqlMap.put(ibaitsVO.getId(), ibaitsVO);
				}
			}
		}
	}

	/**
	 * ����ӿڣ��ӿ�����дʵ����.
	 * 
	 * @param <T>
	 * @param mapperInterface
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T getBean(Class<T> mapperInterface) {
		IbaitsProxy proxy = new IbaitsProxy();
		ClassLoader classLoader = mapperInterface.getClassLoader();
		Class[] interfaces = new Class[] { mapperInterface };
		return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
	}

}
