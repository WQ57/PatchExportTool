package com.wq.validate;

import com.wq.vo.Result;

/**
 * 验证器接口.
 * 
 * @author wuqing
 * 
 */
public interface Validate {

	/**
	 * 验证方法.
	 * 
	 * @param obj
	 * @return
	 */
	Result validate(Object obj);
}
