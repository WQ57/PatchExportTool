package com.wq.vo;

/**
 * ������Ŀ��Ϣ.
 * 
 * @author qingwu
 * 
 */
public class ProjInfoVO {

	/** ��Ŀ����. */
	String projName;
	/** ��Ŀ��Ŀ¼. */
	String projPath;
	/** �����ļ�·��. */
	String exportPath;
	/** ��ȡ��excel�ļ���ַ. */
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
