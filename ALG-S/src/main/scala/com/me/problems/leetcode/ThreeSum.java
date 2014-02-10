package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import scala.actors.threadpool.Arrays;

public class ThreeSum {

	public static int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		Map<Integer, Integer> cache = new HashMap<Integer, Integer>();

		for (int i = 0; i < nums.length; i++) {
			int x = nums[i];
			if (cache.containsKey(x)) {
				int j = cache.get(x);
				if (j < i) {
					result[0] = j + 1;
					result[1] = i + 1;
					break;
				}
			} else {
				cache.put(target - x, i);
			}
		}

		return result;
	}

	public static ArrayList<ArrayList<Integer>> threeSum(int[] num) {
		ArrayList<ArrayList<Integer>> resSet = new ArrayList<ArrayList<Integer>>();
		if (num.length < 3)
			return resSet;
		Arrays.sort(num);
		for (int i = 0; i < num.length - 2 && num[i] <= 0; ++i) {
			// remove duplicates
			if (i > 0 && num[i] == num[i - 1])
				continue;
			// use two pointer to find b+c = -a
			int start = i + 1, end = num.length - 1;
			while (start < end) {
				int sum = num[i] + num[start] + num[end];
				if (sum < 0) {
					start++;
				} else if (sum > 0) {
					end--;
				} else { // find one
					ArrayList<Integer> res = new ArrayList<Integer>(3);
					res.add(num[i]);
					res.add(num[start]);
					res.add(num[end]);
					resSet.add(res);
					// move the left pointer to right and skip duplicates;
					do {
						start++;
					} while (start < end && num[start] == num[start - 1]);
					// move the right pointer to left and skip duplicates
					do {
						end--;
					} while (start < end && num[end] == num[end + 1]);
				}
			}
		}
		return resSet;
	}

	public static int threeSumClosest(int[] num, int target) {
		if (num.length < 3) {
			return Integer.MIN_VALUE;
		}

		int closest = Integer.MAX_VALUE;
		Arrays.sort(num);
		for (int i = 0; i < num.length; i++) {
			if (i > 0 && num[i] == num[i - 1]) {
				continue;
			}
			int x = num[i];
			int k = i + 1;
			int j = num.length - 1;
			while (k < j) {
				int sum = x + num[k] + num[j];
				if (sum == target) {
					closest = target;
					return target;
				} else if (sum > target) {
					j--;
				} else {
					k++;
				}
				int diff = sum - target;
				if (closest == Integer.MAX_VALUE
						|| Math.abs(diff) < Math.abs(closest - target)) {
					closest = sum;
				}
			}
		}
		return closest;
	}

	public static void main(String[] args) {
		// ArrayList<ArrayList<Integer>> result = threeSum(new int[] { -4, -2,
		// -2,
		// -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6 });
		//
		// for (ArrayList<Integer> xs : result) {
		// for (int x : xs) {
		// System.out.print(x + ", ");
		// }
		// System.out.println();
		// }

		int answer = threeSumClosest(new int[] { -3, -2, -5, 3, -4 }, -1);
		System.out.println(answer);
	}
}
