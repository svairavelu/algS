package pat.problems.p1037;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int nc = Integer.valueOf(br.readLine());
			long[] as = new long[nc];
			String[] ax = br.readLine().split("\\s+");
			for(int i = 0; i < ax.length; i++) {
				as[i] = Long.valueOf(ax[i]);
			}
			
			int np = Integer.valueOf(br.readLine());
			long[] bs = new long[np];
			String[] bx = br.readLine().split("\\s+");
			for(int i = 0; i < bx.length; i++) {
				bs[i] = Long.valueOf(bx[i]);
			}
			
			Arrays.sort(as);
			Arrays.sort(bs);
			
			long sum = 0;
			for(int i = 0; i < as.length && i < bs.length; i++) {
				if(as[i] >= 0 || bs[i] >= 0) {
					break;
				}
				
				sum += (as[i] * bs[i]);
			}
			
			for(int i = as.length - 1, j = bs.length - 1; i >= 0 && j >= 0; i--, j--) {
				if(as[i] <= 0 || bs[j] <= 0) {
					break;
				}
				sum += as[i] * bs[j];
			}
			
			System.out.println(sum);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
