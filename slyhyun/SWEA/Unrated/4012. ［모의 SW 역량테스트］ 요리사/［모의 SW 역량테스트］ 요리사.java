/*
 * [문제]
 * - 2명의 손님과 N개의 식재료가 있다.
 * - 식재료들을 각각 N/2개씩 나누어 2개의 요리를 하려고 한다.(N은 짝수)
 * - 이 때 두 음식 A와 B의 맛의 차이가 최소가 되어야 한다.
 * - 식재료 i와 j를 같이 요리하면 시너지 S(i, j) + S(j, i)가 발생한다.
 * - 각 음식의 맛은 음식을 구성하는 식재료들로부터 발생하는 시너지  S(i, j) + S(j, i)들의 합이다.
 * - N*N 격자에 시너지 S(i, j), S(j, i)가 주어진다.
 * - 음식 A와 B를 만들 때 두 음식 간 맛 차이의 최솟값을 출력한다.
 * 
 * [입력]
 * - 식재료의 수 N: 4 ~ 16
 * - 시너지 S(i, j): 1 ~ 20,000
 * - i와 j가 같은 경우의 S(i, j)는 없다. 같은 재료 2개로 요리 불가능.
 * 
 * [설계]
 * - 식재료 선택 (조합 - DFS, 백트래킹)
 *    - 전체 N개의 식재료 중 N/2개를 선택하여 음식 A의 재료로 지정한다.
 *    - 선택되지 않은 나머지 N/2개의 식재료는 자동으로 음식 B의 재료가 된다.
 *    - N개 중 N/2개를 선택하는 조합은 무조건 음식 A와 B의 팀 구성이 뒤바뀐 대칭적인 쌍(중복)이 존재한다.
 *    - 따라서 0번 재료를 음식 A에 고정하면 중복 연산을 차단하여 탐색 횟수를 절반으로 줄일 수 있다.
 * 
 * - 맛의 점수 계산
 *    - 기저 조건(R == N/2)에 도달하면 음식 A의 식재료 리스트와 음식 B의 식재료 리스트를 각각 분리한다.
 *    - 이중 반복문을 순회하며 각 음식의 식재료 쌍(i, j)에 대해 S(i, j) + S(j, i) 시너지 값을 누적한다.
 * 
 * - 최솟값 갱신
 *    - 음식 A의 맛 - 음식 B의 맛의 절댓값을 구한다.
 *    - 구한 차잇값으로 최솟값을 계속해서 업데이트한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
	static int N;
	static int[][] S;
	static boolean[] selected;
	static int min;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			S = new int[N][N];
			selected = new boolean[N];
			min = Integer.MAX_VALUE;

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					S[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			selected[0] = true;
			dfs(1, 1);
			
			sb.append("#").append(tc).append(" ").append(min).append("\n");
		}
		
		System.out.print(sb);
	}

	static void dfs(int idx, int cnt) {
		if (cnt == N / 2) {
			calculate();
			return;
		}

		for (int i = idx; i < N; i++) {
			selected[i] = true;
			dfs(i + 1, cnt + 1);
			selected[i] = false;
		}
	}

	static void calculate() {
		int tasteA = 0;
		int tasteB = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) continue;

				if (selected[i] && selected[j]) {
					tasteA += S[i][j];
				}
				else if (!selected[i] && !selected[j]) {
					tasteB += S[i][j];
				}
			}
		}

		int diff = Math.abs(tasteA - tasteB);
		min = Math.min(min, diff);
	}
}
