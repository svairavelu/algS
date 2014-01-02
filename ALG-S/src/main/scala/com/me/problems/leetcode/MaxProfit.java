package com.me.problems.leetcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MaxProfit {
	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				"src/main/scala/com/me/problems/leetcode/max_profit_1.txt"))) {
			String line = null;
			int[] prices = new int[100];
			int idx = 0;
			while ((line = reader.readLine()) != null) {
				if (idx >= prices.length) {
					prices = Arrays.copyOf(prices, prices.length + 100);
				}
				prices[idx++] = Integer.valueOf(line);
			}

			prices = Arrays.copyOf(prices, idx);
			long start = System.currentTimeMillis();
			System.out.println(maxProfit(prices));
			long end = System.currentTimeMillis();
			System.out.println("it takes [" + (end - start)
					+ "] milliseconds to proces");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// System.out.println(maxProfit(new int[] { 9, 10, 9, 8, 20 }));
	}

	// public static int maxProfit(int[] prices) {
	// if (prices == null || prices.length <= 1) {
	// return 0;
	// }
	// int n = prices.length;
	//
	// int profit = 0;
	// for (int i = 1; i < n; i++) {
	// int diff = prices[i] - prices[i - 1];
	// if (diff > 0) {
	// profit += diff;
	// }
	// }
	// return profit;
	// }
	//
	// public static int maxProfit(int[] prices) {
	// if(prices == null || prices.length <= 1) {
	// return 0;
	// }
	//
	// int max = 0;
	// int tmp = 0;
	// for(int i = 1; i < prices.length; i++) {
	// int x = prices[i - 1];
	// int y = prices[i];
	// tmp += y - x;
	// if(tmp < 0) {
	// tmp = 0;
	// }
	// if(tmp > max) {
	// max = tmp;
	// }
	// }
	// return max;
	// }

	public static int maxProfit(int[] prices) {
		if(prices == null || prices.length <= 1) {
			return 0;
		}
		int n = prices.length;
		int[] xs = new int[n];
		int[] ys = new int[n];
		
		int min = prices[0];
		int max = prices[n - 1];
		for(int i = 1, j = n - 2; i < n; i++, j--) {
			xs[i] = Math.max(xs[i - 1], prices[i] - min);
			min = Math.min(prices[i], min);
			ys[j] = Math.max(ys[j + 1], max - prices[j]);
			max = Math.max(prices[j], max);
		}
		
		int result = 0;
		for(int i = 0; i < n - 1; i++) {
			result = Math.max(xs[i] + ys[i + 1], result);
		}
		return Math.max(result, xs[n - 1]);
	}
}
