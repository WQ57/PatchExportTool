package com.wq.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.wq.utils.excel.vo.WqSheetVO;

/**
 * excel工具实现类.
 * 
 * @author wuqing
 * 
 */
public class WqExcelUtils {

	/**
	 * 写excel.
	 * 
	 * @param list
	 * @param excelPath
	 */
	@SuppressWarnings({ "deprecation" })
	public static void writeExcel(List<WqSheetVO> list, String excelPath) {
		FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();// excel文档
			for (int i = 0; i < list.size(); i++) {
				HSSFSheet sheet = wb.createSheet(list.get(i).getSheetName());// 创建sheet页
				HSSFRow headRow = sheet.createRow(0);// 创建列头行
				List<String> headList = list.get(i).getHead();// 列头列表
				for (int j = 0; j < headList.size(); j++) {// 插入列头
					HSSFCell cell = headRow.createCell(j);
					// HSSFCellStyle style = wb.createCellStyle();
					// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					// style.setFillBackgroundColor(HSSFColor.GREEN.index);
					// cell.setCellStyle(style);
					cell.setCellValue(headList.get(j));
				}
				List<List<Object>> dataList = list.get(i).getData();// 数据列表
				for (int j = 0; j < dataList.size(); j++) {
					HSSFRow dataRow = sheet.createRow(j + 1);// 创建数据行
					for (int t = 0; t < dataList.get(j).size(); t++) {// 插入行数据
						HSSFCell cell = dataRow.createCell(t);
						cell.setCellValue(dataList.get(j).get(t).toString());
					}
				}
			}
			// 输出excel文档到指定位置
			fileOut = new FileOutputStream(excelPath);
			wb.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读excel.
	 * 
	 * @param excelPath
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("deprecation")
	public static List<WqSheetVO> readExcel(String excelPath)
			throws FileNotFoundException, IOException {
		List<WqSheetVO> list = new ArrayList<WqSheetVO>();

		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelPath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);// 读取excel
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {// 遍历sheet
			WqSheetVO vo = new WqSheetVO();
			HSSFSheet sheet = wb.getSheetAt(i);// 当前sheet
			vo.setSheetName(wb.getSheetName(i));// 设置sheetName
			int rowNum = sheet.getLastRowNum();// 行数
			// 读取列头
			List<String> headList = new ArrayList<String>();
			if (rowNum != -1) {// 列头
				HSSFRow headRow = sheet.getRow(0);
				for (int j = 0; j < headRow.getLastCellNum(); j++) {
					headList.add(headRow.getCell(j).getStringCellValue());
				}
			}
			vo.setHead(headList);
			// 读取数据
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (int j = 1; j <= rowNum; j++) {// 行数据
				List<Object> rowList = new ArrayList<Object>();
				HSSFRow dataRow = sheet.getRow(j);
				for (int t = 0; t < dataRow.getLastCellNum(); t++) {
					rowList.add(dataRow.getCell(t).getStringCellValue());
				}
				dataList.add(rowList);
			}
			vo.setData(dataList);
			list.add(vo);
		}
		return list;
	}

}
