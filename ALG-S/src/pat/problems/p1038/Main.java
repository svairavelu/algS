package pat.problems.p1038;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		int n = readN();
		X[] xs = new X[n];
		for(int i = 0; i < n; i++) {
			xs[i] = readX();
		}
		
		Arrays.sort(xs);
		boolean leadingZero = true;
		for(int i = 0; i < n; i++) {
			int[] is = xs[i].xs;
			for(int j = 0; j < is.length; j++) {
				if(is[j] > 0) {
					leadingZero = false;
				}
				if(!leadingZero) {
					System.out.print(is[j]);
				}
			}
		}
	}

	private static X readX() throws IOException {
		int[] is = new int[8];
		int idx = 0;
		int i = readInt();
		while(i != -1) {
			is[idx++] = i;
			i = readInt();
		}
		return new X(Arrays.copyOf(is, idx));
	}
	
	private static int readN() throws IOException {
		int n = 0;
		int x = readInt();
		while(x != -1) {
			n = n * 10 + x;
			x = readInt();
		}
		return n;
	}
	
	private static int readInt() throws IOException {
		InputStream in = System.in;
		int c = in.read();
		if(c >= '0' && c <= '9') {
			return c - '0';
		} else {
			return -1;
		}
	}
}

class X implements Comparable<X>{
	final int[] xs;
	
	public X(int[] xs) {
		this.xs = xs;
	}

	@Override
	public int compareTo(X o) {
		int l1 = xs.length;
		int l2 = o.xs.length;
		
		for(int i = 0; i < l1 && i < l2; i++) {
			if(xs[i] < o.xs[i]) {
				return -1;
			} else if(xs[i] > o.xs[i]) {
				return 1;
			}
		}
		
		if(l2 > l1) {
			for(int i = l1; i < l2; i++) {
				int j = (i - l1) % l1;
				if(o.xs[i] > xs[j]) {
					return -1;
				} else if(o.xs[i] < xs[j]) {
					return 1;
				}
			}
		} else {
			for(int i = l2; i < l1; i++) {
				int j = (i - l2) % l2;
				if(xs[i] > o.xs[j]) {
					return 1;
				} else if(xs[i] < o.xs[j]) {
					return -1;
				}
			}
		}
		
		return 0;
	}
}
