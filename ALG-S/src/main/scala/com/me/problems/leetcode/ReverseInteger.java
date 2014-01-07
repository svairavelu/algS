package com.me.problems.leetcode;

public class ReverseInteger {

	public static void main(String[] args) {
		ReverseInteger app = new ReverseInteger();
		System.out.println(app.reverse(-120));
	}

	public int reverse(int x) {
		boolean neg = false;

		if (x < 0) {
			neg = true;
			x = -x;
		}

		int y = 0;

		while (x > 0) {
			int left = x % 10;
			y = y * 10 + left;
			x = x / 10;
		}
		if (neg) {
			y = -y;
		}
		return y;
	}
}
