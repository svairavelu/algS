package poj;

import java.util.Scanner;

public class P1014Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int[] xs = new int[6];
		int index = 1;
		while (true) {
			int total = 0;
			for (int i = 0; i < 6; i++) {
				xs[i] = cin.nextInt();
				total += xs[i];
			}

			if (total == 0) {
				break;
			}
			System.out.println(String.format("Collection #%d:", index++));
			if (check(xs, total)) {
				System.out.println("Can be divided.");
			} else {
				System.out.println("Can't be divided.");
			}
			System.out.println();
		}
	}

	public static boolean check(int[] xs, int total) {
		int[] v = new int[total];
		int index = 0;
		int W = 0;
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] == 0) {
				continue;
			}
			for (int j = 0; j < xs[i]; j++) {
				v[index++] = i + 1;
				W += i + 1;
			}
		}

		if (W % 2 == 1) {
			return false;
		}

		W = W / 2;

		int[] dp = new int[W + 1];
		for (int i = 0; i < v.length; i++) {
			for (int j = W; j >= v[i]; j--) {
				dp[j] = max(dp[j], dp[j - v[i]] + v[i]);
			}
		}
		return dp[W] == W;
	}

	private static int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

}
