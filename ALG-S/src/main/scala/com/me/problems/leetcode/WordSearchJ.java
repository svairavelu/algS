package com.me.problems.leetcode;

public class WordSearchJ {

	public static void main(String[] args) {
		char[][] board = new char[][] { "bb".toCharArray(), "ab".toCharArray(),
				"ba".toCharArray() };
		System.out.println(exist(board, "a"));
	}

	public static boolean exist(char[][] board, String word) {
		if (board == null || word == null) {
			return false;
		}

		int n = board.length;
		if (n == 0) {
			return false;
		}

		int m = board[0].length;
		if (m == 0) {
			return false;
		}

		boolean[][] checked = new boolean[n][m];
		char[] cs = word.toCharArray();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m;j++) {
				boolean flag = exist(board, checked, i, j, cs, 0);
				if (flag) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean exist(char[][] board, boolean[][] checked, int i,
			int j, char[] cs, int idx) {
		if (idx == cs.length) {
			return true;
		}

		if (board[i][j] != cs[idx]) {
			return false;
		}

		if (idx == cs.length - 1) {
			return true;
		}

		checked[i][j] = true;

		boolean flag = false;
		if (i > 0 && !checked[i - 1][j]) {
			flag = exist(board, checked, i - 1, j, cs, idx + 1);
		}

		if (!flag && j > 0 && !checked[i][j - 1]) {
			flag = exist(board, checked, i, j - 1, cs, idx + 1);
		}

		if (!flag && i < board.length - 1 && !checked[i + 1][j]) {
			flag = exist(board, checked, i + 1, j, cs, idx + 1);
		}

		if (!flag && j < board[i].length - 1 && !checked[i][j + 1]) {
			flag = exist(board, checked, i, j + 1, cs, idx + 1);
		}

		checked[i][j] = false;

		return flag;
	}
}
