package com.me.problems.leetcode;

public class UniquePaths {

	public static void main(String[] args) {
		int[][] grid = { { 0 } };
		System.out.println(uniquePathsWithObstacles(grid));
	}

	public int uniquePaths(int m, int n) {
		int[][] fx = new int[m][n];

		for (int i = 0; i < m; i++) {
			fx[i][0] = 1;
		}

		for (int j = 0; j < n; j++) {
			fx[0][j] = 1;
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				fx[i][j] = fx[i - 1][j] + fx[i][j - 1];
			}
		}

		return fx[m - 1][n - 1];
	}

	public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;

		int[][] fx = new int[m][n];

		fx[0][0] = 1 - obstacleGrid[0][0];
		
		for (int i = 1; i < m; i++) {
			if (obstacleGrid[i][0] == 1) {
				fx[i][0] = 0;
			} else {
				fx[i][0] = fx[i - 1][0];
			}
		}

		for (int j = 1; j < n; j++) {
			if (obstacleGrid[0][j] == 1) {
				fx[0][j] = 0;
			} else {
				fx[0][j] = fx[0][j - 1];
			}
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (obstacleGrid[i][j] == 1) {
					fx[i][j] = 0;
				} else {
					fx[i][j] = fx[i - 1][j] + fx[i][j - 1];
				}
			}
		}

		return fx[m - 1][n - 1];

	}
}
