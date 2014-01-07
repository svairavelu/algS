/**
 * 
 */
package com.me.problems.leetcode;

/**
 * @author Blues
 * 
 */
public class MinimumPathSumJ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public int minPathSum(int[][] grid) {
		if (grid == null) {
			return 0;
		}

		int n = grid.length;
		if (n == 0) {
			return 0;
		}

		int m = grid[0].length;
		if (m == 0) {
			return 0;
		}

		int[][] s = new int[n][m];

		s[0][0] = grid[0][0];

		for (int i = 1; i < n; i++) {
			s[i][0] = s[i - 1][0] + grid[i][0];
		}

		for (int j = 1; j < m; j++) {
			s[0][j] = s[0][j - 1] + grid[0][j];
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				s[i][j] = Math.min(s[i - 1][j], s[i][j - 1]) + grid[i][j];
			}
		}

		return s[n - 1][m - 1];
	}
}
