import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D3. 사과 먹기

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			int N = Integer.parseInt(br.readLine());

			// apple[사과 번호] = {행, 열}
			int[][] apple = new int[11][2];
			int appleCnt = 0;

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < N; j++) {
					int n = Integer.parseInt(st.nextToken());

					if (n != 0) {
						apple[n][0] = i;
						apple[n][1] = j;

						appleCnt++;
					}
				}
			}

			int r = 0;
			int c = 0;

			// 0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위
			int dir = 0;

			int answer = 0;

			for (int number = 1; number <= appleCnt; number++) {

				int nextR = apple[number][0];
				int nextC = apple[number][1];

				int[] result = move(r, c, nextR, nextC, dir);

				answer += result[0];
				dir = result[1];

				r = nextR;
				c = nextC;
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static int[] move(int r, int c, int nextR, int nextC, int dir) {

		// 이번 사과까지 가기 위해 필요한 방향
		boolean[] need = new boolean[4];

		int needCnt = 0;

		// 세로 방향
		if (nextR > r) {
			need[1] = true; // 아래
			needCnt++;

		} else if (nextR < r) {
			need[3] = true; // 위
			needCnt++;
		}

		// 가로 방향
		if (nextC > c) {
			need[0] = true; // 오른쪽
			needCnt++;

		} else if (nextC < c) {
			need[2] = true; // 왼쪽
			needCnt++;
		}

		int turnCnt = 0;

		while (needCnt > 0) {

			// 현재 바라보는 방향이 필요한 방향이라면
			// 해당 방향으로 이동
			if (need[dir]) {

				need[dir] = false;
				needCnt--;

			} else {

				// 필요한 방향이 아니면 오른쪽으로 회전
				dir = (dir + 1) % 4;
				turnCnt++;
			}
		}

		return new int[] { turnCnt, dir };
	}
}
