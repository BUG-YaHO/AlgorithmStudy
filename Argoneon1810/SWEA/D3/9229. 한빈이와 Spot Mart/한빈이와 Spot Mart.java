import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

class Solution {
	int dfs(
			int[] weight, boolean[] visited,
			int numSnacks, int maxWeightLimit,
			int accumWeight,
			int consideredSnacks, int takenSnacks
	) {
		if (takenSnacks == 2) return accumWeight;
		if (consideredSnacks >= numSnacks) return -1;
		int currentWeight = weight[consideredSnacks];
		int tempAccumWeight = accumWeight + currentWeight;
		boolean visitable = tempAccumWeight <= maxWeightLimit;
		int a=-1;
		if (visitable) {
			a = dfs(weight, visited, numSnacks, maxWeightLimit,
					tempAccumWeight, consideredSnacks+1, takenSnacks+1);
			if (a<0) return -1;
		}
		int b = dfs(weight, visited, numSnacks, maxWeightLimit,
				accumWeight, consideredSnacks+1, takenSnacks);
		int m = Integer.max(a, b); 
		visited[consideredSnacks] = (m==a);
		boolean took2 = takenSnacks == 2;
		boolean lightEnough = (m <= maxWeightLimit);
		if (!took2 && !lightEnough)
			return -1;
		return m;
	}
	
	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N, M;
		N = Integer.parseInt(st.nextToken());		// [2, 1000]
		M = Integer.parseInt(st.nextToken());		// [2, 2000000]
		int[] weight = new int[N];
		boolean[] visited = new boolean[N]; 
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; ++i)
			weight[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(weight);
		int m = dfs(weight, visited, N, M, 0, 0, 0);
		return Integer.toString(m == 0 ? -1 : m);
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
