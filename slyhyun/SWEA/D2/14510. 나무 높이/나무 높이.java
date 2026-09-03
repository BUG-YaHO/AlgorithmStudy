import java.util.*;
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			List<Integer> arr = new ArrayList<>(N);
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i < N; i++) {
				arr.add(Integer.parseInt(st.nextToken()));
			}
			
			int max = Collections.max(arr);
			
			int even = 0;
			int odd = 0;
			int sum = 0;
			
			// 짝수 날에 물을 줘야 하는 부분이랑 홀수 날에 물을 줘야 하는 부분으로 분리
			for (int n : arr) {
				int diff = max - n;
				
				even += diff / 2;
				odd += diff % 2;
			}
			
			// 짝수와 홀수의 균형을 맞춰줘야 쉬는 날 없이 물 주기 가능
			// 짝수가 홀수와 같거나 하나 많을 때 최적이므로, 그전까지 짝수를 홀수로 변환
			// 짝수 날에 물을 주는 경우 = 홀수 날에 물 두 번 주면 해결
			while (even > odd + 1) {
				even -= 1;
				odd += 2;
			}
			
			// 마지막 날이 홀수로 끝나야 함
			if (even < odd) {
				sum = odd * 2 - 1;
			}
			// 마지막 날이 짝수로 끝나야 함
			else {
				sum = even * 2;
			}
			
			sb.append("#").append(tc).append(" ").append(sum).append("\n");
		}
		
		System.out.println(sb);
	}
}
