package com.me.problems.leetcode;

public class MaximumProductSubarray {
	static int INT_MIN = Integer.MIN_VALUE;

	static int max(int a, int b) {
		if (a > b) {
			return a;
		}
		return b;
	}

	int helper(int A[], int l, int r) {
		if (l > r) {
			return INT_MIN;
		}

		if (l == r) {
			return A[l];
		}

		int max_prod = INT_MIN;
		int acc = 1;
		for (int i = l; i <= r; ++i) {
			if (A[i] == 0) {
				return max(helper(A, l, i - 1), max(0, helper(A, i + 1, r)));
			}
			acc *= A[i];
			max_prod = max(max_prod, acc);
		}

		// find first negtive number from left and divide out elements before it
		// (including itself).
		if (acc < 0) {
			for (int i = l; i <= r; ++i) {
				acc /= A[i];
				if (A[i] < 0) {
					break;
				}
			}
		}

		return max(max_prod, acc);
	}

	int maxProduct(int A[]) {
		return helper(A, 0, A.length - 1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
