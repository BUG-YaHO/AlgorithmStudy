import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 미로 16x16
 * 0은 갈 수 있는 칸
 * 1은 벽
 * 2는 출발점
 * 3은 종료점
 * 
 * 바깥이 항상 벽이라는 조건이 없다
 * 그림 상으로는 그래 보이지만, 일단 경계체크 하는 쪽으로 구현
 * 
 * 테케 10개 고정
 */
public class Solution {
	static final int WIDTH = 16;
	static final int AREA = WIDTH * WIDTH;
	static final int EMPTY = 0;
	static final int WALL = 1;
	static final int START = 2;
	static final int END = 3;
	static final int VISITED = START;
	static final int[] DT = {-WIDTH, WIDTH, -1, 1};

	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		int T=10;
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

	static String solve(BufferedReader br) throws IOException {
		// 테케 번호 버리기
		br.readLine();
		// 이 문제는 평탄화를 안하는게 좋은 문제지만
		// 평탄화된 플러드필을 연습하고 싶으니 무시함
		// 미로 받기
		int[] maze = new int[AREA];
		char[] line = null;
		int startIdx, endIdx;
		startIdx = endIdx = -1;
		for(int i=0; i<AREA; ++i) {
			if(i%WIDTH==0)
				line = br.readLine().toCharArray();
			maze[i] = line[i%WIDTH]-'0';
			switch(maze[i]) {
				case START:
					startIdx=i;
					break;
				case END:
					endIdx=i;
					break;
			}
		}
		// 출발점에서 플러드필.
		// 값 2를 방문 상태로 간주
		floodfill(maze, startIdx);
		// 종료점의 값이 VISITED로 찍혀있음 도착한 것
		return maze[endIdx] == VISITED ? "1" : "0";
	}

	static void floodfill(int[] maze, int startIdx) {
		Stack mStack = new Stack(AREA);
		mStack.push(startIdx);
		while(mStack.size() > 0) {
			int currentIdx = mStack.pop();
			for(int i=0; i<4; ++i) {
				int nextIdxCandidate = currentIdx + DT[i];
				if(isOffgrid(currentIdx, nextIdxCandidate)) continue;
				if(maze[nextIdxCandidate] == VISITED) continue;
				if(maze[nextIdxCandidate] == WALL) continue;
				maze[nextIdxCandidate] = VISITED;
				mStack.push(nextIdxCandidate);
			}
		}
	}

	static boolean isOffgrid(int prev, int curr) {
		if(curr < 0) return true;
		if(curr >= AREA) return true;
		if((prev%WIDTH != curr%WIDTH) && (prev/WIDTH != curr/WIDTH))
			return true;
		return false;
	}

	static class Stack {
		int[] data;
		int head;
		Stack() { this(8); }
		Stack(int capacity) { 
			data = new int[capacity];
			head = 0;
		}
		private void resize() {
			int[] old = data;
			data = new int[(int)(old.length*1.4)];
			System.arraycopy(old, 0, data, 0, old.length);
		}
		void push(int v) {
			if(head>=data.length) resize();
			data[head++]=v;
		}
		int pop() { return data[--head]; }
		int peak() { return data[head-1]; }
		int size() { return head; }
		void softReset() {
			head=0;
		}
	}
}
