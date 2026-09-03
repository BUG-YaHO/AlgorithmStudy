import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution { // D4. 풍선 사격 게임.

	static ArrayList<Integer> list;
	static int answer;
	static int N;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			list = new ArrayList<>();

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				list.add(Integer.parseInt(st.nextToken()));
			}

			answer = 0;

			dfs(0);

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	static void dfs(int num) {

		if (list.isEmpty()) {
			answer = Math.max(answer, num);
			return;
		}

		for (int i = 0; i < list.size(); i++) {

			int count = shooting(i);

			int removed = list.remove(i);

			dfs(num + count);

			list.add(i, removed);
		}
	}

	static int shooting(int now) {

		boolean left = now - 1 >= 0;
		boolean right = now + 1 < list.size();

		if (left && right) {
			return list.get(now - 1) * list.get(now + 1);
		}

		if (!left && right) {
			return list.get(now + 1);
		}

		if (left && !right) {
			return list.get(now - 1);
		}

		if (!left && !right) {
			return list.get(now);
		}

		return -1;
	}
}
