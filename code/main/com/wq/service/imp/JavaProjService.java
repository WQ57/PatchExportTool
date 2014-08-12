package com.wq.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.wq.utils.FileUtils;
import com.wq.utils.LogUtils;
import com.wq.utils.XmlUtils;
import com.wq.vo.JavaProjVO;

/**
 * java������Ŀ������.
 * 
 * @author wuqing
 * 
 */
public class JavaProjService {

	static LogUtils log = new LogUtils(JavaProjService.class);

	private static String classPathName = ".classpath";

	/**
	 * ���java��Ŀ������Ϣ����ȡeclipse���ɵ�.classpath�ļ���.
	 * 
	 * @param projPath
	 *            ������Ŀ·��
	 * @param kindFilter
	 *            ���͹���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JavaProjVO> getJavaProjInfo(String projPath,
			String[] kindFilters) {
		List<JavaProjVO> list = new ArrayList<JavaProjVO>();
		try {
			String classpathContents = FileUtils.readFile(projPath + "/"
					+ classPathName);
			Document doc = XmlUtils.stringToDocument(classpathContents);
			List<Element> classpathentrys = doc.getRootElement().elements(
					"classpathentry");
			for (Element e : classpathentrys) {
				String kind = e.attributeValue("kind");
				String output = e.attributeValue("output");
				String path = e.attributeValue("path");
				for (String kindFilter : kindFilters) {
					if (kindFilter != null && kindFilter.equals(kind)) {
						JavaProjVO vo = new JavaProjVO();
						vo.setKind(kind);
						vo.setOutput(output);
						vo.setPath(path);
						list.add(vo);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
