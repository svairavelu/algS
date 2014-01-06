package com.me.problems.leetcode;

import java.util.Stack;

public class LargestRectangleInHistogramJ {

	public static void main(String[] args) {
		LargestRectangleInHistogramJ app = new LargestRectangleInHistogramJ();
		System.out.println(app.largestRectangleArea(new int[] { 5, 5, 1, 7, 1,
				1, 5, 2, 7, 6 }));
	}

	public int largestRectangleArea(int[] height) {
		int n = height.length;
		if (n == 0)
			return 0;
		Stack<A> as = new Stack<A>();
		int max = 0;
		for (int i = 0; i < n; i++) {
			int h = height[i];
			if (as.isEmpty()) {
				max = Math.max(max, h);
				as.push(new A(i, h));
			} else {
				A top = as.peek();
				if (top.h < h) {
					max = Math.max(max, h);
					as.push(new A(i, h));
				} else {
					int index = top.i;
					while (top.h >= h) {
						index = top.i;
						top = as.pop();
						int s = top.h * (i - top.i);
						max = Math.max(s, max);
						if (as.isEmpty()) {
							break;
						}
						top = as.peek();
					}
					// the index for current bar is not i, but the index of the
					// bar popped out.
					// because the rectangle with current bar's height is from
					// this index.
					as.push(new A(index, h));
				}
			}
		}
		for (A a : as) {
			// why use n as the final right boarder? think about that, in this
			// stack,
			// from left to right, has an asc ordering, and they certainly can
			// reach
			// at the right most one from its position.
			int s = (n - a.i) * a.h;
			max = Math.max(s, max);
		}
		return max;
	}

	class A {
		final int i, h;

		public A(int i, int h) {
			this.i = i;
			this.h = h;
		}
	}
}
