package pat.problems.p1023;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		int[] xs = new int[10];
		int[] ys = new int[10];
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String line = br.readLine();
			char[] cs = line.toCharArray();
			int[] rs = new int[cs.length];
			int carry = 0;
			for(int i = cs.length - 1; i >= 0; i--) {
				int x = cs[i] - '0';
				xs[x] += 1;
				int y = carry + (x << 1);
				if(y >= 10) {
					y = y - 10;
					carry = 1;
				} else {
					carry = 0;
				}
				ys[y] += 1;
				rs[i] = y;
			}
			
			boolean match = carry == 0 ? true : false;
			for(int i = 0; match && i < 10; i++) {
				if(xs[i] != ys[i]) {
					match = false;
					break;
				}
			}
			
			if(match) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
				if(carry > 0) {
					System.out.print(carry);
				}
			}
			
			for(int i = 0; i < rs.length; i++) {
				System.out.print(rs[i]);
			}
			System.out.println();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
