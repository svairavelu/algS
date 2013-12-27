package com.me.problems.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionApp {

	public int fEvalRPN(int[] pre, String[] expr) {
		if (expr.length == 0) {
			return Integer.valueOf(pre[0]);
		}
		String e = expr[0];
		int n = pre.length - 1;
		if (e.equals("+")) {
			int a = pre[n - 1];
			int b = pre[n];
			pre[n - 1] = a + b;
			pre = Arrays.copyOf(pre, n);
		} else if (e.equals("-")) {
			int a = pre[n - 1];
			int b = pre[n];
			pre[n - 1] = a - b;
			pre = Arrays.copyOf(pre, n);
		} else if (e.equals("*")) {
			int a = pre[n - 1];
			int b = pre[n];
			pre[n - 1] = a * b;
			pre = Arrays.copyOf(pre, n);
		} else if (e.equals("/")) {
			int a = pre[n - 1];
			int b = pre[n];
			pre[n - 1] = a / b;
			pre = Arrays.copyOf(pre, n);
		} else {
			pre = Arrays.copyOf(pre, n + 2);
			pre[n + 1] = Integer.valueOf(e);
		}

		return fEvalRPN(pre, Arrays.copyOfRange(expr, 1, expr.length));
	}

	public int evalRPN(String[] tokens) {
		return fEvalRPN(new int[0], tokens);
	}

	static class Point {
		int x;
		int y;

		Point() {
			x = 0;
			y = 0;
		}

		Point(int a, int b) {
			x = a;
			y = b;
		}
	}

	class Line {
		Point a, b;
		int nodes;

		public Line(Point a, Point b) {
			this.a = a;
			this.b = b;
			this.nodes = 2;
		}

		public boolean inLine(Point c) {
			return (c.x - b.x) * (b.y - a.y) == (b.x - a.x) * (c.y - b.y);
		}
	}

	private int findMaxPointsR(List<Line> lines, Point[] points, int idx,
			int max) {
		if (idx >= points.length) {
			return max;
		}

		Point c = points[idx];
		List<Line> nlines = new ArrayList<Line>();
		for (Line line : lines) {
			if (line.inLine(c)) {
				line.nodes += 1;
				if (max < line.nodes) {
					max = line.nodes;
				}
			} else {
				nlines.add(new Line(line.a, c));
				nlines.add(new Line(line.b, c));
			}
		}
		lines.addAll(nlines);
		return findMaxPointsR(lines, points, idx + 1, max);
	}

	public int maxPoints(Point[] points) {
		if (points.length < 3) {
			return points.length;
		} else {
			List<Line> lines = new ArrayList<Line>();
			lines.add(new Line(points[0], points[1]));
			return findMaxPointsR(lines, points, 2, 2);
		}
	}

	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		public String toString() {
			if (next == null) {
				return String.valueOf(val);
			} else {
				return String.valueOf(val) + "->" + next.toString();
			}
		}
	}

	public ListNode sortList(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode[] nodes = toArray(head);
		ListNode[] backup = new ListNode[nodes.length];
		mergeSort(nodes, backup, 0, nodes.length);
		return nodes[0];
	}

	private void mergeSort(ListNode[] nodes, ListNode[] backup, int start,
			int end) {
		if (end - start == 1) {
			nodes[start].next = null;
			return;
		}

		int dist = end - start;
		int mid = dist >> 1;
		if (dist % 2 == 1) {
			mid += 1;
		}
		mid += start;
		// int mid = (end - start) / 2;

		mergeSort(nodes, backup, start, mid);
		mergeSort(nodes, backup, mid, end);
		ListNode a = nodes[start];
		ListNode b = nodes[mid];
		int idx = start;
		while (a != null && b != null) {
			if (a.val <= b.val) {
				backup[idx++] = a;
				a = a.next;
			} else {
				backup[idx++] = b;
				b = b.next;
			}
		}

		while (a != null) {
			backup[idx++] = a;
			a = a.next;
		}

		while (b != null) {
			backup[idx++] = b;
			b = b.next;
		}

		nodes[start] = backup[start];
		for (int i = start + 1; i < end; i++) {
			ListNode x = backup[i - 1];
			ListNode y = backup[i];
			x.next = y;
			nodes[i] = y;
		}
		nodes[end - 1].next = null;
	}

	static class ListNodeEnd extends ListNode {

		ListNodeEnd() {
			super(-1);
		}
	}

	private ListNode link(ListNode a, ListNode b) {
		ListNode tmp = a;
		while (tmp.next != null) {
			tmp = tmp.next;
		}
		tmp.next = b;
		return a;
	}

	private ListNode removeEnd(ListNode list) {
		ListNode p = list;
		ListNode tmp = p.next;
		while (tmp.next != null) {
			p = tmp;
			tmp = tmp.next;
		}
		p.next = null;
		return list;
	}

	private ListNode reverseBetween(ListNode pre, ListNode mid, ListNode list,
			int m, int n) {
		if (m == 0 && n == 0) {
			mid = removeEnd(mid);
			link(pre, mid);
			link(pre, list);
			return pre;
		}

		if (m == 0 && n > 0) {
			ListNode node = list;
			list = list.next;
			node.next = mid;
			mid = node;
			return reverseBetween(pre, mid, list, m, n - 1);
		}

		ListNode node = list;
		list = list.next;
		node.next = null;
		pre = link(pre, node);
		return reverseBetween(pre, mid, list, m - 1, n - 1);

	}

	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode list = reverseBetween(new ListNodeEnd(), new ListNodeEnd(),
				head, m - 1, n);
		return list.next;
	}
	
	public static ListNode fromString(String str) {
		String[] nodes = str.split("->");
		ListNode head = new ListNode(Integer.valueOf(nodes[0]));
		ListNode tmp = head;
		for(int i = 1; i < nodes.length; i++) {
			int x = Integer.valueOf(nodes[i]);
			ListNode node = new ListNode(x);
			tmp.next = node;
			tmp = node;
		}
		
		return head;
	}
	
	public ListNode insertionSortList(ListNode head) {
		if(head == null) {
			return null;
		}
		ListNode[] nodes = toArray(head);
		
		for(int i = 1; i < nodes.length; i++) {
			ListNode a = nodes[i];
			int  j = i;
			for(; j > 0; j--) {
				ListNode b = nodes[j - 1];
				if(a.val >= b.val) {
					break;
				} else {
					nodes[j] = nodes[j - 1];
				}
			}
			nodes[j] = a;
		}
		
		for(int i = 1; i < nodes.length; i++) {
			ListNode a = nodes[i - 1];
			ListNode b = nodes[i];
			b.next = null;
			a.next = b;
		}
		
		return nodes[0];
	}
	
	private ListNode[] toArray(ListNode head) {
		int n = 100;
		int idx = 0;
		ListNode[] nodes = new ListNode[n];
		ListNode tmp = head;
		while (tmp != null) {
			nodes[idx++] = tmp;
			tmp = tmp.next;
			if (idx >= nodes.length) {
				nodes = Arrays.copyOf(nodes, nodes.length + n);
			}
		}
		return Arrays.copyOf(nodes, idx);
	}
	public static void main(String[] args) {

		SolutionApp app = new SolutionApp();
		// System.out.println(app
		// .evalRPN(new String[] { "4", "13", "5", "/", "+" }));
		// (0,0),(1,1),(1,-1)
		// Point a = new Point(0, 0);
		// Point b = new Point(1, 1);
		// Point c = new Point(1, -1);
		// System.out.println(app.maxPoints(new Point[] { a, b, c }));

//		ListNode head = new ListNode(1);
//		head.next = new ListNode(2);
//		head.next.next = new ListNode(3);
//		head.next.next.next = new ListNode(4);
//
//		head = app.sortList(head);
//		System.out.println(head);
		
//		ListNode head = fromString("1->2->3->4->5");
		ListNode head = fromString("2->1");
//		System.out.println(app.reverseBetween(head, 1, 4));
		System.out.println(app.insertionSortList(head));
	}

}
