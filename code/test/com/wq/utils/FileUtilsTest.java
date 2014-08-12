package com.wq.utils;

import java.util.List;

import junit.framework.TestCase;

public class FileUtilsTest extends TestCase {
	String fileName = "C:/newTemp.txt";

	public void testReadFileByLines() {
		List<String> list = FileUtils.readFileByLines(fileName);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public static void main(String[] args) {

		// FileUtils.readFileByBytes(fileName);
		// FileUtils.readFileByChars(fileName);
		// FileUtils.readFileByRandomAccess(fileName);
	}

}
