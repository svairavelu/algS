package com.me.problems.leetcode;

public class MaximalRectangle {

	public static void main(String[] args) {
		MaximalRectangle app = new MaximalRectangle();

		char[][] matrix = new char[][] { new char[] { '1' } };
		System.out.println(app.maximalRectangle(matrix));
	}

	public int maximalRectangle(char[][] matrix) {
		if (matrix == null) {
			return 0;
		}

		int k = matrix.length;
		if (k == 0) {
			return 0;
		}

		int n = matrix[0].length;
		if (n == 0) {
			return 0;
		}

		int[] h = new int[n];
		int[] l = new int[n];
		int[] r = new int[n];
		for(int i = 0; i < n; i++) {
			r[i] = n;
		}
		int max = 0;
		for (int i = 0; i < k; i++) {
			int left = 0, right = n;
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == '1') {
					h[j] += 1;
					l[j] = Math.max(l[j], left);
				} else {
					left = j + 1;
					h[j] = 0;
					l[j] = 0;
					r[j] = n;
				}
			}
			for (int j = n - 1; j >= 0; j--) {
				if (matrix[i][j] == '1') {
					r[j] = Math.min(r[j], right);
					max = Math.max(max, h[j] * (r[j] - l[j]));
				} else {
					right = j;
				}
			}
		}
		return max;
	}
}
