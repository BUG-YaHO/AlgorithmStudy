import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * [문제 해결]
 * N*N 크기의 치즈의 모든 칸의 맛의 정도가 동일하지 않다.
 * 맛의 정도(1~100)
 * 치즈를 좋아하는 요정 -> 100일동안 치즈를 갉아먹음, X번째 날에는 맛있는 정도가 X인 칸을 먹어버림
 * 치즈 덩어리: 상,하,좌,우로 인접한 칸들을 하나로 묶어놓은 것
 * 
 * 치즈를 요정이 먹으면 -> 회색칸으로 바뀜
 * 그러면 안먹은 곳들을     묶어놓은 덩어리가 여러개 생김
 * 100일 중에서 치즈 덩어리가 가장 많을 때의 덩어리 개수를 구해야 함
 * 
 * [풀이]
 * 각 날짜마다 BFS 이용해서 남아있는 치즈 덩어리 개수 구하고 최댓값 구하기
 */

public class Solution { // D4. 치즈 도둑

	static int N;
	static int[][] arr;
	static boolean[][] eat;
	static boolean[][] visit;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());

			arr = new int[N][N];
			eat = new boolean[N][N];

			for (int i = 0; i < N; i++) {

				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 
			int answer = 1;

			for (int day = 1; day <= 100; day++) {

				// 오늘 먹을 치즈 처리
				for (int r = 0; r < N; r++) {

					for (int c = 0; c < N; c++) {

						if (arr[r][c] == day) {
							eat[r][c] = true;
						}
					}
				}

				// 날짜마다 방문 배열 초기화
				visit = new boolean[N][N];

				int count = 0;

				for (int r = 0; r < N; r++) {
					for (int c = 0; c < N; c++) {

						// 이미 먹힌 치즈
						if (eat[r][c]) {
							continue;
						}

						// 이미 확인한 치즈
						if (visit[r][c]) {
							continue;
						}

						// 새로운 치즈 덩어리 발견
						bfs(r, c);

						count++;
					}
				}

				answer = Math.max(answer, count);
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.print(sb);
	}

	static void bfs(int startR, int startC) {

		Queue<int[]> queue = new ArrayDeque<>();

		queue.offer(new int[] { startR, startC });
		visit[startR][startC] = true;

		while (!queue.isEmpty()) {

			int[] now = queue.poll();

			int r = now[0];
			int c = now[1];

			for (int d = 0; d < 4; d++) {

				int nr = r + dr[d];
				int nc = c + dc[d];

				if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
					continue;
				}

				if (eat[nr][nc]) {
					continue;
				}

				if (visit[nr][nc]) {
					continue;
				}

				visit[nr][nc] = true;

				queue.offer(new int[] { nr, nc });
			}
		}
	}
}