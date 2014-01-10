package com.me.problems.leetcode;

import java.util.ArrayList;

public class TextJustification {

	public static void main(String[] args) {
		String[] words = { "a", "b", "c", "d", "e" };
		ArrayList<String> list = fullJustify(words, 3);
		for (String one : list) {
			System.out.println(one);
		}
	}

	public static ArrayList<String> fullJustify(String[] words, int L) {
		ArrayList<String> result = new ArrayList<String>();
		if (words == null) {
			return result;
		}

		justify(words, 0, L, result);

		return result;

	}

	public static void justify(String[] words, int i, int L,
			ArrayList<String> result) {
		int spaces = 0;
		int len = 0;
		int j = i;
		while (j < words.length && len + words[j].length() + spaces <= L) {
			len += words[j].length();
			spaces += 1;
			j += 1;
		}

		if (j == i) {
			j = i + 1;
		}

		if (j == words.length || j == i + 1) {
			// last line or only one word
			String s = words[i];
			for (int k = i + 1; k < j; k++) {
				s += " " + words[k];
			}
			spaces = L - s.length();
			for (int k = 0; k < spaces; k++) {
				s += " ";
			}
			result.add(s);
		} else {
//			if (len + spaces > L) {
//				len -= words[j].length();
//			}
			spaces = L - len;
			int m = (j - i - 1);
			int avg = spaces / m;
			int more = spaces % m;
			String s = words[i];
			for (int k = i + 1; k < j; k++) {
				for (int l = 0; l < avg; l++) {
					s += " ";
				}
				if (more > 0) {
					s += " ";
					more -= 1;
				}
				s += words[k];
			}
			result.add(s);
		}
		if (j < words.length) {
			justify(words, j, L, result);
		}
	}
}
