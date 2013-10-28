package pat.problems.p1045;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		int n =readInt();  
		int[] xs = new int[n + 1];
		int m = readInt();
		for(int i = 0; i < m; i++) {
			int x = readInt();
			xs[x] = i + 1;
		}
		
		int k = readInt();
		int[] cs = new int[k + 1];
		int idx = 0;
		for(int i = 0; i < k; i++) {
			int x = readInt();
			if(xs[x] != 0) {
				cs[idx++] = x;
			}
		}
		
		int len = check(xs, cs, idx);
		System.out.println(len);
	}

	private static int check(int[] xs, int[] cs, int csSize) {
		
		int[] dp = new int[csSize];
		for(int i = 0;  i < csSize; i++) {
			int x = cs[i];
			int subMax = 0;
			for(int j = 0; j < i; j++) {
				int y = cs[j];
				if(xs[y] <= xs[x]) {
					//fit
					if(dp[j] > subMax) {
						subMax = dp[j];
					}
				}
			}
			dp[i] = subMax + 1;
		}
		
		return dp[csSize - 1];
	}

	
	private static int readInt() throws IOException {
		int s = 0;
		int c = System.in.read();
		while (c != ' ' && c != '\r' && c != '\n') {
			s = s * 10 + (c - '0');
			c = System.in.read();
		}
		if(c == '\r') {
			System.in.read();
		}
		return s;
	}
}
