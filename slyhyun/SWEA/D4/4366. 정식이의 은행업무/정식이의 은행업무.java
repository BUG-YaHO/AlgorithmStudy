/*
 * [문제]
 * - 동일한 수를 각각 2진수와 3진수로 변환하고 한 자리씩 다르게 한다.
 * - 그렇게 해서 주어진 2진수와 3진수를 보고 원래의 수를 알아내면 된다.
 *
 * [입력]
 * - 첫 번째 줄에 한 자리만 틀린 2진수
 * - 두 번째 줄에 한 자리만 틀린 3진수
 * - 각 수의 자릿수: 3 ~ 40
 * 
 * [설계]
 * - 2진수를 한 자리씩 바꾸면서, 3진수도 한 자리씩 바꾼다.
 * - 그렇게 해서 두 수가 같은 10진수를 가리키면 그 값 출력
 */
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String binary = br.readLine();
            String ternary = br.readLine();

            HashSet<Long> numbers = new HashSet<>();

            char[] binArr = binary.toCharArray();

            for (int i = 0; i < binArr.length; i++) {
                char org = binArr[i];

                binArr[i] = (org == '0') ? '1' : '0';

                if (binArr[0] != '0') {
                    numbers.add(Long.parseLong(new String(binArr), 2));
                }

                binArr[i] = org;
            }

            char[] terArr = ternary.toCharArray();

            long ans = 0;
            boolean found = false;

            for (int i = 0; i < terArr.length && !found; i++) {
                char org= terArr[i];

                for (char j = '0'; j <= '2'; j++) {
                    if (j == org) continue;

                    terArr[i] = j;

                    if (terArr[0] != '0') {
                        long cur = Long.parseLong(new String(terArr), 3);

                        if (numbers.contains(cur)) {
                            ans = cur;
                            found = true;

                            break;
                        }
                    }
                }

                terArr[i] = org;
            }

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }

        System.out.print(sb);
    }
}
