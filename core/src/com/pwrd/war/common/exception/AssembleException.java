package com.pwrd.war.common.exception;

/**
 * 组装异常
 * 
 */
public class AssembleException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 6043957842241226132L;

	/**
	 * @param msg
	 */
	public AssembleException(String msg) {
		super(msg);
	}

	/**
	 * @param t
	 */
	public AssembleException(Throwable t) {
		super(t);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public AssembleException(String msg, Throwable t) {
		super(msg, t);
	}

}
