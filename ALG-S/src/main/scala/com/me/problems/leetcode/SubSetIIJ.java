/**
 * 
 */
package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Blues
 * 
 */
public class SubSetIIJ {
	static class P {
		final int s, e;

		public P(int s, int e) {
			super();
			this.s = s;
			this.e = e;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + e;
			result = prime * result + s;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			P other = (P) obj;
			if (e != other.e)
				return false;
			if (s != other.s)
				return false;
			return true;
		}
	}

	private Map<P, ArrayList<ArrayList<Integer>>> cache;
	public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
		this.cache = new HashMap<P, ArrayList<ArrayList<Integer>>>();
		return fx(num, new P(0, num.length));
	}

	private ArrayList<ArrayList<Integer>> fx(int[] num, P p) {
		if(cache.containsKey(p)) {
			return cache.get(p);
		}
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> empty = new ArrayList<Integer>();
		result.add(empty);
		Set<String> set = new HashSet<String>();
		set.add(listToString(empty));
		
		if(p.e == p.s) {
			cache.put(p, result);
			return result;
		}
		
		if(p.e == p.s + 1) {
			ArrayList<Integer> one = new ArrayList<Integer>();
			one.add(num[p.s]);
			result.add(one);
			cache.put(p, result);
			return result;
		}
		
		for(int i = p.s + 1; i < p.e; i++) {
			ArrayList<ArrayList<Integer>> left = fx(num, new P(p.s, i));
			ArrayList<ArrayList<Integer>> right = fx(num, new P(i, p.e));
			for(ArrayList<Integer> l : left) {
				for(ArrayList<Integer> r : right) {
					ArrayList<Integer> lr = new ArrayList<Integer>();
					lr.addAll(l);
					lr.addAll(r);
					Collections.sort(lr);
					String slr = listToString(lr);
					if(set.contains(slr)) {
						continue;
					}
					set.add(slr);
					result.add(lr);
				}
			}
		}
		
		cache.put(p, result);
		return result;
	}
	
	private String listToString(ArrayList<Integer> list) {
		String s = "";
		
		for(int x : list) {
			s = s + x;
		}
		
		return s;
	}
}
