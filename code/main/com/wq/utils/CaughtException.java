package com.wq.utils;

/**
 * Wq异常捕捉.
 * 
 * @author qingwu
 * @date 2013-04-26
 */
@SuppressWarnings("serial")
public class CaughtException extends RuntimeException {
	public CaughtException() {
		super();
	}

	public CaughtException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaughtException(String message) {
		super(message);
	}

	public CaughtException(Throwable cause) {
		super(cause);
	}

	/**
	 * 减少异常栈深度,提高性能
	 * 
	 * @return
	 * @author yangz
	 * @date 2013-1-31 下午3:30:30
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
