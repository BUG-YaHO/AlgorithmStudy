import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for (int tc = 1; tc < T + 1; tc++) {
			sb.append("#").append(tc).append(" ");
			Queue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
			int N = Integer.parseInt(br.readLine()); // 수행해야하는 연산의 수
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine().trim());
				int[] operNum = new int[2];
				int index = 0;
				while (st.hasMoreTokens()) {
					operNum[index++] = Integer.parseInt(st.nextToken().trim());
				}
				
				if (index == 2) {  // 입력값이 2개인 경우
					heap.offer(operNum[1]);
				} else {  // 입력값이 1개인 경우
					if (heap.size() == 0) {  // 출력할 키값이 없으면 -1 출력
						sb.append(-1).append(" ");
						continue;
					}
					sb.append(heap.peek()).append(" ");
					heap.poll();
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}
