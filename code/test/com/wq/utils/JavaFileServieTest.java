package com.wq.utils;

import com.wq.service.JavaFileService;
import com.wq.service.imp.JavaFileServiceImp;
import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

public class JavaFileServieTest {

	public static void main(String[] args) {
		JavaFileService s = new JavaFileServiceImp();
		ProjInfoVO projInfoVO = new ProjInfoVO();
		Result r = s.exportFiles(projInfoVO);
	}

}
