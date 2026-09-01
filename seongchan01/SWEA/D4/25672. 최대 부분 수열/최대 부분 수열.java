import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D4. 최대 부분 수열
	static int N, K;
	static int[] arr;
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			arr = new int[N];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}

			// 합이 음수도 나올 수 있으니 0이 아닌 최솟값으로 초기화
			answer = Integer.MIN_VALUE;

			dfs(0, 0, 0);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);

	}

	static void dfs(int i, int sum, int count) {

		// 종료 조건: 두 개의 수열 선택 시 최대값 구함
		if (count == 2) {
			answer = Math.max(sum, answer);
			return;
		}

		// i부터 K개 선택 하기
		int j = i + K;

		// K개를 선택할 수 없으면 종료
		if (j > N) {
			return;
		}

		// i ~ i+K-1 까지의 구하기
		int nowSum = 0;
		for (int k = i; k < j; k++) {
			nowSum += arr[k];
		}

		// 현재 구간 선택
		dfs(j, sum + nowSum, count + 1);

		// 현재 구간 선택 안 함
		dfs(i + 1, sum, count);
	}
}
