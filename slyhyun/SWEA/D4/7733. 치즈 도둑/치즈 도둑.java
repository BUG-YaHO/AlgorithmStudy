/*
 * [문제]
 * - N*N 치즈
 * - 치즈의 각 칸마다 맛이 1~100으로 표현
 * - 100일동안 X번째 날에는 맛이 X인 칸이 사라짐
 * - 상하좌우로 인접한 칸들을 연결하게 되면 치즈 덩어리가 된다.
 * - 100일 중 치즈 덩어리가 가장 많을 때의 덩어리 개수 구하기
 * 
 * [입력]
 * - N: 2~100
 * - 각 칸은 1~100
 * 
 * [설계]
 * - boolean 배열로 방문한 칸을 관리
 * - 사라진 칸은 현재 날짜와 칸에 적힌 숫자를 비교하여 관리
 * - 하루마다 사라졌거나 방문한 칸을 제외하고 모든 좌표에서 bfs 횟수 = 덩어리 개수
 * - 덩어리 개수 중 최대 갱신
 */
import java.io.*;
import java.util.*;

public class Solution {
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	static int N;
	static int[][] cheese;
	static boolean[][] visited;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			cheese = new int[N][N];
			
			int max = 0;
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					cheese[i][j] = Integer.parseInt(st.nextToken());
					
					max = Math.max(max, cheese[i][j]);
				}
			}
			
			int res = 1;
			
			for (int i = 1; i < max; i++) {
				visited = new boolean[N][N];
				
				int cnt = 0;
				
				for (int j = 0; j < N; j++) {
					for (int k = 0; k < N; k++) {
						if (cheese[j][k] > i && !visited[j][k]) {
							bfs(i, j, k);
							cnt++;
						}
					}
				}
				
				res = Math.max(res, cnt);
			}
			
			sb.append("#").append(tc).append(" ").append(res).append("\n");
		}
		
		System.out.print(sb);
	}
	
	static void bfs(int day, int r, int c) {
		Queue<int[]> q = new ArrayDeque<>();
		
		q.add(new int[]{r, c});
		visited[r][c] = true;
		
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			
			for (int d = 0; d < 4; d++) {
				int nr = cur[0] + dr[d];
				int nc = cur[1] + dc[d];
				
				if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
				if (cheese[nr][nc] <= day || visited[nr][nc]) continue;
				
				visited[nr][nc] = true;
				q.add(new int[]{nr, nc});
			}
		}
	}
}
