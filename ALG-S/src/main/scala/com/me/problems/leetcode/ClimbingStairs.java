package com.me.problems.leetcode;

public class ClimbingStairs {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int climbStairs(int n) {
		if(n <= 0) {
			return 0;
		}
		
		int[] fx = new int[n + 2];
		fx[0] = 0;
		fx[1] = 1;
		fx[2] = 2;
		
		for(int i = 3; i < n + 1; i++) {
			fx[i] = fx[i - 1] + fx[i - 2];
		}
		return fx[n];
	}
}
