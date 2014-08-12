package com.wq.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.wq.utils.excel.WqExcelUtils;
import com.wq.utils.excel.vo.WqSheetVO;

public class ExcelUtilsTest extends TestCase {

	public void testWriteExcel() {
		List<WqSheetVO> list = new ArrayList<WqSheetVO>();
		String excelPath = "C://tmpExcel.xls";
		for (int i = 1; i <= 3; i++) {
			WqSheetVO vo = new WqSheetVO();
			vo.setSheetName("测试sheet_" + i);
			for (int j = 0; j < 10; j++) {
				vo.getHead().add(i + "_列头" + j);
			}
			for (int j = 0; j < 10; j++) {
				List<Object> row = new ArrayList<Object>();
				for (int t = 0; t < 10; t++) {
					row.add(i + "_数据_" + t + "_" + j);
				}
				vo.getData().add(row);
			}
			list.add(vo);
		}
		WqExcelUtils.writeExcel(list, excelPath);
	}

	public void testReadExcel() {
		String excelPath = "E:\\exportFiles\\sns_old\\test.xls";
		try {
			List<WqSheetVO> list = WqExcelUtils.readExcel(excelPath);
			for (int i = 0; i < list.size(); i++) {
				WqSheetVO vo = list.get(i);
				System.out.println("sheetName:" + vo.getSheetName());
				for (int j = 0; j < vo.getHead().size(); j++) {
					System.out.print(vo.getHead().get(j) + "  ");
				}
				System.out.println();
				for (int j = 0; j < vo.getData().size(); j++) {
					for (int t = 0; t < vo.getData().get(j).size(); t++) {
						System.out.print(vo.getData().get(j).get(t).toString()
								+ "  ");
					}
					System.out.println();
				}
				System.out.println();
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
