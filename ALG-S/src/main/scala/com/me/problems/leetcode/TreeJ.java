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
	
	public static boolean isBalanced(Tree tree) {
		int lh = height(tree.left);
		int rh = height(tree.right);
		int diff = Math.abs(lh - rh);
		return diff <= 1;
	}
	
	public static int height(Tree tree) {
		if(tree == null) {
			return -1;
		}
		
		return Math.max(height(tree.left), height(tree.right)) + 1;
	}
}
