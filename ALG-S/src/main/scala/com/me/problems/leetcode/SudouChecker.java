package com.me.problems.leetcode;

public class SudouChecker {

	public static void main(String[] args) {
		String[] strs = { 
				"........2", 
				"......6..", 
				"..14..8..", 
				".........",
				".........", 
				"....3....", 
				"5.86.....", 
				".9....4..", 
				"....5...." };
		int n = strs.length;
		char[][] board = new char[n][n];
		for (int i = 0; i < n; i++) {
			board[i] = strs[i].toCharArray();
		}

		System.out.println(isValidSudoku(board));

	}

	public static boolean isValidSudoku(char[][] board) {
		int n = board.length;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				char c = board[i][j];
				if (c == '.') {
					continue;
				}
				for (int k = 0; k < n; k++) {
					char x = board[i][k];
					if (j != k && x != '.' && x == c) {
						return false;
					}

					// row check
					// column check
					char y = board[k][j];
					if (i != k && y != '.' && y == c) {
						return false;
					}
				}

				final int ks = 3 * (i / 3);
				final int ls = 3 * (j / 3);

				for (int k = ks; k < ks + 3; k++) {
					for (int l = ls; l < ls + 3; l++) {
						char x = board[k][l];
						if (i != k && j != l && x != '.' && c == x) {
							return false;
						}
					}
				}

			}
		}

		return true;
	}
}
