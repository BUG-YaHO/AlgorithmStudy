import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // 3752. 가능한 시험 점수

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());

			int[] arr = new int[N + 1];

			int sum = 0;

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				sum += arr[i];
			}

			boolean[][] dp = new boolean[N + 1][sum + 1];

			dp[0][0] = true;

			for (int i = 1; i <= N; i++) {
				for (int w = 0; w <= sum; w++) {

					// 틀리는 경우
					if (dp[i - 1][w]) {
						dp[i][w] = true;
					}

					// 맞는 경우
					if (w >= arr[i] && dp[i - 1][w - arr[i]]) {
						dp[i][w] = true;
					}
				}
			}

			int answer = 0;

			for (int i = 0; i <= sum; i++) {
				if (dp[N][i]) {
					answer++;
				}
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}
