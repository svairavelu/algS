package com.me.problems.leetcode;

public class LongestPalindromicSubStringJ {

	public static void main(String[] args) {
		LongestPalindromicSubStringJ app = new LongestPalindromicSubStringJ();
		System.out.println(app.longestPalindrome_1("a"));
	}

	private String centerAt(char[] cs, int s, int e) {
		int i = s, j = e;
		while (i >= 0 && j < cs.length && cs[i] == cs[j]) {
			i -= 1;
			j += 1;
		}

		return String.copyValueOf(cs, i + 1, j - i - 1);
	}

	public String longestPalindrome_1(String s) {
		if (s == null) {
			return null;
		}
		char[] cs = s.toCharArray();
		String longest = String.copyValueOf(cs, 0, 1);
		for (int i = 0; i < cs.length - 1; i++) {
			String rs = centerAt(cs, i, i);
			if (rs.length() > longest.length()) {
				longest = rs;
			}

			rs = centerAt(cs, i, i + 1);
			if (rs.length() > longest.length()) {
				longest = rs;
			}
		}

		return longest;
	}

	/**
	 * if P[ i' ] ¡Ü R ¨C i, then P[ i ] ¡û P[ i' ] else P[ i ] ¡Ý P[ i' ]. (Which
	 * we have to expand past the right edge (R) to find P[ i ].
	 * 
	 * @param s
	 * @return
	 */
	public String longestPalindrome(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}

		char[] ss = s.toCharArray();
		int l = ss.length;
		char[] ts = new char[2 * l + 3];
		ts[0] = '^';
		for (int i = 0; i < l; i++) {
			ts[2 * i + 1] = '#';
			ts[2 * (i + 1)] = ss[i];
		}
		ts[2 * l + 1] = '#';
		ts[2 * l + 2] = '$';
		// 12 -> ^#1#2#$

		int n = ts.length;
		int[] p = new int[n];
		int center = 0, right = 0; // center and right bounder
		for (int i = 1; i < n - 1; i++) {
			int left = 2 * center - i;
			if (right > i) {
				p[i] = Math.min(right - i, p[left]);
			} else {
				p[i] = 0;
			}

			while (ts[i + 1 + p[i]] == ts[i - 1 - p[i]]) {
				p[i] += 1;
			}

			if (i + p[i] > right) {
				center = i;
				right = i + p[i];
			}
		}

		int maxLen = 0;
		int centerIndex = 0;
		for (int i = 1; i < n - 1; i++) {
			if (p[i] > maxLen) {
				maxLen = p[i];
				centerIndex = i;
			}
		}
		int startIdx = (centerIndex - 1 - maxLen) / 2;
		return s.substring(startIdx, startIdx + maxLen);
	}
}
