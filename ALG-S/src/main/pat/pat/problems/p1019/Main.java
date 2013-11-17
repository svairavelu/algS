/**
 * 
 */
package pat.problems.p1019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Blues
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String[] line = br.readLine().split("\\s+");
			long n = Long.valueOf(line[0]);
			long base = Long.valueOf(line[1]);
			long[] seq = seq(n, base);
						
			boolean flag = true;
			
			for(int i = 0, j = seq.length - 1; i < j; i++, j--) {
				if(seq[i] != seq[j]) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
			}
			int j = seq.length - 1;
			System.out.print(seq[j]);
			for(j = j - 1; j >= 0; j--) {
				System.out.print(" " + seq[j]);
			}
			System.out.println();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private static long[] seq(final long n, final long base) {
		int MAX_LEN = 35;
//		
		if(n == 0) {
			return new long[]{0};
		}
		long tmp = n;
		int pos = 0;
		long[] seq = new long[MAX_LEN];
		
		while(tmp > 0) {
			long left = (tmp % base);
			seq[pos++] = left;
			tmp = tmp / base;
		}
		
		return Arrays.copyOf(seq, pos);
	}
}
