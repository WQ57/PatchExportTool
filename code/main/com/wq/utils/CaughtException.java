package com.wq.utils;

/**
 * Wq�쳣��׽.
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
	 * �����쳣ջ���,�������
	 * 
	 * @return
	 * @author yangz
	 * @date 2013-1-31 ����3:30:30
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
