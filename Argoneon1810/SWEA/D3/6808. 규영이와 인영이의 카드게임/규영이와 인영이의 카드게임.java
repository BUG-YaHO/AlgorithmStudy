import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static final int ALL_CARDS = 18;
	static final int PER_PLAYER_CARDS = 9;
	static final int P1_ID = 0;
	static final int P2_ID = 1;
	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
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
		StringTokenizer st;
		// 규영이 카드 받으면서, 규영이가 가져간 카드 트래킹하기
		boolean[] usedCards = new boolean[ALL_CARDS];
		int[] p1Cards = new int[PER_PLAYER_CARDS];
		st = new StringTokenizer(br.readLine());
		int counter = 0;
		while(st.hasMoreTokens())
			usedCards[p1Cards[counter++]=Integer.parseInt(st.nextToken())-1]=true;
		// 남은 카드 인영이 주기 (오름차순 보장)
		int[] p2Cards = new int[PER_PLAYER_CARDS];
		for(int i=0, j=0; i<ALL_CARDS; ++i)
			if(!usedCards[i])
				p2Cards[j++]=i;
		// 이긴 플레이어 점수 카운팅
		int[] winCaseCount = new int[2];
		do {
			++winCaseCount[getWinPlayerID(p1Cards, p2Cards)];
		} while(tryGetNextPerm(p2Cards));
		// 점수 출력
		return new StringBuilder()
					.append(winCaseCount[P1_ID])
					.append(' ')
					.append(winCaseCount[P2_ID])
					.toString();
	}

	static boolean tryGetNextPerm(int[] cards) {
		int seed = -1;
		for(int i=cards.length-1; i>0; --i) {
			if(cards[i-1] < cards[i]) {
				seed = i-1;
				break;
			}
		}
		if (seed==-1) return false;
		int toSwap = -1;
		for(int i=cards.length-1; i>seed; --i) {
			if(cards[seed] < cards[i]) {
				toSwap = i;
				break;
			}
		}
		if (toSwap==-1) return false;
		swap(cards, seed, toSwap);
		swapReverse(cards, seed+1, cards.length);
		return true;
	}

	static int getWinPlayerID(int[] p1Cards, int[] p2Cards) {
		int p1Score = 0;
		int p2Score = 0;
		for(int i=0; i<PER_PLAYER_CARDS; ++i) {
			int gameScore = p1Cards[i] + p2Cards[i] + 2;
			if(p1Cards[i]+1 > p2Cards[i]+1)
				p1Score += gameScore;
			else
				p2Score += gameScore;
		}
		// [-1,1] -> [0,2] -> [0,1]
		return (-Integer.compare(p1Score, p2Score)+1)/2;
	}

	static void swapReverse(int[] arr, int startInclusive, int endExclusive) {
		int len = endExclusive - startInclusive;
		for(int i=0; i<len/2; ++i)
			swap(arr, startInclusive+i, endExclusive-i-1);
	}
	
	static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
}
