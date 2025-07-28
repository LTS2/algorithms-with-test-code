package com.tal.algorithms.algorithm.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 7/28/25
 */
public class Greedy {

	/**
	 * 그리디 알고리즘 학습을 위한 유틸리티 클래스다.
	 *
	 * <p>이 클래스는 다양한 그리디 알고리즘 문제에 대한 구현을 제공한다. 각 메서드는
	 * 문제의 특성에 맞게 정렬과 우선순위 큐를 적절히 활용하여 로컬 최적 선택을 반복한다.
	 * 예제 코드가 아닌 실제 프로덕션 코드 품질을 목표로 하며, 모든 알고리즘은 입력에
	 * 대한 검증을 수행하지 않으므로 호출 시 적절한 전처리가 필요하다.</p>
	 */

	private Greedy() {
		// 인스턴스화를 방지한다.
	}

	/**
	 * 주어진 활동 목록에서 서로 겹치지 않는 최대 개수의 활동을 선택한다.
	 *
	 * <p>활동 선택 문제는 시작 시간과 종료 시간이 있는 활동들의 집합에서
	 * 동시에 수행할 수 없는 활동을 제외하고 최대한 많은 활동을 선택하는 문제다.
	 * 종료 시간이 빠른 활동을 먼저 선택하면 항상 최적해를 얻을 수 있다는
	 * 그리디 선택 특성을 이용한다.</p>
	 *
	 * @param activities 시작 및 종료 시간을 가진 활동 목록
	 * @return 선택된 활동들을 종료 시간 기준으로 정렬한 리스트
	 */
	public static List<Activity> selectMaxActivities(List<Activity> activities) {
		if (activities == null || activities.isEmpty()) {
			return Collections.emptyList();
		}
		// 종료 시간이 빠른 순으로 정렬한다.
		List<Activity> sorted = new ArrayList<>(activities);
		sorted.sort(Comparator.comparingInt(Activity::getFinish));
		List<Activity> result = new ArrayList<>();
		// 첫 활동을 선택하고 다음 활동은 시작 시간이 마지막 선택된 활동의 종료 시간 이후인지 검사한다.
		Activity last = null;
		for (Activity activity : sorted) {
			if (last == null || activity.getStart() >= last.getFinish()) {
				result.add(activity);
				last = activity;
			}
		}
		return result;
	}

	/**
	 * 분수 배낭 문제를 해결한다. 각 아이템을 가치 대비 무게 비율이 높은 순으로 정렬하여
	 * 배낭에 담을 수 있을 때까지 가능한 한 많이 담는다.
	 *
	 * @param items    아이템 리스트 (가치와 무게 정보 포함)
	 * @param capacity 배낭의 최대 허용 무게 (양수)
	 * @return 선택된 아이템의 최대 가치 합
	 */
	public static double fractionalKnapsack(List<Item> items, double capacity) {

		if (items == null || items.isEmpty() || capacity <= 0) {
			return 0.0;
		}
		// 가치/무게 비율 내림차순으로 정렬한다.
		List<Item> sorted = new ArrayList<>(items);
		sorted.sort((a, b) -> Double.compare(b.ratio(), a.ratio()));
		double remaining = capacity;
		double valueSum = 0.0;
		for (Item item : sorted) {
			if (remaining <= 0) {
				break;
			}
			if (item.getWeight() <= remaining) {
				// 전체를 담는다.
				valueSum += item.getValue();
				remaining -= item.getWeight();
			} else {
				// 부분적으로 담는다.
				double fraction = remaining / item.getWeight();
				valueSum += item.getValue() * fraction;
				remaining = 0;
			}
		}
		return valueSum;
	}

	/**
	 * Kruskal 알고리즘을 이용하여 가중치 그래프의 최소 신장 트리를 찾는다. 이 알고리즘은
	 * 간선을 가중치 오름차순으로 정렬하고 사이클을 형성하지 않는 간선을 순차적으로
	 * 선택한다. 유니온-파인드 자료구조를 통해
	 * 사이클 여부를 효율적으로 판단한다.
	 *
	 * @param vertexCount 정점의 개수 (0 이상의 정수)
	 * @param edges       그래프의 간선 목록
	 * @return 최소 신장 트리를 구성하는 간선 리스트; 그래프가 연결되지 않은 경우 각
	 * 연결 요소에 대한 최소 신장 숲을 반환한다.
	 */
	public static List<Edge> kruskalMST(int vertexCount, List<Edge> edges) {
		List<Edge> result = new ArrayList<>();
		if (vertexCount <= 0 || edges == null || edges.isEmpty()) {
			return result;
		}
		// 간선을 가중치 오름차순으로 정렬한다.
		List<Edge> sorted = new ArrayList<>(edges);
		sorted.sort(Comparator.comparingDouble(Edge::getWeight));
		DisjointSet ds = new DisjointSet(vertexCount);
		for (Edge edge : sorted) {
			int uRoot = ds.find(edge.getU());
			int vRoot = ds.find(edge.getV());
			if (uRoot != vRoot) {
				result.add(edge);
				ds.union(uRoot, vRoot);
			}
		}
		return result;
	}

	/**
	 * 허프만 코딩을 수행하여 각 문자에 대한 최적의 프리픽스 코드를 생성한다. 빈도수가 낮은
	 * 두 노드를 반복적으로 병합하여 트리를 구성하는 방식이며,
	 * 생성된 트리는 모든 문자에 대한 최단 코드를 제공한다.
	 *
	 * @param frequencyMap 문자와 빈도수를 저장한 맵
	 * @return 각 문자에 대한 허프만 코드가 담긴 맵
	 */
	public static Map<Character, String> huffmanCoding(Map<Character, Integer> frequencyMap) {
		if (frequencyMap == null || frequencyMap.isEmpty()) {
			return Collections.emptyMap();
		}
		// 우선순위 큐를 사용하여 빈도수가 낮은 노드를 먼저 추출한다.
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			queue.offer(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
		}
		// 트리를 구성한다.
		while (queue.size() > 1) {
			HuffmanNode x = queue.poll();
			HuffmanNode y = queue.poll();
			HuffmanNode parent = new HuffmanNode('\0', x.frequency + y.frequency, x, y);
			queue.offer(parent);
		}
		HuffmanNode root = queue.poll();
		// 각 문자에 대한 코드를 생성한다.
		Map<Character, String> codeMap = new HashMap<>();
		buildHuffmanCode(root, "", codeMap);
		return codeMap;
	}

	private static void buildHuffmanCode(HuffmanNode node, String code, Map<Character, String> codeMap) {
		if (node == null) {
			return;
		}
		if (node.isLeaf()) {
			codeMap.put(node.character, code);
			return;
		}
		buildHuffmanCode(node.left, code + "0", codeMap);
		buildHuffmanCode(node.right, code + "1", codeMap);
	}

	/**
	 * 활동을 표현하는 불변 클래스다. Builder 패턴을 사용하여 생성할 수 있다.
	 */
	public static final class Activity {
		private final int start;
		private final int finish;

		private Activity(Activity.Builder builder) {
			this.start = builder.start;
			this.finish = builder.finish;
		}

		public int getStart() {
			return start;
		}

		public int getFinish() {
			return finish;
		}

		public static Activity.Builder builder() {
			return new Activity.Builder();
		}

		/**
		 * 활동 생성용 빌더 클래스다.
		 */
		public static final class Builder {
			private int start;
			private int finish;

			public Activity.Builder start(int start) {
				this.start = start;
				return this;
			}

			public Activity.Builder finish(int finish) {
				this.finish = finish;
				return this;
			}

			public Activity build() {
				return new Activity(this);
			}
		}
	}

	/**
	 * 배낭 문제의 아이템을 표현하는 불변 클래스다. 가치와 무게를 갖고 있으며
	 * 가치 대비 무게 비율을 쉽게 계산할 수 있다.
	 */
	public static final class Item {
		private final double value;
		private final double weight;

		private Item(Item.Builder builder) {
			this.value = builder.value;
			this.weight = builder.weight;
		}

		public double getValue() {
			return value;
		}

		public double getWeight() {
			return weight;
		}

		/**
		 * 가치 대비 무게 비율을 반환한다.
		 *
		 * @return value/weight 비율
		 */
		public double ratio() {
			return value / weight;
		}

		public static Item.Builder builder() {
			return new Item.Builder();
		}

		/**
		 * Item 빌더 클래스로, 값과 무게를 설정할 수 있다.
		 */
		public static final class Builder {
			private double value;
			private double weight;

			public Item.Builder value(double value) {
				this.value = value;
				return this;
			}

			public Item.Builder weight(double weight) {
				this.weight = weight;
				return this;
			}

			public Item build() {
				return new Item(this);
			}
		}
	}

	/**
	 * 그래프 간선을 표현하는 불변 클래스다. 정점 번호는 0 기반으로 가정한다.
	 */
	public static final class Edge {
		private final int u;
		private final int v;
		private final double weight;

		private Edge(Edge.Builder builder) {
			this.u = builder.u;
			this.v = builder.v;
			this.weight = builder.weight;
		}

		public int getU() {
			return u;
		}

		public int getV() {
			return v;
		}

		public double getWeight() {
			return weight;
		}

		public static Edge.Builder builder() {
			return new Edge.Builder();
		}

		/**
		 * Edge 빌더 클래스로, 두 정점과 가중치를 설정할 수 있다.
		 */
		public static final class Builder {
			private int u;
			private int v;
			private double weight;

			public Edge.Builder u(int u) {
				this.u = u;
				return this;
			}

			public Edge.Builder v(int v) {
				this.v = v;
				return this;
			}

			public Edge.Builder weight(double weight) {
				this.weight = weight;
				return this;
			}

			public Edge build() {
				return new Edge(this);
			}
		}
	}

	/**
	 * 허프만 트리의 노드를 표현하는 클래스다. 내부 노드와 리프 노드를 모두 나타낸다.
	 */
	private static final class HuffmanNode {
		private final char character;
		private final int frequency;
		private final HuffmanNode left;
		private final HuffmanNode right;

		HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
			this.character = character;
			this.frequency = frequency;
			this.left = left;
			this.right = right;
		}

		boolean isLeaf() {
			return left == null && right == null;
		}
	}

	/**
	 * 유니온-파인드 자료구조의 간단한 구현으로, Kruskal 알고리즘에서 사이클을 판단하기 위해 사용한다.
	 */
	private static final class DisjointSet {
		private final int[] parent;
		private final int[] rank;

		DisjointSet(int size) {
			this.parent = new int[size];
			this.rank = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}

		int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}

		void union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			if (rootX == rootY) {
				return;
			}
			if (rank[rootX] < rank[rootY]) {
				parent[rootX] = rootY;
			} else if (rank[rootX] > rank[rootY]) {
				parent[rootY] = rootX;
			} else {
				parent[rootY] = rootX;
				rank[rootX]++;
			}
		}
	}
}
