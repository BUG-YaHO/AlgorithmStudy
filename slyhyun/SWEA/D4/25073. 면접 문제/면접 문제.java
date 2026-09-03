import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
		
			// safe: 틀릴 수 있는 문제 개수 * 2배 되기 직전의 카운터 개수
			// -> 틀린 문제 뒤에 배치 가능한 문제 개수 (2배가 되지 않는 문제 개수)
			int safe = (N - M) * (K - 1);
			int bonus = 0;
			int sum = 0;
			
			// 만약 맞춰야하는 문제가 safe 개수보다 많으면 무조건 점수 2배를 해야하는 경우 발생
			if (M > safe) {
				// 몇 번이나 2배를 해줘야 하는지 계산
				bonus = (M - safe) / K;
			}
			
			// 최대한 앞에서 2배를 해주는 것이 좋으므로 bonus 수만큼 2배 해주는 경우 계산
			for (int i = 0; i < bonus; i++) {
                sum += (K - 1);
                sum = (sum + 1) * 2;
			}
			
			// 2배 해주고 남은 문제들 개수만큼 +1
			sum += M - (bonus * K);
			
			sb.append("#").append(tc).append(" ").append(sum).append("\n");
		}
		
		System.out.println(sb);
	}
}
