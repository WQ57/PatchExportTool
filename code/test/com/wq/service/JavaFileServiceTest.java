package com.wq.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.wq.service.imp.JavaFileServiceImp;
import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

public class JavaFileServiceTest extends TestCase {

	JavaFileService s = new JavaFileServiceImp();

	public void testGetCommonProjInfo() {
		List<ProjInfoVO> list = s.getCommonProjInfo();
		System.out.print(list.size());
	}

	public void testWirteCommonProjInfo() {
		// SNSCORE@@E:\CODE\sns_svn\trunk@@\WebRoot\WEB-INF\classes@@E:\exportFiles\sns
		List<ProjInfoVO> list = new ArrayList<ProjInfoVO>();

		ProjInfoVO vo1 = new ProjInfoVO();
		vo1.setProjName("TEST_01");
		vo1.setExportPath("H:\\temp\\test01");
		vo1.setProjPath("H:\\3.workspace\\java\\coo");
		vo1.setExcelPath("H:\\temp\\code1.xls");
		list.add(vo1);
		
		ProjInfoVO vo2 = new ProjInfoVO();
		vo2.setProjName("TEST_02");
		vo2.setExportPath("H:\\temp\\test02");
		vo2.setProjPath("H:\\3.workspace\\sts\\WebDemo");
		vo2.setExcelPath("H:\\temp\\code2.xls");
		list.add(vo2);
		
		s.wirteCommonProjInfo(list);
	}

	public void testExportFiles() {
		List<ProjInfoVO> list = s.getCommonProjInfo();
		Result result = s.exportFiles(list.get(1));
		System.out.println(result.getMsg());
	}
}
