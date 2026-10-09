import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());
            long[] V = new long[n];
            
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                V[i] = Long.parseLong(st.nextToken());
            }
            
            if (n == 1) {
                sb.append("#").append(tc).append(" ").append(1).append("\n");
                continue;
            }
            
            long[] M = new long[n - 1];
            for (int i = 0; i < n - 1; i++) {
                M[i] = (V[i + 1] / V[i]) - 1;
            }
            
            Arrays.sort(M);
            
            int max = 1;
            for (int i = 0; i < n - 1; i++) {
                long limit = M[i] + 1;
                int required = i + 2;
                
                int k = 1;
                if (limit < required) {
                    long current = limit;
                    while (current < required) {
                        current *= limit;
                        k++;
                    }
                }
                
                if (k > max) {
                    max = k;
                }
            }
            
            sb.append("#").append(tc).append(" ").append(max).append("\n");
        }
        
        System.out.print(sb);
    }
}
