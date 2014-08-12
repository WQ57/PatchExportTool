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
 * Java文件操作服务实现类.
 * 
 * @author qingwu
 * 
 */
public class JavaFileServiceImp implements JavaFileService {

	/**
	 * 项目工程文件信息地址.
	 */
	public static final String projInfoTxtPath = "D://WqProj//WqProj.txt";

	/**
	 * java项目信息服务类.
	 */
	private JavaProjService javaProjService = new JavaProjService();

	/**
	 * 项目信息分隔符.
	 */
	public static final String projInfoSplitStr = "@@";

	@SuppressWarnings("static-access")
	@Override
	public List<ProjInfoVO> getCommonProjInfo() {
		List<ProjInfoVO> proList = new ArrayList<ProjInfoVO>();
		// 读取文件
		List<String> list = FileUtils.readFileByLines(this.projInfoTxtPath);
		if (list == null) {
			return proList;
		}
		// 逐行解析文件信息 (格式:项目名称@@项目根目录@@项目class目录@@项目导出路径@@excel路径)
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
		// 组装写入信息(格式:项目名称@@项目根目录@@项目class目录@@项目导出路径@@excel路径)
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
		// 写入文件
		return FileUtils.writeFile(this.projInfoTxtPath, content);
	}

	@Override
	public Result exportFiles(ProjInfoVO projInfoVO) {
		// 对地址分隔符进行统一
		projInfoVO.setExportPath(connectSplit(projInfoVO.getExportPath()));
		projInfoVO.setProjPath(connectSplit(projInfoVO.getProjPath()));
		projInfoVO.setExcelPath(connectSplit(projInfoVO.getExcelPath()));

		Result result = new Result();
		result.setSuccess(false);

		// java编译输出路径配置列表
		List<JavaProjVO> srcList = javaProjService.getJavaProjInfo(
				projInfoVO.getProjPath(), new String[] { "src", "output" });

		try {
			// 读取excel文件内容,获得Java文件列表
			List<JavaFileVO> javaFileList = getJavaFileListFromExcel(projInfoVO
					.getExcelPath());

			// 获得java文件列表
			Map<String, String> soruceMap = getSourceMap(javaFileList);

			// 复制文件
			for (int i = 0; i < javaFileList.size(); i++) {

				if (javaFileList.get(i).getPath().indexOf(".java") != -1) {
					// java文件
					if (!copyJavaFile(projInfoVO, javaFileList.get(i))) {
						result.setSuccess(false);
						result.setMsg(result.getMsg() + "\n" + "Java文件复制失败:可能是"
								+ javaFileList.get(i).getPath() + "不存在！");
					} else {
						result.setMsg(result.getMsg() + "\n" + "Java文件复制成功："
								+ javaFileList.get(i).getPath());
					}

					// class文件
					if (!copyClassFile(projInfoVO, javaFileList.get(i), srcList)) {
						result.setSuccess(false);
						result.setMsg(result.getMsg()
								+ "\n"
								+ "Class文件复制失败:可能是"
								+ getClassFilePath(projInfoVO,
										javaFileList.get(i), srcList) + "不存在！");
					} else {
						result.setMsg(result.getMsg()
								+ "\n"
								+ "Class文件复制成功："
								+ getClassFilePath(projInfoVO,
										javaFileList.get(i), srcList));
					}

				} else {
					// 其它文件
					if (!copyOtherFile(projInfoVO, javaFileList.get(i),
							soruceMap, srcList)) {
						result.setSuccess(false);
						result.setMsg(result.getMsg() + "\n" + "文件复制失败:可能是"
								+ javaFileList.get(i).getPath() + "不存在！");
					} else {
						result.setMsg(result.getMsg() + "\n" + "文件复制成功："
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
	 * 复制其它文件.
	 * 
	 * @param projInfoVO
	 *            项目信息
	 * @param javaFileVO
	 *            java文件信息
	 * @param sourceMap
	 *            编译路径
	 * @param srcList
	 *            class文件编译路径
	 * @return
	 */
	private boolean copyOtherFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO,
			Map<String, String> sourceMap, List<JavaProjVO> srcList) {
		// 1.校验,非java文件退出
		if (javaFileVO.getPath().indexOf(".java") != -1) {
			return true;
		}

		// 2.复制到源文件
		String sourceFile = concatUrlStr(projInfoVO.getProjPath(),
				javaFileVO.getPath());
		String targetFile = concatUrlStr(projInfoVO.getExportPath(),
				javaFileVO.getPath());

		boolean b = FileUtils.copyFile(sourceFile, targetFile);

		// 3.如果是编译路径下文件,复制到class文件目录下
		String output = null;
		JavaProjVO srcVO = null;
		for (JavaProjVO vo : srcList) {
			// 如果有单独设置output，默认是该output
			if (vo.getKind().equals("output")) {
				output = vo.getPath();
			}
			// 该java文件是该编译路径下的
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
	 * 复制java文件.
	 * 
	 * @param projInfoVO
	 *            项目信息
	 * @param javaFileVO
	 *            java文件信息
	 */
	private boolean copyJavaFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO) {
		// 非java文件
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
	 * 复制class文件.
	 * 
	 * @param projInfoVO
	 *            项目信息
	 * @param javaFileVO
	 *            java文件信息
	 * @param srcList
	 *            class文件编译路径
	 */
	private boolean copyClassFile(ProjInfoVO projInfoVO, JavaFileVO javaFileVO,
			List<JavaProjVO> srcList) {
		// 非java文件
		if (javaFileVO.getPath().indexOf(".java") == -1) {
			return true;
		}
		// 获得class文件的源文件地址和输出地址
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
	 * 获得源class文件的路径 .
	 * 
	 * @param projInfoVO
	 *            导出信息信息vo
	 * @param javaFileVO
	 *            java文件vo
	 * @param srcList
	 *            .classpath文件配置
	 * @return
	 */
	private String getClassFilePath(ProjInfoVO projInfoVO,
			JavaFileVO javaFileVO, List<JavaProjVO> srcList) {
		String output = null;
		JavaProjVO srcVO = null;
		for (JavaProjVO vo : srcList) {
			// 如果有单独设置output，默认是该output
			if (vo.getKind().equals("output")) {
				output = vo.getPath();
			}
			// 该java文件是该编译路径下的
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
	 * 获得编译文件路径.
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
					&& !map.containsKey(path.split("/")[0])) {// 如果是java文件,则是编译路径
				map.put(path.split("/")[0], path.split("/")[0]);
			}
		}
		return map;
	}

	/**
	 * 读取excel文件内容,获得Java文件列表.
	 * 
	 * @param excelPath
	 *            excel文件地址
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private List<JavaFileVO> getJavaFileListFromExcel(String excelPath)
			throws FileNotFoundException, IOException {
		// Java文件列表
		List<JavaFileVO> javaFileList = new ArrayList<JavaFileVO>();

		// 读取excel文件内容
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
	 * 纠正url分割符.
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
	 * 连接两个地址.
	 * 
	 * @param str1
	 *            地址一
	 * @param str2
	 *            地址二
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
