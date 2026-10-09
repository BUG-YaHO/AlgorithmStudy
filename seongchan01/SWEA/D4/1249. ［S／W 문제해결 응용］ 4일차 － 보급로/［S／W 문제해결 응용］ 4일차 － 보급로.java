import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * [문제 해석]
 * 2차원 배열 형태의 격자판 위에 각 좌표마다 파여진 도로의 깊이가 있다.
 * 공병대는 출발지에서 도착지에 가기 위한 도로 복구 작업을 가장 빠른 시간내에 수행하려 한다.
 * -> 출발지에서 도착지까지 가는 경로 중에서 복구 시간이 가장 짧은 경로에 대한 총 복구 시간을 구해야 한다.
 * 
 * [풀이]
 * 각 칸마다 복구 시간이 다르기 때문에 이동 비용에 가중치가 존재한다.
 * 가중치가 있는 경로에서 최소 비용을 구하기 위해 다익스트라 알고리즘 사용
 */

public class Solution {

	static int N;
	static int[][] arr;
	static int[][] dist;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());
			arr = new int[N][N];

			for (int i = 0; i < N; i++) {
				String str = br.readLine();

				for (int j = 0; j < N; j++) {
					arr[i][j] = str.charAt(j) - '0';

				}
			}

			dist = new int[N][N];

			// 일단 최댓값
			for (int i = 0; i < N; i++) {
				Arrays.fill(dist[i], Integer.MAX_VALUE);

			}

			int answer = dijkstra();

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static int dijkstra() {

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));

		pq.offer(new int[] { 0, 0, 0 });

		dist[0][0] = 0;

		while (!pq.isEmpty()) {
			int now[] = pq.poll();

			int x = now[0];
			int y = now[1];
			int d = now[2];

			// 도착지면 거리 반환
			if (x == N - 1 && y == N - 1) {
				return d;
			}

			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];

				if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
					continue;
				}

				// 다음 칸 이동할 때 다음칸까지의 거리
				int nextD = d + arr[nx][ny];

				// 다음 칸까지의 거리가 더 작으면 갱신, pq에 넣음 -> 이 좌표부터 다시 탐색 시작
				if (dist[nx][ny] > nextD) {
					dist[nx][ny] = nextD;
					pq.offer(new int[] { nx, ny, nextD });
				}
			}

		}
		return dist[N - 1][N - 1];
	}
}
