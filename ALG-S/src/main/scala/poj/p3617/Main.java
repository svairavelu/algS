package poj.p3617;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = Integer.parseInt(cin.nextLine());
		char[] xs = new char[n];
		for (int i = 0; i < n; i++) {
			xs[i] = cin.nextLine().charAt(0);
		}

		int i = 0, j = n - 1;
		StringBuffer buf = new StringBuffer();
		while (i <= j) {
			int k = i;
			int l = j;
			while (k <= l && xs[k] == xs[l]) {
				k += 1;
				l -= 1;
			}
			if (k > l || xs[k] < xs[l]) {
				buf.append(xs[i]);
				i += 1;
			} else {
				buf.append(xs[j]);
				j -= 1;
			}
		}
		String cows = buf.toString();
		while (cows.length() > 80) {
			System.out.println(cows.substring(0, 80));
			cows = cows.substring(80);
		}
		if (cows.length() > 0) {
			System.out.println(cows);
		}
	}

}
