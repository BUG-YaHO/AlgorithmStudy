/*
 * [문제]
 * - 선행 관계가 있는 작업들이 그래프로 주어진다.
 * - 선행 관계는 방향성을 가진 간선으로 표현된다.
 * - 사이클은 존재하지 않는다.
 * - V개의 작업과 이들 간의 선행 관계가 주어질 때, 일을 끝낼 수 있는 작업 순서 찾기
 * 
 * [입력]
 * - tc 10개
 * - 정점의 개수 V: 3 ~ 1000
 * - 간선의 개수 E: 2 ~ 3000
 * - 선헹 관계를 가지는 두 정점이 공백으로 구분되어 묶음을 이룬다.
 * - 간선의 개수만큼 정점 묶음이 공백으로 구분되어 나열된다.
 * 
 * [설계]
 * - 선행 관계를 가지는 정점들을 인접 리스트로 만든다.
 * - 또한, 진입 차수를 기록하는 배열을 만든다.
 * - 큐에 진입 차수가 0인 정점들을 넣고, 위상 정렬을 수행한다. 
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
			
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			List<Integer>[] edge = new ArrayList[V + 1];
			
			for (int i = 1; i <= V; i++) {
				edge[i] = new ArrayList<>();
			}
			
			int[] arr = new int[V + 1];
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < E; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				edge[from].add(to);
				arr[to]++;				
			}
			
			sb.append("#").append(tc);
			
			Queue<Integer> q = new ArrayDeque<>();
			
			for (int i = 1; i <= V; i++) {
				if (arr[i] != 0) continue;
				
				q.offer(i);
			}
			
			while (!q.isEmpty()) {
				int curr = q.poll();
				
				sb.append(" ").append(curr);
				
				for (int n : edge[curr]) {
					arr[n]--;
					
					if (arr[n] != 0) continue;
					
					q.offer(n);
				}
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}
