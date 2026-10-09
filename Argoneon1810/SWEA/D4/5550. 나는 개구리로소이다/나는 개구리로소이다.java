import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 요구된 것
 * 개구리 소리는 다음과 같을 수 있다
 * 한마리가 울 때
 * "croakcroak", "croak", "croakcroakcroakcroak"
 * 여러마리가 울 때
 * "crcoarkcoroakak"
 * 
 * 여러마리가 울 때를 보면
 * "crcoarkcoroakak"
 *  .. .. .. ....   = 한마리가 두번 움
 *    ^  ^  ^    ^^ = 두번째 마리가 한번 움
 * 와 같이 되어 두마리임을 알 수 있다.
 * 
 * 즉, 규칙:
 * - croak이라는 문자열을 구성하기 위해 다음으로 필요한 글자를
 *   현재 위치에서 가장 가까운 문자를 사용해 구성한다.
 *   = 그리디
 * 
 * 주어진 5글자가 전부 다르므로,
 * '다음으로 찾아야 하는 문자'는 상태머신으로 묶을 수 있다.
 * 
 * '선택'된 문자의 개수를 5로 나눈 횟수가 요청된 결과와 같다
 * 
 * 틀림
 * 이러면 '한마리가 몇 번 울었는가'를 센 것이고, 두번째 이후 마리가 운 것을 세지 않음
 * 첫째 테케가 맞은 것은 단순한 우연
 * 
 * 방문배열을 관리해서 이미 한 마리의 울음을 계산하는데 사용된 칸은 생략
 * 
 * 또 틀림
 * 개구리가 울다 만 부분이 있는 경우는 -1이어야 한다고 함. 미친
 * 
 * 또 틀림
 * 개구리가 울다 만 것의 판정을 옮겨줘야 함.
 */
public class Solution {
	static final char[] CROAK = { 'c', 'r', 'o', 'a', 'k' };

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
		char[] croak = br.readLine().toCharArray();
		boolean[] consumed = new boolean[croak.length];
		int frogsDistinguished = 0;
		while(true) {
			Tuple giggleState = distinguishSingle(croak, consumed);
			// 세는 도중에 끊겼다면 무효
			if(giggleState.b > 0) {
				frogsDistinguished = 0;
				break;
			}
			if (giggleState.a <= 0)
				// 남은 문자열로는 한마리의 울음을 완전히 재건할 수 없음
				break;
			++frogsDistinguished;
		}
		// 다 돌았는데 남는 글자가 있어도 무효
		for(int i=0; i<croak.length; ++i)
			if(!consumed[i])
				frogsDistinguished = 0;
		return Integer.toString(frogsDistinguished <= 0 ? -1 : frogsDistinguished);
	}

	static Tuple distinguishSingle(char[] croak, boolean[] consumed) {
		int count = 0;
		char toLook = CROAK[count%5];
		for(int i=0; i<croak.length; ++i) {
			if(consumed[i]) continue;
			if(croak[i] == toLook) {
				consumed[i] = true;
				++count;
				toLook = CROAK[count%5];
			}
		}
		return new Tuple(count/5, count%5);
	}

	static class Tuple {
		int a, b;
		Tuple(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}
}
