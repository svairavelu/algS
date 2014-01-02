/**
 * 
 */
package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Blues
 * 
 */
public class GenerateParenthesis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> parenthesis = generateParenthesis(3);
		for(String p : parenthesis) {
			System.out.println(p);
		}
	}

	public static ArrayList<String> generateParenthesis(int n) {
		if(n == 0) {
			return new ArrayList<String>();
		}
		return f(n, new HashMap<Integer, ArrayList<String>>());
	}

	public static ArrayList<String> f(int n,
			Map<Integer, ArrayList<String>> cache) {
		if (cache.containsKey(n)) {
			return cache.get(n);
		}

		if (n == 1) {
			ArrayList<String> rs = new ArrayList<String>();
			rs.add("()");
			cache.put(1, rs);
			return rs;
		}

		Set<String> set = new HashSet<String>();
		ArrayList<String> ss = new ArrayList<String>();
		for (int i = 1; i < n; i++) {
			int j = n - i;
			if (j < i) {
				continue;
			}

			ArrayList<String> xs = f(i, cache);
			ArrayList<String> ys = f(j, cache);
			for (String x : xs) {
				for (String y : ys) {
					for (int k = 0; k <= y.length(); k++) {
						String xy = "";
						if (k == 0) {
							xy = x + y;
						} else if (k == y.length()) {
							xy = y + x;
						} else {
							xy = y.substring(0, k) + x + y.substring(k);
						}
						if (set.contains(xy)) {
							continue;
						}
						ss.add(xy);
						set.add(xy);
					}
				}
			}
		}
		cache.put(n, ss);
		return ss;
	}
}
