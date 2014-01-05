package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TreeNodeJ {

	public static void main(String[] args) {
		// testBuildTreeFromInorderAndPostorder();
		// testRecovrTree();
		// testIsValidBST();
		testTenerateBSTress();
	}

	public static void testBuildTreeFromInorderAndPostorder() {
		int[] inorder = new int[] { 1, 2, 3, 4 };
		int[] postorder = new int[] { 2, 3, 1, 4 };
		System.out.println(buildTree(inorder, postorder));
	}

	public static void testRecovrTree() {
		TreeNode tree = fromStr("{0,1}");
		recoverTree(tree);
		System.out.println(tree);
	}

	public static void testIsValidBST() {
		TreeNode tree = fromStr("{0,-1}");
		System.out.println(isValidBST(tree));
	}

	public static boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		int[] vals = new int[100];
		int idx = 0;
		TreeNode current = root;

		while (current != null) {
			if (idx >= vals.length) {
				vals = Arrays.copyOf(vals, vals.length + 10);
			}
			if (current.left == null) {
				// right can't be null
				vals[idx++] = current.val;
				current = current.right;
			} else {
				TreeNode p = current.left;
				while (p.right != null && p.right != current) {
					p = p.right;
				}

				if (p.right == null) {
					p.right = current;
					current = current.left;
				} else {
					p.right = null;

					// parent can't be null here, it is the leftest one in the
					// path
					vals[idx++] = current.val;
					current = current.right;
				}
			}
		}

		for (int i = 1; i < idx; i++) {
			int x = vals[i - 1];
			int y = vals[i];
			if (x >= y) {
				return false;
			}
		}
		return true;
	}

	public static void recoverTree(TreeNode root) {
		if (root == null) {
			return;
		}

		TreeNode first = null;
		TreeNode second = null;
		TreeNode parent = null;
		TreeNode current = root;

		while (current != null) {
			if (current.left == null) {
				// right can't be null
				if (parent != null && parent.val > current.val) {
					if (first == null) {
						first = parent;
					}

					second = current;
				}
				parent = current;

				current = current.right;
			} else {
				TreeNode p = current.left;
				while (p.right != null && p.right != current) {
					p = p.right;
				}

				if (p.right == null) {
					p.right = current;
					current = current.left;
				} else {
					p.right = null;

					// parent can't be null here, it is the leftest one in the
					// path
					if (parent.val > current.val) {
						if (first == null) {
							first = parent;
						}
						second = current;
					}
					parent = current;

					current = current.right;
				}
			}
		}

		if (first != null && second != null) {
			int tmp = first.val;
			first.val = second.val;
			second.val = tmp;
		}
	}

	public static TreeNode buildTree(int[] inorder, int[] postorder) {
		if (inorder == null || inorder.length == 0) {
			return null;
		}
		return buildTree(inorder, postorder, 0, inorder.length);
	}

	private static TreeNode buildTree(int[] inorder, int[] postorder, int s,
			int e) {
		if (s >= e) {
			return null;
		}
		if (s + 1 == e) {
			return new TreeNode(inorder[s]);
		}

		int x = postorder[e - 1];

		int idx = -1;
		for (int i = s; i < e; i++) {
			if (inorder[i] == x) {
				idx = i;
				break;
			}
		}
		TreeNode p = new TreeNode(x);
		if (idx > 0) {
			p.left = buildTree(inorder, postorder, s, idx);
		}

		if (idx < e - 1) {
			for (int i = e - 1; i > idx; i--) {
				postorder[i] = postorder[i - 1];
			}
			postorder[idx] = x;
			p.right = buildTree(inorder, postorder, idx + 1, e);
		}
		return p;
	}

	public static ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
		if (root == null) {
			return new ArrayList<ArrayList<Integer>>();
		}
		ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();

		int idx = 0;
		TreeNode[] xs = new TreeNode[10];
		TreeNode[] ys = new TreeNode[10];
		xs[idx++] = root;

		while (idx > 0) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			int len = idx;
			idx = 0;

			for (int i = 0; i < len; i++) {
				TreeNode x = xs[i];
				row.add(x.val);

				if (idx >= ys.length - 2) {
					ys = Arrays.copyOf(ys, idx + 10);
				}

				if (x.left != null) {
					ys[idx++] = x.left;
				}
				if (x.right != null) {
					ys[idx++] = x.right;
				}
			}

			rows.add(row);
			TreeNode[] tmp = xs;
			xs = ys;
			ys = tmp;
		}

		Collections.reverse(rows);
		return rows;
	}

	public static boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		}

		if (p != null && q == null) {
			return false;
		}

		if (p == null && q != null) {
			return false;
		}

		if (p.val != q.val) {
			return false;
		}

		return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
	}

	public static ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
		if (root == null) {
			return new ArrayList<ArrayList<Integer>>();
		}
		ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();

		int idx = 0;
		TreeNode[] xs = new TreeNode[10];
		TreeNode[] ys = new TreeNode[10];
		xs[idx++] = root;
		boolean leftToRight = true;
		while (idx > 0) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			int len = idx;
			idx = 0;

			for (int i = 0; i < len; i++) {
				TreeNode x = xs[i];
				// row.add(x.val);

				if (idx >= ys.length - 2) {
					ys = Arrays.copyOf(ys, idx + 10);
				}

				if (x.left != null) {
					ys[idx++] = x.left;
				}
				if (x.right != null) {
					ys[idx++] = x.right;
				}
			}

			if (leftToRight) {
				for (int i = 0; i < len; i++) {
					row.add(xs[i].val);
				}
			} else {
				for (int i = len - 1; i >= 0; i--) {
					row.add(xs[i].val);
				}
			}

			leftToRight = !leftToRight;

			rows.add(row);
			TreeNode[] tmp = xs;
			xs = ys;
			ys = tmp;
		}

		// Collections.reverse(rows);
		return rows;
	}

	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "TreeNode [val=" + val + "]";
		}
	}

	public static int maxDepth(TreeNode tree) {
		if (tree == null) {
			return 0;
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
				cache.put(node, 1);
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

			cache.put(node, Math.max(lh, rh) + 1);
			stack.pop();
		}
		return cache.get(tree);
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

	public static TreeNode fromStr(String str) {
		if (str == null) {
			return null;
		}

		String[] cs = str.substring(1, str.length() - 1).split(",");
		int n = cs.length;

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		int idx = 0;
		TreeNode root = new TreeNode(Integer.valueOf(cs[idx++]));
		queue.offer(root);
		while (!queue.isEmpty() && idx < n) {
			TreeNode node = queue.poll();
			if (idx < n) {
				String c = cs[idx++];
				if (c != "#") {
					TreeNode left = new TreeNode(Integer.valueOf(c));
					node.left = left;
					queue.offer(left);
				}
			}

			if (idx < n) {
				String c = cs[idx++];
				if (c != "#") {
					TreeNode right = new TreeNode(Integer.valueOf(c));
					node.right = right;
					queue.offer(right);
				}
			}
		}

		return root;
	}

	public static void testTenerateBSTress() {
		ArrayList<TreeNode> trees = generateTrees(3);
		for (TreeNode tree : trees) {
			System.out.println(tree);
		}
	}

	public static ArrayList<TreeNode> generateTrees(int n) {
		return generateTrees(1, n + 1);
	}

	private static ArrayList<TreeNode> generateTrees(int s, int e) {
		ArrayList<TreeNode> trees = new ArrayList<TreeNode>();
		int n = e - s;
		if (n == 0) {
			// empty
			trees.add(null);
			return trees;
		}

		if (n == 1) {
			TreeNode node = new TreeNode(s);
			trees.add(node);
			return trees;
		}

		for (int i = s; i < e; i++) {
			ArrayList<TreeNode> left = generateTrees(s, i);
			ArrayList<TreeNode> right = generateTrees(i + 1, e);

			if (left.size() == 0) {
				for (TreeNode r : right) {
					TreeNode root = new TreeNode(i);
					root.right = r;
					trees.add(root);
				}
				continue;
			}

			if (right.size() == 0) {
				for (TreeNode l : left) {
					TreeNode root = new TreeNode(i);
					root.left = l;
					trees.add(root);
				}
				continue;
			}

			for (TreeNode l : left) {
				for (TreeNode r : right) {
					TreeNode root = new TreeNode(i);
					root.left = l;
					root.right = r;
					trees.add(root);
				}
			}
		}

		return trees;
	}

	public static int numTrees(int n) {
		if (n == 0)
			return 1;
		if (n == 1)
			return 1;
		int sum = 0;

		for (int i = 0; i < n; i++)
			sum += numTrees(i) * numTrees(n - 1 - i);
		return sum;
	}

	public static ArrayList<Integer> inorderTraversal(TreeNode root) {
		TreeNode current = root;
		ArrayList<Integer> vals = new ArrayList<Integer>();
		
		while(current != null) {
			if(current.left == null) {
				//go right;
				vals.add(current.val);
				current = current.right;
			} else {
				//link current to left's right most child's right
				TreeNode p = current.left;
				while(p.right != null && p.right != current) {
					p = p.right;
				}
				
				if(p.right == null) {
					//link it.
					p.right = current;
					current = current.left;//go left;
				} else {
					//p is the right child found
					p.right = null;
					vals.add(current.val);
					current = current.right;// go right;
				}
			}
		}
		return vals;
	}
}
