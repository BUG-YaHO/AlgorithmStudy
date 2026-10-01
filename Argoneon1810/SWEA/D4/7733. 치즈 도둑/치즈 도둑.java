import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static final int VOID = 0;
	static final int[] DT = new int[4];

	static int find(int[] parent, int pos) {
		while (parent[pos] != pos) {
			parent[pos] = parent[parent[pos]];
			pos = parent[pos];
		}
		return pos;
	}
	static boolean union(
		int[] parent, int[] blockSize, 
		int a, int b
	) {
		int rootA = find(parent, a);
		int rootB = find(parent, b);
		if (rootA == rootB) return false;
		if (blockSize[rootA] < blockSize[rootB]) {
			int temp = rootA;
			rootA = rootB;
			rootB = temp;
		}
		parent[rootB] = rootA;
		blockSize[rootA] += blockSize[rootB];
		return true;
	}
	static int restore(
		int[] parent, int[] blockSize, 
		int[] root, int[] children, 
		int i, int islandCount
	) {
		int currPtr = root[i];
		while (currPtr!=0) {
			int pos = currPtr-1;
			parent[pos] = pos;
			blockSize[pos] = 1;
			++islandCount;
			for(int d=0;d<4;++d) {
				int next = pos+DT[d];
				if(parent[next]==VOID) continue;
				if(union(parent, blockSize, pos, next))
					--islandCount;
			}
			currPtr = children[currPtr];
		}
		return islandCount;
	}

	static void fillDT(int N) {
		int width = N+2;
		DT[0] = -width; DT[1] = width; DT[2] = -1; DT[3] = 1;
	}

	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		fillDT(N);
		int width = N+2;
		int[] root = new int[101];
		int[] children = new int[width*width+1];
		for(int j=1; j<=N; ++j) {
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=N; ++i) {
				int currVal = Integer.parseInt(st.nextToken());
				int prevRootPtr = root[currVal];
				root[currVal] = j*width+i+1;
				children[j*width+i+1] = prevRootPtr;
			}
		}
		int[] parent = new int[width*width];
		int[] blockSize = new int[width*width];
		int maxIsland = 0;
		int islandCount = 0;
		for(int i=100; i>=1; --i) {
			islandCount = restore(parent, blockSize,
					root, children, i, islandCount);
			maxIsland = Integer.max(maxIsland, islandCount);
		}
		return Integer.toString(maxIsland);
	}

	public static void main(String ... args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for (int tc=1; tc<=T; ++tc) {
			sb.append('#')
				.append(tc)
				.append(' ')
				.append(solve(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}
}
