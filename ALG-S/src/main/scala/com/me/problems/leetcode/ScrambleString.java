package com.me.problems.leetcode;

/**
 * 
 * Given a string s1, we may represent it as a binary tree by partitioning it to
 * two non-empty substrings recursively.
 * 
 * Below is one possible representation of s1 = "great":
 * 
 * great / \ gr eat / \ / \ g r e at / \ a t To scramble the string, we may
 * choose any non-leaf node and swap its two children.
 * 
 * For example, if we choose the node "gr" and swap its two children, it
 * produces a scrambled string "rgeat".
 * 
 * rgeat / \ rg eat / \ / \ r g e at / \ a t We say that "rgeat" is a scrambled
 * string of "great".
 * 
 * Similarly, if we continue to swap the children of nodes "eat" and "at", it
 * produces a scrambled string "rgtae".
 * 
 * rgtae / \ rg tae / \ / \ r g ta e / \ t a We say that "rgtae" is a scrambled
 * string of "great".
 * 
 * Given two strings s1 and s2 of the same length, determine if s2 is a
 * scrambled string of s1.
 * 
 * @author Blues
 * 
 */
public class ScrambleString {

	public static void main(String[] args) {

	}

	public boolean isScramble(String s1, String s2) {
		if (s1.equals(s2))
			return true;
		boolean[][][] scrambled = new boolean[s1.length()][s2.length()][s1
				.length() + 1];
		for (int i = 0; i < s1.length(); i++)
			for (int j = 0; j < s2.length(); j++) {
				scrambled[i][j][0] = true;
				scrambled[i][j][1] = s1.charAt(i) == s2.charAt(j);
			}

		for (int i = s1.length() - 1; i >= 0; i--)
			for (int j = s2.length() - 1; j >= 0; j--)
				for (int n = 2; n <= Math.min(s1.length() - i, s2.length() - j); n++)
					for (int m = 1; m < n; m++) {
						scrambled[i][j][n] |= scrambled[i][j][m]
								&& scrambled[i + m][j + m][n - m]
								|| scrambled[i][j + n - m][m]
								&& scrambled[i + m][j][n - m];
						if (scrambled[i][j][n])
							break;
					}
		return scrambled[0][0][s1.length()];
	}

}
