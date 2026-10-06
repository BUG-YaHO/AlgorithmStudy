import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 해석] 
 * N명의 점원들이 자기 키를 이용해 탑을 쌓아서, 선반 위에 닿아야 함
 * 점원들이 쌓는 탑은 점원 1명 이상으로 이루어져 있다
 * 탑의 높이는 탑을 만든 모든 점원의 키와 같다
 * 높이가 B 이상인 탑 중에서 높이가 가장 낮은 탑을 알아내야 함
 *
 */
public class Solution { // D4. 장훈이의 높은 선반

	static int N, B;
	static int[] arr;
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {

			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());

			arr = new int[N];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}

			answer = Integer.MAX_VALUE;

			dfs(0, 0);

			sb.append("#").append(tc).append(" ").append(answer - B).append("\n");
		}
		System.out.println(sb);
	}

	static void dfs(int h, int depth) {

		if (h >= B) {
			answer = Math.min(answer, h);
			return;
		}

		if (depth == N) {
			return;
		}

		// 선택
		dfs(h + arr[depth], depth + 1);

		// 선택X
		dfs(h, depth + 1);
	}
}
