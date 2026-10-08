import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {

	/*
	[ 문제 해설 ]
	16*16 행렬의 형태로 만들어진 미로
	0은 길, 1은 벽
	갈 수 있는 길이 있는지 판단하는 프로그램을 작성하라

	[ 고려 사항 ]
	1 - 벽
	0 - 길
	2 - 출발점
	3 - 도착점


	[ 입력 ]
	10개의 tc 고정
	첫 번째 줄에는 tc의 번호
	두 번째 줄부터 16*16의 행렬이 주어짐

  	[ 출력 ]
  	1 : 목적지까지 도달 가능
  	0 : 불가능

	[ 풀이 ]
	static int[][] arr : 16*16의 배열
	stati boolean[][] isVisited
	int startR, startC

	dfs로 3까지 도달 가능하면 true, 아니면 false 출력
	 */

	static int[][] arr;
	static boolean[][] isVisited;
	static int answer;
	static int[] dr = {1, 0, -1, 0};
	static int[] dc = {0, 1, 0, -1};

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		for (int t = 1; t <= 10; t++) {
			br.readLine(); // tc 줄 날리기
			answer = 0;
			arr = new int[16][16];
			isVisited = new boolean[16][16];
			int startR = 0;
			int startC = 0;

			for (int i = 0; i < 16; i++) {
				String now =  br.readLine();
				for (int j = 0; j < 16; j++) {
					arr[i][j] = now.charAt(j) - '0';
					if (arr[i][j] == 2) {
						startR = i;
						startC = j;
					}
				}
			}

			dfs(startR, startC);

			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		System.out.println(sb);
	}

	static void dfs(int r, int c) {
		if (r < 0 || r >= 16 || c < 0 || c >= 16) {
			return;
		}
		if (isVisited[r][c]) {
			return;
		}
		if (arr[r][c] == 1) {
			return;
		}
		if (arr[r][c] == 3) {
			answer = 1;
			return;
		}

		isVisited[r][c] = true;

		for (int i = 0; i < 4; i++) {
			int nextR = r + dr[i];
			int nextC = c + dc[i];
			dfs(nextR, nextC);
		}
	}
}
