package com.me.problems.leetcode;

public class SpiralMatrixII {

	public static void main(String[] args) {
		int[][] matrix = generateMatrix(3);
		for(int[] row : matrix) {
			for(int x : row) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
	}

	public static int[][] generateMatrix(final int n) {
		final int[][] matrix = new int[n][n];

		Step step = new Left(0, 0, matrix);
		int x = 1;
		int n2 = n * n;
		while(x <= n2) {
			step.setValue(x);
			step = step.next();
			x += 1;
		}
		return matrix;
	}

	static interface Step {
		Step next();
		void setValue(int x);
	}
	
	abstract static class BaseStep implements Step {
		final int i, j;
		final int[][] matrix;
		public BaseStep(int i, int j, int[][] matrix) {
			super();
			this.i = i;
			this.j = j;
			this.matrix = matrix;
		}
		
		public final void setValue(int x) {
			matrix[i][j] = x;
		}
	}
	static class Left extends BaseStep {
		public Left(int i, int j, int[][] matrix) {
			super(i, j, matrix);
		}

		@Override
		public Step next() {
			if(j == matrix[i].length - 1 || matrix[i][j + 1] != 0) {
				return new Down(i + 1, j, this.matrix);
			} else {
				return new Left(i, j + 1, this.matrix);
			}
		}
	}

	static class Down extends BaseStep {

		public Down(int i, int j, int[][] matrix) {
			super(i, j, matrix);
		}

		public Step next() {
			if(i == matrix.length - 1 || matrix[i + 1][j] > 0) {
				return new Right(i, j - 1, matrix);
			} else {
				return new Down(i + 1, j, matrix);
			}
		}
	}

	static class Right extends BaseStep  {
		public Right(int i, int j, int[][] matrix) {
			super(i, j, matrix);
		}

		public Step next() {
			if(j == 0 || matrix[i][j - 1] > 0) {
				return new Up(i - 1, j, matrix);
			} else {
				return new Right(i, j - 1, matrix);
			}
			
		}
	}
	
	static class Up extends BaseStep {

		public Up(int i, int j, int[][] matrix) {
			super(i, j, matrix);
		}

		@Override
		public Step next() {
			if(i == 0 || matrix[i - 1][j] > 0) {
				return new Left(i, j + 1, matrix);
			} else {
				return new Up(i - 1, j, matrix);
			}
		}
		
	}
}
