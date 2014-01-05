package com.me.problems.leetcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		for (int i = 1; i < nodes.length; i++) {
			int x = Integer.valueOf(nodes[i]);
			ListNode node = new ListNode(x);
			tmp.next = node;
			tmp = node;
		}

		return head;
	}

	public ListNode insertionSortList(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode[] nodes = toArray(head);

		for (int i = 1; i < nodes.length; i++) {
			ListNode a = nodes[i];
			int j = i;
			for (; j > 0; j--) {
				ListNode b = nodes[j - 1];
				if (a.val >= b.val) {
					break;
				} else {
					nodes[j] = nodes[j - 1];
				}
			}
			nodes[j] = a;
		}

		for (int i = 1; i < nodes.length; i++) {
			ListNode a = nodes[i - 1];
			ListNode b = nodes[i];
			b.next = null;
			a.next = b;
		}

		return nodes[0];
	}

	public void sortColors(int[] colors) {
		int i = 0, j = colors.length - 1;
		while (i < j) {
			int x = colors[i];
			int y = colors[j];
			if (x == 0) {
				// no need to switch
				i += 1;
				continue;
			}
			if (y == 2) {
				// no need to switch
				j -= 1;
				continue;
			}

			if (x == 2 && y == 0) {
				// switch x & y
				colors[i] = y;
				colors[j] = x;
				i += 1;
				j -= 1;
				continue;
			}

			if (x == 2) {
				// switch x & y, only move i;
				colors[i] = y;
				colors[j] = x;
				j -= 1;
				continue;
			}

			if (y == 0) {
				// switch x & y, move j
				colors[i] = y;
				colors[j] = x;
				i += 1;
				continue;
			}

			if (x == 1 && y == 1) {
				int k = i;
				while (k < j && colors[k] == 1) {
					k += 1;
				}
				if (k == j) {
					// already sorted,
					break;
				}
				colors[j] = colors[k];
				colors[k] = y;
			}
		}
	}

	public int jump(int[] xs) {
		int ret = 0;
		int last = 0;
		int curr = 0;
		for (int i = 0; i < xs.length; ++i) {
			if (i > last) {
				last = curr;
				++ret;
			}
			curr = Math.max(curr, i + xs[i]);
		}

		return ret;
	}

	public int maxSubArray(int[] A) {
		int max = A[0];
		int tmp = 0;
		for (int i = 0; i < A.length; i++) {
			tmp += A[i];
			if (tmp > max) {
				max = tmp;
			}

			if (tmp < 0) {
				tmp = 0;
			}
		}

		return max;
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

	public String strStr(String haystack, String needle) {
		if (needle == null || needle.length() == 0) {
			return haystack;
		}

		char[] xs = haystack.toCharArray();
		char[] ys = needle.toCharArray();

		for (int i = 0; i < xs.length - ys.length + 1; i++) {
			boolean found = true;
			for (int j = 0; j < ys.length; j++) {
				if (xs[i + j] != ys[j]) {
					found = false;
					break;
				}
			}
			if (found) {
				return new String(Arrays.copyOfRange(xs, i, xs.length));
			}
		}
		return null;
	}

	class Range {
		final int s, e;

		Range(int s, int e) {
			this.s = s;
			this.e = e;
		}
	}

	public Range searchRange(int[] xs, int target, int s, int e) {
		if (xs[s] == target && xs[e] == target) {
			return new Range(s, e);
		}

		if (s == e) {
			return null;
		}

		int mid = (e - s) / 2 + s;
		if (xs[mid] < target) {
			return searchRange(xs, target, mid + 1, e);
		}

		if (xs[mid] > target) {
			return searchRange(xs, target, s, mid);
		}

		Range left = searchRange(xs, target, s, mid);
		// left can't be null, at least has mid
		Range right = searchRange(xs, target, mid + 1, e);
		if (right != null) {
			return new Range(left.s, right.e);
		} else {
			return left;
		}
	}

	public int[] searchRange(int[] xs, int target) {
		Range range = searchRange(xs, target, 0, xs.length - 1);
		if (range != null) {
			return new int[] { range.s, range.e };
		} else {
			return new int[] { -1, -1 };
		}
	}

	private int longestValidParentheses(char[] xs, int idx, int max, int num,
			int level) {
		if (idx == xs.length) {
			return max;
		}

		char x = xs[idx];
		if (x == '(') {
			return longestValidParentheses(xs, idx + 1, max, num, level + 1);
		}

		if (level == 0) {
			return longestValidParentheses(xs, idx + 1, max, 0, 0);
		}

		int nmax = (max > (num + 1) ? max : (num + 1));
		return longestValidParentheses(xs, idx + 1, nmax, num + 1, level - 1);
	}

	public int longestValidParentheses(String s) {
		char[] xs = s.toCharArray();
		int[] ps = new int[xs.length];
		int psIdx = 0;
		Range[] ranges = new Range[xs.length];
		int rangeIdx = 0;
		for (int i = 0; i < xs.length; i++) {
			char x = xs[i];
			if (x == '(') {
				ps[psIdx++] = i;
			} else {
				if (psIdx == 0) {
					continue;
				}
				int idx = ps[psIdx - 1];
				psIdx--;
				ranges[rangeIdx++] = new Range(idx, i);
				while (rangeIdx > 1) {
					// merge ranges
					Range lastRange = ranges[rangeIdx - 1];
					Range lastSecondRange = ranges[rangeIdx - 2];
					if (lastRange.s < lastSecondRange.s) {
						// last one is bigger, and contains the second
						ranges[rangeIdx - 2] = lastRange;
						rangeIdx--;
					} else if (lastSecondRange.e + 1 == lastRange.s) {
						// two range can be merged
						ranges[rangeIdx - 2] = new Range(lastSecondRange.s,
								lastRange.e);
						rangeIdx--;
						break;
					} else {
						break;
					}
				}
			}
		}

		int max = 0;
		for (int i = 0; i < rangeIdx; i++) {
			Range range = ranges[i];
			int num = range.e - range.s + 1;
			if (num > max) {
				max = num;
			}
		}
		return max;
	}

	private List<List<Integer>> fourSum1(int[] num, int s, int e, int target) {
		int sum = num[s] + num[e];
		if (sum > target) {
			return null;
		}

		List<List<Integer>> rt = fourSum2(new ArrayList<List<Integer>>(), num,
				s + 1, e - 1, target - sum);
		if (rt != null && rt.size() > 0) {
			List<List<Integer>> get = new ArrayList<List<Integer>>();
			for (List<Integer> one : rt) {
				int b = one.get(0);
				int c = one.get(1);
				List<Integer> abcd = new ArrayList<Integer>();
				abcd.add(s);
				abcd.add(b);
				abcd.add(c);
				abcd.add(e);
				get.add(abcd);
			}
			return get;
		} else {
			return null;
		}
	}

	private List<List<Integer>> fourSum2(List<List<Integer>> rt, int[] num,
			int s, int e, int target) {
		if (s >= e) {
			return rt;
		}

		int x = num[s];
		int y = num[e];
		if (x + y == target) {
			List<Integer> bc = new ArrayList<Integer>();
			bc.add(s);
			bc.add(e);
			rt.add(bc);
			return fourSum2(rt, num, s + 1, e - 1, target);
		} else if (x + y > target) {
			return fourSum2(rt, num, s, e - 1, target);
		} else {
			return fourSum2(rt, num, s + 1, e, target);
		}
	}

	public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
		if (num.length < 4) {
			return new ArrayList<ArrayList<Integer>>();
		}
		Arrays.sort(num);

		int head = num[0];
		if (head < 0) {
			target = target - 4 * head;
			int i = 0;
			for (i = 0; i < num.length; i++) {
				num[i] -= head;
				if (num[i] > target) {
					break;
				}
			}
			num = Arrays.copyOf(num, i);
		}

		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

		Set<String> set = new HashSet<String>();
		for (int i = 0; i < num.length - 3; i++) {
			for (int j = i + 3; j < num.length; j++) {
				List<List<Integer>> rt = fourSum1(num, i, j, target);
				if (rt != null && rt.size() > 0) {
					for (List<Integer> abcd : rt) {
						int a = num[abcd.get(0)];
						int b = num[abcd.get(1)];
						int c = num[abcd.get(2)];
						int d = num[abcd.get(3)];
						String key = "" + a + b + c + d;
						if (set.contains(key)) {
							continue;
						}
						set.add(key);
						ArrayList<Integer> cccc = new ArrayList<Integer>();
						cccc.add(a + head);
						cccc.add(b + head);
						cccc.add(c + head);
						cccc.add(d + head);
						list.add(cccc);
					}
				}
			}
		}

		return list;
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

	public ArrayList<Integer> postorderTraversal(TreeNode root) {
		TreeNode[] array = new TreeNode[100];
		ArrayList<Integer> values = new ArrayList<Integer>();
		int depth = 0;
		TreeNode ptr = root;
		TreeNode tmp = null;
		while (ptr != null) {
			if (ptr.left != null || ptr.right != null) {
				if (depth >= array.length) {
					array = Arrays.copyOf(array, array.length + 10);
				}
				array[depth++] = ptr;
				if (ptr.left != null) {
					tmp = ptr;
					ptr = ptr.left;
					tmp.left = null;
				} else if (ptr.right != null) {
					tmp = ptr;
					ptr = ptr.right;
					tmp.right = null;
				}
			} else {
				values.add(ptr.val);
				if (depth > 0) {
					ptr = array[--depth];
				} else {
					ptr = null;
				}
			}
		}

		return values;
	}

	public ArrayList<Integer> preorderTraversal(TreeNode root) {
		TreeNode[] array = new TreeNode[100];
		ArrayList<Integer> values = new ArrayList<Integer>();
		int depth = 0;
		TreeNode ptr = root;
		TreeNode tmp = null;
		boolean back = false;
		while (ptr != null) {
			if (!back) {
				values.add(ptr.val);
			}
			if (ptr.left != null || ptr.right != null) {
				back = false;
				if (depth >= array.length) {
					array = Arrays.copyOf(array, array.length + 10);
				}
				array[depth++] = ptr;
				if (ptr.left != null) {
					tmp = ptr;
					ptr = ptr.left;
					tmp.left = null;
				} else if (ptr.right != null) {
					tmp = ptr;
					ptr = ptr.right;
					tmp.right = null;
				}
			} else {
				if (depth > 0) {
					ptr = array[--depth];
					back = true;
				} else {
					ptr = null;
				}
			}
		}

		return values;
	}

	public void reorderList(ListNode head) {
		if (head == null) {
			return;
		}
		ListNode[] nodes = toArray(head);
		int j = nodes.length - 1;
		for (int i = 0; i < j; i++, j--) {
			ListNode a = nodes[i];
			ListNode b = nodes[j];
			if (j - i == 1) {
				a.next = b;
				b.next = null;
				break;
			} else if (j - i == 2) {
				ListNode tmp = nodes[i + 1];
				a.next = b;
				b.next = tmp;
				tmp.next = null;
				break;
			} else {
				ListNode tmp = a.next;
				a.next = b;
				b.next = tmp;
			}
		}
	}

	public boolean hasCycle(ListNode head) {
		if (head == null || head.next == null) {
			return false;
		}
		ListNode ptr0 = head;
		ListNode ptr1 = head.next;
		while (ptr0 != null) {
			if (ptr1 == ptr0) {
				break;
			}

			ptr0 = ptr0.next;

			if (ptr1 == null || ptr1.next == null) {
				break;
			}
			ptr1 = ptr1.next.next;
		}
		if (ptr0 == null || ptr1 == null || ptr1.next == null) {
			return false;
		} else {
			return true;
		}
	}

	public ListNode detectCycle(ListNode head) {
		if (head == null || head.next == null) {
			return null;
		}
		ListNode ptr0 = head;
		ListNode ptr1 = head;
		do {
			ptr0 = ptr0.next;
			if (ptr1 == null) {
				break;
			}
			ptr1 = ptr1.next;
			if (ptr1 == null) {
				break;
			}
			ptr1 = ptr1.next;
		} while (ptr1 != ptr0);

		if (ptr0 == null || ptr1 == null || ptr1.next == null) {
			return null;
		} else {
			ptr0 = head;
			while (ptr0 != ptr1) {
				ptr0 = ptr0.next;
				ptr1 = ptr1.next;
			}
			return ptr0;
		}
	}

	class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}
	};

	public RandomListNode copyRandomList(RandomListNode head) {
		if (head == null) {
			return null;
		}

		RandomListNode copyHead = new RandomListNode(-1);
		RandomListNode ptr0 = head;
		RandomListNode ptr1 = copyHead;

		Map<RandomListNode, Integer> ps = new HashMap<RandomListNode, Integer>();
		RandomListNode[] nodes = new RandomListNode[100];
		int idx = 0;
		while (ptr0 != null) {
			ps.put(ptr0, idx);
			ptr1.next = new RandomListNode(ptr0.label);
			ptr0 = ptr0.next;
			ptr1 = ptr1.next;
			if (idx >= nodes.length) {
				nodes = Arrays.copyOf(nodes, nodes.length + 20);
			}
			nodes[idx] = ptr1;
			idx += 1;
		}

		copyHead = copyHead.next;

		nodes = Arrays.copyOf(nodes, idx);

		ptr0 = head;
		ptr1 = copyHead;
		while (ptr0 != null) {
			RandomListNode random = ptr0.random;
			if (random != null) {
				int i = ps.get(random);
				ptr1.random = nodes[i];
			} else {

			}
			ptr0 = ptr0.next;
			ptr1 = ptr1.next;
		}

		return copyHead;
	}

	public int singleNumber(int[] A) {
		if (A.length == 1) {
			return A[0];
		}

		int x = A[0];
		for (int i = 1; i < A.length; i++) {
			x = x ^ A[i];
		}
		return x;
	}

	public int singleNumber2(int[] A) {
		int[] count = new int[32];
		Arrays.fill(count, 0);
		int result = 0;

		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < A.length; j++) {
				if (((A[j] >> i) & 1) == 1) {
					count[i] += 1;
				}
			}

			result |= (count[i] % 3) << i;
		}

		return result;
	}

	public static int pal(int i, int j, String s, int[][] pal) {
		if (pal[i][j] != 0)
			return pal[i][j];

		if (j == i) {
			pal[i][j] = 2;
			return 2;
		}

		if (s.charAt(i) == s.charAt(j)) {
			if (j - i > 2) {
				pal[i][j] = pal(i + 1, j - 1, s, pal);
			} else {
				pal[i][j] = 2;
			}
			return pal[i][j];
		} else {
			pal[i][j] = 1;
			return 1;
		}
	}

	public static int cut(int i, String s, int[] cut, int[][] pal) {

		if (cut[i] != 0) {
			return cut[i];
		}
		if (pal(0, i, s, pal) == 2) {
			cut[i] = 0;
			return 0;
		}

		int min = i;
		for (int k = 1; k <= i; k++) {
			if (pal(k, i, s, pal) == 2) {
				int tmp = cut(k - 1, s, cut, pal) + 1;
				if (tmp < min) {
					min = tmp;
				}
			}
		}
		cut[i] = min;

		return min;
	}

	public static int minCut(String s) {
		int len = s.length();
		if (len <= 1)
			return 0;
		int pal[][] = new int[len][len];
		int cut[] = new int[len];

		return cut(len - 1, s, cut, pal);

	}

	private boolean alphanumeric(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}

		if (c >= 'a' && c <= 'z') {
			return true;
		}

		return false;
	}

	public boolean isPalindrome(String s) {
		if (s == null) {
			return false;
		}

		if (s.trim().isEmpty()) {
			return true;
		}

		char[] cs = s.trim().toLowerCase().toCharArray();

		boolean matched = true;
		for (int i = 0, j = cs.length - 1; i < j;) {
			char x = cs[i];
			char y = cs[j];
			if (!alphanumeric(x)) {
				i += 1;
				continue;
			}

			if (!alphanumeric(y)) {
				j -= 1;
				continue;
			}

			if (x == y) {
				i += 1;
				j -= 1;
			} else {
				matched = false;
				break;
			}
		}

		return matched;
	}

	private int max(int x, int y) {
		if (x > y) {
			return x;
		} else {
			return y;
		}
	}

	public int maxPathSum(TreeNode root) {
		int[] res = new int[1];
		res[0] = Integer.MIN_VALUE;
		maxPath(root, res);
		return res[0];
	}

	private int maxPath(TreeNode root, int[] res) {
		if (root == null)
			return 0;
		int left = maxPath(root.left, res);
		int right = maxPath(root.right, res);
		int arch = left + right + root.val;
		int single = Math.max(root.val, Math.max(left, right) + root.val);
		res[0] = Math.max(res[0], Math.max(arch, single));
		return single;
	}

	static class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
	}

	public static ArrayList<Interval> insert(ArrayList<Interval> intervals,
			Interval newInterval) {
		ArrayList<Interval> list = new ArrayList<Interval>();
		boolean newIntervalProcessed = false;
		for (Interval interval : intervals) {
			if (newIntervalProcessed) {
				list.add(interval);
				continue;
			}

			if (interval.start > newInterval.end) {
				if (!newIntervalProcessed) {
					list.add(newInterval);
					newIntervalProcessed = true;
				}
				list.add(interval);
			} else if (newInterval.start <= interval.start) {
				newInterval = new Interval(newInterval.start, Math.max(
						newInterval.end, interval.end));
			} else if (newInterval.start <= interval.end) {
				newInterval = new Interval(interval.start, Math.max(
						newInterval.end, interval.end));
			} else {
				list.add(interval);
			}
		}

		if (!newIntervalProcessed) {
			list.add(newInterval);
		}
		return list;
	}

	public static int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
		if (triangle == null || triangle.size() == 0) {
			return 0;
		}

		int height = triangle.size() - 1;
		int n = triangle.get(height).size();
		int[] xs = new int[n];
		int[] ys = new int[n];
		ArrayList<Integer> firstRow = triangle.get(0);
		xs[0] = firstRow.get(0);
		for (int i = 1; i <= height; i++) {
			ArrayList<Integer> row = triangle.get(i);
			ys[0] = xs[0] + row.get(0);
			int m = row.size();
			for (int j = 1; j < m - 1; j++) {
				int value = row.get(j);
				int a = xs[j - 1];
				int b = xs[j];
				ys[j] = Math.min(a + value, b + value);
			}
			ys[m - 1] = xs[m - 2] + row.get(m - 1);
			int[] tmp = ys;
			ys = xs;
			xs = tmp;
		}

		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			if (xs[i] < min) {
				min = xs[i];
			}
		}
		return min;
	}

	public static void testTriangle() {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				"src/main/scala/com/me/problems/leetcode/triangle_0.txt"))) {
			String line = reader.readLine();
			ArrayList<ArrayList<Integer>> triangle = new ArrayList<ArrayList<Integer>>();
			while (line != null) {
				String[] nums = line.split(",");
				ArrayList<Integer> row = new ArrayList<Integer>();
				for (String num : nums) {
					row.add(Integer.valueOf(num));
				}
				triangle.add(row);
				line = reader.readLine();
			}

			System.out.println(minimumTotal(triangle));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ArrayList<Integer> getRow(int rowIndex) {
		int[] xs = new int[rowIndex + 1];
		int[] ys = new int[rowIndex + 1];

		xs[0] = 1;
		for (int i = 1; i <= rowIndex; i++) {
			ys[0] = 1;
			for (int j = 1; j < i; j++) {
				ys[j] = xs[j - 1] + xs[j];
			}
			ys[i] = 1;

			int[] tmp = ys;
			ys = xs;
			xs = tmp;
		}

		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i <= rowIndex; i++) {
			res.add(xs[i]);
		}
		return res;
	}

	public static void testPascalTriangle2() {
		ArrayList<Integer> rest = getRow(1);
		for (int x : rest) {
			System.out.print(x + " ");
		}
	}

	public static ArrayList<ArrayList<Integer>> generate(int rowIndex) {
		ArrayList<ArrayList<Integer>> triangle = new ArrayList<ArrayList<Integer>>();
		if (rowIndex == 0) {
			return triangle;
		}

		ArrayList<Integer> row0 = new ArrayList<Integer>();
		row0.add(1);

		triangle.add(row0);
		ArrayList<Integer> lastRow = row0;
		for (int i = 1; i < rowIndex; i++) {
			ArrayList<Integer> ys = new ArrayList<Integer>();
			ys.add(1);
			for (int j = 1; j < i; j++) {
				ys.add(lastRow.get(j - 1) + lastRow.get(j));
			}
			ys.add(1);
			triangle.add(ys);
			lastRow = ys;
		}
		return triangle;
	}

	private static int toNum(boolean[] bits) {
		int num = 0;
		for (int i = 0; i < bits.length; i++) {
			int x = 0;
			if (bits[i]) {
				x = 1;
			}
			num = num * 2 + x;
		}
		return num;
	}

	public static ArrayList<Integer> grayCode(int n) {
		boolean[] bits = new boolean[n];
		ArrayList<Integer> ys = new ArrayList<Integer>();
		ys.add(0);
		int nxt = 0;
		java.util.BitSet bs = new java.util.BitSet();
		bs.set(0);

		while (nxt >= 0) {
			int tmp = nxt;
			for (int i = n - 1; i >= 0; i--) {
				bits[i] = !bits[i];
				int x = toNum(bits);
				if (bs.get(x)) {
					// already added
					bits[i] = !bits[i];
				} else {
					nxt = x;
					bs.set(x);
					ys.add(nxt);
					break;
				}
			}
			if (nxt == tmp) {
				// can't find the next value;
				nxt = -1;
			}
		}
		return ys;
	}

	public static void testGrayCode() {
		ArrayList<Integer> codes = grayCode(12);
		String sep = "";
		for (Integer code : codes) {
			System.out.print(sep + code);
			sep = "->";
		}
	}

	public static ArrayList<String> restoreIpAddresses(String s) {
		// Start typing your Java solution below
		// DO NOT write main() function
		ArrayList<String> result = new ArrayList<String>();
		helper(s, 0, "", result);
		return result;
	}

	public static void helper(String dataSegment, int level, String temp,
			ArrayList<String> result) {
		if (level == 4) {
			if (dataSegment.length() == 0)
				result.add(temp.substring(1));
			return;
		}
		int possi = dataSegment.length() >= 3 ? 3 : dataSegment.length();
		for (int i = 1; i <= possi; i++) {
			String newOne = dataSegment.substring(0, i);
			if (isValidSegment(newOne)) {
				temp += "." + newOne;
				helper(dataSegment.substring(i), level + 1, temp, result);
				temp = temp.substring(0, temp.lastIndexOf("."));
			}
		}
	}

	public static boolean isValidSegment(String s) {
		if (s.charAt(0) == '0')
			return s.length() == 1;
		Integer result = Integer.parseInt(s);
		if (result > 255)
			return false;
		return true;
	}

	public static void testRestoreIpAddresses() {
		restoreIpAddresses("1111");
	}

	public static void main(String[] args) {
		// testGrayCode();
		testRestoreIpAddresses();
		// testPascalTriangle2();
		// testTriangle();
		// ArrayList<Interval> intervals = new ArrayList<Interval>();
		// intervals.add(new Interval(1, 5));
		//
		// ArrayList<Interval> list = insert(intervals, new Interval(2, 3));
		// for (Interval interval : list) {
		// System.out.println(interval);
		// }

		// SolutionApp app = new SolutionApp();

		// System.out.println(app.minCut("abbab"));
		// TreeNode root = new TreeNode(1);
		// root.left = new TreeNode(2);
		// ArrayList<Integer> post = app.postorderTraversal(root);
		// for (Integer val : post) {
		// System.out.println(val);
		// }
		// System.out.println(app.jump(new int[] { 5, 6, 4, 4, 6, 9, 4, 4, 7, 4,
		// 4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8,
		// 8, 4, 4, 2, 0, 3, 8, 5 }));
		//
		// System.out.println(app.maxSubArray(new int[] { -2, 1, -3, 4, -1, 2,
		// 1,
		// -5, 4 }));
		//
		// System.out.println(app.searchRange(new int[] { 1, 2, 3 }, 1));
		//
		// System.out.println(app.fourSum(new int[] { -5, 5, 4, -3, 0, 0, 4, -2
		// },
		// 4));

		// try (BufferedReader reader = new BufferedReader(new
		// FileReader("src/main/scala/com/me/problems/leetcode/longestValidParenthesesTest.txt"))){
		// StringBuilder sb = new StringBuilder();
		// String line = reader.readLine();
		// while(line != null) {
		// sb.append(line);
		// line = reader.readLine();
		// }
		//
		// System.out.println(app.longestValidParentheses(sb.toString()));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println(app.longestValidParentheses("()()"));
		// System.out.println(app
		// .evalRPN(new String[] { "4", "13", "5", "/", "+" }));
		// (0,0),(1,1),(1,-1)
		// Point a = new Point(0, 0);
		// Point b = new Point(1, 1);
		// Point c = new Point(1, -1);
		// System.out.println(app.maxPoints(new Point[] { a, b, c }));

		// ListNode head = new ListNode(1);
		// head.next = new ListNode(2);
		// head.next.next = new ListNode(3);
		// head.next.next.next = new ListNode(4);
		//
		// head = app.sortList(head);
		// System.out.println(head);

		// ListNode head = fromString("1->2->3->4->5");
		// ListNode head = fromString("2->1");
		// System.out.println(app.reverseBetween(head, 1, 4));
		// System.out.println(app.insertionSortList(head));
	}

}
