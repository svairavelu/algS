package poj;

import java.util.Scanner;

public class P2431Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] as = new int[n + 1];
		int[] bs = new int[n + 1];
		for (int i = 0; i < n; i++) {
			as[n - 1 - i] = scanner.nextInt();
			bs[n - 1 - i] = scanner.nextInt();
		}
		as[n] = 0;
		bs[n] = 0;

		int l = scanner.nextInt();
		int p = scanner.nextInt();

		int ans = drive(n + 1, as, bs, l, p);
		System.out.println(ans);
	}

	private static int drive(int n, int[] as, int[] bs, int l, int p) {
		PQ queue = new PQ(n);

		int ans = 0, pos = l;
		for (int i = 0; i < n; i++) {
			int d = pos - as[i];

			while (p < d) {
				if (queue.isEmpty()) {
					return -1;
				}
				p += queue.pop();
				ans += 1;
			}

			p -= d;
			queue.push(bs[i]);
			pos = as[i];
		}
		return ans;
	}

	static class PQ {
		final int capacity;
		private final int xs[];
		private int index = 0;

		public PQ(int capacity) {
			this.capacity = capacity;
			this.xs = new int[this.capacity + 1];
		}

		public void push(int x) {
			index += 1;
			int i = index;
			while (i > 1) {
				int p = i / 2;
				if (xs[p] >= x) {
					break;
				}
				xs[i] = xs[p];
				i = p;
			}
			xs[i] = x;
		}

		public int pop() {
			int x = xs[1];

			int y = xs[index--];
			int i = 1;

			while (2 * i <= index) {
				int c = 2 * i;
				if (c + 1 <= index && xs[c] < xs[c + 1]) {
					c = c + 1;
				}
				if (y >= xs[c]) {
					break;
				}
				xs[i] = xs[c];
				i = c;
			}

			xs[i] = y;

			return x;
		}

		public boolean isEmpty() {
			return index == 0;
		}

		public String toString() {
			StringBuffer buf = new StringBuffer("[");
			String sep = "";
			for (int i = 1; i <= index; i++) {
				buf.append(sep);
				sep = ",";
				buf.append(xs[i]);
			}
			buf.append("]");
			return buf.toString();
		}
	}

}
