package com.me.problems.leetcode;

public class JumpGame {

	public static void main(String[] args) {

	}

	public boolean canJump(int[] A) {
		
		int current = 0;
		for(int i = 0; i < A.length; i++) {
			if(i < A.length - 1 && current == i && A[i] == 0) {
				return false;
			}
			current = Math.max(current, i + A[i]);
		}
		
		return true;
	}
}
