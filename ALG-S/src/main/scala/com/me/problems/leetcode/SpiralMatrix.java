package com.me.problems.leetcode;

import java.util.ArrayList;

public class SpiralMatrix {
	
	public static void main(String[] args) {
		int[][] matrix = {
				{1, 2},
				{3, 4}
		};
		ArrayList<Integer> result = spiralOrder(matrix);
		for(int x : result) {
			System.out.print(x + " ");
		}
	}
	
	public static ArrayList<Integer> spiralOrder(int[][] matrix) {
		if(matrix == null) {
			return new ArrayList<Integer>();
		}
		int m = matrix.length;
		if(m == 0) {
			return new ArrayList<Integer>();
		}
		int n = matrix[0].length;
		if(n == 0) {
			return new ArrayList<Integer>();
		}
		boolean[][] visited = new boolean[m][n];
		
		Step step = new Right(0, 0, matrix, visited);
		int mn = m * n;
		ArrayList<Integer> result = new ArrayList<Integer>(mn);
		for(int i = 0; i < mn; i++) {
			result.add(step.valueAt());
			step = step.next();
		}
		return result;
	}
	
	static interface Step {
		Step next();
		int valueAt();
	}
	
	abstract static class BaseStep implements Step {
		final int i, j;
		final int[][] matrix;
		final boolean[][] visited;
		final int m, n;
		public BaseStep(int i, int j, int[][] matrix, boolean[][] visited) {
			super();
			this.i = i;
			this.j = j;
			this.matrix = matrix;
			this.m = matrix.length;
			this.n = matrix[0].length;
			this.visited = visited;
		}
		
		public int valueAt() {
			return matrix[i][j];
		}
	}
	
	static class Left extends BaseStep {

		public Left(int i, int j, int[][] matrix, boolean[][] visited) {
			super(i, j, matrix, visited);
		}

		@Override
		public Step next() {
			this.visited[i][j] = true;
			if(j == 0 || visited[i][j - 1]) {
				return new Up(i - 1, j, matrix, visited);
			} else {
				return new Left(i, j - 1, matrix, visited);
			}
		}
		
	}
	
	static class Down extends BaseStep {


		public Down(int i, int j, int[][] matrix, boolean[][] visited) {
			super(i, j, matrix, visited);
		}

		@Override
		public Step next() {
			this.visited[i][j] = true;
			if(i == m - 1 || visited[i + 1][j]) {
				return new Left(i, j - 1, matrix, visited);
			} else {
				return new Down(i + 1, j, matrix, visited);
			}
		}
	}
	
	static class Right extends BaseStep {

		public Right(int i, int j, int[][] matrix, boolean[][] visited) {
			super(i, j, matrix, visited);
		}

		@Override
		public Step next() {
			this.visited[i][j] = true;
			if(j == n - 1 || visited[i][j + 1]) {
				return new Down(i + 1, j, matrix, visited);
			} else {
				return new Right(i, j + 1, matrix, visited);
			}
		}
	}
	
	static class Up extends BaseStep {

		public Up(int i, int j, int[][] matrix, boolean[][] visited) {
			super(i, j, matrix, visited);
		}

		@Override
		public Step next() {
			this.visited[i][j] = true;
			if(i == 0 || visited[i - 1][j]) {
				return new Right(i, j + 1, matrix, visited);
			} else {
				return new Up(i - 1, j, matrix, visited);
			}
		}
		
	}

}
