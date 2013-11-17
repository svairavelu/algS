package pat.problems.p1054;

import java.io.IOException;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws IOException {
		int m = readInt();
		int n = readInt();
		int count = 0;
		int cap = 0;
		for(int i = 0; i < m * n; i++) {
			int c = readInt();
			if(count == 0) {
				cap = c;
			} 
			if(cap == c) {
				count += 1;
			} else {
				count -= 1;
			}
		}
		System.out.println(cap);
	}

	private static int readInt() throws IOException {
		InputStream in = System.in;
		int s = 0;
		int c = in.read();
		while(c != ' ' && c != '\r' && c != '\n') {
			s = s * 10 + (c - '0');
			c = in.read();
		}
		
		if(c == '\r') {
			in.read();
		}
		return s;
	}
}
