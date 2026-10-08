import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	static final int IN = 0;
	static final int OUT = 1;
	static int getRankAfterRemoval(int[][][] adjList, int[][] adjTails, int self, int toRemove) {
		// 리스트 마지막 멤버를 지울 멤버 위치에 덮어씀
		// 자기 자신이 마지막 멤버면 쓸데없는 쓰기 연산이 한번 생기긴 하지만
		// 조건 연산과 딱히 차이가 없으니 그냥 둠
		int[] list = adjList[IN][self];
		int tail = --adjTails[IN][self];
		for(int i=0; i<=tail; ++i) {
			if(list[i] == toRemove) {
				list[i] = list[tail];
				break;
			}
		}
		return tail;
	}
	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V, E;
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		// 인접리스트
		int[][][] adjList = new int[2][V][V];
		int[][] adjTails = new int[2][V];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<E; ++i) {
			int from = Integer.parseInt(st.nextToken())-1;
			int to = Integer.parseInt(st.nextToken())-1;
			// 간선 등록
			adjList[OUT][from][adjTails[OUT][from]++] = to;
			adjList[IN][to][adjTails[IN][to]++] = from;
		}

		// 진입차수가 0인 노드로부터 bfs
		Queue<Integer> q = new ArrayDeque<>();
		for(int i=0; i<V; ++i) {
			if(adjTails[IN][i]==0)
				q.offer(i);
		}
		StringBuilder sb = new StringBuilder();
		boolean everRan = false;
		while(!q.isEmpty()) {
			if(everRan)
				sb.append(' ');
			everRan = true;
			int c = q.poll();
			sb.append(c+1);
			// 이번 노드가 가리키는 모든 자녀 랭크 1 감소
			for(int i=0;i<adjTails[OUT][c];++i) {
				int childIdx = adjList[OUT][c][i];
				int newRank = getRankAfterRemoval(
					adjList, adjTails, 
					childIdx, 
					c
				);
				// 새 랭크가 0이라면 큐 등록
				if(newRank == 0)
					q.offer(childIdx);
			}
		}
		return sb.toString();
	}
	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		int T = 10;
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; ++tc) {
			sb
				.append('#')
				.append(tc)
				.append(' ')
				.append(solve(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}
}
