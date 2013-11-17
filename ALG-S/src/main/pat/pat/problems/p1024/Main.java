package pat.problems.p1024;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] line = br.readLine().split("\\s+");
			String nl = line[0];
			int k = Integer.valueOf(line[1]);
			int step = 0;
			
			char[] cs = nl.toCharArray();
			int[] xs = new int[cs.length];
			for(int i = 0; i < cs.length; i++) {
				xs[i] = cs[i] - '0';
			}
			int[] ys = xs;
			while(!isPalindromic(ys) && step < k) {
				ys = addSelfWithReverse(ys);				
				step += 1;
			}
			
			for(int i = 0; i < ys.length; i++) {
				System.out.print(ys[i]);
			}
			System.out.println();
			System.out.println(step);			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private static boolean isPalindromic(int[] xs) {
		boolean rt = true;
		for(int i = 0, j = xs.length - 1; i < j; i++, j--) {
			if(xs[i] != xs[j]) {
				rt = false;
				break;
			}
		}
		return rt;
	}

	private static int[] addSelfWithReverse(int[] xs) {
		int n = xs.length;
		int[] ys = new int[n + 1];
		int carry = 0;
		for(int i = n - 1; i >= 0; i--) {
			int x = xs[i];
			int y = xs[n - 1 - i];
			int s = x + y + carry;
			if(s >= 10) {
				ys[i + 1] = s - 10;
				carry = 1;
			} else {
				ys[i + 1] = s;
				carry = 0;
			}
		}
		ys[0] = carry;
		if(carry == 0) {
			return Arrays.copyOfRange(ys, 1, n + 1);
		} else {
			return ys;
		}
	}
}
