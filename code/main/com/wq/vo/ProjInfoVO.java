package com.wq.vo;

/**
 * 工程项目信息.
 * 
 * @author qingwu
 * 
 */
public class ProjInfoVO {

	/** 项目名称. */
	String projName;
	/** 项目根目录. */
	String projPath;
	/** 导出文件路径. */
	String exportPath;
	/** 读取的excel文件地址. */
	String excelPath;

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjPath() {
		return projPath;
	}

	public void setProjPath(String projPath) {
		this.projPath = projPath;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

}
