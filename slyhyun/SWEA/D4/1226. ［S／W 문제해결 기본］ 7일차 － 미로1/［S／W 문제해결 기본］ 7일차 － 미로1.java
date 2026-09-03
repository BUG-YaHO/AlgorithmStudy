import java.util.*;
import java.io.*;

public class Solution {
	static int[] dr = {-1, 0, 1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc <= 10; tc++) {
			br.readLine();
			
			int[][] maze = new int[16][16];
			
			int r = -1;
			int c = -1;
			
			for (int i = 0; i < 16; i++) {
                String line = br.readLine();
                
                for (int j = 0; j < 16; j++) {
                    maze[i][j] = line.charAt(j) - '0';
                    
                    if (maze[i][j] == 2) {
                        r = i;
                        c = j;
                    }
                }
            }
			
			int result = bfs(r, c, maze);
			
			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}
		
		System.out.print(sb);
	}
	
	static int bfs(int startR, int startC, int[][] maze) {
		Queue<int[]> q = new ArrayDeque<>();
		
		maze[startR][startC] = 1;
		q.offer(new int[]{startR, startC});
		
		while (!q.isEmpty()) {
			int[] curr = q.poll();
			
			int r = curr[0];
			int c = curr[1];
			
			for (int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				int nc = c + dc[d];
				
				if (maze[nr][nc] == 3) {
					return 1;
				}
				
				if (maze[nr][nc] == 1) {
					continue;
				}

				maze[nr][nc] = 1;
				q.offer(new int[]{nr, nc});
			}
		}
		
		return 0;
	}

}
