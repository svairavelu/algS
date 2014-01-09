package com.me.problems.leetcode;

public class SimplifyPath {

	public static void main(String[] args) {
		System.out.println(simplifyPath("/..hidden"));
	}

	public static String simplifyPath(String path) {
		if (path == null) {
			return null;
		}

		char[] cs = path.toCharArray();
		String[] stack = new String[cs.length];
		int top = -1;

		for (int i = 0; i < cs.length;) {
			int j = i;
			while (j < cs.length && cs[j] == '/') {
				j += 1;
			}

			if (j == cs.length) {
				break;
			}
			i = j;
			while (j < cs.length && cs[j] != '/') {
				j += 1;
			}

			if (j > i) {
				String x = String.copyValueOf(cs, i, j - i);
				if (x.equals("..")) {
					if (top >= 0) {
						top -= 1;
					}
				} else if (!x.equals(".")) {
					top += 1;
					stack[top] = x;
				}
			}

			i = j;
		}

		String rs = "/";
		for (int i = 0; i < top + 1; i++) {
			rs += stack[i];
			if (i < top) {
				rs += "/";
			}
		}
		return rs;

	}
}
