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
	비상연락망과 연락을 시작하는 당번에 대한 정보가 주어질 때, 가장 나중에 연락을 받게되는 사람 중 번호가 가장 큰 사람을 구해라

	간선은 양방향일 수도 있고, 단방향일 수도 있다
	당번은 다음 노드에게 동시에 연락을 취할 수 있다
	다음 노드들은 그 다음 노드에게, 동시에 연락을 취한다 (동시에 일어난다)
	-> 이미 연락을 받은 상태인 노드는 다시 연락하지 않는다.

	연락을 취할 수 있는 가장 마지막에 연락을 받은 사람들 중에, 가장 큰 노드 숫자를 리턴


	[ 고려 사항 ]
	연락 인원(노드)는 최대 100명 (번호: 1~100)
		-> 비어있는 번호가 있을 수 있음
	동일한 from, to 쌍이 여러 번 반복되는 경우가 있을 수도 있지만 차이는 없다.

	[ 입력 ]
	10개의 테스트 케이스 고정
	첫 번째 줄 - 입력받는 데이터의 길이 length, 시작 노드 now
	두 번째 줄 - {from, to} 형태의 데이터

	[ 풀이 ]
	1. 크기 101의 리스트 배열을 만든다.
		List<Integer>[] listArr: 현재 노드(인덱스)가 연락할 수 있는 노드들을 리스트로 담음
	2. 현재 노드를 이미 방문했는지 판별하기 위한 크기 101의 boolean 배열을 만든다.
		boolean[] isVisited : 현재 노드 방문여부 판별 배열
	3. bfs 진행
		1. 큐에 당번 번호를 넣음, visited 처리 + -1번을 넣음(현재 턴이 끝났음을 알려준다), max를 최소값으로 초기화
		2. 반복문을 돌면서 각 노드를 뺌
		3. 현재 노드의 다음 당번을 visited처리 해주고, 방문 여부 체크 후 모두 다 큐에 넣음
		4. 이때 현재까지 노드의 최대값 max를 저장해줌
		5. 만약 -1 노드가 나오면, 현재 턴이 끝났다는 뜻
			=> 만약 isEmpty라면 지금까지의 최대값 리턴,
			=> 아니라면 다시 -1을 큐에 넣고 max 초기화 (다음턴)
	 */

	static StringBuilder sb;
	static List<Integer>[] listArr;
	static boolean[] isVisited;
	static int answer;

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		for (int t = 1; t <= 10; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int length = Integer.parseInt(st.nextToken());
			int start =  Integer.parseInt(st.nextToken());

			listArr = new ArrayList[101];
			isVisited = new boolean[101];

			for (int i = 1; i <= 100; i++) {
				listArr[i] = new ArrayList<>();
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < length/2; i++) {
				int from =  Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				listArr[from].add(to);
			}

			bfs(start);

			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		System.out.println(sb);
	}

	static void bfs(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		isVisited[start] = true;

		while (!queue.isEmpty()) {
			int size = queue.size();
			int max = -1;
			// 현재 턴만큼 돌기
			for (int i = 0; i < size; i++) {
				int now =  queue.poll();
				max = Math.max(max, now);
				for (int j = 0; j < listArr[now].size(); j++) {
					int next =  listArr[now].get(j);
					if (isVisited[next]) {
						continue;
					}
					isVisited[next] = true;
					queue.offer(next);
				}
			}
			answer = max;
		}
	}

}
