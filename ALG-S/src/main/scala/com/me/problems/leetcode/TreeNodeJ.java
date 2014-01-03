package com.me.problems.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class TreeNodeJ {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public static boolean isBalanced(TreeNode tree) {
		if (tree == null) {
			return true;
		}
		Map<TreeNode, Integer> cache = new HashMap<TreeNode, Integer>();
		Set<TreeNode> checked = new HashSet<TreeNode>();
		Stack<TreeNode> stack = new Stack<TreeNode>();

		stack.push(tree);
		while (!stack.isEmpty()) {
			TreeNode node = stack.peek();
			checked.add(node);
			TreeNode left = node.left;
			TreeNode right = node.right;
			if (left == null && right == null) {
				cache.put(node, 0);
				stack.pop();
				continue;
			}

			if (left != null && !checked.contains(left)) {
				stack.push(left);
				continue;
			}

			if (right != null && !checked.contains(right)) {
				stack.push(right);
				continue;
			}

			// then left & right are already checked;
			int lh = left == null ? -1 : cache.get(left);
			int rh = right == null ? -1 : cache.get(right);
			if (Math.abs(lh - rh) > 1) {
				return false;
			}

			cache.put(node, Math.max(lh, rh) + 1);
			stack.pop();
		}

		return true;
	}

}
