package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WordBreak {

	// public ArrayList<String> wordBreak(String s, Set<String> dict) {
	// @SuppressWarnings("unchecked")
	// List<Integer>[] stopsForEachIdx = new List[s.length()];
	//
	// for (int j = s.length(); j >= 0; j--) {
	// for (int i = j - 1; i >= 0; i--) {
	// String sub = s.substring(i, j);
	// if (dict.contains(sub)) {
	// List<Integer> stops = stopsForEachIdx[i];
	// if (stops == null) {
	// stops = new ArrayList<Integer>();
	// stopsForEachIdx[i] = stops;
	// }
	// stops.add(j);
	// }
	// }
	// }
	//
	// ArrayList<String> paths = new ArrayList<String>();
	// collect(s, 0, "", paths, stopsForEachIdx);
	// return paths;
	// }
	//
	// private void collect(String s, int idx, String path, List<String> paths,
	// List<Integer>[] stopsForIdx) {
	// List<Integer> stops = stopsForIdx[idx];
	// if (stops == null || stops.size() == 0) {
	// return;
	// }
	// for (int stop : stops) {
	// String sub = s.substring(idx, stop);
	// if (idx == 0) {
	// path = sub;
	// } else {
	// path = path + " " + sub;
	// }
	// if(stop < s.length()) {
	// collect(s, stop, path, paths, stopsForIdx);
	// } else {
	// paths.add(path);
	// }
	// }
	// }
	public ArrayList<String> wordBreak(String s, Set<String> dict) {
		// Note: The Solution object is instantiated only once and is reused by
		// each test case.
		ArrayList<String> result = new ArrayList<String>();
		LinkedList<String> segment = new LinkedList<String>();
		boolean[][] table = new boolean[s.length()][s.length()];
		wordBreak(table, s, dict);// this is initial the table
		if (!table[0][s.length() - 1]) {
			return result;
		}
		recurseAdd(0, s, result, segment, table, dict);
		return result;
	}

	public void recurseAdd(int start, String input, ArrayList<String> result,
			LinkedList<String> forNow, boolean table[][], Set<String> dict) {
		if (start == input.length()) {
			String newOne = "";
			for (int i = forNow.size() - 1; i >= 0; i--) {
				newOne += forNow.get(i) + " ";
			}
			result.add(newOne.trim());
			return;
		}
		for (int i = 1 + start; i <= input.length(); i++) {
			if (dict.contains(input.substring(start, i))) {// valid
				if (i < input.length()) {
					if (table[i][input.length() - 1]) {
						forNow.push(input.substring(start, i)); // push shi
																// segment into
																// stack;
						recurseAdd(i, input, result, forNow, table, dict);
						forNow.pop();
					}
				} else {
					forNow.push(input.substring(start, i)); // push shi segment
															// into stack;
					recurseAdd(i, input, result, forNow, table, dict);
					forNow.pop();
				}

			}
		}

	}

	public void wordBreak(boolean[][] dp, String s, Set<String> dict) { // fill
																		// the
																		// table
																		// to
																		// judge
																		// any
																		// substring
																		// if it
																		// could
																		// be
																		// broke
																		// or
																		// not
		// Note: The Solution object is instantiated only once and is reused by
		// each test case.
		for (int i = 0; i < s.length(); i++)
			dp[i][i] = dict.contains(s.substring(i, i + 1));
		for (int i = 1; i < s.length(); i++) {
			for (int j = 0; j < s.length() - i; j++) {
				// boolean[j][j+i]
				if (dict.contains(s.substring(j, j + i + 1)))
					dp[j][j + i] = true;
				else {
					for (int k = j; k < j + i; k++) {
						if (dp[j][k] && dp[k + 1][j + i]) {
							dp[j][j + i] = true;
							break;
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		WordBreak app = new WordBreak();
		//
		String[] words = new String[] { "a", "aa", "aaa", "aaaa", "aaaaa",
				"aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa" };
		// String[] words = new String[]{"cat", "cats", "and", "sand", "dog"};
		Set<String> dict = new HashSet<String>();
		for (String word : words) {
			dict.add(word);
		}
		List<String> result = app
				.wordBreak(
						"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
						dict);
		// List<String> result = app.wordBreak("catsanddog", dict);
		for (String s : result) {
			System.out.println(s);
		}
	}
}
