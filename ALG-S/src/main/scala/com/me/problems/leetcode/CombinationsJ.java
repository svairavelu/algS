/**
 * 
 */
package com.me.problems.leetcode;

import java.util.ArrayList;

/**
 * @author Blues
 * 
 */
public class CombinationsJ {

	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> list = combine(4, 2);
		for (ArrayList<Integer> xs : list) {
			for (int x : xs) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
	}

	public static ArrayList<ArrayList<Integer>> combine(int n, int k) {
		ArrayList<ArrayList<Integer>> all = new ArrayList<ArrayList<Integer>>();
		if (k <= 0) {
			return all;
		}
		int[] xs = new int[n];
		f(0, k, n, all, xs, 0, k);
		return all;
	}

	private static void f(int x, int k, int n,
			ArrayList<ArrayList<Integer>> all, int[] xs, int len, int t) {
		if (k == 0 && len == t) {
			ArrayList<Integer> nums = new ArrayList<Integer>();
			for (int i = 0; i < len; i++) {
				nums.add(xs[i]);
			}
			all.add(nums);
		} else if (k == 0) {
			return;// a invalid path
		} else {
			for (int i = x + 1; i <= n; i++) {
				xs[len] = i;
				f(i, k - 1, n, all, xs, len + 1, t);
			}
		}
	}
}
