package poj;

import java.util.Arrays;
import java.util.Scanner;

public class P3069Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int r = cin.nextInt();
		int n = cin.nextInt();
		while (r >= 0 && n > 0) {
			System.out.println(process(r, n, cin));
			r = cin.nextInt();
			n = cin.nextInt();
		}
	}

	public static int process(int r, int n, Scanner cin) {
		int[] xs = new int[n];
		for (int i = 0; i < n; i++) {
			xs[i] = cin.nextInt();
		}

		Arrays.sort(xs);
		
		int i = 0, ans = 0;
		while (i < n) {
			int s = xs[i++];
			while (i < n && s + r >= xs[i]) {
				i++;
			}
			int p = xs[i - 1];
			while (i < n && p + r >= xs[i]) {
				i++;
			}
			ans++;
		}
		return ans;
	}

}
