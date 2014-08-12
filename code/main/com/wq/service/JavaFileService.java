package com.wq.service;

import java.util.List;

import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

/**
 * Java文件操作服务.
 * 
 * @author qingwu
 * 
 */
public interface JavaFileService {

	/**
	 * 获得常用项目列表.
	 */
	public List<ProjInfoVO> getCommonProjInfo();

	/**
	 * 重写常用项目信息.
	 * 
	 * @param projInfoVO
	 *            项目信息
	 * @return
	 */
	public boolean wirteCommonProjInfo(List<ProjInfoVO> list);

	/**
	 * 导出文件.
	 * 
	 * @param projInfoVO
	 *            项目信息
	 * @return
	 */
	public Result exportFiles(ProjInfoVO projInfoVO);

}
