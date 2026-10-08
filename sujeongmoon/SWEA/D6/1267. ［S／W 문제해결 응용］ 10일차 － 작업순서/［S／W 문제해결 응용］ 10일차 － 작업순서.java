import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {

	/*
	[ 문제 해설 ]
	어떤 작업(V)은 특정 작업이 끝나야 시작할 수 있다.
	이런 작업의 선행 관계를 나타낸 그래프가 있다.

	각 작업은 하나의 정점으로 표시되고, 선행 관계는 방향성을 가진 간선으로 표현된다.
	V개의 작업과 이들 간의 선행 관계가 주어질 때, 일을 끝낼 수 있는 작업 순서를 찾아라.

	[ 고려 사항 ]
	사이클은 존재하지 않는다.
	10개의 테스트케이스가 주어진다.
	그래프 정점의 개수 V(3 ~ 1000)
	간선의 개수 E(2 ~ 3000)

	[ 입력 ]
	첫 번째 줄에는 그래프 정점의 개수 V(3 ~ 1000), 간선의 개수 E(2 ~ 3000)
	다음 줄에는 E개의 간선이 나열된다.
		간선은 간선을 이루는 두 정점으로 표기된다.
	정점의 번호는 1부터 V까지의 정수값을 가지며, 입력에서 이웃한 수는 모두 공백으로 구분된다.

	[ 풀이 ]
	1. V와 E를 static 변수로 저장
	2. 리스트 배열을 만든다
		List<Integer>[] list = 각 인덱스가 노드의 번호, 현재 노드를 수행하면 줄어드는 간선의 리스트 (리스트의 선행관계가 해당 노드)
	3. 선행관계를 가진 간선 수를 관리하는 배열을 만든다
		int[] arr = 각 인덱스가 노드의 번호, 해당 노드가 수행되기 위해 필요한 선행관계를 가진 간선의 개수
	4. bfs
		1. 큐를 만들어서 arr에서 간선이 0인 경우의 인덱스를 큐에 넣는다
		2. bfs를 진행하면서, 현재 노드를 수행한다, 작업 순서를 sb에 추가하고 list를 돌면서 해당하는 노드의 arr 값을 줄인다
	 */

	static StringBuilder sb;
	static int V; // 정점
	static int E; // 간선
	static List<Integer>[] list;
	static int[] arr;

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		for (int t = 1; t <= 10; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			list = new ArrayList[V + 1];
			arr = new int[V + 1];

			for (int i = 1; i <= V; i++) {
				list[i] = new ArrayList<>();
			}

			st = new StringTokenizer(br.readLine());

			for (int i = 0; i < E; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				list[from].add(to);
				arr[to]++;
			}

			sb.append("#").append(t);
			bfs();
			sb.append("\n");
		}
		System.out.println(sb);
	}

	public static void bfs() {

		Queue<Integer> queue = new ArrayDeque<>();
		for (int i = 1; i <= V; i++) {
			// 선행관계가 없는 노드들을 큐에 넣는다
			if (arr[i] == 0) {
				queue.offer(i);
			}
		}

		while (!queue.isEmpty()) {
			int now = queue.poll();
			for (int i : list[now]) {
				arr[i]--;
				if (arr[i] == 0) {
					queue.offer(i);
				}
			}
			sb.append(" ").append(now);
		}

	}

}