package com.me.problems.leetcode;

import java.util.HashMap;
import java.util.Map;

public class DecodeWaysJ {

	public static void main(String[] args) {
		DecodeWaysJ app = new DecodeWaysJ();
		System.out
				.println(app
						.numDecodings("1168963884134125126536966946586868418993819971673459188478711546479621135253876271366851168524933185"));
		System.out
				.println(app
						.numDecodings2("1168963884134125126536966946586868418993819971673459188478711546479621135253876271366851168524933185"));
	}

	public int numDecodings2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		return fx(s, new HashMap<String, Integer>());
	}

	private int fx(String s, Map<String, Integer> cache) {
		if (cache.containsKey(s)) {
			return cache.get(s);
		}

		if (s.length() == 1) {
			cache.put(s, 1);
			return 1;
		}

		if (s.length() == 2) {
			int sn = Integer.valueOf(s);
			if (sn <= 26) {
				cache.put(s, 2);
				return 2;
			} else {
				cache.put(s, 1);
				return 1;
			}
		}

		int total = 0;
		for (int i = 1; i < s.length(); i++) {
			int lx = fx(s.substring(0, i), cache);
			int rx = fx(s.substring(i), cache);
			total += lx * rx;
		}
		cache.put(s, total);
		return total;
	}

	public int numDecodings(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] cs = s.toCharArray();
		int n = cs.length;

		int[] c = new int[n + 1];
		c[0] = 1;
		for (int i = 1; i <= n; i++) {
			int c1 = 0;
			if (cs[i - 1] != '0')
				c1 = c[i - 1];
			int c2 = 0;
			if (i >= 2
					&& (cs[i - 2] == '1' || cs[i - 2] == '2'
							&& cs[i - 1] <= '6'))
				c2 = c[i - 2];
			c[i] = c1 + c2;
		}

		return c[n];
	}
}
