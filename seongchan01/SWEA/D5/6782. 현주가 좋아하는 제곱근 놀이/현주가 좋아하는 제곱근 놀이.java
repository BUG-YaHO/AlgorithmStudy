import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * [문제 해석]
 * 제곱근 놀이.
 * 2 이상의 정수 N이 있다.
 * N을 N+1로 바꿀 수 있고, 루트N이 정수일 때, N을 루트N으로 바꿀 수 있다.
 * 
 * 게임 목표: N을 2로 만드는 것.
 * - N을 2로 만들기 위해 횟수의 최솟값을 구해야 함.
 * 
 * [풀이]
 * Math.sqrt() 함수 사용 -> 가능하면 바꾸고, 불가능 시 다음 제곱근 넘어감
 * 재귀 사용
 */

public class Solution { // d5. 현주가 좋아하는 제곱근 놀이

	static long answer;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			long N = Long.parseLong(br.readLine());

			answer = 0;

			checkSqrt(N);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static void checkSqrt(long x) {

		if (x == 2) {
			return;
		}

		long root = (long) Math.sqrt(x);

		// 완전제곱수(정수)인지 확인
		if (root * root == x) {
			if (root == 2) {
				answer++;
				return;
			}

			answer++;
			checkSqrt(root);

			// 완전제곱수(정수) 아니면 다음 제곱수로 넘어감
		} else {
			long next = (root + 1) * (root + 1);
			// 넘어간 만큼 answer에 더하기
			answer += next - x;

			checkSqrt(next);
		}
	}
}
