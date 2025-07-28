package com.tal.algorithms.algorithm.data_structure.basic.stack;

import java.util.Arrays;

import com.tal.algorithms.algorithm.data_structure.basic.stack.exception.EmptyStackException;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 3/3/25
 */
public class StackPractice<T> {

	private Object[] elements;

	private int size = 0;

	private static final int DEFAULT_CAPACITY = 16;

	public StackPractice() {
		this.elements = new Object[DEFAULT_CAPACITY];
	}

	public void push(T e) {
		if (elements.length > this.size) {
			reSize();
		}
	}

	private void reSize() {
		elements = Arrays.copyOf(elements, elements.length * 2);
	}

	/**
	 * 가장 위에 있는 값을 반환한다.
	 */
	public T peek() {
		if (this.size < 1 && elements == null) {
			throw new EmptyStackException();
		}

		// return (T)Arrays.copyOf(this.elements[],this.size);
		return null;
	}

	public int totalSize() {
		return elements.length;
	}

}
