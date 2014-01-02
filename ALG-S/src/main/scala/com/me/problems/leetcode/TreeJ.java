package com.me.problems.leetcode;

import com.me.problems.leetcode.SolutionApp.TreeNode;

public class TreeJ {

	public static void main(String[] args) {

	}

	static class Tree {
		final int val;
		final Tree left, right;

		public Tree(int val, Tree left, Tree right) {
			super();
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	public static int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}

		if (root.left == null && root.right == null) {
			return 1;
		}

		if (root.left == null) {
			return minDepth(root.right) + 1;
		}

		if (root.right == null) {
			return minDepth(root.left) + 1;
		}
		return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
	}
}
