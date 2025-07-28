package com.tal.algorithms.algorithm.data_structure.basic.stack.exception;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2/28/25
 */
public class EmptyStackException extends RuntimeException {
	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public EmptyStackException() {

	}

	public EmptyStackException(String s) {
		super(s);
	}
}
