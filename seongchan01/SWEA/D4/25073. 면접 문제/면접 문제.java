import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution { // D4. 면접 문제

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			int[] arr = new int[N];

			int correct = M;
			int wrong = N - M;

			int cnt = 0;

			// 뒤에서 부터 배열 만들기 -> 오답 뒤에 배치해서 연속 정답은 최대한 앞쪽에 배치
			for (int i = N - 1; i >= 0; i--) {

				// 정답을 다 사용했으면 나머지는 오답
				if (correct == 0) {
					arr[i] = 0;
					wrong--;
				}

				// 오답을 다 사용했으면 나머지는 정답
				else if (wrong == 0) {
					arr[i] = 1;
					correct--;
				}

				// 뒤에서부터 K-1개까지 정답 배치
				else if (cnt < K - 1) {
					arr[i] = 1;
					correct--;
					cnt++;
				}

				// K개 연속되기 전에 오답으로 끊기
				else {
					arr[i] = 0;
					wrong--;
					cnt = 0;
				}
			}

			int answer = 0;
			int count = 0;

			// 점수 계산
			for (int i = 0; i < arr.length; i++) {

				if (arr[i] == 0) {
					count = 0;

				} else {
					count++;

					if (count == K) {
						// 기본 1점 + 보너스 1점 = +2
						answer = answer * 2 + 2;
						count = 0;

					} else {
						answer++;
					}
				}
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}
}
