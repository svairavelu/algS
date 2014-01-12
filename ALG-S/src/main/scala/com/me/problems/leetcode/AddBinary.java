/**
 * 
 */
package com.me.problems.leetcode;

import scala.actors.threadpool.Arrays;

/**
 * @author Blues
 * 
 */
public class AddBinary {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(addBinary("1", "1"));
	}

	public static String addBinary(String a, String b) {
		char[] as = a.toCharArray();
		char[] bs = b.toCharArray();
		
		int i = as.length - 1;
		int j = bs.length - 1;
		char[] cs = new char[Math.max(i, j) + 2];
		int index = 0;
		
		int carray = 0;
		
		while(i >=  0 && j >= 0) {
			int x = as[i--] - '0';
			int y = bs[j--] - '0';
			int c = x + y + carray;
			if(c > 1) {
				carray = 1;
				cs[index++] = (char)(c -2 + '0');
			} else {
				carray = 0;
				cs[index++] = (char)(c + '0');
			}
		}
		
		while(i >= 0) {
			int x = as[i--] - '0';
			int c = x + carray;
			if(c > 1) {
				carray = 1;
				cs[index++] = (char)(c -2 + '0');
			} else {
				carray = 0;
				cs[index++] = (char)(c + '0');
			}
		}
		
		while(j >= 0) {
			int y = bs[j--] - '0';
			int c = y + carray;
			if(c > 1) {
				carray = 1;
				cs[index++] = (char)(c -2 + '0');
			} else {
				carray = 0;
				cs[index++] = (char)(c + '0');
			}
		}
		
		if(carray == 1) {
			cs[index++] = (char)(carray + '0');
		}
		
		cs = Arrays.copyOf(cs, index);
		for(i = 0, j = index -1; i < j; i++, j --) {
			char x = cs[i];
			char y = cs[j];
			cs[i] = y;
			cs[j] = x;
		}
		return String.valueOf(cs);
	}
}
