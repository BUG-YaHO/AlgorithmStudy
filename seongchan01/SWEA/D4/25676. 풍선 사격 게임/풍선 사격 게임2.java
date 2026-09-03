package swea.d4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BalloonShootingGame2 {

	static int N;
	static int[] balloons;
	static int[] dp;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			N = Integer.parseInt(br.readLine());

			balloons = new int[N];

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				balloons[i] = Integer.parseInt(st.nextToken());
			}

			dp = new int[1 << N];
			Arrays.fill(dp, -1);

			dp[0] = 0;

			// 1=살아있음, 0=터짐
			// 풍선 다 살아있으면 1111
			// 1 << 4 = 10000 / 하지만 우리가 원하는 건 1111 이므로 -1
			// state = 15 -> 이진수로 보면 1111
			int state = (1 << N) - 1;

			// state = 1111 -> 4개 풍선이 전부 살아있는 상태에서 얻을 수 있는 최대 점수
			// state = 1011 -> 3번 o / 2번 x / 1번 o / 0번 0 일때 최대 점수
			int answer = dfs(state);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static int dfs(int state) {

		// 이미 계산했던 상태면 dfs 안하고 해당 값 return
		if (dp[state] != -1) {
			return dp[state];
		}

		int answer = 0;

		for (int i = 0; i < N; i++) {

			// i번째 풍선이 이미 터졌으면 넘어감
			// ex) 현재 state = 1011, i=2 -> 2번 풍선이 터졌는지 확인
			// 1 << 2 = 0100
			// 1011 & 0100 = 0000 -> 2번 풍선은 터졌으니까 다시 터트릴 필요가 없음
			// else i=1 이면 1번 풍선은 살아있으니 다음 코드 실행
			if ((state & (1 << i)) == 0) {
				continue;
			}

			// i번째 풍선을 터뜨렸을 때 얻는 점수
			// 1111 일때 i=1이면 모든 풍선 살아있는 상태에서 1번 터트리면 얻는 점수
			int count = shooting(state, i);

			// i번째 풍선을 터뜨린 상태
			// state=1111, i=1 -> i번째 풍선 0으로 바꾸기
			// 1<<1 = 0010, ~(1<<1) = 1101
			// 1111&1101 = 1101
			int nextState = state & ~(1 << i);

			// 나머지 풍선을 터뜨렸을 때 얻을 수 있는 최대 점수
			int nextAnswer = dfs(nextState);

			answer = Math.max(answer, count + nextAnswer);
		}

		dp[state] = answer;

		return answer;
	}

	static int shooting(int state, int now) {

		int left = -1;
		int right = -1;

		// 왼쪽에서 가장 가까운 풍선 찾기
		for (int i = now - 1; i >= 0; i--) {

			// ex) state=1011, now=1,
			// 1011&0001 = 0001 -> 가장 가까운 풍선 찾음 break;
			if ((state & (1 << i)) != 0) {
				left = i;
				break;
			}
		}

		// 오른쪽에서 가장 가까운 풍선 찾기
		for (int i = now + 1; i < N; i++) {

			// 1011&0100 = 0000 -> 넘어감
			// 1011&1000 = 1000 -> 가장 가까운 풍선 찾음 break;
			if ((state & (1 << i)) != 0) {
				right = i;
				break;
			}
		}

		if (left != -1 && right != -1) {
			return balloons[left] * balloons[right];
		}

		if (left == -1 && right != -1) {
			return balloons[right];
		}

		if (left != -1 && right == -1) {
			return balloons[left];
		}

		return balloons[now];
	}
}
