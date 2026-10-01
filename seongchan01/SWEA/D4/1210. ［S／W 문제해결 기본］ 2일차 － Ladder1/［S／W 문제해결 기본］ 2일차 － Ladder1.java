import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * [문제해석]
 * 어느 시작점을 골라야지 도착지 X에 도착하는 지 구해야 함
 * 세로 방향 두 막대 사이 임의의 개수 막대들이 랜덤한 간격으로 추가, 연결
 * 사다리 게임처럼 가로 방향의 막대를 만나면 좌우 방향으로 이동 가능
 * 
 * [접근]
 * 어느 출발지에서 출발해야 하는지 구해야 하므로 도착지에서 거꾸로 시작 -> BFS
 */

public class Solution { // D4. Ladder1

	static int[][] arr;

	// 위로만 이동 / 오른쪽 , 왼쪽, 위
	static int[] dx = { 0, 0, -1 };
	static int[] dy = { 1, -1, 0 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= 10; tc++) {

			int T = Integer.parseInt(br.readLine());

			arr = new int[100][100];

			int startX = 0;
			int startY = 0;

			for (int i = 0; i < 100; i++) {

				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < 100; j++) {

					arr[i][j] = Integer.parseInt(st.nextToken());

					// 도착 지점 = 시작점
					if (arr[i][j] == 2) {
						startX = i;
						startY = j;
					}
				}
			}

			int answer = bfs(startX, startY);

			sb.append("#").append(T).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static int bfs(int startX, int startY) {
		boolean[][] visit = new boolean[100][100];

		Queue<int[]> queue = new ArrayDeque<>();

		queue.offer(new int[] { startX, startY });
		visit[startX][startY] = true;

		while (!queue.isEmpty()) {
			int[] now = queue.poll();

			int nowX = now[0];
			int nowY = now[1];

			// 첫 행 일때 종료
			if (nowX == 0) {
				return nowY;
			}

			for (int i = 0; i < 3; i++) {
				int nx = nowX + dx[i];
				int ny = nowY + dy[i];

				if (nx < 0 || nx >= 100 || ny < 0 || ny >= 100 || arr[nx][ny] != 1) {
					continue;
				}

				if (visit[nx][ny]) {
					continue;
				}

				visit[nx][ny] = true;
				queue.add(new int[] { nx, ny });

				// 좌우로 이동했으면 위 X
				if (i == 0 || i == 1) {
					break;
				}
			}
		}

		return 0;
	}
}
