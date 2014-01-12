package com.me.problems.leetcode;

public class PermutationSequence {

	public static void main(String[] args) {
		System.out.println(getPermutation(4, 5));
	}

	public static String getPermutation(int n, int k) {
		// to store number for perm...
		int[] num = new int[n];
		// transfer kth perm. to k-1 th index, starting from 0.
		k = k - 1;
		String ret = "";
		// init nums.
		for (int i = 0; i < n; i++) {
			num[i] = i + 1;
		}
		// fact and integer is one to one mapping w/ formula, please google
		for (int i = n - 1; i >= 0; i--) {
			// the index of the subtree of the current level...
			int times = k / fac(i);
			// equivalent to c++ erase's function.
			for (int sc = 0; sc <= times; sc++)
				// choose -1 since permutation is positive..I guess? anyways
				// question is only from 1 to 9, choose a num you like
				if (num[sc] == -1)
					times++;
			// update the string value
			ret += num[times];
			// make the used num to -1 so next iteration this number will be
			// neglected
			num[times] = -1;
			// for next level iteration
			k = k % fac(i);
		}
		return ret;

	}

	// simple factorial recursive, simple, no space required, but need calculate
	// every time.
	public static int fac(int n) {
		if (n == 0)
			return 1;
		return n * fac(n - 1);
	}
}
