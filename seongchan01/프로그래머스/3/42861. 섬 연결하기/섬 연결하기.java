import java.util.ArrayList;
import java.util.Collections;

class Solution {

	static int[] parent;

	static class Edge implements Comparable<Edge> {
		int from;
		int to;
		int cost;

		Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
	}

	public int solution(int n, int[][] costs) {

		parent = new int[n];

		// 유니온 파인드 초기화
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}

		// Edge 저장
		ArrayList<Edge> edges = new ArrayList<>();

		for (int[] cost : costs) {
			int from = cost[0];
			int to = cost[1];
			int price = cost[2];

			edges.add(new Edge(from, to, price));
		}

		// 오름차순 정렬
		Collections.sort(edges);

		int answer = 0;
		int count = 0;

		// 비용 작은 순으로 탐색
		for (Edge edge : edges) {

			// 아직 서로 연결되지 않은 섬이라면
			if (find(edge.from) != find(edge.to)) {

				union(edge.from, edge.to);

				answer += edge.cost;
				count++;

				// n-1 종료
				if (count == n - 1) {
					break;
				}
			}
		}

		return answer;
	}

	static int find(int x) {

		if (parent[x] == x) {
			return x;
		}

		return parent[x] = find(parent[x]);
	}

	static void union(int a, int b) {

		a = find(a);
		b = find(b);

		if (a != b) {
			parent[b] = a;
		}
	}
}