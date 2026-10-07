/*
 * [문제]
 * - 개구리가 울면 "croak"이라는 소리가 난다.
 * - 개구리 한 마리가 여러 번 울면 문자열이 반복된다.
 * - 개구리 여러 마리가 동시에 울면 문자열이 겹치게 된다.
 * - 울음 소리는 가능한 최소한의 개구리로 이루어져있다.
 * - 이 때 주어진 문자열을 보고 개구리의 울음 소리로 가능한지, 가능하다면 몇 마리인지 맞춘다.
 * 
 * [설계]
 * - "croak" 각 문자의 개수를 기록한다.
 * - 각 문자가 나올 때마다 직전 단계의 문자가 충분히 있는지 확인한다.
 * - 없다면 울음 소리로 불가능하기 땨문에 -1 출력
 * - c를 만나면 개구리 수 +1, k를 만나면 개구리 수 -1하며 최댓값 갱신
 * - 문자열이 끝났을 때, 기록된 문자 개수가 전부 0이어야 한다.
 * - 개구리 수의 최댓값 출력
 */
import java.io.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			String str = br.readLine();
			
			int[] cnt = new int[5];
			int frogs = 0;
			int max = 0;
			boolean isValid = true;
			
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				
				if (ch == 'c') {
					cnt[0]++;
					frogs++;
					
					if (frogs > max) {
						max = frogs;
					}
				}
				else if (ch == 'r') {
					if (cnt[0] <= cnt[1]) {
						isValid = false;
						break;
					}
					
					cnt[1]++;
				}
				else if (ch == 'o') {
					if (cnt[1] <= cnt[2]) {
						isValid = false;
						break;
					}
					
					cnt[2]++;
				}
				else if (ch == 'a') {
					if (cnt[2] <= cnt[3]) {
						isValid = false;
						break;
					}
					
					cnt[3]++;
				}
				else if (ch == 'k') {
					if (cnt[3] <= cnt[4]) {
						isValid = false;
						break;
					}
					
					cnt[0]--;
					cnt[1]--;
					cnt[2]--;
					cnt[3]--;
					
					frogs--;
				}
				else {
					isValid = false;
					break;
				}
			}
			
			if (frogs != 0) {
				isValid = false;
			}
			
			sb.append("#").append(tc).append(" ");
			if (!isValid) sb.append(-1);
			else sb.append(max);
			sb.append("\n");
		}
		
		System.out.print(sb);
	}
}
