package com.me.problems.leetcode;

public class ListNodeJ {

	public static void main(String[] args) {
		// 1->2->3->4->5->NULL
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		// head.next.next = new ListNode(3);
		// head.next.next.next = new ListNode(4);
		// head.next.next.next.next = new ListNode(5);
		head = removeNthFromEnd(head, 1);
		System.out.println(head);
	}

	public static ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode nhead = head;
		ListNode pre = null;
		ListNode node = nhead;
		while (node != null) {
			int k = n;
			ListNode p = node;
			while (k > 0 && p != null) {
				p = p.next;
				k -= 1;
			}

			if (p == null) {
				// node is the nth node from end;
				if (pre == null) {
					// it is the first node to delete;
					nhead = node.next;
				} else {
					pre.next = node.next;
				}

				break;
			} else {
				pre = node;
				node = node.next;
			}
		}

		return nhead;
	}

	public static ListNode rotateRight(ListNode head, int n) {
		if (head == null) {
			return null;
		}

		ListNode node = head;
		int len = 1;
		while (node.next != null) {
			node = node.next;
			len += 1;
		}
		ListNode tail = node;

		node.next = head;
		node = head;
		int k = len - n;
		while (k < 0) {
			k += len;
		}

		for (int i = 0; i < k; i++) {
			tail = node;
			node = node.next;
		}

		tail.next = null;
		return node;
	}

	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		@Override
		public String toString() {
			return "" + val + "->" + next + "";
		}

	}
}
