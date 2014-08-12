package com.wq.utils.excel.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * sheet页VO.
 * 
 * @author wuqing
 * 
 */
public class WqSheetVO {

	/**
	 * sheet页名称.
	 */
	private String sheetName;
	/**
	 * 列头.
	 */
	private List<String> head = new ArrayList<String>();
	/**
	 * 数据.
	 */
	private List<List<Object>> data = new ArrayList<List<Object>>();

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName
	 *            the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the head
	 */
	public List<String> getHead() {
		return head;
	}

	/**
	 * @param head
	 *            the head to set
	 */
	public void setHead(List<String> head) {
		this.head = head;
	}

	/**
	 * @return the data
	 */
	public List<List<Object>> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<List<Object>> data) {
		this.data = data;
	}

}
