import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 정수 하나 (N=[2,10^12])가 주어진다
 * 이 정수 N을 2로 만들 수 있는 최소 연산 횟수를 구한다.
 * 연산 규칙
 * - N<-N+1
 * - N<-Math.sqrt(N) if N%1==0
 * 
 * 관찰 가능한 N의 크기가 1_000_000_000_000
 * float면 32비트, 4_000_000_000_000 byte (4TB)
 * 단순 메모이제이션으로는 불가
 * 
 * 가장 가까운 도달 가능한 어떤 수의 제곱인 수 까지 가야 함
 * - 근거:
 *   - 정수가 아닌 실수인 어떤 숫자를 다시 정수로 만들고자 할 때
 *     SQRT나 +1 연산은 목표 달성에 이를 수 없음
 *   - 즉 처음부터 어떤 숫자가 정수가 아닌 실수가 되어서는 안되므로,
 *     가장 가까운 어떤 수의 제곱수로 이동해야만 함
 * 
 * 현재 수를 sqrt한 수의 소수점 파트를 날리고 +1한 수의 제곱
 * = 가장 가까운 도달 가능한 제곱수
 * 
 * 재귀로 하면 스택 터질거 같으니까 루프로 구현
 */
class Solution {
	long closestSQRT(long v) {
		double rv = Math.sqrt(v);
		if (rv%1 > 0) {
			// 소수점이 있다면 소수점 버리고 1 더하기
			rv = ((long) rv) + 1;
		}
		return (long) rv;
	}
	int getLinearOpCnt(long d1, long d2) {
		return (int)(d2-d1);
	}

	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		long N = Long.parseLong(st.nextToken());
		int opCnt = 0;
		while(N!=2) {
			long csqrt = closestSQRT(N);
			// 자기 자신이 나왔다면 거리가 0. 아니라면 더할 횟수가 나옴
			// 거기까지 간 뒤 제곱근까지 취하므로 +1
			opCnt += getLinearOpCnt(N, csqrt*csqrt) + 1;
			N = csqrt;
		}
		return Integer.toString(opCnt);
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(br))
					.append('\n');
		System.out.print(sb);
	}

	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}
}
