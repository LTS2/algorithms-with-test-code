package com.tal.algorithms.algorithm.data_structure.basic.stack;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 3/3/25
 */
public class StackTest1 {

	@Test
	@DisplayName("스택이 가득 찼을 경우 자동으로 길이를 2배로 늘린다.")
	void stack_capacity_grow() {
		// given
		StackPractice<Integer> stack = new StackPractice<>();

		// when
		for (int i = 0; i < 17; i++) {
			stack.push(i);
		}

		// then
		assertThat(stack.peek()).isEqualTo(16);
		assertThat(stack).isEqualTo(16);
		assertThat(stack.totalSize()).isEqualTo(32);

	}
}
