package com.wq.vo;

/**
 * JavaÏîÄ¿vo.
 * 
 * @author wuqing
 * 
 */
public class JavaProjVO {

	private String kind;

	private String path;

	private String output;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "JavaProjVO [kind=" + kind + ", path=" + path + ", output="
				+ output + "]";
	}

}
