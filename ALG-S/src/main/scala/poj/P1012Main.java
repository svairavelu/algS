package poj;

import java.util.Scanner;

public class P1012Main {
	private static int[] r = new int[14];

	public static void main(String[] args) {
		prepare();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			int k = scanner.nextInt();
			if (k == 0) {
				break;
			}
			System.out.println(r[k]);
		}
	}

	private static void prepare() {
		for (int k = 1; k < 14; k++) {
			for (int i = k + 1;; i += k + 1) {
				if (solve(k, i)) {
					r[k] = i;
					break;
				} else if (solve(k, i + 1)) {
					r[k] = i + 1;
					break;
				}
			}
		}
	}

	private static boolean solve(int k, int i) {
		int n = k * 2, m = i, x = 0;
		while (n > k) {
			x = (x + m - 1) % n;
			if (x < k)
				return false;
			n--;
		}
		return true;
	}

}
