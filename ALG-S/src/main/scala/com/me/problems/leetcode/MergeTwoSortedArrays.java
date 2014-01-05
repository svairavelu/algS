package com.me.problems.leetcode;

public class MergeTwoSortedArrays {

	public static void main(String[] args) {
		int[] A = new int[6];
		A[0] = 1;
		A[1] = 2;
		A[2] = 3;
		int[] B = new int[] { 2, 5, 6 };
		merge(A, 3, B, 3);
		for (int x : A) {
			System.out.println(x);
		}
	}

	public static void merge(int A[], int m, int B[], int n) {
		int i = m - 1;
		int j = n - 1;
		while(i >= 0 && j >= 0) {
			int k = i + j + 1;
			int a = A[i];
			int b = B[j];
			if(a < b) {
				A[k] = b;
				j -= 1;
			} else {
				A[k] = a;
				i -= 1;
			}
		}
		
		while(j >= 0) {
			A[j] = B[j];
			j -= 1;
		}
	}
}
