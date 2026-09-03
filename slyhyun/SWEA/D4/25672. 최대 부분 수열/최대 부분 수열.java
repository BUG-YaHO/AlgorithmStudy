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
			int K = Integer.parseInt(st.nextToken());
			
			int[] arr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			
			int max = Integer.MIN_VALUE;
			int maxLeft = Integer.MIN_VALUE;
			
			// 접근법: 배열을 2개로 나눈 뒤에, (왼쪽 최댓값 + 오른쪽 구간합)을 해서 최종 최댓값을 구하자
			// 왼쪽 구간에 최소 K개의 숫자 존재
			for (int i = K; i < N - K + 1; i++) {
				int lSum = 0;
				int rSum = 0;

				// lSum에 K개의 숫자를 구간합
				for (int k = 0; k < K; k++) {
					lSum += arr[(i - K) + k];
				}
				
				// 왼쪽 구간의 최댓값 찾기
				if (lSum > maxLeft) {
					maxLeft = lSum;
				}
								
				// rSum에 K개의 숫자를 구간합
				for (int k = 0; k < K; k++) {
					rSum += arr[i + k];
				}
				
				// (왼쪽 구간의 최댓값 + 오른쪽 구간의 구간합)에서 최종 최댓값 찾기
				if (maxLeft + rSum > max) {
					max = maxLeft + rSum;
				}
			}
			
			sb.append("#").append(tc).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb);
	}
}
