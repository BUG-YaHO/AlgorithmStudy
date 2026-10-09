import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution {
	static final int WORK = 0;
	static final int HOME = 1;
	static final int FIRST_CUSTOMER = 2;
	static final int X = 0;
	static final int Y = 1;
	static final int INF = Integer.MAX_VALUE;

	int[][] buildDistances(int[][] posArray, int numNodes) {
		int[][] dist = new int[numNodes][numNodes];
		for (int j=0; j<numNodes; ++j)
			for (int i=0; i<numNodes; ++i)
				dist[j][i] = Math.abs(posArray[j][X] - posArray[i][X])
						+ Math.abs(posArray[j][Y] - posArray[i][Y]);
		return dist;
	}

	boolean isVisited(int mask, int customer) {
		return (mask & (1 << customer)) != 0;
	}
    
	void fillDp(int[][] dp, int[][] dist, int N) {
		for (int c=0; c<N; ++c)
			dp[1 << c][c] = dist[WORK][c+FIRST_CUSTOMER];
		for (int mask=1; mask<(1 << N); ++mask)
			for (int last=0; last<N; ++last) {
				if (dp[mask][last] == INF) continue;
				int from = last+FIRST_CUSTOMER;
				for (int i=0; i<N; ++i) {
					if (isVisited(mask, i)) continue;
					int next = mask | (1 << i);
					dp[next][i] = Integer.min(dp[next][i],
							dp[mask][last] + dist[from][i+FIRST_CUSTOMER]);
				}
			}
	}

	int findAnswer(int[][] dp, int[][] dist, int N) {
		int full = (1 << N) - 1;
		int best = INF;
		for (int i=0; i<N; ++i)
			best = Integer.min(best,
					dp[full][i] + dist[i+FIRST_CUSTOMER][HOME]);
		return best;
	}

	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int numCustomers; // 2≤N≤10
		numCustomers = Integer.parseInt(st.nextToken());
		int[][] posArray = new int[numCustomers+2][2];
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<numCustomers+2; ++i) {
			posArray[i][X] = Integer.parseInt(st.nextToken());
			posArray[i][Y] = Integer.parseInt(st.nextToken());
		}
		int[][] dist = buildDistances(posArray, numCustomers+2);
		int[][] dp = new int[1 << numCustomers][numCustomers];
		for (int j=0; j<(1 << numCustomers); ++j)
			Arrays.fill(dp[j], INF);
		fillDp(dp, dist, numCustomers);
		return Integer.toString(findAnswer(dp, dist, numCustomers));
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
