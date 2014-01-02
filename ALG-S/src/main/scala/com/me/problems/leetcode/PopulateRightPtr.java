package com.me.problems.leetcode;

public class PopulateRightPtr {

	public static void main(String[] args) {
		TreeLinkNode tree = new TreeLinkNode(1);
		TreeLinkNode ptr = tree;
		ptr.right = new TreeLinkNode(-9);
		ptr = ptr.right;
		ptr.right = new TreeLinkNode(8);
		ptr = ptr.right;
		ptr.left = new TreeLinkNode(4);
		ptr.right = new TreeLinkNode(-3);
		connect(tree);
		System.out.println(tree);
	}


	public static void connect(TreeLinkNode root) {
		if (root == null) {
			return;
		}

		if (root.right != null) {
			root.right.next = root;
			link(root.right, true);
		}

		if (root.left != null) {
			root.left.next = root;
			link(root.left, false);
		}
	}

	public static void link(TreeLinkNode root, boolean right) {
		TreeLinkNode p = root.next;
		if (right) {
			root.next = null;
			while (p.next != null) {
				p = p.next;
				if (p.left != null) {
					root.next = p.left;
					break;
				}

				if (p.right != null) {
					root.next = p.right;
					break;
				}
			}
		} else {
			if (p.right != null) {
				root.next = p.right;
			} else {
				root.next = null;
				while (p.next != null) {
					p = p.next;
					if (p.left != null) {
						root.next = p.left;
						break;
					}

					if (p.right != null) {
						root.next = p.right;
						break;
					}
				}
			}
		}

		if (root.right != null) {
			root.right.next = root;
			link(root.right, true);
		}

		if (root.left != null) {
			root.left.next = root;
			link(root.left, false);
		}
	}

	static class TreeLinkNode {
		int val;
		TreeLinkNode left, right, next;

		TreeLinkNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "TreeLinkNode [val=" + val + "]";
		}

	}
}
