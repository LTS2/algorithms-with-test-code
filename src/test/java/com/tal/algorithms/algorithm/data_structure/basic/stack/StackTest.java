package com.tal.algorithms.algorithm.data_structure.basic.stack;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tal.algorithms.algorithm.data_structure.basic.stack.exception.EmptyStackException;

/**
 * 테스트 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2/28/25
 */

public class StackTest {

	@Test
	@DisplayName("push 테스트")
	void push_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();

		// when
		stack.push(10);
		stack.push(20);
		stack.push(30);

		// then
		assertThat(stack.size()).isEqualTo(3);
		assertThat(stack.peek()).isEqualTo(30);
	}

	@Test
	@DisplayName("Stack 이 비어있을 때 pop() 호출시 에러")
	void pop_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();

		// then
		assertThatThrownBy(stack::pop) //
			.isInstanceOf(EmptyStackException.class)
			.withFailMessage("빈 스택에서 pop()을 호출하면 EmptyStackException이 발생해야 한다.");
	}

	@Test
	@DisplayName("peek() 테스트")
	void peek_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();
		stack.push(100);
		stack.push(200);

		// when
		int topValue = stack.peek();

		// then: stack 의 크기는 변하지 않으며 현재 최상단에 위치한 값만 가져온다. 현재 코드는 200이 최상단값이다.
		assertThat(topValue).isEqualTo(200);
		assertThat(stack.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("isEmpty() true 테스트")
	void isEmpty_true_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();

		// then: stack의 크기가 비어있을 경우 true를 반환한다.
		assertThat(stack.isEmpty()).isEqualTo(true);
	}

	@Test
	@DisplayName("isEmpty() false 테스트")
	void isEmpty_false_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();

		// when
		stack.push(100);

		// then: stack의 크기가 1 이상일 경우 isEmpty는 false이다.
		assertThat(stack.isEmpty()).isEqualTo(false);
	}

	@Test
	@DisplayName("size() 테스트")
	void size_successfully() {
		// given
		Stack<Integer> stack = new Stack<>();

		// when
		stack.push(100);
		stack.push(200);
		stack.push(300);

		// then: 3개의 값을 넣었으니 size의 크기도 3이어야한다.
		assertThat(stack.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("stack 의 크기가 1/4 이하가 되면 절반으로 줄인다")
	void reduce_capacity_if_stack_size_is_less_than_one_fourth() {
		// given
		Stack<Integer> stack = new Stack<>();

		// when

		// then
	}

	@Test
	@DisplayName("stack 용량이 부족할 경우 현재 길이에서 x2 로 늘린다.")
	void should_double_capacity_when_stack_is_full() {
		// given
		Stack<Integer> stack = new Stack<>();

		// when: 17번 실행
		for (int i = 0; i < 17; i++) {
			stack.push(i);
		}

		// then: stack의 기본 capacity는 16이므로 해당 사이즈를 넘어갔을 경우 현재 capacity의 2배로 늘어나야한다.
		assertThat(stack.totalSize()).isEqualTo(32);
	}
}
