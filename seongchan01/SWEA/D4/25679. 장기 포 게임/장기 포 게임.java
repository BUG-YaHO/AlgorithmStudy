import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution { // D4. 장기 포 게임

	static int answer;
	static int N;
	static int[][] arr;
	static boolean[][] egg;
	static boolean[][] possible;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());

			arr = new int[N][N];
			egg = new boolean[N][N]; // 알 있는지 확인
			possible = new boolean[N][N]; // 잡을 수 있는 자리인지 확인

			int startX = 0;
			int startY = 0;

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());

					if (arr[i][j] == 1) {
						egg[i][j] = true;
					}

					if (arr[i][j] == 2) {
						startX = i;
						startY = j;
					}
				}
			}

			answer = 0;

			dfs(startX, startY, 0);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int x, int y, int depth) {

		// 3번 진행하면 멈춤
		if (depth == 3) {
			return;
		}

		// 갈 수 있는 위치 체크
		ArrayList<int[]> list = check(x, y);

		for (int i = 0; i < list.size(); i++) {
			int[] next = list.get(i);

			int nx = next[0];
			int ny = next[1];

			// 알 잡았을때
			if (egg[nx][ny]) {

				// 알 잡은적 있는지 확인 없으면 answer++;
				if (!possible[nx][ny]) {
					possible[nx][ny] = true;
					answer++;
				}

				// 백트래킹
				arr[nx][ny] = 0; // 알 잡았으니까 그 위치 알 없음으로 수정
				egg[nx][ny] = false;

				dfs(nx, ny, depth + 1);

				arr[nx][ny] = 1;
				egg[nx][ny] = true;
			}

			else {
				// 알 안잡고 빈칸이니 그냥 이동
				dfs(nx, ny, depth + 1);
			}
		}
	}

	static ArrayList<int[]> check(int x, int y) {
		ArrayList<int[]> list = new ArrayList<>();

		int[] dx = { 0, 0, 1, -1 };
		int[] dy = { 1, -1, 0, 0 };

		for (int k = 0; k < 4; k++) {
			int nx = x + dx[k];
			int ny = y + dy[k];

			boolean is1 = false;
			while (nx >= 0 && ny >= 0 && nx < N && ny < N) {

				if (!is1) {
					// 아직 한번도 알 안만났을때 알 처음 만남
					if (arr[nx][ny] == 1) {
						is1 = true;
					}

				} else {
					// 알 2개 이상으로 못 넘음
					if (arr[nx][ny] == 1) {
						list.add(new int[] { nx, ny });
						break;

					} else { // 빈칸일때
						list.add(new int[] { nx, ny });
					}

				}

				nx += dx[k];
				ny += dy[k];
			}

		}

		return list;
	}
}
