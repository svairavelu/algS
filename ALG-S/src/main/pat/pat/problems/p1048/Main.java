package pat.problems.p1048;

import java.io.IOException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		int n = readInt();
		int m = readInt();
		
		int[] xs = new int[n];
		for(int i = 0; i < n; i++){
			xs[i] = readInt();
		}
		
		Arrays.sort(xs);
		int v1 = 0, v2 = 0;
		for(int i = 0, j = n - 1; i < j;) {
			int s = xs[i] + xs[j];
			if(s == m) {
				v1 = xs[i];
				v2 = xs[j];
				break;
			} else if(s > m) {
				j--;
			} else {
				i++;
			}
		}
		
		if(v1 > 0) {
			System.out.println(v1 + " " + v2);
		} else {
			System.out.println("No Solution");
			
		}
	}

	private static int readInt() throws IOException {
		int s = 0;
		int c = System.in.read();
		while(c != ' ' && c != '\r' && c != '\n') {
			s = s * 10 + (c - '0');
			c = System.in.read();
		}
		if(c == '\r') {
			System.in.read();
		}
		return s;
	}
}
