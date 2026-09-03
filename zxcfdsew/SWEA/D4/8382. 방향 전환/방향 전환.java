import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			int dx = Math.abs(x2 - x1);  // x축 이동량
			int dy = Math.abs(y2 - y1);  // y축 이동량

			int m = Math.max(dx, dy);  // 더 많이 움직여야 하는 축의 이동 횟수
			int d = Math.abs(dx - dy);  // 두 축의 불균형 정도

			// 두 축 차이가 홀수면 2m-1, 짝수면 2m
			int answer = (d % 2 == 1) ? 2 * m - 1 : 2 * m;

			sb.append('#').append(tc).append(' ').append(answer).append('\n');
		}
		System.out.print(sb);
	}
}