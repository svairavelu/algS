package pat.problems.p1044;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

	private static int rIdx = 0;

	public static void main(String[] args) throws IOException {
		int n = readInt();
		int m = readInt();

		int[] xs = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = readInt();
		}

		R[] rs = new R[n];
		check(xs, 1, m, rs);
		Arrays.sort(rs, 0, rIdx, new Comparator<R>() {

			@Override
			public int compare(R x, R y) {
				if (x.m > y.m) {
					return 1;
				} else if (x.m < y.m) {
					return -1;
				} else {
					if (x.start < y.start) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		});
		
		R r0 = rs[0];
		int m0 = r0.m;
		for(int i = 0; i < rIdx; i++) {
			if(rs[i].m > m0) {
				break;
			}
			System.out.println(rs[i]);
		}

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

	private static void check(int[] xs, int pos, int m, R[] rs) {
		if (pos > xs.length) {
			return;
		}

		int s = 0;
		for (int i = pos - 1; i >= 0; i--) {
			s += xs[i];
			if (s >= m) {
				rs[rIdx++] = new R(i, pos - 1, s);
				break;
			}
		}
//		s = 0;
//		for (int i = pos; i < xs.length; i++) {
//			s += xs[i];
//			if (s >= m) {
//				rs[rIdx++] = new R(pos, i, s);
//				break;
//			}
//		}

		check(xs, pos + 1, m, rs);
	}
}

class R {
	final int start, end;
	final int m;

	public R(int start, int end, int m) {
		super();
		this.start = start;
		this.end = end;
		this.m = m;
	}

	@Override
	public String toString() {
		return (start + 1) + "-" + (end + 1);
	}

}
