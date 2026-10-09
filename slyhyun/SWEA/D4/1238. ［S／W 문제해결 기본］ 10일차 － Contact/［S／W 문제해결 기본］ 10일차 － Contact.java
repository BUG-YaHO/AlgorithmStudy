/*
 * [문제]
 * - 비상연락망을 표현한 유향 그래프가 있다.
 * - 화살표는 연락 가능한 방향을 의미한다.
 * - 진출 차수가 여러 개인 경우, 진출 정점들에 동시에 연락한다.
 * - 이미 연락을 받은 경우, 다시 연락을 받지 않는다.
 * - 그래프에 비어있는 번호가 있을 수 있다.
 * - 가장 나중에 연락을 받게 되는 사람 중 번호가 가장 큰 사람을 구한다.
 * 
 * [입력]
 * - 연락 인원: 2 ~ 100
 * - 부여되는 번호: 1 ~ 100
 * - 입력받는 데이터는 {from, to, from, to, …} 의 순서로 해석
 * - 입력받는 데이터의 순서는 상관이 없다.
 * - 동일한  {from, to}쌍이 여러 번 반복되는 경우도 있지만, 의미는 없다.
 * 
 * [설계]
 * - 선행 관계를 가지는 정점들을 인접 리스트로 만든다.
 * - 큐에 시작 정점을 넣고, 인접 정점을 전부 동시에 처리한다.
 * - 각 단계가 끝날 때마다 해당 단계의 정점 중 가장 큰 번호를 기록한다.
 * - 그리하여 탐색이 완전히 끝났을 때의 마지막 기록을 정답으로 출력
 */
import java.io.*;
import java.util.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		for (int tc = 1; tc <= 10; tc++) {
			st = new StringTokenizer(br.readLine());
			
			int length = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());
			
			boolean[] visited = new boolean[101];
			ArrayList<Integer>[] graph = new ArrayList[101];
			
			for (int i = 1; i <= 100; i++) {
				graph[i] = new ArrayList<Integer>();
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < length / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				graph[from].add(to);
			}
			
			int max = start;
			
			Queue<Integer> q = new ArrayDeque<>();
			
			visited[start] = true;
			q.offer(start);
			
			while (!q.isEmpty()) {
				int size = q.size();
				int level = 0;
				
				for (int i = 0; i < size; i++) {
					int cur = q.poll();
					
					level = Math.max(level, cur);
					
					for (int n : graph[cur]) {
						if (visited[n]) continue;
						
						visited[n] = true;
						q.offer(n);
					}
				}
				
				max = level;
			}
			
			sb.append("#").append(tc).append(" ").append(max).append("\n");
		}
		
		System.out.print(sb);
	}
}
