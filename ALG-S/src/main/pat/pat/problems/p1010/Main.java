package pat.problems.p1010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] line = br.readLine().split(" ");
			String a = line[0];
			String b = line[1];
			int which = Integer.valueOf(line[2]);
			int base = Integer.valueOf(line[3]);
			
			int baseOfOther;
			if(which == 1) {
				baseOfOther = check(a, base, b);
			} else {
				baseOfOther = check(b, base, a);
			}
			
			if(baseOfOther >= 0) {
				System.out.println(baseOfOther);
			} else {
				System.out.println("Impossible");
			}
			
		} finally {
			br.close();
		}
	}
	
	private static int d2n(char d) {
		if(d <= '9') {
			return d - '0';
		} else {
			return d - 'a' + 10;
		}
	}
	
	private static int check(String x, int base, String y) {
		long sumOfX = sum(x, base);
		char[] cs = y.toCharArray();
		char maxC = '0';
		for(int i = 0; i < cs.length; i++) {
			if(cs[i] > maxC) {
				maxC = cs[i];
			}
		}
		
		long low = d2n(maxC) + 1;
		long high = low + 1;
		if(high < sumOfX + 1) {
			high = sumOfX + 1;
		}
		
		while(low <= high) {
			long mid = (low + high) >> 1;
			long sumOfY = sum(y, (int)mid, sumOfX);
			if(sumOfY > sumOfX) {
				high = mid - 1;
			} else if(sumOfY < sumOfX) {
				low = mid + 1;
			} else {
				return (int)mid;
			}
		}
		return -1;
	}
	

	private static long sum(String x, int base) {
		return sum(x, base, 0);
	}
	
	private static long sum(String x, int base, long limit) {
		char[] xcs = x.toCharArray();
		
		long value = 0;
		
		for(int i = 0; i < xcs.length; i++) {
			char xc = xcs[i];
			int xcv = d2n(xc);
			value = value * base + xcv;
			if(limit > 0 && value > limit) {
				value = limit + 1;
				break;
			}
		}
		
		return value;
	}

}
