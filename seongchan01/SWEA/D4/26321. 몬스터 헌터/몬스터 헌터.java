import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution { // 몬스터 헌터
	static int n;
	static int[][] arr;
	static Set<Integer> set;
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			n = Integer.parseInt(br.readLine());

			arr = new int[n][n];
			set = new HashSet<>();

			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());

					if (arr[i][j] != 0) {
						set.add(arr[i][j]);
					}
				}
			}

			answer = Integer.MAX_VALUE;
			dfs(0, 0, 0);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int x, int y, int time) {
		// 가지치기
		if (time >= answer) {
			return;
		}

		if (set.isEmpty()) {
			answer = Math.min(answer, time);
			return;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {

				int v = arr[i][j];

				// 빈 칸일때 넘어감 / 몬스터 or 고객일때만 생각
				if (v == 0) {
					continue;
				}

				// 이미 처리한 몬스터나 고객이면 넘어감
				if (!set.contains(v)) {
					continue;
				}

				// 고객인데 몬스터 안잡았으면 못감
				if (v < 0) {
					// -1 곱해서 몬스터 잡았는지 확인
					int monster = v * -1;
					if (set.contains(monster)) {
						continue;
					}
				}

				// 좌표 거리 계산해서 + time
				// 몬스터 or 고객일때만 생각하면 되니까 좌표에서 최단거리로 바로 구하기
				int d = Math.abs(x - i) + Math.abs(y - j);

				// 백트래킹
				set.remove(v);

				dfs(i, j, time + d);

				set.add(v);
			}
		}
	}
}
