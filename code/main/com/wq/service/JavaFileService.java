package com.wq.service;

import java.util.List;

import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

/**
 * Java�ļ���������.
 * 
 * @author qingwu
 * 
 */
public interface JavaFileService {

	/**
	 * ��ó�����Ŀ�б�.
	 */
	public List<ProjInfoVO> getCommonProjInfo();

	/**
	 * ��д������Ŀ��Ϣ.
	 * 
	 * @param projInfoVO
	 *            ��Ŀ��Ϣ
	 * @return
	 */
	public boolean wirteCommonProjInfo(List<ProjInfoVO> list);

	/**
	 * �����ļ�.
	 * 
	 * @param projInfoVO
	 *            ��Ŀ��Ϣ
	 * @return
	 */
	public Result exportFiles(ProjInfoVO projInfoVO);

}
