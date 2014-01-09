package com.me.problems.leetcode;

public class EditDistance {

	public static void main(String[] args) {
		System.out.println(minDistance("a", "a"));
	}

	public static int minDistance(String word1, String word2) {
		if (word1 == null || word1.length() == 0) {
			return word2 == null ? 0 : word2.length();
		}

		if (word2 == null || word2.length() == 0) {
			return word1.length();
		}

		char[] ss = word1.toCharArray();
		char[] ts = word2.toCharArray();

		int[][] m = new int[ss.length + 1][ts.length + 1];

		for (int i = 0; i < m.length; i++) {
			m[i][0] = i;
		}

		for (int j = 0; j < m[0].length; j++) {
			m[0][j] = j;
		}

		for (int i = 1; i < m.length; i++) {
			for (int j = 1; j < m[i].length; j++) {
				int a = m[i - 1][j - 1];
				if (ss[i - 1] != ts[j - 1]) {
					a += 1;
				}

				int b = m[i - 1][j] + 1;
				int c = m[i][j - 1] + 1;
				m[i][j] = Math.min(a, Math.min(b, c));
			}
		}

		return m[ss.length][ts.length];
	}
}
