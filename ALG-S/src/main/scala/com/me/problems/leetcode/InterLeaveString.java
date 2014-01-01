package com.me.problems.leetcode;

public class InterLeaveString {

	public static void main(String[] args) {

	}

	public static boolean isInterleave(String s1, String s2, String s3) {
		if(s1 == null || s2 == null || s3 == null) {
			return false;
		}
		
		if(s1.length() + s2.length() != s3.length()) {
			return false;
		}
		
		char[] cs1 = s1.toCharArray();
		char[] cs2 = s2.toCharArray();
		
		char[] cs3 = s3.toCharArray();
		
		int n = cs1.length;
		int m = cs2.length;
		boolean[][] fx = new boolean[n + 1][m + 1];
		fx[0][0] = true;
		
		//it means the first chars of s1 match the first chars of s3
		for(int i = 1; i <= n; i++) {
			char c1 = cs1[i - 1];
			char c3 = cs3[i - 1];
			if(c1 == c3) {
				fx[i][0] = true;
			} else {
				break;
			}
		}
		
		for(int j = 1; j <= m; j++) {
			char c2 = cs2[j - 1];
			char c3 = cs3[j - 1];
			if(c2 == c3) {
				fx[0][j] = true;
			} else {
				break;
			}
		}
		
		for(int i = 1; i <= n; i++) {
			char c1 = cs1[i - 1];
			for(int j = 1; j <= m; j++) {
				char c2 = cs2[j - 1];
				char c3 = cs3[i + j - 1];
				if(c1 == c3) {
					fx[i][j] = fx[i - 1][j];
				}
				
				if(c2 == c3) {
					fx[i][j] = fx[i][j - 1] || fx[i][j];
				}
			}
		}
		
		return fx[n][m];
	}
}
