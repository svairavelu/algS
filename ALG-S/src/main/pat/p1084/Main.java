package p1084;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String x = cin.nextLine();
		String y = cin.nextLine();
		String woreOut = process(x.toUpperCase().toCharArray(), y.toUpperCase()
				.toCharArray());
		System.out.println(woreOut);
	}

	private static String process(char[] xs, char[] ys) {
		boolean[] flag = new boolean[37];

		StringBuffer buf = new StringBuffer();
		int j = 0;
		for (int i = 0; i < xs.length; i++) {
			char x = xs[i];
			char y = '&';
			if (j < ys.length) {
				y = ys[j];
			}
			int k = index(x);
			if (flag[k]) {
			} else if (x == y) {
				j += 1;
			} else {
				flag[k] = true;
				buf.append(x);
			}
		}

		return buf.toString();
	}

	private static int index(char x) {
		if (x >= '0' && x <= '9') {
			return x - '0';
		} else if (x >= 'A' && x <= 'Z') {
			return x - 'A' + 10;
		}
		return 36;
	}

}
