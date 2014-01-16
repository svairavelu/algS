package com.me.problems.leetcode;

public class RemoveElement {

	public static void main(String[] args) {
		int[] xs = new int[] { 1, 2 };
		int n = removeDuplicates(xs);
		for (int i = 0; i < n; i++) {
			System.out.print(xs[i] + " ");
		}
	}

	public static int removeElement(int[] A, int elem) {
		int j = 0;
		for (int i = 0; i < A.length; i++) {
			int x = A[i];
			if (x == elem) {
				// just skip it.
			} else {
				A[j++] = A[i];
			}
		}
		return j;
	}

	public static int removeDuplicates(int[] A) {
		int len = 0;
		int i = 0;
		while (i < A.length) {
			int x = A[i];
			A[len++] = x;
			while (i < A.length && A[i] == x) {
				// just loop
				i += 1;
			}
		}

		return len;
	}
}
