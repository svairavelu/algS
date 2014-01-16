package com.me.problems.leetcode;

public class NextPermutation {

	public static void main(String[] args) {

	}

	public static void nextPermutation(int[] num) {
		int n = num.length;

		int i = n - 2;
		while (i >= 0 && num[i] >= num[i + 1]) {
			i -= 1;
		}

		if (i < 0) {
			for (int k = 0, l = n - 1; k < l; k++, l--) {
				int x = num[k];
				num[k] = num[l];
				num[l] = x;
			}
		} else {
			int j = n - 1;
			while (j > i && num[j] <= num[i]) {
				j -= 1;
			}

			int x = num[i];
			num[i] = num[j];
			num[j] = x;
			for (int k = i + 1, l = n - 1; k < l; k++, l--) {
				x = num[k];
				num[k] = num[l];
				num[l] = x;
			}
		}

	}
}
