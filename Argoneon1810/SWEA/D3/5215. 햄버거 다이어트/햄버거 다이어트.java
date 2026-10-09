import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	int dfs(
			int[][] scoreCaloriePair, 
			boolean[][] selectedIngredients,
			int numIngredients, int calorieLimit, 
			int accumScore, int accumCal, 
			int consideredIngredients
	) {
		if (consideredIngredients>=numIngredients)
			return accumScore;
		if (accumCal>=calorieLimit)
			return accumScore;
		int[] curr = scoreCaloriePair[consideredIngredients];
		int currScore = curr[0];
		int currCalorie = curr[1];
		int tempAccumCal = accumCal + currCalorie;
		boolean currentVisitable = tempAccumCal < calorieLimit;
		int a = 0;
		if (currentVisitable) {
			a = dfs(scoreCaloriePair, selectedIngredients,
					numIngredients, calorieLimit, 
					accumScore+currScore, tempAccumCal, 
					consideredIngredients+1);
		}
		int b = dfs(scoreCaloriePair, selectedIngredients,
				numIngredients, calorieLimit, accumScore, accumCal, 
				consideredIngredients+1);
		int m = Integer.max(a, b);
		selectedIngredients[0][consideredIngredients] = (m == a);
		return m;
	}
	
	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N, L;
		N = Integer.parseInt(st.nextToken()); // [1, 20]
		L = Integer.parseInt(st.nextToken()); // [1, 10000]
		int[][] scoreCaloriePair = new int[N][2];
		for(int j=0; j<N; ++j) {
			st = new StringTokenizer(br.readLine());
			scoreCaloriePair[j][0] = Integer.parseInt(st.nextToken());
			scoreCaloriePair[j][1] = Integer.parseInt(st.nextToken());
		}
		boolean[][] selected = new boolean[2][N];
		int m = dfs(scoreCaloriePair, selected, N, L, 0, 0, 0);
		return Integer.toString(m);
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
