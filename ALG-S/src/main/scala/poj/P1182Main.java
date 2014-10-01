package poj;

import java.util.Scanner;

public class P1182Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String[] firstLine = cin.nextLine().split("\\s+");
		int n = Integer.parseInt(firstLine[0]);
		UF uf = new UF(3 * n);
		int k = Integer.parseInt(firstLine[1]);
		int count = 0;
		for (int i = 0; i < k; i++) {
			String[] line = cin.nextLine().split("\\s+");
			int x = Integer.parseInt(line[0]);
			int a = Integer.parseInt(line[1]) - 1;
			int b = Integer.parseInt(line[2]) - 1;
			if (a < 0 || n <= a || b < 0 || n <= b) {
				count += 1;
				continue;
			}

			if (x == 1) {
				if (uf.sameSet(a, b + n) || uf.sameSet(a, b + 2 * n)) {
					count += 1;
				} else {
					uf.union(a, b);
					uf.union(a + n, b + n);
					uf.union(a + 2 * n, b + 2 * n);
				}
			} else {
				if (uf.sameSet(a, b) || uf.sameSet(a, b + 2 * n)) {
					count += 1;
				} else {
					uf.union(a, b + n);
					uf.union(a + n, b + 2 * n);
					uf.union(a + 2 * n, b);
				}
			}
		}
		System.out.println(count);
	}

	private static class UF {
		private final int capacity;
		private final int[] xs;

		public UF(int capacity) {
			this.capacity = capacity;
			this.xs = new int[capacity];
			for (int i = 0; i < capacity; i++) {
				xs[i] = i;
			}
		}

		public int find(int i) {
			if (xs[i] != i) {
				xs[i] = find(xs[i]);
			}
			return xs[i];
		}

		public boolean union(int i, int j) {
			int pi = find(i);
			int pj = find(j);
			xs[pj] = pi;
			return true;
		}

		public boolean sameSet(int i, int j) {
			int pi = find(i);
			int pj = find(j);
			return pi == pj;
		}
	}
}
