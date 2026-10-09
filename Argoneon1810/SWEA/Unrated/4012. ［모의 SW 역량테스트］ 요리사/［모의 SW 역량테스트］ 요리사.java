import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	boolean isUnfillable(int N, int idx, int picked) {
		return N - idx < N/2 - picked;
	}

	int dfs(int[] r, int N, int total, int idx, int picked, int sumA) {
		if (picked == N/2)
			return Math.abs(2*sumA - total) / 2;
		if (isUnfillable(N, idx, picked))
			return Integer.MAX_VALUE;
		int take = dfs(r, N, total, idx+1, picked+1, sumA + r[idx]);
		int skip = dfs(r, N, total, idx+1, picked, sumA);
		return Integer.min(take, skip);
	}

	String solveInner(BufferedReader br) throws IOException {
		int N = Integer.parseInt(br.readLine().trim());	// (4 ≤ N ≤ 16)
		int[] r = new int[N];
		for (int y=0; y<N; ++y) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int x=0; x<N; ++x) {
				int s = Integer.parseInt(st.nextToken());
				r[y] += s;
				r[x] += s;
			}
		}
		int total = 0;
		for (int i=0; i<N; ++i)
			total += r[i];
		return Integer.toString(dfs(r, N, total, 1, 1, r[0]));
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = Integer.parseInt(br.readLine().trim());
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
