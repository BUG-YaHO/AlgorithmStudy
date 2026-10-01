import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
	
/**
 * 
 * 
 * [ 문제 해설 ]
 * S에서 G로 가기 위한 도로 복구 작업을 빠른 내에 수행해야 함
 * 도로 복구: 도로가 파여진 깊이에 비례함 (깊이 1 = 복구에 드는 시간 1)
 * 
 * 출발지는 좌상단, 도착지는 우하단
 * 가장 깊이가 얕은 곳을 찾아서 가면 됨
 * 
 * [ 출력 ]
 * S에서 G로 가기까지 가장 복구시간이 짧은 경로의 복구시간
 * 
 * [ 입력 ]
 * 지도의 한 줄 크기 N
 * 2차원 배열 지도 (N * N)
 * 
 * [ 제약조건 ]
 * 지도의 한 줄 크기 N <= 100
 * 
 * [ 풀이방법 ]
 * 다익스트라를 이용해서 최단거리가 짧은 노드 순으로 이동할 수 있도록 한다
 * 
 */
	
	static int N;
	static int[][] arr;
	static int[] dr = {1, 0, -1, 0};
	static int[] dc = {0, 1, 0, -1};
	
	static class Road {
		int r;
		int c;
		int time;
		
		public Road(int r, int c, int time) {
			this.r = r;
			this.c = c;
			this.time = time;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			arr = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				String now = br.readLine();
				for (int j = 0; j < N; j++) {
					arr[i][j] = now.charAt(j) - '0';
				}
			}
			
			int answer = dijikstra();
			
	
			sb.append("#").append(t).append(" ").append(answer).append("\n");
		}
		
		System.out.println(sb);
	}
	
	static int dijikstra() {
		
		// 방문 시간 저장하는 배열
		int[][] timeArr = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(timeArr[i], Integer.MAX_VALUE);
		}
		
		PriorityQueue<Road> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.time, o2.time));
		pq.add(new Road(0, 0, arr[0][0]));
		while (!pq.isEmpty()) {
			Road now = pq.poll();
			
			int nowR = now.r;
			int nowC = now.c;
			int nowTime = now.time;
			
			// 가지치기
			if (nowTime > timeArr[nowR][nowC]) {
				continue;
			}
			
			for (int i = 0; i < 4; i++) {
				int nextR = nowR + dr[i];
				int nextC = nowC + dc[i];
				
				if (nextR < 0 || nextR >= N || nextC < 0 || nextC >= N) {
					continue;
				}
				
				int nextTime = nowTime + arr[nextR][nextC];
				if (nextTime < timeArr[nextR][nextC]) {
					//갱신
					timeArr[nextR][nextC] = nextTime;
					pq.add(new Road(nextR, nextC, timeArr[nextR][nextC]));
				}
				
			}

		}
		
		return timeArr[N-1][N-1];
		
		
	}

}

