package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scala.actors.threadpool.Arrays;

public class Anagrams {

	public static void main(String[] args) {
		ArrayList<String> result = anagrams(new String[]{"ape","and","cat"});
		for(String s : result) {
			System.out.print(s + " ");
		}
	}

	public static ArrayList<String> anagrams(String[] strs) {
		if(strs == null || strs.length < 2) {
			return new ArrayList<String>();
		}
		Map<Integer, List<Integer>> cache = new HashMap<Integer, List<Integer>>();

		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			int mark = mark(s);
			List<Integer> indexes = null;
			if(cache.containsKey(mark)) {
				indexes = cache.get(mark);
			} else {
				indexes = new ArrayList<Integer>();
			}
			indexes.add(i);
			cache.put(mark, indexes);
		}
		
		ArrayList<String> result = new ArrayList<String>();
		for(List<Integer> indexes : cache.values()) {
			if(indexes.size() > 1) {
				for(int i : indexes) {
					result.add(strs[i]);
				}
			}
		}
		return result;
	}

	private static int mark(String s) {
		char[] cs = s.toCharArray();
		Arrays.sort(cs);
		int result = 0;
		for (char c : cs) {
			result = result * 27 + (c - 'a' + 1);
		}

		return result;
	}
}
