package com.wq.ibaits;

/**
 * Ibaits�ĵ�����VO
 * 
 * @author wuqing
 * 
 */
public class IbaitsVO {
	/**
	 * ID.
	 */
	private String id;
	/**
	 * ��������.
	 */
	private String optType;
	/**
	 * sql���.
	 */
	private String sql;

	/**
	 * ��������.
	 */
	private String returnType;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the optType
	 */
	public String getOptType() {
		return optType;
	}

	/**
	 * @param optType
	 *            the optType to set
	 */
	public void setOptType(String optType) {
		this.optType = optType;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
