import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
	
	static int[] dr = new int[] {1, 0, -1, 0};
	static int[] dc = new int[] {0, 1, 0, -1};
	
	static int[][] arr;
	static boolean[][] isVisited;
	static int answer;

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int t = 1; t <= 10; t++) {
			
			br.readLine();
			
			arr = new int[16][16];
			isVisited = new boolean[16][16];
			
			int[] start = new int[2];
			answer = 0;
			
			for (int i = 0; i < 16; i++) {
				String row = br.readLine();
				for (int j = 0; j < 16; j++) {
					arr[i][j] = row.charAt(j) - '0';
					if (arr[i][j] == 2) {
						start[0] = i;
						start[1] = j;
					} 
				}
			}
			
			dfs(start[0], start[1]);
			
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		
		System.out.println(sb);
		
	}

	private static void dfs(int r, int c) {
		
		if (r < 0 || c < 0 || r >= 16 || c >= 16) {
			return;
		}
		
		if (arr[r][c] == 1 || isVisited[r][c]) {
			return;
		}
		
		if (arr[r][c] == 3) {
			answer = 1;
			return;
		}
		
		isVisited[r][c] = true;
		for (int i = 0; i < 4; i++) {
			dfs(r+dr[i], c+dc[i]);
		}
		isVisited[r][c] = false;
		
	}

}