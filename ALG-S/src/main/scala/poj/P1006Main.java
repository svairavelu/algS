package poj;

import java.util.Scanner;

public class P1006Main {

	private static int D_MAX = 21252;
	private static int P = 23;
	private static int E = 28;
	private static int I = 33;

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);

		int no = 1;
		while (true) {
			int p = cin.nextInt();
			int e = cin.nextInt();
			int i = cin.nextInt();
			int d = cin.nextInt();
			if (p < 0 && e < 0 && i < 0 && d < 0) {
				break;
			}
			int ans = (p * 5544 + e * 14421 + i * 1288 - d + 21252) % 21252;
			if (ans == 0)
				ans = 21252;
			System.out.println(String
					.format("Case %d: the next triple peak occurs in %d days.",
							no, ans));
			no++;
		}
	}

	// private static int process(int p, int e, int i, int d) {
	// for (int x = 1; p + x * P <= D_MAX + d; x++) {
	// int X = p + x * P;
	// int y = calc(X, e, E, d);
	// if (y >= 0) {
	// int z = calc(X, i, I, d);
	// if (z >= 0) {
	// return X;
	// }
	// }
	// }
	// return D_MAX;
	// }
	//
	// private static int calc(int x, int s, int factor, int d) {
	// int left = 0;
	// int right = (D_MAX + d - s) / factor;
	//
	// while (left <= right) {
	// int mid = (left + right) / 2;
	// int r = s + mid * factor;
	// if (r == x) {
	// return mid;
	// } else if (r < x) {
	// left = mid + 1;
	// } else {
	// right = mid - 1;
	// }
	// }
	// return -1;
	// }

}
