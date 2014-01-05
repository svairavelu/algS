/**
 * 
 */
package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Blues
 * 
 */
public class CombinationSumJ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CombinationSumJ app = new CombinationSumJ();
		ArrayList<ArrayList<Integer>> all = app.combinationSum(new int[] { 8,
				7, 4, 3 }, 11);
		for (ArrayList<Integer> one : all) {

			for (int x : one) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
	}

	public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates,
			int target) {
		Arrays.sort(candidates);
		ArrayList<ArrayList<Integer>> all = new ArrayList<ArrayList<Integer>>();
		combine(candidates, 0, target, new Stack<Integer>(), all);
		return all;
	}

	private void combine(int[] candidates, int start, int x, Stack<Integer> xs,
			ArrayList<ArrayList<Integer>> all) {
		if (x == 0) {
			// one path find
			// xs[n++] = x;

			ArrayList<Integer> rs = new ArrayList<Integer>();
			for (int y : xs) {
				rs.add(y);
			}
			all.add(rs);
			return;
		}
		// candiates is already sorted
		for (int i = start; i < candidates.length; i++) {
			int y = candidates[i];
			if (y > x) {
				break;
			}
			xs.push(y);
			combine(candidates, i, x - y, xs, all);
			xs.pop();
		}
	}

}
