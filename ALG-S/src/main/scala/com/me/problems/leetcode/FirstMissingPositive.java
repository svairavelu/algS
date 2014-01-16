package com.me.problems.leetcode;

public class FirstMissingPositive {

	public static void main(String[] args) {
		System.out.println(firstMissingPositive(new int[] { 3, 4, -1, 1 }));
	}

	public static int firstMissingPositive(int[] A) {
		int n = A.length;
		if (n <= 0)
			return 1;
		int intOutOfRange = n + 2;
		// first run, turn every negetive value into an impossible positive
		// value
		// make every value in A is positive
		for (int i = 0; i < n; ++i) {
			if (A[i] <= 0)
				A[i] = intOutOfRange;
		}
		// second run, make A[] as a hash table, A[i] indicate the presence of i
		// + 1
		// the way is that, if k in [1,n] is in A[], then turn A[k -1] to
		// negetive
		for (int i = 0; i < n; ++i) {
			int ai = A[i];
			int absi = Math.abs(ai);
			if (absi <= n)
				A[absi - 1] = -Math.abs(A[absi - 1]);
		}
		// third run, if A[i] is positive, from step 2, we know that i + 1 is
		// missing.
		for (int i = 0; i < n; ++i) {
			if (A[i] > 0)
				return i + 1;
		}
		// all int from 1 to n is present, then return n + 1
		return n + 1;
	}
}
