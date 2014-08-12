package com.wq.service.imp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wq.service.JavaFileService;
import com.wq.utils.FileUtils;
import com.wq.utils.excel.WqExcelUtils;
import com.wq.utils.excel.vo.WqSheetVO;
import com.wq.vo.JavaFileVO;
import com.wq.vo.JavaProjVO;
import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

/**
 * Java�ļ���������ʵ����.
 * 
 * @author qingwu
 * 
 */
public class JavaFileServiceImp implements JavaFileService {

	/**
	 * ��Ŀ�����ļ���Ϣ��ַ.
	 */
	public static final String projInfoTxtPath = "D://WqProj//WqProj.txt";

	/**
	 * java��Ŀ��Ϣ������.
	 */
	private JavaProjService javaProjService = new JavaProjService();

	/**
	 * ��Ŀ��Ϣ�ָ���.
	 */
	public static final String projInfoSplitStr = "@@";

	@SuppressWarnings("static-access")
	@Override
	public List<ProjInfoVO> getCommonProjInfo() {
		List<ProjInfoVO> proList = new ArrayList<ProjInfoVO>();
		// ��ȡ�ļ�
		List<String> list = FileUtils.readFileByLines(this.projInfoTxtPath);
		if (list == null) {
			return proList;
		}
		// ���н����ļ���Ϣ (��ʽ:��Ŀ����@@��Ŀ��Ŀ¼@@��ĿclassĿ¼@@��Ŀ����·��@@excel·��)
		for (int i = 0; i < list.size(); i++) {
			String[] arry = list.get(i).split(this.projInfoSplitStr);
			ProjInfoVO vo = new ProjInfoVO();
			vo.setProjName((arry.length > 0 && arry[0] != null) ? arry[0] : "");
			vo.setProjPath((arry.length > 1 && arry[1] != null) ? arry[1] : "");
			vo.setExportPath((arry.length > 2 && arry[2] != null) ? arry[2]
					: "");
			vo.setExcelPath((arry.length > 3 && arry[3] != null) ? arry[3] : "");
			proList.add(vo);
		}
		return proList;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean wirteCommonProjInfo(List<ProjInfoVO> list) {
		// ��װд����Ϣ(��ʽ:��Ŀ����@@��Ŀ��Ŀ¼@@��ĿclassĿ¼@@��Ŀ����·��@@excel·��)
		String content = "";
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				content += "\r";
			}
			ProjInfoVO vo = list.get(i);
			content += vo.getProjName() + this.projInfoSplitStr
					+ vo.getProjPath() + this.projInfoSplitStr
					+ vo.getExportPath() + this.projInfoSplitStr
					+ vo.getExcelPath();
		}
		// д���ļ�
		return FileUtils.writeFile(this.projInfoTxtPath, content);
	}

	@Override
	public Result exportFiles(ProjInfoVO projInfoVO) {
		// �Ե�ַ�ָ�������ͳһ
		projInfoVO.setExportPath(connectSplit(projInfoVO.getExportPath()));
		projInfoVO.setProjPath(connectSplit(projInfoVO.getProjPath()));
		projInfoVO.setExcelPath(connectSplit(projInfoVO.getExcelPath()));

		Result result = new Result();
		result.setSuccess(false);

		// java�������·�������б�
		List<JavaProjVO> srcList = javaProjService.getJavaProjInfo(
				projInfoVO.getProjPath(), new String[] { "src", "output" });

		try {
			// ��ȡexcel�ļ�����,���Java�ļ��б�
			List<JavaFileVO> javaFileList = getJavaFileListFromExcel(projInfoVO
					.getExcelPath());

			// ���java�ļ��б�
			Map<String, String> soruceMap = getSourceMap(javaFileList);

			// �����ļ�
			for (int i = 0; i < javaFileList.size(); i++) {

				if (javaFileList.get(i).getPath().indexOf(".java") != -1) {
					// java�ļ�
					if (!copyJavaFile(projInfoVO, javaFileList.get(i))) {
						result.setSuccess(false);
						result.setMsg(result.getMsg() + "\n" + "Java�ļ�����ʧ��:������"
								+ javaFileList.get(i).getPath() + "�����ڣ�");
					} else {
						result.setMsg(result.getMsg() + "\n" + "Java�ļ����Ƴɹ���"
								+ javaFileList.get(i).getPath());
					}

					// class�ļ�
					if (!copyClassFile(projInfoVO, javaFileList.get(i), srcList)) {
						result.setSuccess(false);
						result.setMsg(result.getMsg()
								+ "\n"
								+ "Class�ļ�����ʧ��:������"
								+ getClassFilePath(projInfoVO,
										javaFileList.get(i), srcList) + "�����ڣ�");
					} else {
						result.setMsg(result.getMsg()
								+ "\n"
								+ "Class�ļ����Ƴɹ���"
								+ getClassFilePath(projInfoVO,
										javaFileList.get(i), srcList));
					}

				} else {
					// �����ļ�
					if (!copyOtherFile(projInfoVO, javaFileList.get(i),
							soruceMap, srcList)) {
						result.setSuccess(false);
						result.setMsg(result.getMsg() + "\n" + "�ļ�����ʧ��:������"
								+ javaFileList.get(i).getPath() + "�����ڣ�");
					} else {
						result.setMsg(result.getMsg() + "\n" + "�ļ����Ƴɹ���"
								+ javaFileList.get(i).getPath());
					}
				}
			}

			result.setSuccess(true);
		} catch (FileNotFoundException e) {
			result.setMsg(e.getMessage());
			return result;
		} catch (IOException e) {
			result.setMsg(e.getMessage());
			return result;
		}
		return result;
	}

	// ******************** private method *******************

	/**
	 * ���������ļ�.
	 * 
	 * @param projInfoVO
	 *            ��Ŀ��Ϣ
	 * @param javaFileVO
	 *            java�ļ���Ϣ
	 * @param sourceMap
	 *            ����·��
	 * @param srcList
	 *            class�ļ�����·��
	 * @return
	 */
	private boolean copyOtherFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO,
			Map<String, String> sourceMap, List<JavaProjVO> srcList) {
		// 1.У��,��java�ļ��˳�
		if (javaFileVO.getPath().indexOf(".java") != -1) {
			return true;
		}

		// 2.���Ƶ�Դ�ļ�
		String sourceFile = concatUrlStr(projInfoVO.getProjPath(),
				javaFileVO.getPath());
		String targetFile = concatUrlStr(projInfoVO.getExportPath(),
				javaFileVO.getPath());

		boolean b = FileUtils.copyFile(sourceFile, targetFile);

		// 3.����Ǳ���·�����ļ�,���Ƶ�class�ļ�Ŀ¼��
		String output = null;
		JavaProjVO srcVO = null;
		for (JavaProjVO vo : srcList) {
			// ����е�������output��Ĭ���Ǹ�output
			if (vo.getKind().equals("output")) {
				output = vo.getPath();
			}
			// ��java�ļ��Ǹñ���·���µ�
			if (vo.getKind().equals("src")
					&& javaFileVO.getPath().startsWith(vo.getPath() + "/")) {
				srcVO = vo;
			}
		}
		if (srcVO != null) {
			output = srcVO.getOutput() != null ? srcVO.getOutput() : output;
			String classPathFile = output + "/"
					+ javaFileVO.getPath().replace(srcVO.getPath() + "/", "");
			sourceFile = concatUrlStr(projInfoVO.getProjPath(), classPathFile);
			targetFile = concatUrlStr(projInfoVO.getExportPath(), classPathFile);
			b = (FileUtils.copyFile(sourceFile, targetFile) && b);
		}

		return b;
	}

	/**
	 * ����java�ļ�.
	 * 
	 * @param projInfoVO
	 *            ��Ŀ��Ϣ
	 * @param javaFileVO
	 *            java�ļ���Ϣ
	 */
	private boolean copyJavaFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO) {
		// ��java�ļ�
		if (javaFileVO.getPath().indexOf(".java") == -1) {
			return true;
		}
		String sourceFile = concatUrlStr(projInfoVO.getProjPath(),
				javaFileVO.getPath());
		String targetFile = concatUrlStr(projInfoVO.getExportPath(),
				javaFileVO.getPath());
		return FileUtils.copyFile(sourceFile, targetFile);
	}

	/**
	 * ����class�ļ�.
	 * 
	 * @param projInfoVO
	 *            ��Ŀ��Ϣ
	 * @param javaFileVO
	 *            java�ļ���Ϣ
	 * @param srcList
	 *            class�ļ�����·��
	 */
	private boolean copyClassFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO,
			List<JavaProjVO> srcList) {
		// ��java�ļ�
		if (javaFileVO.getPath().indexOf(".java") == -1) {
			return true;
		}
		// ���class�ļ���Դ�ļ���ַ�������ַ
		String classFilePath = getClassFilePath(projInfoVO, javaFileVO, srcList);
		if (classFilePath == null) {
			return false;
		}
		String sourceFile = concatUrlStr(projInfoVO.getProjPath(),
				classFilePath);
		String targetFile = concatUrlStr(projInfoVO.getExportPath(),
				classFilePath);
		return FileUtils.copyFile(sourceFile, targetFile);
	}

	/**
	 * ���Դclass�ļ���·�� .
	 * 
	 * @param projInfoVO
	 *            ������Ϣ��Ϣvo
	 * @param javaFileVO
	 *            java�ļ�vo
	 * @param srcList
	 *            .classpath�ļ�����
	 * @return
	 */
	private String getClassFilePath(ProjInfoVO projInfoVO,
			JavaFileVO javaFileVO, List<JavaProjVO> srcList) {
		String output = null;
		JavaProjVO srcVO = null;
		for (JavaProjVO vo : srcList) {
			// ����е�������output��Ĭ���Ǹ�output
			if (vo.getKind().equals("output")) {
				output = vo.getPath();
			}
			// ��java�ļ��Ǹñ���·���µ�
			if (vo.getKind().equals("src")
					&& javaFileVO.getPath().startsWith(vo.getPath() + "/")) {
				srcVO = vo;
			}
		}
		if (srcVO == null) {
			return null;
		}
		output = srcVO.getOutput() != null ? srcVO.getOutput() : output;
		String str = output
				+ "/"
				+ javaFileVO.getPath().replace(srcVO.getPath() + "/", "")
						.replace(".java", ".class");
		return str;
	}

	/**
	 * ��ñ����ļ�·��.
	 * 
	 * @param javaFileList
	 * @return
	 */
	private Map<String, String> getSourceMap(List<JavaFileVO> javaFileList) {
		Map<String, String> map = new HashMap<String, String>();
		for (JavaFileVO vo : javaFileList) {
			String path = vo.getPath();
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
			if (path.indexOf(".java") > 0
					&& !map.containsKey(path.split("/")[0])) {// �����java�ļ�,���Ǳ���·��
				map.put(path.split("/")[0], path.split("/")[0]);
			}
		}
		return map;
	}

	/**
	 * ��ȡexcel�ļ�����,���Java�ļ��б�.
	 * 
	 * @param excelPath
	 *            excel�ļ���ַ
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private List<JavaFileVO> getJavaFileListFromExcel(String excelPath)
			throws FileNotFoundException, IOException {
		// Java�ļ��б�
		List<JavaFileVO> javaFileList = new ArrayList<JavaFileVO>();

		// ��ȡexcel�ļ�����
		List<WqSheetVO> excelList = WqExcelUtils.readExcel(excelPath);
		for (int i = 0; i < excelList.size(); i++) {
			WqSheetVO vo = excelList.get(i);
			for (int j = 0; j < vo.getData().size(); j++) {
				for (int t = 0; t < vo.getData().get(j).size(); t++) {
					String str = vo.getData().get(j).get(t).toString();
					str = connectSplit(str);
					JavaFileVO javaFileVO = new JavaFileVO();
					javaFileVO.setPath(str);
					javaFileList.add(javaFileVO);
				}
			}
		}
		return javaFileList;
	}

	/**
	 * ����url�ָ��.
	 * 
	 * @param str
	 * @return
	 */
	public String connectSplit(String str) {
		if (str != null) {
			str = str.replace("\\", "/");
			str = str.replace("//", "/");
		}
		return str;
	}

	/**
	 * ����������ַ.
	 * 
	 * @param str1
	 *            ��ַһ
	 * @param str2
	 *            ��ַ��
	 * @return
	 */
	public String concatUrlStr(String str1, String str2) {
		String str = str1 + str2;
		if (!str1.endsWith("/") && !str2.startsWith("/")) {
			str = str1 + "/" + str2;
		}
		return str;
	}
}
