package com.pwrd.war.core.msg.property;

public class IllegalValueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalValueException() {
		super();

	}

	public IllegalValueException(String message) {
		super(message);

	}

	public IllegalValueException(String message, Throwable cause) {
		super(message, cause);

	}

	public IllegalValueException(Throwable cause) {
		super(cause);

	}

}
