package com.me.problems.leetcode;

public class PowXn {

	public static void main(String[] args) {
		System.out.println(pow(1.00000, -2147483648));
	}

	static double pow(double x, int n) {
		if (n == 0)
			return 1.0;
		// Compute x^{n/2} and store the result into a temporary
		// variable to avoid unnecessary computing
		double half = pow(x, n / 2);
		if (n % 2 == 0)
			return half * half;
		else if (n > 0)
			return half * half * x;
		else
			return half * half / x;
	}
}
