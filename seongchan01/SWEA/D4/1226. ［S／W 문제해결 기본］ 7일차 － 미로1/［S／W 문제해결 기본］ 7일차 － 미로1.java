import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {

	static int N = 16;
	static int[][] arr;
	static boolean[][] visit;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { 1, 0, -1, 0 };
	static int result;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= 10; t++) {
			int tc = Integer.parseInt(br.readLine());

			arr = new int[N][N];
			visit = new boolean[N][N];

			int startX = 0;
			int startY = 0;

			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					arr[i][j] = line.charAt(j) - '0';

					if (arr[i][j] == 2) {
						startX = i;
						startY = j;
					}
				}
			}

			result = 0;
			bfs(startX, startY);

			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}

		System.out.println(sb);
	}

	static void bfs(int startX, int startY) {

		Queue<int[]> queue = new ArrayDeque<>();

		queue.offer(new int[] { startX, startY });
		visit[startX][startY] = true;

		while (!queue.isEmpty()) {

			int[] now = queue.poll();

			int x = now[0];
			int y = now[1];

			for (int d = 0; d < 4; d++) {

				int nx = x + dx[d];
				int ny = y + dy[d];

				if (nx < 0 || nx >= N || ny < 0 || ny >= N || visit[nx][ny] || arr[nx][ny] == 1) {
					continue;
				}

				if (visit[nx][ny]) {
					continue;
				}

				if (arr[nx][ny] == 3) {
					result = 1;
					return;
				}

				visit[nx][ny] = true;
				queue.offer(new int[] { nx, ny });
			}
		}
	}
}
