import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc < T + 1; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			Set<String> hset = new HashSet<>();
			
			int answer = 0;
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				hset.add(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < M; i++) {
				hset.add(st.nextToken());
			}
			
			answer = N + M - hset.size();
			
			StringBuilder sb = new StringBuilder("#");
			sb.append(tc).append(" ").append(answer);
			System.out.println(sb);
		}
	}
}
