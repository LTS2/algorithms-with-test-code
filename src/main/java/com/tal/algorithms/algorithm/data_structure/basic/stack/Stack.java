package com.tal.algorithms.algorithm.data_structure.basic.stack;

import java.util.Arrays;

import com.tal.algorithms.algorithm.data_structure.basic.stack.exception.EmptyStackException;

/**
 * <pre>
 * [Data Structure]
 * Stack 클래스입니다.
 * LIFO(Last In First Out)
 * push(T value): 스택의 최상단에 요소를 추가합니다. 배열의 용량이 부족한 경우, 배열 크기를 2배로 확장해야 합니다.
 * pop(): 스택의 최상단에서 요소를 제거하고 해당 요소를 반환합니다. 스택이 비어 있다면 적절한 예외(예: EmptyStackException)를 발생시킵니다.
 * peek(): 스택의 최상단 요소를 반환하지만 제거하지는 않습니다. 스택이 비어 있다면 예외를 발생시킵니다.
 * isEmpty(): 스택이 비어있는지 여부를 반환합니다.
 * size(): 스택에 저장된 요소의 개수를 반환합니다.
 * 동적 배열 크기 조절
 * <p>
 * 확장: 요소를 추가할 때 배열이 꽉 찬 경우 배열의 크기를 2배로 늘립니다.
 * 축소: 요소를 제거한 후, 스택의 크기가 배열 크기의 1/4 이하가 되면 배열의 크기를 절반으로 줄입니다. 단, 최소 크기 제한(예: 초기 용량)은 유지합니다.
 * 예외 처리
 * <p>
 * 스택이 비어 있을 때 pop()이나 peek()를 호출하면 적절한 예외를 발생시켜야 합니다.
 * 테스트
 * <p>
 * main() 메서드를 작성하여, 위 기능들이 올바르게 동작하는지 테스트 케이스를 포함시키세요.
 * 예를 들어, 다음과 같은 순서로 메서드를 호출했을 때:
 * push(10), push(20), push(30)
 * size()는 3을 반환해야 합니다.
 * peek()는 30을 반환해야 합니다.
 * pop()을 반복 호출하면 30, 20, 10의 순서로 값이 반환되어야 합니다.
 * 추가 고려사항
 * 시간 복잡도: 배열의 크기 조절이 발생할 때 시간 복잡도에 유의하세요.
 * 제네릭: 제네릭을 사용하여 다양한 타입의 데이터를 저장할 수 있도록 설계합니다.
 * 가비지 컬렉션: pop() 호출 후, 배열에서 제거한 요소의 참조를 해제하여 가비지 컬렉션이 원활히 작동하도록 합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2/27/25
 * </pre>
 */
public class Stack<T> {

	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Value in Stack
	 */
	private Object[] elements;

	/**
	 * Indicates the position of the current array
	 */
	private int size = 0;

	public Stack() {
		elements = new Object[DEFAULT_CAPACITY];
	}

	// pop(): 스택의 최상단에서 요소를 제거하고 해당 요소를 반환합니다. 스택이 비어 있다면 적절한 예외(EmptyStackException)를 발생시킵니다.
	@SuppressWarnings("unchecked")
	public T pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}

		T value = (T)elements[--size];
		elements[size] = null; // 가비지 컬렉션을 위해 참조 해제

		// 동적 배열 축소: 스택의 요소 개수가 배열 길이의 1/4 이하일 때 배열 크기를 절반으로 줄인다.
		if (elements.length > DEFAULT_CAPACITY && size > 0 && size <= elements.length / 4) {
			resize(Math.max(DEFAULT_CAPACITY, elements.length / 2));
		}

		return value;
	}

	/**
	 * 스택의 최상단에 요소를 추가합니다. 배열의 용량이 부족한 경우, 배열 크기를 2배로 확장해야 합니다.
	 */
	public void push(T value) {
		if (size == elements.length) {
			resize(2 * elements.length);
		}
		elements[size++] = value;
	}

	//  peek(): 스택의 최상단 요소를 반환하지만 제거하지는 않습니다. 스택이 비어 있다면 예외를 발생시킵니다.
	@SuppressWarnings("unchecked")
	public T peek() {
		if (size < 1) {
			throw new EmptyStackException();
		}
		return (T)elements[size - 1];
	}

	public int size() {
		return this.size;
	}

	/**
	 * @return if size is empty true if don't false
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Resizing when elements size is full
	 *
	 * @param newCapacity 확장할 배열의 크기
	 */
	private void resize(int newCapacity) {
		elements = Arrays.copyOf(elements, newCapacity);
	}

	/**
	 * SuppressWarnings: 개발자에 의해 타입 캐스팅이 안전하다고 보장받는 경우 컴파일러 경고를 억제하기 위해 사용된다.
	 * 그냥 elements 를 반환할 경우 클라이언트에 의해 값이 변경될 수 있으므로 캡슐화 원칙을 위반하게 된다.
	 * 따라서 복사본을 클라이언트에게 제공하면 원본 값을 안전하게 관리할 수 있다.
	 */
	@SuppressWarnings("unchecked")
	public T[] getElements(T[] a) {
		if (a.length < size) {
			return (T[])Arrays.copyOf(this.elements, size, a.getClass());
		}
		System.arraycopy(this.elements, 0, a, 0, size);
		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}

	public int totalSize() {
		return elements.length;
	}
}
