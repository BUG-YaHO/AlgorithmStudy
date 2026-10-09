import java.io.*;
import java.util.*;

public class Solution {
	static final int NUM_CARDS = 12;
	static boolean isTriple(int[] hist, int card) {
		return hist[card]>=3;
	}
	static boolean isStraight(int[] hist) {
		int conseq = 0;
		for(int i=0; i<10; ++i) {
			if(hist[i]>0) ++conseq;
			if(hist[i]==0) conseq=0;
			if(conseq>=3)
				return true;
		}
		return false;
	}
	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] cards = new int[NUM_CARDS];
		int[][] hist = new int[2][10];
		int win = 0;
		for(int i=0; i<NUM_CARDS; ++i) {
			int round = i/2;
			int player = i%2;
			++hist[player][cards[i] = Integer.parseInt(st.nextToken())];
			if(isTriple(hist[player], cards[i]) || isStraight(hist[player])) {
				win = player+1;
				break;
			}
		}
		return Integer.toString(win);
	}
	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; ++tc) {
			sb.append('#')
				.append(tc)
				.append(' ')
				.append(solve(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}	
}
