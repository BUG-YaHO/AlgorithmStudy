import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc < T + 1; tc++) {
			sb.append("#").append(tc).append(" ");
			int N = Integer.parseInt(br.readLine());
			String S = br.readLine();
			
			// 글자수가 홀수면 두번 연달아 쓸 수 없음
			if (N % 2 == 1) {
				sb.append("No\n");
				continue;
			}
			
			// 문자열을 절반으로 나눠서 앞과 뒤가 같으면 Yes
			if (S.substring(0, N / 2).equals(S.substring(N / 2))) {
				sb.append("Yes");
			} else {
				sb.append("No");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}