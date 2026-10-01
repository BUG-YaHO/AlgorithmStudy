import java.io.*;
import java.util.*;

class Solution {
	static final int N = 100;
	static final int[] DT = {-N, -1, 1, N};
	static final int UP = 0, LEFT = 1, RIGHT = 2;
	static final int VOID = 0, LADDER = 1, START = 2;

	boolean isOffgrid(int prev, int curr) {
		return curr<0 || curr>N*N || ((prev%N!=curr%N) && (prev/N!=curr/N));
	}
	boolean isSteppable(int[] grid, int idx) {
		return grid[idx] != VOID;
	}
	void getSideway(int[] grid, int[] bundle, int pos) {
		int left = pos+DT[LEFT];
		if (!isOffgrid(pos, left)) {
			if (isSteppable(grid, left)) {
				bundle[0] = LEFT;
				bundle[1] = left;
				return;
			}
		}
		int right = pos+DT[RIGHT];
		if (!isOffgrid(pos, right)) {
			if (isSteppable(grid, right)) {
				bundle[0] = RIGHT;
				bundle[1] = right;
				return;
			}
		}
		bundle[0] = -1;
		bundle[1] = -1;
	}

	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		st.nextToken();// 테케번호 무시
		int[] grid = new int[N*N];
		int startIdx = -1;
		for(int j=0; j<N; ++j) {
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; ++i) {
				int t = Integer.parseInt(st.nextToken());
				grid[j*N+i] = t;
				if (t==START)
					startIdx = j*N+i;
			}
		}
		int[] goal = {0};
		int[] bundle = {-1, -1};
		int pos = startIdx;
		int dir = UP;
		while(true) {
			int y = pos / N, x = pos % N;
			if (y==0) {
				goal[0] = x;
				break;
			}
			int lookahead = pos + DT[dir];
			boolean shouldTurn = false;
			if (isOffgrid(pos, lookahead))
				shouldTurn = true;
			if (!isSteppable(grid, lookahead))
				shouldTurn = true;
			getSideway(grid, bundle, pos);
			if (dir == UP && bundle[0] != -1)
				shouldTurn = true;
			if (shouldTurn) {
				if (dir > 0) {
					dir = UP;
					lookahead = pos + DT[0];
				}
				else {
					dir = bundle[0];
					lookahead = bundle[1];
				}
			}
			pos = lookahead;
		}
		return Integer.toString(goal[0]);
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = 10;
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(br))
					.append('\n');
		System.out.print(sb);
	}

	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}
}
