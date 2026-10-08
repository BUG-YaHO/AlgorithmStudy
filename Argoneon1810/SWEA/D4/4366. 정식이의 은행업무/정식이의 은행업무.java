import java.io.*;
import java.util.*;

/**
 * 숫자가 2진수 및 3진수로 주어진다.
 * 딱 한 비트가 틀릴 수 있으며, 나머지는 모두 동일하다.
 * 
 * 각각에서 하나씩 틀린다고 했으므로,
 * 2진수에서 틀린 비트와 3진수에서 틀린 비트는 서로 독립이다.
 * 
 * 최대 40자리 2진수와 3진수가 주어진다 하였으나
 * 3진수 40자리는 2진수 40자리에 표현 불가능하니 2진수 40자리까지이다.
 * 
 * 최대 2진수 40자리라면 long으로 표현해야 한다.
 * 
 * 한자리씩 처리하는 과정을 편하게 하고 싶으므로, 받은 숫자를 char[]으로 둔다.
 * 
 * 부호를 한개씩 바꿔 가면서 모든 경우의 수를 set에 밀어넣고
 * set의 교집합을 취하는 게 브루트포스로 가장 빠른 방법 같다.
 * 
 * 바이너리는 0을 1로 1을 0으로 뒤집기만 하면 되므로,
 * 한비트식 순회하면서 그 값을 반대로 뒤집고 셋에 넣는다.
 * 
 * 터너리는 시작값이 아닌 두개 값을 시도해야 하므로 nextTernary로 구현
 * 
 * -> 생각해보니 nextTernary랑 flipBinary가 일반화 가능함
 * 
 * -> 생각해보니 parse도 일반화 가능함
 * 
 * -> 그러면 allPossible도 일반화 가능함
 * 
 * retainAll을 하면 boolean을 반환받을 수 있으니까
 * 겹치는게 없는지 확인할 수 있긴 한데
 * 지문에 의해 항상 겹치는 쌍이 있다고 보장 가능한 것 같으니 무시
 * 보존 안해도 되니까 클론도 안함
 * 
 * 1. 입력이 들어온 순서랑 반대로 뒤집어야
 *    루프 순서대로 낮은 비트를 읽을 수 있어서
 *    뒤집는 연산 추가
 * 2. 일반화된 비트 파싱에 문제가 있어 수정
 * 3. power를 그때그때 새로 계산하지 않고 누적함
 */
public class Solution {
	static final Long[] EMPTY = new Long[0];

	static char next(char b, int base) {
		return (char)((((b-'0')+1)%base)+'0');
	}
	static long parse(char[] bArr, int base) {
		long bits = 0L;
		long pwr = 1;
		for(int i=0; i<bArr.length; ++i, pwr*=base)
			bits += (bArr[i]-'0')*pwr;
		return bits;
	}
	static void allPosible(Set<Long> s, char[] bArr, int base) {
		for(int i=0; i<bArr.length; ++i) {
			bArr[i] = next(bArr[i], base);
			for(int j=0; j<base-1; ++j) {
				long parsed = parse(bArr, base);
				s.add(parsed);
				bArr[i] = next(bArr[i], base);
			}
		}
	}

	static void swap(char[] arr, int a, int b) {
		char temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	static void swapInverse(char[] arr) {
		int n = arr.length;
		for(int i=0; i<n/2; ++i)
			swap(arr, i, n-i-1);
	}

	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		char[] binary = st.nextToken().toCharArray();
		swapInverse(binary);
		st = new StringTokenizer(br.readLine());
		char[] ternary = st.nextToken().toCharArray();
		swapInverse(ternary);
		Set<Long> binarySet = new HashSet<>();
		Set<Long> ternarySet = new HashSet<>();
		allPosible(binarySet, binary, 2);
		allPosible(ternarySet, ternary, 3);
		binarySet.retainAll(ternarySet);
		Long[] res = binarySet.toArray(EMPTY);
		return Long.toString(res[0]);
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
