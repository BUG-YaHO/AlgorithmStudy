import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D2. 나무 높이

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			int[] arr = new int[N];

			StringTokenizer st = new StringTokenizer(br.readLine());

			int max = 0;

			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());

				if (max < arr[i]) {
					max = arr[i];
				}
			}

			// 홀수 날에 +1 해야 하는 횟수
			int oneCount = 0;

			// 짝수 날에 +2 해야 하는 횟수
			int twoCount = 0;

			for (int i = 0; i < N; i++) {

				int less = max - arr[i];

				oneCount += less % 2;
				twoCount += less / 2;
			}

			/*
			 * +2가 너무 많으면 +2 한 번을 +1 두 번으로 변경한다.
			 */
			while (twoCount > oneCount + 1) {

				twoCount--;
				oneCount += 2;
			}

			int answer;

			// +1이 더 많으면 마지막 홀수 날까지만 필요
			if (oneCount > twoCount) {

				answer = oneCount * 2 - 1;

				// +2가 같거나 더 많으면 마지막 짝수 날까지 필요
			} else {

				answer = twoCount * 2;
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

}
