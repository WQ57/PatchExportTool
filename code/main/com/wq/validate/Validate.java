package com.wq.validate;

import com.wq.vo.Result;

/**
 * ��֤���ӿ�.
 * 
 * @author wuqing
 * 
 */
public interface Validate {

	/**
	 * ��֤����.
	 * 
	 * @param obj
	 * @return
	 */
	Result validate(Object obj);
}
