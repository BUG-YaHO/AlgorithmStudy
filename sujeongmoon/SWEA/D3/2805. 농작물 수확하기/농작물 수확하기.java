import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	
	static int N; 

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
 
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			// 공백
			int space = N / 2;
			int answer = 0;
			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					// 공백만큼 패스
					if (j < space) {
						continue;
					}
					if (j >= N - space) {
						continue;
					}
					answer += line.charAt(j) - '0';
				}
				if (i < N / 2) {
					space--;
				} else {
					space++;
				}
			}
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		
		System.out.print(sb);
	}

}
