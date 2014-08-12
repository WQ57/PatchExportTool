package com.wq.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

/**
 * 文件选择过滤器.
 * 
 * @author qingwu
 * 
 */
public class MyFileFilter extends FileFilter {

	/**
	 * 过滤文件后缀.
	 */
	private List<String> filters = new ArrayList<String>();

	/**
	 * 描述.
	 */
	private String description = "All Files";

	public MyFileFilter(){

	}
	
	public MyFileFilter(String[] filters) {
		description = "";
		for (int i = 0; i < filters.length; i++) {
			this.filters.add(filters[i]);
			if (i != 0) {
				description += ",";
			}
			description += filters[i];
		}		
	}

	@Override
	public boolean accept(File f) {
		for (int i = 0; i < this.filters.size(); i++) {
			if (f.isDirectory() || f.getName().endsWith(filters.get(i))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
