package com.me.problems.leetcode;

import java.util.Arrays;

public class PlusOne {

	public static void main(String[] args) {

	}

	public int[] plusOne(int[] digits) {
		int n = digits.length;
		int[] result = new int[n + 1];
		
		int carray = 1;
		for(int i = n - 1; i >= 0; i--) {
			int x = digits[i] + carray;
			if(x > 9) {
				carray = 1;
				result[i + 1] = x - 10;
			} else {
				carray = 0;
				result[i + 1] = x;
			}
		}
		if(carray > 0) {
			result[0] = carray;
		} else {
			result = Arrays.copyOfRange(result, 1, n + 1);
		}
		return result;
	}
}
