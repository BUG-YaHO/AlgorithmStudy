import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D4. 화분과 비료

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int P = Integer.parseInt(st.nextToken());

			int[] arr1 = new int[N];
			int[] arr2 = new int[N];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr1[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr2[i] = Integer.parseInt(st.nextToken());
			}

			int oneSum = arr1[0]; // 현재 1번 화분을 선택한 경우 최대합
			int twoSum = arr2[0]; // 현재 2번 화분을 선택한 경우 최대합

			for (int i = 1; i < N; i++) {

				// 이번에 1번 화분 선택
				int nextOne = Math.max(oneSum + arr1[i] - P, twoSum + arr1[i]);

				// 이번에 2번 화분 선택
				int nextTwo = Math.max(oneSum + arr2[i], twoSum + arr2[i] - P);

				oneSum = nextOne;
				twoSum = nextTwo;
			}

			int answer = Math.max(oneSum, twoSum);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}
