/**
 * 
 */
package p1085;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author senyuan.wsy
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String[] xs = scanner.nextLine().split("\\s+");
		int n = Integer.parseInt(xs[0]);
		long p = Long.parseLong(xs[1]);
		// scanner.nextLine();
		xs = scanner.nextLine().split("\\s+");
		int[] ys = new int[n];
		for (int i = 0; i < n; i++) {
			ys[i] = Integer.parseInt(xs[i]);
		}
		Arrays.sort(ys);
		int m = find(ys, p);
		System.out.println(m);
	}

	private static int find(int[] xs, long p) {

		int max = 0;
		for (int i = 0; i < xs.length; i++) {
			int m = xs[i];
			int j = find(xs, m * p, i, xs.length - 1);
			if (j - m > max) {
				max = j - i;
			}
		}

		return max + 1;
	}

	private static int find(int[] xs, long p, int i, int j) {
		while (i <= j) {
			int k = (i + j) / 2;
			int x = xs[k];
			if (x <= p) {
				i = k + 1;
			} else if (x > p) {
				j = k - 1;
			}
		}
		if (i < xs.length && xs[i] <= p) {
			return i;
		} else {
			return j;
		}
	}
}
