/**
 * 
 */
package com.me.problems.leetcode;

/**
 * @author Blues
 * 
 *         Follow up for "Remove Duplicates": What if duplicates are allowed at
 *         most twice?
 * 
 *         For example, Given sorted array A = [1,1,1,2,2,3],
 * 
 *         Your function should return length = 5, and A is now [1,1,2,2,3].
 * 
 *         Discuss
 */
public class RemoveDuplicatesFromSortedArrayJ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(removeDuplicates(new int[] { 1, 1, 1, 2, 2, 3 }));
	}

	public static int removeDuplicates(int[] A) {
		if (A == null) {
			return 0;
		}

		if (A.length < 3) {
			return A.length;
		}
		int n = A.length;
		int idx = 0;

		int count = 0; // from first
		for (int i = 0; i < n; i++) {
			int x = A[i];
			
			if(i == 0 || x != A[i - 1]) {
				A[idx++] = x;
				count = 1;
			} else {
				count += 1;
				if(count < 3) {
					A[idx++] = x;
				}
			}
		}

		return idx;
	}
}
