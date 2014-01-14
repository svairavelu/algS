/**
 * 
 */
package com.me.problems.leetcode;

import java.util.ArrayList;

import scala.actors.threadpool.Arrays;

/**
 * @author Blues
 * 
 */
public class NQueens {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 4;
		ArrayList<String[]> boards = solveNQueens(n);
		for (String[] board : boards) {
			for (String row : board) {
				System.out.println(row);
			}
			System.out.println();
		}

		System.out.println(totalNQueens(n));
	}

	public static ArrayList<String[]> solveNQueens(int n) {
		ArrayList<Character[]> boards = solveSubNQ(n, n);
		ArrayList<String[]> result = new ArrayList<String[]>();
		for (Character[] board : boards) {
			String[] rows = new String[n];
			for (int i = 0; i < n; i++) {
				String row = "";
				for (int j = 0; j < n; j++) {
					row += board[i * n + j];
				}
				rows[i] = row;
			}
			result.add(rows);
		}
		return result;
	}

	public static ArrayList<Character[]> solveSubNQ(int k, int n) {
		ArrayList<Character[]> boards = new ArrayList<Character[]>();
		if (k == 1) {
			// there are n ways to put Q on the first row
			for (int i = 0; i < n; i++) {
				Character[] board = new Character[n];
				Arrays.fill(board, '.');
				board[i] = 'Q';
				boards.add(board);
			}
			return boards;
		} else {
			// place the k'th row after the previous row
			ArrayList<Character[]> subBoards = solveSubNQ(k - 1, n);
			int start = (k - 1) * n;
			int end = k * n;
			// to place Q
			for (Character[] board : subBoards) {
				for (int i = start; i < end; i++) {
					if (notConfilict(i, board, n)) {
						Character[] nboard = (Character[]) Arrays.copyOf(board,
								end);
						for (int j = start; j < end; j++) {
							nboard[j] = '.';
						}
						nboard[i] = 'Q';
						boards.add(nboard);
					}
				}
			}
			return boards;
		}
	}

	private static boolean notConfilict(int p, Character[] board, int n) {
		int i = p / n;
		int j = p % n;

		for (int k = 0; k < i; k++) {
			if (board[k * n + j] == 'Q') {
				return false;
			}
		}

		int k = i - 1;
		int l = j - 1;
		while (k >= 0 && l >= 0) {
			if (board[k * n + l] == 'Q') {
				return false;
			}
			k = k - 1;
			l = l - 1;
		}

		k = i - 1;
		l = j + 1;
		while (k >= 0 && l < n) {
			if (board[k * n + l] == 'Q') {
				return false;
			}
			k = k - 1;
			l = l + 1;
		}

		return true;
	}

	public static int totalNQueens(int n) {
		if (n <= 0) {
			return 0;
		}

		int totalLimit = 0;
		for (int i = 0; i < n; i++) {
			totalLimit = totalLimit << 1 | 1;
		}

		int[] result = new int[1];
		search(0, 0, 0, totalLimit, result);
		return result[0];
	}

	private static void search(int row, int ld, int rd, int limit, int[] result) {
		if (row == limit) {
			result[0] += 1;
		} else {
			int pos = limit & ~(row | ld | rd);
			while (pos > 0) {
				int p = pos & (-pos);
				pos = pos - p;
				search(row + p, (ld + p) << 1, (rd + p) >> 1, limit, result);
			}
		}
	}
}
