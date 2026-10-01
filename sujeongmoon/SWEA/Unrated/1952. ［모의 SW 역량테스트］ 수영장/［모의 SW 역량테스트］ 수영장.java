import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static int[] arr; // 이용권 배열
	static int[] monthArr; // 수영장 이용계획 배열
	static int answer;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			arr = new int[4];
			monthArr = new int[13]; // 1부터 시작
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < 4; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= 12; i++) {
				monthArr[i] = Integer.parseInt(st.nextToken());
			}
			
			answer = Integer.MAX_VALUE;
			
			dfs(1, 0); // 1월부터 시작
			
			answer = Math.min(answer, arr[3]);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		System.out.println(sb);
	}
	
	static void dfs(int month, int sum) {
		
		if (month > 12) {
			answer = Math.min(answer, sum);
			return;
		}
		
		// 만약 현재 달에 수영장 이용계획이 없다면 다음 달 패스
		if (monthArr[month] == 0) {
			dfs(month+1, sum);
			return;
		}
		
		// 현재 달에 수영장 이용계획이 있다면
		// 일일 요금 쓰는 경우
		dfs(month+1, sum+(arr[0] * monthArr[month]));
		// 월 요금 쓰는 경우
		dfs(month+1, sum+arr[1]);
		// 세 달치 요금 쓰는 경우
		dfs(month+3, sum+arr[2]);
		
	}

}
