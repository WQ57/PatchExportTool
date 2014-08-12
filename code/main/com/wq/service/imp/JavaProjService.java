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
 * java工程项目服务类.
 * 
 * @author wuqing
 * 
 */
public class JavaProjService {

	static LogUtils log = new LogUtils(JavaProjService.class);

	private static String classPathName = ".classpath";

	/**
	 * 获得java项目配置信息（读取eclipse生成的.classpath文件）.
	 * 
	 * @param projPath
	 *            工程项目路径
	 * @param kindFilter
	 *            类型过滤
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
