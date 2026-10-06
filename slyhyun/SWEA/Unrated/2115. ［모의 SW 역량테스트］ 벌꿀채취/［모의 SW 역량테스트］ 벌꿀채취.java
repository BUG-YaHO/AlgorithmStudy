/*
 * [문제]
 * - N*N 격자가 있고, 각 칸(벌통)에 꿀의 양이 숫자로 표시된다.
 * - 두 명의 일꾼이 다음과 같은 과정으로 벌꿀을 채취한다.
 *   - 각 일꾼은 각각 가로로 M칸을 연속해서 골라야 한다.
 *   - 각 일꾼이 선택한 구간은 겹치거나 격자를 벗어나면 안된다.
 *   - 일꾼이 연속된 M칸을 골랐을 때, 꿀을 다 더해서 C 이하인 경우에는 전부 가져가면 된다.
 *   - 만약 M칸의 꿀 합이 C를 초과하는 경우, 합이 C 이하가 되도록 몇 개만 골라내야 한다.
 * - 채취하기로 선택한 각 벌통의 꿀 양을 각각 제곱해서 더한 값이 그 일꾼의 수익이 된다.
 * - 두 일꾼의 최대 수익의 합이 가장 클 때의 결과를 구한다.
 * 
 * [입력]
 * - 격자 크기 N: 3 ~ 10
 * - 구간의 길이 M: 1 ~ 5
 * - M은 반드시 N 이하
 * - 일꾼 당 최대 꿀 C: 10 ~ 30
 * - 각 칸에 담긴 꿀의 양: 1 ~ 9
 * 
 * [설계]
 * - 두 일꾼의 채취 구간 선택 (브루트포스)
 *   - 격자 내에서 겹치지 않는 2개의 연속된 M칸 구간을 고르는 모든 조합을 구한다.
 *   - 첫 번째 일꾼의 시작점 (r1, c1)과 두 번째 일꾼의 시작점 (r2, c2)을 완전 탐색으로 결정한다.
 *   - 두 구간이 서로 겹치지 않아야 하므로, 같은 행일 경우 열 번호가 겹치지 않는지 체크해야 한다.
 * - 각 구간 내에서의 최대 수익 계산 (DFS - 부분집합)
 *   - 선택된 M개의 벌통 중 일부를 골라 합이 C 이하가 되면서, 제곱의 합(수익)이 최대가 되는 최적의 조합을 찾는다.
 *   - 각 구간마다 DFS를 활용해 부분집합을 생성하고, 조건(합 <= C)을 만족하는 최대 제곱 합을 갱신한다.
 * - 최댓값 갱신
 *   - (일꾼 1의 최대 수익 + 일꾼 2의 최대 수익)의 합 중 전체 최댓값을 정답으로 도출한다.
 * - 동일한 구간에 대해 최대 수익을 중복 계산하는 것을 방지하기 위해, 미리 각 위치에서 시작하는 M칸의 최대 수익을 2차원 배열에 계산해 두면 연산량을 더욱 줄일 수 있다.
 */

import java.io.*;
import java.util.*;

public class Solution {
	static int N, M, C;
	static int[][] map;
	static int[][] profit;
	static int max;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			profit = new int[N][N - M + 1];
			
			for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j <= N - M; j++) {
					max = 0;
					dfs(i, j, 0, 0, 0);
					profit[i][j] = max;
				}
			}
			
			int ans = 0;
			
			for (int r1 = 0; r1 < N; r1++) {
                for (int c1 = 0; c1 <= N - M; c1++) {
                    
                    for (int r2 = r1; r2 < N; r2++) {
                        int startC = (r1 == r2) ? c1 + M : 0;
                        
                        for (int c2 = startC; c2 <= N - M; c2++) {
                            int sum = profit[r1][c1] + profit[r2][c2];
                            ans = Math.max(ans, sum);
                        }
                    }
                }
            }
			
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		
		System.out.print(sb);
	}
	
	static void dfs(int r, int c, int idx, int sum, int profit) {
		if (idx == M) {
			max = Math.max(max, profit);
			return;
		}
		
		int cur = map[r][c + idx];
		
		if (sum + cur <= C) {
            dfs(r, c, idx + 1, sum + cur, profit + (cur * cur));
        }
		dfs(r, c, idx + 1, sum, profit);
	}
}
